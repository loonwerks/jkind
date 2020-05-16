package jkind.api.ui.results;

import java.io.IOException;
import java.io.InputStream;

import jkind.api.results.AnalysisResult;
import jkind.api.results.JRealizabilityResult;
import jkind.api.results.PropertyResult;
import jkind.api.results.ResultsUtil;
import jkind.api.results.Status;
import jkind.api.ui.results.AnalysisResultColumnViewer.Column;
import jkind.results.InconsistentProperty;
import jkind.util.Util;

import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;

public class AnalysisResultLabelProvider extends ColumnLabelProvider {
	protected final Column column;

	public AnalysisResultLabelProvider(Column column) {
		this.column = column;
	}

	public AnalysisResultLabelProvider(Column column, ColumnViewer viewer) {
		this.column = column;
		workingSpinner = new Spinner("/icons/working.gif", viewer);
	}

	@Override
	public String getText(Object element) {
		if (element instanceof PropertyResult) {
			PropertyResult pr = (PropertyResult) element;
			switch (column) {
			case PROPERTY:
				if (pr.getName().equals(Util.REALIZABLE)) {
					return pr.getParent().getName();
				} else {
					return pr.getName();
				}
			case RESULT:
				switch (pr.getStatus()) {
				case WAITING:
					return pr.getStatus().toString();
				case WORKING:
					return pr.getStatus().toString() + "..." + getProgress(pr) + " ("
							+ Util.secondsToTime(pr.getElapsed()) + ")";
				case INCONSISTENT:
					InconsistentProperty ic = (InconsistentProperty) pr.getProperty();
					return getFinalStatus(pr) + " (" + ic.getK() + " steps, " + Util.secondsToTime(pr.getElapsed())
							+ ")";
				default:
					return getFinalStatus(pr) + " (" + Util.secondsToTime(pr.getElapsed()) + ")";
				}
			}
		} else if (element instanceof JRealizabilityResult) {
			JRealizabilityResult result = (JRealizabilityResult) element;
			return getText(result.getPropertyResult());
		} else if (element instanceof AnalysisResult) {
			AnalysisResult result = (AnalysisResult) element;
			switch (column) {
			case PROPERTY:
				return result.getName();
			case RESULT:
				return ResultsUtil.getMultiStatus(result).toString();
			}
		}

		return "";
	}

	protected String getProgress(PropertyResult pr) {
		if (pr.getBaseProgress() == 0) {
			return "";
		}

		return " [true for " + pr.getBaseProgress() + " steps]";
	}

	protected String getFinalStatus(PropertyResult pr) {
		if (pr.getStatus() == Status.UNKNOWN || pr.getStatus() == Status.CANCELED) {
			return pr.getStatus().toString() + getProgress(pr);
		} else {
			return pr.getStatus().toString();
		}
	}

	protected static final Image EMPTY_IMAGE = loadImage("/icons/empty.png");
	protected static final Image VALID_IMAGE = loadImage("/icons/valid.png");
	protected static final Image INVALID_IMAGE = loadImage("/icons/invalid.png");
	protected static final Image UNKNOWN_IMAGE = loadImage("/icons/unknown.png");
	protected static final Image INCONSISTENT_IMAGE = loadImage("/icons/invalid.png");
	protected static final Image WAITING_IMAGE = loadImage("/icons/waiting.png");
	protected static final Image CANCEL_IMAGE = loadImage("/icons/cancel.png");
	protected static final Image ERROR_IMAGE = loadImage("/icons/error.png");
	protected static final Image VALID_WARNING_IMAGE = loadImage("/icons/valid-warning.png");

	protected Spinner workingSpinner;

	protected static Image loadImage(String filename) {
		try (InputStream stream = AnalysisResultLabelProvider.class.getResourceAsStream(filename)) {
			return new Image(null, new ImageData(stream));
		} catch (IOException e) {
			return null;
		}
	}

	@Override
	public Image getImage(Object element) {
		if (column == Column.PROPERTY) {
			if (element instanceof PropertyResult) {
				PropertyResult pr = (PropertyResult) element;
				return getStatusImage(pr.getStatus());
			} else if (element instanceof AnalysisResult) {
				AnalysisResult result = (AnalysisResult) element;
				return getStatusImage(ResultsUtil.getMultiStatus(result).getOverallStatus());
			}
		}

		return null;
	}

	protected Image getStatusImage(Status status) {
		if (status == null) {
			return EMPTY_IMAGE;
		}

		switch (status) {
		case WORKING:
			return workingSpinner.getFrame();
		case VALID:
			return VALID_IMAGE;
		case INVALID:
			return INVALID_IMAGE;
		case UNKNOWN:
			return UNKNOWN_IMAGE;
		case INCONSISTENT:
			return INCONSISTENT_IMAGE;
		case CANCELED:
			return CANCEL_IMAGE;
		case ERROR:
			return ERROR_IMAGE;
		case WAITING:
			return WAITING_IMAGE;
		case VALID_REFINED:
			return VALID_WARNING_IMAGE;
		default:
			return WAITING_IMAGE;
		}
	}

	@Override
	public void dispose() {
		if (workingSpinner != null) {
			workingSpinner.dispose();
		}
	}
}
