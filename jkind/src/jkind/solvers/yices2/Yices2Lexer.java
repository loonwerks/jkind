// Generated from Yices2.g4 by ANTLR 4.4
package jkind.solvers.yices2;

import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({ "all", "warnings", "unchecked", "unused", "cast" })
public class Yices2Lexer extends Lexer {
	static {
		RuntimeMetaData.checkVersion("4.4", RuntimeMetaData.VERSION);
	}

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache = new PredictionContextCache();
	public static final int T__11 = 1, T__10 = 2, T__9 = 3, T__8 = 4, T__7 = 5, T__6 = 6, T__5 = 7, T__4 = 8, T__3 = 9,
			T__2 = 10, T__1 = 11, T__0 = 12, BOOL = 13, INT = 14, ID = 15, WS = 16, ERROR = 17;
	public static String[] modeNames = { "DEFAULT_MODE" };

	public static final String[] tokenNames = { "'\\u0000'", "'\\u0001'", "'\\u0002'", "'\\u0003'", "'\\u0004'",
			"'\\u0005'", "'\\u0006'", "'\\u0007'", "'\b'", "'\t'", "'\n'", "'\\u000B'", "'\f'", "'\r'", "'\\u000E'",
			"'\\u000F'", "'\\u0010'", "'\\u0011'" };
	public static final String[] ruleNames = { "T__11", "T__10", "T__9", "T__8", "T__7", "T__6", "T__5", "T__4", "T__3",
			"T__2", "T__1", "T__0", "BOOL", "DIGIT", "SYMBOL", "INT", "ID", "WS", "ERROR" };

	public Yices2Lexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this, _ATN, _decisionToDFA, _sharedContextCache);
	}

	@Override
	public String getGrammarFileName() {
		return "Yices2.g4";
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

	public static final String _serializedATN = "\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2\23\177\b\1\4\2\t"
			+ "\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"
			+ "\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"
			+ "\4\23\t\23\4\24\t\24\3\2\3\2\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\4\3\4\3"
			+ "\4\3\4\3\4\3\5\3\5\3\5\3\6\3\6\3\6\3\6\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7"
			+ "\3\7\3\b\3\b\3\t\3\t\3\n\3\n\3\13\3\13\3\f\3\f\3\f\3\f\3\f\3\r\3\r\3\r"
			+ "\3\r\3\r\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\5\16d\n\16\3\17"
			+ "\3\17\3\20\3\20\3\21\6\21k\n\21\r\21\16\21l\3\22\3\22\3\22\7\22r\n\22"
			+ "\f\22\16\22u\13\22\3\23\6\23x\n\23\r\23\16\23y\3\23\3\23\3\24\3\24\2\2"
			+ "\25\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16\33\17\35"
			+ "\2\37\2!\20#\21%\22\'\23\3\2\5\3\2\62;\b\2##%\'\60\60Bac|\u0080\u0080"
			+ "\5\2\13\f\16\17\"\"\u0081\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2"
			+ "\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2"
			+ "\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2!\3\2\2\2\2#\3\2\2"
			+ "\2\2%\3\2\2\2\2\'\3\2\2\2\3)\3\2\2\2\5+\3\2\2\2\7\63\3\2\2\2\t8\3\2\2"
			+ "\2\13;\3\2\2\2\r?\3\2\2\2\17H\3\2\2\2\21J\3\2\2\2\23L\3\2\2\2\25N\3\2"
			+ "\2\2\27P\3\2\2\2\31U\3\2\2\2\33c\3\2\2\2\35e\3\2\2\2\37g\3\2\2\2!j\3\2"
			+ "\2\2#n\3\2\2\2%w\3\2\2\2\'}\3\2\2\2)*\7\61\2\2*\4\3\2\2\2+,\7f\2\2,-\7"
			+ "g\2\2-.\7h\2\2./\7c\2\2/\60\7w\2\2\60\61\7n\2\2\61\62\7v\2\2\62\6\3\2"
			+ "\2\2\63\64\7v\2\2\64\65\7{\2\2\65\66\7r\2\2\66\67\7g\2\2\67\b\3\2\2\2"
			+ "89\7/\2\29:\7@\2\2:\n\3\2\2\2;<\7k\2\2<=\7p\2\2=>\7v\2\2>\f\3\2\2\2?@"
			+ "\7h\2\2@A\7w\2\2AB\7p\2\2BC\7e\2\2CD\7v\2\2DE\7k\2\2EF\7q\2\2FG\7p\2\2"
			+ "G\16\3\2\2\2HI\7*\2\2I\20\3\2\2\2JK\7+\2\2K\22\3\2\2\2LM\7/\2\2M\24\3"
			+ "\2\2\2NO\7?\2\2O\26\3\2\2\2PQ\7d\2\2QR\7q\2\2RS\7q\2\2ST\7n\2\2T\30\3"
			+ "\2\2\2UV\7t\2\2VW\7g\2\2WX\7c\2\2XY\7n\2\2Y\32\3\2\2\2Z[\7v\2\2[\\\7t"
			+ "\2\2\\]\7w\2\2]d\7g\2\2^_\7h\2\2_`\7c\2\2`a\7n\2\2ab\7u\2\2bd\7g\2\2c"
			+ "Z\3\2\2\2c^\3\2\2\2d\34\3\2\2\2ef\t\2\2\2f\36\3\2\2\2gh\t\3\2\2h \3\2"
			+ "\2\2ik\5\35\17\2ji\3\2\2\2kl\3\2\2\2lj\3\2\2\2lm\3\2\2\2m\"\3\2\2\2ns"
			+ "\5\37\20\2or\5\37\20\2pr\5\35\17\2qo\3\2\2\2qp\3\2\2\2ru\3\2\2\2sq\3\2"
			+ "\2\2st\3\2\2\2t$\3\2\2\2us\3\2\2\2vx\t\4\2\2wv\3\2\2\2xy\3\2\2\2yw\3\2"
			+ "\2\2yz\3\2\2\2z{\3\2\2\2{|\b\23\2\2|&\3\2\2\2}~\13\2\2\2~(\3\2\2\2\b\2" + "clqsy\3\b\2\2";
	public static final ATN _ATN = new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}