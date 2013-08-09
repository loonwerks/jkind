package jkind.lustre.values;

import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;

import jkind.lustre.BinaryOp;
import jkind.lustre.UnaryOp;

/**
 * A record signal value (only used during static analysis)
 */
public class RecordValue extends Value {
	final public SortedMap<String, Value> fields;

	public RecordValue(Map<String, Value> fields) {
		this.fields = Collections.unmodifiableSortedMap(new TreeMap<>(fields));
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
		StringBuilder text = new StringBuilder();
		text.append("{");
		Iterator<Entry<String, Value>> iterator = fields.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<String, Value> entry = iterator.next();
			text.append(entry.getKey());
			text.append(" = ");
			text.append(entry.getValue());
			if (iterator.hasNext()) {
				text.append("; ");
			}
		}
		return text.toString();
	}
}
