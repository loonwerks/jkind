package jkind.analysis;

import java.util.HashSet;
import java.util.Set;

import jkind.lustre.Equation;
import jkind.lustre.Node;
import jkind.lustre.VarDecl;
import jkind.translation.Util;

public class StaticAnalyzer {
	public static boolean check(Node node) {
		return TypeChecker.check(node) && variablesUnique(node) && assignmentsSound(node)
				&& propertiesUnique(node);
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

	private static boolean assignmentsSound(Node node) {
		Set<String> toAssign = new HashSet<String>();
		toAssign.addAll(Util.getIds(node.outputs));
		toAssign.addAll(Util.getIds(node.locals));
		Set<String> assigned = new HashSet<String>();
		boolean sound = true;

		for (Equation eq : node.equations) {
			if (toAssign.contains(eq.id)) {
				toAssign.remove(eq.id);
				assigned.add(eq.id);
			} else if (assigned.contains(eq.id)) {
				System.out.println("Error at line " + eq.location
						+ " variable cannot be reassigned");
				sound = false;
			} else {
				System.out.println("Error at line " + eq.location + " variable cannot be assigned");
				sound = false;
			}
		}

		if (!toAssign.isEmpty()) {
			System.out.println("Error: variables must be assigned: " + toAssign);
			sound = false;
		}

		return sound;
	}
	
	private static boolean propertiesUnique(Node node) {
		boolean unique = true;
		Set<String> seen = new HashSet<String>();
		
		for (String prop : node.properties) {
			if (seen.contains(prop)) {
				System.out.println("Error: property " + prop + " declared multiple times");
				unique = false;
			} else {
				seen.add(prop);
			}
		}
		
		return unique;
	}
}
