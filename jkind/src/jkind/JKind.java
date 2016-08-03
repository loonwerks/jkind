package jkind;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import jkind.analysis.LinearChecker;
import jkind.analysis.StaticAnalyzer;
import jkind.engines.Director;
import jkind.lustre.Equation;
import jkind.lustre.Expr;
import jkind.lustre.IdExpr;
import jkind.lustre.NamedType;
import jkind.lustre.Node;
import jkind.lustre.Program;
import jkind.lustre.VarDecl;
import jkind.lustre.builders.NodeBuilder;
import jkind.translation.InlineSimpleEquations;
import jkind.translation.Specification;
import jkind.translation.Translate;
import jkind.util.Util;

public class JKind {
	public static void main(String[] args) {
		try {
			JKindSettings settings = JKindArgumentParser.parse(args);
			Program program = Main.parseLustre(settings.filename);

			StaticAnalyzer.check(program, settings.solver);
			if (!LinearChecker.isLinear(program)) {
				if (settings.pdrMax > 0) {
					Output.warning("disabling PDR due to non-linearities");
					settings.pdrMax = 0;
				}
			}

			Node main = Translate.translate(program);
			if(settings.allAssigned){
				//after experiments, uncomment this line:
				//main = normalizeAssertions(main);
				main = setIvcArgs(main, getAllAssigned(main));
			}
			Specification userSpec = new Specification(main, settings.noSlicing);
			Specification analysisSpec = getAnalysisSpec(userSpec, settings);

			new Director(settings, userSpec, analysisSpec).run();
			System.exit(0); // Kills all threads
		} catch (Throwable t) {
			t.printStackTrace();
			System.exit(ExitCodes.UNCAUGHT_EXCEPTION);
		}
	}

	private static List<String> getAllAssigned(Node node) {
		List<String> result = new ArrayList<>();
		result.addAll(Util.getIds(node.locals));
		result.addAll(Util.getIds(node.outputs));
		return result;
	}

	private static Node setIvcArgs(Node node, List<String> newSupport) {
		return new NodeBuilder(node).clearIvc().addIvcs(newSupport).build();
	}
	
	public static final String EQUATION_NAME = "__addedEQforAsrInconsis_by_JKind__"; 
	private static Node normalizeAssertions(Node node) {   
		List<VarDecl> locals = new ArrayList<>(node.locals); 
		List<Equation> equations = new ArrayList<>(node.equations);
		List<Expr> assertions = new ArrayList<>(node.assertions);
		 
		
		Iterator<Expr> iter = assertions.iterator();
		int id = 0;
		List<IdExpr> newAssertions = new ArrayList<>();
		
		while (iter.hasNext()) {
			Expr asr = iter.next();
			if (! (asr instanceof IdExpr)) {
				newAssertions.add(defineNewEquation(asr, locals, equations, EQUATION_NAME + id));
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
	
	private static IdExpr defineNewEquation(Expr rightSide, List<VarDecl> locals, List<Equation> equations, String newVar) {
		locals.add(new VarDecl(newVar, NamedType.BOOL));
		IdExpr ret = new IdExpr(newVar);
		equations.add(new Equation(ret, rightSide));
		 
		return ret;
	}

	private static Specification getAnalysisSpec(Specification userSpec, JKindSettings settings) {
		if (settings.inline) {
			Node inlined = InlineSimpleEquations.node(userSpec.node);
			return new Specification(inlined, settings.noSlicing);
		} else {
			return userSpec;
		}
	}
}
