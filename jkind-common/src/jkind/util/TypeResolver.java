package jkind.util;

import java.util.Map;

import jkind.lustre.NamedType;
import jkind.lustre.Type;
import jkind.lustre.visitors.TypeMapVisitor;

public class TypeResolver extends TypeMapVisitor {
	public static Type resolve(Type type, Map<String, Type> map) {
		return type.accept(new TypeResolver(map));
	}

	public TypeResolver(Map<String, Type> map) {
		this.map = map;
	}

	private final Map<String, Type> map;

	@Override
	public Type visit(NamedType e) {
		if (e.isBuiltin()) {
			return e;
		} else {
			return map.get(e.name).accept(this);
		}
	}
}
