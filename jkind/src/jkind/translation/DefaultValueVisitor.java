package jkind.translation;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jkind.lustre.ArrayExpr;
import jkind.lustre.ArrayType;
import jkind.lustre.BoolExpr;
import jkind.lustre.EnumType;
import jkind.lustre.Expr;
import jkind.lustre.IntExpr;
import jkind.lustre.NamedType;
import jkind.lustre.RealExpr;
import jkind.lustre.RecordExpr;
import jkind.lustre.RecordType;
import jkind.lustre.SubrangeIntType;
import jkind.lustre.TupleExpr;
import jkind.lustre.TupleType;
import jkind.lustre.Type;
import jkind.lustre.visitors.TypeVisitor;

public class DefaultValueVisitor implements TypeVisitor<Expr> {
	@Override
	public Expr visit(ArrayType e) {
		List<Expr> elements = new ArrayList<>();
		Expr baseValue = e.base.accept(this);
		for (int i = 0; i < e.size; i++) {
			elements.add(baseValue);
		}
		return new ArrayExpr(elements);
	}

	@Override
	public Expr visit(EnumType e) {
		// Enums are already inlined
		return new IntExpr(0);
	}

	@Override
	public Expr visit(NamedType e) {
		if (e == NamedType.BOOL) {
			return new BoolExpr(false);
		} else if (e == NamedType.INT) {
			return new IntExpr(BigInteger.ZERO);
		} else if (e == NamedType.REAL) {
			return new RealExpr(BigDecimal.ZERO);
		} else {
			throw new IllegalArgumentException();
		}
	}

	@Override
	public Expr visit(RecordType e) {
		Map<String, Expr> fields = new HashMap<>();
		for (String key : e.fields.keySet()) {
			fields.put(key, e.fields.get(key).accept(this));
		}
		return new RecordExpr(e.id, fields);
	}

	@Override
	public Expr visit(TupleType e) {
		List<Expr> elements = new ArrayList<>();
		for (Type t : e.types) {
			elements.add(t.accept(this));
		}
		return new TupleExpr(elements);
	}

	@Override
	public Expr visit(SubrangeIntType e) {
		return new IntExpr(e.low);
	}
}
