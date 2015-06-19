package jkind.advice;

import java.io.IOException;
import java.io.InputStream;

import jkind.Main;
import jkind.lustre.Node;
import jkind.lustre.Program;
import jkind.lustre.builders.NodeBuilder;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.RecognitionException;

public class AdviceEncoder {
	public static String encode(Advice advice) {
		NodeBuilder builder = new NodeBuilder("main");
		builder.addLocals(advice.getVarDecls());
		builder.addAssertions(advice.getInvariants());
		return new Program(builder.build()).toString();
	}

	public static Advice decode(InputStream is) throws RecognitionException, IOException {
		Program program = Main.parseLustre(new ANTLRInputStream(is));
		Node main = program.getMainNode();
		
		Advice advice = new Advice();
		advice.addVarDecls(main.locals);
		advice.addInvariants(main.assertions);
		return advice;
	}
}
