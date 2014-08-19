package jkind.analysis;

import java.util.HashSet;
import java.util.Set;

import jkind.lustre.BinaryExpr;
import jkind.lustre.BinaryOp;
import jkind.lustre.CondactExpr;
import jkind.lustre.Constant;
import jkind.lustre.EnumType;
import jkind.lustre.IdExpr;
import jkind.lustre.NodeCallExpr;
import jkind.lustre.Program;
import jkind.lustre.visitors.ExprConjunctiveVisitor;
import jkind.util.Util;

public class ConstantAnalyzer extends ExprConjunctiveVisitor {
	private final Set<String> constants = new HashSet<>();

	public ConstantAnalyzer() {
	}

	public ConstantAnalyzer(Program program) {
		for (Constant c : program.constants) {
			constants.add(c.id);
		}
		for (EnumType et : Util.getEnumTypes(program.types)) {
			for (String id : et.values) {
				constants.add(id);
			}
		}
	}

	@Override
	public Boolean visit(BinaryExpr e) {
		return e.op != BinaryOp.ARROW && super.visit(e);
	}

	@Override
	public Boolean visit(CondactExpr e) {
		return false;
	}

	@Override
	public Boolean visit(IdExpr e) {
		return constants.contains(e.id);
	}

	@Override
	public Boolean visit(NodeCallExpr e) {
		return false;
	}
}
