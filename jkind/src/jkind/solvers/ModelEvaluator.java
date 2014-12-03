package jkind.solvers;

import jkind.analysis.evaluation.Evaluator;
import jkind.lustre.IdExpr;
import jkind.lustre.values.Value;
import jkind.util.StreamIndex;

public class ModelEvaluator extends Evaluator {
	private final Model model;
	private final int index;
	
	public ModelEvaluator(Model model, int index) {
		super();
		this.model = model;
		this.index = index;
	}

	@Override
	public Value visit(IdExpr e) {
		return model.getValue(new StreamIndex(e.id, index));
	}
}
