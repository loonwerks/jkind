package jkind.fuzz;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Random;

/**
 * Seeded generator of random Lustre programs in JKind's dialect.
 *
 * The generator is deterministic in its seed: the same seed and configuration
 * always produce the same program text, so a crashing input is reproducible
 * from its seed alone.
 *
 * Two broad modes are supported. In well-typed mode the generator only emits
 * programs it believes pass static analysis, which drives inputs deep into
 * translation and the solving engines. In chaos mode it deliberately injects
 * semantic faults (unknown identifiers, type mismatches, empty subranges,
 * division by zero, out-of-bounds constant indices, ...) which JKind is
 * expected to reject with a clean diagnostic rather than a stack trace.
 */
public final class LustreGen {

	/** Tunable generation weights and limits. */
	public static final class Config {
		public int maxNodes = 3;
		public int maxInputs = 4;
		public int maxOutputs = 3;
		public int maxLocals = 5;
		public int maxExprDepth = 4;
		public int maxTypedefs = 3;
		public int maxConstants = 3;
		public int maxFunctions = 2;
		public int maxAsserts = 2;
		/** Probability of injecting a semantic fault at each opportunity. */
		public double chaos = 0.0;
		/** Allow compound types (records, arrays, enums, subranges). */
		public boolean compoundTypes = true;
		/** Allow real-typed values. */
		public boolean reals = true;
		/** Allow node calls and condacts. */
		public boolean nodeCalls = true;
		/** Emit --%IVC annotations. */
		public boolean ivc = true;
		/**
		 * Restrict multiplication and division to a constant operand, keeping
		 * generated verification conditions inside linear arithmetic so that
		 * end-to-end solver runs terminate quickly.
		 */
		public boolean linearOnly = true;
		/** Bound on the magnitude of generated integer literals. */
		public int intLiteralBound = 20;
		/** Never emit integer literals outside 64-bit range. */
		public boolean hugeLiterals = true;
		/**
		 * Define each variable only in terms of inputs, variables defined
		 * earlier, and (under a pre) anything. This rules out algebraic loops,
		 * which have no step-by-step meaning and cannot be interpreted.
		 */
		public boolean acyclic = false;

		public static Config wellTyped() {
			return new Config();
		}

		public static Config chaos(double p) {
			Config c = new Config();
			c.chaos = p;
			return c;
		}

		/** Non-linear arithmetic, which only some solvers accept. */
		public static Config nonlinear() {
			Config c = new Config();
			c.linearOnly = false;
			return c;
		}

		/** Small, side-effect free programs suited to interpretation. */
		public static Config interpretable() {
			Config c = new Config();
			c.maxNodes = 2;
			c.maxExprDepth = 3;
			c.compoundTypes = false;
			c.reals = false;
			c.ivc = false;
			c.linearOnly = true;
			c.hugeLiterals = false;
			c.intLiteralBound = 8;
			c.maxAsserts = 1;
			c.acyclic = true;
			c.maxNodes = 1;
			c.nodeCalls = false;
			c.maxFunctions = 0;
			c.maxTypedefs = 0;
			c.maxConstants = 0;
			return c;
		}
	}

	private final Random rnd;
	private final Config cfg;
	private final StringBuilder out = new StringBuilder();

	private final List<Ty> typedefs = new ArrayList<>();
	private final List<Var> constants = new ArrayList<>();
	private final List<Sig> functions = new ArrayList<>();
	private final List<Sig> nodes = new ArrayList<>();

	/** Variables visible in the node currently being generated. */
	private List<Var> scope = new ArrayList<>();
	/** Inputs, plus variables whose equations have already been emitted. */
	private final java.util.Set<String> defined = new java.util.HashSet<>();
	/** Nesting depth inside pre, where referring to any variable is safe. */
	private int preDepth = 0;
	private int idCounter = 0;

	public static final class Var {
		public final String id;
		public final Ty ty;

		Var(String id, Ty ty) {
			this.id = id;
			this.ty = ty;
		}
	}

