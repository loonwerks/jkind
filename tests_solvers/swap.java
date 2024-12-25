package jkind.solvers.cvc5;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jkind.sexp.Cons;
import jkind.sexp.Sexp;
import jkind.sexp.Symbol;
import jkind.solvers.MaxSatSolver;
import jkind.solvers.Model;
import jkind.solvers.Result;
import jkind.solvers.SatResult;
import jkind.solvers.UnknownResult;
import jkind.solvers.UnsatResult;
import jkind.solvers.smtlib2.SmtLib2Solver;
import jkind.solvers.smtlib2.SolverOutOfMemoryException;

public class Cvc5Solver extends SmtLib2Solver implements MaxSatSolver {
    private final boolean linear;
    private int actCount = 1;

    public Cvc5Solver(String scratchBase, boolean linear) {
        super(scratchBase);
        this.linear = linear;
    }

    @Override
    protected String getSolverName() {
        return "Cvc5";
    }

    @Override
    protected String[] getSolverOptions() {
        return new String[] { "--lang", "smt2" };
    }

    @Override
    public void initialize() {
        setLogic("ALL"); // Set the logic to ALL or QF_NIRA it can be set with other options too.
        setOption("produce-models", true);
        setOption("produce-unsat-cores", true);
        setOption("minimize-unsat-cores", true);
        setOption("produce-proofs", true);
        setOption("quant-ind", true);
    }

    public void setLogic(String logic) {
        send("(set-logic " + logic + ")");
    }
    
    public void setOption(String option, boolean value) {
        send("(set-option :" + option + " " + value + ")");
    }

    @Override
    public Result query(Sexp sexp) {
        Result result;

        if (linear) {
            Symbol literal = createActivationLiteral("act", actCount++);
            send(new Cons("assert", new Cons("=>", literal, new Cons("not", sexp))));
            send(new Cons("check-sat", literal));
        } else {
            push();
            send(new Cons("assert", new Cons("not", sexp)));
            send(new Cons("check-sat"));
        }

        try {
            String status = readFromSolver();
            if (isSat(status)) {
                send("(get-model)");
                result = new SatResult(parseModel(readFromSolver()));
            } else if (isUnsat(status)) {
                result = new UnsatResult();
            } else {
                result = new UnknownResult();
            }
        } catch (SolverOutOfMemoryException e) {
            result = new UnknownResult();
        } finally {
            if (!linear) {
                pop();
            }
        }

        return result;
    }

    @Override
    public Result quickCheckSat(List<Symbol> activationLiterals) {
        send(new Cons("check-sat", activationLiterals));
        String status = readFromSolver();
        if (isSat(status)) {
            return new SatResult();
        } else if (isUnsat(status)) {
            return new UnsatResult(getUnsatCore(activationLiterals));
        } else {
            return new UnknownResult();
        }
    }

    @Override
    protected List<Symbol> getUnsatCore(List<Symbol> activationLiterals) {
        List<Symbol> unsatCore = new ArrayList<>();
        send("(get-unsat-core)");
        String core = readCore();
        for (String s : core.split("\\s+")) {
            if (!s.isEmpty()) {
                unsatCore.add(new Symbol(s));
            }
        }
        return unsatCore;
    }

    private String readCore() {
        StringBuilder coreBuilder = new StringBuilder();
        try {
            String line;
            while ((line = fromSolver.readLine()) != null) {
                coreBuilder.append(line).append(" ");
                if (line.endsWith(")")) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String core = coreBuilder.toString().trim();
        return core.substring(1, core.length() - 1); // Remove surrounding parentheses
    }

    @Override
    public void assertSoft(Sexp sexp) {
        throw new UnsupportedOperationException("Soft assertions are not supported in CVC5.");
    }

    @Override
    public Result maxsatQuery(Sexp query) {
        return query(query);
    }
}

