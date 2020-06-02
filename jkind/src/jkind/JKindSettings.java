package jkind;

public class JKindSettings extends Settings {
	public int n = Integer.MAX_VALUE;
	public int timeout = Integer.MAX_VALUE;

	public boolean miniJkind = false;
	public boolean excel = false;
	public boolean xml = false;
	public boolean xmlToStdout = false;

	public String main = null;
	public boolean boundedModelChecking = true;
	public boolean kInduction = true;
	public boolean invariantGeneration = true;
	public int pdrMax = 1;
	public boolean inductiveCounterexamples = false;
	public boolean reduceIvc = false;
	public boolean allIvcs = false;
	public boolean smoothCounterexamples = false;
	public AllIvcsAlgorithm allIvcsAlgorithm = AllIvcsAlgorithm.OFFLINE_MIVC_ENUM_ALG;
	public int allIvcsMaxGrows = 1000;
	public int allIvcsJkindTimeout = -1; // if set to -1, a timeout will be computed based on the first call of the jkind's solve method
	public boolean inlining = true;
	public boolean slicing = true;
	public SolverOption solver = SolverOption.SMTINTERPOL;
	public boolean scratch = false;
	public String writeAdvice = null;
	public String readAdvice = null;
	public boolean allAssigned = false;

	public JKindSettings() {
	}
}
