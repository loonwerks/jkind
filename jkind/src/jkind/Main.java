package jkind;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import jkind.lustre.ArrayType;
import jkind.lustre.Node;
import jkind.lustre.Program;
import jkind.lustre.RecordType;
import jkind.lustre.VarDecl;
import jkind.lustre.parsing.FlattenIds;
import jkind.lustre.parsing.LustreLexer;
import jkind.lustre.parsing.LustreParseException;
import jkind.lustre.parsing.LustreParser;
import jkind.lustre.parsing.LustreParser.ProgramContext;
import jkind.lustre.parsing.LustreToAstVisitor;
import jkind.lustre.parsing.StdErrErrorListener;
import jkind.lustre.parsing.ValidIdChecker;
import jkind.util.Util;

import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;

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
	public static final String VERSION = "4.0.0";

	public static void main(String[] args) {
		String availableEntryPoints = "Available entry points: -jkind, -jlustre2kind, -jlustre2excel, -jrealizability, -benchmark";
		if (args.length == 0) {
			StdErr.println("JKind Suite " + VERSION);
			StdErr.println(availableEntryPoints);
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

		case "-jrealizability":
			JRealizability.main(subArgs);
			break;

		case "-benchmark":
			Benchmark.main(subArgs);
			break;

		default:
			StdErr.error("unknown entry point: " + entryPoint);
			StdErr.println(availableEntryPoints);
			System.exit(ExitCodes.UNKNOWN_ENTRY_POINT);
		}
	}

	public static Program parseLustre(String filename) throws Exception {
		File file = new File(filename);
		if (!file.exists() || !file.isFile()) {
			StdErr.fatal(ExitCodes.FILE_NOT_FOUND, "cannot find file " + filename);
		}
		if (!file.canRead()) {
			StdErr.fatal(ExitCodes.FILE_NOT_READABLE, "cannot read file " + filename);
		}

		StdErr.setLocationReference(readAllLines(filename));
		return parseLustre(new ANTLRFileStream(filename));
	}

	private static List<String> readAllLines(String filename) {
		Path path = FileSystems.getDefault().getPath(filename);
		try {
			return Files.readAllLines(path);
		} catch (IOException e) {
			return Collections.emptyList();
		}
	}

	public static Program parseLustre(CharStream stream) throws Exception {
		LustreLexer lexer = new LustreLexer(stream);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		LustreParser parser = new LustreParser(tokens);
		parser.removeErrorListeners();
		parser.addErrorListener(new StdErrErrorListener());
		ProgramContext program = parser.program();

		if (parser.getNumberOfSyntaxErrors() > 0) {
			System.exit(ExitCodes.PARSE_ERROR);
		}

		try {
			return flattenOrCheck(new LustreToAstVisitor().program(program));
		} catch (LustreParseException e) {
			StdErr.fatal(ExitCodes.PARSE_ERROR, e.getLocation(), e.getMessage());
			throw e;
		}
	}

	/**
	 * We allow extended ids (with ~ [ ] .) only when the program is a single
	 * node with simple types. This is useful for working with output from
	 * JLustre2Kind.
	 */
	private static Program flattenOrCheck(Program program) {
		if (isSimple(program)) {
			return new FlattenIds().visit(program);
		} else {
			if (!ValidIdChecker.check(program)) {
				System.exit(ExitCodes.PARSE_ERROR);
			}
			return program;
		}
	}

	private static boolean isSimple(Program program) {
		if (!program.types.isEmpty()) {
			return false;
		} else if (!program.constants.isEmpty()) {
			return false;
		} else if (program.nodes.size() != 1) {
			return false;
		}

		Node main = program.getMainNode();
		for (VarDecl vd : Util.getVarDecls(main)) {
			if (vd.type instanceof ArrayType || vd.type instanceof RecordType) {
				return false;
			}
		}
		return true;
	}
}
