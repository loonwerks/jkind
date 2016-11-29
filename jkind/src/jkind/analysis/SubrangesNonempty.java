package jkind.analysis;

import jkind.StdErr;
import jkind.lustre.Program;
import jkind.lustre.SubrangeIntType;
import jkind.lustre.visitors.TypeIterVisitor;

public class SubrangesNonempty extends TypeIterVisitor {
	public static boolean check(Program program) {
		SubrangesNonempty visitor = new SubrangesNonempty();
		visitor.visitProgram(program);
		return visitor.nonempty;
	}

	private boolean nonempty = true;

	@Override
	public Void visit(SubrangeIntType e) {
		if (e.high.compareTo(e.low) < 0) {
			StdErr.error(e.location, "subrange is empty");
			nonempty = false;
		}
		return null;
	}
}
