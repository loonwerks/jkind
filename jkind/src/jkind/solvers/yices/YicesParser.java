// Generated from Yices.g4 by ANTLR 4.2
package jkind.solvers.yices;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class YicesParser extends Parser {
	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__13=1, T__12=2, T__11=3, T__10=4, T__9=5, T__8=6, T__7=7, T__6=8, T__5=9, 
		T__4=10, T__3=11, T__2=12, T__1=13, T__0=14, PREDEFINED_OP=15, BOOL=16, 
		INT=17, ID=18, WS=19, ERROR=20;
	public static final String[] tokenNames = {
		"<INVALID>", "'assertion'", "'sat'", "'ids'", "')'", "'-'", "'unknown'", 
		"':'", "'('", "'unsatisfied'", "'core'", "'/'", "'='", "'unsat'", "'cost'", 
		"PREDEFINED_OP", "BOOL", "INT", "ID", "WS", "ERROR"
	};
	public static final int
		RULE_result = 0, RULE_satResult = 1, RULE_unsatResult = 2, RULE_model = 3, 
		RULE_unsatAssertions = 4, RULE_cost = 5, RULE_unsatCore = 6, RULE_alias = 7, 
		RULE_variable = 8, RULE_function = 9, RULE_predefined = 10, RULE_value = 11, 
		RULE_integer = 12, RULE_numeric = 13;
	public static final String[] ruleNames = {
		"result", "satResult", "unsatResult", "model", "unsatAssertions", "cost", 
		"unsatCore", "alias", "variable", "function", "predefined", "value", "integer", 
		"numeric"
	};

	@Override
	public String getGrammarFileName() { return "Yices.g4"; }

	@Override
	public String[] getTokenNames() { return tokenNames; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public YicesParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class ResultContext extends ParserRuleContext {
		public TerminalNode EOF() { return getToken(YicesParser.EOF, 0); }
		public UnsatResultContext unsatResult() {
			return getRuleContext(UnsatResultContext.class,0);
		}
		public SatResultContext satResult() {
			return getRuleContext(SatResultContext.class,0);
		}
		public ResultContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_result; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof YicesListener ) ((YicesListener)listener).enterResult(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof YicesListener ) ((YicesListener)listener).exitResult(this);
		}
	}

	public final ResultContext result() throws RecognitionException {
		ResultContext _localctx = new ResultContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_result);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(30);
			switch (_input.LA(1)) {
			case 2:
			case 6:
				{
				setState(28); satResult();
				}
				break;
			case 13:
				{
				setState(29); unsatResult();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(32); match(EOF);
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

	public static class SatResultContext extends ParserRuleContext {
		public CostContext cost() {
			return getRuleContext(CostContext.class,0);
		}
		public UnsatAssertionsContext unsatAssertions() {
			return getRuleContext(UnsatAssertionsContext.class,0);
		}
		public ModelContext model() {
			return getRuleContext(ModelContext.class,0);
		}
		public SatResultContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_satResult; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof YicesListener ) ((YicesListener)listener).enterSatResult(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof YicesListener ) ((YicesListener)listener).exitSatResult(this);
		}
	}

	public final SatResultContext satResult() throws RecognitionException {
		SatResultContext _localctx = new SatResultContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_satResult);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(34);
			_la = _input.LA(1);
			if ( !(_la==2 || _la==6) ) {
			_errHandler.recoverInline(this);
			}
			consume();
			setState(36);
			_la = _input.LA(1);
			if (_la==9) {
				{
				setState(35); unsatAssertions();
				}
			}

			setState(39);
			_la = _input.LA(1);
			if (_la==8) {
				{
				setState(38); model();
				}
			}

			setState(42);
			_la = _input.LA(1);
			if (_la==14) {
				{
				setState(41); cost();
				}
			}

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

	public static class UnsatResultContext extends ParserRuleContext {
		public UnsatCoreContext unsatCore() {
			return getRuleContext(UnsatCoreContext.class,0);
		}
		public UnsatResultContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_unsatResult; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof YicesListener ) ((YicesListener)listener).enterUnsatResult(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof YicesListener ) ((YicesListener)listener).exitUnsatResult(this);
		}
	}

	public final UnsatResultContext unsatResult() throws RecognitionException {
		UnsatResultContext _localctx = new UnsatResultContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_unsatResult);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(44); match(13);
			setState(46);
			_la = _input.LA(1);
			if (_la==13) {
				{
				setState(45); unsatCore();
				}
			}

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

	public static class ModelContext extends ParserRuleContext {
		public List<FunctionContext> function() {
			return getRuleContexts(FunctionContext.class);
		}
		public PredefinedContext predefined(int i) {
			return getRuleContext(PredefinedContext.class,i);
		}
		public List<PredefinedContext> predefined() {
			return getRuleContexts(PredefinedContext.class);
		}
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
			if ( listener instanceof YicesListener ) ((YicesListener)listener).enterModel(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof YicesListener ) ((YicesListener)listener).exitModel(this);
		}
	}

	public final ModelContext model() throws RecognitionException {
		ModelContext _localctx = new ModelContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_model);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(52); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				setState(52);
				switch ( getInterpreter().adaptivePredict(_input,5,_ctx) ) {
				case 1:
					{
					setState(48); alias();
					}
					break;

				case 2:
					{
					setState(49); variable();
					}
					break;

				case 3:
					{
					setState(50); function();
					}
					break;

				case 4:
					{
					setState(51); predefined();
					}
					break;
				}
				}
				setState(54); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==8 );
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

	public static class UnsatAssertionsContext extends ParserRuleContext {
		public List<TerminalNode> INT() { return getTokens(YicesParser.INT); }
		public TerminalNode INT(int i) {
			return getToken(YicesParser.INT, i);
		}
		public UnsatAssertionsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_unsatAssertions; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof YicesListener ) ((YicesListener)listener).enterUnsatAssertions(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof YicesListener ) ((YicesListener)listener).exitUnsatAssertions(this);
		}
	}

	public final UnsatAssertionsContext unsatAssertions() throws RecognitionException {
		UnsatAssertionsContext _localctx = new UnsatAssertionsContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_unsatAssertions);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(56); match(9);
			setState(57); match(1);
			setState(58); match(3);
			setState(59); match(7);
			setState(61); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(60); match(INT);
				}
				}
				setState(63); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==INT );
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

	public static class CostContext extends ParserRuleContext {
		public TerminalNode INT() { return getToken(YicesParser.INT, 0); }
		public CostContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_cost; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof YicesListener ) ((YicesListener)listener).enterCost(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof YicesListener ) ((YicesListener)listener).exitCost(this);
		}
	}

	public final CostContext cost() throws RecognitionException {
		CostContext _localctx = new CostContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_cost);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(65); match(14);
			setState(66); match(7);
			setState(67); match(INT);
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

	public static class UnsatCoreContext extends ParserRuleContext {
		public List<TerminalNode> INT() { return getTokens(YicesParser.INT); }
		public TerminalNode INT(int i) {
			return getToken(YicesParser.INT, i);
		}
		public UnsatCoreContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_unsatCore; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof YicesListener ) ((YicesListener)listener).enterUnsatCore(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof YicesListener ) ((YicesListener)listener).exitUnsatCore(this);
		}
	}

	public final UnsatCoreContext unsatCore() throws RecognitionException {
		UnsatCoreContext _localctx = new UnsatCoreContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_unsatCore);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(69); match(13);
			setState(70); match(10);
			setState(71); match(3);
			setState(72); match(7);
			setState(74); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(73); match(INT);
				}
				}
				setState(76); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==INT );
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
		public List<TerminalNode> ID() { return getTokens(YicesParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(YicesParser.ID, i);
		}
		public AliasContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_alias; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof YicesListener ) ((YicesListener)listener).enterAlias(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof YicesListener ) ((YicesListener)listener).exitAlias(this);
		}
	}

	public final AliasContext alias() throws RecognitionException {
		AliasContext _localctx = new AliasContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_alias);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(78); match(8);
			setState(79); match(12);
			setState(80); match(ID);
			setState(81); match(ID);
			setState(82); match(4);
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
		public TerminalNode ID() { return getToken(YicesParser.ID, 0); }
		public VariableContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_variable; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof YicesListener ) ((YicesListener)listener).enterVariable(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof YicesListener ) ((YicesListener)listener).exitVariable(this);
		}
	}

	public final VariableContext variable() throws RecognitionException {
		VariableContext _localctx = new VariableContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_variable);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(84); match(8);
			setState(85); match(12);
			setState(86); match(ID);
			setState(87); value();
			setState(88); match(4);
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
		public ValueContext value(int i) {
			return getRuleContext(ValueContext.class,i);
		}
		public List<ValueContext> value() {
			return getRuleContexts(ValueContext.class);
		}
		public TerminalNode ID() { return getToken(YicesParser.ID, 0); }
		public FunctionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_function; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof YicesListener ) ((YicesListener)listener).enterFunction(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof YicesListener ) ((YicesListener)listener).exitFunction(this);
		}
	}

	public final FunctionContext function() throws RecognitionException {
		FunctionContext _localctx = new FunctionContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_function);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(90); match(8);
			setState(91); match(12);
			setState(92); match(8);
			setState(93); match(ID);
			setState(97);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << 5) | (1L << BOOL) | (1L << INT))) != 0)) {
				{
				{
				setState(94); value();
				}
				}
				setState(99);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(100); match(4);
			setState(101); value();
			setState(102); match(4);
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

	public static class PredefinedContext extends ParserRuleContext {
		public ValueContext value(int i) {
			return getRuleContext(ValueContext.class,i);
		}
		public TerminalNode PREDEFINED_OP() { return getToken(YicesParser.PREDEFINED_OP, 0); }
		public List<ValueContext> value() {
			return getRuleContexts(ValueContext.class);
		}
		public PredefinedContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_predefined; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof YicesListener ) ((YicesListener)listener).enterPredefined(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof YicesListener ) ((YicesListener)listener).exitPredefined(this);
		}
	}

	public final PredefinedContext predefined() throws RecognitionException {
		PredefinedContext _localctx = new PredefinedContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_predefined);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(104); match(8);
			setState(105); match(12);
			setState(106); match(8);
			setState(107); match(PREDEFINED_OP);
			setState(111);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << 5) | (1L << BOOL) | (1L << INT))) != 0)) {
				{
				{
				setState(108); value();
				}
				}
				setState(113);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(114); match(4);
			setState(115); value();
			setState(116); match(4);
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
		public TerminalNode BOOL() { return getToken(YicesParser.BOOL, 0); }
		public NumericContext numeric() {
			return getRuleContext(NumericContext.class,0);
		}
		public ValueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_value; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof YicesListener ) ((YicesListener)listener).enterValue(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof YicesListener ) ((YicesListener)listener).exitValue(this);
		}
	}

	public final ValueContext value() throws RecognitionException {
		ValueContext _localctx = new ValueContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_value);
		try {
			setState(120);
			switch (_input.LA(1)) {
			case BOOL:
				enterOuterAlt(_localctx, 1);
				{
				setState(118); match(BOOL);
				}
				break;
			case 5:
			case INT:
				enterOuterAlt(_localctx, 2);
				{
				setState(119); numeric();
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
		public TerminalNode INT() { return getToken(YicesParser.INT, 0); }
		public IntegerContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_integer; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof YicesListener ) ((YicesListener)listener).enterInteger(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof YicesListener ) ((YicesListener)listener).exitInteger(this);
		}
	}

	public final IntegerContext integer() throws RecognitionException {
		IntegerContext _localctx = new IntegerContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_integer);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(123);
			_la = _input.LA(1);
			if (_la==5) {
				{
				setState(122); match(5);
				}
			}

			setState(125); match(INT);
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
		public List<TerminalNode> INT() { return getTokens(YicesParser.INT); }
		public TerminalNode INT(int i) {
			return getToken(YicesParser.INT, i);
		}
		public NumericContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_numeric; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof YicesListener ) ((YicesListener)listener).enterNumeric(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof YicesListener ) ((YicesListener)listener).exitNumeric(this);
		}
	}

	public final NumericContext numeric() throws RecognitionException {
		NumericContext _localctx = new NumericContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_numeric);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(128);
			_la = _input.LA(1);
			if (_la==5) {
				{
				setState(127); match(5);
				}
			}

			setState(130); match(INT);
			setState(133);
			_la = _input.LA(1);
			if (_la==11) {
				{
				setState(131); match(11);
				setState(132); match(INT);
				}
			}

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
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3\26\u008a\4\2\t\2"+
		"\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\3\2\3\2\5\2!\n\2\3\2\3\2\3\3"+
		"\3\3\5\3\'\n\3\3\3\5\3*\n\3\3\3\5\3-\n\3\3\4\3\4\5\4\61\n\4\3\5\3\5\3"+
		"\5\3\5\6\5\67\n\5\r\5\16\58\3\6\3\6\3\6\3\6\3\6\6\6@\n\6\r\6\16\6A\3\7"+
		"\3\7\3\7\3\7\3\b\3\b\3\b\3\b\3\b\6\bM\n\b\r\b\16\bN\3\t\3\t\3\t\3\t\3"+
		"\t\3\t\3\n\3\n\3\n\3\n\3\n\3\n\3\13\3\13\3\13\3\13\3\13\7\13b\n\13\f\13"+
		"\16\13e\13\13\3\13\3\13\3\13\3\13\3\f\3\f\3\f\3\f\3\f\7\fp\n\f\f\f\16"+
		"\fs\13\f\3\f\3\f\3\f\3\f\3\r\3\r\5\r{\n\r\3\16\5\16~\n\16\3\16\3\16\3"+
		"\17\5\17\u0083\n\17\3\17\3\17\3\17\5\17\u0088\n\17\3\17\2\2\20\2\4\6\b"+
		"\n\f\16\20\22\24\26\30\32\34\2\3\4\2\4\4\b\b\u008c\2 \3\2\2\2\4$\3\2\2"+
		"\2\6.\3\2\2\2\b\66\3\2\2\2\n:\3\2\2\2\fC\3\2\2\2\16G\3\2\2\2\20P\3\2\2"+
		"\2\22V\3\2\2\2\24\\\3\2\2\2\26j\3\2\2\2\30z\3\2\2\2\32}\3\2\2\2\34\u0082"+
		"\3\2\2\2\36!\5\4\3\2\37!\5\6\4\2 \36\3\2\2\2 \37\3\2\2\2!\"\3\2\2\2\""+
		"#\7\2\2\3#\3\3\2\2\2$&\t\2\2\2%\'\5\n\6\2&%\3\2\2\2&\'\3\2\2\2\')\3\2"+
		"\2\2(*\5\b\5\2)(\3\2\2\2)*\3\2\2\2*,\3\2\2\2+-\5\f\7\2,+\3\2\2\2,-\3\2"+
		"\2\2-\5\3\2\2\2.\60\7\17\2\2/\61\5\16\b\2\60/\3\2\2\2\60\61\3\2\2\2\61"+
		"\7\3\2\2\2\62\67\5\20\t\2\63\67\5\22\n\2\64\67\5\24\13\2\65\67\5\26\f"+
		"\2\66\62\3\2\2\2\66\63\3\2\2\2\66\64\3\2\2\2\66\65\3\2\2\2\678\3\2\2\2"+
		"8\66\3\2\2\289\3\2\2\29\t\3\2\2\2:;\7\13\2\2;<\7\3\2\2<=\7\5\2\2=?\7\t"+
		"\2\2>@\7\23\2\2?>\3\2\2\2@A\3\2\2\2A?\3\2\2\2AB\3\2\2\2B\13\3\2\2\2CD"+
		"\7\20\2\2DE\7\t\2\2EF\7\23\2\2F\r\3\2\2\2GH\7\17\2\2HI\7\f\2\2IJ\7\5\2"+
		"\2JL\7\t\2\2KM\7\23\2\2LK\3\2\2\2MN\3\2\2\2NL\3\2\2\2NO\3\2\2\2O\17\3"+
		"\2\2\2PQ\7\n\2\2QR\7\16\2\2RS\7\24\2\2ST\7\24\2\2TU\7\6\2\2U\21\3\2\2"+
		"\2VW\7\n\2\2WX\7\16\2\2XY\7\24\2\2YZ\5\30\r\2Z[\7\6\2\2[\23\3\2\2\2\\"+
		"]\7\n\2\2]^\7\16\2\2^_\7\n\2\2_c\7\24\2\2`b\5\30\r\2a`\3\2\2\2be\3\2\2"+
		"\2ca\3\2\2\2cd\3\2\2\2df\3\2\2\2ec\3\2\2\2fg\7\6\2\2gh\5\30\r\2hi\7\6"+
		"\2\2i\25\3\2\2\2jk\7\n\2\2kl\7\16\2\2lm\7\n\2\2mq\7\21\2\2np\5\30\r\2"+
		"on\3\2\2\2ps\3\2\2\2qo\3\2\2\2qr\3\2\2\2rt\3\2\2\2sq\3\2\2\2tu\7\6\2\2"+
		"uv\5\30\r\2vw\7\6\2\2w\27\3\2\2\2x{\7\22\2\2y{\5\34\17\2zx\3\2\2\2zy\3"+
		"\2\2\2{\31\3\2\2\2|~\7\7\2\2}|\3\2\2\2}~\3\2\2\2~\177\3\2\2\2\177\u0080"+
		"\7\23\2\2\u0080\33\3\2\2\2\u0081\u0083\7\7\2\2\u0082\u0081\3\2\2\2\u0082"+
		"\u0083\3\2\2\2\u0083\u0084\3\2\2\2\u0084\u0087\7\23\2\2\u0085\u0086\7"+
		"\r\2\2\u0086\u0088\7\23\2\2\u0087\u0085\3\2\2\2\u0087\u0088\3\2\2\2\u0088"+
		"\35\3\2\2\2\21 &),\60\668ANcqz}\u0082\u0087";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}