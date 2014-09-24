package jkind.api.ui.counterexample;

import org.eclipse.swt.SWT;

public class CategoryHeader {
	public final static int FOREGROUND_COLOR = SWT.COLOR_INFO_FOREGROUND;
	public final static int BACKGROUND_COLOR = SWT.COLOR_INFO_BACKGROUND;
	private final String category;

	public CategoryHeader(String category) {
		this.category = category;
	}
	
	public String getCategory() {
		return category;
	}
}
