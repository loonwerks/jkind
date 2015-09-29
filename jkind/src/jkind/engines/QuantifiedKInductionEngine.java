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
			Result result = solver.query(getInductiveQuery(k, possiblyValid.get(0)));

			String prop = possiblyValid.remove(0);
			List<String> propList = Collections.singletonList(prop);

			if (result instanceof SatResult || result instanceof UnknownResult) {
				Model model = getModel(result);
				if (model == null) {
					sendUnknown(propList);
					properties.clear();
					break;
				}

				if (result instanceof UnknownResult) {
					sendUnknown(propList);
				}
				sendInductiveCounterexamples(propList, k + 1, model);
			} else if (result instanceof UnsatResult) {
				properties.removeAll(propList);
				addPropertiesAsInvariants(k, propList);
				sendValid(propList, k);
			}
		}
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
