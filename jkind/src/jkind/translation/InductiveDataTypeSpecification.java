package jkind.translation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jkind.JKindException;
import jkind.lustre.Equation;
import jkind.lustre.Expr;
import jkind.lustre.InductType;
import jkind.lustre.Node;
import jkind.lustre.Program;
import jkind.lustre.QuantExpr;
import jkind.lustre.TypeDef;
import jkind.sexp.Sexp;
import jkind.slicing.DependencyMap;
import jkind.util.Util;

public class InductiveDataTypeSpecification extends Specification{
    
    public final List<Sexp> functions;
    public final List<InductType> inductTypes;
    public final Map<String, Expr> quantifiedPropertyExprs = new HashMap<>();
    
    public InductiveDataTypeSpecification(Node node, DependencyMap dependencyMap, Program program) {
        super(node, dependencyMap);
        this.functions = RecursiveFunction2Sexp.constructRecursiveFunctions(program.recFuns);
        List<InductType> inductTypes = new ArrayList<>();
        for(TypeDef def : program.types){
            if(def.type instanceof InductType){
                inductTypes.add((InductType) def.type);
            }
        }
        this.inductTypes = Util.safeList(inductTypes);
        
        setPropertyExprs(node);
    }

	private void setPropertyExprs(Node node) {
		for (Equation eq : node.equations) {
			if(eq.expr instanceof QuantExpr){
				if(eq.lhs.size() != 1){
					throw new JKindException("There should only be one LHS variable");
				}
				String prop = eq.lhs.get(0).id;
				if(node.properties.contains(prop)){
					quantifiedPropertyExprs.put(prop, eq.expr);
				}
			}
		}

    }
    
}
