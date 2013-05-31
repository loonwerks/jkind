package jkind.analysis;

import jkind.lustre.BinaryExpr;
import jkind.lustre.BinaryOp;
import jkind.lustre.Equation;
import jkind.lustre.IterVisitor;
import jkind.lustre.Node;
import jkind.lustre.Program;
import jkind.lustre.UnaryExpr;
import jkind.lustre.UnaryOp;

public class WarnUnguardedPreVisitor extends IterVisitor {
	public static void check(Program program) {
		for (Node node : program.nodes) {
			for (Equation eq : node.equations) {
				eq.expr.accept(UNGUARDED);
			}
		}
	}
	
	final public static WarnUnguardedPreVisitor GUARDED = new WarnUnguardedPreVisitor();
	final public static WarnUnguardedPreVisitor UNGUARDED = new WarnUnguardedPreVisitor();

	@Override
	public Void visit(UnaryExpr e) {
		if (e.op == UnaryOp.PRE) {
			if (this == GUARDED) {
				e.expr.accept(UNGUARDED);
			} else {
				System.out.println("Warning at line " + e.location + " unguarded pre expression");
			}
		} else {
			super.visit(e);
		}
		
		return null;
	}
	
	@Override
	public Void visit(BinaryExpr e) {
		if (e.op == BinaryOp.ARROW) {
			e.left.accept(this);
			e.right.accept(GUARDED);
		} else {
			super.visit(e);
		}
		
		return null;
	}
}
