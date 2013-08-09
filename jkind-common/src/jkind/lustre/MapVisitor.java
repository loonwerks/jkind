package jkind.lustre;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


public class MapVisitor implements ExprVisitor<Expr> {
	@Override
	public Expr visit(BinaryExpr e) {
		return new BinaryExpr(e.location, e.left.accept(this), e.op, e.right.accept(this));
	}

	@Override
	public Expr visit(BoolExpr e) {
		return e;
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
		List<Expr> args = new ArrayList<>();
		for (Expr arg : e.args) {
			args.add(arg.accept(this));
		}
		return new NodeCallExpr(e.location, e.node, args);
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
		return new RecordExpr(e.location, fields);
	}

	@Override
	public Expr visit(UnaryExpr e) {
		return new UnaryExpr(e.location, e.op, e.expr.accept(this));
	}
}
