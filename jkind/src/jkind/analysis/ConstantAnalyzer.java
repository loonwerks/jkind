package jkind.analysis;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jkind.lustre.ArrayAccessExpr;
import jkind.lustre.ArrayExpr;
import jkind.lustre.ArrayUpdateExpr;
import jkind.lustre.BinaryExpr;
import jkind.lustre.BinaryOp;
import jkind.lustre.BoolExpr;
import jkind.lustre.CastExpr;
import jkind.lustre.CondactExpr;
import jkind.lustre.Constant;
import jkind.lustre.Expr;
import jkind.lustre.IdExpr;
import jkind.lustre.IfThenElseExpr;
import jkind.lustre.IntExpr;
import jkind.lustre.Node;
import jkind.lustre.NodeCallExpr;
import jkind.lustre.RealExpr;
import jkind.lustre.RecordAccessExpr;
import jkind.lustre.RecordExpr;
import jkind.lustre.RecordUpdateExpr;
import jkind.lustre.TupleExpr;
import jkind.lustre.UnaryExpr;
import jkind.lustre.UnaryOp;
import jkind.lustre.visitors.ExprVisitor;
import jkind.util.Util;

public class ConstantAnalyzer implements ExprVisitor<Boolean> {
	private final Set<String> constants = new HashSet<>();

	public ConstantAnalyzer() {
	}

	public ConstantAnalyzer(List<Constant> constantDecls) {
		for (Constant c : constantDecls) {
			constants.add(c.id);
		}
	}

	public ConstantAnalyzer(Node node, List<Constant> constantDecls) {
		this(constantDecls);
		constants.removeAll(Util.getIds(Util.getVarDecls(node)));
	}

	@Override
	public Boolean visit(ArrayAccessExpr e) {
		return e.array.accept(this) && e.index.accept(this);
	}

	@Override
	public Boolean visit(ArrayExpr e) {
		return visitExprs(e.elements);
	}

	@Override
	public Boolean visit(ArrayUpdateExpr e) {
		return e.array.accept(this) && e.index.accept(this) && e.value.accept(this);
	}

	@Override
	public Boolean visit(BinaryExpr e) {
		if (e.op == BinaryOp.ARROW) {
			return false;
		} else {
			return e.left.accept(this) && e.right.accept(this);
		}
	}

	@Override
	public Boolean visit(BoolExpr e) {
		return true;
	}

	@Override
	public Boolean visit(CastExpr e) {
		return e.expr.accept(this);
	}

	@Override
	public Boolean visit(CondactExpr e) {
		return false;
	}

	@Override
	public Boolean visit(IdExpr e) {
		return constants.contains(e.id);
	}

	@Override
	public Boolean visit(IfThenElseExpr e) {
		return e.cond.accept(this) && e.thenExpr.accept(this) && e.elseExpr.accept(this);
	}

	@Override
	public Boolean visit(IntExpr e) {
		return true;
	}

	@Override
	public Boolean visit(NodeCallExpr e) {
		return false;
	}

	@Override
	public Boolean visit(RealExpr e) {
		return true;
	}

	@Override
	public Boolean visit(RecordAccessExpr e) {
		return e.record.accept(this);
	}

	@Override
	public Boolean visit(RecordExpr e) {
		return visitExprs(e.fields.values());
	}

	@Override
	public Boolean visit(RecordUpdateExpr e) {
		return e.record.accept(this) && e.value.accept(this);
	}

	@Override
	public Boolean visit(TupleExpr e) {
		return visitExprs(e.elements);
	}

	@Override
	public Boolean visit(UnaryExpr e) {
		if (e.op == UnaryOp.PRE) {
			return false;
		} else {
			return e.expr.accept(this);
		}
	}

	private Boolean visitExprs(Collection<Expr> exprs) {
		for (Expr expr : exprs) {
			if (!expr.accept(this)) {
				return false;
			}
		}
		return true;
	}
}
