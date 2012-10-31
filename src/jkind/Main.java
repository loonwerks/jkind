package jkind;

import java.io.File;
import java.io.IOException;

import jkind.analysis.StaticAnalyzer;
import jkind.lustre.Lustre2AST;
import jkind.lustre.LustreLexer;
import jkind.lustre.LustreParser;
import jkind.lustre.Node;
import jkind.lustre.Program;
import jkind.processes.Director;
import jkind.slicing.Slicer;
import jkind.translation.InlineConstants;
import jkind.translation.InlineNodeCalls;

import org.antlr.runtime.ANTLRFileStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.CommonTree;

public class Main {
	final public static String VERSION = "0.1";
	
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

		program = InlineConstants.program(program);
		Node main = InlineNodeCalls.program(program);
		
		main = Slicer.slice(main);
		new Director(filename, main).run();
		System.exit(0); // Kills all threads
	}

	private static Program parseLustre(String filename) throws IOException, RecognitionException {
		CharStream stream = new ANTLRFileStream(filename);
		LustreLexer lexer = new LustreLexer(stream);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		LustreParser parser = new LustreParser(tokens);
		CommonTree tree = (CommonTree) parser.program().getTree();
		if (parser.getNumberOfSyntaxErrors() > 0) {
			System.exit(-1);
		}
		return Lustre2AST.program(tree);
	}
}
