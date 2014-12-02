package jkind.engines.pdr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jkind.engines.StopException;
import jkind.lustre.Node;
import jkind.lustre.Type;
import jkind.lustre.VarDecl;
import jkind.lustre.values.Value;
import jkind.solvers.SimpleModel;
import jkind.solvers.smtinterpol.ScriptUser;
import jkind.solvers.smtinterpol.SmtInterpolUtil;
import jkind.solvers.smtinterpol.Subst;
import jkind.translation.TransitionRelation;
import jkind.util.StreamIndex;
import de.uni_freiburg.informatik.ultimate.logic.Annotation;
import de.uni_freiburg.informatik.ultimate.logic.Logics;
import de.uni_freiburg.informatik.ultimate.logic.Model;
import de.uni_freiburg.informatik.ultimate.logic.QuotedObject;
import de.uni_freiburg.informatik.ultimate.logic.Sort;
import de.uni_freiburg.informatik.ultimate.logic.Term;
import de.uni_freiburg.informatik.ultimate.logic.TermVariable;

public class PdrInterpolatingSolver extends ScriptUser {
	private final List<VarDecl> varDecls;
	private final Map<String, Term[]> variableLists = new HashMap<>();
	private final Term[] base;
	private final Term[] prime;

	private final Term P;

	public PdrInterpolatingSolver(Node node, String property, String scratchBase) {
		super(SmtInterpolUtil.getScript(scratchBase));

		script.setOption(":produce-interpolants", true);
		script.setOption(":simplify-interpolants", true);
		script.setLogic(Logics.QF_UFLIRA);
		script.setOption(":verbosity", 2);

		Lustre2Term lustre2Term = new Lustre2Term(script, node);
		this.varDecls = lustre2Term.getVariables();

		this.base = getVariables("");
		this.prime = getVariables("'");

		defineTransitionRelation(lustre2Term.getTransition());
		this.P = lustre2Term.encodeProperty(property);
	}

	private void defineTransitionRelation(Term transition) {
		TermVariable[] params = new TermVariable[base.length + prime.length];
		for (int i = 0; i < base.length; i++) {
			Term v = base[i];
			params[i] = script.variable(v.toString(), v.getSort());
		}
		for (int i = 0; i < prime.length; i++) {
			Term v = prime[i];
			params[i + base.length] = script.variable(v.toString(), v.getSort());
		}

		Term body = subst(transition, concat(base, prime), params);
		script.defineFun(TransitionRelation.T.str, params, script.sort("Bool"), body);
	}

	public Set<Predicate> refine(List<Cube> cubes) {
		List<Term> pieces = new ArrayList<>();

		Term[] vars = getVariables(StreamIndex.getSuffix(-1));
		for (int i = 0; i < cubes.size() - 1; i++) {
			Term[] nextVars = getVariables(StreamIndex.getSuffix(i));
			pieces.add(and(apply(cubes.get(i), vars), T(vars, nextVars)));
			vars = nextVars;
		}
		pieces.add(and(apply(cubes.get(cubes.size() - 1), vars), not(P(vars))));

		Term[] interpolants = getInterpolants(pieces);

		Set<Predicate> result = new HashSet<>();
		for (int i = 0; i < cubes.size() - 1; i++) {
			vars = getVariables(StreamIndex.getSuffix(i));
			result.addAll(PredicateCollector.collect(subst(interpolants[i], vars, base)));
		}
		return result;
	}

	private Term[] getInterpolants(List<Term> terms) {
		script.push(1);

		Term[] names = new Term[terms.size()];
		for (int i = 0; i < terms.size(); i++) {
			String name = "I" + i;
			script.assertTerm(name(terms.get(i), name));
			names[i] = script.term(name);
		}

		switch (script.checkSat()) {
		case UNSAT:
			Term[] interps = script.getInterpolants(names);
			script.pop(1);
			return interps;

		case SAT:
			int length = terms.size() - 1;
			SimpleModel extractedModel = extractModel(script.getModel(), length);
			throw new CounterexampleException(length, extractedModel);

		default:
			comment("SMT solver returned 'unknown' due to " + script.getInfo(":reason-unknown"));
			throw new StopException();
		}
	}

	private SimpleModel extractModel(Model model, int length) {
		SimpleModel result = new SimpleModel();
		for (int i = -1; i < length; i++) {
			for (VarDecl vd : varDecls) {
				String name = vd.id + StreamIndex.getSuffix(i);
				Term evaluated = model.evaluate(script.term(name));
				Value value = SmtInterpolUtil.getValue(evaluated, vd.type);
				result.addValue(name, value);
			}
		}
		return result;
	}

	private void comment(String comment) {
		script.echo(new QuotedObject(comment));
	}

	private Term T(Term[] variables1, Term[] variables2) {
		return script.term(TransitionRelation.T.str, concat(variables1, variables2));
	}

	private Term[] concat(Term[] terms1, Term[] terms2) {
		Term[] result = new Term[terms1.length + terms2.length];
		System.arraycopy(terms1, 0, result, 0, terms1.length);
		System.arraycopy(terms2, 0, result, terms1.length, terms2.length);
		return result;
	}

	private Term P(Term[] vars) {
		return subst(P, base, vars);
	}

	public Term[] getVariables(String suffix) {
		if (variableLists.containsKey(suffix)) {
			return variableLists.get(suffix);
		}

		Term[] result = new Term[varDecls.size()];
		for (int i = 0; i < varDecls.size(); i++) {
			VarDecl vd = varDecls.get(i);
			String name = vd.id + suffix;
			result[i] = declareVar(name, vd.type);
		}
		variableLists.put(suffix, result);
		return result;
	}

	private Term declareVar(String name, Type type) {
		Sort sort = getSort(type);
		script.declareFun(name, new Sort[0], sort);
		return script.term(name);
	}

	private Sort getSort(Type type) {
		return SmtInterpolUtil.getSort(script, type);
	}

	private Term apply(Term term, Term[] arguments) {
		return subst(term, base, arguments);
	}

	private Term apply(Cube cube, Term[] arguments) {
		return apply(cube.toTerm(script), arguments);
	}

	private Term subst(Term term, Term[] variables, Term[] arguments) {
		return Subst.apply(script, term, variables, arguments);
	}

	private Term name(Term term, String name) {
		return script.annotate(term, new Annotation(":named", name));
	}
}
