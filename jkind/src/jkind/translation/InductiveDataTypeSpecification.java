package jkind.translation;

import java.util.ArrayList;
import java.util.List;

import jkind.lustre.InductType;
import jkind.lustre.Node;
import jkind.lustre.Program;
import jkind.lustre.TypeDef;
import jkind.sexp.Sexp;
import jkind.slicing.DependencyMap;
import jkind.util.Util;

public class InductiveDataTypeSpecification extends Specification{
    
    public final List<Sexp> functions;
    public final List<InductType> inductTypes;
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
    }
    
}
