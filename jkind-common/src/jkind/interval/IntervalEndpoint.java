package jkind.interval;

public abstract class IntervalEndpoint implements Comparable<IntervalEndpoint> {
	@Override
	public abstract int compareTo(IntervalEndpoint other);

	public IntervalEndpoint max(IntervalEndpoint other) {
		return this.compareTo(other) >= 0 ? this : other;
	}

	public IntervalEndpoint min(IntervalEndpoint other) {
		return this.compareTo(other) <= 0 ? this : other;
	}
}
