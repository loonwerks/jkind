package jkind.engines.pdr;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.uni_freiburg.informatik.ultimate.logic.Annotation;
import de.uni_freiburg.informatik.ultimate.logic.ApplicationTerm;
import de.uni_freiburg.informatik.ultimate.logic.Logics;
import de.uni_freiburg.informatik.ultimate.logic.Model;
import de.uni_freiburg.informatik.ultimate.logic.QuotedObject;
import de.uni_freiburg.informatik.ultimate.logic.Sort;
import de.uni_freiburg.informatik.ultimate.logic.Term;
import de.uni_freiburg.informatik.ultimate.logic.TermVariable;
import jkind.engines.StopException;
import jkind.lustre.Expr;
import jkind.lustre.Function;
import jkind.lustre.Node;
import jkind.lustre.Type;
import jkind.lustre.VarDecl;
import jkind.solvers.smtinterpol.ScriptUser;
import jkind.solvers.smtinterpol.SmtInterpolUtil;
import jkind.solvers.smtinterpol.Subst;
import jkind.solvers.smtinterpol.Term2Expr;
import jkind.solvers.smtlib2.SmtLib2Model;
import jkind.solvers.smtlib2.SmtLib2Solver;
import jkind.translation.Relation;
import jkind.util.StreamIndex;

public class PdrSmt extends ScriptUser {
	private final List<Frame> F;

	private final List<VarDecl> varDecls;
	private final Map<String, Term[]> variableLists = new HashMap<>();
	private final Term[] base;
	private final Term[] baseAbstract;
	private final Term[] primeAbstract;
	private final Term[] prime;

	private final Term I;
	private final Term A;
	private final Term P;

	private final Set<Term> predicates = new HashSet<>();

	private final NameGenerator abstractAssertions = new NameGenerator("abstract");

	private final List<Function> functions;

	public PdrSmt(Node node, List<Function> functions, List<Frame> F, String property, String scratchBase) {
		super(SmtInterpolUtil.getScript(scratchBase));
		this.F = F;

		script.setOption(":produce-interpolants", true);
		script.setOption(":produce-unsat-cores", true);
		script.setOption(":simplify-interpolants", true);
		script.setLogic(Logics.QF_UFLIRA);
		script.setOption(":verbosity", 2);

		this.functions = functions;
		declareFunctions(functions);

		Lustre2Term lustre2Term = new Lustre2Term(script, node);
		this.varDecls = lustre2Term.getVariables();

		this.base = getVariables("");
		this.baseAbstract = getVariables("-");
		this.primeAbstract = getVariables("-'");
		this.prime = getVariables("'");

		this.I = lustre2Term.getInit();
		this.A = lustre2Term.getAssertions();
		defineTransitionRelation(lustre2Term.getTransition());
		this.P = lustre2Term.encodeProperty(property);

		assertAbstract(T(baseAbstract, primeAbstract));

		addPredicates(PredicateCollector.collect(I));
		addPredicates(PredicateCollector.collect(P));
	}

	private void declareFunctions(List<Function> functions) {
		for (Function function : functions) {
			SmtInterpolUtil.declareFunction(script, function);
		}
	}

	private void assertAbstract(Term t) {
		script.assertTerm(name(t, abstractAssertions.getNextName()));
	}

	private void defineTransitionRelation(Term transition) {
		TermVariable[] params = new TermVariable[base.length + prime.length];
		for (int i = 0; i < base.length; i++) {
			params[i] = variable(base[i]);
		}
		for (int i = 0; i < prime.length; i++) {
			params[i + base.length] = variable(prime[i]);
		}

		Term body = subst(transition, concat(base, prime), params);
		script.defineFun(Relation.T, params, script.sort("Bool"), body);
	}

	private TermVariable variable(Term v) {
		String name = variableName(v);
		return script.variable(name, v.getSort());
	}

	private String variableName(Term v) {
		if (v instanceof ApplicationTerm) {
			ApplicationTerm at = (ApplicationTerm) v;
			return at.getFunction().getName();
		} else {
			throw new IllegalArgumentException("Unexpected variable type: " + v.getClass().getSimpleName());
		}
	}

	private void addPredicates(Set<Term> otherPredicates) {
		otherPredicates.removeAll(predicates);
		predicates.addAll(otherPredicates);
		for (Term p : otherPredicates) {
			comment("New predicate: " + p);
			assertAbstract(term("=", apply(p, base), apply(p, baseAbstract)));
			assertAbstract(term("=", apply(p, primeAbstract), apply(p, prime)));
		}
		return;
	}

	public Cube getBadCube() {
		return extractCube(checkSat(and(R(depth()), not(P))));
	}

	private int depth() {
		return F.size() - 2;
	}

	private Term R(int k) {
		List<Term> conjuncts = new ArrayList<>();
		for (int i = k; i < F.size(); i++) {
			conjuncts.add(F.get(i).toTerm(script));
		}
		return and(conjuncts);
	}

	private Model checkSat(Term assertion) {
		script.push(1);
		script.assertTerm(assertion);
		switch (script.checkSat()) {
		case UNSAT:
			script.pop(1);
			return null;

		case SAT:
			Model model = script.getModel();
			script.pop(1);
			return model;

		default:
			commentUnknownReason();
			throw new StopException();
		}
	}

