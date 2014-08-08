package jkind.solvers.yices;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import jkind.lustre.Type;
import jkind.lustre.values.Value;
import jkind.solvers.Label;
import jkind.solvers.Result;
import jkind.solvers.SatResult;
import jkind.solvers.UnsatResult;
import jkind.solvers.yices.YicesParser.AliasContext;
import jkind.solvers.yices.YicesParser.FunctionContext;
import jkind.solvers.yices.YicesParser.SatResultContext;
import jkind.solvers.yices.YicesParser.UnsatCoreContext;
import jkind.solvers.yices.YicesParser.UnsatResultContext;
import jkind.solvers.yices.YicesParser.VariableContext;
import jkind.util.Util;

import org.antlr.v4.runtime.tree.TerminalNode;

public class ResultExtractorListener extends YicesBaseListener {
	private Result result;
	private YicesModel model;
	private final Map<String, Type> streamTypes;
	
	public ResultExtractorListener(Map<String, Type> streamTypes) {
		this.streamTypes = streamTypes;
	}

	public Result getResult() {
		return result;
	}

	@Override
	public void enterSatResult(SatResultContext ctx) {
		model = new YicesModel(streamTypes);
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
		model.addValue(ctx.ID().getText(), Util.parseValue("int", ctx.value().getText()));
	}

	@Override
	public void enterFunction(FunctionContext ctx) {
		String fn = ctx.ID().getText();
		BigInteger arg = new BigInteger(ctx.integer().getText());
		Value value = Util.parseValue(Util.getName(streamTypes.get(fn)), ctx.value().getText());
		model.addFunctionValue(fn, arg, value);
	}
}
