package jkind;

public enum SolverOption {
	YICES, CVC4, Z3, YICES2, MATHSAT;
	
	@Override
	public String toString() {
		return super.toString().toLowerCase();
	}
}
