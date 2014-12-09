package jkind;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;

public class JKindArgumentParser {
	private static final String EXCEL = "excel";
	private static final String INDUCT_CEX = "induct_cex";
	private static final String INTERVAL = "interval";
	private static final String N = "n";
	private static final String NO_BMC = "no_bmc";
	private static final String NO_INV_GEN = "no_inv_gen";
	private static final String NO_K_INDUCTION = "no_k_induction";
	private static final String PDR_MAX = "pdr_max";
	private static final String READ_ADVICE = "read_advice";
	private static final String REDUCE_INV = "reduce_inv";
	private static final String SCRATCH = "scratch";
	private static final String SMOOTH = "smooth";
	private static final String SOLVER = "solver";
	private static final String TIMEOUT = "timeout";
	private static final String WRITE_ADVICE = "write_advice";
	private static final String XML = "xml";
	private static final String XML_TO_STDOUT = "xml_to_stdout";
	private static final String VERSION = "version";
	private static final String HELP = "help";

	private static Options getOptions() {
		Options options = new Options();
		options.addOption(EXCEL, false, "generate results in Excel format");
		options.addOption(INDUCT_CEX, false, "generate inductive counterexamples");
		options.addOption(INTERVAL, false, "generalize counterexamples using interval analysis");
		options.addOption(N, true, "maximum depth for bmc and k-induction (default: 200)");
		options.addOption(NO_BMC, false, "disable bounded model checking");
		options.addOption(NO_INV_GEN, false, "disable invariant generation");
		options.addOption(NO_K_INDUCTION, false, "disable k-induction");
		options.addOption(PDR_MAX, true,
				"maximum number of PDR parallel instances (0 to disable PDR)");
		options.addOption(READ_ADVICE, true, "read advice from specified file");
		options.addOption(REDUCE_INV, false, "reduce and display invariants used");
		options.addOption(SCRATCH, false, "produce files for debugging purposes");
		options.addOption(SMOOTH, false, "smooth counterexamples (minimal changes in input values)");
		options.addOption(SOLVER, true,
				"SMT solver (default: yices, alternatives: cvc4, z3, yices2, mathsat, smtinterpol)");
		options.addOption(TIMEOUT, true, "maximum runtime in seconds (default: 100)");
		options.addOption(WRITE_ADVICE, true, "write advice to specified file");
		options.addOption(XML, false, "generate results in XML format");
		options.addOption(XML_TO_STDOUT, false, "generate results in XML format on stardard out");
		options.addOption(VERSION, false, "display version information");
		options.addOption(HELP, false, "print this message");

		return options;
	}

	public static JKindSettings parse(String[] args) {
		CommandLineParser parser = new BasicParser();
		try {
			JKindSettings settings = getSettings(parser.parse(getOptions(), args));
			checkSettings(settings);
			return settings;
		} catch (Throwable t) {
			Output.fatal(ExitCodes.INVALID_OPTIONS,
					"reading command line arguments: " + t.getMessage());
			return null;
		}
	}

	private static void printHelp() {
		HelpFormatter formatter = new HelpFormatter();
		formatter.printHelp("jkind [options] <input>", getOptions());
	}

