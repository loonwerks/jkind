package jkind.results;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import jkind.excel.ExcelFormatter;
import jkind.excel.Layout;
import jkind.excel.SingletonLayout;

/**
 * The results of an execution of JKind
 */
public final class JKindResult {
	private String text;
	private List<Property> properties;

	public JKindResult(String text, List<Property> properties) {
		this.text = text;
		this.properties = properties;
	}

	/**
	 * The console output from JKind
	 */
	public String getText() {
		return text;
	}

	/**
	 * All properties returned from JKind
	 */
	public List<Property> getProperties() {
		return Collections.unmodifiableList(properties);
	}

	/**
	 * Get a specific property by name
	 * 
	 * @param name
	 *            property to retrieve
	 * @return property with the specified name or <code>null</code> if it
	 *         cannot be found
	 */
	public Property getProperty(String name) {
		for (Property property : properties) {
			if (property.getName().equals(name)) {
				return property;
			}
		}
		return null;
	}

	/**
	 * Rename all properties and signals, possibly omitting some
	 * 
	 * @param renaming
	 *            The renaming to use
	 * @return Renamed version of the result. Note that the console output of
	 *         JKind is not renamed.
	 * @see Renaming
	 */
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
	 * @throws JKindException
	 */
	public void toExcel(File file, Layout layout) {
		ExcelFormatter formatter = new ExcelFormatter(file, layout);
		formatter.write(this);
		formatter.close();
	}

	/**
	 * Convert results to an Excel spreadsheet using default layout
	 * 
	 * Using this requires the jxl.jar file in your classpath
	 * 
	 * @param file
	 *            file to write Excel spreadsheet to
	 * @throws JKindException
	 */
	public void toExcel(File file) {
		toExcel(file, new SingletonLayout("Signals"));
	}
}
