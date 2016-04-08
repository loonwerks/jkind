package jkind.lustre.parsing;

import java.util.HashMap;
import java.util.Map;

import jkind.JKindException;
import jkind.lustre.Equation;
import jkind.lustre.Expr;
import jkind.lustre.IdExpr;
import jkind.lustre.Location;
import jkind.lustre.Node;
import jkind.lustre.Program;
import jkind.lustre.VarDecl;
import jkind.translation.SubstitutionVisitor;
import jkind.util.Util;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;

public class LustreParseUtil {
	public static Expr expr(String input) {
		try {
			LustreParser parser = getParser(input);
			return new LustreToAstVisitor().expr(parser.expr());
		} catch (LustreParseException e) {
			throw new JKindException(formatErrorMessage(input, e));
		}
	}

	public static Expr expr(String input, Map<String, ? extends Expr> map) {
		return expr(input).accept(new SubstitutionVisitor(map));
	}

	public static Expr expr(String input, Mapping... mappings) {
		return expr(input, map(mappings));
	}

	public static Equation equation(String input) {
		try {
			LustreParser parser = getParser(input);
			return new LustreToAstVisitor().equation(parser.equation());
		} catch (LustreParseException e) {
			throw new JKindException(formatErrorMessage(input, e));
		}
	}

	public static Equation equation(String input, Map<String, ? extends Expr> map) {
		return new FullSubstitutionVisitor(map).visit(equation(input));
	}

	public static Equation equation(String input, Mapping... mappings) {
		return equation(input, map(mappings));
	}

	public static Node node(String input) {
		try {
			LustreParser parser = getParser(input);
			return new LustreToAstVisitor().node(parser.node());
		} catch (LustreParseException e) {
			throw new JKindException(formatErrorMessage(input, e));
		}
	}

	public static Node node(String input, Map<String, ? extends Expr> map) {
		return new FullSubstitutionVisitor(map).visit(node(input));
	}

	public static Node node(String input, Mapping... mappings) {
		return node(input, map(mappings));
	}

	public static Program program(String input) {
		try {
			LustreParser parser = getParser(input);
			return new LustreToAstVisitor().program(parser.program());
		} catch (LustreParseException e) {
			throw new JKindException(formatErrorMessage(input, e));
		}
	}

	public static Program program(String input, Map<String, ? extends Expr> map) {
		return new FullSubstitutionVisitor(map).visit(program(input));
	}

	public static Program program(String input, Mapping... mappings) {
		return program(input, map(mappings));
	}

	private static LustreParser getParser(String input) {
		CharStream stream = new ANTLRInputStream(input);
		LustreLexer lexer = new LustreLexer(stream);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		LustreParser parser = new LustreParser(tokens);
		parser.removeErrorListeners();
		parser.addErrorListener(new LustreParseExceptionErrorListener());
		return parser;
	}

	private static String formatErrorMessage(String input, LustreParseException e) {
		String message = "LustreParseUtil internal parsing error";
		message += System.lineSeparator() + e.getMessage();
		String[] lines = input.split("\\r?\\n");
		Location loc = e.getLocation();
		if (1 <= loc.line && loc.line <= lines.length) {
			String line = lines[loc.line - 1];
			message += System.lineSeparator() + line;
			message += System.lineSeparator() + Util.spaces(loc.charPositionInLine) + "^";
		}
		return message;
	}

	public static class Mapping {
		private final String key;
		private final Expr value;

		public Mapping(String key, Expr value) {
			this.key = key;
			this.value = value;
		}
	}

	public static Mapping to(String key, Expr value) {
		return new Mapping(key, value);
	}

	public static Mapping to(String key, VarDecl varDecl) {
		return new Mapping(key, new IdExpr(varDecl.id));
	}

	public static Mapping to(String key, String id) {
		return new Mapping(key, new IdExpr(id));
	}

	public static Map<String, Expr> map(Mapping... mappings) {
		Map<String, Expr> map = new HashMap<>();
		for (Mapping mapping : mappings) {
			map.put(mapping.key, mapping.value);
		}
		return map;
	}
}
