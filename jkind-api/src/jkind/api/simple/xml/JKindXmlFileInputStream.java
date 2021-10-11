package jkind.api.simple.xml;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * 
 * @deprecated
 *    To be reomved in 6.0.
 * 	  This class represents a transitional API to provide a basic, command-
 *    line oriented means of using JKind.  This functionality duplicates that
 *    of the jkind.api package but removes the dependencies on Eclipse.  Once
 *    the Eclipse-specific dependencies have been removed, this functionality
 *    will migrate to package jkind.api.
 */
@Deprecated
public class JKindXmlFileInputStream extends InputStream {
	private final File xmlFile;
	private InputStream stream;
	private volatile boolean done;
	private static final int POLL_INTERVAL = 100;

	public JKindXmlFileInputStream(File xmlFile) {
		this.xmlFile = xmlFile;
		this.stream = null;
		this.done = false;
	}

	@Override
	public int read() throws IOException {
		if (!getStream()) {
			return -1;
		}

		int c;
		try {
			while ((c = stream.read()) == -1 && !done) {
				Thread.sleep(POLL_INTERVAL);
			}
		} catch (InterruptedException e) {
			return -1;
		}

		return c;
	}

	private boolean getStream() {
		if (stream == null) {
			try {
				while (!xmlFile.exists() && !done) {
					Thread.sleep(POLL_INTERVAL);
				}
			} catch (InterruptedException e) {
				return false;
			}

			try {
				stream = new BufferedInputStream(new FileInputStream(xmlFile));
			} catch (FileNotFoundException e) {
				// File deleted before we could open it
				return false;
			}
		}

		return true;
	}

	public void done() {
		done = true;
	}

	@Override
	public void close() throws IOException {
		if (stream != null) {
			stream.close();
			stream = null;
		}
	}
}