	private static JKindSettings getSettings(CommandLine line) {
		JKindSettings settings = new JKindSettings();

		ensureExclusive(line, EXCEL, XML);
		ensureExclusive(line, EXCEL, XML_TO_STDOUT);
		ensureExclusive(line, XML, XML_TO_STDOUT);

		if (line.hasOption(VERSION)) {
			Output.println("JKind " + Main.VERSION);
			System.exit(0);
		}

		if (line.hasOption(HELP)) {
			printHelp();
			System.exit(0);
		}

		if (line.hasOption(EXCEL)) {
			settings.excel = true;
		}

		if (line.hasOption(INDUCT_CEX)) {
			settings.inductiveCounterexamples = true;
		}

		if (line.hasOption(NO_BMC)) {
			settings.boundedModelChecking = false;
		}

		if (line.hasOption(NO_INV_GEN)) {
			settings.invariantGeneration = false;
		}

		if (line.hasOption(NO_K_INDUCTION)) {
			settings.kInduction = false;
		}

		if (line.hasOption(N)) {
			settings.n = parseNonnegativeInt(line.getOptionValue(N));
		}

		if (line.hasOption(PDR_MAX)) {
			settings.pdrMax = parseNonnegativeInt(line.getOptionValue(PDR_MAX));
		} else {
			int available = Runtime.getRuntime().availableProcessors();
			int heuristic = (available - 4) / 2;
			settings.pdrMax = Math.max(1, heuristic);
		}

		if (line.hasOption(READ_ADVICE)) {
			settings.readAdvice = line.getOptionValue(READ_ADVICE);
		}

		if (line.hasOption(REDUCE_INV)) {
			settings.reduceInvariants = true;
		}

		if (line.hasOption(TIMEOUT)) {
			settings.timeout = parseNonnegativeInt(line.getOptionValue(TIMEOUT));
		}

		if (line.hasOption(SCRATCH)) {
			settings.scratch = true;
		}

		if (line.hasOption(SMOOTH)) {
			settings.smoothCounterexamples = true;
		}

		if (line.hasOption(INTERVAL)) {
			settings.intervalGeneralization = true;
		}

		if (line.hasOption(SOLVER)) {
			settings.solver = getSolverOption(line.getOptionValue(SOLVER));
		}

		if (line.hasOption(WRITE_ADVICE)) {
			settings.writeAdvice = line.getOptionValue(WRITE_ADVICE);
		}

		if (line.hasOption(XML)) {
			settings.xml = true;
		}

		if (line.hasOption(XML_TO_STDOUT)) {
			settings.xmlToStdout = true;
			settings.xml = true;
		}

		String[] input = line.getArgs();
		if (input.length != 1) {
			printHelp();
			System.exit(ExitCodes.INVALID_OPTIONS);
		}
		settings.filename = input[0];

		return settings;
	}
	
	private static int parseNonnegativeInt(String text) {
		BigInteger bi = new BigInteger(text);
		if (bi.compareTo(BigInteger.ZERO) < 0) {
			return 0;
		} else if (bi.compareTo(BigInteger.valueOf(Integer.MAX_VALUE)) > 0) {
			return Integer.MAX_VALUE;
		} else {
			return bi.intValue();
		}
	}

	private static SolverOption getSolverOption(String solver) {
		List<SolverOption> options = Arrays.asList(SolverOption.values());
		for (SolverOption option : options) {
			if (solver.equals(option.toString())) {
				return option;
			}
		}

		Output.error("unknown solver: " + solver);
		Output.println("Valid options: " + options);
		System.exit(ExitCodes.INVALID_OPTIONS);
		return null;
	}

	private static void ensureExclusive(CommandLine line, String opt1, String opt2) {
		if (line.hasOption(opt1) && line.hasOption(opt2)) {
			Output.fatal(ExitCodes.INVALID_OPTIONS, "cannot use option -" + opt1 + " with option -"
					+ opt2);
		}
	}

	private static void checkSettings(JKindSettings settings) {
		if (settings.solver != SolverOption.YICES) {
			if (settings.smoothCounterexamples) {
				Output.fatal(ExitCodes.INVALID_OPTIONS, "smoothing not supported with "
						+ settings.solver);
			}
			if (settings.reduceInvariants) {
				Output.fatal(ExitCodes.INVALID_OPTIONS, "invariant reduction not supported with "
						+ settings.solver);
			}
		}

		if (!settings.boundedModelChecking && !settings.kInduction && !settings.invariantGeneration
				&& settings.pdrMax == 0 && settings.readAdvice == null) {
			Output.fatal(ExitCodes.INVALID_OPTIONS, "all proving engines disabled");
		}

		if (!settings.boundedModelChecking && settings.kInduction) {
			Output.warning("k-induction requires bmc");
		}
	}
}
