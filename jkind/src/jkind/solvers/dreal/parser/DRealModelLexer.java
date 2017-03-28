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
		T__0=10, REAL=11, INFRULE=12, EXPONENT=13, NUMERAL=14, SIMPLE_SYMBOL=15, 
		SYMBOL_CHAR=16, SYMBOL_OR_NUM_CHAR=17, QUOTED_SYMBOL=18, STRING=19, WARNING=20, 
		WS=21, COMMENT=22, ERROR=23;
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
		"T__0", "REAL", "INFRULE", "EXPONENT", "NUMERAL", "SIMPLE_SYMBOL", "SYMBOL_CHAR", 
		"SYMBOL_OR_NUM_CHAR", "QUOTED_SYMBOL", "STRING", "WARNING", "WS", "COMMENT", 
		"ERROR"
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
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2\31\u00ce\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\3\2"+
		"\3\2\3\2\3\2\3\2\3\3\3\3\3\3\3\3\3\3\3\3\3\4\3\4\3\4\3\4\3\4\3\5\3\5\3"+
		"\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\6\3\6\3\7\3\7\3\b\3\b\3\t\3\t\3\n"+
		"\3\n\3\13\3\13\3\13\3\13\3\13\3\13\3\f\5\f^\n\f\3\f\3\f\3\f\5\fc\n\f\3"+
		"\f\5\ff\n\f\3\r\5\ri\n\r\3\r\3\r\3\r\3\r\3\r\3\r\3\16\3\16\5\16s\n\16"+
		"\3\16\3\16\3\17\6\17x\n\17\r\17\16\17y\3\20\3\20\7\20~\n\20\f\20\16\20"+
		"\u0081\13\20\3\21\3\21\3\22\3\22\5\22\u0087\n\22\3\23\3\23\3\23\7\23\u008c"+
		"\n\23\f\23\16\23\u008f\13\23\3\23\3\23\3\24\3\24\3\24\3\24\7\24\u0097"+
		"\n\24\f\24\16\24\u009a\13\24\3\24\3\24\3\25\3\25\3\25\3\25\3\25\3\25\3"+
		"\25\3\25\3\25\3\25\3\25\7\25\u00a9\n\25\f\25\16\25\u00ac\13\25\3\25\5"+
		"\25\u00af\n\25\3\25\3\25\3\26\6\26\u00b4\n\26\r\26\16\26\u00b5\3\26\3"+
		"\26\3\27\3\27\3\27\7\27\u00bd\n\27\f\27\16\27\u00c0\13\27\3\27\5\27\u00c3"+
		"\n\27\3\27\5\27\u00c6\n\27\3\27\5\27\u00c9\n\27\3\27\3\27\3\30\3\30\4"+
		"\u008d\u0098\2\31\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31"+
		"\16\33\17\35\20\37\21!\22#\23%\24\'\25)\26+\27-\30/\31\3\2\n\4\2--//\4"+
		"\2GGgg\3\2\62;\13\2##&(,-/\61>\\^^`ac|\u0080\u0080\3\2~~\4\2\f\f\17\17"+
		"\5\2\13\f\16\17\"\"\5\2\f\f\17\17\'\'\u00df\2\3\3\2\2\2\2\5\3\2\2\2\2"+
		"\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2"+
		"\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2"+
		"\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2"+
		"\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2\3\61\3\2\2\2\5\66\3\2\2"+
		"\2\7<\3\2\2\2\tA\3\2\2\2\13L\3\2\2\2\rN\3\2\2\2\17P\3\2\2\2\21R\3\2\2"+
		"\2\23T\3\2\2\2\25V\3\2\2\2\27]\3\2\2\2\31h\3\2\2\2\33p\3\2\2\2\35w\3\2"+
		"\2\2\37{\3\2\2\2!\u0082\3\2\2\2#\u0086\3\2\2\2%\u0088\3\2\2\2\'\u0092"+
		"\3\2\2\2)\u009d\3\2\2\2+\u00b3\3\2\2\2-\u00b9\3\2\2\2/\u00cc\3\2\2\2\61"+
		"\62\7v\2\2\62\63\7t\2\2\63\64\7w\2\2\64\65\7g\2\2\65\4\3\2\2\2\66\67\7"+
		"w\2\2\678\7p\2\289\7f\2\29:\7g\2\2:;\7h\2\2;\6\3\2\2\2<=\7D\2\2=>\7q\2"+
		"\2>?\7q\2\2?@\7n\2\2@\b\3\2\2\2AB\7]\2\2BC\7\"\2\2CD\7G\2\2DE\7P\2\2E"+
		"F\7V\2\2FG\7K\2\2GH\7T\2\2HI\7G\2\2IJ\7\"\2\2JK\7_\2\2K\n\3\2\2\2LM\7"+
		"<\2\2M\f\3\2\2\2NO\7]\2\2O\16\3\2\2\2PQ\7.\2\2Q\20\3\2\2\2RS\7_\2\2S\22"+
		"\3\2\2\2TU\7?\2\2U\24\3\2\2\2VW\7h\2\2WX\7c\2\2XY\7n\2\2YZ\7u\2\2Z[\7"+
		"g\2\2[\26\3\2\2\2\\^\t\2\2\2]\\\3\2\2\2]^\3\2\2\2^_\3\2\2\2_b\5\35\17"+
		"\2`a\7\60\2\2ac\5\35\17\2b`\3\2\2\2bc\3\2\2\2ce\3\2\2\2df\5\33\16\2ed"+
		"\3\2\2\2ef\3\2\2\2f\30\3\2\2\2gi\t\2\2\2hg\3\2\2\2hi\3\2\2\2ij\3\2\2\2"+
		"jk\7K\2\2kl\7P\2\2lm\7H\2\2mn\7V\2\2no\7[\2\2o\32\3\2\2\2pr\t\3\2\2qs"+
		"\t\2\2\2rq\3\2\2\2rs\3\2\2\2st\3\2\2\2tu\5\35\17\2u\34\3\2\2\2vx\t\4\2"+
		"\2wv\3\2\2\2xy\3\2\2\2yw\3\2\2\2yz\3\2\2\2z\36\3\2\2\2{\177\5!\21\2|~"+
		"\5#\22\2}|\3\2\2\2~\u0081\3\2\2\2\177}\3\2\2\2\177\u0080\3\2\2\2\u0080"+
		" \3\2\2\2\u0081\177\3\2\2\2\u0082\u0083\t\5\2\2\u0083\"\3\2\2\2\u0084"+
		"\u0087\5!\21\2\u0085\u0087\4\62;\2\u0086\u0084\3\2\2\2\u0086\u0085\3\2"+
		"\2\2\u0087$\3\2\2\2\u0088\u0089\7~\2\2\u0089\u008d\n\6\2\2\u008a\u008c"+
		"\13\2\2\2\u008b\u008a\3\2\2\2\u008c\u008f\3\2\2\2\u008d\u008e\3\2\2\2"+
		"\u008d\u008b\3\2\2\2\u008e\u0090\3\2\2\2\u008f\u008d\3\2\2\2\u0090\u0091"+
		"\7~\2\2\u0091&\3\2\2\2\u0092\u0098\7$\2\2\u0093\u0094\7$\2\2\u0094\u0097"+
		"\7$\2\2\u0095\u0097\13\2\2\2\u0096\u0093\3\2\2\2\u0096\u0095\3\2\2\2\u0097"+
		"\u009a\3\2\2\2\u0098\u0099\3\2\2\2\u0098\u0096\3\2\2\2\u0099\u009b\3\2"+
		"\2\2\u009a\u0098\3\2\2\2\u009b\u009c\7$\2\2\u009c(\3\2\2\2\u009d\u009e"+
		"\7Y\2\2\u009e\u009f\7c\2\2\u009f\u00a0\7t\2\2\u00a0\u00a1\7p\2\2\u00a1"+
		"\u00a2\7k\2\2\u00a2\u00a3\7p\2\2\u00a3\u00a4\7i\2\2\u00a4\u00a5\7<\2\2"+
		"\u00a5\u00a6\3\2\2\2\u00a6\u00aa\n\7\2\2\u00a7\u00a9\n\7\2\2\u00a8\u00a7"+
		"\3\2\2\2\u00a9\u00ac\3\2\2\2\u00aa\u00a8\3\2\2\2\u00aa\u00ab\3\2\2\2\u00ab"+
		"\u00ae\3\2\2\2\u00ac\u00aa\3\2\2\2\u00ad\u00af\7\17\2\2\u00ae\u00ad\3"+
		"\2\2\2\u00ae\u00af\3\2\2\2\u00af\u00b0\3\2\2\2\u00b0\u00b1\7\f\2\2\u00b1"+
		"*\3\2\2\2\u00b2\u00b4\t\b\2\2\u00b3\u00b2\3\2\2\2\u00b4\u00b5\3\2\2\2"+
		"\u00b5\u00b3\3\2\2\2\u00b5\u00b6\3\2\2\2\u00b6\u00b7\3\2\2\2\u00b7\u00b8"+
		"\b\26\2\2\u00b8,\3\2\2\2\u00b9\u00c2\7=\2\2\u00ba\u00be\n\t\2\2\u00bb"+
		"\u00bd\n\7\2\2\u00bc\u00bb\3\2\2\2\u00bd\u00c0\3\2\2\2\u00be\u00bc\3\2"+
		"\2\2\u00be\u00bf\3\2\2\2\u00bf\u00c3\3\2\2\2\u00c0\u00be\3\2\2\2\u00c1"+
		"\u00c3\3\2\2\2\u00c2\u00ba\3\2\2\2\u00c2\u00c1\3\2\2\2\u00c3\u00c8\3\2"+
		"\2\2\u00c4\u00c6\7\17\2\2\u00c5\u00c4\3\2\2\2\u00c5\u00c6\3\2\2\2\u00c6"+
		"\u00c7\3\2\2\2\u00c7\u00c9\7\f\2\2\u00c8\u00c5\3\2\2\2\u00c8\u00c9\3\2"+
		"\2\2\u00c9\u00ca\3\2\2\2\u00ca\u00cb\b\27\2\2\u00cb.\3\2\2\2\u00cc\u00cd"+
		"\13\2\2\2\u00cd\60\3\2\2\2\25\2]behry\177\u0086\u008d\u0096\u0098\u00aa"+
		"\u00ae\u00b5\u00be\u00c2\u00c5\u00c8\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}