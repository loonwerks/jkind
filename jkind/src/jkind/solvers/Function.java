package jkind.solvers;

import java.util.List;

import jkind.lustre.values.Value;

public abstract class Function {
	public abstract Value getValue(List<Value> args);
}
