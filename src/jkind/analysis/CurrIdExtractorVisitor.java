package jkind.analysis;

import java.util.HashSet;
import java.util.Set;

import jkind.lustre.Expr;
import jkind.lustre.IdExpr;
import jkind.lustre.IterVisitor;
import jkind.lustre.UnaryExpr;
import jkind.lustre.UnaryOp;

public class CurrIdExtractorVisitor extends IterVisitor {
	public static Set<String> getCurrIds(Expr expr) {
		CurrIdExtractorVisitor visitor = new CurrIdExtractorVisitor();
		expr.accept(visitor);
		return visitor.set;
	}
	
	private Set<String> set = new HashSet<String>();
	
	@Override
	public Void visit(UnaryExpr e) {
		if (e.op == UnaryOp.PRE) {
			return null;
		} else {
			return super.visit(e);
		}
	}
	
	@Override
	public Void visit(IdExpr e) {
		set.add(e.id);
		return null;
	}
}
