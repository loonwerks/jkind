package jkind.analysis.evaluation;

import java.math.BigInteger;
import java.util.List;

import jkind.Output;
import jkind.lustre.BinaryExpr;
import jkind.lustre.BinaryOp;
import jkind.lustre.Constant;
import jkind.lustre.Equation;
import jkind.lustre.Expr;
import jkind.lustre.Node;
import jkind.lustre.Program;
import jkind.lustre.values.IntegerValue;
import jkind.lustre.values.RealValue;
import jkind.lustre.values.Value;
import jkind.lustre.visitors.ExprIterVisitor;
import jkind.util.BigFraction;

public class DivisionChecker extends ExprIterVisitor {
	private ConstantEvaluator constantEvaluator;

	public static boolean check(Program program) {
		try {
			new DivisionChecker().visitProgram(program);
			return true;
		} catch (DivisionException e) {
			return false;
		}
	}

	public void visitProgram(Program program) {
		visitConstants(program.constants);
		for (Node node : program.nodes) {
			visitNode(node);
		}
	}

	private void visitConstants(List<Constant> constants) {
		constantEvaluator = new ConstantEvaluator();
		for (Constant c : constants) {
			// Check constants for division by zero, before admitting them
			c.expr.accept(this);
			constantEvaluator.addConstant(c);
		}
	}

	public void visitNode(Node node) {
		for (Equation eq : node.equations) {
			eq.expr.accept(this);
		}
		for (Expr e : node.assertions) {
			e.accept(this);
		}
	}

	@Override
	public Void visit(BinaryExpr e) {
		e.left.accept(this);
		e.right.accept(this);

		if (e.op == BinaryOp.DIVIDE || e.op == BinaryOp.INT_DIVIDE || e.op == BinaryOp.MODULUS) {
			int rightSignum = signum(e.right.accept(constantEvaluator));

			if (rightSignum == 0) {
				Output.error(e.location, "division by zero");
				throw new DivisionException();
			} else if (rightSignum < 0 && e.op == BinaryOp.INT_DIVIDE) {
				Output.error(e.location, "integer division by negative numbers is disabled");
				throw new DivisionException();
			} else if (rightSignum < 0 && e.op == BinaryOp.MODULUS) {
				Output.error(e.location, "modulus by negative numbers is disabled");
				throw new DivisionException();
			}
		}

		return null;
	}

	private int signum(Value value) {
		if (value instanceof IntegerValue) {
			IntegerValue iv = (IntegerValue) value;
			return iv.value.compareTo(BigInteger.ZERO);
		} else if (value instanceof RealValue) {
			RealValue rv = (RealValue) value;
			return rv.value.compareTo(BigFraction.ZERO);
		} else {
			/*
			 * This should only arise for non-constant division which is
			 * currently only enabled for Z3. We return 1 to allow everything to
			 * go through. This allows, for example, users to divide by a
			 * negative integer which gives different results for different
			 * solvers, or even to divide by 0. We allow this, but may change
			 * the semantics of those operations later.
			 */
			return 1;
		}
	}
}
