package jkind.engines.invariant;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jkind.JKindSettings;
import jkind.analysis.evaluation.InitialStepEvaluator;
import jkind.lustre.BinaryExpr;
import jkind.lustre.BinaryOp;
import jkind.lustre.BoolExpr;
import jkind.lustre.Constant;
import jkind.lustre.EnumType;
import jkind.lustre.Expr;
import jkind.lustre.IdExpr;
import jkind.lustre.IntExpr;
import jkind.lustre.NamedType;
import jkind.lustre.SubrangeIntType;
import jkind.lustre.Type;
import jkind.lustre.UnaryExpr;
import jkind.lustre.UnaryOp;
import jkind.lustre.VarDecl;
import jkind.lustre.values.IntegerValue;
import jkind.lustre.values.Value;
import jkind.translation.ConstantsLookupVisitor;
import jkind.translation.Specification;

public class CandidateGenerator {
	private Specification spec;
	private JKindSettings settings;

	private List<Expr> candidates;
	private InitialStepEvaluator evaluator;
	CombinatorialInfo info;

	private Map<String, String> mapInputOutputLocalTypes; // Map containing locals, inputs and outputs types

	public CandidateGenerator(Specification spec, JKindSettings settings) {
		this.spec = spec;
		this.settings = settings;
		this.evaluator = new InitialStepEvaluator(spec.node);
		this.info = new CombinatorialInfo(spec.node);
	}

	public List<Expr> generate() {
		/*
		 * Constant and integer candidates storage
		 */
		ArrayList<String> int_candidates = new ArrayList<String>(); // Integer candidates list
		Set<BigInteger> constants = new HashSet<BigInteger>(); // Constant candidates list
		ConstantsLookupVisitor clv = new ConstantsLookupVisitor();
		clv.visit(spec.node);
		constants = clv.getConstants(); // Constant candidates list creation

		candidates = new ArrayList<>();

		candidates.add(new BoolExpr(true));
		candidates.add(new BoolExpr(false));

		CombinatorialInfo info = new CombinatorialInfo(spec.node);

		for (String id : spec.typeMap.keySet()) {
			if (!settings.combinatorialCandidates // Getting all ids
					&& info.isCombinatorial(id) && !spec.node.properties.contains(id)) {
				continue;
			}

			Type type = spec.typeMap.get(id);
			if (type == NamedType.INT) {
				if (settings.initCandidates) {
					addIntCandidates(id);
				}
				int_candidates.add(id); // Integer IDs list creation. Used later for int x int and int x const candidates generation
			} else if (type == NamedType.BOOL && settings.boolCandidates) {
				addBoolCandidates(id);
			} else if (type instanceof SubrangeIntType && settings.subrangeCandidates) {
				addSubrangeCandidates(id, (SubrangeIntType) type);
			} else if (type instanceof EnumType && settings.enumCandidates) {
				addEnumCandidates(id, (EnumType) type);
			}
		}

		/*
		 * Additional candidates for long-running timer properties
		 */
		// Adding int x int candidates
		if (settings.allIntIntCandidates)
			addIntIntCandidates(int_candidates);
		// Adding int x const candidates
		if (settings.allIntConstCandidates) {
			addIntConstCandidates(constants, int_candidates);
		}

		if (settings.typedIntIntCandidates || settings.typedIntConstCandidates) {
			// Creates the list of int IDs with types
			this.createInputOutputLocalTypes();
			// Adding int x int candidates with matching types
			if (settings.typedIntIntCandidates && !settings.allIntIntCandidates) {
				addTypedIntIntCandidates();
			}
			// Adding int x const candidates with matching types
			if (settings.typedIntConstCandidates && !settings.allIntConstCandidates) {
				addTypedIntConstCandidates();
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

	/*
	 * INT x INT candidates no matter their type
	 */
	private void addIntIntCandidates(List<String> int_candidates) {
		for (String id1 : int_candidates)
			for (String id2 : int_candidates)
				if (id1 != id2) {
					candidates.add(new BinaryExpr(new IdExpr(id1), BinaryOp.GREATEREQUAL, new IdExpr(id2)));
				}
	}

	/*
	 * INT x CONST candidates no matter their type
	 */
	private void addIntConstCandidates(Set<BigInteger> constants, List<String> int_candidates) {
		for (String id : int_candidates)
			for (BigInteger ct : constants) {
				candidates.add(new BinaryExpr(new IdExpr(id), BinaryOp.GREATEREQUAL, new IntExpr(ct)));
				candidates.add(new BinaryExpr(new IdExpr(id), BinaryOp.LESSEQUAL, new IntExpr(ct)));
			}
	}

	/*
	 * Creates a map with input, output and local types for all non boolean flows.
	 * The key of the map equals to the id of the VarDecl and the value is the
	 * original type
	 */
	private void createInputOutputLocalTypes() {
		this.mapInputOutputLocalTypes = new HashMap<String, String>();
		List<VarDecl> ids = new ArrayList<VarDecl>();
		if (this.spec.node.inputs != null) {
			ids.addAll(this.spec.node.inputs);
		}
		if (this.spec.node.outputs != null) {
			ids.addAll(this.spec.node.outputs);
		}
		if (this.spec.node.locals != null) {
			ids.addAll(this.spec.node.locals);
		}
		if (ids != null) {
			for (VarDecl id : ids) {
				if (id.originalType != "bool")
					mapInputOutputLocalTypes.put(id.id, id.originalType);
			}
		}

	}

	/*
	 * Adding int x int candidates only if the left and right side has the same
	 * original type (before inlining)
	 */
	private void addTypedIntIntCandidates() {
		for (String id1 : this.mapInputOutputLocalTypes.keySet()) {
			for (String id2 : this.mapInputOutputLocalTypes.keySet()) {
				if (this.mapInputOutputLocalTypes.get(id1).equals(this.mapInputOutputLocalTypes.get(id2))
						&& id1 != id2) {
					if (!settings.combinatorialCandidates // Getting all ids
							&& (info.isCombinatorial(id1) || info.isCombinatorial(id2))
							&& (!spec.node.properties.contains(id1) || !spec.node.properties.contains(id2)))
						continue;
					candidates.add(new BinaryExpr(new IdExpr(id1), BinaryOp.GREATEREQUAL, new IdExpr(id2)));
				}
			}
		}
	}

	/*
	 * Adding int x const candidates only if the left and right side has the same
	 * original type (before inlining)
	 */
	private void addTypedIntConstCandidates() {
		for (String id : this.mapInputOutputLocalTypes.keySet()) {
			if (!settings.combinatorialCandidates // Getting all ids
					&& info.isCombinatorial(id) && !spec.node.properties.contains(id))
				continue;
			for (Constant ct : this.spec.constants) {
				if (ct.expr instanceof IntExpr) {
					if (this.mapInputOutputLocalTypes.get(id).equals(ct.type.toString())) {
						candidates.add(new BinaryExpr(new IdExpr(id), BinaryOp.GREATEREQUAL, (IntExpr) ct.expr));
						candidates.add(new BinaryExpr(new IdExpr(id), BinaryOp.LESSEQUAL, (IntExpr) ct.expr));
					}
				}
			}
		}
	}
}
