package jkind.translation.compound;

import jkind.lustre.Expr;
import jkind.lustre.Type;

public class ExprType {
	public final Expr expr;
	public final Type type;

	public ExprType(Expr expr, Type type) {
		this.expr = expr;
		this.type = type;
	}
}
