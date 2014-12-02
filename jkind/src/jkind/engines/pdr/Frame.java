package jkind.engines.pdr;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jkind.lustre.Expr;
import jkind.sexp.Sexp;
import jkind.translation.Lustre2Sexp;
import jkind.util.SexpUtil;

public class Frame {
	private final Expr expr;
	private final Set<Cube> cubes = new HashSet<>();

	public Frame(Expr expr) {
		this.expr = expr;
	}

	public Frame() {
		this.expr = null;
	}

	public Sexp toSexp(int index) {
		if (expr != null) {
			return expr.accept(new Lustre2Sexp(index));
		}

		List<Sexp> terms = new ArrayList<>();
		for (Cube c : cubes) {
			terms.add(SexpUtil.not(c.toSexp(index)));
		}
		return SexpUtil.and(terms);
	}

	public void add(Cube c) {
		assert expr == null;
		cubes.add(c);
	}

	public Set<Cube> getCubes() {
		return cubes;
	}

	public boolean isEmpty() {
		return expr == null && cubes.isEmpty();
	}

	@Override
	public String toString() {
		if (expr != null) {
			return expr.toString();
		} else {
			return cubes.toString();
		}
	}
}
