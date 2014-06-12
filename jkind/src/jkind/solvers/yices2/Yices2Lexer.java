// Generated from Yices2.g4 by ANTLR 4.2
package jkind.solvers.yices2;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class Yices2Lexer extends Lexer {
	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__10=1, T__9=2, T__8=3, T__7=4, T__6=5, T__5=6, T__4=7, T__3=8, T__2=9, 
		T__1=10, T__0=11, BOOL=12, INT=13, ID=14, WS=15, ERROR=16;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] tokenNames = {
		"<INVALID>",
		"'->'", "')'", "'function'", "'bool'", "'type'", "'-'", "'('", "'int'", 
		"'/'", "'='", "'real'", "BOOL", "INT", "ID", "WS", "ERROR"
	};
	public static final String[] ruleNames = {
		"T__10", "T__9", "T__8", "T__7", "T__6", "T__5", "T__4", "T__3", "T__2", 
		"T__1", "T__0", "BOOL", "DIGIT", "SYMBOL", "INT", "ID", "WS", "ERROR"
	};


	public Yices2Lexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "Yices2.g4"; }

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
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2\22u\b\1\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\3\2\3\2\3\2\3\3\3\3\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\5"+
		"\3\5\3\5\3\5\3\5\3\6\3\6\3\6\3\6\3\6\3\7\3\7\3\b\3\b\3\t\3\t\3\t\3\t\3"+
		"\n\3\n\3\13\3\13\3\f\3\f\3\f\3\f\3\f\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3"+
		"\r\5\rZ\n\r\3\16\3\16\3\17\3\17\3\20\6\20a\n\20\r\20\16\20b\3\21\3\21"+
		"\3\21\7\21h\n\21\f\21\16\21k\13\21\3\22\6\22n\n\22\r\22\16\22o\3\22\3"+
		"\22\3\23\3\23\2\2\24\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27"+
		"\r\31\16\33\2\35\2\37\17!\20#\21%\22\3\2\5\3\2\62;\b\2##%\'\60\60Bac|"+
		"\u0080\u0080\5\2\13\f\16\17\"\"w\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2"+
		"\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2"+
		"\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2"+
		"#\3\2\2\2\2%\3\2\2\2\3\'\3\2\2\2\5*\3\2\2\2\7,\3\2\2\2\t\65\3\2\2\2\13"+
		":\3\2\2\2\r?\3\2\2\2\17A\3\2\2\2\21C\3\2\2\2\23G\3\2\2\2\25I\3\2\2\2\27"+
		"K\3\2\2\2\31Y\3\2\2\2\33[\3\2\2\2\35]\3\2\2\2\37`\3\2\2\2!d\3\2\2\2#m"+
		"\3\2\2\2%s\3\2\2\2\'(\7/\2\2()\7@\2\2)\4\3\2\2\2*+\7+\2\2+\6\3\2\2\2,"+
		"-\7h\2\2-.\7w\2\2./\7p\2\2/\60\7e\2\2\60\61\7v\2\2\61\62\7k\2\2\62\63"+
		"\7q\2\2\63\64\7p\2\2\64\b\3\2\2\2\65\66\7d\2\2\66\67\7q\2\2\678\7q\2\2"+
		"89\7n\2\29\n\3\2\2\2:;\7v\2\2;<\7{\2\2<=\7r\2\2=>\7g\2\2>\f\3\2\2\2?@"+
		"\7/\2\2@\16\3\2\2\2AB\7*\2\2B\20\3\2\2\2CD\7k\2\2DE\7p\2\2EF\7v\2\2F\22"+
		"\3\2\2\2GH\7\61\2\2H\24\3\2\2\2IJ\7?\2\2J\26\3\2\2\2KL\7t\2\2LM\7g\2\2"+
		"MN\7c\2\2NO\7n\2\2O\30\3\2\2\2PQ\7v\2\2QR\7t\2\2RS\7w\2\2SZ\7g\2\2TU\7"+
		"h\2\2UV\7c\2\2VW\7n\2\2WX\7u\2\2XZ\7g\2\2YP\3\2\2\2YT\3\2\2\2Z\32\3\2"+
		"\2\2[\\\t\2\2\2\\\34\3\2\2\2]^\t\3\2\2^\36\3\2\2\2_a\5\33\16\2`_\3\2\2"+
		"\2ab\3\2\2\2b`\3\2\2\2bc\3\2\2\2c \3\2\2\2di\5\35\17\2eh\5\35\17\2fh\5"+
		"\33\16\2ge\3\2\2\2gf\3\2\2\2hk\3\2\2\2ig\3\2\2\2ij\3\2\2\2j\"\3\2\2\2"+
		"ki\3\2\2\2ln\t\4\2\2ml\3\2\2\2no\3\2\2\2om\3\2\2\2op\3\2\2\2pq\3\2\2\2"+
		"qr\b\22\2\2r$\3\2\2\2st\13\2\2\2t&\3\2\2\2\b\2Ybgio\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}