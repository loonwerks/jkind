package jkind.interval;

import jkind.util.BigFraction;

public class RealEndpoint extends NumericEndpoint {
	private final BigFraction value;

	public static final RealEndpoint ZERO = new RealEndpoint(BigFraction.ZERO);
	public static final RealEndpoint ONE = new RealEndpoint(BigFraction.ONE);
	public static final RealEndpoint POS_INFINITY = new RealEndpoint();
	public static final RealEndpoint NEG_INFINITY = new RealEndpoint();

	public RealEndpoint(BigFraction value) {
		this.value = value;
	}

	/*
	 * Private constructor used only to construct special infinity values
	 */
	private RealEndpoint() {
		this.value = null;
	}

	public BigFraction getValue() {
		return value;
	}

	@Override
	public int compareTo(IntervalEndpoint other) {
		return compareTo((RealEndpoint) other);
	}

	private int compareTo(RealEndpoint other) {
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
	public RealEndpoint add(NumericEndpoint o) {
		RealEndpoint other = (RealEndpoint) o;

		if ((this == POS_INFINITY && other == NEG_INFINITY)
				|| (this == NEG_INFINITY && other == POS_INFINITY)) {
			throw new ArithmeticException("Infinity + negative infinity is undefined");
		}

		if (!isFinite()) {
			return this;
		} else if (!other.isFinite()) {
			return other;
		} else {
			return new RealEndpoint(value.add(other.value));
		}
	}

	@Override
	public RealEndpoint multiply(NumericEndpoint o) {
		RealEndpoint other = (RealEndpoint) o;

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

		return new RealEndpoint(value.multiply(other.value));
	}

	@Override
	public RealEndpoint divide(NumericEndpoint o) {
		RealEndpoint other = (RealEndpoint) o;

		if (!other.isFinite()) {
			throw new ArithmeticException("Division by non-constant");
		}

		if (!isFinite()) {
			return signum() * other.signum() > 0 ? POS_INFINITY : NEG_INFINITY;
		} else {
			return new RealEndpoint(value.divide(other.value));
		}
	}

	@Override
	public RealEndpoint negate() {
		if (this == POS_INFINITY) {
			return NEG_INFINITY;
		} else if (this == NEG_INFINITY) {
			return POS_INFINITY;
		} else {
			return new RealEndpoint(value.negate());
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
		if (other instanceof RealEndpoint) {
			return this.compareTo((RealEndpoint) other) == 0;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return value != null ? value.hashCode() : 0;
	}
}
