package jkind.lustre.values;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringJoiner;

import jkind.lustre.BinaryOp;
import jkind.lustre.UnaryOp;

/**
 * An array signal value (only used during static analysis)
 */
public class ArrayValue extends Value {
	public final List<Value> elements;

	public ArrayValue(List<Value> elements) {
		this.elements = Collections.unmodifiableList(elements);
	}

	public ArrayValue update(BigInteger index, Value value) {
		if (validIndex(index)) {
			List<Value> copy = new ArrayList<>(elements);
			copy.set(index.intValue(), value);
			return new ArrayValue(copy);
		} else {
			return this;
		}
	}

	public Value get(BigInteger index) {
		if (validIndex(index)) {
			return elements.get(index.intValue());
		}
		return null;
	}

	private boolean validIndex(BigInteger index) {
		return BigInteger.ZERO.compareTo(index) <= 0
				&& index.compareTo(BigInteger.valueOf(elements.size())) < 0;
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
		StringJoiner text = new StringJoiner(", ", "[", "]");
		elements.forEach(v -> text.add(v.toString()));
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
