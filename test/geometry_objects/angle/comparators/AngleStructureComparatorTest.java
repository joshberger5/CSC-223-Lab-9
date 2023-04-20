package geometry_objects.angle.comparators;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import exceptions.FactException;
import geometry_objects.Segment;
import geometry_objects.angle.Angle;
import geometry_objects.points.Point;

class AngleStructureComparatorTest {

	@Test
	void StructurallyComparableEqualsTest() throws FactException {
		AngleStructureComparator asc = new AngleStructureComparator();
		
		//        A
		//        /\
		//      B/  \C
		//      /    \
		//    D/______\E
		
		Point A = new Point("A", 10, 10);
		Point B = new Point("B", 6, 6);
		Point C = new Point("C", 14, 6);
		Point D = new Point("D", 2, 2);
		Point E = new Point("E", 18, 2);
		
		Segment DA = new Segment(D, A);
		Segment AC = new Segment(A, C);
		Segment CA = new Segment(C, A);
		Segment AD = new Segment(A, D);
		
		Angle DAC = new Angle(DA, AC);
		Angle CAD = new Angle(CA, AD);
		
		assertEquals(1, asc.compare(DAC, CAD));
	}
	
	@Test
	void StructurallyComparableWithLeftAngleGreaterTest() throws FactException {
		AngleStructureComparator asc = new AngleStructureComparator();
		
		//        A
		//        /\
		//      B/  \C
		//      /    \
		//    D/______\E
		
		Point A = new Point("A", 10, 10);
		Point B = new Point("B", 6, 6);
		Point C = new Point("C", 14, 6);
		Point D = new Point("D", 2, 2);
		Point E = new Point("E", 18, 2);
		
		Segment DA = new Segment(D, A);
		Segment AC = new Segment(A, C);
		Segment BA = new Segment(B, A);
		
		Angle DAC = new Angle(DA, AC);
		Angle BAC = new Angle(BA, AC);
		
		assertEquals(1, asc.compare(DAC, BAC));
	}
	
	@Test
	void StructurallyComparableWithNeitherAngleGreaterTest() throws FactException {
		AngleStructureComparator asc = new AngleStructureComparator();
		
		//        A
		//        /\
		//      B/  \C
		//      /    \
		//    D/______\E
		
		Point A = new Point("A", 10, 10);
		Point B = new Point("B", 6, 6);
		Point C = new Point("C", 14, 6);
		Point D = new Point("D", 2, 2);
		Point E = new Point("E", 18, 2);
		
		Segment DA = new Segment(D, A);
		Segment AC = new Segment(A, C);
		Segment BA = new Segment(B, A);
		Segment AE = new Segment(A, E);
		
		Angle DAC = new Angle(DA, AC);
		Angle BAE = new Angle(BA, AE);
		
		assertEquals(0, asc.compare(DAC, BAE));
	}

	@Test
	void StructurallyComparableWithRightAngleGreaterTest() throws FactException {
		AngleStructureComparator asc = new AngleStructureComparator();
		
		//        A
		//        /\
		//      B/  \C
		//      /    \
		//    D/______\E
		
		Point A = new Point("A", 10, 10);
		Point B = new Point("B", 6, 6);
		Point C = new Point("C", 14, 6);
		Point D = new Point("D", 2, 2);
		Point E = new Point("E", 18, 2);
		
		Segment DA = new Segment(D, A);
		Segment AC = new Segment(A, C);
		Segment BA = new Segment(B, A);
		
		Angle DAC = new Angle(DA, AC);
		Angle BAC = new Angle(BA, AC);
		
		assertEquals(-1, asc.compare(BAC, DAC));
	}
	
	@Test
	void StructurallyIncomparableNotSameVertextOrOverlappingRaysTest() throws FactException {
		AngleStructureComparator asc = new AngleStructureComparator();
		
		//        A
		//        /\
		//      B/  \C
		//      /    \
		//    D/______\E
		
		Point A = new Point("A", 10, 10);
		Point B = new Point("B", 6, 6);
		Point C = new Point("C", 14, 6);
		Point D = new Point("D", 2, 2);
		Point E = new Point("E", 18, 2);
		
		Segment DA = new Segment(D, A);
		Segment AC = new Segment(A, C);
		Segment DE = new Segment(D, E);
		Segment EC = new Segment(E, C);
		
		Angle DAC = new Angle(DA, AC);
		Angle DEC = new Angle(DE, EC);
		
		assertEquals(Integer.MAX_VALUE, asc.compare(DEC, DAC));
	}
	
	@Test
	void StructurallyIncomparableSameVertextNotOverlappingRaysTest() throws FactException {
		AngleStructureComparator asc = new AngleStructureComparator();
		
		//         A
		//        /|\
		//      B/ | \C
		//      /  |  \
		//    D/___F___\E
		
		Point A = new Point("A", 10, 10);
		Point B = new Point("B", 6, 6);
		Point C = new Point("C", 14, 6);
		Point D = new Point("D", 2, 2);
		Point E = new Point("E", 18, 2);
		Point F = new Point("F", 10, 2);
		
		Segment DA = new Segment(D, A);
		Segment BA = new Segment(B, A);
		Segment AC = new Segment(A, C);
		Segment AF = new Segment(A, F);
		
		Angle DAC = new Angle(DA, AC);
		Angle BAF = new Angle(BA, AF);
		
		assertEquals(Integer.MAX_VALUE, asc.compare(BAF, DAC));
	}
	
	@Test
	void NullTest() throws FactException {
		AngleStructureComparator asc = new AngleStructureComparator();
		
		//         A
		//        /|\
		//      B/ | \C
		//      /  |  \
		//    D/___F___\E
		
		Point A = new Point("A", 10, 10);
		Point B = new Point("B", 6, 6);
		Point C = new Point("C", 14, 6);
		Point D = new Point("D", 2, 2);
		Point E = new Point("E", 18, 2);
		Point F = new Point("F", 10, 2);
		
		Segment DA = new Segment(D, A);
		Segment AC = new Segment(A, C);
		
		Angle DAC = new Angle(DA, AC);
		
		assertEquals(Integer.MAX_VALUE, asc.compare(null, DAC));
	}
}
