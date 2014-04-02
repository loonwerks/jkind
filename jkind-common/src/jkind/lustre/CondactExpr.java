package jkind.lustre;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import jkind.lustre.visitors.ExprVisitor;

public class CondactExpr extends Expr {
	final public Expr clock;
	final public CallExpr call;
	final public List<Expr> args;

	public CondactExpr(Location loc, Expr clock, CallExpr call, List<Expr> args) {
		super(loc);
		this.clock = clock;
		this.call = call;
		this.args = Collections.unmodifiableList(args);
	}
	
	public CondactExpr(Expr clock, CallExpr call, List<Expr> args) {
		this(Location.NULL, clock, call, args);
	}
	
	public CondactExpr(Expr clock, CallExpr call, Expr... args) {
		this(Location.NULL, clock, call, Arrays.asList(args));
	}

	@Override
	public <T> T accept(ExprVisitor<T> visitor) {
		return visitor.visit(this);
	}
}
