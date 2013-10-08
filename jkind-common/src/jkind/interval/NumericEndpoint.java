package jkind.interval;

public abstract class NumericEndpoint extends IntervalEndpoint {
    public abstract boolean isFinite();
    public abstract int signum();
    
    public abstract NumericEndpoint add(NumericEndpoint other);
    public abstract NumericEndpoint multiply(NumericEndpoint other);
    public abstract NumericEndpoint divide(NumericEndpoint other);
    public abstract NumericEndpoint negate();

	public NumericEndpoint min(NumericEndpoint other) {
		return (NumericEndpoint) min((IntervalEndpoint) other);
	}

	public NumericEndpoint max(NumericEndpoint other) {
		return (NumericEndpoint) max((IntervalEndpoint) other);
	}
	
	public abstract double toDouble();
}
