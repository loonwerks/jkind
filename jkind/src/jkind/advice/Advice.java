package jkind.advice;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import jkind.engines.invariant.InvariantSet;
import jkind.lustre.Expr;
import jkind.lustre.Node;
import jkind.lustre.Type;
import jkind.lustre.VarDecl;
import jkind.util.Util;

public class Advice {
	private final List<VarDecl> varDecls = new ArrayList<>();
	private final InvariantSet invariants = new InvariantSet();

	public void addVarDecls(List<VarDecl> newVarDecls) {
		varDecls.addAll(newVarDecls);
	}

	public void addInvariant(Expr newInvariant) {
		invariants.add(newInvariant);
	}

	public void addInvariants(List<Expr> newInvariants) {
		invariants.addAll(newInvariants);
	}

	public List<VarDecl> getVarDecls() {
		return varDecls;
	}

	public List<Expr> getInvariants() {
		return invariants.getInvariants();
	}

	public void prune(Node node) {
		Map<String, Type> map = Util.getTypeMap(node);

		Predicate<VarDecl> noLongerExists = vd -> !map.containsKey(vd.id);
		varDecls.removeIf(noLongerExists);

		Predicate<VarDecl> typeChanged = vd -> !baseTypesMatch(map.get(vd.id), vd.type);
		varDecls.removeIf(typeChanged);

		VariableUsageChecker checker = new VariableUsageChecker(varDecls);
		Predicate<Expr> usesUndefinedVariables = inv -> !checker.check(inv);
		invariants.removeIf(usesUndefinedVariables);
	}

	private boolean baseTypesMatch(Type type1, Type type2) {
		return Util.getName(type1).equals(Util.getName(type2));
	}
}
