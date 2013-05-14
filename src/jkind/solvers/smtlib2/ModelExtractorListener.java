package jkind.solvers.smtlib2;

import java.util.ArrayList;
import java.util.List;

import jkind.sexp.Cons;
import jkind.sexp.Sexp;
import jkind.sexp.Symbol;
import jkind.solvers.Lambda;
import jkind.solvers.smtlib2.SmtLib2Parser.BodyContext;
import jkind.solvers.smtlib2.SmtLib2Parser.ConsBodyContext;
import jkind.solvers.smtlib2.SmtLib2Parser.DefunContext;
import jkind.solvers.smtlib2.SmtLib2Parser.DefvalContext;
import jkind.solvers.smtlib2.SmtLib2Parser.ModelContext;
import jkind.solvers.smtlib2.SmtLib2Parser.SymbolBodyContext;

public class ModelExtractorListener extends SmtLib2BaseListener {
	private SmtLib2Model model;
	
	public SmtLib2Model getModel() {
		return model;
	}

	@Override
	public void enterModel(ModelContext ctx) {
		model = new SmtLib2Model();
	}
	
	@Override
	public void enterDefval(DefvalContext ctx) {
		model.addValue(ctx.ID().getText(), sexp(ctx.body()));
	}
	
	@Override
	public void enterDefun(DefunContext ctx) {
		String fn = ctx.ID().getText();
		Symbol arg = new Symbol(ctx.arg().ID().getText());
		model.addFunction(fn, new Lambda(arg, sexp(ctx.body())));
	}

	private Sexp sexp(BodyContext ctx) {
		if (ctx instanceof SymbolBodyContext) {
			SymbolBodyContext sbc = (SymbolBodyContext) ctx;
			return new Symbol(sbc.symbol().getText());
		} else if (ctx instanceof ConsBodyContext) {
			ConsBodyContext cbc = (ConsBodyContext) ctx;
			List<Sexp> args = new ArrayList<Sexp>();
			for (BodyContext sub : cbc.body()) {
				args.add(sexp(sub));
			}
			return new Cons(cbc.fn().getText(), args);
		} else {
			throw new IllegalArgumentException();
		}
	}
}
