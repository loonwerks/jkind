package jkind.fuzz;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.security.Permission;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Collections;

import org.antlr.v4.runtime.ANTLRInputStream;

import jkind.JKindSettings;
import jkind.Main;
import jkind.SolverOption;
import jkind.StdErr;
import jkind.analysis.StaticAnalyzer;
import jkind.lustre.Program;
import jkind.translation.Specification;
import jkind.translation.Translate;

/**
 * Reproduces a generated input from its seed, and reports why inputs are
 * rejected. Used both to replay a finding and to tune the generator so that a
 * useful share of inputs reach the deeper passes.
 */
public final class Repro {

	static final class ExitTrap extends SecurityException {
		private static final long serialVersionUID = 1L;
		final int code;

		ExitTrap(int code) {
			super("exit " + code);
			this.code = code;
		}
	}

	public static void main(String[] args) throws Exception {
		String kind = "well-typed";
		long seed = 0;
		int tally = 0;

		for (int i = 0; i < args.length; i++) {
			switch (args[i]) {
			case "--kind":
				kind = args[++i];
				break;
			case "--seed":
				seed = Long.parseLong(args[++i]);
				break;
			case "--tally":
				tally = Integer.parseInt(args[++i]);
				break;
			default:
				throw new IllegalArgumentException("unknown option: " + args[i]);
			}
		}

		if (tally > 0) {
			tally(kind, seed, tally);
		} else {
			System.out.println(source(kind, seed));
		}
	}

	static String source(String kind, long seed) {
		switch (kind) {
		case "well-typed":
			return LustreGen.generate(seed, LustreGen.Config.wellTyped());
		case "interpretable":
			return LustreGen.generate(seed, LustreGen.Config.interpretable());
		case "chaos":
			return LustreGen.generate(seed, LustreGen.Config.chaos(0.15));
		default:
			throw new IllegalArgumentException("unknown generator: " + kind);
		}
	}

	/**
	 * Runs a batch of generated programs through the front end and tallies the
	 * diagnostics that stop them, so the generator can be tuned toward inputs
	 * that exercise the deeper passes.
	 */
	static void tally(String kind, long baseSeed, int count) {
		PrintStream realOut = System.out;
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

		Map<String, Integer> reasons = new LinkedHashMap<>();
		int completed = 0;
		for (int i = 0; i < count; i++) {
			long seed = baseSeed + i;
			String src = source(kind, seed);
			ByteArrayOutputStream captured = new ByteArrayOutputStream();
			PrintStream capture = new PrintStream(captured, true, StandardCharsets.UTF_8);
			System.setOut(capture);
			System.setErr(capture);
			String reason;
			try {
				Program program = Main.parseLustre(new ANTLRInputStream(src));
				JKindSettings settings = new JKindSettings();
				StaticAnalyzer.check(program, SolverOption.SMTINTERPOL, settings);
				Program translated = Translate.translate(program);
				new Specification(translated, settings.slicing);
				reason = null;
				completed++;
			} catch (ExitTrap e) {
				reason = firstError(captured.toString(StandardCharsets.UTF_8));
			} catch (Throwable t) {
				String where = "?";
				for (StackTraceElement f : t.getStackTrace()) {
					if (f.getClassName().startsWith("jkind.") && !f.getClassName().startsWith("jkind.fuzz.")) {
						where = f.getClassName() + "." + f.getMethodName() + ":" + f.getLineNumber();
						break;
					}
				}
				reason = "THROWN " + t.getClass().getSimpleName() + " at " + where + " seed=" + seed;
			}
			System.setOut(realOut);
			System.setErr(realOut);
			if (reason != null) {
				reasons.merge(reason, 1, Integer::sum);
			}
		}

		System.setSecurityManager(null);
		realOut.printf("%s: %d/%d completed (%.1f%%)%n", kind, completed, count, 100.0 * completed / count);
		reasons.entrySet().stream().sorted(Map.Entry.<String, Integer> comparingByValue().reversed()).limit(25)
				.forEach(e -> realOut.printf("  %5d  %s%n", e.getValue(), e.getKey()));
	}

	/** The first ERROR line of captured diagnostics, normalized for tallying. */
	private static String firstError(String output) {
		for (String line : output.split("\n")) {
			if (line.startsWith("ERROR")) {
				return line.replaceAll("at line \\d+:\\d+", "at line L:C").replaceAll("'[^']*'", "'X'")
						.replaceAll("\\b[a-z]+\\d+\\b", "X").replaceAll("\\[.*\\]", "[...]").trim();
			}
		}
		for (String line : output.split("\n")) {
			String t = line.trim();
			if (!t.isEmpty()) {
				return "(no ERROR) " + t.replaceAll("\\d+", "N");
			}
		}
		return "(silent exit)";
	}
}
