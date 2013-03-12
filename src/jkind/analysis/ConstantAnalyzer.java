package jkind.analysis;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jkind.lustre.BinaryExpr;
import jkind.lustre.BinaryOp;
import jkind.lustre.BoolExpr;
import jkind.lustre.Constant;
import jkind.lustre.ExprVisitor;
import jkind.lustre.IdExpr;
import jkind.lustre.IfThenElseExpr;
import jkind.lustre.IntExpr;
import jkind.lustre.Node;
import jkind.lustre.NodeCallExpr;
import jkind.lustre.RealExpr;
import jkind.lustre.UnaryExpr;
import jkind.lustre.UnaryOp;
import jkind.lustre.VarDecl;
import jkind.translation.Util;

public class ConstantAnalyzer implements ExprVisitor<Boolean> {
	private Set<String> constants;

	public ConstantAnalyzer(Node node, List<Constant> constantDecls) {
		constants = new HashSet<String>();
		addConstantsToSet(constantDecls);
		removeShadowedConstants(Util.getVarDecls(node));
	}
	
	public ConstantAnalyzer(List<Constant> constantDecls) {
		constants = new HashSet<String>();
		addConstantsToSet(constantDecls);
	}
	
	private void addConstantsToSet(List<Constant> constantDecls) {
		for (Constant c : constantDecls) {
			constants.add(c.id);
		}
	}

	private void removeShadowedConstants(List<VarDecl> decls) {
		for (VarDecl decl : decls) {
			constants.remove(decl.id);
		}
	}

	@Override
	public Boolean visit(BinaryExpr e) {
		if (e.op == BinaryOp.ARROW) {
			return false;
		} else {
			return e.left.accept(this) && e.right.accept(this);
		}
	}

	@Override
	public Boolean visit(BoolExpr e) {
		return true;
	}

	@Override
	public Boolean visit(IdExpr e) {
		return constants.contains(e.id);
	}

	@Override
	public Boolean visit(IfThenElseExpr e) {
		return e.cond.accept(this) && e.thenExpr.accept(this) && e.elseExpr.accept(this);
	}

	@Override
	public Boolean visit(IntExpr e) {
		return true;
	}

	@Override
	public Boolean visit(NodeCallExpr e) {
		return false;
	}
	
	@Override
	public Boolean visit(RealExpr e) {
		return true;
	}

	@Override
	public Boolean visit(UnaryExpr e) {
		if (e.op == UnaryOp.PRE) {
			return false;
		} else {
			return e.expr.accept(this);
		}
	}
}
