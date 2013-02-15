// Generated from Yices.g4 by ANTLR 4.0
package jkind.solvers;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class YicesLexer extends Lexer {
	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__4=1, T__3=2, T__2=3, T__1=4, T__0=5, RESULT=6, PREDEFINED_OP=7, BOOL=8, 
		INT=9, ID=10, WS=11, ERROR=12;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] tokenNames = {
		"<INVALID>",
		"')'", "'-'", "'('", "'/'", "'='", "RESULT", "PREDEFINED_OP", "BOOL", 
		"INT", "ID", "WS", "ERROR"
	};
	public static final String[] ruleNames = {
		"T__4", "T__3", "T__2", "T__1", "T__0", "RESULT", "PREDEFINED_OP", "BOOL", 
		"DIGIT", "SYMBOL", "INT", "ID", "WS", "ERROR"
	};


	public YicesLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "Yices.g4"; }

	@Override
	public String[] getTokenNames() { return tokenNames; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	@Override
	public void action(RuleContext _localctx, int ruleIndex, int actionIndex) {
		switch (ruleIndex) {
		case 12: WS_action((RuleContext)_localctx, actionIndex); break;
		}
	}
	private void WS_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 0: skip();  break;
		}
	}

	public static final String _serializedATN =
		"\2\4\16`\b\1\4\2\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4"+
		"\t\t\t\4\n\t\n\4\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\3\2\3\2\3"+
		"\3\3\3\3\4\3\4\3\5\3\5\3\6\3\6\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\5\7\62"+
		"\n\7\3\b\3\b\3\b\3\b\3\b\3\b\5\b:\n\b\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t"+
		"\3\t\5\tE\n\t\3\n\3\n\3\13\3\13\3\f\6\fL\n\f\r\f\16\fM\3\r\3\r\3\r\7\r"+
		"S\n\r\f\r\16\rV\13\r\3\16\6\16Y\n\16\r\16\16\16Z\3\16\3\16\3\17\3\17\2"+
		"\20\3\3\1\5\4\1\7\5\1\t\6\1\13\7\1\r\b\1\17\t\1\21\n\1\23\2\1\25\2\1\27"+
		"\13\1\31\f\1\33\r\2\35\16\1\3\2\5\3\62;\6%&B\\aac|\5\13\f\16\17\"\"d\2"+
		"\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2"+
		"\2\2\17\3\2\2\2\2\21\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2"+
		"\35\3\2\2\2\3\37\3\2\2\2\5!\3\2\2\2\7#\3\2\2\2\t%\3\2\2\2\13\'\3\2\2\2"+
		"\r\61\3\2\2\2\179\3\2\2\2\21D\3\2\2\2\23F\3\2\2\2\25H\3\2\2\2\27K\3\2"+
		"\2\2\31O\3\2\2\2\33X\3\2\2\2\35^\3\2\2\2\37 \7+\2\2 \4\3\2\2\2!\"\7/\2"+
		"\2\"\6\3\2\2\2#$\7*\2\2$\b\3\2\2\2%&\7\61\2\2&\n\3\2\2\2\'(\7?\2\2(\f"+
		"\3\2\2\2)*\7u\2\2*+\7c\2\2+\62\7v\2\2,-\7w\2\2-.\7p\2\2./\7u\2\2/\60\7"+
		"c\2\2\60\62\7v\2\2\61)\3\2\2\2\61,\3\2\2\2\62\16\3\2\2\2\63\64\7o\2\2"+
		"\64\65\7q\2\2\65:\7f\2\2\66\67\7f\2\2\678\7k\2\28:\7x\2\29\63\3\2\2\2"+
		"9\66\3\2\2\2:\20\3\2\2\2;<\7v\2\2<=\7t\2\2=>\7w\2\2>E\7g\2\2?@\7h\2\2"+
		"@A\7c\2\2AB\7n\2\2BC\7u\2\2CE\7g\2\2D;\3\2\2\2D?\3\2\2\2E\22\3\2\2\2F"+
		"G\t\2\2\2G\24\3\2\2\2HI\t\3\2\2I\26\3\2\2\2JL\5\23\n\2KJ\3\2\2\2LM\3\2"+
		"\2\2MK\3\2\2\2MN\3\2\2\2N\30\3\2\2\2OT\5\25\13\2PS\5\25\13\2QS\5\23\n"+
		"\2RP\3\2\2\2RQ\3\2\2\2SV\3\2\2\2TR\3\2\2\2TU\3\2\2\2U\32\3\2\2\2VT\3\2"+
		"\2\2WY\t\4\2\2XW\3\2\2\2YZ\3\2\2\2ZX\3\2\2\2Z[\3\2\2\2[\\\3\2\2\2\\]\b"+
		"\16\2\2]\34\3\2\2\2^_\13\2\2\2_\36\3\2\2\2\n\2\619DMRTZ";
	public static final ATN _ATN =
		ATNSimulator.deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
	}
}