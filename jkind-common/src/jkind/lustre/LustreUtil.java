package jkind.lustre;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

public class LustreUtil {
	public static Expr typeConstraint(String id, Type type) {
		if (type instanceof SubrangeIntType) {
			return subrangeConstraint(id, (SubrangeIntType) type);
		} else if (type instanceof EnumType) {
			return enumConstraint(id, (EnumType) type);
		} else {
			return null;
		}
	}

	public static Expr subrangeConstraint(String id, SubrangeIntType subrange) {
		return boundConstraint(id, new IntExpr(subrange.low), new IntExpr(subrange.high));
	}

	public static Expr enumConstraint(String id, EnumType et) {
		return boundConstraint(id, new IntExpr(0), new IntExpr(et.values.size() - 1));
	}

	private static Expr boundConstraint(String id, Expr low, Expr high) {
		return and(lessEqual(low, id(id)), lessEqual(id(id), high));
	}

	public static Expr and(Expr left, Expr right) {
		return new BinaryExpr(left, BinaryOp.AND, right);
	}

	public static Expr or(Expr left, Expr right) {
		return new BinaryExpr(left, BinaryOp.OR, right);
	}

	public static Expr lessEqual(Expr left, Expr right) {
		return new BinaryExpr(left, BinaryOp.LESSEQUAL, right);
	}

	public static Expr notEqual(Expr left, Expr right) {
		return new BinaryExpr(left, BinaryOp.NOTEQUAL, right);
	}

	public static Expr divide(Expr left, Expr right) {
		return new BinaryExpr(left, BinaryOp.DIVIDE, right);
	}

	public static Expr negative(Expr expr) {
		return new UnaryExpr(UnaryOp.NEGATIVE, expr);
	}

	public static Expr not(Expr expr) {
		return new UnaryExpr(UnaryOp.NOT, expr);
	}

	public static IdExpr id(String id) {
		return new IdExpr(id);
	}

	public static RealExpr real(BigInteger bi) {
		return new RealExpr(new BigDecimal(bi));
	}
	
	public static Expr and(List<Expr> conjuncts) {
		return conjuncts.stream().reduce((acc, e) -> and(acc, e)).orElse(new BoolExpr(true));
	}
}
