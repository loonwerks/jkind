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
		T__18=1, T__17=2, T__16=3, T__15=4, T__14=5, T__13=6, T__12=7, T__11=8, 
		T__10=9, T__9=10, T__8=11, T__7=12, T__6=13, T__5=14, T__4=15, T__3=16, 
		T__2=17, T__1=18, T__0=19, BOOL=20, INT=21, REAL=22, ID=23, WS=24, ERROR=25;
	public static final String[] tokenNames = {
		"<INVALID>", "'/'", "'Real'", "'model'", "'Int'", "'Bool'", "'>='", "'|'", 
		"'<'", "'='", "'>'", "'<='", "'declare-datatypes'", "'('", "')'", "'and'", 
		"'ite'", "'define-fun'", "'not'", "'-'", "BOOL", "INT", "REAL", "ID", 
		"WS", "ERROR"
	};
	public static final int
		RULE_model = 0, RULE_define = 1, RULE_typeConstructor = 2, RULE_typeMember = 3, 
		RULE_arg = 4, RULE_type = 5, RULE_body = 6, RULE_fn = 7, RULE_symbol = 8, 
		RULE_id = 9, RULE_qid = 10;
	public static final String[] ruleNames = {
		"model", "define", "typeConstructor", "typeMember", "arg", "type", "body", 
		"fn", "symbol", "id", "qid"
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
			setState(22); match(T__6);
			setState(23); match(T__16);
			setState(27);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__6) {
				{
				{
				setState(24); define();
				}
				}
				setState(29);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(30); match(T__5);
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
		public DefineContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_define; }
	 
		public DefineContext() { }
		public void copyFrom(DefineContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class DefinefunContext extends DefineContext {
		public IdContext id(int i) {
			return getRuleContext(IdContext.class,i);
		}
		public List<IdContext> id() {
			return getRuleContexts(IdContext.class);
		}
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public BodyContext body() {
			return getRuleContext(BodyContext.class,0);
		}
		public ArgContext arg() {
			return getRuleContext(ArgContext.class,0);
		}
		public DefinefunContext(DefineContext ctx) { copyFrom(ctx); }
	}
	public static class DeclareDataTypesContext extends DefineContext {
		public TypeConstructorContext typeConstructor(int i) {
			return getRuleContext(TypeConstructorContext.class,i);
		}
		public IdContext id() {
			return getRuleContext(IdContext.class,0);
		}
		public List<TypeConstructorContext> typeConstructor() {
			return getRuleContexts(TypeConstructorContext.class);
		}
		public ArgContext arg() {
			return getRuleContext(ArgContext.class,0);
		}
		public DeclareDataTypesContext(DefineContext ctx) { copyFrom(ctx); }
	}

	public final DefineContext define() throws RecognitionException {
		DefineContext _localctx = new DefineContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_define);
		int _la;
		try {
			setState(67);
			switch ( getInterpreter().adaptivePredict(_input,5,_ctx) ) {
			case 1:
				_localctx = new DefinefunContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(33); match(T__6);
				setState(34); match(T__2);
				setState(35); id();
				setState(36); match(T__6);
				setState(38);
				_la = _input.LA(1);
				if (_la==T__6) {
					{
					setState(37); arg();
					}
				}

				setState(40); match(T__5);
				setState(43);
				switch (_input.LA(1)) {
				case T__17:
				case T__15:
				case T__14:
					{
					setState(41); type();
					}
					break;
				case T__12:
				case ID:
					{
					setState(42); id();
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(45); body();
				setState(46); match(T__5);
				}
				break;
			case 2:
				_localctx = new DeclareDataTypesContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(48); match(T__6);
				setState(49); match(T__7);
				setState(50); match(T__6);
				setState(52);
				_la = _input.LA(1);
				if (_la==T__6) {
					{
					setState(51); arg();
					}
				}

				setState(54); match(T__5);
				setState(55); match(T__6);
				setState(56); match(T__6);
				setState(57); id();
				setState(59); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(58); typeConstructor();
					}
					}
					setState(61); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==T__6 );
				setState(63); match(T__5);
				setState(64); match(T__5);
				setState(65); match(T__5);
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

	public static class TypeConstructorContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(SmtLib2Parser.ID, 0); }
		public List<TypeMemberContext> typeMember() {
			return getRuleContexts(TypeMemberContext.class);
		}
		public TypeMemberContext typeMember(int i) {
			return getRuleContext(TypeMemberContext.class,i);
		}
		public TypeConstructorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typeConstructor; }
	}

	public final TypeConstructorContext typeConstructor() throws RecognitionException {
		TypeConstructorContext _localctx = new TypeConstructorContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_typeConstructor);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(69); match(T__6);
			setState(70); match(ID);
			setState(74);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__6) {
				{
				{
				setState(71); typeMember();
				}
				}
				setState(76);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(77); match(T__5);
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

	public static class TypeMemberContext extends ParserRuleContext {
		public List<TerminalNode> ID() { return getTokens(SmtLib2Parser.ID); }
		public TerminalNode ID(int i) {
			return getToken(SmtLib2Parser.ID, i);
		}
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TypeMemberContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typeMember; }
	}

	public final TypeMemberContext typeMember() throws RecognitionException {
		TypeMemberContext _localctx = new TypeMemberContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_typeMember);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(79); match(T__6);
			setState(80); match(ID);
			setState(83);
			switch (_input.LA(1)) {
			case T__17:
			case T__15:
			case T__14:
				{
				setState(81); type();
				}
				break;
			case ID:
				{
				setState(82); match(ID);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(85); match(T__5);
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
		enterRule(_localctx, 8, RULE_arg);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(87); match(T__6);
			setState(88); id();
			setState(89); type();
			setState(90); match(T__5);
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
		enterRule(_localctx, 10, RULE_type);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(92);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__17) | (1L << T__15) | (1L << T__14))) != 0)) ) {
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
		enterRule(_localctx, 12, RULE_body);
		int _la;
		try {
			setState(105);
			switch (_input.LA(1)) {
			case T__12:
			case BOOL:
			case INT:
			case REAL:
			case ID:
				_localctx = new SymbolBodyContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(94); symbol();
				}
				break;
			case T__6:
				_localctx = new ConsBodyContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(95); match(T__6);
				setState(96); fn();
				setState(100);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__12) | (1L << T__6) | (1L << BOOL) | (1L << INT) | (1L << REAL) | (1L << ID))) != 0)) {
					{
					{
					setState(97); body();
					}
					}
					setState(102);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(103); match(T__5);
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
		public IdContext id() {
			return getRuleContext(IdContext.class,0);
		}
		public FnContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_fn; }
	}

	public final FnContext fn() throws RecognitionException {
		FnContext _localctx = new FnContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_fn);
		try {
			setState(118);
			switch (_input.LA(1)) {
			case T__10:
				enterOuterAlt(_localctx, 1);
				{
				setState(107); match(T__10);
				}
				break;
			case T__0:
				enterOuterAlt(_localctx, 2);
				{
				setState(108); match(T__0);
				}
				break;
			case T__18:
				enterOuterAlt(_localctx, 3);
				{
				setState(109); match(T__18);
				}
				break;
			case T__4:
				enterOuterAlt(_localctx, 4);
				{
				setState(110); match(T__4);
				}
				break;
			case T__3:
				enterOuterAlt(_localctx, 5);
				{
				setState(111); match(T__3);
				}
				break;
			case T__1:
				enterOuterAlt(_localctx, 6);
				{
				setState(112); match(T__1);
				}
				break;
			case T__13:
				enterOuterAlt(_localctx, 7);
				{
				setState(113); match(T__13);
				}
				break;
			case T__8:
				enterOuterAlt(_localctx, 8);
				{
				setState(114); match(T__8);
				}
				break;
			case T__11:
				enterOuterAlt(_localctx, 9);
				{
				setState(115); match(T__11);
				}
				break;
			case T__9:
				enterOuterAlt(_localctx, 10);
				{
				setState(116); match(T__9);
				}
				break;
			case T__12:
			case ID:
				enterOuterAlt(_localctx, 11);
				{
				setState(117); id();
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
		enterRule(_localctx, 16, RULE_symbol);
		try {
			setState(124);
			switch (_input.LA(1)) {
			case T__12:
			case ID:
				enterOuterAlt(_localctx, 1);
				{
				setState(120); id();
				}
				break;
			case BOOL:
				enterOuterAlt(_localctx, 2);
				{
				setState(121); match(BOOL);
				}
				break;
			case INT:
				enterOuterAlt(_localctx, 3);
				{
				setState(122); match(INT);
				}
				break;
			case REAL:
				enterOuterAlt(_localctx, 4);
				{
				setState(123); match(REAL);
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
		enterRule(_localctx, 18, RULE_id);
		try {
			setState(128);
			switch (_input.LA(1)) {
			case T__12:
				enterOuterAlt(_localctx, 1);
				{
				setState(126); qid();
				}
				break;
			case ID:
				enterOuterAlt(_localctx, 2);
				{
				setState(127); match(ID);
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
		enterRule(_localctx, 20, RULE_qid);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(130); match(T__12);
			setState(131); match(ID);
			setState(132); match(T__12);
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
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3\33\u0089\4\2\t\2"+
		"\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\3\2\3\2\3\2\7\2\34\n\2\f\2\16\2\37\13\2\3\2\3\2\3\2\3\3"+
		"\3\3\3\3\3\3\3\3\5\3)\n\3\3\3\3\3\3\3\5\3.\n\3\3\3\3\3\3\3\3\3\3\3\3\3"+
		"\3\3\5\3\67\n\3\3\3\3\3\3\3\3\3\3\3\6\3>\n\3\r\3\16\3?\3\3\3\3\3\3\3\3"+
		"\5\3F\n\3\3\4\3\4\3\4\7\4K\n\4\f\4\16\4N\13\4\3\4\3\4\3\5\3\5\3\5\3\5"+
		"\5\5V\n\5\3\5\3\5\3\6\3\6\3\6\3\6\3\6\3\7\3\7\3\b\3\b\3\b\3\b\7\be\n\b"+
		"\f\b\16\bh\13\b\3\b\3\b\5\bl\n\b\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3"+
		"\t\3\t\5\ty\n\t\3\n\3\n\3\n\3\n\5\n\177\n\n\3\13\3\13\5\13\u0083\n\13"+
		"\3\f\3\f\3\f\3\f\3\f\2\2\r\2\4\6\b\n\f\16\20\22\24\26\2\3\4\2\4\4\6\7"+
		"\u0095\2\30\3\2\2\2\4E\3\2\2\2\6G\3\2\2\2\bQ\3\2\2\2\nY\3\2\2\2\f^\3\2"+
		"\2\2\16k\3\2\2\2\20x\3\2\2\2\22~\3\2\2\2\24\u0082\3\2\2\2\26\u0084\3\2"+
		"\2\2\30\31\7\17\2\2\31\35\7\5\2\2\32\34\5\4\3\2\33\32\3\2\2\2\34\37\3"+
		"\2\2\2\35\33\3\2\2\2\35\36\3\2\2\2\36 \3\2\2\2\37\35\3\2\2\2 !\7\20\2"+
		"\2!\"\7\2\2\3\"\3\3\2\2\2#$\7\17\2\2$%\7\23\2\2%&\5\24\13\2&(\7\17\2\2"+
		"\')\5\n\6\2(\'\3\2\2\2()\3\2\2\2)*\3\2\2\2*-\7\20\2\2+.\5\f\7\2,.\5\24"+
		"\13\2-+\3\2\2\2-,\3\2\2\2./\3\2\2\2/\60\5\16\b\2\60\61\7\20\2\2\61F\3"+
		"\2\2\2\62\63\7\17\2\2\63\64\7\16\2\2\64\66\7\17\2\2\65\67\5\n\6\2\66\65"+
		"\3\2\2\2\66\67\3\2\2\2\678\3\2\2\289\7\20\2\29:\7\17\2\2:;\7\17\2\2;="+
		"\5\24\13\2<>\5\6\4\2=<\3\2\2\2>?\3\2\2\2?=\3\2\2\2?@\3\2\2\2@A\3\2\2\2"+
		"AB\7\20\2\2BC\7\20\2\2CD\7\20\2\2DF\3\2\2\2E#\3\2\2\2E\62\3\2\2\2F\5\3"+
		"\2\2\2GH\7\17\2\2HL\7\31\2\2IK\5\b\5\2JI\3\2\2\2KN\3\2\2\2LJ\3\2\2\2L"+
		"M\3\2\2\2MO\3\2\2\2NL\3\2\2\2OP\7\20\2\2P\7\3\2\2\2QR\7\17\2\2RU\7\31"+
		"\2\2SV\5\f\7\2TV\7\31\2\2US\3\2\2\2UT\3\2\2\2VW\3\2\2\2WX\7\20\2\2X\t"+
		"\3\2\2\2YZ\7\17\2\2Z[\5\24\13\2[\\\5\f\7\2\\]\7\20\2\2]\13\3\2\2\2^_\t"+
		"\2\2\2_\r\3\2\2\2`l\5\22\n\2ab\7\17\2\2bf\5\20\t\2ce\5\16\b\2dc\3\2\2"+
		"\2eh\3\2\2\2fd\3\2\2\2fg\3\2\2\2gi\3\2\2\2hf\3\2\2\2ij\7\20\2\2jl\3\2"+
		"\2\2k`\3\2\2\2ka\3\2\2\2l\17\3\2\2\2my\7\13\2\2ny\7\25\2\2oy\7\3\2\2p"+
		"y\7\21\2\2qy\7\22\2\2ry\7\24\2\2sy\7\b\2\2ty\7\r\2\2uy\7\n\2\2vy\7\f\2"+
		"\2wy\5\24\13\2xm\3\2\2\2xn\3\2\2\2xo\3\2\2\2xp\3\2\2\2xq\3\2\2\2xr\3\2"+
		"\2\2xs\3\2\2\2xt\3\2\2\2xu\3\2\2\2xv\3\2\2\2xw\3\2\2\2y\21\3\2\2\2z\177"+
		"\5\24\13\2{\177\7\26\2\2|\177\7\27\2\2}\177\7\30\2\2~z\3\2\2\2~{\3\2\2"+
		"\2~|\3\2\2\2~}\3\2\2\2\177\23\3\2\2\2\u0080\u0083\5\26\f\2\u0081\u0083"+
		"\7\31\2\2\u0082\u0080\3\2\2\2\u0082\u0081\3\2\2\2\u0083\25\3\2\2\2\u0084"+
		"\u0085\7\t\2\2\u0085\u0086\7\31\2\2\u0086\u0087\7\t\2\2\u0087\27\3\2\2"+
		"\2\17\35(-\66?ELUfkx~\u0082";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}