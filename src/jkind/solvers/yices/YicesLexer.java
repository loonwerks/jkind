// Generated from Yices.g4 by ANTLR 4.0
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
		T__9=1, T__8=2, T__7=3, T__6=4, T__5=5, T__4=6, T__3=7, T__2=8, T__1=9, 
		T__0=10, PREDEFINED_OP=11, BOOL=12, INT=13, ID=14, WS=15, ERROR=16;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] tokenNames = {
		"<INVALID>",
		"'sat'", "'ids'", "')'", "'-'", "':'", "'('", "'core'", "'/'", "'='", 
		"'unsat'", "PREDEFINED_OP", "BOOL", "INT", "ID", "WS", "ERROR"
	};
	public static final String[] ruleNames = {
		"T__9", "T__8", "T__7", "T__6", "T__5", "T__4", "T__3", "T__2", "T__1", 
		"T__0", "PREDEFINED_OP", "BOOL", "DIGIT", "SYMBOL", "INT", "ID", "WS", 
		"ERROR"
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
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	@Override
	public void action(RuleContext _localctx, int ruleIndex, int actionIndex) {
		switch (ruleIndex) {
		case 16: WS_action((RuleContext)_localctx, actionIndex); break;
		}
	}
	private void WS_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 0: skip();  break;
		}
	}

	public static final String _serializedATN =
		"\2\4\22s\b\1\4\2\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4"+
		"\t\t\t\4\n\t\n\4\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20"+
		"\4\21\t\21\4\22\t\22\4\23\t\23\3\2\3\2\3\2\3\2\3\3\3\3\3\3\3\3\3\4\3\4"+
		"\3\5\3\5\3\6\3\6\3\7\3\7\3\b\3\b\3\b\3\b\3\b\3\t\3\t\3\n\3\n\3\13\3\13"+
		"\3\13\3\13\3\13\3\13\3\f\3\f\3\f\3\f\3\f\3\f\5\fM\n\f\3\r\3\r\3\r\3\r"+
		"\3\r\3\r\3\r\3\r\3\r\5\rX\n\r\3\16\3\16\3\17\3\17\3\20\6\20_\n\20\r\20"+
		"\16\20`\3\21\3\21\3\21\7\21f\n\21\f\21\16\21i\13\21\3\22\6\22l\n\22\r"+
		"\22\16\22m\3\22\3\22\3\23\3\23\2\24\3\3\1\5\4\1\7\5\1\t\6\1\13\7\1\r\b"+
		"\1\17\t\1\21\n\1\23\13\1\25\f\1\27\r\1\31\16\1\33\2\1\35\2\1\37\17\1!"+
		"\20\1#\21\2%\22\1\3\2\5\3\62;\6%&B\\aac|\5\13\f\16\17\"\"v\2\3\3\2\2\2"+
		"\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2"+
		"\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2"+
		"\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\3\'\3\2\2\2\5+\3\2\2\2"+
		"\7/\3\2\2\2\t\61\3\2\2\2\13\63\3\2\2\2\r\65\3\2\2\2\17\67\3\2\2\2\21<"+
		"\3\2\2\2\23>\3\2\2\2\25@\3\2\2\2\27L\3\2\2\2\31W\3\2\2\2\33Y\3\2\2\2\35"+
		"[\3\2\2\2\37^\3\2\2\2!b\3\2\2\2#k\3\2\2\2%q\3\2\2\2\'(\7u\2\2()\7c\2\2"+
		")*\7v\2\2*\4\3\2\2\2+,\7k\2\2,-\7f\2\2-.\7u\2\2.\6\3\2\2\2/\60\7+\2\2"+
		"\60\b\3\2\2\2\61\62\7/\2\2\62\n\3\2\2\2\63\64\7<\2\2\64\f\3\2\2\2\65\66"+
		"\7*\2\2\66\16\3\2\2\2\678\7e\2\289\7q\2\29:\7t\2\2:;\7g\2\2;\20\3\2\2"+
		"\2<=\7\61\2\2=\22\3\2\2\2>?\7?\2\2?\24\3\2\2\2@A\7w\2\2AB\7p\2\2BC\7u"+
		"\2\2CD\7c\2\2DE\7v\2\2E\26\3\2\2\2FG\7o\2\2GH\7q\2\2HM\7f\2\2IJ\7f\2\2"+
		"JK\7k\2\2KM\7x\2\2LF\3\2\2\2LI\3\2\2\2M\30\3\2\2\2NO\7v\2\2OP\7t\2\2P"+
		"Q\7w\2\2QX\7g\2\2RS\7h\2\2ST\7c\2\2TU\7n\2\2UV\7u\2\2VX\7g\2\2WN\3\2\2"+
		"\2WR\3\2\2\2X\32\3\2\2\2YZ\t\2\2\2Z\34\3\2\2\2[\\\t\3\2\2\\\36\3\2\2\2"+
		"]_\5\33\16\2^]\3\2\2\2_`\3\2\2\2`^\3\2\2\2`a\3\2\2\2a \3\2\2\2bg\5\35"+
		"\17\2cf\5\35\17\2df\5\33\16\2ec\3\2\2\2ed\3\2\2\2fi\3\2\2\2ge\3\2\2\2"+
		"gh\3\2\2\2h\"\3\2\2\2ig\3\2\2\2jl\t\4\2\2kj\3\2\2\2lm\3\2\2\2mk\3\2\2"+
		"\2mn\3\2\2\2no\3\2\2\2op\b\22\2\2p$\3\2\2\2qr\13\2\2\2r&\3\2\2\2\t\2L"+
		"W`egm";
	public static final ATN _ATN =
		ATNSimulator.deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
	}
}