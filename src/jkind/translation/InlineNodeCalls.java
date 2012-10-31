package jkind.translation;

import java.util.ArrayList;
import java.util.List;

import jkind.lustre.Equation;
import jkind.lustre.Node;
import jkind.lustre.Program;
import jkind.lustre.VarDecl;

public class InlineNodeCalls {
	public static Node program(Program program) {
		InlineNodeCallVisitor inliner = new InlineNodeCallVisitor(Util.getNodeTable(program.nodes));
		Node main = program.main;

		List<Equation> equations = new ArrayList<Equation>();
		for (Equation eq : main.equations) {
			equations.add(new Equation(eq.location, eq.id, eq.expr.accept(inliner)));
		}
		
		List<VarDecl> locals = new ArrayList<VarDecl>(main.locals);
		locals.addAll(inliner.getNewLocals());
		equations.addAll(inliner.getNewEquations());

		return new Node(main.location, main.id, main.inputs, main.outputs, locals, equations,
				main.properties);
	}
}
