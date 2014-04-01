package jkind.lustre;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import jkind.lustre.visitors.ExprVisitor;

public class NodeCallExpr extends Expr {
	final public String node;
	final public List<Expr> args;

	public NodeCallExpr(Location loc, String node, List<Expr> args) {
		super(loc);
		this.node = node;
		this.args = Collections.unmodifiableList(args);
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
