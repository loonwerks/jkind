package jkind.engines.ivcs; 
import java.io.FileOutputStream;  
import java.io.PrintWriter; 
import java.util.HashSet; 
import java.util.Set;
import jkind.ExitCodes;
import jkind.JKindSettings; 
import jkind.engines.MiniJKind;  
import jkind.lustre.Node;  
import jkind.translation.Specification; 
import jkind.util.Util; 

public class MinimalIvcFinder {
	private long startTime; 
	private Node node; 
	private String fileName; 
	private String property;
	
	protected MinimalIvcFinder(Node node, String fileName, String property){
		startTime = System.currentTimeMillis(); 
	    this.node = node; 
	    this.fileName = fileName; 
	    this.property = property;
	}

	private double getRuntime() {
		return (System.currentTimeMillis() - startTime) / 1000.0;
	}

	public Set<String> reduce(Set<String> candidates, Set<String> mustElements, boolean writeToFile) {  
		Set<String> minimal = new HashSet<>(candidates);
		JKindSettings js = new JKindSettings(); 
		//js.noSlicing = true;   
		js.allAssigned = false;
		js.miniJkind = true; 
		js.timeout = 300;
		
		//------------ only for the experiment -------------
		String xmlFilename = fileName + "_minimizationInfo.xml";
		int counter = 1;
		try (PrintWriter out = new PrintWriter(new FileOutputStream(xmlFilename))) {
			out.println("<?xml version=\"1.0\"?>");
			out.println("<Results xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">");
		//--------------------------------------------------
		
		for (String s : candidates) {    
			Node candidate = IvcUtil.unassign(node, s, property);
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
		minimal = IvcUtil.trimNode(minimal);
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

	private void writeXML(Set<String> minimal, double runtime) {
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
	
}