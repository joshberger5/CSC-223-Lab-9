package preprocessor;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;

import exceptions.FactException;
import geometry_objects.Segment;
import geometry_objects.angle.Angle;
import geometry_objects.angle.AngleEquivalenceClasses;
import geometry_objects.points.Point;
import geometry_objects.points.PointDatabase;
import input.components.FigureNode;
import preprocessor.delegates.ImplicitPointPreprocessor;
import input.InputFacade;

class AngleIdentifierTest
{
	protected PointDatabase _points;
	protected Preprocessor _pp;
	protected Map<Segment, Segment> _segments;
	protected PointDatabase _iPoints;
	
	protected void init(String filename)
	{
		FigureNode fig = InputFacade.extractFigure(filename);

		Map.Entry<PointDatabase, Set<Segment>> pair = InputFacade.toGeometryRepresentation(fig);

		_points = pair.getKey();
		Set<Segment> segments = pair.getValue();

		_pp = new Preprocessor(_points, pair.getValue());

		_pp.analyze();

		_segments = _pp.getAllSegments();
		_iPoints = new PointDatabase(new ArrayList<Point>(ImplicitPointPreprocessor.compute(_points, new ArrayList<Segment>(segments))));
	}
	
	//      A                                 
	//     / \                                
	//    B___C                               
	//   / \ / \                              
	//  /   X   \  X is not a specified point (it is implied) 
	// D_________E
	//
	// This figure contains 44 angles
	//
	@Test
	void test_crossing_symmetric_triangle()
	{
		init("crossing_symmetric_triangle.json");

		AngleIdentifier angleIdentifier = new AngleIdentifier(_segments);

		AngleEquivalenceClasses computedAngles = angleIdentifier.getAngles();

		System.out.println(computedAngles);
		
		// The number of classes should equate to the number of 'minimal' angles
		//assertEquals(25, computedAngles.numClasses(), "Number of Angle Equivalence classes");
		
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
			// A
			expectedAngles.add(new Angle(ab, ac)); // 1
			expectedAngles.add(new Angle(ad, ac));
			expectedAngles.add(new Angle(ab, ae));
			expectedAngles.add(new Angle(ad, ae));
			
			// B
			expectedAngles.add(new Angle(ab, bc)); // 2
			
			expectedAngles.add(new Angle(ab, a_star_b)); // 3
			expectedAngles.add(new Angle(ab, be));
			
			expectedAngles.add(new Angle(ab, bd)); // 4
			
			expectedAngles.add(new Angle(bc, a_star_b)); // 5
			expectedAngles.add(new Angle(bc, be));
			
			expectedAngles.add(new Angle(bc, bd)); // 6
			
			expectedAngles.add(new Angle(a_star_b, bd)); // 7
			expectedAngles.add(new Angle(be, bd));
			
			// C
			expectedAngles.add(new Angle(ac, bc)); // 8
			
			expectedAngles.add(new Angle(ac, a_star_c)); // 9
			expectedAngles.add(new Angle(ac, cd));
			
			expectedAngles.add(new Angle(ac, ce)); // 10
			
			expectedAngles.add(new Angle(bc, a_star_c)); // 11
			expectedAngles.add(new Angle(bc, cd));
			
			expectedAngles.add(new Angle(bc, ce)); // 12
			
			expectedAngles.add(new Angle(a_star_c, ce)); // 13
			expectedAngles.add(new Angle(cd, ce));
			
			// *_A
			expectedAngles.add(new Angle(a_star_b, a_star_c)); // 14
			
			expectedAngles.add(new Angle(a_star_b, a_star_e)); // 15
			
			expectedAngles.add(new Angle(a_star_b, a_star_d)); // 16
			
			expectedAngles.add(new Angle(a_star_c, a_star_e)); // 17
			
			expectedAngles.add(new Angle(a_star_c, a_star_d)); // 18
			
			expectedAngles.add(new Angle(a_star_e, a_star_d)); // 19
			
			// D
			expectedAngles.add(new Angle(bd, a_star_d)); // 20
			expectedAngles.add(new Angle(ad, a_star_d));
			
			expectedAngles.add(new Angle(bd, cd)); // 21
			expectedAngles.add(new Angle(ad, cd));
			expectedAngles.add(new Angle(bd, de));
			expectedAngles.add(new Angle(ad, de));
			
			expectedAngles.add(new Angle(a_star_d, de)); // 22
			expectedAngles.add(new Angle(cd, de));
			
			// E
			expectedAngles.add(new Angle(ce, a_star_e)); // 23
			expectedAngles.add(new Angle(ae, a_star_e));
			expectedAngles.add(new Angle(ce, be));
			expectedAngles.add(new Angle(ae, be));
			
			expectedAngles.add(new Angle(ce, de)); // 24
			expectedAngles.add(new Angle(ae, de));
			
			expectedAngles.add(new Angle(a_star_e, de)); // 25
			expectedAngles.add(new Angle(be, de));
		}
		catch (FactException te) { System.err.println("Invalid Angles in Angle test."); }

