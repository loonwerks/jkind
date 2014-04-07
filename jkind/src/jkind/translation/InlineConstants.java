package jkind.translation;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jkind.lustre.Constant;
import jkind.lustre.Expr;
import jkind.lustre.IdExpr;
import jkind.lustre.Node;
import jkind.lustre.Program;
import jkind.lustre.visitors.AstMapVisitor;
import jkind.util.Util;

public class InlineConstants extends AstMapVisitor {
	public static Program program(Program program) {
		return new InlineConstants().visit(program);
	}

	private final Map<String, Expr> constants = new HashMap<>();
	private final Set<String> variables = new HashSet<>();
	
	@Override
	protected List<Constant> visitConstants(List<Constant> es) {
		for (Constant e : es) {
			constants.put(e.id, e.expr.accept(new SubstitutionVisitor(constants)));
		}
		
		return Collections.emptyList();
	}

	@Override
	public Node visit(Node e) {
		variables.clear();
		variables.addAll(Util.getIds(Util.getVarDecls(e)));
		return super.visit(e);
	}
	
	@Override
	public Expr visit(IdExpr e) {
		if (!variables.contains(e.id) && constants.containsKey(e.id)) {
			return constants.get(e.id);
		} else {
			return e;
		}
	}
}
