package jkind.api.examples;

import java.io.File;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;

import jkind.api.JKindApi;
import jkind.api.results.AnalysisResult;
import jkind.api.results.CompositeAnalysisResult;
import jkind.api.results.JKindResult;
import jkind.api.results.PropertyResult;
import jkind.api.results.Renaming;
import jkind.api.results.Status;
import jkind.api.ui.results.AnalysisResultTree;

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
 * This example illustrates how to dynamically report the results of multiple
 * JKind API executions. It also shows how a {@link Renaming} may be used to
 * change the display of properties and signals.
 */
public class ComplexUiExample {
	public static void main(String[] args) throws IOException {
		if (args.length < 1) {
			System.out.println("Must specify a directory or Lustre file as argument");
			System.exit(-1);
		}

		AnalysisResult result = buildAnalysisResult(new File(args[0]));
		run(result);
	}

	private static class WorkItem {
		private File file;
		private JKindResult result;

		public WorkItem(File file, JKindResult result) {
			this.file = file;
			this.result = result;
		}
	}

	private static Queue<WorkItem> queue = new ArrayDeque<>();
	private static Renaming renaming = new Renaming() {
		@Override
		public String rename(String original) {
			return original.replace("_", " ");
		}
	};

	private static AnalysisResult buildAnalysisResult(File file) throws IOException {
		if (file.isDirectory()) {
			CompositeAnalysisResult result = new CompositeAnalysisResult(file.getName());
			for (File subFile : file.listFiles()) {
				AnalysisResult subResult = buildAnalysisResult(subFile);
				if (subResult != null) {
					result.addChild(subResult);
				}
			}
			if (result.getChildren().isEmpty()) {
				return null;
			} else {
				return result;
			}
		} else if (file.getName().endsWith(".lus")) {
			List<String> properties = BasicUiExample.parseProperties(file);
			JKindResult result = new JKindResult(file.getName(), properties, renaming);
			queue.add(new WorkItem(file, result));
			return result;
		} else {
			return null;
		}
	}

	private static void run(AnalysisResult result) {
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setText("JKind Multiple Run Example");
		createControls(shell, result);

		shell.pack();
		Point size = shell.getSize();
		shell.setSize(size.x, 500);
		shell.open();

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		display.dispose();

		/*
		 * When the user clicks the close button, the background threads may
		 * still be running. Calling System.exit kills all threads.
		 */
		System.exit(0);
	}

	private static void createControls(final Shell parent, final AnalysisResult result) {
		parent.setLayout(new GridLayout(2, true));
		AnalysisResultTree tree = createTree(parent);
		final Button startButton = createButton(parent, "Start");
		final Button cancelButton = createButton(parent, "Cancel");
		cancelButton.setEnabled(false);

		/*
		 * The leaves of the AnalysisResult object will be populated by later
		 * JKindApi.execute calls. The JKindTree viewer listens for changes to
		 * the AnalysisResult and automatically updates itself as needed.
		 */
		tree.setInput(result);

		// The monitor is only currently used to detect cancellation
		final IProgressMonitor monitor = new NullProgressMonitor();

		startButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				startButton.setEnabled(false);
				cancelButton.setEnabled(true);

				/*
				 * The JKindApi execute methods run synchronously, thus they
				 * should usually be wrapped in a thread
				 */
				new Thread("Analysis") {
					@Override
					public void run() {
						while (!queue.isEmpty() && !monitor.isCanceled()) {
							WorkItem item = queue.remove();
							new JKindApi().execute(item.file, item.result, monitor);
						}

						while (!queue.isEmpty()) {
							WorkItem item = queue.remove();
							item.result.cancel();
						}
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

		tree.getViewer().addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				if (event.getSelection() instanceof IStructuredSelection) {
					IStructuredSelection sel = (IStructuredSelection) event.getSelection();
					if (!sel.isEmpty() && sel.getFirstElement() instanceof PropertyResult) {
						BasicUiExample.click(parent, (PropertyResult) sel.getFirstElement());
					} else if (!sel.isEmpty() && sel.getFirstElement() instanceof JKindResult) {
						click(parent, (JKindResult) sel.getFirstElement());
					}
				}
			}
		});
	}

	public static void click(Shell parent, JKindResult result) {
		if (result.getMultiStatus().getOverallStatus() != Status.WAITING) {
			try {
				File file = File.createTempFile("cex-", ".xls");
				result.toExcel(file);
				Program.launch(file.toString());
			} catch (Throwable t) {
				MessageDialog.openError(parent, "Error opening Excel file", t.getMessage());
			}
		}
	}

	private static AnalysisResultTree createTree(Composite parent) {
		/*
		 * AnalysisResultTree knows how to format itself. The code here is just
		 * to position the table within its parent.
		 */
		AnalysisResultTree tree = new AnalysisResultTree(parent);
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.verticalAlignment = GridData.FILL;
		gridData.grabExcessVerticalSpace = true;
		gridData.horizontalSpan = 2;
		tree.setLayoutData(gridData);
		return tree;
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
