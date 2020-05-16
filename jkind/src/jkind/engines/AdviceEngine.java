package jkind.engines;

import java.util.List;

import jkind.JKindSettings;
import jkind.advice.Advice;
import jkind.engines.invariant.AbstractInvariantGenerationEngine;
import jkind.engines.invariant.ListInvariant;
import jkind.engines.invariant.StructuredInvariant;
import jkind.lustre.Expr;
import jkind.translation.Specification;

public class AdviceEngine extends AbstractInvariantGenerationEngine {
	public static final String NAME = "advice";
	private final List<Expr> potentialInvariants;

	public AdviceEngine(Specification spec, JKindSettings settings, Director director, Advice advice) {
		super(NAME, spec, settings, director);

		advice.prune(spec.node);
		this.potentialInvariants = advice.getInvariants();
	}

	@Override
	protected StructuredInvariant createInitialInvariant() {
		return new ListInvariant(potentialInvariants);
	}
}
