package jkind.api.ui.counterexample;

import jkind.results.Counterexample;
import jkind.results.layout.Layout;

import org.eclipse.jface.layout.TreeColumnLayout;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

public class CounterexampleTreeViewer {
	protected TreeViewer treeViewer;
	protected final Composite composite;

	public CounterexampleTreeViewer(Composite parent) {
		this.composite = new Composite(parent, SWT.None);
	}

	public void setFocus() {
		if (treeViewer != null) {
			treeViewer.getTree().setFocus();
		}
	}

	public void setInput(Counterexample cex, Layout layout) {
		initializeTreeViewer(layout);
		createColumns(cex);
		treeViewer.setInput(cex);
		composite.layout(true);
	}

	private void initializeTreeViewer(Layout layout) {
		if (treeViewer != null) {
			treeViewer.getControl().dispose();
		}
		treeViewer = new TreeViewer(composite, SWT.MULTI | SWT.FULL_SELECTION);

		treeViewer.setContentProvider(new CounterexampleContentProvider(layout));
		treeViewer.getTree().setHeaderVisible(true);
		treeViewer.getTree().setLinesVisible(true);
	}

	private void createColumns(Counterexample cex) {
		TreeColumnLayout layout = new TreeColumnLayout();
		composite.setLayout(layout);
		createNameColumn(layout);
		for (int i = 0; i < cex.getLength(); i++) {
			createStepColumn(i, layout);
		}
	}

	private void createNameColumn(TreeColumnLayout layout) {
		TreeViewerColumn nameCol = new TreeViewerColumn(treeViewer, SWT.None);
		nameCol.getColumn().setText("Name");
		nameCol.setLabelProvider(new CounterexampleNameLabelProvider());
		layout.setColumnData(nameCol.getColumn(), new ColumnWeightData(10, 200));
	}

	private void createStepColumn(int i, TreeColumnLayout layout) {
		TreeViewerColumn stepCol = new TreeViewerColumn(treeViewer, SWT.None);
		stepCol.getColumn().setText("Step " + (i + 1));
		stepCol.setLabelProvider(new CounterexampleStepLabelProvider(i));
		layout.setColumnData(stepCol.getColumn(), new ColumnWeightData(1, 50));
	}

	public TreeViewer getTreeViewer() {
		return treeViewer;
	}
}
