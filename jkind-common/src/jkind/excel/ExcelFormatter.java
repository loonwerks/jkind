package jkind.excel;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jkind.JKindException;
import jkind.lustre.values.BooleanValue;
import jkind.lustre.values.IntegerValue;
import jkind.lustre.values.RealValue;
import jkind.lustre.values.Value;
import jkind.results.Counterexample;
import jkind.results.InvalidProperty;
import jkind.results.JKindResult;
import jkind.results.Property;
import jkind.results.Signal;
import jkind.results.UnknownProperty;
import jkind.results.ValidProperty;
import jxl.CellView;
import jxl.Workbook;
import jxl.format.CellFormat;
import jxl.format.Colour;
import jxl.write.Boolean;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableHyperlink;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class ExcelFormatter {
	final private Layout layout;

	private WritableWorkbook workbook;
	private int numSheets;

	private WritableSheet summarySheet;
	private int summaryRow;

	private WritableSheet currSheet;
	private int currRow;

	private static final CellFormat boldFormat = getBoldFormat();
	private static final CellFormat fadedFormat = getFadedFormat();
	private static final CellFormat defaultFormat = new WritableCellFormat();

	public ExcelFormatter(File file, Layout layout) {
		this.layout = layout;

		try {
			workbook = Workbook.createWorkbook(file);
			numSheets = 0;

			summarySheet = workbook.createSheet("Summary", numSheets++);
			autosize(summarySheet, 4);
			summarySheet.addCell(new Label(0, 0, "Property", boldFormat));
			summarySheet.addCell(new Label(1, 0, "Result", boldFormat));
			summarySheet.addCell(new Label(2, 0, "K", boldFormat));
			summarySheet.addCell(new Label(3, 0, "Runtime", boldFormat));
			summaryRow = 1;
		} catch (WriteException | IOException e) {
			throw new JKindException("Error writing to Excel file", e);
		}
	}

	public void close() {
		try {
			workbook.write();
			workbook.close();
		} catch (Exception e) {
			throw new JKindException("Error closing Excel file", e);
		}
	}

	public void write(JKindResult result) {
		try {
			for (Property property : result.getProperties()) {
				write(property);
			}
		} catch (WriteException e) {
			throw new JKindException("Error writing to Excel file", e);
		}
	}

	private void write(Property property) throws WriteException {
		if (property instanceof ValidProperty) {
			write((ValidProperty) property);
		} else if (property instanceof InvalidProperty) {
			write((InvalidProperty) property);
		} else if (property instanceof UnknownProperty) {
			write((UnknownProperty) property);
		} else {
			throw new IllegalArgumentException("Unknown property type: "
					+ property.getClass().getSimpleName());
		}
	}

	private void write(ValidProperty property) throws WriteException {
		summarySheet.addCell(new Label(0, summaryRow, property.getName()));
		if (property.getInvariants().isEmpty()) {
			summarySheet.addCell(new Label(1, summaryRow, "Valid"));
		} else {
			WritableSheet invSheet = writeInvariants(property.getName(), property.getInvariants());
			summarySheet
					.addHyperlink(new WritableHyperlink(1, summaryRow, "Valid", invSheet, 0, 0));
		}
		summarySheet.addCell(new Number(2, summaryRow, property.getK()));
		summarySheet.addCell(new Number(3, summaryRow, property.getRuntime()));
		summaryRow++;
	}

	private WritableSheet writeInvariants(String property, List<String> invariants)
			throws WriteException {
		currSheet = workbook.createSheet(trimName(property), numSheets++);
		currRow = 0;

		currSheet.addCell(new Label(0, currRow, "Invariants for " + property, boldFormat));
		currRow += 2;

		for (String invariant : invariants) {
			currSheet.addCell(new Label(0, currRow, invariant));
			currRow++;
		}

		autosize(currSheet, 1);
		return currSheet;
	}

	private void write(InvalidProperty property) throws WriteException {
		WritableSheet cexSheet = writeCounterexample(property.getName(), property.getK(),
				property.getCounterexample());

		summarySheet.addCell(new Label(0, summaryRow, property.getName()));
		summarySheet.addHyperlink(new WritableHyperlink(1, summaryRow, "Invalid", cexSheet, 0, 0));
		summarySheet.addCell(new Number(2, summaryRow, property.getK()));
		summarySheet.addCell(new Number(3, summaryRow, property.getRuntime()));
		summaryRow++;
	}

	private WritableSheet writeCounterexample(String property, int k, Counterexample cex)
			throws WriteException {
		currSheet = workbook.createSheet(trimName(property), numSheets++);
		currRow = 0;
		autosize(currSheet, 1);

		writeStepsHeader(k);
		for (String category : layout.getCategories()) {
			List<Signal<Value>> signals = getCategorySignals(cex, category);
			writeSection(category, signals, k);
		}
		return currSheet;
	}

	private void writeStepsHeader(int k) throws WriteException, RowsExceededException {
		currSheet.addCell(new Label(0, currRow, "Step", boldFormat));
		for (int col = 1; col <= k; col++) {
			currSheet.addCell(new Number(col, currRow, col - 1));
		}
		currRow++;
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
			currRow++;
			currSheet.addCell(new Label(0, currRow, category, boldFormat));
			currRow++;
			for (Signal<Value> signal : signals) {
				writeSignal(signal, k);
				currRow++;
			}
		}
	}

	private void writeSignal(Signal<Value> signal, int k) throws WriteException {
		currSheet.addCell(new Label(0, currRow, signal.getName()));
		Value prev = null;
		for (int i = 0; i < k; i++) {
			Value curr = signal.getValue(i);
			if (curr != null) {
				CellFormat format = curr.equals(prev) ? fadedFormat : defaultFormat;
				writeValue(curr, i + 1, format);
			}
			prev = curr;
		}
	}

	private void writeValue(Value value, int col, CellFormat format) throws WriteException {
		if (value instanceof BooleanValue) {
			BooleanValue bv = (BooleanValue) value;
			currSheet.addCell(new Boolean(col, currRow, bv.value, format));
		} else if (value instanceof IntegerValue) {
			IntegerValue iv = (IntegerValue) value;
			currSheet.addCell(new Number(col, currRow, iv.value.doubleValue(), format));
		} else if (value instanceof RealValue) {
			RealValue rv = (RealValue) value;
			double quotient = rv.num.doubleValue() / rv.denom.doubleValue();
			currSheet.addCell(new Number(col, currRow, quotient, format));
		} else {
			throw new JKindException("Unknown value type in Excel writer: "
					+ value.getClass().getSimpleName());
		}
	}

	private void write(UnknownProperty property) throws WriteException {
		summarySheet.addCell(new Label(0, summaryRow, property.getName()));
		Counterexample cex = property.getInductiveCounterexample();
		if (cex == null) {
			summarySheet.addCell(new Label(1, summaryRow, "Unknown"));
		} else {
			WritableSheet cexSheet = writeCounterexample(property.getName(), property.getK(), cex);
			summarySheet.addHyperlink(new WritableHyperlink(1, summaryRow, "Unknown",
					cexSheet, 0, 0));
		}
		summaryRow++;
	}

	private static WritableCellFormat getBoldFormat() {
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

	private static WritableCellFormat getFadedFormat() {
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

	private void autosize(WritableSheet sheet, int max) {
		for (int col = 0; col < max; col++) {
			CellView cell = sheet.getColumnView(col);
			cell.setAutosize(true);
			sheet.setColumnView(col, cell);
		}
	}

	private String trimName(String name) {
		if (name.length() <= 31) {
			return name;
		} else {
			return name.substring(0, 28) + "...";
		}
	}
}
