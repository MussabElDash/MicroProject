package utilities;

public class Pair<T, K> {
	public T first;
	public K second;

	public Pair(T first, K second) {
		this.first = first;
		this.second = second;
	}

	@Override
	public boolean equals(Object obj) {
		boolean sup = super.equals(obj);
		if (sup)
			return sup;
		if (obj.getClass() != getClass())
			return false;
		Pair<?, ?> temp = (Pair<?, ?>) obj;
		return temp.first == first && temp.second == second;
	}
}
