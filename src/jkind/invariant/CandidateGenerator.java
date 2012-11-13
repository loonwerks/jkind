package jkind.invariant;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import jkind.lustre.SubrangeIntType;
import jkind.lustre.Type;
import jkind.sexp.Cons;
import jkind.sexp.Sexp;
import jkind.sexp.Symbol;
import jkind.translation.Specification;
import jkind.translation.Util;

public class CandidateGenerator {
	private Specification spec;
	private Sexp i;

	private List<Candidate> candidates;
	private int candidateIndex;

	public CandidateGenerator(Specification spec, Sexp i) {
		this.spec = spec;
		this.i = i;
	}

	public List<Candidate> generate() {
		candidates = new ArrayList<Candidate>();
		candidateIndex = 0;
		
		candidates.add(Candidate.TRUE);
		candidates.add(Candidate.FALSE);
		
		CombinatorialInfo info = new CombinatorialInfo(spec.node);
		
		for (String id : spec.typeMap.keySet()) {
			if (info.isCombinatorial(id)) {
				continue;
			}
			
			Type type = spec.typeMap.get(id);
			if (type == Type.BOOL) {
				Sexp s = new Cons("$" + id, i);
				addCandidate(s);
				// addCandidate(new Cons("not", s));
			} else if (type instanceof SubrangeIntType) {
				SubrangeIntType subrange = (SubrangeIntType) type;
				addCandidate(Util.subrangeConstraint(id, i, subrange));

				Sexp s = new Cons("$" + id, i);
				for (BigInteger r = subrange.low; r.compareTo(subrange.high) <= 0; r = r.add(BigInteger.ONE)) {
					addCandidate(new Cons("=", s, Sexp.fromBigInt(r)));
					// addCandidate(new Cons("/=", s, Sexp.fromBigInt(r)));
				}
			}
		}
		
		return candidates;
	}

	private void addCandidate(Sexp s) {
		Sexp iType = new Cons(i, new Symbol("::"), new Symbol("nat"));
		Candidate candidate = new Candidate("can" + candidateIndex, new Cons("lambda", iType, s));
		candidateIndex++;
		candidates.add(candidate);
	}
}
