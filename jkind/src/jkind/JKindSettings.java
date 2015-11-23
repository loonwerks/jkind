package jkind;


public class JKindSettings extends Settings {
	public int n = 200;
	public int timeout = 100;
	
	public boolean excel = false;
	public boolean xml = false;
	public boolean xmlToStdout = false;
	
	public boolean boundedModelChecking = true;
	public boolean kInduction = true;
	public boolean invariantGeneration = true;
    public int pdrMax = 1;
	public boolean inductiveCounterexamples = false;
	public boolean reduceInvariants = false;
	public boolean smoothCounterexamples = false;
    public boolean intervalGeneralization = false;
    public boolean inline = true;
	
	public SolverOption solver = SolverOption.YICES;
	public boolean scratch = false;

	public String writeAdvice = null;
	public String readAdvice = null;
}
