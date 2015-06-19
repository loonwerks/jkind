package jkind.api.results;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public abstract class AnalysisResult {
	protected final String name;
	protected final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
	protected AnalysisResult parent; 
	
	public AnalysisResult(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setParent(AnalysisResult parent) {
		pcs.firePropertyChange("parent", this.parent, this.parent = parent);
	}
	
	public AnalysisResult getParent() {
		return parent;
	}
	
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		pcs.addPropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		pcs.removePropertyChangeListener(listener);
	}
}
