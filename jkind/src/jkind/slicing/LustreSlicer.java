package jkind.slicing;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

	private static Set<String> getPropertyDependencies(Node node, DependencyMap depMap) {
		Set<String> keep = new HashSet<>();
		for (String prop : node.properties) {
			keep.addAll(depMap.get(prop));
		}
		return keep;
	}

	private final Set<String> keep;

	private LustreSlicer(Set<String> keep) {
		this.keep = keep;
	}

	@Override
	protected List<VarDecl> visitVarDecls(List<VarDecl> decls) {
		List<VarDecl> sliced = new ArrayList<>();
		for (VarDecl decl : decls) {
			if (keep.contains(decl.id)) {
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
			Set<String> deps = IdExtractorVisitor.getIds(assertion);
			if (deps.size() > 0 && keep.contains(deps.iterator().next())) {
				sliced.add(assertion);
			}
		}
		return sliced;
	}

	private static boolean containsAny(Set<String> keep, List<IdExpr> lhs) {
		for (IdExpr idExpr : lhs) {
			if (keep.contains(idExpr.id)) {
				return true;
			}
		}
		return false;
	}
}
