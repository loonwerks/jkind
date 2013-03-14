package jkind.analysis.evaluation;

import java.math.BigDecimal;

import jkind.lustre.BinaryOp;
import jkind.lustre.UnaryOp;

public class RealValue extends Value {
	final public BigDecimal value;
	
	public RealValue(BigDecimal value) {
		if (value == null) {
			throw new IllegalArgumentException("Cannot create null real value");
		}
		this.value = value;
	}

	@Override
	public Value applyBinaryOp(BinaryOp op, Value right) {
		if (!(right instanceof RealValue)) {
			return null;
		}
		BigDecimal other = ((RealValue) right).value;

		switch (op) {
		case PLUS:
			return new RealValue(value.add(other));
		case MINUS:
			return new RealValue(value.subtract(other));
		case MULTIPLY:
			return new RealValue(value.multiply(other));
		case DIVIDE:
			return new RealValue(value.divide(other));
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
			return new RealValue(value.negate());
		default:
			return null;
		}
	}
}
