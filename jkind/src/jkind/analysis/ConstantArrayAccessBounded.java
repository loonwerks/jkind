package jkind.analysis;

import java.math.BigInteger;

import jkind.StdErr;
import jkind.analysis.evaluation.ConstantEvaluator;
import jkind.lustre.ArrayAccessExpr;
import jkind.lustre.ArrayType;
import jkind.lustre.ArrayUpdateExpr;
import jkind.lustre.Equation;
import jkind.lustre.Expr;
import jkind.lustre.Location;
import jkind.lustre.Node;
import jkind.lustre.Program;
import jkind.lustre.visitors.ExprIterVisitor;
import jkind.lustre.visitors.TypeReconstructor;

public class ConstantArrayAccessBounded extends ExprIterVisitor {
	private boolean passed = true;
	private ConstantAnalyzer constantAnalyzer;
	private ConstantEvaluator constantEvaluator;
	private TypeReconstructor typeReconstructor;

	public static boolean check(Program program) {
		return new ConstantArrayAccessBounded().visitProgram(program);
	}

	public boolean visitProgram(Program program) {
		constantAnalyzer = new ConstantAnalyzer(program);
		constantEvaluator = new ConstantEvaluator(program);
		typeReconstructor = new TypeReconstructor(program);

		for (Node node : program.nodes) {
			visitNode(node);
		}

		return passed;
	}

	public void visitNode(Node node) {
		typeReconstructor.setNodeContext(node);

		for (Equation eq : node.equations) {
			eq.expr.accept(this);
		}
		for (Expr e : node.assertions) {
			e.accept(this);
		}
	}

	@Override
	public Void visit(ArrayAccessExpr e) {
		checkAccess(e.index.location, e.array, e.index);
		super.visit(e);
		return null;
	}

	@Override
	public Void visit(ArrayUpdateExpr e) {
		checkAccess(e.index.location, e.array, e.index);
		super.visit(e);
		return null;
	}

	private void checkAccess(Location location, Expr arrayExpr, Expr indexExpr) {
		if (isConstant(indexExpr)) {
			BigInteger index = evalIndex(indexExpr);
			ArrayType arrayType = getArrayType(arrayExpr);
			if (index.compareTo(BigInteger.ZERO) < 0 || index.compareTo(BigInteger.valueOf(arrayType.size)) >= 0) {
				StdErr.error(location, "index " + index + " out of range");
				passed = false;
			}
		}
	}

	private ArrayType getArrayType(Expr e) {
		return (ArrayType) e.accept(typeReconstructor);
	}

	private boolean isConstant(Expr e) {
		return e.accept(constantAnalyzer);
	}

	private BigInteger evalIndex(Expr e) {
		return constantEvaluator.evalInt(e).value;
	}
}
