package jkind.slicing;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jkind.lustre.Equation;
import jkind.lustre.IdExpr;
import jkind.lustre.Node;
import jkind.lustre.VarDecl;

public class Slicer {
	public static Node slice(Node node) {
		DependencyMap depMap = new DependencyMap(node);
		Set<String> keep = getPropertyDependencies(node, depMap);
		return sliceByKeep(node, keep);
	}

	private static Set<String> getPropertyDependencies(Node node, DependencyMap depMap) {
		Set<String> keep = new HashSet<String>();
		for (String prop : node.properties) {
			keep.addAll(depMap.get(prop));
		}
		return keep;
	}

	private static Node sliceByKeep(Node node, Set<String> keep) {
		List<VarDecl> inputs = sliceVarDecls(node.inputs, keep);
		List<VarDecl> outputs = sliceVarDecls(node.outputs, keep);
		List<VarDecl> locals = sliceVarDecls(node.locals, keep);
		List<Equation> equations = sliceEquations(node.equations, keep);
		return new Node(node.location, node.id, inputs, outputs, locals, equations, node.properties);
	}

	private static List<VarDecl> sliceVarDecls(List<VarDecl> decls, Set<String> keep) {
		List<VarDecl> sliced = new ArrayList<VarDecl>();
		for (VarDecl decl : decls) {
			if (keep.contains(decl.id)) {
				sliced.add(decl);
			}
		}
		return sliced;
	}

	private static List<Equation> sliceEquations(List<Equation> equations, Set<String> keep) {
		List<Equation> sliced = new ArrayList<Equation>();
		for (Equation eq : equations) {
			if (containsAny(keep, eq.lhs)) {
				sliced.add(eq);
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
