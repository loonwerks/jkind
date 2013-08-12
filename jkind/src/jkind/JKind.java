package jkind;

import java.io.File;

import jkind.analysis.StaticAnalyzer;
import jkind.lustre.Node;
import jkind.lustre.Program;
import jkind.processes.Director;
import jkind.slicing.DependencyMap;
import jkind.slicing.LustreSlicer;
import jkind.translation.FlattenRecordTypes;
import jkind.translation.InlineConstants;
import jkind.translation.InlineNodeCalls;
import jkind.translation.InlineUserTypes;
import jkind.translation.Specification;

public class JKind {
	public static void main(String[] args) {
		try {
			JKindSettings settings = JKindArgumentParser.parse(args);
			String filename = settings.filename;
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
			Specification spec = new Specification(filename, main, dependencyMap);
			new Director(settings, spec).run();
			System.exit(0); // Kills all threads
		} catch (Throwable t) {
			t.printStackTrace();
			System.exit(-1);
		}
	}
}
