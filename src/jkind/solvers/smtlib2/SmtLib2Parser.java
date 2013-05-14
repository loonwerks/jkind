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
		T__3=10, T__2=11, T__1=12, T__0=13, BOOL=14, INT=15, ID=16, WS=17, ERROR=18;
	public static final String[] tokenNames = {
		"<INVALID>", "'define-fun'", "')'", "'-'", "'ite'", "'('", "'not'", "'='", 
		"'Int'", "'Bool'", "'and'", "'Real'", "'model'", "'/'", "BOOL", "INT", 
		"ID", "WS", "ERROR"
	};
	public static final int
		RULE_model = 0, RULE_defval = 1, RULE_defun = 2, RULE_arg = 3, RULE_type = 4, 
		RULE_body = 5, RULE_fn = 6, RULE_symbol = 7;
	public static final String[] ruleNames = {
		"model", "defval", "defun", "arg", "type", "body", "fn", "symbol"
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
		public List<DefunContext> defun() {
			return getRuleContexts(DefunContext.class);
		}
		public DefvalContext defval(int i) {
			return getRuleContext(DefvalContext.class,i);
		}
		public TerminalNode EOF() { return getToken(SmtLib2Parser.EOF, 0); }
		public DefunContext defun(int i) {
			return getRuleContext(DefunContext.class,i);
		}
		public List<DefvalContext> defval() {
			return getRuleContexts(DefvalContext.class);
		}
		public ModelContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_model; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SmtLib2Listener ) ((SmtLib2Listener)listener).enterModel(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SmtLib2Listener ) ((SmtLib2Listener)listener).exitModel(this);
		}
	}

	public final ModelContext model() throws RecognitionException {
		ModelContext _localctx = new ModelContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_model);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(16); match(5);
			setState(17); match(12);
			setState(22);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==5) {
				{
				setState(20);
				switch ( getInterpreter().adaptivePredict(_input,0,_ctx) ) {
				case 1:
					{
					setState(18); defval();
					}
					break;

				case 2:
					{
					setState(19); defun();
					}
					break;
				}
				}
				setState(24);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(25); match(2);
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

	public static class DefvalContext extends ParserRuleContext {
		public BodyContext body() {
			return getRuleContext(BodyContext.class,0);
		}
		public TerminalNode ID() { return getToken(SmtLib2Parser.ID, 0); }
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public DefvalContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_defval; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SmtLib2Listener ) ((SmtLib2Listener)listener).enterDefval(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SmtLib2Listener ) ((SmtLib2Listener)listener).exitDefval(this);
		}
	}

	public final DefvalContext defval() throws RecognitionException {
		DefvalContext _localctx = new DefvalContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_defval);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(28); match(5);
			setState(29); match(1);
			setState(30); match(ID);
			setState(31); match(5);
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

	public static class DefunContext extends ParserRuleContext {
		public BodyContext body() {
			return getRuleContext(BodyContext.class,0);
		}
		public ArgContext arg() {
			return getRuleContext(ArgContext.class,0);
		}
		public TerminalNode ID() { return getToken(SmtLib2Parser.ID, 0); }
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public DefunContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_defun; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SmtLib2Listener ) ((SmtLib2Listener)listener).enterDefun(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SmtLib2Listener ) ((SmtLib2Listener)listener).exitDefun(this);
		}
	}

	public final DefunContext defun() throws RecognitionException {
		DefunContext _localctx = new DefunContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_defun);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(37); match(5);
			setState(38); match(1);
			setState(39); match(ID);
			setState(40); match(5);
			setState(41); arg();
			setState(42); match(2);
			setState(43); type();
			setState(44); body();
			setState(45); match(2);
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
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SmtLib2Listener ) ((SmtLib2Listener)listener).enterArg(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SmtLib2Listener ) ((SmtLib2Listener)listener).exitArg(this);
		}
	}

	public final ArgContext arg() throws RecognitionException {
		ArgContext _localctx = new ArgContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_arg);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(47); match(5);
			setState(48); match(ID);
			setState(49); type();
			setState(50); match(2);
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
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SmtLib2Listener ) ((SmtLib2Listener)listener).enterType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SmtLib2Listener ) ((SmtLib2Listener)listener).exitType(this);
		}
	}

	public final TypeContext type() throws RecognitionException {
		TypeContext _localctx = new TypeContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_type);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(52);
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
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SmtLib2Listener ) ((SmtLib2Listener)listener).enterSymbolBody(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SmtLib2Listener ) ((SmtLib2Listener)listener).exitSymbolBody(this);
		}
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
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SmtLib2Listener ) ((SmtLib2Listener)listener).enterConsBody(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SmtLib2Listener ) ((SmtLib2Listener)listener).exitConsBody(this);
		}
	}

	public final BodyContext body() throws RecognitionException {
		BodyContext _localctx = new BodyContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_body);
		int _la;
		try {
			setState(65);
			switch (_input.LA(1)) {
			case BOOL:
			case INT:
			case ID:
				_localctx = new SymbolBodyContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(54); symbol();
				}
				break;
			case 5:
				_localctx = new ConsBodyContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(55); match(5);
				setState(56); fn();
				setState(60);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << 5) | (1L << BOOL) | (1L << INT) | (1L << ID))) != 0)) {
					{
					{
					setState(57); body();
					}
					}
					setState(62);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(63); match(2);
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
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SmtLib2Listener ) ((SmtLib2Listener)listener).enterFn(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SmtLib2Listener ) ((SmtLib2Listener)listener).exitFn(this);
		}
	}

	public final FnContext fn() throws RecognitionException {
		FnContext _localctx = new FnContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_fn);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(67);
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
		public TerminalNode INT() { return getToken(SmtLib2Parser.INT, 0); }
		public TerminalNode ID() { return getToken(SmtLib2Parser.ID, 0); }
		public TerminalNode BOOL() { return getToken(SmtLib2Parser.BOOL, 0); }
		public SymbolContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_symbol; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SmtLib2Listener ) ((SmtLib2Listener)listener).enterSymbol(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SmtLib2Listener ) ((SmtLib2Listener)listener).exitSymbol(this);
		}
	}

	public final SymbolContext symbol() throws RecognitionException {
		SymbolContext _localctx = new SymbolContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_symbol);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(69);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << BOOL) | (1L << INT) | (1L << ID))) != 0)) ) {
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
		"\2\3\24J\4\2\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t"+
		"\t\3\2\3\2\3\2\3\2\7\2\27\n\2\f\2\16\2\32\13\2\3\2\3\2\3\2\3\3\3\3\3\3"+
		"\3\3\3\3\3\3\3\3\3\3\3\3\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\5\3"+
		"\5\3\5\3\5\3\5\3\6\3\6\3\7\3\7\3\7\3\7\7\7=\n\7\f\7\16\7@\13\7\3\7\3\7"+
		"\5\7D\n\7\3\b\3\b\3\t\3\t\3\t\2\n\2\4\6\b\n\f\16\20\2\5\4\n\13\r\r\6\5"+
		"\6\b\t\f\f\17\17\3\20\22E\2\22\3\2\2\2\4\36\3\2\2\2\6\'\3\2\2\2\b\61\3"+
		"\2\2\2\n\66\3\2\2\2\fC\3\2\2\2\16E\3\2\2\2\20G\3\2\2\2\22\23\7\7\2\2\23"+
		"\30\7\16\2\2\24\27\5\4\3\2\25\27\5\6\4\2\26\24\3\2\2\2\26\25\3\2\2\2\27"+
		"\32\3\2\2\2\30\26\3\2\2\2\30\31\3\2\2\2\31\33\3\2\2\2\32\30\3\2\2\2\33"+
		"\34\7\4\2\2\34\35\7\1\2\2\35\3\3\2\2\2\36\37\7\7\2\2\37 \7\3\2\2 !\7\22"+
		"\2\2!\"\7\7\2\2\"#\7\4\2\2#$\5\n\6\2$%\5\f\7\2%&\7\4\2\2&\5\3\2\2\2\'"+
		"(\7\7\2\2()\7\3\2\2)*\7\22\2\2*+\7\7\2\2+,\5\b\5\2,-\7\4\2\2-.\5\n\6\2"+
		"./\5\f\7\2/\60\7\4\2\2\60\7\3\2\2\2\61\62\7\7\2\2\62\63\7\22\2\2\63\64"+
		"\5\n\6\2\64\65\7\4\2\2\65\t\3\2\2\2\66\67\t\2\2\2\67\13\3\2\2\28D\5\20"+
		"\t\29:\7\7\2\2:>\5\16\b\2;=\5\f\7\2<;\3\2\2\2=@\3\2\2\2><\3\2\2\2>?\3"+
		"\2\2\2?A\3\2\2\2@>\3\2\2\2AB\7\4\2\2BD\3\2\2\2C8\3\2\2\2C9\3\2\2\2D\r"+
		"\3\2\2\2EF\t\3\2\2F\17\3\2\2\2GH\t\4\2\2H\21\3\2\2\2\6\26\30>C";
	public static final ATN _ATN =
		ATNSimulator.deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
	}
}