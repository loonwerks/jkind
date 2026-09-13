package jkind.fuzz;

import java.io.ByteArrayInputStream;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/** Reads the XML results JKind writes with {@code -xml_to_stdout}. */
public final class JKindXml {

	/** One property's result, with its counterexample if it has one. */
	public static final class Property {
		public final String name;
		/** "valid", "falsifiable", "unknown", or a solver-specific answer. */
		public final String answer;
		public final int k;
		/** Signal name to step to value; empty unless the answer is falsifiable. */
		public final Map<String, Map<Integer, Val>> signals;
		/** Inductive validity core elements, when JKind was asked for them. */
		public final java.util.List<String> ivc;

		Property(String name, String answer, int k, Map<String, Map<Integer, Val>> signals,
				java.util.List<String> ivc) {
			this.name = name;
			this.answer = answer;
			this.k = k;
			this.signals = signals;
			this.ivc = ivc;
		}

		/** Number of steps the counterexample covers. */
		public int steps() {
			int steps = 0;
			for (Map<Integer, Val> signal : signals.values()) {
				for (int time : signal.keySet()) {
					steps = Math.max(steps, time + 1);
				}
			}
			return steps;
		}
	}

	public static List<Property> parse(String xml) throws Exception {
		List<Property> properties = new ArrayList<>();
		int start = xml.indexOf("<?xml");
		if (start < 0) {
			return properties;
		}

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		// The input is JKind's own output, but keep the parser from resolving
		// anything external regardless.
		factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
		DocumentBuilder builder = factory.newDocumentBuilder();
		org.w3c.dom.Document document = builder
				.parse(new ByteArrayInputStream(xml.substring(start).getBytes(StandardCharsets.UTF_8)));

		NodeList propertyNodes = document.getElementsByTagName("Property");
		for (int i = 0; i < propertyNodes.getLength(); i++) {
			Element property = (Element) propertyNodes.item(i);
			String name = property.getAttribute("name");
			String answer = text(property, "Answer");
			String kText = text(property, "K");
			int k = kText == null || kText.isEmpty() ? -1 : Integer.parseInt(kText.trim());

			Map<String, Map<Integer, Val>> signals = new LinkedHashMap<>();
			NodeList signalNodes = property.getElementsByTagName("Signal");
			for (int s = 0; s < signalNodes.getLength(); s++) {
				Element signal = (Element) signalNodes.item(s);
				String signalName = signal.getAttribute("name");
				String type = signal.getAttribute("type");
				Map<Integer, Val> byTime = new LinkedHashMap<>();
				NodeList valueNodes = signal.getElementsByTagName("Value");
				for (int v = 0; v < valueNodes.getLength(); v++) {
					Element value = (Element) valueNodes.item(v);
					int time = Integer.parseInt(value.getAttribute("time"));
					byTime.put(time, parseValue(type, value.getTextContent().trim()));
				}
				signals.put(signalName, byTime);
			}
			List<String> ivc = new ArrayList<>();
			NodeList ivcNodes = property.getElementsByTagName("Ivc");
			for (int v = 0; v < ivcNodes.getLength(); v++) {
				ivc.add(ivcNodes.item(v).getTextContent().trim());
			}
			properties.add(new Property(name, answer == null ? "" : answer.trim(), k, signals, ivc));
		}
		return properties;
	}

	private static Val parseValue(String type, String text) {
		if ("bool".equals(type)) {
			return new Val.Bool("1".equals(text) || "true".equalsIgnoreCase(text));
		}
		if (text.contains("/")) {
			String[] parts = text.split("/");
			return new Val.Rat(new BigInteger(parts[0].trim()), new BigInteger(parts[1].trim()));
		}
		if ("real".equals(type)) {
			return Val.Rat.parseDecimal(text);
		}
		return new Val.Int(new BigInteger(text));
	}

	private static String text(Element parent, String tag) {
		NodeList nodes = parent.getElementsByTagName(tag);
		return nodes.getLength() == 0 ? null : nodes.item(0).getTextContent();
	}
}
