package jkind.translation;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;

import jkind.lustre.Expr;
import jkind.lustre.IntExpr;
import jkind.lustre.visitors.AstMapVisitor;

/**
 * Replace ids with expressions based on a map. This substitution is not
 * recursive: the newly substituted expression will not be analyzed for
 * additional substitutions.
 */
public class ConstantsLookupVisitor extends AstMapVisitor {
	private Set<BigInteger> constants;
	
	public ConstantsLookupVisitor() {
		constants = new HashSet<BigInteger>();
	}

	@Override
	public Expr visit(IntExpr e) {
		constants.add(e.value);
		return e;
	}

	public Set<BigInteger> getConstants() {
		return constants;
	}
}
