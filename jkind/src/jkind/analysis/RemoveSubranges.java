package jkind.analysis;

import jkind.lustre.NamedType;
import jkind.lustre.SubrangeIntType;
import jkind.lustre.Type;
import jkind.lustre.visitors.TypeMapVisitor;

public class RemoveSubranges extends TypeMapVisitor {
	public static Type remove(Type type) {
		return type.accept(new RemoveSubranges());
	}

	@Override
	public Type visit(SubrangeIntType e) {
		return NamedType.INT;
	}
}
