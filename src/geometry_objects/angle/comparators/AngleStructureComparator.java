/**
 * Write a succinct, meaningful description of the class here. You should avoid wordiness    
 * and redundancy. If necessary, additional paragraphs should be preceded by <p>,
 * the html tag for a new paragraph.
 *
 * <p>Bugs: (a list of bugs and / or other problems)
 *
 * @author <your name>
 * @date   <date of completion>
 */

package geometry_objects.angle.comparators;

import java.util.Comparator;

import geometry_objects.Segment;
import geometry_objects.angle.Angle;
import geometry_objects.points.Point;
import utilities.math.MathUtilities;
import utilities.math.analytic_geometry.GeometryUtilities;

public class AngleStructureComparator implements Comparator<Angle>
{
	public static final int STRUCTURALLY_INCOMPARABLE = Integer.MAX_VALUE;
	
	/**
	 * Given the figure below:
	 * 
	 *    A-------B----C-----------D
	 *     \
	 *      \
	 *       \
	 *        E
	 *         \
	 *          \
	 *           F
	 * 
	 * What we care about is the fact that angle BAE is the smallest angle (structurally)
	 * and DAF is the largest angle (structurally). 
	 * 
	 * If one angle X has both rays (segments) that are subsegments of an angle Y, then X < Y.
	 * 
	 * If only one segment of an angle is a subsegment, then no conclusion can be made.
	 * 
	 * So:
	 *     BAE < CAE
   	 *     BAE < DAF
   	 *     CAF < DAF

   	 *     CAE inconclusive BAF
	 * 
	 * @param left -- an angle
	 * @param right -- an angle
	 * @return -- according to the algorithm above:
 	 *              Integer.MAX_VALUE will refer to our error result
 	 *              0 indicates an inconclusive result
	 *              -1 for less than
	 *              1 for greater than
	 */
	@Override
	public int compare(Angle left, Angle right)
	{
		// checks if the left and right angles exist
		if (left == null || right == null) return STRUCTURALLY_INCOMPARABLE;
		
		if(left == right) return 1;
		
		// check they share a vertex
		if (left.getVertex() != right.getVertex()) return STRUCTURALLY_INCOMPARABLE;
		
		// checks that the measure of the two angles are the same
		if(!MathUtilities.doubleEquals(left.getMeasure(), right.getMeasure())) return STRUCTURALLY_INCOMPARABLE;
		
		// checks if left's 1st ray corresponds with one of right's rays
		// if so, saves which one
		// if not, the angles are structurally incomparable
		Segment leftRay1Corresponder = null;
		if (left.getRay1().isCollinearWith(right.getRay1())) leftRay1Corresponder = right.getRay1();
		if (left.getRay1().isCollinearWith(right.getRay2())) leftRay1Corresponder = right.getRay2();
		if (leftRay1Corresponder == null) return STRUCTURALLY_INCOMPARABLE;
		
		// checks if left's 1st ray corresponds with one of right's rays
		// if so, saves which one
		// if not, the angles are structurally incomparable
		Segment leftRay2Corresponder = null;
		if (left.getRay2().isCollinearWith(right.getRay2())) leftRay2Corresponder = right.getRay2();
		if (left.getRay2().isCollinearWith(right.getRay1())) leftRay2Corresponder = right.getRay1();
		if (leftRay2Corresponder == null) return STRUCTURALLY_INCOMPARABLE;
		
		// check that all of the edge points are on the same side of the vertex
		// as their corresponder
		if(!MathUtilities.doubleEquals(left.getMeasure(), 180)) {
			Point vertex = left.getRay1().sharedVertex(left.getRay2());
			
			Point leftEdge1 = left.getRay1().other(vertex);
			Point leftEdge2 = left.getRay2().other(vertex);
			Point rightEdge1 = leftRay1Corresponder.other(vertex);
			Point rightEdge2 = leftRay2Corresponder.other(vertex);
			
			double distance1 = Point.distance(leftEdge1, rightEdge1);
			double distance2 = Point.distance(leftEdge2, rightEdge2);
		
			if((distance1 > Point.distance(leftEdge1,  vertex) &&
			   distance1 > Point.distance(rightEdge1, vertex)) ||
			   (distance2 > Point.distance(leftEdge2,  vertex) &&
			   distance2 > Point.distance(rightEdge2, vertex)))
				return STRUCTURALLY_INCOMPARABLE;
		}
		
		// checks both rays for the left angle are greater than
		// or equal in length to the corresponding rays in the right angle
		// if so, return 1
		if (left.getRay1().length() >= leftRay1Corresponder.length() &&
			left.getRay2().length() >= leftRay2Corresponder.length()) 
			return 1;
		
		// checks both rays for the left angle are less than
		// or equal in length to the corresponding rays in the right angle
		// if so, return -1
		if (left.getRay1().length() <= leftRay1Corresponder.length() &&
			left.getRay2().length() <= leftRay2Corresponder.length()) 
			return -1;
		
		// they are structurally comparable, but both rays for the left angle
		// aren't both greater or both less than both rays for the right angle
		return 0;
	}
}
