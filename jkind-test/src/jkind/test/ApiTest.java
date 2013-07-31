package jkind.test;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import jkind.api.InvalidProperty;
import jkind.api.JKindApi;
import jkind.api.JKindApiException;
import jkind.api.JKindResult;
import jkind.api.Property;
import jkind.api.Signal;
import jkind.api.UnknownProperty;
import jkind.api.ValidProperty;
import jkind.lustre.Constant;
import jkind.lustre.Location;
import jkind.lustre.Node;
import jkind.lustre.Program;
import jkind.lustre.TypeDef;
import jkind.lustre.parsing.LustreLexer;
import jkind.lustre.parsing.LustreParser;
import jkind.lustre.parsing.LustreParser.ProgramContext;
import jkind.lustre.parsing.LustreToAstVisitor;
import junit.framework.TestCase;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RecognitionException;
import org.junit.Test;

public class ApiTest extends TestCase {
	@Test
	public void testBasicResults() throws RecognitionException, IOException {
		StringBuilder text = new StringBuilder();
		text.append("node main() returns (counter : int);\n");
		text.append("var\n");
		text.append(" valid_prop, invalid_prop, unknown_prop : bool;\n");
		text.append("let\n");
		text.append("  counter = 0 -> 1 + pre counter;\n");
		text.append("  valid_prop = counter >= 0;\n");
		text.append("  invalid_prop = counter < 10;\n");
		text.append("  unknown_prop = counter < 1000;\n");
		text.append("  --%PROPERTY valid_prop;\n");
		text.append("  --%PROPERTY invalid_prop;\n");
		text.append("  --%PROPERTY unknown_prop;\n");
		text.append("tel;");
		
		Program program = parseLustre(text.toString());
		JKindApi api = new JKindApi();
		api.setN(20);
		JKindResult result = api.execute(program);
		
		Property validPropRaw = result.getProperty("valid_prop");
		assertTrue(validPropRaw instanceof ValidProperty);
		ValidProperty validProp = (ValidProperty) validPropRaw;
		assertEquals(1, validProp.getK());
		
		Property invalidPropRaw = result.getProperty("invalid_prop");
		assertTrue(invalidPropRaw instanceof InvalidProperty);
		InvalidProperty invalidProp = (InvalidProperty) invalidPropRaw;
		assertEquals(11, invalidProp.getK());
		Signal counterSignal = invalidProp.getCounterexample().getSignal("counter");
		for (int i = 0; i < 11; i++) {
			assertEquals(Integer.toString(i), counterSignal.get(i).toString());
		}
		Signal invalidPropSignal = invalidProp.getCounterexample().getSignal("invalid_prop");
		for (int i = 0; i < 10; i++) {
			assertEquals("true", invalidPropSignal.get(i).toString());
		}
		assertEquals("false", invalidPropSignal.get(10).toString());
		
		Property unknownPropRaw = result.getProperty("unknown_prop");
		assertTrue(unknownPropRaw instanceof UnknownProperty);
	}
	
	@Test
	public void testError() throws IOException {
		List<TypeDef> types = Collections.emptyList();
		List<Constant> constants = Collections.emptyList();
		List<Node> nodes = Collections.emptyList();
		Program program = new Program(Location.NULL, types, constants, nodes);
		try {
			new JKindApi().execute(program);
			fail("Expected exception");
		} catch (JKindApiException e) {
			assertTrue(e.getMessage().contains("no main node"));
		}
	}
	
	private static Program parseLustre(String content) throws IOException, RecognitionException {
		CharStream stream = new ANTLRInputStream(content);
		LustreLexer lexer = new LustreLexer(stream);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		LustreParser parser = new LustreParser(tokens);
		ProgramContext program = parser.program();

		if (parser.getNumberOfSyntaxErrors() > 0) {
			throw new IllegalArgumentException("Parse error in test case");
		}

		return new LustreToAstVisitor().program(program);
	}
}
