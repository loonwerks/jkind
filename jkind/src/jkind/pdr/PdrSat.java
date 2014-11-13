package jkind.pdr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.uni_freiburg.informatik.ultimate.logic.Script;
import de.uni_freiburg.informatik.ultimate.logic.Sort;
import de.uni_freiburg.informatik.ultimate.logic.Term;
import de.uni_freiburg.informatik.ultimate.smtinterpol.smtlib2.SMTInterpol;
import jkind.lustre.EnumType;
import jkind.lustre.NamedType;
import jkind.lustre.Node;
import jkind.lustre.SubrangeIntType;
import jkind.lustre.Type;
import jkind.lustre.VarDecl;

public class PdrSat extends ScriptUser {
	private final Node node;
	private final List<Frame> F;

	private final List<VarDecl> varDecls;
	private final Map<String, List<Term>> variableLists = new HashMap<>();
	private final List<Term> base;
	private final List<Term> prime;

	private final Term I;
	private final Term T;
	private final Term P;
	
	private final Set<Predicate> predicates = new HashSet<>();
	private Term TAbstract;

	public PdrSat(Node node, List<Frame> F) {
		super(new SMTInterpol());
		this.node = node;
		this.F = F;

		Lustre2Term lustre2Term = new Lustre2Term(script);
		varDecls = lustre2Term.getVariables(node);

		this.base = getVariables("");
		this.prime = getVariables("'");

		this.I = lustre2Term.getInit();
		this.T = lustre2Term.getTransition(node);
		this.P = lustre2Term.getProperty(node);

		predicates.addAll(PredicateCollector.collect(I));
		predicates.addAll(PredicateCollector.collect(P));

		recomputeTAbstract();
	}

	public Cube getBadCube() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isInitial(Cube cube) {
		// TODO Auto-generated method stub
		return false;
	}

	public enum Option {
		DEFAULT, EXTRACT_MODEL, NO_IND
	};

	public TCube solveRelative(TCube s, Option option) {
		// TODO Auto-generated method stub
		return null;
	}

	public TCube solveRelative(TCube s) {
		return solveRelative(s, Option.DEFAULT);
	}

	public boolean isBlocked(TCube s) {
		// TODO Auto-generated method stub
		return false;
	}

	public void blockCubeInSolver(TCube s) {
		// TODO Auto-generated method stub

	}

	public void refine(Cube cube) {
		// TODO Auto-generated method stub

	}

	public Frame initialFrame() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean refine(List<Cube> cubes) {
		// TODO Auto-generated method stub
		return false;
	}

	private void recomputeTAbstract() {
		List<Term> bar = getVariables("-");
		List<Term> barPrime = getVariables("-'");
		this.TAbstract = and(eqP(base, bar), T(bar, barPrime), eqP(barPrime, prime));
	}

	private Term T(List<Term> variables1, List<Term> variables2) {
		return solver.subst(solver.subst(T, base, variables1), prime, variables2);
	}

	private Term P(List<Term> variables) {
		return solver.subst(P, base, variables);
	}
	
	private Term eqP(List<Term> variables1, List<Term> variables2) {
		Term[] terms = new Term[predicates.size()];
		int i = 0;
		for (Predicate p : predicates) {
			terms[i++] = term("=", apply(p, variables1), apply(p, variables2));
		}
		return and(terms);
	}

	public List<Term> getVariables(String suffix) {
		if (variableLists.containsKey(suffix)) {
			return variableLists.get(suffix);
		}

		List<Term> result = new ArrayList<>();
		for (VarDecl vd : varDecls) {
			String name = vd.id + suffix;
			Sort sort = getSort(vd.type);
			script.declareFun(name, new Sort[0], sort);
			result.add(script.term(name));
		}
		variableLists.put(suffix, result);
		return result;
	}

	private Sort getSort(Type type) {
		if (type instanceof NamedType) {
			NamedType namedType = (NamedType) type;
			switch (namedType.name) {
			case "bool":
				return script.sort("Bool");
			case "int":
				return script.sort("Int");
			case "real":
				return script.sort("Real");
			}
		} else if (type instanceof SubrangeIntType || type instanceof EnumType) {
			return script.sort("Int");
		}

		throw new IllegalArgumentException("Unhandled type " + type);
	}
}
