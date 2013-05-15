package jkind;

import jkind.Settings.SolverOption;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;

public class ArgumentParser {
	final private static String BMC = "bmc";
	final private static String EXCEL = "excel";
	final private static String INDUCT_CEX = "induct_cex";
	final private static String N = "n";
	final private static String NO_INV_GEN = "no_inv_gen";
	final private static String REDUCE_INV = "reduce_inv";
	final private static String SCRATCH = "scratch";
	final private static String SMOOTH = "smooth";
	final private static String SOLVER = "solver";
	final private static String TIMEOUT = "timeout";
	final private static String XML = "xml";
	final private static String VERSION = "version";
	final private static String HELP = "help";

	private static Options getOptions() {
		Options options = new Options();
		options.addOption(BMC, false, "bounded model checking only (implies -" + NO_INV_GEN + ")");
		options.addOption(EXCEL, false, "generate results in Excel format");
		options.addOption(INDUCT_CEX, false, "generate inductive counterexamples");
		options.addOption(N, true, "number of iterations (default 200)");
		options.addOption(NO_INV_GEN, false, "disable invariant generation");
		options.addOption(REDUCE_INV, false, "reduce and display invariants used");
		options.addOption(SCRATCH, false, "produce files for debugging purposes");
		options.addOption(SMOOTH, false, "smooth counterexamples (minimum changes in values)");
		options.addOption(SOLVER, true, "SMT solver (default: yices, alternatives: cvc4, z3)");
		options.addOption(TIMEOUT, true, "maximum runtime in seconds (default 100)");
		options.addOption(XML, false, "generate results in XML format");
		options.addOption(VERSION, false, "display version information");
		options.addOption(HELP, false, "print this message");
		return options;
	}

	public static String parse(String[] args) {
		CommandLineParser parser = new GnuParser();
		try {
			CommandLine line = parser.parse(getOptions(), args);
			setSettings(line);
			checkSettings();
			String[] input = line.getArgs();
			if (input.length != 1) {
				printHelp();
				System.exit(-1);
			}
			return input[0];
		} catch (Throwable t) {
			System.out.println("Error reading command line arguments: " + t.getMessage());
			System.exit(-1);
			return null;
		}
	}

	private static void printHelp() {
		HelpFormatter formatter = new HelpFormatter();
		formatter.printHelp("jkind [options] <input>", getOptions());
	}

	private static void setSettings(CommandLine line) {
		if (line.hasOption(VERSION)) {
			System.out.println("JKind " + Main.VERSION);
			System.exit(0);
		}
		
		if (line.hasOption(HELP)) {
			printHelp();
			System.exit(0);
		}
		
		if (line.hasOption(EXCEL) && line.hasOption(XML)) {
			System.out.println("Error: only one output format may be selected");
			System.exit(-1);
		}
		
		if (line.hasOption(BMC)) {
			Settings.useInductiveProcess = false;
			Settings.useInvariantProcess = false;
		}
		
		if (line.hasOption(EXCEL)) {
			Settings.excel = true;
		}
		
		if (line.hasOption(INDUCT_CEX)) {
			Settings.inductiveCounterexamples = true;
		}
		
		if (line.hasOption(NO_INV_GEN)) {
			Settings.useInvariantProcess = false;
		}
		
		if (line.hasOption(N)) {
			Settings.n = Integer.parseInt(line.getOptionValue(N));
		}
		
		if (line.hasOption(REDUCE_INV)) {
			Settings.reduceInvariants = true;
		}
		
		if (line.hasOption(TIMEOUT)) {
			Settings.timeout = Integer.parseInt(line.getOptionValue(TIMEOUT));
		}
		
		if (line.hasOption(SCRATCH)) {
			Settings.scratch = true;
		}
		
		if (line.hasOption(SMOOTH))	 {
			Settings.smoothCounterexamples = true;
		}
		
		if (line.hasOption(SOLVER)) {
			String solver = line.getOptionValue(SOLVER);
			if (solver.equals("yices")) {
				Settings.solver = SolverOption.YICES;
			} else if (solver.equals("cvc4")) {
				Settings.solver = SolverOption.CVC4;
			} else if (solver.equals("z3")) {
				Settings.solver = SolverOption.Z3;
			} else {
				System.out.println("Unknown solver: " + solver);
				System.exit(-1);
			}
		}
		
		if (line.hasOption(XML)) {
			Settings.xml = true;
		}
	}
	
	private static void checkSettings() {
		if (Settings.solver == SolverOption.CVC4) {
			if (Settings.smoothCounterexamples) {
				System.out.println("Smoothing not supported with CVC4");
				System.exit(-1);
			}
			if (Settings.reduceInvariants) {
				System.out.println("Invariant reduction not supported with CVC4");
				System.exit(-1);
			}
		}
		if (Settings.solver == SolverOption.Z3) {
			if (Settings.smoothCounterexamples) {
				System.out.println("Smoothing not supported with Z3");
				System.exit(-1);
			}
			if (Settings.reduceInvariants) {
				System.out.println("Invariant reduction not supported with Z3");
				System.exit(-1);
			}
		}
	}
}
