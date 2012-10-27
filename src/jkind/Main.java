package jkind;

import java.io.File;
import java.io.IOException;

import jkind.lustre.LustreLexer;
import jkind.lustre.LustreParser;
import jkind.lustre.Node;
import jkind.processes.Director;
import jkind.slicing.Slicer;

import org.antlr.runtime.ANTLRFileStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;

public class Main {
	final public static String VERSION = "0.1";
	
	public static void main(String args[]) throws IOException, RecognitionException, InterruptedException {
		String filename = ArgumentParser.parse(args);
		if (!new File(filename).exists()) {
			System.out.println("Cannot find file " + filename);
			System.exit(-1);
		}
		
		Node node = parseLustre(filename);
		node = Slicer.slice(node);
		new Director(filename, node).run();
		System.exit(0); // Kills all threads
	}

	private static Node parseLustre(String filename) throws IOException, RecognitionException {
		CharStream stream = new ANTLRFileStream(filename);
		LustreLexer lexer = new LustreLexer(stream);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		LustreParser parser = new LustreParser(tokens);
		Node node = parser.node();
		if (parser.getNumberOfSyntaxErrors() > 0) {
			System.out.println("Parse error in " + filename);
			System.exit(-1);
		}
		return node;
	}
}
