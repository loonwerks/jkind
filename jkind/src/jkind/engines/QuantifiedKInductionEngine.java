package jkind.engines;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jkind.JKindException;
import jkind.JKindSettings;
import jkind.lustre.Equation;
import jkind.lustre.Expr;
import jkind.sexp.Cons;
import jkind.sexp.Sexp;
import jkind.solvers.Model;
import jkind.solvers.Result;
import jkind.solvers.SatResult;
import jkind.solvers.UnknownResult;
import jkind.solvers.UnsatResult;
import jkind.solvers.cvc4.Cvc4MultiSolver;
import jkind.translation.InductiveDataTypeSpecification;
import jkind.translation.Lustre2Sexp;
import jkind.translation.Specification;
import jkind.util.SexpUtil;

public class QuantifiedKInductionEngine extends KInductionEngine {

	private final Map<String, Expr> exprMap;
	public QuantifiedKInductionEngine(InductiveDataTypeSpecification spec, JKindSettings settings, Director director) {
		super(spec, settings, director);
		exprMap = spec.propertyExprs;
	}

	
	@Override
	protected void checkProperties(int k) {

		List<String> possiblyValid = new ArrayList<>(properties);
		while (!possiblyValid.isEmpty()) {
			String prop = possiblyValid.remove(0);
			List<String> propList = Collections.singletonList(prop);

			Result result = query(prop, k);

			if (result instanceof SatResult || result instanceof UnknownResult) {
				
				if (result instanceof SatResult) {
					Model model = getModel(result);
					if (model == null) {
						sendUnknown(propList);
						properties.clear();
						break;
					}
					sendInductiveCounterexamples(propList, k + 1, model);
				}

				if (result instanceof UnknownResult) {
					sendUnknown(propList);
				}
			} else if (result instanceof UnsatResult) {
				addPropertiesAsInvariants(k, propList);
				sendValid(propList, k);
			}
		}
	}
	
	private Result query(String prop, int k){
		if(!(solver instanceof Cvc4MultiSolver)){
			throw new JKindException("QuantifiedBmcEngine expects "
					+ "its solver to be the Cvc4MultiSolver");
		}
		if(!properties.contains(prop)){
			throw new JKindException("We assume that this property "
					+ "is in the properties at the begining of the call");
		}
		
		Cvc4MultiSolver multiSolver = (Cvc4MultiSolver)solver;
		Sexp sexp = getInductiveQuery(k,prop);
		
		multiSolver.asyncQuery(sexp);
		
		while(!multiSolver.asyncQueryCompleted() && properties.contains(prop)){
			processMessages();
			try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
		}
		
		if(properties.contains(prop)){
			return multiSolver.getAsyncQueryResult();
		}
		
		multiSolver.cancelAsyncQuery();
		return new UnknownResult();
	}
	
	@Override
	protected void addPropertiesAsInvariants(int k, List<String> valid) {
		List<Expr> newInvariants = new ArrayList<>();
		for(String str : valid){
			newInvariants.add(exprMap.get(str));
		}
		invariants.addAll(newInvariants);
		assertNewInvariants(newInvariants, k);
	}
	
	protected Sexp getInductiveQuery(int k, String possiblyValid){
		return getInductiveQuery(k, Collections.singletonList(possiblyValid));
	}
	
	@Override
	protected Sexp getInductiveQuery(int k, List<String> possiblyValid) {
		
		List<Sexp> hyps = new ArrayList<>();
		
		for (int i = 0; i < k; i++) {
			hyps.add(conjoin(toSexps(possiblyValid, i)));
		}
		Sexp conc = conjoin(toSexps(possiblyValid, k));

		return new Cons("=>", SexpUtil.conjoin(hyps), conc);
	}
	
	private List<Sexp> toSexps(List<String> props, int k){
		List<Sexp> sexps = new ArrayList<>();
		Lustre2Sexp translater = new Lustre2Sexp(k);
		for(String prop : props){
			Expr expr = exprMap.get(prop);
			sexps.add(expr.accept(translater));
		}
		return sexps;
	}
	
	private Sexp conjoin(List<Sexp> sexps){
		return new Cons("and", sexps);
	}
	
}
