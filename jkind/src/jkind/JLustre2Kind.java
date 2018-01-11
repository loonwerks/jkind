package jkind;

import java.io.File;

import jkind.analysis.StaticAnalyzer;
import jkind.jlustre2kind.KindEncodeIdsVisitor;
import jkind.jlustre2kind.ObfuscateIdsVisitor;
import jkind.lustre.Node;
import jkind.lustre.Program;
import jkind.lustre.builders.NodeBuilder;
import jkind.lustre.builders.ProgramBuilder;
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

			program = Translate.translate(program);
			Node main = program.getMainNode();
			main = RemoveEnumTypes.node(main);
			main = LustreSlicer.slice(main, new DependencyMap(main, main.properties, program.functions));

			if (settings.encode) {
				main = new KindEncodeIdsVisitor().visit(main);
			}
			
			if (settings.obfuscate) {
				main = new ObfuscateIdsVisitor().visit(main);
				main = new NodeBuilder(main).setId("main").build();
			}
			
			program = new ProgramBuilder(program).clearNodes().addNode(main).build();
			if (settings.stdout) {
				System.out.println(program.toString());
			} else {
				Util.writeToFile(program.toString(), new File(outFilename));
				System.out.println("Wrote " + outFilename);
			}
		} catch (Throwable t) {
			t.printStackTrace();
			System.exit(ExitCodes.UNCAUGHT_EXCEPTION);
		}
	}
}
