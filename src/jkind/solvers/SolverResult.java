package jkind.solvers;

public class SolverResult {
	public enum Result {
		SAT, UNSAT
	};

	private Result result;
	private Model model;

	public SolverResult(Result result, Model model) {
		this.result = result;
		this.model = model;
	}

	public Result getResult() {
		return result;
	}

	public Model getModel() {
		return model;
	}
	
	@Override
	public String toString() {
		return result.toString();
	}
}
