package jkind;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

import jkind.analysis.LinearChecker;
import jkind.analysis.StaticAnalyzer;
import jkind.lustre.Equation;
import jkind.lustre.Node;
import jkind.lustre.Program;
import jkind.lustre.VarDecl;
import jkind.lustre.builders.NodeBuilder;
import jkind.slicing.DependencyMap;
import jkind.slicing.LustreSlicer;
import jkind.translation.RemoveEnumTypes;
import jkind.translation.Translate;
import jkind.util.Util;

public class JSupport {
	private final static long startTime = System.currentTimeMillis();

	private static double getRuntime() {
		return (System.currentTimeMillis() - startTime) / 1000.0;
	}

	public static void main(String args[]) {
		try {
			JKindSettings settings = JKindArgumentParser.parse(args);
			Program program = Main.parseLustre(settings.filename);

			StaticAnalyzer.check(program, settings.solver);
			if (!LinearChecker.isLinear(program)) {
				throw new IllegalArgumentException("Non-linear not supported");
			}

			Node main = Translate.translate(program);
			main = RemoveEnumTypes.node(main);
			DependencyMap dependencyMap = new DependencyMap(main, main.properties);
			main = LustreSlicer.slice(main, dependencyMap);
			main = new NodeBuilder(main).clearSupport().build();

			if (main.properties.size() != 1) {
				throw new IllegalArgumentException("Expected exactly one property, but found "
						+ main.properties.size());
			}

			List<String> possible = getAllAssigned(main);
			List<String> minimal = new ArrayList<>(possible);
			for (String s : possible) {
				Node candidate = unassign(main, s);
				if (propertyTrue(candidate, args)) {
					minimal.remove(s);
					main = candidate;
				}
			}

			String xmlFilename = settings.filename + "_jsup.xml";
			try (PrintWriter out = new PrintWriter(new FileOutputStream(xmlFilename))) {
				out.println("<?xml version=\"1.0\"?>");
				out.println("<Results xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">");
				out.println("  <MinimalSupport property=\"" + main.properties + "\">");
				out.println("    <Runtime unit=\"sec\">" + getRuntime() + "</Runtime>");
				for (String s : Util.safeStringSortedSet(minimal)) {
					out.println("    <Support>" + s + "</Support>");
				}
				out.println("  </MinimalSupport>");
				out.println("</Results>");
			}
		} catch (Throwable t) {
			t.printStackTrace();
			System.exit(ExitCodes.UNCAUGHT_EXCEPTION);
		}
	}

	private static Node unassign(Node node, String v) {
		List<VarDecl> inputs = new ArrayList<>(node.inputs);
		inputs.add(new VarDecl(v, Util.getTypeMap(node).get(v)));
		List<VarDecl> locals = removeVariable(node.locals, v);
		List<VarDecl> outputs = removeVariable(node.outputs, v);

		List<Equation> equations = new ArrayList<>(node.equations);
		Iterator<Equation> iter = equations.iterator();
		while (iter.hasNext()) {
			Equation eq = iter.next();
			if (eq.lhs.get(0).id.equals(v)) {
				iter.remove();
			}
		}

		NodeBuilder builder = new NodeBuilder(node);
		builder.clearInputs().addInputs(inputs);
		builder.clearLocals().addLocals(locals);
		builder.clearOutputs().addOutputs(outputs);
		builder.clearEquations().addEquations(equations);
		return builder.build();
	}

	private static List<VarDecl> removeVariable(List<VarDecl> varDecls, String v) {
		List<VarDecl> result = new ArrayList<>(varDecls);
		Iterator<VarDecl> iter = result.iterator();
		while (iter.hasNext()) {
			if (iter.next().id.equals(v)) {
				iter.remove();
			}
		}
		return result;
	}

	private static List<String> getAllAssigned(Node node) {
		List<String> result = new ArrayList<>();
		result.addAll(Util.getIds(node.locals));
		result.addAll(Util.getIds(node.outputs));
		return result;
	}

	private static boolean propertyTrue(Node node, String[] args) throws IOException {
		String[] filePath = (args[args.length - 1]).split(Pattern.quote(File.separator));
		String fileName = filePath[filePath.length - 1];
		Util.writeToFile(node.toString(), new File(fileName));

		List<String> cmd = new ArrayList<>();
		cmd.add("java");
		cmd.add("-jar");
		cmd.add(findJKindJar().toString());
		cmd.add("-jkind");
		cmd.add(fileName);
		for (String arg : args) {
			if (!arg.contains(".lus")) {
				cmd.add(arg);
			}
		}
		ProcessBuilder pb = new ProcessBuilder(cmd);

		BufferedReader reader = new BufferedReader(new InputStreamReader(pb.start()
				.getInputStream()));
		String line;
		while ((line = reader.readLine()) != null) {
			if (line.startsWith("VALID PROPERTIES")) {
				return true;
			}
			if (line.startsWith("INVALID PROPERTIES")) {
				return false;
			}
			if (line.startsWith("UNKNOWN PROPERTIES")) {
				throw new IllegalArgumentException("Got unknown result");
			}
			if (line.contains("Exception")) {
				throw new IllegalArgumentException("Got exception");
			}
		}
		throw new IllegalArgumentException("Didn't find result");
	}

	public static File findJKindJar() {
		/*
		 * On Windows, invoking Process.destroy does not kill the subprocesses
		 * of the destroyed process. If we were to run jkind.bat and kill it,
		 * only the cmd.exe process which is running the batch file would be
		 * killed. The underlying JKind process would continue to its natural
		 * end. To avoid this, we search the user's path for the jkind.jar file
		 * and invoke it directly.
		 * 
		 * In order to support JKIND_HOME or PATH as the location for JKind, we
		 * now search in non-windows environments too.
		 */

		File jar = findJKindJar(System.getenv("JKIND_HOME"));
		if (jar != null) {
			return jar;
		}

		jar = findJKindJar(System.getenv("PATH"));
		if (jar != null) {
			return jar;
		}

		throw new JKindException("Unable to find jkind.jar in JKIND_HOME or on system PATH");
	}

	public static File findJKindJar(String path) {
		if (path == null) {
			return null;
		}

		for (String element : path.split(File.pathSeparator)) {
			File jar = new File(element, "jkind.jar");
			if (jar.exists()) {
				return jar;
			}
		}

		return null;
	}
}
