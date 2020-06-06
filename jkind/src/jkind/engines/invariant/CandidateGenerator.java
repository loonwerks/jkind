package jkind.engines.invariant;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import jkind.analysis.evaluation.InitialStepEvaluator;
import jkind.lustre.BinaryExpr;
import jkind.lustre.BinaryOp;
import jkind.lustre.BoolExpr;
import jkind.lustre.EnumType;
import jkind.lustre.Expr;
import jkind.lustre.IdExpr;
import jkind.lustre.IntExpr;
import jkind.lustre.NamedType;
import jkind.lustre.SubrangeIntType;
import jkind.lustre.Type;
import jkind.lustre.UnaryExpr;
import jkind.lustre.UnaryOp;
import jkind.lustre.values.IntegerValue;
import jkind.lustre.values.Value;
import jkind.translation.Specification;

public class CandidateGenerator {
	private Specification spec;

	private List<Expr> candidates;
	private InitialStepEvaluator evaluator;

	public CandidateGenerator(Specification spec) {
		this.spec = spec;
		this.evaluator = new InitialStepEvaluator(spec.node);
	}

	public List<Expr> generate() {
		candidates = new ArrayList<>();

		candidates.add(new BoolExpr(true));
		candidates.add(new BoolExpr(false));

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
		BigInteger init = getConstantInitialValue(id);
		IdExpr idExpr = new IdExpr(id);
		if (init != null) {
			IntExpr initExpr = new IntExpr(init);
			candidates.add(new BinaryExpr(idExpr, BinaryOp.GREATEREQUAL, initExpr));
			candidates.add(new BinaryExpr(idExpr, BinaryOp.LESSEQUAL, initExpr));
		} else {
			candidates.add(new BinaryExpr(idExpr, BinaryOp.GREATEREQUAL, new IntExpr(0)));
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
		candidates.add(new IdExpr(id));
		candidates.add(new UnaryExpr(UnaryOp.NOT, new IdExpr(id)));
	}

	private void addSubrangeCandidates(String id, SubrangeIntType subrange) {
		IdExpr idExpr = new IdExpr(id);
		for (BigInteger r = subrange.low; r.compareTo(subrange.high) <= 0; r = r.add(BigInteger.ONE)) {
			candidates.add(new BinaryExpr(idExpr, BinaryOp.EQUAL, new IntExpr(r)));
		}
	}

	private void addEnumCandidates(String id, EnumType et) {
		BigInteger low = BigInteger.ZERO;
		BigInteger high = BigInteger.valueOf(et.values.size() - 1);
		addSubrangeCandidates(id, new SubrangeIntType(low, high));
	}
}
