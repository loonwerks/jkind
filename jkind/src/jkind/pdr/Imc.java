package jkind.pdr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import jkind.lustre.EnumType;
import jkind.lustre.NamedType;
import jkind.lustre.Node;
import jkind.lustre.SubrangeIntType;
import jkind.lustre.Type;
import jkind.lustre.VarDecl;
import de.uni_freiburg.informatik.ultimate.logic.Annotation;
import de.uni_freiburg.informatik.ultimate.logic.ApplicationTerm;
import de.uni_freiburg.informatik.ultimate.logic.Logics;
import de.uni_freiburg.informatik.ultimate.logic.Model;
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
	private final Map<String, Term> P;

	public Imc(Node node) {
		super(new SMTInterpol());

		script.setOption(":produce-interpolants", true);
		script.setOption(":simplify-interpolants", true);
		script.setOption(":simplify-repeatedly", true);
		script.setLogic(Logics.QF_UFLIRA);
		script.setOption(":verbosity", 2);

		Lustre2Term lustre2Term = new Lustre2Term(script, node);
		varDecls = lustre2Term.getVariables();

		this.base = getVariables("");
		this.prime = getVariables("'");

		this.I = lustre2Term.getInit();
		this.T = lustre2Term.getTransition();
		this.P = lustre2Term.getProperties();
	}

	public void imcMain() {
		for (int k = 1; !P.isEmpty(); k++) {
			script.push(1);
			imcLoop(k);
			script.pop(1);
			variableLists.clear(); // TODO: This is a hack
		}
	}

	public void imcLoop(int k) {
		for (int i = 0; i < k; i++) {
			getVariables("$" + i);
		}

		List<Term> conjuncts = new ArrayList<>();
		List<Term> vars = getVariables("$1");
		for (int i = 1; i < k; i++) {
			List<Term> nextVars = getVariables("$" + (i + 1));
			conjuncts.add(T(vars, nextVars));
			vars = nextVars;
		}

		HashMap<String, Term> currP = new HashMap<>();
		for (Entry<String, Term> entry : P.entrySet()) {
			currP.put(entry.getKey(), subst(entry.getValue(), base, vars));
		}

		script.assertTerm(name(and(conjuncts), "B1"));

		List<Term> vars0 = getVariables("$0");
		List<Term> vars1 = getVariables("$1");
		script.assertTerm(name(T(vars0, vars1), "A2"));
		Term R = I;
		while (!currP.isEmpty()) {
			script.push(1);
			script.assertTerm(name(not(and(currP.values())), "B2"));
			script.assertTerm(name(subst(R, base, vars0), "A1"));

			switch (script.checkSat()) {
			case SAT:
				Model model = script.getModel();
				script.pop(1);
				if (R.equals(I)) {
					for (String prop : new ArrayList<>(currP.keySet())) {
						if (!isTrue(model.evaluate(currP.get(prop)))) {
							currP.remove(prop);
							P.remove(prop);
							System.out.println("Property " + prop
									+ " has counterexample of length " + k);
						}
					}
				} else {
					for (String prop : new ArrayList<>(currP.keySet())) {
						if (!isTrue(model.evaluate(currP.get(prop)))) {
							currP.remove(prop);
						}
					}
				}
				break;

			case UNSAT:
				Term nextR = or(R, subst(getInterpolant(), vars1, base));
				script.pop(1);
				switch (Util.checkSat(script, not(implies(nextR, R)))) {
				case UNSAT:
					for (String prop : currP.keySet()) {
						System.out.println("Property " + prop + " is valid (k = " + k + ")");
						P.remove(prop);
					}
					currP.clear();
					break;

				case UNKNOWN:
					throw new IllegalArgumentException("SMT solver returned 'unknown' due to "
							+ script.getInfo(":reason-unknown"));

				default:
					break;
				}
				R = nextR;
				break;

			case UNKNOWN:
				throw new IllegalArgumentException("SMT solver returned 'unknown' due to "
						+ script.getInfo(":reason-unknown"));
			}
		}
	}

	private boolean isTrue(Term term) {
		if (term instanceof ApplicationTerm) {
			ApplicationTerm at = (ApplicationTerm) term;
			return at.getFunction().getName().equals("true");
		}
		return false;
	}

	private Term getInterpolant() {
		Term A = and(term("A1"), term("A2"));
		Term B = and(term("B1"), term("B2"));
		Term[] terms = script.getInterpolants(new Term[] { A, B });
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
