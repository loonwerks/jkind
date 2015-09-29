package jkind.translation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jkind.JKindException;
import jkind.lustre.BoolExpr;
import jkind.lustre.Equation;
import jkind.lustre.Expr;
import jkind.lustre.IdExpr;
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
    public final Map<String, Expr> propertyExprs = new HashMap<>();
    
    public InductiveDataTypeSpecification(Node node, DependencyMap dependencyMap, Program program) {
        super(removeQuantifedProps(node), dependencyMap);
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
    
    private static Node removeQuantifedProps(Node node){
    	//CVC4 does not do well with quantifiers in the transition relation
    	//we replace quantified properties with themselves in the node and then
    	//handle them differently in the QuantifedBMC and QuantifiedKInduction Engines
    	List<Equation> newEqs = new ArrayList<>();
    	for(Equation eq : node.equations){
    		String lhs = eq.lhs.get(0).id;
    		if(node.properties.contains(lhs)){
    			newEqs.add(new Equation(new IdExpr(lhs), new IdExpr(lhs)));
    		}else{
    			newEqs.add(eq);
    		}
    	}
    	return new Node(node.location, node.id, node.inputs, node.outputs, node.locals, 
    			newEqs, node.properties, node.assertions, node.realizabilityInputs, node.contracts);
    }

	private void setPropertyExprs(Node node) {
		for (Equation eq : node.equations) {
			//if(eq.expr instanceof QuantExpr){
				if(eq.lhs.size() != 1){
					throw new JKindException("There should only be one LHS variable");
				}
				String prop = eq.lhs.get(0).id;
				if(node.properties.contains(prop)){
					propertyExprs.put(prop, eq.expr);
				}
			//}
		}

    }
    
}
