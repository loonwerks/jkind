package jkind.solvers.cvc4;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

import org.antlr.v4.runtime.RecognitionException;

import jkind.JKindException;
import jkind.sexp.Cons;
import jkind.sexp.Sexp;
import jkind.solvers.Result;
import jkind.solvers.SatResult;
import jkind.solvers.UnknownResult;
import jkind.solvers.UnsatResult;
import jkind.translation.Relation;

public class Cvc4SolverThread extends Cvc4Solver implements Runnable{
    
    private Sexp storedQuery = null;
    private final BlockingQueue<MultiSolverResult> outgoing;
    private AtomicBoolean destroyed = new AtomicBoolean(false);
    
    public Cvc4SolverThread(String scratchBase, ProcessBuilder processBuilder, BlockingQueue<MultiSolverResult> outgoing) {
        super(scratchBase, processBuilder);
        this.outgoing = outgoing;
    }

    @Override
    public void run() {
       if(storedQuery == null){
           throw new JKindException("Cvc4SolverThread started without a stored query");
       }
       cancelableQuery();
    }
    
    public void destory(){
        destroyed.set(true);
    }
    
    public void storeQuery(Sexp sexp){
        if(storedQuery != null){
            throw new JKindException("Cvc4SolverThread already had a stored query");
        }
        storedQuery = sexp;
    }
    
    private void cancelableQuery(){
        Result result = null;
        push();

        assertSexp(new Cons("not", storedQuery));
        storedQuery = null;
        send("(check-sat)");
        send("(echo \"" + DONE + "\")");
        String status = readFromSolver();
        if(status == null){
            return;
        }
        if (isSat(status)) {
            send("(get-model)");
            send("(echo \"" + DONE + "\")");
            result = new SatResult(parseModel(readFromSolver()));
        } else if (isUnsat(status)) {
            result = new UnsatResult();
        } else {
            //result = new UnknownResult();
            //throw new IllegalArgumentException("Unknown result: " + result);
            
            //for unknown results we just return without sending a result
            pop();
            return;
        }

        pop();
        try {
            outgoing.put(new MultiSolverResult(result, this));
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    

    @Override
    public Result query(Sexp sexp) {
        throw new JKindException("Cvc4SolverThread cannot be queried directly"
                + " Use \"storeQuery\" and then spawn a new thread with the class");
    }
    
    
    private String nonBlockingReadLine(){
        char c = 0;
        StringBuilder sb = new StringBuilder();
        boolean readChar = false;
        try {
            do {
                if (fromSolver.ready()) {
                    int res = fromSolver.read();
                    if (res == -1) {
                        throw new JKindException("unexpected end of file during solver read");
                    }
                    c = (char) res;
                    sb.append(c);
                    readChar = true;
                }else{
                    readChar = false;
                }
                if(destroyed.get()){
                    this.process.destroyForcibly();
                    return null;
                }else if(!readChar){
                    Thread.sleep(10);
                }
            } while (c != '\n');
            
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
    
    @Override
    protected String readFromSolver() {
        try {
            String line;
            StringBuilder content = new StringBuilder();
            while (true) {
            
                line = nonBlockingReadLine();
                comment(name + ": " + line);
                if (line == null) {
                    return null;
                } else if (line.contains("define-fun " + Relation.T + " ")) {
                    // No need to parse the transition relation
                } else if (isDone(line)) {
                    break;
                } else if (line.contains("model is not available")) {
                    return null;
                } else if (line.contains(" is not well-founded")){
                    int endIndex = line.indexOf(" is not well-founded");
                    //TODO: this will break if the error message changes
                    String typeName = line.substring(21, endIndex);
                    throw new JKindException("Type '"+typeName+"' is not well-founded");
                } else if (line.contains("error \"") || line.contains("Error:")) {
                    // Flush the output since errors span multiple lines
                    while ((line = nonBlockingReadLine()) != null) {
                        comment(name + ": " + line);
                        if (isDone(line)) {
                            break;
                        }
                    }
                    throw new JKindException(name + " error (see scratch file for details)");
                } else {
                    content.append(line);
                    content.append("\n");
                }
            }

            return content.toString();
        } catch (RecognitionException e) {
            throw new JKindException("Error parsing " + name + " output", e);
        }
    }
    
}
