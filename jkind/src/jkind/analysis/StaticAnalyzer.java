package jkind.analysis;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jkind.ExitCodes;
import jkind.Output;
import jkind.SolverOption;
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
	public static void check(Program program, SolverOption solver) {
		checkErrors(program, solver);
		checkSolverLimitations(program, solver);
		checkWarnings(program, solver);
	}

	private static void checkErrors(Program program, SolverOption solver) {
		boolean valid = true;
		valid = valid && hasMainNode(program);
		valid = valid && typesUnique(program);
		valid = valid && TypesDefined.check(program);
		valid = valid && TypeDependencyChecker.check(program);
		valid = valid && enumsAndConstantsUnique(program);
		valid = valid && ConstantDependencyChecker.check(program);
		valid = valid && nodesUnique(program);
		valid = valid && variablesUnique(program);
		valid = valid && TypeChecker.check(program);
		valid = valid && SubrangesNonempty.check(program);
		valid = valid && ArraysNonempty.check(program);
		valid = valid && constantsConstant(program);
		valid = valid && DivisionChecker.check(program);
		valid = valid && NodeDependencyChecker.check(program);
		valid = valid && assignmentsSound(program);
		valid = valid && ConstantArrayAccessBounded.check(program);
		valid = valid && propertiesUnique(program);
		valid = valid && propertiesExist(program);
		valid = valid && propertiesBoolean(program);
		if (solver != SolverOption.Z3) {
			valid = valid && LinearChecker.check(program, Level.ERROR);
		}
		
		if (!valid) {
			System.exit(ExitCodes.STATIC_ANALYSIS_ERROR);
		}
	}

	private static void checkSolverLimitations(Program program, SolverOption solver) {
		if (solver == SolverOption.MATHSAT) {
			if (!MathSatFeatureChecker.check(program)) {
				System.exit(ExitCodes.UNSUPPORTED_FEATURE);
			}
		}
	}

	private static void checkWarnings(Program program, SolverOption solver) {
		warnUnusedAsserts(program);
		warnAlgebraicLoops(program);
		WarnUnguardedPreVisitor.check(program);
		if (solver == SolverOption.Z3) {
			LinearChecker.check(program, Level.WARNING);
		}
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
			if (!seen.add(def.id)) {
				Output.error(def.location, "type " + def.id + " already defined");
				unique = false;
			}
		}
		return unique;
	}

	private static boolean enumsAndConstantsUnique(Program program) {
		boolean unique = true;
		Set<String> seen = new HashSet<>();

		for (EnumType et : Util.getEnumTypes(program.types)) {
			for (String value : et.values) {
				if (!seen.add(value)) {
					Output.error(et.location, value + " defined multiple times");
					unique = false;
				}
			}
		}

		for (Constant c : program.constants) {
			if (!seen.add(c.id)) {
				Output.error(c.location, c.id + " defined multiple times");
				unique = false;
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
			if (!seen.add(node.id)) {
				Output.error(node.location, "node " + node.id + " already defined");
				unique = false;
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
			if (!seen.add(decl.id)) {
				Output.error(decl.location, "variable " + decl.id + " already declared");
				unique = false;
			}
		}
		return unique;
	}

	private static boolean constantsConstant(Program program) {
		boolean constant = true;
		ConstantAnalyzer constantAnalyzer = new ConstantAnalyzer(program);
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
				if (!seen.add(prop)) {
					Output.error("in node '" + node.id + "' property '" + prop
							+ "' declared multiple times");
					unique = false;
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
			Map<String, Set<String>> directDepends = Util.getDirectDependencies(node);

			Set<String> covered = new HashSet<>();
			for (Equation eq : node.equations) {
				List<String> stack = new ArrayList<>();
				for (IdExpr idExpr : eq.lhs) {
					checkAlgebraicLoops(node.id, idExpr.id, stack, covered, directDepends);
				}
			}
		}
	}

	private static boolean checkAlgebraicLoops(String node, String id, List<String> stack,
			Set<String> covered, Map<String, Set<String>> directDepends) {
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

		if (!covered.add(id)) {
			return false;
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
