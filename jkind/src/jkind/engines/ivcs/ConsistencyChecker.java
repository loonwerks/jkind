package jkind.engines.ivcs;  
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List; 
import java.util.Set; 
import jkind.JKind;
import jkind.JKindException;
import jkind.JKindSettings; 
import jkind.SolverOption;
import jkind.engines.Director;
import jkind.engines.MiniJKind;
import jkind.engines.SolverBasedEngine;
import jkind.engines.ivcs.messages.ConsistencyMessage;
import jkind.engines.messages.BaseStepMessage;
import jkind.engines.messages.EngineType;
import jkind.engines.messages.InductiveCounterexampleMessage;
import jkind.engines.messages.InvalidMessage;
import jkind.engines.messages.InvariantMessage;
import jkind.engines.messages.Itinerary;
import jkind.engines.messages.UnknownMessage;
import jkind.engines.messages.ValidMessage; 
import jkind.lustre.BinaryExpr;
import jkind.lustre.BinaryOp;
import jkind.lustre.BoolExpr; 
import jkind.lustre.Equation;
import jkind.lustre.Expr;
import jkind.lustre.IdExpr; 
import jkind.lustre.IntExpr;
import jkind.lustre.NamedType;
import jkind.lustre.Node; 
import jkind.lustre.RealExpr;  
import jkind.lustre.UnaryExpr;
import jkind.lustre.UnaryOp;
import jkind.lustre.VarDecl;
import jkind.lustre.builders.NodeBuilder;
import jkind.lustre.values.IntegerValue;
import jkind.lustre.values.RealValue;
import jkind.lustre.values.Value;  
import jkind.lustre.visitors.TypeAwareAstMapVisitor;
import jkind.results.Counterexample;
import jkind.results.Signal; 
import jkind.sexp.Cons;
import jkind.sexp.Sexp;
import jkind.sexp.Symbol;
import jkind.slicing.Dependency;
import jkind.slicing.DependencySet;
import jkind.slicing.DependencyVisitor; 
import jkind.solvers.Result;
import jkind.solvers.SatResult; 
import jkind.solvers.z3.Z3Solver; 
import jkind.translation.Lustre2Sexp; 
import jkind.translation.Specification;
import jkind.util.Util; 

public class ConsistencyChecker  extends SolverBasedEngine {
	public static final String NAME = "consistency-checker";
	private Specification localSpec; 
	private Z3Solver z3Solver;
	private ConsistencyMessage message = null;
	private static final int BMC_STEPS = 3; 
	
	public ConsistencyChecker(Specification spec, JKindSettings settings, Director director) {
		super(NAME, spec, settings, director);  
	}
	
	@Override
	protected void initializeSolver() {
		solver = getSolver();
		solver.initialize();
		z3Solver = (Z3Solver) solver;
		solver.define(new VarDecl(INIT.str, NamedType.BOOL));
		createVariables(0);
		createVariables(1); 
	}

	@Override
	public void main() {
		processMessagesAndWaitUntil(() -> properties.isEmpty());
	}

	private void check(ValidMessage vm) {
		for (String property : vm.valid) {
			if (properties.remove(property)) {
				message = new ConsistencyMessage(vm);
				check();
			}
		}
	}
	
	private void check() {
		if (!preCheck(message.vm.valid.get(0))){
				/*
				   create an over-approx of the model with IVCs
				   if the first check is passed,
				   will make all the assertions regular equations
				   define a new property which is the same as conjunction of the assertions
				   we need this over-approx node because 
			           1- we might have several IVCs
			           2- it would be cheaper to verify
				   if proof goes through ==> no inconsistency
				   otherwise ==> it will find an example to show the inconsistency 
				*/
				Node main = overApproximateWithIvc(spec.node);
				if(main == null){
					sendValid();
					return;
				} 
				main = IvcUtil.setIvcArgs(main, IvcUtil.getAllAssigned(main)); 
				localSpec = new Specification(main, settings.noSlicing);  
				checkConsistency();
		}else{
			sendValid();
		}
	}

