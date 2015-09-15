package jkind.translation;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import jkind.JKindException;
import jkind.lustre.ArrayAccessExpr;
import jkind.lustre.ArrayExpr;
import jkind.lustre.ArrayUpdateExpr;
import jkind.lustre.BinaryExpr;
import jkind.lustre.BoolExpr;
import jkind.lustre.CastExpr;
import jkind.lustre.CondactExpr;
import jkind.lustre.Equation;
import jkind.lustre.Expr;
import jkind.lustre.IdExpr;
import jkind.lustre.IfThenElseExpr;
import jkind.lustre.InductDataExpr;
import jkind.lustre.IntExpr;
import jkind.lustre.NodeCallExpr;
import jkind.lustre.QuantExpr;
import jkind.lustre.RealExpr;
import jkind.lustre.RecordAccessExpr;
import jkind.lustre.RecordExpr;
import jkind.lustre.RecordUpdateExpr;
import jkind.lustre.RecursiveFunction;
import jkind.lustre.TupleExpr;
import jkind.lustre.Type;
import jkind.lustre.UnaryExpr;
import jkind.lustre.VarDecl;
import jkind.lustre.visitors.ExprVisitor;
import jkind.sexp.Cons;
import jkind.sexp.Sexp;
import jkind.sexp.Symbol;
import jkind.util.Util;

public class RecursiveFunction2Sexp implements ExprVisitor<Sexp>{

    
    public static List<Sexp> constructRecursiveFunctions(List<RecursiveFunction> recFuns) {
     
        List<Sexp> sexps = new ArrayList<>();
        for(RecursiveFunction recFun : recFuns){
            sexps.add(constructRecursiveFunction(recFun));
        }
        
        return sexps;
    }
    
    private static Sexp constructRecursiveFunction(RecursiveFunction recFun){
        
        if(recFun.equations.size() != 1){
            throw new JKindException("Recursive function equations should be flattened by now");
        }
        
        List<Sexp> args = new ArrayList<>();
        
        for(VarDecl var : recFun.inputs){
            args.add(new Cons(var.id, new Symbol(Util.capitalize(var.type.toString()))));
        }
        Sexp argCons = new Cons(args);
        
        args = new ArrayList<>(); //I would do a "clear()" but this is messing up the debugger...
        args.add(new Symbol("define-fun-rec"));
        args.add(new Symbol(recFun.id));
        args.add(argCons);
        
        Equation eq = recFun.equations.get(0);
        args.add(type(recFun.output.type));
        args.add(eq.expr.accept(new RecursiveFunction2Sexp()));
        
        Sexp cons = new Cons(args);
        
        return cons;
    }
    
    @Override
    public Sexp visit(ArrayAccessExpr e) {
        throw new IllegalArgumentException("unsupported expression found in a recursive function");
    }

    @Override
    public Sexp visit(ArrayExpr e) {
        throw new IllegalArgumentException("unsupported expression found in a recursive function");
    }

    @Override
    public Sexp visit(ArrayUpdateExpr e) {
        throw new IllegalArgumentException("unsupported expression found in a recursive function");
    }

    @Override
    public Sexp visit(BinaryExpr e) {
        Sexp left = e.left.accept(this);
        Sexp right = e.right.accept(this);

        switch (e.op) {
        case NOTEQUAL:
        case XOR:
            return new Cons("not", new Cons("=", left, right));

        case ARROW:
            throw new IllegalArgumentException("arrows cannot appear in recursive functions");

        default:
            return new Cons(e.op.toString(), left, right);
        }
    }

    @Override
    public Sexp visit(BoolExpr e) {
        return Sexp.fromBoolean(e.value);
    }

    @Override
    public Sexp visit(CastExpr e) {
        throw new IllegalArgumentException("unsupported expression found in a recursive function");
    }

    @Override
    public Sexp visit(CondactExpr e) {
        throw new IllegalArgumentException("unsupported expression found in a recursive function");
    }

    @Override
    public Sexp visit(IdExpr e) {
        return new Symbol(e.id);
    }

    @Override
    public Sexp visit(IfThenElseExpr e) {
        return new Cons("ite", e.cond.accept(this), e.thenExpr.accept(this),
            e.elseExpr.accept(this));
    }

    @Override
    public Sexp visit(IntExpr e) {
        return Sexp.fromBigInt(e.value);
    }

    @Override
    public Sexp visit(NodeCallExpr e) {
        throw new IllegalArgumentException("unsupported expression found in a recursive function");
    }

    @Override
    public Sexp visit(RealExpr e) {
        Sexp numerator = Sexp.fromBigInt(e.value.unscaledValue());
        Sexp denominator = Sexp.fromBigInt(BigDecimal.TEN.pow(e.value.scale()).toBigInteger());
        return new Cons("/", numerator, denominator);
    }

    @Override
    public Sexp visit(RecordAccessExpr e) {
        throw new IllegalArgumentException("unsupported expression found in a recursive function");
    }

    @Override
    public Sexp visit(RecordExpr e) {
        throw new IllegalArgumentException("unsupported expression found in a recursive function");
    }

    @Override
    public Sexp visit(RecordUpdateExpr e) {
        throw new IllegalArgumentException("unsupported expression found in a recursive function");
    }

    @Override
    public Sexp visit(TupleExpr e) {
        throw new IllegalArgumentException("unsupported expression found in a recursive function");
    }

    @Override
    public Sexp visit(UnaryExpr e) {
        switch (e.op) {
        case PRE:
            throw new IllegalArgumentException("pre expressions cannot appear in recursive functions");
        case NEGATIVE:
            return new Cons("-", e.expr.accept(this));

        default:
            return new Cons(e.op.toString(), e.expr.accept(this));
        }
    }

    @Override
    public Sexp visit(InductDataExpr e) {
        List<Sexp> args = new ArrayList<>();
        for(Expr expr : e.args){
            args.add(expr.accept(this));
        }
        
        if(args.size() != 0){
        	return new Cons(e.name, args);
        }
        return new Symbol(e.name);
    }
    
    private static Symbol type(Type type) {
        return new Symbol(capitalize(Util.getName(type)));
    }

    private static String capitalize(String name) {
        return name.substring(0, 1).toUpperCase() + name.substring(1);
    }

	@Override
	public Sexp visit(QuantExpr e) {
		throw new JKindException("recursive functions cannot contain quantifiers");
	}

}
