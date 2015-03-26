package jkind;

import static java.util.stream.Collectors.toList;

import java.util.List;

import jkind.lustre.Equation;
import jkind.lustre.IdExpr;
import jkind.lustre.VarDecl;
import jkind.lustre.visitors.PrettyPrintVisitor;

public class KindPrettyPrintVisitor extends PrettyPrintVisitor {
	@Override
	public Void visit(IdExpr e) {
		return super.visit(encode(e));
	}

	@Override
	public Void visit(VarDecl varDecl) {
		return super.visit(encode(varDecl));
	}

	@Override
	protected void property(String s) {
		super.property(encode(s));
	}

	@Override
	public Void visit(Equation equation) {
		return super.visit(encode(equation));
	}

	private String encode(String str) {
		str = str.replaceAll("\\.", "~dot~");
		str = str.replaceAll("\\[", "~lbrack~");
		str = str.replaceAll("\\]", "~rbrack~");
		return str;
	}

	private IdExpr encode(IdExpr e) {
		return new IdExpr(e.location, encode(e.id));
	}

	private VarDecl encode(VarDecl varDecl) {
		return new VarDecl(varDecl.location, encode(varDecl.id), varDecl.type);
	}

	private Equation encode(Equation equation) {
		List<IdExpr> lhs = equation.lhs.stream().map(this::encode).collect(toList());
		return new Equation(equation.location, lhs, equation.expr);
	}
}
