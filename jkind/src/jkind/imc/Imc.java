package jkind.imc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jkind.engines.pdr.Lustre2Term;
import jkind.lustre.EnumType;
import jkind.lustre.NamedType;
import jkind.lustre.Node;
import jkind.lustre.SubrangeIntType;
import jkind.lustre.Type;
import jkind.lustre.VarDecl;
import jkind.solvers.smtinterpol.ScriptUser;
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
		this.P = lustre2Term.encodeProperty(node.properties.get(0));
	}

	public int imcMain() {
		for (int k = 1; true; k++) {
			System.out.print(k);
			script.push(1);
			switch (imcLoop(k)) {
			case SAT:
				return k;
			case UNSAT:
				return -1;
			default:
				break;
			}
			script.pop(1);
			variableLists.clear(); // TODO: This is a hack
		}
	}

	public LBool imcLoop(int k) {
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
		conjuncts.add(not(P(vars)));

		script.assertTerm(name(and(conjuncts), "B"));

		List<Term> vars0 = getVariables("$0");
		List<Term> vars1 = getVariables("$1");
		script.assertTerm(name(T(vars0, vars1), "A2"));
		Term R = I;
		while (true) {
			script.push(1);
			script.assertTerm(name(subst(R, base, vars0), "A1"));

			switch (script.checkSat()) {
			case SAT:
				script.pop(1);
				if (R.equals(I)) {
					return LBool.SAT;
				} else {
					return LBool.UNKNOWN;
				}

			case UNSAT:
				Term nextR = or(R, subst(getInterpolant(), vars1, base));
				script.pop(1);
				switch (Util.checkSat(script, not(implies(nextR, R)))) {
				case UNSAT:
					return LBool.UNSAT;
				case UNKNOWN:
					return LBool.UNKNOWN;
				default:
					break;
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
		Term A = and(term("A1"), term("A2"));
		Term B = term("B");
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
