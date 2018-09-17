package jkind.lustre;

import java.util.Collections;
import java.util.List;

import jkind.Assert;
import jkind.lustre.visitors.AstVisitor;
import jkind.util.Util;

public class Function extends Ast {
	public final String id;
	public final List<VarDecl> inputs;
	public final List<VarDecl> outputs;

	public Function(Location location, String id, List<VarDecl> inputs, List<VarDecl> outputs) {
		super(location);
		Assert.isNotNull(id);
		this.id = id;
		this.inputs = Util.safeList(inputs);
		this.outputs = Util.safeList(outputs);
	}

	public Function(String id, List<VarDecl> inputs, List<VarDecl> outputs) {
		this(Location.NULL, id, inputs, outputs);
	}

	public Function(String id, List<VarDecl> inputs, VarDecl output) {
		this(Location.NULL, id, inputs, Collections.singletonList(output));
	}

	@Override
	public <T, S extends T> T accept(AstVisitor<T, S> visitor) {
		return visitor.visit(this);
	}
}
