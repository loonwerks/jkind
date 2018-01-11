package jkind.lustre.visitors;

import static java.util.stream.Collectors.toList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;

import jkind.lustre.ArrayAccessExpr;
import jkind.lustre.ArrayExpr;
import jkind.lustre.ArrayUpdateExpr;
import jkind.lustre.BinaryExpr;
import jkind.lustre.BoolExpr;
import jkind.lustre.CastExpr;
import jkind.lustre.CondactExpr;
import jkind.lustre.Expr;
import jkind.lustre.FunctionCallExpr;
import jkind.lustre.IdExpr;
import jkind.lustre.IfThenElseExpr;
import jkind.lustre.IntExpr;
import jkind.lustre.NodeCallExpr;
import jkind.lustre.RealExpr;
import jkind.lustre.RecordAccessExpr;
import jkind.lustre.RecordExpr;
import jkind.lustre.RecordUpdateExpr;
import jkind.lustre.TupleExpr;
import jkind.lustre.UnaryExpr;

public class ExprMapVisitor implements ExprVisitor<Expr> {
	@Override
	public Expr visit(ArrayAccessExpr e) {
		return new ArrayAccessExpr(e.location, e.array.accept(this), e.index.accept(this));
	}

	@Override
	public Expr visit(ArrayExpr e) {
		return new ArrayExpr(e.location, visitExprs(e.elements));
	}

	@Override
	public Expr visit(ArrayUpdateExpr e) {
		return new ArrayUpdateExpr(e.location, e.array.accept(this), e.index.accept(this), e.value.accept(this));
	}

	@Override
	public Expr visit(BinaryExpr e) {
		Expr left = e.left.accept(this);
		Expr right = e.right.accept(this);
		if (e.left == left && e.right == right) {
			return e;
		}
		return new BinaryExpr(e.location, left, e.op, right);
	}

	@Override
	public Expr visit(BoolExpr e) {
		return e;
	}

	@Override
	public Expr visit(CastExpr e) {
		return new CastExpr(e.location, e.type, e.expr.accept(this));
	}

	@Override
	public Expr visit(CondactExpr e) {
		return new CondactExpr(e.location, e.clock.accept(this), (NodeCallExpr) e.call.accept(this),
				visitExprs(e.args));
	}

	@Override
	public Expr visit(FunctionCallExpr e) {
		return new FunctionCallExpr(e.location, e.function, visitExprs(e.args));
	}

	@Override
	public Expr visit(IdExpr e) {
		return e;
	}

	@Override
	public Expr visit(IfThenElseExpr e) {
		Expr cond = e.cond.accept(this);
		Expr thenExpr = e.thenExpr.accept(this);
		Expr elseExpr = e.elseExpr.accept(this);
		if (e.cond == cond && e.thenExpr == thenExpr && e.elseExpr == elseExpr) {
			return e;
		}
		return new IfThenElseExpr(e.location, cond, thenExpr, elseExpr);
	}

	@Override
	public Expr visit(IntExpr e) {
		return e;
	}

	@Override
	public Expr visit(NodeCallExpr e) {
		return new NodeCallExpr(e.location, e.node, visitExprs(e.args));
	}

	@Override
	public Expr visit(RealExpr e) {
		return e;
	}

	@Override
	public Expr visit(RecordAccessExpr e) {
		return new RecordAccessExpr(e.location, e.record.accept(this), e.field);
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
	public Expr visit(RecordUpdateExpr e) {
		return new RecordUpdateExpr(e.location, e.record.accept(this), e.field, e.value.accept(this));
	}

	@Override
	public Expr visit(TupleExpr e) {
		return new TupleExpr(e.location, visitExprs(e.elements));
	}

	@Override
	public Expr visit(UnaryExpr e) {
		Expr expr = e.expr.accept(this);
		if (e.expr == expr) {
			return e;
		}
		return new UnaryExpr(e.location, e.op, expr);
	}

	protected List<Expr> visitExprs(List<? extends Expr> es) {
		return map(e -> e.accept(this), es);
	}

	protected <A, B> List<B> map(Function<? super A, ? extends B> f, List<A> xs) {
		return xs.stream().map(f).collect(toList());
	}
}
