package jkind.faultseeder;

import java.math.BigDecimal;
import java.math.BigInteger;

import jkind.lustre.ArrayType;
import jkind.lustre.BoolExpr;
import jkind.lustre.EnumType;
import jkind.lustre.Expr;
import jkind.lustre.IntExpr;
import jkind.lustre.NamedType;
import jkind.lustre.RealExpr;
import jkind.lustre.RecordType;
import jkind.lustre.SubrangeIntType;
import jkind.lustre.TupleType;
import jkind.lustre.visitors.TypeVisitor;

public class DefaultValueExprVisitor implements TypeVisitor<Expr> {
	@Override
	public Expr visit(ArrayType e) {
		throw new IllegalArgumentException();
	}

	@Override
	public Expr visit(EnumType e) {
		return new IntExpr(BigInteger.ZERO);
	}

	@Override
	public Expr visit(NamedType e) {
		switch (e.name) {
		case "bool":
			return new BoolExpr(false); 
		case "int":
			return new IntExpr(BigInteger.ZERO);
		case "real":
			return new RealExpr(BigDecimal.ZERO);
		default:
			throw new IllegalArgumentException("Unable to create default value for type: " + e.name);
		}
	}

	@Override
	public Expr visit(RecordType e) {
		throw new IllegalArgumentException();
	}

	@Override
	public Expr visit(TupleType e) {
		throw new IllegalArgumentException();
	}

	@Override
	public Expr visit(SubrangeIntType e) {
		return new IntExpr(e.low);
	}
}
