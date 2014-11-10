package jkind.pdr;

import jkind.sexp.Cons;
import jkind.sexp.Sexp;
import jkind.sexp.Symbol;
import de.uni_freiburg.informatik.ultimate.logic.Script;
import de.uni_freiburg.informatik.ultimate.logic.Term;

public class SexpConvert {
	private final Script solver;

	public SexpConvert(Script solver) {
		this.solver = solver;
	}

	public Term convert(Sexp sexp) {
		if (sexp instanceof Symbol) {
			return convert((Symbol) sexp);
		} else if (sexp instanceof Cons) {
			return convert((Cons) sexp);
		} else {
			throw new IllegalArgumentException();
		}
	}

	private Term convert(Symbol symbol) {
		return solver.term(symbol.str);
	}

	private Term convert(Cons cons) {
		Term[] args = new Term[cons.args.size()];
		for (int i = 0; i < cons.args.size(); i++) {
			args[i] = convert(cons.args.get(i));
		}
		return solver.term(cons.head.toString(), args);
	}

}
