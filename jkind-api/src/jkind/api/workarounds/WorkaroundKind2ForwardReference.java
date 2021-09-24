package jkind.api.workarounds;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jkind.lustre.NamedType;
import jkind.lustre.Program;
import jkind.lustre.Type;
import jkind.lustre.TypeDef;
import jkind.lustre.builders.ProgramBuilder;
import jkind.lustre.visitors.TypeIterVisitor;
import jkind.util.TopologicalSorter;

public class WorkaroundKind2ForwardReference {
	public static Program program(Program program) {
		ProgramBuilder bulider = new ProgramBuilder(program);
		bulider.clearTypes().addTypes(getSortedTypes(program.types));
		return bulider.build();
	}

	private static List<TypeDef> getSortedTypes(List<TypeDef> types) {
		TopologicalSorter<TypeDef> sorter = new TopologicalSorter<>();
		for (TypeDef type : types) {
			sorter.put(type.id, type, getTypeDependencies(type.type));
		}
		return sorter.getSortedValues();
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
