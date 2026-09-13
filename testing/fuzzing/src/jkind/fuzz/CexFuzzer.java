package jkind.fuzz;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Permission;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import org.antlr.v4.runtime.ANTLRInputStream;

import jkind.Main;
import jkind.StdErr;
import jkind.lustre.NamedType;
import jkind.lustre.Program;
import jkind.lustre.SubrangeIntType;
import jkind.lustre.VarDecl;

/**
 * End-to-end fuzzer that checks JKind's answers rather than just its stability.
 *
 * For each generated model it runs a complete, tightly bounded JKind
 * invocation and then checks the result independently with {@link Interp}:
 *
 *  - a counterexample must be a real behaviour of the model: every value JKind
 *    reports must match what the equations compute from the same inputs, every
 *    assertion must hold, and the property must actually be false at the last
 *    step;
 *  - a property reported valid must survive random concrete traces. A trace
 *    that satisfies the assertions and falsifies the property is a proof that
 *    the "valid" answer is unsound.
 *
 * Models are deliberately small and linear so that each invocation finishes in
 * well under a second; every run is also hard-killed at a deadline.
 */
public final class CexFuzzer {

	static final class ExitTrap extends SecurityException {
		private static final long serialVersionUID = 1L;
		final int code;

		ExitTrap(int code) {
			super("exit " + code);
			this.code = code;
		}
	}

	private static long iterations = 2000;
	private static int threads = Math.max(1, Runtime.getRuntime().availableProcessors() / 2);
	private static long baseSeed = 1;
	private static Path outDir = Paths.get("cex-out");
	private static String jarPath = "build/jkind.jar";
	private static String javaBin = System.getProperty("java.home") + "/bin/java";
	private static int maxK = 8;
	private static int solverTimeout = 2;
	private static long killMillis = 10_000;
	private static int randomTraces = 40;
	private static int traceSteps = 12;
	private static String solver = null;
	/**
	 * When set, every model is also checked with this second solver and the two
	 * answers are compared. A property that one solver proves valid and the
	 * other falsifies is a defect in one of them, independent of any
	 * interpreter.
	 */
	private static String compareSolver = null;
	/** Run the full mode and solver matrix rather than a single configuration. */
	private static boolean matrix = false;

	/**
	 * One way of invoking JKind: a solver, and the options that select a mode.
	 *
	 * None of these options may change an answer. Disabling an engine can leave
	 * a property undecided, and IVC or smoothing options add information to a
	 * result, but no option turns a valid property into a falsifiable one. That
	 * invariant is the oracle for the matrix.
	 */
	static final class RunConfig {
		final String label;
		final String solver;
		final List<String> args;

		RunConfig(String label, String solver, String... args) {
			this.label = label;
			this.solver = solver;
			this.args = java.util.Arrays.asList(args);
		}
	}

	private static List<RunConfig> configurations() {
		List<RunConfig> configs = new ArrayList<>();
		configs.add(new RunConfig("default", solver));
		if (!matrix) {
			if (compareSolver != null) {
				configs.add(new RunConfig("solver:" + compareSolver, compareSolver));
			}
			return configs;
		}

		// Modes, all on the default solver.
		configs.add(new RunConfig("no-slicing", solver, "-no_slicing"));
		configs.add(new RunConfig("ivc", "z3", "-ivc"));
		configs.add(new RunConfig("all-ivcs", "z3", "-all_ivcs"));
		configs.add(new RunConfig("induct-cex", solver, "-induct_cex"));
		configs.add(new RunConfig("smooth", "z3", "-smooth"));
		configs.add(new RunConfig("bmc-only", solver, "-no_k_induction", "-no_inv_gen"));
		configs.add(new RunConfig("kind-only", solver, "-no_bmc", "-no_inv_gen"));

		// The updated solvers, on the default mode.
        for (String s : new String[] { "z3", "cvc5", "yices2", "smtinterpol" }) {
			configs.add(new RunConfig("solver:" + s, s));
		}
		return configs;
	}

	private static final Map<String, AtomicLong> outcomes = new ConcurrentHashMap<>();
	private static final List<String> findings = Collections.synchronizedList(new ArrayList<>());
	private static final AtomicLong done = new AtomicLong();
	private static PrintStream console;

	private static void bump(String key) {
		outcomes.computeIfAbsent(key, k -> new AtomicLong()).incrementAndGet();
	}

