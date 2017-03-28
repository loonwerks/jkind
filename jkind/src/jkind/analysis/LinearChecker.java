package jkind.analysis;

import jkind.StdErr;
import jkind.lustre.BinaryExpr;
import jkind.lustre.Equation;
import jkind.lustre.Expr;
import jkind.lustre.Node;
import jkind.lustre.Program;
import jkind.lustre.UnaryExpr;
import jkind.lustre.visitors.ExprIterVisitor;

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

	public static boolean isLinear(Program program) {
		return new LinearChecker(Level.IGNORE).visitProgram(program);
	}

	public static boolean isLinear(Node node) {
		return isLinear(new Program(node));
	}

	public boolean visitProgram(Program program) {
		constantAnalyzer = new ConstantAnalyzer(program);

		for (Node node : program.nodes) {
			visitNode(node);
		}

		return passed;
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

		switch (e.op) {
		case MULTIPLY:
			if (!isConstant(e.left) && !isConstant(e.right)) {
				StdErr.output(level, e.location, "non-constant multiplication");
				passed = false;
			}
			break;

		case DIVIDE:
		case INT_DIVIDE:
			if (!isConstant(e.right)) {
				StdErr.output(level, e.location, "non-constant division");
				passed = false;
			}
			break;

		case MODULUS:
			if (!isConstant(e.right)) {
				StdErr.output(level, e.location, "non-constant modulus");
				passed = false;
			}
			break;
		case POW:
			StdErr.output(level, e.location, "Use of power function");
			passed = false;
			break;
		case ARCTAN2:
			StdErr.output(level, e.location, "Use of arctan2 function");
			passed = false;
			break;
		default:
			break;
		}

		return null;
	}

	@Override
	public Void visit(UnaryExpr e) {
		switch (e.op){
		case EXP: 
		case LOG:
		case SQRT:
		case SIN:
		case COS: 
		case TAN:
		case ARCSIN: 
		case ARCCOS:
		case ARCTAN:
		case SINH: 
		case COSH:
		case TANH:
		case MATAN: 
		{ 
			StdErr.output(level, e.location, "use of trigonometric function");
			passed = false; 
		}
		default: break;
		}
		return null;
	}
	private boolean isConstant(Expr e) {
		return e.accept(constantAnalyzer);
	}
}
