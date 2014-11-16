package jkind.pdr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jkind.lustre.EnumType;
import jkind.lustre.NamedType;
import jkind.lustre.Node;
import jkind.lustre.SubrangeIntType;
import jkind.lustre.Type;
import jkind.lustre.VarDecl;
import jkind.solvers.Result;
import jkind.solvers.SatResult;
import jkind.solvers.SimpleModel;
import jkind.solvers.UnknownResult;
import jkind.solvers.UnsatResult;
import de.uni_freiburg.informatik.ultimate.logic.Annotation;
import de.uni_freiburg.informatik.ultimate.logic.Logics;
import de.uni_freiburg.informatik.ultimate.logic.Script.LBool;
import de.uni_freiburg.informatik.ultimate.logic.Sort;
import de.uni_freiburg.informatik.ultimate.logic.Term;
import de.uni_freiburg.informatik.ultimate.logic.Util;
import de.uni_freiburg.informatik.ultimate.smtinterpol.smtlib2.SMTInterpol;

public class Imc extends ScriptUser {
	private final List<VarDecl> varDecls;
	private final Map<String, List<Term>> variableLists = new HashMap<>();
	private final List<Term> base;
	private final List<Term> prime;

	private final Term I;
	private final Term T;
	private final Term P;

	public Imc(Node node) {
		super(new SMTInterpol());

		script.setOption(":produce-interpolants", true);
		script.setLogic(Logics.QF_UFLIRA);
		script.setOption(":verbosity", 3);

		Lustre2Term lustre2Term = new Lustre2Term(script, node);
		varDecls = lustre2Term.getVariables();

		this.base = getVariables("");
		this.prime = getVariables("'");

		this.I = lustre2Term.getInit();
		this.T = lustre2Term.getTransition();
		this.P = lustre2Term.getProperty();
	}

	public int imcMain() {
		for (int k = 1; true; k++) {
			System.out.print(k);
			script.push(1);
			Result result = imcLoop(k);
			if (result instanceof SatResult) {
				return k;
			} else if (result instanceof UnsatResult) {
				return -1;
			}
			script.pop(1);
			variableLists.clear(); // TODO: This is a hack
		}
	}

	public Result imcLoop(int k) {
		for (int i = 0; i < k; i++) {
			getVariables("$" + i);
		}

		List<Term> conjuncts = new ArrayList<>();
		List<Term> disjuncts = new ArrayList<>();
		List<Term> vars = getVariables("$1");
		disjuncts.add(not(P(vars)));
		for (int i = 1; i < k; i++) {
			List<Term> nextVars = getVariables("$" + (i + 1));
			conjuncts.add(T(vars, nextVars));
			disjuncts.add(not(P(nextVars)));
			vars = nextVars;
		}
		conjuncts.add(or(disjuncts));

		script.assertTerm(name(and(conjuncts), "B"));

		Term R = I;
		while (true) {
			List<Term> vars0 = getVariables("$0");
			List<Term> vars1 = getVariables("$1");
			script.push(1);
			script.assertTerm(name(and(subst(R, base, vars0), T(vars0, vars1)), "A"));

			switch (script.checkSat()) {
			case SAT:
				// TODO: Cex
				script.pop(1);
				if (R.equals(I)) {
					return new SatResult(new SimpleModel());
				} else {
					return new UnknownResult();
				}

			case UNSAT:
				Term nextR = or(R, subst(getInterpolant(), vars1, base));
				script.pop(1);
				// TODO: Handle unknown
				if (Util.checkSat(script, not(implies(nextR, R))) == LBool.UNSAT) {
					return new UnsatResult();
				}
				System.out.print(".");
				R = nextR;
				break;

			case UNKNOWN:
				throw new IllegalArgumentException("SMT solver returned 'unknown' due to "
						+ script.getInfo(":reason-unknown"));
			}
		}
	}

	private Term getInterpolant() {
		Term[] terms = script.getInterpolants(new Term[] { term("A"), term("B") });
		return terms[0];
	}

	private Term subst(Term term, List<Term> variables, List<Term> arguments) {
		return Subst.apply(script, term, variables, arguments);
	}

	private Term name(Term term, String name) {
		return script.annotate(term, new Annotation(":named", name));
	}

	private Term T(List<Term> arguments1, List<Term> arguments2) {
		return subst(subst(T, base, arguments1), prime, arguments2);
	}

	private Term P(List<Term> arguments) {
		return subst(P, base, arguments);
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
