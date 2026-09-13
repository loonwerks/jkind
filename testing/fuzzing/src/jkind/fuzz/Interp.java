package jkind.fuzz;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jkind.lustre.BinaryExpr;
import jkind.lustre.BoolExpr;
import jkind.lustre.CastExpr;
import jkind.lustre.Equation;
import jkind.lustre.Expr;
import jkind.lustre.IdExpr;
import jkind.lustre.IfThenElseExpr;
import jkind.lustre.IntExpr;
import jkind.lustre.NamedType;
import jkind.lustre.Node;
import jkind.lustre.Program;
import jkind.lustre.RealExpr;
import jkind.lustre.UnaryExpr;
import jkind.lustre.VarDecl;
import jkind.util.Util;

/**
 * A direct interpreter for the synchronous fragment of Lustre that the tier-2
 * generator produces: one node, scalar types, no node calls or condacts.
 *
 * It exists to check JKind's answers independently. JKind decides properties by
 * translating a model into SMT and reasoning about it; this evaluates the model
 * step by step as the language defines it. Where the two disagree, one of them
 * is wrong.
 *
 * The semantics implemented here are written out in SEMANTICS.md. Integer
 * division and modulus follow SMT conventions, matching what JKind encodes:
 * {@code div} rounds toward negative infinity and {@code mod} is non-negative.
 */
public final class Interp {

	/** Thrown when a program uses a feature outside the interpreted fragment. */
	public static final class Unsupported extends RuntimeException {
		private static final long serialVersionUID = 1L;

		public Unsupported(String message) {
			super(message);
		}
	}

	/**
	 * Negative control. When set, the interpreter's arrow operator is
	 * deliberately wrong, which must make the tier-2 oracles report findings.
	 * A run with this enabled that finds nothing means the oracles are inert
	 * and the clean runs prove nothing.
	 */
	private static final boolean SABOTAGE = Boolean.getBoolean("jkind.fuzz.sabotage");

	private final Node node;
	private final Map<String, Expr> definitions = new LinkedHashMap<>();
	private final List<String> inputs = new ArrayList<>();
	private final List<String> computed = new ArrayList<>();

	/** Values per step, filled in as steps are evaluated. */
	private List<Map<String, Val>> values;
	private final Set<String> evaluating = new HashSet<>();

	public Interp(Program program) {
		if (!program.functions.isEmpty()) {
			throw new Unsupported("uninterpreted functions");
		}
		if (program.nodes.size() != 1) {
			throw new Unsupported("expected exactly one node");
		}
		if (!program.types.isEmpty()) {
			throw new Unsupported("type declarations");
		}
		if (!program.constants.isEmpty()) {
			throw new Unsupported("constant declarations");
		}
		this.node = program.nodes.get(0);

		for (VarDecl vd : node.inputs) {
			requireScalar(vd);
			inputs.add(vd.id);
		}
		for (VarDecl vd : node.outputs) {
			requireScalar(vd);
			computed.add(vd.id);
		}
		for (VarDecl vd : node.locals) {
			requireScalar(vd);
			computed.add(vd.id);
		}

		for (Equation eq : node.equations) {
			if (eq.lhs.size() != 1) {
				throw new Unsupported("multiple assignment");
			}
			definitions.put(eq.lhs.get(0).id, eq.expr);
		}
		for (String id : computed) {
			if (!definitions.containsKey(id)) {
				throw new Unsupported("variable without an equation: " + id);
			}
		}
	}

	private void requireScalar(VarDecl vd) {
		if (!(vd.type instanceof NamedType) && !(vd.type instanceof jkind.lustre.SubrangeIntType)) {
			throw new Unsupported("non-scalar type: " + vd.type);
		}
	}

	public List<String> inputNames() {
		return inputs;
	}

	public List<String> computedNames() {
		return computed;
	}

	public List<String> propertyNames() {
		return node.properties;
	}

	/** The result of evaluating a model over a concrete input trace. */
	public static final class Trace {
		/** Value of every variable at every step. */
		public final List<Map<String, Val>> values;
		/** Whether every assertion held, per step. */
		public final List<Boolean> assertionsHold;

