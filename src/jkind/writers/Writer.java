package jkind.writers;

import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import jkind.solvers.Model;

public abstract class Writer {
	public abstract void begin();
	public abstract void end();
	
	public abstract void writeValid(List<String> props, int k);
	public abstract void writeInvalid(List<String> props, int k, Model model);
	
	protected static SortedSet<String> sort(Set<String> set) {
		return new TreeSet<String>(set);
	}
}
