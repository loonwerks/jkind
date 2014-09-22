package jkind.results;

import java.util.Comparator;
import java.util.List;

import jkind.lustre.values.BooleanValue;
import jkind.lustre.values.EnumValue;
import jkind.lustre.values.IntegerValue;
import jkind.lustre.values.RealValue;
import jkind.lustre.values.Value;

public class ListValueComparator implements Comparator<List<Value>> {
	@Override
	public int compare(List<Value> values1, List<Value> values2) {
		if (values1.size() != values2.size()) {
			throw new IllegalArgumentException();
		}

		for (int i = 0; i < values1.size(); i++) {
			int c = compare(values1.get(i), values2.get(i));
			if (c != 0) {
				return c;
			}
		}

		return 0;
	}

	private int compare(Value value1, Value value2) {
		if (value1 instanceof BooleanValue && value2 instanceof BooleanValue) {
			BooleanValue bv1 = (BooleanValue) value1;
			BooleanValue bv2 = (BooleanValue) value2;
			return Boolean.compare(bv1.value, bv2.value);
		} else if (value1 instanceof EnumValue && value2 instanceof EnumValue) {
			EnumValue ev1 = (EnumValue) value1;
			EnumValue ev2 = (EnumValue) value2;
			return ev1.value.compareTo(ev2.value);
		} else if (value1 instanceof IntegerValue && value2 instanceof IntegerValue) {
			IntegerValue iv1 = (IntegerValue) value1;
			IntegerValue iv2 = (IntegerValue) value2;
			return iv1.value.compareTo(iv2.value);
		} else if (value1 instanceof RealValue && value2 instanceof RealValue) {
			RealValue rv1 = (RealValue) value1;
			RealValue rv2 = (RealValue) value2;
			return rv1.value.compareTo(rv2.value);
		}

		throw new IllegalArgumentException("Unsupported value type for comparison: " + value1.getClass().getName());
	}
}
