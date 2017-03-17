// Generated from DRealModel.g4 by ANTLR 4.4
package jkind.solvers.dreal.parser;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class DRealModelParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.4", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__9=1, T__8=2, T__7=3, T__6=4, T__5=5, T__4=6, T__3=7, T__2=8, T__1=9, 
		T__0=10, INTEGER=11, REAL=12, INFRULE=13, EXPONENT=14, NUMERAL=15, SIMPLE_SYMBOL=16, 
		SYMBOL_CHAR=17, SYMBOL_OR_NUM_CHAR=18, QUOTED_SYMBOL=19, STRING=20, WS=21, 
		COMMENT=22, ERROR=23;
	public static final String[] tokenNames = {
		"<INVALID>", "'true'", "'undef'", "'Bool'", "'[ ENTIRE ]'", "':'", "'['", 
		"','", "']'", "'='", "'false'", "INTEGER", "REAL", "INFRULE", "EXPONENT", 
		"NUMERAL", "SIMPLE_SYMBOL", "SYMBOL_CHAR", "SYMBOL_OR_NUM_CHAR", "QUOTED_SYMBOL", 
		"STRING", "WS", "COMMENT", "ERROR"
	};
	public static final int
		RULE_model = 0, RULE_var_assign = 1, RULE_var_value = 2, RULE_number_value = 3, 
		RULE_three_val_bool = 4, RULE_symbol = 5;
	public static final String[] ruleNames = {
		"model", "var_assign", "var_value", "number_value", "three_val_bool", 
		"symbol"
	};

	@Override
	public String getGrammarFileName() { return "DRealModel.g4"; }

	@Override
	public String[] getTokenNames() { return tokenNames; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public DRealModelParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class ModelContext extends ParserRuleContext {
		public Var_assignContext var_assign(int i) {
			return getRuleContext(Var_assignContext.class,i);
		}
		public TerminalNode EOF() { return getToken(DRealModelParser.EOF, 0); }
		public List<Var_assignContext> var_assign() {
			return getRuleContexts(Var_assignContext.class);
		}
		public ModelContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_model; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DRealModelListener ) ((DRealModelListener)listener).enterModel(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DRealModelListener ) ((DRealModelListener)listener).exitModel(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DRealModelVisitor ) return ((DRealModelVisitor<? extends T>)visitor).visitModel(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ModelContext model() throws RecognitionException {
		ModelContext _localctx = new ModelContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_model);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(15);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==SIMPLE_SYMBOL || _la==QUOTED_SYMBOL) {
				{
				{
				setState(12); var_assign();
				}
				}
				setState(17);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
			setState(18); match(EOF);
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

	public static class Var_assignContext extends ParserRuleContext {
		public SymbolContext symbol() {
			return getRuleContext(SymbolContext.class,0);
		}
		public Var_valueContext var_value() {
			return getRuleContext(Var_valueContext.class,0);
		}
		public Var_assignContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_var_assign; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DRealModelListener ) ((DRealModelListener)listener).enterVar_assign(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DRealModelListener ) ((DRealModelListener)listener).exitVar_assign(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DRealModelVisitor ) return ((DRealModelVisitor<? extends T>)visitor).visitVar_assign(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Var_assignContext var_assign() throws RecognitionException {
		Var_assignContext _localctx = new Var_assignContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_var_assign);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(20); symbol();
			setState(21); match(T__5);
			setState(22); var_value();
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

	public static class Var_valueContext extends ParserRuleContext {
		public Var_valueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_var_value; }
	 
		public Var_valueContext() { }
		public void copyFrom(Var_valueContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class NumberRangeValContext extends Var_valueContext {
		public Number_valueContext number_value(int i) {
			return getRuleContext(Number_valueContext.class,i);
		}
		public List<Number_valueContext> number_value() {
			return getRuleContexts(Number_valueContext.class);
		}
		public NumberRangeValContext(Var_valueContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DRealModelListener ) ((DRealModelListener)listener).enterNumberRangeVal(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DRealModelListener ) ((DRealModelListener)listener).exitNumberRangeVal(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DRealModelVisitor ) return ((DRealModelVisitor<? extends T>)visitor).visitNumberRangeVal(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class BoolValContext extends Var_valueContext {
		public Three_val_boolContext three_val_bool() {
			return getRuleContext(Three_val_boolContext.class,0);
		}
		public BoolValContext(Var_valueContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DRealModelListener ) ((DRealModelListener)listener).enterBoolVal(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DRealModelListener ) ((DRealModelListener)listener).exitBoolVal(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DRealModelVisitor ) return ((DRealModelVisitor<? extends T>)visitor).visitBoolVal(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Var_valueContext var_value() throws RecognitionException {
		Var_valueContext _localctx = new Var_valueContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_var_value);
		try {
			setState(35);
			switch (_input.LA(1)) {
			case T__6:
				_localctx = new NumberRangeValContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(24); match(T__6);
				setState(25); match(T__1);
				setState(26); match(T__4);
				setState(27); number_value();
				setState(28); match(T__3);
				setState(29); number_value();
				setState(30); match(T__2);
				}
				break;
			case T__7:
				_localctx = new BoolValContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(32); match(T__7);
				setState(33); match(T__1);
				setState(34); three_val_bool();
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

	public static class Number_valueContext extends ParserRuleContext {
		public Number_valueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_number_value; }
	 
		public Number_valueContext() { }
		public void copyFrom(Number_valueContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class InfinityValContext extends Number_valueContext {
		public TerminalNode INFRULE() { return getToken(DRealModelParser.INFRULE, 0); }
		public InfinityValContext(Number_valueContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DRealModelListener ) ((DRealModelListener)listener).enterInfinityVal(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DRealModelListener ) ((DRealModelListener)listener).exitInfinityVal(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DRealModelVisitor ) return ((DRealModelVisitor<? extends T>)visitor).visitInfinityVal(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class RealValContext extends Number_valueContext {
		public TerminalNode REAL() { return getToken(DRealModelParser.REAL, 0); }
		public RealValContext(Number_valueContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DRealModelListener ) ((DRealModelListener)listener).enterRealVal(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DRealModelListener ) ((DRealModelListener)listener).exitRealVal(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DRealModelVisitor ) return ((DRealModelVisitor<? extends T>)visitor).visitRealVal(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class IntegerValContext extends Number_valueContext {
		public TerminalNode INTEGER() { return getToken(DRealModelParser.INTEGER, 0); }
		public IntegerValContext(Number_valueContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DRealModelListener ) ((DRealModelListener)listener).enterIntegerVal(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DRealModelListener ) ((DRealModelListener)listener).exitIntegerVal(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DRealModelVisitor ) return ((DRealModelVisitor<? extends T>)visitor).visitIntegerVal(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Number_valueContext number_value() throws RecognitionException {
		Number_valueContext _localctx = new Number_valueContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_number_value);
		try {
			setState(40);
			switch (_input.LA(1)) {
			case INFRULE:
				_localctx = new InfinityValContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(37); match(INFRULE);
				}
				break;
			case REAL:
				_localctx = new RealValContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(38); match(REAL);
				}
				break;
			case INTEGER:
				_localctx = new IntegerValContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(39); match(INTEGER);
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

	public static class Three_val_boolContext extends ParserRuleContext {
		public Three_val_boolContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_three_val_bool; }
	 
		public Three_val_boolContext() { }
		public void copyFrom(Three_val_boolContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class TrueValContext extends Three_val_boolContext {
		public TrueValContext(Three_val_boolContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DRealModelListener ) ((DRealModelListener)listener).enterTrueVal(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DRealModelListener ) ((DRealModelListener)listener).exitTrueVal(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DRealModelVisitor ) return ((DRealModelVisitor<? extends T>)visitor).visitTrueVal(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class FalseValContext extends Three_val_boolContext {
		public FalseValContext(Three_val_boolContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DRealModelListener ) ((DRealModelListener)listener).enterFalseVal(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DRealModelListener ) ((DRealModelListener)listener).exitFalseVal(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DRealModelVisitor ) return ((DRealModelVisitor<? extends T>)visitor).visitFalseVal(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class UndefValContext extends Three_val_boolContext {
		public UndefValContext(Three_val_boolContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DRealModelListener ) ((DRealModelListener)listener).enterUndefVal(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DRealModelListener ) ((DRealModelListener)listener).exitUndefVal(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DRealModelVisitor ) return ((DRealModelVisitor<? extends T>)visitor).visitUndefVal(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Three_val_boolContext three_val_bool() throws RecognitionException {
		Three_val_boolContext _localctx = new Three_val_boolContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_three_val_bool);
		try {
			setState(45);
			switch (_input.LA(1)) {
			case T__9:
				_localctx = new TrueValContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(42); match(T__9);
				}
				break;
			case T__0:
				_localctx = new FalseValContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(43); match(T__0);
				}
				break;
			case T__8:
				_localctx = new UndefValContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(44); match(T__8);
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

	public static class SymbolContext extends ParserRuleContext {
		public TerminalNode SIMPLE_SYMBOL() { return getToken(DRealModelParser.SIMPLE_SYMBOL, 0); }
		public TerminalNode QUOTED_SYMBOL() { return getToken(DRealModelParser.QUOTED_SYMBOL, 0); }
		public SymbolContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_symbol; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DRealModelListener ) ((DRealModelListener)listener).enterSymbol(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DRealModelListener ) ((DRealModelListener)listener).exitSymbol(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DRealModelVisitor ) return ((DRealModelVisitor<? extends T>)visitor).visitSymbol(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SymbolContext symbol() throws RecognitionException {
		SymbolContext _localctx = new SymbolContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_symbol);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(47);
			_la = _input.LA(1);
			if ( !(_la==SIMPLE_SYMBOL || _la==QUOTED_SYMBOL) ) {
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
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3\31\64\4\2\t\2\4\3"+
		"\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\3\2\7\2\20\n\2\f\2\16\2\23\13\2\3"+
		"\2\3\2\3\3\3\3\3\3\3\3\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\5\4"+
		"&\n\4\3\5\3\5\3\5\5\5+\n\5\3\6\3\6\3\6\5\6\60\n\6\3\7\3\7\3\7\2\2\b\2"+
		"\4\6\b\n\f\2\3\4\2\22\22\25\25\63\2\21\3\2\2\2\4\26\3\2\2\2\6%\3\2\2\2"+
		"\b*\3\2\2\2\n/\3\2\2\2\f\61\3\2\2\2\16\20\5\4\3\2\17\16\3\2\2\2\20\23"+
		"\3\2\2\2\21\17\3\2\2\2\21\22\3\2\2\2\22\24\3\2\2\2\23\21\3\2\2\2\24\25"+
		"\7\2\2\3\25\3\3\2\2\2\26\27\5\f\7\2\27\30\7\7\2\2\30\31\5\6\4\2\31\5\3"+
		"\2\2\2\32\33\7\6\2\2\33\34\7\13\2\2\34\35\7\b\2\2\35\36\5\b\5\2\36\37"+
		"\7\t\2\2\37 \5\b\5\2 !\7\n\2\2!&\3\2\2\2\"#\7\5\2\2#$\7\13\2\2$&\5\n\6"+
		"\2%\32\3\2\2\2%\"\3\2\2\2&\7\3\2\2\2\'+\7\17\2\2(+\7\16\2\2)+\7\r\2\2"+
		"*\'\3\2\2\2*(\3\2\2\2*)\3\2\2\2+\t\3\2\2\2,\60\7\3\2\2-\60\7\f\2\2.\60"+
		"\7\4\2\2/,\3\2\2\2/-\3\2\2\2/.\3\2\2\2\60\13\3\2\2\2\61\62\t\2\2\2\62"+
		"\r\3\2\2\2\6\21%*/";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}