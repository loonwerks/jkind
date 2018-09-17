package jkind.faultseeder;


/***
 * 
 * ComputeFaultSitesVisitor
 * ========================
 * 
 * 
 * 
 */
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.ThreadLocalRandom;

import jkind.lustre.BinaryExpr;
import jkind.lustre.BinaryOp;
import jkind.lustre.BoolExpr;
import jkind.lustre.Expr;
import jkind.lustre.IdExpr;
import jkind.lustre.IntExpr;
import jkind.lustre.NamedType;
import jkind.lustre.Node;
import jkind.lustre.NodeCallExpr;
import jkind.lustre.RealExpr;
import jkind.lustre.Type;
import jkind.lustre.UnaryExpr;
import jkind.lustre.UnaryOp;
import jkind.lustre.visitors.AstMapVisitor;
import jkind.lustre.visitors.TypeReconstructor;

public class ComputeFaultSitesVisitor extends AstMapVisitor {
	private final FaultSites sites = new FaultSites();
	private final FaultReplacementLocations repls; 
	private final boolean linearMutantsOnly; 
	
	private TypeReconstructor reconstructor; 
	
	public ComputeFaultSitesVisitor(TypeReconstructor reconstructor, 
									boolean linearMutantsOnly,
									FaultReplacementLocations repls) {
		this.reconstructor = reconstructor;
		this.repls = repls;
		this.linearMutantsOnly = linearMutantsOnly;
	}

	public FaultSites getFaultSites() {
		return sites;
	}
	
	@Override
	public Node visit(Node n) {
		this.reconstructor.setNodeContext(n);
		return super.visit(n);
	}
	
	// No need to override
	// 	ArrayAccessExpr
	//	ArrayExpr
	//	ArrayUpdateExpr
	// 	CastExpr
	//
	
	public BinaryOp randomRelationalOp() {
		int ordinal = ThreadLocalRandom.current().nextInt(0, 6);
		switch(ordinal) {
		case 0: return BinaryOp.EQUAL;
		case 1: return BinaryOp.NOTEQUAL;
		case 2: return BinaryOp.GREATER;
		case 3: return BinaryOp.LESS;
		case 4: return BinaryOp.GREATEREQUAL;
		case 5: return BinaryOp.LESSEQUAL;
		default: 
			throw new IllegalArgumentException("Ordinal out of range");
		}
	}
	
	public BinaryOp randomBooleanOp() {
		int ordinal = ThreadLocalRandom.current().nextInt(0, 4);
		switch(ordinal) {
		case 0: return BinaryOp.OR;
		case 1: return BinaryOp.AND;
		case 2: return BinaryOp.XOR;
		case 3: return BinaryOp.IMPLIES;
		default: 
			throw new IllegalArgumentException("Ordinal out of range");
		}
	}

	public BinaryOp randomArithOp() {
		int ordinal = ThreadLocalRandom.current().nextInt(0, 6);
		switch(ordinal) {
		case 0: return BinaryOp.PLUS;
		case 1: return BinaryOp.MINUS;
		case 2: return BinaryOp.MULTIPLY;
		case 3: return BinaryOp.DIVIDE; 
		case 4: return BinaryOp.INT_DIVIDE;
		case 5: return BinaryOp.MODULUS;
		default: 
			throw new IllegalArgumentException("Ordinal out of range");
		}		
	}

	public BinaryOp randomEqualityOp() {
		int ordinal = ThreadLocalRandom.current().nextInt(0, 2);
		switch(ordinal) {
		case 0: return BinaryOp.EQUAL;
		case 1: return BinaryOp.NOTEQUAL;
		default: 
			throw new IllegalArgumentException("Ordinal out of range");
		}
	}


	public BinaryExpr checkArithBinaryOpRepl(FaultSites oldSites, BinaryExpr e) {
		if (this.repls.arithmeticOp.contains(sites.arithmeticOp) && 
			this.sites.arithmeticOp != oldSites.arithmeticOp) {
			while (true) {
				BinaryOp op = randomArithOp();
				Type t = this.reconstructor.visit(e);
				
				// ensure correct type non-duplicate operator.
				if ((t == NamedType.INT && op == BinaryOp.DIVIDE) || 
					(t == NamedType.REAL && (op == BinaryOp.INT_DIVIDE || op == BinaryOp.MODULUS)) ||
					(op == e.op)) {
					continue;
				} else {
					BinaryExpr result = new BinaryExpr(e.location, e.left, op, e.right);
					System.out.println("Performing replacement " + sites.arithmeticOp + " from set: " + repls.arithmeticOp);
					System.out.println("location: " + e.location);
					System.out.println("Original expression: " + e);
					System.out.println("Replacement expression: " + result + "\n");
					if (linearMutantsOnly) {
						// For now, we treat all multiplications, divisions, etc. as non-linear.
						// We can revisit this by fixing the LinearChecker to allow 
						// expression-level checks.
						if (op == BinaryOp.DIVIDE || op == BinaryOp.INT_DIVIDE || 
							op == BinaryOp.MULTIPLY || op == BinaryOp.MODULUS) {
							continue;
						}
						/* 
						LinearChecker lc = new LinearChecker(Level.IGNORE);
						if (!lc.visit(result)) {
							// non-linear expression
						}
						*/
					}
					return result;
				}
			}
		}
		return e;
	}
	