		assertEquals(expectedAngles.size(), computedAngles.size());
		
		//
		// Equality
		//
		for (Angle expected : expectedAngles)
		{
			if(!computedAngles.contains(expected)) System.out.println("Not: " + expected);
			assertTrue(computedAngles.contains(expected));
		}
	}
	
	//
	// A---B---C---D---E---F
	// This figure contains 20 angles
	@Test
	void test_collinear_line_segments()
	{
		init("collinear_line_segments.json");
		
		AngleIdentifier angleIdentifier = new AngleIdentifier(_segments);

		AngleEquivalenceClasses computedAngles = angleIdentifier.getAngles();
		
		//
		// ALL original segments: 5 in this figure.
		//
		
		Segment ab = new Segment(_points.getPoint("A"), _points.getPoint("B"));
		Segment bc = new Segment(_points.getPoint("B"), _points.getPoint("C"));
		Segment cd = new Segment(_points.getPoint("C"), _points.getPoint("D"));
		Segment de = new Segment(_points.getPoint("D"), _points.getPoint("E"));
		Segment ef = new Segment(_points.getPoint("E"), _points.getPoint("F"));
		
		//
		// Non-minimal, computed segments: 10 in this figure.
		//
		Segment ac = new Segment(_points.getPoint("A"), _points.getPoint("C"));
		Segment ad = new Segment(_points.getPoint("A"), _points.getPoint("D"));
		Segment ae = new Segment(_points.getPoint("A"), _points.getPoint("E"));
		Segment af = new Segment(_points.getPoint("A"), _points.getPoint("F"));
		Segment bd = new Segment(_points.getPoint("B"), _points.getPoint("D"));
		Segment be = new Segment(_points.getPoint("B"), _points.getPoint("E"));
		Segment bf = new Segment(_points.getPoint("B"), _points.getPoint("F"));
		Segment ce = new Segment(_points.getPoint("C"), _points.getPoint("E"));
		Segment cf = new Segment(_points.getPoint("C"), _points.getPoint("F"));
		Segment df = new Segment(_points.getPoint("D"), _points.getPoint("F"));

		//
		// Angles we expect to find (20)
		//
		List<Angle> expectedAngles = new ArrayList<Angle>();
		try {
			expectedAngles.add(new Angle(ab, bc));
			expectedAngles.add(new Angle(ab, bd));
			expectedAngles.add(new Angle(ab, be));
			expectedAngles.add(new Angle(ab, bf));
			
			expectedAngles.add(new Angle(ac, cd));
			expectedAngles.add(new Angle(bc, cd));
			expectedAngles.add(new Angle(ac, ce));
			expectedAngles.add(new Angle(bc, ce));
			expectedAngles.add(new Angle(ac, cf));
			expectedAngles.add(new Angle(bc, cf));
			
			expectedAngles.add(new Angle(ad, de));
			expectedAngles.add(new Angle(bd, de));
			expectedAngles.add(new Angle(cd, de));
			expectedAngles.add(new Angle(ad, df));
			expectedAngles.add(new Angle(bd, df));
			expectedAngles.add(new Angle(cd, df));
			
			expectedAngles.add(new Angle(ae, ef));
			expectedAngles.add(new Angle(be, ef));
			expectedAngles.add(new Angle(ce, ef));
			expectedAngles.add(new Angle(de, ef));
		}
		catch (FactException te) { System.err.println("Invalid Angles in Angle test."); }
		
		assertEquals(expectedAngles.size(), computedAngles.size());
		
		//
		// Equality
		//
		for (Angle expected : expectedAngles)
		{
			assertTrue(computedAngles.contains(expected));
		}
	}
}
