package jkind;

import java.io.File;

import jkind.analysis.Level;
import jkind.analysis.StaticAnalyzer;
import jkind.lustre.InlinedProgram;
import jkind.lustre.Node;
import jkind.lustre.Program;
import jkind.processes.Director;
import jkind.slicing.DependencyMap;
import jkind.slicing.LustreSlicer;
import jkind.translation.Specification;
import jkind.translation.Translate;

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

			Level nonlinear = settings.solver == SolverOption.Z3 ? Level.WARNING : Level.ERROR;
			if (!StaticAnalyzer.check(program, nonlinear)) {
				System.exit(-1);
			}

			InlinedProgram ip = Translate.translate(program);
			DependencyMap dependencyMap = new DependencyMap(ip.node, ip.node.properties);
			Node sliced = LustreSlicer.slice(ip.node, dependencyMap);
			Specification spec = new Specification(filename, ip.functions, sliced, dependencyMap);
			new Director(settings, spec).run();
			System.exit(0); // Kills all threads
		} catch (Throwable t) {
			t.printStackTrace();
			System.exit(-1);
		}
	}
}
