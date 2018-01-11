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
		return new LustreSlicer(getPropertyDependencies(node, depMap)).visit(node);
	}

	private static DependencySet getPropertyDependencies(Node node, DependencyMap depMap) {
		DependencySet keep = new DependencySet();
		for (String prop : node.properties) {
			keep.addAll(depMap.get(prop));
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
		List<Expr> sliced = new ArrayList<>();
		for (Expr assertion : assertions) {
			DependencySet deps = DependencyVisitor.get(assertion);
			if (deps.isEmpty() || keep.contains(deps.first())) {
				sliced.add(assertion);
			}
		}
		return sliced;
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
