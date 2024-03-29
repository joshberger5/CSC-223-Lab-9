package geometry_objects.angle;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import geometry_objects.angle.comparators.AngleStructureComparator;
import utilities.eq_classes.EquivalenceClasses;
import utilities.eq_classes.LinkedEquivalenceClass;

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
 * Equivalence classes structure we want:
 * 
 *   canonical = BAE
 *   rest = BAF, CAE, DAE, CAF, DAF
 */
public class AngleEquivalenceClasses extends EquivalenceClasses<Angle> {
	
	public AngleEquivalenceClasses() {
		super(new AngleStructureComparator());
	}
	
	/**                                                                                  
	 * creates a new equivalence class with the angle
	 * @param angle
	 * @return whether the class was created
	 */
	@Override
    protected boolean createNewEquivalenceClassWithElement(Angle angle) {
		AngleLinkedEquivalenceClass equivalence = new AngleLinkedEquivalenceClass();
    	equivalence.add(angle);
    	
    	return _classes.add(equivalence);
    }
}