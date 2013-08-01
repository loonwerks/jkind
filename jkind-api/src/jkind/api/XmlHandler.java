package jkind.api;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import jkind.JKindException;
import jkind.lustre.values.BooleanValue;
import jkind.lustre.values.IntegerValue;
import jkind.lustre.values.RealValue;
import jkind.lustre.values.Value;
import jkind.results.Counterexample;
import jkind.results.InvalidProperty;
import jkind.results.Property;
import jkind.results.Signal;
import jkind.results.UnknownProperty;
import jkind.results.ValidProperty;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

class XmlHandler extends DefaultHandler {
	public List<Property> properties = new ArrayList<>();

	private Counterexample cex;
	private Signal<Value> signal;

	private String propertyName;
	private double runtime;
	private String answer;
	private int k;
	private List<String> invariants;
	
	private String type;
	private int time;

	private boolean readRuntime = false;
	private boolean readAnswer = false;
	private boolean readK = false;
	private boolean readValue = false;
	private boolean readInvariant = false;

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) {
		if (qName.equals("Property")) {
			propertyName = attributes.getValue("name");
			runtime = 0;
			invariants = new ArrayList<>();
			answer = null;
			k = 0;
			cex = null;
		} else if (qName.equals("Runtime")) {
			readRuntime = true;
		} else if (qName.equals("Answer")) {
			readAnswer = true;
		} else if (qName.equals("K")) {
			readK = true;
		} else if (qName.equals("Counterexample")) {
			cex = new Counterexample();
		} else if (qName.equals("Signal")) {
			signal = new Signal<Value>(attributes.getValue("name"));
			type = attributes.getValue("type");
			cex.addSignal(signal);
		} else if (qName.equals("Value")) {
			readValue = true;
			time = Integer.parseInt(attributes.getValue("time"));
		} else if (qName.equals("Invariant")) {
			readInvariant = true;
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName) {
		if (qName.equals("Property")) {
			Property prop;
			switch (answer) {
			case "valid":
				prop = new ValidProperty(propertyName, k, invariants, runtime);
				break;

			case "falsifiable":
				prop = new InvalidProperty(propertyName, k, cex, runtime);
				break;

			case "unknown":
				prop = new UnknownProperty(propertyName, k, cex);
				break;

			default:
				throw new JKindException("Unknown property answer in XML file: " + answer);
			}
			properties.add(prop);
		}
	}

	@Override
	public void characters(char ch[], int start, int length) {
		if (readRuntime) {
			runtime = Double.parseDouble(new String(ch, start, length));
			readRuntime = false;
		} else if (readAnswer) {
			answer = new String(ch, start, length);
			readAnswer = false;
		} else if (readK) {
			k = Integer.parseInt(new String(ch, start, length));
			readK = false;
		} else if (readValue) {
			Value value = readValue(new String(ch, start, length));
			signal.putValue(time, value);
			readValue = false;
		} else if (readInvariant) {
			invariants.add(new String(ch, start, length));
			readInvariant = false;
		}
	}

	private Value readValue(String str) {
		if (type.equals("bool")) {
			if (str.equals("0")) {
				return BooleanValue.FALSE;
			} else if (str.equals("1")) {
				return BooleanValue.TRUE;
			}
		} else if (type.equals("int")) {
			return new IntegerValue(new BigInteger(str));
		} else if (type.equals("real")) {
			String[] strs = str.split("/");
			if (strs.length <= 2) {
				BigInteger num = new BigInteger(strs[0]);
				BigInteger denom = strs.length > 1 ? new BigInteger(strs[1]) : BigInteger.ONE;
				return new RealValue(num, denom);
			}
		}

		throw new IllegalArgumentException("Unable to parse " + str + " as " + type);
	}
}
