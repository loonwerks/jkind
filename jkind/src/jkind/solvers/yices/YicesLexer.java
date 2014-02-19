// Generated from Yices.g4 by ANTLR 4.2
package jkind.solvers.yices;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class YicesLexer extends Lexer {
	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__13=1, T__12=2, T__11=3, T__10=4, T__9=5, T__8=6, T__7=7, T__6=8, T__5=9, 
		T__4=10, T__3=11, T__2=12, T__1=13, T__0=14, PREDEFINED_OP=15, BOOL=16, 
		INT=17, ID=18, WS=19, ERROR=20;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] tokenNames = {
		"<INVALID>",
		"'assertion'", "'sat'", "'ids'", "')'", "'-'", "'unknown'", "':'", "'('", 
		"'unsatisfied'", "'core'", "'/'", "'='", "'unsat'", "'cost'", "PREDEFINED_OP", 
		"BOOL", "INT", "ID", "WS", "ERROR"
	};
	public static final String[] ruleNames = {
		"T__13", "T__12", "T__11", "T__10", "T__9", "T__8", "T__7", "T__6", "T__5", 
		"T__4", "T__3", "T__2", "T__1", "T__0", "PREDEFINED_OP", "BOOL", "DIGIT", 
		"SYMBOL", "INT", "ID", "WS", "ERROR"
	};


	public YicesLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "Yices.g4"; }

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
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2\26\u009e\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\3\2\3\2\3\2\3"+
		"\2\3\2\3\2\3\2\3\2\3\2\3\2\3\3\3\3\3\3\3\3\3\4\3\4\3\4\3\4\3\5\3\5\3\6"+
		"\3\6\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\b\3\b\3\t\3\t\3\n\3\n\3\n\3\n\3"+
		"\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\13\3\13\3\13\3\13\3\13\3\f\3\f\3\r\3"+
		"\r\3\16\3\16\3\16\3\16\3\16\3\16\3\17\3\17\3\17\3\17\3\17\3\20\3\20\3"+
		"\20\3\20\3\20\3\20\5\20x\n\20\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21"+
		"\3\21\5\21\u0083\n\21\3\22\3\22\3\23\3\23\3\24\6\24\u008a\n\24\r\24\16"+
		"\24\u008b\3\25\3\25\3\25\7\25\u0091\n\25\f\25\16\25\u0094\13\25\3\26\6"+
		"\26\u0097\n\26\r\26\16\26\u0098\3\26\3\26\3\27\3\27\2\2\30\3\3\5\4\7\5"+
		"\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16\33\17\35\20\37\21!\22#\2"+
		"%\2\'\23)\24+\25-\26\3\2\5\3\2\62;\t\2##%\'\60\60B\\`ac|\u0080\u0080\5"+
		"\2\13\f\16\17\"\"\u00a1\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2"+
		"\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25"+
		"\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2"+
		"\2\2\2!\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\3/\3\2\2"+
		"\2\59\3\2\2\2\7=\3\2\2\2\tA\3\2\2\2\13C\3\2\2\2\rE\3\2\2\2\17M\3\2\2\2"+
		"\21O\3\2\2\2\23Q\3\2\2\2\25]\3\2\2\2\27b\3\2\2\2\31d\3\2\2\2\33f\3\2\2"+
		"\2\35l\3\2\2\2\37w\3\2\2\2!\u0082\3\2\2\2#\u0084\3\2\2\2%\u0086\3\2\2"+
		"\2\'\u0089\3\2\2\2)\u008d\3\2\2\2+\u0096\3\2\2\2-\u009c\3\2\2\2/\60\7"+
		"c\2\2\60\61\7u\2\2\61\62\7u\2\2\62\63\7g\2\2\63\64\7t\2\2\64\65\7v\2\2"+
		"\65\66\7k\2\2\66\67\7q\2\2\678\7p\2\28\4\3\2\2\29:\7u\2\2:;\7c\2\2;<\7"+
		"v\2\2<\6\3\2\2\2=>\7k\2\2>?\7f\2\2?@\7u\2\2@\b\3\2\2\2AB\7+\2\2B\n\3\2"+
		"\2\2CD\7/\2\2D\f\3\2\2\2EF\7w\2\2FG\7p\2\2GH\7m\2\2HI\7p\2\2IJ\7q\2\2"+
		"JK\7y\2\2KL\7p\2\2L\16\3\2\2\2MN\7<\2\2N\20\3\2\2\2OP\7*\2\2P\22\3\2\2"+
		"\2QR\7w\2\2RS\7p\2\2ST\7u\2\2TU\7c\2\2UV\7v\2\2VW\7k\2\2WX\7u\2\2XY\7"+
		"h\2\2YZ\7k\2\2Z[\7g\2\2[\\\7f\2\2\\\24\3\2\2\2]^\7e\2\2^_\7q\2\2_`\7t"+
		"\2\2`a\7g\2\2a\26\3\2\2\2bc\7\61\2\2c\30\3\2\2\2de\7?\2\2e\32\3\2\2\2"+
		"fg\7w\2\2gh\7p\2\2hi\7u\2\2ij\7c\2\2jk\7v\2\2k\34\3\2\2\2lm\7e\2\2mn\7"+
		"q\2\2no\7u\2\2op\7v\2\2p\36\3\2\2\2qr\7o\2\2rs\7q\2\2sx\7f\2\2tu\7f\2"+
		"\2uv\7k\2\2vx\7x\2\2wq\3\2\2\2wt\3\2\2\2x \3\2\2\2yz\7v\2\2z{\7t\2\2{"+
		"|\7w\2\2|\u0083\7g\2\2}~\7h\2\2~\177\7c\2\2\177\u0080\7n\2\2\u0080\u0081"+
		"\7u\2\2\u0081\u0083\7g\2\2\u0082y\3\2\2\2\u0082}\3\2\2\2\u0083\"\3\2\2"+
		"\2\u0084\u0085\t\2\2\2\u0085$\3\2\2\2\u0086\u0087\t\3\2\2\u0087&\3\2\2"+
		"\2\u0088\u008a\5#\22\2\u0089\u0088\3\2\2\2\u008a\u008b\3\2\2\2\u008b\u0089"+
		"\3\2\2\2\u008b\u008c\3\2\2\2\u008c(\3\2\2\2\u008d\u0092\5%\23\2\u008e"+
		"\u0091\5%\23\2\u008f\u0091\5#\22\2\u0090\u008e\3\2\2\2\u0090\u008f\3\2"+
		"\2\2\u0091\u0094\3\2\2\2\u0092\u0090\3\2\2\2\u0092\u0093\3\2\2\2\u0093"+
		"*\3\2\2\2\u0094\u0092\3\2\2\2\u0095\u0097\t\4\2\2\u0096\u0095\3\2\2\2"+
		"\u0097\u0098\3\2\2\2\u0098\u0096\3\2\2\2\u0098\u0099\3\2\2\2\u0099\u009a"+
		"\3\2\2\2\u009a\u009b\b\26\2\2\u009b,\3\2\2\2\u009c\u009d\13\2\2\2\u009d"+
		".\3\2\2\2\t\2w\u0082\u008b\u0090\u0092\u0098\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}