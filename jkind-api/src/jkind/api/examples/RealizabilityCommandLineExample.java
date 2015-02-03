package jkind.api.examples;

import java.io.File;

import jkind.api.JRealizabilityApi;
import jkind.api.results.JRealizabilityResult;
import jkind.api.results.PropertyResult;
import jkind.results.InvalidProperty;

import org.eclipse.core.runtime.NullProgressMonitor;

/**
 * This example illustrates how to call the JRealizability API and process the
 * results
 * 
 * @see CommandLineExample
 */
public class RealizabilityCommandLineExample {
	public static void main(String[] args) {
		if (args.length < 1) {
			System.out.println("Must specify Lustre file as argument");
			System.exit(-1);
		}

		File file = new File(args[0]);
		JRealizabilityResult result = new JRealizabilityResult(file.getName() + " realizable");
		NullProgressMonitor monitor = new NullProgressMonitor();
		new JRealizabilityApi().execute(file, result, monitor);

		PropertyResult pr = result.getPropertyResult();
		System.out.println(result.getName() + " - " + pr.getStatus());

		if (pr.getProperty() instanceof InvalidProperty) {
			InvalidProperty ip = (InvalidProperty) pr.getProperty();
			File xlsFile = new File(args[0] + ".xls");
			ip.getCounterexample().toExcel(xlsFile);
			System.out.println("Counterexample written to " + xlsFile);
		}
	}
}
