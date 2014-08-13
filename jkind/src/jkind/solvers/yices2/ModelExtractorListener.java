package jkind.solvers.yices2;

import java.math.BigInteger;
import java.util.Map;

import jkind.lustre.Type;
import jkind.lustre.values.BooleanValue;
import jkind.lustre.values.IntegerValue;
import jkind.lustre.values.RealValue;
import jkind.lustre.values.Value;
import jkind.solvers.yices.YicesModel;
import jkind.solvers.yices2.Yices2Parser.AliasContext;
import jkind.solvers.yices2.Yices2Parser.IntegerContext;
import jkind.solvers.yices2.Yices2Parser.IntegerNumericContext;
import jkind.solvers.yices2.Yices2Parser.NegativeIntegerContext;
import jkind.solvers.yices2.Yices2Parser.PositiveIntegerContext;
import jkind.solvers.yices2.Yices2Parser.QuotientContext;
import jkind.solvers.yices2.Yices2Parser.QuotientNumericContext;
import jkind.solvers.yices2.Yices2Parser.ValueContext;
import jkind.solvers.yices2.Yices2Parser.VariableContext;
import jkind.util.BigFraction;

public class ModelExtractorListener extends Yices2BaseListener {
	private final YicesModel model;
	
	public ModelExtractorListener(Map<String, Type> varTypes) {
		this.model = new YicesModel(varTypes);
	}

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

	private Value value(ValueContext ctx) {
		if (ctx.BOOL() != null) {
			return BooleanValue.fromBoolean(ctx.BOOL().getText().equals("true"));
		} else if (ctx.numeric() instanceof IntegerNumericContext) {
			IntegerNumericContext ictx = (IntegerNumericContext) ctx.numeric();
			return new IntegerValue(integer(ictx.integer()));
		} else if (ctx.numeric() instanceof QuotientNumericContext) {
			QuotientNumericContext qctx = (QuotientNumericContext) ctx.numeric();
			return new RealValue(quotient(qctx.quotient()));
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
