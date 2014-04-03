package jkind.translation.tuples;

import java.util.List;

import jkind.lustre.BinaryExpr;
import jkind.lustre.BinaryOp;
import jkind.lustre.Equation;
import jkind.lustre.Expr;
import jkind.lustre.IfThenElseExpr;
import jkind.lustre.Node;
import jkind.lustre.TupleExpr;
import jkind.lustre.UnaryExpr;
import jkind.lustre.visitors.ExprMapVisitor;

/**
 * Lift all tuples as far as possible
 */
public class LiftTuples extends ExprMapVisitor {
	public static Node node(Node node) {
		return new LiftTuples().visitNode(node);
	}

	private Node visitNode(Node node) {
		List<Equation> equations = visitEquations(node.equations);
		List<Expr> assertions = visitAll(node.assertions);
		return new Node(node.id, node.inputs, node.outputs, node.locals, equations,
				node.properties, assertions);
	}

	@Override
	public Expr visit(BinaryExpr e) {
		Expr left = e.left.accept(this);
		Expr right = e.right.accept(this);
		if (left instanceof TupleExpr && e.op == BinaryOp.ARROW) {
			return TupleUtil.mapBinary(e.op, (TupleExpr) left, (TupleExpr) right);
		} else {
			return new BinaryExpr(e.location, left, e.op, right);
		}
	}

	@Override
	public Expr visit(IfThenElseExpr e) {
		Expr cond = e.cond.accept(this);
		Expr thenExpr = e.thenExpr.accept(this);
		Expr elseExpr = e.elseExpr.accept(this);
		if (thenExpr instanceof TupleExpr) {
			return TupleUtil.mapIf(e.cond, (TupleExpr) thenExpr, (TupleExpr) elseExpr);
		} else {
			return new IfThenElseExpr(e.location, cond, thenExpr, elseExpr);
		}
	}

	@Override
	public Expr visit(UnaryExpr e) {
		Expr expr = e.expr.accept(this);
		if (expr instanceof TupleExpr) {
			return TupleUtil.mapUnary(e.op, (TupleExpr) expr);
		} else {
			return new UnaryExpr(e.location, e.op, expr);
		}
	}
}
