package jkind.analysis;

import jkind.StdErr;
import jkind.lustre.ArrayType;
import jkind.lustre.Program;
import jkind.lustre.visitors.TypeIterVisitor;

public class ArraysNonempty extends TypeIterVisitor {
	public static boolean check(Program program) {
		ArraysNonempty visitor = new ArraysNonempty();
		visitor.visitProgram(program);
		return visitor.nonempty;
	}

	private boolean nonempty = true;

	@Override
	public Void visit(ArrayType e) {
		if (e.size == 0) {
			StdErr.error(e.location, "array is empty");
			nonempty = false;
		}
		return null;
	}
}