	public BinaryExpr checkEqualityBinaryOpRepl(FaultSites oldSites, BinaryExpr e) {
		if (this.repls.equalityOp.contains(sites.equalityOp) && 
			this.sites.equalityOp != oldSites.equalityOp) {
			while (true) {
				BinaryOp op = randomEqualityOp();
				if (op != e.op) { return new BinaryExpr(e.location, e.left, op, e.right); }
			}
		}
		return e;
	}

	public BinaryExpr checkRelationalBinaryOpRepl(FaultSites oldSites, BinaryExpr e) {
		if (this.repls.relationalOp.contains(sites.relationalOp) &&
			this.sites.relationalOp != oldSites.relationalOp) {
			while (true) {
				BinaryOp op = randomRelationalOp();
				if ((op != e.op)) { return new BinaryExpr(e.location, e.left, op, e.right); }
			}
		}
		return e;
	}
	
	public BinaryExpr checkBooleanBinaryOpRepl(FaultSites oldSites, BinaryExpr e) {
		if (this.repls.booleanOp.contains(sites.booleanOp) && 
			this.sites.booleanOp != oldSites.booleanOp) {
			while (true) {
				BinaryOp op = randomBooleanOp();
				if ((op != e.op)) { return new BinaryExpr(e.location, e.left, op, e.right); }
			}
		}
		return e;
	}
	

	public BinaryExpr checkBinaryOpRepl(FaultSites oldSites, BinaryExpr e) {
		e = checkArithBinaryOpRepl(oldSites, e);
		e = checkEqualityBinaryOpRepl(oldSites, e);
		e = checkRelationalBinaryOpRepl(oldSites, e);
		e = checkBooleanBinaryOpRepl(oldSites, e);
		return e;
	}
	
	public Expr checkNegationOpRepl(FaultSites oldSites, Expr e) {
		if (this.repls.negationOp.contains(this.sites.negationOp) &&
			this.sites.negationOp != oldSites.negationOp) {
			Type t = e.accept(this.reconstructor);
			if (t == NamedType.BOOL) {
				return new UnaryExpr(e.location, UnaryOp.NOT, e);
			} else if (t == NamedType.INT || t == NamedType.REAL) {
				return new UnaryExpr(e.location, UnaryOp.NEGATIVE, e); 
			} else {
				throw new IllegalArgumentException("checkNegationOpRepl: expression is not of Boolean or Numeric Type");
			}
		}
		return e;
	}

	
	public Expr checkIncrementRepl(FaultSites oldSites, Expr e) {
		if (this.repls.incrementOp.contains(sites.incrementOp) && 
			this.sites.incrementOp != oldSites.incrementOp) {
			Type t = e.accept(this.reconstructor);
			boolean sign = ThreadLocalRandom.current().nextBoolean();
			BinaryOp op = sign ? BinaryOp.PLUS : BinaryOp.MINUS;
			if (t == NamedType.INT) {
				return new BinaryExpr(e.location, e, op, new IntExpr(e.location, BigInteger.ONE));
			} else if (t == NamedType.REAL) {
				return new BinaryExpr(e.location, e, op, new RealExpr(e.location, BigDecimal.ONE));
			} else {
				throw new IllegalArgumentException("checkConstantRepl: non-numeric type");
			}
		}
		return e;
	}
	
	public Expr checkConstantRepl(FaultSites oldSites, Expr e) {
		if (this.repls.constantOp.contains(sites.constantOp) && 
			this.sites.constantOp != oldSites.constantOp) {
			Type t = e.accept(this.reconstructor);
			DefaultValueExprVisitor exprVisitor = new DefaultValueExprVisitor();
			
			// currently not all types supported.
			try {
				e = t.accept(exprVisitor);
			} catch (IllegalArgumentException ie) { }
		}
		return e;
	}
	
	// TODO: MWW: this would be better if I made it an initialized delay.
	// Future work: requires a "default value for type" visitor to construct 
	// the initial value.  See DefaultValueVisitor.java.
	
