package jkind;

import java.io.IOException;

import org.antlr.runtime.ANTLRFileStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;

import jkind.lustre.LustreLexer;
import jkind.lustre.LustreParser;
import jkind.lustre.Node;

public class Main {
	public static void main(String args[]) throws IOException, RecognitionException {
		Node node = parseLustre(args[0]);
		for (String p : node.properties) {
			System.out.println(p);
		}
	}

	private static Node parseLustre(String filename) throws IOException, RecognitionException {
		CharStream stream = new ANTLRFileStream(filename);
		LustreLexer lexer = new LustreLexer(stream);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		LustreParser parser = new LustreParser(tokens);
		return parser.node();
	}
}
