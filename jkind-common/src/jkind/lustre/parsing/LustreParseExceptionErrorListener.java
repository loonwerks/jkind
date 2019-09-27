package jkind.lustre.parsing;

import jkind.lustre.Location;

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;

public class LustreParseExceptionErrorListener extends BaseErrorListener {
	@Override
	public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line,
			int charPositionInLine, String msg, RecognitionException e) {
		throw new LustreParseException(new Location(line, charPositionInLine), msg);
	}
}
