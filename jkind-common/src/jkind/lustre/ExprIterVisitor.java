package jkind.lustre;

import java.util.Collection;

public class ExprIterVisitor implements ExprVisitor<Void> {
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
	public Void visit(CondactExpr e) {
		e.clock.accept(this);
		e.call.accept(this);
		visitAll(e.args);
		return null;
	}

	@Override
	public Void visit(IdExpr e) {
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
		visitAll(e.args);
		return null;
	}

	@Override
	public Void visit(ProjectionExpr e) {
		e.expr.accept(this);
		return null;
	}

	@Override
	public Void visit(RealExpr e) {
		return null;
	}

	@Override
	public Void visit(RecordExpr e) {
		visitAll(e.fields.values());
		return null;
	}
	
	@Override
	public Void visit(UnaryExpr e) {
		e.expr.accept(this);
		return null;
	}
	
	protected Void visitAll(Collection<Expr> list) {
		for (Expr e : list) {
			e.accept(this);
		}
		return null;
	}
}
