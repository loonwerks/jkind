// $ANTLR 3.4 Yices.g 2012-10-25 18:07:00

  package jkind.solvers;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked"})
public class YicesLexer extends Lexer {
    public static final int EOF=-1;
    public static final int T__10=10;
    public static final int T__11=11;
    public static final int T__12=12;
    public static final int T__13=13;
    public static final int T__14=14;
    public static final int T__15=15;
    public static final int T__16=16;
    public static final int T__17=17;
    public static final int T__18=18;
    public static final int DIGIT=4;
    public static final int ERROR=5;
    public static final int ID=6;
    public static final int INT=7;
    public static final int SYMBOL=8;
    public static final int WS=9;

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
    public String getGrammarFileName() { return "Yices.g"; }

    // $ANTLR start "T__10"
    public final void mT__10() throws RecognitionException {
        try {
            int _type = T__10;
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
    // $ANTLR end "T__10"

    // $ANTLR start "T__11"
    public final void mT__11() throws RecognitionException {
        try {
            int _type = T__11;
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
    // $ANTLR end "T__11"

    // $ANTLR start "T__12"
    public final void mT__12() throws RecognitionException {
        try {
            int _type = T__12;
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
    // $ANTLR end "T__12"

    // $ANTLR start "T__13"
    public final void mT__13() throws RecognitionException {
        try {
            int _type = T__13;
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
    // $ANTLR end "T__13"

    // $ANTLR start "T__14"
    public final void mT__14() throws RecognitionException {
        try {
            int _type = T__14;
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
    // $ANTLR end "T__14"

    // $ANTLR start "T__15"
    public final void mT__15() throws RecognitionException {
        try {
            int _type = T__15;
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
    // $ANTLR end "T__15"

    // $ANTLR start "T__16"
    public final void mT__16() throws RecognitionException {
        try {
            int _type = T__16;
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
    // $ANTLR end "T__16"

    // $ANTLR start "T__17"
    public final void mT__17() throws RecognitionException {
        try {
            int _type = T__17;
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
    // $ANTLR end "T__17"

    // $ANTLR start "T__18"
    public final void mT__18() throws RecognitionException {
        try {
            int _type = T__18;
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
    // $ANTLR end "T__18"

    // $ANTLR start "DIGIT"
    public final void mDIGIT() throws RecognitionException {
        try {
            // Yices.g:60:15: ( '0' .. '9' )
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
            // Yices.g:61:16: ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '@' )
            // Yices.g:
            {
            if ( (input.LA(1) >= '@' && input.LA(1) <= 'Z')||input.LA(1)=='_'||(input.LA(1) >= 'a' && input.LA(1) <= 'z') ) {
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
            // Yices.g:63:3: ( SYMBOL ( SYMBOL | DIGIT )* )
            // Yices.g:63:5: SYMBOL ( SYMBOL | DIGIT )*
            {
            mSYMBOL(); 


            // Yices.g:63:12: ( SYMBOL | DIGIT )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( ((LA1_0 >= '0' && LA1_0 <= '9')||(LA1_0 >= '@' && LA1_0 <= 'Z')||LA1_0=='_'||(LA1_0 >= 'a' && LA1_0 <= 'z')) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // Yices.g:
            	    {
            	    if ( (input.LA(1) >= '0' && input.LA(1) <= '9')||(input.LA(1) >= '@' && input.LA(1) <= 'Z')||input.LA(1)=='_'||(input.LA(1) >= 'a' && input.LA(1) <= 'z') ) {
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
            	    break loop1;
                }
            } while (true);


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
            // Yices.g:64:4: ( ( DIGIT )+ )
            // Yices.g:64:6: ( DIGIT )+
            {
            // Yices.g:64:6: ( DIGIT )+
            int cnt2=0;
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( ((LA2_0 >= '0' && LA2_0 <= '9')) ) {
                    alt2=1;
                }


                switch (alt2) {
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
            	    if ( cnt2 >= 1 ) break loop2;
                        EarlyExitException eee =
                            new EarlyExitException(2, input);
                        throw eee;
                }
                cnt2++;
            } while (true);


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
            // Yices.g:66:3: ( ( ' ' | '\\t' | '\\n' | '\\r' | '\\f' )+ )
            // Yices.g:66:5: ( ' ' | '\\t' | '\\n' | '\\r' | '\\f' )+
            {
            // Yices.g:66:5: ( ' ' | '\\t' | '\\n' | '\\r' | '\\f' )+
            int cnt3=0;
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( ((LA3_0 >= '\t' && LA3_0 <= '\n')||(LA3_0 >= '\f' && LA3_0 <= '\r')||LA3_0==' ') ) {
                    alt3=1;
                }


                switch (alt3) {
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
            	    if ( cnt3 >= 1 ) break loop3;
                        EarlyExitException eee =
                            new EarlyExitException(3, input);
                        throw eee;
                }
                cnt3++;
            } while (true);


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
            // Yices.g:68:6: ( '.' )
            // Yices.g:68:8: '.'
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

    public void mTokens() throws RecognitionException {
        // Yices.g:1:8: ( T__10 | T__11 | T__12 | T__13 | T__14 | T__15 | T__16 | T__17 | T__18 | ID | INT | WS | ERROR )
        int alt4=13;
        switch ( input.LA(1) ) {
        case '(':
            {
            alt4=1;
            }
            break;
        case ')':
            {
            alt4=2;
            }
            break;
        case '-':
            {
            alt4=3;
            }
            break;
        case '/':
            {
            alt4=4;
            }
            break;
        case '=':
            {
            alt4=5;
            }
            break;
        case 'f':
            {
            int LA4_6 = input.LA(2);

            if ( (LA4_6=='a') ) {
                int LA4_14 = input.LA(3);

                if ( (LA4_14=='l') ) {
                    int LA4_18 = input.LA(4);

                    if ( (LA4_18=='s') ) {
                        int LA4_22 = input.LA(5);

                        if ( (LA4_22=='e') ) {
                            int LA4_26 = input.LA(6);

                            if ( ((LA4_26 >= '0' && LA4_26 <= '9')||(LA4_26 >= '@' && LA4_26 <= 'Z')||LA4_26=='_'||(LA4_26 >= 'a' && LA4_26 <= 'z')) ) {
                                alt4=10;
                            }
                            else {
                                alt4=6;
                            }
                        }
                        else {
                            alt4=10;
                        }
                    }
                    else {
                        alt4=10;
                    }
                }
                else {
                    alt4=10;
                }
            }
            else {
                alt4=10;
            }
            }
            break;
        case 's':
            {
            int LA4_7 = input.LA(2);

            if ( (LA4_7=='a') ) {
                int LA4_15 = input.LA(3);

                if ( (LA4_15=='t') ) {
                    int LA4_19 = input.LA(4);

                    if ( ((LA4_19 >= '0' && LA4_19 <= '9')||(LA4_19 >= '@' && LA4_19 <= 'Z')||LA4_19=='_'||(LA4_19 >= 'a' && LA4_19 <= 'z')) ) {
                        alt4=10;
                    }
                    else {
                        alt4=7;
                    }
                }
                else {
                    alt4=10;
                }
            }
            else {
                alt4=10;
            }
            }
            break;
        case 't':
            {
            int LA4_8 = input.LA(2);

            if ( (LA4_8=='r') ) {
                int LA4_16 = input.LA(3);

                if ( (LA4_16=='u') ) {
                    int LA4_20 = input.LA(4);

                    if ( (LA4_20=='e') ) {
                        int LA4_24 = input.LA(5);

                        if ( ((LA4_24 >= '0' && LA4_24 <= '9')||(LA4_24 >= '@' && LA4_24 <= 'Z')||LA4_24=='_'||(LA4_24 >= 'a' && LA4_24 <= 'z')) ) {
                            alt4=10;
                        }
                        else {
                            alt4=8;
                        }
                    }
                    else {
                        alt4=10;
                    }
                }
                else {
                    alt4=10;
                }
            }
            else {
                alt4=10;
            }
            }
            break;
        case 'u':
            {
            int LA4_9 = input.LA(2);

            if ( (LA4_9=='n') ) {
                int LA4_17 = input.LA(3);

                if ( (LA4_17=='s') ) {
                    int LA4_21 = input.LA(4);

                    if ( (LA4_21=='a') ) {
                        int LA4_25 = input.LA(5);

                        if ( (LA4_25=='t') ) {
                            int LA4_28 = input.LA(6);

                            if ( ((LA4_28 >= '0' && LA4_28 <= '9')||(LA4_28 >= '@' && LA4_28 <= 'Z')||LA4_28=='_'||(LA4_28 >= 'a' && LA4_28 <= 'z')) ) {
                                alt4=10;
                            }
                            else {
                                alt4=9;
                            }
                        }
                        else {
                            alt4=10;
                        }
                    }
                    else {
                        alt4=10;
                    }
                }
                else {
                    alt4=10;
                }
            }
            else {
                alt4=10;
            }
            }
            break;
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
        case 'd':
        case 'e':
        case 'g':
        case 'h':
        case 'i':
        case 'j':
        case 'k':
        case 'l':
        case 'm':
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
            alt4=10;
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
            alt4=11;
            }
            break;
        case '\t':
        case '\n':
        case '\f':
        case '\r':
        case ' ':
            {
            alt4=12;
            }
            break;
        case '.':
            {
            alt4=13;
            }
            break;
        default:
            NoViableAltException nvae =
                new NoViableAltException("", 4, 0, input);

            throw nvae;

        }

        switch (alt4) {
            case 1 :
                // Yices.g:1:10: T__10
                {
                mT__10(); 


                }
                break;
            case 2 :
                // Yices.g:1:16: T__11
                {
                mT__11(); 


                }
                break;
            case 3 :
                // Yices.g:1:22: T__12
                {
                mT__12(); 


                }
                break;
            case 4 :
                // Yices.g:1:28: T__13
                {
                mT__13(); 


                }
                break;
            case 5 :
                // Yices.g:1:34: T__14
                {
                mT__14(); 


                }
                break;
            case 6 :
                // Yices.g:1:40: T__15
                {
                mT__15(); 


                }
                break;
            case 7 :
                // Yices.g:1:46: T__16
                {
                mT__16(); 


                }
                break;
            case 8 :
                // Yices.g:1:52: T__17
                {
                mT__17(); 


                }
                break;
            case 9 :
                // Yices.g:1:58: T__18
                {
                mT__18(); 


                }
                break;
            case 10 :
                // Yices.g:1:64: ID
                {
                mID(); 


                }
                break;
            case 11 :
                // Yices.g:1:67: INT
                {
                mINT(); 


                }
                break;
            case 12 :
                // Yices.g:1:71: WS
                {
                mWS(); 


                }
                break;
            case 13 :
                // Yices.g:1:74: ERROR
                {
                mERROR(); 


                }
                break;

        }

    }


 

}