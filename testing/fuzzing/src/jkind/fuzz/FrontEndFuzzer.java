package jkind.fuzz;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Permission;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import org.antlr.v4.runtime.ANTLRInputStream;

import jkind.JKindSettings;
import jkind.Main;
import jkind.SolverOption;
import jkind.StdErr;
import jkind.analysis.LinearChecker;
import jkind.analysis.StaticAnalyzer;
import jkind.lustre.Program;
import jkind.translation.InlineSimpleEquations;
import jkind.translation.Specification;
import jkind.translation.Translate;

/**
 * High-volume fuzzer for the JKind front end.
 *
 * Every input is pushed through the same sequence of passes that
 * {@code jkind.JKind} runs before the solving engines start: parsing, static
 * analysis, linearity checking, translation, specification construction and
 * equation inlining. None of these passes need an SMT solver, so inputs run in
 * process at thousands per second.
 *
 * JKind's contract for bad input is a diagnostic followed by
 * {@code System.exit} with a specific code. Anything that instead throws out of
 * a pass reaches the {@code catch (Throwable)} in {@code JKind.main}, which
 * prints a stack trace and exits UNCAUGHT_EXCEPTION. Those escaping throwables
 * are what this fuzzer reports.
 */
public final class FrontEndFuzzer {

	// ------------------------------------------------------------------
	// Exit trapping
	// ------------------------------------------------------------------

	/** Thrown in place of a {@code System.exit} call made by a pass. */
	static final class ExitTrap extends SecurityException {
		private static final long serialVersionUID = 1L;
		final int code;

		ExitTrap(int code) {
			super("exit " + code);
			this.code = code;
		}

		@Override
		public synchronized Throwable fillInStackTrace() {
			return this; // Exits are common; skip the cost of a stack trace.
		}
	}

	private static volatile boolean trapExits = true;

	private static void installExitTrap() {
		System.setSecurityManager(new SecurityManager() {
			@Override
			public void checkPermission(Permission perm) {
			}

			@Override
			public void checkPermission(Permission perm, Object context) {
			}

			@Override
			public void checkExit(int status) {
				if (trapExits) {
					throw new ExitTrap(status);
				}
			}
		});
	}

	// ------------------------------------------------------------------
	// Outcomes
	// ------------------------------------------------------------------

	enum Stage {
		GENERATE, PARSE, STATIC_ANALYSIS, LINEARITY, TRANSLATE, SPECIFICATION, INLINE, ROUND_TRIP, COMPLETED
	}

	/** One distinct crash signature, with a retained reproducer. */
	static final class Bucket {
		final String signature;
		final AtomicLong count = new AtomicLong();
		volatile String sampleSource;
		volatile String sampleStack;
		volatile String sampleOrigin;
		volatile Stage stage;

		Bucket(String signature) {
			this.signature = signature;
		}
	}

	private static final Map<String, Bucket> buckets = new ConcurrentHashMap<>();
	private static final Map<String, AtomicLong> stageReach = new ConcurrentHashMap<>();
	private static final Map<String, AtomicLong> exitCodes = new ConcurrentHashMap<>();
	private static final Map<String, AtomicLong> generatorUse = new ConcurrentHashMap<>();
	private static final AtomicLong completed = new AtomicLong();
	private static final AtomicLong crashes = new AtomicLong();
	private static final AtomicLong hangs = new AtomicLong();
	private static final AtomicLong done = new AtomicLong();

	private static void bump(Map<String, AtomicLong> map, String key) {
		map.computeIfAbsent(key, k -> new AtomicLong()).incrementAndGet();
	}

	// ------------------------------------------------------------------
	// Configuration
	// ------------------------------------------------------------------

	private static long iterations = 100_000;
	private static int threads = Runtime.getRuntime().availableProcessors();
	private static long baseSeed = 1;
	private static Path outDir = Paths.get("fuzz-out");
	private static Path corpusDir = null;
	private static long hangMillis = 20_000;
	private static boolean roundTrip = true;
	private static PrintStream console;

	private static List<String> corpus = new ArrayList<>();

