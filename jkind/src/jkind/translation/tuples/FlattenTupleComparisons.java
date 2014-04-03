package jkind.translation.tuples;

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
import jkind.translation.compound.CompoundUtil;

/**
 * Expand equalities and inequalities on tuples
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

	@Override
	public Expr visit(BinaryExpr e) {
		if (isTupleComparison(e)) {
			TupleExpr left = (TupleExpr) e.left.accept(this);
			TupleExpr right = (TupleExpr) e.right.accept(this);
			TupleExpr tuple = TupleUtil.mapBinary(BinaryOp.EQUAL, left, right);
			Expr equal = CompoundUtil.conjoin(tuple.elements);
			if (e.op == BinaryOp.EQUAL) {
				return equal;
			} else {
				return new UnaryExpr(UnaryOp.NOT, equal);
			}
		} else {
			return super.visit(e);
		}
	}

	private boolean isTupleComparison(BinaryExpr e) {
		// We assume tuples are lifted, so just check the top level of e.left
		return (e.op == BinaryOp.EQUAL || e.op == BinaryOp.NOTEQUAL) && e.left instanceof TupleExpr;
	}
}
