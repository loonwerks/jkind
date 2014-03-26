package jkind.analysis;

import java.util.List;

import jkind.lustre.BinaryExpr;
import jkind.lustre.Constant;
import jkind.lustre.Equation;
import jkind.lustre.Expr;
import jkind.lustre.ExprIterVisitor;
import jkind.lustre.Node;
import jkind.lustre.Program;

public class LinearChecker extends ExprIterVisitor {
	private final Level level;
	private boolean passed;
	private ConstantAnalyzer constantAnalyzer;

	public LinearChecker(Level level) {
		this.level = level;
		this.passed = true;
	}

	public static boolean check(Program program, Level level) {
		return new LinearChecker(level).visitProgram(program);
	}

	public boolean visitProgram(Program program) {
		for (Node node : program.nodes) {
			visitNode(node, program.constants);
		}

		return passed;
	}

	public void visitNode(Node node, List<Constant> constants) {
		constantAnalyzer = new ConstantAnalyzer(node, constants);

		for (Equation eq : node.equations) {
			eq.expr.accept(this);
		}
	}

	@Override
	public Void visit(BinaryExpr e) {
		e.left.accept(this);
		e.right.accept(this);

		switch (e.op) {
		case MULTIPLY:
			if (!isConstant(e.left) && !isConstant(e.right)) {
				System.out.println(level + " at line " + e.location
						+ " non-constant multiplication");
				passed = false;
			}
			break;

		case DIVIDE:
		case INT_DIVIDE:
			if (!isConstant(e.right)) {
				System.out.println(level + " at line " + e.location + " non-constant division");
				passed = false;
			}
			break;

		case MODULUS:
			if (!isConstant(e.right)) {
				System.out.println(level + " at line " + e.location + " non-constant modulus");
				passed = false;
			}
			break;

		default:
			break;
		}

		return null;
	}

	private boolean isConstant(Expr e) {
		return e.accept(constantAnalyzer);
	}
}
