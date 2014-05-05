package jkind.analysis;

import java.util.Map;

import jkind.lustre.BinaryExpr;
import jkind.lustre.BinaryOp;
import jkind.lustre.CallExpr;
import jkind.lustre.Equation;
import jkind.lustre.Expr;
import jkind.lustre.Node;
import jkind.lustre.Program;
import jkind.lustre.UnaryExpr;
import jkind.lustre.UnaryOp;
import jkind.lustre.visitors.ExprIterVisitor;
import jkind.util.Util;

public class WarnUnguardedPreVisitor extends ExprIterVisitor {
	private static Map<String, Node> nodeTable;

	public static void check(Program program) {
		nodeTable = Util.getNodeTable(program.nodes);
		for (Node node : program.nodes) {
			for (Equation eq : node.equations) {
				eq.expr.accept(UNGUARDED);
			}
			for (Expr e : node.assertions) {
				e.accept(UNGUARDED);
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
	
	@Override
	public Void visit(CallExpr e) {
		if (nodeTable.containsKey(e.name)) {
			UNGUARDED.visitExprs(e.args);
		} else {
			visitExprs(e.args);
		}
		return null;
	}
}
