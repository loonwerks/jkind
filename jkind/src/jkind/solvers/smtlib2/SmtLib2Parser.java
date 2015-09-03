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
		T__20=1, T__19=2, T__18=3, T__17=4, T__16=5, T__15=6, T__14=7, T__13=8, 
		T__12=9, T__11=10, T__10=11, T__9=12, T__8=13, T__7=14, T__6=15, T__5=16, 
		T__4=17, T__3=18, T__2=19, T__1=20, T__0=21, BOOL=22, INT=23, REAL=24, 
		ID=25, WS=26, ERROR=27;
	public static final String[] tokenNames = {
		"<INVALID>", "'/'", "'Real'", "'model'", "'Int'", "'Bool'", "'>='", "';'", 
		"'|'", "'<'", "'='", "'>'", "'<='", "'declare-datatypes'", "'declare-sort'", 
		"'('", "')'", "'and'", "'ite'", "'define-fun'", "'not'", "'-'", "BOOL", 
		"INT", "REAL", "ID", "WS", "ERROR"
	};
	public static final int
		RULE_model = 0, RULE_comment = 1, RULE_define = 2, RULE_typeConstructor = 3, 
		RULE_typeMember = 4, RULE_arg = 5, RULE_type = 6, RULE_body = 7, RULE_fn = 8, 
		RULE_symbol = 9, RULE_id = 10, RULE_qid = 11;
	public static final String[] ruleNames = {
		"model", "comment", "define", "typeConstructor", "typeMember", "arg", 
		"type", "body", "fn", "symbol", "id", "qid"
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
		public List<CommentContext> comment() {
			return getRuleContexts(CommentContext.class);
		}
		public CommentContext comment(int i) {
			return getRuleContext(CommentContext.class,i);
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
			setState(24); match(T__6);
			setState(25); match(T__18);
			setState(30);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__14 || _la==T__6) {
				{
				setState(28);
				switch (_input.LA(1)) {
				case T__6:
					{
					setState(26); define();
					}
					break;
				case T__14:
					{
					setState(27); comment();
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				setState(32);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(33); match(T__5);
			setState(34); match(EOF);
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

	public static class CommentContext extends ParserRuleContext {
		public CommentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_comment; }
	}

	public final CommentContext comment() throws RecognitionException {
		CommentContext _localctx = new CommentContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_comment);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(36); match(T__14);
			setState(40);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,2,_ctx);
			while ( _alt!=1 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1+1 ) {
					{
					{
					setState(37);
					matchWildcard();
					}
					} 
				}
				setState(42);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,2,_ctx);
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
	public static class DeclareSortContext extends DefineContext {
		public IdContext id() {
			return getRuleContext(IdContext.class,0);
		}
		public TerminalNode INT() { return getToken(SmtLib2Parser.INT, 0); }
		public DeclareSortContext(DefineContext ctx) { copyFrom(ctx); }
	}
	public static class DefinefunContext extends DefineContext {
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
		enterRule(_localctx, 4, RULE_define);
		int _la;
		try {
			setState(83);
			switch ( getInterpreter().adaptivePredict(_input,6,_ctx) ) {
			case 1:
				_localctx = new DefinefunContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(43); match(T__6);
				setState(44); match(T__2);
				setState(45); id();
				setState(46); match(T__6);
				setState(50);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__6) {
					{
					{
					setState(47); arg();
					}
					}
					setState(52);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(53); match(T__5);
				{
				setState(54); type();
				}
				setState(55); body();
				setState(56); match(T__5);
				}
				break;
			case 2:
				_localctx = new DeclareDataTypesContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(58); match(T__6);
				setState(59); match(T__8);
				setState(60); match(T__6);
				setState(62);
				_la = _input.LA(1);
				if (_la==T__6) {
					{
					setState(61); arg();
					}
				}

				setState(64); match(T__5);
				setState(65); match(T__6);
				setState(66); match(T__6);
				setState(67); id();
				setState(69); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(68); typeConstructor();
					}
					}
					setState(71); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==T__6 );
				setState(73); match(T__5);
				setState(74); match(T__5);
				setState(75); match(T__5);
				}
				break;
			case 3:
				_localctx = new DeclareSortContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(77); match(T__6);
				setState(78); match(T__7);
				setState(79); id();
				setState(80); match(INT);
				setState(81); match(T__5);
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
		enterRule(_localctx, 6, RULE_typeConstructor);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(85); match(T__6);
			setState(86); match(ID);
			setState(90);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__6) {
				{
				{
				setState(87); typeMember();
				}
				}
				setState(92);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(93); match(T__5);
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
		enterRule(_localctx, 8, RULE_typeMember);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(95); match(T__6);
			setState(96); match(ID);
			setState(99);
			switch ( getInterpreter().adaptivePredict(_input,8,_ctx) ) {
			case 1:
				{
				setState(97); type();
				}
				break;
			case 2:
				{
				setState(98); match(ID);
				}
				break;
			}
			setState(101); match(T__5);
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
		enterRule(_localctx, 10, RULE_arg);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(103); match(T__6);
			setState(104); id();
			setState(105); type();
			setState(106); match(T__5);
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
		public IdContext id() {
			return getRuleContext(IdContext.class,0);
		}
		public TypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_type; }
	}

	public final TypeContext type() throws RecognitionException {
		TypeContext _localctx = new TypeContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_type);
		try {
			setState(112);
			switch (_input.LA(1)) {
			case T__16:
				enterOuterAlt(_localctx, 1);
				{
				setState(108); match(T__16);
				}
				break;
			case T__17:
				enterOuterAlt(_localctx, 2);
				{
				setState(109); match(T__17);
				}
				break;
			case T__19:
				enterOuterAlt(_localctx, 3);
				{
				setState(110); match(T__19);
				}
				break;
			case T__13:
			case ID:
				enterOuterAlt(_localctx, 4);
				{
				setState(111); id();
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
		enterRule(_localctx, 14, RULE_body);
		int _la;
		try {
			setState(125);
			switch (_input.LA(1)) {
			case T__13:
			case BOOL:
			case INT:
			case REAL:
			case ID:
				_localctx = new SymbolBodyContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(114); symbol();
				}
				break;
			case T__6:
				_localctx = new ConsBodyContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(115); match(T__6);
				setState(116); fn();
				setState(120);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__13) | (1L << T__6) | (1L << BOOL) | (1L << INT) | (1L << REAL) | (1L << ID))) != 0)) {
					{
					{
					setState(117); body();
					}
					}
					setState(122);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(123); match(T__5);
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
		enterRule(_localctx, 16, RULE_fn);
		try {
			setState(138);
			switch (_input.LA(1)) {
			case T__11:
				enterOuterAlt(_localctx, 1);
				{
				setState(127); match(T__11);
				}
				break;
			case T__0:
				enterOuterAlt(_localctx, 2);
				{
				setState(128); match(T__0);
				}
				break;
			case T__20:
				enterOuterAlt(_localctx, 3);
				{
				setState(129); match(T__20);
				}
				break;
			case T__4:
				enterOuterAlt(_localctx, 4);
				{
				setState(130); match(T__4);
				}
				break;
			case T__3:
				enterOuterAlt(_localctx, 5);
				{
				setState(131); match(T__3);
				}
				break;
			case T__1:
				enterOuterAlt(_localctx, 6);
				{
				setState(132); match(T__1);
				}
				break;
			case T__15:
				enterOuterAlt(_localctx, 7);
				{
				setState(133); match(T__15);
				}
				break;
			case T__9:
				enterOuterAlt(_localctx, 8);
				{
				setState(134); match(T__9);
				}
				break;
			case T__12:
				enterOuterAlt(_localctx, 9);
				{
				setState(135); match(T__12);
				}
				break;
			case T__10:
				enterOuterAlt(_localctx, 10);
				{
				setState(136); match(T__10);
				}
				break;
			case T__13:
			case ID:
				enterOuterAlt(_localctx, 11);
				{
				setState(137); id();
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
		enterRule(_localctx, 18, RULE_symbol);
		try {
			setState(144);
			switch (_input.LA(1)) {
			case T__13:
			case ID:
				enterOuterAlt(_localctx, 1);
				{
				setState(140); id();
				}
				break;
			case BOOL:
				enterOuterAlt(_localctx, 2);
				{
				setState(141); match(BOOL);
				}
				break;
			case INT:
				enterOuterAlt(_localctx, 3);
				{
				setState(142); match(INT);
				}
				break;
			case REAL:
				enterOuterAlt(_localctx, 4);
				{
				setState(143); match(REAL);
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
		enterRule(_localctx, 20, RULE_id);
		try {
			setState(148);
			switch (_input.LA(1)) {
			case T__13:
				enterOuterAlt(_localctx, 1);
				{
				setState(146); qid();
				}
				break;
			case ID:
				enterOuterAlt(_localctx, 2);
				{
				setState(147); match(ID);
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
		enterRule(_localctx, 22, RULE_qid);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(150); match(T__13);
			setState(151); match(ID);
			setState(152); match(T__13);
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
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3\35\u009d\4\2\t\2"+
		"\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\3\2\3\2\3\2\3\2\7\2\37\n\2\f\2\16\2\"\13\2\3\2\3"+
		"\2\3\2\3\3\3\3\7\3)\n\3\f\3\16\3,\13\3\3\4\3\4\3\4\3\4\3\4\7\4\63\n\4"+
		"\f\4\16\4\66\13\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\5\4A\n\4\3\4\3\4"+
		"\3\4\3\4\3\4\6\4H\n\4\r\4\16\4I\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3"+
		"\4\5\4V\n\4\3\5\3\5\3\5\7\5[\n\5\f\5\16\5^\13\5\3\5\3\5\3\6\3\6\3\6\3"+
		"\6\5\6f\n\6\3\6\3\6\3\7\3\7\3\7\3\7\3\7\3\b\3\b\3\b\3\b\5\bs\n\b\3\t\3"+
		"\t\3\t\3\t\7\ty\n\t\f\t\16\t|\13\t\3\t\3\t\5\t\u0080\n\t\3\n\3\n\3\n\3"+
		"\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\5\n\u008d\n\n\3\13\3\13\3\13\3\13\5\13"+
		"\u0093\n\13\3\f\3\f\5\f\u0097\n\f\3\r\3\r\3\r\3\r\3\r\3*\2\16\2\4\6\b"+
		"\n\f\16\20\22\24\26\30\2\2\u00ad\2\32\3\2\2\2\4&\3\2\2\2\6U\3\2\2\2\b"+
		"W\3\2\2\2\na\3\2\2\2\fi\3\2\2\2\16r\3\2\2\2\20\177\3\2\2\2\22\u008c\3"+
		"\2\2\2\24\u0092\3\2\2\2\26\u0096\3\2\2\2\30\u0098\3\2\2\2\32\33\7\21\2"+
		"\2\33 \7\5\2\2\34\37\5\6\4\2\35\37\5\4\3\2\36\34\3\2\2\2\36\35\3\2\2\2"+
		"\37\"\3\2\2\2 \36\3\2\2\2 !\3\2\2\2!#\3\2\2\2\" \3\2\2\2#$\7\22\2\2$%"+
		"\7\2\2\3%\3\3\2\2\2&*\7\t\2\2\')\13\2\2\2(\'\3\2\2\2),\3\2\2\2*+\3\2\2"+
		"\2*(\3\2\2\2+\5\3\2\2\2,*\3\2\2\2-.\7\21\2\2./\7\25\2\2/\60\5\26\f\2\60"+
		"\64\7\21\2\2\61\63\5\f\7\2\62\61\3\2\2\2\63\66\3\2\2\2\64\62\3\2\2\2\64"+
		"\65\3\2\2\2\65\67\3\2\2\2\66\64\3\2\2\2\678\7\22\2\289\5\16\b\29:\5\20"+
		"\t\2:;\7\22\2\2;V\3\2\2\2<=\7\21\2\2=>\7\17\2\2>@\7\21\2\2?A\5\f\7\2@"+
		"?\3\2\2\2@A\3\2\2\2AB\3\2\2\2BC\7\22\2\2CD\7\21\2\2DE\7\21\2\2EG\5\26"+
		"\f\2FH\5\b\5\2GF\3\2\2\2HI\3\2\2\2IG\3\2\2\2IJ\3\2\2\2JK\3\2\2\2KL\7\22"+
		"\2\2LM\7\22\2\2MN\7\22\2\2NV\3\2\2\2OP\7\21\2\2PQ\7\20\2\2QR\5\26\f\2"+
		"RS\7\31\2\2ST\7\22\2\2TV\3\2\2\2U-\3\2\2\2U<\3\2\2\2UO\3\2\2\2V\7\3\2"+
		"\2\2WX\7\21\2\2X\\\7\33\2\2Y[\5\n\6\2ZY\3\2\2\2[^\3\2\2\2\\Z\3\2\2\2\\"+
		"]\3\2\2\2]_\3\2\2\2^\\\3\2\2\2_`\7\22\2\2`\t\3\2\2\2ab\7\21\2\2be\7\33"+
		"\2\2cf\5\16\b\2df\7\33\2\2ec\3\2\2\2ed\3\2\2\2fg\3\2\2\2gh\7\22\2\2h\13"+
		"\3\2\2\2ij\7\21\2\2jk\5\26\f\2kl\5\16\b\2lm\7\22\2\2m\r\3\2\2\2ns\7\7"+
		"\2\2os\7\6\2\2ps\7\4\2\2qs\5\26\f\2rn\3\2\2\2ro\3\2\2\2rp\3\2\2\2rq\3"+
		"\2\2\2s\17\3\2\2\2t\u0080\5\24\13\2uv\7\21\2\2vz\5\22\n\2wy\5\20\t\2x"+
		"w\3\2\2\2y|\3\2\2\2zx\3\2\2\2z{\3\2\2\2{}\3\2\2\2|z\3\2\2\2}~\7\22\2\2"+
		"~\u0080\3\2\2\2\177t\3\2\2\2\177u\3\2\2\2\u0080\21\3\2\2\2\u0081\u008d"+
		"\7\f\2\2\u0082\u008d\7\27\2\2\u0083\u008d\7\3\2\2\u0084\u008d\7\23\2\2"+
		"\u0085\u008d\7\24\2\2\u0086\u008d\7\26\2\2\u0087\u008d\7\b\2\2\u0088\u008d"+
		"\7\16\2\2\u0089\u008d\7\13\2\2\u008a\u008d\7\r\2\2\u008b\u008d\5\26\f"+
		"\2\u008c\u0081\3\2\2\2\u008c\u0082\3\2\2\2\u008c\u0083\3\2\2\2\u008c\u0084"+
		"\3\2\2\2\u008c\u0085\3\2\2\2\u008c\u0086\3\2\2\2\u008c\u0087\3\2\2\2\u008c"+
		"\u0088\3\2\2\2\u008c\u0089\3\2\2\2\u008c\u008a\3\2\2\2\u008c\u008b\3\2"+
		"\2\2\u008d\23\3\2\2\2\u008e\u0093\5\26\f\2\u008f\u0093\7\30\2\2\u0090"+
		"\u0093\7\31\2\2\u0091\u0093\7\32\2\2\u0092\u008e\3\2\2\2\u0092\u008f\3"+
		"\2\2\2\u0092\u0090\3\2\2\2\u0092\u0091\3\2\2\2\u0093\25\3\2\2\2\u0094"+
		"\u0097\5\30\r\2\u0095\u0097\7\33\2\2\u0096\u0094\3\2\2\2\u0096\u0095\3"+
		"\2\2\2\u0097\27\3\2\2\2\u0098\u0099\7\n\2\2\u0099\u009a\7\33\2\2\u009a"+
		"\u009b\7\n\2\2\u009b\31\3\2\2\2\21\36 *\64@IU\\erz\177\u008c\u0092\u0096";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}