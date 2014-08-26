package jkind.api;

import java.util.ArrayList;
import java.util.List;

import jkind.lustre.Equation;
import jkind.lustre.Expr;
import jkind.lustre.IdExpr;
import jkind.lustre.Node;
import jkind.lustre.Program;
import jkind.lustre.RecordAccessExpr;
import jkind.lustre.VarDecl;
import jkind.lustre.builders.NodeBuilder;
import jkind.lustre.visitors.TypeAwareAstMapVisitor;

/**
 * Kind2 currently doesn't support arbitrary expressions on the left hand side
 * of a record access. See:
 * 
 * https://github.com/kind2-mc/kind2/issues/122
 * 
 * We work around this by creating intermediate variables for record accesses.
 */
public class WorkaroundKind2RecordAccess extends TypeAwareAstMapVisitor {
	public static Program program(Program program) {
		return new WorkaroundKind2RecordAccess().visit(program);
	}

	private List<VarDecl> newLocals = new ArrayList<>();
	private List<Equation> newEquations = new ArrayList<>();

	@Override
	public Node visit(Node e) {
		NodeBuilder builder = new NodeBuilder(super.visit(e));
		builder.addLocals(newLocals);
		builder.addEquations(newEquations);
		return builder.build();
	}

	@Override
	public Expr visit(RecordAccessExpr e) {
		if (e.record instanceof IdExpr) {
			return new RecordAccessExpr(e.location, e.record, e.field);
		} else {
			IdExpr id = freshId();
			newLocals.add(new VarDecl(id.id, getType(e.record)));
			newEquations.add(new Equation(id, e.record.accept(this)));
			return new RecordAccessExpr(e.location, id, e.field);
		}
	}

	private static final String PREFIX = "___kind2_record_access_workaround_";
	private int counter = 0;

	private IdExpr freshId() {
		return new IdExpr(PREFIX + counter++);
	}
}
