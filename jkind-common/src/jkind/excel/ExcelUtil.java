package jkind.excel;

import jkind.JKindException;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.CellFormat;
import jxl.CellView;
import jxl.format.Colour;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WriteException;

public class ExcelUtil {
	public static CellFormat getBoldFormat() {
		WritableCellFormat boldFormat = new WritableCellFormat();
		WritableFont font = new WritableFont(boldFormat.getFont());
		try {
			font.setBoldStyle(WritableFont.BOLD);
		} catch (WriteException e) {
			throw new JKindException("Error creating bold font for Excel", e);
		}
		boldFormat.setFont(font);
		return boldFormat;
	}

	public static CellFormat getFadedFormat() {
		WritableCellFormat fadedFormat = new WritableCellFormat();
		WritableFont font = new WritableFont(fadedFormat.getFont());
		try {
			font.setColour(Colour.GREY_25_PERCENT);
		} catch (WriteException e) {
			throw new JKindException("Error creating grey font for Excel", e);
		}
		fadedFormat.setFont(font);
		return fadedFormat;
	}

	public static CellFormat getHighlightFormat() {
		WritableCellFormat highlightFormat = new WritableCellFormat();
		try {
			highlightFormat.setBackground(Colour.ROSE);
		} catch (WriteException e) {
			throw new JKindException("Error creating highlighting for Excel", e);
		}
		return highlightFormat;
	}

	public static CellFormat addBottomBorder(CellFormat cf) {
		WritableCellFormat format = new WritableCellFormat(cf);
		try {
			format.setBorder(Border.BOTTOM, BorderLineStyle.THIN);
		} catch (WriteException e) {
			throw new JKindException("Error creating border format for Excel", e);
		}
		return format;
	}

	public static CellFormat addLeftBorder(CellFormat cf) {
		WritableCellFormat format = new WritableCellFormat(cf);
		try {
			format.setBorder(Border.LEFT, BorderLineStyle.THIN);
		} catch (WriteException e) {
			throw new JKindException("Error creating border format for Excel", e);
		}
		return format;
	}

	public static void autosize(WritableSheet sheet, int max) {
		for (int col = 0; col < max; col++) {
			CellView cell = sheet.getColumnView(col);
			cell.setAutosize(true);
			sheet.setColumnView(col, cell);
		}
	}

	public static String trimName(String name) {
		if (name.length() <= 31) {
			return name;
		} else {
			return name.substring(0, 28) + "...";
		}
	}
}
