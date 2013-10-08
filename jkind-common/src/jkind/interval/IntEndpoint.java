package jkind.interval;

import java.math.BigInteger;

public class IntEndpoint extends NumericEndpoint {
	private final BigInteger value;

	public static final IntEndpoint ZERO = new IntEndpoint(BigInteger.ZERO);
	public static final IntEndpoint ONE = new IntEndpoint(BigInteger.ONE);
	public static final IntEndpoint POS_INFINITY = new IntEndpoint();
	public static final IntEndpoint NEG_INFINITY = new IntEndpoint();

	public IntEndpoint(BigInteger value) {
		this.value = value;
	}

	/*
	 * Private constructor used only to construct special infinity values
	 */
	private IntEndpoint() {
		this.value = null;
	}

	public BigInteger getValue() {
		return value;
	}

	@Override
	public int compareTo(IntervalEndpoint other) {
		return compareTo((IntEndpoint) other);
	}

	private int compareTo(IntEndpoint other) {
		if (this == POS_INFINITY) {
			return other == POS_INFINITY ? 0 : 1;
		} else if (other == POS_INFINITY) {
			return -1;
		}

		if (this == NEG_INFINITY) {
			return other == NEG_INFINITY ? 0 : -1;
		} else if (other == NEG_INFINITY) {
			return 1;
		}

		return value.compareTo(other.value);
	}

	@Override
	public IntEndpoint add(NumericEndpoint o) {
		IntEndpoint other = (IntEndpoint) o;

		if ((this == POS_INFINITY && other == NEG_INFINITY)
				|| (this == NEG_INFINITY && other == POS_INFINITY)) {
			throw new ArithmeticException("Infinity + negative infinity is undefined");
		}

		if (!isFinite()) {
			return this;
		} else if (!other.isFinite()) {
			return other;
		} else {
			return new IntEndpoint(value.add(other.value));
		}
	}

	@Override
	public IntEndpoint multiply(NumericEndpoint o) {
		IntEndpoint other = (IntEndpoint) o;

		/**
		 * Note that 0 * infinity is 0 since our infinity represents an
		 * arbitrarily large finite value and not the traditional mathematical
		 * notion of infinity
		 */
		if (signum() == 0 || other.signum() == 0) {
			return ZERO;
		}

		if (!isFinite() || !other.isFinite()) {
			return signum() * other.signum() > 0 ? POS_INFINITY : NEG_INFINITY;
		}

		return new IntEndpoint(value.multiply(other.value));
	}

	@Override
	public IntEndpoint divide(NumericEndpoint o) {
		IntEndpoint other = (IntEndpoint) o;

		if (!other.isFinite()) {
			throw new ArithmeticException("Division by non-constant");
		}
		
		if (!isFinite()) {
			return signum() * other.signum() > 0 ? POS_INFINITY : NEG_INFINITY;
		} else {
			return new IntEndpoint(value.divide(other.value));
		}
	}

	@Override
	public IntEndpoint negate() {
		if (this == POS_INFINITY) {
			return NEG_INFINITY;
		} else if (this == NEG_INFINITY) {
			return POS_INFINITY;
		} else {
			return new IntEndpoint(value.negate());
		}
	}

	@Override
	public boolean isFinite() {
		return this != POS_INFINITY && this != NEG_INFINITY;
	}

	@Override
	public int signum() {
		if (this == POS_INFINITY) {
			return 1;
		} else if (this == NEG_INFINITY) {
			return -1;
		} else {
			return value.signum();
		}
	}

	@Override
	public String toString() {
		if (this == POS_INFINITY) {
			return "inf";
		} else if (this == NEG_INFINITY) {
			return "-inf";
		} else {
			return value.toString();
		}
	}

	@Override
	public boolean equals(Object other) {
		if (other instanceof IntEndpoint) {
			return this.compareTo((IntEndpoint) other) == 0;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return value != null ? value.hashCode() : 0;
	}
}
