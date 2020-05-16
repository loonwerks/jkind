package jkind;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;

public class JRealizabilityArgumentParser extends ArgumentParser {
	private static final String EXCEL = "excel";
	private static final String EXTEND_CEX = "extend_cex";
	private static final String N = "n";
	private static final String REDUCE = "reduce";
	private static final String SCRATCH = "scratch";
	private static final String TIMEOUT = "timeout";
	private static final String XML = "xml";

	private final JRealizabilitySettings settings;

	private JRealizabilityArgumentParser() {
		this("JRealizability", new JRealizabilitySettings());
	}

	private JRealizabilityArgumentParser(String name, JRealizabilitySettings settings) {
		super(name, settings);
		this.settings = settings;
	}

	@Override
	protected Options getOptions() {
		Options options = super.getOptions();
		options.addOption(EXCEL, false, "generate results in Excel format");
		options.addOption(EXTEND_CEX, false, "report extend counterexample");
		options.addOption(N, true, "number of iterations (default 200)");
		options.addOption(REDUCE, false, "reduce conflicting properties in case of unrealizable");
		options.addOption(SCRATCH, false, "produce files for debugging purposes");
		options.addOption(TIMEOUT, true, "maximum runtime in seconds (default 100)");
		options.addOption(XML, false, "generate results in XML format");
		return options;
	}

	public static JRealizabilitySettings parse(String[] args) {
		JRealizabilityArgumentParser parser = new JRealizabilityArgumentParser();
		parser.parseArguments(args);
		return parser.settings;
	}

	@Override
	protected void parseCommandLine(CommandLine line) {
		super.parseCommandLine(line);

		ensureExclusive(line, EXCEL, XML);

		if (line.hasOption(EXCEL)) {
			settings.excel = true;
		}

		if (line.hasOption(EXTEND_CEX)) {
			settings.extendCounterexample = true;
		}

		if (line.hasOption(N)) {
			settings.n = parseNonnegativeInt(line.getOptionValue(N));
		}

		if (line.hasOption(REDUCE)) {
			settings.reduce = true;
		}

		if (line.hasOption(TIMEOUT)) {
			settings.timeout = parseNonnegativeInt(line.getOptionValue(TIMEOUT));
		}

		if (line.hasOption(SCRATCH)) {
			settings.scratch = true;
		}

		if (line.hasOption(XML)) {
			settings.xml = true;
		}
	}
}
