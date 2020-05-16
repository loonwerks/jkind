// Generated from Yices.g4 by ANTLR 4.4
package jkind.solvers.yices;

import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({ "all", "warnings", "unchecked", "unused", "cast" })
public class YicesLexer extends Lexer {
	static {
		RuntimeMetaData.checkVersion("4.4", RuntimeMetaData.VERSION);
	}

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache = new PredictionContextCache();
	public static final int T__12 = 1, T__11 = 2, T__10 = 3, T__9 = 4, T__8 = 5, T__7 = 6, T__6 = 7, T__5 = 8, T__4 = 9,
			T__3 = 10, T__2 = 11, T__1 = 12, T__0 = 13, PREDEFINED_OP = 14, BOOL = 15, INT = 16, ID = 17, WS = 18,
			ERROR = 19;
	public static String[] modeNames = { "DEFAULT_MODE" };

	public static final String[] tokenNames = { "'\\u0000'", "'\\u0001'", "'\\u0002'", "'\\u0003'", "'\\u0004'",
			"'\\u0005'", "'\\u0006'", "'\\u0007'", "'\b'", "'\t'", "'\n'", "'\\u000B'", "'\f'", "'\r'", "'\\u000E'",
			"'\\u000F'", "'\\u0010'", "'\\u0011'", "'\\u0012'", "'\\u0013'" };
	public static final String[] ruleNames = { "T__12", "T__11", "T__10", "T__9", "T__8", "T__7", "T__6", "T__5",
			"T__4", "T__3", "T__2", "T__1", "T__0", "PREDEFINED_OP", "BOOL", "DIGIT", "SYMBOL", "INT", "ID", "WS",
			"ERROR" };

