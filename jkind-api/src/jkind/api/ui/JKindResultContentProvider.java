package jkind.api.ui;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import jkind.api.results.JKindResult;
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
		if (oldInput instanceof JKindResult) {
			JKindResult result = (JKindResult) oldInput;

			result.removePropertyChangeListener(this);
			for (PropertyResult pr : result.getPropertyResults()) {
				pr.removePropertyChangeListener(this);
			}
		}
		
		if (newInput instanceof JKindResult) {
			JKindResult result = (JKindResult) newInput;

			result.addPropertyChangeListener(this);
			for (PropertyResult pr : result.getPropertyResults()) {
				pr.addPropertyChangeListener(this);
			}
		}
	}

	@Override
	public Object[] getElements(Object inputElement) {
		if (inputElement instanceof JKindResult) {
			JKindResult result = (JKindResult) inputElement;
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
