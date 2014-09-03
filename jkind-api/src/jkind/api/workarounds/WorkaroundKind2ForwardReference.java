package jkind.api.workarounds;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jkind.lustre.Constant;
import jkind.lustre.Expr;
import jkind.lustre.IdExpr;
import jkind.lustre.NamedType;
import jkind.lustre.Node;
import jkind.lustre.NodeCallExpr;
import jkind.lustre.Program;
import jkind.lustre.Type;
import jkind.lustre.TypeDef;
import jkind.lustre.builders.ProgramBuilder;
import jkind.lustre.visitors.AstIterVisitor;
import jkind.lustre.visitors.ExprIterVisitor;
import jkind.lustre.visitors.TypeIterVisitor;

public class WorkaroundKind2ForwardReference {
	public static Program program(Program program) {
		ProgramBuilder bulider = new ProgramBuilder(program);
		bulider.clearTypes().addTypes(getSortedTypes(program.types));
		bulider.clearConstants().addConstants(getSortedConstants(program.constants));
		bulider.clearNodes().addNodes(getSortedNodes(program.nodes));
		return bulider.build();
	}

	private static List<TypeDef> getSortedTypes(List<TypeDef> types) {
		DependencyMap<TypeDef> depMap = new DependencyMap<>();
		for (TypeDef type : types) {
			depMap.put(type.id, type, getTypeDependencies(type.type));
		}
		return depMap.getSortedValues();
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

	private static Collection<Constant> getSortedConstants(List<Constant> constants) {
		DependencyMap<Constant> depMap = new DependencyMap<>();
		for (Constant constant : constants) {
			depMap.put(constant.id, constant, getConstantDependencies(constant.expr));
		}
		return depMap.getSortedValues();
	}

	private static Set<String> getConstantDependencies(Expr expr) {
		final Set<String> dependencies = new HashSet<>();
		expr.accept(new ExprIterVisitor() {
			@Override
			public Void visit(IdExpr e) {
				dependencies.add(e.id);
				return null;
			}
		});
		return dependencies;
	}

	private static Collection<Node> getSortedNodes(List<Node> nodes) {
		DependencyMap<Node> depMap = new DependencyMap<>();
		for (Node node : nodes) {
			depMap.put(node.id, node, getNodeDependencies(node));
		}
		return depMap.getSortedValues();
	}

	private static Set<String> getNodeDependencies(Node node) {
		final Set<String> dependencies = new HashSet<>();
		node.accept(new AstIterVisitor() {
			@Override
			public Void visit(NodeCallExpr e) {
				dependencies.add(e.node);
				return null;
			}
		});
		return dependencies;
	}
}