	public YicesLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this, _ATN, _decisionToDFA, _sharedContextCache);
	}

	@Override
	public String getGrammarFileName() {
		return "Yices.g4";
	}

	@Override
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override
	public String[] getRuleNames() {
		return ruleNames;
	}

	@Override
	public String getSerializedATN() {
		return _serializedATN;
	}

	@Override
	public String[] getModeNames() {
		return modeNames;
	}

	@Override
	public ATN getATN() {
		return _ATN;
	}

	public static final String _serializedATN = "\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2\25\u00a9\b\1\4\2"
			+ "\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"
			+ "\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"
			+ "\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\3\2\3\2\3\3\3\3\3\3\3\3"
			+ "\3\3\3\4\3\4\3\4\3\4\3\4\3\4\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\6\3\6\3"
			+ "\6\3\6\3\6\3\7\3\7\3\7\3\7\3\7\3\7\3\b\3\b\3\b\3\b\3\t\3\t\3\n\3\n\3\n"
			+ "\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3"
			+ "\13\3\13\3\13\3\13\3\f\3\f\3\r\3\r\3\16\3\16\3\17\3\17\3\17\3\17\3\17"
			+ "\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17"
			+ "\5\17\u0083\n\17\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\5\20\u008e"
			+ "\n\20\3\21\3\21\3\22\3\22\3\23\6\23\u0095\n\23\r\23\16\23\u0096\3\24\3"
			+ "\24\3\24\7\24\u009c\n\24\f\24\16\24\u009f\13\24\3\25\6\25\u00a2\n\25\r"
			+ "\25\16\25\u00a3\3\25\3\25\3\26\3\26\2\2\27\3\3\5\4\7\5\t\6\13\7\r\b\17"
			+ "\t\21\n\23\13\25\f\27\r\31\16\33\17\35\20\37\21!\2#\2%\22\'\23)\24+\25"
			+ "\3\2\5\3\2\62;\t\2##%\'\60\60<<Bac|\u0080\u0080\5\2\13\f\16\17\"\"\u00ae"
			+ "\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2"
			+ "\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2"
			+ "\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2%\3\2\2\2\2\'\3"
			+ "\2\2\2\2)\3\2\2\2\2+\3\2\2\2\3-\3\2\2\2\5/\3\2\2\2\7\64\3\2\2\2\t:\3\2"
			+ "\2\2\13B\3\2\2\2\rG\3\2\2\2\17M\3\2\2\2\21Q\3\2\2\2\23S\3\2\2\2\25]\3"
			+ "\2\2\2\27i\3\2\2\2\31k\3\2\2\2\33m\3\2\2\2\35\u0082\3\2\2\2\37\u008d\3"
			+ "\2\2\2!\u008f\3\2\2\2#\u0091\3\2\2\2%\u0094\3\2\2\2\'\u0098\3\2\2\2)\u00a1"
			+ "\3\2\2\2+\u00a7\3\2\2\2-.\7\61\2\2.\4\3\2\2\2/\60\7k\2\2\60\61\7f\2\2"
			+ "\61\62\7u\2\2\62\63\7<\2\2\63\6\3\2\2\2\64\65\7w\2\2\65\66\7p\2\2\66\67"
			+ "\7u\2\2\678\7c\2\289\7v\2\29\b\3\2\2\2:;\7w\2\2;<\7p\2\2<=\7m\2\2=>\7"
			+ "p\2\2>?\7q\2\2?@\7y\2\2@A\7p\2\2A\n\3\2\2\2BC\7e\2\2CD\7q\2\2DE\7t\2\2"
			+ "EF\7g\2\2F\f\3\2\2\2GH\7e\2\2HI\7q\2\2IJ\7u\2\2JK\7v\2\2KL\7<\2\2L\16"
			+ "\3\2\2\2MN\7u\2\2NO\7c\2\2OP\7v\2\2P\20\3\2\2\2QR\7?\2\2R\22\3\2\2\2S"
			+ "T\7c\2\2TU\7u\2\2UV\7u\2\2VW\7g\2\2WX\7t\2\2XY\7v\2\2YZ\7k\2\2Z[\7q\2"
			+ "\2[\\\7p\2\2\\\24\3\2\2\2]^\7w\2\2^_\7p\2\2_`\7u\2\2`a\7c\2\2ab\7v\2\2"
			+ "bc\7k\2\2cd\7u\2\2de\7h\2\2ef\7k\2\2fg\7g\2\2gh\7f\2\2h\26\3\2\2\2ij\7"
			+ "*\2\2j\30\3\2\2\2kl\7+\2\2l\32\3\2\2\2mn\7/\2\2n\34\3\2\2\2op\7o\2\2p"
			+ "q\7q\2\2q\u0083\7f\2\2rs\7f\2\2st\7k\2\2t\u0083\7x\2\2uv\7v\2\2vw\7q\2"
			+ "\2wx\7a\2\2xy\7k\2\2yz\7p\2\2z\u0083\7v\2\2{|\7v\2\2|}\7q\2\2}~\7a\2\2"
			+ "~\177\7t\2\2\177\u0080\7g\2\2\u0080\u0081\7c\2\2\u0081\u0083\7n\2\2\u0082"
			+ "o\3\2\2\2\u0082r\3\2\2\2\u0082u\3\2\2\2\u0082{\3\2\2\2\u0083\36\3\2\2"
			+ "\2\u0084\u0085\7v\2\2\u0085\u0086\7t\2\2\u0086\u0087\7w\2\2\u0087\u008e"
			+ "\7g\2\2\u0088\u0089\7h\2\2\u0089\u008a\7c\2\2\u008a\u008b\7n\2\2\u008b"
			+ "\u008c\7u\2\2\u008c\u008e\7g\2\2\u008d\u0084\3\2\2\2\u008d\u0088\3\2\2"
			+ "\2\u008e \3\2\2\2\u008f\u0090\t\2\2\2\u0090\"\3\2\2\2\u0091\u0092\t\3"
			+ "\2\2\u0092$\3\2\2\2\u0093\u0095\5!\21\2\u0094\u0093\3\2\2\2\u0095\u0096"
			+ "\3\2\2\2\u0096\u0094\3\2\2\2\u0096\u0097\3\2\2\2\u0097&\3\2\2\2\u0098"
			+ "\u009d\5#\22\2\u0099\u009c\5#\22\2\u009a\u009c\5!\21\2\u009b\u0099\3\2"
			+ "\2\2\u009b\u009a\3\2\2\2\u009c\u009f\3\2\2\2\u009d\u009b\3\2\2\2\u009d"
			+ "\u009e\3\2\2\2\u009e(\3\2\2\2\u009f\u009d\3\2\2\2\u00a0\u00a2\t\4\2\2"
			+ "\u00a1\u00a0\3\2\2\2\u00a2\u00a3\3\2\2\2\u00a3\u00a1\3\2\2\2\u00a3\u00a4"
			+ "\3\2\2\2\u00a4\u00a5\3\2\2\2\u00a5\u00a6\b\25\2\2\u00a6*\3\2\2\2\u00a7"
			+ "\u00a8\13\2\2\2\u00a8,\3\2\2\2\t\2\u0082\u008d\u0096\u009b\u009d\u00a3" + "\3\b\2\2";
	public static final ATN _ATN = new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}