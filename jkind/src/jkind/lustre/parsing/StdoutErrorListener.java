package jkind.lustre.parsing;

import jkind.Output;
import jkind.lustre.Location;

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;

public class StdoutErrorListener extends BaseErrorListener {
	@Override
	public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line,
			int charPositionInLine, String msg, RecognitionException e) {
		Output.println("Parse error line " + line + ":" + charPositionInLine + " " + msg);
		Output.showLocation(new Location(line, charPositionInLine));
	}
}
