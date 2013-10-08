package jkind.interval;

public class IdIndexPair {
	public String id;
	public int i;

	public IdIndexPair(String id, int i) {
		this.id = id;
		this.i = i;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof IdIndexPair) {
			IdIndexPair other = (IdIndexPair) o;
			return i == other.i && id.equals(other.id);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return id.hashCode() + i;
	}
}
