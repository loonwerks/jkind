package jkind.translation.compound;

import java.util.HashMap;
import java.util.Map;

import jkind.analysis.TypeReconstructor;
import jkind.lustre.Expr;
import jkind.lustre.Node;
import jkind.lustre.RecordAccessExpr;
import jkind.lustre.RecordExpr;
import jkind.lustre.RecordType;
import jkind.lustre.RecordUpdateExpr;
import jkind.lustre.visitors.ExprMapVisitor;

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
public class RemoveRecordUpdates extends ExprMapVisitor {
	public static Node node(Node node) {
		return new RemoveRecordUpdates().visitNode(node);
	}

	private final TypeReconstructor typeReconstructor = new TypeReconstructor();

	@Override
	public Node visitNode(Node node) {
		typeReconstructor.setNodeContext(node);
		return super.visitNode(node);
	}

	private RecordType getRecordType(Expr e) {
		return (RecordType) e.accept(typeReconstructor);
	}

	@Override
	public Expr visit(RecordUpdateExpr e) {
		Expr record = e.record.accept(this);
		Expr value = e.value.accept(this);

		RecordType rt = getRecordType(record);
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
