package jkind.api.ui;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import jkind.api.results.DynamicJKindResult;
import jkind.api.results.PropertyResult;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.widgets.Display;

public class JKindResultContentProvider implements IStructuredContentProvider,
		PropertyChangeListener {
	private JKindTable viewer;

	public JKindResultContentProvider(JKindTable viewer) {
		this.viewer = viewer;
	}

	@Override
	public void dispose() {
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		if (oldInput instanceof DynamicJKindResult) {
			DynamicJKindResult result = (DynamicJKindResult) oldInput;

			result.removePropertyChangeListener(this);
			for (PropertyResult pr : result.getPropertyResults()) {
				pr.removePropertyChangeListener(this);
			}
		}
		
		if (newInput instanceof DynamicJKindResult) {
			DynamicJKindResult result = (DynamicJKindResult) newInput;

			result.addPropertyChangeListener(this);
			for (PropertyResult pr : result.getPropertyResults()) {
				pr.addPropertyChangeListener(this);
			}
		}
	}

	@Override
	public Object[] getElements(Object inputElement) {
		if (inputElement instanceof DynamicJKindResult) {
			DynamicJKindResult result = (DynamicJKindResult) inputElement;
			return result.getPropertyResults().toArray();
		}

		return null;
	}

	@Override
	public void propertyChange(PropertyChangeEvent event) {
		if (event.getSource() instanceof PropertyResult) {
			final PropertyResult pr = (PropertyResult) event.getSource();
			Display.getDefault().asyncExec(new Runnable() {
				@Override
				public void run() {
					viewer.update(pr);
				}
			});
		}
	}
}
