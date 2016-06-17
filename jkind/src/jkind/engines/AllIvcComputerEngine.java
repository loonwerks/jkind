package jkind.engines;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import jkind.JKindException;
import jkind.JKindSettings;
import jkind.engines.messages.BaseStepMessage;
import jkind.engines.messages.EngineType;
import jkind.engines.messages.InductiveCounterexampleMessage;
import jkind.engines.messages.InvalidMessage;
import jkind.engines.messages.InvariantMessage;
import jkind.engines.messages.Itinerary;
import jkind.engines.messages.UnknownMessage;
import jkind.engines.messages.ValidMessage;
import jkind.lustre.Expr;
import jkind.lustre.NamedType;
import jkind.lustre.VarDecl;
import jkind.sexp.Cons;
import jkind.sexp.Sexp;
import jkind.sexp.Symbol;
import jkind.solvers.Result;
import jkind.solvers.SatResult;
import jkind.solvers.UnknownResult;
import jkind.solvers.UnsatResult;
import jkind.translation.Lustre2Sexp;
import jkind.translation.Specification;
import jkind.util.LinkedBiMap;
import jkind.util.SexpUtil;

public class AllIvcComputerEngine extends IvcReductionEngine {
	public AllIvcComputerEngine(Specification spec, JKindSettings settings, Director director) {
		super(spec, settings, director);
		// TODO Auto-generated constructor stub
	}
	public static final String NAME = "all-ivc-computer"; 
	private Set<Symbol> mustIvcs = new HashSet<Symbol>();
	private Set<Symbol> currIvcs = new HashSet<Symbol>();
	private Symbol currIvc = new Symbol("");
	private int reductionCounter = -1;


}
