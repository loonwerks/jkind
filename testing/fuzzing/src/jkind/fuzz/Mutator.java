package jkind.fuzz;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Corpus-based mutation and random token generation.
 *
 * Mutation takes a known-good model and damages it in ways that keep it close
 * to valid Lustre, which is where parser and static-analysis robustness bugs
 * tend to hide. Token soup produces syntactically implausible input to exercise
 * lexer and parser error recovery.
 */
public final class Mutator {

	private static final String[] KEYWORDS = { "node", "function", "returns", "var", "let", "tel", "const", "type",
			"struct", "enum", "subrange", "of", "int", "bool", "real", "assert", "pre", "if", "then", "else", "and",
			"or", "xor", "not", "div", "mod", "condact", "floor", "true", "false" };

	private static final String[] OPERATORS = { "->", "=>", "<=", ">=", "<>", ":=", "+", "-", "*", "/", "=", "<", ">",
			"(", ")", "[", "]", "{", "}", ";", ",", ".", ":" };

	private static final String[] ANNOTATIONS = { "--%PROPERTY", "--%MAIN;", "--%IVC", "--%REALIZABLE" };

	private static final String[] EXTREME_NUMBERS = { "0", "-1", "1", "9223372036854775807", "9223372036854775808",
			"-9223372036854775808", "18446744073709551616", "99999999999999999999999999999999999999999999", "0.0",
			"000000000000000000001" };

	private final Random rnd;

	public Mutator(long seed) {
		this.rnd = new Random(seed);
	}

	/** Applies one to four mutations to the given source text. */
	public String mutate(String source) {
		String result = source;
		int rounds = 1 + rnd.nextInt(4);
		for (int i = 0; i < rounds; i++) {
			result = mutateOnce(result);
		}
		return result;
	}

	private String mutateOnce(String source) {
		switch (rnd.nextInt(12)) {
		case 0:
			return deleteLine(source);
		case 1:
			return duplicateLine(source);
		case 2:
			return swapLines(source);
		case 3:
			return truncate(source);
		case 4:
			return replaceToken(source, KEYWORDS);
		case 5:
			return replaceToken(source, OPERATORS);
		case 6:
			return replaceNumber(source);
		case 7:
			return insertToken(source);
		case 8:
			return deleteChar(source);
		case 9:
			return flipChar(source);
		case 10:
			return repeatFragment(source);
		default:
			return insertAnnotation(source);
		}
	}

	private List<String> lines(String source) {
		return new ArrayList<>(Arrays.asList(source.split("\n", -1)));
	}

	private String deleteLine(String source) {
		List<String> lines = lines(source);
		if (lines.size() < 2) {
			return source;
		}
		lines.remove(rnd.nextInt(lines.size()));
		return String.join("\n", lines);
	}

	private String duplicateLine(String source) {
		List<String> lines = lines(source);
		if (lines.isEmpty()) {
			return source;
		}
		int i = rnd.nextInt(lines.size());
		lines.add(i, lines.get(i));
		return String.join("\n", lines);
	}

	private String swapLines(String source) {
		List<String> lines = lines(source);
		if (lines.size() < 2) {
			return source;
		}
		int a = rnd.nextInt(lines.size());
		int b = rnd.nextInt(lines.size());
		String tmp = lines.get(a);
		lines.set(a, lines.get(b));
		lines.set(b, tmp);
		return String.join("\n", lines);
	}

	private String truncate(String source) {
		if (source.isEmpty()) {
			return source;
		}
		return source.substring(0, rnd.nextInt(source.length()));
	}

	private String replaceToken(String source, String[] pool) {
		String[] tokens = source.split("(?<=\\s)|(?=\\s)");
		if (tokens.length == 0) {
			return source;
		}
		int i = rnd.nextInt(tokens.length);
		tokens[i] = pool[rnd.nextInt(pool.length)];
		return String.join("", tokens);
	}

	private String replaceNumber(String source) {
		java.util.regex.Matcher m = java.util.regex.Pattern.compile("\\d+").matcher(source);
		List<int[]> spans = new ArrayList<>();
		while (m.find()) {
			spans.add(new int[] { m.start(), m.end() });
		}
		if (spans.isEmpty()) {
			return source;
		}
		int[] span = spans.get(rnd.nextInt(spans.size()));
		String replacement = EXTREME_NUMBERS[rnd.nextInt(EXTREME_NUMBERS.length)];
		return source.substring(0, span[0]) + replacement + source.substring(span[1]);
	}

	private String insertToken(String source) {
		if (source.isEmpty()) {
			return randomToken();
		}
		int i = rnd.nextInt(source.length());
		return source.substring(0, i) + " " + randomToken() + " " + source.substring(i);
	}

	private String insertAnnotation(String source) {
		List<String> lines = lines(source);
		if (lines.isEmpty()) {
			return source;
		}
		String ann = ANNOTATIONS[rnd.nextInt(ANNOTATIONS.length)];
		if (!ann.endsWith(";")) {
			ann = ann + " x" + rnd.nextInt(100) + ";";
		}
		lines.add(rnd.nextInt(lines.size()), "  " + ann);
		return String.join("\n", lines);
	}

	private String deleteChar(String source) {
		if (source.isEmpty()) {
			return source;
		}
		int i = rnd.nextInt(source.length());
		return source.substring(0, i) + source.substring(i + 1);
	}

	private String flipChar(String source) {
		if (source.isEmpty()) {
			return source;
		}
		int i = rnd.nextInt(source.length());
		char c = (char) (32 + rnd.nextInt(95));
		return source.substring(0, i) + c + source.substring(i + 1);
	}

	private String repeatFragment(String source) {
		if (source.length() < 4) {
			return source;
		}
		int start = rnd.nextInt(source.length() - 2);
		int end = start + 1 + rnd.nextInt(Math.min(80, source.length() - start - 1));
		String fragment = source.substring(start, end);
		int repeats = 2 + rnd.nextInt(8);
		StringBuilder sb = new StringBuilder(source.substring(0, end));
		for (int i = 0; i < repeats; i++) {
			sb.append(fragment);
		}
		return sb.append(source.substring(end)).toString();
	}

	private String randomToken() {
		switch (rnd.nextInt(4)) {
		case 0:
			return KEYWORDS[rnd.nextInt(KEYWORDS.length)];
		case 1:
			return OPERATORS[rnd.nextInt(OPERATORS.length)];
		case 2:
			return EXTREME_NUMBERS[rnd.nextInt(EXTREME_NUMBERS.length)];
		default:
			return "id" + rnd.nextInt(1000);
		}
	}

	/** A random sequence of Lustre tokens, almost never a valid program. */
	public String tokenSoup() {
		int count = 5 + rnd.nextInt(200);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < count; i++) {
			sb.append(randomToken());
			sb.append(rnd.nextInt(6) == 0 ? "\n" : " ");
		}
		return sb.toString();
	}

	/** Random bytes in the printable range, to probe the lexer directly. */
	public String randomText() {
		int count = 1 + rnd.nextInt(500);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < count; i++) {
			sb.append((char) (32 + rnd.nextInt(95)));
		}
		return sb.toString();
	}
}