	private boolean preCheck(String prop) {
		JKindSettings js = new JKindSettings(); 
		js.noSlicing = true; 
		js.miniJkind = true;
		js.reduceIvc = true;
		js.allAssigned = true;
		js.pdrMax = 0;  
 Node test = unassignProp(prop);
		MiniJKind miniJkind = new MiniJKind (new Specification(test, settings.noSlicing), js);
		try{
			miniJkind.verify();   
		}catch(IllegalStateException e){
			System.out.println("IllegalStateException in Consistency checker: status = "+miniJkind.getPropertyStatus());
		}
		if (miniJkind.getPropertyStatus() == MiniJKind.VALID) {
			message.setConsistencyMsgWithUc(findRightSide(miniJkind.getPropertyIvc())); 
			return true;
		}
		else{   
			return false;
		}
	}
	
	private void checkConsistency() {
		JKindSettings js = new JKindSettings(); 
		js.noSlicing = true; 
		js.miniJkind = true; 

		MiniJKind miniJkind = new MiniJKind (localSpec, js);
        miniJkind.verify();   
       
		if (miniJkind.getPropertyStatus() == MiniJKind.VALID) {
			if(postCheck()){ 
				message.setNoInConsistency();  
				sendValid();
				return;
			}
		}
		else if (miniJkind.getPropertyStatus() == MiniJKind.INVALID) {   
			message.setConsistencyMsgWithCex(renameSignals(miniJkind.getInvalidModel()));
		}
		else{ 
			message.setConsistencyMsgWithUc(findRightSide(new ArrayList<>(message.vm.ivc)));
		}
		sendValid();
	}

	private boolean postCheck() {
		JKindSettings temp = new JKindSettings();
		temp.n = BMC_STEPS;
		temp.solver =  SolverOption.Z3;
		Set<String> result = new BmcBasedConsistencyChecker(spec, temp).acceptWithNoDirector(message.vm.valid.get(0));
		if (result != null){ 
			message.setConsistencyMsgWithUc(findRightSide(new ArrayList<>(result)));
			sendValid();
			return false;
		}
		return true;
	}

	private Node overApproximateWithIvc(Node node) { 
		List<Equation> equations = removeEquations(node.equations, message.vm.ivc); 
		List<VarDecl> locals = removeVariables(node.locals, equations);
		List<VarDecl> outputs = removeVariables(node.outputs, equations);
		List<VarDecl> inputs = new ArrayList<>(node.inputs);
		try{
			removeAssertions(node.assertions, equations, inputs, locals);
		}catch(JKindException e){
			return null;
		}

		NodeBuilder builder = new NodeBuilder(node);  
		builder.clearAssertions();
		builder.clearInputs();
		builder.clearProperties().addProperty(defineNewPropertyForT(equations, locals, outputs));
		builder.clearLocals().addLocals(locals);
		builder.clearOutputs().addOutputs(outputs);
		builder.clearEquations().addEquations(equations); 
		return builder.build();
	}
	
	private void removeAssertions(List<Expr> assertions, List<Equation> equations, List<VarDecl> inputs, List<VarDecl> locals) {
		List<String> strAsr = new ArrayList<>();
		// we suppose that whenever this engine is used, -all_assigned flag is activate
		// so all assertions are of type of IdExpr 
		for(Expr asr : assertions){
			strAsr.add(asr.toString());
		}
		
		// collect equations that are asserted
		List<Equation> neededAssertions = new ArrayList<>();
		for (Equation eq : equations){
			if(strAsr.contains(eq.lhs.get(0).id)){
				neededAssertions.add(eq);
			}
		}
		// find inputs consumed in the collected assertions
		Set<VarDecl> neededInputs = getRequiredInputs(inputs, neededAssertions);
		if (passAssumCons(equations)){
			for(VarDecl i : neededInputs){ 
				defineNewEquation(getValue(i, equations, assertions), locals, equations, i);
			}
		}
	}

	private Set<VarDecl> getRequiredInputs(List<VarDecl> inputs, List<Equation> neededAssertions) {
		Set<VarDecl> ret = new HashSet<>();
		for (Equation eq : neededAssertions){
			containsInput(eq.expr, inputs, ret);
		}
		return ret;
	}

