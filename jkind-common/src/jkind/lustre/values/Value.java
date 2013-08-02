package jkind.lustre.values;

import jkind.lustre.BinaryOp;
import jkind.lustre.UnaryOp;

/**
 * A signal value
 * @see BooleanValue
 * @see IntegerValue
 * @see RealValue
 */
public abstract class Value {
	public abstract Value applyBinaryOp(BinaryOp op, Value right);
	public abstract Value applyUnaryOp(UnaryOp op);
}