	public static void main(String[] args) throws Exception {
		for (int i = 0; i < args.length; i++) {
			switch (args[i]) {
			case "--iterations":
				iterations = Long.parseLong(args[++i]);
				break;
			case "--threads":
				threads = Integer.parseInt(args[++i]);
				break;
			case "--seed":
				baseSeed = Long.parseLong(args[++i]);
				break;
			case "--out":
				outDir = Paths.get(args[++i]);
				break;
			case "--jar":
				jarPath = args[++i];
				break;
			case "--java":
				javaBin = args[++i];
				break;
			case "--max-k":
				maxK = Integer.parseInt(args[++i]);
				break;
			case "--solver-timeout":
				solverTimeout = Integer.parseInt(args[++i]);
				break;
			case "--kill-millis":
				killMillis = Long.parseLong(args[++i]);
				break;
			case "--random-traces":
				randomTraces = Integer.parseInt(args[++i]);
				break;
			case "--solver":
				solver = args[++i];
				break;
			case "--compare-solver":
				compareSolver = args[++i];
				break;
			case "--matrix":
				matrix = true;
				break;
			default:
				throw new IllegalArgumentException("unknown option: " + args[i]);
			}
		}

		console = System.out;
		Files.createDirectories(outDir);
		Files.createDirectories(outDir.resolve("models"));
		StdErr.setLocationReference(Collections.emptyList());
		System.setSecurityManager(new SecurityManager() {
			@Override
			public void checkPermission(Permission perm) {
			}

			@Override
			public void checkExit(int status) {
				throw new ExitTrap(status);
			}
		});

		console.printf("JKind end-to-end counterexample fuzzer%n");
		console.printf("  iterations : %d (%d threads)%n", iterations, threads);
		console.printf("  bounds     : -n %d -timeout %ds, killed at %dms%n", maxK, solverTimeout, killMillis);
		console.printf("  validation : %d random traces of %d steps per valid property%n%n", randomTraces,
				traceSteps);

		long start = System.currentTimeMillis();
		AtomicLong cursor = new AtomicLong();
		List<Thread> workers = new ArrayList<>();
		for (int i = 0; i < threads; i++) {
			Thread t = new Thread(() -> {
				while (true) {
					long index = cursor.getAndIncrement();
					if (index >= iterations) {
						return;
					}
					try {
						runOne(baseSeed * 1_000_003L + index);
					} catch (Throwable e) {
						bump("harness-error/" + e.getClass().getSimpleName());
					}
					long n = done.incrementAndGet();
					if (n % 25 == 0) {
						long elapsed = Math.max(1, System.currentTimeMillis() - start);
						console.printf("\r  %,d/%,d  %.1f/s  findings=%d      ", n, iterations,
								n * 1000.0 / elapsed, findings.size());
					}
				}
			});
			t.setDaemon(true);
			t.setName("cex-" + i);
			t.start();
			workers.add(t);
		}
		for (Thread t : workers) {
			t.join();
		}

		System.setSecurityManager(null);
		report(System.currentTimeMillis() - start);
	}

	private static void runOne(long seed) throws Exception {
		String source = LustreGen.generate(seed, LustreGen.Config.interpretable());

		Program program;
		try {
			program = Main.parseLustre(new ANTLRInputStream(source));
		} catch (ExitTrap e) {
			bump("skipped/unparsable");
			return;
		}

		Interp interp;
		try {
			interp = new Interp(program);
		} catch (Interp.Unsupported e) {
			bump("skipped/outside-fragment");
			return;
		}
		if (interp.propertyNames().isEmpty()) {
			bump("skipped/no-properties");
			return;
		}

		Path model = outDir.resolve("models").resolve("m" + seed + ".lus");
		Files.write(model, source.getBytes(StandardCharsets.UTF_8));
		try {
			Map<String, Map<String, String>> answersByConfig = new LinkedHashMap<>();

			for (RunConfig config : configurations()) {
				RunResult run = runJKind(model, config);

				if (run.killed) {
					// A mode that cannot finish a model this small inside the
					// deadline is worth knowing about, but the solver timeout
					// makes it expected often enough not to be a finding.
					bump("run/killed-at-deadline/" + config.label);
					continue;
				}
				if (run.exitCode == EXIT_INVALID_OPTIONS) {
					// The mode does not apply to this solver, for example
					// smoothing on a backend that does not support it.
					bump("run/not-applicable/" + config.label);
					continue;
				}
				if (run.crashed()) {
					report(seed, source, "JKind crashed in mode '" + config.label + "' (exit " + run.exitCode
							+ ")\n" + run.firstStackTrace());
					continue;
				}

				List<JKindXml.Property> results = JKindXml.parse(run.stdout);
				if (results.isEmpty()) {
					bump("run/no-results/" + config.label);
					continue;
				}

				Map<String, String> answers = new LinkedHashMap<>();
				for (JKindXml.Property property : results) {
					answers.put(property.name, property.answer);
					check(seed, source, program, interp, property, config.label);
					checkIvc(seed, source, interp, property, config.label);
				}
				answersByConfig.put(config.label, answers);
			}

			crossCheck(seed, source, answersByConfig);
		} finally {
			Files.deleteIfExists(model);
		}
	}

