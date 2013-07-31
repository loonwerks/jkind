package jkind.lustre;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import jkind.JKindException;

public class FilePrettyPrintVisitor extends PrettyPrintVisitor {
	private BufferedWriter writer;

	public FilePrettyPrintVisitor(File file) throws IOException {
		writer = new BufferedWriter(new FileWriter(file));
	}
	
	public void close() throws IOException {
		if (writer != null) {
			writer.close();
		}
	}

	protected void write(Object o) {
		try {
			writer.append(o.toString());
		} catch (IOException e) {
			throw new JKindException("Unable to write to file", e);
		}
	}
}