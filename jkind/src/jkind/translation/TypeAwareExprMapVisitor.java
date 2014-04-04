package jkind.translation;

import jkind.analysis.TypeReconstructor;
import jkind.lustre.Expr;
import jkind.lustre.Node;
import jkind.lustre.Type;
import jkind.lustre.visitors.ExprMapVisitor;

public class TypeAwareExprMapVisitor extends ExprMapVisitor {
	private final TypeReconstructor typeReconstructor = new TypeReconstructor();
	
	protected Type getType(Expr e) {
		return e.accept(typeReconstructor);
	}
	
	@Override
	public Node visitNode(Node node) {
		typeReconstructor.setNodeContext(node);
		return super.visitNode(node);
	}
}
