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
import jkind.lustre.Location;
import jkind.lustre.MapVisitor;
import jkind.lustre.Node;
import jkind.lustre.NodeCallExpr;
import jkind.lustre.Program;
import jkind.lustre.VarDecl;
import jkind.util.Util;

public class InlineNodeCalls extends MapVisitor {
	public static Node program(Program program) {
		InlineNodeCalls inliner = new InlineNodeCalls(Util.getNodeTable(program.nodes));
		Node main = program.main;
		List<Equation> equations = inliner.visit(main);

		List<VarDecl> locals = new ArrayList<VarDecl>(main.locals);
		locals.addAll(inliner.newLocals);

		return new Node(main.location, main.id, main.inputs, main.outputs, locals, equations,
				main.properties, main.assertions);
	}
	
	private Map<String, Node> nodeTable;
	private List<VarDecl> newLocals;
	private Queue<Equation> queue;

	private InlineNodeCalls(Map<String, Node> nodeTable) {
		this.nodeTable = nodeTable;
		this.newLocals = new ArrayList<VarDecl>();
		this.queue = new ArrayDeque<Equation>();
	}

	private List<Equation> visit(Node node) {
		queue.addAll(node.equations);
		List<Equation> result = new ArrayList<Equation>();

		while (!queue.isEmpty()) {
			Equation eq = queue.poll();
			if (eq.lhs.size() == 1) {
				result.add(new Equation(eq.location, eq.lhs, eq.expr.accept(this)));
			} else {
				List<IdExpr> outputs = visitNodeCallExpr((NodeCallExpr) eq.expr);
				for (int i = 0; i < eq.lhs.size(); i++) {
					result.add(new Equation(Location.NULL, eq.lhs.get(i), outputs.get(i)));
				}
			}
		}
		
		return result;
	}

	private IdExpr newVar(VarDecl decl) {
		VarDecl newDecl = new VarDecl(decl.location, "%inline" + newLocals.size(), decl.type);
		newLocals.add(newDecl);
		return new IdExpr(Location.NULL, newDecl.id);
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

		List<IdExpr> result = new ArrayList<IdExpr>();
		for (VarDecl decl : node.outputs) {
			result.add(translation.get(decl.id));
		}
		return result;
	}

	private Map<String, IdExpr> getTranslation(Node node) {
		Map<String, IdExpr> translation = new HashMap<String, IdExpr>();
		for (VarDecl decl : Util.getVarDecls(node)) {
			translation.put(decl.id, newVar(decl));
		}
		return translation;
	}

	private void createInputEquations(List<VarDecl> inputs, List<Expr> args,
			Map<String, IdExpr> translation) {
		for (int i = 0; i < inputs.size(); i++) {
			IdExpr idExpr = translation.get(inputs.get(i).id);
			Expr arg = args.get(i);
			queue.add(new Equation(Location.NULL, idExpr, arg));
		}
	}

	private void createAssignmentEquations(List<Equation> equations, Map<String, IdExpr> translation) {
		SubstitutionVisitor substitution = new SubstitutionVisitor(translation);
		for (Equation eq : equations) {
			List<IdExpr> lhs = new ArrayList<IdExpr>();
			for (IdExpr idExpr : eq.lhs) {
				lhs.add(translation.get(idExpr.id));
			}
			Expr expr = eq.expr.accept(substitution);
			queue.add(new Equation(eq.location, lhs, expr));
		}
	}
}
