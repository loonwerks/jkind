package jkind.api.ui.results;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import jkind.api.results.AnalysisResult;
import jkind.api.results.CompositeAnalysisResult;
import jkind.api.results.JKindResult;
import jkind.api.results.PropertyResult;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.widgets.Display;

public class AnalysisResultContentProvider implements ITreeContentProvider, PropertyChangeListener {
	private AnalysisResultColumnViewer viewer;

	public AnalysisResultContentProvider(AnalysisResultColumnViewer viewer) {
		this.viewer = viewer;
	}

	@Override
	public void dispose() {
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		if (oldInput instanceof AnalysisResult) {
			stopListening((AnalysisResult) oldInput);
		}

		if (newInput instanceof AnalysisResult) {
			startListening((AnalysisResult) newInput);
		}
	}

	private void stopListening(AnalysisResult result) {
		if (result instanceof JKindResult) {
			JKindResult jKindResult = (JKindResult) result;
			jKindResult.removePropertyChangeListener(this);
			for (PropertyResult pr : jKindResult.getPropertyResults()) {
				pr.removePropertyChangeListener(this);
			}
		} else if (result instanceof CompositeAnalysisResult) {
			CompositeAnalysisResult compositeResult = (CompositeAnalysisResult) result;
			compositeResult.removePropertyChangeListener(this);
			for (AnalysisResult subResult : compositeResult.getChildren()) {
				stopListening(subResult);
			}
		}
	}

	private void startListening(AnalysisResult result) {
		if (result instanceof JKindResult) {
			JKindResult jKindResult = (JKindResult) result;
			jKindResult.addPropertyChangeListener(this);
			for (PropertyResult pr : jKindResult.getPropertyResults()) {
				pr.addPropertyChangeListener(this);
			}
		} else if (result instanceof CompositeAnalysisResult) {
			CompositeAnalysisResult compositeResult = (CompositeAnalysisResult) result;
			compositeResult.addPropertyChangeListener(this);
			for (AnalysisResult subResult : compositeResult.getChildren()) {
				startListening(subResult);
			}
		}
	}

	@Override
	public Object[] getElements(Object inputElement) {
		return getChildren(inputElement);
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		if (parentElement instanceof JKindResult) {
			JKindResult result = (JKindResult) parentElement;
			return result.getPropertyResults().toArray();
		} else if (parentElement instanceof CompositeAnalysisResult) {
			CompositeAnalysisResult result = (CompositeAnalysisResult) parentElement;
			return result.getChildren().toArray();
		} else {
			return new Object[0];
		}
	}

	@Override
	public Object getParent(Object element) {
		if (element instanceof AnalysisResult) {
			AnalysisResult result = (AnalysisResult) element;
			return result.getParent();
		} else {
			return null;
		}
	}

	@Override
	public boolean hasChildren(Object element) {
		return getChildren(element).length > 0;
	}

	@Override
	public void propertyChange(PropertyChangeEvent event) {
		if (event.getSource() instanceof AnalysisResult) {
			final AnalysisResult result = (AnalysisResult) event.getSource();
			Display.getDefault().asyncExec(new Runnable() {
				@Override
				public void run() {
					viewer.update(result);
				}
			});
		}
	}
}