		Trace(List<Map<String, Val>> values, List<Boolean> assertionsHold) {
			this.values = values;
			this.assertionsHold = assertionsHold;
		}

		/** True when all assertions hold at every step up to and including k. */
		public boolean assertionsHoldThrough(int k) {
			for (int i = 0; i <= k && i < assertionsHold.size(); i++) {
				if (!assertionsHold.get(i)) {
					return false;
				}
			}
			return true;
		}
	}

	/**
	 * Evaluates the model over the given input trace. Entry {@code k} of
	 * {@code inputTrace} supplies a value for each input at step {@code k}.
	 */
	public Trace run(List<Map<String, Val>> inputTrace) {
		int steps = inputTrace.size();
		values = new ArrayList<>();
		for (int k = 0; k < steps; k++) {
			values.add(new HashMap<>(inputTrace.get(k)));
		}

		List<Boolean> assertionsHold = new ArrayList<>();
		for (int k = 0; k < steps; k++) {
			for (String id : computed) {
				valueOf(id, k);
			}
			boolean holds = true;
			for (Expr assertion : node.assertions) {
				holds &= asBool(eval(assertion, k));
			}
			assertionsHold.add(holds);
		}
		return new Trace(values, assertionsHold);
	}

	private Val valueOf(String id, int k) {
		Map<String, Val> step = values.get(k);
		Val known = step.get(id);
		if (known != null) {
			return known;
		}
		Expr definition = definitions.get(id);
		if (definition == null) {
			throw new Unsupported("no value for " + id);
		}
		String marker = id + "@" + k;
		if (!evaluating.add(marker)) {
			throw new Unsupported("algebraic loop through " + id);
		}
		try {
			Val value = eval(definition, k);
			step.put(id, value);
			return value;
		} finally {
			evaluating.remove(marker);
		}
	}

	private Val eval(Expr e, int k) {
		if (e instanceof IntExpr) {
			return new Val.Int(((IntExpr) e).value);
		}
		if (e instanceof BoolExpr) {
			return new Val.Bool(((BoolExpr) e).value);
		}
		if (e instanceof RealExpr) {
			return Val.Rat.parseDecimal(((RealExpr) e).value.toString());
		}
		if (e instanceof IdExpr) {
			return valueOf(((IdExpr) e).id, k);
		}
		if (e instanceof IfThenElseExpr) {
			IfThenElseExpr ite = (IfThenElseExpr) e;
			return asBool(eval(ite.cond, k)) ? eval(ite.thenExpr, k) : eval(ite.elseExpr, k);
		}
		if (e instanceof CastExpr) {
			return cast((CastExpr) e, k);
		}
		if (e instanceof UnaryExpr) {
			return unary((UnaryExpr) e, k);
		}
		if (e instanceof BinaryExpr) {
			return binary((BinaryExpr) e, k);
		}
		throw new Unsupported("expression form: " + e.getClass().getSimpleName());
	}

	private Val cast(CastExpr e, int k) {
		Val value = eval(e.expr, k);
		if (e.type == NamedType.REAL) {
			if (value instanceof Val.Int) {
				return new Val.Rat(((Val.Int) value).value, BigInteger.ONE);
			}
			return value;
		}
		if (e.type == NamedType.INT) {
			if (value instanceof Val.Rat) {
				return new Val.Int(((Val.Rat) value).floor());
			}
			return value;
		}
		throw new Unsupported("cast to " + e.type);
	}

	private Val unary(UnaryExpr e, int k) {
		switch (e.op) {
		case NOT:
			return new Val.Bool(!asBool(eval(e.expr, k)));
		case NEGATIVE: {
			Val value = eval(e.expr, k);
			if (value instanceof Val.Int) {
				return new Val.Int(((Val.Int) value).value.negate());
			}
			return ((Val.Rat) value).negate();
		}
		case PRE:
			if (k == 0) {
				// The generator only emits guarded pre, so reaching step -1
				// means the program is outside the interpreted fragment.
				throw new Unsupported("unguarded pre");
			}
			return eval(e.expr, k - 1);
		default:
			throw new Unsupported("unary operator " + e.op);
		}
	}

