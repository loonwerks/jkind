package jkind.writers;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import jkind.solvers.Model;
import jkind.solvers.Value;

public class ConsoleWriter extends Writer {
	private void writeLine() {
		System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++");
	}
	
	@Override
	public void writeValid(List<String> props, int k) {
		writeLine();
		System.out.println("VALID PROPERTIES: " + props + " || K = " + k);
		writeLine();
		System.out.println();
	}

	@Override
	public void writeInvalid(List<String> props, int k, Model model) {
		writeLine();
		System.out.println("INVALID PROPERTIES: " + props + " || K = " + k);

		System.out.format("%25s %6s ", "", "Step");
		System.out.println();
		System.out.format("%-25s ", "variable");
		for (int i = 0; i < k; i++) {
			System.out.format("%6s ", i);
		}
		System.out.println();
		System.out.println();
		
		for (String fn : sort(model.getFunctions())) {
			System.out.format("%-25s ", fn);
			Map<Integer, Value> fnMap = model.getFunction(fn);
			for (int i = 0; i < k; i++) {
				System.out.format("%6s ", fnMap.get(i));
			}
			System.out.println();
		}
		
		writeLine();
		System.out.println();
	}
	
	private SortedSet<String> sort(Set<String> set) {
		return new TreeSet<String>(set);
	}
}