	private boolean passAssumCons(List<Equation> eqs) {
		// IGNORE THIS METHOD FOR NOW... NEEDS FURTHER RESEARCH
		/*comment("Checking consistency over assertion: ");
		solver.push();
		assertEquations(eqs);
		solver.assertSexp(new Cons("not", getSexpEquation(getEquation(message.vm.valid.get(0), spec.node.equations))));
		Result result = z3Solver.checkSat(new ArrayList<>(), true, false);
		if(result instanceof UnsatResult){
			solver.pop();
			return true;
		}
		Model model = ((SatResult) result).getModel();  
		solver.pop();
		message.setConsistencyMsgWithUc(message.vm.ivc);
		throw new JKindException (""); */
		return true;
	}

	private Expr getValue(VarDecl i, List<Equation> equations, List<Expr> assertions) {
		z3Solver.push();
		comment("Making "+ i.id + " a local variable:");
		assertAssertions(i, assertions, equations);
		assertEquations(equations);  
		Expr preVal = findValue(i.id, true, equations); 
		solver.assertSexp(new Cons ("=", new IdExpr(i.id).accept(new Lustre2Sexp(0)), new Symbol(preVal.toString())));
		Expr postVal = findValue(i.id, false, equations);
		z3Solver.pop();
		return new BinaryExpr(preVal, BinaryOp.ARROW, postVal);
	}
	
	

	private void assertAssertions(VarDecl i, List<Expr> assertions, List<Equation> equations) {
		Expr rs = new IdExpr("");
		for (Expr asr : assertions){
			rs = getEquation(asr.toString(), equations).expr; 
			if(containsVar(rs, i)){  
				solver.assertSexp(asr.accept(new Lustre2Sexp(1)));
			}
		}
	}

	private Expr findValue(String in, boolean init, List<Equation> equations) {
		z3Solver.push();
		if(init){
			solver.assertSexp(new Cons("=", INIT, Sexp.fromBoolean(true)));
		}else{
			solver.assertSexp(new Cons("=", INIT, Sexp.fromBoolean(false)));
		}
		Result result = z3Solver.checkSat(new ArrayList<>(), true, false);
		if(result instanceof SatResult){
			Sexp var = (new IdExpr(in)).accept(new Lustre2Sexp(1));
			inlinePre(in, equations, valueToExpr(((SatResult)result).getModel().getValue(var.toString())));
			z3Solver.pop();
			return valueToExpr(((SatResult)result).getModel().getValue(var.toString()));
		}else{ 
			message.setConsistencyMsgWithUc(findRightSide(new ArrayList<>(message.vm.ivc)));
			throw new JKindException ("");
		}
	}

	private void inlinePre(String in, List<Equation> equations, Expr valueToExpr) {
		List<Equation> itr = new ArrayList<>(equations);
		for(Equation eq : itr){
			Expr newEq = new ReplacePres(in, eq.expr, valueToExpr).check();  
			equations.remove(eq);
			equations.add(new Equation(eq.lhs.get(0), newEq));
		}
	}

	private Expr valueToExpr(Value value) {
		if(value instanceof RealValue){
			return new RealExpr(new BigDecimal(((RealValue)value).value.toString()));
		}else if(value instanceof IntegerValue){
			return new IntExpr(new BigInteger(((IntegerValue)value).value.toString()));
		}else{
			return new BoolExpr(((jkind.lustre.values.BooleanValue)value).value);
		}
	}

	private void assertEquations(List<Equation> equations) { 
		for (Equation eq : equations) {
			z3Solver.assertSexp(getSexpEquation(eq));
		}
	}
	
