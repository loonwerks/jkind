package jkind.api.examples;

import java.io.File;

import org.eclipse.core.runtime.NullProgressMonitor;

import jkind.api.JKindApi;
import jkind.api.results.JKindResult;
import jkind.api.results.PropertyResult;

/**
 * This example illustrates how to call the JKind API and process the results
 */
public class CommandLineExample {
	public static void main(String[] args) {
		if (args.length < 1) {
			System.out.println("Must specify Lustre file as argument");
			System.exit(-1);
		}

		/*
		 * We take a Lustre file as input, though in practice programmers will
		 * often construct their Lustre programmatically.
		 */
		File file = new File(args[0]);

		/*
		 * The results of JKind API execution will be stored in here. A
		 * programmer may attach listeners to this object to dynamically respond
		 * to results.
		 */
		JKindResult result = new JKindResult(file.getName());

		/*
		 * The progress monitor is used to cancel execution if needed. We will
		 * not use that feature here.
		 */
		NullProgressMonitor monitor = new NullProgressMonitor();

		/*
		 * This triggers the actual execution of analysis. Options can be set on
		 * the JKindApi object if desired.
		 */
		new JKindApi().execute(file, result, monitor);

		/*
		 * Process some of the results
		 */
		for (PropertyResult pr : result.getPropertyResults()) {
			System.out.println(pr.getName() + " - " + pr.getStatus());
		}

		/*
		 * Dumps the results to an Excel file
		 */
		File xlsFile = new File(args[0] + ".xls");
		result.toExcel(xlsFile);
		System.out.println("Complete results written to " + xlsFile);
	}
}
