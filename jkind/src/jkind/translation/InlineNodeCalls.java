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
import jkind.lustre.Node;
import jkind.lustre.Program;
import jkind.lustre.TupleExpr;
import jkind.lustre.VarDecl;
import jkind.lustre.builders.NodeBuilder;
import jkind.lustre.builders.ProgramBuilder;
import jkind.lustre.visitors.AstMapVisitor;
import jkind.util.Util;

public class InlineNodeCalls extends AstMapVisitor {
	public static Program program(Program program) {
		return new InlineNodeCalls().visit(program);
	}

	private final Map<String, Node> nodeTable = new HashMap<>();
	private final List<VarDecl> newLocals = new ArrayList<>();
	private final List<String> newProperties = new ArrayList<>();
	private final Map<String, Integer> usedPrefixes = new HashMap<>();
	private final Queue<Equation> queue = new ArrayDeque<>();
	private final Map<String, Expr> inlinedCalls = new HashMap<>();

	@Override
	public Program visit(Program program) {
		Node main = program.getMainNode();
		nodeTable.putAll(Util.getNodeTable(program.nodes));

		NodeBuilder builder = new NodeBuilder(main);
		builder.clearAssertions();
		builder.addAssertions(visitAssertions(main.assertions));
		builder.clearEquations();
		builder.addEquations(visitEquationsQueue(main.equations));
		builder.addLocals(newLocals);
		builder.addProperties(newProperties);
		main = builder.build();
		
		return new ProgramBuilder(program).clearNodes().addNode(main).build();
	}

	public List<Equation> visitEquationsQueue(List<Equation> equations) {
		queue.addAll(equations);
		List<Equation> result = new ArrayList<>();

		while (!queue.isEmpty()) {
			Equation eq = queue.poll();
			result.add(visit(eq));
		}
		return result;
	}

	@Override
	public Expr visit(CallExpr e) {
		if (isNodeCall(e)) {
			// Detect duplicate node calls to reduce code size
			String key = getKey(e);
			if (inlinedCalls.containsKey(key)) {
				return inlinedCalls.get(key);
			} else {
				Expr result = TupleExpr.compress(visitNodeCallExpr(e));
				inlinedCalls.put(key, result);
				return result;
			}
		} else {
			return super.visit(e);
		}
	}

	private String getKey(CallExpr e) {
		return new CallExpr(getOriginalName(e), e.args).toString();
	}

	private String getOriginalName(CallExpr e) {
		return e.name.substring(e.name.lastIndexOf('.') + 1);
	}

	public List<IdExpr> visitNodeCallExpr(CallExpr e) {
		String prefix = newPrefix(e.name);
		Node node = nodeTable.get(getOriginalName(e));

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
		return nodeTable.containsKey(getOriginalName(e));
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
					return new CallExpr(e.location, prefix + e.name, visitExprs(e.args));
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
