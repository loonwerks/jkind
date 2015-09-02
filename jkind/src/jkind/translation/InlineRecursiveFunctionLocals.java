package jkind.translation;

import java.util.ArrayList;
import java.util.List;

import jkind.JKindException;
import jkind.analysis.IdRewriteVisitor;
import jkind.analysis.IdRewriter;
import jkind.lustre.ArrayAccessExpr;
import jkind.lustre.ArrayExpr;
import jkind.lustre.ArrayUpdateExpr;
import jkind.lustre.BinaryExpr;
import jkind.lustre.BoolExpr;
import jkind.lustre.CastExpr;
import jkind.lustre.CondactExpr;
import jkind.lustre.Constant;
import jkind.lustre.Equation;
import jkind.lustre.Expr;
import jkind.lustre.IdExpr;
import jkind.lustre.IfThenElseExpr;
import jkind.lustre.InductDataExpr;
import jkind.lustre.IntExpr;
import jkind.lustre.Location;
import jkind.lustre.Node;
import jkind.lustre.NodeCallExpr;
import jkind.lustre.Program;
import jkind.lustre.RealExpr;
import jkind.lustre.RecordAccessExpr;
import jkind.lustre.RecordExpr;
import jkind.lustre.RecordUpdateExpr;
import jkind.lustre.RecursiveFunction;
import jkind.lustre.TupleExpr;
import jkind.lustre.TypeDef;
import jkind.lustre.UnaryExpr;
import jkind.lustre.visitors.ExprVisitor;

public class InlineRecursiveFunctionLocals extends IdRewriteVisitor{

    public InlineRecursiveFunctionLocals(IdRewriter rewriter) {
        super(rewriter);
        // TODO Auto-generated constructor stub
    }

    public static Program program(Program program){
        
        return program;
//        List<RecursiveFunction> recFuns = new ArrayList<>();
//        
//        for(RecursiveFunction recFun : program.recFuns){
//            
//            
//            
//            InlineRecursiveFunctionLocals inliner = new InlineRecursiveFunctionLocals(rewriter);
//            List<Equation> finalEq = new ArrayList<>();
//            for(Equation eq : recFun.equations){
//                String id = eq.lhs.get(0).id;
//                if(id.equals(recFun.output.id)){
//                   finalEq.add(new Equation(new IdExpr(id),eq.expr.accept(inliner)));
//                }
//            }
//            
//            recFuns.add(new RecursiveFunction(recFun.id, recFun.inputs, null, recFun.output, finalEq));
//        }
//        
//        
//        return new Program(program.location, program.types, program.constants,
//                program.nodes, program.main, recFuns);
    }
   

}
