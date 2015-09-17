package jkind.lustre;

import java.util.List;

import jkind.lustre.visitors.TypeVisitor;
import jkind.util.Util;

public class InductType extends Type {
    public final String name;
    public final List<TypeConstructor> constructors;
    
	public InductType(Location location, String name, List<TypeConstructor> constructors) {
		super(location);
		this.name = name;
		this.constructors = Util.safeList(constructors);
	}
	
	public InductType(String name, List<TypeConstructor> constructors){
		this(Location.NULL, name, constructors);
	}

	@Override
	public int hashCode() {
		return name.hashCode();
	};
	
	@Override
	public boolean equals(Object obj){
		
		if(obj instanceof NamedType){
			return ((NamedType) obj).name.equals(name);
		}
		
		if(!(obj instanceof InductType)){
			return false;
		}
		
		return ((InductType)obj).name.equals(name);
	}
	
	@Override
	public String toString(){
		return name;
	}
	
	@Override
	public <T> T accept(TypeVisitor<T> visitor) {
		return visitor.visit(this);
	}

}
