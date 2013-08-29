// Generated from SmtLib2.g4 by ANTLR 4.1
package jkind.solvers.smtlib2;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class SmtLib2Lexer extends Lexer {
	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__16=1, T__15=2, T__14=3, T__13=4, T__12=5, T__11=6, T__10=7, T__9=8, 
		T__8=9, T__7=10, T__6=11, T__5=12, T__4=13, T__3=14, T__2=15, T__1=16, 
		T__0=17, BOOL=18, INT=19, REAL=20, ID=21, WS=22, ERROR=23;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] tokenNames = {
		"<INVALID>",
		"'define-fun'", "')'", "'-'", "'ite'", "'('", "'not'", "'<'", "'='", "'<='", 
		"'Int'", "'Bool'", "'>'", "'and'", "'Real'", "'model'", "'/'", "'>='", 
		"BOOL", "INT", "REAL", "ID", "WS", "ERROR"
	};
	public static final String[] ruleNames = {
		"T__16", "T__15", "T__14", "T__13", "T__12", "T__11", "T__10", "T__9", 
		"T__8", "T__7", "T__6", "T__5", "T__4", "T__3", "T__2", "T__1", "T__0", 
		"BOOL", "DIGIT", "SYMBOL", "INT", "REAL", "ID", "WS", "ERROR"
	};


	public SmtLib2Lexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "SmtLib2.g4"; }

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
		case 23: WS_action((RuleContext)_localctx, actionIndex); break;
		}
	}
	private void WS_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 0: skip();  break;
		}
	}

	public static final String _serializedATN =
		"\3\uacf5\uee8c\u4f5d\u8b0d\u4a45\u78bd\u1b2f\u3378\2\31\u00a4\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31"+
		"\t\31\4\32\t\32\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\3\3\3\3"+
		"\4\3\4\3\5\3\5\3\5\3\5\3\6\3\6\3\7\3\7\3\7\3\7\3\b\3\b\3\t\3\t\3\n\3\n"+
		"\3\n\3\13\3\13\3\13\3\13\3\f\3\f\3\f\3\f\3\f\3\r\3\r\3\16\3\16\3\16\3"+
		"\16\3\17\3\17\3\17\3\17\3\17\3\20\3\20\3\20\3\20\3\20\3\20\3\21\3\21\3"+
		"\22\3\22\3\22\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\5\23~\n\23"+
		"\3\24\3\24\3\25\3\25\3\26\6\26\u0085\n\26\r\26\16\26\u0086\3\27\6\27\u008a"+
		"\n\27\r\27\16\27\u008b\3\27\3\27\6\27\u0090\n\27\r\27\16\27\u0091\3\30"+
		"\3\30\3\30\7\30\u0097\n\30\f\30\16\30\u009a\13\30\3\31\6\31\u009d\n\31"+
		"\r\31\16\31\u009e\3\31\3\31\3\32\3\32\2\33\3\3\1\5\4\1\7\5\1\t\6\1\13"+
		"\7\1\r\b\1\17\t\1\21\n\1\23\13\1\25\f\1\27\r\1\31\16\1\33\17\1\35\20\1"+
		"\37\21\1!\22\1#\23\1%\24\1\'\2\1)\2\1+\25\1-\26\1/\27\1\61\30\2\63\31"+
		"\1\3\2\5\3\2\62;\t\2##%\'\60\60B\\`ac|\u0080\u0080\5\2\13\f\16\17\"\""+
		"\u00a8\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2"+
		"\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3"+
		"\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2"+
		"\2#\3\2\2\2\2%\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2\2\2\2"+
		"\63\3\2\2\2\3\65\3\2\2\2\5@\3\2\2\2\7B\3\2\2\2\tD\3\2\2\2\13H\3\2\2\2"+
		"\rJ\3\2\2\2\17N\3\2\2\2\21P\3\2\2\2\23R\3\2\2\2\25U\3\2\2\2\27Y\3\2\2"+
		"\2\31^\3\2\2\2\33`\3\2\2\2\35d\3\2\2\2\37i\3\2\2\2!o\3\2\2\2#q\3\2\2\2"+
		"%}\3\2\2\2\'\177\3\2\2\2)\u0081\3\2\2\2+\u0084\3\2\2\2-\u0089\3\2\2\2"+
		"/\u0093\3\2\2\2\61\u009c\3\2\2\2\63\u00a2\3\2\2\2\65\66\7f\2\2\66\67\7"+
		"g\2\2\678\7h\2\289\7k\2\29:\7p\2\2:;\7g\2\2;<\7/\2\2<=\7h\2\2=>\7w\2\2"+
		">?\7p\2\2?\4\3\2\2\2@A\7+\2\2A\6\3\2\2\2BC\7/\2\2C\b\3\2\2\2DE\7k\2\2"+
		"EF\7v\2\2FG\7g\2\2G\n\3\2\2\2HI\7*\2\2I\f\3\2\2\2JK\7p\2\2KL\7q\2\2LM"+
		"\7v\2\2M\16\3\2\2\2NO\7>\2\2O\20\3\2\2\2PQ\7?\2\2Q\22\3\2\2\2RS\7>\2\2"+
		"ST\7?\2\2T\24\3\2\2\2UV\7K\2\2VW\7p\2\2WX\7v\2\2X\26\3\2\2\2YZ\7D\2\2"+
		"Z[\7q\2\2[\\\7q\2\2\\]\7n\2\2]\30\3\2\2\2^_\7@\2\2_\32\3\2\2\2`a\7c\2"+
		"\2ab\7p\2\2bc\7f\2\2c\34\3\2\2\2de\7T\2\2ef\7g\2\2fg\7c\2\2gh\7n\2\2h"+
		"\36\3\2\2\2ij\7o\2\2jk\7q\2\2kl\7f\2\2lm\7g\2\2mn\7n\2\2n \3\2\2\2op\7"+
		"\61\2\2p\"\3\2\2\2qr\7@\2\2rs\7?\2\2s$\3\2\2\2tu\7v\2\2uv\7t\2\2vw\7w"+
		"\2\2w~\7g\2\2xy\7h\2\2yz\7c\2\2z{\7n\2\2{|\7u\2\2|~\7g\2\2}t\3\2\2\2}"+
		"x\3\2\2\2~&\3\2\2\2\177\u0080\t\2\2\2\u0080(\3\2\2\2\u0081\u0082\t\3\2"+
		"\2\u0082*\3\2\2\2\u0083\u0085\5\'\24\2\u0084\u0083\3\2\2\2\u0085\u0086"+
		"\3\2\2\2\u0086\u0084\3\2\2\2\u0086\u0087\3\2\2\2\u0087,\3\2\2\2\u0088"+
		"\u008a\5\'\24\2\u0089\u0088\3\2\2\2\u008a\u008b\3\2\2\2\u008b\u0089\3"+
		"\2\2\2\u008b\u008c\3\2\2\2\u008c\u008d\3\2\2\2\u008d\u008f\7\60\2\2\u008e"+
		"\u0090\5\'\24\2\u008f\u008e\3\2\2\2\u0090\u0091\3\2\2\2\u0091\u008f\3"+
		"\2\2\2\u0091\u0092\3\2\2\2\u0092.\3\2\2\2\u0093\u0098\5)\25\2\u0094\u0097"+
		"\5)\25\2\u0095\u0097\5\'\24\2\u0096\u0094\3\2\2\2\u0096\u0095\3\2\2\2"+
		"\u0097\u009a\3\2\2\2\u0098\u0096\3\2\2\2\u0098\u0099\3\2\2\2\u0099\60"+
		"\3\2\2\2\u009a\u0098\3\2\2\2\u009b\u009d\t\4\2\2\u009c\u009b\3\2\2\2\u009d"+
		"\u009e\3\2\2\2\u009e\u009c\3\2\2\2\u009e\u009f\3\2\2\2\u009f\u00a0\3\2"+
		"\2\2\u00a0\u00a1\b\31\2\2\u00a1\62\3\2\2\2\u00a2\u00a3\13\2\2\2\u00a3"+
		"\64\3\2\2\2\n\2}\u0086\u008b\u0091\u0096\u0098\u009e";
	public static final ATN _ATN =
		ATNSimulator.deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}