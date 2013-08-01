package jkind.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jkind.JKindException;
import jkind.api.JKindApi;
import jkind.lustre.Program;
import jkind.lustre.parsing.LustreLexer;
import jkind.lustre.parsing.LustreParser;
import jkind.lustre.parsing.LustreParser.ProgramContext;
import jkind.lustre.parsing.LustreToAstVisitor;
import jkind.lustre.values.BooleanValue;
import jkind.lustre.values.IntegerValue;
import jkind.lustre.values.Value;
import jkind.results.Counterexample;
import jkind.results.InvalidProperty;
import jkind.results.JKindResult;
import jkind.results.MapRenaming;
import jkind.results.Property;
import jkind.results.Signal;
import jkind.results.UnknownProperty;
import jkind.results.ValidProperty;
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
		text.append("  valid_prop, invalid_prop, unknown_prop : bool;\n");
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
		Counterexample cex = invalidProp.getCounterexample();
		Signal<IntegerValue> counterSignal = cex.getIntegerSignal("counter");
		for (int i = 0; i < 11; i++) {
			assertEquals(i, counterSignal.getValue(i).value.intValue());
		}
		Signal<BooleanValue> invalidPropSignal = cex.getBooleanSignal("invalid_prop");
		for (int i = 0; i < 10; i++) {
			assertTrue(invalidPropSignal.getValue(i).value);
		}
		assertFalse(invalidPropSignal.getValue(10).value);

		Property unknownPropRaw = result.getProperty("unknown_prop");
		assertTrue(unknownPropRaw instanceof UnknownProperty);
	}

	@Test
	public void testError() throws IOException {
		Program program = new Program();
		try {
			new JKindApi().execute(program);
			fail("Expected exception");
		} catch (JKindException e) {
			assertTrue(e.getMessage().contains("no main node"));
		}
	}

	@Test
	public void testRenamingProperties() {
		Property property1 = new UnknownProperty("property1", 0, null);
		Property property2 = new UnknownProperty("property2", 0, null);
		List<Property> properties = new ArrayList<>();
		properties.add(property1);
		properties.add(property2);
		JKindResult original = new JKindResult("", properties);

		Map<String, String> map = new HashMap<>();
		map.put("property1", "prop1");
		JKindResult revised = original.rename(new MapRenaming(map));

		assertEquals(1, revised.getProperties().size());
		assertNull(revised.getProperty("property1"));
		assertNull(revised.getProperty("property2"));
		assertNotNull(revised.getProperty("prop1"));
	}

	@Test
	public void testRenamingCounterexample() {
		Signal<Value> signal1 = new Signal<>("signal1");
		Signal<Value> signal2 = new Signal<>("signal2");
		Counterexample original = new Counterexample();
		original.addSignal(signal1);
		original.addSignal(signal2);

		Map<String, String> map = new HashMap<>();
		map.put("signal1", "sig1");
		Counterexample revised = original.rename(new MapRenaming(map));

		assertEquals(1, revised.getSignals().size());
		assertNull(revised.getSignal("signal1"));
		assertNull(revised.getSignal("signal2"));
		assertNotNull(revised.getSignal("sig1"));
	}
	
	@Test
	public void testInvariantReduction() throws IOException {
		StringBuilder text = new StringBuilder();
		text.append("node main() returns (counter : int);\n");
		text.append("var\n");
		text.append(" x, inv_gen_prop : bool;\n");
		text.append("let\n");
		text.append("  counter = 1 -> if pre counter < 5 then pre counter + 1 else 5;\n");
		text.append("  x = false -> not (counter >= 5) and pre x;\n");
		text.append("  inv_gen_prop = true -> not pre x or x;\n");
		text.append("  --%PROPERTY inv_gen_prop;\n");
		text.append("tel;");

		Program program = parseLustre(text.toString());
		JKindApi api = new JKindApi();
		api.setReduceInvariants();
		JKindResult result = api.execute(program);
		Property prop = result.getProperty("inv_gen_prop");
		assertTrue(prop instanceof ValidProperty);
		ValidProperty validProp = (ValidProperty) prop;
		assertEquals(1, validProp.getInvariants().size());
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
