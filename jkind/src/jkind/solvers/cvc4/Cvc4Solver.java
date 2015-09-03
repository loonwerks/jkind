package jkind.solvers.cvc4;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.antlr.v4.runtime.RecognitionException;

import jkind.JKindException;
import jkind.lustre.InductType;
import jkind.lustre.InductTypeElement;
import jkind.lustre.RecursiveFunction;
import jkind.lustre.Type;
import jkind.lustre.TypeConstructor;
import jkind.sexp.Cons;
import jkind.sexp.Sexp;
import jkind.sexp.Symbol;
import jkind.solvers.smtlib2.SmtLib2Solver;
import jkind.translation.RecursiveFunctionSpecification;
import jkind.translation.Relation;
import jkind.translation.Specification;

public class Cvc4Solver extends SmtLib2Solver {
    
    private final Set<String> definedTypes = new HashSet<>();
    
	public Cvc4Solver(String scratchBase) {
		super(scratchBase, new ProcessBuilder(getCVC4(), "--lang", "smt", "--fmf-fun"), "CVC4");
	}

	private static String getCVC4() {
		String home = System.getenv("CVC4_HOME");
		if (home != null) {
			return new File(new File(home, "bin"), "cvc4").toString();
		}
		return "cvc4";
	}
	
    public void define(InductType type) {

        if (!definedTypes.contains(type.name)) {
            definedTypes.add(type.name);
            List<Sexp> constructorExprs = new ArrayList<>();
            for (TypeConstructor constructor : type.constructors) {
                // args.add(new Symbol(constructor.name));
                List<Sexp> args = new ArrayList<>();
                for (InductTypeElement element : constructor.elements) {
                    args.add(new Cons(element.name, type(element.type)));
                }
                constructorExprs.add(new Cons(constructor.name, args));
            }
            Sexp cons = new Cons(type.name, constructorExprs);
            cons = new Cons("declare-datatypes", new Symbol("()"), new Symbol("(" + cons.toString() + ")"));
            send(cons);
            isWellFounded(); //checks for well-foundedness
        }
	}

    protected boolean isWellFounded() {
        try {
            if (fromSolver.ready()) {
                String line = fromSolver.readLine();
                if (line.contains(" is not well-founded")) {
                    int endIndex = line.indexOf(" is not well-founded");
                    // TODO: this will break if the error message changes
                    String typeName = line.substring(21, endIndex);
                    throw new JKindException("Type '" + typeName + "' is not well-founded");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }
    
	@Override
	public void initialize(Specification spec) {
		send("(set-option :produce-models true)");
		send("(set-option :incremental true)");
		send("(set-option :rewrite-divk true)");
//		send("(set-logic AUFLIRA)");
		send("(set-logic ALL_SUPPORTED)");
		
		for(Type type : spec.typeMap.values()){
			if(type instanceof InductType){
				define((InductType) type);
			}
		}
		
		if(spec instanceof RecursiveFunctionSpecification){
		    for(Sexp sexp : ((RecursiveFunctionSpecification) spec).functions){
		        send(sexp);
		    }
		}

	}
}
