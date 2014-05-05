package jkind;

import java.io.File;

import jkind.analysis.Level;
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
				System.out.println("Error: input file must have .lus extension");
			}
			String outFilename = filename.substring(0, filename.length() - 4) + ".kind.lus";

			Program program = Main.parseLustre(filename);
			StaticAnalyzer.check(program, Level.WARNING);
			
			program = Translate.translate(program);
			Node main = program.getMainNode();
			main = RemoveEnumTypes.node(main);
			
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
