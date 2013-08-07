package jkind.api.results;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import jkind.excel.ExcelFormatter;
import jkind.excel.Layout;
import jkind.excel.SingletonLayout;
import jkind.results.Property;

public class JKindResult extends AnalysisResult implements PropertyChangeListener {
	private final StringBuilder text = new StringBuilder();
	private final List<PropertyResult> propertyResults = new ArrayList<PropertyResult>();
	private final MultiStatus multiStatus = new MultiStatus();
	private Ticker ticker;

	public JKindResult(String name) {
		super(name);
	}

	public JKindResult(String name, List<String> properties) {
		super(name);
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
		propertyResult.setParent(this);
		pcs.fireIndexedPropertyChange("propertyResults", propertyResults.size() - 1, null,
				propertyResult);
		addStatus(propertyResult.getStatus());
		propertyResult.addPropertyChangeListener(this);
		return propertyResult;
	}

	private void addStatus(Status other) {
		multiStatus.add(other);
		pcs.firePropertyChange("status", null, other);
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
	
	public MultiStatus getMultiStatus() {
		return multiStatus;
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
		if (ticker != null) {
			ticker.done();
		}
	}

	public void done() {
		for (PropertyResult pr : propertyResults) {
			pr.done();
		}
		if (ticker != null) {
			ticker.done();
		}
	}

	/**
	 * Convert results to an Excel spreadsheet
	 * 
	 * Using this requires the jxl.jar file in your classpath
	 * 
	 * @param file
	 *            file to write Excel spreadsheet to
	 * @param layout
	 *            layout information for counterexamples
	 * @see Layout
	 * @throws jkind.JKindException
	 */
	public void toExcel(File file, Layout layout) {
		ExcelFormatter formatter = new ExcelFormatter(file, layout);
		formatter.write(getProperties());
		formatter.close();
	}

	private List<Property> getProperties() {
		List<Property> properties = new ArrayList<Property>();
		for (PropertyResult pr : propertyResults) {
			if (pr.getProperty() != null) {
				properties.add(pr.getProperty());
			}
		}
		return properties;
	}

	/**
	 * Convert results to an Excel spreadsheet using default layout
	 * 
	 * Using this requires the jxl.jar file in your classpath
	 * 
	 * @param file
	 *            file to write Excel spreadsheet to
	 * @throws jkind.JKindException
	 */
	public void toExcel(File file) {
		toExcel(file, new SingletonLayout("Signals"));
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		// Status updates from immediate children are noted and propagated
		if ("status".equals(evt.getPropertyName()) && propertyResults.contains(evt.getSource())) {
			multiStatus.remove((Status) evt.getOldValue());
			multiStatus.add((Status) evt.getNewValue());
			pcs.firePropertyChange("status", evt.getOldValue(), evt.getNewValue());
		}
		
		if ("multiStatus".equals(evt.getPropertyName()) && propertyResults.contains(evt.getSource())) {
			multiStatus.remove((MultiStatus) evt.getOldValue());
			multiStatus.add((MultiStatus) evt.getNewValue());
			pcs.firePropertyChange("multiStatus", evt.getOldValue(), evt.getNewValue());
		}
	}
}
