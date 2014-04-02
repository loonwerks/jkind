package jkind.translation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jkind.lustre.Equation;
import jkind.lustre.Expr;
import jkind.lustre.Function;
import jkind.lustre.NamedType;
import jkind.lustre.Node;
import jkind.lustre.SubrangeIntType;
import jkind.lustre.Type;
import jkind.lustre.VarDecl;
import jkind.sexp.Cons;
import jkind.sexp.Sexp;
import jkind.sexp.Symbol;
import jkind.solvers.Decl;
import jkind.solvers.FunctionDecl;
import jkind.solvers.Lambda;
import jkind.solvers.StreamDecl;
import jkind.solvers.StreamDef;
import jkind.util.SexpUtil;
import jkind.util.Util;

public class Lustre2Sexps {
	private StreamDef transition;
	private final List<Decl> declarations = new ArrayList<>();

	public Lustre2Sexps(List<Function> functions, Node node) {
		createDeclarations(functions, node);
		createTransition(node);
	}

	private void createDeclarations(List<Function> functions, Node node) {
		for (Function function : functions) {
			List<Type> inputs = Util.getTypes(function.inputs);
			Type output = function.outputs.get(0).type;
			declarations.add(new FunctionDecl("$$" + function.id, inputs, output));
		}
		for (VarDecl decl : Util.getVarDecls(node)) {
			declarations.add(new StreamDecl("$" + decl.id, decl.type));
		}
	}

	private void createTransition(Node node) {
		Expr2SexpVisitor visitor = new Expr2SexpVisitor(SexpUtil.I);
		List<Sexp> conjuncts = new ArrayList<>();

		for (Equation eq : node.equations) {
			conjuncts.add(equation2Sexp(eq, SexpUtil.I, visitor));
		}

		for (VarDecl input : node.inputs) {
			if (input.type instanceof SubrangeIntType) {
				conjuncts.add(SexpUtil.subrangeConstraint(input.id, SexpUtil.I,
						(SubrangeIntType) input.type));
			}
		}

		for (Expr assertion : node.assertions) {
			conjuncts.add(assertion.accept(visitor));
		}

		Lambda lambda = new Lambda(SexpUtil.I, new Cons("and", conjuncts));
		transition = new StreamDef(Keywords.T, NamedType.BOOL, lambda);
	}

	private Sexp equation2Sexp(Equation eq, Symbol iSym, Expr2SexpVisitor visitor) {
		Sexp body = eq.expr.accept(visitor);
		return new Cons("=", new Cons("$" + eq.lhs.get(0).id, iSym), body);
	}

	public StreamDef getTransition() {
		return transition;
	}

	public List<Decl> getDeclarations() {
		return declarations;
	}

	public Map<String, Decl> getDeclarationsTable() {
		Map<String, Decl> result = new HashMap<>();
		for (Decl decl : declarations) {
			result.put(decl.getId().toString(), decl);
		}
		return result;
	}
}
