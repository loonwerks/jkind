package jkind.api.examples.coverage;

import static java.util.stream.Collectors.toList;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jkind.api.results.JKindResult;
import jkind.api.results.PropertyResult;
import jkind.lustre.Program;
import jkind.results.ValidProperty;

public class CoverageReporter {
	public static void writeHtml(String filename, Program program, Map<String, ELocation> locationMap,
			JKindResult result) throws Exception {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename + ".html"))) {
			writer.write("<html>\n");
			writeHeader(writer);
			writer.write("<body>\n");

			for (PropertyResult pr : result.getPropertyResults()) {
				if (!(pr.getProperty() instanceof ValidProperty)) {
					writer.write("<p class='not-valid'>" + pr.getProperty().getName() + " is invalid/unknown</p>\n");
				}
			}

			for (PropertyResult pr : result.getPropertyResults()) {
				if (pr.getProperty() instanceof ValidProperty) {
					ValidProperty vp = (ValidProperty) pr.getProperty();
					List<String> allIvcs = getAllIvcs(program);

					List<ELocation> locations = getUnusedLocations(locationMap, allIvcs, vp.getIvc());

					int total = allIvcs.size();
					int covered = total - locations.size();
					String stats = String.format("%s: %.1f%% (%d of %d covered)", vp.getName(), 100.0 * covered / total,
							covered, total);
					writer.write("<div class='valid'>\n");
					writer.write("<div class='stats'>" + stats + "</div>\n");
					displayLocations(writer, filename, locations);
					writer.write("</div>\n");
				}
			}

			writer.write("</body>\n");
			writer.write("</html>\n");
		}
	}

	private static List<ELocation> getUnusedLocations(Map<String, ELocation> locationMap, List<String> allIvcs,
			Set<String> usedIvcs) {
		Set<String> baseUsedIvcs = new HashSet<>();
		Pattern pattern = Pattern.compile(".*(" + ExtractorVisitor.PREFIX + "\\d+).*");
		for (String ivc : usedIvcs) {
			Matcher matcher = pattern.matcher(ivc);
			if (!matcher.matches()) {
				System.err.println("Unknown IVC: " + ivc);
				System.exit(-1);
			}

			baseUsedIvcs.add(matcher.group(1));
		}

		List<ELocation> result = new ArrayList<>();
		for (String ivc : allIvcs) {
			if (!baseUsedIvcs.contains(ivc)) {
				result.add(locationMap.get(ivc));
			}
		}
		return result;
	}

	private static List<String> getAllIvcs(Program program) {
		return program.nodes.stream().flatMap(n -> n.ivc.stream()).collect(toList());
	}

	private static void writeHeader(BufferedWriter writer) throws IOException {
		writer.write("<head>\n");
		writer.write("<style>\n");
		String filename = "/jkind/api/examples/coverage/style.css";
		try (InputStream stream = Main.class.getResourceAsStream(filename)) {
			int c;
			while ((c = stream.read()) != -1) {
				writer.write((char) c);
			}
		}
		writer.write("</style>\n");
		writer.write("</head>\n");
	}

	private static void displayLocations(BufferedWriter writer, String filename, List<ELocation> locations)
			throws Exception {
		int i = 0;

		writer.write("<div class='lustre'>");
		try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
			int c;
			boolean in_span = false;
			while ((c = reader.read()) != -1) {
				boolean need_span = contains(locations, i);
				String prefix = "";
				if (!in_span && need_span) {
					prefix = "<span class='unused'>";
				} else if (in_span && !need_span) {
					prefix = "</span>";
				}
				writer.write(prefix + (char) c);
				i++;
				in_span = need_span;
			}
		}
		writer.write("</div>\n");
	}

	private static boolean contains(List<ELocation> locations, int i) {
		return locations.stream().anyMatch(loc -> loc.start <= i && i <= loc.stop);
	}
}
