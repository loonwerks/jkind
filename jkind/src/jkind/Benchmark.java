package jkind;

import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.lang.ProcessBuilder.Redirect;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import jkind.util.Util;

public class Benchmark {
	private static final File LOG = new File("results.log");

	private static int N = 1;

	public static void main(String args[]) {
		try {
			LOG.delete();
	
			if (args.length >= 2 && "-N".equals(args[0])) {
				N = Integer.parseInt(args[1]);
				args = Arrays.copyOfRange(args, 2, args.length);
			}
			
			Output.println("Arguments: " + join(args));
			Output.println();
			Output.println("File, Runtime");
			for (String file : getFiles()) {
				Output.printf("%s, %.1f", file, getMedianRuntime(args, file));
				Output.println();
			}
		} catch (Throwable t) {
			t.printStackTrace();
			System.exit(ExitCodes.UNCAUGHT_EXCEPTION);
		}
	}

	private static String[] getFiles() {
		return new File(".").list(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".lus");
			}
		});
	}

	private static String join(String[] strings) {
		StringBuilder buf = new StringBuilder();
		for (String string : strings) {
			buf.append(string);
			buf.append(" ");
		}
		return buf.toString();
	}

	private static double getMedianRuntime(String[] fixedArgs, String file) throws Exception {
		List<String> args = new ArrayList<>();
		if (Util.isWindows()) {
			args.add("cmd");
			args.add("/c");
		}
		args.add("jkind");
		args.addAll(Arrays.asList(fixedArgs));
		args.add(file);

		List<Double> results = new ArrayList<>();
		for (int i = 0; i < N; i++) {
			results.add(getRuntime(args));
		}

		return median(results);
	}

	private static double getRuntime(List<String> args) throws Exception {
		ProcessBuilder pb = new ProcessBuilder(args);
		pb.redirectErrorStream(true);
		pb.redirectOutput(Redirect.appendTo(LOG));
		
		try (FileWriter out = new FileWriter(LOG, true)) {
			out.write(args.toString());
			out.write(System.lineSeparator());
			out.close();
		}

		long start = System.nanoTime();
		Process process = pb.start();
		process.waitFor();
		long stop = System.nanoTime();

		return (stop - start) / 1000.0 / 1000.0 / 1000.0;
	}

	private static double median(List<Double> results) {
		Collections.sort(results);
		int n = results.size();
		if (n % 2 == 1) {
			return results.get(n / 2);
		} else {
			return (results.get(n / 2) + results.get(n / 2 - 1)) / 2;
		}
	}
}
