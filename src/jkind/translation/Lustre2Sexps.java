package jkind.translation;

import java.util.ArrayList;
import java.util.List;

import jkind.lustre.Equation;
import jkind.lustre.Node;
import jkind.lustre.SubrangeIntType;
import jkind.lustre.VarDecl;
import jkind.sexp.Cons;
import jkind.sexp.Sexp;
import jkind.sexp.Symbol;

public class Lustre2Sexps {
	private Sexp transition;
	private List<Sexp> definitions = new ArrayList<Sexp>();

	public Lustre2Sexps(Node node) {
		createDefinitions(node);
		createTransition(node.equations, node.inputs);
	}

	private void createDefinitions(Node node) {
		for (VarDecl decl : Util.getVarDecls(node)) {
			Sexp type = new Cons("->", new Symbol("nat"), new Symbol(decl.type.name));
			Sexp def = new Cons("define", new Symbol("$" + decl.id), new Symbol("::"), type);
			definitions.add(def);
		}
	}

	private void createTransition(List<Equation> equations, List<VarDecl> inputs) {
		Symbol i = new Symbol("i");
		List<Sexp> conjuncts = new ArrayList<Sexp>();
		for (Equation eq : equations) {
			conjuncts.add(equation2Sexp(eq, i));
		}
		for (VarDecl input : inputs) {
			if (input.type instanceof SubrangeIntType) {
				conjuncts.add(Util.subrangeConstraint(input.id, i, (SubrangeIntType) input.type));
			}
		}

		Sexp iType = new Cons(i, new Symbol("::"), new Symbol("nat"));
		Sexp lambda = new Cons("lambda", iType, new Cons("and", conjuncts));
		Sexp tType = new Cons("->", new Symbol("nat"), new Symbol("bool"));
		transition = new Cons("define", Keywords.T, new Symbol("::"), tType, lambda);
	}

	private Sexp equation2Sexp(Equation eq, Symbol iSym) {
		Sexp body = eq.expr.accept(new Expr2SexpVisitor(iSym));
		return new Cons("=", new Cons("$" + eq.lhs.get(0).id, iSym), body);
	}

	public Sexp getTransition() {
		return transition;
	}

	public List<Sexp> getDefinitions() {
		return definitions;
	}
}
