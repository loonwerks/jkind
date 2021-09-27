package jkind.api.simple.xml;

import java.io.IOException;
import java.io.InputStream;

import jkind.JKindException;

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
public class LineInputStream implements AutoCloseable {
	private final InputStream source;

	public LineInputStream(InputStream source) {
		this.source = source;
	}

	public String readLine() throws IOException {
		StringBuilder buffer = new StringBuilder();

		int c;
		while ((c = source.read()) != -1) {
			buffer.append((char) c);
			if (c == '\n') {
				return buffer.toString();
			}
		}

		if (buffer.length() == 0) {
			source.close();
			return null;
		} else {
			return buffer.toString();
		}
	}

	@Override
	public void close() {
		try {
			source.close();
		} catch (IOException e) {
			throw new JKindException("Error closing input stream", e);
		}
	}
}
