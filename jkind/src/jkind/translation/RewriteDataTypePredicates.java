package jkind.translation;

import java.util.HashSet;
import java.util.Set;

import jkind.lustre.Expr;
import jkind.lustre.InductDataExpr;
import jkind.lustre.InductType;
import jkind.lustre.Program;
import jkind.lustre.TypeConstructor;
import jkind.lustre.TypeDef;
import jkind.lustre.visitors.AstMapVisitor;

public class RewriteDataTypePredicates extends AstMapVisitor{
	Set<String> constructorNames = new HashSet<>();
	public RewriteDataTypePredicates(Program program){
		for(TypeDef def : program.types){
			if(def.type instanceof InductType){
				InductType inductType = (InductType)def.type;
				for(TypeConstructor constructor : inductType.constructors){
					constructorNames.add(constructor.name);
				}
            }
        }
    }

    public static Program program(Program program) {
        return new RewriteDataTypePredicates(program).visit(program);
    }

    @Override
    public Expr visit(InductDataExpr data) {
        if (data.name.startsWith(InductDataExpr.CONSTRUCTOR_PREDICATE_PREFIX)){
			String withoutPrefix = data.name.substring(InductDataExpr.CONSTRUCTOR_PREDICATE_PREFIX.length());
			if(constructorNames.contains(withoutPrefix)){
				return new InductDataExpr("is-"+withoutPrefix, data.args);
			}
		}
		return data;
	}
	
}
