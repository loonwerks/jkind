package jkind.lustre.values;

import jkind.lustre.BinaryOp;
import jkind.lustre.UnaryOp;
import jkind.util.BigFraction;

/**
 * A real signal value
 */
public class RealValue extends Value implements Comparable<RealValue> {
	public final BigFraction value;
	
	public RealValue(BigFraction value) {
		this.value = value;
	}

	@Override
	public Value applyBinaryOp(BinaryOp op, Value right) {
		if (!(right instanceof RealValue)) {
			return null;
		}
		RealValue other = (RealValue) right;

		switch (op) {
		case PLUS:
			return new RealValue(value.add(other.value));
		case MINUS:
			return new RealValue(value.subtract(other.value));
		case MULTIPLY:
			return new RealValue(value.multiply(other.value));
		case DIVIDE:
			return new RealValue(value.divide(other.value));
		case EQUAL:
			return BooleanValue.fromBoolean(compareTo(other) == 0);
		case NOTEQUAL:
			return BooleanValue.fromBoolean(compareTo(other) != 0);
		case GREATER:
			return BooleanValue.fromBoolean(compareTo(other) > 0);
		case LESS:
			return BooleanValue.fromBoolean(compareTo(other) < 0);
		case GREATEREQUAL:
			return BooleanValue.fromBoolean(compareTo(other) >= 0);
		case LESSEQUAL:
			return BooleanValue.fromBoolean(compareTo(other) <= 0);
		default:
			return null;
		}
	}

	@Override
	public int compareTo(RealValue other) {
		return value.compareTo(other.value);
	}

	@Override
	public Value applyUnaryOp(UnaryOp op) {
		switch (op) {
		case NEGATIVE:
			return new RealValue(value.negate());
		default:
			return null;
		}
	}

	@Override
	public String toString() {
		return value.toString();
	}
	
	@Override
	public int hashCode() {
		return value.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof RealValue) {
			RealValue other = (RealValue) obj;
			return value.equals(other.value);
		}
		return false;
	}
}
