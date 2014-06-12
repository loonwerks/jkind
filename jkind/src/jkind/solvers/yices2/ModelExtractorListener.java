package jkind.solvers.yices2;

import java.math.BigInteger;

import jkind.solvers.BoolValue;
import jkind.solvers.NumericValue;
import jkind.solvers.Value;
import jkind.solvers.yices.YicesModel;
import jkind.solvers.yices2.Yices2Parser.AliasContext;
import jkind.solvers.yices2.Yices2Parser.FunctionValueContext;
import jkind.solvers.yices2.Yices2Parser.IntegerContext;
import jkind.solvers.yices2.Yices2Parser.IntegerNumericContext;
import jkind.solvers.yices2.Yices2Parser.NegativeIntegerContext;
import jkind.solvers.yices2.Yices2Parser.NumericContext;
import jkind.solvers.yices2.Yices2Parser.PositiveIntegerContext;
import jkind.solvers.yices2.Yices2Parser.QuotientContext;
import jkind.solvers.yices2.Yices2Parser.QuotientNumericContext;
import jkind.solvers.yices2.Yices2Parser.ValueContext;
import jkind.solvers.yices2.Yices2Parser.VariableContext;
import jkind.util.BigFraction;

public class ModelExtractorListener extends Yices2BaseListener {
	private YicesModel model = new YicesModel();
	
	public YicesModel getModel() {
		return model;
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
	public void enterFunctionValue(FunctionValueContext ctx) {
		String fn = ctx.ID().getText();
		BigInteger arg = integer(ctx.integer());
		Value value = value(ctx.value());
		model.addFunctionValue(fn, arg, value);
	}

	private Value value(ValueContext ctx) {
		if (ctx.BOOL() != null) {
			return BoolValue.fromBool(ctx.BOOL().getText().equals("true"));
		} else {
			return numericValue(ctx.numeric());
		}
	}
	
	private NumericValue numericValue(NumericContext ctx) {
		if (ctx instanceof IntegerNumericContext) {
			IntegerNumericContext ictx = (IntegerNumericContext) ctx;
			return new NumericValue(integer(ictx.integer()));
		} else if (ctx instanceof QuotientNumericContext) {
			QuotientNumericContext qctx = (QuotientNumericContext) ctx;
			return new NumericValue(quotient(qctx.quotient()));
		} else {
			throw new IllegalArgumentException();
		}
	}

	private BigFraction quotient(QuotientContext ctx) {
		return new BigFraction(integer(ctx.integer(0)), integer(ctx.integer(1)));
	}

	private BigInteger integer(IntegerContext ctx) {
		if (ctx instanceof PositiveIntegerContext) {
			PositiveIntegerContext pctx = (PositiveIntegerContext) ctx;
			return new BigInteger(pctx.INT().getText());
		} else if (ctx instanceof NegativeIntegerContext) {
			NegativeIntegerContext nctx = (NegativeIntegerContext) ctx;
			return new BigInteger(nctx.INT().getText()).negate();
		} else {
			throw new IllegalArgumentException();
		}
	}
}
