package jkind.analysis;

import java.util.HashSet;
import java.util.Set;

import jkind.lustre.Constant;
import jkind.lustre.Equation;
import jkind.lustre.IdExpr;
import jkind.lustre.Node;
import jkind.lustre.Program;
import jkind.lustre.TypeDef;
import jkind.lustre.VarDecl;
import jkind.translation.Util;

public class StaticAnalyzer {
	public static boolean check(Program program) {
		return TypeChecker.check(program) && typesUnique(program) && constantsUnique(program)
				&& nodesUnique(program) && variablesUnique(program) && assignmentsSound(program)
				&& propertiesUnique(program.main) && LinearChecker.check(program);
	}

	private static boolean typesUnique(Program program) {
		boolean unique = true;
		Set<String> seen = new HashSet<String>();
		for (TypeDef def : program.types) {
			if (seen.contains(def.id)) {
				System.out.println("Error at line " + def.location + " type " + def.id
						+ " already defined");
				unique = false;
			} else {
				seen.add(def.id);
			}
		}
		return unique;
	}
	
	private static boolean constantsUnique(Program program) {
		boolean unique = true;
		Set<String> seen = new HashSet<String>();
		for (Constant c : program.constants) {
			if (seen.contains(c.id)) {
				System.out.println("Error at line " + c.location + " constant " + c.id
						+ " already defined");
				unique = false;
			} else {
				seen.add(c.id);
			}
		}
		return unique;
	}

	private static boolean nodesUnique(Program program) {
		boolean unique = true;
		Set<String> seen = new HashSet<String>();
		for (Node node : program.nodes) {
			if (seen.contains(node.id)) {
				System.out.println("Error at line " + node.location + " node " + node.id
						+ " already defined");
				unique = false;
			} else {
				seen.add(node.id);
			}
		}
		return unique;
	}

	private static boolean variablesUnique(Program program) {
		boolean unique = true;
		for (Node node : program.nodes) {
			unique = unique && variablesUnique(node);
		}
		return unique;
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

	private static boolean assignmentsSound(Program program) {
		boolean sound = true;
		for (Node node : program.nodes) {
			sound = sound && assignmentsSound(node);
		}
		return sound;
	}

	private static boolean assignmentsSound(Node node) {
		Set<String> toAssign = new HashSet<String>();
		toAssign.addAll(Util.getIds(node.outputs));
		toAssign.addAll(Util.getIds(node.locals));
		Set<String> assigned = new HashSet<String>();
		boolean sound = true;

		for (Equation eq : node.equations) {
			for (IdExpr idExpr : eq.lhs) {
				if (toAssign.contains(idExpr.id)) {
					toAssign.remove(idExpr.id);
					assigned.add(idExpr.id);
				} else if (assigned.contains(idExpr.id)) {
					System.out.println("Error at line " + eq.location
							+ " variable cannot be reassigned");
					sound = false;
				} else {
					System.out.println("Error at line " + eq.location + " variable cannot be assigned");
					sound = false;
				}
			}
		}

		if (!toAssign.isEmpty()) {
			System.out.println("Error in node " + node.id + ": variables must be assigned: "
					+ toAssign);
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
