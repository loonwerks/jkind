package jkind;

import java.io.IOException;

import jkind.lustre.LustreLexer;
import jkind.lustre.LustreParser;
import jkind.lustre.Node;
import jkind.processes.BaseProcess;
import jkind.translation.Lustre2Sexps;

import org.antlr.runtime.ANTLRFileStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;

public class Main {
	public static void main(String args[]) throws IOException, RecognitionException {
		Node node = parseLustre(args[0]);
		Lustre2Sexps translation = new Lustre2Sexps(node);
		
		Thread baseThread = new Thread(new BaseProcess(node.properties, translation));
		baseThread.start();
	}

	private static Node parseLustre(String filename) throws IOException, RecognitionException {
		CharStream stream = new ANTLRFileStream(filename);
		LustreLexer lexer = new LustreLexer(stream);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		LustreParser parser = new LustreParser(tokens);
		return parser.node();
	}
}
