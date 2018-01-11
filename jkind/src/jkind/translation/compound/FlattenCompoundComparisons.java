package jkind.translation.compound;

import static jkind.lustre.LustreUtil.not;

import java.util.List;

import jkind.lustre.ArrayType;
import jkind.lustre.BinaryExpr;
import jkind.lustre.BinaryOp;
import jkind.lustre.Expr;
import jkind.lustre.LustreUtil;
import jkind.lustre.Program;
import jkind.lustre.RecordType;
import jkind.lustre.TupleType;
import jkind.lustre.Type;
import jkind.lustre.visitors.TypeAwareAstMapVisitor;

/**
 * Expand equalities and inequalities on records and arrays
 */
public class FlattenCompoundComparisons extends TypeAwareAstMapVisitor {
	public static Program program(Program program) {
		return new FlattenCompoundComparisons().visit(program);
	}

	@Override
	public Expr visit(BinaryExpr e) {
		Expr left = e.left.accept(this);
		Expr right = e.right.accept(this);
		if (e.op == BinaryOp.EQUAL || e.op == BinaryOp.NOTEQUAL) {
			Type type = getType(e.left);
			if (type instanceof ArrayType || type instanceof RecordType || type instanceof TupleType) {
				List<ExprType> leftExprTypes = CompoundUtil.flattenExpr(left, type);
				List<ExprType> rightExprTypes = CompoundUtil.flattenExpr(right, type);

				List<Expr> leftExprs = CompoundUtil.mapExprs(leftExprTypes);
				List<Expr> rightExprs = CompoundUtil.mapExprs(rightExprTypes);

				List<Expr> exprs = CompoundUtil.mapBinary(BinaryOp.EQUAL, leftExprs, rightExprs);
				Expr equal = LustreUtil.and(exprs);
				if (e.op == BinaryOp.EQUAL) {
					return equal;
				} else {
					return not(equal);
				}
			}
		}

		return new BinaryExpr(e.location, left, e.op, right);
	}
}
