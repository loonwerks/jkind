package jkind.api.ui.counterexample;

import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

public class CounterexampleNameLabelProvider extends ColumnLabelProvider {
	@Override
	public String getText(Object element) {
		if (element instanceof SignalGroup) {
			SignalGroup group = (SignalGroup) element;
			return group.getName();
		} else if (element instanceof CategoryHeader) {
			CategoryHeader header = (CategoryHeader) element;
			return header.getCategory();
		}

		return "";
	}
	
	@Override
	public Color getForeground(Object element) {
		if (element instanceof CategoryHeader) {
			return Display.getCurrent().getSystemColor(CategoryHeader.FOREGROUND_COLOR);
		}
		
		return null;
	}
	
	@Override
	public Color getBackground(Object element) {
		if (element instanceof CategoryHeader) {
			return Display.getCurrent().getSystemColor(CategoryHeader.BACKGROUND_COLOR);
		}
		
		return null;
	}
}
