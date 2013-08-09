package jkind.lustre;

import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class RecordType extends Type {
	final public Location location;
	final public SortedMap<String, Type> fields;

	public RecordType(Location location, Map<String, Type> fields) {
		this.location = location;
		this.fields = Collections.unmodifiableSortedMap(new TreeMap<>(fields));
	}

	public RecordType(Map<String, Type> fields) {
		this(Location.NULL, fields);
	}

	@Override
	public String toString() {
		StringBuilder text = new StringBuilder();
		text.append("{");
		Iterator<String> iterator = fields.keySet().iterator();
		while (iterator.hasNext()) {
			String field = iterator.next();
			text.append(field);
			text.append(" = ");
			text.append(fields.get(field));
			if (iterator.hasNext()) {
				text.append("; ");
			}
		}
		text.append("}");
		return text.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fields == null) ? 0 : fields.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof RecordType)) {
			return false;
		}
		RecordType other = (RecordType) obj;
		if (fields == null) {
			if (other.fields != null) {
				return false;
			}
		} else if (!fields.equals(other.fields)) {
			return false;
		}
		return true;
	}
}