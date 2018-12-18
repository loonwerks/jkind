package jkind.lustre.values;

import java.math.BigInteger;

import jkind.lustre.BinaryOp;
import jkind.lustre.UnaryOp;
import jkind.util.Util;

/**
 * An integer signal value
 */
public class IntegerValue extends Value {
	public final BigInteger value;

	public IntegerValue(BigInteger value) {
		if (value == null) {
			throw new IllegalArgumentException("Cannot create null integer value");
		}
		this.value = value;
	}

	@Override
	public Value applyBinaryOp(BinaryOp op, Value right) {

		if (right instanceof UnknownValue) {
			return UnknownValue.UNKNOWN;
		}

		if (!(right instanceof IntegerValue)) {
			return null;
		}
		BigInteger other = ((IntegerValue) right).value;

		switch (op) {
		case PLUS:
			return new IntegerValue(value.add(other));
		case MINUS:
			return new IntegerValue(value.subtract(other));
		case MULTIPLY:
			return new IntegerValue(value.multiply(other));
		case INT_DIVIDE:
			return new IntegerValue(Util.smtDivide(value, other));
		case MODULUS:
			return new IntegerValue(value.mod(other));
		case EQUAL:
			return BooleanValue.fromBoolean(value.compareTo(other) == 0);
		case NOTEQUAL:
			return BooleanValue.fromBoolean(value.compareTo(other) != 0);
		case GREATER:
			return BooleanValue.fromBoolean(value.compareTo(other) > 0);
		case LESS:
			return BooleanValue.fromBoolean(value.compareTo(other) < 0);
		case GREATEREQUAL:
			return BooleanValue.fromBoolean(value.compareTo(other) >= 0);
		case LESSEQUAL:
			return BooleanValue.fromBoolean(value.compareTo(other) <= 0);
		default:
			return null;
		}
	}

	@Override
	public Value applyUnaryOp(UnaryOp op) {
		switch (op) {
		case NEGATIVE:
			return new IntegerValue(value.negate());
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
		if (obj instanceof IntegerValue) {
			IntegerValue other = (IntegerValue) obj;
			return value.equals(other.value);
		}
		return false;
	}
}
