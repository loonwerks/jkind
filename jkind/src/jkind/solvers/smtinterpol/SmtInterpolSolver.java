package jkind.solvers.smtinterpol;

import java.util.ArrayList;
import java.util.List;

import de.uni_freiburg.informatik.ultimate.logic.Annotation;
import de.uni_freiburg.informatik.ultimate.logic.Logics;
import de.uni_freiburg.informatik.ultimate.logic.QuotedObject;
import de.uni_freiburg.informatik.ultimate.logic.Script;
import de.uni_freiburg.informatik.ultimate.logic.Sort;
import de.uni_freiburg.informatik.ultimate.logic.Term;
import de.uni_freiburg.informatik.ultimate.logic.TermVariable;
import jkind.JKindException;
import jkind.lustre.Function;
import jkind.lustre.Type;
import jkind.lustre.VarDecl;
import jkind.sexp.Cons;
import jkind.sexp.Sexp;
import jkind.sexp.Symbol;
import jkind.solvers.Model;
import jkind.solvers.Result;
import jkind.solvers.SatResult;
import jkind.solvers.Solver;
import jkind.solvers.UnknownResult;
import jkind.solvers.UnsatResult;
import jkind.solvers.smtlib2.SmtLib2Solver;
import jkind.translation.Relation;

public class SmtInterpolSolver extends Solver {
	private final Script script;

	public SmtInterpolSolver(String scratchBase) {
		this.script = SmtInterpolUtil.getScript(scratchBase);
	}

	@Override
	public void initialize() {
		script.setOption(":produce-unsat-cores", true);
		script.setLogic(Logics.QF_UFLIRA);
		script.setOption(":verbosity", 2);
	}

	@Override
	public void assertSexp(Sexp sexp) {
		script.assertTerm(convert(sexp));
	}

	@Override
	public void define(VarDecl decl) {
		varTypes.put(decl.id, decl.type);
		script.declareFun(decl.id, new Sort[0], getSort(decl.type));
	}

	@Override
	public void declare(Function function) {
		functions.add(function);
		SmtInterpolUtil.declareFunction(script, function);
	}

	@Override
	public void define(Relation relation) {
		TermVariable[] params = createTermVariables(relation.getInputs());
		Term definition = convert(params, relation.getBody());
		script.defineFun(relation.getName(), params, script.sort("Bool"), definition);
	}

	private TermVariable[] createTermVariables(List<VarDecl> inputs) {
		return inputs.stream().map(this::createTermVariable).toArray(i -> new TermVariable[i]);
	}

	private TermVariable createTermVariable(VarDecl decl) {
		return script.variable(decl.id, getSort(decl.type));
	}

	@Override
	public Result query(Sexp sexp) {
		Model model;

		push();
		assertSexp(new Cons("not", sexp));

		switch (script.checkSat()) {
		case SAT:
			model = extractModel(script.getModel());
			pop();
			return new SatResult(model);

		case UNSAT:
			pop();
			return new UnsatResult();

		case UNKNOWN:
			model = extractModel(script.getModel());
			pop();
			return new UnknownResult(model);
		}

		throw new JKindException("Unhandled result from solver");
	}

	@Override
	protected Result quickCheckSat(List<Symbol> activationLiterals) {
		push();

		for (Symbol actLit : activationLiterals) {
			String name = "_" + actLit.str;
			script.assertTerm(script.annotate(convert(actLit), new Annotation(":named", name)));
		}

		switch (script.checkSat()) {
		case SAT:
			pop();
			return new SatResult();

		case UNSAT:
			List<Symbol> unsatCore = new ArrayList<>();
			for (Term t : script.getUnsatCore()) {
				unsatCore.add(new Symbol(t.toString().substring(1)));
			}
			pop();
			return new UnsatResult(unsatCore);

		case UNKNOWN:
			pop();
			return new UnknownResult();
		}

		throw new JKindException("Unhandled result from solver");
	}

	private Model extractModel(de.uni_freiburg.informatik.ultimate.logic.Model model) {
		return SmtLib2Solver.parseSmtLib2Model(model.toString(), varTypes, functions);
	}

	@Override
	public void push() {
		script.push(1);
	}

	@Override
	public void pop() {
		script.pop(1);
	}

	@Override
	public void comment(String str) {
		script.echo(new QuotedObject(str));
	}

	@Override
	public void stop() {
	}

	private Sort getSort(Type type) {
		return SmtInterpolUtil.getSort(script, type);
	}

	private Term convert(TermVariable[] params, Sexp sexp) {
		return SmtInterpolUtil.convert(script, params, sexp);
	}

	private Term convert(Sexp sexp) {
		return SmtInterpolUtil.convert(script, sexp);
	}
}
