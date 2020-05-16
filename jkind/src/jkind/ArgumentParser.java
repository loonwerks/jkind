package jkind;

import java.math.BigInteger;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;

public abstract class ArgumentParser {
	protected static final String VERSION = "version";
	protected static final String HELP = "help";

	protected final String name;
	protected final Settings baseSettings;

	protected ArgumentParser(String name, Settings baseSettings) {
		super();
		this.name = name;
		this.baseSettings = baseSettings;
	}

	public void parseArguments(String[] args) {
		CommandLineParser parser = new BasicParser();
		try {
			parseCommandLine(parser.parse(getOptions(), args));
		} catch (Throwable t) {
			StdErr.fatal(ExitCodes.INVALID_OPTIONS, "reading command line arguments: " + t.getMessage());
		}
	}

	protected Options getOptions() {
		Options options = new Options();
		options.addOption(VERSION, false, "display version information");
		options.addOption(HELP, false, "print this message");
		return options;
	}

	protected void parseCommandLine(CommandLine line) {
		if (line.hasOption(VERSION)) {
			StdErr.println(name + " " + Main.VERSION);
			System.exit(0);
		}

		if (line.hasOption(HELP)) {
			printHelp();
			System.exit(0);
		}

		String[] input = line.getArgs();
		if (input.length != 1) {
			printHelp();
			System.exit(ExitCodes.INVALID_OPTIONS);
		}
		baseSettings.filename = input[0];
	}

	protected void printHelp() {
		HelpFormatter formatter = new HelpFormatter();
		formatter.printHelp(name.toLowerCase() + " [options] <input>", getOptions());
	}

	protected static int parseNonnegativeInt(String text) {
		BigInteger bi = new BigInteger(text);
		if (bi.compareTo(BigInteger.ZERO) < 0) {
			return 0;
		} else if (bi.compareTo(BigInteger.valueOf(Integer.MAX_VALUE)) > 0) {
			return Integer.MAX_VALUE;
		} else {
			return bi.intValue();
		}
	}

	protected static void ensureExclusive(CommandLine line, String opt1, String opt2) {
		if (line.hasOption(opt1) && line.hasOption(opt2)) {
			StdErr.fatal(ExitCodes.INVALID_OPTIONS, "cannot use option -" + opt1 + " with option -" + opt2);
		}
	}
}
