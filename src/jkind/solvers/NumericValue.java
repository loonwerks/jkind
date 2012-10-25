package jkind.solvers;


public class NumericValue extends Value {
	private String val;
	
	public NumericValue(String val) {
		this.val = val;
	}
	
	@Override
	public String toString() {
		return val;
	}
}
