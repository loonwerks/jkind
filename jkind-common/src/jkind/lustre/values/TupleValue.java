package jkind.lustre.values;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import jkind.lustre.BinaryOp;
import jkind.lustre.UnaryOp;

/**
 * A tuple signal value (only used during static analysis)
 */
public class TupleValue extends Value {
	final public List<Value> elements;

	public TupleValue(List<Value> elements) {
		this.elements = Collections.unmodifiableList(elements);
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
		text.append("(");
		Iterator<Value> iterator = elements.iterator();
		text.append(iterator.next());
		while (iterator.hasNext()) {
			text.append(", ");
			text.append(iterator.next());
		}
		text.append(")");
		return text.toString();
	}

	@Override
	public int hashCode() {
		return elements.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof TupleValue) {
			TupleValue other = (TupleValue) obj;
			return elements.equals(other.elements);
		}
		return false;
	}
}
