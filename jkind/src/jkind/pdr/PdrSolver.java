package jkind.pdr;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jkind.lustre.EnumType;
import jkind.lustre.NamedType;
import jkind.lustre.SubrangeIntType;
import jkind.lustre.Type;
import jkind.lustre.VarDecl;
import de.uni_freiburg.informatik.ultimate.logic.ApplicationTerm;
import de.uni_freiburg.informatik.ultimate.logic.Logics;
import de.uni_freiburg.informatik.ultimate.logic.Model;
import de.uni_freiburg.informatik.ultimate.logic.Script;
import de.uni_freiburg.informatik.ultimate.logic.Sort;
import de.uni_freiburg.informatik.ultimate.logic.Term;
import de.uni_freiburg.informatik.ultimate.logic.Util;
import de.uni_freiburg.informatik.ultimate.smtinterpol.smtlib2.SMTInterpol;

public class PdrSolver {
	private final Script script = new SMTInterpol();
	
	private List<VarDecl> varDecls;
	private final Map<String, List<Term>> variableLists = new HashMap<>();

	public PdrSolver() {
		script.setLogic(Logics.QF_UFLIRA);
		script.setOption(":verbosity", 3);
	}

	public Model checkSat(Term query) {
		script.push(1);
		script.assertTerm(query);
		switch (script.checkSat()) {
		case UNSAT:
			script.pop(1);
			return null;

		case SAT:
			Model model = script.getModel();
			script.pop(1);
			return model;

		default:
			throw new IllegalArgumentException();
		}
	}

	public Cube extractCube(Model model, Set<Predicate> predicates, List<Term> variables) {
		if (model == null) {
			return null;
		}

		Cube c = new Cube();
		for (Predicate pred : predicates) {
			if (isTrue(model.evaluate(pred.apply(script, variables)))) {
				c.addPositive(pred);
			} else {
				c.addNegative(pred);
			}
		}
		return c;
	}

	private boolean isTrue(Term term) {
		if (term instanceof ApplicationTerm) {
			ApplicationTerm at = (ApplicationTerm) term;
			return at.getFunction().getName().equals("true");
		}
		return false;
	}

	public void setVarDecls(List<VarDecl> varDecls) {
		this.varDecls = varDecls;
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

	public Term not(Term term) {
		return Util.not(script, term);
	}

	public Term or(Term... terms) {
		return Util.or(script, terms);
	}

	public Term and(Term... terms) {
		return Util.and(script, terms);
	}

	public Term and(List<Term> conjuncts) {
		return and(conjuncts.toArray(new Term[conjuncts.size()]));
	}

	public Term term(String funcname, Term... params) {
		return script.term(funcname, params);
	}

	public Term numeral(BigInteger num) {
		return script.numeral(num);
	}

	public Term decimal(BigDecimal decimal) {
		return script.decimal(decimal);
	}

	public Term frame(Frame frame) {
		return frame.toTerm(script);
	}

	public Term cube(Cube s) {
		return s.toTerm(script);
	}

	public Term apply(Cube s, List<Term> arguments) {
		return s.apply(script, arguments);
	}
}
