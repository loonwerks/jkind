package jkind.lustre;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import jkind.lustre.visitors.ExprVisitor;

public class CallExpr extends Expr {
	final public String name;
	final public List<Expr> args;

	public CallExpr(Location loc, String name, List<Expr> args) {
		super(loc);
		this.name = name;
		this.args = Collections.unmodifiableList(args);
	}
	
	public CallExpr(String name, List<Expr> args) {
		this(Location.NULL, name, args);
	}
	
	public CallExpr(String name, Expr... args) {
		this(Location.NULL, name, Arrays.asList(args));
	}

	@Override
	public <T> T accept(ExprVisitor<T> visitor) {
		return visitor.visit(this);
	}
}
