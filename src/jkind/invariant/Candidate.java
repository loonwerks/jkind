package jkind.invariant;

import java.math.BigInteger;

import jkind.lustre.Type;
import jkind.sexp.Cons;
import jkind.sexp.Sexp;
import jkind.sexp.Symbol;
import jkind.solvers.BoolValue;
import jkind.solvers.Lambda;
import jkind.solvers.Model;
import jkind.solvers.StreamDef;
import jkind.util.Util;

public class Candidate {
	final public StreamDef def;
	final public String text;

	public Candidate(StreamDef def, String text) {
		this.def = def;
		this.text = text;
	}

	public boolean isTrue(Model model, BigInteger k) {
		return model.getFunctionValue(def.getId().toString(), k) == BoolValue.TRUE;
	}

	public Sexp index(Sexp index, boolean pure) {
		if (pure) {
			return def.instantiate(index);
		} else {
			return new Cons(def.getId(), index);
		}
	}

	@Override
	public String toString() {
		return text;
	}

	final public static Candidate TRUE = new Candidate(new StreamDef("canTrue", Type.BOOL,
			new Lambda(Util.I, new Symbol("true"))), "true");
	final public static Candidate FALSE = new Candidate(new StreamDef("canFalse", Type.BOOL,
			new Lambda(Util.I, new Symbol("false"))), "false");
}