	/** Signature of a generated node or uninterpreted function. */
	public static final class Sig {
		public final String id;
		public final List<Ty> inputs;
		public final List<Ty> outputs;
		public final boolean isFunction;

		Sig(String id, List<Ty> inputs, List<Ty> outputs, boolean isFunction) {
			this.id = id;
			this.inputs = inputs;
			this.outputs = outputs;
			this.isFunction = isFunction;
		}
	}

	public LustreGen(long seed, Config cfg) {
		this.rnd = new Random(seed);
		this.cfg = cfg;
	}

	public static String generate(long seed, Config cfg) {
		return new LustreGen(seed, cfg).program();
	}

	// ------------------------------------------------------------------
	// Program structure
	// ------------------------------------------------------------------

	public String program() {
		genTypedefs();
		genConstants();
		genFunctions();

		int nodeCount = 1 + rnd.nextInt(Math.max(1, cfg.maxNodes));
		for (int i = 0; i < nodeCount; i++) {
			boolean isMain = i == nodeCount - 1;
			genNode(isMain);
		}
		return out.toString();
	}

	private void genTypedefs() {
		if (!cfg.compoundTypes) {
			return;
		}
		int count = rnd.nextInt(cfg.maxTypedefs + 1);
		for (int i = 0; i < count; i++) {
			String name = "t" + (idCounter++);
			switch (rnd.nextInt(4)) {
			case 0: { // record
				LinkedHashMap<String, Ty> fields = new LinkedHashMap<>();
				int n = 1 + rnd.nextInt(3);
				StringBuilder body = new StringBuilder();
				for (int f = 0; f < n; f++) {
					String fieldName = "f" + (idCounter++);
					Ty ft = simpleType();
					fields.put(fieldName, ft);
					if (f > 0) {
						body.append("; ");
					}
					body.append(fieldName).append(" : ").append(ft.syn);
				}
				out.append("type ").append(name).append(" = struct { ").append(body).append(" };\n");
				typedefs.add(Ty.record(name, fields));
				break;
			}
			case 1: { // enum
				List<String> values = new ArrayList<>();
				int n = 1 + rnd.nextInt(3);
				for (int v = 0; v < n; v++) {
					values.add("E" + (idCounter++));
				}
				out.append("type ").append(name).append(" = enum { ").append(String.join(", ", values))
						.append(" };\n");
				typedefs.add(Ty.enumeration(name, values));
				break;
			}
			case 2: { // array alias
				Ty elem = simpleType();
				int size = chaosHit() ? 0 : 1 + rnd.nextInt(4);
				out.append("type ").append(name).append(" = ").append(elem.syn).append("[").append(size)
						.append("];\n");
				typedefs.add(Ty.array(elem, size).aliased(name));
				break;
			}
			default: { // plain alias
				Ty base = simpleType();
				out.append("type ").append(name).append(" = ").append(base.syn).append(";\n");
				typedefs.add(base.aliased(name));
				break;
			}
			}
		}
		if (count > 0) {
			out.append("\n");
		}
	}

	private void genConstants() {
		int count = rnd.nextInt(cfg.maxConstants + 1);
		for (int i = 0; i < count; i++) {
			String name = "c" + (idCounter++);
			Ty ty = simpleType();
			// Constants must be constant expressions: build from literals only.
			String value = literal(ty);
			out.append("const ").append(name).append(" : ").append(ty.syn).append(" = ").append(value).append(";\n");
			constants.add(new Var(name, ty));
		}
		if (count > 0) {
			out.append("\n");
		}
	}

