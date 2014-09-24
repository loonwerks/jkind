package jkind.api.ui.results;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import jkind.JKindException;

import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.widgets.Display;

public class Spinner {
	private final List<Image> frames = new ArrayList<>();
	private final List<Integer> delays = new ArrayList<>();
	private int current = 0;
	private Thread thread;

	public Spinner(String filename, final ColumnViewer viewer) {
		ImageLoader loader = new ImageLoader();
		try (InputStream stream = AnalysisResultLabelProvider.class.getResourceAsStream(filename)) {
			if (stream == null) {
				throw new JKindException("Unable to find resource: " + filename);
			}

			loader.load(stream);
			for (ImageData data : loader.data) {
				frames.add(new Image(null, data));
				delays.add(data.delayTime * 10);
			}
		} catch (IOException e) {
		}

		thread = new Thread("Spinner") {
			@Override
			public void run() {
				try {
					while (!viewer.getControl().isDisposed()) {
						Thread.sleep(delays.get(current));
						current = (current + 1) % frames.size();
						Display.getDefault().asyncExec(new Runnable() {
							@Override
							public void run() {
								if (!viewer.getControl().isDisposed()) {
									viewer.refresh();
								}
							}
						});
					}
				} catch (InterruptedException e) {
				}
			}
		};
		thread.start();
	}

	public Image getFrame() {
		return frames.get(current);
	}

	public void dispose() {
		if (thread != null) {
			thread.interrupt();
			thread = null;
		}
		for (Image image : frames) {
			image.dispose();
		}
	}
}
