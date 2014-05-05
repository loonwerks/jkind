package jkind.lustre;

import java.util.Collections;
import java.util.List;

import jkind.lustre.visitors.TypeVisitor;

public class EnumType extends Type {
	final public String id;
	final public List<String> values;

	public EnumType(Location location, String id, List<String> values) {
		super(location);
		this.id = id;
		this.values = Collections.unmodifiableList(values);
	}

	public EnumType(String id, List<String> values) {
		this(Location.NULL, id, values);
	}

	@Override
	public String toString() {
		return id;
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof EnumType) {
			EnumType et = (EnumType) obj;
			return id.equals(et.id);
		}
		return false;
	}

	@Override
	public <T> T accept(TypeVisitor<T> visitor) {
		return visitor.visit(this);
	}
}