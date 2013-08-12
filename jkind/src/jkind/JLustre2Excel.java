package jkind;

import java.io.File;

import jkind.analysis.StaticAnalyzer;
import jkind.lustre.Node;
import jkind.lustre.Program;
import jkind.translation.FlattenRecordTypes;
import jkind.translation.InlineConstants;
import jkind.translation.InlineNodeCalls;
import jkind.translation.InlineUserTypes;
import jkind.translation.Node2Excel;

public class JLustre2Excel {
	public static void main(String args[]) {
		try {
			if (args.length != 1) {
				System.out.println("Usage: jlustre2excel <input>");
				System.exit(-1);
			}
			String filename = args[0];
			
			if (!new File(filename).exists()) {
				System.out.println("Error: cannot find file " + filename);
				System.exit(-1);
			}

			Program program = Main.parseLustre(filename);
			if (program.main == null) {
				System.out.println("Error: no main node");
				System.exit(-1);
			}

			if (!StaticAnalyzer.check(program, false)) {
				System.exit(-1);
			}

			program = InlineUserTypes.program(program);
			program = InlineConstants.program(program);
			Node main = InlineNodeCalls.program(program);
			main = FlattenRecordTypes.node(main);

			String outFilename = filename + ".xls";
			Node2Excel.convert(main, outFilename);
			System.out.println("Wrote " + outFilename);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}
}
