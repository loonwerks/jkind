package jkind.engines.invariant;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import jkind.lustre.Expr;

public class InvariantSet {
	private final List<Expr> invariants = new ArrayList<>();
	private final Set<String> uniqueRepresentations = new HashSet<>();

	public void add(Expr invariant) {
		if (uniqueRepresentations.add(invariant.toString())) {
			invariants.add(invariant);
		}
	}

	public void addAll(Collection<Expr> invariants) {
		invariants.forEach(this::add);
	}

	public List<Expr> getInvariants() {
		return invariants;
	}

	public void removeIf(Predicate<Expr> predicate) {
		Iterator<Expr> iterator = invariants.iterator();
		while (iterator.hasNext()) {
			Expr invariant = iterator.next();
			if (predicate.test(invariant)) {
				iterator.remove();
				uniqueRepresentations.remove(invariant.toString());
			}
		}
	}
}
