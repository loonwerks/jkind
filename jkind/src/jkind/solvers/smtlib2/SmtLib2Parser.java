// Generated from SmtLib2.g4 by ANTLR 4.0
package jkind.solvers.smtlib2;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class SmtLib2Parser extends Parser {
	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__12=1, T__11=2, T__10=3, T__9=4, T__8=5, T__7=6, T__6=7, T__5=8, T__4=9, 
		T__3=10, T__2=11, T__1=12, T__0=13, BOOL=14, INT=15, REAL=16, ID=17, WS=18, 
		ERROR=19;
	public static final String[] tokenNames = {
		"<INVALID>", "'define-fun'", "')'", "'-'", "'ite'", "'('", "'not'", "'='", 
		"'Int'", "'Bool'", "'and'", "'Real'", "'model'", "'/'", "BOOL", "INT", 
		"REAL", "ID", "WS", "ERROR"
	};
	public static final int
		RULE_model = 0, RULE_define = 1, RULE_arg = 2, RULE_type = 3, RULE_body = 4, 
		RULE_fn = 5, RULE_symbol = 6;
	public static final String[] ruleNames = {
		"model", "define", "arg", "type", "body", "fn", "symbol"
	};

	@Override
	public String getGrammarFileName() { return "SmtLib2.g4"; }

	@Override
	public String[] getTokenNames() { return tokenNames; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public SmtLib2Parser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class ModelContext extends ParserRuleContext {
		public List<DefineContext> define() {
			return getRuleContexts(DefineContext.class);
		}
		public TerminalNode EOF() { return getToken(SmtLib2Parser.EOF, 0); }
		public DefineContext define(int i) {
			return getRuleContext(DefineContext.class,i);
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
			setState(14); match(5);
			setState(15); match(12);
			setState(19);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==5) {
				{
				{
				setState(16); define();
				}
				}
				setState(21);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(22); match(2);
			setState(23); match(EOF);
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

	public static class DefineContext extends ParserRuleContext {
		public ArgContext arg() {
			return getRuleContext(ArgContext.class,0);
		}
		public BodyContext body() {
			return getRuleContext(BodyContext.class,0);
		}
		public TerminalNode ID() { return getToken(SmtLib2Parser.ID, 0); }
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public DefineContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_define; }
	}

	public final DefineContext define() throws RecognitionException {
		DefineContext _localctx = new DefineContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_define);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(25); match(5);
			setState(26); match(1);
			setState(27); match(ID);
			setState(28); match(5);
			setState(30);
			_la = _input.LA(1);
			if (_la==5) {
				{
				setState(29); arg();
				}
			}

			setState(32); match(2);
			setState(33); type();
			setState(34); body();
			setState(35); match(2);
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

	public static class ArgContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(SmtLib2Parser.ID, 0); }
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public ArgContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_arg; }
	}

	public final ArgContext arg() throws RecognitionException {
		ArgContext _localctx = new ArgContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_arg);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(37); match(5);
			setState(38); match(ID);
			setState(39); type();
			setState(40); match(2);
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

	public static class TypeContext extends ParserRuleContext {
		public TypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_type; }
	}

	public final TypeContext type() throws RecognitionException {
		TypeContext _localctx = new TypeContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_type);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(42);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << 8) | (1L << 9) | (1L << 11))) != 0)) ) {
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
	public static class SymbolBodyContext extends BodyContext {
		public SymbolContext symbol() {
			return getRuleContext(SymbolContext.class,0);
		}
		public SymbolBodyContext(BodyContext ctx) { copyFrom(ctx); }
	}
	public static class ConsBodyContext extends BodyContext {
		public List<BodyContext> body() {
			return getRuleContexts(BodyContext.class);
		}
		public FnContext fn() {
			return getRuleContext(FnContext.class,0);
		}
		public BodyContext body(int i) {
			return getRuleContext(BodyContext.class,i);
		}
		public ConsBodyContext(BodyContext ctx) { copyFrom(ctx); }
	}

	public final BodyContext body() throws RecognitionException {
		BodyContext _localctx = new BodyContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_body);
		int _la;
		try {
			setState(55);
			switch (_input.LA(1)) {
			case BOOL:
			case INT:
			case REAL:
			case ID:
				_localctx = new SymbolBodyContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(44); symbol();
				}
				break;
			case 5:
				_localctx = new ConsBodyContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(45); match(5);
				setState(46); fn();
				setState(50);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << 5) | (1L << BOOL) | (1L << INT) | (1L << REAL) | (1L << ID))) != 0)) {
					{
					{
					setState(47); body();
					}
					}
					setState(52);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(53); match(2);
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
		enterRule(_localctx, 10, RULE_fn);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(57);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << 3) | (1L << 4) | (1L << 6) | (1L << 7) | (1L << 10) | (1L << 13))) != 0)) ) {
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

	public static class SymbolContext extends ParserRuleContext {
		public TerminalNode REAL() { return getToken(SmtLib2Parser.REAL, 0); }
		public TerminalNode INT() { return getToken(SmtLib2Parser.INT, 0); }
		public TerminalNode ID() { return getToken(SmtLib2Parser.ID, 0); }
		public TerminalNode BOOL() { return getToken(SmtLib2Parser.BOOL, 0); }
		public SymbolContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_symbol; }
	}

	public final SymbolContext symbol() throws RecognitionException {
		SymbolContext _localctx = new SymbolContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_symbol);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(59);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << BOOL) | (1L << INT) | (1L << REAL) | (1L << ID))) != 0)) ) {
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

	public static final String _serializedATN =
		"\2\3\25@\4\2\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\3\2\3"+
		"\2\3\2\7\2\24\n\2\f\2\16\2\27\13\2\3\2\3\2\3\2\3\3\3\3\3\3\3\3\3\3\5\3"+
		"!\n\3\3\3\3\3\3\3\3\3\3\3\3\4\3\4\3\4\3\4\3\4\3\5\3\5\3\6\3\6\3\6\3\6"+
		"\7\6\63\n\6\f\6\16\6\66\13\6\3\6\3\6\5\6:\n\6\3\7\3\7\3\b\3\b\3\b\2\t"+
		"\2\4\6\b\n\f\16\2\5\4\n\13\r\r\6\5\6\b\t\f\f\17\17\3\20\23<\2\20\3\2\2"+
		"\2\4\33\3\2\2\2\6\'\3\2\2\2\b,\3\2\2\2\n9\3\2\2\2\f;\3\2\2\2\16=\3\2\2"+
		"\2\20\21\7\7\2\2\21\25\7\16\2\2\22\24\5\4\3\2\23\22\3\2\2\2\24\27\3\2"+
		"\2\2\25\23\3\2\2\2\25\26\3\2\2\2\26\30\3\2\2\2\27\25\3\2\2\2\30\31\7\4"+
		"\2\2\31\32\7\1\2\2\32\3\3\2\2\2\33\34\7\7\2\2\34\35\7\3\2\2\35\36\7\23"+
		"\2\2\36 \7\7\2\2\37!\5\6\4\2 \37\3\2\2\2 !\3\2\2\2!\"\3\2\2\2\"#\7\4\2"+
		"\2#$\5\b\5\2$%\5\n\6\2%&\7\4\2\2&\5\3\2\2\2\'(\7\7\2\2()\7\23\2\2)*\5"+
		"\b\5\2*+\7\4\2\2+\7\3\2\2\2,-\t\2\2\2-\t\3\2\2\2.:\5\16\b\2/\60\7\7\2"+
		"\2\60\64\5\f\7\2\61\63\5\n\6\2\62\61\3\2\2\2\63\66\3\2\2\2\64\62\3\2\2"+
		"\2\64\65\3\2\2\2\65\67\3\2\2\2\66\64\3\2\2\2\678\7\4\2\28:\3\2\2\29.\3"+
		"\2\2\29/\3\2\2\2:\13\3\2\2\2;<\t\3\2\2<\r\3\2\2\2=>\t\4\2\2>\17\3\2\2"+
		"\2\6\25 \649";
	public static final ATN _ATN =
		ATNSimulator.deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
	}
}