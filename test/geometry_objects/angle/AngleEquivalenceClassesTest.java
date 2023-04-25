package geometry_objects.angle;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;

import exceptions.FactException;
import geometry_objects.Segment;
import geometry_objects.angle.Angle;
import geometry_objects.points.Point;
import geometry_objects.points.PointDatabase;
import input.InputFacade;
import input.components.FigureNode;
import preprocessor.AngleIdentifier;
import preprocessor.Preprocessor;
import preprocessor.delegates.ImplicitPointPreprocessor;

public class AngleEquivalenceClassesTest {
	
	protected PointDatabase _points;
	protected Preprocessor _pp;
	protected Map<Segment, Segment> _segments;
	protected PointDatabase _iPoints;
	
	protected void init(String filename)
	{
		FigureNode fig = InputFacade.extractFigure("crossing_symmetric_triangle.json");

		Map.Entry<PointDatabase, Set<Segment>> pair = InputFacade.toGeometryRepresentation(fig);

		_points = pair.getKey();
		Set<Segment> segments = pair.getValue();

		_pp = new Preprocessor(_points, pair.getValue());

		_pp.analyze();

		_segments = _pp.getAllSegments();
		_iPoints = new PointDatabase(new ArrayList<Point>(ImplicitPointPreprocessor.compute(_points, new ArrayList<Segment>(segments))));
	}
	
	//       A      
	//      / \     
	//     B___C    
	//    / \ / \   
	//   /  *_A  \  *_A is not a specified point (it is implied)
	//  / /     \ \ 
	// D-----------E
	// This figure contains 40 angles
	//
	@Test
	void test_crossing_symmetric_triangle()
	{
		init("crossing_symmetric_triangle.json");
		
		//
		// ALL original segments: 8 in this figure.
		//
		Segment ab = new Segment(_points.getPoint("A"), _points.getPoint("B"));
		Segment ac = new Segment(_points.getPoint("A"), _points.getPoint("C"));
		Segment bc = new Segment(_points.getPoint("B"), _points.getPoint("C"));

		Segment bd = new Segment(_points.getPoint("B"), _points.getPoint("D"));
		Segment ce = new Segment(_points.getPoint("C"), _points.getPoint("E"));
		Segment de = new Segment(_points.getPoint("D"), _points.getPoint("E"));

		Segment be = new Segment(_points.getPoint("B"), _points.getPoint("E"));
		Segment cd = new Segment(_points.getPoint("C"), _points.getPoint("D"));

		//
		// Implied minimal segments: 4 in this figure.
		//
		Point a_star = _iPoints.getPoint(3,3);
		
		Segment a_star_b = new Segment(a_star, _points.getPoint("B"));
		Segment a_star_c = new Segment(a_star, _points.getPoint("C"));
		Segment a_star_d = new Segment(a_star, _points.getPoint("D"));
		Segment a_star_e = new Segment(a_star, _points.getPoint("E"));

		//
		// Non-minimal, computed segments: 2 in this figure.
		//
		Segment ad = new Segment(_points.getPoint("A"), _points.getPoint("D"));
		Segment ae = new Segment(_points.getPoint("A"), _points.getPoint("E"));

		//
		// Angles we expect to find
		//
		List<Angle> expectedAngles = new ArrayList<Angle>();
		try {
			expectedAngles.add(new Angle(ab, ac));
			expectedAngles.add(new Angle(ad, ac));
			expectedAngles.add(new Angle(ab, ae));
			expectedAngles.add(new Angle(ad, ae));
			
			
			expectedAngles.add(new Angle(ab, bc));
			expectedAngles.add(new Angle(ab, a_star_b));
			expectedAngles.add(new Angle(ab, be));
			expectedAngles.add(new Angle(ab, bd));
			
			expectedAngles.add(new Angle(bc, a_star_b));
			expectedAngles.add(new Angle(bc, be));
			expectedAngles.add(new Angle(bc, bd));
			
			expectedAngles.add(new Angle(a_star_b, bd));
			expectedAngles.add(new Angle(be, bd));
			
			
			expectedAngles.add(new Angle(ac, bc));
			expectedAngles.add(new Angle(ac, a_star_c));
			expectedAngles.add(new Angle(ac, cd));
			expectedAngles.add(new Angle(ac, ce));
			
			expectedAngles.add(new Angle(bc, a_star_c));
			expectedAngles.add(new Angle(bc, cd));
			expectedAngles.add(new Angle(bc, ce));
			
			expectedAngles.add(new Angle(a_star_c, ce));
			expectedAngles.add(new Angle(cd, ce));
			
			
			expectedAngles.add(new Angle(a_star_b, a_star_c));
			expectedAngles.add(new Angle(a_star_b, a_star_e));
			expectedAngles.add(new Angle(a_star_b, a_star_d));
			expectedAngles.add(new Angle(a_star_c, a_star_e));
			expectedAngles.add(new Angle(a_star_c, a_star_d));
			expectedAngles.add(new Angle(a_star_e, a_star_d));
			
			
			expectedAngles.add(new Angle(bd, a_star_d));
			expectedAngles.add(new Angle(ad, a_star_d));
			expectedAngles.add(new Angle(bd, cd));
			expectedAngles.add(new Angle(ad, cd));
			
			expectedAngles.add(new Angle(bd, de));
			expectedAngles.add(new Angle(ad, de));
			
			expectedAngles.add(new Angle(a_star_d, de));
			expectedAngles.add(new Angle(cd, de));
			
			
			expectedAngles.add(new Angle(ce, a_star_e));
			expectedAngles.add(new Angle(ae, a_star_e));
			expectedAngles.add(new Angle(ce, be));
			expectedAngles.add(new Angle(ae, be));
			
			expectedAngles.add(new Angle(ce, de));
			expectedAngles.add(new Angle(ae, de));
			
			expectedAngles.add(new Angle(a_star_e, de));
			expectedAngles.add(new Angle(be, de));
		}
		catch (FactException te) { System.err.println("Invalid Angles in Angle test."); }

		
		AngleEquivalenceClasses classes = new AngleEquivalenceClasses();
		for(Angle angle: expectedAngles) {
			classes.add(angle);
		}
		
		assertEquals(expectedAngles.size(), classes.size());
		
		//
		// Equality
		//
		for (Angle expected : expectedAngles)
		{
			assertTrue(classes.contains(expected));
		}
	}
}