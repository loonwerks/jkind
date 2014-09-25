package jkind;

import jkind.lustre.Program;

public class JLustre2Ast {
	public static void main(String[] args) {
		try {
			if (args.length != 1) {
				Output.fatal(ExitCodes.INVALID_OPTIONS, "usage: jlustre2ast <input>");
			}
			String filename = args[0];

			Program program = Main.parseLustre(filename);
			AstPrintVisitor visitor = new AstPrintVisitor();
			program.accept(visitor);
			System.out.println(visitor.toString());
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(ExitCodes.UNCAUGHT_EXCEPTION);
		}
	}
}
