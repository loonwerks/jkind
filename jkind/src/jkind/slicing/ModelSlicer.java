package jkind.slicing;

import jkind.solvers.Model;
import jkind.solvers.SimpleModel;
import jkind.util.StreamIndex;

public class ModelSlicer {
	public static SimpleModel slice(Model original, DependencySet keep) {
		SimpleModel sliced = new SimpleModel();
		for (String var : original.getVariableNames()) {
			StreamIndex si = StreamIndex.decode(var);
			if (si != null && keep.contains(si.getStream())) {
				sliced.putValue(si, original.getValue(var));
			}
		}
		return sliced;
	}
}
