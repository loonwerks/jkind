package jkind.engines.ivcs; 
import java.io.FileOutputStream;  
import java.io.PrintWriter;
import java.util.ArrayList; 
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import jkind.ExitCodes;
import jkind.JKindSettings; 
import jkind.engines.MiniJKind; 
import jkind.lustre.Equation;
import jkind.lustre.Node; 
import jkind.lustre.VarDecl;
import jkind.lustre.builders.NodeBuilder; 
import jkind.translation.Specification; 
import jkind.util.Util; 

public class MinimalIvcFinder {
	private long startTime; 
	private Node node; 
	private String fileName; 
	
	protected MinimalIvcFinder(Node node, String fileName){
		startTime = System.currentTimeMillis(); 
	    this.node = node; 
	    this.fileName = fileName; 
		if (node.properties.size() != 1) {
			throw new IllegalArgumentException("Expected exactly one property, but found "
					+ node.properties.size());
		} 
	}

	private double getRuntime() {
		return (System.currentTimeMillis() - startTime) / 1000.0;
	}

	public List<String> reduce(Set<String> candidates, Set<String> mustElements, boolean writeToFile) {  
		List<String> minimal = new ArrayList<>(candidates);
		JKindSettings js = new JKindSettings(); 
		//js.noSlicing = true;   
		js.allAssigned = false;
		js.miniJkind = true; 
		
		//------------ only for the experiment -------------
		String xmlFilename = fileName + "_minimizationInfo.xml";
		int counter = 1;
		try (PrintWriter out = new PrintWriter(new FileOutputStream(xmlFilename))) {
			out.println("<?xml version=\"1.0\"?>");
			out.println("<Results xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">");
		//--------------------------------------------------
		
		for (String s : candidates) {    
			Node candidate = unassign(node, s);
			MiniJKind miniJkind = new MiniJKind (new Specification(candidate, js.noSlicing), js);
			miniJkind.verify();
			
			//------------ only for the experiment -------------
			out.println("  <Run id=\"" + counter + "\">");
			out.println("    <Runtime unit=\"sec\">" + miniJkind.getRuntime() + "</Runtime>");
			out.println("    <Status>" + miniJkind.getPropertyStatus() + "</Status>");
			out.println("  </Run>");
			counter++ ;
			//--------------------------------------------------
			
			if (miniJkind.getPropertyStatus() == MiniJKind.VALID) {
				minimal.remove(s);
				node = candidate;
			}
		}
		minimal.addAll(mustElements);
		if (writeToFile){
			writeXML(minimal, getRuntime());
		}
		
		
		
		//------------ only for the experiment -------------
		out.println("</Results>");
		out.flush();
		out.close(); 
		}catch (Throwable t) {
			t.printStackTrace();
			System.exit(ExitCodes.UNCAUGHT_EXCEPTION);
		}
		//--------------------------------------------------
		
		
		return minimal;
	}

	private void writeXML(List<String> minimal, double runtime) {
		String xmlFilename = fileName + "_minimalIvc.xml";
		try (PrintWriter out = new PrintWriter(new FileOutputStream(xmlFilename))) {
			out.println("<?xml version=\"1.0\"?>");
			out.println("<Results xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">");
			out.println("  <MinimalIvc property=\"" + node.properties + "\">");
			out.println("    <Runtime unit=\"sec\">" + runtime + "</Runtime>");
			for (String s : Util.safeStringSortedSet(minimal)) {
				out.println("    <IVC>" + s + "</IVC>");
			}
			out.println("  </MinimalIvc>");
			out.println("</Results>");
			out.flush();
			out.close();
		} catch (Throwable t) {
			t.printStackTrace();
			System.exit(ExitCodes.UNCAUGHT_EXCEPTION);
		}
	}

	private static Node unassign(Node node, String v) {
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
		builder.clearLocals().addLocals(locals);
		builder.clearOutputs().addOutputs(outputs);
		builder.clearEquations().addEquations(equations);
		return builder.build();
	}

	private static List<VarDecl> removeVariable(List<VarDecl> varDecls, String v) {
		List<VarDecl> result = new ArrayList<>(varDecls);
		Iterator<VarDecl> iter = result.iterator();
		while (iter.hasNext()) {
			if (iter.next().id.equals(v)) {
				iter.remove();
			}
		}
		return result;
	}
}