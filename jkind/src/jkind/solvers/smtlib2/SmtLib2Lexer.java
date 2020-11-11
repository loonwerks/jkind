// Generated from SmtLib2.g4 by ANTLR 4.4
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
	static { RuntimeMetaData.checkVersion("4.4", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__21=1, T__20=2, T__19=3, T__18=4, T__17=5, T__16=6, T__15=7, T__14=8, 
		T__13=9, T__12=10, T__11=11, T__10=12, T__9=13, T__8=14, T__7=15, T__6=16, 
		T__5=17, T__4=18, T__3=19, T__2=20, T__1=21, T__0=22, BOOL=23, INT=24, 
		REAL=25, ID=26, WS=27, ERROR=28;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] tokenNames = {
		"'\\u0000'", "'\\u0001'", "'\\u0002'", "'\\u0003'", "'\\u0004'", "'\\u0005'", 
		"'\\u0006'", "'\\u0007'", "'\b'", "'\t'", "'\n'", "'\\u000B'", "'\f'", 
		"'\r'", "'\\u000E'", "'\\u000F'", "'\\u0010'", "'\\u0011'", "'\\u0012'", 
		"'\\u0013'", "'\\u0014'", "'\\u0015'", "'\\u0016'", "'\\u0017'", "'\\u0018'", 
		"'\\u0019'", "'\\u001A'", "'\\u001B'", "'\\u001C'"
	};
	public static final String[] ruleNames = {
		"T__21", "T__20", "T__19", "T__18", "T__17", "T__16", "T__15", "T__14", 
		"T__13", "T__12", "T__11", "T__10", "T__9", "T__8", "T__7", "T__6", "T__5", 
		"T__4", "T__3", "T__2", "T__1", "T__0", "BOOL", "DIGIT", "SYMBOL", "INT", 
		"REAL", "ID", "WS", "ERROR"
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
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2\36\u00bb\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31"+
		"\t\31\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\3\2"+
		"\3\2\3\3\3\3\3\3\3\3\3\3\3\4\3\4\3\4\3\4\3\4\3\4\3\5\3\5\3\5\3\5\3\6\3"+
		"\6\3\6\3\6\3\6\3\7\3\7\3\7\3\b\3\b\3\t\3\t\3\n\3\n\3\13\3\13\3\f\3\f\3"+
		"\f\3\r\3\r\3\r\3\16\3\16\3\16\3\16\3\17\3\17\3\20\3\20\3\21\3\21\3\21"+
		"\3\21\3\22\3\22\3\23\3\23\3\23\3\23\3\24\3\24\3\25\3\25\3\25\3\25\3\25"+
		"\3\25\3\25\3\25\3\25\3\25\3\25\3\26\3\26\3\26\3\26\3\27\3\27\3\30\3\30"+
		"\3\30\3\30\3\30\3\30\3\30\3\30\3\30\5\30\u0095\n\30\3\31\3\31\3\32\3\32"+
		"\3\33\6\33\u009c\n\33\r\33\16\33\u009d\3\34\6\34\u00a1\n\34\r\34\16\34"+
		"\u00a2\3\34\3\34\6\34\u00a7\n\34\r\34\16\34\u00a8\3\35\3\35\3\35\7\35"+
		"\u00ae\n\35\f\35\16\35\u00b1\13\35\3\36\6\36\u00b4\n\36\r\36\16\36\u00b5"+
		"\3\36\3\36\3\37\3\37\2\2 \3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25"+
		"\f\27\r\31\16\33\17\35\20\37\21!\22#\23%\24\'\25)\26+\27-\30/\31\61\2"+
		"\63\2\65\32\67\339\34;\35=\36\3\2\5\3\2\62;\t\2##%\'))/\60Bac|\u0080\u0080"+
		"\5\2\13\f\16\17\"\"\u00bf\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2"+
		"\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2"+
		"\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3"+
		"\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2"+
		"\2\2\2-\3\2\2\2\2/\3\2\2\2\2\65\3\2\2\2\2\67\3\2\2\2\29\3\2\2\2\2;\3\2"+
		"\2\2\2=\3\2\2\2\3?\3\2\2\2\5A\3\2\2\2\7F\3\2\2\2\tL\3\2\2\2\13P\3\2\2"+
		"\2\rU\3\2\2\2\17X\3\2\2\2\21Z\3\2\2\2\23\\\3\2\2\2\25^\3\2\2\2\27`\3\2"+
		"\2\2\31c\3\2\2\2\33f\3\2\2\2\35j\3\2\2\2\37l\3\2\2\2!n\3\2\2\2#r\3\2\2"+
		"\2%t\3\2\2\2\'x\3\2\2\2)z\3\2\2\2+\u0085\3\2\2\2-\u0089\3\2\2\2/\u0094"+
		"\3\2\2\2\61\u0096\3\2\2\2\63\u0098\3\2\2\2\65\u009b\3\2\2\2\67\u00a0\3"+
		"\2\2\29\u00aa\3\2\2\2;\u00b3\3\2\2\2=\u00b9\3\2\2\2?@\7\61\2\2@\4\3\2"+
		"\2\2AB\7T\2\2BC\7g\2\2CD\7c\2\2DE\7n\2\2E\6\3\2\2\2FG\7o\2\2GH\7q\2\2"+
		"HI\7f\2\2IJ\7g\2\2JK\7n\2\2K\b\3\2\2\2LM\7K\2\2MN\7p\2\2NO\7v\2\2O\n\3"+
		"\2\2\2PQ\7D\2\2QR\7q\2\2RS\7q\2\2ST\7n\2\2T\f\3\2\2\2UV\7@\2\2VW\7?\2"+
		"\2W\16\3\2\2\2XY\7~\2\2Y\20\3\2\2\2Z[\7>\2\2[\22\3\2\2\2\\]\7?\2\2]\24"+
		"\3\2\2\2^_\7@\2\2_\26\3\2\2\2`a\7q\2\2ab\7t\2\2b\30\3\2\2\2cd\7>\2\2d"+
		"e\7?\2\2e\32\3\2\2\2fg\7n\2\2gh\7g\2\2hi\7v\2\2i\34\3\2\2\2jk\7*\2\2k"+
		"\36\3\2\2\2lm\7+\2\2m \3\2\2\2no\7c\2\2op\7p\2\2pq\7f\2\2q\"\3\2\2\2r"+
		"s\7,\2\2s$\3\2\2\2tu\7k\2\2uv\7v\2\2vw\7g\2\2w&\3\2\2\2xy\7-\2\2y(\3\2"+
		"\2\2z{\7f\2\2{|\7g\2\2|}\7h\2\2}~\7k\2\2~\177\7p\2\2\177\u0080\7g\2\2"+
		"\u0080\u0081\7/\2\2\u0081\u0082\7h\2\2\u0082\u0083\7w\2\2\u0083\u0084"+
		"\7p\2\2\u0084*\3\2\2\2\u0085\u0086\7p\2\2\u0086\u0087\7q\2\2\u0087\u0088"+
		"\7v\2\2\u0088,\3\2\2\2\u0089\u008a\7/\2\2\u008a.\3\2\2\2\u008b\u008c\7"+
		"v\2\2\u008c\u008d\7t\2\2\u008d\u008e\7w\2\2\u008e\u0095\7g\2\2\u008f\u0090"+
		"\7h\2\2\u0090\u0091\7c\2\2\u0091\u0092\7n\2\2\u0092\u0093\7u\2\2\u0093"+
		"\u0095\7g\2\2\u0094\u008b\3\2\2\2\u0094\u008f\3\2\2\2\u0095\60\3\2\2\2"+
		"\u0096\u0097\t\2\2\2\u0097\62\3\2\2\2\u0098\u0099\t\3\2\2\u0099\64\3\2"+
		"\2\2\u009a\u009c\5\61\31\2\u009b\u009a\3\2\2\2\u009c\u009d\3\2\2\2\u009d"+
		"\u009b\3\2\2\2\u009d\u009e\3\2\2\2\u009e\66\3\2\2\2\u009f\u00a1\5\61\31"+
		"\2\u00a0\u009f\3\2\2\2\u00a1\u00a2\3\2\2\2\u00a2\u00a0\3\2\2\2\u00a2\u00a3"+
		"\3\2\2\2\u00a3\u00a4\3\2\2\2\u00a4\u00a6\7\60\2\2\u00a5\u00a7\5\61\31"+
		"\2\u00a6\u00a5\3\2\2\2\u00a7\u00a8\3\2\2\2\u00a8\u00a6\3\2\2\2\u00a8\u00a9"+
		"\3\2\2\2\u00a98\3\2\2\2\u00aa\u00af\5\63\32\2\u00ab\u00ae\5\63\32\2\u00ac"+
		"\u00ae\5\61\31\2\u00ad\u00ab\3\2\2\2\u00ad\u00ac\3\2\2\2\u00ae\u00b1\3"+
		"\2\2\2\u00af\u00ad\3\2\2\2\u00af\u00b0\3\2\2\2\u00b0:\3\2\2\2\u00b1\u00af"+
		"\3\2\2\2\u00b2\u00b4\t\4\2\2\u00b3\u00b2\3\2\2\2\u00b4\u00b5\3\2\2\2\u00b5"+
		"\u00b3\3\2\2\2\u00b5\u00b6\3\2\2\2\u00b6\u00b7\3\2\2\2\u00b7\u00b8\b\36"+
		"\2\2\u00b8<\3\2\2\2\u00b9\u00ba\13\2\2\2\u00ba>\3\2\2\2\n\2\u0094\u009d"+
		"\u00a2\u00a8\u00ad\u00af\u00b5\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}