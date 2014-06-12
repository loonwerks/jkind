package jkind;

import java.math.BigInteger;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;

public class JKindArgumentParser {
	final private static String BMC = "bmc";
	final private static String EXCEL = "excel";
	final private static String INDUCT_CEX = "induct_cex";
	final private static String INTERVAL = "interval";
	final private static String N = "n";
	final private static String NO_INV_GEN = "no_inv_gen";
	final private static String REDUCE_INV = "reduce_inv";
	final private static String SCRATCH = "scratch";
	final private static String SMOOTH = "smooth";
	final private static String SOLVER = "solver";
	final private static String TIMEOUT = "timeout";
	final private static String XML = "xml";
	final private static String XML_TO_STDOUT = "xml_to_stdout";
	final private static String VERSION = "version";
	final private static String HELP = "help";

	private static Options getOptions() {
		Options options = new Options();
		options.addOption(BMC, false, "bounded model checking only (implies -" + NO_INV_GEN + ")");
		options.addOption(EXCEL, false, "generate results in Excel format");
		options.addOption(INDUCT_CEX, false, "generate inductive counterexamples");
		options.addOption(INTERVAL, false, "generalize counterexamples using interval analysis");
		options.addOption(N, true, "number of iterations (default 200)");
		options.addOption(NO_INV_GEN, false, "disable invariant generation");
		options.addOption(REDUCE_INV, false, "reduce and display invariants used");
		options.addOption(SCRATCH, false, "produce files for debugging purposes");
		options.addOption(SMOOTH, false, "smooth counterexamples (minimal changes in input values)");
		options.addOption(SOLVER, true, "SMT solver (default: yices, alternatives: cvc4, z3)");
		options.addOption(TIMEOUT, true, "maximum runtime in seconds (default 100)");
		options.addOption(XML, false, "generate results in XML format");
		options.addOption(XML_TO_STDOUT, false, "generate results in XML format on stardard out");
		options.addOption(VERSION, false, "display version information");
		options.addOption(HELP, false, "print this message");

		return options;
	}

	public static JKindSettings parse(String[] args) {
		CommandLineParser parser = new GnuParser();
		try {
			JKindSettings settings = getSettings(parser.parse(getOptions(), args));
			checkSettings(settings);
			return settings;
		} catch (Throwable t) {
			Output.error("reading command line arguments: " + t.getMessage());
			System.exit(-1);
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
		ensureExclusive(line, BMC, REDUCE_INV);
		ensureExclusive(line, BMC, INDUCT_CEX);

		if (line.hasOption(VERSION)) {
			Output.println("JKind " + Main.VERSION);
			System.exit(0);
		}

		if (line.hasOption(HELP)) {
			printHelp();
			System.exit(0);
		}

		if (line.hasOption(BMC)) {
			settings.useInductiveProcess = false;
			settings.useInvariantProcess = false;
		}

		if (line.hasOption(EXCEL)) {
			settings.excel = true;
		}

		if (line.hasOption(INDUCT_CEX)) {
			settings.inductiveCounterexamples = true;
		}

		if (line.hasOption(NO_INV_GEN)) {
			settings.useInvariantProcess = false;
		}

		if (line.hasOption(N)) {
			settings.n = Integer.parseInt(line.getOptionValue(N));
		}

		if (line.hasOption(REDUCE_INV)) {
			settings.reduceInvariants = true;
		}

		if (line.hasOption(TIMEOUT)) {
			BigInteger timeout = new BigInteger(line.getOptionValue(TIMEOUT));
			if (timeout.compareTo(BigInteger.valueOf(Integer.MAX_VALUE)) > 0) {
				settings.timeout = Integer.MAX_VALUE;
			} else {
				settings.timeout = timeout.intValue();
			}
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
			String solver = line.getOptionValue(SOLVER);
			if (solver.equals("yices")) {
				settings.solver = SolverOption.YICES;
			} else if (solver.equals("cvc4")) {
				settings.solver = SolverOption.CVC4;
			} else if (solver.equals("z3")) {
				settings.solver = SolverOption.Z3;
			} else if (solver.equals("yices2")) {
				settings.solver = SolverOption.YICES2;
			} else {
				Output.error("unknown solver: " + solver);
				System.exit(-1);
			}
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
			System.exit(-1);
		}
		settings.filename = input[0];

		return settings;
	}

	private static void ensureExclusive(CommandLine line, String opt1, String opt2) {
		if (line.hasOption(opt1) && line.hasOption(opt2)) {
			Output.error("cannot use option -" + opt1 + " with option -" + opt2);
			System.exit(-1);
		}
	}

	private static void checkSettings(JKindSettings settings) {
		if (settings.solver != SolverOption.YICES) {
			if (settings.smoothCounterexamples) {
				Output.error("smoothing not supported with " + settings.solver);
				System.exit(-1);
			}
			if (settings.reduceInvariants) {
				Output.error("invariant reduction not supported with " + settings.solver);
				System.exit(-1);
			}
		}
	}
}
