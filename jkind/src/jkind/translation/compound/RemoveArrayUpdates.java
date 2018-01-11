package jkind.translation.compound;

import java.util.ArrayList;
import java.util.List;

import jkind.lustre.ArrayAccessExpr;
import jkind.lustre.ArrayExpr;
import jkind.lustre.ArrayType;
import jkind.lustre.ArrayUpdateExpr;
import jkind.lustre.Expr;
import jkind.lustre.IntExpr;
import jkind.lustre.Program;
import jkind.lustre.visitors.TypeAwareAstMapVisitor;

/**
 * Replace all non-constant array indices using if-then-else expressions. Remove
 * all array updates entirely.
 * 
 * Assumption: All node calls have been inlined.
 */
public class RemoveArrayUpdates extends TypeAwareAstMapVisitor {
	public static Program program(Program program) {
		return new RemoveArrayUpdates().visit(program);
	}
	
	@Override
	public Expr visit(ArrayUpdateExpr e) {
		Expr array = e.array.accept(this);
		IntExpr indexExpr = (IntExpr) e.index;
		Expr value = e.value.accept(this);

		ArrayType at = (ArrayType) getType(array);
		int index = indexExpr.value.intValue();
		List<Expr> elements = new ArrayList<>();
		for (int i = 0; i < at.size; i++) {
			if (i == index) {
				elements.add(value);
			} else {
				elements.add(new ArrayAccessExpr(array, i));
			}
		}

		return new ArrayExpr(elements);
	}
}