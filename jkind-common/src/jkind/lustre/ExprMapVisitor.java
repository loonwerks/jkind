package jkind.lustre;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class ExprMapVisitor implements ExprVisitor<Expr> {
	@Override
	public Expr visit(BinaryExpr e) {
		return new BinaryExpr(e.location, e.left.accept(this), e.op, e.right.accept(this));
	}

	@Override
	public Expr visit(BoolExpr e) {
		return e;
	}

	@Override
	public Expr visit(CastExpr e) {
		return new CastExpr(e.type, e.expr.accept(this));
	}
	
	@Override
	public Expr visit(CondactExpr e) {
		return new CondactExpr(e.clock.accept(this), (NodeCallExpr) e.call.accept(this),
				visitAll(e.args));
	}

	@Override
	public Expr visit(IdExpr e) {
		return e;
	}

	@Override
	public Expr visit(IfThenElseExpr e) {
		return new IfThenElseExpr(e.location, e.cond.accept(this), e.thenExpr.accept(this),
				e.elseExpr.accept(this));
	}

	@Override
	public Expr visit(IntExpr e) {
		return e;
	}

	@Override
	public Expr visit(NodeCallExpr e) {
		return new NodeCallExpr(e.location, e.node, visitAll(e.args));
	}

	@Override
	public Expr visit(ProjectionExpr e) {
		return new ProjectionExpr(e.location, e.expr.accept(this), e.field);
	}

	@Override
	public Expr visit(RealExpr e) {
		return e;
	}

	@Override
	public Expr visit(RecordExpr e) {
		Map<String, Expr> fields = new HashMap<>();
		for (Entry<String, Expr> entry : e.fields.entrySet()) {
			fields.put(entry.getKey(), entry.getValue().accept(this));
		}
		return new RecordExpr(e.location, e.id, fields);
	}

	@Override
	public Expr visit(UnaryExpr e) {
		return new UnaryExpr(e.location, e.op, e.expr.accept(this));
	}

	protected List<Expr> visitAll(List<? extends Expr> list) {
		List<Expr> result = new ArrayList<>();
		for (Expr e : list) {
			result.add(e.accept(this));
		}
		return result;
	}
}
