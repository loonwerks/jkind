package jkind;

public enum SolverOption {
	YICES, CVC4, Z3;
	
	@Override
	public String toString() {
		return super.toString().toLowerCase();
	}
}
