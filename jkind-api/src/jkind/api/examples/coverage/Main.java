package jkind.api.examples.coverage;

import java.io.File;

import jkind.SolverOption;
import jkind.api.JKindApi;
import jkind.api.results.JKindResult;
import jkind.api.results.PropertyResult;
import jkind.api.results.Status;
import jkind.lustre.Program;
import jkind.lustre.parsing.LustreLexer;
import jkind.lustre.parsing.LustreParser;
import jkind.lustre.parsing.LustreParser.ProgramContext;
import jkind.util.Util;

import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.eclipse.core.runtime.NullProgressMonitor;

public class Main {
	public static void main(String[] args) throws Exception {
		if (args.length != 1) {
			System.err.println("Usage of jcoverage : <filename.lus>");
			System.exit(-1);
		}
		String filename = args[0];
		Program program = parseLustre(new ANTLRFileStream(filename));
		
		ExtractorVisitor visitor = new ExtractorVisitor();
		program = visitor.visit(program);
		program = SubrangeFixVisitor.fix(program);
		
		Util.writeToFile(program.toString(), new File(filename + ".coverage"));
		
		//-------------- for the experiments -------------------------
		double runtime = System.currentTimeMillis(); 
		//----------------------------------------------------------
		
		
		JKindResult result = runJKind(filename, program);
		
		
		//------------ for the experiments -------------------------
		runtime = (System.currentTimeMillis() - runtime) / 1000.0; 
		// ---------------------------------------------------------
		
		
		
		CoverageReporter.writeHtml(filename, program, visitor.getLocationMap(), result, runtime);
	}

	private static Program parseLustre(CharStream stream) throws Exception {
		LustreLexer lexer = new LustreLexer(stream);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		LustreParser parser = new LustreParser(tokens);
		ProgramContext program = parser.program();
		if (parser.getNumberOfSyntaxErrors() > 0) {
			System.exit(-1);
		}
		return new LustreToEAstVisitor().program(program);
	}

	private static JKindResult runJKind(String filename, Program program) {
		JKindResult result = initialJKindResult(program);
		JKindApi api = new JKindApi();
		api.setIvcReduction();
		api.setSolver(SolverOption.Z3);
		api.execute(filename, program, result, new NullProgressMonitor());

		return result;
	}

	private static JKindResult initialJKindResult(Program program) {
		JKindResult result = new JKindResult("results");
		for (String prop : program.getMainNode().properties) {
			PropertyResult pr = result.addProperty(prop);
			pr.addPropertyChangeListener(evt -> {
				if (pr.getStatus() != Status.WORKING) {
					System.out.println(pr);
				}
			});
		}
		return result;
	}
}
