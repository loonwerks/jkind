package jkind.translation;

import java.util.ArrayList;
import java.util.List;

import jkind.lustre.Equation;
import jkind.lustre.Expr;
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
		createTransition(node);
	}

	private void createDefinitions(Node node) {
		for (VarDecl decl : Util.getVarDecls(node)) {
			Sexp type = new Cons("->", new Symbol("nat"), new Symbol(decl.type.name));
			Sexp def = new Cons("define", new Symbol("$" + decl.id), new Symbol("::"), type);
			definitions.add(def);
		}
	}

	private void createTransition(Node node) {
		List<Sexp> conjuncts = new ArrayList<Sexp>();
		for (Equation eq : node.equations) {
			conjuncts.add(equation2Sexp(eq, Util.I));
		}
		for (VarDecl input : node.inputs) {
			if (input.type instanceof SubrangeIntType) {
				conjuncts.add(Util.subrangeConstraint(input.id, Util.I, (SubrangeIntType) input.type));
			}
		}
		for (Expr assertion : node.assertions) {
			conjuncts.add(assertion.accept(new Expr2SexpVisitor(Util.I)));
		}

		Sexp lambda = Util.lambdaI(new Cons("and", conjuncts));
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
