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
		T__10=1, T__9=2, T__8=3, T__7=4, T__6=5, T__5=6, T__4=7, T__3=8, T__2=9, 
		T__1=10, T__0=11, INTEGER=12, REAL=13, INFRULE=14, EXPONENT=15, NUMERAL=16, 
		SIMPLE_SYMBOL=17, SYMBOL_CHAR=18, SYMBOL_OR_NUM_CHAR=19, QUOTED_SYMBOL=20, 
		STRING=21, WS=22, COMMENT=23, TOEOL=24, ERROR=25;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] tokenNames = {
		"'\\u0000'", "'\\u0001'", "'\\u0002'", "'\\u0003'", "'\\u0004'", "'\\u0005'", 
		"'\\u0006'", "'\\u0007'", "'\b'", "'\t'", "'\n'", "'\\u000B'", "'\f'", 
		"'\r'", "'\\u000E'", "'\\u000F'", "'\\u0010'", "'\\u0011'", "'\\u0012'", 
		"'\\u0013'", "'\\u0014'", "'\\u0015'", "'\\u0016'", "'\\u0017'", "'\\u0018'", 
		"'\\u0019'"
	};
	public static final String[] ruleNames = {
		"T__10", "T__9", "T__8", "T__7", "T__6", "T__5", "T__4", "T__3", "T__2", 
		"T__1", "T__0", "INTEGER", "REAL", "INFRULE", "EXPONENT", "NUMERAL", "SIMPLE_SYMBOL", 
		"SYMBOL_CHAR", "SYMBOL_OR_NUM_CHAR", "QUOTED_SYMBOL", "STRING", "WS", 
		"COMMENT", "TOEOL", "ERROR"
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
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2\33\u00d6\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31"+
		"\t\31\4\32\t\32\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\3\3\3\3\3\3\3\3"+
		"\3\3\4\3\4\3\4\3\4\3\4\3\4\3\5\3\5\3\5\3\5\3\5\3\6\3\6\3\6\3\6\3\6\3\6"+
		"\3\6\3\6\3\6\3\6\3\6\3\7\3\7\3\b\3\b\3\t\3\t\3\n\3\n\3\13\3\13\3\f\3\f"+
		"\3\f\3\f\3\f\3\f\3\r\5\rk\n\r\3\r\3\r\3\16\5\16p\n\16\3\16\3\16\3\16\3"+
		"\16\5\16v\n\16\3\17\5\17y\n\17\3\17\3\17\3\17\3\17\3\17\3\17\3\20\3\20"+
		"\5\20\u0083\n\20\3\20\3\20\3\21\6\21\u0088\n\21\r\21\16\21\u0089\3\22"+
		"\3\22\7\22\u008e\n\22\f\22\16\22\u0091\13\22\3\23\3\23\3\24\3\24\5\24"+
		"\u0097\n\24\3\25\3\25\3\25\7\25\u009c\n\25\f\25\16\25\u009f\13\25\3\25"+
		"\3\25\3\26\3\26\3\26\3\26\7\26\u00a7\n\26\f\26\16\26\u00aa\13\26\3\26"+
		"\3\26\3\27\6\27\u00af\n\27\r\27\16\27\u00b0\3\27\3\27\3\30\3\30\3\30\7"+
		"\30\u00b8\n\30\f\30\16\30\u00bb\13\30\3\30\5\30\u00be\n\30\3\30\5\30\u00c1"+
		"\n\30\3\30\5\30\u00c4\n\30\3\30\3\30\3\31\3\31\7\31\u00ca\n\31\f\31\16"+
		"\31\u00cd\13\31\3\31\5\31\u00d0\n\31\3\31\5\31\u00d3\n\31\3\32\3\32\4"+
		"\u009d\u00a8\2\33\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31"+
		"\16\33\17\35\20\37\21!\22#\23%\24\'\25)\26+\27-\30/\31\61\32\63\33\3\2"+
		"\n\4\2--//\4\2GGgg\3\2\62;\13\2##&(,-/\61>\\^^`ac|\u0080\u0080\3\2~~\5"+
		"\2\13\f\16\17\"\"\5\2\f\f\17\17\'\'\4\2\f\f\17\17\u00e8\2\3\3\2\2\2\2"+
		"\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2"+
		"\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2"+
		"\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2"+
		"\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2\2\2"+
		"\2\63\3\2\2\2\3\65\3\2\2\2\5>\3\2\2\2\7C\3\2\2\2\tI\3\2\2\2\13N\3\2\2"+
		"\2\rY\3\2\2\2\17[\3\2\2\2\21]\3\2\2\2\23_\3\2\2\2\25a\3\2\2\2\27c\3\2"+
		"\2\2\31j\3\2\2\2\33o\3\2\2\2\35x\3\2\2\2\37\u0080\3\2\2\2!\u0087\3\2\2"+
		"\2#\u008b\3\2\2\2%\u0092\3\2\2\2\'\u0096\3\2\2\2)\u0098\3\2\2\2+\u00a2"+
		"\3\2\2\2-\u00ae\3\2\2\2/\u00b4\3\2\2\2\61\u00c7\3\2\2\2\63\u00d4\3\2\2"+
		"\2\65\66\7Y\2\2\66\67\7c\2\2\678\7t\2\289\7p\2\29:\7k\2\2:;\7p\2\2;<\7"+
		"i\2\2<=\7<\2\2=\4\3\2\2\2>?\7v\2\2?@\7t\2\2@A\7w\2\2AB\7g\2\2B\6\3\2\2"+
		"\2CD\7w\2\2DE\7p\2\2EF\7f\2\2FG\7g\2\2GH\7h\2\2H\b\3\2\2\2IJ\7D\2\2JK"+
		"\7q\2\2KL\7q\2\2LM\7n\2\2M\n\3\2\2\2NO\7]\2\2OP\7\"\2\2PQ\7G\2\2QR\7P"+
		"\2\2RS\7V\2\2ST\7K\2\2TU\7T\2\2UV\7G\2\2VW\7\"\2\2WX\7_\2\2X\f\3\2\2\2"+
		"YZ\7<\2\2Z\16\3\2\2\2[\\\7]\2\2\\\20\3\2\2\2]^\7.\2\2^\22\3\2\2\2_`\7"+
		"_\2\2`\24\3\2\2\2ab\7?\2\2b\26\3\2\2\2cd\7h\2\2de\7c\2\2ef\7n\2\2fg\7"+
		"u\2\2gh\7g\2\2h\30\3\2\2\2ik\t\2\2\2ji\3\2\2\2jk\3\2\2\2kl\3\2\2\2lm\5"+
		"!\21\2m\32\3\2\2\2np\t\2\2\2on\3\2\2\2op\3\2\2\2pq\3\2\2\2qr\5!\21\2r"+
		"s\7\60\2\2su\5!\21\2tv\5\37\20\2ut\3\2\2\2uv\3\2\2\2v\34\3\2\2\2wy\t\2"+
		"\2\2xw\3\2\2\2xy\3\2\2\2yz\3\2\2\2z{\7K\2\2{|\7P\2\2|}\7H\2\2}~\7V\2\2"+
		"~\177\7[\2\2\177\36\3\2\2\2\u0080\u0082\t\3\2\2\u0081\u0083\t\2\2\2\u0082"+
		"\u0081\3\2\2\2\u0082\u0083\3\2\2\2\u0083\u0084\3\2\2\2\u0084\u0085\5!"+
		"\21\2\u0085 \3\2\2\2\u0086\u0088\t\4\2\2\u0087\u0086\3\2\2\2\u0088\u0089"+
		"\3\2\2\2\u0089\u0087\3\2\2\2\u0089\u008a\3\2\2\2\u008a\"\3\2\2\2\u008b"+
		"\u008f\5%\23\2\u008c\u008e\5\'\24\2\u008d\u008c\3\2\2\2\u008e\u0091\3"+
		"\2\2\2\u008f\u008d\3\2\2\2\u008f\u0090\3\2\2\2\u0090$\3\2\2\2\u0091\u008f"+
		"\3\2\2\2\u0092\u0093\t\5\2\2\u0093&\3\2\2\2\u0094\u0097\5%\23\2\u0095"+
		"\u0097\4\62;\2\u0096\u0094\3\2\2\2\u0096\u0095\3\2\2\2\u0097(\3\2\2\2"+
		"\u0098\u0099\7~\2\2\u0099\u009d\n\6\2\2\u009a\u009c\13\2\2\2\u009b\u009a"+
		"\3\2\2\2\u009c\u009f\3\2\2\2\u009d\u009e\3\2\2\2\u009d\u009b\3\2\2\2\u009e"+
		"\u00a0\3\2\2\2\u009f\u009d\3\2\2\2\u00a0\u00a1\7~\2\2\u00a1*\3\2\2\2\u00a2"+
		"\u00a8\7$\2\2\u00a3\u00a4\7$\2\2\u00a4\u00a7\7$\2\2\u00a5\u00a7\13\2\2"+
		"\2\u00a6\u00a3\3\2\2\2\u00a6\u00a5\3\2\2\2\u00a7\u00aa\3\2\2\2\u00a8\u00a9"+
		"\3\2\2\2\u00a8\u00a6\3\2\2\2\u00a9\u00ab\3\2\2\2\u00aa\u00a8\3\2\2\2\u00ab"+
		"\u00ac\7$\2\2\u00ac,\3\2\2\2\u00ad\u00af\t\7\2\2\u00ae\u00ad\3\2\2\2\u00af"+
		"\u00b0\3\2\2\2\u00b0\u00ae\3\2\2\2\u00b0\u00b1\3\2\2\2\u00b1\u00b2\3\2"+
		"\2\2\u00b2\u00b3\b\27\2\2\u00b3.\3\2\2\2\u00b4\u00bd\7=\2\2\u00b5\u00b9"+
		"\n\b\2\2\u00b6\u00b8\n\t\2\2\u00b7\u00b6\3\2\2\2\u00b8\u00bb\3\2\2\2\u00b9"+
		"\u00b7\3\2\2\2\u00b9\u00ba\3\2\2\2\u00ba\u00be\3\2\2\2\u00bb\u00b9\3\2"+
		"\2\2\u00bc\u00be\3\2\2\2\u00bd\u00b5\3\2\2\2\u00bd\u00bc\3\2\2\2\u00be"+
		"\u00c3\3\2\2\2\u00bf\u00c1\7\17\2\2\u00c0\u00bf\3\2\2\2\u00c0\u00c1\3"+
		"\2\2\2\u00c1\u00c2\3\2\2\2\u00c2\u00c4\7\f\2\2\u00c3\u00c0\3\2\2\2\u00c3"+
		"\u00c4\3\2\2\2\u00c4\u00c5\3\2\2\2\u00c5\u00c6\b\30\2\2\u00c6\60\3\2\2"+
		"\2\u00c7\u00cb\n\b\2\2\u00c8\u00ca\n\t\2\2\u00c9\u00c8\3\2\2\2\u00ca\u00cd"+
		"\3\2\2\2\u00cb\u00c9\3\2\2\2\u00cb\u00cc\3\2\2\2\u00cc\u00d2\3\2\2\2\u00cd"+
		"\u00cb\3\2\2\2\u00ce\u00d0\7\17\2\2\u00cf\u00ce\3\2\2\2\u00cf\u00d0\3"+
		"\2\2\2\u00d0\u00d1\3\2\2\2\u00d1\u00d3\7\f\2\2\u00d2\u00cf\3\2\2\2\u00d2"+
		"\u00d3\3\2\2\2\u00d3\62\3\2\2\2\u00d4\u00d5\13\2\2\2\u00d5\64\3\2\2\2"+
		"\26\2joux\u0082\u0089\u008f\u0096\u009d\u00a6\u00a8\u00b0\u00b9\u00bd"+
		"\u00c0\u00c3\u00cb\u00cf\u00d2\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}