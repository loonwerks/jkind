package jkind;

import java.io.File;
import java.io.IOException;

import jkind.analysis.StaticAnalyzer;
import jkind.lustre.Node;
import jkind.lustre.Program;
import jkind.lustre.parsing.LustreLexer;
import jkind.lustre.parsing.LustreParser;
import jkind.lustre.parsing.LustreParser.ProgramContext;
import jkind.lustre.parsing.LustreToAstVisitor;
import jkind.lustre.parsing.StdoutErrorListener;
import jkind.processes.Director;
import jkind.slicing.LustreSlicer;
import jkind.translation.InlineConstants;
import jkind.translation.InlineNodeCalls;
import jkind.translation.InlineTypes;
import jkind.translation.Specification;

import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RecognitionException;

public class Main {
	final public static String VERSION = "1.0.1";
	
	public static void main(String args[]) throws IOException, RecognitionException, InterruptedException {
		String filename = ArgumentParser.parse(args);
		if (!new File(filename).exists()) {
			System.out.println("Cannot find file " + filename);
			System.exit(-1);
		}
		
		Program program = parseLustre(filename);
		if (program.main == null) {
			System.out.println("Error: no main node");
			System.exit(-1);
		}
		
		if (!StaticAnalyzer.check(program)) {
			System.exit(-1);
		}

		program = InlineTypes.program(program);
		program = InlineConstants.program(program);
		Node main = InlineNodeCalls.program(program);
		
		main = LustreSlicer.slice(main);
		Specification spec = new Specification(filename, main);
		new Director(spec).run();
		System.exit(0); // Kills all threads
	}

	private static Program parseLustre(String filename) throws IOException, RecognitionException {
		CharStream stream = new ANTLRFileStream(filename);
		LustreLexer lexer = new LustreLexer(stream);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		LustreParser parser = new LustreParser(tokens);
		
		parser.removeErrorListeners();
		parser.addErrorListener(new StdoutErrorListener());
		
		ProgramContext program = parser.program();
		if (parser.getNumberOfSyntaxErrors() > 0) {
			System.exit(-1);
		}
		
		return new LustreToAstVisitor().program(program);
	}
}
