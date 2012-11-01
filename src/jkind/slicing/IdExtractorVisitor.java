package jkind.slicing;

import java.util.HashSet;
import java.util.Set;

import jkind.lustre.Expr;
import jkind.lustre.IdExpr;
import jkind.lustre.IterVisitor;

public class IdExtractorVisitor extends IterVisitor {
	public static Set<String> getIds(Expr expr) {
		IdExtractorVisitor visitor = new IdExtractorVisitor();
		expr.accept(visitor);
		return visitor.set;
	}
	
	private Set<String> set = new HashSet<String>();
	
	@Override
	public Void visit(IdExpr e) {
		set.add(e.id);
		return null;
	}
}
