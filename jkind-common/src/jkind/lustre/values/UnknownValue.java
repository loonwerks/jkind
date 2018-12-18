package jkind.lustre.values;

import jkind.lustre.BinaryOp;
import jkind.lustre.UnaryOp;
import jkind.lustre.values.BooleanValue;
import jkind.lustre.values.Value;

public class UnknownValue extends Value {

	public static final Value UNKNOWN = new UnknownValue();

	@Override
	public Value applyBinaryOp(BinaryOp op, Value right) {
		switch (op) {
		case PLUS:
		case MINUS:
		case MULTIPLY:
		case DIVIDE:
		case INT_DIVIDE:
		case MODULUS:
		case EQUAL:
		case NOTEQUAL:
		case GREATER:
		case LESS:
		case GREATEREQUAL:
		case LESSEQUAL:
		case XOR:
		case IMPLIES:
			return UNKNOWN;

		case OR:
			if (right instanceof BooleanValue) {
				BooleanValue v = (BooleanValue) right;
				if (v.value) {
					return BooleanValue.TRUE;
				}
			}
			return UNKNOWN;

		case AND:
			if (right instanceof BooleanValue) {
				BooleanValue v = (BooleanValue) right;
				if (!v.value) {
					return BooleanValue.FALSE;
				}
			}
			return UNKNOWN;

		case ARROW:
		default:
			return null;
		}
	}

	@Override
	public Value applyUnaryOp(UnaryOp op) {
		switch (op) {
		case NEGATIVE:
		case NOT:
			return UNKNOWN;

		default:
			return null;
		}

	}

	@Override
	public String toString() {
		return "?";
	}
}
