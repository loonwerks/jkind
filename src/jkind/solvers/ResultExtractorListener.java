package jkind.solvers;

import java.math.BigInteger;

import jkind.solvers.SolverResult.Result;
import jkind.solvers.YicesParser.AliasContext;
import jkind.solvers.YicesParser.FunctionContext;
import jkind.solvers.YicesParser.ModelContext;
import jkind.solvers.YicesParser.SolverResultContext;
import jkind.solvers.YicesParser.ValueContext;
import jkind.solvers.YicesParser.VariableContext;

public class ResultExtractorListener extends YicesBaseListener {
	private SolverResult solverResult;
	private Model model;

	public SolverResult getSolverResult() {
		return solverResult;
	}

	@Override
	public void exitSolverResult(SolverResultContext ctx) {
		Result result = ctx.RESULT().getText().equals("sat") ? Result.SAT : Result.UNSAT;
		solverResult = new SolverResult(result, model);
	}
	
	@Override
	public void enterModel(ModelContext ctx) {
		model = new Model();
	}
	
	@Override
	public void enterAlias(AliasContext ctx) {
		model.addAlias(ctx.ID(0).getText(), ctx.ID(1).getText());
	}

	@Override
	public void enterVariable(VariableContext ctx) {
		model.addValue(ctx.ID().getText(), value(ctx.value()));
	}

	@Override
	public void enterFunction(FunctionContext ctx) {
		String fn = ctx.ID().getText();
		BigInteger arg = new BigInteger(ctx.integer().getText());
		Value value = value(ctx.value());
		model.addFunctionValue(fn, arg, value);
	}

	private Value value(ValueContext ctx) {
		if (ctx.BOOL() != null) {
			return ctx.BOOL().getText().equals("true") ? BoolValue.TRUE : BoolValue.FALSE;
		} else {
			return new NumericValue(ctx.numeric().getText());
		}
	}
}
