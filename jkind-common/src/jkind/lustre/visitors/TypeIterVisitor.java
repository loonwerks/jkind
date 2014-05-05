package jkind.lustre.visitors;

import jkind.lustre.ArrayType;
import jkind.lustre.EnumType;
import jkind.lustre.NamedType;
import jkind.lustre.Node;
import jkind.lustre.Program;
import jkind.lustre.RecordType;
import jkind.lustre.SubrangeIntType;
import jkind.lustre.TupleType;
import jkind.lustre.Type;
import jkind.lustre.TypeDef;
import jkind.lustre.VarDecl;
import jkind.util.Util;

public class TypeIterVisitor implements TypeVisitor<Void> {
	@Override
	public Void visit(ArrayType e) {
		e.base.accept(this);
		return null;
	}

	@Override
	public Void visit(EnumType e) {
		return null;
	}

	@Override
	public Void visit(NamedType e) {
		return null;
	}

	@Override
	public Void visit(RecordType e) {
		for (Type type : e.fields.values()) {
			type.accept(this);
		}
		return null;
	}

	@Override
	public Void visit(TupleType e) {
		for (Type t : e.types) {
			t.accept(this);
		}
		return null;
	}

	@Override
	public Void visit(SubrangeIntType e) {
		return null;
	}

	public void visitProgram(Program program) {
		for (TypeDef def : program.types) {
			def.type.accept(this);
		}

		for (Node node : program.nodes) {
			for (VarDecl decl : Util.getVarDecls(node)) {
				decl.type.accept(this);
			}
		}
	}
}
