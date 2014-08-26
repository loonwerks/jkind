package jkind.translation;

import java.util.Map;

import jkind.lustre.Expr;
import jkind.lustre.IdExpr;
import jkind.lustre.visitors.AstMapVisitor;

public class SubstitutionVisitor extends AstMapVisitor {
	protected final Map<String, ? extends Expr> map;

	public SubstitutionVisitor(Map<String, ? extends Expr> map) {
		this.map = map;
	}

	@Override
	public Expr visit(IdExpr e) {
		if (map.containsKey(e.id)) {
			return map.get(e.id);
		} else {
			return e;
		}
	}
}
