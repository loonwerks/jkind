package jkind.engines.ivcs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import jkind.JKind;
import jkind.JKindException;
import jkind.lustre.Equation;
import jkind.lustre.Expr;
import jkind.lustre.IdExpr;
import jkind.lustre.NamedType;
import jkind.lustre.Node;
import jkind.lustre.VarDecl;
import jkind.lustre.builders.NodeBuilder;
import jkind.sexp.Symbol;
import jkind.util.LinkedBiMap;
import jkind.util.Util;

public class IvcUtil {
	
	public static Node normalizeAssertions(Node node) {   
		List<VarDecl> locals = new ArrayList<>(node.locals); 
		List<Equation> equations = new ArrayList<>(node.equations);
		List<Expr> assertions = new ArrayList<>(node.assertions);
		 
		
		Iterator<Expr> iter = assertions.iterator();
		int id = 0;
		List<IdExpr> newAssertions = new ArrayList<>();
		
		while (iter.hasNext()) {
			Expr asr = iter.next();
			if (! (asr instanceof IdExpr)) {
				newAssertions.add(defineNewEquation(asr, locals, equations, JKind.EQUATION_NAME + id));
				id ++;
				iter.remove(); 
			}
		}
		assertions.addAll(newAssertions);

		NodeBuilder builder = new NodeBuilder(node); 
		builder.clearLocals().addLocals(locals); 
		builder.clearEquations().addEquations(equations);
		builder.clearAssertions().addAssertions(assertions);  
		return builder.build();
	}
	
	public static List<String> getAllAssigned(Node node) {
		List<String> result = new ArrayList<>();
		result.addAll(Util.getIds(node.locals));
		result.addAll(Util.getIds(node.outputs));
		return result;
	}

	public static Node setIvcArgs(Node node, List<String> newIvc) {
		return new NodeBuilder(node).clearIvc().addIvcs(newIvc).build();
	}	
	
	public static Node unassign(Node node, String v, String property) {
		List<VarDecl> inputs = new ArrayList<>(node.inputs);
		inputs.add(new VarDecl(v, Util.getTypeMap(node).get(v)));
		List<VarDecl> locals = removeVariable(node.locals, v);
		List<VarDecl> outputs = removeVariable(node.outputs, v);

		List<Equation> equations = new ArrayList<>(node.equations);
		Iterator<Equation> iter = equations.iterator();
		while (iter.hasNext()) {
			Equation eq = iter.next();
			if (eq.lhs.get(0).id.equals(v)) {
				iter.remove();
			}
		}

		NodeBuilder builder = new NodeBuilder(node);
		builder.clearIvc();
		builder.clearInputs().addInputs(inputs);
		builder.clearProperties();
		builder.addProperty(property);
		builder.clearLocals().addLocals(locals);
		builder.clearOutputs().addOutputs(outputs);
		builder.clearEquations().addEquations(equations);
		return builder.build();
	}
	
	public static List<VarDecl> removeVariables(List<VarDecl> varDecls, List<String> vars) {
		List<VarDecl> result = new ArrayList<>(varDecls);
		Iterator<VarDecl> iter = result.iterator();
		while (iter.hasNext()) {
			if (vars.contains(iter.next().id)) {
				iter.remove(); 
			}
		}
		return result;
	}
	
	public static List<VarDecl> removeVariable(List<VarDecl> varDecls, String v) {
		List<VarDecl> result = new ArrayList<>(varDecls);
		Iterator<VarDecl> iter = result.iterator();
		while (iter.hasNext()) {
			if (iter.next().id.equals(v)) {
				iter.remove();
			}
		}
		return result;
	}
	
	public static IdExpr defineNewEquation(Expr rightSide, List<VarDecl> locals, List<Equation> equations, String newVar) {
		locals.add(new VarDecl(newVar, NamedType.BOOL));
		IdExpr ret = new IdExpr(newVar);
		equations.add(new Equation(ret, rightSide));
		 
		return ret;
	}
	
	public static Set<String> trimNode(Collection<String> set) {
		Set<String> ret = new HashSet<>();
		for (String e : set) {
			ret.add(e.replaceAll("~[0-9]+", ""));
		}
		return ret;
	}
	
