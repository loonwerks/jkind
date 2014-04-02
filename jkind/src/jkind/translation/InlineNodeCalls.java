package jkind.translation;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import jkind.lustre.CallExpr;
import jkind.lustre.Equation;
import jkind.lustre.Expr;
import jkind.lustre.IdExpr;
import jkind.lustre.InlinedProgram;
import jkind.lustre.Node;
import jkind.lustre.Program;
import jkind.lustre.TupleExpr;
import jkind.lustre.VarDecl;
import jkind.lustre.visitors.ExprMapVisitor;
import jkind.util.Util;

public class InlineNodeCalls extends ExprMapVisitor {
	public static InlinedProgram program(Program program) {
		InlineNodeCalls inliner = new InlineNodeCalls(Util.getNodeTable(program.nodes));
		Node main = program.getMainNode();

		List<Expr> assertions = inliner.visitAssertions(main.assertions);
		List<Equation> equations = inliner.visitEquations(main.equations);

		List<VarDecl> locals = new ArrayList<>(main.locals);
		locals.addAll(inliner.newLocals);
		List<String> properties = new ArrayList<>(main.properties);
		properties.addAll(inliner.newProperties);

		Node node = new Node(main.location, main.id, main.inputs, main.outputs, locals, equations,
				properties, assertions);
		return new InlinedProgram(program.functions, node);
	}

	private final Map<String, Node> nodeTable;
	private final List<VarDecl> newLocals = new ArrayList<>();
	private final List<String> newProperties = new ArrayList<>();
	private final Map<String, Integer> usedPrefixes = new HashMap<>();
	private final Queue<Equation> queue = new ArrayDeque<>();

	private InlineNodeCalls(Map<String, Node> nodeTable) {
		this.nodeTable = nodeTable;
	}

	private List<Expr> visitAssertions(List<Expr> assertions) {
		List<Expr> result = new ArrayList<>();
		for (Expr assertion : assertions) {
			result.add(assertion.accept(this));
		}
		return result;
	}

	private List<Equation> visitEquations(List<Equation> equations) {
		queue.addAll(equations);
		List<Equation> result = new ArrayList<>();

		while (!queue.isEmpty()) {
			Equation eq = queue.poll();
			result.add(new Equation(eq.location, eq.lhs, eq.expr.accept(this)));
		}
		return result;
	}

	@Override
	public Expr visit(CallExpr e) {
		if (isNodeCall(e)) {
			return TupleExpr.compress(visitNodeCallExpr(e));
		} else {
			return super.visit(e);
		}
	}

	public List<IdExpr> visitNodeCallExpr(CallExpr e) {
		String prefix = newPrefix(e.name);
		Node node = nodeTable.get(originalName(e));

		Map<String, IdExpr> translation = getTranslation(prefix, node);

		createInputEquations(node.inputs, e.args, translation);
		createAssignmentEquations(prefix, node.equations, translation);
		accumulateProperties(node.properties, translation);

		List<IdExpr> result = new ArrayList<>();
		for (VarDecl decl : node.outputs) {
			result.add(translation.get(decl.id));
		}
		return result;
	}

	private boolean isNodeCall(CallExpr e) {
		return nodeTable.containsKey(originalName(e));
	}

	private String originalName(CallExpr e) {
		return e.name.substring(e.name.lastIndexOf('.') + 1);
	}

	private Map<String, IdExpr> getTranslation(String prefix, Node node) {
		Map<String, IdExpr> translation = new HashMap<>();
		for (VarDecl decl : Util.getVarDecls(node)) {
			String id = prefix + decl.id;
			newLocals.add(new VarDecl(id, decl.type));
			translation.put(decl.id, new IdExpr(id));
		}
		return translation;
	}

	private void createInputEquations(List<VarDecl> inputs, List<Expr> args,
			Map<String, IdExpr> translation) {
		for (int i = 0; i < inputs.size(); i++) {
			IdExpr idExpr = translation.get(inputs.get(i).id);
			Expr arg = args.get(i);
			queue.add(new Equation(idExpr, arg));
		}
	}

	private void createAssignmentEquations(final String prefix, List<Equation> equations,
			Map<String, IdExpr> translation) {
		SubstitutionVisitor substitution = new SubstitutionVisitor(translation) {
			@Override
			public Expr visit(CallExpr e) {
				if (isNodeCall(e)) {
					return new CallExpr(e.location, prefix + e.name, visitAll(e.args));
				} else {
					return super.visit(e);
				}
			}
		};

		for (Equation eq : equations) {
			List<IdExpr> lhs = new ArrayList<>();
			for (IdExpr idExpr : eq.lhs) {
				lhs.add(translation.get(idExpr.id));
			}
			Expr expr = eq.expr.accept(substitution);
			queue.add(new Equation(eq.location, lhs, expr));
		}
	}

	private String newPrefix(String prefix) {
		int i = 0;
		if (usedPrefixes.containsKey(prefix)) {
			i = usedPrefixes.get(prefix);
		}
		usedPrefixes.put(prefix, i + 1);
		return prefix + "~" + i + ".";
	}

	private void accumulateProperties(List<String> properties, Map<String, IdExpr> translation) {
		for (String property : properties) {
			newProperties.add(translation.get(property).id);
		}
	}
}
