package jkind.lustre;

import java.util.Map;
import java.util.SortedMap;

import jkind.Assert;
import jkind.lustre.visitors.TypeVisitor;
import jkind.util.Util;

public class RecordType extends Type {
	public final String id;
	public final SortedMap<String, Type> fields;

	public RecordType(Location location, String id, Map<String, Type> fields) {
		super(location);
		Assert.isNotNull(id);
		Assert.isNotNull(fields);
		Assert.isTrue(fields.size() > 0);
		this.id = id;
		this.fields = Util.safeStringSortedMap(fields);
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