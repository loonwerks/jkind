package jkind.translation;

import jkind.analysis.TypeReconstructor;
import jkind.lustre.Expr;
import jkind.lustre.Node;
import jkind.lustre.Program;
import jkind.lustre.Type;
import jkind.lustre.visitors.AstMapVisitor;

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
}
