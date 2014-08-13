package jkind;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;

public class JLustre2KindArgumentParser {
	final private static String ENCODE = "encode";
	final private static String STDOUT = "stdout";
	final private static String VERSION = "version";
	final private static String HELP = "help";

	private static Options getOptions() {
		Options options = new Options();
		options.addOption(STDOUT, false, "write result to standard out");
		options.addOption(ENCODE, false, "Encode identifiers to avoid reserved symbols");
		options.addOption(VERSION, false, "display version information");
		options.addOption(HELP, false, "print this message");
		return options;
	}

	public static JLustre2KindSettings parse(String[] args) {
		CommandLineParser parser = new GnuParser();
		try {
			return getSettings(parser.parse(getOptions(), args));
		} catch (Throwable t) {
			Output.fatal("reading command line arguments: " + t.getMessage());
			return null;
		}
	}

	private static void printHelp() {
		HelpFormatter formatter = new HelpFormatter();
		formatter.printHelp("jlustre2kind [options] <input>", getOptions());
	}

	private static JLustre2KindSettings getSettings(CommandLine line) {
		JLustre2KindSettings settings = new JLustre2KindSettings();
		if (line.hasOption(VERSION)) {
			Output.println("JLustre2Kind " + Main.VERSION);
			System.exit(0);
		}

		if (line.hasOption(HELP)) {
			printHelp();
			System.exit(0);
		}

		if (line.hasOption(ENCODE)) {
			settings.encode = true;
		}

		if (line.hasOption(STDOUT)) {
			settings.stdout = true;
		}

		String[] input = line.getArgs();
		if (input.length != 1) {
			printHelp();
			System.exit(-1);
		}
		settings.filename = input[0];
		
		return settings;
	}
}
