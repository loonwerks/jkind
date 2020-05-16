package jkind;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;

public class JLustre2KindArgumentParser extends ArgumentParser {
	private static final String ENCODE = "encode";
	private static final String OBFUSCATE = "obfuscate";
	private static final String STDOUT = "stdout";

	private final JLustre2KindSettings settings;

	private JLustre2KindArgumentParser() {
		this("JLustre2Kind", new JLustre2KindSettings());
	}

	private JLustre2KindArgumentParser(String name, JLustre2KindSettings settings) {
		super(name, settings);
		this.settings = settings;
	}

	@Override
	protected Options getOptions() {
		Options options = super.getOptions();
		options.addOption(STDOUT, false, "write result to standard out");
		options.addOption(OBFUSCATE, false, "obfuscate variable names");
		options.addOption(ENCODE, false, "encode identifiers to avoid reserved symbols");
		return options;
	}

	public static JLustre2KindSettings parse(String[] args) {
		JLustre2KindArgumentParser parser = new JLustre2KindArgumentParser();
		parser.parseArguments(args);
		return parser.settings;
	}

	@Override
	protected void parseCommandLine(CommandLine line) {
		super.parseCommandLine(line);

		if (line.hasOption(ENCODE)) {
			settings.encode = true;
		}

		if (line.hasOption(OBFUSCATE)) {
			settings.obfuscate = true;
		}

		if (line.hasOption(STDOUT)) {
			settings.stdout = true;
		}
	}
}
