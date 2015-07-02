package jkind.translation;

import java.util.ArrayList;
import java.util.List;

import jkind.lustre.Expr;
import jkind.lustre.IdExpr;
import jkind.lustre.visitors.ExprIterVisitor;

public class IdListExtractorVisitor extends ExprIterVisitor {
	public static List<String> getIds(Expr expr) {
		IdListExtractorVisitor visitor = new IdListExtractorVisitor();
		expr.accept(visitor);
		return visitor.list;
	}
	
	private final List<String> list = new ArrayList<>();

	@Override
	public Void visit(IdExpr e) {
		list.add(e.id);
		return null;
	}
}
