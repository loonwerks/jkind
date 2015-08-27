package jkind.solvers.cvc4;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import jkind.lustre.InductType;
import jkind.lustre.InductTypeElement;
import jkind.lustre.Type;
import jkind.lustre.TypeConstructor;
import jkind.sexp.Cons;
import jkind.sexp.Sexp;
import jkind.sexp.Symbol;
import jkind.solvers.smtlib2.SmtLib2Solver;
import jkind.translation.Specification;

public class Cvc4Solver extends SmtLib2Solver {
	public Cvc4Solver(String scratchBase) {
		super(scratchBase, new ProcessBuilder(getCVC4(), "--lang", "smt"), "CVC4");
	}

	private static String getCVC4() {
		String home = System.getenv("CVC4_HOME");
		if (home != null) {
			return new File(new File(home, "bin"), "cvc4").toString();
		}
		return "cvc4";
	}
	
	public void define(InductType type){
		List<Sexp> constructorExprs = new ArrayList<>();
		for(TypeConstructor constructor : type.constructors){
//			args.add(new Symbol(constructor.name));
			List<Sexp> args = new ArrayList<>();
			for(InductTypeElement element : constructor.elements){
				args.add(new Cons(element.name, new Symbol(element.type.toString())));
			}
			constructorExprs.add(new Cons(constructor.name, args));
		}
		
		Sexp cons = new Cons(type.name, constructorExprs);
		cons = new Cons("declare-datatypes", new Symbol("()"), new Symbol("("+cons.toString()+")"));
		send(cons);
	}

	@Override
	public void initialize(Specification spec) {
		
		for(Type type : spec.typeMap.values()){
			if(type instanceof InductType){
				define((InductType) type);
			}
		}
		
		send("(set-option :produce-models true)");
		send("(set-option :incremental true)");
		send("(set-option :rewrite-divk true)");
//		send("(set-logic AUFLIRA)");
		send("(set-logic ALL_SUPPORTED)");

	}
}
