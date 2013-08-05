package jkind.api.examples;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jkind.api.JKindApi;
import jkind.api.results.DynamicJKindResult;
import jkind.api.ui.JKindTable;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
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

public class JKindRunExample {
	public static void main(String[] args) throws IOException {
		if (args.length < 1) {
			System.out.println("Must specify lustre file as argument");
			System.exit(-1);
		}

		run(args[0], parseProperties(args[0]));
	}

	private static List<String> parseProperties(String filename) throws IOException {
		/*
		 * It may seem strange that we have to extract the properties here, but
		 * the JKind API never actually parses the lustre file. It leaves that
		 * to the underlying analysis tool (JKind, Kind 2, etc). In a real world
		 * use of the JKind API, the programmer would probably already have a
		 * list of the property names. Thus, this parsing is just for the sake
		 * of this example.
		 */

		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(filename));
			List<String> properties = new ArrayList<>();
			Pattern pattern = Pattern.compile(".*--%PROPERTY +([a-zA-Z_0-9]*) *;.*");
			String line;

			while ((line = reader.readLine()) != null) {
				Matcher matcher = pattern.matcher(line);
				if (matcher.matches()) {
					properties.add(matcher.group(1));
				}
			}

			return properties;
		} finally {
			if (reader != null) {
				reader.close();
			}
		}
	}

	private static void run(String filename, List<String> properties) {
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setText("JKind Run Example");
		createControls(shell, filename, properties);

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

	private static void createControls(Composite parent, String filename, List<String> properties) {
		parent.setLayout(new GridLayout(2, true));
		JKindTable viewer = createJKindTable(parent);
		final Button startButton = createButton(parent, "Start");
		final Button cancelButton = createButton(parent, "Cancel");
		cancelButton.setEnabled(false);

		final File file = new File(filename);
		final DynamicJKindResult result = new DynamicJKindResult(properties);
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
						new JKindApi().execute(file, result, monitor);
					}
				}.start();
			}
		});

		cancelButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				cancelButton.setEnabled(false);

			}
		});
	}

	private static JKindTable createJKindTable(Composite parent) {
		JKindTable table = new JKindTable(parent);
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
