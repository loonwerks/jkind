package jkind.solvers.dreal;

import java.math.BigInteger;
import java.util.Map;

import jkind.interval.IntEndpoint;
import jkind.interval.NumericEndpoint;
import jkind.interval.NumericInterval;
import jkind.interval.RealEndpoint;
import jkind.lustre.Type;
import jkind.lustre.values.BooleanValue;
import jkind.lustre.values.Value;
import jkind.solvers.dreal.parser.DRealModelParser.BoolValContext;
import jkind.solvers.dreal.parser.DRealModelParser.InfinityValContext;
import jkind.solvers.dreal.parser.DRealModelParser.IntegerValContext;
import jkind.solvers.dreal.parser.DRealModelParser.ModelContext;
import jkind.solvers.dreal.parser.DRealModelParser.NumberRangeValContext;
import jkind.solvers.dreal.parser.DRealModelParser.Number_valueContext;
import jkind.solvers.dreal.parser.DRealModelParser.RealValContext;
import jkind.solvers.dreal.parser.DRealModelParser.SymbolContext;
import jkind.solvers.dreal.parser.DRealModelParser.Var_assignContext;
import jkind.solvers.dreal.parser.DRealModelParser.Var_valueContext;
import jkind.solvers.dreal.parser.DRealModelParser.WarningContext;
import jkind.solvers.smtlib2.Quoting;
import jkind.util.BigFraction;

public class ModelExtractor {
	public static DRealModel getModel(ModelContext ctx, Map<String, Type> varTypes) {
		DRealModel model = new DRealModel(varTypes);
		for (Var_assignContext defineCtx : ctx.var_assign()) {
			walkVarAssign(defineCtx, model);
		}
		for (WarningContext warnCtx : ctx.warning()) {
			walkWarning(warnCtx, model); 
		}
		return model;
	}

	public static void walkWarning(WarningContext ctx, DRealModel model) {
		model.addWarning(ctx.getText());
	}
	
	public static void walkVarAssign(Var_assignContext ctx, DRealModel model) {
		String var = getId(ctx.symbol());
		Value val = getVal(ctx.var_value());
		model.addValue(var, val);
	}

	private static String getId(SymbolContext id) {
		return Quoting.unquote(id.getText());
	}

	private static Value getVal(Var_valueContext ctx) {
		if (ctx instanceof NumberRangeValContext) {
			NumberRangeValContext nr_ctx = (NumberRangeValContext)ctx;
			NumericEndpoint low = getNumber(nr_ctx.number_value(0));
			NumericEndpoint high = getNumber(nr_ctx.number_value(1));
			return new NumericInterval(low, high);
		} else if (ctx instanceof BoolValContext) {
			String value = ((BoolValContext) ctx).three_val_bool().getText();
			if ("undef".equals(value)) {
				return null;
			} else if ("true".equals(value)) {
				return BooleanValue.TRUE;
			} else if ("false".equals(value)) {
				return BooleanValue.FALSE;
			} else {
				throw new IllegalArgumentException("Boolean value " + value + " is not true, false, or undef");
			}
		} else {
			throw new IllegalArgumentException("Unable to parse model value: " + ctx.toString());
		}
	}
	

	private static NumericEndpoint getNumber(Number_valueContext ctx) {
		if (ctx instanceof InfinityValContext) {
			if (ctx.getText().startsWith("-")) {
				return RealEndpoint.NEGATIVE_INFINITY;
			} else {
				return RealEndpoint.POSITIVE_INFINITY;
			}
		} else if (ctx instanceof RealValContext) {
			double val = Double.parseDouble(ctx.getText()); 
			return new RealEndpoint(BigFraction.fromValue(val));
		}
		else {
			throw new IllegalArgumentException("Unknown number type in getNumber()");
		}
	}
}
