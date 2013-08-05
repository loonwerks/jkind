package jkind.api.ui;

import jkind.api.results.PropertyResult;
import jkind.api.results.Status;

import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;

public class JKindResultLabelProvider extends ColumnLabelProvider {
	private final Column column;

	public static enum Column {
		PROPERTY, RESULT
	};

	private static final Color WORKING_COLOR = new Color(null, new RGB(255, 255, 204));
	private static final Color VALID_COLOR = new Color(null, new RGB(204, 255, 204));
	private static final Color INVALID_COLOR = new Color(null, new RGB(255, 204, 204));
	private static final Color UNKNOWN_COLOR = new Color(null, new RGB(204, 204, 204));

	public JKindResultLabelProvider(Column column) {
		this.column = column;
	}

	@Override
	public String getText(Object element) {
		if (element instanceof PropertyResult) {
			PropertyResult pr = (PropertyResult) element;
			switch (column) {
			case PROPERTY:
				return pr.getName();
			case RESULT:
				switch (pr.getStatus()) {
				case WAITING:
					return pr.getStatus().toString();
				case WORKING:
					return pr.getStatus().toString() + "... (" + pr.getElapsed() + "s)";
				default:
					return pr.getStatus().toString() + " (" + pr.getElapsed() + "s)";
				}
			}
		}

		return "";
	}

	@Override
	public Color getBackground(Object element) {
		if (element instanceof PropertyResult) {
			PropertyResult pr = (PropertyResult) element;
			return getStatusColor(pr.getStatus());
		}

		return null;
	}

	private static Color getStatusColor(Status status) {
		switch (status) {
		case WORKING:
			return WORKING_COLOR;
		case VALID:
			return VALID_COLOR;
		case INVALID:
			return INVALID_COLOR;
		case UNKNOWN:
			return UNKNOWN_COLOR;
		case CANCELED:
			return UNKNOWN_COLOR;
		case ERROR:
			return INVALID_COLOR;
		default:
			return null;
		}
	}
}
