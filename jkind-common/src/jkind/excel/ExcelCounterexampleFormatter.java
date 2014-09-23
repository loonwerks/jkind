package jkind.excel;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import jkind.JKindException;
import jkind.interval.BoolInterval;
import jkind.interval.NumericInterval;
import jkind.lustre.values.BooleanValue;
import jkind.lustre.values.EnumValue;
import jkind.lustre.values.IntegerValue;
import jkind.lustre.values.RealValue;
import jkind.lustre.values.Value;
import jkind.results.Counterexample;
import jkind.results.FunctionTable;
import jkind.results.Signal;
import jkind.results.layout.Layout;
import jkind.util.Util;
import jxl.Workbook;
import jxl.format.CellFormat;
import jxl.write.Boolean;
import jxl.write.Label;
import jxl.write.Number;
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

	public WritableSheet writeCounterexample(String property, Counterexample cex) {
		try {
			sheet = workbook
					.createSheet(ExcelUtil.trimName(property), workbook.getNumberOfSheets());
			row = 0;
			ExcelUtil.autosize(sheet, 1);

			int length = cex.getLength();
			writeStepsHeader(length);
			for (String category : layout.getCategories()) {
				List<Signal<Value>> signals = getCategorySignals(cex, category);
				writeSection(category, signals, length);
			}

			if (cex.hasNonEmptyFunction()) {
				row++;
				sheet.addCell(new Label(0, row, "Functions", boldFormat));
				row++;
				for (Entry<String, FunctionTable> entry : cex.getFunctionTables().entrySet()) {
					writeFunction(entry.getKey(), entry.getValue());
				}
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

	private List<Signal<Value>> getCategorySignals(Counterexample cex, String category) {
		List<Signal<Value>> signals = new ArrayList<>();
		for (Signal<Value> signal : cex.getSignals()) {
			if (category.equals(layout.getCategory(signal.getName()))) {
				signals.add(signal);
			}
		}
		return signals;
	}

	private void writeSection(String category, List<Signal<Value>> signals, int k)
			throws WriteException {
		if (!signals.isEmpty()) {
			row++;
			sheet.addCell(new Label(0, row, category, boldFormat));
			row++;
			for (Signal<Value> signal : signals) {
				writeSignal(signal, k);
				row++;
			}
		}
	}

	private void writeSignal(Signal<Value> signal, int k) throws WriteException {
		sheet.addCell(new Label(0, row, signal.getName()));
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
			sheet.addCell(new Number(col, row, iv.value.doubleValue(), format));
		} else if (value instanceof RealValue) {
			RealValue rv = (RealValue) value;
			sheet.addCell(new Number(col, row, rv.value.doubleValue(), format));
		} else if (value instanceof EnumValue) {
			EnumValue ev = (EnumValue) value;
			sheet.addCell(new Label(col, row, ev.value));
		} else if (value instanceof NumericInterval) {
			NumericInterval ni = (NumericInterval) value;
			if (ni.isExact()) {
				sheet.addCell(new Number(col, row, ni.getLow().toDouble(), format));
			} else {
				String str = "[" + ni.getLow().toDouble() + ", " + ni.getHigh().toDouble() + "]";
				sheet.addCell(new Label(col, row, str, format));
			}
		} else if (value instanceof BoolInterval) {
			BoolInterval bi = (BoolInterval) value;
			sheet.addCell(new Boolean(col, row, bi.isTrue(), format));
		} else {
			throw new JKindException("Unknown value type in Excel writer: "
					+ value.getClass().getSimpleName());
		}
	}

	private void writeFunction(String name, FunctionTable table) throws WriteException {
		int col = 0;
		sheet.addCell(new Label(col++, row, name, defaultFormat));
		for (String input : table.getInputNames()) {
			sheet.addCell(new Label(col++, row, input, defaultFormat));
		}
		for (String output : table.getOutputNames()) {
			sheet.addCell(new Label(col++, row, output, defaultFormat));
		}
		row++;

		Map<List<Value>, List<Value>> map = table.getMap();
		for (List<Value> inputs : map.keySet()) {
			List<Value> outputs = map.get(inputs);
			col = 1;
			for (Value input : inputs) {
				if (!Util.isArbitrary(input)) {
					writeValue(input, col, defaultFormat);
				}
				col++;
			}
			for (Value output : outputs) {
				if (!Util.isArbitrary(output)) {
					writeValue(output, col, defaultFormat);
				}
				col++;
			}
			row++;
		}
		row++;
	}
}
