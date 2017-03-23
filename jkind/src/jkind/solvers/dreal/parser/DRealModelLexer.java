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
		SYMBOL_CHAR=17, SYMBOL_OR_NUM_CHAR=18, QUOTED_SYMBOL=19, STRING=20, WARNING=21, 
		WS=22, COMMENT=23, ERROR=24;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] tokenNames = {
		"'\\u0000'", "'\\u0001'", "'\\u0002'", "'\\u0003'", "'\\u0004'", "'\\u0005'", 
		"'\\u0006'", "'\\u0007'", "'\b'", "'\t'", "'\n'", "'\\u000B'", "'\f'", 
		"'\r'", "'\\u000E'", "'\\u000F'", "'\\u0010'", "'\\u0011'", "'\\u0012'", 
		"'\\u0013'", "'\\u0014'", "'\\u0015'", "'\\u0016'", "'\\u0017'", "'\\u0018'"
	};
	public static final String[] ruleNames = {
		"T__9", "T__8", "T__7", "T__6", "T__5", "T__4", "T__3", "T__2", "T__1", 
		"T__0", "INTEGER", "REAL", "INFRULE", "EXPONENT", "NUMERAL", "SIMPLE_SYMBOL", 
		"SYMBOL_CHAR", "SYMBOL_OR_NUM_CHAR", "QUOTED_SYMBOL", "STRING", "WARNING", 
		"WS", "COMMENT", "ERROR"
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
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2\32\u00d3\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31"+
		"\t\31\3\2\3\2\3\2\3\2\3\2\3\3\3\3\3\3\3\3\3\3\3\3\3\4\3\4\3\4\3\4\3\4"+
		"\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\6\3\6\3\7\3\7\3\b\3\b\3"+
		"\t\3\t\3\n\3\n\3\13\3\13\3\13\3\13\3\13\3\13\3\f\5\f`\n\f\3\f\3\f\3\r"+
		"\5\re\n\r\3\r\3\r\3\r\3\r\5\rk\n\r\3\16\5\16n\n\16\3\16\3\16\3\16\3\16"+
		"\3\16\3\16\3\17\3\17\5\17x\n\17\3\17\3\17\3\20\6\20}\n\20\r\20\16\20~"+
		"\3\21\3\21\7\21\u0083\n\21\f\21\16\21\u0086\13\21\3\22\3\22\3\23\3\23"+
		"\5\23\u008c\n\23\3\24\3\24\3\24\7\24\u0091\n\24\f\24\16\24\u0094\13\24"+
		"\3\24\3\24\3\25\3\25\3\25\3\25\7\25\u009c\n\25\f\25\16\25\u009f\13\25"+
		"\3\25\3\25\3\26\3\26\3\26\3\26\3\26\3\26\3\26\3\26\3\26\3\26\3\26\7\26"+
		"\u00ae\n\26\f\26\16\26\u00b1\13\26\3\26\5\26\u00b4\n\26\3\26\3\26\3\27"+
		"\6\27\u00b9\n\27\r\27\16\27\u00ba\3\27\3\27\3\30\3\30\3\30\7\30\u00c2"+
		"\n\30\f\30\16\30\u00c5\13\30\3\30\5\30\u00c8\n\30\3\30\5\30\u00cb\n\30"+
		"\3\30\5\30\u00ce\n\30\3\30\3\30\3\31\3\31\4\u0092\u009d\2\32\3\3\5\4\7"+
		"\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16\33\17\35\20\37\21!\22"+
		"#\23%\24\'\25)\26+\27-\30/\31\61\32\3\2\n\4\2--//\4\2GGgg\3\2\62;\13\2"+
		"##&(,-/\61>\\^^`ac|\u0080\u0080\3\2~~\4\2\f\f\17\17\5\2\13\f\16\17\"\""+
		"\5\2\f\f\17\17\'\'\u00e4\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2"+
		"\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25"+
		"\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2"+
		"\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2"+
		"\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2\2\2\3\63\3\2\2\2\58\3\2\2\2\7>\3\2\2"+
		"\2\tC\3\2\2\2\13N\3\2\2\2\rP\3\2\2\2\17R\3\2\2\2\21T\3\2\2\2\23V\3\2\2"+
		"\2\25X\3\2\2\2\27_\3\2\2\2\31d\3\2\2\2\33m\3\2\2\2\35u\3\2\2\2\37|\3\2"+
		"\2\2!\u0080\3\2\2\2#\u0087\3\2\2\2%\u008b\3\2\2\2\'\u008d\3\2\2\2)\u0097"+
		"\3\2\2\2+\u00a2\3\2\2\2-\u00b8\3\2\2\2/\u00be\3\2\2\2\61\u00d1\3\2\2\2"+
		"\63\64\7v\2\2\64\65\7t\2\2\65\66\7w\2\2\66\67\7g\2\2\67\4\3\2\2\289\7"+
		"w\2\29:\7p\2\2:;\7f\2\2;<\7g\2\2<=\7h\2\2=\6\3\2\2\2>?\7D\2\2?@\7q\2\2"+
		"@A\7q\2\2AB\7n\2\2B\b\3\2\2\2CD\7]\2\2DE\7\"\2\2EF\7G\2\2FG\7P\2\2GH\7"+
		"V\2\2HI\7K\2\2IJ\7T\2\2JK\7G\2\2KL\7\"\2\2LM\7_\2\2M\n\3\2\2\2NO\7<\2"+
		"\2O\f\3\2\2\2PQ\7]\2\2Q\16\3\2\2\2RS\7.\2\2S\20\3\2\2\2TU\7_\2\2U\22\3"+
		"\2\2\2VW\7?\2\2W\24\3\2\2\2XY\7h\2\2YZ\7c\2\2Z[\7n\2\2[\\\7u\2\2\\]\7"+
		"g\2\2]\26\3\2\2\2^`\t\2\2\2_^\3\2\2\2_`\3\2\2\2`a\3\2\2\2ab\5\37\20\2"+
		"b\30\3\2\2\2ce\t\2\2\2dc\3\2\2\2de\3\2\2\2ef\3\2\2\2fg\5\37\20\2gh\7\60"+
		"\2\2hj\5\37\20\2ik\5\35\17\2ji\3\2\2\2jk\3\2\2\2k\32\3\2\2\2ln\t\2\2\2"+
		"ml\3\2\2\2mn\3\2\2\2no\3\2\2\2op\7K\2\2pq\7P\2\2qr\7H\2\2rs\7V\2\2st\7"+
		"[\2\2t\34\3\2\2\2uw\t\3\2\2vx\t\2\2\2wv\3\2\2\2wx\3\2\2\2xy\3\2\2\2yz"+
		"\5\37\20\2z\36\3\2\2\2{}\t\4\2\2|{\3\2\2\2}~\3\2\2\2~|\3\2\2\2~\177\3"+
		"\2\2\2\177 \3\2\2\2\u0080\u0084\5#\22\2\u0081\u0083\5%\23\2\u0082\u0081"+
		"\3\2\2\2\u0083\u0086\3\2\2\2\u0084\u0082\3\2\2\2\u0084\u0085\3\2\2\2\u0085"+
		"\"\3\2\2\2\u0086\u0084\3\2\2\2\u0087\u0088\t\5\2\2\u0088$\3\2\2\2\u0089"+
		"\u008c\5#\22\2\u008a\u008c\4\62;\2\u008b\u0089\3\2\2\2\u008b\u008a\3\2"+
		"\2\2\u008c&\3\2\2\2\u008d\u008e\7~\2\2\u008e\u0092\n\6\2\2\u008f\u0091"+
		"\13\2\2\2\u0090\u008f\3\2\2\2\u0091\u0094\3\2\2\2\u0092\u0093\3\2\2\2"+
		"\u0092\u0090\3\2\2\2\u0093\u0095\3\2\2\2\u0094\u0092\3\2\2\2\u0095\u0096"+
		"\7~\2\2\u0096(\3\2\2\2\u0097\u009d\7$\2\2\u0098\u0099\7$\2\2\u0099\u009c"+
		"\7$\2\2\u009a\u009c\13\2\2\2\u009b\u0098\3\2\2\2\u009b\u009a\3\2\2\2\u009c"+
		"\u009f\3\2\2\2\u009d\u009e\3\2\2\2\u009d\u009b\3\2\2\2\u009e\u00a0\3\2"+
		"\2\2\u009f\u009d\3\2\2\2\u00a0\u00a1\7$\2\2\u00a1*\3\2\2\2\u00a2\u00a3"+
		"\7Y\2\2\u00a3\u00a4\7c\2\2\u00a4\u00a5\7t\2\2\u00a5\u00a6\7p\2\2\u00a6"+
		"\u00a7\7k\2\2\u00a7\u00a8\7p\2\2\u00a8\u00a9\7i\2\2\u00a9\u00aa\7<\2\2"+
		"\u00aa\u00ab\3\2\2\2\u00ab\u00af\n\7\2\2\u00ac\u00ae\n\7\2\2\u00ad\u00ac"+
		"\3\2\2\2\u00ae\u00b1\3\2\2\2\u00af\u00ad\3\2\2\2\u00af\u00b0\3\2\2\2\u00b0"+
		"\u00b3\3\2\2\2\u00b1\u00af\3\2\2\2\u00b2\u00b4\7\17\2\2\u00b3\u00b2\3"+
		"\2\2\2\u00b3\u00b4\3\2\2\2\u00b4\u00b5\3\2\2\2\u00b5\u00b6\7\f\2\2\u00b6"+
		",\3\2\2\2\u00b7\u00b9\t\b\2\2\u00b8\u00b7\3\2\2\2\u00b9\u00ba\3\2\2\2"+
		"\u00ba\u00b8\3\2\2\2\u00ba\u00bb\3\2\2\2\u00bb\u00bc\3\2\2\2\u00bc\u00bd"+
		"\b\27\2\2\u00bd.\3\2\2\2\u00be\u00c7\7=\2\2\u00bf\u00c3\n\t\2\2\u00c0"+
		"\u00c2\n\7\2\2\u00c1\u00c0\3\2\2\2\u00c2\u00c5\3\2\2\2\u00c3\u00c1\3\2"+
		"\2\2\u00c3\u00c4\3\2\2\2\u00c4\u00c8\3\2\2\2\u00c5\u00c3\3\2\2\2\u00c6"+
		"\u00c8\3\2\2\2\u00c7\u00bf\3\2\2\2\u00c7\u00c6\3\2\2\2\u00c8\u00cd\3\2"+
		"\2\2\u00c9\u00cb\7\17\2\2\u00ca\u00c9\3\2\2\2\u00ca\u00cb\3\2\2\2\u00cb"+
		"\u00cc\3\2\2\2\u00cc\u00ce\7\f\2\2\u00cd\u00ca\3\2\2\2\u00cd\u00ce\3\2"+
		"\2\2\u00ce\u00cf\3\2\2\2\u00cf\u00d0\b\30\2\2\u00d0\60\3\2\2\2\u00d1\u00d2"+
		"\13\2\2\2\u00d2\62\3\2\2\2\25\2_djmw~\u0084\u008b\u0092\u009b\u009d\u00af"+
		"\u00b3\u00ba\u00c3\u00c7\u00ca\u00cd\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}