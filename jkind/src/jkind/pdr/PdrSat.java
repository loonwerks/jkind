package jkind.pdr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jkind.lustre.EnumType;
import jkind.lustre.NamedType;
import jkind.lustre.Node;
import jkind.lustre.SubrangeIntType;
import jkind.lustre.Type;
import jkind.lustre.VarDecl;
import de.uni_freiburg.informatik.ultimate.logic.ApplicationTerm;
import de.uni_freiburg.informatik.ultimate.logic.Logics;
import de.uni_freiburg.informatik.ultimate.logic.Model;
import de.uni_freiburg.informatik.ultimate.logic.Sort;
import de.uni_freiburg.informatik.ultimate.logic.Term;
import de.uni_freiburg.informatik.ultimate.smtinterpol.smtlib2.SMTInterpol;

public class PdrSat extends ScriptUser {
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
		this.F = F;

		script.setOption(":produce-interpolants", true);
		script.setLogic(Logics.QF_UFLIRA);
		script.setOption(":verbosity", 3);

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
		return checkSat(and(R(depth()), not(P)));
	}

	private int depth() {
		return F.size() - 2;
	}

	private Term lastFrame() {
		return R(F.size() - 1);
	}

	public Term R(int k) {
		List<Term> conjuncts = new ArrayList<>();
		for (int i = k; i < F.size(); i++) {
			conjuncts.add(F.get(i).toTerm(script));
		}
		return and(conjuncts);
	}

	private Cube checkSat(Term assertion) {
		script.push(1);
		script.assertTerm(assertion);
		switch (script.checkSat()) {
		case UNSAT:
			script.pop(1);
			return null;

		case SAT:
			Cube cube = extractCube();
			script.pop(1);
			return cube;

		default:
			throw new IllegalArgumentException();
		}
	}

	private Cube extractCube() {
		Model model = script.getModel();
		Cube result = new Cube();
		for (Predicate p : predicates) {
			if (isTrue(model.evaluate(p.getTerm()))) {
				result.addPositive(p);
			} else {
				result.addNegative(p);
			}
		}
		return result;
	}

	private boolean isTrue(Term term) {
		if (term instanceof ApplicationTerm) {
			ApplicationTerm at = (ApplicationTerm) term;
			return at.getFunction().getName().equals("true");
		}
		return false;
	}

	public boolean isInitial(Cube cube) {
		// Given our Lustre translation, a frame is initial if it does not
		// contain ~init
		return !cube.getNegative().contains(new Predicate(I));
	}

	public enum Option {
		DEFAULT, EXTRACT_MODEL, NO_IND
	};

	public TCube solveRelative(TCube s, Option option) {
		int frame = s.getFrame();
		Cube cube = s.getCube();

		Term query;
		if (option == Option.NO_IND) {
			query = and(R(frame - 1), TAbstract, prime(cube));
		} else {
			query = and(R(frame - 1), not(cube), TAbstract, prime(cube));
		}

		// TODO: Make use of DEFAULT vs EXTRACT_MODEL?

		Cube p = checkSat(query);
		if (p == null) {
			// UNSAT
			// TODO: Generalize based on unsat cores
			return s;
		} else {
			return new TCube(p, TCube.FRAME_NULL);
		}
	}

	public TCube solveRelative(TCube s) {
		return solveRelative(s, Option.DEFAULT);
	}

	public boolean isBlocked(TCube s) {
		int frame = s.getFrame();
		Cube cube = s.getCube();
		Term query = and(R(frame), cube);
		return checkSat(query) == null;
	}

	public void blockCubeInSolver(TCube s) {
		// Nothing to do until we make better use of incremental solving and
		// activation literals
	}

	public Frame createInitialFrame() {
		return new Frame(I);
	}

	public boolean refine(List<Cube> cubes) {
		// TODO do refinement
		return false;
	}

	private void recomputeTAbstract() {
		List<Term> bar = getVariables("-");
		List<Term> barPrime = getVariables("-'");
		this.TAbstract = and(eqP(base, bar), T(bar, barPrime), eqP(barPrime, prime));
	}

	private Term T(List<Term> variables1, List<Term> variables2) {
		return subst(subst(T, base, variables1), prime, variables2);
	}

	private Term P(List<Term> variables) {
		return subst(P, base, variables);
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

	private Term not(Cube cube) {
		return not(cube.toTerm(script));
	}

	private Term prime(Cube cube) {
		return subst(cube.toTerm(script), base, prime);
	}

	private Term apply(Predicate p, List<Term> arguments) {
		return subst(p.getTerm(), base, arguments);
	}

	private Term subst(Term term, List<Term> variables, List<Term> arguments) {
		return Subst.apply(script, term, variables, arguments);
	}

	private Term and(Term term, Cube cube) {
		return and(term, cube.toTerm(script));
	}
}
