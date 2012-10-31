package jkind.analysis;

import java.util.HashSet;
import java.util.Set;

import jkind.lustre.Node;
import jkind.lustre.VarDecl;
import jkind.translation.Util;

public class StaticAnalyzer {
	public static boolean check(Node node) {
		return TypeChecker.check(node) && variablesUnique(node);
	}

	private static boolean variablesUnique(Node node) {
		boolean unique = true;
		Set<String> seen = new HashSet<String>();
		for (VarDecl decl : Util.getVarDecls(node)) {
			if (seen.contains(decl.id)) {
				System.out.println("Error at line " + decl.location + " variable " + decl.id
						+ " already declared");
				unique = false;
			} else {
				seen.add(decl.id);
			}
		}
		return unique;
	}
}
