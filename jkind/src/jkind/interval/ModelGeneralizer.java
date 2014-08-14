package jkind.interval;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Set;

import jkind.lustre.EnumType;
import jkind.lustre.Equation;
import jkind.lustre.Expr;
import jkind.lustre.IdExpr;
import jkind.lustre.NamedType;
import jkind.lustre.SubrangeIntType;
import jkind.lustre.Type;
import jkind.lustre.values.BooleanValue;
import jkind.lustre.values.EnumValue;
import jkind.lustre.values.IntegerValue;
import jkind.lustre.values.RealValue;
import jkind.lustre.values.Value;
import jkind.results.Counterexample;
import jkind.results.Signal;
import jkind.solvers.Model;
import jkind.translation.Specification;
import jkind.util.StreamIndex;

public class ModelGeneralizer {
	private final Specification spec;
	private final String property;
	private final Model basisModel;
	private final int k;

	private final Map<StreamIndex, Interval> cache = new HashMap<>();
	private final Map<String, Expr> equations = new HashMap<>();

	private final Queue<StreamIndex> toGeneralize = new ArrayDeque<>();
	private final Map<StreamIndex, Interval> generalized = new HashMap<>();

	private final ReverseDependencyMap dependsOn;

	private final IntIntervalGeneralizer intIntervalGeneralizer;
	private final RealIntervalGeneralizer realIntervalGeneralizer;

	public ModelGeneralizer(Specification spec, String property, Model model, int k) {
		this.spec = spec;
		this.property = property;
		this.basisModel = model;
		this.k = k;

		for (Equation eq : spec.node.equations) {
			equations.put(eq.lhs.get(0).id, eq.expr);
		}

		dependsOn = new ReverseDependencyMap(spec.node, spec.dependencyMap.get(property));

		intIntervalGeneralizer = new IntIntervalGeneralizer(this);
		realIntervalGeneralizer = new RealIntervalGeneralizer(this);
	}

	public Counterexample generalize() {
		// This fills the initial toGeneralize queue as a side-effect
		if (!modelConsistent()) {
			throw new IllegalStateException("Internal JKind error during interval generalization");
		}

		// More items may be added to the toGeneralize queue as prior
		// generalizations make them relevant
		while (!toGeneralize.isEmpty()) {
			StreamIndex si = toGeneralize.remove();
			Interval interval = generalizeInterval(si);
			generalized.put(si, interval);
		}

		return extractCounterexample();
	}

	private Interval generalizeInterval(StreamIndex si) {
		Type type = spec.typeMap.get(si.getStream());
		if (type == NamedType.BOOL) {
			return generalizeBoolInterval(si);
		} else if (type == NamedType.INT) {
			NumericInterval initial = (NumericInterval) originalInterval(si);
			return intIntervalGeneralizer.generalize(si, initial);
		} else if (type == NamedType.REAL) {
			NumericInterval initial = (NumericInterval) originalInterval(si);
			return realIntervalGeneralizer.generalize(si, initial);
		} else if (type instanceof SubrangeIntType || type instanceof EnumType) {
			return generalizeSubrangeIntInterval(si);
		} else {
			throw new IllegalArgumentException("Unknown type in generalization: " + type);
		}
	}

	private Interval generalizeBoolInterval(StreamIndex si) {
		if (modelConsistent(si, BoolInterval.ARBITRARY)) {
			return BoolInterval.ARBITRARY;
		} else {
			return originalInterval(si);
		}
	}

	private Interval generalizeSubrangeIntInterval(StreamIndex si) {
		NumericInterval next = new NumericInterval(IntEndpoint.NEGATIVE_INFINITY,
				IntEndpoint.POSITIVE_INFINITY);
		if (modelConsistent(si, next)) {
			return next;
		} else {
			return originalInterval(si);
		}
	}

