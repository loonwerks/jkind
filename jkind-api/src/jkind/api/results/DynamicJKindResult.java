package jkind.api.results;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DynamicJKindResult extends AnalysisResult {
	private final StringBuilder text = new StringBuilder();
	private final List<PropertyResult> propertyResults = new ArrayList<PropertyResult>();
	private Ticker ticker;
	private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

	public DynamicJKindResult() {
	}

	public DynamicJKindResult(List<String> properties) {
		addProperties(properties);
	}

	public void addProperties(List<String> properties) {
		for (String property : properties) {
			addProperty(property);
		}
	}

	public PropertyResult addProperty(String property) {
		PropertyResult propertyResult = new PropertyResult(property);
		propertyResults.add(propertyResult);
		pcs.fireIndexedPropertyChange("propertyResults", propertyResults.size() - 1, null,
				propertyResult);
		return propertyResult;
	}

	public List<PropertyResult> getPropertyResults() {
		return Collections.unmodifiableList(propertyResults);
	}

	public PropertyResult getPropertyResult(String name) {
		for (PropertyResult pr : propertyResults) {
			if (pr.getName().equals(name)) {
				return pr;
			}
		}
		return null;
	}

	public void addText(char c) {
		text.append(c);
	}

	public void addText(String string) {
		text.append(string);
	}

	public String getText() {
		return text.toString();
	}

	public void start() {
		for (PropertyResult pr : propertyResults) {
			pr.start();
		}
		ticker = new Ticker(this);
		ticker.start();
	}

	public void tick() {
		for (PropertyResult pr : propertyResults) {
			pr.tick();
		}
	}

	public void cancel() {
		for (PropertyResult pr : propertyResults) {
			pr.cancel();
		}
		ticker.done();
	}

	public void done() {
		for (PropertyResult pr : propertyResults) {
			pr.done();
		}
		ticker.done();
	}

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		this.pcs.addPropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		this.pcs.removePropertyChangeListener(listener);
	}
}
