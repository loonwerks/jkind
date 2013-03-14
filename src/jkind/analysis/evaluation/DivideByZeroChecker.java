package jkind.analysis.evaluation;

import java.math.BigDecimal;
import java.math.BigInteger;

import jkind.lustre.BinaryExpr;
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
		
		switch (e.op) {
		case DIVIDE:
			Value right = e.right.accept(constantEvaluator);
			if (right instanceof RealValue) {
				BigDecimal rightValue = ((RealValue) right).value;
				if (rightValue.compareTo(BigDecimal.ZERO) == 0) {
					System.out.println("Error at line " + e.location + " division by zero");
					throw new DivideByZeroException();
				}
			}
				
		case INT_DIVIDE:
			Value right2 = e.right.accept(constantEvaluator);
			if (right2 instanceof IntegerValue) {
				BigInteger rightValue = ((IntegerValue) right2).value;
				if (rightValue.compareTo(BigInteger.ZERO) == 0) {
					System.out.println("Error at line " + e.location + " division by zero");
					throw new DivideByZeroException();
				}
			}
		}
		
		return null;
	}
}
