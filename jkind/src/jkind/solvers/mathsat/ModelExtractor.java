package jkind.solvers.mathsat;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jkind.lustre.Type;
import jkind.sexp.Cons;
import jkind.sexp.Sexp;
import jkind.sexp.Symbol;
import jkind.solvers.mathsat.MathSatParser.AssignmentContext;
import jkind.solvers.mathsat.MathSatParser.BodyContext;
import jkind.solvers.mathsat.MathSatParser.ConsBodyContext;
import jkind.solvers.mathsat.MathSatParser.IdContext;
import jkind.solvers.mathsat.MathSatParser.ModelContext;
import jkind.solvers.mathsat.MathSatParser.SymbolBodyContext;
import jkind.solvers.smtlib2.Quoting;
import jkind.solvers.smtlib2.SmtLib2Model;

public class ModelExtractor {
	public static SmtLib2Model getModel(ModelContext ctx, Map<String, Type> varTypes) {
		SmtLib2Model model = new SmtLib2Model(varTypes);
		for (AssignmentContext assignCtx : ctx.assignment()) {
			walkAssign(assignCtx, model);
		}
		return model;
	}

	public static void walkAssign(AssignmentContext assignCtx, SmtLib2Model model) {
		String var = getId(assignCtx.id());
		Sexp body = sexp(assignCtx.body());
		model.addValue(var, body);
	}

	private static String getId(IdContext id) {
		return Quoting.unquote(id.getText());
	}

	private static Sexp sexp(BodyContext ctx) {
		if (ctx instanceof SymbolBodyContext) {
			SymbolBodyContext sbc = (SymbolBodyContext) ctx;
			return new Symbol(sbc.symbol().getText());
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
}