	private static final int EXIT_INVALID_OPTIONS = -2;
	private static final int EXIT_UNCAUGHT_EXCEPTION = -8;

	/**
	 * No option may change a decided answer. Two configurations that decide the
	 * same property differently mean one of them is wrong, whichever it is.
	 */
	private static void crossCheck(long seed, String source, Map<String, Map<String, String>> answersByConfig) {
		List<String> labels = new ArrayList<>(answersByConfig.keySet());
		for (int i = 0; i < labels.size(); i++) {
			for (int j = i + 1; j < labels.size(); j++) {
				Map<String, String> a = answersByConfig.get(labels.get(i));
				Map<String, String> b = answersByConfig.get(labels.get(j));
				for (Map.Entry<String, String> entry : a.entrySet()) {
					String other = b.get(entry.getKey());
					if (other == null) {
						continue;
					}
					boolean contradiction = ("valid".equals(entry.getValue()) && "falsifiable".equals(other))
							|| ("falsifiable".equals(entry.getValue()) && "valid".equals(other));
					if (contradiction) {
						report(seed, source, String.format("modes disagree on property %s: %s says %s, %s says %s",
								entry.getKey(), labels.get(i), entry.getValue(), labels.get(j), other));
						return;
					}
					bump("cross-check/agree");
				}
			}
		}
	}

	/**
	 * Every element of a reported inductive validity core must name a real
	 * model element. A core naming something that is not in the model is
	 * meaningless to the user reading it.
	 */
	private static void checkIvc(long seed, String source, Interp interp, JKindXml.Property property, String label) {
		if (property.ivc.isEmpty()) {
			return;
		}
		bump("ivc/reported");
		for (String element : property.ivc) {
			if (!interp.computedNames().contains(element) && !interp.inputNames().contains(element)) {
				report(seed, source, String.format(
						"mode '%s' reported IVC element '%s' for property %s, which is not a variable of the model",
						label, element, property.name));
				return;
			}
		}
		bump("ivc/validated");
	}

	/** Everything one JKind invocation produced. */
	static final class RunResult {
		final String stdout;
		final String stderr;
		final int exitCode;
		final boolean killed;

		RunResult(String stdout, String stderr, int exitCode, boolean killed) {
			this.stdout = stdout;
			this.stderr = stderr;
			this.exitCode = exitCode;
			this.killed = killed;
		}

		/**
		 * JKind reports bad input with a diagnostic and a documented exit code.
		 * A Java stack trace on stderr, or the uncaught-exception exit code,
		 * means something escaped instead.
		 */
		boolean crashed() {
			return exitCode == EXIT_UNCAUGHT_EXCEPTION || stderr.contains("\tat jkind.")
					|| stderr.contains("\tat java.");
		}

		String firstStackTrace() {
			int at = stderr.indexOf("\tat ");
			if (at < 0) {
				return stderr.length() > 600 ? stderr.substring(0, 600) : stderr;
			}
			int from = Math.max(0, stderr.lastIndexOf('\n', stderr.lastIndexOf('\n', at) - 1));
			int to = Math.min(stderr.length(), at + 600);
			return stderr.substring(from, to);
		}
	}

