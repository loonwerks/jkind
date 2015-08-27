package jkind.translation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jkind.lustre.Equation;
import jkind.lustre.Expr;
import jkind.lustre.InductDataExpr;
import jkind.lustre.InductType;
import jkind.lustre.Node;
import jkind.lustre.Program;
import jkind.lustre.TypeConstructor;
import jkind.lustre.TypeDef;
import jkind.lustre.visitors.AstMapVisitor;
import jkind.lustre.visitors.ExprMapVisitor;

public class rewriteDataTypePredicates extends ExprMapVisitor{
	Set<String> constructorNames = new HashSet<>();
	public rewriteDataTypePredicates(Program program){
		for(TypeDef def : program.types){
			if(def.type instanceof InductType){
				InductType inductType = (InductType)def.type;
				for(TypeConstructor constructor : inductType.constructors){
					constructorNames.add(constructor.name);
				}
			}
		}
	}
	
	public static Program program(Program program){
		List<Node> newNodes = new ArrayList<>();
		rewriteDataTypePredicates visitor = new rewriteDataTypePredicates(program);
		for(Node node : program.nodes){
			List<Equation> newEqs = new ArrayList<>();
			for(Equation eq : node.equations){
				newEqs.add(new Equation(eq.lhs, eq.expr.accept(visitor)));
			}
			newNodes.add(new Node(node.location, node.id, node.inputs, node.outputs,
					node.locals, newEqs, node.properties, node.assertions, node.realizabilityInputs, node.contracts));
		}
		return new Program(program.types, program.constants, newNodes);
	}
	
	@Override
	public Expr visit(InductDataExpr data){
		if(data.name.startsWith(InductDataExpr.CONSTRUCTOR_PREDICATE_PREFIX)){
			String withoutPrefix = data.name.substring(InductDataExpr.CONSTRUCTOR_PREDICATE_PREFIX.length());
			if(constructorNames.contains(withoutPrefix)){
				return new InductDataExpr("is-"+withoutPrefix, data.args);
			}
		}
		return data;
	}
	
}