	public static void main(String[] args) throws Exception {
		parseArgs(args);
		console = System.out;
		loadCorpus();

		Files.createDirectories(outDir);
		console.printf("JKind front-end fuzzer%n");
		console.printf("  iterations : %d%n", iterations);
		console.printf("  threads    : %d%n", threads);
		console.printf("  base seed  : %d%n", baseSeed);
		console.printf("  corpus     : %d seed models%n", corpus.size());
		console.printf("  output     : %s%n%n", outDir.toAbsolutePath());

		// JKind's error reporting consults a static line table for context. An
		// empty table keeps reporting side-effect free and thread safe.
		StdErr.setLocationReference(Collections.emptyList());

		PrintStream devNull = new PrintStream(new OutputStream() {
			@Override
			public void write(int b) {
			}

			@Override
			public void write(byte[] b, int off, int len) {
			}
		});
		System.setOut(devNull);
		System.setErr(devNull);
		installExitTrap();

		long start = System.currentTimeMillis();
		AtomicLong cursor = new AtomicLong();
		Worker[] workers = new Worker[threads];
		for (int i = 0; i < threads; i++) {
			workers[i] = new Worker(cursor);
			workers[i].setDaemon(true);
			workers[i].setName("fuzz-" + i);
			workers[i].start();
		}

		Thread watchdog = new Thread(() -> watch(workers, start));
		watchdog.setDaemon(true);
		watchdog.start();

		for (Worker w : workers) {
			w.join();
		}

		long elapsed = System.currentTimeMillis() - start;
		trapExits = false;
		System.setOut(console);
		System.setErr(console);
		System.setSecurityManager(null);
		report(elapsed);
	}