	/** Runs JKind on one model under one configuration. */
	private static RunResult runJKind(Path model, RunConfig config) throws Exception {
		List<String> command = new ArrayList<>();
		command.add(javaBin);
		command.add("-jar");
		command.add(jarPath);
		command.add("-jkind");
		command.add("-xml_to_stdout");
		command.add("-n");
		command.add(Integer.toString(maxK));
		command.add("-timeout");
		command.add(Integer.toString(solverTimeout));
		if (!config.args.contains("-no_bmc") && !config.args.contains("-no_k_induction")) {
			command.add("-pdr_max");
			command.add("0");
		}
		if (config.solver != null) {
			command.add("-solver");
			command.add(config.solver);
		}
		command.addAll(config.args);
		command.add(model.toAbsolutePath().toString());

		ProcessBuilder builder = new ProcessBuilder(command);
		builder.redirectErrorStream(false);
		Process process = builder.start();
		process.getOutputStream().close();

		ByteArrayOutputStream stdout = new ByteArrayOutputStream();
		ByteArrayOutputStream stderr = new ByteArrayOutputStream();
		Thread outPump = new Thread(() -> copy(process.getInputStream(), stdout));
		Thread errPump = new Thread(() -> copy(process.getErrorStream(), stderr));
		outPump.setDaemon(true);
		errPump.setDaemon(true);
		outPump.start();
		errPump.start();

		if (!process.waitFor(killMillis, TimeUnit.MILLISECONDS)) {
			process.destroyForcibly();
			process.waitFor(2, TimeUnit.SECONDS);
			return new RunResult("", "", 0, true);
		}
		outPump.join(2000);
		errPump.join(2000);

		// The JVM reports System.exit(-8) as 248; normalize back.
		int exitCode = process.exitValue();
		if (exitCode > 128) {
			exitCode -= 256;
		}
		return new RunResult(stdout.toString(StandardCharsets.UTF_8.name()),
				stderr.toString(StandardCharsets.UTF_8.name()), exitCode, false);
	}

	private static void copy(InputStream in, OutputStream out) {
		byte[] buffer = new byte[8192];
		try {
			int n;
			while ((n = in.read(buffer)) > 0) {
				out.write(buffer, 0, n);
			}
		} catch (Exception e) {
			// The process was killed; whatever arrived is enough.
		}
	}

	// ------------------------------------------------------------------
	// Oracles
	// ------------------------------------------------------------------

	private static void check(long seed, String source, Program program, Interp interp,
			JKindXml.Property property, String label) {
		switch (property.answer) {
		case "falsifiable":
			checkCounterexample(seed, source, program, interp, property, label);
			break;
		case "valid":
			checkValid(seed, source, program, interp, property, label);
			break;
		default:
			bump("result/" + property.answer);
			break;
		}
	}

	/**
	 * A counterexample must be a real behaviour: replaying its inputs through
	 * the model must reproduce every value JKind reported, satisfy every
	 * assertion, and leave the property false at the final step.
	 */
	private static void checkCounterexample(long seed, String source, Program program, Interp interp,
			JKindXml.Property property, String label) {
		bump("result/falsifiable");
		int steps = property.steps();
		if (steps == 0) {
			report(seed, source, "mode '" + label + "': falsifiable with an empty counterexample for "
					+ property.name);
			return;
		}

		List<Map<String, Val>> inputTrace = new ArrayList<>();
		boolean sliced = false;
		for (int k = 0; k < steps; k++) {
			Map<String, Val> step = new HashMap<>();
			for (VarDecl input : program.nodes.get(0).inputs) {
				Map<Integer, Val> signal = property.signals.get(input.id);
				if (signal == null || !signal.containsKey(k)) {
					// Slicing drops inputs the property cannot depend on.
					sliced = true;
					step.put(input.id, defaultValue(input));
				} else {
					step.put(input.id, signal.get(k));
				}
			}
			inputTrace.add(step);
		}

		Interp.Trace trace;
		try {
			trace = interp.run(inputTrace);
		} catch (Interp.Unsupported e) {
			bump("skipped/uninterpretable-replay");
			return;
		} catch (RuntimeException e) {
			report(seed, source, "mode '" + label + "': interpreter failed replaying " + property.name + ": " + e);
			return;
		}

		// Values JKind reported must match what the model computes.
		for (Map.Entry<String, Map<Integer, Val>> signal : property.signals.entrySet()) {
			String name = signal.getKey();
			if (!interp.computedNames().contains(name)) {
				continue;
			}
			for (Map.Entry<Integer, Val> entry : signal.getValue().entrySet()) {
				int k = entry.getKey();
				if (k >= steps) {
					continue;
				}
				Val expected = entry.getValue();
				Val actual = trace.values.get(k).get(name);
				if (actual != null && !actual.equals(expected)) {
					report(seed, source, String.format(
							"mode '%s': counterexample for %s disagrees with the model: %s at step %d is %s in "
									+ "JKind's counterexample but %s when the equations are evaluated on the "
									+ "same inputs",
							label, property.name, name, k, expected, actual));
					return;
				}
			}
		}

		if (!sliced && !trace.assertionsHoldThrough(steps - 1)) {
			report(seed, source, "mode '" + label + "': counterexample for " + property.name
					+ " violates the model's own assertions");
			return;
		}

		Val last = trace.values.get(steps - 1).get(property.name);
		if (last instanceof Val.Bool && ((Val.Bool) last).value) {
			report(seed, source, "mode '" + label + "': counterexample for " + property.name
					+ " does not falsify it: the property is true at every step of the reported trace");
			return;
		}
		bump("counterexample/validated");
	}

