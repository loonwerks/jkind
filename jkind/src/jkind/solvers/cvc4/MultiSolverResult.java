package jkind.solvers.cvc4;

import jkind.solvers.Result;
import jkind.solvers.Solver;

public class MultiSolverResult {
    public final Result result;
    public final Solver solver;
    
    public MultiSolverResult(Result result, Solver solver){
        this.result = result;
        this.solver = solver;
    }

}
