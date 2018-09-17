// Generated from MathSat.g4 by ANTLR 4.4
package jkind.solvers.mathsat;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class MathSatParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.4", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__4=1, T__3=2, T__2=3, T__1=4, T__0=5, BOOL=6, INT=7, ID=8, WS=9, ERROR=10;
	public static final String[] tokenNames = {
		"<INVALID>", "'/'", "'('", "')'", "'|'", "'-'", "BOOL", "INT", "ID", "WS", 
		"ERROR"
	};
	public static final int
		RULE_model = 0, RULE_assignment = 1, RULE_unsatAssumptions = 2, RULE_body = 3, 
		RULE_fn = 4, RULE_fnApp = 5, RULE_symbol = 6, RULE_id = 7, RULE_qid = 8;
	public static final String[] ruleNames = {
		"model", "assignment", "unsatAssumptions", "body", "fn", "fnApp", "symbol", 
		"id", "qid"
	};

	@Override
	public String getGrammarFileName() { return "MathSat.g4"; }

	@Override
	public String[] getTokenNames() { return tokenNames; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public MathSatParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class ModelContext extends ParserRuleContext {
		public List<AssignmentContext> assignment() {
			return getRuleContexts(AssignmentContext.class);
		}
		public TerminalNode EOF() { return getToken(MathSatParser.EOF, 0); }
		public AssignmentContext assignment(int i) {
			return getRuleContext(AssignmentContext.class,i);
		}
		public ModelContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_model; }
	}

	public final ModelContext model() throws RecognitionException {
		ModelContext _localctx = new ModelContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_model);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(18); match(T__3);
			setState(22);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__3) {
				{
				{
				setState(19); assignment();
				}
				}
				setState(24);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(25); match(T__2);
			setState(26); match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AssignmentContext extends ParserRuleContext {
		public IdContext id() {
			return getRuleContext(IdContext.class,0);
		}
		public FnAppContext fnApp() {
			return getRuleContext(FnAppContext.class,0);
		}
		public BodyContext body() {
			return getRuleContext(BodyContext.class,0);
		}
		public AssignmentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assignment; }
	}

	public final AssignmentContext assignment() throws RecognitionException {
		AssignmentContext _localctx = new AssignmentContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_assignment);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(28); match(T__3);
			setState(31);
			switch (_input.LA(1)) {
			case T__1:
			case ID:
				{
				setState(29); id();
				}
				break;
			case T__3:
				{
				setState(30); fnApp();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(33); body();
			setState(34); match(T__2);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class UnsatAssumptionsContext extends ParserRuleContext {
		public SymbolContext symbol(int i) {
			return getRuleContext(SymbolContext.class,i);
		}
		public List<SymbolContext> symbol() {
			return getRuleContexts(SymbolContext.class);
		}
		public UnsatAssumptionsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_unsatAssumptions; }
	}

	public final UnsatAssumptionsContext unsatAssumptions() throws RecognitionException {
		UnsatAssumptionsContext _localctx = new UnsatAssumptionsContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_unsatAssumptions);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(36); match(T__3);
			setState(40);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__1) | (1L << BOOL) | (1L << INT) | (1L << ID))) != 0)) {
				{
				{
				setState(37); symbol();
				}
				}
				setState(42);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(43); match(T__2);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BodyContext extends ParserRuleContext {
		public BodyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_body; }
	 
		public BodyContext() { }
		public void copyFrom(BodyContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class ConsBodyContext extends BodyContext {
		public BodyContext body(int i) {
			return getRuleContext(BodyContext.class,i);
		}
		public FnContext fn() {
			return getRuleContext(FnContext.class,0);
		}
		public List<BodyContext> body() {
			return getRuleContexts(BodyContext.class);
		}
		public ConsBodyContext(BodyContext ctx) { copyFrom(ctx); }
	}
	public static class SymbolBodyContext extends BodyContext {
		public SymbolContext symbol() {
			return getRuleContext(SymbolContext.class,0);
		}
		public SymbolBodyContext(BodyContext ctx) { copyFrom(ctx); }
	}

	public final BodyContext body() throws RecognitionException {
		BodyContext _localctx = new BodyContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_body);
		int _la;
		try {
			setState(56);
			switch (_input.LA(1)) {
			case T__1:
			case BOOL:
			case INT:
			case ID:
				_localctx = new SymbolBodyContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(45); symbol();
				}
				break;
			case T__3:
				_localctx = new ConsBodyContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(46); match(T__3);
				setState(47); fn();
				setState(51);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__3) | (1L << T__1) | (1L << BOOL) | (1L << INT) | (1L << ID))) != 0)) {
					{
					{
					setState(48); body();
					}
					}
					setState(53);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(54); match(T__2);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FnContext extends ParserRuleContext {
		public FnContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_fn; }
	}

	public final FnContext fn() throws RecognitionException {
		FnContext _localctx = new FnContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_fn);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(58);
			_la = _input.LA(1);
			if ( !(_la==T__4 || _la==T__0) ) {
			_errHandler.recoverInline(this);
			}
			consume();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FnAppContext extends ParserRuleContext {
		public BodyContext body(int i) {
			return getRuleContext(BodyContext.class,i);
		}
		public IdContext id() {
			return getRuleContext(IdContext.class,0);
		}
		public List<BodyContext> body() {
			return getRuleContexts(BodyContext.class);
		}
		public FnAppContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_fnApp; }
	}

	public final FnAppContext fnApp() throws RecognitionException {
		FnAppContext _localctx = new FnAppContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_fnApp);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(60); match(T__3);
			setState(61); id();
			setState(63); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(62); body();
				}
				}
				setState(65); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__3) | (1L << T__1) | (1L << BOOL) | (1L << INT) | (1L << ID))) != 0) );
			setState(67); match(T__2);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SymbolContext extends ParserRuleContext {
		public TerminalNode BOOL() { return getToken(MathSatParser.BOOL, 0); }
		public IdContext id() {
			return getRuleContext(IdContext.class,0);
		}
		public TerminalNode INT() { return getToken(MathSatParser.INT, 0); }
		public SymbolContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_symbol; }
	}

	public final SymbolContext symbol() throws RecognitionException {
		SymbolContext _localctx = new SymbolContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_symbol);
		try {
			setState(72);
			switch (_input.LA(1)) {
			case T__1:
			case ID:
				enterOuterAlt(_localctx, 1);
				{
				setState(69); id();
				}
				break;
			case BOOL:
				enterOuterAlt(_localctx, 2);
				{
				setState(70); match(BOOL);
				}
				break;
			case INT:
				enterOuterAlt(_localctx, 3);
				{
				setState(71); match(INT);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IdContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(MathSatParser.ID, 0); }
		public QidContext qid() {
			return getRuleContext(QidContext.class,0);
		}
		public IdContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_id; }
	}

	public final IdContext id() throws RecognitionException {
		IdContext _localctx = new IdContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_id);
		try {
			setState(76);
			switch (_input.LA(1)) {
			case T__1:
				enterOuterAlt(_localctx, 1);
				{
				setState(74); qid();
				}
				break;
			case ID:
				enterOuterAlt(_localctx, 2);
				{
				setState(75); match(ID);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class QidContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(MathSatParser.ID, 0); }
		public QidContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_qid; }
	}

	public final QidContext qid() throws RecognitionException {
		QidContext _localctx = new QidContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_qid);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(78); match(T__1);
			setState(79); match(ID);
			setState(80); match(T__1);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3\fU\4\2\t\2\4\3\t"+
		"\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\3\2\3\2\7\2"+
		"\27\n\2\f\2\16\2\32\13\2\3\2\3\2\3\2\3\3\3\3\3\3\5\3\"\n\3\3\3\3\3\3\3"+
		"\3\4\3\4\7\4)\n\4\f\4\16\4,\13\4\3\4\3\4\3\5\3\5\3\5\3\5\7\5\64\n\5\f"+
		"\5\16\5\67\13\5\3\5\3\5\5\5;\n\5\3\6\3\6\3\7\3\7\3\7\6\7B\n\7\r\7\16\7"+
		"C\3\7\3\7\3\b\3\b\3\b\5\bK\n\b\3\t\3\t\5\tO\n\t\3\n\3\n\3\n\3\n\3\n\2"+
		"\2\13\2\4\6\b\n\f\16\20\22\2\3\4\2\3\3\7\7T\2\24\3\2\2\2\4\36\3\2\2\2"+
		"\6&\3\2\2\2\b:\3\2\2\2\n<\3\2\2\2\f>\3\2\2\2\16J\3\2\2\2\20N\3\2\2\2\22"+
		"P\3\2\2\2\24\30\7\4\2\2\25\27\5\4\3\2\26\25\3\2\2\2\27\32\3\2\2\2\30\26"+
		"\3\2\2\2\30\31\3\2\2\2\31\33\3\2\2\2\32\30\3\2\2\2\33\34\7\5\2\2\34\35"+
		"\7\2\2\3\35\3\3\2\2\2\36!\7\4\2\2\37\"\5\20\t\2 \"\5\f\7\2!\37\3\2\2\2"+
		"! \3\2\2\2\"#\3\2\2\2#$\5\b\5\2$%\7\5\2\2%\5\3\2\2\2&*\7\4\2\2\')\5\16"+
		"\b\2(\'\3\2\2\2),\3\2\2\2*(\3\2\2\2*+\3\2\2\2+-\3\2\2\2,*\3\2\2\2-.\7"+
		"\5\2\2.\7\3\2\2\2/;\5\16\b\2\60\61\7\4\2\2\61\65\5\n\6\2\62\64\5\b\5\2"+
		"\63\62\3\2\2\2\64\67\3\2\2\2\65\63\3\2\2\2\65\66\3\2\2\2\668\3\2\2\2\67"+
		"\65\3\2\2\289\7\5\2\29;\3\2\2\2:/\3\2\2\2:\60\3\2\2\2;\t\3\2\2\2<=\t\2"+
		"\2\2=\13\3\2\2\2>?\7\4\2\2?A\5\20\t\2@B\5\b\5\2A@\3\2\2\2BC\3\2\2\2CA"+
		"\3\2\2\2CD\3\2\2\2DE\3\2\2\2EF\7\5\2\2F\r\3\2\2\2GK\5\20\t\2HK\7\b\2\2"+
		"IK\7\t\2\2JG\3\2\2\2JH\3\2\2\2JI\3\2\2\2K\17\3\2\2\2LO\5\22\n\2MO\7\n"+
		"\2\2NL\3\2\2\2NM\3\2\2\2O\21\3\2\2\2PQ\7\6\2\2QR\7\n\2\2RS\7\6\2\2S\23"+
		"\3\2\2\2\n\30!*\65:CJN";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}