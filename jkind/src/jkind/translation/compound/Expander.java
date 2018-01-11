package jkind.translation.compound;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import jkind.lustre.ArrayAccessExpr;
import jkind.lustre.ArrayExpr;
import jkind.lustre.ArrayType;
import jkind.lustre.Expr;
import jkind.lustre.RecordAccessExpr;
import jkind.lustre.RecordExpr;
import jkind.lustre.RecordType;
import jkind.lustre.Type;

/**
 * This utility expands an expression with a complex type to corresponding
 * complex literals with explicit accesses.
 */
public abstract class Expander {
	public Expr expand(Expr expr, Type type) {
		if (type instanceof ArrayType) {
			ArrayType arrayType = (ArrayType) type;
			List<Expr> elements = new ArrayList<>();
			for (int i = 0; i < arrayType.size; i++) {
				elements.add(expand(new ArrayAccessExpr(expr, i), arrayType.base));
			}
			return new ArrayExpr(elements);
		} else if (type instanceof RecordType) {
			RecordType recordType = (RecordType) type;
			Map<String, Expr> fields = new HashMap<>();
			for (Entry<String, Type> entry : recordType.fields.entrySet()) {
				String field = entry.getKey();
				Type fieldType = entry.getValue();
				fields.put(field, expand(new RecordAccessExpr(expr, field), fieldType));
			}
			return new RecordExpr(recordType.id, fields);
		} else {
			return baseCase(expr);
		}
	}

	abstract protected Expr baseCase(Expr expr);
}