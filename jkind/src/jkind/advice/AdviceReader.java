package jkind.advice;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;

import jkind.ExitCodes;
import jkind.StdErr;

public class AdviceReader {
	public static Advice read(String inputFilename) {
		try (InputStream in = new GZIPInputStream(new FileInputStream(inputFilename))) {
			return AdviceEncoder.decode(in);
		} catch (Exception e) {
			StdErr.fatal(ExitCodes.INVALID_OPTIONS, "unable to parse advice file: " + e.getMessage());
			throw new IllegalStateException();
		}
	}
}
