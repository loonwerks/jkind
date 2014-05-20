package jkind.translation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jkind.lustre.EnumType;
import jkind.lustre.Expr;
import jkind.lustre.IdExpr;
import jkind.lustre.IntExpr;
import jkind.lustre.Program;
import jkind.lustre.TypeDef;
import jkind.lustre.visitors.AstMapVisitor;
import jkind.util.Util;

public class InlineEnumValues extends AstMapVisitor {
	public static Program program(Program program) {
		return new InlineEnumValues().visit(program);
	}

	private final Map<String, IntExpr> enumValues = new HashMap<>();

	@Override
	protected List<TypeDef> visitTypeDefs(List<TypeDef> es) {
		for (EnumType et : Util.getEnumTypes(es)) {
			for (int i = 0; i < et.values.size(); i++) {
				enumValues.put(et.values.get(i), new IntExpr(i));
			}
		}

		return es;
	}

	@Override
	public Expr visit(IdExpr e) {
		if (enumValues.containsKey(e.id)) {
			return enumValues.get(e.id);
		} else {
			return e;
		}
	}
}
