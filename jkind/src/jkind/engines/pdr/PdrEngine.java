package jkind.engines.pdr;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import jkind.JKindSettings;
import jkind.engines.Director;
import jkind.engines.Engine;
import jkind.engines.invariant.InvariantSet;
import jkind.engines.messages.BaseStepMessage;
import jkind.engines.messages.InductiveCounterexampleMessage;
import jkind.engines.messages.InvalidMessage;
import jkind.engines.messages.InvariantMessage;
import jkind.engines.messages.UnknownMessage;
import jkind.engines.messages.ValidMessage;
import jkind.lustre.Equation;
import jkind.lustre.Expr;
import jkind.lustre.IdExpr;
import jkind.translation.ContainsTemporalOperator;
import jkind.translation.Specification;

public class PdrEngine extends Engine {
	public static final String NAME = "pdr";
	private final ConcurrentMap<String, PdrSubengine> subengines = new ConcurrentHashMap<>();
	private int scratchCounter = 1;
	private InvariantSet invariants = new InvariantSet();

	public PdrEngine(Specification spec, JKindSettings settings, Director director) {
		super(NAME, spec, settings, director);
	}

	@Override
	protected void main() {
		while (!done()) {
			processMessagesAndWaitUntil(() -> done() || canSpawnSubengine());
			if (canSpawnSubengine()) {
				spawnSubengine();
			}
		}
	}

	private boolean done() {
		return throwable != null || (properties.isEmpty() && subengines.isEmpty());
	}

	private boolean canSpawnSubengine() {
		return subengines.size() < settings.pdrMax && !properties.isEmpty();
	}

	private void spawnSubengine() {
		String prop = properties.remove(0);
		String scratch = settings.scratch ? getScratchBase() + scratchCounter++ : null;
		PdrSubengine subengine = new PdrSubengine(prop, invariants.getInvariants(), spec, scratch, this, director);
		subengines.put(prop, subengine);
		subengine.start();
	}

	public void reportUnknown(String prop) {
		subengines.remove(prop);
		director.receiveMessage(new UnknownMessage(getName(), prop));
	}

	public void reportThrowable(Throwable throwable) {
		this.throwable = throwable;
	}

	@Override
	protected void handleMessage(BaseStepMessage bsm) {
	}

	@Override
	protected void handleMessage(InductiveCounterexampleMessage icm) {
	}

	@Override
	protected void handleMessage(InvalidMessage im) {
		cancel(im.invalid);
	}

	@Override
	protected void handleMessage(InvariantMessage im) {
		addInvariants(im.invariants);
	}

	private void addInvariants(List<Expr> invs) {
		invariants.addAll(invs);
		for (PdrSubengine subengine : subengines.values()) {
			subengine.recieveInvariants(invs);
		}
	}

	@Override
	protected void handleMessage(UnknownMessage um) {
		cancel(um.unknown);
	}

	@Override
	protected void handleMessage(ValidMessage vm) {
		cancel(vm.valid);
		
		List<Expr> invs = new ArrayList<>();
		for (String valid : vm.valid) {
			invs.add(new IdExpr(valid));
			Expr expr = lookupEquation(valid);
			if (!ContainsTemporalOperator.check(expr)) {
				invs.add(expr);
			}
		}
		addInvariants(invs);
	}

	private Expr lookupEquation(String valid) {
		for (Equation eq : this.spec.node.equations) {
			if (eq.lhs.get(0).id.equals(valid)) {
				return eq.expr;
			}
		}
		throw new IllegalArgumentException("Failed to find property: " + valid);
	}

	private void cancel(List<String> cancel) {
		for (String prop : new HashSet<>(subengines.keySet())) {
			if (cancel.contains(prop)) {
				PdrSubengine subengine = subengines.remove(prop);
				if (subengine != null) {
					subengine.cancel();
				}
			}
		}
		properties.removeAll(cancel);
	}
}
