package jkind.solvers.cvc4;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import jkind.JKindException;
import jkind.lustre.InductType;
import jkind.lustre.VarDecl;
import jkind.sexp.Sexp;
import jkind.solvers.Result;
import jkind.solvers.SatResult;
import jkind.solvers.Solver;
import jkind.solvers.UnsatResult;
import jkind.translation.Relation;
import jkind.translation.Specification;

public class Cvc4MultiSolver extends Solver {

    private Cvc4SolverThread satSolver = null;
    private Cvc4SolverThread unsatSolver = null;
    private final String scratchBase;
    private final BlockingQueue<MultiSolverResult> incoming = new LinkedBlockingQueue<>();
    private final LinkedList<List<Sexp>> assertionsQueue = new LinkedList<>();
    private List<VarDecl> definedVars = new ArrayList<>();
    private List<Relation> definedRelations = new ArrayList<>();
    private List<InductType> definedInductTypes = new ArrayList<>();
    private Specification initialSpec = null;
    private boolean asyncQueryFinished = true;

    public Cvc4MultiSolver(String scratchBase) {
        this.scratchBase = scratchBase;
        assertionsQueue.add(new ArrayList<>()); //TODO maybe don't do this initially
        buildSatSolver();
        buildUnsatSolver();
    }

    private void buildSatSolver() {
        if(satSolver != null){
            throw new JKindException("Cvc4MultiSolver attempt to to build solver that is already built");
        }
        //ProcessBuilder satProcess = new ProcessBuilder(getCVC4(), "--lang", "smt", "--fmf-fun", "--uf-ss=none");
        ProcessBuilder satProcess = new ProcessBuilder(getCVC4(), "--lang", "smt", "--fmf-fun");

        satSolver = new Cvc4SolverThread(scratchBase+"_SAT", satProcess, incoming);
        rebuildSolver(satSolver);
    }

    private void buildUnsatSolver() {
        if(unsatSolver != null){
            throw new JKindException("Cvc4MultiSolver attempt to to build solver that is already built");
        }
        ProcessBuilder unsatProcess = new ProcessBuilder(getCVC4(), "--lang", "smt", "--quant-ind");
        		//,"--conjecture-gen", "--conjecture-filter-canonical", "--conjecture-gen-per-round=100");
        unsatSolver = new Cvc4SolverThread(scratchBase+"_UNSAT", unsatProcess, incoming);
        rebuildSolver(unsatSolver);
    }

    private void rebuildSolver(Cvc4SolverThread solver) {
        if (initialSpec != null) {
            solver.initialize(initialSpec);
        }
        for (VarDecl var : definedVars){
            solver.define(var);
        }
        for(Relation relation : definedRelations){
            solver.define(relation);
        }
        for(InductType inductType : definedInductTypes){
            solver.define(inductType);
        }
        boolean afterFirst = false;
        Iterator<List<Sexp>> descIter = assertionsQueue.descendingIterator();
        
        while(descIter.hasNext()){
        	List<Sexp> assertList = descIter.next();
        	if(afterFirst){
                solver.push();
            }
            for(Sexp sexp : assertList){
                solver.assertSexp(sexp);
            }
            afterFirst = true;
        }
        
    }

    private static String getCVC4() {
        String home = System.getenv("CVC4_HOME");
        if (home != null) {
            return new File(new File(home, "bin"), "cvc4").toString();
        }
        return "cvc4";
    }

    @Override
    public void initialize(Specification spec) {

        if (initialSpec != null) {
            throw new JKindException("Cvc4MultiSolver has been initialized twice");
        }
        initialSpec = spec;
        satSolver.initialize(spec);
        unsatSolver.initialize(spec);
    }

    @Override
    public void assertSexp(Sexp sexp) {
        assertionsQueue.peek().add(sexp);
        satSolver.assertSexp(sexp);
        unsatSolver.assertSexp(sexp);
    }

    @Override
    public void define(VarDecl decl) {
        definedVars.add(decl);
        satSolver.define(decl);
        unsatSolver.define(decl);
    }

    @Override
    public void define(Relation relation) {
        definedRelations.add(relation);
        satSolver.define(relation);
        unsatSolver.define(relation);
    }
    
    private void startQuery(Sexp sexp){
    	 satSolver.storeQuery(sexp);
         unsatSolver.storeQuery(sexp);
         Thread satThread = new Thread(satSolver);
         Thread unsatThread = new Thread(unsatSolver);
         satThread.start();
         unsatThread.start();
    }
    
    private Result waitAndGetQueryResult(){
    	while(incoming.isEmpty()){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        MultiSolverResult multiResult = incoming.poll();
        //TODO sometimes they both return so we don't need to restart the other
        //we might want to change how this works to improve the performance later
        if(multiResult.solver == satSolver){
            restartSolver(unsatSolver);
        }else{
            if(multiResult.solver != unsatSolver){
                throw new JKindException("What solver is this suposed to be?");
            }
            restartSolver(satSolver);
        }
        
        incoming.clear(); //sometimes we get multiple results that we need to clear
        return multiResult.result;
    }
    
    @Override
    public Result query(Sexp sexp) {
        startQuery(sexp);
        return waitAndGetQueryResult();
    }

    public void asyncQuery(Sexp sexp){
    	if(!asyncQueryFinished){
    		throw new JKindException("Tried to start another query before it finished");
    	}
    	startQuery(sexp);
    }
    
    public void cancelAsyncQuery(){
    	restartSolver(unsatSolver);
    	restartSolver(satSolver);
    	incoming.clear();
    	asyncQueryFinished = true;
    }
    
    public boolean asyncQueryCompleted(){
    	return !incoming.isEmpty();
    }
    
    public Result getAsyncQueryResult(){
    	if(!asyncQueryCompleted()){
    		throw new JKindException("Tried to get asynchronous query result before completed");
    	}
    	asyncQueryFinished = true;
    	return waitAndGetQueryResult();
    }
    
	private synchronized void restartSolver(Cvc4SolverThread solver) {
		solver.destory();
		if (solver == satSolver) {
			satSolver = null;
			buildSatSolver();
		} else if (solver == unsatSolver) {
			unsatSolver = null;
			buildUnsatSolver();
		} else {
			throw new IllegalArgumentException("attempting to restart unkown solver");
		}
	}

    @Override
    public void push() {
        assertionsQueue.push((new ArrayList<>()));
        satSolver.push();
        unsatSolver.push();
    }

    @Override
    public void pop() {
        assertionsQueue.pop();
        satSolver.pop();
        unsatSolver.pop();
    }

    @Override
    public void comment(String str) {
        satSolver.comment(str);
        unsatSolver.comment(str);
    }

    @Override
	public synchronized void stop() {
    	//the call to the solver constructor in 
    	//"buildSatSolver" may throw an exception
    	//if we are already shutting down. This causes
    	//the solver variable to be null
		if (satSolver != null) {
			satSolver.destory();
			satSolver.stop();
		}
		if (unsatSolver != null) {
			unsatSolver.destory();
			unsatSolver.stop();
		}
    }
    
    @Override
    public void define(InductType type) {
        definedInductTypes.add(type);
        satSolver.define(type);
        unsatSolver.define(type);
    }
}
