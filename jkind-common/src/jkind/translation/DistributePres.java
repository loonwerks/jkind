package jkind.translation;

import static jkind.lustre.LustreUtil.arrow;
import static jkind.lustre.LustreUtil.pre;
import jkind.lustre.BinaryExpr;
import jkind.lustre.BinaryOp;
import jkind.lustre.CondactExpr;
import jkind.lustre.Expr;
import jkind.lustre.IdExpr;
import jkind.lustre.Node;
import jkind.lustre.NodeCallExpr;
import jkind.lustre.UnaryExpr;
import jkind.lustre.UnaryOp;
import jkind.lustre.visitors.AstMapVisitor;

/**
 * Push all instances of 'pre' operator down as far as possible.
 * 
 * For example:
 * 
 * <pre>
 * pre(x + pre(3 * z)) = pre(x) + 3 * pre(pre(z))
 * </pre>
 * 
 * If there are no node calls, condacts, or arrow expressions underneath 'pre'
 * operators, then in the result 'pre' will only be applied to variables.
 */
public class DistributePres extends AstMapVisitor {
	private int pres = 0;

	public static Node node(Node node) {
		return new DistributePres().visit(node);
	}

	private Expr applyPres(Expr e) {
		Expr result = e;
		for (int i = 0; i < pres; i++) {
			result = pre(result);
		}
		return result;
	}

	@Override
	public Expr visit(BinaryExpr e) {
		if (e.op == BinaryOp.ARROW) {
			Expr left = e.left.accept(new DistributePres());
			Expr right = e.right.accept(new DistributePres());
			return applyPres(arrow(left, right));
		} else {
			return super.visit(e);
		}
	}

	@Override
	public Expr visit(CondactExpr e) {
		return applyPres(e);
	}

	@Override
	public Expr visit(IdExpr e) {
		return applyPres(e);
	}

	@Override
	public Expr visit(NodeCallExpr e) {
		return applyPres(e);
	}

	@Override
	public Expr visit(UnaryExpr e) {
		if (e.op == UnaryOp.PRE) {
			pres++;
			Expr result = e.expr.accept(this);
			pres--;
			return result;
		} else {
			return super.visit(e);
		}
	}
}
