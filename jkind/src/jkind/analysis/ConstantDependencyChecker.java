package jkind.analysis;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jkind.StdErr;
import jkind.lustre.Constant;
import jkind.lustre.Expr;
import jkind.lustre.IdExpr;
import jkind.lustre.Program;
import jkind.lustre.visitors.ExprIterVisitor;
import jkind.util.CycleFinder;

public class ConstantDependencyChecker {
	public static boolean check(Program program) {
		return new ConstantDependencyChecker().check(program.constants);
	}

	protected boolean check(List<Constant> constants) {
		Map<String, Set<String>> dependencies = new HashMap<>();
		for (Constant c : constants) {
			dependencies.put(c.id, getConstantDependencies(c.expr));
		}

		List<String> cycle = CycleFinder.findCycle(dependencies);
		if (cycle != null) {
			StdErr.error("cyclic constants: " + cycle);
			return false;
		}
		return true;
	}

	private static Set<String> getConstantDependencies(Expr e) {
		final Set<String> dependencies = new HashSet<>();
		e.accept(new ExprIterVisitor() {
			@Override
			public Void visit(IdExpr e) {
				dependencies.add(e.id);
				return null;
			}
		});
		return dependencies;
	}
}
