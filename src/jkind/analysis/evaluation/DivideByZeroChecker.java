package jkind.analysis.evaluation;

import java.math.BigInteger;

import jkind.lustre.BinaryExpr;
import jkind.lustre.BinaryOp;
import jkind.lustre.Equation;
import jkind.lustre.IterVisitor;
import jkind.lustre.Node;
import jkind.lustre.Program;

public class DivideByZeroChecker extends IterVisitor {
	private ConstantEvaluator constantEvaluator;

	public static boolean check(Program program) {
		try {
			new DivideByZeroChecker().visitProgram(program);
			return true;
		} catch (DivideByZeroException e) {
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
			Value right = e.right.accept(constantEvaluator);
			if (isZero(right)) {
				System.out.println("Error at line " + e.location + " division by zero");
				throw new DivideByZeroException();
			}
		}

		return null;
	}

	private boolean isZero(Value value) {
		if (value instanceof IntegerValue) {
			IntegerValue iv = (IntegerValue) value;
			return (iv.value.compareTo(BigInteger.ZERO) == 0);
		} else if (value instanceof RealValue) {
			RealValue rv = (RealValue) value;
			return (rv.num.equals(BigInteger.ZERO));
		} else {
			return false;
		}
	}
}
