package jkind.translation.tuples;

import java.util.ArrayList;

import java.util.Iterator;
import java.util.List;

import jkind.lustre.BinaryExpr;
import jkind.lustre.BinaryOp;
import jkind.lustre.Equation;
import jkind.lustre.Expr;
import jkind.lustre.Node;
import jkind.lustre.TupleExpr;
import jkind.lustre.UnaryExpr;
import jkind.lustre.UnaryOp;
import jkind.lustre.visitors.ExprMapVisitor;

/**
 * Expand equalities and inequalities on tuple expressions.
 * 
 * Assumption: All tuple expressions have been lifted as far as possible.
 */
public class FlattenTupleComparisons extends ExprMapVisitor {
	public static Node node(Node node) {
		return new FlattenTupleComparisons().visitNode(node);
	}

	private Node visitNode(Node node) {
		List<Equation> equations = visitEquations(node.equations);
		List<Expr> assertions = visitAll(node.assertions);
		return new Node(node.id, node.inputs, node.outputs, node.locals, equations,
				node.properties, assertions);
	}

	private List<Equation> visitEquations(List<Equation> equations) {
		List<Equation> results = new ArrayList<>();
		for (Equation eq : equations) {
			results.add(new Equation(eq.location, eq.lhs, eq.expr.accept(this)));
		}
		return results;
	}

	@Override
	public Expr visit(BinaryExpr e) {
		if (isTupleComparison(e)) {
			TupleExpr left = (TupleExpr) e.left.accept(this);
			TupleExpr right = (TupleExpr) e.right.accept(this);
			TupleExpr tuple = TupleUtil.mapBinary(BinaryOp.EQUAL, left, right);
			Expr equal = conjoin(tuple.elements);
			if (e.op == BinaryOp.EQUAL) {
				return equal;
			} else {
				return new UnaryExpr(UnaryOp.NOT, equal);
			}
		} else {
			return super.visit(e);
		}
	}

	private Expr conjoin(List<Expr> exprs) {
		Iterator<Expr> iterator = exprs.iterator();
		Expr result = iterator.next();
		while (iterator.hasNext()) {
			result = new BinaryExpr(result, BinaryOp.AND, iterator.next());
		}
		return result;
	}

	private boolean isTupleComparison(BinaryExpr e) {
		// We assume tuples are lifted, so just check the top level of e.left
		return (e.op == BinaryOp.EQUAL || e.op == BinaryOp.NOTEQUAL) && e.left instanceof TupleExpr;
	}
}