	private Val binary(BinaryExpr e, int k) {
		switch (e.op) {
		case ARROW:
			if (SABOTAGE) {
				return k == 0 ? eval(e.right, 0) : eval(e.left, k);
			}
			return k == 0 ? eval(e.left, 0) : eval(e.right, k);
		case AND:
			// Lustre's logical operators are not short-circuiting, but both
			// operands are total here, so evaluation order is unobservable.
			return new Val.Bool(asBool(eval(e.left, k)) & asBool(eval(e.right, k)));
		case OR:
			return new Val.Bool(asBool(eval(e.left, k)) | asBool(eval(e.right, k)));
		case XOR:
			return new Val.Bool(asBool(eval(e.left, k)) ^ asBool(eval(e.right, k)));
		case IMPLIES:
			return new Val.Bool(!asBool(eval(e.left, k)) | asBool(eval(e.right, k)));
		default:
			break;
		}

		Val left = eval(e.left, k);
		Val right = eval(e.right, k);

		if (e.op == jkind.lustre.BinaryOp.EQUAL) {
			return new Val.Bool(left.equals(right));
		}
		if (e.op == jkind.lustre.BinaryOp.NOTEQUAL) {
			return new Val.Bool(!left.equals(right));
		}

		if (left instanceof Val.Int && right instanceof Val.Int) {
			return integerOp(e, ((Val.Int) left).value, ((Val.Int) right).value);
		}
		if (left instanceof Val.Rat && right instanceof Val.Rat) {
			return rationalOp(e, (Val.Rat) left, (Val.Rat) right);
		}
		throw new Unsupported("operator " + e.op + " on " + left + " and " + right);
	}

	private Val integerOp(BinaryExpr e, BigInteger a, BigInteger b) {
		switch (e.op) {
		case PLUS:
			return new Val.Int(a.add(b));
		case MINUS:
			return new Val.Int(a.subtract(b));
		case MULTIPLY:
			return new Val.Int(a.multiply(b));
		case INT_DIVIDE:
			if (b.signum() == 0) {
				throw new Unsupported("division by zero");
			}
			return new Val.Int(Util.smtDivide(a, b));
		case MODULUS:
			if (b.signum() <= 0) {
				throw new Unsupported("modulus by a non-positive value");
			}
			return new Val.Int(a.mod(b));
		case GREATER:
			return new Val.Bool(a.compareTo(b) > 0);
		case GREATEREQUAL:
			return new Val.Bool(a.compareTo(b) >= 0);
		case LESS:
			return new Val.Bool(a.compareTo(b) < 0);
		case LESSEQUAL:
			return new Val.Bool(a.compareTo(b) <= 0);
		default:
			throw new Unsupported("integer operator " + e.op);
		}
	}

	private Val rationalOp(BinaryExpr e, Val.Rat a, Val.Rat b) {
		switch (e.op) {
		case PLUS:
			return a.add(b);
		case MINUS:
			return a.subtract(b);
		case MULTIPLY:
			return a.multiply(b);
		case DIVIDE:
			if (b.num.signum() == 0) {
				throw new Unsupported("division by zero");
			}
			return a.divide(b);
		case GREATER:
			return new Val.Bool(a.compareTo(b) > 0);
		case GREATEREQUAL:
			return new Val.Bool(a.compareTo(b) >= 0);
		case LESS:
			return new Val.Bool(a.compareTo(b) < 0);
		case LESSEQUAL:
			return new Val.Bool(a.compareTo(b) <= 0);
		default:
			throw new Unsupported("real operator " + e.op);
		}
	}

	private static boolean asBool(Val v) {
		if (!(v instanceof Val.Bool)) {
			throw new Unsupported("expected a boolean, got " + v);
		}
		return ((Val.Bool) v).value;
	}
}
