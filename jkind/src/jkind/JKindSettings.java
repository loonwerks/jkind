package jkind;

public class JKindSettings {
	public int n = 200;
	public int timeout = 100;
	public boolean excel = false;
	public boolean xml = false;
	public boolean scratch = false;
	public boolean useInductiveProcess = true;
	public boolean useInvariantProcess = true;
	public boolean inductiveCounterexamples = false;
	public boolean reduceInvariants = false;
	public boolean smoothCounterexamples = false;
	public SolverOption solver = SolverOption.YICES;
	public String filename = null;

	public static enum SolverOption {
		YICES, CVC4, Z3
	};
}
