package jkind.lustre;

import java.util.List;

import jkind.lustre.visitors.TypeVisitor;
import jkind.util.Util;

public class InductType extends Type {
    public final String name;
    public final List<TypeConstructor> constructors;
	protected InductType(Location location, String name, List<TypeConstructor> constructors) {
		super(location);
		this.name = name;
		this.constructors = Util.safeList(constructors);
	}
	
	public InductType(String name, List<TypeConstructor> constructors){
		this(Location.NULL, name, constructors);
	}

	@Override
	public <T> T accept(TypeVisitor<T> visitor) {
		return visitor.visit(this);
	}

}
