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
	public int allIvcsAlgorithm = 1;
	public int allIvcsMaxGrows = 1000;
	public int allIvcsJkindTimeout = -1; // if set to -1, a timeout will be computed based on the first call of the jkind's solve method	
	public boolean inlining = true;
	public boolean slicing = true;
	public SolverOption solver = SolverOption.SMTINTERPOL;
	public boolean scratch = false;
	public String writeAdvice = null;
	public String readAdvice = null; 
	public boolean allAssigned = false; 
	public String useUnsatCore = null;
	
	public JKindSettings() { }
	public JKindSettings(JKindSettings settings) {
		this.n = settings.n;
		this.timeout = settings.timeout;
		
		this.miniJkind = settings.miniJkind;
		this.excel = settings.excel;
		this.xml = settings.xml;
		this.xmlToStdout = settings.xmlToStdout;

		this.main = settings.main;
		this.boundedModelChecking = settings.boundedModelChecking;
		this.kInduction = settings.kInduction;
		this.invariantGeneration = settings.invariantGeneration;
	   	this.pdrMax = settings.pdrMax;
		this.inductiveCounterexamples = settings.inductiveCounterexamples;
		this.reduceIvc = settings.reduceIvc;
		this.allIvcs = settings.allIvcs;
		this.allIvcsAlgorithm = settings.allIvcsAlgorithm;
		this.allIvcsMaxGrows = settings.allIvcsMaxGrows;
		this.allIvcsJkindTimeout = settings.allIvcsJkindTimeout;
		this.smoothCounterexamples = settings.smoothCounterexamples;
		this.inlining = settings.inlining;
		this.slicing = settings.slicing;
		this.solver = settings.solver;
		this.scratch = settings.scratch;
		this.writeAdvice = settings.writeAdvice;
		this.readAdvice = settings.readAdvice; 
		this.allAssigned = settings.allAssigned; 
		this.useUnsatCore = settings.useUnsatCore;
		
	}
}
