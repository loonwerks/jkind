// Generated from Cvc4.g4 by ANTLR 4.0
package jkind.solvers.cvc4;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class Cvc4Lexer extends Lexer {
	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__12=1, T__11=2, T__10=3, T__9=4, T__8=5, T__7=6, T__6=7, T__5=8, T__4=9, 
		T__3=10, T__2=11, T__1=12, T__0=13, BOOL=14, INT=15, ID=16, WS=17, ERROR=18;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] tokenNames = {
		"<INVALID>",
		"'define-fun'", "')'", "'-'", "'ite'", "'('", "'not'", "'='", "'Int'", 
		"'Bool'", "'and'", "'Real'", "'model'", "'/'", "BOOL", "INT", "ID", "WS", 
		"ERROR"
	};
	public static final String[] ruleNames = {
		"T__12", "T__11", "T__10", "T__9", "T__8", "T__7", "T__6", "T__5", "T__4", 
		"T__3", "T__2", "T__1", "T__0", "BOOL", "DIGIT", "SYMBOL", "INT", "ID", 
		"WS", "ERROR"
	};


	public Cvc4Lexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "Cvc4.g4"; }

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
		case 18: WS_action((RuleContext)_localctx, actionIndex); break;
		}
	}
	private void WS_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 0: skip();  break;
		}
	}

	public static final String _serializedATN =
		"\2\4\24\u0085\b\1\4\2\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b"+
		"\t\b\4\t\t\t\4\n\t\n\4\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20"+
		"\t\20\4\21\t\21\4\22\t\22\4\23\t\23\4\24\t\24\4\25\t\25\3\2\3\2\3\2\3"+
		"\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\3\3\3\3\4\3\4\3\5\3\5\3\5\3\5\3\6\3\6"+
		"\3\7\3\7\3\7\3\7\3\b\3\b\3\t\3\t\3\t\3\t\3\n\3\n\3\n\3\n\3\n\3\13\3\13"+
		"\3\13\3\13\3\f\3\f\3\f\3\f\3\f\3\r\3\r\3\r\3\r\3\r\3\r\3\16\3\16\3\17"+
		"\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\5\17j\n\17\3\20\3\20\3\21\3\21"+
		"\3\22\6\22q\n\22\r\22\16\22r\3\23\3\23\3\23\7\23x\n\23\f\23\16\23{\13"+
		"\23\3\24\6\24~\n\24\r\24\16\24\177\3\24\3\24\3\25\3\25\2\26\3\3\1\5\4"+
		"\1\7\5\1\t\6\1\13\7\1\r\b\1\17\t\1\21\n\1\23\13\1\25\f\1\27\r\1\31\16"+
		"\1\33\17\1\35\20\1\37\2\1!\2\1#\21\1%\22\1\'\23\2)\24\1\3\2\5\3\62;\7"+
		"##%\'B\\aac|\5\13\f\16\17\"\"\u0087\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2"+
		"\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23"+
		"\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2"+
		"\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\3+\3\2\2\2\5\66\3\2"+
		"\2\2\78\3\2\2\2\t:\3\2\2\2\13>\3\2\2\2\r@\3\2\2\2\17D\3\2\2\2\21F\3\2"+
		"\2\2\23J\3\2\2\2\25O\3\2\2\2\27S\3\2\2\2\31X\3\2\2\2\33^\3\2\2\2\35i\3"+
		"\2\2\2\37k\3\2\2\2!m\3\2\2\2#p\3\2\2\2%t\3\2\2\2\'}\3\2\2\2)\u0083\3\2"+
		"\2\2+,\7f\2\2,-\7g\2\2-.\7h\2\2./\7k\2\2/\60\7p\2\2\60\61\7g\2\2\61\62"+
		"\7/\2\2\62\63\7h\2\2\63\64\7w\2\2\64\65\7p\2\2\65\4\3\2\2\2\66\67\7+\2"+
		"\2\67\6\3\2\2\289\7/\2\29\b\3\2\2\2:;\7k\2\2;<\7v\2\2<=\7g\2\2=\n\3\2"+
		"\2\2>?\7*\2\2?\f\3\2\2\2@A\7p\2\2AB\7q\2\2BC\7v\2\2C\16\3\2\2\2DE\7?\2"+
		"\2E\20\3\2\2\2FG\7K\2\2GH\7p\2\2HI\7v\2\2I\22\3\2\2\2JK\7D\2\2KL\7q\2"+
		"\2LM\7q\2\2MN\7n\2\2N\24\3\2\2\2OP\7c\2\2PQ\7p\2\2QR\7f\2\2R\26\3\2\2"+
		"\2ST\7T\2\2TU\7g\2\2UV\7c\2\2VW\7n\2\2W\30\3\2\2\2XY\7o\2\2YZ\7q\2\2Z"+
		"[\7f\2\2[\\\7g\2\2\\]\7n\2\2]\32\3\2\2\2^_\7\61\2\2_\34\3\2\2\2`a\7v\2"+
		"\2ab\7t\2\2bc\7w\2\2cj\7g\2\2de\7h\2\2ef\7c\2\2fg\7n\2\2gh\7u\2\2hj\7"+
		"g\2\2i`\3\2\2\2id\3\2\2\2j\36\3\2\2\2kl\t\2\2\2l \3\2\2\2mn\t\3\2\2n\""+
		"\3\2\2\2oq\5\37\20\2po\3\2\2\2qr\3\2\2\2rp\3\2\2\2rs\3\2\2\2s$\3\2\2\2"+
		"ty\5!\21\2ux\5!\21\2vx\5\37\20\2wu\3\2\2\2wv\3\2\2\2x{\3\2\2\2yw\3\2\2"+
		"\2yz\3\2\2\2z&\3\2\2\2{y\3\2\2\2|~\t\4\2\2}|\3\2\2\2~\177\3\2\2\2\177"+
		"}\3\2\2\2\177\u0080\3\2\2\2\u0080\u0081\3\2\2\2\u0081\u0082\b\24\2\2\u0082"+
		"(\3\2\2\2\u0083\u0084\13\2\2\2\u0084*\3\2\2\2\b\2irwy\177";
	public static final ATN _ATN =
		ATNSimulator.deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
	}
}