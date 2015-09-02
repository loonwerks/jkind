package jkind.translation;

import java.util.List;

import jkind.lustre.Node;
import jkind.lustre.RecursiveFunction;
import jkind.sexp.Sexp;
import jkind.slicing.DependencyMap;
import jkind.util.Util;

public class RecursiveFunctionSpecification extends Specification{
    
    public final List<Sexp> functions;
    public RecursiveFunctionSpecification(Node node, DependencyMap dependencyMap, List<RecursiveFunction> recFuns) {
        super(node, dependencyMap);
        this.functions = RecursiveFunction2Sexp.constructRecursiveFunctions(recFuns);
    }
    
}
