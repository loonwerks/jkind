package jkind;

import java.io.File;

import jkind.analysis.Level;
import jkind.analysis.StaticAnalyzer;
import jkind.lustre.InlinedProgram;
import jkind.lustre.Program;
import jkind.translation.Node2Excel;
import jkind.translation.Translate;

public class JLustre2Excel {
	public static void main(String args[]) {
		try {
			if (args.length != 1) {
				System.out.println("usage: jlustre2excel <input>");
				System.exit(-1);
			}
			String filename = args[0];

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

			InlinedProgram ip = Translate.translate(program);
			String outFilename = filename + ".xls";
			Node2Excel.convert(ip.node, outFilename);
			System.out.println("Wrote " + outFilename);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}
}
