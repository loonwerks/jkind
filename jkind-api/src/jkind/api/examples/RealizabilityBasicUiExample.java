package jkind.api.examples;

import java.io.File;

import jkind.api.JRealizabilityApi;
import jkind.api.results.JRealizabilityResult;
import jkind.api.results.PropertyResult;
import jkind.api.results.Status;
import jkind.api.ui.results.AnalysisResultTable;
import jkind.results.Counterexample;
import jkind.results.InvalidProperty;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.program.Program;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * This example illustrates how to dynamically report the results of a JKind API
 * execution.
 */
public class RealizabilityBasicUiExample {
	public static void main(String[] args) {
		if (args.length < 1) {
			System.out.println("Must specify lustre file as argument");
			System.exit(-1);
		}

		run(new File(args[0]));
	}

	private static void run(File file) {
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setText("JRealizability Run Example");
		createControls(shell, file);

		shell.pack();
		Point size = shell.getSize();
		shell.setSize(size.x, Math.min(size.y, 500));
		shell.open();

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		display.dispose();

		System.exit(0);
	}

	private static void createControls(final Shell parent, final File file) {
		parent.setLayout(new GridLayout(2, true));
		AnalysisResultTable viewer = createTable(parent);
		final Button startButton = createButton(parent, "Start");
		final Button cancelButton = createButton(parent, "Cancel");
		cancelButton.setEnabled(false);

		final JRealizabilityResult result = new JRealizabilityResult(file.getName() + " realizable");
		viewer.setInput(result);
		final IProgressMonitor monitor = new NullProgressMonitor();

		startButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				startButton.setEnabled(false);
				cancelButton.setEnabled(true);

				new Thread("Analysis") {
					@Override
					public void run() {
						new JRealizabilityApi().execute(file, result, monitor);
					}
				}.start();
			}
		});

		cancelButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				cancelButton.setEnabled(false);
				monitor.setCanceled(true);
			}
		});

		viewer.getViewer().addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				if (event.getSelection() instanceof IStructuredSelection) {
					IStructuredSelection sel = (IStructuredSelection) event.getSelection();
					if (!sel.isEmpty()) {
						click(parent, (PropertyResult) sel.getFirstElement());
					}
				}
			}
		});
	}

	public static void click(Shell parent, PropertyResult pr) {
		if (pr.getStatus() == Status.INVALID) {
			InvalidProperty ip = (InvalidProperty) pr.getProperty();
			Counterexample cex = ip.getCounterexample();
			try {
				File file = File.createTempFile("cex-", ".xls");
				cex.toExcel(file);
				Program.launch(file.toString());
			} catch (Throwable t) {
				MessageDialog.openError(parent, "Error opening Excel file", t.getMessage());
			}
		}
	}

	private static AnalysisResultTable createTable(Composite parent) {
		/*
		 * AnalysisResultTable knows how to format itself. The code here is just
		 * to position the table within its parent.
		 */
		AnalysisResultTable table = new AnalysisResultTable(parent);
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.verticalAlignment = GridData.FILL;
		gridData.grabExcessVerticalSpace = true;
		gridData.horizontalSpan = 2;
		table.setLayoutData(gridData);
		return table;
	}

	private static Button createButton(Composite parent, String text) {
		Button button = new Button(parent, SWT.None);
		button.setText(text);
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.CENTER;
		button.setLayoutData(gridData);
		return button;
	}
}
