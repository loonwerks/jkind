// Generated from Yices.g4 by ANTLR 4.0
package jkind.solvers.yices;
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
		T__12=1, T__11=2, T__10=3, T__9=4, T__8=5, T__7=6, T__6=7, T__5=8, T__4=9, 
		T__3=10, T__2=11, T__1=12, T__0=13, PREDEFINED_OP=14, BOOL=15, INT=16, 
		ID=17, WS=18, ERROR=19;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] tokenNames = {
		"<INVALID>",
		"'assertion'", "'sat'", "'ids'", "')'", "'-'", "':'", "'('", "'unsatisfied'", 
		"'core'", "'/'", "'='", "'unsat'", "'cost'", "PREDEFINED_OP", "BOOL", 
		"INT", "ID", "WS", "ERROR"
	};
	public static final String[] ruleNames = {
		"T__12", "T__11", "T__10", "T__9", "T__8", "T__7", "T__6", "T__5", "T__4", 
		"T__3", "T__2", "T__1", "T__0", "PREDEFINED_OP", "BOOL", "DIGIT", "SYMBOL", 
		"INT", "ID", "WS", "ERROR"
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
		case 19: WS_action((RuleContext)_localctx, actionIndex); break;
		}
	}
	private void WS_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 0: skip();  break;
		}
	}

	public static final String _serializedATN =
		"\2\4\25\u0094\b\1\4\2\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b"+
		"\t\b\4\t\t\t\4\n\t\n\4\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20"+
		"\t\20\4\21\t\21\4\22\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\3\2"+
		"\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\3\3\3\3\3\3\3\3\4\3\4\3\4\3\4\3"+
		"\5\3\5\3\6\3\6\3\7\3\7\3\b\3\b\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t"+
		"\3\t\3\t\3\n\3\n\3\n\3\n\3\n\3\13\3\13\3\f\3\f\3\r\3\r\3\r\3\r\3\r\3\r"+
		"\3\16\3\16\3\16\3\16\3\16\3\17\3\17\3\17\3\17\3\17\3\17\5\17n\n\17\3\20"+
		"\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\5\20y\n\20\3\21\3\21\3\22\3\22"+
		"\3\23\6\23\u0080\n\23\r\23\16\23\u0081\3\24\3\24\3\24\7\24\u0087\n\24"+
		"\f\24\16\24\u008a\13\24\3\25\6\25\u008d\n\25\r\25\16\25\u008e\3\25\3\25"+
		"\3\26\3\26\2\27\3\3\1\5\4\1\7\5\1\t\6\1\13\7\1\r\b\1\17\t\1\21\n\1\23"+
		"\13\1\25\f\1\27\r\1\31\16\1\33\17\1\35\20\1\37\21\1!\2\1#\2\1%\22\1\'"+
		"\23\1)\24\2+\25\1\3\2\5\3\62;\6%\'B\\aac|\5\13\f\16\17\"\"\u0097\2\3\3"+
		"\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2"+
		"\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3"+
		"\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2"+
		")\3\2\2\2\2+\3\2\2\2\3-\3\2\2\2\5\67\3\2\2\2\7;\3\2\2\2\t?\3\2\2\2\13"+
		"A\3\2\2\2\rC\3\2\2\2\17E\3\2\2\2\21G\3\2\2\2\23S\3\2\2\2\25X\3\2\2\2\27"+
		"Z\3\2\2\2\31\\\3\2\2\2\33b\3\2\2\2\35m\3\2\2\2\37x\3\2\2\2!z\3\2\2\2#"+
		"|\3\2\2\2%\177\3\2\2\2\'\u0083\3\2\2\2)\u008c\3\2\2\2+\u0092\3\2\2\2-"+
		".\7c\2\2./\7u\2\2/\60\7u\2\2\60\61\7g\2\2\61\62\7t\2\2\62\63\7v\2\2\63"+
		"\64\7k\2\2\64\65\7q\2\2\65\66\7p\2\2\66\4\3\2\2\2\678\7u\2\289\7c\2\2"+
		"9:\7v\2\2:\6\3\2\2\2;<\7k\2\2<=\7f\2\2=>\7u\2\2>\b\3\2\2\2?@\7+\2\2@\n"+
		"\3\2\2\2AB\7/\2\2B\f\3\2\2\2CD\7<\2\2D\16\3\2\2\2EF\7*\2\2F\20\3\2\2\2"+
		"GH\7w\2\2HI\7p\2\2IJ\7u\2\2JK\7c\2\2KL\7v\2\2LM\7k\2\2MN\7u\2\2NO\7h\2"+
		"\2OP\7k\2\2PQ\7g\2\2QR\7f\2\2R\22\3\2\2\2ST\7e\2\2TU\7q\2\2UV\7t\2\2V"+
		"W\7g\2\2W\24\3\2\2\2XY\7\61\2\2Y\26\3\2\2\2Z[\7?\2\2[\30\3\2\2\2\\]\7"+
		"w\2\2]^\7p\2\2^_\7u\2\2_`\7c\2\2`a\7v\2\2a\32\3\2\2\2bc\7e\2\2cd\7q\2"+
		"\2de\7u\2\2ef\7v\2\2f\34\3\2\2\2gh\7o\2\2hi\7q\2\2in\7f\2\2jk\7f\2\2k"+
		"l\7k\2\2ln\7x\2\2mg\3\2\2\2mj\3\2\2\2n\36\3\2\2\2op\7v\2\2pq\7t\2\2qr"+
		"\7w\2\2ry\7g\2\2st\7h\2\2tu\7c\2\2uv\7n\2\2vw\7u\2\2wy\7g\2\2xo\3\2\2"+
		"\2xs\3\2\2\2y \3\2\2\2z{\t\2\2\2{\"\3\2\2\2|}\t\3\2\2}$\3\2\2\2~\u0080"+
		"\5!\21\2\177~\3\2\2\2\u0080\u0081\3\2\2\2\u0081\177\3\2\2\2\u0081\u0082"+
		"\3\2\2\2\u0082&\3\2\2\2\u0083\u0088\5#\22\2\u0084\u0087\5#\22\2\u0085"+
		"\u0087\5!\21\2\u0086\u0084\3\2\2\2\u0086\u0085\3\2\2\2\u0087\u008a\3\2"+
		"\2\2\u0088\u0086\3\2\2\2\u0088\u0089\3\2\2\2\u0089(\3\2\2\2\u008a\u0088"+
		"\3\2\2\2\u008b\u008d\t\4\2\2\u008c\u008b\3\2\2\2\u008d\u008e\3\2\2\2\u008e"+
		"\u008c\3\2\2\2\u008e\u008f\3\2\2\2\u008f\u0090\3\2\2\2\u0090\u0091\b\25"+
		"\2\2\u0091*\3\2\2\2\u0092\u0093\13\2\2\2\u0093,\3\2\2\2\t\2mx\u0081\u0086"+
		"\u0088\u008e";
	public static final ATN _ATN =
		ATNSimulator.deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
	}
}