package jkind.util;

import java.math.BigInteger;

import jkind.lustre.ArrayType;
import jkind.lustre.EnumType;
import jkind.lustre.NamedType;
import jkind.lustre.RecordType;
import jkind.lustre.SubrangeIntType;
import jkind.lustre.TupleType;
import jkind.lustre.values.BooleanValue;
import jkind.lustre.values.IntegerValue;
import jkind.lustre.values.RealValue;
import jkind.lustre.values.Value;
import jkind.lustre.visitors.TypeVisitor;

public class DefaultValueVisitor implements TypeVisitor<Value> {
	@Override
	public Value visit(ArrayType e) {
		throw new IllegalArgumentException();
	}

	@Override
	public Value visit(EnumType e) {
		return new IntegerValue(BigInteger.ZERO);
	}

	@Override
	public Value visit(NamedType e) {
		switch (e.name) {
		case "bool":
			return BooleanValue.FALSE;
		case "int":
			return new IntegerValue(BigInteger.ZERO);
		case "real":
			return new RealValue(BigFraction.ZERO);
		default:
			throw new IllegalArgumentException("Unable to create default value for type: " + e.name);
		}
	}

	@Override
	public Value visit(RecordType e) {
		throw new IllegalArgumentException();
	}

	@Override
	public Value visit(TupleType e) {
		throw new IllegalArgumentException();
	}

	@Override
	public Value visit(SubrangeIntType e) {
		return new IntegerValue(e.low);
	}
}
