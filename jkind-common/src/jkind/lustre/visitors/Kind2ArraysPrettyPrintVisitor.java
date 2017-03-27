package jkind.lustre.visitors;

import jkind.lustre.ArrayType;

import jkind.lustre.Type;
import jkind.lustre.VarDecl;

public class Kind2ArraysPrettyPrintVisitor extends PrettyPrintVisitor {
	@Override
	public Void visit(VarDecl varDecl) {
		Type type = varDecl.type;
		if (type instanceof ArrayType) {
			StringBuilder sb = new StringBuilder("");
			while (type instanceof ArrayType) {
				ArrayType arrayType = (ArrayType) type;
				StringBuilder thisStr = new StringBuilder("^" + arrayType.size);
				thisStr.append(sb);
				sb = thisStr;
				type = arrayType.base;
			}
			write(varDecl.id);
			write(" : ");
			write(type);
			write(sb);
			return null;
		} else {
			return super.visit(varDecl);
		}
	}
}
