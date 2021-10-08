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
		T__2=25, T__1=26, T__0=27, BOOL=28, INT=29, REAL=30, ID=31, DIV0=32, WS=33, 
		ERROR=34;
	public static final String[] tokenNames = {
		"<INVALID>", "'/'", "'to_real'", "'Bool'", "'='", "'<='", "'('", "'*'", 
		"'to_int'", "'ite'", "'define-fun'", "'Real'", "'model'", "'Int'", "'mod'", 
		"'>='", "'|'", "'<'", "'>'", "'or'", "'=>'", "'let'", "'div'", "')'", 
		"'and'", "'+'", "'not'", "'-'", "BOOL", "INT", "REAL", "ID", "'/0'", "WS", 
		"ERROR"
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
			setState(22);
			_la = _input.LA(1);
			if (_la==T__15) {
				{
				setState(21); match(T__15);
				}
			}

			setState(27);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__21) {
				{
				{
				setState(24); define();
				}
				}
				setState(29);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(30); match(T__4);
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
			setState(33); match(T__21);
			setState(34); match(T__17);
			setState(35); id();
			setState(36); match(T__21);
			setState(40);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__21) {
				{
				{
				setState(37); arg();
				}
				}
				setState(42);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(43); match(T__4);
			setState(44); type();
			setState(45); body();
			setState(46); match(T__4);
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
			setState(48); match(T__21);
			setState(49); id();
			setState(50); type();
			setState(51); match(T__4);
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
			setState(53);
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
			setState(79);
			switch ( getInterpreter().adaptivePredict(_input,5,_ctx) ) {
			case 1:
				_localctx = new SymbolBodyContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(55); symbol();
				}
				break;
			case 2:
				_localctx = new ConsBodyContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(56); match(T__21);
				setState(57); fn();
				setState(61);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__21) | (1L << T__11) | (1L << BOOL) | (1L << INT) | (1L << REAL) | (1L << ID))) != 0)) {
					{
					{
					setState(58); body();
					}
					}
					setState(63);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(64); match(T__4);
				}
				break;
			case 3:
				_localctx = new LetBodyContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(66); match(T__21);
				setState(67); match(T__6);
				setState(68); match(T__21);
				setState(72);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__21) {
					{
					{
					setState(69); binding();
					}
					}
					setState(74);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(75); match(T__4);
				setState(76); body();
				setState(77); match(T__4);
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
			setState(81); match(T__21);
			setState(82); id();
			setState(83); body();
			setState(84); match(T__4);
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
		public TerminalNode DIV0() { return getToken(SmtLib2Parser.DIV0, 0); }
		public FnContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_fn; }
	}

	public final FnContext fn() throws RecognitionException {
		FnContext _localctx = new FnContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_fn);
		try {
			setState(106);
			switch (_input.LA(1)) {
			case T__23:
				enterOuterAlt(_localctx, 1);
				{
				setState(86); match(T__23);
				}
				break;
			case T__2:
				enterOuterAlt(_localctx, 2);
				{
				setState(87); match(T__2);
				}
				break;
			case T__20:
				enterOuterAlt(_localctx, 3);
				{
				setState(88); match(T__20);
				}
				break;
			case T__0:
				enterOuterAlt(_localctx, 4);
				{
				setState(89); match(T__0);
				}
				break;
			case T__26:
				enterOuterAlt(_localctx, 5);
				{
				setState(90); match(T__26);
				}
				break;
			case DIV0:
				enterOuterAlt(_localctx, 6);
				{
				setState(91); match(DIV0);
				}
				break;
			case T__5:
				enterOuterAlt(_localctx, 7);
				{
				setState(92); match(T__5);
				}
				break;
			case T__13:
				enterOuterAlt(_localctx, 8);
				{
				setState(93); match(T__13);
				}
				break;
			case T__3:
				enterOuterAlt(_localctx, 9);
				{
				setState(94); match(T__3);
				}
				break;
			case T__8:
				enterOuterAlt(_localctx, 10);
				{
				setState(95); match(T__8);
				}
				break;
			case T__1:
				enterOuterAlt(_localctx, 11);
				{
				setState(96); match(T__1);
				}
				break;
			case T__7:
				enterOuterAlt(_localctx, 12);
				{
				setState(97); match(T__7);
				}
				break;
			case T__18:
				enterOuterAlt(_localctx, 13);
				{
				setState(98); match(T__18);
				}
				break;
			case T__21:
			case T__11:
			case T__4:
			case BOOL:
			case INT:
			case REAL:
			case ID:
				enterOuterAlt(_localctx, 14);
				{
				}
				break;
			case T__12:
				enterOuterAlt(_localctx, 15);
				{
				setState(100); match(T__12);
				}
				break;
			case T__22:
				enterOuterAlt(_localctx, 16);
				{
				setState(101); match(T__22);
				}
				break;
			case T__10:
				enterOuterAlt(_localctx, 17);
				{
				setState(102); match(T__10);
				}
				break;
			case T__9:
				enterOuterAlt(_localctx, 18);
				{
				setState(103); match(T__9);
				}
				break;
			case T__25:
				enterOuterAlt(_localctx, 19);
				{
				setState(104); match(T__25);
				}
				break;
			case T__19:
				enterOuterAlt(_localctx, 20);
				{
				setState(105); match(T__19);
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
			setState(112);
			switch (_input.LA(1)) {
			case T__11:
			case ID:
				enterOuterAlt(_localctx, 1);
				{
				setState(108); id();
				}
				break;
			case BOOL:
				enterOuterAlt(_localctx, 2);
				{
				setState(109); match(BOOL);
				}
				break;
			case INT:
				enterOuterAlt(_localctx, 3);
				{
				setState(110); match(INT);
				}
				break;
			case REAL:
				enterOuterAlt(_localctx, 4);
				{
				setState(111); match(REAL);
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
			setState(116);
			switch (_input.LA(1)) {
			case T__11:
				enterOuterAlt(_localctx, 1);
				{
				setState(114); qid();
				}
				break;
			case ID:
				enterOuterAlt(_localctx, 2);
				{
				setState(115); match(ID);
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
			setState(118); match(T__11);
			setState(119); match(ID);
			setState(120); match(T__11);
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
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3$}\4\2\t\2\4\3\t\3"+
		"\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t\13\3\2"+
		"\3\2\5\2\31\n\2\3\2\7\2\34\n\2\f\2\16\2\37\13\2\3\2\3\2\3\2\3\3\3\3\3"+
		"\3\3\3\3\3\7\3)\n\3\f\3\16\3,\13\3\3\3\3\3\3\3\3\3\3\3\3\4\3\4\3\4\3\4"+
		"\3\4\3\5\3\5\3\6\3\6\3\6\3\6\7\6>\n\6\f\6\16\6A\13\6\3\6\3\6\3\6\3\6\3"+
		"\6\3\6\7\6I\n\6\f\6\16\6L\13\6\3\6\3\6\3\6\3\6\5\6R\n\6\3\7\3\7\3\7\3"+
		"\7\3\7\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b"+
		"\3\b\3\b\3\b\3\b\5\bm\n\b\3\t\3\t\3\t\3\t\5\ts\n\t\3\n\3\n\5\nw\n\n\3"+
		"\13\3\13\3\13\3\13\3\13\2\2\f\2\4\6\b\n\f\16\20\22\24\2\3\5\2\5\5\r\r"+
		"\17\17\u0090\2\26\3\2\2\2\4#\3\2\2\2\6\62\3\2\2\2\b\67\3\2\2\2\nQ\3\2"+
		"\2\2\fS\3\2\2\2\16l\3\2\2\2\20r\3\2\2\2\22v\3\2\2\2\24x\3\2\2\2\26\30"+
		"\7\b\2\2\27\31\7\16\2\2\30\27\3\2\2\2\30\31\3\2\2\2\31\35\3\2\2\2\32\34"+
		"\5\4\3\2\33\32\3\2\2\2\34\37\3\2\2\2\35\33\3\2\2\2\35\36\3\2\2\2\36 \3"+
		"\2\2\2\37\35\3\2\2\2 !\7\31\2\2!\"\7\2\2\3\"\3\3\2\2\2#$\7\b\2\2$%\7\f"+
		"\2\2%&\5\22\n\2&*\7\b\2\2\')\5\6\4\2(\'\3\2\2\2),\3\2\2\2*(\3\2\2\2*+"+
		"\3\2\2\2+-\3\2\2\2,*\3\2\2\2-.\7\31\2\2./\5\b\5\2/\60\5\n\6\2\60\61\7"+
		"\31\2\2\61\5\3\2\2\2\62\63\7\b\2\2\63\64\5\22\n\2\64\65\5\b\5\2\65\66"+
		"\7\31\2\2\66\7\3\2\2\2\678\t\2\2\28\t\3\2\2\29R\5\20\t\2:;\7\b\2\2;?\5"+
		"\16\b\2<>\5\n\6\2=<\3\2\2\2>A\3\2\2\2?=\3\2\2\2?@\3\2\2\2@B\3\2\2\2A?"+
		"\3\2\2\2BC\7\31\2\2CR\3\2\2\2DE\7\b\2\2EF\7\27\2\2FJ\7\b\2\2GI\5\f\7\2"+
		"HG\3\2\2\2IL\3\2\2\2JH\3\2\2\2JK\3\2\2\2KM\3\2\2\2LJ\3\2\2\2MN\7\31\2"+
		"\2NO\5\n\6\2OP\7\31\2\2PR\3\2\2\2Q9\3\2\2\2Q:\3\2\2\2QD\3\2\2\2R\13\3"+
		"\2\2\2ST\7\b\2\2TU\5\22\n\2UV\5\n\6\2VW\7\31\2\2W\r\3\2\2\2Xm\7\6\2\2"+
		"Ym\7\33\2\2Zm\7\t\2\2[m\7\35\2\2\\m\7\3\2\2]m\7\"\2\2^m\7\30\2\2_m\7\20"+
		"\2\2`m\7\32\2\2am\7\25\2\2bm\7\34\2\2cm\7\26\2\2dm\7\13\2\2em\3\2\2\2"+
		"fm\7\21\2\2gm\7\7\2\2hm\7\23\2\2im\7\24\2\2jm\7\4\2\2km\7\n\2\2lX\3\2"+
		"\2\2lY\3\2\2\2lZ\3\2\2\2l[\3\2\2\2l\\\3\2\2\2l]\3\2\2\2l^\3\2\2\2l_\3"+
		"\2\2\2l`\3\2\2\2la\3\2\2\2lb\3\2\2\2lc\3\2\2\2ld\3\2\2\2le\3\2\2\2lf\3"+
		"\2\2\2lg\3\2\2\2lh\3\2\2\2li\3\2\2\2lj\3\2\2\2lk\3\2\2\2m\17\3\2\2\2n"+
		"s\5\22\n\2os\7\36\2\2ps\7\37\2\2qs\7 \2\2rn\3\2\2\2ro\3\2\2\2rp\3\2\2"+
		"\2rq\3\2\2\2s\21\3\2\2\2tw\5\24\13\2uw\7!\2\2vt\3\2\2\2vu\3\2\2\2w\23"+
		"\3\2\2\2xy\7\22\2\2yz\7!\2\2z{\7\22\2\2{\25\3\2\2\2\13\30\35*?JQlrv";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}