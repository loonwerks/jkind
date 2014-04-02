// Generated from SmtLib2.g4 by ANTLR 4.2
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
		T__17=1, T__16=2, T__15=3, T__14=4, T__13=5, T__12=6, T__11=7, T__10=8, 
		T__9=9, T__8=10, T__7=11, T__6=12, T__5=13, T__4=14, T__3=15, T__2=16, 
		T__1=17, T__0=18, BOOL=19, INT=20, REAL=21, ID=22, WS=23, ERROR=24;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] tokenNames = {
		"<INVALID>",
		"'define-fun'", "')'", "'-'", "'ite'", "'('", "'not'", "'<'", "'='", "'<='", 
		"'Int'", "'Bool'", "'>'", "'and'", "'Real'", "'model'", "'/'", "'>='", 
		"'|'", "BOOL", "INT", "REAL", "ID", "WS", "ERROR"
	};
	public static final String[] ruleNames = {
		"T__17", "T__16", "T__15", "T__14", "T__13", "T__12", "T__11", "T__10", 
		"T__9", "T__8", "T__7", "T__6", "T__5", "T__4", "T__3", "T__2", "T__1", 
		"T__0", "BOOL", "DIGIT", "SYMBOL", "INT", "REAL", "ID", "WS", "ERROR"
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
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2\32\u00a8\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31"+
		"\t\31\4\32\t\32\4\33\t\33\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2"+
		"\3\3\3\3\3\4\3\4\3\5\3\5\3\5\3\5\3\6\3\6\3\7\3\7\3\7\3\7\3\b\3\b\3\t\3"+
		"\t\3\n\3\n\3\n\3\13\3\13\3\13\3\13\3\f\3\f\3\f\3\f\3\f\3\r\3\r\3\16\3"+
		"\16\3\16\3\16\3\17\3\17\3\17\3\17\3\17\3\20\3\20\3\20\3\20\3\20\3\20\3"+
		"\21\3\21\3\22\3\22\3\22\3\23\3\23\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3"+
		"\24\3\24\5\24\u0082\n\24\3\25\3\25\3\26\3\26\3\27\6\27\u0089\n\27\r\27"+
		"\16\27\u008a\3\30\6\30\u008e\n\30\r\30\16\30\u008f\3\30\3\30\6\30\u0094"+
		"\n\30\r\30\16\30\u0095\3\31\3\31\3\31\7\31\u009b\n\31\f\31\16\31\u009e"+
		"\13\31\3\32\6\32\u00a1\n\32\r\32\16\32\u00a2\3\32\3\32\3\33\3\33\2\2\34"+
		"\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16\33\17\35\20"+
		"\37\21!\22#\23%\24\'\25)\2+\2-\26/\27\61\30\63\31\65\32\3\2\5\3\2\62;"+
		"\b\2##%\'\60\60Bac|\u0080\u0080\5\2\13\f\16\17\"\"\u00ac\2\3\3\2\2\2\2"+
		"\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2"+
		"\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2"+
		"\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2"+
		"\2\'\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2\2\2\2\63\3\2\2\2\2\65\3\2"+
		"\2\2\3\67\3\2\2\2\5B\3\2\2\2\7D\3\2\2\2\tF\3\2\2\2\13J\3\2\2\2\rL\3\2"+
		"\2\2\17P\3\2\2\2\21R\3\2\2\2\23T\3\2\2\2\25W\3\2\2\2\27[\3\2\2\2\31`\3"+
		"\2\2\2\33b\3\2\2\2\35f\3\2\2\2\37k\3\2\2\2!q\3\2\2\2#s\3\2\2\2%v\3\2\2"+
		"\2\'\u0081\3\2\2\2)\u0083\3\2\2\2+\u0085\3\2\2\2-\u0088\3\2\2\2/\u008d"+
		"\3\2\2\2\61\u0097\3\2\2\2\63\u00a0\3\2\2\2\65\u00a6\3\2\2\2\678\7f\2\2"+
		"89\7g\2\29:\7h\2\2:;\7k\2\2;<\7p\2\2<=\7g\2\2=>\7/\2\2>?\7h\2\2?@\7w\2"+
		"\2@A\7p\2\2A\4\3\2\2\2BC\7+\2\2C\6\3\2\2\2DE\7/\2\2E\b\3\2\2\2FG\7k\2"+
		"\2GH\7v\2\2HI\7g\2\2I\n\3\2\2\2JK\7*\2\2K\f\3\2\2\2LM\7p\2\2MN\7q\2\2"+
		"NO\7v\2\2O\16\3\2\2\2PQ\7>\2\2Q\20\3\2\2\2RS\7?\2\2S\22\3\2\2\2TU\7>\2"+
		"\2UV\7?\2\2V\24\3\2\2\2WX\7K\2\2XY\7p\2\2YZ\7v\2\2Z\26\3\2\2\2[\\\7D\2"+
		"\2\\]\7q\2\2]^\7q\2\2^_\7n\2\2_\30\3\2\2\2`a\7@\2\2a\32\3\2\2\2bc\7c\2"+
		"\2cd\7p\2\2de\7f\2\2e\34\3\2\2\2fg\7T\2\2gh\7g\2\2hi\7c\2\2ij\7n\2\2j"+
		"\36\3\2\2\2kl\7o\2\2lm\7q\2\2mn\7f\2\2no\7g\2\2op\7n\2\2p \3\2\2\2qr\7"+
		"\61\2\2r\"\3\2\2\2st\7@\2\2tu\7?\2\2u$\3\2\2\2vw\7~\2\2w&\3\2\2\2xy\7"+
		"v\2\2yz\7t\2\2z{\7w\2\2{\u0082\7g\2\2|}\7h\2\2}~\7c\2\2~\177\7n\2\2\177"+
		"\u0080\7u\2\2\u0080\u0082\7g\2\2\u0081x\3\2\2\2\u0081|\3\2\2\2\u0082("+
		"\3\2\2\2\u0083\u0084\t\2\2\2\u0084*\3\2\2\2\u0085\u0086\t\3\2\2\u0086"+
		",\3\2\2\2\u0087\u0089\5)\25\2\u0088\u0087\3\2\2\2\u0089\u008a\3\2\2\2"+
		"\u008a\u0088\3\2\2\2\u008a\u008b\3\2\2\2\u008b.\3\2\2\2\u008c\u008e\5"+
		")\25\2\u008d\u008c\3\2\2\2\u008e\u008f\3\2\2\2\u008f\u008d\3\2\2\2\u008f"+
		"\u0090\3\2\2\2\u0090\u0091\3\2\2\2\u0091\u0093\7\60\2\2\u0092\u0094\5"+
		")\25\2\u0093\u0092\3\2\2\2\u0094\u0095\3\2\2\2\u0095\u0093\3\2\2\2\u0095"+
		"\u0096\3\2\2\2\u0096\60\3\2\2\2\u0097\u009c\5+\26\2\u0098\u009b\5+\26"+
		"\2\u0099\u009b\5)\25\2\u009a\u0098\3\2\2\2\u009a\u0099\3\2\2\2\u009b\u009e"+
		"\3\2\2\2\u009c\u009a\3\2\2\2\u009c\u009d\3\2\2\2\u009d\62\3\2\2\2\u009e"+
		"\u009c\3\2\2\2\u009f\u00a1\t\4\2\2\u00a0\u009f\3\2\2\2\u00a1\u00a2\3\2"+
		"\2\2\u00a2\u00a0\3\2\2\2\u00a2\u00a3\3\2\2\2\u00a3\u00a4\3\2\2\2\u00a4"+
		"\u00a5\b\32\2\2\u00a5\64\3\2\2\2\u00a6\u00a7\13\2\2\2\u00a7\66\3\2\2\2"+
		"\n\2\u0081\u008a\u008f\u0095\u009a\u009c\u00a2\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}