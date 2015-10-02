package jkind.interval;

import jkind.JKindException;
import jkind.lustre.BinaryOp;
import jkind.lustre.UnaryOp;
import jkind.lustre.values.IntegerValue;
import jkind.lustre.values.RealValue;
import jkind.lustre.values.Value;

public class NumericInterval extends Interval {
	private final NumericEndpoint low;
	private final NumericEndpoint high;

	public NumericInterval(NumericEndpoint low, NumericEndpoint high) {
		this.low = low;
		this.high = high;
	}

	public NumericEndpoint getLow() {
		return low;
	}

	public NumericEndpoint getHigh() {
		return high;
	}

	@Override
	public boolean isArbitrary() {
		return !low.isFinite() && !high.isFinite();
	}

	@Override
	public boolean isExact() {
		return low.equals(high);
	}

	@Override
	public Value getExactValue() {
		if (!isExact()) {
			throw new IllegalArgumentException("Value is not exact");
		}

		if (low instanceof IntEndpoint) {
			IntEndpoint ie = (IntEndpoint) low;
			return new IntegerValue(ie.getValue());
		} else if (low instanceof RealEndpoint) {
			RealEndpoint re = (RealEndpoint) low;
			return new RealValue(re.getValue());
		} else {
			throw new IllegalArgumentException("Unknown endpoint type");
		}
	}

	@Override
	public String toString() {
		if (low.equals(high)) {
			return low.toString();
		} else {
			return "[" + low + ", " + high + "]";
		}
	}

	@Override
	public Interval applyBinaryOp(BinaryOp op, Value right) {
		if (!(right instanceof NumericInterval)) {
			return null;
		}
		NumericInterval other = (NumericInterval) right;

		switch (op) {
		case PLUS:
			return plus(other);
		case MINUS:
			return plus(other.negate());
		case MULTIPLY:
			return multiply(other);
		case DIVIDE:
		case INT_DIVIDE:
			return divide(other);
		case MODULUS:
			return modulus(other);
		case EQUAL:
			return equal(other);
		case NOTEQUAL:
			return equal(other).negate();
		case GREATER:
			return greater(other);
		case LESS:
			return less(other);
		case GREATEREQUAL:
			return less(other).negate();
		case LESSEQUAL:
			return greater(other).negate();
		default:
			return null;
		}
	}

	private Interval plus(NumericInterval other) {
		return new NumericInterval(low.add(other.low), high.add(other.high));
	}

	private NumericInterval multiply(NumericInterval other) {
		NumericEndpoint val0 = low.multiply(other.low);
		NumericEndpoint val1 = low.multiply(other.high);
		NumericEndpoint val2 = high.multiply(other.low);
		NumericEndpoint val3 = high.multiply(other.high);

		return new NumericInterval(min(val0, val1, val2, val3), max(val0, val1, val2, val3));
	}

	private NumericInterval divide(NumericInterval other) {
		NumericEndpoint val0 = low.divide(other.low);
		NumericEndpoint val1 = low.divide(other.high);
		NumericEndpoint val2 = high.divide(other.low);
		NumericEndpoint val3 = high.divide(other.high);

		return new NumericInterval(min(val0, val1, val2, val3), max(val0, val1, val2, val3));
	}

	private NumericInterval modulus(NumericInterval other) {
		if (!other.low.equals(other.high)) {
			throw new JKindException("Non-constant modulus in interval simulation");
		}
		IntEndpoint b = (IntEndpoint) other.low;

		if (isExact()) {
			IntEndpoint a = (IntEndpoint) low;
			IntEndpoint v = a.modulus(b);
			return new NumericInterval(v, v);
		} else {
			return new NumericInterval(IntEndpoint.ZERO, b.add(IntEndpoint.ONE.negate()));
		}
	}

	private NumericEndpoint min(NumericEndpoint val, NumericEndpoint... others) {
		NumericEndpoint result = val;
		for (NumericEndpoint other : others) {
			result = result.min(other);
		}
		return result;
	}

	private NumericEndpoint max(NumericEndpoint val, NumericEndpoint... others) {
		NumericEndpoint result = val;
		for (NumericEndpoint other : others) {
			result = result.max(other);
		}
		return result;
	}

	private BoolInterval equal(NumericInterval other) {
		if (isExact() && other.isExact() && low.equals(other.low)) {
			return BoolInterval.TRUE;
		} else if (disjoint(other)) {
			return BoolInterval.FALSE;
		} else {
			return BoolInterval.ARBITRARY;
		}
	}

	private BoolInterval greater(NumericInterval other) {
		if (low.compareTo(other.high) > 0) {
			return BoolInterval.TRUE;
		} else if (high.compareTo(other.low) <= 0) {
			return BoolInterval.FALSE;
		} else {
			return BoolInterval.ARBITRARY;
		}
	}

	private BoolInterval less(NumericInterval other) {
		if (high.compareTo(other.low) < 0) {
			return BoolInterval.TRUE;
		} else if (low.compareTo(other.high) >= 0) {
			return BoolInterval.FALSE;
		} else {
			return BoolInterval.ARBITRARY;
		}
	}

	private boolean disjoint(NumericInterval other) {
		return high.compareTo(other.low) < 0 || low.compareTo(other.high) > 0;
	}

	@Override
	public NumericInterval applyUnaryOp(UnaryOp op) {
		switch (op) {
		case NEGATIVE:
			return negate();

		default:
			return null;
		}
	}

	private NumericInterval negate() {
		return new NumericInterval(high.negate(), low.negate());
	}

	@Override
	public Interval join(Interval interval) {
		NumericInterval other = (NumericInterval) interval;
		return new NumericInterval(low.min(other.low), high.max(other.high));
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof NumericInterval) {
			NumericInterval other = (NumericInterval) o;
			return (high.equals(other.high) && low.equals(other.low));
		}
		return false;
	}

	@Override
	public int hashCode() {
		return low.hashCode() + high.hashCode();
	}
}
