package jkind.excel;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import jkind.JKindException;
import jkind.results.Counterexample;
import jkind.results.InconsistentProperty;
import jkind.results.InvalidProperty;
import jkind.results.Property;
import jkind.results.UnknownProperty;
import jkind.results.ValidProperty;
import jkind.results.layout.Layout;
import jkind.util.Util;
import jxl.Workbook;
import jxl.format.CellFormat;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableHyperlink;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

public class ExcelFormatter implements Closeable {
	private WritableWorkbook workbook;

	private WritableSheet summarySheet;
	private int summaryRow;

	private WritableSheet currSheet;
	private int currRow;

	/*
	 * CellFormats cannot be static, since JXL has strange results when a cell
	 * format is reused in another workbook. See {@link
	 * http://jexcelapi.sourceforge.net/resources/faq/}.
	 */
	private final CellFormat boldFormat = ExcelUtil.getBoldFormat();
	private final ExcelCounterexampleFormatter cexFormatter;

	public ExcelFormatter(File file, Layout layout) {
		try {
			workbook = Workbook.createWorkbook(file);

			summarySheet = workbook.createSheet("Summary", workbook.getNumberOfSheets());
			ExcelUtil.autosize(summarySheet, 4);
			summarySheet.addCell(new Label(0, 0, "Property", boldFormat));
			summarySheet.addCell(new Label(1, 0, "Result", boldFormat));
			summarySheet.addCell(new Label(2, 0, "Source", boldFormat));
			summarySheet.addCell(new Label(3, 0, "K", boldFormat));
			summarySheet.addCell(new Label(4, 0, "Runtime", boldFormat));
			summarySheet.addCell(new Label(5, 0, "True For", boldFormat));
			summaryRow = 1;
		} catch (WriteException | IOException e) {
			throw new JKindException("Error writing to Excel file", e);
		}

		cexFormatter = new ExcelCounterexampleFormatter(workbook, layout);
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

	public void write(List<Property> properties) {
		try {
			for (Property property : properties) {
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
		} else if (property instanceof InconsistentProperty) {
			write((InconsistentProperty) property);
		} else {
			throw new IllegalArgumentException("Unknown property type: "
					+ property.getClass().getSimpleName());
		}
	}

	private void write(ValidProperty property) throws WriteException {
		String name = property.getName();
		String source = property.getSource();
		int k = property.getK();
		List<String> invariants = property.getInvariants();
		Set<String> ivc = property.getIvc();
		double runtime = property.getRuntime();

		summarySheet.addCell(new Label(0, summaryRow, name));
		if (invariants.isEmpty() && ivc.isEmpty()) {
			summarySheet.addCell(new Label(1, summaryRow, "Valid"));
		} else {
			WritableSheet validSheet = writeValidSheet(name, invariants, ivc);
			WritableHyperlink link = new WritableHyperlink(1, summaryRow, "Valid", validSheet, 0, 0);
			summarySheet.addHyperlink(link);
		}

		summarySheet.addCell(new Label(2, summaryRow, source));
		summarySheet.addCell(new Number(3, summaryRow, k));
		summarySheet.addCell(new Number(4, summaryRow, runtime));
		summaryRow++;
	}

	private WritableSheet writeValidSheet(String property, List<String> invariants, Set<String> ivc)
			throws WriteException {
		currSheet = workbook
				.createSheet(ExcelUtil.trimName(property), workbook.getNumberOfSheets());
		currRow = 0;

		if (!invariants.isEmpty()) {
			currSheet.addCell(new Label(0, currRow, "Invariants", boldFormat));
			currRow++;
			for (String invariant : Util.safeStringSortedSet(invariants)) {
				currSheet.addCell(new Label(0, currRow, invariant));
				currRow++;
			}
			currRow++;
		}

		if (!ivc.isEmpty()) {
			currSheet.addCell(new Label(0, currRow, "Inductive Validity Core", boldFormat));
			currRow++;
			for (String e : ivc) {
				currSheet.addCell(new Label(0, currRow, e));
				currRow++;
			}
		}

		ExcelUtil.autosize(currSheet, 1);
		return currSheet;
	}

	private void write(InvalidProperty property) throws WriteException {
		String name = property.getName();
		String source = property.getSource();
		Counterexample cex = property.getCounterexample();
		int length = cex.getLength();
		double runtime = property.getRuntime();
		List<String> conflicts = property.getConflicts();

		WritableSheet cexSheet = writeCounterexample(name, cex, conflicts);
		summarySheet.addCell(new Label(0, summaryRow, name));
		summarySheet.addHyperlink(new WritableHyperlink(1, summaryRow, "Invalid", cexSheet, 0, 0));
		summarySheet.addCell(new Label(2, summaryRow, source));
		summarySheet.addCell(new Number(3, summaryRow, length));
		summarySheet.addCell(new Number(4, summaryRow, runtime));
		summaryRow++;
	}

	private void write(UnknownProperty property) throws WriteException {
		String name = property.getName();
		int trueFor = property.getTrueFor();
		Counterexample cex = property.getInductiveCounterexample();
		double runtime = property.getRuntime();

		summarySheet.addCell(new Label(0, summaryRow, name));
		if (cex == null) {
			summarySheet.addCell(new Label(1, summaryRow, "Unknown"));
		} else {
			WritableSheet cexSheet = writeCounterexample(name, cex, Collections.emptyList());
			summarySheet.addHyperlink(new WritableHyperlink(1, summaryRow, "Unknown", cexSheet, 0,
					0));
		}
		summarySheet.addCell(new Number(4, summaryRow, runtime));
		summarySheet.addCell(new Number(5, summaryRow, trueFor));
		summaryRow++;
	}

	private WritableSheet writeCounterexample(String name, Counterexample cex,
			List<String> conflicts) {
		return cexFormatter.writeCounterexample(name, cex, conflicts);
	}

	private void write(InconsistentProperty property) throws WriteException {
		String name = property.getName();
		String source = property.getSource();
		int k = property.getK();
		double runtime = property.getRuntime();

		summarySheet.addCell(new Label(0, summaryRow, name));
		summarySheet.addCell(new Label(1, summaryRow, "Inconsistent"));
		summarySheet.addCell(new Label(2, summaryRow, source));
		summarySheet.addCell(new Number(3, summaryRow, k));
		summarySheet.addCell(new Number(4, summaryRow, runtime));
		summaryRow++;
	}
}
