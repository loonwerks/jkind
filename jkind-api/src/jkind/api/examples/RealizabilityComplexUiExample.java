package jkind.api.examples;

import java.io.File;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Queue;

import jkind.api.JRealizabilityApi;
import jkind.api.results.AnalysisResult;
import jkind.api.results.CompositeAnalysisResult;
import jkind.api.results.JRealizabilityResult;
import jkind.api.ui.results.AnalysisResultTree;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * This example illustrates how to dynamically report the results of multiple
 * JRealizability API executions.
 */
public class RealizabilityComplexUiExample {
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
		private JRealizabilityResult result;

		public WorkItem(File file, JRealizabilityResult result) {
			this.file = file;
			this.result = result;
		}
	}

	private static Queue<WorkItem> queue = new ArrayDeque<>();

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
			JRealizabilityResult result = new JRealizabilityResult(file.getName() + " realizable");
			queue.add(new WorkItem(file, result));
			return result;
		} else {
			return null;
		}
	}

	private static void run(AnalysisResult result) {
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setText("JRealizability Multiple Run Example");
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
		 * JRealizableApi.execute calls. The JKindTree viewer listens for
		 * changes to the AnalysisResult and automatically updates itself as
		 * needed.
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
				 * The JRealizabilityApi execute methods run synchronously, thus
				 * they should usually be wrapped in a thread
				 */
				new Thread("Analysis") {
					@Override
					public void run() {
						while (!queue.isEmpty() && !monitor.isCanceled()) {
							WorkItem item = queue.remove();
							new JRealizabilityApi().execute(item.file, item.result, monitor);
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
					if (!sel.isEmpty() && sel.getFirstElement() instanceof JRealizabilityResult) {
						JRealizabilityResult result = (JRealizabilityResult) sel.getFirstElement();
						RealizabilityBasicUiExample.click(parent, result.getPropertyResult());
					}
				}
			}
		});
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
