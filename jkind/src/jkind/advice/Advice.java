package jkind.advice;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import jkind.engines.invariant.InvariantSet;
import jkind.lustre.EnumType;
import jkind.lustre.Expr;
import jkind.lustre.NamedType;
import jkind.lustre.Node;
import jkind.lustre.SubrangeIntType;
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
		return getBaseName(type1).equals(getBaseName(type2));
	}

	private String getBaseName(Type type) {
		if (type instanceof EnumType) {
			EnumType et = (EnumType) type;
			return et.id;
		} else if (type instanceof NamedType) {
			NamedType nt = (NamedType) type;
			return nt.name;
		} else if (type instanceof SubrangeIntType) {
			return NamedType.INT.name;
		} else {
			throw new IllegalArgumentException("Unexpected type: " + type.getClass().getSimpleName());
		}
	}
}
