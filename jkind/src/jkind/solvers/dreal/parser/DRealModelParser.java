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
		SYMBOL_CHAR=17, SYMBOL_OR_NUM_CHAR=18, QUOTED_SYMBOL=19, STRING=20, WARNING=21, 
		WS=22, COMMENT=23, ERROR=24;
	public static final String[] tokenNames = {
		"<INVALID>", "'true'", "'undef'", "'Bool'", "'[ ENTIRE ]'", "':'", "'['", 
		"','", "']'", "'='", "'false'", "INTEGER", "REAL", "INFRULE", "EXPONENT", 
		"NUMERAL", "SIMPLE_SYMBOL", "SYMBOL_CHAR", "SYMBOL_OR_NUM_CHAR", "QUOTED_SYMBOL", 
		"STRING", "WARNING", "WS", "COMMENT", "ERROR"
	};
	public static final int
		RULE_model = 0, RULE_var_assign = 1, RULE_warning = 2, RULE_var_value = 3, 
		RULE_number_value = 4, RULE_three_val_bool = 5, RULE_symbol = 6;
	public static final String[] ruleNames = {
		"model", "var_assign", "warning", "var_value", "number_value", "three_val_bool", 
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
		public List<WarningContext> warning() {
			return getRuleContexts(WarningContext.class);
		}
		public Var_assignContext var_assign(int i) {
			return getRuleContext(Var_assignContext.class,i);
		}
		public TerminalNode EOF() { return getToken(DRealModelParser.EOF, 0); }
		public WarningContext warning(int i) {
			return getRuleContext(WarningContext.class,i);
		}
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
			setState(18);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << SIMPLE_SYMBOL) | (1L << QUOTED_SYMBOL) | (1L << WARNING))) != 0)) {
				{
				setState(16);
				switch (_input.LA(1)) {
				case SIMPLE_SYMBOL:
				case QUOTED_SYMBOL:
					{
					setState(14); var_assign();
					}
					break;
				case WARNING:
					{
					setState(15); warning();
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				setState(20);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(21); match(EOF);
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
			setState(23); symbol();
			setState(24); match(T__5);
			setState(25); var_value();
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

	public static class WarningContext extends ParserRuleContext {
		public TerminalNode WARNING() { return getToken(DRealModelParser.WARNING, 0); }
		public WarningContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_warning; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DRealModelListener ) ((DRealModelListener)listener).enterWarning(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DRealModelListener ) ((DRealModelListener)listener).exitWarning(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DRealModelVisitor ) return ((DRealModelVisitor<? extends T>)visitor).visitWarning(this);
			else return visitor.visitChildren(this);
		}
	}

	public final WarningContext warning() throws RecognitionException {
		WarningContext _localctx = new WarningContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_warning);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(27); match(WARNING);
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
		enterRule(_localctx, 6, RULE_var_value);
		try {
			setState(40);
			switch (_input.LA(1)) {
			case T__6:
				_localctx = new NumberRangeValContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(29); match(T__6);
				setState(30); match(T__1);
				setState(31); match(T__4);
				setState(32); number_value();
				setState(33); match(T__3);
				setState(34); number_value();
				setState(35); match(T__2);
				}
				break;
			case T__7:
				_localctx = new BoolValContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(37); match(T__7);
				setState(38); match(T__1);
				setState(39); three_val_bool();
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
		enterRule(_localctx, 8, RULE_number_value);
		try {
			setState(45);
			switch (_input.LA(1)) {
			case INFRULE:
				_localctx = new InfinityValContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(42); match(INFRULE);
				}
				break;
			case REAL:
				_localctx = new RealValContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(43); match(REAL);
				}
				break;
			case INTEGER:
				_localctx = new IntegerValContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(44); match(INTEGER);
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
		enterRule(_localctx, 10, RULE_three_val_bool);
		try {
			setState(50);
			switch (_input.LA(1)) {
			case T__9:
				_localctx = new TrueValContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(47); match(T__9);
				}
				break;
			case T__0:
				_localctx = new FalseValContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(48); match(T__0);
				}
				break;
			case T__8:
				_localctx = new UndefValContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(49); match(T__8);
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
		enterRule(_localctx, 12, RULE_symbol);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(52);
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
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3\329\4\2\t\2\4\3\t"+
		"\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\3\2\3\2\7\2\23\n\2\f\2\16\2"+
		"\26\13\2\3\2\3\2\3\3\3\3\3\3\3\3\3\4\3\4\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3"+
		"\5\3\5\3\5\3\5\5\5+\n\5\3\6\3\6\3\6\5\6\60\n\6\3\7\3\7\3\7\5\7\65\n\7"+
		"\3\b\3\b\3\b\2\2\t\2\4\6\b\n\f\16\2\3\4\2\22\22\25\258\2\24\3\2\2\2\4"+
		"\31\3\2\2\2\6\35\3\2\2\2\b*\3\2\2\2\n/\3\2\2\2\f\64\3\2\2\2\16\66\3\2"+
		"\2\2\20\23\5\4\3\2\21\23\5\6\4\2\22\20\3\2\2\2\22\21\3\2\2\2\23\26\3\2"+
		"\2\2\24\22\3\2\2\2\24\25\3\2\2\2\25\27\3\2\2\2\26\24\3\2\2\2\27\30\7\2"+
		"\2\3\30\3\3\2\2\2\31\32\5\16\b\2\32\33\7\7\2\2\33\34\5\b\5\2\34\5\3\2"+
		"\2\2\35\36\7\27\2\2\36\7\3\2\2\2\37 \7\6\2\2 !\7\13\2\2!\"\7\b\2\2\"#"+
		"\5\n\6\2#$\7\t\2\2$%\5\n\6\2%&\7\n\2\2&+\3\2\2\2\'(\7\5\2\2()\7\13\2\2"+
		")+\5\f\7\2*\37\3\2\2\2*\'\3\2\2\2+\t\3\2\2\2,\60\7\17\2\2-\60\7\16\2\2"+
		".\60\7\r\2\2/,\3\2\2\2/-\3\2\2\2/.\3\2\2\2\60\13\3\2\2\2\61\65\7\3\2\2"+
		"\62\65\7\f\2\2\63\65\7\4\2\2\64\61\3\2\2\2\64\62\3\2\2\2\64\63\3\2\2\2"+
		"\65\r\3\2\2\2\66\67\t\2\2\2\67\17\3\2\2\2\7\22\24*/\64";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}