	public Expr checkDelayRepl(FaultSites oldSites, Expr e) {
		if (this.repls.delayOp.contains(sites.constantOp) && 
			this.sites.delayOp != oldSites.delayOp) {
			return new UnaryExpr(UnaryOp.PRE, e);
		}
		return e;
	}
	
	// TODO: For now I am leaving out variable replacement faults and node parameter faults.
	@SuppressWarnings("unused")
	public Expr checkReplacementRepl(FaultSites oldSites, IdExpr e) {
		return e;
	}
	
	@SuppressWarnings("unused")
	public Expr checkNodeParameterRepl(FaultSites oldSites, NodeCallExpr e) {
		return e;
	}
	
	public Expr checkRepl(FaultSites oldSites, Expr e) {
		e = checkNegationOpRepl(oldSites, e);
		e = checkIncrementRepl(oldSites, e);
		e = checkConstantRepl(oldSites, e);
		e = checkDelayRepl(oldSites, e);
		
		// These are only for special cases: replacement only works for ID expressions, 
		// and parameter swapping only works for node calls.
		// e = checkReplacementRepl(oldSites, e);
		// e = checkNodeParameterRepl(oldSites, e);
		return e;
	}
	
	@Override
	public Expr visit(BinaryExpr e) {
		e = (BinaryExpr)super.visit(e);
		FaultSites oldSites = new FaultSites(sites);
		
		switch(e.op) {
		case PLUS: case MINUS: case MULTIPLY: case DIVIDE: case INT_DIVIDE: case MODULUS: {
			sites.arithmeticOp++; 
			sites.constantOp++; 
			sites.delayOp++; 
			sites.negationOp++;
			sites.incrementOp++;
			break;
		}
		case EQUAL: case NOTEQUAL: case GREATER: case LESS: case GREATEREQUAL: case LESSEQUAL: {
			Type t = e.left.accept(this.reconstructor);
			if (t instanceof NamedType) {
				NamedType nt = (NamedType)t; 
				if (nt == NamedType.INT || 
					nt == NamedType.REAL) {
					sites.relationalOp++; 
					sites.delayOp++; 
				}
				sites.equalityOp++; 
			}
			break;
		}
		case OR: case AND: case XOR: case IMPLIES: {
			sites.booleanOp++;
			sites.constantOp++;
			sites.delayOp++; 
			sites.negationOp++;
			sites.equalityOp++; 
			break;
		}
		case ARROW: {
			break;
		}
		}

		Expr result = checkBinaryOpRepl(oldSites, e);
		result = checkRepl(oldSites, result);
//		if (result != e) {
//			System.out.println("Modified result: " + result);
//		}
		return result;
	}
	
	@Override
	public Expr visit(BoolExpr e) {
		Expr result = super.visit(e);
		FaultSites oldSites = new FaultSites(sites); 
		
		sites.booleanOp++; 
		sites.constantOp++; 
		return checkRepl(oldSites, result);
	}
	
	// No cast expression override.
	// No condact expression override

	@Override
	public Expr visit(IdExpr e) {
		FaultSites oldSites = new FaultSites(sites); 
		
		sites.variableReplacement++;
		sites.delayOp++; 
		sites.constantOp++; 
		Type t = this.reconstructor.visit(e);
		if (t == NamedType.INT || t == NamedType.REAL) {
			sites.incrementOp++;
			sites.negationOp++;
		} else if (t == NamedType.BOOL) {
			sites.negationOp++; 
		}
		return checkRepl(oldSites, e);
	}
	
	// no override of ITE expression

	@Override
	public Expr visit(IntExpr e) {
		FaultSites oldSites = new FaultSites(sites); 

		sites.delayOp++; 
		sites.constantOp++; 
		sites.incrementOp++;
		sites.negationOp++;
		return checkRepl(oldSites, e);
	}
	
	@Override
	public Expr visit(NodeCallExpr e) {
		FaultSites oldSites = new FaultSites(sites); 
		e = (NodeCallExpr)super.visit(e);
		sites.nodeCallParameter++; 
		return checkRepl(oldSites, e);
	}

	@Override
	public Expr visit(RealExpr e) {
		FaultSites oldSites = new FaultSites(sites); 
		sites.delayOp++; 
		sites.constantOp++; 
		sites.incrementOp++;
		sites.negationOp++;
		return checkRepl(oldSites, e);
	}
	
	// @Override 
	// public Expr visit(RecordAccessExpr e) {

	// @Override
	// public Expr visit(RecordExpr e) {

	// @Override
	// public Expr visit(RecordUpdateExpr e) {

	// @Override
	// public Expr visit(TupleExpr e) {

	// @Override 
	// public Expr visit(UnaryExpr e) {		
}
