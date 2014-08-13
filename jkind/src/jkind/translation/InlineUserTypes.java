package jkind.translation;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jkind.lustre.Program;
import jkind.lustre.Type;
import jkind.lustre.TypeDef;
import jkind.lustre.VarDecl;
import jkind.lustre.visitors.AstMapVisitor;
import jkind.util.Util;

public class InlineUserTypes extends AstMapVisitor {
	public static Program program(Program program) {
		return new InlineUserTypes().visit(program);
	}

	private final Map<String, Type> types = new HashMap<>();

	@Override
	protected List<TypeDef> visitTypeDefs(List<TypeDef> es) {
		types.putAll(Util.createResolvedTypeTable(es));
		return Collections.emptyList();
	}

	@Override
	public VarDecl visit(VarDecl e) {
		return new VarDecl(e.id, Util.resolveType(e.type, types));
	}
}
