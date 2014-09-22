package jkind;

import java.io.File;

import jkind.analysis.StaticAnalyzer;
import jkind.lustre.Node;
import jkind.lustre.Program;
import jkind.slicing.DependencyMap;
import jkind.slicing.LustreSlicer;
import jkind.translation.RemoveEnumTypes;
import jkind.translation.Translate;
import jkind.util.Util;

public class JLustre2Kind {
	public static void main(String args[]) {
		try {
			JLustre2KindSettings settings = JLustre2KindArgumentParser.parse(args);
			String filename = settings.filename;

			if (!filename.toLowerCase().endsWith(".lus")) {
				Output.error("input file must have .lus extension");
			}
			String outFilename = filename.substring(0, filename.length() - 4) + ".kind.lus";

			Program program = Main.parseLustre(filename);
			StaticAnalyzer.check(program, SolverOption.Z3);

			Node main = Translate.translate(program);
			main = RemoveEnumTypes.node(main);
			DependencyMap dependencyMap = new DependencyMap(main, main.properties);
			main = LustreSlicer.slice(main, dependencyMap);

			String result = main.toString();
			if (settings.encode) {
				result = result.replaceAll("\\.", "~dot~");
				// We want to escape array brackets, but not subrange brackets
				result = result.replaceAll("\\[", "~lbrack~");
				result = result.replaceAll("\\]", "~rbrack~");
				result = result.replaceAll("subrange ~lbrack~(-?[0-9]+, -?[0-9]+)~rbrack~", "subrange [$1]");
			}
			
			if (settings.stdout) {
				System.out.println(result);
			} else {
				Util.writeToFile(result, new File(outFilename));
				Output.println("Wrote " + outFilename);
			}
		} catch (Throwable t) {
			t.printStackTrace();
			System.exit(ExitCodes.UNCAUGHT_EXCEPTION);
		}
	}
}
