// Generated from Yices2.g4 by ANTLR 4.2
package jkind.solvers.yices2;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class Yices2Parser extends Parser {
	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__10=1, T__9=2, T__8=3, T__7=4, T__6=5, T__5=6, T__4=7, T__3=8, T__2=9, 
		T__1=10, T__0=11, BOOL=12, INT=13, ID=14, WS=15, ERROR=16;
	public static final String[] tokenNames = {
		"<INVALID>", "'->'", "')'", "'function'", "'bool'", "'type'", "'-'", "'('", 
		"'int'", "'/'", "'='", "'real'", "BOOL", "INT", "ID", "WS", "ERROR"
	};
	public static final int
		RULE_model = 0, RULE_alias = 1, RULE_variable = 2, RULE_function = 3, 
		RULE_functionType = 4, RULE_type = 5, RULE_functionValue = 6, RULE_value = 7, 
		RULE_integer = 8, RULE_quotient = 9, RULE_numeric = 10;
	public static final String[] ruleNames = {
		"model", "alias", "variable", "function", "functionType", "type", "functionValue", 
		"value", "integer", "quotient", "numeric"
	};

	@Override
	public String getGrammarFileName() { return "Yices2.g4"; }

	@Override
	public String[] getTokenNames() { return tokenNames; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public Yices2Parser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class ModelContext extends ParserRuleContext {
		public List<FunctionContext> function() {
			return getRuleContexts(FunctionContext.class);
		}
		public TerminalNode EOF() { return getToken(Yices2Parser.EOF, 0); }
		public List<VariableContext> variable() {
			return getRuleContexts(VariableContext.class);
		}
		public FunctionContext function(int i) {
			return getRuleContext(FunctionContext.class,i);
		}
		public VariableContext variable(int i) {
			return getRuleContext(VariableContext.class,i);
		}
		public AliasContext alias(int i) {
			return getRuleContext(AliasContext.class,i);
		}
		public List<AliasContext> alias() {
			return getRuleContexts(AliasContext.class);
		}
		public ModelContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_model; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Yices2Listener ) ((Yices2Listener)listener).enterModel(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Yices2Listener ) ((Yices2Listener)listener).exitModel(this);
		}
	}

	public final ModelContext model() throws RecognitionException {
		ModelContext _localctx = new ModelContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_model);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(25); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				setState(25);
				switch ( getInterpreter().adaptivePredict(_input,0,_ctx) ) {
				case 1:
					{
					setState(22); alias();
					}
					break;

				case 2:
					{
					setState(23); variable();
					}
					break;

				case 3:
					{
					setState(24); function();
					}
					break;
				}
				}
				setState(27); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==7 );
			setState(29); match(EOF);
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

	public static class AliasContext extends ParserRuleContext {
		public List<TerminalNode> ID() { return getTokens(Yices2Parser.ID); }
		public TerminalNode ID(int i) {
			return getToken(Yices2Parser.ID, i);
		}
		public AliasContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_alias; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Yices2Listener ) ((Yices2Listener)listener).enterAlias(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Yices2Listener ) ((Yices2Listener)listener).exitAlias(this);
		}
	}

	public final AliasContext alias() throws RecognitionException {
		AliasContext _localctx = new AliasContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_alias);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(31); match(7);
			setState(32); match(10);
			setState(33); match(ID);
			setState(34); match(ID);
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

	public static class VariableContext extends ParserRuleContext {
		public ValueContext value() {
			return getRuleContext(ValueContext.class,0);
		}
		public TerminalNode ID() { return getToken(Yices2Parser.ID, 0); }
		public VariableContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_variable; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Yices2Listener ) ((Yices2Listener)listener).enterVariable(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Yices2Listener ) ((Yices2Listener)listener).exitVariable(this);
		}
	}

	public final VariableContext variable() throws RecognitionException {
		VariableContext _localctx = new VariableContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_variable);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(37); match(7);
			setState(38); match(10);
			setState(39); match(ID);
			setState(40); value();
			setState(41); match(2);
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

	public static class FunctionContext extends ParserRuleContext {
		public FunctionValueContext functionValue(int i) {
			return getRuleContext(FunctionValueContext.class,i);
		}
		public TerminalNode ID() { return getToken(Yices2Parser.ID, 0); }
		public FunctionTypeContext functionType() {
			return getRuleContext(FunctionTypeContext.class,0);
		}
		public List<FunctionValueContext> functionValue() {
			return getRuleContexts(FunctionValueContext.class);
		}
		public FunctionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_function; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Yices2Listener ) ((Yices2Listener)listener).enterFunction(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Yices2Listener ) ((Yices2Listener)listener).exitFunction(this);
		}
	}

	public final FunctionContext function() throws RecognitionException {
		FunctionContext _localctx = new FunctionContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_function);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(43); match(7);
			setState(44); match(3);
			setState(45); match(ID);
			setState(46); functionType();
			setState(50);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==7) {
				{
				{
				setState(47); functionValue();
				}
				}
				setState(52);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(53); match(2);
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

	public static class FunctionTypeContext extends ParserRuleContext {
		public TypeContext type(int i) {
			return getRuleContext(TypeContext.class,i);
		}
		public List<TypeContext> type() {
			return getRuleContexts(TypeContext.class);
		}
		public FunctionTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_functionType; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Yices2Listener ) ((Yices2Listener)listener).enterFunctionType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Yices2Listener ) ((Yices2Listener)listener).exitFunctionType(this);
		}
	}

	public final FunctionTypeContext functionType() throws RecognitionException {
		FunctionTypeContext _localctx = new FunctionTypeContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_functionType);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(55); match(7);
			setState(56); match(5);
			setState(57); match(7);
			setState(58); match(1);
			setState(62);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << 4) | (1L << 8) | (1L << 11))) != 0)) {
				{
				{
				setState(59); type();
				}
				}
				setState(64);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(65); match(2);
			setState(66); match(2);
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
			if ( listener instanceof Yices2Listener ) ((Yices2Listener)listener).enterType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Yices2Listener ) ((Yices2Listener)listener).exitType(this);
		}
	}

	public final TypeContext type() throws RecognitionException {
		TypeContext _localctx = new TypeContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_type);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(68);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << 4) | (1L << 8) | (1L << 11))) != 0)) ) {
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

	public static class FunctionValueContext extends ParserRuleContext {
		public ValueContext value() {
			return getRuleContext(ValueContext.class,0);
		}
		public IntegerContext integer() {
			return getRuleContext(IntegerContext.class,0);
		}
		public TerminalNode ID() { return getToken(Yices2Parser.ID, 0); }
		public FunctionValueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_functionValue; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Yices2Listener ) ((Yices2Listener)listener).enterFunctionValue(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Yices2Listener ) ((Yices2Listener)listener).exitFunctionValue(this);
		}
	}

	public final FunctionValueContext functionValue() throws RecognitionException {
		FunctionValueContext _localctx = new FunctionValueContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_functionValue);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(70); match(7);
			setState(71); match(10);
			setState(72); match(7);
			setState(73); match(ID);
			setState(74); integer();
			setState(75); match(2);
			setState(76); value();
			setState(77); match(2);
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

	public static class ValueContext extends ParserRuleContext {
		public TerminalNode BOOL() { return getToken(Yices2Parser.BOOL, 0); }
		public NumericContext numeric() {
			return getRuleContext(NumericContext.class,0);
		}
		public ValueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_value; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Yices2Listener ) ((Yices2Listener)listener).enterValue(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Yices2Listener ) ((Yices2Listener)listener).exitValue(this);
		}
	}

	public final ValueContext value() throws RecognitionException {
		ValueContext _localctx = new ValueContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_value);
		try {
			setState(81);
			switch (_input.LA(1)) {
			case BOOL:
				enterOuterAlt(_localctx, 1);
				{
				setState(79); match(BOOL);
				}
				break;
			case 7:
			case INT:
				enterOuterAlt(_localctx, 2);
				{
				setState(80); numeric();
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

	public static class IntegerContext extends ParserRuleContext {
		public IntegerContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_integer; }
	 
		public IntegerContext() { }
		public void copyFrom(IntegerContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class PositiveIntegerContext extends IntegerContext {
		public TerminalNode INT() { return getToken(Yices2Parser.INT, 0); }
		public PositiveIntegerContext(IntegerContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Yices2Listener ) ((Yices2Listener)listener).enterPositiveInteger(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Yices2Listener ) ((Yices2Listener)listener).exitPositiveInteger(this);
		}
	}
	public static class NegativeIntegerContext extends IntegerContext {
		public TerminalNode INT() { return getToken(Yices2Parser.INT, 0); }
		public NegativeIntegerContext(IntegerContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Yices2Listener ) ((Yices2Listener)listener).enterNegativeInteger(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Yices2Listener ) ((Yices2Listener)listener).exitNegativeInteger(this);
		}
	}

	public final IntegerContext integer() throws RecognitionException {
		IntegerContext _localctx = new IntegerContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_integer);
		try {
			setState(88);
			switch (_input.LA(1)) {
			case INT:
				_localctx = new PositiveIntegerContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(83); match(INT);
				}
				break;
			case 7:
				_localctx = new NegativeIntegerContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(84); match(7);
				setState(85); match(6);
				setState(86); match(INT);
				setState(87); match(2);
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

	public static class QuotientContext extends ParserRuleContext {
		public IntegerContext integer(int i) {
			return getRuleContext(IntegerContext.class,i);
		}
		public List<IntegerContext> integer() {
			return getRuleContexts(IntegerContext.class);
		}
		public QuotientContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_quotient; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Yices2Listener ) ((Yices2Listener)listener).enterQuotient(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Yices2Listener ) ((Yices2Listener)listener).exitQuotient(this);
		}
	}

	public final QuotientContext quotient() throws RecognitionException {
		QuotientContext _localctx = new QuotientContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_quotient);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(90); match(7);
			setState(91); match(9);
			setState(92); integer();
			setState(93); integer();
			setState(94); match(2);
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

	public static class NumericContext extends ParserRuleContext {
		public NumericContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_numeric; }
	 
		public NumericContext() { }
		public void copyFrom(NumericContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class IntegerNumericContext extends NumericContext {
		public IntegerContext integer() {
			return getRuleContext(IntegerContext.class,0);
		}
		public IntegerNumericContext(NumericContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Yices2Listener ) ((Yices2Listener)listener).enterIntegerNumeric(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Yices2Listener ) ((Yices2Listener)listener).exitIntegerNumeric(this);
		}
	}
	public static class QuotientNumericContext extends NumericContext {
		public QuotientContext quotient() {
			return getRuleContext(QuotientContext.class,0);
		}
		public QuotientNumericContext(NumericContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Yices2Listener ) ((Yices2Listener)listener).enterQuotientNumeric(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Yices2Listener ) ((Yices2Listener)listener).exitQuotientNumeric(this);
		}
	}

	public final NumericContext numeric() throws RecognitionException {
		NumericContext _localctx = new NumericContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_numeric);
		try {
			setState(98);
			switch ( getInterpreter().adaptivePredict(_input,6,_ctx) ) {
			case 1:
				_localctx = new IntegerNumericContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(96); integer();
				}
				break;

			case 2:
				_localctx = new QuotientNumericContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(97); quotient();
				}
				break;
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
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3\22g\4\2\t\2\4\3\t"+
		"\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t\13\4"+
		"\f\t\f\3\2\3\2\3\2\6\2\34\n\2\r\2\16\2\35\3\2\3\2\3\3\3\3\3\3\3\3\3\3"+
		"\3\3\3\4\3\4\3\4\3\4\3\4\3\4\3\5\3\5\3\5\3\5\3\5\7\5\63\n\5\f\5\16\5\66"+
		"\13\5\3\5\3\5\3\6\3\6\3\6\3\6\3\6\7\6?\n\6\f\6\16\6B\13\6\3\6\3\6\3\6"+
		"\3\7\3\7\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\t\3\t\5\tT\n\t\3\n\3\n"+
		"\3\n\3\n\3\n\5\n[\n\n\3\13\3\13\3\13\3\13\3\13\3\13\3\f\3\f\5\fe\n\f\3"+
		"\f\2\2\r\2\4\6\b\n\f\16\20\22\24\26\2\3\5\2\6\6\n\n\r\rc\2\33\3\2\2\2"+
		"\4!\3\2\2\2\6\'\3\2\2\2\b-\3\2\2\2\n9\3\2\2\2\fF\3\2\2\2\16H\3\2\2\2\20"+
		"S\3\2\2\2\22Z\3\2\2\2\24\\\3\2\2\2\26d\3\2\2\2\30\34\5\4\3\2\31\34\5\6"+
		"\4\2\32\34\5\b\5\2\33\30\3\2\2\2\33\31\3\2\2\2\33\32\3\2\2\2\34\35\3\2"+
		"\2\2\35\33\3\2\2\2\35\36\3\2\2\2\36\37\3\2\2\2\37 \7\2\2\3 \3\3\2\2\2"+
		"!\"\7\t\2\2\"#\7\f\2\2#$\7\20\2\2$%\7\20\2\2%&\7\4\2\2&\5\3\2\2\2\'(\7"+
		"\t\2\2()\7\f\2\2)*\7\20\2\2*+\5\20\t\2+,\7\4\2\2,\7\3\2\2\2-.\7\t\2\2"+
		"./\7\5\2\2/\60\7\20\2\2\60\64\5\n\6\2\61\63\5\16\b\2\62\61\3\2\2\2\63"+
		"\66\3\2\2\2\64\62\3\2\2\2\64\65\3\2\2\2\65\67\3\2\2\2\66\64\3\2\2\2\67"+
		"8\7\4\2\28\t\3\2\2\29:\7\t\2\2:;\7\7\2\2;<\7\t\2\2<@\7\3\2\2=?\5\f\7\2"+
		">=\3\2\2\2?B\3\2\2\2@>\3\2\2\2@A\3\2\2\2AC\3\2\2\2B@\3\2\2\2CD\7\4\2\2"+
		"DE\7\4\2\2E\13\3\2\2\2FG\t\2\2\2G\r\3\2\2\2HI\7\t\2\2IJ\7\f\2\2JK\7\t"+
		"\2\2KL\7\20\2\2LM\5\22\n\2MN\7\4\2\2NO\5\20\t\2OP\7\4\2\2P\17\3\2\2\2"+
		"QT\7\16\2\2RT\5\26\f\2SQ\3\2\2\2SR\3\2\2\2T\21\3\2\2\2U[\7\17\2\2VW\7"+
		"\t\2\2WX\7\b\2\2XY\7\17\2\2Y[\7\4\2\2ZU\3\2\2\2ZV\3\2\2\2[\23\3\2\2\2"+
		"\\]\7\t\2\2]^\7\13\2\2^_\5\22\n\2_`\5\22\n\2`a\7\4\2\2a\25\3\2\2\2be\5"+
		"\22\n\2ce\5\24\13\2db\3\2\2\2dc\3\2\2\2e\27\3\2\2\2\t\33\35\64@SZd";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}