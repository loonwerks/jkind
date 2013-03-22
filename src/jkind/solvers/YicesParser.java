// Generated from Yices.g4 by ANTLR 4.0
package jkind.solvers;
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
		T__4=1, T__3=2, T__2=3, T__1=4, T__0=5, RESULT=6, PREDEFINED_OP=7, BOOL=8, 
		INT=9, ID=10, WS=11, ERROR=12;
	public static final String[] tokenNames = {
		"<INVALID>", "')'", "'-'", "'('", "'/'", "'='", "RESULT", "PREDEFINED_OP", 
		"BOOL", "INT", "ID", "WS", "ERROR"
	};
	public static final int
		RULE_solverResult = 0, RULE_model = 1, RULE_alias = 2, RULE_variable = 3, 
		RULE_function = 4, RULE_predefined = 5, RULE_value = 6, RULE_integer = 7, 
		RULE_numeric = 8;
	public static final String[] ruleNames = {
		"solverResult", "model", "alias", "variable", "function", "predefined", 
		"value", "integer", "numeric"
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
	public static class SolverResultContext extends ParserRuleContext {
		public ModelContext model() {
			return getRuleContext(ModelContext.class,0);
		}
		public TerminalNode RESULT() { return getToken(YicesParser.RESULT, 0); }
		public TerminalNode EOF() { return getToken(YicesParser.EOF, 0); }
		public SolverResultContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_solverResult; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof YicesListener ) ((YicesListener)listener).enterSolverResult(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof YicesListener ) ((YicesListener)listener).exitSolverResult(this);
		}
	}

	public final SolverResultContext solverResult() throws RecognitionException {
		SolverResultContext _localctx = new SolverResultContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_solverResult);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(18); match(RESULT);
			setState(20);
			_la = _input.LA(1);
			if (_la==3) {
				{
				setState(19); model();
				}
			}

			setState(22); match(EOF);
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
		enterRule(_localctx, 2, RULE_model);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(28); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				setState(28);
				switch ( getInterpreter().adaptivePredict(_input,1,_ctx) ) {
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

				case 4:
					{
					setState(27); predefined();
					}
					break;
				}
				}
				setState(30); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==3 );
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
		enterRule(_localctx, 4, RULE_alias);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(32); match(3);
			setState(33); match(5);
			setState(34); match(ID);
			setState(35); match(ID);
			setState(36); match(1);
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
		enterRule(_localctx, 6, RULE_variable);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(38); match(3);
			setState(39); match(5);
			setState(40); match(ID);
			setState(41); value();
			setState(42); match(1);
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
		enterRule(_localctx, 8, RULE_function);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(44); match(3);
			setState(45); match(5);
			setState(46); match(3);
			setState(47); match(ID);
			setState(48); integer();
			setState(49); match(1);
			setState(50); value();
			setState(51); match(1);
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
		enterRule(_localctx, 10, RULE_predefined);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(53); match(3);
			setState(54); match(5);
			setState(55); match(3);
			setState(56); match(PREDEFINED_OP);
			setState(57); integer();
			setState(58); integer();
			setState(59); match(1);
			setState(60); integer();
			setState(61); match(1);
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
		enterRule(_localctx, 12, RULE_value);
		try {
			setState(65);
			switch (_input.LA(1)) {
			case BOOL:
				enterOuterAlt(_localctx, 1);
				{
				setState(63); match(BOOL);
				}
				break;
			case 2:
			case INT:
				enterOuterAlt(_localctx, 2);
				{
				setState(64); numeric();
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
		enterRule(_localctx, 14, RULE_integer);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(68);
			_la = _input.LA(1);
			if (_la==2) {
				{
				setState(67); match(2);
				}
			}

			setState(70); match(INT);
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
		enterRule(_localctx, 16, RULE_numeric);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(73);
			_la = _input.LA(1);
			if (_la==2) {
				{
				setState(72); match(2);
				}
			}

			setState(75); match(INT);
			setState(78);
			_la = _input.LA(1);
			if (_la==4) {
				{
				setState(76); match(4);
				setState(77); match(INT);
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
		"\2\3\16S\4\2\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t"+
		"\t\4\n\t\n\3\2\3\2\5\2\27\n\2\3\2\3\2\3\3\3\3\3\3\3\3\6\3\37\n\3\r\3\16"+
		"\3 \3\4\3\4\3\4\3\4\3\4\3\4\3\5\3\5\3\5\3\5\3\5\3\5\3\6\3\6\3\6\3\6\3"+
		"\6\3\6\3\6\3\6\3\6\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\b\3\b\5\b"+
		"D\n\b\3\t\5\tG\n\t\3\t\3\t\3\n\5\nL\n\n\3\n\3\n\3\n\5\nQ\n\n\3\n\2\13"+
		"\2\4\6\b\n\f\16\20\22\2\2R\2\24\3\2\2\2\4\36\3\2\2\2\6\"\3\2\2\2\b(\3"+
		"\2\2\2\n.\3\2\2\2\f\67\3\2\2\2\16C\3\2\2\2\20F\3\2\2\2\22K\3\2\2\2\24"+
		"\26\7\b\2\2\25\27\5\4\3\2\26\25\3\2\2\2\26\27\3\2\2\2\27\30\3\2\2\2\30"+
		"\31\7\1\2\2\31\3\3\2\2\2\32\37\5\6\4\2\33\37\5\b\5\2\34\37\5\n\6\2\35"+
		"\37\5\f\7\2\36\32\3\2\2\2\36\33\3\2\2\2\36\34\3\2\2\2\36\35\3\2\2\2\37"+
		" \3\2\2\2 \36\3\2\2\2 !\3\2\2\2!\5\3\2\2\2\"#\7\5\2\2#$\7\7\2\2$%\7\f"+
		"\2\2%&\7\f\2\2&\'\7\3\2\2\'\7\3\2\2\2()\7\5\2\2)*\7\7\2\2*+\7\f\2\2+,"+
		"\5\16\b\2,-\7\3\2\2-\t\3\2\2\2./\7\5\2\2/\60\7\7\2\2\60\61\7\5\2\2\61"+
		"\62\7\f\2\2\62\63\5\20\t\2\63\64\7\3\2\2\64\65\5\16\b\2\65\66\7\3\2\2"+
		"\66\13\3\2\2\2\678\7\5\2\289\7\7\2\29:\7\5\2\2:;\7\t\2\2;<\5\20\t\2<="+
		"\5\20\t\2=>\7\3\2\2>?\5\20\t\2?@\7\3\2\2@\r\3\2\2\2AD\7\n\2\2BD\5\22\n"+
		"\2CA\3\2\2\2CB\3\2\2\2D\17\3\2\2\2EG\7\4\2\2FE\3\2\2\2FG\3\2\2\2GH\3\2"+
		"\2\2HI\7\13\2\2I\21\3\2\2\2JL\7\4\2\2KJ\3\2\2\2KL\3\2\2\2LM\3\2\2\2MP"+
		"\7\13\2\2NO\7\6\2\2OQ\7\13\2\2PN\3\2\2\2PQ\3\2\2\2Q\23\3\2\2\2\t\26\36"+
		" CFKP";
	public static final ATN _ATN =
		ATNSimulator.deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
	}
}