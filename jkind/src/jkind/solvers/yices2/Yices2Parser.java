// Generated from Yices2.g4 by ANTLR 4.4
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
	static { RuntimeMetaData.checkVersion("4.4", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__11=1, T__10=2, T__9=3, T__8=4, T__7=5, T__6=6, T__5=7, T__4=8, T__3=9, 
		T__2=10, T__1=11, T__0=12, BOOL=13, INT=14, ID=15, WS=16, ERROR=17;
	public static final String[] tokenNames = {
		"<INVALID>", "'/'", "'default'", "'type'", "'->'", "'int'", "'function'", 
		"'('", "')'", "'-'", "'='", "'bool'", "'real'", "BOOL", "INT", "ID", "WS", 
		"ERROR"
	};
	public static final int
		RULE_model = 0, RULE_alias = 1, RULE_variable = 2, RULE_function = 3, 
		RULE_functionType = 4, RULE_type = 5, RULE_functionValue = 6, RULE_defaultValue = 7, 
		RULE_value = 8, RULE_integer = 9, RULE_quotient = 10, RULE_numeric = 11;
	public static final String[] ruleNames = {
		"model", "alias", "variable", "function", "functionType", "type", "functionValue", 
		"defaultValue", "value", "integer", "quotient", "numeric"
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
		public List<AliasContext> alias() {
			return getRuleContexts(AliasContext.class);
		}
		public TerminalNode EOF() { return getToken(Yices2Parser.EOF, 0); }
		public AliasContext alias(int i) {
			return getRuleContext(AliasContext.class,i);
		}
		public FunctionContext function(int i) {
			return getRuleContext(FunctionContext.class,i);
		}
		public VariableContext variable(int i) {
			return getRuleContext(VariableContext.class,i);
		}
		public List<VariableContext> variable() {
			return getRuleContexts(VariableContext.class);
		}
		public List<FunctionContext> function() {
			return getRuleContexts(FunctionContext.class);
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
			setState(27); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				setState(27);
				switch ( getInterpreter().adaptivePredict(_input,0,_ctx) ) {
				case 1:
					{
					setState(24); alias();
					}
					break;
				case 2:
					{
					setState(25); variable();
					}
					break;
				case 3:
					{
					setState(26); function();
					}
					break;
				}
				}
				setState(29); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==T__5 );
			setState(31); match(EOF);
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
			setState(33); match(T__5);
			setState(34); match(T__2);
			setState(35); match(ID);
			setState(36); match(ID);
			setState(37); match(T__4);
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
		public TerminalNode ID() { return getToken(Yices2Parser.ID, 0); }
		public ValueContext value() {
			return getRuleContext(ValueContext.class,0);
		}
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
			setState(39); match(T__5);
			setState(40); match(T__2);
			setState(41); match(ID);
			setState(42); value();
			setState(43); match(T__4);
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
		public List<FunctionValueContext> functionValue() {
			return getRuleContexts(FunctionValueContext.class);
		}
		public TerminalNode ID() { return getToken(Yices2Parser.ID, 0); }
		public DefaultValueContext defaultValue() {
			return getRuleContext(DefaultValueContext.class,0);
		}
		public FunctionTypeContext functionType() {
			return getRuleContext(FunctionTypeContext.class,0);
		}
		public FunctionValueContext functionValue(int i) {
			return getRuleContext(FunctionValueContext.class,i);
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
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(45); match(T__5);
			setState(46); match(T__6);
			setState(47); match(ID);
			setState(48); functionType();
			setState(52);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,2,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(49); functionValue();
					}
					} 
				}
				setState(54);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,2,_ctx);
			}
			setState(56);
			_la = _input.LA(1);
			if (_la==T__5) {
				{
				setState(55); defaultValue();
				}
			}

			setState(58); match(T__4);
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
			setState(60); match(T__5);
			setState(61); match(T__9);
			setState(62); match(T__5);
			setState(63); match(T__8);
			setState(67);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__7) | (1L << T__1) | (1L << T__0))) != 0)) {
				{
				{
				setState(64); type();
				}
				}
				setState(69);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(70); match(T__4);
			setState(71); match(T__4);
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
			setState(73);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__7) | (1L << T__1) | (1L << T__0))) != 0)) ) {
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
		public TerminalNode ID() { return getToken(Yices2Parser.ID, 0); }
		public List<ValueContext> value() {
			return getRuleContexts(ValueContext.class);
		}
		public ValueContext value(int i) {
			return getRuleContext(ValueContext.class,i);
		}
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
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(75); match(T__5);
			setState(76); match(T__2);
			setState(77); match(T__5);
			setState(78); match(ID);
			setState(80); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(79); value();
				}
				}
				setState(82); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__5) | (1L << BOOL) | (1L << INT))) != 0) );
			setState(84); match(T__4);
			setState(85); value();
			setState(86); match(T__4);
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

	public static class DefaultValueContext extends ParserRuleContext {
		public ValueContext value() {
			return getRuleContext(ValueContext.class,0);
		}
		public DefaultValueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_defaultValue; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Yices2Listener ) ((Yices2Listener)listener).enterDefaultValue(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Yices2Listener ) ((Yices2Listener)listener).exitDefaultValue(this);
		}
	}

	public final DefaultValueContext defaultValue() throws RecognitionException {
		DefaultValueContext _localctx = new DefaultValueContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_defaultValue);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(88); match(T__5);
			setState(89); match(T__10);
			setState(90); value();
			setState(91); match(T__4);
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
		public NumericContext numeric() {
			return getRuleContext(NumericContext.class,0);
		}
		public TerminalNode BOOL() { return getToken(Yices2Parser.BOOL, 0); }
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
		enterRule(_localctx, 16, RULE_value);
		try {
			setState(95);
			switch (_input.LA(1)) {
			case BOOL:
				enterOuterAlt(_localctx, 1);
				{
				setState(93); match(BOOL);
				}
				break;
			case T__5:
			case INT:
				enterOuterAlt(_localctx, 2);
				{
				setState(94); numeric();
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

	public final IntegerContext integer() throws RecognitionException {
		IntegerContext _localctx = new IntegerContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_integer);
		try {
			setState(102);
			switch (_input.LA(1)) {
			case INT:
				_localctx = new PositiveIntegerContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(97); match(INT);
				}
				break;
			case T__5:
				_localctx = new NegativeIntegerContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(98); match(T__5);
				setState(99); match(T__3);
				setState(100); match(INT);
				setState(101); match(T__4);
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
		public List<NumericContext> numeric() {
			return getRuleContexts(NumericContext.class);
		}
		public NumericContext numeric(int i) {
			return getRuleContext(NumericContext.class,i);
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
		enterRule(_localctx, 20, RULE_quotient);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(104); match(T__5);
			setState(105); match(T__11);
			setState(106); numeric();
			setState(107); numeric();
			setState(108); match(T__4);
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
		enterRule(_localctx, 22, RULE_numeric);
		try {
			setState(112);
			switch ( getInterpreter().adaptivePredict(_input,8,_ctx) ) {
			case 1:
				_localctx = new IntegerNumericContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(110); integer();
				}
				break;
			case 2:
				_localctx = new QuotientNumericContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(111); quotient();
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
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3\23u\4\2\t\2\4\3\t"+
		"\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t\13\4"+
		"\f\t\f\4\r\t\r\3\2\3\2\3\2\6\2\36\n\2\r\2\16\2\37\3\2\3\2\3\3\3\3\3\3"+
		"\3\3\3\3\3\3\3\4\3\4\3\4\3\4\3\4\3\4\3\5\3\5\3\5\3\5\3\5\7\5\65\n\5\f"+
		"\5\16\58\13\5\3\5\5\5;\n\5\3\5\3\5\3\6\3\6\3\6\3\6\3\6\7\6D\n\6\f\6\16"+
		"\6G\13\6\3\6\3\6\3\6\3\7\3\7\3\b\3\b\3\b\3\b\3\b\6\bS\n\b\r\b\16\bT\3"+
		"\b\3\b\3\b\3\b\3\t\3\t\3\t\3\t\3\t\3\n\3\n\5\nb\n\n\3\13\3\13\3\13\3\13"+
		"\3\13\5\13i\n\13\3\f\3\f\3\f\3\f\3\f\3\f\3\r\3\r\5\rs\n\r\3\r\2\2\16\2"+
		"\4\6\b\n\f\16\20\22\24\26\30\2\3\4\2\7\7\r\16r\2\35\3\2\2\2\4#\3\2\2\2"+
		"\6)\3\2\2\2\b/\3\2\2\2\n>\3\2\2\2\fK\3\2\2\2\16M\3\2\2\2\20Z\3\2\2\2\22"+
		"a\3\2\2\2\24h\3\2\2\2\26j\3\2\2\2\30r\3\2\2\2\32\36\5\4\3\2\33\36\5\6"+
		"\4\2\34\36\5\b\5\2\35\32\3\2\2\2\35\33\3\2\2\2\35\34\3\2\2\2\36\37\3\2"+
		"\2\2\37\35\3\2\2\2\37 \3\2\2\2 !\3\2\2\2!\"\7\2\2\3\"\3\3\2\2\2#$\7\t"+
		"\2\2$%\7\f\2\2%&\7\21\2\2&\'\7\21\2\2\'(\7\n\2\2(\5\3\2\2\2)*\7\t\2\2"+
		"*+\7\f\2\2+,\7\21\2\2,-\5\22\n\2-.\7\n\2\2.\7\3\2\2\2/\60\7\t\2\2\60\61"+
		"\7\b\2\2\61\62\7\21\2\2\62\66\5\n\6\2\63\65\5\16\b\2\64\63\3\2\2\2\65"+
		"8\3\2\2\2\66\64\3\2\2\2\66\67\3\2\2\2\67:\3\2\2\28\66\3\2\2\29;\5\20\t"+
		"\2:9\3\2\2\2:;\3\2\2\2;<\3\2\2\2<=\7\n\2\2=\t\3\2\2\2>?\7\t\2\2?@\7\5"+
		"\2\2@A\7\t\2\2AE\7\6\2\2BD\5\f\7\2CB\3\2\2\2DG\3\2\2\2EC\3\2\2\2EF\3\2"+
		"\2\2FH\3\2\2\2GE\3\2\2\2HI\7\n\2\2IJ\7\n\2\2J\13\3\2\2\2KL\t\2\2\2L\r"+
		"\3\2\2\2MN\7\t\2\2NO\7\f\2\2OP\7\t\2\2PR\7\21\2\2QS\5\22\n\2RQ\3\2\2\2"+
		"ST\3\2\2\2TR\3\2\2\2TU\3\2\2\2UV\3\2\2\2VW\7\n\2\2WX\5\22\n\2XY\7\n\2"+
		"\2Y\17\3\2\2\2Z[\7\t\2\2[\\\7\4\2\2\\]\5\22\n\2]^\7\n\2\2^\21\3\2\2\2"+
		"_b\7\17\2\2`b\5\30\r\2a_\3\2\2\2a`\3\2\2\2b\23\3\2\2\2ci\7\20\2\2de\7"+
		"\t\2\2ef\7\13\2\2fg\7\20\2\2gi\7\n\2\2hc\3\2\2\2hd\3\2\2\2i\25\3\2\2\2"+
		"jk\7\t\2\2kl\7\3\2\2lm\5\30\r\2mn\5\30\r\2no\7\n\2\2o\27\3\2\2\2ps\5\24"+
		"\13\2qs\5\26\f\2rp\3\2\2\2rq\3\2\2\2s\31\3\2\2\2\13\35\37\66:ETahr";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}