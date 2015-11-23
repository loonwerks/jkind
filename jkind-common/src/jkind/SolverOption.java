package jkind;

public enum SolverOption {
	YICES, CVC4, Z3, YICES2, MATHSAT, SMTINTERPOL;

	@Override
	public String toString() {
		return name().toLowerCase();
	}
}
