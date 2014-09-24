package jkind.api.examples;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import jkind.api.JKindApi;
import jkind.api.results.JKindResult;
import jkind.results.layout.Layout;

import org.eclipse.core.runtime.NullProgressMonitor;

/**
 * This example illustrates how to use a Layout to format counterexamples in the
 * Excel output
 */
public class LayoutExample {
	public static void main(String[] args) {
		if (args.length < 1) {
			System.out.println("Must specify Lustre file as argument");
			System.exit(-1);
		}

		File file = new File(args[0]);
		JKindResult result = new JKindResult(file.getName());
		NullProgressMonitor monitor = new NullProgressMonitor();
		new JKindApi().execute(file, result, monitor);

		File xlsFile = new File(args[0] + ".xls");
		result.toExcel(xlsFile, getLayout());
		System.out.println("Details written to " + xlsFile);
	}

	/*
	 * For demonstration purposes we group signals based on the first letter of
	 * their name. Note that variables with leading underscores will be dropped
	 * since getCategory() on them will return a value that does not appear in
	 * the results of getCategories().
	 */
	public static Layout getLayout() {
		Layout layout = new Layout() {
			@Override
			public String getCategory(String signal) {
				return signal.substring(0, 1).toUpperCase();
			}

			@Override
			public List<String> getCategories() {
				List<String> categories = new ArrayList<>();
				for (char c = 'A'; c <= 'Z'; c++) {
					categories.add(Character.toString(c));
				}
				return categories;
			}
		};
		return layout;
	}
}
