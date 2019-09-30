package jkind.lustre;

import jkind.Assert;
import jkind.lustre.visitors.ExprVisitor;
import jkind.util.Util;

import java.util.Arrays;
import java.util.List;

public class RepairExpr extends Expr {
	public final Expr origExpr;
	public final NodeCallExpr repairNode;

	public RepairExpr(Location loc, Expr origExpr, NodeCallExpr repairNode) {
		super(loc);
		Assert.isNotNull(origExpr);
		this.origExpr = origExpr;
		Assert.isNotNull(repairNode);
		this.repairNode = repairNode;
	}

	public RepairExpr(Expr origExpr, NodeCallExpr repairNode) {
		this(Location.NULL, origExpr, repairNode);
	}


	@Override
	public <T> T accept(ExprVisitor<T> visitor) {
		return visitor.visit(this);
	}
}
