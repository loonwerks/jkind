package jkind.lustre.visitors;

import jkind.lustre.Expr;
import jkind.lustre.NamedType;
import jkind.lustre.Node;
import jkind.lustre.Program;
import jkind.lustre.QuantExpr;
import jkind.lustre.Type;

public class TypeAwareAstMapVisitor extends AstMapVisitor {
	private TypeReconstructor typeReconstructor = new TypeReconstructor();

	protected Type getType(Expr e) {
		return e.accept(typeReconstructor);
	}

	@Override
	public Program visit(Program e) {
		typeReconstructor = new TypeReconstructor(e);
		return super.visit(e);
	}

	@Override
	public Node visit(Node e) {
		typeReconstructor.setNodeContext(e);
		return super.visit(e);
	}
	
	@Override
	public Expr visit(QuantExpr e) {
		typeReconstructor.addVariables(e.boundVars);
		e.expr.accept(this);
		return super.visit(e);
	}
}
