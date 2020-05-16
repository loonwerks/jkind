// Generated from MathSat.g4 by ANTLR 4.4
package jkind.solvers.mathsat;

import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({ "all", "warnings", "unchecked", "unused", "cast" })
public class MathSatLexer extends Lexer {
	static {
		RuntimeMetaData.checkVersion("4.4", RuntimeMetaData.VERSION);
	}

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache = new PredictionContextCache();
	public static final int T__4 = 1, T__3 = 2, T__2 = 3, T__1 = 4, T__0 = 5, BOOL = 6, INT = 7, ID = 8, WS = 9,
			ERROR = 10;
	public static String[] modeNames = { "DEFAULT_MODE" };

	public static final String[] tokenNames = { "'\\u0000'", "'\\u0001'", "'\\u0002'", "'\\u0003'", "'\\u0004'",
			"'\\u0005'", "'\\u0006'", "'\\u0007'", "'\b'", "'\t'", "'\n'" };
	public static final String[] ruleNames = { "T__4", "T__3", "T__2", "T__1", "T__0", "BOOL", "DIGIT", "SYMBOL", "INT",
			"ID", "WS", "ERROR" };

	public MathSatLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this, _ATN, _decisionToDFA, _sharedContextCache);
	}

	@Override
	public String getGrammarFileName() {
		return "MathSat.g4";
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

	public static final String _serializedATN = "\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2\fJ\b\1\4\2\t\2\4"
			+ "\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"
			+ "\13\4\f\t\f\4\r\t\r\3\2\3\2\3\3\3\3\3\4\3\4\3\5\3\5\3\6\3\6\3\7\3\7\3"
			+ "\7\3\7\3\7\3\7\3\7\3\7\3\7\5\7/\n\7\3\b\3\b\3\t\3\t\3\n\6\n\66\n\n\r\n"
			+ "\16\n\67\3\13\3\13\3\13\7\13=\n\13\f\13\16\13@\13\13\3\f\6\fC\n\f\r\f"
			+ "\16\fD\3\f\3\f\3\r\3\r\2\2\16\3\3\5\4\7\5\t\6\13\7\r\b\17\2\21\2\23\t"
			+ "\25\n\27\13\31\f\3\2\5\3\2\62;\b\2##%\'\60\60Bac|\u0080\u0080\5\2\13\f"
			+ "\16\17\"\"L\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2"
			+ "\2\2\r\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\3\33"
			+ "\3\2\2\2\5\35\3\2\2\2\7\37\3\2\2\2\t!\3\2\2\2\13#\3\2\2\2\r.\3\2\2\2\17"
			+ "\60\3\2\2\2\21\62\3\2\2\2\23\65\3\2\2\2\259\3\2\2\2\27B\3\2\2\2\31H\3"
			+ "\2\2\2\33\34\7\61\2\2\34\4\3\2\2\2\35\36\7*\2\2\36\6\3\2\2\2\37 \7+\2"
			+ "\2 \b\3\2\2\2!\"\7~\2\2\"\n\3\2\2\2#$\7/\2\2$\f\3\2\2\2%&\7v\2\2&\'\7"
			+ "t\2\2\'(\7w\2\2(/\7g\2\2)*\7h\2\2*+\7c\2\2+,\7n\2\2,-\7u\2\2-/\7g\2\2"
			+ ".%\3\2\2\2.)\3\2\2\2/\16\3\2\2\2\60\61\t\2\2\2\61\20\3\2\2\2\62\63\t\3"
			+ "\2\2\63\22\3\2\2\2\64\66\5\17\b\2\65\64\3\2\2\2\66\67\3\2\2\2\67\65\3"
			+ "\2\2\2\678\3\2\2\28\24\3\2\2\29>\5\21\t\2:=\5\21\t\2;=\5\17\b\2<:\3\2"
			+ "\2\2<;\3\2\2\2=@\3\2\2\2><\3\2\2\2>?\3\2\2\2?\26\3\2\2\2@>\3\2\2\2AC\t"
			+ "\4\2\2BA\3\2\2\2CD\3\2\2\2DB\3\2\2\2DE\3\2\2\2EF\3\2\2\2FG\b\f\2\2G\30"
			+ "\3\2\2\2HI\13\2\2\2I\32\3\2\2\2\b\2.\67<>D\3\b\2\2";
	public static final ATN _ATN = new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}