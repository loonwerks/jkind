package jkind.solvers.smtlib2;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jkind.lustre.Function;
import jkind.lustre.Type;
import jkind.sexp.Cons;
import jkind.sexp.Sexp;
import jkind.sexp.Symbol;
import jkind.solvers.smtlib2.SmtLib2Parser.ArgContext;
import jkind.solvers.smtlib2.SmtLib2Parser.BodyContext;
import jkind.solvers.smtlib2.SmtLib2Parser.ConsBodyContext;
import jkind.solvers.smtlib2.SmtLib2Parser.DefineContext;
import jkind.solvers.smtlib2.SmtLib2Parser.IdContext;
import jkind.solvers.smtlib2.SmtLib2Parser.ModelContext;
import jkind.solvers.smtlib2.SmtLib2Parser.SymbolBodyContext;
import jkind.solvers.smtlib2.SmtLib2Parser.TypeContext;

public class ModelExtractor {
	public static SmtLib2Model getModel(ModelContext ctx, Map<String, Type> varTypes, List<Function> functions) {
		SmtLib2Model model = new SmtLib2Model(varTypes, functions);
		for (DefineContext defineCtx : ctx.define()) {
			walkDefine(defineCtx, model);
		}
		return model;
	}

	public static void walkDefine(DefineContext ctx, SmtLib2Model model) {
		String var = getId(ctx.id());
		Sexp args = getArgs(ctx.arg());
		Sexp body = sexp(ctx.body());
		if (args != null) {
			body = new Cons("lambda", args, body);
		}
		model.addValue(var, body);
	}

	private static Sexp getArgs(List<ArgContext> argContexts) {
		if (argContexts.size() == 0) {
			return null;
		}
		List<Sexp> cons = new ArrayList<>();
		for (ArgContext argCtx : argContexts) {
			String id = getId(argCtx.id());
			String type = getType(argCtx.type());
			cons.add(new Cons(id, new Symbol(type)));
		}
		return new Cons(cons);
	}

	private static String getId(IdContext id) {
		return Quoting.unquote(id.getText());
	}

	private static String getType(TypeContext id) {
		return Quoting.unquote(id.getText());
	}

	private static Sexp sexp(BodyContext ctx) {
		if (ctx instanceof SymbolBodyContext) {
			SymbolBodyContext sbc = (SymbolBodyContext) ctx;
			return new Symbol(parse(sbc.symbol().getText()));
		} else if (ctx instanceof ConsBodyContext) {
			ConsBodyContext cbc = (ConsBodyContext) ctx;
			List<Sexp> args = new ArrayList<>();
			for (BodyContext sub : cbc.body()) {
				args.add(sexp(sub));
			}
			return new Cons(cbc.fn().getText(), args);
		} else {
			throw new IllegalArgumentException();
		}
	}

	private static String parse(String string) {
		if (string.contains(".")) {
			BigDecimal d = new BigDecimal(string);
			BigInteger numerator = d.unscaledValue();
			BigInteger denominator = BigDecimal.TEN.pow(d.scale()).toBigInteger();

			BigInteger gcd = numerator.gcd(denominator);
			numerator = numerator.divide(gcd);
			denominator = denominator.divide(gcd);

			if (denominator.equals(BigInteger.ONE)) {
				return numerator.toString();
			} else {
				return numerator + "/" + denominator;
			}
		} else {
			return string;
		}
	}
}
