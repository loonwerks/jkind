package jkind.slicing;

import java.math.BigInteger;
import java.util.Map;
import java.util.Set;

import jkind.solvers.Model;
import jkind.solvers.Value;
import jkind.solvers.smtlib2.SmtLib2Model;
import jkind.solvers.yices.YicesModel;

public class CounterexampleSlicer {
	private DependencyMap dependencyMap;
	
	public CounterexampleSlicer(DependencyMap dependencyMap) {
		this.dependencyMap = dependencyMap;
	}
	
	public Model slice(String prop, Model model) {
		if (model instanceof YicesModel) {
			return slice(prop, (YicesModel) model);
		} else if (model instanceof SmtLib2Model) {
			return slice(prop, (SmtLib2Model) model);
		} else {
			throw new UnsupportedOperationException();
		}
	}
	
	public YicesModel slice(String prop, YicesModel model) {
		Set<String> keep = dependencyMap.get(prop);
		YicesModel sliced = new YicesModel();
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
	
	public SmtLib2Model slice(String prop, SmtLib2Model model) {
		Set<String> keep = dependencyMap.get(prop);
		SmtLib2Model sliced = new SmtLib2Model();
		for (String fn : model.getFunctions()) {
			if (fn.startsWith("$") && keep.contains(fn.substring(1))) {
				sliced.addFunction(fn, model.getFunction(fn));
			}
		}
		return sliced;
	}
}
