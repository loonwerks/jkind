package jkind;

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

			Program program = Main.parseLustre(filename);
			StaticAnalyzer.check(program, Level.WARNING);
			
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
