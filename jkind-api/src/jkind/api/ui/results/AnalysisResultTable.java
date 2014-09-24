package jkind.api.ui.results;

import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

public class AnalysisResultTable extends AnalysisResultColumnViewer {
	private TableViewer tableViewer;

	public AnalysisResultTable(Composite parent) {
		super(parent);
	}

	@Override
	protected ColumnViewer createViewer() {
		tableViewer = new TableViewer(composite, SWT.FULL_SELECTION);
		tableViewer.getTable().setHeaderVisible(true);
		createColumns();
		return tableViewer;
	}

	private void createColumns() {
		TableViewerColumn propertyColumn = new TableViewerColumn(tableViewer, SWT.None);
		propertyColumn.getColumn().setText("Property");
		propertyColumn.getColumn().setWidth(400);
		propertyColumn.setLabelProvider(new AnalysisResultLabelProvider(Column.PROPERTY, tableViewer));

		TableViewerColumn resultColumn = new TableViewerColumn(tableViewer, SWT.None);
		resultColumn.getColumn().setText("Result");
		resultColumn.setLabelProvider(new AnalysisResultLabelProvider(Column.RESULT));

		TableColumnLayout layout = new TableColumnLayout();
		composite.setLayout(layout);
		layout.setColumnData(propertyColumn.getColumn(), new ColumnWeightData(2));
		layout.setColumnData(resultColumn.getColumn(), new ColumnWeightData(1));
	}
	
	@Override
	public TableViewer getViewer() {
		return tableViewer;
	}
}
