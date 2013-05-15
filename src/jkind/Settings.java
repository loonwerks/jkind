package jkind;

public class Settings {
	public static int n = 200;
	public static int timeout = 100;
	public static boolean excel = false;
	public static boolean xml = false;
	public static boolean scratch = false;
	public static boolean useInductiveProcess = true;
	public static boolean useInvariantProcess = true;
	public static boolean inductiveCounterexamples = false;
	public static boolean reduceInvariants = false;
	public static boolean smoothCounterexamples = false;
	public static SolverOption solver = SolverOption.YICES;
	
	public static enum SolverOption { YICES, CVC4, Z3 };
}
