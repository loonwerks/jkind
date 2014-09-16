package jkind.slicing;

import java.util.Set;

import jkind.solvers.Model;
import jkind.util.StreamIndex;

public class ModelSlicer {
	public static Model slice(Model original, Set<String> keep) {
		SimpleModel sliced = new SimpleModel();
		
		for (String var : original.getVariableNames()) {
			StreamIndex si = StreamIndex.decode(var);
			if (si != null && keep.contains(si.getStream())) {
				sliced.addValue(si, original.getValue(var));
			}
		}
		
		for (String fn : original.getFunctionNames()) {
			sliced.addFunction(fn, original.getFunction(fn));
		}
		
		return sliced;
	}
}
