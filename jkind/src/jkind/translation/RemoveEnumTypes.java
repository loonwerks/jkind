package jkind.translation;

import java.math.BigInteger;

import jkind.lustre.EnumType;
import jkind.lustre.Node;
import jkind.lustre.SubrangeIntType;
import jkind.lustre.VarDecl;
import jkind.lustre.visitors.AstMapVisitor;

public class RemoveEnumTypes extends AstMapVisitor {
	public static Node node(Node node) {
		return new RemoveEnumTypes().visit(node);
	}

	@Override
	public VarDecl visit(VarDecl e) {
		if (e.type instanceof EnumType) {
			EnumType et = (EnumType) e.type;
			BigInteger high = BigInteger.valueOf(et.values.size() - 1);
			return new VarDecl(e.id, new SubrangeIntType(BigInteger.ZERO, high));
		} else {
			return e;
		}
	}
}
