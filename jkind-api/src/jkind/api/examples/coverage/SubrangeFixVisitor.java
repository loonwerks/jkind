package jkind.api.examples.coverage;

import java.util.Map;

import jkind.lustre.Node;
import jkind.lustre.Program;
import jkind.lustre.Type;
import jkind.lustre.VarDecl;
import jkind.lustre.visitors.AstMapVisitor;

public class SubrangeFixVisitor extends AstMapVisitor {
	private Map<String, Type> env;

	public static Program fix(Program program) {
		return (Program) program.accept(new SubrangeFixVisitor());
	}

	@Override
	public Node visit(Node e) {
		env = new MarkSubrangesVisitor().mark(e);
		return super.visit(e);
	}

	@Override
	public VarDecl visit(VarDecl e) {
		return new VarDecl(e.location, e.id, env.get(e.id));
	}
}
