package jkind.translation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jkind.lustre.Expr;
import jkind.lustre.InductDataExpr;
import jkind.lustre.InductType;
import jkind.lustre.InductTypeElement;
import jkind.lustre.NodeCallExpr;
import jkind.lustre.Program;
import jkind.lustre.RecursiveFunction;
import jkind.lustre.TypeConstructor;
import jkind.lustre.TypeDef;
import jkind.lustre.visitors.AstMapVisitor;
import jkind.util.Util;

public class ReplaceNodesByInductivePredicates extends AstMapVisitor {

	private final Set<String> names = new HashSet<>();

	public static Program program(Program program){
		return new ReplaceNodesByInductivePredicates(program).visit(program);
	}
	
	public ReplaceNodesByInductivePredicates(Program program) {
		for (TypeDef def : program.types) {
			if (def.type instanceof InductType) {
				InductType inductType = (InductType) def.type;
				names.addAll(Util.inductDataTypeFunctions(inductType));
				names.addAll(Util.inductDataTypeConstructorPredicateNames(inductType));
			}
		}
		for(RecursiveFunction recFun : program.recFuns){
			names.add(recFun.id);
		}
	}
	
	@Override
	public Expr visit(NodeCallExpr e) {
		
		List<Expr> newArgs = new ArrayList<>();
		for(Expr arg : e.args){
			newArgs.add(arg.accept(this));
		}
		
		if(newArgs.size() == 1 && names.contains(e.node)){
			return new InductDataExpr(e.node, newArgs);
		}
		return e;
	}
	
}