	private Counterexample extractCounterexample() {
		// This fills the cache as a side-effect
		if (!modelConsistent()) {
			throw new IllegalStateException("Internal JKind error during interval generalization");
		}

		Counterexample cex = new Counterexample(k);
		for (Entry<StreamIndex, Interval> entry : cache.entrySet()) {
			StreamIndex si = entry.getKey();
			Interval value = entry.getValue();

			if (!value.isArbitrary() && si.getIndex() >= 0) {
				Signal<Value> signal = cex.getOrCreateSignal(si.getStream());
				Type type = spec.typeMap.get(si.getStream());
				signal.putValue(si.getIndex(), convert(type, value));
			}
		}
		return cex;
	}

	private Value convert(Type type, Interval value) {
		if (type instanceof EnumType) {
			EnumType et = (EnumType) type;
			return new EnumValue(et.values.get(getExactInt(value)));
		} else {
			return value;
		}
	}

	private int getExactInt(Interval value) {
		NumericInterval ni = (NumericInterval) value;
		if (!ni.isExact()) {
			throw new IllegalStateException();
		}
		IntEndpoint ie = (IntEndpoint) ni.getLow();
		return ie.getValue().intValue();
	}

	private Interval originalInterval(StreamIndex si) {
		return eval(si);
	}

	private boolean modelConsistent() {
		BoolInterval interval = (BoolInterval) eval(new StreamIndex(property, k - 1));
		if (!interval.isFalse()) {
			return false;
		}
		for (Expr as : spec.node.assertions) {
			for (int i = 0; i < k; i++) {
				interval = (BoolInterval) eval(as, i);
				if (!interval.isTrue()) {
					return false;
				}
			}
		}
		return true;
	}

	public boolean modelConsistent(StreamIndex si, Interval proposedValue) {
		clearCacheFrom(si);
		cache.put(si, proposedValue);
		boolean result = modelConsistent();
		clearCacheFrom(si);
		return result;
	}

	private void clearCacheFrom(StreamIndex si) {
		for (String recompute : dependsOn.get(si.getStream())) {
			for (int i = si.getIndex(); i < k; i++) {
				cache.remove(new StreamIndex(recompute, i));
			}
		}
	}

	public static class AlgebraicLoopException extends RuntimeException {
		private static final long serialVersionUID = 1L;
	}

	private final Set<StreamIndex> working = new HashSet<>();

	private Interval eval(Expr expr, int i) {
		return expr.accept(new IntervalEvaluator(i, this));
	}

	private Interval eval(StreamIndex si) {
		return eval(new IdExpr(si.getStream()), si.getIndex());
	}

	/*
	 * The IntervalEvaluator will call back in to this class to look up ids.
	 */
	public Interval evalId(StreamIndex si) {
		if (cache.containsKey(si)) {
			return cache.get(si);
		}

		Interval result;
		if (equations.containsKey(si.getStream()) && si.getIndex() >= 0) {
			if (working.contains(si)) {
				throw new AlgebraicLoopException();
			}

			working.add(si);
			result = eval(equations.get(si.getStream()), si.getIndex());
			working.remove(si);

		} else if (generalized.containsKey(si)) {
			result = generalized.get(si);
		} else {
			result = getFromBasisModel(si);
			// Due to checking all assertions, we may hit variables that our
			// property doesn't depend on. We detect and ignore these variables.
			if (si.getIndex() >= 0 && dependsOn.get(si.getStream()) != null) {
				toGeneralize.add(si);
			}
		}
		cache.put(si, result);
		return result;
	}

	private Interval getFromBasisModel(StreamIndex si) {
		return valueToInterval(basisModel.getValue(si));
	}

	private Interval valueToInterval(Value value) {
		if (value instanceof BooleanValue) {
			BooleanValue bv = (BooleanValue) value;
			return bv.value ? BoolInterval.TRUE : BoolInterval.FALSE;
		} else if (value instanceof IntegerValue) {
			IntegerValue iv = (IntegerValue) value;
			IntEndpoint endpoint = new IntEndpoint(iv.value);
			return new NumericInterval(endpoint, endpoint);
		} else if (value instanceof RealValue) {
			RealValue rv = (RealValue) value;
			RealEndpoint endpoint = new RealEndpoint(rv.value);
			return new NumericInterval(endpoint, endpoint);
		} else {
			throw new IllegalArgumentException("Unknown interval type: "
					+ value.getClass().getName());
		}
	}
}
