package jkind.api;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import jkind.lustre.values.BooleanValue;
import jkind.lustre.values.IntegerValue;
import jkind.lustre.values.RealValue;
import jkind.lustre.values.Value;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

public class XmlHandler extends DefaultHandler {
	public List<Property> properties = new ArrayList<>();

	private Counterexample cex;
	private Signal signal;

	private String propertyName;
	private String answer;
	private int k;
	private String type;
	private int time;

	private boolean readAnswer = false;
	private boolean readK = false;
	private boolean readValue = false;

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) {
		if (qName.equals("Property")) {
			propertyName = attributes.getValue("name");
		} else if (qName.equals("Answer")) {
			readAnswer = true;
		} else if (qName.equals("K")) {
			readK = true;
		} else if (qName.equals("Counterexample")) {
			cex = new Counterexample();
		} else if (qName.equals("Signal")) {
			signal = new Signal(attributes.getValue("name"));
			type = attributes.getValue("type");
			cex.addSignal(signal);
		} else if (qName.equals("Value")) {
			readValue = true;
			time = Integer.parseInt(attributes.getValue("time"));
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName) {
		if (qName.equals("Property")) {
			Property prop;
			switch (answer) {
			case "valid":
				prop = new ValidProperty(propertyName, k);
				break;

			case "falsifiable":
				prop = new InvalidProperty(propertyName, k, cex);
				break;

			case "unknown":
				prop = new UnknownProperty(propertyName);
				break;

			default:
				throw new JKindApiException("Unknown property answer in XML file: " + answer);
			}
			properties.add(prop);
		}
	}

	@Override
	public void characters(char ch[], int start, int length) {
		if (readAnswer) {
			answer = new String(ch, start, length);
			readAnswer = false;
		} else if (readK) {
			k = Integer.parseInt(new String(ch, start, length));
			readK = false;
		} else if (readValue) {
			Value value = readValue(new String(ch, start, length));
			signal.put(time, value);
			readValue = false;
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
