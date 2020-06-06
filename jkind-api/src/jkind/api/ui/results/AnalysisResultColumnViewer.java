package jkind.api.ui.results;

import jkind.api.results.AnalysisResult;

import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

public abstract class AnalysisResultColumnViewer {
	protected final Composite composite;
	protected final ColumnViewer viewer;

	public AnalysisResultColumnViewer(Composite parent) {
		this.composite = new Composite(parent, SWT.None);
		this.viewer = createViewer();
		viewer.setContentProvider(new AnalysisResultContentProvider(this));
	}

	/**
	 * Warning: This method will be called by the JKindColumnViewer constructor
	 * and thus subclasses will not be fully initialized at this time.
	 * Subclasses may use this method to initialize themselves.
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

	public Composite getComposite() {
		return composite;
	}

	public ColumnViewer getViewer() {
		return viewer;
	}

	public Control getControl() {
		return viewer.getControl();
	}
}
