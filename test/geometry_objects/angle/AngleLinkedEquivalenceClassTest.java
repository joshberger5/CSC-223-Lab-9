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
import preprocessor.Preprocessor;
import preprocessor.delegates.ImplicitPointPreprocessor;

public class AngleLinkedEquivalenceClassTest {
	
	//
	//    A
	//    |
	//    B---C
	//    |
	//    D
	//
	@Test
	void testDifferentClasses()
	{
		Point a = new Point("A", 0, 2);
		Point b = new Point("B", 0, 1);
		Point c = new Point("C", 1, 1);
		Point d = new Point("D", 0, 0);
		
		Segment ab = new Segment(a, b);
		Segment bc = new Segment(b, c);
		Segment bd = new Segment(b, d);

		//
		// Non-minimal, computed segments: 1 in this figure.
		//
		Segment ad = new Segment(a, d);

		List<Angle> angles = new ArrayList<Angle>();
		try {
			// in class
			angles.add(new Angle(ab, bd));
			
			// not in class
			angles.add(new Angle(ab, bc));
			angles.add(new Angle(bc, bd));
		}
		catch (FactException te) { System.err.println("Invalid Angles in Angle test."); }

		
		AngleLinkedEquivalenceClass classes = new AngleLinkedEquivalenceClass();
		for(Angle angle: angles) {
			classes.add(angle);
		}
		
		assertEquals(1, classes.size());
	}
	
	//
	//    A---B
	//    |
	//    C
	//    |
	//    D
	//
	@Test
	void testSameClasses()
	{
		Point a = new Point("A", 0, 2);
		Point b = new Point("B", 1, 2);
		Point c = new Point("C", 0, 1);
		Point d = new Point("D", 0, 0);
		
		Segment ab = new Segment(a, b);
		Segment ac = new Segment(a, c);
		Segment cd = new Segment(c, d);

		//
		// Non-minimal, computed segments: 1 in this figure.
		//
		Segment ad = new Segment(a, d);

		List<Angle> angles = new ArrayList<Angle>();
		try {
			angles.add(new Angle(ab, ac));
			angles.add(new Angle(ab, ad));
		}
		catch (FactException te) { System.err.println("Invalid Angles in Angle test."); }

		
		AngleLinkedEquivalenceClass classes = new AngleLinkedEquivalenceClass();
		for(Angle angle: angles) {
			classes.add(angle);
		}
		
		assertEquals(2, classes.size());
	}
	
	//
	//        A
	//        |
	//    E---B---C
	//        |
	//        D
	//
	@Test
	void testCross()
	{
		Point a = new Point("A", 1, 2);
		Point b = new Point("B", 1, 1);
		Point c = new Point("C", 2, 1);
		Point d = new Point("D", 1, 0);
		Point e = new Point("E", 0, 1);
		
		Segment ab = new Segment(a, b);
		Segment bc = new Segment(b, c);
		Segment bd = new Segment(b, d);
		Segment be = new Segment(b, e);

		//
		// Non-minimal, computed segments: 2 in this figure.
		//
		Segment ad = new Segment(a, d);
		Segment ce = new Segment(c, e);

		List<Angle> angles = new ArrayList<Angle>();
		try {
			// in class
			angles.add(new Angle(ab, bc));
			
			// not in class
			angles.add(new Angle(bd, be));
		}
		catch (FactException te) { System.err.println("Invalid Angles in Angle test."); }

		
		AngleLinkedEquivalenceClass classes = new AngleLinkedEquivalenceClass();
		for(Angle angle: angles) {
			classes.add(angle);
		}
		
		assertEquals(1, classes.size());
	}
	
	//
	//          A
	//         /
	//    E---B---C
	//       /
	//      D
	//
	@Test
	void testTiltedCross()
	{
		Point a = new Point("A", 1.5, 2);
		Point b = new Point("B", 1, 1);
		Point c = new Point("C", 2, 1);
		Point d = new Point("D", 0.5, 0);
		Point e = new Point("E", 0, 1);
		
		Segment ab = new Segment(a, b);
		Segment bc = new Segment(b, c);
		Segment bd = new Segment(b, d);
		Segment be = new Segment(b, e);

		//
		// Non-minimal, computed segments: 2 in this figure.
		//
		Segment ad = new Segment(a, d);
		Segment ce = new Segment(c, e);

		List<Angle> angles = new ArrayList<Angle>();
		try {
			// in class
			angles.add(new Angle(ab, bc));
			
			// not in class
			angles.add(new Angle(bd, be));
		}
		catch (FactException te) { System.err.println("Invalid Angles in Angle test."); }

		
		AngleLinkedEquivalenceClass classes = new AngleLinkedEquivalenceClass();
		for(Angle angle: angles) {
			classes.add(angle);
		}
		
		assertEquals(1, classes.size());
	}
	
	//
	//   A       E
	//    \     /
	//     B   D
	//      \ /
	//       C
	//
	@Test
	void testExtendedV()
	{
		Point a = new Point("A", 0, 2);
		Point b = new Point("B", 1, 1);
		Point c = new Point("C", 2, 0);
		Point d = new Point("D", 3, 1);
		Point e = new Point("E", 4, 2);
		
		Segment ab = new Segment(a, b);
		Segment bc = new Segment(b, c);
		Segment cd = new Segment(c, d);
		Segment de = new Segment(d, e);

		//
		// Non-minimal, computed segments: 2 in this figure.
		//
		Segment ac = new Segment(a, c);
		Segment ce = new Segment(c, e);

		List<Angle> angles = new ArrayList<Angle>();
		try {
			// in class
			angles.add(new Angle(ac, ce));
			angles.add(new Angle(ac, cd));
			angles.add(new Angle(bc, ce));
			angles.add(new Angle(bc, cd));
		}
		catch (FactException te) { System.err.println("Invalid Angles in Angle test."); }

		
		AngleLinkedEquivalenceClass classes = new AngleLinkedEquivalenceClass();
		for(Angle angle: angles) {
			classes.add(angle);
		}
		
		assertEquals(4, classes.size());
	}
	
}