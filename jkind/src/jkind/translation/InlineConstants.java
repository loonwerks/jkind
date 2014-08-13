package jkind.translation;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jkind.lustre.Constant;
import jkind.lustre.Expr;
import jkind.lustre.IdExpr;
import jkind.lustre.Program;
import jkind.lustre.visitors.AstMapVisitor;

public class InlineConstants extends AstMapVisitor {
	public static Program program(Program program) {
		return new InlineConstants().visit(program);
	}

	private final Map<String, Expr> constants = new HashMap<>();

	@Override
	protected List<Constant> visitConstants(List<Constant> es) {
		for (Constant e : es) {
			constants.put(e.id, e.expr);
		}

		return Collections.emptyList();
	}

	@Override
	public Expr visit(IdExpr e) {
		if (constants.containsKey(e.id)) {
			return constants.get(e.id).accept(this);
		} else {
			return e;
		}
	}
}
