package ciic4020.list;

import ciic4020.linkedlist.List;
import ciic4020.linkedlist.ListFactory;

public class LinkedListFactory<E> implements ListFactory<E> {

	@Override
	public List<E> newInstance(int initialCapacity) {
		// We don't pre-allocate nodes, so initialCapacity isn't actually used
		return (List<E>) new LinkedList<E>();
	}

	@Override
	public List<E> newInstance() {
		return (List<E>) new LinkedList<E>();
	}

}