package jkind.translation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jkind.lustre.Equation;
import jkind.lustre.Expr;
import jkind.lustre.IdExpr;
import jkind.lustre.Location;
import jkind.lustre.Node;
import jkind.lustre.NodeCallExpr;
import jkind.lustre.VarDecl;

public class InlineNodeCallVisitor extends MapVisitor {
	private Map<String, Node> nodeTable;
	private List<VarDecl> newLocals;
	private List<Equation> newEquations;

	public InlineNodeCallVisitor(Map<String, Node> nodeTable) {
		this.nodeTable = nodeTable;
		this.newLocals = new ArrayList<VarDecl>();
		this.newEquations = new ArrayList<Equation>();
	}

	public List<VarDecl> getNewLocals() {
		return newLocals;
	}

	public List<Equation> getNewEquations() {
		return newEquations;
	}

	private IdExpr newVar(VarDecl decl) {
		VarDecl newDecl = new VarDecl(decl.location, "#inline" + newLocals.size(), decl.type);
		newLocals.add(newDecl);
		return new IdExpr(Location.NULL, newDecl.id);
	}

	@Override
	public Expr visit(NodeCallExpr e) {
		Node node = nodeTable.get(e.node);
		Map<String, IdExpr> translation = getTranslation(node);

		createInputEquations(node.inputs, e.args, translation);
		createAssignmentEquations(node.equations, translation);

		if (node.outputs.size() == 1) {
			String output = node.outputs.get(0).id;
			return new IdExpr(e.location, translation.get(output).id);
		} else {
			throw new IllegalArgumentException("Not implemented");
		}
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
		SubstitutionVisitor substitution = new SubstitutionVisitor(translation);
		for (int i = 0; i < inputs.size(); i++) {
			String id = translation.get(inputs.get(i).id).id;
			Expr arg = args.get(i).accept(substitution);
			newEquations.add(new Equation(Location.NULL, id, arg));
		}
	}

	private void createAssignmentEquations(List<Equation> equations, Map<String, IdExpr> translation) {
		SubstitutionVisitor substitution = new SubstitutionVisitor(translation);
		for (Equation eq : equations) {
			String id = translation.get(eq.id).id;
			Expr expr = eq.expr.accept(substitution);
			newEquations.add(new Equation(eq.location, id, expr));
		}
	}
}