	private Sexp getSexpEquation(Equation eq) {
		Lustre2Sexp visitor = new Lustre2Sexp(1); 
		Sexp body = eq.expr.accept(visitor);
		Sexp head = eq.lhs.get(0).accept(visitor); 
		return new Cons("=", head, body);
	}
	
	
	private boolean containsInput(Expr eq, List<VarDecl> inputs, Set<VarDecl> neededInputs) {
		DependencySet ds = DependencyVisitor.get(eq);
		boolean ret = false;
		List<VarDecl> itr = new ArrayList<>(inputs);
		for(VarDecl i : itr){
			if(ds.contains(i.id)){
				neededInputs.add(i); 
				inputs.remove(i);
				ret = true;
			}
		} 
		return ret;
	}
	
	private boolean containsVar(Expr rs, VarDecl input) {
		DependencySet ds = DependencyVisitor.get(rs);
		for(Dependency d : ds){
			if(d.name.equals(input.id)){
					return true;
			}
		}
		
		return false;
	}

	private List<Equation> removeEquations(List<Equation> equations, Collection<String> ivc) {
		Set<Equation> ret = new HashSet<>(); 
		Set<Equation> right = new HashSet<>();
		for(Equation eq : equations){
			if(ivc.contains(eq.lhs.get(0).id)){
				ret.add(eq); 
				right.add(eq);
			}
		} 
		for(VarDecl i : spec.node.locals){
			for(Equation e : right){
				if (containsVar (e.expr, i)){
					ret.add(getEquation(i.id, equations));
				}
			}
		}
		for(VarDecl i : spec.node.outputs){
			for(Equation e : right){
				if (containsVar (e.expr, i)){
					ret.add(e);
				}
			}
		}

		return new ArrayList<>(ret); 
	}
	
	private Equation getEquation(String i, List<Equation> equations) {
		for(Equation eq : equations){
			if(eq.lhs.get(0).id.equals(i))
				return eq;
		}
		return null;
	}

	private List<VarDecl> removeVariables(List<VarDecl> vars, List<Equation> equations) {
		Set<VarDecl> ret = new HashSet<>();
		List<String> left = new ArrayList<>(); 
		
		for(Equation eq : equations){
			left.add(eq.lhs.get(0).id); 
		}
		for(VarDecl v : vars){
			if(left.contains(v.id)){
				ret.add(v);
			}
		}
		return new ArrayList<>(ret); 
	}

	private String defineNewPropertyForT(List<Equation> equations, List<VarDecl> locals, List<VarDecl> outputs) {
		Expr prop = new BoolExpr(true); 
		for(Equation eq : equations){
			if(isAssertion(eq)){
				prop = new BinaryExpr(prop, BinaryOp.AND, eq.lhs.get(0));
			}
		}  
		prop = new BinaryExpr(new BoolExpr(true), BinaryOp.ARROW, prop);
		String newVar = "__newPrpAddedByConsistencyChecker_by_JKind__";
		defineNewEquation(prop, locals, equations, new VarDecl(newVar, NamedType.BOOL));
		return newVar;
	}
	
	private boolean isAssertion(Equation eq) {
		for(Expr asr : spec.node.assertions){
			if (asr.toString().equals(eq.lhs.get(0).id))
				return true;
		}
		return false;
	}
	
	private void defineNewEquation(Expr rightSide, List<VarDecl> locals, List<Equation> equations, VarDecl i) {
		locals.add(i); 
		equations.add(new Equation(new IdExpr(i.id), rightSide));
	}
 
	
	private Node unassignProp(String property) {
		List<VarDecl> inputs = new ArrayList<>(spec.node.inputs); 
		inputs.add(new VarDecl(property, Util.getTypeMap(spec.node).get(property))); 
		List<VarDecl> locals = removeVariable(spec.node.locals, property);
		List<VarDecl> outputs = removeVariable(spec.node.outputs, property);
		List<Equation> equations = new ArrayList<>(spec.node.equations);
		List<Expr> assertions = new ArrayList<>(spec.node.assertions); 
		Set<String> keepAsr = new HashSet<>();
		
		equations = removeEquations(equations, message.vm.ivc); 
		equations.remove(getEquation(property, equations));
		Iterator<Expr> iter0 = assertions.iterator();
		for(Equation eq : equations){
			keepAsr.add(eq.expr.toString());
		} 
		while (iter0.hasNext()) {
			Expr asr = iter0.next();
			if(!(keepAsr.contains(asr))){
				iter0.remove(); 
			}else if (property.equals(asr.toString())) {
				iter0.remove(); 
				}
		}  
		locals = removeVariables(locals, equations);
		outputs = removeVariables(outputs, equations);

		NodeBuilder builder = new NodeBuilder(spec.node);
		builder.clearInputs().addInputs(inputs);
		builder.clearLocals().addLocals(locals);
		builder.clearOutputs().addOutputs(outputs);
		builder.clearEquations().addEquations(equations);
		builder.clearAssertions().addAssertions(assertions); 
		builder.clearProperties().addProperty(property); 
		builder.clearIvc();
		return builder.build();
	}
	
