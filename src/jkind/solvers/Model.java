package jkind.solvers;

import java.math.BigInteger;
import java.util.Set;

import jkind.sexp.Symbol;

public abstract class Model {
	public abstract Value getValue(Symbol sym);
	public abstract Value getFunctionValue(String fn, BigInteger index);
	public abstract Set<String> getFunctions();
}
