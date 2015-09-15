package jkind.translation;

import jkind.analysis.IdRewriteVisitor;
import jkind.analysis.IdRewriter;
import jkind.lustre.Program;

public class InlineRecursiveFunctionLocals extends IdRewriteVisitor{

    public InlineRecursiveFunctionLocals(IdRewriter rewriter) {
        super(rewriter);
    }

    public static Program program(Program program){
    	//TODO change this to inline the locals later
        return program;
    }

}
