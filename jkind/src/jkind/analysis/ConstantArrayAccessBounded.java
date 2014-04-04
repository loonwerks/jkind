package jkind.analysis;

import java.util.List;

import jkind.analysis.evaluation.ConstantEvaluator;
import jkind.lustre.ArrayAccessExpr;
import jkind.lustre.ArrayType;
import jkind.lustre.ArrayUpdateExpr;
import jkind.lustre.Constant;
import jkind.lustre.Equation;
import jkind.lustre.Expr;
import jkind.lustre.Location;
import jkind.lustre.Node;
import jkind.lustre.Program;
import jkind.lustre.values.IntegerValue;
import jkind.lustre.visitors.ExprIterVisitor;

public class ConstantArrayAccessBounded extends ExprIterVisitor {
	private boolean passed = true;
	private ConstantAnalyzer constantAnalyzer;
	private ConstantEvaluator constantEvaluator;
	private TypeReconstructor typeReconstructor;

	public static boolean check(Program program) {
		return new ConstantArrayAccessBounded().visitProgram(program);
	}

	public boolean visitProgram(Program program) {
		constantEvaluator = new ConstantEvaluator(program.constants);
		typeReconstructor = new TypeReconstructor(program);

		for (Node node : program.nodes) {
			visitNode(node, program.constants);
		}

		return passed;
	}

	public void visitNode(Node node, List<Constant> constants) {
		constantAnalyzer = new ConstantAnalyzer(node, constants);
		constantEvaluator.setHidden(node);
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
			int index = evalIndex(indexExpr);
			ArrayType arrayType = getArrayType(arrayExpr);
			if (index < 0 || index >= arrayType.size) {
				System.out.println("Error at line " + location + " index " + index
						+ " out of range");
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

	private int evalIndex(Expr e) {
		IntegerValue iv = (IntegerValue) e.accept(constantEvaluator);
		return iv.value.intValue();
	}
}
