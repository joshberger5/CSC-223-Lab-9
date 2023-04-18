package geometry_objects.angle;

import geometry_objects.angle.comparators.AngleStructureComparator;
import utilities.eq_classes.LinkedEquivalenceClass;

/**
 * This implementation requires greater knowledge of the implementing Comparator.
 * 
 * According to our specifications for the AngleStructureComparator, we have
 * the following cases:
 *
 *    Consider Angles A and B
 *    * Integer.MAX_VALUE -- indicates that A and B are completely incomparable
                             STRUCTURALLY (have different measure, don't share sides, etc. )
 *    * 0 -- The result is indeterminate:
 *           A and B are structurally the same, but it is not clear one is structurally
 *           smaller (or larger) than another
 *    * 1 -- A > B structurally
 *    * -1 -- A < B structurally
 *    
 *    We want the 'smallest' angle structurally to be the canonical element of an
 *    equivalence class.
 * 
 * @author XXX
 */
public class AngleLinkedEquivalenceClass extends LinkedEquivalenceClass<Angle> {
	
	public AngleLinkedEquivalenceClass() {
		super(new AngleStructureComparator());
	}
	
	/**
	 * check if it is equivalent to the canonical
	 * @param otherAngle
	 * @return if the target belongs
	 */
	@Override
	public boolean belongs(Angle otherAngle) {
		if (otherAngle == null) return false;
		if (isEmpty()) return true;
		
		return (_comparator.compare(_canonical, otherAngle) != Integer.MAX_VALUE);
	}
	
	/**
	 * if the element belongs in the list and isn't in it add it to the front of the rest of the list
	 * @param angle
	 * @return whether the element was added
	 */
	@Override
	public boolean add(Angle angle) {
		// you obviously don't want to add null to the set
		if (angle == null) return false;
		
		// if the list is empty, then the first element added needs to be the canonical
		if (isEmpty()) {
			_canonical = angle;
			return true;
		}
		
		if(_comparator.compare(_canonical, angle) == -1) {
			demoteAndSetCanonical(angle);
		}
		
		// if the element does not belong in the set
		// or
		// it matches either the canonical or a non-canonical element
		// then don't add it (its a set, so it shouldn't have repeats)
		if (!belongs(angle) || _canonical.equals(angle) || _rest.contains(angle)) return false;
		_rest.addToFront(angle);
		return true;
	}
}