	/**
	 * A valid property must hold on every concrete behaviour. Random traces
	 * cannot prove that, but a single trace that satisfies the assertions and
	 * falsifies the property disproves the "valid" answer outright.
	 */
	private static void checkValid(long seed, String source, Program program, Interp interp,
			JKindXml.Property property, String label) {
		bump("result/valid");
		Random rnd = new Random(seed ^ 0x5DEECE66DL);
		for (int attempt = 0; attempt < randomTraces; attempt++) {
			List<Map<String, Val>> inputTrace = new ArrayList<>();
			for (int k = 0; k < traceSteps; k++) {
				Map<String, Val> step = new HashMap<>();
				for (VarDecl input : program.nodes.get(0).inputs) {
					step.put(input.id, randomValue(input, rnd));
				}
				inputTrace.add(step);
			}

			Interp.Trace trace;
			try {
				trace = interp.run(inputTrace);
			} catch (Interp.Unsupported e) {
				bump("skipped/uninterpretable-valid");
				return;
			} catch (RuntimeException e) {
				return;
			}

			for (int k = 0; k < traceSteps; k++) {
				if (!trace.assertionsHoldThrough(k)) {
					break; // Beyond here the trace is not a behaviour of the model.
				}
				Val value = trace.values.get(k).get(property.name);
				if (value instanceof Val.Bool && !((Val.Bool) value).value) {
					report(seed, source, String.format(
							"mode '%s': property %s was reported VALID but a concrete trace falsifies it at "
									+ "step %d while satisfying every assertion", label, property.name, k));
					return;
				}
			}
		}
		bump("valid/survived-random-traces");
	}

	private static Val defaultValue(VarDecl decl) {
		if (decl.type == NamedType.BOOL) {
			return new Val.Bool(false);
		}
		if (decl.type instanceof SubrangeIntType) {
			return new Val.Int(((SubrangeIntType) decl.type).low);
		}
		if (decl.type == NamedType.REAL) {
			return Val.Rat.of(0);
		}
		return new Val.Int(0);
	}

	private static Val randomValue(VarDecl decl, Random rnd) {
		if (decl.type == NamedType.BOOL) {
			return new Val.Bool(rnd.nextBoolean());
		}
		if (decl.type instanceof SubrangeIntType) {
			SubrangeIntType subrange = (SubrangeIntType) decl.type;
			BigInteger span = subrange.high.subtract(subrange.low).add(BigInteger.ONE);
			if (span.signum() <= 0) {
				return new Val.Int(subrange.low);
			}
			BigInteger offset = new BigInteger(span.bitLength() + 8, rnd).mod(span);
			return new Val.Int(subrange.low.add(offset));
		}
		if (decl.type == NamedType.REAL) {
			return new Val.Rat(BigInteger.valueOf(rnd.nextInt(41) - 20), BigInteger.valueOf(1 + rnd.nextInt(4)));
		}
		return new Val.Int(rnd.nextInt(17) - 8);
	}

	private static void report(long seed, String source, String message) {
		bump("FINDING");
		String text = "seed " + seed + ": " + message + "\n\n" + source + "\n";
		findings.add(text);
		try {
			Files.write(outDir.resolve("finding-" + seed + ".txt"), text.getBytes(StandardCharsets.UTF_8));
		} catch (Exception e) {
			// Reporting is best effort; the summary still carries the finding.
		}
	}

	private static void report(long elapsedMillis) throws Exception {
		StringBuilder sb = new StringBuilder();
		sb.append("# JKind end-to-end counterexample fuzzing\n\n");
		sb.append(String.format("- models: %,d%n", done.get()));
		sb.append(String.format("- wall time: %.1fs%n", elapsedMillis / 1000.0));
		sb.append(String.format("- findings: %d%n%n", findings.size()));
		sb.append("## Outcomes\n\n");
		outcomes.entrySet().stream().sorted(Map.Entry.comparingByKey())
				.forEach(e -> sb.append(String.format("- %-36s %,d%n", e.getKey(), e.getValue().get())));
		if (!findings.isEmpty()) {
			sb.append("\n## Findings\n\n");
			for (String finding : findings) {
				sb.append("```\n").append(finding).append("```\n\n");
			}
		}
		Files.write(outDir.resolve("report.md"), sb.toString().getBytes(StandardCharsets.UTF_8));
		console.println();
		console.println();
		console.print(sb);
	}
}
