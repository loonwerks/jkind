// Generated from SmtLib2.g4 by ANTLR 4.4
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
	static { RuntimeMetaData.checkVersion("4.4", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__26=1, T__25=2, T__24=3, T__23=4, T__22=5, T__21=6, T__20=7, T__19=8, 
		T__18=9, T__17=10, T__16=11, T__15=12, T__14=13, T__13=14, T__12=15, T__11=16, 
		T__10=17, T__9=18, T__8=19, T__7=20, T__6=21, T__5=22, T__4=23, T__3=24, 
		T__2=25, T__1=26, T__0=27, BOOL=28, INT=29, REAL=30, ID=31, WS=32, ERROR=33;
	public static final String[] tokenNames = {
		"<INVALID>", "'/'", "'to_real'", "'Bool'", "'='", "'<='", "'('", "'*'", 
		"'to_int'", "'ite'", "'define-fun'", "'Real'", "'model'", "'Int'", "'mod'", 
		"'>='", "'|'", "'<'", "'>'", "'or'", "'=>'", "'let'", "'div'", "')'", 
		"'and'", "'+'", "'not'", "'-'", "BOOL", "INT", "REAL", "ID", "WS", "ERROR"
	};
	public static final int
		RULE_model = 0, RULE_define = 1, RULE_arg = 2, RULE_type = 3, RULE_body = 4, 
		RULE_binding = 5, RULE_fn = 6, RULE_symbol = 7, RULE_id = 8, RULE_qid = 9;
	public static final String[] ruleNames = {
		"model", "define", "arg", "type", "body", "binding", "fn", "symbol", "id", 
		"qid"
	};

	@Override
	public String getGrammarFileName() { return "SmtLib2.g4"; }

	@Override
	public String[] getTokenNames() { return tokenNames; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public SmtLib2Parser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class ModelContext extends ParserRuleContext {
		public DefineContext define(int i) {
			return getRuleContext(DefineContext.class,i);
		}
		public List<DefineContext> define() {
			return getRuleContexts(DefineContext.class);
		}
		public TerminalNode EOF() { return getToken(SmtLib2Parser.EOF, 0); }
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
			setState(20); match(T__21);
			setState(21); match(T__15);
			setState(25);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__21) {
				{
				{
				setState(22); define();
				}
				}
				setState(27);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(28); match(T__4);
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

	public static class DefineContext extends ParserRuleContext {
		public ArgContext arg(int i) {
			return getRuleContext(ArgContext.class,i);
		}
		public IdContext id() {
			return getRuleContext(IdContext.class,0);
		}
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public BodyContext body() {
			return getRuleContext(BodyContext.class,0);
		}
		public List<ArgContext> arg() {
			return getRuleContexts(ArgContext.class);
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
			setState(31); match(T__21);
			setState(32); match(T__17);
			setState(33); id();
			setState(34); match(T__21);
			setState(38);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__21) {
				{
				{
				setState(35); arg();
				}
				}
				setState(40);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(41); match(T__4);
			setState(42); type();
			setState(43); body();
			setState(44); match(T__4);
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
		public IdContext id() {
			return getRuleContext(IdContext.class,0);
		}
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
			setState(46); match(T__21);
			setState(47); id();
			setState(48); type();
			setState(49); match(T__4);
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
			setState(51);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__24) | (1L << T__16) | (1L << T__14))) != 0)) ) {
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
	public static class LetBodyContext extends BodyContext {
		public List<BindingContext> binding() {
			return getRuleContexts(BindingContext.class);
		}
		public BodyContext body() {
			return getRuleContext(BodyContext.class,0);
		}
		public BindingContext binding(int i) {
			return getRuleContext(BindingContext.class,i);
		}
		public LetBodyContext(BodyContext ctx) { copyFrom(ctx); }
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
		enterRule(_localctx, 8, RULE_body);
		int _la;
		try {
			setState(77);
			switch ( getInterpreter().adaptivePredict(_input,4,_ctx) ) {
			case 1:
				_localctx = new SymbolBodyContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(53); symbol();
				}
				break;
			case 2:
				_localctx = new ConsBodyContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(54); match(T__21);
				setState(55); fn();
				setState(59);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__21) | (1L << T__11) | (1L << BOOL) | (1L << INT) | (1L << REAL) | (1L << ID))) != 0)) {
					{
					{
					setState(56); body();
					}
					}
					setState(61);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(62); match(T__4);
				}
				break;
			case 3:
				_localctx = new LetBodyContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(64); match(T__21);
				setState(65); match(T__6);
				setState(66); match(T__21);
				setState(70);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__21) {
					{
					{
					setState(67); binding();
					}
					}
					setState(72);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(73); match(T__4);
				setState(74); body();
				setState(75); match(T__4);
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

	public static class BindingContext extends ParserRuleContext {
		public IdContext id() {
			return getRuleContext(IdContext.class,0);
		}
		public BodyContext body() {
			return getRuleContext(BodyContext.class,0);
		}
		public BindingContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_binding; }
	}

	public final BindingContext binding() throws RecognitionException {
		BindingContext _localctx = new BindingContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_binding);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(79); match(T__21);
			setState(80); id();
			setState(81); body();
			setState(82); match(T__4);
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
		enterRule(_localctx, 12, RULE_fn);
		try {
			setState(103);
			switch (_input.LA(1)) {
			case T__23:
				enterOuterAlt(_localctx, 1);
				{
				setState(84); match(T__23);
				}
				break;
			case T__2:
				enterOuterAlt(_localctx, 2);
				{
				setState(85); match(T__2);
				}
				break;
			case T__20:
				enterOuterAlt(_localctx, 3);
				{
				setState(86); match(T__20);
				}
				break;
			case T__0:
				enterOuterAlt(_localctx, 4);
				{
				setState(87); match(T__0);
				}
				break;
			case T__26:
				enterOuterAlt(_localctx, 5);
				{
				setState(88); match(T__26);
				}
				break;
			case T__5:
				enterOuterAlt(_localctx, 6);
				{
				setState(89); match(T__5);
				}
				break;
			case T__13:
				enterOuterAlt(_localctx, 7);
				{
				setState(90); match(T__13);
				}
				break;
			case T__3:
				enterOuterAlt(_localctx, 8);
				{
				setState(91); match(T__3);
				}
				break;
			case T__8:
				enterOuterAlt(_localctx, 9);
				{
				setState(92); match(T__8);
				}
				break;
			case T__1:
				enterOuterAlt(_localctx, 10);
				{
				setState(93); match(T__1);
				}
				break;
			case T__7:
				enterOuterAlt(_localctx, 11);
				{
				setState(94); match(T__7);
				}
				break;
			case T__18:
				enterOuterAlt(_localctx, 12);
				{
				setState(95); match(T__18);
				}
				break;
			case T__21:
			case T__11:
			case T__4:
			case BOOL:
			case INT:
			case REAL:
			case ID:
				enterOuterAlt(_localctx, 13);
				{
				}
				break;
			case T__12:
				enterOuterAlt(_localctx, 14);
				{
				setState(97); match(T__12);
				}
				break;
			case T__22:
				enterOuterAlt(_localctx, 15);
				{
				setState(98); match(T__22);
				}
				break;
			case T__10:
				enterOuterAlt(_localctx, 16);
				{
				setState(99); match(T__10);
				}
				break;
			case T__9:
				enterOuterAlt(_localctx, 17);
				{
				setState(100); match(T__9);
				}
				break;
			case T__25:
				enterOuterAlt(_localctx, 18);
				{
				setState(101); match(T__25);
				}
				break;
			case T__19:
				enterOuterAlt(_localctx, 19);
				{
				setState(102); match(T__19);
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
		public TerminalNode BOOL() { return getToken(SmtLib2Parser.BOOL, 0); }
		public TerminalNode REAL() { return getToken(SmtLib2Parser.REAL, 0); }
		public IdContext id() {
			return getRuleContext(IdContext.class,0);
		}
		public TerminalNode INT() { return getToken(SmtLib2Parser.INT, 0); }
		public SymbolContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_symbol; }
	}

	public final SymbolContext symbol() throws RecognitionException {
		SymbolContext _localctx = new SymbolContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_symbol);
		try {
			setState(109);
			switch (_input.LA(1)) {
			case T__11:
			case ID:
				enterOuterAlt(_localctx, 1);
				{
				setState(105); id();
				}
				break;
			case BOOL:
				enterOuterAlt(_localctx, 2);
				{
				setState(106); match(BOOL);
				}
				break;
			case INT:
				enterOuterAlt(_localctx, 3);
				{
				setState(107); match(INT);
				}
				break;
			case REAL:
				enterOuterAlt(_localctx, 4);
				{
				setState(108); match(REAL);
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
		public TerminalNode ID() { return getToken(SmtLib2Parser.ID, 0); }
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
		enterRule(_localctx, 16, RULE_id);
		try {
			setState(113);
			switch (_input.LA(1)) {
			case T__11:
				enterOuterAlt(_localctx, 1);
				{
				setState(111); qid();
				}
				break;
			case ID:
				enterOuterAlt(_localctx, 2);
				{
				setState(112); match(ID);
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
		public TerminalNode ID() { return getToken(SmtLib2Parser.ID, 0); }
		public QidContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_qid; }
	}

	public final QidContext qid() throws RecognitionException {
		QidContext _localctx = new QidContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_qid);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(115); match(T__11);
			setState(116); match(ID);
			setState(117); match(T__11);
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
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3#z\4\2\t\2\4\3\t\3"+
		"\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t\13\3\2"+
		"\3\2\3\2\7\2\32\n\2\f\2\16\2\35\13\2\3\2\3\2\3\2\3\3\3\3\3\3\3\3\3\3\7"+
		"\3\'\n\3\f\3\16\3*\13\3\3\3\3\3\3\3\3\3\3\3\3\4\3\4\3\4\3\4\3\4\3\5\3"+
		"\5\3\6\3\6\3\6\3\6\7\6<\n\6\f\6\16\6?\13\6\3\6\3\6\3\6\3\6\3\6\3\6\7\6"+
		"G\n\6\f\6\16\6J\13\6\3\6\3\6\3\6\3\6\5\6P\n\6\3\7\3\7\3\7\3\7\3\7\3\b"+
		"\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3"+
		"\b\5\bj\n\b\3\t\3\t\3\t\3\t\5\tp\n\t\3\n\3\n\5\nt\n\n\3\13\3\13\3\13\3"+
		"\13\3\13\2\2\f\2\4\6\b\n\f\16\20\22\24\2\3\5\2\5\5\r\r\17\17\u008b\2\26"+
		"\3\2\2\2\4!\3\2\2\2\6\60\3\2\2\2\b\65\3\2\2\2\nO\3\2\2\2\fQ\3\2\2\2\16"+
		"i\3\2\2\2\20o\3\2\2\2\22s\3\2\2\2\24u\3\2\2\2\26\27\7\b\2\2\27\33\7\16"+
		"\2\2\30\32\5\4\3\2\31\30\3\2\2\2\32\35\3\2\2\2\33\31\3\2\2\2\33\34\3\2"+
		"\2\2\34\36\3\2\2\2\35\33\3\2\2\2\36\37\7\31\2\2\37 \7\2\2\3 \3\3\2\2\2"+
		"!\"\7\b\2\2\"#\7\f\2\2#$\5\22\n\2$(\7\b\2\2%\'\5\6\4\2&%\3\2\2\2\'*\3"+
		"\2\2\2(&\3\2\2\2()\3\2\2\2)+\3\2\2\2*(\3\2\2\2+,\7\31\2\2,-\5\b\5\2-."+
		"\5\n\6\2./\7\31\2\2/\5\3\2\2\2\60\61\7\b\2\2\61\62\5\22\n\2\62\63\5\b"+
		"\5\2\63\64\7\31\2\2\64\7\3\2\2\2\65\66\t\2\2\2\66\t\3\2\2\2\67P\5\20\t"+
		"\289\7\b\2\29=\5\16\b\2:<\5\n\6\2;:\3\2\2\2<?\3\2\2\2=;\3\2\2\2=>\3\2"+
		"\2\2>@\3\2\2\2?=\3\2\2\2@A\7\31\2\2AP\3\2\2\2BC\7\b\2\2CD\7\27\2\2DH\7"+
		"\b\2\2EG\5\f\7\2FE\3\2\2\2GJ\3\2\2\2HF\3\2\2\2HI\3\2\2\2IK\3\2\2\2JH\3"+
		"\2\2\2KL\7\31\2\2LM\5\n\6\2MN\7\31\2\2NP\3\2\2\2O\67\3\2\2\2O8\3\2\2\2"+
		"OB\3\2\2\2P\13\3\2\2\2QR\7\b\2\2RS\5\22\n\2ST\5\n\6\2TU\7\31\2\2U\r\3"+
		"\2\2\2Vj\7\6\2\2Wj\7\33\2\2Xj\7\t\2\2Yj\7\35\2\2Zj\7\3\2\2[j\7\30\2\2"+
		"\\j\7\20\2\2]j\7\32\2\2^j\7\25\2\2_j\7\34\2\2`j\7\26\2\2aj\7\13\2\2bj"+
		"\3\2\2\2cj\7\21\2\2dj\7\7\2\2ej\7\23\2\2fj\7\24\2\2gj\7\4\2\2hj\7\n\2"+
		"\2iV\3\2\2\2iW\3\2\2\2iX\3\2\2\2iY\3\2\2\2iZ\3\2\2\2i[\3\2\2\2i\\\3\2"+
		"\2\2i]\3\2\2\2i^\3\2\2\2i_\3\2\2\2i`\3\2\2\2ia\3\2\2\2ib\3\2\2\2ic\3\2"+
		"\2\2id\3\2\2\2ie\3\2\2\2if\3\2\2\2ig\3\2\2\2ih\3\2\2\2j\17\3\2\2\2kp\5"+
		"\22\n\2lp\7\36\2\2mp\7\37\2\2np\7 \2\2ok\3\2\2\2ol\3\2\2\2om\3\2\2\2o"+
		"n\3\2\2\2p\21\3\2\2\2qt\5\24\13\2rt\7!\2\2sq\3\2\2\2sr\3\2\2\2t\23\3\2"+
		"\2\2uv\7\22\2\2vw\7!\2\2wx\7\22\2\2x\25\3\2\2\2\n\33(=HOios";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}