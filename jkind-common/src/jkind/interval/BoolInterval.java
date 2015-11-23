package jkind.interval;

import jkind.lustre.BinaryOp;
import jkind.lustre.UnaryOp;
import jkind.lustre.values.BooleanValue;
import jkind.lustre.values.Value;

public class BoolInterval extends Interval {
	public static final BoolInterval TRUE = new BoolInterval();
	public static final BoolInterval FALSE = new BoolInterval();
	public static final BoolInterval ARBITRARY = new BoolInterval();

	private BoolInterval() {
	}

	public boolean isTrue() {
		return this == TRUE;
	}

	public boolean isFalse() {
		return this == FALSE;
	}

	@Override
	public boolean isArbitrary() {
		return this == ARBITRARY;
	}

	@Override
	public boolean isExact() {
		return isTrue() || isFalse();
	}

	@Override
	public Value getExactValue() {
		if (!isExact()) {
			throw new IllegalArgumentException("Value is not exact");
		}
		return isTrue() ? BooleanValue.TRUE : BooleanValue.FALSE;
	}
	
	@Override
	public BoolInterval applyBinaryOp(BinaryOp op, Value right) {
		if (!(right instanceof BoolInterval)) {
			return null;
		}
		BoolInterval other = (BoolInterval) right;

		switch (op) {
		case EQUAL:
			if (isArbitrary() || other.isArbitrary()) {
				return ARBITRARY;
			} else {
				return this == other ? TRUE : FALSE;
			}

		case NOTEQUAL:
		case XOR:
			if (isArbitrary() || other.isArbitrary()) {
				return ARBITRARY;
			} else {
				return this == other ? FALSE : TRUE;
			}

		case OR:
			if (isTrue() || other.isTrue()) {
				return TRUE;
			} else if (isFalse() && other.isFalse()) {
				return FALSE;
			} else {
				return ARBITRARY;
			}

		case AND:
			if (isTrue() && other.isTrue()) {
				return TRUE;
			} else if (isFalse() || other.isFalse()) {
				return FALSE;
			} else {
				return ARBITRARY;
			}

		case IMPLIES:
			if (isFalse() || other.isTrue()) {
				return TRUE;
			} else if (isTrue() && other.isFalse()) {
				return FALSE;
			} else {
				return ARBITRARY;
			}

		default:
			return null;
		}
	}

	@Override
	public BoolInterval applyUnaryOp(UnaryOp op) {
		switch (op) {
		case NOT:
			return negate();

		default:
			return null;
		}
	}

	public BoolInterval negate() {
		if (isArbitrary()) {
			return ARBITRARY;
		} else {
			return isTrue() ? FALSE : TRUE;
		}
	}

	@Override
	public Interval join(Interval interval) {
		BoolInterval other = (BoolInterval) interval;
		if (this == other) {
			return this;
		} else {
			return ARBITRARY;
		}
	}
	

	@Override
	public String toString() {
		if (isTrue()) {
			return "true";
		} else if (isFalse()) {
			return "false";
		} else if (isArbitrary()) {
			return "-";
		} else {
			throw new IllegalStateException();
		}
	}
}
