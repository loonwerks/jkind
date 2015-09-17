package jkind.translation;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import jkind.lustre.Expr;
import jkind.lustre.IdExpr;
import jkind.lustre.InductDataExpr;
import jkind.lustre.InductType;
import jkind.lustre.Program;
import jkind.lustre.TypeConstructor;
import jkind.lustre.TypeDef;
import jkind.lustre.visitors.AstMapVisitor;

public class Replace0AryTypeConstructors extends AstMapVisitor{
    
    private final Set<String> zeroAryConstructorNames = new HashSet<>();
    
    public Replace0AryTypeConstructors(Program program) {
        for(TypeDef def : program.types){
            if(def.type instanceof InductType){
                InductType inductType = (InductType)def.type;
                for(TypeConstructor constructor : inductType.constructors){
                    if(constructor.elements.size() == 0){
                        zeroAryConstructorNames.add(constructor.name);
                    }
                }
            }
        }
    }

    public static Program program(Program program){
        return new Replace0AryTypeConstructors(program).visit(program);
    }
    
    @Override
    public Expr visit(IdExpr id){
        if(zeroAryConstructorNames.contains(id.id)){
            return new InductDataExpr(id.id, Collections.emptyList());
        }
        return id;
    }

}
