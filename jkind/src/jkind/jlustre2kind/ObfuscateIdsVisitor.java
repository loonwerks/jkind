package jkind.jlustre2kind;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import jkind.lustre.Equation;
import jkind.lustre.IdExpr;
import jkind.lustre.VarDecl;
import jkind.lustre.visitors.AstMapVisitor;

public class ObfuscateIdsVisitor extends AstMapVisitor {
	private final Map<String, String> obfuscated = new HashMap<>();

	private String obfuscate(String str) {
		return obfuscated.computeIfAbsent(str, s -> randomId());
	}

	private String randomId() {
		return "x" + UUID.randomUUID().toString().replace("-", "");
	}

	@Override
	public VarDecl visit(VarDecl e) {
		return new VarDecl(e.location, obfuscate(e.id), e.type);
	}

	@Override
	public Equation visit(Equation e) {
		return super.visit(new Equation(e.location, map(this::visit, e.lhs), e.expr));
	}

	@Override
	public IdExpr visit(IdExpr e) {
		return new IdExpr(e.location, obfuscate(e.id));
	}

	@Override
	protected String visitProperty(String e) {
		return obfuscate(e);
	}

	@Override
	protected String visitIvc(String e) {
		return obfuscate(e);
	}

	@Override
	protected String visitRealizabilityInput(String e) {
		return obfuscate(e);
	}
}
