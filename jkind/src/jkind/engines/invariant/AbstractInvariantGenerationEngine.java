package jkind.engines.invariant;

import java.util.ArrayList;
import java.util.List;

import jkind.JKindSettings;
import jkind.engines.Director;
import jkind.engines.SolverBasedEngine;
import jkind.engines.StopException;
import jkind.engines.messages.BaseStepMessage;
import jkind.engines.messages.InductiveCounterexampleMessage;
import jkind.engines.messages.InvalidMessage;
import jkind.engines.messages.InvariantMessage;
import jkind.engines.messages.Itinerary;
import jkind.engines.messages.UnknownMessage;
import jkind.engines.messages.ValidMessage;
import jkind.lustre.Expr;
import jkind.lustre.IdExpr;
import jkind.sexp.Cons;
import jkind.sexp.Sexp;
import jkind.solvers.Model;
import jkind.solvers.ModelEvaluator;
import jkind.solvers.Result;
import jkind.solvers.UnsatResult;
import jkind.translation.Specification;
import jkind.util.SexpUtil;

public abstract class AbstractInvariantGenerationEngine extends SolverBasedEngine {
	private final InvariantSet provenInvariants = new InvariantSet();

	public AbstractInvariantGenerationEngine(String name, Specification spec, JKindSettings settings,
			Director director) {
		super(name, spec, settings, director);
	}

	@Override
	public void main() {
		StructuredInvariant invariant = createInitialInvariant();
		if (invariant.isTrivial()) {
			comment("No invariants proposed");
			return;
		}

		createVariables(-1);
		createVariables(0);
		for (int k = 1; k <= settings.n; k++) {
			comment("K = " + k);

			refineBaseStep(k - 1, invariant);
			if (invariant.isTrivial()) {
				comment("No invariants remaining after base step");
				return;
			}

			createVariables(k);
			refineInductiveStep(k, invariant);
		}
	}

	protected abstract StructuredInvariant createInitialInvariant();

	private void refineBaseStep(int k, StructuredInvariant invariant) {
		solver.push();
		Result result;

		for (int i = 0; i <= k; i++) {
			assertBaseTransition(i);
		}

		do {
			checkForStop();

			Sexp query = SexpUtil.conjoinInvariants(invariant.toExprs(), k);
			result = solver.query(query);

			if (!(result instanceof UnsatResult)) {
				Model model = getModel(result);
				if (model == null) {
					comment("No model - unable to continue");
					throw new StopException();
				}
				invariant.refine(new ModelEvaluator(model, k));
				comment("Finished single base step refinement");
			}
		} while (!invariant.isTrivial() && !(result instanceof UnsatResult));

		solver.pop();
	}

	private void refineInductiveStep(int k, StructuredInvariant original) {
		solver.push();
		StructuredInvariant invariant = original.copy();
		Result result;

		for (int i = 0; i <= k; i++) {
			assertInvariants(provenInvariants, i);
			assertInductiveTransition(i);
		}

		do {
			checkForStop();

			result = solver.query(getInductiveQuery(k, invariant));

			if (!(result instanceof UnsatResult)) {
				Model model = getModel(result);
				if (model == null) {
					comment("No model - unable to continue");
					throw new StopException();
				}
				invariant.refine(new ModelEvaluator(model, k));
				comment("Finished single inductive step refinement");
			}
		} while (!invariant.isTrivial() && !(result instanceof UnsatResult));

		solver.pop();

		List<Expr> newInvariants = invariant.toFinalInvariants();
		provenInvariants.addAll(newInvariants);
		sendValidProperties(newInvariants, k);
		sendInvariants(newInvariants);

		original.reduceProven(invariant);
		return;
	}

	private void assertInvariants(InvariantSet set, int i) {
		solver.assertSexp(SexpUtil.conjoinInvariants(set.getInvariants(), i));
	}

	private void checkForStop() {
		processMessages();
		if (properties.isEmpty()) {
			throw new StopException();
		}
	}

	private Sexp getInductiveQuery(int k, StructuredInvariant invariant) {
		List<Expr> exprs = invariant.toExprs();
		List<Sexp> hyps = new ArrayList<>();
		for (int i = 0; i < k; i++) {
			hyps.add(SexpUtil.conjoinInvariants(exprs, i));
		}
		Sexp conc = SexpUtil.conjoinInvariants(exprs, k);

		return new Cons("=>", SexpUtil.conjoin(hyps), conc);
	}

	private void sendValidProperties(List<Expr> newInvariants, int k) {
		List<String> valid = new ArrayList<>();
		for (Expr inv : newInvariants) {
			if (inv instanceof IdExpr) {
				IdExpr idExpr = (IdExpr) inv;
				if (properties.contains(idExpr.id)) {
					properties.remove(idExpr.id);
					valid.add(idExpr.id);
				}
			}
		}

		if (!valid.isEmpty()) {
			Itinerary itinerary = director.getValidMessageItinerary();
			List<Expr> invariants = provenInvariants.getInvariants();
			director.broadcast(
					new ValidMessage(getName(), valid, k, getRuntime(), invariants, null, itinerary, null, false));
		}
	}

	private void sendInvariants(List<Expr> newInvariants) {
		comment("Sending invariants:");
		for (Expr inv : newInvariants) {
			comment("  " + inv);
		}

		director.broadcast(new InvariantMessage(newInvariants));
	}

	@Override
	protected void handleMessage(BaseStepMessage bsm) {
	}

	@Override
	protected void handleMessage(InductiveCounterexampleMessage icm) {
	}

	@Override
	protected void handleMessage(InvalidMessage im) {
		properties.removeAll(im.invalid);
	}

	@Override
	protected void handleMessage(InvariantMessage im) {
	}

	@Override
	protected void handleMessage(UnknownMessage um) {
		properties.removeAll(um.unknown);
	}

	@Override
	protected void handleMessage(ValidMessage vm) {
		properties.removeAll(vm.valid);
	}

	private double getRuntime() {
		return (System.currentTimeMillis() - director.startTime) / 1000.0;
	}
}
