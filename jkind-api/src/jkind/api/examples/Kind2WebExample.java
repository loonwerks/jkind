package jkind.api.examples;

import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import jkind.api.xml.Kind2WebInputStream;
import jkind.api.xml.LineInputStream;

public class Kind2WebExample {
	public static void main(String[] args) throws Exception {
		if (args.length != 2) {
			System.err.println("Usage: Kind2WebExample [url] [filename]");
			return;
		}

		URI uri = new URI(args[0]);
		String filename = args[1];

		try (LineInputStream lines = new LineInputStream(
				new Kind2WebInputStream(uri, getKindArgs(), getLustre(filename)))) {
			String line;
			while ((line = lines.readLine()) != null) {
				System.out.print(line);
			}
		}
	}

	private static List<String> getKindArgs() {
		List<String> args = new ArrayList<>();
		args.add("-xml");
		args.add("--timeout_wall");
		args.add("100");
		args.add("-v");
		return args;
	}

	private static String getLustre(String filename) throws IOException {
		try (FileReader reader = new FileReader(filename)) {
			StringBuilder result = new StringBuilder();
			int i;
			while ((i = reader.read()) != -1) {
				result.append((char) i);
			}
			return result.toString();
		}
	}
}
