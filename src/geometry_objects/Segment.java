package geometry_objects;

import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import geometry_objects.delegates.LineDelegate;
import geometry_objects.delegates.SegmentDelegate;
import geometry_objects.delegates.intersections.IntersectionDelegate;
import geometry_objects.points.Point;
import utilities.math.MathUtilities;
import utilities.math.analytic_geometry.GeometryUtilities;

public class Segment extends GeometricObject
{

    //
    // This functionality may be helpful to add to your Segment class.
    //
	
    /*
     * @param thisRay -- a ray
     * @param thatRay -- a ray
     * @return Does thatRay overlay thisRay? As in, both share same origin point, but other two points
     * are not common: one extends over the other.
     */
    public static boolean overlaysAsRay(Segment left, Segment right)
    {
    	// Equal segments overlay
    	if (left.equals(right)) return true;

    	// Get point where they share an endpoint
    	Point shared = left.sharedVertex(right);
    	if (shared == null) return false;

    	// Collinearity is required
    	if (!left.isCollinearWith(right)) return false;
    	
    	Point otherL = left.other(shared);
    	Point otherR = right.other(shared);
    	
        // Rays pointing in the same direction?
        // Avoid: <--------------------- . ---------------->
        //      V------------W------------Z
                                     // middle  endpoint  endpoint
        return GeometryUtilities.between(otherL, shared, otherR) ||
        	   GeometryUtilities.between(otherR, shared, otherL);
    }
}