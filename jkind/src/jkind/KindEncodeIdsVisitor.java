package jkind;

import jkind.lustre.Equation;
import jkind.lustre.IdExpr;
import jkind.lustre.VarDecl;
import jkind.lustre.visitors.AstMapVisitor;

public class KindEncodeIdsVisitor extends AstMapVisitor {
	@Override
	public VarDecl visit(VarDecl e) {
		return new VarDecl(e.location, encode(e.id), e.type);
	}

	@Override
	public Equation visit(Equation e) {
		return super.visit(new Equation(e.location, map(this::visit, e.lhs), e.expr));
	}

	@Override
	public IdExpr visit(IdExpr e) {
		return new IdExpr(e.location, encode(e.id));
	}

	@Override
	protected String visitProperty(String e) {
		return encode(e);
	}

	private String encode(String str) {
		str = str.replaceAll("\\.", "~dot~");
		str = str.replaceAll("\\[", "~lbrack~");
		str = str.replaceAll("\\]", "~rbrack~");
		return str;
	}
}
