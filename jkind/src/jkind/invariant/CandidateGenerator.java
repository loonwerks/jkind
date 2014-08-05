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
import jkind.solvers.Lambda;
import jkind.solvers.StreamDef;
import jkind.translation.Specification;
import jkind.util.SexpUtil;

public class CandidateGenerator {
	private Specification spec;

	private List<Candidate> candidates;
	private int candidateIndex;
	private InitialStepEvaluator evaluator;

	public CandidateGenerator(Specification spec) {
		this.spec = spec;
		this.evaluator = new InitialStepEvaluator(spec.node);
	}

	public List<Candidate> generate() {
		candidates = new ArrayList<>();
		candidateIndex = 0;

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
		Sexp s = new Cons("$" + id, SexpUtil.I);
		BigInteger init = getConstantInitialValue(id);
		if (init != null) {
			addCandidate(new Cons(">=", s, Sexp.fromBigInt(init)), "(" + id + " >= " + init + ")");
			addCandidate(new Cons("<=", s, Sexp.fromBigInt(init)), "(" + id + " <= " + init + ")");
		} else {
			addCandidate(new Cons(">=", s, Sexp.fromInt(0)), "(" + id + " >= 0)");
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
		Sexp s = new Cons("$" + id, SexpUtil.I);
		addCandidate(s, id);
		addCandidate(new Cons("not", s), "not " + id);
	}

	private void addSubrangeCandidates(String id, SubrangeIntType subrange) {
		addCandidate(SexpUtil.subrangeConstraint(id, SexpUtil.I, subrange), "(" + subrange.low
				+ " <= " + id + " and " + id + " <= " + subrange.high + ")");

		Sexp s = new Cons("$" + id, SexpUtil.I);
		for (BigInteger r = subrange.low; r.compareTo(subrange.high) <= 0; r = r
				.add(BigInteger.ONE)) {
			addCandidate(new Cons("=", s, Sexp.fromBigInt(r)), "(" + id + " = " + r + ")");
			// addCandidate(new Cons("/=", s, Sexp.fromBigInt(r)), "(" +
			// id + " <> " + r + ")");
		}
	}

	private void addEnumCandidates(String id, EnumType et) {
		addCandidate(SexpUtil.enumConstraint(id, SexpUtil.I, et), "(" + 0 + " <= " + id + " and "
				+ id + " < " + et.values.size() + ")");

		Sexp s = new Cons("$" + id, SexpUtil.I);
		BigInteger size = BigInteger.valueOf(et.values.size());
		for (BigInteger r = BigInteger.ZERO; r.compareTo(size) < 0; r = r.add(BigInteger.ONE)) {
			addCandidate(new Cons("=", s, Sexp.fromBigInt(r)), "(" + id + " = " + r + ")");
			// addCandidate(new Cons("/=", s, Sexp.fromBigInt(r)), "(" +
			// id + " <> " + r + ")");
		}
	}

	private void addCandidate(Sexp s, String text) {
		StreamDef def = new StreamDef("can" + candidateIndex, NamedType.BOOL, new Lambda(
				SexpUtil.I, s));
		candidateIndex++;
		candidates.add(new Candidate(def, text));
	}
}
