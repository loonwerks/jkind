package jkind.slicing;

import java.util.HashSet;
import java.util.Set;

import jkind.lustre.BinaryExpr;
import jkind.lustre.BoolExpr;
import jkind.lustre.Expr;
import jkind.lustre.ExprVisitor;
import jkind.lustre.IdExpr;
import jkind.lustre.IfThenElseExpr;
import jkind.lustre.IntExpr;
import jkind.lustre.NodeCallExpr;
import jkind.lustre.RealExpr;
import jkind.lustre.UnaryExpr;

public class IdExtractorVisitor implements ExprVisitor<Void> {
	public static Set<String> getIds(Expr expr) {
		IdExtractorVisitor visitor = new IdExtractorVisitor();
		expr.accept(visitor);
		return visitor.set;
	}
	
	private Set<String> set = new HashSet<String>();
	
	@Override
	public Void visit(BinaryExpr e) {
		e.left.accept(this);
		e.right.accept(this);
		return null;
	}

	@Override
	public Void visit(BoolExpr e) {
		return null;
	}

	@Override
	public Void visit(IdExpr e) {
		set.add(e.id);
		return null;
	}

	@Override
	public Void visit(IfThenElseExpr e) {
		e.cond.accept(this);
		e.thenExpr.accept(this);
		e.elseExpr.accept(this);
		return null;
	}

	@Override
	public Void visit(IntExpr e) {
		return null;
	}

	@Override
	public Void visit(NodeCallExpr e) {
		for (Expr arg : e.args) {
			arg.accept(this);
		}
		return null;
	}
	
	@Override
	public Void visit(RealExpr e) {
		return null;
	}

	@Override
	public Void visit(UnaryExpr e) {
		e.expr.accept(this);
		return null;
	}
}
