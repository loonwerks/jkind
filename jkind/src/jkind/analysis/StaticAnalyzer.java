package jkind.analysis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jkind.Output;
import jkind.analysis.evaluation.DivisionChecker;
import jkind.lustre.Constant;
import jkind.lustre.EnumType;
import jkind.lustre.Equation;
import jkind.lustre.Expr;
import jkind.lustre.IdExpr;
import jkind.lustre.NamedType;
import jkind.lustre.Node;
import jkind.lustre.Program;
import jkind.lustre.TypeDef;
import jkind.lustre.VarDecl;
import jkind.util.Util;

public class StaticAnalyzer {
	public static void check(Program program, Level nonlinear) {
		if (!checkErrors(program, nonlinear)) {
			System.exit(-1);
		}
		checkWarnings(program, nonlinear);
	}

	private static boolean checkErrors(Program program, Level nonlinear) {
		boolean result = true;
		result = result && hasMainNode(program);
		result = result && typesUnique(program);
		result = result && TypesDefined.check(program);;
		result = result && TypeDependencyChecker.check(program);
		result = result && enumsAndConstantsUnique(program);
		result = result && nodesUnique(program);
		result = result && variablesUnique(program);
		result = result && TypeChecker.check(program);
		result = result && SubrangesNonempty.check(program);
		result = result && ArraysNonempty.check(program);
		result = result && constantsConstant(program);
		result = result && DivisionChecker.check(program);
		result = result && NodeDependencyChecker.check(program);
		result = result && assignmentsSound(program);
		result = result && ConstantArrayAccessBounded.check(program);
		result = result && propertiesUnique(program);
		result = result && propertiesExist(program);
		result = result && propertiesBoolean(program);
		if (nonlinear == Level.ERROR) {
			result = result && LinearChecker.check(program, nonlinear);
		}
		return result;
	}

	private static boolean checkWarnings(Program program, Level nonlinear) {
		warnUnusedAsserts(program);
		warnAlgebraicLoops(program);
		WarnUnguardedPreVisitor.check(program);
		if (nonlinear == Level.WARNING) {
			LinearChecker.check(program, nonlinear);
		}

		return true;
	}

	private static boolean hasMainNode(Program program) {
		if (program.getMainNode() == null) {
			Output.error("no main node");
			return false;
		}
		return true;
	}

	private static boolean typesUnique(Program program) {
		boolean unique = true;
		Set<String> seen = new HashSet<>();
		for (TypeDef def : program.types) {
			if (seen.contains(def.id)) {
				Output.error(def.location, "type " + def.id + " already defined");
				unique = false;
			} else {
				seen.add(def.id);
			}
		}
		return unique;
	}

	private static boolean enumsAndConstantsUnique(Program program) {
		boolean unique = true;
		Set<String> seen = new HashSet<>();

		for (TypeDef def : program.types) {
			if (def.type instanceof EnumType) {
				EnumType et = (EnumType) def.type;
				for (String value : et.values) {
					if (seen.contains(value)) {
						Output.error(def.location, value + " defined multiple times");
						unique = false;
					} else {
						seen.add(value);
					}
				}
			}
		}

		for (Constant c : program.constants) {
			if (seen.contains(c.id)) {
				Output.error(c.location, c.id + " defined multiple times");
				unique = false;
			} else {
				seen.add(c.id);
			}
		}

		for (Node node : program.nodes) {
			for (VarDecl vd : Util.getVarDecls(node)) {
				if (seen.contains(vd.id)) {
					Output.error(vd.location, vd.id + " already defined globally");
					unique = false;
				}
			}
		}

		return unique;
	}

