package jkind.analysis;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jkind.lustre.Equation;
import jkind.lustre.Expr;
import jkind.lustre.Node;
import jkind.lustre.NodeCallExpr;
import jkind.lustre.Program;
import jkind.lustre.visitors.ExprIterVisitor;

public class NodeDependencyChecker extends CycleChecker {
	public static boolean check(Program program) {
		return new NodeDependencyChecker().check(program.nodes);
	}

	protected boolean check(List<Node> nodes) {
		for (Node node : nodes) {
			dependencies.put(node.id, getNodeDependencies(node));
		}

		return super.checkDependencies();
	}

	private static Set<String> getNodeDependencies(Node node) {
		final Set<String> dependencies = new HashSet<>();
		ExprIterVisitor nodeCallCollector = new ExprIterVisitor() {
			@Override
			public Void visit(NodeCallExpr e) {
				dependencies.add(e.node);
				super.visit(e);
				return null;
			}
		};

		for (Equation eq : node.equations) {
			eq.expr.accept(nodeCallCollector);
		}
		for (Expr e : node.assertions) {
			e.accept(nodeCallCollector);
		}
		return dependencies;
	}
	
	@Override
	protected String getError() {
		return "cyclic node calls";
	}
}