	private static void parseArgs(String[] args) {
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
			case "--corpus":
				corpusDir = Paths.get(args[++i]);
				break;
			case "--hang-millis":
				hangMillis = Long.parseLong(args[++i]);
				break;
			case "--no-round-trip":
				roundTrip = false;
				break;
			default:
				throw new IllegalArgumentException("unknown option: " + args[i]);
			}
		}
	}

	private static void loadCorpus() throws IOException {
		if (corpusDir == null || !Files.isDirectory(corpusDir)) {
			return;
		}
		try (java.util.stream.Stream<Path> paths = Files.walk(corpusDir)) {
			corpus = paths.filter(p -> p.toString().endsWith(".lus")).filter(Files::isRegularFile).map(p -> {
				try {
					return new String(Files.readAllBytes(p), StandardCharsets.UTF_8);
				} catch (IOException e) {
					return null;
				}
			}).filter(s -> s != null && s.length() < 200_000).collect(Collectors.toList());
		}
	}

	// ------------------------------------------------------------------
	// Workers
	// ------------------------------------------------------------------

	/** What a worker is doing right now, for hang detection. */
	static final class Progress {
		volatile long index = -1;
		volatile long startedAt = 0;
		volatile String origin = "";
		volatile String source = "";
		volatile boolean reportedHang = false;
	}

	static final class Worker extends Thread {
		final AtomicLong cursor;
		final Progress progress = new Progress();

		Worker(AtomicLong cursor) {
			this.cursor = cursor;
		}

		@Override
		public void run() {
			while (true) {
				long index = cursor.getAndIncrement();
				if (index >= iterations) {
					return;
				}
				long seed = baseSeed * 1_000_003L + index;
				String origin;
				String source;
				SolverOption solver;
				try {
					Input input = generate(seed);
					origin = input.origin;
					source = input.source;
					solver = input.solver;
				} catch (Throwable t) {
					record(Stage.GENERATE, t, "generator:" + seed, "");
					done.incrementAndGet();
					continue;
				}

				progress.index = index;
				progress.origin = origin;
				progress.source = source;
				progress.reportedHang = false;
				progress.startedAt = System.currentTimeMillis();

				bump(generatorUse, origin.substring(0, origin.indexOf(':')));
				runPipeline(source, origin, solver);

				progress.startedAt = 0;
				done.incrementAndGet();
			}
		}
	}

	static final class Input {
		final String source;
		final String origin;
		/**
		 * Static analysis rejects non-linear arithmetic for some solvers, so the
		 * solver is part of the input: it selects which checks apply.
		 */
		final SolverOption solver;

		Input(String source, String origin, SolverOption solver) {
			this.source = source;
			this.origin = origin;
			this.solver = solver;
		}
	}

	/**
	 * Builds the input for one iteration. Everything is derived from the seed,
	 * so any reported finding replays exactly.
	 */
	static Input generate(long seed) {
		Random picker = new Random(seed);
		int roll = picker.nextInt(100);
		// Z3 accepts non-linear arithmetic; SMTInterpol does not. Alternating
		// exercises both static-analysis paths.
		SolverOption solver = picker.nextInt(100) < 40 ? SolverOption.Z3 : SolverOption.SMTINTERPOL;
		if (roll < 32) {
			return new Input(LustreGen.generate(seed, LustreGen.Config.wellTyped()), "well-typed:" + seed, solver);
		} else if (roll < 40) {
			return new Input(LustreGen.generate(seed, LustreGen.Config.nonlinear()), "nonlinear:" + seed,
					SolverOption.Z3);
		} else if (roll < 65) {
			double p = new double[] { 0.05, 0.15, 0.30 }[picker.nextInt(3)];
			return new Input(LustreGen.generate(seed, LustreGen.Config.chaos(p)), "chaos" + p + ":" + seed, solver);
		} else if (roll < 90 && !corpus.isEmpty()) {
			Mutator mutator = new Mutator(seed);
			String base = corpus.get(picker.nextInt(corpus.size()));
			return new Input(mutator.mutate(base), "mutation:" + seed, solver);
		} else if (roll < 97) {
			return new Input(new Mutator(seed).tokenSoup(), "token-soup:" + seed, solver);
		} else {
			return new Input(new Mutator(seed).randomText(), "random-text:" + seed, solver);
		}
	}

	/**
	 * Runs the passes JKind runs before its engines start, recording how far the
	 * input got and any throwable that escaped.
	 */
	static void runPipeline(String source, String origin, SolverOption solver) {
		Stage stage = Stage.PARSE;
		try {
			Program program = Main.parseLustre(new ANTLRInputStream(source));
			stage = Stage.STATIC_ANALYSIS;
			bump(stageReach, Stage.PARSE.name());

			JKindSettings settings = new JKindSettings();
			StaticAnalyzer.check(program, solver, settings);
			bump(stageReach, Stage.STATIC_ANALYSIS.name());

			stage = Stage.LINEARITY;
			LinearChecker.isLinear(program);
			bump(stageReach, Stage.LINEARITY.name());

			stage = Stage.ROUND_TRIP;
			if (roundTrip) {
				checkRoundTrip(program, origin);
			}

			stage = Stage.TRANSLATE;
			Program translated = Translate.translate(program);
			bump(stageReach, Stage.TRANSLATE.name());

			stage = Stage.SPECIFICATION;
			Specification spec = new Specification(translated, settings.slicing);
			bump(stageReach, Stage.SPECIFICATION.name());

			stage = Stage.INLINE;
			InlineSimpleEquations.node(spec.node);
			bump(stageReach, Stage.INLINE.name());

			completed.incrementAndGet();
			bump(stageReach, Stage.COMPLETED.name());
		} catch (ExitTrap e) {
			bump(exitCodes, Integer.toString(e.code));
		} catch (Throwable t) {
			record(stage, t, origin, source);
		}
	}

	/**
	 * Pretty printing a parsed program and reparsing it must yield the same
	 * program. A mismatch means the printer and the parser disagree, which
	 * silently corrupts any tool that round-trips models through JKind.
	 */
	private static void checkRoundTrip(Program program, String origin) {
		String printed;
		try {
			printed = program.toString();
		} catch (ExitTrap e) {
			throw e;
		} catch (Throwable t) {
			record(Stage.ROUND_TRIP, t, origin + " [printing]", "");
			return;
		}

		Program reparsed;
		try {
			reparsed = Main.parseLustre(new ANTLRInputStream(printed));
		} catch (ExitTrap e) {
			// The printer produced something JKind itself cannot parse.
			recordSignature("round-trip/unparsable-output/exit" + e.code, Stage.ROUND_TRIP, origin, printed,
					"pretty printer emitted text that fails to reparse (exit " + e.code + ")");
			return;
		} catch (Throwable t) {
			record(Stage.ROUND_TRIP, t, origin + " [reparsing]", printed);
			return;
		}

		String reprinted;
		try {
			reprinted = reparsed.toString();
		} catch (Throwable t) {
			record(Stage.ROUND_TRIP, t, origin + " [reprinting]", printed);
			return;
		}

		if (!printed.equals(reprinted)) {
			recordSignature("round-trip/not-idempotent", Stage.ROUND_TRIP, origin, printed,
					"print -> parse -> print is not idempotent\n--- first ---\n" + truncate(printed)
							+ "\n--- second ---\n" + truncate(reprinted));
		}
	}

	private static String truncate(String s) {
		return s.length() > 4000 ? s.substring(0, 4000) + "\n... [truncated]" : s;
	}

	// ------------------------------------------------------------------
	// Recording
	// ------------------------------------------------------------------

	private static void record(Stage stage, Throwable t, String origin, String source) {
		StringBuilder stack = new StringBuilder();
		stack.append(t.getClass().getName());
		if (t.getMessage() != null) {
			stack.append(": ").append(t.getMessage());
		}
		stack.append('\n');
		StackTraceElement[] frames = t.getStackTrace();
		for (int i = 0; i < Math.min(frames.length, 25); i++) {
			stack.append("\tat ").append(frames[i]).append('\n');
		}
		recordSignature(signature(stage, t), stage, origin, source, stack.toString());
	}

	/** Buckets a finding by the pass and the first JKind frame involved. */
	private static String signature(Stage stage, Throwable t) {
		String where = "unknown";
		for (StackTraceElement frame : t.getStackTrace()) {
			if (frame.getClassName().startsWith("jkind.") && !frame.getClassName().startsWith("jkind.fuzz.")) {
				where = frame.getClassName() + "." + frame.getMethodName() + ":" + frame.getLineNumber();
				break;
			}
		}
		return stage + "/" + t.getClass().getSimpleName() + "/" + where;
	}

	private static void recordSignature(String signature, Stage stage, String origin, String source, String detail) {
		crashes.incrementAndGet();
		Bucket bucket = buckets.computeIfAbsent(signature, Bucket::new);
		if (bucket.count.getAndIncrement() == 0) {
			bucket.sampleSource = source;
			bucket.sampleStack = detail;
			bucket.sampleOrigin = origin;
			bucket.stage = stage;
		}
	}

	// ------------------------------------------------------------------
	// Hang detection and progress
	// ------------------------------------------------------------------

	private static void watch(Worker[] workers, long start) {
		long lastDone = 0;
		while (true) {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				return;
			}
			long now = System.currentTimeMillis();
			for (Worker w : workers) {
				Progress p = w.progress;
				long startedAt = p.startedAt;
				if (startedAt != 0 && !p.reportedHang && now - startedAt > hangMillis) {
					p.reportedHang = true;
					hangs.incrementAndGet();
					StringBuilder sb = new StringBuilder();
					sb.append("no progress for ").append((now - startedAt) / 1000).append("s in thread ")
							.append(w.getName()).append('\n');
					StackTraceElement[] frames = w.getStackTrace();
					for (int i = 0; i < Math.min(frames.length, 25); i++) {
						sb.append("\tat ").append(frames[i]).append('\n');
					}
					String where = "unknown";
					for (StackTraceElement frame : frames) {
						if (frame.getClassName().startsWith("jkind.")
								&& !frame.getClassName().startsWith("jkind.fuzz.")) {
							where = frame.getClassName() + "." + frame.getMethodName();
							break;
						}
					}
					recordSignature("HANG/" + where, Stage.GENERATE, p.origin, p.source, sb.toString());
				}
			}

			long current = done.get();
			long elapsed = Math.max(1, now - start);
			console.printf("\r  %,d/%,d  %,d/s  crashes=%,d buckets=%d hangs=%d      ", current, iterations,
					current * 1000 / elapsed, crashes.get(), buckets.size(), hangs.get());
			if (current >= iterations && current == lastDone) {
				return;
			}
			lastDone = current;
		}
	}

	// ------------------------------------------------------------------
	// Reporting
	// ------------------------------------------------------------------

	private static void report(long elapsedMillis) throws IOException {
		double seconds = elapsedMillis / 1000.0;
		StringBuilder sb = new StringBuilder();
		sb.append("# JKind front-end fuzzing report\n\n");
		sb.append(String.format("- inputs: %,d%n", done.get()));
		sb.append(String.format("- wall time: %.1fs (%,.0f inputs/s)%n", seconds, done.get() / Math.max(0.001, seconds)));
		sb.append(String.format("- base seed: %d%n", baseSeed));
		sb.append(String.format("- distinct findings: %d (%,d occurrences)%n", buckets.size(), crashes.get()));
		sb.append(String.format("- hangs: %d%n%n", hangs.get()));

		sb.append("## Input mix\n\n");
		generatorUse.entrySet().stream().sorted(Map.Entry.comparingByKey())
				.forEach(e -> sb.append(String.format("- %-14s %,d%n", e.getKey(), e.getValue().get())));

		sb.append("\n## How far inputs got\n\n");
		for (Stage s : Stage.values()) {
			AtomicLong n = stageReach.get(s.name());
			if (n != null) {
				sb.append(String.format("- %-16s %,d (%.1f%%)%n", s, n.get(), 100.0 * n.get() / done.get()));
			}
		}

		sb.append("\n## Clean rejections by exit code\n\n");
		exitCodes.entrySet().stream().sorted(Map.Entry.comparingByKey()).forEach(
				e -> sb.append(String.format("- exit %-4s %,d  (%s)%n", e.getKey(), e.getValue().get(),
						exitName(e.getKey()))));

		sb.append("\n## Findings\n\n");
		List<Bucket> sorted = new ArrayList<>(buckets.values());
		sorted.sort(Comparator.comparingLong((Bucket b) -> b.count.get()).reversed());
		if (sorted.isEmpty()) {
			sb.append("None.\n");
		}
		for (Bucket b : sorted) {
			sb.append(String.format("### %s%n%n", b.signature));
			sb.append(String.format("- occurrences: %,d%n", b.count.get()));
			sb.append(String.format("- first seen: %s%n%n", b.sampleOrigin));
			sb.append("```\n").append(b.sampleStack).append("```\n\n");
		}

		Path reportPath = outDir.resolve("report.md");
		Files.write(reportPath, sb.toString().getBytes(StandardCharsets.UTF_8));

		Path crashDir = outDir.resolve("findings");
		Files.createDirectories(crashDir);
		int i = 0;
		for (Bucket b : sorted) {
			String name = String.format("%03d-%s", i++, b.signature.replaceAll("[^A-Za-z0-9._-]", "_"));
			if (name.length() > 120) {
				name = name.substring(0, 120);
			}
			if (b.sampleSource != null && !b.sampleSource.isEmpty()) {
				Files.write(crashDir.resolve(name + ".lus"), b.sampleSource.getBytes(StandardCharsets.UTF_8));
			}
			Files.write(crashDir.resolve(name + ".txt"),
					(b.sampleOrigin + "\n\n" + b.sampleStack).getBytes(StandardCharsets.UTF_8));
		}

		console.println();
		console.println();
		console.print(sb);
		console.println("Report written to " + reportPath.toAbsolutePath());
	}

	private static String exitName(String code) {
		switch (code) {
		case "-2":
			return "invalid options";
		case "-3":
			return "file not found";
		case "-5":
			return "parse error";
		case "-6":
			return "static analysis error";
		case "-7":
			return "unsupported feature";
		case "-8":
			return "uncaught exception";
		default:
			return "other";
		}
	}
}
