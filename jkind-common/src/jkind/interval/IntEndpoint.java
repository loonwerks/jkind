package jkind.interval;

import java.math.BigInteger;

import jkind.util.BigFraction;
import jkind.util.Util;

public class IntEndpoint extends NumericEndpoint {
	private final BigInteger value;

	public static final IntEndpoint ZERO = new IntEndpoint(BigInteger.ZERO);
	public static final IntEndpoint ONE = new IntEndpoint(BigInteger.ONE);
	public static final IntEndpoint POSITIVE_INFINITY = new IntEndpoint();
	public static final IntEndpoint NEGATIVE_INFINITY = new IntEndpoint();

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
		if (this == POSITIVE_INFINITY) {
			return other == POSITIVE_INFINITY ? 0 : 1;
		} else if (other == POSITIVE_INFINITY) {
			return -1;
		}

		if (this == NEGATIVE_INFINITY) {
			return other == NEGATIVE_INFINITY ? 0 : -1;
		} else if (other == NEGATIVE_INFINITY) {
			return 1;
		}

		return value.compareTo(other.value);
	}

	@Override
	public IntEndpoint add(NumericEndpoint o) {
		IntEndpoint other = (IntEndpoint) o;

		if ((this == POSITIVE_INFINITY && other == NEGATIVE_INFINITY)
				|| (this == NEGATIVE_INFINITY && other == POSITIVE_INFINITY)) {
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
			return signum() * other.signum() > 0 ? POSITIVE_INFINITY : NEGATIVE_INFINITY;
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
			return signum() * other.signum() > 0 ? POSITIVE_INFINITY : NEGATIVE_INFINITY;
		} else {
			return new IntEndpoint(Util.smtDivide(value, other.value));
		}
	}

	public IntEndpoint modulus(IntEndpoint other) {
		if (!isFinite() || !other.isFinite()) {
			throw new ArithmeticException("Modulus with infinite value");
		}
		
		return new IntEndpoint(value.mod(other.value));
	}

	@Override
	public IntEndpoint negate() {
		if (this == POSITIVE_INFINITY) {
			return NEGATIVE_INFINITY;
		} else if (this == NEGATIVE_INFINITY) {
			return POSITIVE_INFINITY;
		} else {
			return new IntEndpoint(value.negate());
		}
	}

	@Override
	public boolean isFinite() {
		return this != POSITIVE_INFINITY && this != NEGATIVE_INFINITY;
	}

	@Override
	public int signum() {
		if (this == POSITIVE_INFINITY) {
			return 1;
		} else if (this == NEGATIVE_INFINITY) {
			return -1;
		} else {
			return value.signum();
		}
	}

	@Override
	public double toDouble() {
		if (this == NEGATIVE_INFINITY) {
			return Double.NEGATIVE_INFINITY;
		} else if (this == POSITIVE_INFINITY) {
			return Double.POSITIVE_INFINITY;
		} else {
			return value.doubleValue();
		}
	}

	public RealEndpoint real() {
		if (this == POSITIVE_INFINITY) {
			return RealEndpoint.POSITIVE_INFINITY;
		} else if (this == NEGATIVE_INFINITY) {
			return RealEndpoint.NEGATIVE_INFINITY;
		} else {
			return new RealEndpoint(new BigFraction(value));
		}
	}

	@Override
	public String toString() {
		if (this == POSITIVE_INFINITY) {
			return "inf";
		} else if (this == NEGATIVE_INFINITY) {
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
