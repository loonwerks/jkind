package jkind.translation;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

public class Node2Excel {
	public static final int LENGTH = 30;
	
	public static void convert(Node main, String filename) throws JXLException, IOException {
		WritableWorkbook workbook = Workbook.createWorkbook(new File(filename));
		WritableSheet sheet = workbook.createSheet("Lustre", 0);

		// Print steps header
		CellFormat boldFormat = getBoldFormat();
		sheet.addCell(new Label(0, 0, "Step", boldFormat));
		for (int col = 1; col <= LENGTH; col++) {
			sheet.addCell(new Number(col, 0, col - 1));
		}

		// Print headers
		List<String> rows = Util.getIds(Util.getVarDecls(main));
		Map<String, Integer> rowAssignments = createRowAssignments(main, 2);
		for (String row : rows) {
			sheet.addCell(new Label(0, rowAssignments.get(row), row, boldFormat));
		}

		// Print equations for locals and outputs
		for (Equation eq : main.equations) {
			int row = rowAssignments.get(eq.lhs.get(0).id);
			for (int col = 1; col <= LENGTH; col++) {
				Expr2FormulaVisitor visitor = new Expr2FormulaVisitor(col, rowAssignments);
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
				int row = rowAssignments.get(local.id);
				CellView rowView = sheet.getRowView(row);
				rowView.setHidden(true);
				sheet.setRowView(row, rowView);
			}
		}

		workbook.write();
		workbook.close();
	}

	private static CellFormat getBoldFormat() throws WriteException {
		WritableCellFormat boldFormat = new WritableCellFormat();
		WritableFont font = new WritableFont(boldFormat.getFont());
		font.setBoldStyle(WritableFont.BOLD);
		boldFormat.setFont(font);
		return boldFormat;
	}

	private static Map<String, Integer> createRowAssignments(Node main, int start) {
		Map<String, Integer> rowAssignments = new HashMap<>();
		int i = start;

		for (VarDecl input : main.inputs) {
			rowAssignments.put(input.id, i++);
		}

		i++;

		for (VarDecl local : main.locals) {
			rowAssignments.put(local.id, i++);
		}

		i++;

		for (VarDecl output : main.outputs) {
			rowAssignments.put(output.id, i++);
		}

		return rowAssignments;
	}
}
