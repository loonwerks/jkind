package jkind.translation;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jkind.lustre.EnumType;
import jkind.lustre.Equation;
import jkind.lustre.Node;
import jkind.lustre.VarDecl;
import jkind.util.Util;
import jxl.CellView;
import jxl.JXLException;
import jxl.Workbook;
import jxl.format.CellFormat;
import jxl.write.Formula;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class Node2Excel {
	public static final int LENGTH = 30;

	private final Map<String, Integer> rowAssignments = new HashMap<>();
	private final Map<String, String> intToEnum = new HashMap<>();
	private final Map<String, String> enumToInt = new HashMap<>();
	private int row = 0;
	private int enumerationsStartRow;

	public void convert(Node main, String filename) throws JXLException, IOException {
		WritableWorkbook workbook = Workbook.createWorkbook(new File(filename));
		WritableSheet sheet = workbook.createSheet("Lustre", 0);

		// Print steps header
		CellFormat boldFormat = getBoldFormat();
		sheet.addCell(new Label(0, row, "Step", boldFormat));
		for (int col = 1; col <= LENGTH; col++) {
			sheet.addCell(new Number(col, row, col - 1));
		}

		// Calculate locations and fill in enumerations
		row += 2;
		createRowAssignments(main);
		row += 1;
		createEnumerations(sheet, main);

		// Print headers
		List<String> rows = Util.getIds(Util.getVarDecls(main));
		for (String row : rows) {
			sheet.addCell(new Label(0, rowAssignments.get(row), row, boldFormat));
		}

		// Print equations for locals and outputs
		for (Equation eq : main.equations) {
			String id = eq.lhs.get(0).id;
			int row = rowAssignments.get(id);
			for (int col = 1; col <= LENGTH; col++) {
				Expr2FormulaVisitor visitor = new Expr2FormulaVisitor(id, col, rowAssignments, intToEnum, enumToInt);
				eq.expr.accept(visitor);
				String formula = visitor.toString();
				if (!formula.isEmpty()) {
					sheet.addCell(new Formula(col, row, formula));
				}
			}
		}

		// Hide generated locals
		for (VarDecl local : main.locals) {
			if (local.id.contains("~")) {
				hideRow(sheet, rowAssignments.get(local.id));
			}
		}

		// Hide enumerations
		for (int i = enumerationsStartRow; i < row; i++) {
			hideRow(sheet, i);
		}

		workbook.write();
		workbook.close();
	}

	private void hideRow(WritableSheet sheet, int i) throws RowsExceededException {
		CellView rowView = sheet.getRowView(i);
		rowView.setHidden(true);
		sheet.setRowView(i, rowView);
	}

	/**
	 * The enumerated type { A, B, C, D } will be represented by
	 * 
	 * <pre>
	 *   0  1  2  3
	 *   A  B  C  D
	 *   0  1  2  3
	 * </pre>
	 * 
	 * This allows us to do a hlookup on rows 1 & 2 to convert integers to
	 * enumerated values, and a hlookup on rows 2 & 3 to convert enumerated
	 * values to integers.
	 * 
	 * Note: The index and match functions do not work in JExcelApi. Nor do
	 * array literals. Otherwise, this conversion would be much easier.
	 */
	private void createEnumerations(WritableSheet sheet, Node main) throws JXLException {
		Map<EnumType, String> intToEnumCache = new HashMap<>();
		Map<EnumType, String> enumToIntCache = new HashMap<>();

		enumerationsStartRow = row;
		for (VarDecl def : Util.getVarDecls(main)) {
			if (def.type instanceof EnumType) {
				EnumType et = (EnumType) def.type;
				if (intToEnumCache.containsKey(et)) {
					intToEnum.put(def.id, intToEnumCache.get(et));
					enumToInt.put(def.id, enumToIntCache.get(et));
				} else {
					for (int i = 0; i < et.values.size(); i++) {
						sheet.addCell(new Number(i, row, i));
						sheet.addCell(new Label(i, row + 1, et.values.get(i)));
						sheet.addCell(new Number(i, row + 2, i));
					}
					String i2e = "$A$" + (row + 1) + ":$ZZ$" + (row + 2);
					String e2i = "$A$" + (row + 2) + ":$ZZ$" + (row + 3);
					intToEnum.put(def.id, i2e);
					enumToInt.put(def.id, e2i);
					intToEnumCache.put(et, i2e);
					enumToIntCache.put(et, e2i);
					row += 4;
				}
			}
		}

	}

	private static CellFormat getBoldFormat() throws WriteException {
		WritableCellFormat boldFormat = new WritableCellFormat();
		WritableFont font = new WritableFont(boldFormat.getFont());
		font.setBoldStyle(WritableFont.BOLD);
		boldFormat.setFont(font);
		return boldFormat;
	}

	private void createRowAssignments(Node main) {
		for (VarDecl input : main.inputs) {
			rowAssignments.put(input.id, row++);
		}

		row++;

		for (VarDecl local : main.locals) {
			rowAssignments.put(local.id, row++);
		}

		row++;

		for (VarDecl output : main.outputs) {
			rowAssignments.put(output.id, row++);
		}
	}
}
