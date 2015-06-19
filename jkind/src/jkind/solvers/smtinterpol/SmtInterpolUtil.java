package jkind.solvers.smtinterpol;

import java.io.FileNotFoundException;
import java.math.BigInteger;

import jkind.JKindException;
import jkind.lustre.EnumType;
import jkind.lustre.NamedType;
import jkind.lustre.SubrangeIntType;
import jkind.lustre.Type;
import jkind.lustre.values.BooleanValue;
import jkind.lustre.values.IntegerValue;
import jkind.lustre.values.RealValue;
import jkind.lustre.values.Value;
import jkind.sexp.Cons;
import jkind.sexp.Sexp;
import jkind.sexp.Symbol;
import jkind.util.BigFraction;
import jkind.util.Util;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import de.uni_freiburg.informatik.ultimate.logic.ApplicationTerm;
import de.uni_freiburg.informatik.ultimate.logic.ConstantTerm;
import de.uni_freiburg.informatik.ultimate.logic.LoggingScript;
import de.uni_freiburg.informatik.ultimate.logic.Rational;
import de.uni_freiburg.informatik.ultimate.logic.Script;
import de.uni_freiburg.informatik.ultimate.logic.Sort;
import de.uni_freiburg.informatik.ultimate.logic.Term;
import de.uni_freiburg.informatik.ultimate.logic.TermVariable;
import de.uni_freiburg.informatik.ultimate.smtinterpol.smtlib2.SMTInterpol;

public class SmtInterpolUtil {
	public static Script getScript(String scratchBase) {
		Logger logger = Logger.getRootLogger();
		logger.setLevel(Level.OFF);

		Script baseScript = new SMTInterpol(logger);
		if (scratchBase == null) {
			return baseScript;
		}

		String filename = scratchBase + ".smt2";
		try {
			return new LoggingScript(baseScript, filename, true);
		} catch (FileNotFoundException e) {
			throw new JKindException("Unable to open scratch file: " + filename, e);
		}
	}

	public static Sort getSort(Script script, Type type) {
		if (type instanceof NamedType) {
			NamedType namedType = (NamedType) type;
			switch (namedType.name) {
			case "bool":
				return script.sort("Bool");
			case "int":
				return script.sort("Int");
			case "real":
				return script.sort("Real");
			}
		} else if (type instanceof SubrangeIntType || type instanceof EnumType) {
			return script.sort("Int");
		}

		throw new JKindException("Unhandled type " + type);
	}

	public static Term convert(Script script, Sexp sexp) {
		return convert(script, new TermVariable[0], sexp);
	}

	public static Term convert(Script script, TermVariable[] params, Sexp sexp) {
		if (sexp instanceof Cons) {
			Cons cons = (Cons) sexp;
			return convert(script, params, cons);
		} else if (sexp instanceof Symbol) {
			Symbol symbol = (Symbol) sexp;
			return convert(script, params, symbol);
		} else {
			throw new JKindException("Unknown sexp: " + sexp);
		}
	}

	private static Term convert(Script script, TermVariable[] params, Cons cons) {
		if (!(cons.head instanceof Symbol)) {
			throw new JKindException("Cannot convert complex sexp to term: " + cons);
		}

		String head = ((Symbol) cons.head).str;
		Term[] args = new Term[cons.args.size()];
		for (int i = 0; i < cons.args.size(); i++) {
			args[i] = convert(script, params, cons.args.get(i));
		}
		return script.term(head, args);
	}

	private static Term convert(Script script, TermVariable[] params, Symbol symbol) {
		String text = symbol.str;

		if (text.matches("^[0-9]+$")) {
			return script.numeral(text);
		}

		for (int i = 0; i < params.length; i++) {
			if (params[i].getName().equals(text)) {
				return params[i];
			}
		}

		return script.term(text);
	}

	public static Value getValue(Term term, Type type) {
		if (term instanceof ApplicationTerm) {
			return getValue((ApplicationTerm) term);
		} else if (term instanceof ConstantTerm) {
			return getValue((ConstantTerm) term, type);
		} else {
			throw new JKindException("Unhandled term type: " + term.getClass().getSimpleName());
		}
	}

	private static Value getValue(ApplicationTerm at) {
		String name = at.getFunction().getName();
		switch (name) {
		case "true":
			return BooleanValue.TRUE;

		case "false":
			return BooleanValue.FALSE;

		default:
			throw new JKindException("Unhandled function in term to value conversion: " + name);
		}
	}

	private static Value getValue(ConstantTerm ct, Type type) {
		String typeName = Util.getName(type);

		if (ct.getValue() instanceof Rational) {
			Rational rational = (Rational) ct.getValue();
			switch (typeName) {
			case "int":
				if (rational.denominator().equals(BigInteger.ONE)) {
					return new IntegerValue(rational.numerator());
				} else {
					throw new JKindException("Cannot convert rational to integer: " + rational);
				}

			case "real":
				return new RealValue(new BigFraction(rational.numerator(), rational.denominator()));
			}
		}

		throw new JKindException("Unhandled constant in term to value conversion: " + ct);
	}
}
