package jkind.lustre;

import java.util.Arrays;
import java.util.List;

import jkind.Assert;
import jkind.lustre.visitors.ExprVisitor;
import jkind.util.Util;

public class FunctionCallExpr extends Expr {
	final public String name;
	final public List<Expr> args;

	public FunctionCallExpr(Location loc, String name, List<Expr> args) {
		super(loc);
		Assert.isNotNull(name);
		this.name = name;
		this.args = Util.safeList(args);
	}
	
	public FunctionCallExpr(String name, List<Expr> args) {
		this(Location.NULL, name, args);
	}
	
	public FunctionCallExpr(String name, Expr... args) {
		this(Location.NULL, name, Arrays.asList(args));
	}

	@Override
	public <T> T accept(ExprVisitor<T> visitor) {
		return visitor.visit(this);
	}
}
