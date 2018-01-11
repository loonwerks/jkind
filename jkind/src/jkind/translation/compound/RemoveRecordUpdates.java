package jkind.translation.compound;

import java.util.HashMap;
import java.util.Map;

import jkind.lustre.Expr;
import jkind.lustre.Program;
import jkind.lustre.RecordAccessExpr;
import jkind.lustre.RecordExpr;
import jkind.lustre.RecordType;
import jkind.lustre.RecordUpdateExpr;
import jkind.lustre.visitors.TypeAwareAstMapVisitor;

/**
 * Removes all record updates via expansion to full record expressions.
 * 
 * <code>
 * 	type record_type = struct { a : int; b : int; c : bool }; 
 * 	expr1 {a := expr2}
 * 
 * 		===> 
 * 
 * 	record_type {a = expr2; b = expr1.b; c = expr1.c};
 * </code>
 * 
 * Assumptions: Nodes are already inlined.
 * 
 * Guarantees: All record update expressions are removed
 */
public class RemoveRecordUpdates extends TypeAwareAstMapVisitor {
	public static Program program(Program program) {
		return new RemoveRecordUpdates().visit(program);
	}
	
	@Override
	public Expr visit(RecordUpdateExpr e) {
		Expr record = e.record.accept(this);
		Expr value = e.value.accept(this);

		RecordType rt = (RecordType) getType(record);
		Map<String, Expr> fields = new HashMap<>();

		for (String key : rt.fields.keySet()) {
			if (key.equals(e.field)) {
				fields.put(key, value);
			} else {
				fields.put(key, new RecordAccessExpr(record, key));
			}
		}
		return new RecordExpr(rt.id, fields);
	}
}
