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
		if (left == null || right == null) return STRUCTURALLY_INCOMPARABLE;
		if(left.getMeasure() != right.getMeasure()) return STRUCTURALLY_INCOMPARABLE;
		
		// checks if left's 1st ray corresponds with one of right's rays
		// if so, saves which one
		// if not, the angles are structurally incomparable
		Segment leftRay1Corresponder = corresponder(left.getRay1(), right);
		if (leftRay1Corresponder == null) return STRUCTURALLY_INCOMPARABLE;
		
		// checks if left's 1st ray corresponds with one of right's rays
		// if so, saves which one
		// if not, the angles are structurally incomparable
		Segment leftRay2Corresponder = corresponder(left.getRay2(), right);
		if (leftRay2Corresponder == null) return STRUCTURALLY_INCOMPARABLE;
		
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
	
	/**
	 * Checks to see if a Segment is collinear with one of the Rays for an Angle
	 * @param s - the Segment to check against the Rays
	 * @param a - the Angle containing the Rays to check against the Segment
	 * @return which Ray from Angle a s is collinear with,
	 * if neither, returns null
	 */
	private Segment corresponder(Segment s, Angle a) {
		if (s.isCollinearWith(a.getRay1())) return a.getRay1();
		if (s.isCollinearWith(a.getRay2())) return a.getRay2();
		return null;
	}
}
