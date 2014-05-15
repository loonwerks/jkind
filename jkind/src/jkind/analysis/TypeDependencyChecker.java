package jkind.analysis;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jkind.lustre.NamedType;
import jkind.lustre.Program;
import jkind.lustre.Type;
import jkind.lustre.TypeDef;
import jkind.lustre.visitors.TypeIterVisitor;

public class TypeDependencyChecker extends CycleChecker {
	public static boolean check(Program program) {
		return new TypeDependencyChecker().check(program.types);
	}

	protected boolean check(List<TypeDef> types) {
		for (TypeDef def : types) {
			dependencies.put(def.id, getTypeDependencies(def.type));
		}

		return super.checkDependencies();
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
	
	@Override
	protected String getError() {
		return "cyclic types";
	}
}
