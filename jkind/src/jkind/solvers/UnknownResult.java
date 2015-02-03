package jkind.solvers;

public class UnknownResult extends Result {
	final private Model model;

	public UnknownResult(Model model) {
		super();
		this.model = model;
	}

	public UnknownResult() {
		this(null);
	}

	public Model getModel() {
		return model;
	}
}
