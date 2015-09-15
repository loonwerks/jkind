package jkind.analysis;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jkind.Output;
import jkind.lustre.Node;
import jkind.lustre.Program;
import jkind.lustre.QuantExpr;
import jkind.lustre.VarDecl;
import jkind.lustre.visitors.AstIterVisitor;

public class QuantifiedVariableChecker extends AstIterVisitor{

	private static boolean passed = true;
	private Set<String> definedVars = new HashSet<>();
	
	private QuantifiedVariableChecker(Set<String> definedVars){
		this.definedVars.addAll(definedVars);
	}
	
	public static boolean check(Program program){
		passed = true;
		for(Node node : program.nodes){
			check(node);
		}
		return passed;
	}
	
	private static void check(Node node){
		List<VarDecl> nodeVars = new ArrayList<>();
		nodeVars.addAll(node.inputs);
		nodeVars.addAll(node.locals);
		nodeVars.addAll(node.outputs);
		node.accept(new QuantifiedVariableChecker(varNames(nodeVars)));
	}
	
	private static Set<String> varNames(List<VarDecl> vars){
		Set<String> names = new HashSet<>();
		for(VarDecl var : vars){
			names.add(var.id);
		}
		return names;
	}
	
	@Override
	public Void visit(QuantExpr e){
		
		for(VarDecl var : e.boundVars){
			if(definedVars.contains(var.id)){
				passed = false;
				Output.error(e.location, "quantifier redefines variable '"+var.id+"'");
			}
		}
		
		//check that we don't define a bound variable more than once
		for(int i = 0; i < e.boundVars.size(); i++){
			VarDecl outer = e.boundVars.get(i);
			for(int j = i+1; j < e.boundVars.size(); j++){
				VarDecl inner = e.boundVars.get(j);
				if(outer.id.equals(inner.id)){
					passed = false;
					Output.error(e.location, "quantifer defines variable '"+outer.id+"' multiple times");
				}
			}
		}
		
		Set<String> originalVars = new HashSet<>();
		originalVars.addAll(definedVars);
		definedVars.addAll(varNames(e.boundVars));
		e.expr.accept(this);
		definedVars = originalVars;
		return null;
	}
		
}