	private Cube extractCube(Model model) {
		if (model == null) {
			return null;
		}

		Cube result = new Cube();
		for (Term p : predicates) {
			result.addPLiteral(isTrue(model.evaluate(p)) ? p : not(p));
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
		return !cube.getPLiterals().contains(not(I));
	}

	public enum Option {
		DEFAULT, EXTRACT_MODEL, NO_IND
	};

	public TCube solveRelative(TCube s, Option option) {
		int frame = s.getFrame();
		Cube cube = s.getCube();

		script.push(1);

		if (option != Option.NO_IND) {
			script.assertTerm(not(cube));
		}

		for (int i = frame - 1; i < F.size() - 1; i++) {
			script.assertTerm(name(F.get(i).toTerm(script), "F" + i));
		}
		script.assertTerm(F.get(F.size() - 1).toTerm(script));

		List<Term> pLiterals = s.getCube().getPLiterals();
		for (int i = 0; i < pLiterals.size(); i++) {
			script.assertTerm(name(prime(pLiterals.get(i)), "P" + i));
		}

		switch (script.checkSat()) {
		case UNSAT:
			Term[] unsatCore = script.getUnsatCore();
			script.pop(1);
			int minFrame = getMinimumF(unsatCore);
			Cube minCube = getMinimalNonInitialCube(pLiterals, unsatCore);

			if (minFrame == F.size() - 2 || minFrame == TCube.FRAME_INF) {
				return new TCube(minCube, minFrame);
			} else {
				return new TCube(minCube, minFrame + 1);
			}

		case SAT:
			Cube c = null;
			if (option == Option.EXTRACT_MODEL) {
				c = extractCube(script.getModel());
			}
			script.pop(1);
			return new TCube(c, TCube.FRAME_NULL);

		default:
			commentUnknownReason();
			throw new StopException();
		}
	}

	private void commentUnknownReason() {
		comment("SMT solver returned 'unknown' due to " + script.getInfo(":reason-unknown"));
	}

	private int getMinimumF(Term[] unsatCore) {
		int min = TCube.FRAME_INF;
		for (Term t : unsatCore) {
			String name = t.toString();
			if (name.startsWith("F")) {
				int frame = Integer.parseInt(name.substring(1));
				if (frame < min) {
					min = frame;
				}
			}
		}
		return min;
	}

	private Cube getMinimalNonInitialCube(List<Term> pLiterals, Term[] unsatCore) {
		Cube result = new Cube();
		for (Term t : unsatCore) {
			String name = t.toString();
			if (name.startsWith("P")) {
				int pIndex = Integer.parseInt(name.substring(1));
				result.addPLiteral(pLiterals.get(pIndex));
			}
		}

		if (isInitial(result)) {
			result.addPLiteral(not(I));
		}

		return result;
	}

	public TCube solveRelative(TCube s) {
		return solveRelative(s, Option.DEFAULT);
	}

	public boolean isBlocked(TCube s) {
		int frame = s.getFrame();
		Cube cube = s.getCube();
		Term query = and(cube, R(frame));
		return checkSat(query) == null;
	}

	public Frame createInitialFrame() {
		return new Frame(I);
	}

	public void refine(List<Cube> cubes) {
		List<Term> pieces = new ArrayList<>();

		Term[] vars = getVariables(StreamIndex.getSuffix(-1));
		for (int i = 0; i < cubes.size() - 1; i++) {
			Term[] nextVars = getVariables(StreamIndex.getSuffix(i));
			pieces.add(and(apply(cubes.get(i), vars), T(vars, nextVars)));
			vars = nextVars;
		}
		pieces.add(and(apply(cubes.get(cubes.size() - 1), vars), not(P(vars))));

		Term[] interpolants = getInterpolants(pieces);

		for (int i = 0; i < cubes.size() - 1; i++) {
			vars = getVariables(StreamIndex.getSuffix(i));
			addPredicates(PredicateCollector.collect(subst(interpolants[i], vars, base)));
		}
	}

	private Term[] getInterpolants(List<Term> terms) {
		script.push(1);

		Term[] names = new Term[terms.size() + 1];
		for (int i = 0; i < terms.size(); i++) {
			String name = "I" + i;
			script.assertTerm(name(terms.get(i), name));
			names[i] = term(name);
		}
		// Ignore all variables related to abstract transition relation
		names[terms.size()] = and(term(abstractAssertions.getAllNames()));

		switch (script.checkSat()) {
		case UNSAT:
			Term[] interps = script.getInterpolants(names);
			script.pop(1);
			return interps;

		case SAT:
			int length = terms.size() - 1;
			SmtLib2Model extractedModel = extractModel(script.getModel(), length);
			throw new CounterexampleException(length, extractedModel);

		default:
			commentUnknownReason();
			throw new StopException();
		}
	}

	private SmtLib2Model extractModel(Model model, int length) {
		Map<String, Type> varTypes = new HashMap<>();
		for (int i = -1; i < length; i++) {
			for (VarDecl vd : varDecls) {
				String name = vd.id + StreamIndex.getSuffix(i);
				varTypes.put(name, vd.type);
			}
		}

		return SmtLib2Solver.parseSmtLib2Model(model.toString(), varTypes, functions);
	}

	public void comment(String comment) {
		script.echo(new QuotedObject(comment));
	}

	public Expr getInvariant(Cube cube) {
		List<Term> disjuncts = new ArrayList<>();

		for (Term literal : cube.getPLiterals()) {
			if (literal != not(I) && literal != A) {
				disjuncts.add(not(literal));
			}
		}

		return Term2Expr.disjunction(disjuncts);
	}

	private Term T(Term[] variables1, Term[] variables2) {
		return script.term(Relation.T, concat(variables1, variables2));
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

	private Term not(Cube cube) {
		return not(cube.toTerm(script));
	}

	private Term prime(Term term) {
		return subst(term, base, prime);
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

	private Term and(Cube cube, Term... terms) {
		return and(cube.toTerm(script), and(terms));
	}

	private Term name(Term term, String name) {
		return script.annotate(term, new Annotation(":named", name));
	}

	private List<Term> term(List<String> variables) {
		return variables.stream().map(this::term).collect(toList());
	}
}
