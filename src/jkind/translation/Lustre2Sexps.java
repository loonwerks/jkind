package jkind.translation;

import java.util.ArrayList;
import java.util.List;

import jkind.lustre.Equation;
import jkind.lustre.Expr;
import jkind.lustre.Node;
import jkind.lustre.SubrangeIntType;
import jkind.lustre.Type;
import jkind.lustre.VarDecl;
import jkind.sexp.Cons;
import jkind.sexp.Sexp;
import jkind.sexp.Symbol;
import jkind.solvers.Lambda;
import jkind.solvers.StreamDecl;
import jkind.solvers.StreamDef;
import jkind.util.Util;

public class Lustre2Sexps {
	private StreamDef transition;
	private List<StreamDecl> declarations = new ArrayList<StreamDecl>();

	public Lustre2Sexps(Node node) {
		createDefinitions(node);
		createTransition(node);
	}

	private void createDefinitions(Node node) {
		for (VarDecl decl : Util.getVarDecls(node)) {
			declarations.add(new StreamDecl(new Symbol("$" + decl.id), decl.type));
		}
	}

	private void createTransition(Node node) {
		List<Sexp> conjuncts = new ArrayList<Sexp>();
		for (Equation eq : node.equations) {
			conjuncts.add(equation2Sexp(eq, Util.I));
		}
		for (VarDecl input : node.inputs) {
			if (input.type instanceof SubrangeIntType) {
				conjuncts.add(Util.subrangeConstraint(input.id, Util.I,
						(SubrangeIntType) input.type));
			}
		}
		for (Expr assertion : node.assertions) {
			conjuncts.add(assertion.accept(new Expr2SexpVisitor(Util.I)));
		}

		Lambda lambda = new Lambda(Util.I, new Cons("and", conjuncts));
		transition = new StreamDef(Keywords.T, Type.BOOL, lambda);
	}

	private Sexp equation2Sexp(Equation eq, Symbol iSym) {
		Sexp body = eq.expr.accept(new Expr2SexpVisitor(iSym));
		return new Cons("=", new Cons("$" + eq.lhs.get(0).id, iSym), body);
	}

	public StreamDef getTransition() {
		return transition;
	}

	public List<StreamDecl> getDeclarations() {
		return declarations;
	}
}
