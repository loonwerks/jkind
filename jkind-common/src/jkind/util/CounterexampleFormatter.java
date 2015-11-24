package jkind.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import jkind.lustre.values.RealValue;
import jkind.lustre.values.Value;
import jkind.results.Counterexample;
import jkind.results.Signal;
import jkind.results.layout.Layout;

public class CounterexampleFormatter {
	private static final int DECIMAL_DIGITS = 3;
	private static final String TRUNCATE_SUFFIX = "*";
	private static final List<String> SEPARATOR = Collections.emptyList();
	private static final String NEWLINE = System.lineSeparator();

	private final Counterexample cex;
	private final Layout layout;
	private boolean truncated = false;

	public CounterexampleFormatter(Counterexample cex, Layout layout) {
		this.cex = cex;
		this.layout = layout;
	}

	@Override
	public String toString() {
		return formatContent(getContent()) + footer();
	}

	private String footer() {
		if (truncated) {
			return NEWLINE + " * display value has been truncated" + NEWLINE;
		} else {
			return "";
		}
	}

	private List<List<String>> getContent() {
		List<List<String>> content = new ArrayList<>();

		content.add(getHeader());
		content.add(getStepCountsRow());

		for (String category : layout.getCategories()) {
			List<Signal<Value>> signals = cex.getCategorySignals(layout, category);
			content.addAll(getSectionRows(category, signals));
		}

		return content;
	}

	private List<String> getHeader() {
		ArrayList<String> header = new ArrayList<>();
		header.add("");
		header.add("Step");
		return header;
	}

	private List<String> getStepCountsRow() {
		List<String> row = new ArrayList<>();
		row.add("variable");
		for (int i = 0; i < cex.getLength(); i++) {
			row.add(Integer.toString(i));
		}
		return row;
	}

	private List<List<String>> getSectionRows(String category, List<Signal<Value>> signals) {
		List<List<String>> rows = new ArrayList<>();
		if (!signals.isEmpty()) {
			rows.add(SEPARATOR);
			rows.add(Collections.singletonList(category.toUpperCase()));
			for (Signal<Value> signal : signals) {
				rows.add(getSignalRow(signal));
			}
		}
		return rows;
	}

	private List<String> getSignalRow(Signal<Value> signal) {
		List<String> row = new ArrayList<>();
		row.add(signal.getName());
		for (int i = 0; i < cex.getLength(); i++) {
			row.add(getString(signal.getValue(i)));
		}
		return row;
	}

	private String getString(Value value) {
		if (Util.isArbitrary(value)) {
			return "-";
		} else if (value instanceof RealValue) {
			RealValue rv = (RealValue) value;
			String text = rv.value.toTruncatedDecimal(DECIMAL_DIGITS, TRUNCATE_SUFFIX);
			if (text.contains(TRUNCATE_SUFFIX)) {
				truncated = true;
			}
			return text;
		} else {
			return value.toString();
		}
	}

	private String formatContent(List<List<String>> content) {
		List<Integer> minColumnWidths = computeMinimumColumnWidths(content);

		StringBuilder text = new StringBuilder();
		for (int row = 0; row < content.size(); row++) {
			List<String> rowContent = content.get(row);
			for (int col = 0; col < rowContent.size(); col++) {
				String cell = rowContent.get(col);
				String format = getFormatString(col, minColumnWidths.get(col));
				text.append(String.format(format, cell));
			}
			text.append(NEWLINE);
		}

		return text.toString();
	}

	private String getFormatString(int col, int minWidth) {
		if (col == 0) {
			int width = minWidth + 6;
			return "%-" + width + "s ";
		} else {
			int width = minWidth;
			return "%" + width + "s ";
		}

	}

	private List<Integer> computeMinimumColumnWidths(List<List<String>> content) {
		List<Integer> result = new ArrayList<>();
		for (List<String> row : content) {
			for (int i = 0; i < row.size(); i++) {
				expandToFitElement(result, i);
				int curr = result.get(i);
				int next = Math.max(row.get(i).length(), curr);
				result.set(i, next);
			}
		}
		return result;
	}

	private void expandToFitElement(List<Integer> result, int i) {
		while (result.size() - 1 < i) {
			result.add(0);
		}
	}
}
