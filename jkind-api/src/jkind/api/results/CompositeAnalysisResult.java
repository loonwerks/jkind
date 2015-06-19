package jkind.api.results;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CompositeAnalysisResult extends AnalysisResult implements PropertyChangeListener {
	final private List<AnalysisResult> children = new ArrayList<>();
	final private MultiStatus multiStatus = new MultiStatus();

	public CompositeAnalysisResult(String name) {
		super(name);
	}

	public void addChild(AnalysisResult child) {
		children.add(child);
		child.setParent(this);
		pcs.fireIndexedPropertyChange("children", children.size() - 1, null, child);
		addMultiStatus(ResultsUtil.getMultiStatus(child));
		child.addPropertyChangeListener(this);
	}

	private void addMultiStatus(MultiStatus other) {
		multiStatus.add(other);
		pcs.firePropertyChange("multiStatus", null, other);
	}
	
	public List<AnalysisResult> getChildren() {
		return Collections.unmodifiableList(children);
	}

	public MultiStatus getMultiStatus() {
		return multiStatus;
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		// Status updates from immediate children are noted and propagated
		if ("status".equals(evt.getPropertyName()) && children.contains(evt.getSource())) {
			multiStatus.remove((Status) evt.getOldValue());
			multiStatus.add((Status) evt.getNewValue());
			pcs.firePropertyChange("status", evt.getOldValue(), evt.getNewValue());
		}
		
		if ("multiStatus".equals(evt.getPropertyName()) && children.contains(evt.getSource())) {
			multiStatus.remove((MultiStatus) evt.getOldValue());
			multiStatus.add((MultiStatus) evt.getNewValue());
			pcs.firePropertyChange("multiStatus", evt.getOldValue(), evt.getNewValue());
		}
	}
	
	@Override
	public String toString() {
		return name + children;
	}
}
