package jkind.analysis.evaluation;

import java.util.HashMap;
import java.util.Map;

import jkind.lustre.Constant;
import jkind.lustre.EnumType;
import jkind.lustre.Expr;
import jkind.lustre.IdExpr;
import jkind.lustre.Program;
import jkind.lustre.UnaryExpr;
import jkind.lustre.UnaryOp;
import jkind.lustre.values.EnumValue;
import jkind.lustre.values.Value;
import jkind.lustre.visitors.Evaluator;
import jkind.util.Util;

public class ConstantEvaluator extends Evaluator {
	private final Map<String, Value> constants = new HashMap<>();
	private final Map<String, Expr> constantDefinitions = new HashMap<>();

	public ConstantEvaluator() {
	}

	public ConstantEvaluator(Program program) {
		for (EnumType et : Util.getEnumTypes(program.types)) {
			for (String id : et.values) {
				constants.put(id, new EnumValue(id));
			}
		}

		for (Constant c : program.constants) {
			constantDefinitions.put(c.id, c.expr);
		}

		for (Constant c : program.constants) {
			addConstant(c);
		}
	}

	public Value addConstant(Constant c) {
		return constants.put(c.id, c.expr.accept(this));
	}

	public boolean containsConstant(String id) {
		return constants.containsKey(id);
	}

	@Override
	public Value visit(IdExpr e) {
		if (constants.containsKey(e.id)) {
			return constants.get(e.id);
		} else if (constantDefinitions.containsKey(e.id)) {
			return constantDefinitions.get(e.id).accept(this);
		} else {
			return null;
		}
	}

	@Override
	public Value visit(UnaryExpr e) {
		if (e.op == UnaryOp.PRE) {
			return e.expr.accept(this);
		} else {
			return super.visit(e);
		}
	}
}
