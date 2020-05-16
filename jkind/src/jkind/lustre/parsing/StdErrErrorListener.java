package jkind.lustre.parsing;

import jkind.StdErr;
import jkind.lustre.Location;

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;

public class StdErrErrorListener extends BaseErrorListener {
	@Override
	public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine,
			String msg, RecognitionException e) {
		StdErr.println("Parse error line " + line + ":" + charPositionInLine + " " + msg);
		StdErr.showLocation(new Location(line, charPositionInLine));
	}
}
