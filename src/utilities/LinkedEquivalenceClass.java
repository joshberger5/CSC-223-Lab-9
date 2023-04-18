package utilities;

import java.util.Comparator;

/**
 * Set of elements that are equivalent based on some rule
 *
 * <p>Bugs: none known
 *
 * @author Josh Berger and Jake Shore
 * @date 2/13/2023
 * @param <T>
 */
public class LinkedEquivalenceClass<T> {
	private T _canonical;
	private Comparator<T> _comparator;
	private LinkedList<T> _rest;
	
	/**
	 * instantiate the variables
	 * @param comparator
	 */
	public LinkedEquivalenceClass(Comparator<T> comparator) {
		_comparator = comparator;
		_canonical = null;
		_rest = new LinkedList<T>();
	}
	
	/**
	 * returns the canonical variable
	 * @return the canonical variable
	 */
	public T canonical() {
		return _canonical;
	}
	
	/**
	 * returns if there is no canonical and no rest of the list
	 * @return if it is empty
	 */
	public boolean isEmpty() {
		return (_canonical == null && _rest.isEmpty());
	}
	
	/** 
	 * gets rid of both the canonical and the rest of the list
	 */
	public void clear() {
		_canonical = null;
		_rest.clear();
	}
	
	/**
	 * gets rid of rest of the list not canonical
	 */
	public void clearNonCanonical() {
		_rest.clear();
	}
	
	/**
	 * returns the size of the rest of the list and the canonical
	 * @return the size of the rest plus the canonical
	 */
	public int size() {
		return _rest.size() + (_canonical == null ? 0 : 1);		
	}
	
	/**
	 * if the element belongs in the list and isn't in it add it to the front of the rest of the list
	 * @param element
	 * @return whether the element was added
	 */
	public boolean add(T element) {
		// you obviously don't want to add null to the set
		if (element == null) return false;
		
		// if the list is empty, then the first element added needs to be the canonical
		if (isEmpty()) {
			_canonical = element;
			return true;
		}
		
		// if the element does not belong in the set
		// or
		// it matches either the canonical or a non-canonical element
		// then don't add it (its a set, so it shouldn't have repeats)
		if (!belongs(element) || _canonical.equals(element) || _rest.contains(element)) return false;
		_rest.addToFront(element);
		return true;
	}
	
	/**
	 * check if the target is in the list 
	 * @param target
	 * @return if the target belongs and is contained
	 */
	public boolean contains(T target) {
		if (target == null || _canonical == null || !belongs(target)) return false;
		return (_canonical.equals(target) || _rest.contains(target));
	}
	
	/**
	 * check if it is equivalent to the canonical
	 * @param target
	 * @return if the target belongs
	 */
	public boolean belongs(T target) {
		if (target == null) return false;
		if (isEmpty()) return true;
		return (_comparator.compare(_canonical, target) == 0);
	}
	
	/**
	 * if the element belongs in the list remove it from the rest of the list
	 * @param target
	 * @return whether the target was removed
	 */
	public boolean remove(T target) {
		if (target == null || !belongs(target)) return false;
		return _rest.remove(target);
	}
	
	/**
	 * removes the canonical value if there is something to replace it with
	 * @return whether the canonical variable was removed
	 */
	public boolean removeCanonical() {
		if (_rest.isEmpty()) return false;
		_canonical = _rest.pop_front();
		return true;
	}
	
	/**
	 * if the element belongs in the list add it as the canonical and add the existing canonical to the list 
	 * @param element
	 * @return whether the canonical value was set
	 */
	public boolean demoteAndSetCanonical(T element) {
		// if the element is null, don't add it
		// if the list is empty, there is no canonical to demote
		// if it equals the canonical, you don't want to make a repeat
		// if it doesn't belong, then you also don't want to add it
		if (element == null || isEmpty() || _canonical.equals(element) || !belongs(element)) return false;
		
		// if it doesn't meet any of those, then remove it from the rest of the list, if its in there
		// change the canonical to the new element
		// add the old canonical to the rest of the list
		_rest.remove(element);
		T temp = _canonical;
		_canonical = element;
		add(temp);
		return true;
	}
	
	/**
	 * makes the whole list a string
	 */
	public String toString() {
		if (isEmpty()) return "";
		if (_rest.isEmpty()) return _canonical + "";
		return _canonical + " | " + _rest.toString();
	}
}