	private static boolean nodesUnique(Program program) {
		boolean unique = true;
		Set<String> seen = new HashSet<>();
		for (Node node : program.nodes) {
			if (seen.contains(node.id)) {
				Output.error(node.location, "node " + node.id + " already defined");
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
		Set<String> seen = new HashSet<>();
		for (VarDecl decl : Util.getVarDecls(node)) {
			if (seen.contains(decl.id)) {
				Output.error(decl.location, "variable " + decl.id + " already declared");
				unique = false;
			} else {
				seen.add(decl.id);
			}
		}
		return unique;
	}

	private static boolean constantsConstant(Program program) {
		boolean constant = true;
		ConstantAnalyzer constantAnalyzer = new ConstantAnalyzer(program.constants);
		for (Constant c : program.constants) {
			if (!c.expr.accept(constantAnalyzer)) {
				Output.error(c.location, "constant " + c.id + " does not have a constant value");
				constant = false;
			}
		}
		return constant;
	}

	private static boolean assignmentsSound(Program program) {
		boolean sound = true;
		for (Node node : program.nodes) {
			sound = assignmentsSound(node) && sound;
		}
		return sound;
	}

	private static boolean assignmentsSound(Node node) {
		Set<String> toAssign = new HashSet<>();
		toAssign.addAll(Util.getIds(node.outputs));
		toAssign.addAll(Util.getIds(node.locals));
		Set<String> assigned = new HashSet<>();
		boolean sound = true;

		for (Equation eq : node.equations) {
			for (IdExpr idExpr : eq.lhs) {
				if (toAssign.contains(idExpr.id)) {
					toAssign.remove(idExpr.id);
					assigned.add(idExpr.id);
				} else if (assigned.contains(idExpr.id)) {
					Output.error(idExpr.location, "variable '" + idExpr.id
							+ "' cannot be reassigned");
					sound = false;
				} else {
					Output.error(idExpr.location, "variable '" + idExpr.id + "' cannot be assigned");
					sound = false;
				}
			}
		}

		if (!toAssign.isEmpty()) {
			Output.error("in node '" + node.id + "' variables must be assigned: " + toAssign);
			sound = false;
		}

		return sound;
	}

	private static boolean propertiesUnique(Program program) {
		boolean unique = true;

		for (Node node : program.nodes) {
			Set<String> seen = new HashSet<>();
			for (String prop : node.properties) {
				if (seen.contains(prop)) {
					Output.error("in node '" + node.id + "' property '" + prop
							+ "' declared multiple times");
					unique = false;
				} else {
					seen.add(prop);
				}
			}
		}

		return unique;
	}

	private static boolean propertiesExist(Program program) {
		boolean exist = true;

		for (Node node : program.nodes) {
			Set<String> variables = new HashSet<>(Util.getIds(Util.getVarDecls(node)));
			for (String prop : node.properties) {
				if (!variables.contains(prop)) {
					Output.error("in node '" + node.id + "' property '" + prop + "' does not exist");
					exist = false;
				}
			}
		}

		return exist;
	}

	private static boolean propertiesBoolean(Program program) {
		boolean allBoolean = true;

		for (Node node : program.nodes) {
			Set<String> booleans = getBooleans(node);
			for (String prop : node.properties) {
				if (!booleans.contains(prop)) {
					Output.error("in node '" + node.id + "' property '" + prop
							+ "' does not have type bool");
					allBoolean = false;
				}
			}
		}

		return allBoolean;
	}

	private static Set<String> getBooleans(Node node) {
		Set<String> booleans = new HashSet<>();
		for (VarDecl varDecl : Util.getVarDecls(node)) {
			if (varDecl.type == NamedType.BOOL) {
				booleans.add(varDecl.id);
			}
		}
		return booleans;
	}

	private static void warnUnusedAsserts(Program program) {
		for (Node node : program.nodes) {
			if (node.id.equals(program.main)) {
				continue;
			}

			for (Expr expr : node.assertions) {
				Output.warning(expr.location, "assertion in subnode ignored");
			}
		}
	}

	private static void warnAlgebraicLoops(Program program) {
		for (Node node : program.nodes) {
			Map<String, Set<String>> directDepends = new HashMap<>();
			for (Equation eq : node.equations) {
				Set<String> set = CurrIdExtractorVisitor.getCurrIds(eq.expr);
				for (IdExpr idExpr : eq.lhs) {
					directDepends.put(idExpr.id, set);
				}
			}

			List<String> covered = new ArrayList<>();
			for (Equation eq : node.equations) {
				List<String> stack = new ArrayList<>();
				for (IdExpr idExpr : eq.lhs) {
					checkAlgebraicLoops(node.id, idExpr.id, stack, covered, directDepends);
				}
			}
		}
	}

	private static boolean checkAlgebraicLoops(String node, String id, List<String> stack,
			List<String> covered, Map<String, Set<String>> directDepends) {
		if (stack.contains(id)) {
			StringBuilder text = new StringBuilder();
			text.append("in node '" + node + "' possible algebraic loop: ");
			for (String s : stack.subList(stack.indexOf(id), stack.size())) {
				text.append(s + " -> ");
			}
			text.append(id);
			Output.warning(text.toString());
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
