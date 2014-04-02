package jkind.solvers.yices;

import java.util.ArrayList;
import java.util.List;

import jkind.solvers.BoolValue;
import jkind.solvers.Label;
import jkind.solvers.NumericValue;
import jkind.solvers.Result;
import jkind.solvers.SatResult;
import jkind.solvers.UnsatResult;
import jkind.solvers.Value;
import jkind.solvers.yices.YicesParser.AliasContext;
import jkind.solvers.yices.YicesParser.FunctionContext;
import jkind.solvers.yices.YicesParser.SatResultContext;
import jkind.solvers.yices.YicesParser.UnsatCoreContext;
import jkind.solvers.yices.YicesParser.UnsatResultContext;
import jkind.solvers.yices.YicesParser.ValueContext;
import jkind.solvers.yices.YicesParser.VariableContext;

import org.antlr.v4.runtime.tree.TerminalNode;

public class ResultExtractorListener extends YicesBaseListener {
	private Result result;
	private YicesModel model;
	
	public Result getResult() {
		return result;
	}

	@Override
	public void enterSatResult(SatResultContext ctx) {
		model = new YicesModel();
		result = new SatResult(model);
	}
	
	@Override
	public void enterUnsatResult(UnsatResultContext ctx) {
		result = new UnsatResult();
	}
	
	@Override
	public void enterUnsatCore(UnsatCoreContext ctx) {
		List<Label> unsatCore = ((UnsatResult) result).getUnsatCore();
		for (TerminalNode node : ctx.INT()) {
			unsatCore.add(new Label(node.getText()));
		}
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
		int n = ctx.value().size();
		
		List<Value> inputs = new ArrayList<>();
		for (ValueContext inputCtx : ctx.value().subList(0, n - 1)) {
			inputs.add(value(inputCtx));
		}
		Value output = value(ctx.value().get(n - 1));
		model.addFunctionValue(fn, inputs, output);
	}

	private Value value(ValueContext ctx) {
		if (ctx.BOOL() != null) {
			return BoolValue.fromBool(ctx.BOOL().getText().equals("true"));
		} else {
			return new NumericValue(ctx.numeric().getText());
		}
	}
}
