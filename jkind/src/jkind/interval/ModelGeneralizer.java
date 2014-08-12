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
import jkind.util.SexpUtil;

public class ModelGeneralizer {
	private final Specification spec;
	private final String property;
	private final Model basisModel;
	private final int k;

	private final Map<IdIndexPair, Interval> cache = new HashMap<>();
	private final Map<String, Expr> equations = new HashMap<>();

	private final Queue<IdIndexPair> toGeneralize = new ArrayDeque<>();
	private final Map<IdIndexPair, Interval> generalized = new HashMap<>();

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
			IdIndexPair pair = toGeneralize.remove();
			Interval interval = generalizeInterval(pair.id, pair.i);
			generalized.put(pair, interval);
		}

		return extractCounterexample();
	}

	private Interval generalizeInterval(String id, int i) {
		Type type = spec.typeMap.get(id);
		if (type == NamedType.BOOL) {
			return generalizeBoolInterval(id, i);
		} else if (type == NamedType.INT) {
			NumericInterval initial = (NumericInterval) originalInterval(id, i);
			return intIntervalGeneralizer.generalize(id, i, initial);
		} else if (type == NamedType.REAL) {
			NumericInterval initial = (NumericInterval) originalInterval(id, i);
			return realIntervalGeneralizer.generalize(id, i, initial);
		} else if (type instanceof SubrangeIntType || type instanceof EnumType) {
			return generalizeSubrangeIntInterval(id, i);
		} else {
			throw new IllegalArgumentException("Unknown type in generalization: " + type);
		}
	}

	private Interval generalizeBoolInterval(String id, int i) {
		if (modelConsistent(id, i, BoolInterval.ARBITRARY)) {
			return BoolInterval.ARBITRARY;
		} else {
			return originalInterval(id, i);
		}
	}

	private Interval generalizeSubrangeIntInterval(String id, int i) {
		NumericInterval next = new NumericInterval(IntEndpoint.NEGATIVE_INFINITY,
				IntEndpoint.POSITIVE_INFINITY);
		if (modelConsistent(id, i, next)) {
			return next;
		} else {
			return originalInterval(id, i);
		}
	}

	private Counterexample extractCounterexample() {
		// This fills the cache as a side-effect
		if (!modelConsistent()) {
			throw new IllegalStateException("Internal JKind error during interval generalization");
		}

		Counterexample cex = new Counterexample(k);
		for (Entry<IdIndexPair, Interval> entry : cache.entrySet()) {
			IdIndexPair pair = entry.getKey();
			Interval value = entry.getValue();

			if (!value.isArbitrary()) {
				Signal<Value> signal = cex.getSignal(pair.id);
				if (signal == null) {
					signal = new Signal<>(pair.id);
					cex.addSignal(signal);
				}

				Type type = spec.typeMap.get(pair.id);
				if (type instanceof EnumType) {
					EnumType et = (EnumType) type;
					int v = getExactInt(value);
					if (v < 0 || et.values.size() <= v) {
						// This can happen due to looking before the initial
						// state
						continue;
					}
					signal.putValue(pair.i, new EnumValue(et.values.get(v)));
				} else {
					signal.putValue(pair.i, value);
				}
			}
		}
		return cex;
	}

	private int getExactInt(Interval value) {
		NumericInterval ni = (NumericInterval) value;
		if (!ni.isExact()) {
			throw new IllegalStateException();
		}
		IntEndpoint ie = (IntEndpoint) ni.getLow();
		return ie.getValue().intValue();
	}

	private Interval originalInterval(String id, int i) {
		return eval(id, i);
	}

	private boolean modelConsistent() {
		BoolInterval interval = (BoolInterval) eval(property, k - 1);
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

	public boolean modelConsistent(String id, int i, Interval proposedValue) {
		clearCacheFrom(id, i);
		cache.put(new IdIndexPair(id, i), proposedValue);
		boolean result = modelConsistent();
		clearCacheFrom(id, i);
		return result;
	}

	private void clearCacheFrom(String id, int step) {
		for (String recompute : dependsOn.get(id)) {
			for (int i = step; i < k; i++) {
				cache.remove(new IdIndexPair(recompute, i));
			}
		}
	}

	public static class AlgebraicLoopException extends RuntimeException {
		private static final long serialVersionUID = 1L;
	}

	private final Set<IdIndexPair> working = new HashSet<>();

	private Interval eval(Expr expr, int i) {
		return expr.accept(new IntervalEvaluator(i, this));
	}

	private Interval eval(String id, int i) {
		return eval(new IdExpr(id), i);
	}

	/*
	 * The IntervalEvaluator will call back in to this class to look up ids.
	 */
	public Interval evalId(String id, int i) {
		IdIndexPair pair = new IdIndexPair(id, i);
		if (cache.containsKey(pair)) {
			return cache.get(pair);
		}

		Interval result;
		if (equations.containsKey(id) && i >= 0) {
			pair = new IdIndexPair(id, i);
			if (working.contains(pair)) {
				throw new AlgebraicLoopException();
			}

			working.add(pair);
			result = eval(equations.get(id), i);
			working.remove(pair);

		} else if (generalized.containsKey(pair)) {
			result = generalized.get(pair);
		} else {
			result = getFromBasisModel(pair);
			// Due to checking all assertions, we may hit variables that our
			// property doesn't depend on. We detect and ignore these variables.
			if (i >= 0 && dependsOn.get(id) != null) {
				toGeneralize.add(pair);
			}
		}
		cache.put(pair, result);
		return result;
	}

	private Interval getFromBasisModel(IdIndexPair pair) {
		Value value = basisModel.getValue(SexpUtil.offset(pair.id, pair.i));

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
			throw new IllegalArgumentException("Unknown interval type: " + value.getClass().getName());
		}
	}
}
