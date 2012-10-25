package jkind.solvers;


public class BoolValue extends Value {
	final public static BoolValue TRUE = new BoolValue(true);
	final public static BoolValue FALSE = new BoolValue(false);
	
	private boolean val;
	
	private BoolValue(boolean val) {
		this.val = val;
	}
	
	public boolean getBool() {
		return val;
	}
	
	@Override
	public String toString() {
		return Boolean.toString(val);
	}
}
