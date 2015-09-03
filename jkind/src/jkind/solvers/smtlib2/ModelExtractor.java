package jkind.solvers.smtlib2;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jkind.JKindException;
import jkind.lustre.Type;
import jkind.sexp.Cons;
import jkind.sexp.Sexp;
import jkind.sexp.Symbol;
import jkind.solvers.smtlib2.SmtLib2Parser.BodyContext;
import jkind.solvers.smtlib2.SmtLib2Parser.ConsBodyContext;
import jkind.solvers.smtlib2.SmtLib2Parser.DeclareDataTypesContext;
import jkind.solvers.smtlib2.SmtLib2Parser.DeclareSortContext;
import jkind.solvers.smtlib2.SmtLib2Parser.DefineContext;
import jkind.solvers.smtlib2.SmtLib2Parser.DefinefunContext;
import jkind.solvers.smtlib2.SmtLib2Parser.IdContext;
import jkind.solvers.smtlib2.SmtLib2Parser.ModelContext;
import jkind.solvers.smtlib2.SmtLib2Parser.SymbolBodyContext;
import jkind.solvers.smtlib2.SmtLib2Parser.TypeConstructorContext;

public class ModelExtractor {
	public static SmtLib2Model getModel(ModelContext ctx, Map<String, Type> varTypes) {
		SmtLib2Model model = new SmtLib2Model(varTypes);
		for (DefineContext defineCtx : ctx.define()) {
		    if(defineCtx instanceof DefinefunContext){
		        walkDefine((DefinefunContext) defineCtx, model);
		    }else if(defineCtx instanceof DeclareDataTypesContext){
		        walkDeclare((DeclareDataTypesContext) defineCtx, model);
		    }else if (defineCtx instanceof DeclareSortContext){
		        //not sure if we need to do anything with sorts
		    }else{
		        throw new JKindException("Unhandled model parser type");
		    }
			
		}
		return model;
	}

	public static void walkDeclare(DeclareDataTypesContext ctx, SmtLib2Model model){
	    //we need to add the type constructors to the model
	    for(TypeConstructorContext constructor : ctx.typeConstructor()){
	        model.addTypeConstructor(constructor.ID().getText());   
	    }
	}
	
	public static void walkDefine(DefinefunContext ctx, SmtLib2Model model) {
		String var = Quoting.unquote(ctx.id().getText());
		Sexp body = sexp(ctx.body());
		model.addValue(var, body);
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
