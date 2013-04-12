package jkind.writers;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import jkind.JKindException;
import jkind.invariant.Invariant;
import jkind.lustre.Node;
import jkind.processes.messages.InductiveCounterexampleMessage;
import jkind.solvers.BoolValue;
import jkind.solvers.Model;
import jkind.solvers.NumericValue;
import jkind.solvers.Value;
import jkind.util.Util;
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

public class ExcelWriter extends Writer {
	private List<String> inputs;
	private List<String> outputs;
	private List<String> locals;

	private WritableWorkbook workbook;
	private int numSheets;

	private WritableSheet summarySheet;
	private int summaryRow;

	private WritableSheet currSheet;
	private int currRow;

	private CellFormat boldFormat;
	private CellFormat fadedFormat;
	private CellFormat defaultFormat;

	public ExcelWriter(String filename, Node node) throws IOException {
		this.inputs = Util.getIds(node.inputs);
		this.outputs = Util.getIds(node.outputs);
		this.locals = Util.getIds(node.locals);

		this.workbook = Workbook.createWorkbook(new File(filename));
		this.numSheets = 0;

		this.boldFormat = getBoldFormat();
		this.fadedFormat = getFadedFormat();
		this.defaultFormat = new WritableCellFormat();
	}

	@Override
	public void begin() {
		try {
			summarySheet = workbook.createSheet("Summary", numSheets++);
			autosize(summarySheet, 4);
			summarySheet.addCell(new Label(0, 0, "Property", boldFormat));
			summarySheet.addCell(new Label(1, 0, "Result", boldFormat));
			summarySheet.addCell(new Label(2, 0, "K", boldFormat));
			summarySheet.addCell(new Label(3, 0, "Runtime", boldFormat));
			summaryRow = 1;
		} catch (WriteException e) {
			throw new JKindException("Error writing to Excel sheet", e);
		}
	}

	private void autosize(WritableSheet sheet, int max) {
		for (int col = 0; col < max; col++) {
			CellView cell = sheet.getColumnView(col);
			cell.setAutosize(true);
			sheet.setColumnView(col, cell);
		}
	}

	@Override
	public void end() {
		try {
			workbook.write();
			workbook.close();
		} catch (Exception e) {
			throw new JKindException("Error closing Excel file", e);
		}
	}

	@Override
	public void writeValid(List<String> props, int k, long elapsed, List<Invariant> invariants) {
		try {
			for (String prop : props) {
				summarySheet.addCell(new Label(0, summaryRow, prop));
				if (invariants.isEmpty()) {
					summarySheet.addCell(new Label(1, summaryRow, "Valid"));
				} else {
					WritableSheet invSheet = writeInvariants(prop, invariants);
					summarySheet.addHyperlink(new WritableHyperlink(1, summaryRow, "Valid",
							invSheet, 0, 0));
				}
				summarySheet.addCell(new Number(2, summaryRow, k));
				summarySheet.addCell(new Number(3, summaryRow, elapsed / 1000.0));
				summaryRow++;
			}
		} catch (WriteException e) {
			throw new JKindException("Error writing to Excel file", e);
		}
	}

	private WritableSheet writeInvariants(String prop, List<Invariant> invariants)
			throws WriteException {
		currSheet = workbook.createSheet(trimName(prop), numSheets++);
		currRow = 0;

		currSheet.addCell(new Label(0, currRow, "Invariants for " + prop, boldFormat));
		currRow += 2;

		for (Invariant invariant : invariants) {
			currSheet.addCell(new Label(0, currRow, invariant.toString()));
			currRow++;
		}

		autosize(currSheet, 1);
		return currSheet;
	}

	@Override
	public void writeInvalid(String prop, int k, Model model, long elapsed) {
		try {
			WritableSheet cexSheet = writeCounterexample(prop, k, BigInteger.ZERO, model);

			summarySheet.addCell(new Label(0, summaryRow, prop));
			summarySheet.addHyperlink(new WritableHyperlink(1, summaryRow, "Invalid", cexSheet, 0,
					0));
			summarySheet.addCell(new Number(2, summaryRow, k));
			summarySheet.addCell(new Number(3, summaryRow, elapsed / 1000.0));
			summaryRow++;
		} catch (WriteException e) {
			throw new JKindException("Error writing to Excel file", e);
		}
	}

