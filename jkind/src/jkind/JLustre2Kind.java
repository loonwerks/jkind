package jkind;

import java.io.File;

import jkind.analysis.Level;
import jkind.analysis.StaticAnalyzer;
import jkind.lustre.Node;
import jkind.lustre.Program;
import jkind.slicing.DependencyMap;
import jkind.slicing.LustreSlicer;
import jkind.translation.InlineConstants;
import jkind.translation.InlineNodeCalls;
import jkind.translation.InlineUserTypes;
import jkind.translation.RemoveCondacts;
import jkind.translation.compound.FlattenCompoundTypes;
import jkind.translation.compound.RemoveNonConstantArrayIndices;
import jkind.translation.tuples.FlattenTuples;
import jkind.util.Util;

public class JLustre2Kind {
	public static void main(String args[]) {
		try {
			JLustre2KindSettings settings = JLustre2KindArgumentParser.parse(args);
			String filename = settings.filename;

			if (!filename.toLowerCase().endsWith(".lus")) {
				System.out.println("Error: input file must have .lus extension");
			}
			String outFilename = filename.substring(0, filename.length() - 4) + ".kind.lus";

			if (!new File(filename).exists()) {
				System.out.println("Error: cannot find file " + filename);
				System.exit(-1);
			}

			Program program = Main.parseLustre(filename);
			if (program.getMainNode() == null) {
				System.out.println("Error: no main node");
				System.exit(-1);
			}

			if (!StaticAnalyzer.check(program, Level.WARNING)) {
				System.exit(-1);
			}

			program = InlineUserTypes.program(program);
			program = InlineConstants.program(program);
			program = RemoveCondacts.program(program);
			Node main = InlineNodeCalls.program(program);
			main = FlattenTuples.node(main);
			main = RemoveNonConstantArrayIndices.node(main);
			main = FlattenCompoundTypes.node(main);

			DependencyMap dependencyMap = new DependencyMap(main, main.properties);
			main = LustreSlicer.slice(main, dependencyMap);

			String result = main.toString();
			if (settings.encode) {
				result = result.replaceAll("\\.", "~dot~");
				result = result.replaceAll("\\[", "~lbrack~");
				result = result.replaceAll("\\]", "~rbrack~");
			}
			
			if (settings.stdout) {
				System.out.println(result);
			} else {
				Util.writeToFile(result, new File(outFilename));
				System.out.println("Wrote " + outFilename);
			}
		} catch (Throwable t) {
			t.printStackTrace();
			System.exit(-1);
		}
	}
}
