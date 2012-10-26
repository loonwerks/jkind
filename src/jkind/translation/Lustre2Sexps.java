package jkind.translation;

import java.util.ArrayList;
import java.util.List;

import jkind.lustre.Equation;
import jkind.lustre.Node;
import jkind.lustre.Type;
import jkind.lustre.VarDecl;
import jkind.sexp.Cons;
import jkind.sexp.Sexp;
import jkind.sexp.Symbol;

public class Lustre2Sexps {
	private Sexp transition;
	private List<Sexp> definitions = new ArrayList<Sexp>();
	
	public Lustre2Sexps(Node node) {
		createDefinitions(node);
		createTransition(node.equations);
	}

	private void createDefinitions(Node node) {
		for (VarDecl decl : Util.getVarDecls(node)) {
			Sexp type = new Cons("->", new Symbol("nat"), typeToSymbol(decl.type));
			Sexp def = new Cons("define", new Symbol(decl.id), new Symbol("::"), type);
			definitions.add(def);
		}
	}

	private Symbol typeToSymbol(Type type) {
		switch (type) {
		case INT:
			return new Symbol("int");
		case REAL:
			return new Symbol("real");
		case BOOL:
			return new Symbol("bool");
		default:
			throw new IllegalArgumentException("Unknown type: " + type);
		}
	}

	private void createTransition(List<Equation> equations) {
		String i = "@i";
		Symbol iSym = new Symbol(i);
		List<Sexp> eqSexps = new ArrayList<Sexp>();
		for (Equation eq : equations) {
			eqSexps.add(equation2Sexp(eq, iSym));
		}
		
		Sexp iType = new Cons(iSym, new Symbol("::"), new Symbol("nat"));
		Sexp lambda = new Cons("lambda", iType, new Cons("and", eqSexps));
		Sexp tType = new Cons("->", new Symbol("nat"), new Symbol("bool"));
		transition = new Cons("define", Keywords.T_SYM, new Symbol("::"), tType, lambda);
	}
	
	private Sexp equation2Sexp(Equation eq, Symbol iSym) {
		Sexp body = eq.expr.accept(new Expr2SexpVisitor(iSym));
		return new Cons("=", new Cons(eq.id, iSym), body);
	}

	public Sexp getTransition() {
		return transition;
	}

	public List<Sexp> getDefinitions() {
		return definitions;
	}
}
