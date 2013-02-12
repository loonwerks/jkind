// $ANTLR 3.5 Yices.g 2013-02-12 10:11:50

  package jkind.solvers;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class YicesLexer extends Lexer {
	public static final int EOF=-1;
	public static final int T__11=11;
	public static final int T__12=12;
	public static final int T__13=13;
	public static final int T__14=14;
	public static final int T__15=15;
	public static final int T__16=16;
	public static final int T__17=17;
	public static final int T__18=18;
	public static final int T__19=19;
	public static final int BUILT_IN=4;
	public static final int DIGIT=5;
	public static final int ERROR=6;
	public static final int ID=7;
	public static final int INT=8;
	public static final int SYMBOL=9;
	public static final int WS=10;

	  protected void ignore(Stack<Void> stack, List<Void> list, ArrayList<Void> arraylist) {}


	// delegates
	// delegators
	public Lexer[] getDelegates() {
		return new Lexer[] {};
	}

	public YicesLexer() {} 
	public YicesLexer(CharStream input) {
		this(input, new RecognizerSharedState());
	}
	public YicesLexer(CharStream input, RecognizerSharedState state) {
		super(input,state);
	}
	@Override public String getGrammarFileName() { return "Yices.g"; }

	// $ANTLR start "T__11"
	public final void mT__11() throws RecognitionException {
		try {
			int _type = T__11;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// Yices.g:14:7: ( '(' )
			// Yices.g:14:9: '('
			{
			match('('); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__11"

	// $ANTLR start "T__12"
	public final void mT__12() throws RecognitionException {
		try {
			int _type = T__12;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// Yices.g:15:7: ( ')' )
			// Yices.g:15:9: ')'
			{
			match(')'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__12"

	// $ANTLR start "T__13"
	public final void mT__13() throws RecognitionException {
		try {
			int _type = T__13;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// Yices.g:16:7: ( '-' )
			// Yices.g:16:9: '-'
			{
			match('-'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__13"

	// $ANTLR start "T__14"
	public final void mT__14() throws RecognitionException {
		try {
			int _type = T__14;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// Yices.g:17:7: ( '/' )
			// Yices.g:17:9: '/'
			{
			match('/'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__14"

	// $ANTLR start "T__15"
	public final void mT__15() throws RecognitionException {
		try {
			int _type = T__15;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// Yices.g:18:7: ( '=' )
			// Yices.g:18:9: '='
			{
			match('='); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__15"

	// $ANTLR start "T__16"
	public final void mT__16() throws RecognitionException {
		try {
			int _type = T__16;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// Yices.g:19:7: ( 'false' )
			// Yices.g:19:9: 'false'
			{
			match("false"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__16"

	// $ANTLR start "T__17"
	public final void mT__17() throws RecognitionException {
		try {
			int _type = T__17;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// Yices.g:20:7: ( 'sat' )
			// Yices.g:20:9: 'sat'
			{
			match("sat"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__17"

	// $ANTLR start "T__18"
	public final void mT__18() throws RecognitionException {
		try {
			int _type = T__18;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// Yices.g:21:7: ( 'true' )
			// Yices.g:21:9: 'true'
			{
			match("true"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__18"

	// $ANTLR start "T__19"
	public final void mT__19() throws RecognitionException {
		try {
			int _type = T__19;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// Yices.g:22:7: ( 'unsat' )
			// Yices.g:22:9: 'unsat'
			{
			match("unsat"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__19"

	// $ANTLR start "BUILT_IN"
	public final void mBUILT_IN() throws RecognitionException {
		try {
			int _type = BUILT_IN;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// Yices.g:55:9: ( 'mod' | 'div' )
			int alt1=2;
			int LA1_0 = input.LA(1);
			if ( (LA1_0=='m') ) {
				alt1=1;
			}
			else if ( (LA1_0=='d') ) {
				alt1=2;
			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 1, 0, input);
				throw nvae;
			}

			switch (alt1) {
				case 1 :
					// Yices.g:55:11: 'mod'
					{
					match("mod"); 

					}
					break;
				case 2 :
					// Yices.g:55:19: 'div'
					{
					match("div"); 

					}
					break;

			}
			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "BUILT_IN"

	// $ANTLR start "DIGIT"
	public final void mDIGIT() throws RecognitionException {
		try {
			// Yices.g:66:15: ( '0' .. '9' )
			// Yices.g:
			{
			if ( (input.LA(1) >= '0' && input.LA(1) <= '9') ) {
				input.consume();
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				recover(mse);
				throw mse;
			}
			}

		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "DIGIT"

	// $ANTLR start "SYMBOL"
	public final void mSYMBOL() throws RecognitionException {
		try {
			// Yices.g:67:16: ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '@' | '$' | '#' )
			// Yices.g:
			{
			if ( (input.LA(1) >= '#' && input.LA(1) <= '$')||(input.LA(1) >= '@' && input.LA(1) <= 'Z')||input.LA(1)=='_'||(input.LA(1) >= 'a' && input.LA(1) <= 'z') ) {
				input.consume();
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				recover(mse);
				throw mse;
			}
			}

		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "SYMBOL"

	// $ANTLR start "ID"
	public final void mID() throws RecognitionException {
		try {
			int _type = ID;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// Yices.g:69:3: ( SYMBOL ( SYMBOL | DIGIT )* )
			// Yices.g:69:5: SYMBOL ( SYMBOL | DIGIT )*
			{
			mSYMBOL(); 

			// Yices.g:69:12: ( SYMBOL | DIGIT )*
			loop2:
			while (true) {
				int alt2=2;
				int LA2_0 = input.LA(1);
				if ( ((LA2_0 >= '#' && LA2_0 <= '$')||(LA2_0 >= '0' && LA2_0 <= '9')||(LA2_0 >= '@' && LA2_0 <= 'Z')||LA2_0=='_'||(LA2_0 >= 'a' && LA2_0 <= 'z')) ) {
					alt2=1;
				}

				switch (alt2) {
				case 1 :
					// Yices.g:
					{
					if ( (input.LA(1) >= '#' && input.LA(1) <= '$')||(input.LA(1) >= '0' && input.LA(1) <= '9')||(input.LA(1) >= '@' && input.LA(1) <= 'Z')||input.LA(1)=='_'||(input.LA(1) >= 'a' && input.LA(1) <= 'z') ) {
						input.consume();
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						recover(mse);
						throw mse;
					}
					}
					break;

				default :
					break loop2;
				}
			}

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "ID"

	// $ANTLR start "INT"
	public final void mINT() throws RecognitionException {
		try {
			int _type = INT;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// Yices.g:70:4: ( ( DIGIT )+ )
			// Yices.g:70:6: ( DIGIT )+
			{
			// Yices.g:70:6: ( DIGIT )+
			int cnt3=0;
			loop3:
			while (true) {
				int alt3=2;
				int LA3_0 = input.LA(1);
				if ( ((LA3_0 >= '0' && LA3_0 <= '9')) ) {
					alt3=1;
				}

				switch (alt3) {
				case 1 :
					// Yices.g:
					{
					if ( (input.LA(1) >= '0' && input.LA(1) <= '9') ) {
						input.consume();
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						recover(mse);
						throw mse;
					}
					}
					break;

				default :
					if ( cnt3 >= 1 ) break loop3;
					EarlyExitException eee = new EarlyExitException(3, input);
					throw eee;
				}
				cnt3++;
			}

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "INT"

	// $ANTLR start "WS"
	public final void mWS() throws RecognitionException {
		try {
			int _type = WS;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// Yices.g:72:3: ( ( ' ' | '\\t' | '\\n' | '\\r' | '\\f' )+ )
			// Yices.g:72:5: ( ' ' | '\\t' | '\\n' | '\\r' | '\\f' )+
			{
			// Yices.g:72:5: ( ' ' | '\\t' | '\\n' | '\\r' | '\\f' )+
			int cnt4=0;
			loop4:
			while (true) {
				int alt4=2;
				int LA4_0 = input.LA(1);
				if ( ((LA4_0 >= '\t' && LA4_0 <= '\n')||(LA4_0 >= '\f' && LA4_0 <= '\r')||LA4_0==' ') ) {
					alt4=1;
				}

				switch (alt4) {
				case 1 :
					// Yices.g:
					{
					if ( (input.LA(1) >= '\t' && input.LA(1) <= '\n')||(input.LA(1) >= '\f' && input.LA(1) <= '\r')||input.LA(1)==' ' ) {
						input.consume();
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						recover(mse);
						throw mse;
					}
					}
					break;

				default :
					if ( cnt4 >= 1 ) break loop4;
					EarlyExitException eee = new EarlyExitException(4, input);
					throw eee;
				}
				cnt4++;
			}

			_channel = HIDDEN;
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "WS"

	// $ANTLR start "ERROR"
	public final void mERROR() throws RecognitionException {
		try {
			int _type = ERROR;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// Yices.g:74:6: ( '.' )
			// Yices.g:74:8: '.'
			{
			match('.'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "ERROR"

	@Override
	public void mTokens() throws RecognitionException {
		// Yices.g:1:8: ( T__11 | T__12 | T__13 | T__14 | T__15 | T__16 | T__17 | T__18 | T__19 | BUILT_IN | ID | INT | WS | ERROR )
		int alt5=14;
		switch ( input.LA(1) ) {
		case '(':
			{
			alt5=1;
			}
			break;
		case ')':
			{
			alt5=2;
			}
			break;
		case '-':
			{
			alt5=3;
			}
			break;
		case '/':
			{
			alt5=4;
			}
			break;
		case '=':
			{
			alt5=5;
			}
			break;
		case 'f':
			{
			int LA5_6 = input.LA(2);
			if ( (LA5_6=='a') ) {
				int LA5_16 = input.LA(3);
				if ( (LA5_16=='l') ) {
					int LA5_22 = input.LA(4);
					if ( (LA5_22=='s') ) {
						int LA5_28 = input.LA(5);
						if ( (LA5_28=='e') ) {
							int LA5_33 = input.LA(6);
							if ( ((LA5_33 >= '#' && LA5_33 <= '$')||(LA5_33 >= '0' && LA5_33 <= '9')||(LA5_33 >= '@' && LA5_33 <= 'Z')||LA5_33=='_'||(LA5_33 >= 'a' && LA5_33 <= 'z')) ) {
								alt5=11;
							}

							else {
								alt5=6;
							}

						}

						else {
							alt5=11;
						}

					}

					else {
						alt5=11;
					}

				}

				else {
					alt5=11;
				}

			}

			else {
				alt5=11;
			}

			}
			break;
		case 's':
			{
			int LA5_7 = input.LA(2);
			if ( (LA5_7=='a') ) {
				int LA5_17 = input.LA(3);
				if ( (LA5_17=='t') ) {
					int LA5_23 = input.LA(4);
					if ( ((LA5_23 >= '#' && LA5_23 <= '$')||(LA5_23 >= '0' && LA5_23 <= '9')||(LA5_23 >= '@' && LA5_23 <= 'Z')||LA5_23=='_'||(LA5_23 >= 'a' && LA5_23 <= 'z')) ) {
						alt5=11;
					}

					else {
						alt5=7;
					}

				}

				else {
					alt5=11;
				}

			}

			else {
				alt5=11;
			}

			}
			break;
		case 't':
			{
			int LA5_8 = input.LA(2);
			if ( (LA5_8=='r') ) {
				int LA5_18 = input.LA(3);
				if ( (LA5_18=='u') ) {
					int LA5_24 = input.LA(4);
					if ( (LA5_24=='e') ) {
						int LA5_30 = input.LA(5);
						if ( ((LA5_30 >= '#' && LA5_30 <= '$')||(LA5_30 >= '0' && LA5_30 <= '9')||(LA5_30 >= '@' && LA5_30 <= 'Z')||LA5_30=='_'||(LA5_30 >= 'a' && LA5_30 <= 'z')) ) {
							alt5=11;
						}

						else {
							alt5=8;
						}

					}

					else {
						alt5=11;
					}

				}

				else {
					alt5=11;
				}

			}

			else {
				alt5=11;
			}

			}
			break;
		case 'u':
			{
			int LA5_9 = input.LA(2);
			if ( (LA5_9=='n') ) {
				int LA5_19 = input.LA(3);
				if ( (LA5_19=='s') ) {
					int LA5_25 = input.LA(4);
					if ( (LA5_25=='a') ) {
						int LA5_31 = input.LA(5);
						if ( (LA5_31=='t') ) {
							int LA5_35 = input.LA(6);
							if ( ((LA5_35 >= '#' && LA5_35 <= '$')||(LA5_35 >= '0' && LA5_35 <= '9')||(LA5_35 >= '@' && LA5_35 <= 'Z')||LA5_35=='_'||(LA5_35 >= 'a' && LA5_35 <= 'z')) ) {
								alt5=11;
							}

							else {
								alt5=9;
							}

						}

						else {
							alt5=11;
						}

					}

					else {
						alt5=11;
					}

				}

				else {
					alt5=11;
				}

			}

			else {
				alt5=11;
			}

			}
			break;
		case 'm':
			{
			int LA5_10 = input.LA(2);
			if ( (LA5_10=='o') ) {
				int LA5_20 = input.LA(3);
				if ( (LA5_20=='d') ) {
					int LA5_26 = input.LA(4);
					if ( ((LA5_26 >= '#' && LA5_26 <= '$')||(LA5_26 >= '0' && LA5_26 <= '9')||(LA5_26 >= '@' && LA5_26 <= 'Z')||LA5_26=='_'||(LA5_26 >= 'a' && LA5_26 <= 'z')) ) {
						alt5=11;
					}

					else {
						alt5=10;
					}

				}

				else {
					alt5=11;
				}

			}

			else {
				alt5=11;
			}

			}
			break;
		case 'd':
			{
			int LA5_11 = input.LA(2);
			if ( (LA5_11=='i') ) {
				int LA5_21 = input.LA(3);
				if ( (LA5_21=='v') ) {
					int LA5_27 = input.LA(4);
					if ( ((LA5_27 >= '#' && LA5_27 <= '$')||(LA5_27 >= '0' && LA5_27 <= '9')||(LA5_27 >= '@' && LA5_27 <= 'Z')||LA5_27=='_'||(LA5_27 >= 'a' && LA5_27 <= 'z')) ) {
						alt5=11;
					}

					else {
						alt5=10;
					}

				}

				else {
					alt5=11;
				}

			}

			else {
				alt5=11;
			}

			}
			break;
		case '#':
		case '$':
		case '@':
		case 'A':
		case 'B':
		case 'C':
		case 'D':
		case 'E':
		case 'F':
		case 'G':
		case 'H':
		case 'I':
		case 'J':
		case 'K':
		case 'L':
		case 'M':
		case 'N':
		case 'O':
		case 'P':
		case 'Q':
		case 'R':
		case 'S':
		case 'T':
		case 'U':
		case 'V':
		case 'W':
		case 'X':
		case 'Y':
		case 'Z':
		case '_':
		case 'a':
		case 'b':
		case 'c':
		case 'e':
		case 'g':
		case 'h':
		case 'i':
		case 'j':
		case 'k':
		case 'l':
		case 'n':
		case 'o':
		case 'p':
		case 'q':
		case 'r':
		case 'v':
		case 'w':
		case 'x':
		case 'y':
		case 'z':
			{
			alt5=11;
			}
			break;
		case '0':
		case '1':
		case '2':
		case '3':
		case '4':
		case '5':
		case '6':
		case '7':
		case '8':
		case '9':
			{
			alt5=12;
			}
			break;
		case '\t':
		case '\n':
		case '\f':
		case '\r':
		case ' ':
			{
			alt5=13;
			}
			break;
		case '.':
			{
			alt5=14;
			}
			break;
		default:
			NoViableAltException nvae =
				new NoViableAltException("", 5, 0, input);
			throw nvae;
		}
		switch (alt5) {
			case 1 :
				// Yices.g:1:10: T__11
				{
				mT__11(); 

				}
				break;
			case 2 :
				// Yices.g:1:16: T__12
				{
				mT__12(); 

				}
				break;
			case 3 :
				// Yices.g:1:22: T__13
				{
				mT__13(); 

				}
				break;
			case 4 :
				// Yices.g:1:28: T__14
				{
				mT__14(); 

				}
				break;
			case 5 :
				// Yices.g:1:34: T__15
				{
				mT__15(); 

				}
				break;
			case 6 :
				// Yices.g:1:40: T__16
				{
				mT__16(); 

				}
				break;
			case 7 :
				// Yices.g:1:46: T__17
				{
				mT__17(); 

				}
				break;
			case 8 :
				// Yices.g:1:52: T__18
				{
				mT__18(); 

				}
				break;
			case 9 :
				// Yices.g:1:58: T__19
				{
				mT__19(); 

				}
				break;
			case 10 :
				// Yices.g:1:64: BUILT_IN
				{
				mBUILT_IN(); 

				}
				break;
			case 11 :
				// Yices.g:1:73: ID
				{
				mID(); 

				}
				break;
			case 12 :
				// Yices.g:1:76: INT
				{
				mINT(); 

				}
				break;
			case 13 :
				// Yices.g:1:80: WS
				{
				mWS(); 

				}
				break;
			case 14 :
				// Yices.g:1:83: ERROR
				{
				mERROR(); 

				}
				break;

		}
	}



}
