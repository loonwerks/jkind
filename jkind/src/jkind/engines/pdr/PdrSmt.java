package jkind.engines.pdr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jkind.engines.StopException;
import jkind.lustre.Expr;
import jkind.lustre.LustreUtil;
import jkind.lustre.Node;
import jkind.lustre.Type;
import jkind.lustre.VarDecl;
import jkind.solvers.smtinterpol.ScriptUser;
import jkind.solvers.smtinterpol.SmtInterpolUtil;
import jkind.solvers.smtinterpol.Subst;
import jkind.translation.TransitionRelation;
import de.uni_freiburg.informatik.ultimate.logic.Annotation;
import de.uni_freiburg.informatik.ultimate.logic.ApplicationTerm;
import de.uni_freiburg.informatik.ultimate.logic.Logics;
import de.uni_freiburg.informatik.ultimate.logic.Model;
import de.uni_freiburg.informatik.ultimate.logic.QuotedObject;
import de.uni_freiburg.informatik.ultimate.logic.Sort;
import de.uni_freiburg.informatik.ultimate.logic.Term;
import de.uni_freiburg.informatik.ultimate.logic.TermVariable;

public class PdrSmt extends ScriptUser {
	private final List<Frame> F;

	private final List<VarDecl> varDecls;
	private final Map<String, Term[]> variableLists = new HashMap<>();
	private final Term[] base;
	private final Term[] prime;

	private final Predicate I;
	private final Term P;

	private final Set<Predicate> predicates = new HashSet<>();
	private final Term[] baseAbstract;
	private final Term[] primeAbstract;
	
	private final PdrInterpolatingSolver interp;

	public PdrSmt(Node node, List<Frame> F, String property, String scratchBase) {
		super(SmtInterpolUtil.getScript(scratchBase));
		this.F = F;

		script.setOption(":produce-interpolants", true);
		script.setOption(":produce-unsat-cores", true);
		script.setOption(":simplify-interpolants", true);
		script.setLogic(Logics.QF_UFLIRA);
		script.setOption(":verbosity", 2);

		Lustre2Term lustre2Term = new Lustre2Term(script, node);
		this.varDecls = lustre2Term.getVariables();

		this.base = getVariables("");
		this.prime = getVariables("'");

		this.I = lustre2Term.getInit();
		defineTransitionRelation(lustre2Term.getTransition());
		this.P = lustre2Term.encodeProperty(property);

		this.baseAbstract = getVariables("-");
		this.primeAbstract = getVariables("-'");
		script.assertTerm(T(baseAbstract, primeAbstract));

		addPredicate(I);
		addPredicates(PredicateCollector.collect(P));
		
		this.interp = new PdrInterpolatingSolver(node, property, scratchBase == null ? null : scratchBase + "-interpolate");
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

	private void addPredicates(Set<Predicate> otherPredicates) {
		otherPredicates.forEach(this::addPredicate);
	}

	private void addPredicate(Predicate p) {
		if (predicates.add(p)) {
			script.assertTerm(term("=", apply(p, base), apply(p, baseAbstract)));
			script.assertTerm(term("=", apply(p, primeAbstract), apply(p, prime)));
		}
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
		for (Predicate p : predicates) {
			Term t = p.toTerm(script);
			result.addPLiteral(new PLiteral(isTrue(model.evaluate(t)), p));
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

		List<PLiteral> pLiterals = s.getCube().getPLiterals();
		for (int i = 0; i < pLiterals.size(); i++) {
			script.assertTerm(name(prime(pLiterals.get(i).toTerm(script)), "P" + i));
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

	private Cube getMinimalNonInitialCube(List<PLiteral> pLiterals, Term[] unsatCore) {
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
		return new Frame(I.toTerm(script));
	}

	public void refine(List<Cube> cubes) {
		addPredicates(interp.refine(cubes));
	}

	public void comment(String comment) {
		script.echo(new QuotedObject(comment));
	}

	public Expr getInvariant(Cube cube) {
		List<Expr> disjuncts = new ArrayList<>();

		for (PLiteral literal : cube.getPLiterals()) {
			if (!literal.equals(not(I))) {
				disjuncts.add(literal.negate().toExpr());
			}
		}

		return LustreUtil.or(disjuncts);
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

	private Term apply(Predicate p, Term[] arguments) {
		return apply(p.toTerm(script), arguments);
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

	private PLiteral not(Predicate p) {
		return new PLiteral(false, p);
	}
}
