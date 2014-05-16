package jkind.analysis.evaluation;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jkind.Output;
import jkind.lustre.BinaryExpr;
import jkind.lustre.BinaryOp;
import jkind.lustre.Constant;
import jkind.lustre.IdExpr;
import jkind.lustre.Program;
import jkind.lustre.values.IntegerValue;
import jkind.lustre.values.RealValue;
import jkind.lustre.values.Value;
import jkind.lustre.visitors.AstIterVisitor;
import jkind.util.BigFraction;

public class DivisionChecker extends AstIterVisitor {
	private ConstantEvaluator constantEvaluator;
	private final Map<String, Constant> constantDefinitions = new HashMap<>();

	public static boolean check(Program program) {
		try {
			new DivisionChecker().visit(program);
			return true;
		} catch (DivisionException e) {
			return false;
		}
	}

	@Override
	protected void visitConstants(List<Constant> constants) {
		for (Constant c : constants) {
			constantDefinitions.put(c.id, c);
		}

		// In order to use a ConstantEvaluator, we first have to ensure that the
		// constants themselves don't have division by zero. This is a bit
		// tricky due to constants being defined in any order.
		constantEvaluator = new ConstantEvaluator() {
			@Override
			public Value visit(IdExpr e) {
				Value value = super.visit(e);
				if (value == null) {
					// Check constants on the fly
					checkConstant(constantDefinitions.get(e.id));
				}
				return value;
			}
		};

		for (Constant c : constants) {
			if (!constantEvaluator.containsConstant(c.id)) {
				checkConstant(c);
			}
		}
	}
	
	private void checkConstant(Constant c) {
		c.accept(this);
		constantEvaluator.addConstant(c);
	}

	@Override
	public Void visit(BinaryExpr e) {
		e.left.accept(this);
		e.right.accept(this);

		if (e.op == BinaryOp.DIVIDE || e.op == BinaryOp.INT_DIVIDE || e.op == BinaryOp.MODULUS) {
			int rightSignum = signum(e.right.accept(constantEvaluator));

			if (rightSignum == 0) {
				Output.error(e.location, "division by zero");
				throw new DivisionException();
			} else if (rightSignum < 0 && e.op == BinaryOp.INT_DIVIDE) {
				Output.error(e.location, "integer division by negative numbers is disabled");
				throw new DivisionException();
			} else if (rightSignum < 0 && e.op == BinaryOp.MODULUS) {
				Output.error(e.location, "modulus by negative numbers is disabled");
				throw new DivisionException();
			}
		}

		return null;
	}

	private int signum(Value value) {
		if (value instanceof IntegerValue) {
			IntegerValue iv = (IntegerValue) value;
			return iv.value.compareTo(BigInteger.ZERO);
		} else if (value instanceof RealValue) {
			RealValue rv = (RealValue) value;
			return rv.value.compareTo(BigFraction.ZERO);
		} else {
			/*
			 * This should only arise for non-constant division which is
			 * currently only enabled for Z3. We return 1 to allow everything to
			 * go through. This allows, for example, users to divide by a
			 * negative integer which gives different results for different
			 * solvers, or even to divide by 0. We allow this, but may change
			 * the semantics of those operations later.
			 */
			return 1;
		}
	}
}
