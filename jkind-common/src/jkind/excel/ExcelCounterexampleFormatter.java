package jkind.excel;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import jkind.JKindException;
import jkind.interval.BoolInterval;
import jkind.interval.NumericInterval;
import jkind.lustre.values.BooleanValue;
import jkind.lustre.values.EnumValue;
import jkind.lustre.values.IntegerValue;
import jkind.lustre.values.RealValue;
import jkind.lustre.values.Value;
import jkind.results.Counterexample;
import jkind.results.Signal;
import jkind.results.layout.Layout;
import jkind.util.BigFraction;
import jkind.util.Util;
import jxl.Workbook;
import jxl.format.CellFormat;
import jxl.write.Boolean;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCell;
import jxl.write.WritableCellFeatures;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class ExcelCounterexampleFormatter implements Closeable {
	final private Layout layout;

	private WritableWorkbook workbook;

	private WritableSheet sheet;
	private int row;

	/*
	 * CellFormats cannot be static, since JXL has strange results when a cell
	 * format is reused in another workbook. See {@link
	 * http://jexcelapi.sourceforge.net/resources/faq/}.
	 */
	private final CellFormat boldFormat = ExcelUtil.getBoldFormat();
	private final CellFormat fadedFormat = ExcelUtil.getFadedFormat();
	private final CellFormat defaultFormat = new WritableCellFormat();
	private final CellFormat highlightFormat = ExcelUtil.getHighlightFormat();

	public ExcelCounterexampleFormatter(File file, Layout layout) {
		this.layout = layout;

		try {
			workbook = Workbook.createWorkbook(file);
		} catch (IOException e) {
			throw new JKindException("Error writing to Excel file", e);
		}
	}

	public ExcelCounterexampleFormatter(WritableWorkbook workbook, Layout layout) {
		this.workbook = workbook;
		this.layout = layout;
	}

	@Override
	public void close() {
		try {
			if (workbook != null) {
				workbook.write();
				workbook.close();
				workbook = null;
			}
		} catch (Exception e) {
			throw new JKindException("Error closing Excel file", e);
		}
	}

	public WritableSheet writeCounterexample(String property, Counterexample cex,
			List<String> conflicts) {
		try {
			sheet = workbook
					.createSheet(ExcelUtil.trimName(property), workbook.getNumberOfSheets());
			row = 0;
			ExcelUtil.autosize(sheet, 1);

			int length = cex.getLength();
			writeStepsHeader(length);
			for (String category : layout.getCategories()) {
				List<Signal<Value>> signals = cex.getCategorySignals(layout, category);
				writeSection(category, signals, length, conflicts);
			}
			return sheet;
		} catch (WriteException e) {
			throw new JKindException("Error writing counterexample to Excel file", e);
		}
	}

	private void writeStepsHeader(int k) throws WriteException, RowsExceededException {
		sheet.addCell(new Label(0, row, "Step", boldFormat));
		for (int col = 1; col <= k; col++) {
			sheet.addCell(new Number(col, row, col - 1));
		}
		row++;
	}

	private void writeSection(String category, List<Signal<Value>> signals, int k,
			List<String> conflicts) throws WriteException {
		if (!signals.isEmpty()) {
			row++;
			sheet.addCell(new Label(0, row, category, boldFormat));
			row++;
			for (Signal<Value> signal : signals) {
				writeSignal(signal, k, conflicts);
				row++;
			}
		}
	}

	private void writeSignal(Signal<Value> signal, int k, List<String> conflicts)
			throws WriteException {
		sheet.addCell(new Label(0, row, signal.getName(),
				conflicts.contains(signal.getName()) ? highlightFormat : defaultFormat));
		Value prev = null;
		for (int i = 0; i < k; i++) {
			Value curr = signal.getValue(i);
			if (!Util.isArbitrary(curr)) {
				CellFormat format = curr.equals(prev) ? fadedFormat : defaultFormat;
				writeValue(curr, i + 1, format);
			}
			prev = curr;
		}
	}

	private void writeValue(Value value, int col, CellFormat format) throws WriteException {
		if (value instanceof BooleanValue) {
			BooleanValue bv = (BooleanValue) value;
			sheet.addCell(new Boolean(col, row, bv.value, format));
		} else if (value instanceof IntegerValue) {
			IntegerValue iv = (IntegerValue) value;
			sheet.addCell(getNumberCell(new BigFraction(iv.value), col, format));
		} else if (value instanceof RealValue) {
			RealValue rv = (RealValue) value;
			sheet.addCell(getNumberCell(rv.value, col, format));
		} else if (value instanceof EnumValue) {
			EnumValue ev = (EnumValue) value;
			sheet.addCell(new Label(col, row, ev.value));
		} else if (value instanceof NumericInterval) {
			NumericInterval ni = (NumericInterval) value;
			String str = "[" + ni.getLow().toDouble() + ", " + ni.getHigh().toDouble() + "]";
			sheet.addCell(new Label(col, row, str, format));
		} else if (value instanceof BoolInterval) {
			BoolInterval bi = (BoolInterval) value;
			sheet.addCell(new Boolean(col, row, bi.isTrue(), format));
		} else {
			throw new JKindException("Unknown value type in Excel writer: "
					+ value.getClass().getSimpleName());
		}
	}

	private WritableCell getNumberCell(BigFraction value, int col, CellFormat format) {
		WritableCell cell = new Number(col, row, value.doubleValue(), format);

		// Excel displays at most a float value. We check if that display
		// will match the exact value. If not, we add a comment with a better
		// approximation (or the exact value)
		if (!isExactFloat(value)) {
			WritableCellFeatures features = new WritableCellFeatures();
			features.setComment(getTruncatedDecimal(value, 20), 8, 3);
			cell.setCellFeatures(features);
		}

		return cell;
	}

	private boolean isExactFloat(BigFraction value) {
		try {
			String str = Float.toString((float) value.doubleValue());
			BigFraction approx = BigFraction.valueOf(new BigDecimal(str));
			return value.equals(approx);
		} catch (NumberFormatException e) {
			return false;
		}
	}

	private String getTruncatedDecimal(BigFraction value, int scale) {
		BigDecimal num = new BigDecimal(value.getNumerator());
		BigDecimal denom = new BigDecimal(value.getDenominator());
		BigDecimal truncated = num.divide(denom, scale, BigDecimal.ROUND_DOWN);

		if (BigFraction.valueOf(truncated).equals(value)) {
			return removeTrailingZeros(truncated.toPlainString());
		} else {
			return truncated.toPlainString() + "...";
		}
	}

	private String removeTrailingZeros(String str) {
		if (!str.contains(".")) {
			return str;
		}

		return str.replaceFirst("\\.?0*$", "");
	}
}
