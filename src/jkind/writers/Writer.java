package jkind.writers;

import java.util.List;

import jkind.solvers.Model;

public abstract class Writer {
	public abstract void writeValid(List<String> props, int k);
	public abstract void writeInvalid(List<String> props, int k, Model model);
}
