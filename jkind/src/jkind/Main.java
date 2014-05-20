package jkind;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import jkind.lustre.Program;
import jkind.lustre.parsing.LustreLexer;
import jkind.lustre.parsing.LustreParser;
import jkind.lustre.parsing.LustreParser.ProgramContext;
import jkind.lustre.parsing.LustreToAstVisitor;
import jkind.lustre.parsing.StdoutErrorListener;

import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.BailErrorStrategy;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.DefaultErrorStrategy;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.atn.PredictionMode;
import org.antlr.v4.runtime.misc.ParseCancellationException;

/**
 * This class serves as a single entry point for all JKind-based command line
 * tools. This allows us to distribute a single jar for all tools. It would not
 * be enough to simply put this jar is the classpath and load the appropriate
 * main, because we rely on some third-party jars which Java is not able to load
 * out of another jar. Instead, we use Eclipse's "Export to Runnable JAR"
 * feature which comes with a jar-in-jar loader. The downside is that such
 * runnable JARs allow only a single entry point.
 */
public class Main {
	final public static String VERSION = "1.5.1";

	public static void main(String[] args) {
		String availableEntryPoints = "Available entry points: -jkind, -jlustre2kind, -jlustre2excel, -benchmark";
		if (args.length == 0) {
			Output.println("JKind Suite " + VERSION);
			Output.println(availableEntryPoints);
			System.exit(0);
		}

		String entryPoint = args[0];
		String[] subArgs = Arrays.copyOfRange(args, 1, args.length);

		switch (entryPoint) {
		case "-jkind":
			JKind.main(subArgs);
			break;

		case "-jlustre2kind":
			JLustre2Kind.main(subArgs);
			break;

		case "-jlustre2excel":
			JLustre2Excel.main(subArgs);
			break;

		case "-benchmark":
			Benchmark.main(subArgs);
			break;

		default:
			Output.error("unknown entry point: " + entryPoint);
			Output.println(availableEntryPoints);
			System.exit(-1);
		}
	}

	public static Program parseLustre(String filename) throws IOException, RecognitionException {
		if (!new File(filename).exists()) {
			Output.error("cannot find file " + filename);
			System.exit(-1);
		}
		
		CharStream stream = new ANTLRFileStream(filename);
		LustreLexer lexer = new LustreLexer(stream);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		LustreParser parser = new LustreParser(tokens);

		ProgramContext program;

		// Due to performance issues in the Antlr 4.0 release, we use a 2-stage
		// parsing approach. https://github.com/antlr/antlr4/issues/192
		parser.removeErrorListeners();
		parser.getInterpreter().setPredictionMode(PredictionMode.SLL);
		parser.setErrorHandler(new BailErrorStrategy());
		try {
			program = parser.program();
		} catch (ParseCancellationException e) {
			tokens.reset();
			parser.addErrorListener(new StdoutErrorListener());
			parser.setErrorHandler(new DefaultErrorStrategy());
			parser.getInterpreter().setPredictionMode(PredictionMode.LL);
			program = parser.program();
		}

		if (parser.getNumberOfSyntaxErrors() > 0) {
			System.exit(-1);
		}

		return new LustreToAstVisitor().program(program);
	}
}