	private List<VarDecl> removeVariable(List<VarDecl> varDecls, String property) {
		List<VarDecl> result = new ArrayList<>(varDecls);
		Iterator<VarDecl> iter = result.iterator();
		while (iter.hasNext()) {
			if (property.equals(iter.next().id)) {
				iter.remove(); 
				break;
			}
		}
		return result;
	}
	
	private Counterexample renameSignals(Counterexample cex) {
		Counterexample ret = new Counterexample(cex.getLength());
		for (Signal<Value> signal : cex.getSignals()) { 
			ret.addSignal(signal.rename(findRightSide(signal.getName())));  
		}   
		return ret;
	}
	
	private Set<String> findRightSide(List<String> propertyIvc) {
		Set<String> ret = new HashSet<>(); 
		for (String core : propertyIvc){
			String rn = findRightSide(core);
			if (rn != ""){
				ret.add(rn);
			}
		}
		return ret;
	}
	
	private String findRightSide(String name) { 
		if(name.contains(JKind.EQUATION_NAME)){
			for(Equation eq : spec.node.equations){
				if(name.equals(eq.lhs.get(0).id)){
					return ("assert "+ eq.expr.toString());
				}
			}
		}else if(name.contains("__newPrpAddedByConsistencyChecker_by_JKind__")){
			return "";
		}
		return name;

	}

	private void sendValid() {
		Set<String> ivc = new HashSet<>();
		for(String core : message.vm.ivc){
			ivc.add(findRightSide(core));
		}
		message.vm = new ValidMessage (message.vm.source, message.vm.valid, message.vm.k,
		        message.vm.invariants, ivc, null, null);
		director.writeConsistencyCheckerResults(message); 
		if(! settings.allIvcs){ 
			Itinerary itinerary = message.vm.getNextItinerary();
			director.broadcast(new ValidMessage(message.vm.source, message.vm.valid, message.vm.k,
					        message.vm.invariants, ivc, itinerary, null));
		}
	}

	@Override
	protected void handleMessage(BaseStepMessage bsm) { 
	}

	@Override
	protected void handleMessage(InductiveCounterexampleMessage icm) { 
	}

	@Override
	protected void handleMessage(InvalidMessage im) { 		
	}

	@Override
	protected void handleMessage(InvariantMessage im) { 
	}

	@Override
	protected void handleMessage(UnknownMessage um) { 
	}

	@Override
	protected void handleMessage(ValidMessage vm) {
		if (vm.getNextDestination() == EngineType.CONSISTENCY_CHECKER) {
			check(vm);
		}
	}
	
	public void handleMessage(ConsistencyMessage cm) {
		this.message  = cm;
		check();
	}
	
	private class ReplacePres extends TypeAwareAstMapVisitor{ 
	    Expr eq;
		Expr value;
		String name;
		ReplacePres(String in, Expr e, Expr val){
			value = val;
			eq =  e;
			name = in; 
		}
		public Expr check() { 
		    return eq.accept (new ReplacePres(name, eq, value));
		}

		@Override
		public Expr visit(UnaryExpr e) {
			if (e.op == UnaryOp.PRE) { 
				if(e.expr.toString().equals(name)){  
					return value;
				}else return new UnaryExpr(UnaryOp.PRE, e.expr);
			} 
			return new UnaryExpr(e.op, e.expr.accept(this));
		}

	}

}
