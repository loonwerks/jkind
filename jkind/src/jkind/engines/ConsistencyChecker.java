package jkind.engines; 
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
import jkind.Output;
import jkind.SolverOption;
import jkind.engines.messages.BaseStepMessage;
import jkind.engines.messages.EngineType;
import jkind.engines.messages.InductiveCounterexampleMessage;
import jkind.engines.messages.InvalidMessage;
import jkind.engines.messages.InvariantMessage;
import jkind.engines.messages.Itinerary;
import jkind.engines.messages.UnknownMessage;
import jkind.engines.messages.ValidMessage;
import jkind.lustre.ArrayAccessExpr;
import jkind.lustre.ArrayExpr;
import jkind.lustre.ArrayUpdateExpr;
import jkind.lustre.BinaryExpr;
import jkind.lustre.BinaryOp;
import jkind.lustre.BoolExpr;
import jkind.lustre.CastExpr;
import jkind.lustre.CondactExpr;
import jkind.lustre.Equation;
import jkind.lustre.Expr;
import jkind.lustre.IdExpr;
import jkind.lustre.IfThenElseExpr;
import jkind.lustre.IntExpr;
import jkind.lustre.NamedType;
import jkind.lustre.Node;
import jkind.lustre.NodeCallExpr;
import jkind.lustre.RealExpr;
import jkind.lustre.RecordAccessExpr;
import jkind.lustre.RecordExpr;
import jkind.lustre.RecordUpdateExpr;
import jkind.lustre.TupleExpr;
import jkind.lustre.UnaryExpr;
import jkind.lustre.UnaryOp;
import jkind.lustre.VarDecl;
import jkind.lustre.builders.NodeBuilder;
import jkind.lustre.values.IntegerValue;
import jkind.lustre.values.RealValue;
import jkind.lustre.values.Value; 
import jkind.lustre.visitors.ExprVisitor;
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
				// create an over-approx of the model with IVCs
				// if the first check is passed,
				// will make all the assertions regular equations
				// define a new property which is the same as transition system
				// we need this over-approx node because we might have several IVCs
				// if proof goes through ==> no inconsistency
				// otherwise ==> it will find an example to show the inconsistency 
				if (preCheck(property)){
					sendValid(property, vm);
				}
				else{ 
					JKindSettings temp = new JKindSettings();
					temp.n = 2;
					temp.solver =  SolverOption.Z3;
					if (! (new BmcBasedConsistencyChecker(spec, temp).acceptFromConsistencyChecker(property))){
						sendValid(property, vm);
						return;
					}
					Node main = overApproximateWithIvc(property, spec.node, vm.ivc, vm.invariants);
					if(main == null){
						sendValid(property, vm);
						return;
					} 
					main = setIvcArgs(main, getAllAssigned(main));  
					localSpec = new Specification(main, settings.noSlicing);  
					checkConsistency(property, vm);
				}
			}
		}
	}

	private boolean preCheck(String prop) {
		JKindSettings js = new JKindSettings(); 
		js.noSlicing = true; 
		js.miniJkind = true;
		js.reduceIvc = true;
		js.pdrMax = 0; 
		MiniJKind miniJkind = new MiniJKind (new Specification(unassignProp(prop), settings.noSlicing), js);
        miniJkind.verify();   
		if (miniJkind.getPropertyStatus() == MiniJKind.VALID) {
			printInconsistency("", miniJkind.getPropertyIvc()); 
			return true;
		}
		else{   
			return false;
		}
	}

	private void checkConsistency(String property, ValidMessage vm) {
		JKindSettings js = new JKindSettings(); 
		js.noSlicing = true; 
		js.miniJkind = true; 
		
		MiniJKind miniJkind = new MiniJKind (localSpec, js);
        miniJkind.verify();   
		if (miniJkind.getPropertyStatus() == MiniJKind.VALID) {
			printNoInconsistency();
		}
		else if (miniJkind.getPropertyStatus() == MiniJKind.INVALID) {  
			printInconsistency(miniJkind.getInvalidModel(), vm.ivc);
		}
		else{
			printInconsistency("", vm.ivc);
		}
		sendValid(property, vm);
	}

	private Node overApproximateWithIvc(String prop, Node node, Set<String> ivc, List<Expr> invariants) { 
		List<Equation> equations = removeEquations(node.equations, ivc); 
		List<VarDecl> locals = removeVariables(node.locals, equations);
		List<VarDecl> outputs = removeVariables(node.outputs, equations);
		try{
			removeAssertions(node.assertions, equations, node.inputs, locals);
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
		List<Equation> itr = new ArrayList<>(equations);
		for (Equation eq : itr){
			if(strAsr.contains(eq.lhs.get(0).id)){
				Set<VarDecl> neededInputs = new HashSet<>(); 
				if(containsInput(eq, inputs, neededInputs)){
					for(VarDecl i : neededInputs){ 
						defineNewEquation(getValue(i, equations, assertions), locals, equations, i);
					}
				}
			}
		}
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
		Expr rs = null;
		for (Expr asr : assertions){
			for(Equation eq : equations){
				if(eq.lhs.get(0).id.equals(asr.toString())){
					rs = eq.expr;
					break;
				}
			}
			if(containsInput(rs, i)){ 
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
			Output.println("---------------------------------------------------------------------------");
			Output.println("  Model is inconsistent with assertions on the input variable: " + in);
			Output.println("---------------------------------------------------------------------------");
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
		Lustre2Sexp visitor = new Lustre2Sexp(1); 
		for (Equation eq : equations) {
			Sexp body = eq.expr.accept(visitor);
			Sexp head = eq.lhs.get(0).accept(visitor); 
			z3Solver.assertSexp(new Cons("=", head, body));
		}
	}

	private boolean containsInput(Equation eq, List<VarDecl> inputs, Set<VarDecl> neededInputs) {
		DependencySet ds = DependencyVisitor.get(eq.expr);
		boolean ret = false;
		for(VarDecl i : inputs){
			if(ds.contains(i.id)){
				neededInputs.add(i);
				ret = true;
			}
		}
		return ret;
	}
	
	private boolean containsInput(Expr rs, VarDecl input) {
		DependencySet ds = DependencyVisitor.get(rs);
		if(ds.contains(input.id)){
				return true;
		}
		return false;
	}

	private List<Equation> removeEquations(List<Equation> equations, Set<String> ivc) {
		List<Equation> ret = new ArrayList<>();
		Set<Dependency> map = new HashSet<>();
		Set<String> depName = new HashSet<>();
		for(String core : ivc){ 
			try{
				map.addAll(spec.dependencyMap.get(core).getSet());
			}catch(NullPointerException e){}
		} 
		for(Dependency d : map){
			depName.add(d.name);
		}
		for(Equation eq : equations){
			if(ivc.contains(eq.lhs.get(0).id)){
				ret.add(eq);
			}else if(depName.contains(eq.lhs.get(0).id)){
				ret.add(eq);
			}
		} 
		return ret; 
	}
	
	private List<VarDecl> removeVariables(List<VarDecl> vars, List<Equation> equations) {
		List<VarDecl> ret = new ArrayList<>();
		List<String> left = new ArrayList<>();
		for(Equation eq : equations){
			left.add(eq.lhs.get(0).id);
		}
		for(VarDecl var : vars){
			if(left.contains(var.id)){
				ret.add(var);
			}
		}
		return ret; 
	}

	private String defineNewPropertyForT(List<Equation> equations, List<VarDecl> locals, List<VarDecl> outputs) {
		Expr prop = new BoolExpr(true); 
		for(Equation eq : equations){
			if(boolExpr(eq.lhs.get(0).id, locals, outputs)){
				prop = new BinaryExpr(prop, BinaryOp.AND, eq.lhs.get(0));
			}else{
				prop = new BinaryExpr(prop, BinaryOp.AND, new BinaryExpr(eq.lhs.get(0), BinaryOp.EQUAL, eq.expr));
			}
		}  
		prop = new BinaryExpr(new BoolExpr(true), BinaryOp.ARROW, prop);
		String newVar = "__newPrpAddedByConsistencyChecker_by_JKind__";
		defineNewEquation(prop, locals, equations, new VarDecl(newVar, NamedType.BOOL));
		return newVar;
	}
	
	private boolean boolExpr(String ivc, List<VarDecl> locals, List<VarDecl> outputs) {
		for(VarDecl var : locals){
			if(ivc.equals(var.id)){
				if(var.type.equals(NamedType.BOOL)){
					return true;
				}
				else return false;
			}
		}
		for(VarDecl var : outputs){
			if(ivc.equals(var.id)){
				if(var.type.equals(NamedType.BOOL)){
					return true;
				}
				else return false;
			}
		}
		return false;
	}
	
	private void defineNewEquation(Expr rightSide, List<VarDecl> locals, List<Equation> equations, VarDecl i) {
		locals.add(i); 
		equations.add(new Equation(new IdExpr(i.id), rightSide));
	}
 
	private static List<String> getAllAssigned(Node node) {
		List<String> result = new ArrayList<>();
		result.addAll(Util.getIds(node.locals));
		result.addAll(Util.getIds(node.outputs));
		return result;
	}
	
	private static Node setIvcArgs(Node node, List<String> newSupport) {
		return new NodeBuilder(node).clearIvc().addIvcs(newSupport).build();
	}
	
	private Node unassignProp(String property) {
		List<VarDecl> inputs = new ArrayList<>(spec.node.inputs);
		inputs.add(new VarDecl(property, Util.getTypeMap(spec.node).get(property))); 
		List<VarDecl> locals = removeVariable(spec.node.locals, property);
		List<VarDecl> outputs = removeVariable(spec.node.outputs, property);
		List<Equation> equations = new ArrayList<>(spec.node.equations);
		List<Expr> assertions = new ArrayList<>(spec.node.assertions);
		Iterator<Equation> iter = equations.iterator();
		while (iter.hasNext()) {
			Equation eq = iter.next();
			if (property.equals(eq.lhs.get(0).id)) {
				iter.remove(); 
				break;
			}
		}
		// maybe this is not necessary:
		Iterator<Expr> iter0 = assertions.iterator();
		while (iter0.hasNext()) {
			Expr asr = iter0.next();
			if (property.equals(asr.toString())) {
				iter0.remove(); 
				break;
			}
		}

		NodeBuilder builder = new NodeBuilder(spec.node);
		builder.clearInputs().addInputs(inputs);
		builder.clearLocals().addLocals(locals);
		builder.clearOutputs().addOutputs(outputs);
		builder.clearEquations().addEquations(equations);
		builder.clearAssertions().addAssertions(assertions); 
		builder.clearProperties().addProperty(property);
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
	
	private void printInconsistency(String invalidModel, Collection<String> ivc) {
		Output.println("----------------------------------------------------------------------");
		Output.println("  Model is inconsistent:");  
		if(invalidModel == ""){
			Output.println("  some inconsistent parts:");  
			for(String core : ivc){
				Output.println("      - " +  findRightSide(core)); 
			}
		}else{ 
			String[] cex = invalidModel.split("\n");
			for(String core : ivc){
				Output.println("     "+ parseModel(cex, core)); 
			}
		}
		Output.println("----------------------------------------------------------------------");
	}

	private void printNoInconsistency() {
		Output.println("-------------------------------");
		Output.println("  No inconsistency was found!");
		Output.println("-------------------------------");
	}
	
	private String parseModel(String[] cex, String ivc) { 
		String ret = "";
		for(String line : cex){
			if (line.split(" ")[0].equals(ivc)){ 
				String temp = line.substring(ivc.length() + 2);
				temp = temp.trim();
				if(ivc.contains(JKind.EQUATION_NAME)){
					ret = findRightSide(ivc) + ":\n                      ";
				}else{
					ret = ivc + ":\n                      ";
				}
				return ret + temp;
			}
		}
		return ret;
	}
	
	private String findRightSide(String ivc) {
		List<Equation> eqList;
		if(localSpec == null){
			eqList = spec.node.equations;
		}else{
			eqList = localSpec.node.equations;
		}
		for (Equation eq : eqList){
			if(ivc.equals(eq.lhs.get(0).id)){
				if(ivc.contains(MiniJKind.EQUATION_NAME ) || ivc.contains(JKind.EQUATION_NAME)){
					return "assert (" + eq.expr.toString() +")";
				}else{
					return ivc ;
				}
			}
		}
		return null;
	}

	private void sendValid(String valid, ValidMessage vm) {
		Itinerary itinerary = vm.getNextItinerary();
		director.broadcast(new ValidMessage(vm.source, valid, vm.k, vm.invariants, vm.ivc , itinerary, null)); 
	}

	@Override
	protected void handleMessage(BaseStepMessage bsm) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void handleMessage(InductiveCounterexampleMessage icm) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void handleMessage(InvalidMessage im) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void handleMessage(InvariantMessage im) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void handleMessage(UnknownMessage um) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void handleMessage(ValidMessage vm) {
		if (vm.getNextDestination() == EngineType.CONSISTENCY_CHECKER) {
			check(vm);
		}
		
	}
	
	
	private class ReplacePres implements ExprVisitor<Expr> { 
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
		public Expr visit(ArrayAccessExpr e) {
			return new ArrayAccessExpr(e.array.accept(this), e.index.accept(this)); 
		}

		@Override
		public Expr visit(ArrayExpr e) {
			visitExprs(e.elements);
			//not sure about this
			return null;
		}

		@Override
		public Expr visit(ArrayUpdateExpr e) {
			return new ArrayUpdateExpr(e.array.accept(this), e.index.accept(this), e.value.accept(this));
		}

		@Override
		public Expr visit(BinaryExpr e) {
			return new BinaryExpr(e.left.accept(this), e.op, e.right.accept(this));
		}

		@Override
		public Expr visit(BoolExpr e) {
			return new BoolExpr(e.value);
		}

		@Override
		public Expr visit(CastExpr e) {
			return e.expr.accept(this);
		}

		@Override
		public Expr visit(CondactExpr e) {
			//not sure about this one
			return new CondactExpr(e.clock.accept(this), ((NodeCallExpr) e.call.accept(this)), visitExprs(e.args));
		}

		@Override
		public Expr visit(IdExpr e) {
			return new IdExpr(e.id);
		}

		@Override
		public Expr visit(IfThenElseExpr e) {
			return new IfThenElseExpr(e.cond.accept(this), e.thenExpr.accept(this), e.elseExpr.accept(this));
		}

		@Override
		public Expr visit(IntExpr e) {
			return new IntExpr(e.value);
		}

		@Override
		public Expr visit(NodeCallExpr e) {
			//not sure
			visitExprs(e.args);
			return null;
		}

		@Override
		public Expr visit(RealExpr e) {
			return new RealExpr(e.value);
		}

		@Override
		public Expr visit(RecordAccessExpr e) {
			return new RecordAccessExpr(e.record.accept(this), e.field);
		}

		@Override
		public Expr visit(RecordExpr e) {
			//not sure
			visitExprs(e.fields.values());
			return null;
		}

		@Override
		public Expr visit(RecordUpdateExpr e) {
			return new RecordUpdateExpr(e.record.accept(this), e.field, e.value.accept(this));
		}

		@Override
		public Expr visit(TupleExpr e) {
			// not sure
			visitExprs(e.elements);
			return null;
		}

		@Override
		public Expr visit(UnaryExpr e) {
			if (e.op == UnaryOp.PRE) { 
				if(e.expr.toString().equals(name)){  
					return new UnaryExpr(UnaryOp.NEGATIVE, new UnaryExpr(UnaryOp.NEGATIVE, value));
				}else return new UnaryExpr(UnaryOp.PRE, e.expr);
			} 
			return new UnaryExpr(e.op, e.expr.accept(this));
		}

		protected List<Expr> visitExprs(Collection<Expr> list) {
			List<Expr> ret = new ArrayList<>();
			for (Expr e : list) {
				 ret.add(e.accept(this));
			}
			return ret;
		}
	}

}
