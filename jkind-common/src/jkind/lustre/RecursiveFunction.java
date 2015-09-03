package jkind.lustre;

import java.util.List;

import jkind.lustre.visitors.AstVisitor;
import jkind.util.Util;

public class RecursiveFunction extends Ast {
    public final String id;
    public final List<VarDecl> inputs;
    public final List<VarDecl> locals;
    public final VarDecl output;
    public final List<Equation> equations;

    public RecursiveFunction(Location location, String id, List<VarDecl> inputs, List<VarDecl> locals,
            VarDecl output, List<Equation> equation) {
        super(location);
        this.id = id;
        this.inputs = Util.safeList(inputs);
        this.locals = Util.safeList(locals);
        this.output = new VarDecl(output.id, output.type);
        this.equations = equation;
    }
    
    public RecursiveFunction(String id, List<VarDecl> inputs, List<VarDecl> locals,
            VarDecl output, List<Equation> equation) {
        this(Location.NULL, id, inputs, locals, output, equation);
        // TODO Auto-generated constructor stub
    }

    @Override
    public <T, S extends T> T accept(AstVisitor<T, S> visitor) {
        return visitor.visit(this);
    }
    
    @Override
    public String toString(){
        return id;
    }

}
