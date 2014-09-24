package jkind.api.examples;

import java.io.File;

import jkind.api.JKindApi;
import jkind.api.results.JKindResult;
import jkind.api.results.PropertyResult;
import jkind.api.ui.counterexample.CounterexampleTreeViewer;
import jkind.results.Counterexample;
import jkind.results.InvalidProperty;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class CounterexampleTreeViewerExample {
	public static void main(String[] args) {
		if (args.length < 1) {
			System.out.println("Must specify lustre file as argument");
			System.exit(-1);
		}

		JKindResult result = runJKind(new File(args[0]));

		for (PropertyResult pr : result.getPropertyResults()) {
			if (pr.getProperty() instanceof InvalidProperty) {
				InvalidProperty ip = (InvalidProperty) pr.getProperty();
				display(ip.getCounterexample());
			}
		}
	}

	private static void display(Counterexample cex) {
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setSize(800, 600);
		shell.setLayout(new FillLayout());

		CounterexampleTreeViewer viewer = new CounterexampleTreeViewer(shell);
		viewer.setInput(cex, LayoutExample.getLayout());

		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}

	private static JKindResult runJKind(File file) {
		JKindApi api = new JKindApi();
		JKindResult result = new JKindResult("");
		api.execute(file, result, new NullProgressMonitor());
		return result;
	}
}
