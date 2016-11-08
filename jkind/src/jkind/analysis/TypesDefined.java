package jkind.analysis;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jkind.StdErr;
import jkind.lustre.Constant;
import jkind.lustre.NamedType;
import jkind.lustre.Program;
import jkind.lustre.Type;
import jkind.lustre.TypeDef;
import jkind.lustre.VarDecl;
import jkind.lustre.visitors.AstIterVisitor;
import jkind.lustre.visitors.TypeIterVisitor;

public class TypesDefined extends AstIterVisitor {
	public static boolean check(Program program) {
		TypesDefined visitor = new TypesDefined();
		visitor.visit(program);
		return visitor.passed;
	}

	private final Set<String> defined = new HashSet<>();
	private boolean passed = true;

	@Override
	protected void visitTypeDefs(List<TypeDef> es) {
		for (TypeDef e : es) {
			defined.add(e.id);
		}
		super.visitTypeDefs(es);
	}

	@Override
	public Void visit(TypeDef e) {
		check(e.type);
		return null;
	}

	@Override
	public Void visit(Constant e) {
		if (e.type != null) {
			check(e.type);
		}
		return null;
	}

	@Override
	public Void visit(VarDecl e) {
		check(e.type);
		return null;
	}

	private void check(Type type) {
		type.accept(new TypeIterVisitor() {
			@Override
			public Void visit(NamedType e) {
				if (!e.isBuiltin() && !defined.contains(e.name)) {
					StdErr.error(e.location, "unknown type " + e.name);
					passed = false;
				}
				return null;
			}
		});
	}
}
