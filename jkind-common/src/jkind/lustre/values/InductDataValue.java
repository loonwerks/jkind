package jkind.lustre.values;

import java.util.Collections;
import java.util.List;
import java.util.StringJoiner;

import jkind.lustre.BinaryOp;
import jkind.lustre.TypeConstructor;
import jkind.lustre.UnaryOp;

public class InductDataValue extends Value{

	public final List<Value> argValues;
	public final String name;
	public InductDataValue(String name, List<Value> argValues){
		this.name = name;
		this.argValues = Collections.unmodifiableList(argValues);
	}
	
	@Override
	public Value applyBinaryOp(BinaryOp op, Value right) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Value applyUnaryOp(UnaryOp op) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append("(");
		str.append(name);
		for(Value argVal : argValues){
			str.append(" ");
			str.append(argVal);
		}
		str.append(")");
		return str.toString();
	}

	@Override
	public int hashCode() {
		return argValues.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof InductDataValue) {
			InductDataValue other = (InductDataValue) obj;
			if(!this.name.equals(other.name)){
				return false;
			}
			return argValues.equals(other.argValues);
		}
		return false;
	}

}
