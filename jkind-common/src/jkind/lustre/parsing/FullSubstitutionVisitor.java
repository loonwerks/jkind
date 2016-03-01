package jkind.lustre.parsing;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jkind.JKindException;
import jkind.lustre.Constant;
import jkind.lustre.Equation;
import jkind.lustre.Expr;
import jkind.lustre.IdExpr;
import jkind.lustre.VarDecl;
import jkind.lustre.visitors.AstMapVisitor;

/**
 * Replace ids with expressions based on a map. This substitution is not
 * recursive: the newly substituted expression will not be analyzed for
 * additional substitutions. This substitution differs from SubstitutionVisitor
 * in that it works on non-expression ids such as on the left-hand side of an
 * equation.
 */
public class FullSubstitutionVisitor extends AstMapVisitor {
	private final Map<String, ? extends Expr> map;

	public FullSubstitutionVisitor(Map<String, ? extends Expr> map) {
		this.map = map;
	}

	private String visit(String id) {
		if (map.containsKey(id)) {
			Expr e = map.get(id);
			if (e instanceof IdExpr) {
				IdExpr ide = (IdExpr) e;
				return ide.id;
			} else {
				throw new JKindException("Unable to replace '" + id + "' with non-id '" + e + "'");
			}
		} else {
			return id;
		}
	}

	@Override
	public Expr visit(IdExpr e) {
		if (map.containsKey(e.id)) {
			return map.get(e.id);
		} else {
			return e;
		}
	}

	@Override
	public Constant visit(Constant e) {
		return new Constant(e.location, visit(e.id), e.type, e.expr.accept(this));
	}

	@Override
	public Equation visit(Equation e) {
		List<IdExpr> lhs = new ArrayList<>();
		for (IdExpr ide : e.lhs) {
			lhs.add(new IdExpr(visit(ide.id)));
		}
		return new Equation(e.location, lhs, e.expr.accept(this));
	}

	@Override
	protected String visitProperty(String e) {
		return visit(e);
	}

	@Override
	protected String visitIvc(String e) {
		return visit(e);
	}

	@Override
	protected String visitRealizabilityInput(String e) {
		return visit(e);
	}

	@Override
	public VarDecl visit(VarDecl e) {
		return new VarDecl(e.location, visit(e.id), e.type);
	}
}