	private WritableSheet writeCounterexample(String prop, int k, BigInteger offset, Model model)
			throws WriteException {
		currSheet = workbook.createSheet(trimName(prop), numSheets++);
		currRow = 0;
		autosize(currSheet, 1);

		// Print steps header
		currSheet.addCell(new Label(0, currRow, "Step", boldFormat));
		for (int col = 1; col <= k; col++) {
			currSheet.addCell(new Number(col, currRow, col - 1));
		}
		currRow++;

		writeSection("Inputs", inputs, k, offset, model);
		writeSection("Outputs", outputs, k, offset, model);
		writeSection("Locals", locals, k, offset, model);

		return currSheet;
	}

	private String trimName(String name) {
		if (name.length() <= 31) {
			return name;
		} else {
			return name.substring(0, 28) + "...";
		}
	}

	private int writeSection(String header, List<String> idList, int k, BigInteger offset,
			Model model) throws WriteException {
		currRow++;
		currSheet.addCell(new Label(0, currRow, header, boldFormat));
		currRow++;
		for (String input : getRelevantFunctions(model.getFunctions(), idList)) {
			writeSignal(input, k, offset, model);
			currRow++;
		}
		return currRow;
	}

	private SortedSet<String> getRelevantFunctions(Set<String> functions, List<String> idList) {
		SortedSet<String> result = new TreeSet<String>();
		for (String fn : getRelevantFunctions(functions)) {
			if (idList.contains(fn.substring(1))) {
				result.add(fn);
			}
		}
		return result;
	}

	private void writeSignal(String input, int k, BigInteger offset, Model model)
			throws WriteException {
		currSheet.addCell(new Label(0, currRow, input.substring(1)));
		Value prev = null;
		for (int i = 0; i < k; i++) {
			BigInteger key = BigInteger.valueOf(i).add(offset);
			Value curr = model.getFunctionValue(input, key);
			if (curr != null) {
				CellFormat format = curr.equals(prev) ? fadedFormat : defaultFormat;
				writeValue(curr, i + 1, format);
			}
			prev = curr;
		}
	}

	private void writeValue(Value value, int col, CellFormat format) throws WriteException {
		if (value instanceof BoolValue) {
			BoolValue bv = (BoolValue) value;
			currSheet.addCell(new Boolean(col, currRow, bv.getBool(), format));
		} else if (value instanceof NumericValue) {
			NumericValue nv = (NumericValue) value;
			currSheet.addCell(new Number(col, currRow, parseDouble(nv.toString()), format));
		} else {
			throw new JKindException("Unknown value type in Excel writer");
		}
	}

	private double parseDouble(String str) {
		String[] strs = str.split("/");
		if (strs.length > 2) {
			throw new JKindException("Unable to parse numeric value: " + str);
		}

		BigDecimal result = new BigDecimal(strs[0]);
		if (strs.length > 1) {
			result = result.divide(new BigDecimal(strs[1]));
		}

		return result.doubleValue();
	}

	@Override
	public void writeUnknown(List<String> props,
			Map<String, InductiveCounterexampleMessage> inductiveCounterexamples) {
		try {
			for (String prop : props) {
				InductiveCounterexampleMessage icm = inductiveCounterexamples.get(prop);
				summarySheet.addCell(new Label(0, summaryRow, prop));
				if (icm == null) {
					summarySheet.addCell(new Label(1, summaryRow, "Unknown"));
				} else {
					WritableSheet cexSheet = writeCounterexample(prop, icm.k, icm.n, icm.model);
					summarySheet.addHyperlink(new WritableHyperlink(1, summaryRow, "Unknown",
							cexSheet, 0, 0));
				}
				summaryRow++;
			}
		} catch (WriteException e) {
			throw new JKindException("Error writing to Excel file", e);
		}
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
}
