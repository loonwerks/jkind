package jkind.interval;

import jkind.lustre.BinaryOp;
import jkind.lustre.UnaryOp;
import jkind.lustre.values.Value;

public abstract class Interval extends Value {
	@Override
	public abstract Interval applyBinaryOp(BinaryOp op, Value right);

	@Override
	public abstract Interval applyUnaryOp(UnaryOp op);

	public abstract Interval join(Interval other);

	public abstract boolean isArbitrary();

	public abstract boolean isExact();

	public abstract Value getExactValue();
}
