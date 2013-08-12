package jkind;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import jkind.analysis.StaticAnalyzer;
import jkind.lustre.Node;
import jkind.lustre.Program;
import jkind.slicing.DependencyMap;
import jkind.slicing.LustreSlicer;
import jkind.translation.FlattenRecordTypes;
import jkind.translation.InlineConstants;
import jkind.translation.InlineNodeCalls;
import jkind.translation.InlineUserTypes;

public class JLustre2Kind {
	public static void main(String args[]) {
		try {
			if (args.length != 1) {
				System.out.println("Usage: jlustre2kind <input>");
				System.exit(-1);
			}
			String filename = args[0];
			
			if (!filename.endsWith(".lus")) {
				System.out.println("Error: input file must have .lus extension");
			}
			String outFilename = filename.replaceAll(".lus$", ".kind.lus");

			if (!new File(filename).exists()) {
				System.out.println("Error: cannot find file " + filename);
				System.exit(-1);
			}

			Program program = Main.parseLustre(filename);
			if (program.getMainNode() == null) {
				System.out.println("Error: no main node");
				System.exit(-1);
			}

			if (!StaticAnalyzer.check(program)) {
				System.exit(-1);
			}

			program = InlineUserTypes.program(program);
			program = InlineConstants.program(program);
			Node main = InlineNodeCalls.program(program);
			main = FlattenRecordTypes.node(main);
			
			DependencyMap dependencyMap = new DependencyMap(main, main.properties);
			main = LustreSlicer.slice(main, dependencyMap);
			
			write(main.toString(), new File(outFilename));
			System.out.println("Wrote " + outFilename);
		} catch (Throwable t) {
			t.printStackTrace();
			System.exit(-1);
		}
	}

	private static void write(String string, File file) throws IOException {
		Writer writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(file));
			writer.append(string);
		} finally {
			if (writer != null) {
				writer.close();
			}
		}
	}
}