	private void genFunctions() {
		if (!cfg.nodeCalls) {
			return;
		}
		int count = rnd.nextInt(cfg.maxFunctions + 1);
		for (int i = 0; i < count; i++) {
			String name = "fn" + (idCounter++);
			List<Ty> ins = new ArrayList<>();
			List<Ty> outs = new ArrayList<>();
			StringBuilder inDecl = new StringBuilder();
			int nin = rnd.nextInt(3);
			for (int a = 0; a < nin; a++) {
				Ty t = unconstrainedType();
				ins.add(t);
				if (a > 0) {
					inDecl.append("; ");
				}
				inDecl.append("a").append(a).append(" : ").append(t.syn);
			}
			// Function outputs may not be constrained types (subrange, enum).
			Ty outTy = chaosHit() ? Ty.subrange(0, 5) : unconstrainedType();
			outs.add(outTy);
			out.append("function ").append(name).append("(").append(inDecl).append(") returns (y : ")
					.append(outTy.syn).append(");\n");
			functions.add(new Sig(name, ins, outs, true));
		}
		if (count > 0) {
			out.append("\n");
		}
	}

	private void genNode(boolean isMain) {
		String name = "n" + (idCounter++);
		scope = new ArrayList<>();

		List<Var> inputs = declVars("i", rnd.nextInt(cfg.maxInputs + 1));
		List<Var> outputs = declVars("o", 1 + rnd.nextInt(cfg.maxOutputs));
		List<Var> locals = declVars("v", rnd.nextInt(cfg.maxLocals + 1));

		boolean hasBool = false;
		for (Var v : outputs) {
			hasBool |= v.ty.kind == Ty.K.BOOL && v.ty.syn.equals("bool");
		}
		for (Var v : locals) {
			hasBool |= v.ty.kind == Ty.K.BOOL && v.ty.syn.equals("bool");
		}
		if (!hasBool) {
			locals.add(new Var("v" + (idCounter++), Ty.BOOL));
		}

		scope.addAll(inputs);
		scope.addAll(outputs);
		scope.addAll(locals);

		out.append("node ").append(name).append("(").append(declList(inputs)).append(") returns (")
				.append(declList(outputs)).append(");\n");
		if (!locals.isEmpty()) {
			out.append("var\n  ").append(declList(locals)).append(";\n");
		}
		out.append("let\n");
		if (isMain) {
			out.append("  --%MAIN;\n");
		}

		List<Var> toAssign = new ArrayList<>();
		toAssign.addAll(outputs);
		toAssign.addAll(locals);
		for (Var v : inputs) {
			defined.add(v.id);
		}
		for (Var v : toAssign) {
			if (chaosHit()) {
				// Omit an assignment, or assign a variable twice, or assign an
				// input: all of these must be reported as unsound assignments.
				switch (rnd.nextInt(3)) {
				case 0:
					continue;
				case 1:
					out.append("  ").append(v.id).append(" = ").append(expr(v.ty, cfg.maxExprDepth)).append(";\n");
					break;
				default:
					break;
				}
			}
			out.append("  ").append(v.id).append(" = ").append(expr(v.ty, cfg.maxExprDepth)).append(";\n");
			defined.add(v.id);
		}

		int asserts = rnd.nextInt(cfg.maxAsserts + 1);
		for (int i = 0; i < asserts; i++) {
			out.append("  assert ").append(expr(Ty.BOOL, Math.min(3, cfg.maxExprDepth))).append(";\n");
		}

		List<Var> bools = new ArrayList<>();
		for (Var v : toAssign) {
			if (v.ty.kind == Ty.K.BOOL && v.ty.syn.equals("bool")) {
				bools.add(v);
			}
		}
		boolean emitted = false;
		for (Var v : bools) {
			if (rnd.nextBoolean()) {
				out.append("  --%PROPERTY ").append(v.id).append(";\n");
				emitted = true;
			}
		}
		if (!emitted && !bools.isEmpty()) {
			out.append("  --%PROPERTY ").append(bools.get(0).id).append(";\n");
		}
		if (chaosHit()) {
			out.append("  --%PROPERTY ").append("undefined_prop_").append(idCounter++).append(";\n");
		}
		if (cfg.ivc && !toAssign.isEmpty() && rnd.nextInt(4) == 0) {
			Var v = toAssign.get(rnd.nextInt(toAssign.size()));
			out.append("  --%IVC ").append(v.id).append(";\n");
		}

		out.append("tel;\n\n");

		List<Ty> inTys = new ArrayList<>();
		for (Var v : inputs) {
			inTys.add(v.ty);
		}
		List<Ty> outTys = new ArrayList<>();
		for (Var v : outputs) {
			outTys.add(v.ty);
		}
		nodes.add(new Sig(name, inTys, outTys, false));
	}

