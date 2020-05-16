package jkind.analysis;

import jkind.lustre.ArrayType;
import jkind.lustre.EnumType;
import jkind.lustre.NamedType;
import jkind.lustre.RecordType;
import jkind.lustre.SubrangeIntType;
import jkind.lustre.TupleType;
import jkind.lustre.Type;
import jkind.lustre.visitors.TypeVisitor;

public class ContainsSubrange implements TypeVisitor<Boolean> {
	public static boolean check(Type type) {
		return type.accept(new ContainsSubrange());
	}

	@Override
	public Boolean visit(ArrayType e) {
		return e.base.accept(this);
	}

	@Override
	public Boolean visit(EnumType e) {
		return false;
	}

	@Override
	public Boolean visit(NamedType e) {
		return false;
	}

	@Override
	public Boolean visit(RecordType e) {
		// We only care if the toString() has subranges, thus records never do
		return false;
	}

	@Override
	public Boolean visit(TupleType e) {
		for (Type type : e.types) {
			if (type.accept(this)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public Boolean visit(SubrangeIntType e) {
		return true;
	}
}
