package jkind.lustre;

import java.util.Arrays;
import java.util.List;

import jkind.Assert;
import jkind.lustre.visitors.ExprVisitor;
import jkind.util.Util;

public class NodeCallExpr extends Expr {
	public final String node;
	public final List<Expr> args;

	public NodeCallExpr(Location loc, String node, List<Expr> args) {
		super(loc);
		Assert.isNotNull(node);
		this.node = node;
		this.args = Util.safeList(args);
	}

	public NodeCallExpr(String node, List<Expr> args) {
		this(Location.NULL, node, args);
	}

	public NodeCallExpr(String node, Expr... args) {
		this(Location.NULL, node, Arrays.asList(args));
	}

	@Override
	public <T> T accept(ExprVisitor<T> visitor) {
		return visitor.visit(this);
	}
}
