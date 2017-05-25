package jkind;

import java.io.File;

import jkind.analysis.StaticAnalyzer;
import jkind.jlustre2kind.KindEncodeIdsVisitor;
import jkind.jlustre2kind.ObfuscateIdsVisitor;
import jkind.lustre.Node;
import jkind.lustre.Program;
import jkind.lustre.builders.NodeBuilder;
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
				StdErr.error("input file must have .lus extension");
			}
			String outFilename = filename.substring(0, filename.length() - 4) + ".kind.lus";

			Program program = Main.parseLustre(filename);
			StaticAnalyzer.check(program, SolverOption.Z3);

			Node main = Translate.translate(program);
			main = RemoveEnumTypes.node(main);
			DependencyMap dependencyMap = new DependencyMap(main, main.properties);
			main = LustreSlicer.slice(main, dependencyMap);

			if (settings.encode) {
				main = new KindEncodeIdsVisitor().visit(main);
			}
			if (settings.obfuscate) {
				main = new ObfuscateIdsVisitor().visit(main);
				main = new NodeBuilder(main).setId("main").build();
			}
			if (settings.stdout) {
				System.out.println(main.toString());
			} else {
				Util.writeToFile(main.toString(), new File(outFilename));
				System.out.println("Wrote " + outFilename);
			}
		} catch (Throwable t) {
			t.printStackTrace();
			System.exit(ExitCodes.UNCAUGHT_EXCEPTION);
		}
	}
}
