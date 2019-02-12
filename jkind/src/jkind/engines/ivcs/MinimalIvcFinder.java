package jkind.engines.ivcs;  
import java.util.HashSet; 
import java.util.Set; 
import jkind.JKindSettings; 
import jkind.engines.MiniJKind;  
import jkind.lustre.Node;
import jkind.lustre.Program;
import jkind.translation.Specification;  

public class MinimalIvcFinder { 
	private Node node;  
	private String property;
	
	public MinimalIvcFinder(Program program, String property){
	    this.node = program.getMainNode();
	    this.property = property;
	}

	public Set<String> minimizeIvc(Set<String> candidates, Set<String> mustElements, int timeout) {  
		Set<String> minimal = new HashSet<>(candidates);
		JKindSettings js = new JKindSettings(); 
		//js.noSlicing = true;   
		js.allAssigned = false;
		js.miniJkind = true; 
		js.timeout = timeout; 
		for (String s : candidates) {  
			Program candidate = new Program(IvcUtil.unassign(node, s, property));
			MiniJKind miniJkind = new MiniJKind (candidate, new Specification(candidate, js.slicing), js);
			miniJkind.verify();
			if (miniJkind.getPropertyStatus() == MiniJKind.VALID) {
				minimal.remove(s);
				node = candidate.getMainNode();
			}
			miniJkind = null;
		}
		minimal.addAll(mustElements);
		minimal = IvcUtil.trimNode(minimal);
		return minimal;
	}
	
	public Set<String> computeMust(Set<String> candidates, int timeout) { 
		Set<String> must = new HashSet<>();
		JKindSettings js = new JKindSettings(); 
		//js.noSlicing = true;   
		js.allAssigned = false;
		js.miniJkind = true; 
		js.timeout = timeout; 
		for (String s : candidates) {    
			Program candidate = new Program(IvcUtil.unassign(node, s, property));
			MiniJKind miniJkind = new MiniJKind (candidate, new Specification(candidate, js.slicing), js);
			miniJkind.verify();
			if (miniJkind.getPropertyStatus() != MiniJKind.VALID) {
				must.add(s);
			}
			miniJkind = null;
		} 
		must = IvcUtil.trimNode(must);
		return must;
	}	
}