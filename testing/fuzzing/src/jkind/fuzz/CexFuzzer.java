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
			String xml = runJKind(model);
			if (xml == null) {
				bump("run/killed-at-deadline");
				return;
			}
			List<JKindXml.Property> results = JKindXml.parse(xml);
			if (results.isEmpty()) {
				bump("run/no-results");
				return;
			}
			for (JKindXml.Property property : results) {
				check(seed, source, program, interp, property);
			}
			if (compareSolver != null) {
				compareSolvers(seed, source, model, results);
			}
		} finally {
			Files.deleteIfExists(model);
		}
	}

	/**
	 * Re-runs the model under a second solver and reports any property the two
	 * solvers answer differently. Only outright contradictions are reported;
	 * one solver returning "unknown" where the other decides the property is
	 * expected.
	 */
	private static void compareSolvers(long seed, String source, Path model, List<JKindXml.Property> first)
			throws Exception {
		String xml = runJKind(model, compareSolver);
		if (xml == null) {
			bump("compare/killed-at-deadline");
			return;
		}
		List<JKindXml.Property> second = JKindXml.parse(xml);
		Map<String, String> answers = new HashMap<>();
		for (JKindXml.Property property : second) {
			answers.put(property.name, property.answer);
		}
		for (JKindXml.Property property : first) {
			String other = answers.get(property.name);
			if (other == null) {
				continue;
			}
			boolean contradiction = ("valid".equals(property.answer) && "falsifiable".equals(other))
					|| ("falsifiable".equals(property.answer) && "valid".equals(other));
			if (contradiction) {
				report(seed, source, String.format(
						"solvers disagree on property %s: %s says %s, %s says %s", property.name,
						solver == null ? "the default solver" : solver, property.answer, compareSolver, other));
			} else {
				bump("compare/agree");
			}
		}
	}

	/** Runs JKind on one model, returning its XML output or null if killed. */
	private static String runJKind(Path model) throws Exception {
		return runJKind(model, solver);
	}

	private static String runJKind(Path model, String withSolver) throws Exception {
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
		command.add("-pdr_max");
		command.add("0");
		if (withSolver != null) {
			command.add("-solver");
			command.add(withSolver);
		}
		command.add(model.toAbsolutePath().toString());

		ProcessBuilder builder = new ProcessBuilder(command);
		builder.redirectErrorStream(false);
		Process process = builder.start();
		process.getOutputStream().close();

		ByteArrayOutputStream stdout = new ByteArrayOutputStream();
		Thread pump = new Thread(() -> copy(process.getInputStream(), stdout));
		pump.setDaemon(true);
		pump.start();
		Thread drainErr = new Thread(() -> copy(process.getErrorStream(), new ByteArrayOutputStream()));
		drainErr.setDaemon(true);
		drainErr.start();

		if (!process.waitFor(killMillis, TimeUnit.MILLISECONDS)) {
			process.destroyForcibly();
			process.waitFor(2, TimeUnit.SECONDS);
			return null;
		}
		pump.join(2000);
		return stdout.toString(StandardCharsets.UTF_8.name());
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
			JKindXml.Property property) {
		switch (property.answer) {
		case "falsifiable":
			checkCounterexample(seed, source, program, interp, property);
			break;
		case "valid":
			checkValid(seed, source, program, interp, property);
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
			JKindXml.Property property) {
		bump("result/falsifiable");
		int steps = property.steps();
		if (steps == 0) {
			report(seed, source, "falsifiable with an empty counterexample for " + property.name);
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
			report(seed, source, "interpreter failed replaying " + property.name + ": " + e);
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
							"counterexample for %s disagrees with the model: %s at step %d is %s in JKind's "
									+ "counterexample but %s when the equations are evaluated on the same inputs",
							property.name, name, k, expected, actual));
					return;
				}
			}
		}

		if (!sliced && !trace.assertionsHoldThrough(steps - 1)) {
			report(seed, source,
					"counterexample for " + property.name + " violates the model's own assertions");
			return;
		}

		Val last = trace.values.get(steps - 1).get(property.name);
		if (last instanceof Val.Bool && ((Val.Bool) last).value) {
			report(seed, source, "counterexample for " + property.name + " does not falsify it: the property is "
					+ "true at every step of the reported trace");
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
			JKindXml.Property property) {
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
							"property %s was reported VALID but a concrete trace falsifies it at step %d "
									+ "while satisfying every assertion", property.name, k));
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
