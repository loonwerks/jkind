package jkind.translation;

import java.util.ArrayList;
import java.util.List;

import jkind.lustre.Equation;
import jkind.lustre.Expr;
import jkind.lustre.IdExpr;
import jkind.lustre.Node;
import jkind.lustre.UnaryExpr;
import jkind.lustre.UnaryOp;
import jkind.lustre.VarDecl;

public class FlattenPres extends TypeAwareAstMapVisitor {
	public static Node node(Node node) {
		return new FlattenPres().visit(node);
	}

	private List<VarDecl> newLocals = new ArrayList<>();
	private List<Equation> newEquations = new ArrayList<>();
	private int counter = 0;

	@Override
	public Node visit(Node e) {
		List<VarDecl> locals = new ArrayList<>(visitVarDecls(e.locals));
		List<Equation> equations = new ArrayList<>(visitEquations(e.equations));
		List<Expr> assertions = visitAssertions(e.assertions);

		locals.addAll(newLocals);
		equations.addAll(newEquations);

		return new Node(e.location, e.id, e.inputs, e.outputs, locals, equations, e.properties,
				assertions);
	}

	private IdExpr getFreshId() {
		return new IdExpr("%flatten" + counter++);
	}

	@Override
	public Expr visit(UnaryExpr e) {
		if (e.op == UnaryOp.PRE && ContainsTemporalOperator.check(e.expr)) {
			Expr nested = e.expr.accept(this);
			IdExpr id = getFreshId();
			newLocals.add(new VarDecl(id.id, getType(e.expr)));
			newEquations.add(new Equation(id, nested));
			return new UnaryExpr(e.op, id);
		} else {
			return super.visit(e);
		}
	}
}