	private List<Var> declVars(String prefix, int count) {
		List<Var> vars = new ArrayList<>();
		for (int i = 0; i < count; i++) {
			vars.add(new Var(prefix + (idCounter++), varType()));
		}
		return vars;
	}

	private String declList(List<Var> vars) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < vars.size(); i++) {
			if (i > 0) {
				sb.append("; ");
			}
			sb.append(vars.get(i).id).append(" : ").append(vars.get(i).ty.syn);
		}
		return sb.toString();
	}

	// ------------------------------------------------------------------
	// Types
	// ------------------------------------------------------------------

	/** Types without records, arrays or enums. */
	private Ty simpleType() {
		int n = cfg.reals ? 3 : 2;
		switch (rnd.nextInt(n)) {
		case 0:
			return Ty.BOOL;
		case 1:
			return Ty.INT;
		default:
			return Ty.REAL;
		}
	}

	/** Types legal as function outputs: no subranges or enums. */
	private Ty unconstrainedType() {
		return simpleType();
	}

	/** Any type a variable may be declared with. */
	private Ty varType() {
		if (cfg.compoundTypes && !typedefs.isEmpty() && rnd.nextInt(4) == 0) {
			return typedefs.get(rnd.nextInt(typedefs.size()));
		}
		if (cfg.compoundTypes && rnd.nextInt(8) == 0) {
			if (chaosHit()) {
				// Empty subrange: low bound above high bound.
				return Ty.subrange(5, 2);
			}
			long lo = -10 + rnd.nextInt(10);
			return Ty.subrange(lo, lo + rnd.nextInt(20));
		}
		if (cfg.compoundTypes && rnd.nextInt(10) == 0) {
			return Ty.array(simpleType(), 1 + rnd.nextInt(3));
		}
		return simpleType();
	}

	// ------------------------------------------------------------------
	// Expressions
	// ------------------------------------------------------------------

	private boolean chaosHit() {
		return cfg.chaos > 0 && rnd.nextDouble() < cfg.chaos;
	}

	private String expr(Ty t, int depth) {
		if (chaosHit()) {
			return chaosExpr(t, depth);
		}
		if (depth <= 0 || rnd.nextInt(4) == 0) {
			return leaf(t);
		}

		List<String> choices = new ArrayList<>();
		choices.add("ite");
		choices.add("arrow");
		choices.add("pre");
		choices.add("paren");
		if (t.kind == Ty.K.BOOL) {
			choices.add("not");
			choices.add("logic");
			choices.add("compare");
			choices.add("equality");
		}
		if (t.kind == Ty.K.INT) {
			choices.add("intArith");
			if (cfg.reals) {
				choices.add("floor");
			}
		}
		if (t.kind == Ty.K.SUBRANGE) {
			choices.add("boundedIte");
		}
		if (t.kind == Ty.K.REAL) {
			choices.add("realArith");
			choices.add("realCast");
		}
		if (t.kind == Ty.K.ARRAY) {
			choices.add("arrayLit");
			choices.add("arrayUpdate");
		}
		if (t.kind == Ty.K.RECORD) {
			choices.add("recordLit");
			choices.add("recordUpdate");
		}
		if (cfg.nodeCalls && hasCallReturning(t)) {
			choices.add("call");
		}
		String pick = choices.get(rnd.nextInt(choices.size()));
		return build(pick, t, depth);
	}

	private String build(String pick, Ty t, int depth) {
		int d = depth - 1;
		switch (pick) {
		case "ite":
			return "(if " + expr(Ty.BOOL, d) + " then " + expr(t, d) + " else " + expr(t, d) + ")";
		case "arrow":
			return "(" + expr(t, d) + " -> " + expr(t, d) + ")";
		case "pre": {
			// Guard the pre so the program stays meaningful at step 0. The
			// guard is evaluated at step 0, so it is bound by the ordering
			// constraint; the body refers to the previous step and is not.
			String guard = leaf(t);
			preDepth++;
			String body = expr(t, d);
			preDepth--;
			return "(" + guard + " -> pre " + body + ")";
		}
		case "paren":
			return "(" + expr(t, d) + ")";
		case "not":
			return "(not " + expr(Ty.BOOL, d) + ")";
		case "logic": {
			String[] ops = { "and", "or", "xor", "=>" };
			String op = ops[rnd.nextInt(ops.length)];
			return "(" + expr(Ty.BOOL, d) + " " + op + " " + expr(Ty.BOOL, d) + ")";
		}
		case "compare": {
			String[] ops = { "<", "<=", ">", ">=" };
			String op = ops[rnd.nextInt(ops.length)];
			Ty operand = numericOperandType();
			return "(" + expr(operand, d) + " " + op + " " + expr(operand, d) + ")";
		}
		case "equality": {
			String op = rnd.nextBoolean() ? "=" : "<>";
			Ty operand = comparableOperandType();
			return "(" + expr(operand, d) + " " + op + " " + expr(operand, d) + ")";
		}
		case "intArith":
			return intArith(t, d);
		case "boundedIte":
			return "(if " + expr(Ty.BOOL, d) + " then " + expr(t, d) + " else " + expr(t, d) + ")";
		case "realArith":
			return realArith(d);
		case "floor":
			return "floor(" + expr(Ty.REAL, d) + ")";
		case "realCast":
			return "real(" + expr(Ty.INT, d) + ")";
		case "arrayLit": {
			StringBuilder sb = new StringBuilder("[");
			for (int i = 0; i < t.size; i++) {
				if (i > 0) {
					sb.append(", ");
				}
				sb.append(expr(t.elem, d));
			}
			return sb.append("]").toString();
		}
		case "arrayUpdate": {
			int idx = t.size == 0 ? 0 : rnd.nextInt(t.size);
			return "(" + expr(t, d) + "[" + idx + " := " + expr(t.elem, d) + "])";
		}
		case "recordLit": {
			StringBuilder sb = new StringBuilder(t.syn).append(" { ");
			boolean first = true;
			for (java.util.Map.Entry<String, Ty> e : t.fieldMap().entrySet()) {
				if (!first) {
					sb.append("; ");
				}
				first = false;
				sb.append(e.getKey()).append(" = ").append(expr(e.getValue(), d));
			}
			return sb.append(" }").toString();
		}
		case "recordUpdate": {
			List<String> names = new ArrayList<>(t.fieldMap().keySet());
			String f = names.get(rnd.nextInt(names.size()));
			return "(" + expr(t, d) + "{" + f + " := " + expr(t.fieldMap().get(f), d) + "})";
		}
		case "call":
			return call(t, d);
		default:
			return leaf(t);
		}
	}

	private String intArith(Ty t, int d) {
		// Subrange-typed results must stay provably inside their bounds, so we
		// only combine bounded pieces with if-then-else there.
		if (t.kind == Ty.K.SUBRANGE) {
			return "(if " + expr(Ty.BOOL, d) + " then " + literal(t) + " else " + expr(t, d) + ")";
		}
		switch (rnd.nextInt(5)) {
		case 0:
			return "(" + expr(Ty.INT, d) + " + " + expr(Ty.INT, d) + ")";
		case 1:
			return "(" + expr(Ty.INT, d) + " - " + expr(Ty.INT, d) + ")";
		case 2:
			if (cfg.linearOnly) {
				return "(" + (rnd.nextInt(9) - 4) + " * " + expr(Ty.INT, d) + ")";
			}
			return "(" + expr(Ty.INT, d) + " * " + expr(Ty.INT, d) + ")";
		case 3:
			return "(" + expr(Ty.INT, d) + " div " + nonZeroInt() + ")";
		default:
			return "(" + expr(Ty.INT, d) + " mod " + nonZeroInt() + ")";
		}
	}

	private String realArith(int d) {
		switch (rnd.nextInt(4)) {
		case 0:
			return "(" + expr(Ty.REAL, d) + " + " + expr(Ty.REAL, d) + ")";
		case 1:
			return "(" + expr(Ty.REAL, d) + " - " + expr(Ty.REAL, d) + ")";
		case 2:
			if (cfg.linearOnly) {
				return "(" + nonZeroReal() + " * " + expr(Ty.REAL, d) + ")";
			}
			return "(" + expr(Ty.REAL, d) + " * " + expr(Ty.REAL, d) + ")";
		default:
			return "(" + expr(Ty.REAL, d) + " / " + nonZeroReal() + ")";
		}
	}

	private String nonZeroInt() {
		return Integer.toString(1 + rnd.nextInt(9));
	}

	private String nonZeroReal() {
		return (1 + rnd.nextInt(9)) + "." + rnd.nextInt(10);
	}

	private Ty numericOperandType() {
		if (cfg.reals && rnd.nextInt(3) == 0) {
			return Ty.REAL;
		}
		return Ty.INT;
	}

	private Ty comparableOperandType() {
		if (rnd.nextInt(3) == 0) {
			return Ty.BOOL;
		}
		return numericOperandType();
	}

	private boolean hasCallReturning(Ty t) {
		for (Sig s : nodes) {
			if (s.outputs.size() == 1 && s.outputs.get(0).fitsIn(t)) {
				return true;
			}
		}
		for (Sig s : functions) {
			if (s.outputs.get(0).fitsIn(t)) {
				return true;
			}
		}
		return false;
	}

	private String call(Ty t, int d) {
		List<Sig> candidates = new ArrayList<>();
		for (Sig s : nodes) {
			if (s.outputs.size() == 1 && s.outputs.get(0).fitsIn(t)) {
				candidates.add(s);
			}
		}
		for (Sig s : functions) {
			if (s.outputs.get(0).fitsIn(t)) {
				candidates.add(s);
			}
		}
		if (candidates.isEmpty()) {
			return leaf(t);
		}
		Sig s = candidates.get(rnd.nextInt(candidates.size()));
		StringBuilder args = new StringBuilder();
		for (int i = 0; i < s.inputs.size(); i++) {
			if (i > 0) {
				args.append(", ");
			}
			args.append(expr(s.inputs.get(i), d));
		}
		String callExpr = s.id + "(" + args + ")";
		if (!s.isFunction && cfg.nodeCalls && rnd.nextInt(8) == 0) {
			// condact(clock, call, defaults) requires one default per output.
			return "condact(" + expr(Ty.BOOL, 0) + ", " + callExpr + ", " + literal(s.outputs.get(0)) + ")";
		}
		return callExpr;
	}

	private String leaf(Ty t) {
		List<String> options = new ArrayList<>();
		for (Var v : scope) {
			if (v.ty.fitsIn(t) && referable(v)) {
				options.add(v.id);
			}
		}
		for (Var c : constants) {
			if (c.ty.fitsIn(t)) {
				options.add(c.id);
			}
		}
		// Record and array fields of in-scope variables are also usable.
		for (Var v : scope) {
			if (!referable(v)) {
				continue;
			}
			if (v.ty.kind == Ty.K.RECORD) {
				for (java.util.Map.Entry<String, Ty> e : v.ty.fieldMap().entrySet()) {
					if (e.getValue().fitsIn(t)) {
						options.add(v.id + "." + e.getKey());
					}
				}
			} else if (v.ty.kind == Ty.K.ARRAY && v.ty.size > 0 && v.ty.elem.fitsIn(t)) {
				options.add(v.id + "[" + rnd.nextInt(v.ty.size) + "]");
			}
		}
		if (!options.isEmpty() && rnd.nextInt(4) != 0) {
			return options.get(rnd.nextInt(options.size()));
		}
		return literal(t);
	}

	/**
	 * Whether referring to this variable here keeps the program acyclic. Inside
	 * a pre the reference is to the previous step, so anything is safe.
	 */
	private boolean referable(Var v) {
		return !cfg.acyclic || preDepth > 0 || defined.contains(v.id);
	}

	private String literal(Ty t) {
		switch (t.kind) {
		case BOOL:
			return rnd.nextBoolean() ? "true" : "false";
		case INT:
			if (cfg.hugeLiterals && rnd.nextInt(50) == 0) {
				// Occasionally produce a literal far outside 64-bit range.
				return new BigInteger(160, rnd).toString();
			}
			return Integer.toString(rnd.nextInt(2 * cfg.intLiteralBound + 1) - cfg.intLiteralBound);
		case REAL:
			return (rnd.nextInt(21) - 10) + "." + rnd.nextInt(100);
		case SUBRANGE: {
			if (t.hi < t.lo) {
				return Long.toString(t.lo);
			}
			long span = t.hi - t.lo + 1;
			return Long.toString(t.lo + (long) (rnd.nextDouble() * span));
		}
		case ENUM:
			return t.values.get(rnd.nextInt(t.values.size()));
		case ARRAY: {
			StringBuilder sb = new StringBuilder("[");
			for (int i = 0; i < Math.max(1, t.size); i++) {
				if (i > 0) {
					sb.append(", ");
				}
				sb.append(literal(t.elem));
			}
			return sb.append("]").toString();
		}
		case RECORD: {
			StringBuilder sb = new StringBuilder(t.syn).append(" { ");
			boolean first = true;
			for (java.util.Map.Entry<String, Ty> e : t.fieldMap().entrySet()) {
				if (!first) {
					sb.append("; ");
				}
				first = false;
				sb.append(e.getKey()).append(" = ").append(literal(e.getValue()));
			}
			return sb.append(" }").toString();
		}
		default:
			return "0";
		}
	}

	// ------------------------------------------------------------------
	// Deliberate faults
	// ------------------------------------------------------------------

	/**
	 * Emits an expression containing a semantic fault. Each of these must be
	 * reported by JKind as a clean error rather than an uncaught exception.
	 */
	private String chaosExpr(Ty t, int depth) {
		switch (rnd.nextInt(10)) {
		case 0:
			return "undefined_id_" + (idCounter++);
		case 1:
			// Type mismatch: a bool where a number is expected and vice versa.
			return t.kind == Ty.K.BOOL ? "17" : "true";
		case 2:
			return "(" + expr(Ty.INT, 0) + " div 0)";
		case 3:
			return "(" + expr(Ty.INT, 0) + " mod 0)";
		case 4:
			return "(" + expr(Ty.REAL, 0) + " / 0.0)";
		case 5: {
			// Constant array access out of bounds.
			for (Var v : scope) {
				if (v.ty.kind == Ty.K.ARRAY) {
					return v.id + "[" + (v.ty.size + 5) + "]";
				}
			}
			return "[1, 2, 3][99]";
		}
		case 6:
			// Unknown record field.
			for (Var v : scope) {
				if (v.ty.kind == Ty.K.RECORD) {
					return v.id + ".no_such_field";
				}
			}
			return leaf(t);
		case 7:
			// Call with the wrong number of arguments.
			if (!nodes.isEmpty()) {
				Sig s = nodes.get(rnd.nextInt(nodes.size()));
				return s.id + "(" + expr(Ty.INT, 0) + ", " + expr(Ty.INT, 0) + ", " + expr(Ty.BOOL, 0) + ")";
			}
			return leaf(t);
		case 8:
			// Deeply nested expression: probes for stack overflow.
			return deepNest(200 + rnd.nextInt(2000));
		default:
			// Unguarded pre chain.
			return "pre pre pre " + leaf(t);
		}
	}

	private String deepNest(int n) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < n; i++) {
			sb.append("(1 + ");
		}
		sb.append("1");
		for (int i = 0; i < n; i++) {
			sb.append(")");
		}
		return sb.toString();
	}
}
