package jkind.solvers;

import java.util.List;

import jkind.sexp.Symbol;

public class UnsatResult extends Result {
	final private List<Symbol> unsatCore;

	public UnsatResult() {
		this.unsatCore = null;
	}

	public UnsatResult(List<Symbol> unsatCore) {
		this.unsatCore = unsatCore;
	}

	public List<Symbol> getUnsatCore() {
		return unsatCore;
	}
}
