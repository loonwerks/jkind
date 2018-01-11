package jkind.analysis;

import java.util.Map;

import jkind.lustre.EnumType;
import jkind.lustre.NamedType;
import jkind.lustre.SubrangeIntType;
import jkind.lustre.Type;
import jkind.lustre.visitors.TypeIterVisitor;

public class ContainsConstrainedType extends TypeIterVisitor {
	public static boolean check(Type type, Map<String, Type> typeTable) {
		ContainsConstrainedType visitor = new ContainsConstrainedType(typeTable);
		type.accept(visitor);
		return visitor.containsConstrainedType;
	}

	private final Map<String, Type> typeTable;
	private boolean containsConstrainedType = false;

	public ContainsConstrainedType(Map<String, Type> typeTable) {
		this.typeTable = typeTable;
	}

	@Override
	public Void visit(EnumType e) {
		containsConstrainedType = true;
		return null;
	}

	@Override
	public Void visit(NamedType e) {
		if (!e.isBuiltin()) {
			typeTable.get(e.name).accept(this);
		}
		return null;
	}

	@Override
	public Void visit(SubrangeIntType e) {
		containsConstrainedType = true;
		return null;
	}
}