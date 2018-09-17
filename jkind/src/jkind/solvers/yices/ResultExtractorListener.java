package jkind.solvers.yices;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.antlr.v4.runtime.tree.TerminalNode;

import jkind.lustre.Function;
import jkind.lustre.Type;
import jkind.lustre.values.Value;
import jkind.results.FunctionTable;
import jkind.sexp.Symbol;
import jkind.solvers.Result;
import jkind.solvers.SatResult;
import jkind.solvers.UnsatResult;
import jkind.solvers.yices.YicesParser.AliasContext;
import jkind.solvers.yices.YicesParser.FunctionContext;
import jkind.solvers.yices.YicesParser.SatResultContext;
import jkind.solvers.yices.YicesParser.UnsatCoreContext;
import jkind.solvers.yices.YicesParser.UnsatResultContext;
import jkind.solvers.yices.YicesParser.VariableContext;
import jkind.util.SexpUtil;
import jkind.util.Util;

public class ResultExtractorListener extends YicesBaseListener {
	private Result result;
	private YicesModel model;
	private List<Symbol> unsatCore;
	private final Map<String, Type> varTypes;
	private final List<Function> functions;

	public ResultExtractorListener(Map<String, Type> varTypes, List<Function> functions) {
		this.varTypes = varTypes;
		this.functions = functions;
	}

	public Result getResult() {
		return result;
	}

	@Override
	public void enterSatResult(SatResultContext ctx) {
		model = new YicesModel(varTypes, functions);
		result = new SatResult(model);
	}

	@Override
	public void enterUnsatResult(UnsatResultContext ctx) {
		unsatCore = new ArrayList<>();
	}

	@Override
	public void enterUnsatCore(UnsatCoreContext ctx) {
		for (TerminalNode node : ctx.INT()) {
			unsatCore.add(new Symbol(node.getText()));
		}
	}

	@Override
	public void exitUnsatResult(UnsatResultContext ctx) {
		result = new UnsatResult(unsatCore);
	}

	@Override
	public void enterAlias(AliasContext ctx) {
		model.addAlias(ctx.ID(0).getText(), ctx.ID(1).getText());
	}

	@Override
	public void enterVariable(VariableContext ctx) {
		String var = ctx.ID().getText();
		if (SexpUtil.isEncodedFunction(var)) {
			// Nullary function value
			FunctionTable table = model.getFunctionTable(var);
			Type type = table.getOutput().type;
			Value value = Util.parseValue(type, ctx.value().getText());
			table.addRow(Collections.emptyList(), value);
		} else {
			Type type = varTypes.get(var);
			if (type != null) {
				model.addValue(var, Util.parseValue(type, ctx.value().getText()));
			}
		}
	}

	@Override
	public void enterFunction(FunctionContext ctx) {
		String name = ctx.ID().getText();
		FunctionTable table = model.getFunctionTable(name);

		int n = ctx.value().size();
		List<Value> inputs = new ArrayList<>();
		for (int i = 0; i < n - 1; i++) {
			String type = Util.getName(table.getInputs().get(i).type);
			inputs.add(Util.parseValue(type, ctx.value(i).getText()));
		}

		String type = table.getOutput().type.toString();
		Value output = Util.parseValue(type, ctx.value(n - 1).getText());

		table.addRow(inputs, output);
	}
}
