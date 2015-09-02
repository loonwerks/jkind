package jkind.translation;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import jkind.analysis.IdGatherer;
import jkind.analysis.IdRewriter;
import jkind.lustre.Equation;
import jkind.lustre.Expr;
import jkind.lustre.IdExpr;
import jkind.lustre.RecursiveFunction;
import jkind.lustre.VarDecl;

public class RecursiveFunctionLocalReWriter implements IdRewriter {

    private Map<String, Expr> id2ExprMap = new HashMap<>();
    
    public RecursiveFunctionLocalReWriter(RecursiveFunction recFun) {
        
        Set<String> nonLocalIds = new HashSet<>();
        
        for(VarDecl var : recFun.inputs){
            nonLocalIds.add(var.id);
        }
        nonLocalIds.add(recFun.output.id);
        
        Map<String, Set<String>> dependencyMap = new HashMap<>();
        for(Equation eq : recFun.equations){
            Set<String> ids = eq.expr.accept(new IdGatherer());
            ids.removeAll(nonLocalIds);
            dependencyMap.put(eq.lhs.get(0).id, ids);
        }
        
        
    }
    
    
    @Override
    public Expr rewrite(IdExpr id) {
        return null;
    }

}
