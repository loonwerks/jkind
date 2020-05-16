package jkind.lustre;

import java.util.Arrays;
import java.util.List;

import jkind.Assert;
import jkind.lustre.visitors.ExprVisitor;
import jkind.util.Util;

public class CondactExpr extends Expr {
	public final Expr clock;
	public final NodeCallExpr call;
	public final List<Expr> args;

	public CondactExpr(Location loc, Expr clock, NodeCallExpr call, List<Expr> args) {
		super(loc);
		Assert.isNotNull(clock);
		Assert.isNotNull(call);
		this.clock = clock;
		this.call = call;
		this.args = Util.safeList(args);
	}

	public CondactExpr(Expr clock, NodeCallExpr call, List<Expr> args) {
		this(Location.NULL, clock, call, args);
	}

	public CondactExpr(Expr clock, NodeCallExpr call, Expr... args) {
		this(Location.NULL, clock, call, Arrays.asList(args));
	}

	@Override
	public <T> T accept(ExprVisitor<T> visitor) {
		return visitor.visit(this);
	}
}
