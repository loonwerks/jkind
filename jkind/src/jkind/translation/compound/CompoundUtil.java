package jkind.translation.compound;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import jkind.lustre.ArrayAccessExpr;
import jkind.lustre.ArrayType;
import jkind.lustre.BinaryExpr;
import jkind.lustre.BinaryOp;
import jkind.lustre.Expr;
import jkind.lustre.IdExpr;
import jkind.lustre.RecordAccessExpr;
import jkind.lustre.RecordType;
import jkind.lustre.Type;
import jkind.lustre.VarDecl;

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

	public static List<VarDecl> flattenVarDecls(List<VarDecl> varDecls) {
		List<VarDecl> result = new ArrayList<>();
		for (VarDecl varDecl : varDecls) {
			IdExpr id = new IdExpr(varDecl.id);
			for (ExprType et : flattenExpr(id, varDecl.type)) {
				result.add(new VarDecl(et.expr.toString(), et.type));
			}
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
