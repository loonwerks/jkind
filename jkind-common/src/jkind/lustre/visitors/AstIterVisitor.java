package jkind.lustre.visitors;

import java.util.List;

import jkind.lustre.*;

public class AstIterVisitor extends ExprIterVisitor implements AstVisitor<Void, Void> {
	@Override
	public Void visit(Constant e) {
		e.expr.accept(this);
		return null;
	}

	@Override
	public Void visit(Equation e) {
		e.expr.accept(this);
		return null;
	}

	@Override
	public Void visit(Function e) {
		visitVarDecls(e.inputs);
		visitVarDecls(e.outputs);
		return null;
	}

	@Override
	public Void visit(Node e) {
		visitVarDecls(e.inputs);
		visitVarDecls(e.outputs);
		visitVarDecls(e.locals);
		visitEquations(e.equations);
		visitAssertions(e.assertions);
		return null;
	}

	@Override
	public Void visit(RepairNode e) {
		visitVarDecls(e.inputs);
		visitVarDecls(e.holeinputs);
		visitVarDecls(e.outputs);
		visitVarDecls(e.locals);
		visitEquations(e.equations);
		visitAssertions(e.assertions);
		return null;
	}


	protected void visitVarDecls(List<VarDecl> es) {
		for (VarDecl e : es) {
			visit(e);
		}
	}

	protected void visitEquations(List<Equation> es) {
		for (Equation e : es) {
			visit(e);
		}
	}

	protected void visitAssertions(List<Expr> es) {
		visitExprs(es);
	}

	@Override
	public Void visit(Program e) {
		visitTypeDefs(e.types);
		visitConstants(e.constants);
		visitFunctions(e.functions);
		visitNodes(e.nodes);
		visitRepairNodes(e.repairNodes);
		return null;
	}

	protected void visitTypeDefs(List<TypeDef> es) {
		for (TypeDef e : es) {
			visit(e);
		}
	}

	protected void visitConstants(List<Constant> es) {
		for (Constant e : es) {
			visit(e);
		}
	}

	protected void visitFunctions(List<Function> es) {
		for (Function e : es) {
			visit(e);
		}
	}

	protected void visitNodes(List<Node> es) {
		for (Node e : es) {
			visit(e);
		}
	}

	protected void visitRepairNodes(List<RepairNode> es) {
		for (RepairNode e : es) {
			visit(e);
		}
	}
	@Override
	public Void visit(TypeDef e) {
		return null;
	}

	@Override
	public Void visit(VarDecl e) {
		return null;
	}

	@Override
	public Void visit(Contract contract) {
		visitExprs(contract.requires);
		visitExprs(contract.ensures);
		return null;
	}
}
