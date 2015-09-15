package jkind.analysis;

import java.util.List;

import jkind.Output;
import jkind.lustre.ArrayAccessExpr;
import jkind.lustre.ArrayExpr;
import jkind.lustre.ArrayUpdateExpr;
import jkind.lustre.BinaryExpr;
import jkind.lustre.BinaryOp;
import jkind.lustre.BoolExpr;
import jkind.lustre.CastExpr;
import jkind.lustre.CondactExpr;
import jkind.lustre.Equation;
import jkind.lustre.IdExpr;
import jkind.lustre.IfThenElseExpr;
import jkind.lustre.InductDataExpr;
import jkind.lustre.IntExpr;
import jkind.lustre.Location;
import jkind.lustre.NodeCallExpr;
import jkind.lustre.Program;
import jkind.lustre.QuantExpr;
import jkind.lustre.RealExpr;
import jkind.lustre.RecordAccessExpr;
import jkind.lustre.RecordExpr;
import jkind.lustre.RecordUpdateExpr;
import jkind.lustre.RecursiveFunction;
import jkind.lustre.TupleExpr;
import jkind.lustre.UnaryExpr;
import jkind.lustre.UnaryOp;
import jkind.lustre.visitors.ExprVisitor;

public class RecursiveExprChecker implements ExprVisitor<Void>{

    private boolean passed = true;
    
    public static boolean check(Program program){
        return new RecursiveExprChecker().visitRecursiveFunctionDef(program.recFuns);
    }
    
    public boolean visitRecursiveFunctionDef(List<RecursiveFunction> recFuns){
        
        for (RecursiveFunction recFun : recFuns) {
            
            if(recFun.locals.size() != 0){
                error(recFun.location, "we do not support local variables in recursive functions yet");
                passed = false;
            }
            for (Equation eq : recFun.equations) {
                eq.expr.accept(this);
            }
        }
        return passed;
    }
    
    
    @Override
    public Void visit(ArrayAccessExpr e) {
        error(e.location, "recusive functions cannot contain array access expressions");
        e.accept(this);
        return null;
    }

    @Override
    public Void visit(ArrayExpr e) {
        error(e.location, "recursive functions cannot contain array expressions");
        return null;
    }

    @Override
    public Void visit(ArrayUpdateExpr e) {
        error(e.location, "recursive functions cannot contain array update expressions");
        return null;
    }

    @Override
    public Void visit(BinaryExpr e) {
        if(e.op == BinaryOp.ARROW){
            error(e.location, "recursive functions cannot contain arrow expressions");
        }
        e.left.accept(this);
        e.right.accept(this);
        return null;
    }

    @Override
    public Void visit(BoolExpr e) {
        return null;
    }

    @Override
    public Void visit(CastExpr e) {
        error(e.location, "I do not think that recursive functions can contain cast expressions? Can they?");
        return null;
    }

    @Override
    public Void visit(CondactExpr e) {
        error(e.location, "recursive functions cannot contain condact expressions");
        return null;
    }

    @Override
    public Void visit(IdExpr e) {
        return null;
    }

    @Override
    public Void visit(IfThenElseExpr e) {
        return null;
    }

    @Override
    public Void visit(IntExpr e) {
        return null;
    }

    @Override
    public Void visit(NodeCallExpr e) {
        error(e.location, "recursive functions cannot contain node call expressions");
        return null;
    }

    @Override
    public Void visit(RealExpr e) {
        return null;
    }

    @Override
    public Void visit(RecordAccessExpr e) {
        error(e.location, "recursive functions cannot contain record access expressions");
        return null;
    }

    @Override
    public Void visit(RecordExpr e) {
        error(e.location, "recursive functions cannot contain record expressions");
        return null;
    }

    @Override
    public Void visit(RecordUpdateExpr e) {
        error(e.location, "recursive functions cannot contain record update expressions");
        return null;
    }

    @Override
    public Void visit(TupleExpr e) {
        error(e.location, "recursive functions cannot contain tuple expressions");
        return null;
    }

    @Override
    public Void visit(UnaryExpr e) {
        if(e.op == UnaryOp.PRE){
            error(e.location, "recursive functions cannot contain pre expressions");
        }
        return null;
    }

    @Override
    public Void visit(InductDataExpr e) {
        return null;
    }
    
    private void error(Location location, String message) {
        passed = false;
        Output.error(location, message);
    }

	@Override
	public Void visit(QuantExpr e) {
		error(e.location, "recursive functions cannot contain quantifiers");
		return null;
	}

}
