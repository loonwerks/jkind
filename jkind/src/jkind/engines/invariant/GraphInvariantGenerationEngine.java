package jkind.engines.invariant;

import java.util.List;

import jkind.JKindSettings;
import jkind.engines.Director;
import jkind.lustre.Expr;
import jkind.translation.Specification;

public class GraphInvariantGenerationEngine extends AbstractInvariantGenerationEngine {
	public static final String NAME = "invariant-generation";

	public GraphInvariantGenerationEngine(Specification spec, JKindSettings settings, Director director) {
		super(NAME, spec, settings, director);
	}

	@Override
	protected GraphInvariant createInitialInvariant() {
		List<Expr> candidates = new CandidateGenerator(spec).generate();
		comment("Proposed " + candidates.size() + " candidates");
		return new GraphInvariant(candidates);
	}
}
