package jkind.slicing;

import java.math.BigInteger;
import java.util.Map;
import java.util.Set;

import jkind.lustre.Node;
import jkind.solvers.Model;
import jkind.solvers.Value;

public class CounterexampleSlicer {
	private DependencyMap dependencyMap;
	
	public CounterexampleSlicer(Node node) {
		dependencyMap = new DependencyMap(node);
	}

	public Model slice(String prop, Model model) {
		Set<String> keep = dependencyMap.get(prop);
		Model sliced = new Model();
		for (String fn : model.getFunctions()) {
			if (fn.startsWith("$") && keep.contains(fn.substring(1))) {
				Map<BigInteger, Value> fnMap = model.getFunction(fn);
				for (BigInteger i : fnMap.keySet()) {
					sliced.addFunctionValue(fn, i, fnMap.get(i));
				}
			}
		}
		return sliced;
	}
}
