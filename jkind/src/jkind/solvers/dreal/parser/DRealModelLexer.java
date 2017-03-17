// Generated from DRealModel.g4 by ANTLR 4.4
package jkind.solvers.dreal.parser;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class DRealModelLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.4", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__9=1, T__8=2, T__7=3, T__6=4, T__5=5, T__4=6, T__3=7, T__2=8, T__1=9, 
		T__0=10, INTEGER=11, REAL=12, INFRULE=13, EXPONENT=14, NUMERAL=15, SIMPLE_SYMBOL=16, 
		SYMBOL_CHAR=17, SYMBOL_OR_NUM_CHAR=18, QUOTED_SYMBOL=19, STRING=20, WS=21, 
		COMMENT=22, ERROR=23;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] tokenNames = {
		"'\\u0000'", "'\\u0001'", "'\\u0002'", "'\\u0003'", "'\\u0004'", "'\\u0005'", 
		"'\\u0006'", "'\\u0007'", "'\b'", "'\t'", "'\n'", "'\\u000B'", "'\f'", 
		"'\r'", "'\\u000E'", "'\\u000F'", "'\\u0010'", "'\\u0011'", "'\\u0012'", 
		"'\\u0013'", "'\\u0014'", "'\\u0015'", "'\\u0016'", "'\\u0017'"
	};
	public static final String[] ruleNames = {
		"T__9", "T__8", "T__7", "T__6", "T__5", "T__4", "T__3", "T__2", "T__1", 
		"T__0", "INTEGER", "REAL", "INFRULE", "EXPONENT", "NUMERAL", "SIMPLE_SYMBOL", 
		"SYMBOL_CHAR", "SYMBOL_OR_NUM_CHAR", "QUOTED_SYMBOL", "STRING", "WS", 
		"COMMENT", "ERROR"
	};


	public DRealModelLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "DRealModel.g4"; }

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
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2\31\u00bc\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\3\2"+
		"\3\2\3\2\3\2\3\2\3\3\3\3\3\3\3\3\3\3\3\3\3\4\3\4\3\4\3\4\3\4\3\5\3\5\3"+
		"\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\6\3\6\3\7\3\7\3\b\3\b\3\t\3\t\3\n"+
		"\3\n\3\13\3\13\3\13\3\13\3\13\3\13\3\f\5\f^\n\f\3\f\3\f\3\r\5\rc\n\r\3"+
		"\r\3\r\3\r\3\r\5\ri\n\r\3\16\5\16l\n\16\3\16\3\16\3\16\3\16\3\16\3\16"+
		"\3\17\3\17\5\17v\n\17\3\17\3\17\3\20\6\20{\n\20\r\20\16\20|\3\21\3\21"+
		"\7\21\u0081\n\21\f\21\16\21\u0084\13\21\3\22\3\22\3\23\3\23\5\23\u008a"+
		"\n\23\3\24\3\24\3\24\7\24\u008f\n\24\f\24\16\24\u0092\13\24\3\24\3\24"+
		"\3\25\3\25\3\25\3\25\7\25\u009a\n\25\f\25\16\25\u009d\13\25\3\25\3\25"+
		"\3\26\6\26\u00a2\n\26\r\26\16\26\u00a3\3\26\3\26\3\27\3\27\3\27\7\27\u00ab"+
		"\n\27\f\27\16\27\u00ae\13\27\3\27\5\27\u00b1\n\27\3\27\5\27\u00b4\n\27"+
		"\3\27\5\27\u00b7\n\27\3\27\3\27\3\30\3\30\4\u0090\u009b\2\31\3\3\5\4\7"+
		"\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16\33\17\35\20\37\21!\22"+
		"#\23%\24\'\25)\26+\27-\30/\31\3\2\n\4\2--//\4\2GGgg\3\2\62;\13\2##&(,"+
		"-/\61>\\^^`ac|\u0080\u0080\3\2~~\5\2\13\f\16\17\"\"\5\2\f\f\17\17\'\'"+
		"\4\2\f\f\17\17\u00cb\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2"+
		"\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3"+
		"\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2"+
		"\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2"+
		"\2-\3\2\2\2\2/\3\2\2\2\3\61\3\2\2\2\5\66\3\2\2\2\7<\3\2\2\2\tA\3\2\2\2"+
		"\13L\3\2\2\2\rN\3\2\2\2\17P\3\2\2\2\21R\3\2\2\2\23T\3\2\2\2\25V\3\2\2"+
		"\2\27]\3\2\2\2\31b\3\2\2\2\33k\3\2\2\2\35s\3\2\2\2\37z\3\2\2\2!~\3\2\2"+
		"\2#\u0085\3\2\2\2%\u0089\3\2\2\2\'\u008b\3\2\2\2)\u0095\3\2\2\2+\u00a1"+
		"\3\2\2\2-\u00a7\3\2\2\2/\u00ba\3\2\2\2\61\62\7v\2\2\62\63\7t\2\2\63\64"+
		"\7w\2\2\64\65\7g\2\2\65\4\3\2\2\2\66\67\7w\2\2\678\7p\2\289\7f\2\29:\7"+
		"g\2\2:;\7h\2\2;\6\3\2\2\2<=\7D\2\2=>\7q\2\2>?\7q\2\2?@\7n\2\2@\b\3\2\2"+
		"\2AB\7]\2\2BC\7\"\2\2CD\7G\2\2DE\7P\2\2EF\7V\2\2FG\7K\2\2GH\7T\2\2HI\7"+
		"G\2\2IJ\7\"\2\2JK\7_\2\2K\n\3\2\2\2LM\7<\2\2M\f\3\2\2\2NO\7]\2\2O\16\3"+
		"\2\2\2PQ\7.\2\2Q\20\3\2\2\2RS\7_\2\2S\22\3\2\2\2TU\7?\2\2U\24\3\2\2\2"+
		"VW\7h\2\2WX\7c\2\2XY\7n\2\2YZ\7u\2\2Z[\7g\2\2[\26\3\2\2\2\\^\t\2\2\2]"+
		"\\\3\2\2\2]^\3\2\2\2^_\3\2\2\2_`\5\37\20\2`\30\3\2\2\2ac\t\2\2\2ba\3\2"+
		"\2\2bc\3\2\2\2cd\3\2\2\2de\5\37\20\2ef\7\60\2\2fh\5\37\20\2gi\5\35\17"+
		"\2hg\3\2\2\2hi\3\2\2\2i\32\3\2\2\2jl\t\2\2\2kj\3\2\2\2kl\3\2\2\2lm\3\2"+
		"\2\2mn\7K\2\2no\7P\2\2op\7H\2\2pq\7V\2\2qr\7[\2\2r\34\3\2\2\2su\t\3\2"+
		"\2tv\t\2\2\2ut\3\2\2\2uv\3\2\2\2vw\3\2\2\2wx\5\37\20\2x\36\3\2\2\2y{\t"+
		"\4\2\2zy\3\2\2\2{|\3\2\2\2|z\3\2\2\2|}\3\2\2\2} \3\2\2\2~\u0082\5#\22"+
		"\2\177\u0081\5%\23\2\u0080\177\3\2\2\2\u0081\u0084\3\2\2\2\u0082\u0080"+
		"\3\2\2\2\u0082\u0083\3\2\2\2\u0083\"\3\2\2\2\u0084\u0082\3\2\2\2\u0085"+
		"\u0086\t\5\2\2\u0086$\3\2\2\2\u0087\u008a\5#\22\2\u0088\u008a\4\62;\2"+
		"\u0089\u0087\3\2\2\2\u0089\u0088\3\2\2\2\u008a&\3\2\2\2\u008b\u008c\7"+
		"~\2\2\u008c\u0090\n\6\2\2\u008d\u008f\13\2\2\2\u008e\u008d\3\2\2\2\u008f"+
		"\u0092\3\2\2\2\u0090\u0091\3\2\2\2\u0090\u008e\3\2\2\2\u0091\u0093\3\2"+
		"\2\2\u0092\u0090\3\2\2\2\u0093\u0094\7~\2\2\u0094(\3\2\2\2\u0095\u009b"+
		"\7$\2\2\u0096\u0097\7$\2\2\u0097\u009a\7$\2\2\u0098\u009a\13\2\2\2\u0099"+
		"\u0096\3\2\2\2\u0099\u0098\3\2\2\2\u009a\u009d\3\2\2\2\u009b\u009c\3\2"+
		"\2\2\u009b\u0099\3\2\2\2\u009c\u009e\3\2\2\2\u009d\u009b\3\2\2\2\u009e"+
		"\u009f\7$\2\2\u009f*\3\2\2\2\u00a0\u00a2\t\7\2\2\u00a1\u00a0\3\2\2\2\u00a2"+
		"\u00a3\3\2\2\2\u00a3\u00a1\3\2\2\2\u00a3\u00a4\3\2\2\2\u00a4\u00a5\3\2"+
		"\2\2\u00a5\u00a6\b\26\2\2\u00a6,\3\2\2\2\u00a7\u00b0\7=\2\2\u00a8\u00ac"+
		"\n\b\2\2\u00a9\u00ab\n\t\2\2\u00aa\u00a9\3\2\2\2\u00ab\u00ae\3\2\2\2\u00ac"+
		"\u00aa\3\2\2\2\u00ac\u00ad\3\2\2\2\u00ad\u00b1\3\2\2\2\u00ae\u00ac\3\2"+
		"\2\2\u00af\u00b1\3\2\2\2\u00b0\u00a8\3\2\2\2\u00b0\u00af\3\2\2\2\u00b1"+
		"\u00b6\3\2\2\2\u00b2\u00b4\7\17\2\2\u00b3\u00b2\3\2\2\2\u00b3\u00b4\3"+
		"\2\2\2\u00b4\u00b5\3\2\2\2\u00b5\u00b7\7\f\2\2\u00b6\u00b3\3\2\2\2\u00b6"+
		"\u00b7\3\2\2\2\u00b7\u00b8\3\2\2\2\u00b8\u00b9\b\27\2\2\u00b9.\3\2\2\2"+
		"\u00ba\u00bb\13\2\2\2\u00bb\60\3\2\2\2\23\2]bhku|\u0082\u0089\u0090\u0099"+
		"\u009b\u00a3\u00ac\u00b0\u00b3\u00b6\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}