package jkind.translation.compound;

import java.util.ArrayList;
import java.util.List;

import jkind.analysis.TypeReconstructor;
import jkind.lustre.ArrayAccessExpr;
import jkind.lustre.ArrayExpr;
import jkind.lustre.ArrayType;
import jkind.lustre.ArrayUpdateExpr;
import jkind.lustre.Expr;
import jkind.lustre.Function;
import jkind.lustre.InlinedProgram;
import jkind.lustre.IntExpr;
import jkind.lustre.Node;
import jkind.lustre.visitors.ExprMapVisitor;

/**
 * Replace all non-constant array indices using if-then-else expressions. Remove
 * all array updates entirely.
 * 
 * Assumption: All node calls have been inlined.
 */
public class RemoveArrayUpdates extends ExprMapVisitor {
	public static InlinedProgram inlinedProgram(InlinedProgram ip) {
		Node node = new RemoveArrayUpdates(ip.functions).visitNode(ip.node);
		return new InlinedProgram(ip.functions, node);
	}

	public RemoveArrayUpdates(List<Function> functions) {
		this.typeReconstructor = new TypeReconstructor(functions);
	}

	private final TypeReconstructor typeReconstructor;

	@Override
	public Node visitNode(Node node) {
		typeReconstructor.setNodeContext(node);
		return super.visitNode(node);
	}

	private ArrayType getArrayType(Expr e) {
		return (ArrayType) e.accept(typeReconstructor);
	}

	@Override
	public Expr visit(ArrayUpdateExpr e) {
		Expr array = e.array.accept(this);
		IntExpr indexExpr = (IntExpr) e.index;
		Expr value = e.value.accept(this);

		ArrayType at = getArrayType(array);
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