package jkind.lustre.values;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import jkind.lustre.BinaryOp;
import jkind.lustre.UnaryOp;

/**
 * An array signal value (only used during static analysis)
 */
public class ArrayValue extends Value {
	final public List<Value> elements;

	public ArrayValue(List<Value> elements) {
		this.elements = Collections.unmodifiableList(elements);
	}

	public ArrayValue update(int index, Value value) {
		if (validIndex(index)) {
			List<Value> copy = new ArrayList<>(elements);
			copy.set(index, value);
			return new ArrayValue(copy);
		} else {
			return this;
		}
	}

	public Value get(int index) {
		if (validIndex(index)) {
			return elements.get(index);
		}
		return null;
	}

	private boolean validIndex(int index) {
		return 0 <= index && index < elements.size();
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
		text.append("[");
		Iterator<Value> iterator = elements.iterator();
		text.append(iterator.next());
		while (iterator.hasNext()) {
			text.append(", ");
			text.append(iterator.next());
		}
		text.append("]");
		return text.toString();
	}

	@Override
	public int hashCode() {
		return elements.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ArrayValue) {
			ArrayValue other = (ArrayValue) obj;
			return elements.equals(other.elements);
		}
		return false;
	}
}
