package jkind.results;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class JKindResult {
	private String text;
	private List<Property> properties;

	public JKindResult(String text, List<Property> properties) {
		this.text = text;
		this.properties = properties;
	}

	public String getText() {
		return text;
	}

	public List<Property> getProperties() {
		return Collections.unmodifiableList(properties);
	}

	public Property getProperty(String name) {
		for (Property property : properties) {
			if (property.getName().equals(name)) {
				return property;
			}
		}
		return null;
	}
	
	public JKindResult rename(Renaming renaming) {
		List<Property> renamedProperties = new ArrayList<>();
		for (Property property : properties) {
			Property newProperty = property.rename(renaming);
			if (newProperty != null) {
				renamedProperties.add(newProperty);
			}
		}
		return new JKindResult(text, renamedProperties);
	}
}
