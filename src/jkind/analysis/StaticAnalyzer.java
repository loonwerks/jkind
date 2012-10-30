package jkind.analysis;

import jkind.lustre.Node;

public class StaticAnalyzer {
	public static boolean node(Node node) {
		if (!TypeChecker.node(node)) {
			return false;
		}
		
		return true;
	}
}
