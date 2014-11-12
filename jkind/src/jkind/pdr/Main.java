package jkind.pdr;

import java.io.IOException;

import jkind.SolverOption;
import jkind.analysis.StaticAnalyzer;
import jkind.lustre.Node;
import jkind.lustre.Program;
import jkind.slicing.DependencyMap;
import jkind.slicing.LustreSlicer;
import jkind.translation.Translate;

import org.antlr.v4.runtime.RecognitionException;

public class Main {
	public static void main(String[] args) throws RecognitionException, IOException {
		long start = System.currentTimeMillis();
		Program program = jkind.Main.parseLustre(args[0]);
		StaticAnalyzer.check(program, SolverOption.YICES);

		Node main = Translate.translate(program);
		main = LustreSlicer.slice(main, new DependencyMap(main, main.properties));

		Pdr pdr = new Pdr(main);
		showCex(pdr.check());
		long stop = System.currentTimeMillis();
		System.out.println((stop - start) / 1000.0);
	}

	private static void showCex(Cube c) {
		while (c != null) {
			System.out.println(c);
			c = c.getNext();
		}
		System.out.println("Done");
	}
}
