package jkind.invariant;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import jkind.analysis.evaluation.InitialStepEvaluator;
import jkind.lustre.EnumType;
import jkind.lustre.NamedType;
import jkind.lustre.SubrangeIntType;
import jkind.lustre.Type;
import jkind.lustre.values.IntegerValue;
import jkind.lustre.values.Value;
import jkind.sexp.Cons;
import jkind.sexp.Sexp;
import jkind.sexp.Symbol;
import jkind.solvers.Lambda;
import jkind.translation.Specification;
import jkind.util.SexpUtil;

public class CandidateGenerator {
	private Specification spec;

	private List<Candidate> candidates;
	private InitialStepEvaluator evaluator;

	public CandidateGenerator(Specification spec) {
		this.spec = spec;
		this.evaluator = new InitialStepEvaluator(spec.node);
	}

	public List<Candidate> generate() {
		candidates = new ArrayList<>();

		candidates.add(Candidate.TRUE);
		candidates.add(Candidate.FALSE);

		CombinatorialInfo info = new CombinatorialInfo(spec.node);

		for (String id : spec.typeMap.keySet()) {
			if (info.isCombinatorial(id) && !spec.node.properties.contains(id)) {
				continue;
			}

			Type type = spec.typeMap.get(id);
			if (type == NamedType.INT) {
				addIntCandidates(id);
			} else if (type == NamedType.BOOL) {
				addBoolCandidates(id);
			} else if (type instanceof SubrangeIntType) {
				addSubrangeCandidates(id, (SubrangeIntType) type);
			} else if (type instanceof EnumType) {
				addEnumCandidates(id, (EnumType) type);
			}
		}

		return candidates;
	}

	private void addIntCandidates(String id) {
		Sexp var = new Symbol(id);
		BigInteger init = getConstantInitialValue(id);
		if (init != null) {
			addCandidate(id, new Cons(">=", var, Sexp.fromBigInt(init)), "(" + id + " >= " + init
					+ ")");
			addCandidate(id, new Cons("<=", var, Sexp.fromBigInt(init)), "(" + id + " <= " + init
					+ ")");
		} else {
			addCandidate(id, new Cons(">=", var, Sexp.fromInt(0)), "(" + id + " >= 0)");
		}
	}

	private BigInteger getConstantInitialValue(String id) {
		Value value = evaluator.eval(id);
		if (value instanceof IntegerValue) {
			IntegerValue iv = (IntegerValue) value;
			return iv.value;
		}
		return null;
	}

	private void addBoolCandidates(String id) {
		Sexp var = new Symbol(id);
		addCandidate(id, var, id);
		addCandidate(id, new Cons("not", var), "not " + id);
	}

	private void addSubrangeCandidates(String id, SubrangeIntType subrange) {
		Sexp var = new Symbol(id);
		addCandidate(id, SexpUtil.subrangeConstraint(id, subrange), "(" + subrange.low + " <= "
				+ id + " and " + id + " <= " + subrange.high + ")");

		for (BigInteger r = subrange.low; r.compareTo(subrange.high) <= 0; r = r
				.add(BigInteger.ONE)) {
			addCandidate(id, new Cons("=", var, Sexp.fromBigInt(r)), "(" + id + " = " + r + ")");
		}
	}

	private void addEnumCandidates(String id, EnumType et) {
		BigInteger low = BigInteger.ZERO;
		BigInteger high = BigInteger.valueOf(et.values.size() - 1);
		addSubrangeCandidates(id, new SubrangeIntType(low, high));
	}

	private void addCandidate(String id, Sexp s, String text) {
		candidates.add(new Candidate(new Lambda(id, s), text));
	}
}
