package jkind.api.ui.counterexample;

import java.util.ArrayList;
import java.util.List;

import jkind.lustre.values.Value;
import jkind.results.Counterexample;
import jkind.results.Signal;
import jkind.results.layout.Layout;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

public class CounterexampleContentProvider implements ITreeContentProvider {
	private final Layout layout;

	public CounterexampleContentProvider(Layout layout) {
		this.layout = layout;
	}

	@Override
	public void dispose() {
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
	}

	@Override
	public Object[] getElements(Object inputElement) {
		Counterexample cex = (Counterexample) inputElement;
		List<Object> result = new ArrayList<>();

		boolean first = true;
		for (String category : layout.getCategories()) {
			List<Signal<Value>> signals = cex.getCategorySignals(layout, category);
			if (!signals.isEmpty()) {
				if (first) {
					first = false;
				} else {
					result.add(new Spacer());
				}
				result.add(new CategoryHeader(category));
				result.addAll(SignalGrouper.group(null, signals));
			}
		}

		return result.toArray();
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		if (!(parentElement instanceof SignalGroup)) {
			return new Object[0];
		}

		SignalGroup group = (SignalGroup) parentElement;
		if (group.isSingleton()) {
			return new Object[0];
		}

		return SignalGrouper.group(group, group.getSignals()).toArray();
	}

	@Override
	public Object getParent(Object element) {
		if (element instanceof SignalGroup) {
			SignalGroup group = (SignalGroup) element;
			return group.getParent();
		}
		return null;
	}

	@Override
	public boolean hasChildren(Object element) {
		return getChildren(element).length > 0;
	}
}
