package jkind.test;

import java.io.File;
import java.io.FilenameFilter;
import java.lang.ProcessBuilder.Redirect;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Benchmark {
	private static final File LOG = new File("results.log");

	private static int N = 3;

	public static void main(String args[]) throws Exception {
		LOG.delete();

		System.out.println("Arguments: " + join(args));
		System.out.println();
		System.out.println("File, Runtime");
		for (String file : getFiles()) {
			System.out.printf("%s, %.1f", file, getMedianRuntime(args, file));
			System.out.println();
		}
	}

	private static String[] getFiles() {
		return new File(".").list(new FilenameFilter() {
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
		List<String> args = new ArrayList<String>();
		if (System.getProperty("os.name").startsWith("Windows")) {
			args.add("cmd");
			args.add("/c");
		}
		args.add("jkind");
		args.addAll(Arrays.asList(fixedArgs));
		args.add(file);

		List<Double> results = new ArrayList<Double>();
		for (int i = 0; i < N; i++) {
			results.add(getRuntime(args));
		}

		return median(results);
	}

	private static double getRuntime(List<String> args) throws Exception {
		ProcessBuilder pb = new ProcessBuilder(args);
		pb.redirectErrorStream(true);
		pb.redirectOutput(Redirect.appendTo(LOG));

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
