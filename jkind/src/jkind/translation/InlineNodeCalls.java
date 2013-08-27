package jkind.translation;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import jkind.lustre.Equation;
import jkind.lustre.Expr;
import jkind.lustre.IdExpr;
import jkind.lustre.MapVisitor;
import jkind.lustre.Node;
import jkind.lustre.NodeCallExpr;
import jkind.lustre.Program;
import jkind.lustre.VarDecl;
import jkind.util.Util;

public class InlineNodeCalls extends MapVisitor {
	public static Node program(Program program) {
		InlineNodeCalls inliner = new InlineNodeCalls(Util.getNodeTable(program.nodes));
		Node main = program.getMainNode();

		List<Expr> assertions = inliner.visitAssertions(main.assertions);
		List<Equation> equations = inliner.visitEquations(main.equations);
		
		List<VarDecl> locals = new ArrayList<>(main.locals);
		locals.addAll(inliner.newLocals);
		List<String> properties = new ArrayList<>(main.properties);
		properties.addAll(inliner.newProperties);

		return new Node(main.location, main.id, main.inputs, main.outputs, locals, equations,
				properties, assertions);
	}

	private final Map<String, Node> nodeTable;
	private final List<VarDecl> newLocals = new ArrayList<>();
	private final List<String> newProperties = new ArrayList<>();
	private final Map<String, Integer> usedNames = new HashMap<>();
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
			if (eq.lhs.size() == 1) {
				result.add(new Equation(eq.location, eq.lhs, eq.expr.accept(this)));
			} else {
				List<IdExpr> outputs = visitNodeCallExpr((NodeCallExpr) eq.expr);
				for (int i = 0; i < eq.lhs.size(); i++) {
					result.add(new Equation(eq.lhs.get(i), outputs.get(i)));
				}
			}
		}

		return result;
	}

	@Override
	public Expr visit(NodeCallExpr e) {
		List<IdExpr> result = visitNodeCallExpr(e);

		if (result.size() == 1) {
			return result.get(0);
		} else {
			throw new IllegalArgumentException("Multi-return node calls should already be inlined");
		}
	}

	public List<IdExpr> visitNodeCallExpr(NodeCallExpr e) {
		Node node = nodeTable.get(e.node);
		Map<String, IdExpr> translation = getTranslation(node);

		createInputEquations(node.inputs, e.args, translation);
		createAssignmentEquations(node.equations, translation);
		accumulateProperties(node.properties, translation);

		List<IdExpr> result = new ArrayList<>();
		for (VarDecl decl : node.outputs) {
			result.add(translation.get(decl.id));
		}
		return result;
	}

	private Map<String, IdExpr> getTranslation(Node node) {
		Map<String, IdExpr> translation = new HashMap<>();
		for (VarDecl decl : Util.getVarDecls(node)) {
			translation.put(decl.id, newVar(node.id, decl));
		}
		return translation;
	}

	private IdExpr newVar(String nodeName, VarDecl decl) {
		VarDecl newDecl = new VarDecl(decl.location, newName(nodeName, decl.id), decl.type);
		newLocals.add(newDecl);
		return new IdExpr(newDecl.id);
	}

	private String newName(String node, String id) {
		String base = node + "." + id;
		int i = 0;
		if (usedNames.containsKey(base)) {
			i = usedNames.get(base);
		}
		usedNames.put(base, i + 1);
		return node + "~" + i + "." + id;
	}

	private void createInputEquations(List<VarDecl> inputs, List<Expr> args,
			Map<String, IdExpr> translation) {
		for (int i = 0; i < inputs.size(); i++) {
			IdExpr idExpr = translation.get(inputs.get(i).id);
			Expr arg = args.get(i);
			queue.add(new Equation(idExpr, arg));
		}
	}

	private void createAssignmentEquations(List<Equation> equations, Map<String, IdExpr> translation) {
		SubstitutionVisitor substitution = new SubstitutionVisitor(translation);
		for (Equation eq : equations) {
			List<IdExpr> lhs = new ArrayList<>();
			for (IdExpr idExpr : eq.lhs) {
				lhs.add(translation.get(idExpr.id));
			}
			Expr expr = eq.expr.accept(substitution);
			queue.add(new Equation(eq.location, lhs, expr));
		}
	}

	private void accumulateProperties(List<String> properties, Map<String, IdExpr> translation) {
		for (String property : properties) {
			newProperties.add(translation.get(property).id);
		}
	}
}
