package utilities;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * holds elements in different comparator classes based upon how they
 * compare to some condition.
 * 
 * <p>Bugs: none known
 * 
 * @author Josh Berger and Jake Shore
 * @date 2/13/2023
 * @param <T>
 */
public class EquivalenceClasses<T> {

	protected Comparator<T> _comparator;
	protected List<LinkedEquivalenceClass<T>> _classes;
	
	/**
	 * constructor that retrieves a comparator
	 * @param comparator
	 */
	public EquivalenceClasses(Comparator<T> comparator) {
		_comparator = comparator;
		_classes = new ArrayList<LinkedEquivalenceClass<T>>();
	}
	
	/**
	 * creates a new equivalence class with the element
	 * @param element
	 * @return whether the class was created
	 */
	private boolean createNewEquivalenceClassWithElement(T element) {
		LinkedEquivalenceClass<T> equivalence = new LinkedEquivalenceClass<T>(_comparator);
		equivalence.add(element);
		return _classes.add(equivalence);
	}

	/**
	 * Adds element to preexisting equivalence class
	 * @param element
	 * @return whether the element was added
	 */
	private boolean addToValidEquivalenceClass(T element) {
		int belongsIndex = indexOfClassBelongs(element);
		if(belongsIndex == -1) return false;
		return _classes.get(belongsIndex).add(element);
	}
	
	/**
	 * gets the index of the equivalence class the element belongs to
	 * @param element
	 * @return index of class the element belongs to or -1 if not found
	 */
	private int indexOfClassBelongs(T element) {
		for(int i=0; i<_classes.size(); i++) {
			if (_classes.get(i).belongs(element)) {
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * determines if an element is contained at a given index
	 * @param element
	 * @param index
	 * @return whether the element is at the index
	 */
	private boolean containsAt(T element, int index) {
		return _classes.get(index).contains(element);
	}
	
	/**
	 * Determines whether one of the equivalence lists contained the target element
	 * @param target
	 * @return whether the target element was contained
	 */
	public boolean contains(T target) {
		return indexOfClass(target) > -1;
	}
	
	/**
	 * adds an element to an appropriate equivalence list based on the comparator
	 * @param element
	 * @return whether the element was added
	 */
	public boolean add(T element) {
		if (element == null || _comparator == null || contains(element)) return false;
		if(addToValidEquivalenceClass(element)) return true;
		return createNewEquivalenceClassWithElement(element);
	}
	
	/**
	 * finds size of the EquivalenceClasses
	 * @return total size of all equivalence classes
	 */
	public int size() {
		int size = 0;
		for(LinkedEquivalenceClass<T> aClass : _classes) {
			size += aClass.size();
		}
		return size;
	}
	
	/**
	 * finds the number of equivalences classes contained
	 * @return the number of equivalences classes
	 */
	public int numClasses() {
		return _classes.size();
	}

	/**
	 * finds the index of the class that has element
	 * @param element
	 * @return index of class containing element or -1 if not found
	 */
	protected int indexOfClass(T element) {
		int belongsIndex = indexOfClassBelongs(element);
		if(belongsIndex == -1 || !containsAt(element, belongsIndex)) return -1;
		return belongsIndex;
	}
	
	/**
	 * Returns a string representation of the object.
	 */
	public String toString() {
		StringBuilder output = new StringBuilder();
		output.append("Classes:\n");
		for(LinkedEquivalenceClass<T> aClass : _classes) {
			output.append(aClass + "\n");
		}
		return output.toString();
	}
	
}
