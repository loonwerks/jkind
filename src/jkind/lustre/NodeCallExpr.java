package jkind.lustre;

import java.util.Collections;
import java.util.List;

public class NodeCallExpr extends Expr {
	final public String node;
	final public List<Expr> args;

	public NodeCallExpr(Location loc, String node, List<Expr> args) {
		super(loc);
		this.node = node;
		this.args = Collections.unmodifiableList(args);
	}

	@Override
	public <T> T accept(ExprVisitor<T> visitor) {
		return visitor.visit(this);
	}
}
