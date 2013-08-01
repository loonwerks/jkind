package jkind.invariant;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import jkind.lustre.SubrangeIntType;
import jkind.lustre.Type;
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

	public CandidateGenerator(Specification spec) {
		this.spec = spec;
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
			if (type == Type.BOOL) {
				Sexp s = new Cons("$" + id, SexpUtil.I);
				addCandidate(s, id);
				addCandidate(new Cons("not", s), "not " + id);
			} else if (type instanceof SubrangeIntType) {
				SubrangeIntType subrange = (SubrangeIntType) type;
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
		}

		return candidates;
	}

	private void addCandidate(Sexp s, String text) {
		StreamDef def = new StreamDef("can" + candidateIndex, Type.BOOL, new Lambda(SexpUtil.I, s));
		candidateIndex++;
		candidates.add(new Candidate(def, text));
	}
}
