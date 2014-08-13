package jkind.solvers.yices;

import java.util.List;
import java.util.Map;

import jkind.lustre.Type;
import jkind.solvers.Label;
import jkind.solvers.Result;
import jkind.solvers.SatResult;
import jkind.solvers.UnsatResult;
import jkind.solvers.yices.YicesParser.AliasContext;
import jkind.solvers.yices.YicesParser.SatResultContext;
import jkind.solvers.yices.YicesParser.UnsatCoreContext;
import jkind.solvers.yices.YicesParser.UnsatResultContext;
import jkind.solvers.yices.YicesParser.VariableContext;
import jkind.util.Util;

import org.antlr.v4.runtime.tree.TerminalNode;

public class ResultExtractorListener extends YicesBaseListener {
	private Result result;
	private YicesModel model;
	private final Map<String, Type> varTypes;

	public ResultExtractorListener(Map<String, Type> varTypes) {
		this.varTypes = varTypes;
	}

	public Result getResult() {
		return result;
	}

	@Override
	public void enterSatResult(SatResultContext ctx) {
		model = new YicesModel(varTypes);
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
		String var = ctx.ID().getText();
		Type type = varTypes.get(var);
		if (type != null) {
			model.addValue(var, Util.parseValue(type, ctx.value().getText()));
		}
	}
}
