package jkind.translation.compound;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import jkind.lustre.ArrayAccessExpr;
import jkind.lustre.ArrayType;
import jkind.lustre.BinaryExpr;
import jkind.lustre.BinaryOp;
import jkind.lustre.BoolExpr;
import jkind.lustre.Expr;
import jkind.lustre.RecordAccessExpr;
import jkind.lustre.RecordType;
import jkind.lustre.Type;

public class CompoundUtil {
	public static List<ExprType> flattenExpr(Expr expr, Type type) {
		if (type instanceof ArrayType) {
			ArrayType arrayType = (ArrayType) type;
			return flattenArrayExpr(expr, arrayType.base, arrayType.size);
		} else if (type instanceof RecordType) {
			RecordType recordType = (RecordType) type;
			return flattenRecordExpr(expr, recordType.fields);
		} else {
			return Collections.singletonList(new ExprType(expr, type));
		}
	}

	private static List<ExprType> flattenArrayExpr(Expr expr, Type base, int size) {
		List<ExprType> result = new ArrayList<>();
		for (int i = 0; i < size; i++) {
			result.addAll(flattenExpr(new ArrayAccessExpr(expr, i), base));
		}
		return result;
	}

	private static List<ExprType> flattenRecordExpr(Expr expr, Map<String, Type> fields) {
		List<ExprType> result = new ArrayList<>();
		for (Entry<String, Type> entry : fields.entrySet()) {
			result.addAll(flattenExpr(new RecordAccessExpr(expr, entry.getKey()), entry.getValue()));
		}
		return result;
	}

	public static Expr conjoin(List<Expr> exprs) {
		if (exprs.isEmpty()) {
			return new BoolExpr(true);
		}
		
		Iterator<Expr> iterator = exprs.iterator();
		Expr result = iterator.next();
		while (iterator.hasNext()) {
			result = new BinaryExpr(result, BinaryOp.AND, iterator.next());
		}
		return result;
	}

	public static List<Expr> mapExprs(List<ExprType> ets) {
		List<Expr> result = new ArrayList<>();
		for (ExprType et : ets) {
			result.add(et.expr);
		}
		return result;
	}

	public static List<Expr> mapBinary(BinaryOp op, List<Expr> exprs1, List<Expr> exprs2) {
		List<Expr> result = new ArrayList<>();
		for (int i = 0; i < exprs1.size(); i++) {
			result.add(new BinaryExpr(exprs1.get(i), op, exprs2.get(i)));
		}
		return result;
	}
}
