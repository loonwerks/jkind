package jkind.lustre;

import java.util.Arrays;
import java.util.List;

import jkind.Assert;
import jkind.lustre.visitors.ExprVisitor;
import jkind.util.Util;

public class FunctionCallExpr extends Expr {
	public final String function;
	public final List<Expr> args;

	public FunctionCallExpr(Location loc, String function, List<Expr> args) {
		super(loc);
		Assert.isNotNull(function);
		this.function = function;
		this.args = Util.safeList(args);
	}
	
	public FunctionCallExpr(String node, List<Expr> args) {
		this(Location.NULL, node, args);
	}
	
	public FunctionCallExpr(String node, Expr... args) {
		this(Location.NULL, node, Arrays.asList(args));
	}

	@Override
	public <T> T accept(ExprVisitor<T> visitor) {
		return visitor.visit(this);
	}
}