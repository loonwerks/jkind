package jkind.slicing;

import java.util.ArrayList;
import java.util.List;

import jkind.lustre.Equation;
import jkind.lustre.Expr;
import jkind.lustre.IdExpr;
import jkind.lustre.Node;
import jkind.lustre.VarDecl;
import jkind.lustre.visitors.AstMapVisitor;

public class LustreSlicer extends AstMapVisitor {
	public static Node slice(Node node, DependencyMap depMap) {
		return new LustreSlicer(getDependenciesToKeep(node, depMap)).visit(node);
	}

	/*
	 * What the sliced node must retain: everything the properties depend on,
	 * and everything the assertions depend on. Assertions are constraints on
	 * the behaviours of the model as a whole, so removing one admits behaviours
	 * the original model does not have.
	 */
	private static DependencySet getDependenciesToKeep(Node node, DependencyMap depMap) {
		DependencySet keep = new DependencySet();
		for (String prop : node.properties) {
			DependencySet deps = depMap.get(prop);
			if (deps != null) {
				keep.addAll(deps);
			}
		}
		for (Expr assertion : node.assertions) {
			for (Dependency dep : DependencyVisitor.get(assertion)) {
				DependencySet deps = depMap.get(dep);
				if (deps != null) {
					keep.addAll(deps);
				}
			}
		}
		return keep;
	}

	private final DependencySet keep;

	private LustreSlicer(DependencySet keep) {
		this.keep = keep;
	}

	@Override
	protected List<VarDecl> visitVarDecls(List<VarDecl> decls) {
		List<VarDecl> sliced = new ArrayList<>();
		for (VarDecl decl : decls) {
			if (keep.contains(Dependency.variable(decl.id))) {
				sliced.add(decl);
			}
		}
		return sliced;
	}

	@Override
	protected List<Equation> visitEquations(List<Equation> equations) {
		List<Equation> sliced = new ArrayList<>();

		for (Equation eq : equations) {
			if (containsAny(keep, eq.lhs)) {
				sliced.add(eq);
			}
		}
		return sliced;
	}

	@Override
	protected List<Expr> visitAssertions(List<Expr> assertions) {
		// Every assertion is kept, and getDependenciesToKeep has retained the
		// variables each one needs.
		return new ArrayList<>(assertions);
	}

	@Override
	protected List<String> visitIvc(List<String> ivc) {
		List<String> sliced = new ArrayList<>();
		for (String e : ivc) {
			if (keep.contains(Dependency.variable(e))) {
				sliced.add(e);
			}
		}
		return sliced;
	}

	private static boolean containsAny(DependencySet set, List<IdExpr> lhs) {
		for (IdExpr idExpr : lhs) {
			if (set.contains(Dependency.variable(idExpr.id))) {
				return true;
			}
		}
		return false;
	}
}
