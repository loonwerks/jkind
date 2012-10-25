package jkind;

import java.io.IOException;

import jkind.lustre.LustreLexer;
import jkind.lustre.LustreParser;
import jkind.lustre.Node;
import jkind.sexp.Sexp;
import jkind.translation.Lustre2Sexps;

import org.antlr.runtime.ANTLRFileStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;

public class Main {
	public static void main(String args[]) throws IOException, RecognitionException {
		Node node = parseLustre(args[0]);
		
		Lustre2Sexps translator = new Lustre2Sexps(node);
		for (Sexp def : translator.getDefinitions()) {
			System.out.println(def);
		}
		System.out.println(translator.getTransition());
	}

	private static Node parseLustre(String filename) throws IOException, RecognitionException {
		CharStream stream = new ANTLRFileStream(filename);
		LustreLexer lexer = new LustreLexer(stream);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		LustreParser parser = new LustreParser(tokens);
		return parser.node();
	}
}
