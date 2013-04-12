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
	
	public static BoolValue fromBool(boolean b) {
		return b ? BoolValue.TRUE : BoolValue.FALSE;
	}
	
	@Override
	public String toString() {
		return Boolean.toString(val);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (val ? 1231 : 1237);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BoolValue other = (BoolValue) obj;
		if (val != other.val)
			return false;
		return true;
	}
}
