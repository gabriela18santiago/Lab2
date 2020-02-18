package ciic4020.list;

public class ArrayListFactory<E> implements ListFactory<E> {

	private static final int DEFAULT_SIZE = 10;
	
	@Override
	public ciic4020.list.List<E> newInstance(int initialCapacity) {
		return new ArrayList<E>(initialCapacity);
	}

	@Override
	public ciic4020.list.List<E> newInstance() {
		return new ArrayList<E>(DEFAULT_SIZE);
	}

}