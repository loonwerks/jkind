package jkind.api.ui;

import jkind.api.results.JKindResult;
import jkind.api.results.PropertyResult;
import jkind.api.ui.JKindResultLabelProvider.Column;

import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

public class JKindTable {
	final private Composite composite;
	final private TableViewer viewer;
	
	public JKindTable(Composite parent) {
		composite = new Composite(parent, SWT.None);
		viewer = new TableViewer(composite, SWT.FULL_SELECTION);
		createColumns();
		viewer.setContentProvider(new JKindResultContentProvider(this));
		viewer.getTable().setHeaderVisible(true);
	}

	private void createColumns() {
		TableViewerColumn propertyColumn = new TableViewerColumn(viewer, SWT.None);
		propertyColumn.getColumn().setText("Property");
		propertyColumn.getColumn().setWidth(200);
		propertyColumn.setLabelProvider(new JKindResultLabelProvider(Column.PROPERTY));

		TableViewerColumn resultColumn = new TableViewerColumn(viewer, SWT.None);
		resultColumn.getColumn().setText("Result");
		resultColumn.getColumn().setWidth(200);
		resultColumn.setLabelProvider(new JKindResultLabelProvider(Column.RESULT));
		
		TableColumnLayout layout = new TableColumnLayout();
		composite.setLayout(layout);
		layout.setColumnData(propertyColumn.getColumn(), new ColumnWeightData(1));
		layout.setColumnData(resultColumn.getColumn(), new ColumnWeightData(1));
	}

	public void update(PropertyResult pr) {
		viewer.update(pr, null);
	}

	public void setInput(JKindResult model) {
		viewer.setInput(model);
	}

	public void setLayoutData(Object layoutData) {
		composite.setLayoutData(layoutData);
	}
}
