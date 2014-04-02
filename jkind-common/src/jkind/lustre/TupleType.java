package jkind.lustre;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import jkind.lustre.visitors.TypeVisitor;

public class TupleType extends Type {
	final public List<Type> types;

	public TupleType(List<Type> types) {
		super(Location.NULL);
		this.types = Collections.unmodifiableList(types);
	}

	@Override
	public String toString() {
		if (types.isEmpty()) {
			return "()";
		}

		StringBuilder sb = new StringBuilder();

		Iterator<Type> iterator = types.iterator();
		sb.append("(");
		sb.append(iterator.next());
		while (iterator.hasNext()) {
			sb.append(", ");
			sb.append(iterator.next());
		}
		sb.append(")");
		return sb.toString();
	}

	@Override
	public int hashCode() {
		return types.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof TupleType) {
			TupleType tt = (TupleType) obj;
			return types.equals(tt.types);
		}
		return false;
	}

	@Override
	public <T> T accept(TypeVisitor<T> visitor) {
		return visitor.visit(this);
	}
}