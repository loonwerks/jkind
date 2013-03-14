package jkind.analysis.evaluation;

import jkind.lustre.BinaryOp;
import jkind.lustre.UnaryOp;

public abstract class Value {
	public abstract Value applyBinaryOp(BinaryOp op, Value right);
	public abstract Value applyUnaryOp(UnaryOp op);
}
