package jkind.analysis.evaluation;

import java.math.BigInteger;

import jkind.lustre.BinaryExpr;
import jkind.lustre.BinaryOp;
import jkind.lustre.Equation;
import jkind.lustre.IterVisitor;
import jkind.lustre.Node;
import jkind.lustre.Program;
import jkind.lustre.values.IntegerValue;
import jkind.lustre.values.RealValue;
import jkind.lustre.values.Value;
import jkind.util.BigFraction;

public class DivisionChecker extends IterVisitor {
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
		constantEvaluator = new ConstantEvaluator(program.constants);
		for (Node node : program.nodes) {
			visitNode(node);
		}
	}

	public void visitNode(Node node) {
		constantEvaluator.setHidden(node);
		for (Equation eq : node.equations) {
			eq.expr.accept(this);
		}
	}

	@Override
	public Void visit(BinaryExpr e) {
		e.left.accept(this);
		e.right.accept(this);

		if (e.op == BinaryOp.DIVIDE || e.op == BinaryOp.INT_DIVIDE) {
			int rightSignum = signum(e.right.accept(constantEvaluator));
			
			if (rightSignum == 0) {
				System.out.println("Error at line " + e.location + " division by zero");
				throw new DivisionException();
			} else if (rightSignum < 0 && e.op == BinaryOp.INT_DIVIDE) {
				System.out.println("Error at line " + e.location
						+ " integer division by negative numbers is disabled");
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
			throw new IllegalArgumentException();
		}
	}
}
