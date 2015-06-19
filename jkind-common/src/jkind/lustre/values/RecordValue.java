package jkind.lustre.values;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.StringJoiner;
import java.util.TreeMap;

import jkind.lustre.BinaryOp;
import jkind.lustre.UnaryOp;

/**
 * A record signal value (only used during static analysis)
 */
public class RecordValue extends Value {
	public final SortedMap<String, Value> fields;

	public RecordValue(Map<String, Value> fields) {
		this.fields = Collections.unmodifiableSortedMap(new TreeMap<>(fields));
	}

	public Value update(String field, Value value) {
		Map<String, Value> copy = new HashMap<>(fields);
		copy.put(field, value);
		return new RecordValue(copy);
	}

	@Override
	public Value applyBinaryOp(BinaryOp op, Value right) {
		return null;
	}

	@Override
	public Value applyUnaryOp(UnaryOp op) {
		return null;
	}

	@Override
	public String toString() {
		StringJoiner text = new StringJoiner("; ", "{", "}");
		fields.forEach((field, value) -> text.add(field + " = " + value));
		return text.toString();
	}

	@Override
	public int hashCode() {
		return fields.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof RecordValue) {
			RecordValue other = (RecordValue) obj;
			return fields.equals(other.fields);
		}
		return false;
	}
}