	public static Set<String> findRightSide(Set<String> initialIvc, boolean allAssigned, List<Equation> equations) {
		Set<String> ivc = new HashSet<>(initialIvc);
		if(allAssigned){
			Set<String> itr = new HashSet<>(ivc);
			for(String core : itr){
				if(core.contains(JKind.EQUATION_NAME)){
					for (Equation eq : equations){
						if(core.equals(eq.lhs.get(0).id)){
							ivc.remove(core);
							ivc.add("assert "+ eq.expr.toString());
						}
					}
				}
			}
		}
		return ivc;
	}
	
	protected static VarDecl unTrim(String v, Node node) {
		Set<VarDecl> args = new HashSet<>();
		args.addAll(node.locals);
		args.addAll(node.outputs);
		for(VarDecl var : args){
			if(trimVar(var.id).equals(v)){
				return var;
			}
		}
		return null;
	}
	protected static String trimVar(String v) {
		return v.replaceAll("~[0-9]+", "");
	}
	
	protected static Expr getInvariantByName(String name, List<Expr> invariants) {
		for (Expr invariant : invariants) {
			if (invariant.toString().equals(name)) {
				return invariant;
			}
		}
		throw new JKindException("Unable to find property " + name + " during reduction\n"
				+ " try to re-run the process with  -pdr_max 0  option.");
	}

	protected static Set<String> getIvcNames(LinkedBiMap<String, Symbol> ivcMap, List<Symbol> symbols) {
		Set<String> result = new HashSet<>();
		for (Symbol s : symbols) {
			result.add(ivcMap.inverse().get(s));
		}
		return result;
	}
	
	protected static List<Symbol> getIvcLiterals(LinkedBiMap<String, Symbol> ivcMap, Collection<String> set) {
		List<Symbol> result = new ArrayList<>();
		for (String ivc : set) {
			result.add(ivcMap.get(ivc));
		}
		return result;
	}
	
	protected static Node unassign(Node node, Set<String> wantedElem, List<String> deactivate, String property) {
		List<VarDecl> inputs = new ArrayList<>(node.inputs);
		
		for(String v : deactivate){
			inputs.add(new VarDecl(v, Util.getTypeMap(node).get(v))); 
		}

		List<VarDecl> locals = removeVariables(node.locals, deactivate);
		List<VarDecl> outputs = removeVariables(node.outputs, deactivate);
		List<Equation> equations = new ArrayList<>(node.equations);
		Iterator<Equation> iter = equations.iterator();
		while (iter.hasNext()) {
			Equation eq = iter.next();
			if (deactivate.contains(eq.lhs.get(0).id)) {
				iter.remove(); 
			}
		} 
     
		NodeBuilder builder = new NodeBuilder(node);
		builder.clearInputs().addInputs(inputs);
		builder.clearLocals().addLocals(locals);
		builder.clearOutputs().addOutputs(outputs);
		builder.clearEquations().addEquations(equations); 
		builder.clearIvc().addIvcs(new ArrayList<>(wantedElem));
		builder.clearProperties().addProperty(property);
		return builder.build();
	}
	
	public static Node overApproximateWithIvc(Node node, Set<String> ivc, String property) { 
		List<Equation> equations = removeEquations(node.equations, ivc); 
		List<VarDecl> inputs = new ArrayList<>(node.inputs); 
		List<VarDecl> locals = keepVariables(node.locals, equations, inputs);
		List<VarDecl> outputs = keepVariables(node.outputs, equations, inputs); 
		
		NodeBuilder builder = new NodeBuilder(node); 
		builder.clearInputs().addInputs(inputs);
		builder.clearProperties().addProperty(property);
		builder.clearLocals().addLocals(locals);
		builder.clearOutputs().addOutputs(outputs);
		builder.clearEquations().addEquations(equations); 
		return builder.build();
	}

	protected static List<VarDecl> keepVariables(List<VarDecl> vars, List<Equation> equations, List<VarDecl> inputs) {
		Set<VarDecl> ret = new HashSet<>();
		List<String> left = new ArrayList<>(); 
		
		for(Equation eq : equations){
			left.add(eq.lhs.get(0).id); 
		}
		for(VarDecl v : vars){
			if(left.contains(v.id)){
				ret.add(v);
			}else{
				inputs.add(v);
			}
		}
		return new ArrayList<>(ret); 
	}

	protected static List<Equation> removeEquations(List<Equation> equations, Set<String> ivc) { 
			List<Equation> ret = new ArrayList<>(); 
			for(Equation eq : equations){
				if(ivc.contains(eq.lhs.get(0).id)){
					ret.add(eq);  
				}
			}

			return ret; 
	} 
}
