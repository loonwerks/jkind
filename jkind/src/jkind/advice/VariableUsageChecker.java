package jkind.advice;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jkind.lustre.Expr;
import jkind.lustre.IdExpr;
import jkind.lustre.VarDecl;
import jkind.lustre.visitors.ExprConjunctiveVisitor;
import jkind.util.Util;

public class VariableUsageChecker extends ExprConjunctiveVisitor {
	private final Set<String> ids = new HashSet<>();

	public VariableUsageChecker(List<VarDecl> varDecls) {
		ids.addAll(Util.getIds(varDecls));
	}

	public boolean check(Expr expr) {
		return expr.accept(this);
	}

	@Override
	public Boolean visit(IdExpr e) {
		return ids.contains(e.id);
	}
}
