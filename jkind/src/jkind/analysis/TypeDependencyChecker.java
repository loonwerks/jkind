package jkind.analysis;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jkind.StdErr;
import jkind.lustre.NamedType;
import jkind.lustre.Program;
import jkind.lustre.Type;
import jkind.lustre.TypeDef;
import jkind.lustre.visitors.TypeIterVisitor;
import jkind.util.CycleFinder;

public class TypeDependencyChecker {
	public static boolean check(Program program) {
		return new TypeDependencyChecker().check(program.types);
	}

	protected boolean check(List<TypeDef> types) {
		Map<String, Set<String>> dependencies = new HashMap<>();
		for (TypeDef def : types) {
			dependencies.put(def.id, getTypeDependencies(def.type));
		}

		List<String> cycle = CycleFinder.findCycle(dependencies);
		if (cycle != null) {
			StdErr.error("cyclic types: " + cycle);
			return false;
		}
		return true;
	}

	private static Set<String> getTypeDependencies(Type type) {
		final Set<String> dependencies = new HashSet<>();
		type.accept(new TypeIterVisitor() {
			@Override
			public Void visit(NamedType e) {
				if (!e.isBuiltin()) {
					dependencies.add(e.name);
				}
				return null;
			}
		});
		return dependencies;
	}
}
