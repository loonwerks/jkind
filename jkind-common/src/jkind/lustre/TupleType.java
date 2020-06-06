package jkind.lustre;

import java.util.List;
import java.util.StringJoiner;

import jkind.lustre.visitors.TypeVisitor;
import jkind.util.Util;

public class TupleType extends Type {
	public final List<Type> types;

	public TupleType(List<? extends Type> types) {
		super(Location.NULL);
		if (types != null && types.size() == 1) {
			throw new IllegalArgumentException("Cannot construct singleton tuple type");
		}
		this.types = Util.safeList(types);
	}

	public static Type compress(List<? extends Type> types) {
		if (types.size() == 1) {
			return types.get(0);
		}
		return new TupleType(types);
	}

	@Override
	public String toString() {
		StringJoiner text = new StringJoiner(", ", "(", ")");
		types.forEach(t -> text.add(t.toString()));
		return text.toString();
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