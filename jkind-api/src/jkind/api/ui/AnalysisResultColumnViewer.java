package jkind.api.ui;

import jkind.api.results.AnalysisResult;

import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

public abstract class AnalysisResultColumnViewer {
	final protected Composite composite;
	final protected ColumnViewer viewer;

	public AnalysisResultColumnViewer(Composite parent) {
		this.composite = new Composite(parent, SWT.None);
		this.viewer = createViewer();
		viewer.setContentProvider(new AnalysisResultContentProvider(this));
	}

	/**
	 * Warning: This method will be called by the JKindColumnViewer constructor
	 * and thus subclasses will not be fully initialized at this time.
	 * Subclasses may use this method to initialized themselves.
	 */
	protected abstract ColumnViewer createViewer();
	
	public static enum Column {
		PROPERTY, RESULT
	}
	
	public void update(Object element) {
		viewer.update(element, null);
	}

	public void setInput(AnalysisResult model) {
		viewer.setInput(model);
	}

	public void setLayoutData(Object layoutData) {
		composite.setLayoutData(layoutData);
	}

	public void addSelectionChangedListener(ISelectionChangedListener listener) {
		viewer.addSelectionChangedListener(listener);
	}

	public void removeSelectionChangedListener(ISelectionChangedListener listener) {
		viewer.removeSelectionChangedListener(listener);
	}
}
