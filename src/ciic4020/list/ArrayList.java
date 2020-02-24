package ciic4020.list;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayList<E> implements List<E> {

	// private fields
	private E elements[];
	
	private int currentSize;




	private class ListIterator implements Iterator<E> {
		private int currentPosition;
		
		public ListIterator() {
			this.currentPosition = 0;
		}

		@Override
		public boolean hasNext() {
			return this.currentPosition < size();
		}

		@Override
		public E next() {
			if (this.hasNext()) {
				return (E) elements[this.currentPosition++];
			}
			else
				throw new NoSuchElementException();
		}
	}

	
	@SuppressWarnings("unchecked")
	public ArrayList(int initialCapacity) {
		if (initialCapacity < 1)
			throw new IllegalArgumentException("Capacity must be at least 1.");
		this.currentSize = 0;
		this.elements = (E[]) new Object[initialCapacity];
	}

	@Override
	public void add(E obj) {
		if (obj == null)
			throw new IllegalArgumentException("Object cannot be null.");
		else {
			if (this.size() == this.elements.length)
				reAllocate();
			this.elements[this.currentSize++] = obj;
		}		
	}
	
	@SuppressWarnings("unchecked")
	private void reAllocate() {
		// create a new array with twice the size
		E newElements[] = (E[]) new Object[2*this.elements.length];
		for (int i = 0; i < this.size(); i++)
			newElements[i] = this.elements[i];
		// replace old elements with newElements
		this.clear();
		this.elements = newElements;
	}

	@Override
	public void add(int index, E obj) {
		if (obj == null)
			throw new IllegalArgumentException("Object cannot be null.");
		if (index == this.currentSize)
			this.add(obj); // Use other method to "append"
		else {
			if (index >= 0 && index < this.currentSize) {
				if (this.currentSize == this.elements.length)
					reAllocate();
				// move everybody one spot to the back
				for (int i = this.currentSize; i > index; i--)
					this.elements[i] = this.elements[i - 1];
				// add element at position index
				this.elements[index] = obj;
				this.currentSize++;
			}
			else
				throw new ArrayIndexOutOfBoundsException();
		}
	}

	@Override
	public boolean remove(E obj) {
		if (obj == null)
			throw new IllegalArgumentException("Object cannot be null.");
		// first find obj in the array
		int position = this.firstIndex(obj);
		if (position >= 0) // found it
			return this.remove(position);
		else
			return false;
	}

	@Override
	public boolean remove(int index) {
		if (index >= 0 && index < this.currentSize) {
			// move everybody one spot to the front
			for (int i = index; i < this.currentSize - 1; i++)
				this.elements[i] = this.elements[i + 1];
			this.elements[--this.currentSize] = null;
			return true;
		}
		else
			return false;
	}

	@Override
	public int removeAll(E obj) {
		int counter = 0;
		while (this.remove(obj))
			counter++;
		return counter;
	}

	@Override
	public E get(int index) {
		if (index >= 0 && index < this.size())
			return this.elements[index];
		else
			throw new ArrayIndexOutOfBoundsException();
	}

	@Override
	public E set(int index, E obj) {
		if (obj == null)
			throw new IllegalArgumentException("Object cannot be null.");
		if (index >= 0 && index < this.size()) {
			E temp = this.elements[index];
			this.elements[index] = obj;
			return temp;
		}
		else
			throw new ArrayIndexOutOfBoundsException();
	}

	@Override
	public E first() {
		if (this.isEmpty())
			return null;
		else
			return this.elements[0];
	}

	@Override
	public E last() {
		if (this.isEmpty())
			return null;
		else
			return this.elements[this.currentSize - 1];
	}

	@Override
	public int firstIndex(E obj) {
		for (int i = 0; i < this.size(); i++)
			if (this.elements[i].equals(obj))
				return i;
		return -1;
	}

	@Override
	public int lastIndex(E obj) {
		for (int i = this.size() - 1; i >= 0; i--)
			if (this.elements[i].equals(obj))
				return i;
		return -1;
	}

	@Override
	public int size() {
		return this.currentSize;
	}

	@Override
	public boolean isEmpty() {
		return this.size() == 0;
	}

	@Override
	public boolean contains(E obj) {
		return this.firstIndex(obj) >= 0;
	}

	@Override
	public void clear() {
		for (int i = 0; i < this.currentSize; i++)
			this.elements[i] = null;
		this.currentSize = 0;
	}

	//Exercise 1
	public static int totalCount(String s, Object[] lists) {
		int count = 0;
		for (int i = 0; i < lists.length; i++) {
			List<String> temp = new ArrayList<>(lists.length);
			temp = ((List<String>) lists[i]);
			for (int j = 0; j < temp.size(); j++) {
				if (temp.get(j).equals(s)) {
					count++;
				}
			}
		}
		return count;
	}

	//Exercise 2
	@Override
	public int replaceAll(E e, E f) {
		int count = 0;
		//It verifies if the list is empty or if it contains the element "e"
		if(this.isEmpty() || !this.contains(e)){return count;}
		else {
			for (int i = 0; i < this.size(); i++) {
				if (this.get(i).equals(e)) {
					this.set(i, f);
					count++;
				}
			}
			return count;
		}
	}

	//Exercise 3
	@Override
	public List<E> reverse() {
		List<E> newList = new ArrayList<E>(this.size());
		if(this.isEmpty()) {return newList;}
		else {
			for (int i = this.size() - 1; i >= 0; i--) {
				newList.add(this.get(i));
			}
			return newList;
		}
	}

	@Override
	public Iterator<E> iterator() {
		return new ListIterator();
	}
}