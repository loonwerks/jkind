package jkind.lustre;

import java.util.Collections;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import jkind.lustre.visitors.TypeVisitor;

public class RecordType extends Type {
	final public String id;
	final public SortedMap<String, Type> fields;

	public RecordType(Location location, String id, Map<String, Type> fields) {
		super(location);
		this.id = id;
		this.fields = Collections.unmodifiableSortedMap(new TreeMap<>(fields));
	}

	public RecordType(String id, Map<String, Type> fields) {
		this(Location.NULL, id, fields);
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
		if (obj instanceof RecordType) {
			RecordType rt = (RecordType) obj;
			return id.equals(rt.id);
		}
		return false;
	}

	@Override
	public <T> T accept(TypeVisitor<T> visitor) {
		return visitor.visit(this);
	}
}