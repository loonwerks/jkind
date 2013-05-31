package jkind.analysis.evaluation;

import java.math.BigDecimal;
import java.math.BigInteger;

import jkind.lustre.BinaryOp;
import jkind.lustre.UnaryOp;

public class RealValue extends Value implements Comparable<RealValue> {
	final public BigInteger num;
	final public BigInteger denom; // Always positive

	public RealValue(BigInteger num, BigInteger denom) {
		if (denom.equals(BigInteger.ZERO)) {
			throw new ArithmeticException("Divide by zero");
		}

		if (denom.compareTo(BigInteger.ZERO) > 0) {
			this.num = num;
			this.denom = denom;
		} else {
			this.num = num.negate();
			this.denom = denom.negate();
		}
	}

	public RealValue(BigDecimal value) {
		BigInteger TEN = BigInteger.valueOf(10);
		this.num = value.unscaledValue();
		this.denom = TEN.pow(value.scale());
	}

	@Override
	public Value applyBinaryOp(BinaryOp op, Value right) {
		if (!(right instanceof RealValue)) {
			return null;
		}
		RealValue other = (RealValue) right;

		switch (op) {
		case PLUS:
			return new RealValue(num.multiply(other.denom).add(other.num.multiply(denom)),
					denom.multiply(other.denom));
		case MINUS:
			return new RealValue(num.multiply(other.denom).subtract(other.num.multiply(denom)),
					denom.multiply(other.denom));
		case MULTIPLY:
			return new RealValue(num.multiply(other.num), denom.multiply(other.denom));
		case DIVIDE:
			return new RealValue(num.multiply(other.denom), denom.multiply(other.num));
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
		return num.multiply(other.denom).compareTo(other.num.multiply(denom));
	}

	@Override
	public Value applyUnaryOp(UnaryOp op) {
		switch (op) {
		case NEGATIVE:
			return new RealValue(num.negate(), denom);
		default:
			return null;
		}
	}
}
