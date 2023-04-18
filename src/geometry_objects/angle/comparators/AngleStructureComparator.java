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
		// checks if left's 1st ray corresponds with one of right's rays
		// if so, saves which one
		// if not, the angle's are structurally incomparable
		Segment leftRay1Corresponder;
		if (left.getRay1().isCollinearWith(right.getRay1())) leftRay1Corresponder = right.getRay1();
		else if (left.getRay1().isCollinearWith(right.getRay1())) leftRay1Corresponder = right.getRay2();
		else return STRUCTURALLY_INCOMPARABLE;
		
		// checks if left's 2st ray corresponds with one of right's rays
		// if so, saves which one
		// if not, the angle's are structurally incomparable
		Segment leftRay2Corresponder;
		if (left.getRay2().isCollinearWith(right.getRay1())) leftRay2Corresponder = right.getRay1();
		else if (left.getRay2().isCollinearWith(right.getRay1())) leftRay2Corresponder = right.getRay2();
		else return STRUCTURALLY_INCOMPARABLE;
		
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
