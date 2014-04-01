package jkind.lustre.visitors;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import jkind.lustre.ArrayType;
import jkind.lustre.NamedType;
import jkind.lustre.RecordType;
import jkind.lustre.SubrangeIntType;
import jkind.lustre.Type;


public class TypeMapVisitor implements TypeVisitor<Type> {
	@Override
	public Type visit(ArrayType e) {
		return new ArrayType(e.location, e.base.accept(this), e.size);
	}

	@Override
	public Type visit(NamedType e) {
		return e;
	}

	@Override
	public Type visit(RecordType e) {
		Map<String, Type> fields = new HashMap<>();
		for (Entry<String, Type> entry : e.fields.entrySet()) {
			fields.put(entry.getKey(), entry.getValue().accept(this));
		}
		return new RecordType(e.location, e.id, fields);
	}

	@Override
	public Type visit(SubrangeIntType e) {
		return e;
	}
}
