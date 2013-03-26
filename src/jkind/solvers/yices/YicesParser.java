// Generated from Yices.g4 by ANTLR 4.0
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
		T__9=1, T__8=2, T__7=3, T__6=4, T__5=5, T__4=6, T__3=7, T__2=8, T__1=9, 
		T__0=10, PREDEFINED_OP=11, BOOL=12, INT=13, ID=14, WS=15, ERROR=16;
	public static final String[] tokenNames = {
		"<INVALID>", "'sat'", "'ids'", "')'", "'-'", "':'", "'('", "'core'", "'/'", 
		"'='", "'unsat'", "PREDEFINED_OP", "BOOL", "INT", "ID", "WS", "ERROR"
	};
	public static final int
		RULE_result = 0, RULE_satResult = 1, RULE_unsatResult = 2, RULE_model = 3, 
		RULE_unsatCore = 4, RULE_alias = 5, RULE_variable = 6, RULE_function = 7, 
		RULE_predefined = 8, RULE_value = 9, RULE_integer = 10, RULE_numeric = 11;
	public static final String[] ruleNames = {
		"result", "satResult", "unsatResult", "model", "unsatCore", "alias", "variable", 
		"function", "predefined", "value", "integer", "numeric"
	};

	@Override
	public String getGrammarFileName() { return "Yices.g4"; }

	@Override
	public String[] getTokenNames() { return tokenNames; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

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
			setState(26);
			switch (_input.LA(1)) {
			case 1:
				{
				setState(24); satResult();
				}
				break;
			case 10:
				{
				setState(25); unsatResult();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(28); match(EOF);
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
			setState(30); match(1);
			setState(32);
			_la = _input.LA(1);
			if (_la==6) {
				{
				setState(31); model();
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
			setState(34); match(10);
			setState(36);
			_la = _input.LA(1);
			if (_la==10) {
				{
				setState(35); unsatCore();
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
		public PredefinedContext predefined(int i) {
			return getRuleContext(PredefinedContext.class,i);
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
		public FunctionContext function(int i) {
			return getRuleContext(FunctionContext.class,i);
		}
		public List<PredefinedContext> predefined() {
			return getRuleContexts(PredefinedContext.class);
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
			setState(42); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				setState(42);
				switch ( getInterpreter().adaptivePredict(_input,3,_ctx) ) {
				case 1:
					{
					setState(38); alias();
					}
					break;

				case 2:
					{
					setState(39); variable();
					}
					break;

				case 3:
					{
					setState(40); function();
					}
					break;

				case 4:
					{
					setState(41); predefined();
					}
					break;
				}
				}
				setState(44); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==6 );
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
		enterRule(_localctx, 8, RULE_unsatCore);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(46); match(10);
			setState(47); match(7);
			setState(48); match(2);
			setState(49); match(5);
			setState(51); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(50); match(INT);
				}
				}
				setState(53); 
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
		public TerminalNode ID(int i) {
			return getToken(YicesParser.ID, i);
		}
		public List<TerminalNode> ID() { return getTokens(YicesParser.ID); }
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
		enterRule(_localctx, 10, RULE_alias);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(55); match(6);
			setState(56); match(9);
			setState(57); match(ID);
			setState(58); match(ID);
			setState(59); match(3);
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
		enterRule(_localctx, 12, RULE_variable);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(61); match(6);
			setState(62); match(9);
			setState(63); match(ID);
			setState(64); value();
			setState(65); match(3);
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
		public IntegerContext integer() {
			return getRuleContext(IntegerContext.class,0);
		}
		public ValueContext value() {
			return getRuleContext(ValueContext.class,0);
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
		enterRule(_localctx, 14, RULE_function);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(67); match(6);
			setState(68); match(9);
			setState(69); match(6);
			setState(70); match(ID);
			setState(71); integer();
			setState(72); match(3);
			setState(73); value();
			setState(74); match(3);
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
		public List<IntegerContext> integer() {
			return getRuleContexts(IntegerContext.class);
		}
		public IntegerContext integer(int i) {
			return getRuleContext(IntegerContext.class,i);
		}
		public TerminalNode PREDEFINED_OP() { return getToken(YicesParser.PREDEFINED_OP, 0); }
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
		enterRule(_localctx, 16, RULE_predefined);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(76); match(6);
			setState(77); match(9);
			setState(78); match(6);
			setState(79); match(PREDEFINED_OP);
			setState(80); integer();
			setState(81); integer();
			setState(82); match(3);
			setState(83); integer();
			setState(84); match(3);
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
		public TerminalNode BOOL() { return getToken(YicesParser.BOOL, 0); }
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
		enterRule(_localctx, 18, RULE_value);
		try {
			setState(88);
			switch (_input.LA(1)) {
			case BOOL:
				enterOuterAlt(_localctx, 1);
				{
				setState(86); match(BOOL);
				}
				break;
			case 4:
			case INT:
				enterOuterAlt(_localctx, 2);
				{
				setState(87); numeric();
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
		enterRule(_localctx, 20, RULE_integer);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(91);
			_la = _input.LA(1);
			if (_la==4) {
				{
				setState(90); match(4);
				}
			}

			setState(93); match(INT);
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
		enterRule(_localctx, 22, RULE_numeric);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(96);
			_la = _input.LA(1);
			if (_la==4) {
				{
				setState(95); match(4);
				}
			}

			setState(98); match(INT);
			setState(101);
			_la = _input.LA(1);
			if (_la==8) {
				{
				setState(99); match(8);
				setState(100); match(INT);
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
		"\2\3\22j\4\2\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t"+
		"\t\4\n\t\n\4\13\t\13\4\f\t\f\4\r\t\r\3\2\3\2\5\2\35\n\2\3\2\3\2\3\3\3"+
		"\3\5\3#\n\3\3\4\3\4\5\4\'\n\4\3\5\3\5\3\5\3\5\6\5-\n\5\r\5\16\5.\3\6\3"+
		"\6\3\6\3\6\3\6\6\6\66\n\6\r\6\16\6\67\3\7\3\7\3\7\3\7\3\7\3\7\3\b\3\b"+
		"\3\b\3\b\3\b\3\b\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\n\3\n\3\n\3\n\3"+
		"\n\3\n\3\n\3\n\3\n\3\n\3\13\3\13\5\13[\n\13\3\f\5\f^\n\f\3\f\3\f\3\r\5"+
		"\rc\n\r\3\r\3\r\3\r\5\rh\n\r\3\r\2\16\2\4\6\b\n\f\16\20\22\24\26\30\2"+
		"\2i\2\34\3\2\2\2\4 \3\2\2\2\6$\3\2\2\2\b,\3\2\2\2\n\60\3\2\2\2\f9\3\2"+
		"\2\2\16?\3\2\2\2\20E\3\2\2\2\22N\3\2\2\2\24Z\3\2\2\2\26]\3\2\2\2\30b\3"+
		"\2\2\2\32\35\5\4\3\2\33\35\5\6\4\2\34\32\3\2\2\2\34\33\3\2\2\2\35\36\3"+
		"\2\2\2\36\37\7\1\2\2\37\3\3\2\2\2 \"\7\3\2\2!#\5\b\5\2\"!\3\2\2\2\"#\3"+
		"\2\2\2#\5\3\2\2\2$&\7\f\2\2%\'\5\n\6\2&%\3\2\2\2&\'\3\2\2\2\'\7\3\2\2"+
		"\2(-\5\f\7\2)-\5\16\b\2*-\5\20\t\2+-\5\22\n\2,(\3\2\2\2,)\3\2\2\2,*\3"+
		"\2\2\2,+\3\2\2\2-.\3\2\2\2.,\3\2\2\2./\3\2\2\2/\t\3\2\2\2\60\61\7\f\2"+
		"\2\61\62\7\t\2\2\62\63\7\4\2\2\63\65\7\7\2\2\64\66\7\17\2\2\65\64\3\2"+
		"\2\2\66\67\3\2\2\2\67\65\3\2\2\2\678\3\2\2\28\13\3\2\2\29:\7\b\2\2:;\7"+
		"\13\2\2;<\7\20\2\2<=\7\20\2\2=>\7\5\2\2>\r\3\2\2\2?@\7\b\2\2@A\7\13\2"+
		"\2AB\7\20\2\2BC\5\24\13\2CD\7\5\2\2D\17\3\2\2\2EF\7\b\2\2FG\7\13\2\2G"+
		"H\7\b\2\2HI\7\20\2\2IJ\5\26\f\2JK\7\5\2\2KL\5\24\13\2LM\7\5\2\2M\21\3"+
		"\2\2\2NO\7\b\2\2OP\7\13\2\2PQ\7\b\2\2QR\7\r\2\2RS\5\26\f\2ST\5\26\f\2"+
		"TU\7\5\2\2UV\5\26\f\2VW\7\5\2\2W\23\3\2\2\2X[\7\16\2\2Y[\5\30\r\2ZX\3"+
		"\2\2\2ZY\3\2\2\2[\25\3\2\2\2\\^\7\6\2\2]\\\3\2\2\2]^\3\2\2\2^_\3\2\2\2"+
		"_`\7\17\2\2`\27\3\2\2\2ac\7\6\2\2ba\3\2\2\2bc\3\2\2\2cd\3\2\2\2dg\7\17"+
		"\2\2ef\7\n\2\2fh\7\17\2\2ge\3\2\2\2gh\3\2\2\2h\31\3\2\2\2\f\34\"&,.\67"+
		"Z]bg";
	public static final ATN _ATN =
		ATNSimulator.deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
	}
}