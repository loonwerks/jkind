package jkind.engines;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import jkind.JKindException;
import jkind.JKindSettings;
import jkind.engines.messages.InvalidMessage;
import jkind.lustre.Expr;
import jkind.sexp.Sexp;
import jkind.solvers.Model;
import jkind.solvers.Result;
import jkind.solvers.SatResult;
import jkind.solvers.UnknownResult;
import jkind.solvers.UnsatResult;
import jkind.solvers.cvc4.Cvc4MultiSolver;
import jkind.translation.InductiveDataTypeSpecification;
import jkind.translation.Lustre2Sexp;
import jkind.translation.Lustre2SexpNoArrow;

public class QuantifiedBmcEngine extends BmcEngine{

	private final Map<String, Expr> exprMap;
	
	public QuantifiedBmcEngine(InductiveDataTypeSpecification spec, JKindSettings settings, Director director) {
		super(spec, settings, director);
		exprMap = spec.propertyExprs;
	}
	
	@Override
	protected void checkProperties(int k) {
		Result result;
		List<String> possiblyFalse = new ArrayList<>(properties);
		do {
			String prop = possiblyFalse.remove(0);
			List<String> singleProp = Collections.singletonList(prop);
			
			result = query(prop, k);
			if (result instanceof SatResult || result instanceof UnknownResult) {
				Model model = getModel(result);
				if (model == null) {
					sendUnknown(properties);
					properties.clear();
					break;
				}

				if (result instanceof SatResult) {
					sendInvalid(singleProp, k, model);
				} else {
					sendUnknown(singleProp);
				}
				
			}
		} while (!possiblyFalse.isEmpty());

		sendBaseStep(k);
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
		
		List<String> singleProp = Collections.singletonList(prop);
		Sexp sexp = toSexps(singleProp, k).get(0);
		
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
		return new UnsatResult();
	}
	
	private List<Sexp> toSexps(List<String> props, int k){
		List<Sexp> sexps = new ArrayList<>();
		Lustre2Sexp translater = new Lustre2SexpNoArrow(k);
		for(String prop : props){
			Expr expr = exprMap.get(prop);
			sexps.add(expr.accept(translater));
		}
		return sexps;
	}
	
	
	@Override
	protected void handleMessage(InvalidMessage im) {
		if (!im.source.equals(this.name)) {
			throw new JKindException("We did not expect another engine to send an invalid "
					+ "message while using Inductive Datatypes and/or Quantified Expressions");
		}
	}
	
}
