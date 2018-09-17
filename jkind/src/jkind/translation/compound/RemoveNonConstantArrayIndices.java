package jkind.translation.compound;

import java.util.ArrayList;
import java.util.List;

import jkind.analysis.ConstantAnalyzer;
import jkind.analysis.evaluation.ConstantEvaluator;
import jkind.lustre.ArrayAccessExpr;
import jkind.lustre.ArrayExpr;
import jkind.lustre.ArrayType;
import jkind.lustre.ArrayUpdateExpr;
import jkind.lustre.BinaryExpr;
import jkind.lustre.BinaryOp;
import jkind.lustre.Expr;
import jkind.lustre.IfThenElseExpr;
import jkind.lustre.IntExpr;
import jkind.lustre.Program;
import jkind.lustre.Type;
import jkind.lustre.values.IntegerValue;
import jkind.lustre.visitors.TypeAwareAstMapVisitor;
import jkind.translation.DefaultValueVisitor;

/**
 * Replace all non-constant array indices using if-then-else expressions.
 * Constant array indices are evaluated to be integer literal.
 * 
 * Assumption: All node calls have been inlined.
 */
public class RemoveNonConstantArrayIndices extends TypeAwareAstMapVisitor {
	public static Program program(Program program) {
		return new RemoveNonConstantArrayIndices().visit(program);
	}

	private boolean isConstant(Expr e) {
		return e.accept(new ConstantAnalyzer());
	}

	private IntExpr evalIndex(Expr e) {
		IntegerValue value = new ConstantEvaluator().evalInt(e);
		return new IntExpr(value.value);
	}

	@Override
	public Expr visit(ArrayAccessExpr e) {
		Expr array = e.array.accept(this);
		Expr index = e.index.accept(this);
		if (isConstant(index)) {
			return new ArrayAccessExpr(array, evalIndex(index));
		} else {
			return expandArrayAccess(array, index);
		}
	}

	private Expr expandArrayAccess(Expr array, Expr index) {
		ArrayType arrayType = (ArrayType) getType(array);
		Expr result = getDefaultValue(arrayType.base);
		for (int i = arrayType.size - 1; i >= 0; i--) {
			Expr cond = new BinaryExpr(index, BinaryOp.EQUAL, new IntExpr(i));
			Expr thenExpr = new ArrayAccessExpr(array, i);
			result = new IfThenElseExpr(cond, thenExpr, result);
		}
		return result;
	}

	private Expr getDefaultValue(Type type) {
		return type.accept(new DefaultValueVisitor());
	}

	@Override
	public Expr visit(ArrayUpdateExpr e) {
		Expr array = e.array.accept(this);
		Expr index = e.index.accept(this);
		Expr value = e.value.accept(this);
		if (isConstant(index)) {
			return new ArrayUpdateExpr(array, evalIndex(index), value);
		} else {
			return expandNonConstantArrayUpdate(array, index, value);
		}
	}

	private Expr expandNonConstantArrayUpdate(Expr array, Expr index, Expr value) {
		ArrayType arrayType = (ArrayType) getType(array);
		List<Expr> elements = new ArrayList<>();
		for (int i = 0; i < arrayType.size; i++) {
			Expr cond = new BinaryExpr(index, BinaryOp.EQUAL, new IntExpr(i));
			Expr elseExpr = new ArrayAccessExpr(array, i);
			elements.add(new IfThenElseExpr(cond, value, elseExpr));
		}
		return new ArrayExpr(elements);
	}
}
