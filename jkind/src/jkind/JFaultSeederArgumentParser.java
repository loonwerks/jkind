package jkind;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;

public class JFaultSeederArgumentParser extends ArgumentParser {
	private static final String TOTAL_FAULTS = "total_faults";
	private static final String SINGLE_FILE = "single_file";
	private static final String STRATEGY = "strategy";
	// private static final String QUIET = "quiet";
	private static final String STATS = "stats";
	private static final String NON_LINEAR = "non_linear";

	private final JFaultSeederSettings settings;

	private JFaultSeederArgumentParser() {
		this("JKindFaultSeeder", new JFaultSeederSettings());
	}

	private JFaultSeederArgumentParser(String name, JFaultSeederSettings settings) {
		super(name, settings);
		this.settings = settings;
	}

	@Override
	protected Options getOptions() {
		Options options = super.getOptions();
		options.addOption(TOTAL_FAULTS, true, "total faults to emit per file (used with Proportional and Adjusted strategies)");
		options.addOption(SINGLE_FILE, false, "add all faults to one file (default is one fault per file)");
		options.addOption(STRATEGY, true, "strategy for fault construction.  One of: {manual, proportional, adjusted}");
		options.addOption(STATS, false, "provide statistics for the number of possible faults");
		options.addOption(NON_LINEAR, false, "allow multiplication and division mutations between two variables.");
		return options;
	}

	public static JFaultSeederSettings parse(String[] args) {
		JFaultSeederArgumentParser parser = new JFaultSeederArgumentParser();
		parser.parseArguments(args);
		parser.checkSettings();
		return parser.settings;
	}

	@Override
	protected void parseCommandLine(CommandLine line) {
		super.parseCommandLine(line);

		if (line.hasOption(TOTAL_FAULTS)) {
			settings.totalFaults = parseNonnegativeInt(line.getOptionValue(TOTAL_FAULTS));
		}
		
		if (line.hasOption(SINGLE_FILE)) {
			settings.faultPerFile = true;
		}

		if (line.hasOption(STRATEGY)) {
			switch(line.getOptionValue(STRATEGY).toLowerCase()) {
			case "manual": settings.strategy = JFaultSeederSettings.Strategy.Manual; break;
			case "adjusted": settings.strategy = JFaultSeederSettings.Strategy.Adjusted; break;
			case "proportional": settings.strategy = JFaultSeederSettings.Strategy.Proportional; break;
			default: 
				StdErr.fatal(ExitCodes.INVALID_OPTIONS, "Unknown strategy");
			}
		}
		
		if (line.hasOption(STATS)) {
			settings.stats = true;
		}
	}

	private void checkSettings() {
		if (settings.strategy == JFaultSeederSettings.Strategy.Manual) {
			StdErr.fatal(ExitCodes.INVALID_OPTIONS, "Strategy manual is not yet supported.");
		}
	}
}
