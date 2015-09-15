package jkind.lustre;

import java.util.List;

import jkind.lustre.visitors.ExprVisitor;
import jkind.util.Util;

public class QuantExpr extends Expr {

	public final QuantOp op;
	public final List<VarDecl> boundVars;
	public final Expr expr;
	
	public QuantExpr(Location location, QuantOp op, List<VarDecl> boundVars, Expr expr) {
		super(location);
		this.op = op;
		this.boundVars = Util.safeList(boundVars);
		this.expr = expr;
	}
	
	public QuantExpr(QuantOp op, List<VarDecl> boundVars, Expr expr) {
		this(Location.NULL, op, boundVars, expr);
	}

	@Override
	public <T> T accept(ExprVisitor<T> visitor) {
		return visitor.visit(this);
	}

}
