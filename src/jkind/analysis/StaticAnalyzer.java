package jkind.analysis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jkind.analysis.evaluation.DivideByZeroChecker;
import jkind.lustre.Constant;
import jkind.lustre.Equation;
import jkind.lustre.Expr;
import jkind.lustre.IdExpr;
import jkind.lustre.Node;
import jkind.lustre.Program;
import jkind.lustre.SubrangeIntType;
import jkind.lustre.Type;
import jkind.lustre.TypeDef;
import jkind.lustre.VarDecl;
import jkind.slicing.IdExtractorVisitor;
import jkind.util.Util;

public class StaticAnalyzer {
	public static boolean check(Program program) {
		return checkErrors(program) && checkWarnings(program);
	}

	private static boolean checkErrors(Program program) {
		boolean result = true;
		result = result && TypeChecker.check(program);
		result = result && typesUnique(program);
		result = result && subrangesNonempty(program);
		result = result && constantsUnique(program);
		result = result && constantsConstant(program);
		result = result && nodesUnique(program);
		result = result && NodeDependencyChecker.check(program);
		result = result && variablesUnique(program);
		result = result && assignmentsSound(program);
		result = result && propertiesUnique(program.main);
		result = result && propertiesExist(program.main);
		result = result && propertiesBoolean(program.main);
		result = result && LinearChecker.check(program);
		result = result && DivideByZeroChecker.check(program);
		result = result && assertsDoNotUseComputedValues(program.main);
		return result;
	}

	private static boolean checkWarnings(Program program) {
		warnUnusedAssertsAndProperties(program);
		warnAlgebraicLoops(program);
		WarnUnguardedPreVisitor.check(program);

		return true;
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

	private static boolean subrangesNonempty(Program program) {
		boolean nonempty = true;

		for (TypeDef def : program.types) {
			if (!checkSubrangeNonempty(def.type)) {
				nonempty = false;
			}
		}

		for (Node node : program.nodes) {
			for (VarDecl decl : Util.getVarDecls(node)) {
				if (!checkSubrangeNonempty(decl.type)) {
					nonempty = false;
				}
			}
		}

		return nonempty;
	}

	private static boolean checkSubrangeNonempty(Type type) {
		if (type instanceof SubrangeIntType) {
			SubrangeIntType subrange = (SubrangeIntType) type;
			if (subrange.high.compareTo(subrange.low) < 0) {
				System.out.println("Error at line " + subrange.location + " subrange is empty");
				return false;
			}
		}

		return true;
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

	private static boolean constantsConstant(Program program) {
		boolean constant = true;
		ConstantAnalyzer constantAnalyzer = new ConstantAnalyzer(program.constants);
		for (Constant c : program.constants) {
			if (!c.expr.accept(constantAnalyzer)) {
				System.out.println("Error at line " + c.location + " constant " + c.id
						+ " does not have a constant value");
				constant = false;
			}
		}
		return constant;
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
			unique = variablesUnique(node) && unique;
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
			sound = assignmentsSound(node) && sound;
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
					System.out.println("Error at line " + idExpr.location + " variable '"
							+ idExpr.id + "' cannot be reassigned");
					sound = false;
				} else {
					System.out.println("Error at line " + idExpr.location + " variable '"
							+ idExpr.id + "' cannot be assigned");
					sound = false;
				}
			}
		}

		if (!toAssign.isEmpty()) {
			System.out.println("Error in node '" + node.id + "' variables must be assigned: "
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
				System.out.println("Error: property '" + prop + "' declared multiple times");
				unique = false;
			} else {
				seen.add(prop);
			}
		}

		return unique;
	}

	private static boolean propertiesExist(Node node) {
		boolean exist = true;

		Set<String> variables = new HashSet<String>(Util.getIds(Util.getVarDecls(node)));
		for (String prop : node.properties) {
			if (!variables.contains(prop)) {
				System.out.println("Error: property '" + prop + "' does not exist");
				exist = false;
			}
		}

		return exist;
	}
	
	private static boolean propertiesBoolean(Node node) {
		boolean allBoolean = true;

		Set<String> booleans = new HashSet<String>();
		for (VarDecl varDecl : Util.getVarDecls(node)) {
			if (varDecl.type == Type.BOOL) {
				booleans.add(varDecl.id);
			}
		}
		
		for (String prop : node.properties) {
			if (!booleans.contains(prop)) {
				System.out.println("Error: property '" + prop + "' does not have type bool");
				allBoolean = false;
			}
		}

		return allBoolean;
	}

	private static boolean assertsDoNotUseComputedValues(Node node) {
		boolean result = true;

		List<String> computed = new ArrayList<String>();
		computed.addAll(Util.getIds(node.locals));
		computed.addAll(Util.getIds(node.outputs));
		
		for (Expr expr : node.assertions) {
			Set<String> vars = IdExtractorVisitor.getIds(expr);
			vars.retainAll(computed);
			if (!vars.isEmpty()) {
				System.out.println("Error at line " + expr.location
						+ " assertion refers to non-input variables: " + vars);
				result = false;
			}
		}

		return result;
	}

	private static void warnUnusedAssertsAndProperties(Program program) {
		for (Node node : program.nodes) {
			if (node == program.main) {
				continue;
			}

			for (Expr expr : node.assertions) {
				System.out.println("Warning at line " + expr.location
						+ " assertion in subnode ignored");
			}

			for (String prop : node.properties) {
				System.out.println("Warning: Property '" + prop + "' in subnode '" + node.id
						+ "' is ignored");
			}
		}
	}

	private static void warnAlgebraicLoops(Program program) {
		for (Node node : program.nodes) {
			Map<String, Set<String>> directDepends = new HashMap<String, Set<String>>();
			for (Equation eq : node.equations) {
				Set<String> set = CurrIdExtractorVisitor.getCurrIds(eq.expr);
				for (IdExpr idExpr : eq.lhs) {
					directDepends.put(idExpr.id, set);
				}
			}

			List<String> covered = new ArrayList<String>();
			for (Equation eq : node.equations) {
				List<String> stack = new ArrayList<String>();
				for (IdExpr idExpr : eq.lhs) {
					checkAlgebraicLoops(node.id, idExpr.id, stack, covered, directDepends);
				}
			}
		}
	}

	private static boolean checkAlgebraicLoops(String node, String id, List<String> stack,
			List<String> covered, Map<String, Set<String>> directDepends) {
		if (stack.contains(id)) {
			System.out.print("Warning in node '" + node + "' possible algebraic loop: ");
			for (String s : stack.subList(stack.indexOf(id), stack.size())) {
				System.out.print(s + " -> ");
			}
			System.out.println(id);
			return true;
		}

		if (covered.contains(id)) {
			return false;
		} else {
			covered.add(id);
		}

		if (directDepends.containsKey(id)) {
			stack.add(id);
			for (String next : directDepends.get(id)) {
				if (checkAlgebraicLoops(node, next, stack, covered, directDepends)) {
					return true;
				}
			}
			stack.remove(stack.size() - 1);
		}

		return false;
	}
}
