// $ANTLR 3.4 Lustre.g 2012-10-27 07:23:36

  package jkind.lustre;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked"})
public class LustreLexer extends Lexer {
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
    public static final int T__20=20;
    public static final int T__21=21;
    public static final int T__22=22;
    public static final int T__23=23;
    public static final int T__24=24;
    public static final int T__25=25;
    public static final int T__26=26;
    public static final int T__27=27;
    public static final int T__28=28;
    public static final int T__29=29;
    public static final int T__30=30;
    public static final int T__31=31;
    public static final int T__32=32;
    public static final int T__33=33;
    public static final int T__34=34;
    public static final int T__35=35;
    public static final int T__36=36;
    public static final int T__37=37;
    public static final int T__38=38;
    public static final int T__39=39;
    public static final int T__40=40;
    public static final int T__41=41;
    public static final int T__42=42;
    public static final int T__43=43;
    public static final int T__44=44;
    public static final int T__45=45;
    public static final int T__46=46;
    public static final int ERROR=4;
    public static final int ID=5;
    public static final int INT=6;
    public static final int MAIN=7;
    public static final int ML_COMMENT=8;
    public static final int SL_COMMENT=9;
    public static final int WS=10;

      protected void ignore(Stack<Void> stack, List<Void> list, ArrayList<Void> arraylist) {}


    // delegates
    // delegators
    public Lexer[] getDelegates() {
        return new Lexer[] {};
    }

    public LustreLexer() {} 
    public LustreLexer(CharStream input) {
        this(input, new RecognizerSharedState());
    }
    public LustreLexer(CharStream input, RecognizerSharedState state) {
        super(input,state);
    }
    public String getGrammarFileName() { return "Lustre.g"; }

    // $ANTLR start "T__11"
    public final void mT__11() throws RecognitionException {
        try {
            int _type = T__11;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Lustre.g:14:7: ( '(' )
            // Lustre.g:14:9: '('
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
            // Lustre.g:15:7: ( ')' )
            // Lustre.g:15:9: ')'
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
            // Lustre.g:16:7: ( '*' )
            // Lustre.g:16:9: '*'
            {
            match('*'); 

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
            // Lustre.g:17:7: ( '+' )
            // Lustre.g:17:9: '+'
            {
            match('+'); 

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
            // Lustre.g:18:7: ( ',' )
            // Lustre.g:18:9: ','
            {
            match(','); 

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
            // Lustre.g:19:7: ( '-' )
            // Lustre.g:19:9: '-'
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
    // $ANTLR end "T__16"

    // $ANTLR start "T__17"
    public final void mT__17() throws RecognitionException {
        try {
            int _type = T__17;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Lustre.g:20:7: ( '--%PROPERTY' )
            // Lustre.g:20:9: '--%PROPERTY'
            {
            match("--%PROPERTY"); 



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
            // Lustre.g:21:7: ( '->' )
            // Lustre.g:21:9: '->'
            {
            match("->"); 



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
            // Lustre.g:22:7: ( '/' )
            // Lustre.g:22:9: '/'
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
    // $ANTLR end "T__19"

    // $ANTLR start "T__20"
    public final void mT__20() throws RecognitionException {
        try {
            int _type = T__20;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Lustre.g:23:7: ( ':' )
            // Lustre.g:23:9: ':'
            {
            match(':'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__20"

    // $ANTLR start "T__21"
    public final void mT__21() throws RecognitionException {
        try {
            int _type = T__21;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Lustre.g:24:7: ( ';' )
            // Lustre.g:24:9: ';'
            {
            match(';'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__21"

    // $ANTLR start "T__22"
    public final void mT__22() throws RecognitionException {
        try {
            int _type = T__22;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Lustre.g:25:7: ( '<' )
            // Lustre.g:25:9: '<'
            {
            match('<'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__22"

    // $ANTLR start "T__23"
    public final void mT__23() throws RecognitionException {
        try {
            int _type = T__23;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Lustre.g:26:7: ( '<=' )
            // Lustre.g:26:9: '<='
            {
            match("<="); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__23"

    // $ANTLR start "T__24"
    public final void mT__24() throws RecognitionException {
        try {
            int _type = T__24;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Lustre.g:27:7: ( '<>' )
            // Lustre.g:27:9: '<>'
            {
            match("<>"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__24"

    // $ANTLR start "T__25"
    public final void mT__25() throws RecognitionException {
        try {
            int _type = T__25;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Lustre.g:28:7: ( '=' )
            // Lustre.g:28:9: '='
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
    // $ANTLR end "T__25"

    // $ANTLR start "T__26"
    public final void mT__26() throws RecognitionException {
        try {
            int _type = T__26;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Lustre.g:29:7: ( '=>' )
            // Lustre.g:29:9: '=>'
            {
            match("=>"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__26"

    // $ANTLR start "T__27"
    public final void mT__27() throws RecognitionException {
        try {
            int _type = T__27;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Lustre.g:30:7: ( '>' )
            // Lustre.g:30:9: '>'
            {
            match('>'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__27"

    // $ANTLR start "T__28"
    public final void mT__28() throws RecognitionException {
        try {
            int _type = T__28;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Lustre.g:31:7: ( '>=' )
            // Lustre.g:31:9: '>='
            {
            match(">="); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__28"

    // $ANTLR start "T__29"
    public final void mT__29() throws RecognitionException {
        try {
            int _type = T__29;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Lustre.g:32:7: ( 'and' )
            // Lustre.g:32:9: 'and'
            {
            match("and"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__29"

    // $ANTLR start "T__30"
    public final void mT__30() throws RecognitionException {
        try {
            int _type = T__30;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Lustre.g:33:7: ( 'bool' )
            // Lustre.g:33:9: 'bool'
            {
            match("bool"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__30"

    // $ANTLR start "T__31"
    public final void mT__31() throws RecognitionException {
        try {
            int _type = T__31;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Lustre.g:34:7: ( 'else' )
            // Lustre.g:34:9: 'else'
            {
            match("else"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__31"

    // $ANTLR start "T__32"
    public final void mT__32() throws RecognitionException {
        try {
            int _type = T__32;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Lustre.g:35:7: ( 'false' )
            // Lustre.g:35:9: 'false'
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
    // $ANTLR end "T__32"

    // $ANTLR start "T__33"
    public final void mT__33() throws RecognitionException {
        try {
            int _type = T__33;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Lustre.g:36:7: ( 'if' )
            // Lustre.g:36:9: 'if'
            {
            match("if"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__33"

    // $ANTLR start "T__34"
    public final void mT__34() throws RecognitionException {
        try {
            int _type = T__34;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Lustre.g:37:7: ( 'int' )
            // Lustre.g:37:9: 'int'
            {
            match("int"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__34"

    // $ANTLR start "T__35"
    public final void mT__35() throws RecognitionException {
        try {
            int _type = T__35;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Lustre.g:38:7: ( 'let' )
            // Lustre.g:38:9: 'let'
            {
            match("let"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__35"

    // $ANTLR start "T__36"
    public final void mT__36() throws RecognitionException {
        try {
            int _type = T__36;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Lustre.g:39:7: ( 'node' )
            // Lustre.g:39:9: 'node'
            {
            match("node"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__36"

    // $ANTLR start "T__37"
    public final void mT__37() throws RecognitionException {
        try {
            int _type = T__37;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Lustre.g:40:7: ( 'not' )
            // Lustre.g:40:9: 'not'
            {
            match("not"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__37"

    // $ANTLR start "T__38"
    public final void mT__38() throws RecognitionException {
        try {
            int _type = T__38;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Lustre.g:41:7: ( 'or' )
            // Lustre.g:41:9: 'or'
            {
            match("or"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__38"

    // $ANTLR start "T__39"
    public final void mT__39() throws RecognitionException {
        try {
            int _type = T__39;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Lustre.g:42:7: ( 'pre' )
            // Lustre.g:42:9: 'pre'
            {
            match("pre"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__39"

    // $ANTLR start "T__40"
    public final void mT__40() throws RecognitionException {
        try {
            int _type = T__40;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Lustre.g:43:7: ( 'real' )
            // Lustre.g:43:9: 'real'
            {
            match("real"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__40"

    // $ANTLR start "T__41"
    public final void mT__41() throws RecognitionException {
        try {
            int _type = T__41;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Lustre.g:44:7: ( 'returns' )
            // Lustre.g:44:9: 'returns'
            {
            match("returns"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__41"

    // $ANTLR start "T__42"
    public final void mT__42() throws RecognitionException {
        try {
            int _type = T__42;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Lustre.g:45:7: ( 'tel' )
            // Lustre.g:45:9: 'tel'
            {
            match("tel"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__42"

    // $ANTLR start "T__43"
    public final void mT__43() throws RecognitionException {
        try {
            int _type = T__43;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Lustre.g:46:7: ( 'then' )
            // Lustre.g:46:9: 'then'
            {
            match("then"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__43"

    // $ANTLR start "T__44"
    public final void mT__44() throws RecognitionException {
        try {
            int _type = T__44;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Lustre.g:47:7: ( 'true' )
            // Lustre.g:47:9: 'true'
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
    // $ANTLR end "T__44"

    // $ANTLR start "T__45"
    public final void mT__45() throws RecognitionException {
        try {
            int _type = T__45;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Lustre.g:48:7: ( 'var' )
            // Lustre.g:48:9: 'var'
            {
            match("var"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__45"

    // $ANTLR start "T__46"
    public final void mT__46() throws RecognitionException {
        try {
            int _type = T__46;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Lustre.g:49:7: ( 'xor' )
            // Lustre.g:49:9: 'xor'
            {
            match("xor"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__46"

    // $ANTLR start "INT"
    public final void mINT() throws RecognitionException {
        try {
            int _type = INT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Lustre.g:197:4: ( ( '0' .. '9' )+ )
            // Lustre.g:197:6: ( '0' .. '9' )+
            {
            // Lustre.g:197:6: ( '0' .. '9' )+
            int cnt1=0;
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( ((LA1_0 >= '0' && LA1_0 <= '9')) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // Lustre.g:
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
            	    if ( cnt1 >= 1 ) break loop1;
                        EarlyExitException eee =
                            new EarlyExitException(1, input);
                        throw eee;
                }
                cnt1++;
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

    // $ANTLR start "ID"
    public final void mID() throws RecognitionException {
        try {
            int _type = ID;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Lustre.g:198:3: ( ( 'a' .. 'z' | 'A' .. 'Z' | '_' ) ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '0' .. '9' )* )
            // Lustre.g:199:3: ( 'a' .. 'z' | 'A' .. 'Z' | '_' ) ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '0' .. '9' )*
            {
            if ( (input.LA(1) >= 'A' && input.LA(1) <= 'Z')||input.LA(1)=='_'||(input.LA(1) >= 'a' && input.LA(1) <= 'z') ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            // Lustre.g:199:27: ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '0' .. '9' )*
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( ((LA2_0 >= '0' && LA2_0 <= '9')||(LA2_0 >= 'A' && LA2_0 <= 'Z')||LA2_0=='_'||(LA2_0 >= 'a' && LA2_0 <= 'z')) ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // Lustre.g:
            	    {
            	    if ( (input.LA(1) >= '0' && input.LA(1) <= '9')||(input.LA(1) >= 'A' && input.LA(1) <= 'Z')||input.LA(1)=='_'||(input.LA(1) >= 'a' && input.LA(1) <= 'z') ) {
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

    // $ANTLR start "WS"
    public final void mWS() throws RecognitionException {
        try {
            int _type = WS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Lustre.g:202:3: ( ( ' ' | '\\t' | '\\n' | '\\r' | '\\f' )+ )
            // Lustre.g:202:5: ( ' ' | '\\t' | '\\n' | '\\r' | '\\f' )+
            {
            // Lustre.g:202:5: ( ' ' | '\\t' | '\\n' | '\\r' | '\\f' )+
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
            	    // Lustre.g:
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

    // $ANTLR start "SL_COMMENT"
    public final void mSL_COMMENT() throws RecognitionException {
        try {
            int _type = SL_COMMENT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Lustre.g:204:11: ( '--' (~ ( '%' | '\\n' | '\\r' ) (~ ( '\\n' | '\\r' ) )* |) ( ( '\\r' )? '\\n' )? )
            // Lustre.g:204:13: '--' (~ ( '%' | '\\n' | '\\r' ) (~ ( '\\n' | '\\r' ) )* |) ( ( '\\r' )? '\\n' )?
            {
            match("--"); 



            // Lustre.g:204:18: (~ ( '%' | '\\n' | '\\r' ) (~ ( '\\n' | '\\r' ) )* |)
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( ((LA5_0 >= '\u0000' && LA5_0 <= '\t')||(LA5_0 >= '\u000B' && LA5_0 <= '\f')||(LA5_0 >= '\u000E' && LA5_0 <= '$')||(LA5_0 >= '&' && LA5_0 <= '\uFFFF')) ) {
                alt5=1;
            }
            else {
                alt5=2;
            }
            switch (alt5) {
                case 1 :
                    // Lustre.g:204:19: ~ ( '%' | '\\n' | '\\r' ) (~ ( '\\n' | '\\r' ) )*
                    {
                    if ( (input.LA(1) >= '\u0000' && input.LA(1) <= '\t')||(input.LA(1) >= '\u000B' && input.LA(1) <= '\f')||(input.LA(1) >= '\u000E' && input.LA(1) <= '$')||(input.LA(1) >= '&' && input.LA(1) <= '\uFFFF') ) {
                        input.consume();
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        recover(mse);
                        throw mse;
                    }


                    // Lustre.g:204:36: (~ ( '\\n' | '\\r' ) )*
                    loop4:
                    do {
                        int alt4=2;
                        int LA4_0 = input.LA(1);

                        if ( ((LA4_0 >= '\u0000' && LA4_0 <= '\t')||(LA4_0 >= '\u000B' && LA4_0 <= '\f')||(LA4_0 >= '\u000E' && LA4_0 <= '\uFFFF')) ) {
                            alt4=1;
                        }


                        switch (alt4) {
                    	case 1 :
                    	    // Lustre.g:
                    	    {
                    	    if ( (input.LA(1) >= '\u0000' && input.LA(1) <= '\t')||(input.LA(1) >= '\u000B' && input.LA(1) <= '\f')||(input.LA(1) >= '\u000E' && input.LA(1) <= '\uFFFF') ) {
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
                    	    break loop4;
                        }
                    } while (true);


                    }
                    break;
                case 2 :
                    // Lustre.g:204:63: 
                    {
                    }
                    break;

            }


            // Lustre.g:204:65: ( ( '\\r' )? '\\n' )?
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( (LA7_0=='\n'||LA7_0=='\r') ) {
                alt7=1;
            }
            switch (alt7) {
                case 1 :
                    // Lustre.g:204:66: ( '\\r' )? '\\n'
                    {
                    // Lustre.g:204:66: ( '\\r' )?
                    int alt6=2;
                    int LA6_0 = input.LA(1);

                    if ( (LA6_0=='\r') ) {
                        alt6=1;
                    }
                    switch (alt6) {
                        case 1 :
                            // Lustre.g:204:66: '\\r'
                            {
                            match('\r'); 

                            }
                            break;

                    }


                    match('\n'); 

                    }
                    break;

            }


            _channel=HIDDEN;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "SL_COMMENT"

    // $ANTLR start "ML_COMMENT"
    public final void mML_COMMENT() throws RecognitionException {
        try {
            int _type = ML_COMMENT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Lustre.g:205:11: ( '/*' ( options {greedy=false; } : . )* '*/' )
            // Lustre.g:205:13: '/*' ( options {greedy=false; } : . )* '*/'
            {
            match("/*"); 



            // Lustre.g:205:18: ( options {greedy=false; } : . )*
            loop8:
            do {
                int alt8=2;
                int LA8_0 = input.LA(1);

                if ( (LA8_0=='*') ) {
                    int LA8_1 = input.LA(2);

                    if ( (LA8_1=='/') ) {
                        alt8=2;
                    }
                    else if ( ((LA8_1 >= '\u0000' && LA8_1 <= '.')||(LA8_1 >= '0' && LA8_1 <= '\uFFFF')) ) {
                        alt8=1;
                    }


                }
                else if ( ((LA8_0 >= '\u0000' && LA8_0 <= ')')||(LA8_0 >= '+' && LA8_0 <= '\uFFFF')) ) {
                    alt8=1;
                }


                switch (alt8) {
            	case 1 :
            	    // Lustre.g:205:45: .
            	    {
            	    matchAny(); 

            	    }
            	    break;

            	default :
            	    break loop8;
                }
            } while (true);


            match("*/"); 



            _channel=HIDDEN;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "ML_COMMENT"

    // $ANTLR start "MAIN"
    public final void mMAIN() throws RecognitionException {
        try {
            int _type = MAIN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Lustre.g:206:5: ( '--%MAIN' ( ';' )? )
            // Lustre.g:206:7: '--%MAIN' ( ';' )?
            {
            match("--%MAIN"); 



            // Lustre.g:206:17: ( ';' )?
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( (LA9_0==';') ) {
                alt9=1;
            }
            switch (alt9) {
                case 1 :
                    // Lustre.g:206:17: ';'
                    {
                    match(';'); 

                    }
                    break;

            }


            _channel=HIDDEN;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "MAIN"

    // $ANTLR start "ERROR"
    public final void mERROR() throws RecognitionException {
        try {
            int _type = ERROR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Lustre.g:208:6: ( '.' )
            // Lustre.g:208:8: '.'
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
        // Lustre.g:1:8: ( T__11 | T__12 | T__13 | T__14 | T__15 | T__16 | T__17 | T__18 | T__19 | T__20 | T__21 | T__22 | T__23 | T__24 | T__25 | T__26 | T__27 | T__28 | T__29 | T__30 | T__31 | T__32 | T__33 | T__34 | T__35 | T__36 | T__37 | T__38 | T__39 | T__40 | T__41 | T__42 | T__43 | T__44 | T__45 | T__46 | INT | ID | WS | SL_COMMENT | ML_COMMENT | MAIN | ERROR )
        int alt10=43;
        alt10 = dfa10.predict(input);
        switch (alt10) {
            case 1 :
                // Lustre.g:1:10: T__11
                {
                mT__11(); 


                }
                break;
            case 2 :
                // Lustre.g:1:16: T__12
                {
                mT__12(); 


                }
                break;
            case 3 :
                // Lustre.g:1:22: T__13
                {
                mT__13(); 


                }
                break;
            case 4 :
                // Lustre.g:1:28: T__14
                {
                mT__14(); 


                }
                break;
            case 5 :
                // Lustre.g:1:34: T__15
                {
                mT__15(); 


                }
                break;
            case 6 :
                // Lustre.g:1:40: T__16
                {
                mT__16(); 


                }
                break;
            case 7 :
                // Lustre.g:1:46: T__17
                {
                mT__17(); 


                }
                break;
            case 8 :
                // Lustre.g:1:52: T__18
                {
                mT__18(); 


                }
                break;
            case 9 :
                // Lustre.g:1:58: T__19
                {
                mT__19(); 


                }
                break;
            case 10 :
                // Lustre.g:1:64: T__20
                {
                mT__20(); 


                }
                break;
            case 11 :
                // Lustre.g:1:70: T__21
                {
                mT__21(); 


                }
                break;
            case 12 :
                // Lustre.g:1:76: T__22
                {
                mT__22(); 


                }
                break;
            case 13 :
                // Lustre.g:1:82: T__23
                {
                mT__23(); 


                }
                break;
            case 14 :
                // Lustre.g:1:88: T__24
                {
                mT__24(); 


                }
                break;
            case 15 :
                // Lustre.g:1:94: T__25
                {
                mT__25(); 


                }
                break;
            case 16 :
                // Lustre.g:1:100: T__26
                {
                mT__26(); 


                }
                break;
            case 17 :
                // Lustre.g:1:106: T__27
                {
                mT__27(); 


                }
                break;
            case 18 :
                // Lustre.g:1:112: T__28
                {
                mT__28(); 


                }
                break;
            case 19 :
                // Lustre.g:1:118: T__29
                {
                mT__29(); 


                }
                break;
            case 20 :
                // Lustre.g:1:124: T__30
                {
                mT__30(); 


                }
                break;
            case 21 :
                // Lustre.g:1:130: T__31
                {
                mT__31(); 


                }
                break;
            case 22 :
                // Lustre.g:1:136: T__32
                {
                mT__32(); 


                }
                break;
            case 23 :
                // Lustre.g:1:142: T__33
                {
                mT__33(); 


                }
                break;
            case 24 :
                // Lustre.g:1:148: T__34
                {
                mT__34(); 


                }
                break;
            case 25 :
                // Lustre.g:1:154: T__35
                {
                mT__35(); 


                }
                break;
            case 26 :
                // Lustre.g:1:160: T__36
                {
                mT__36(); 


                }
                break;
            case 27 :
                // Lustre.g:1:166: T__37
                {
                mT__37(); 


                }
                break;
            case 28 :
                // Lustre.g:1:172: T__38
                {
                mT__38(); 


                }
                break;
            case 29 :
                // Lustre.g:1:178: T__39
                {
                mT__39(); 


                }
                break;
            case 30 :
                // Lustre.g:1:184: T__40
                {
                mT__40(); 


                }
                break;
            case 31 :
                // Lustre.g:1:190: T__41
                {
                mT__41(); 


                }
                break;
            case 32 :
                // Lustre.g:1:196: T__42
                {
                mT__42(); 


                }
                break;
            case 33 :
                // Lustre.g:1:202: T__43
                {
                mT__43(); 


                }
                break;
            case 34 :
                // Lustre.g:1:208: T__44
                {
                mT__44(); 


                }
                break;
            case 35 :
                // Lustre.g:1:214: T__45
                {
                mT__45(); 


                }
                break;
            case 36 :
                // Lustre.g:1:220: T__46
                {
                mT__46(); 


                }
                break;
            case 37 :
                // Lustre.g:1:226: INT
                {
                mINT(); 


                }
                break;
            case 38 :
                // Lustre.g:1:230: ID
                {
                mID(); 


                }
                break;
            case 39 :
                // Lustre.g:1:233: WS
                {
                mWS(); 


                }
                break;
            case 40 :
                // Lustre.g:1:236: SL_COMMENT
                {
                mSL_COMMENT(); 


                }
                break;
            case 41 :
                // Lustre.g:1:247: ML_COMMENT
                {
                mML_COMMENT(); 


                }
                break;
            case 42 :
                // Lustre.g:1:258: MAIN
                {
                mMAIN(); 


                }
                break;
            case 43 :
                // Lustre.g:1:263: ERROR
                {
                mERROR(); 


                }
                break;

        }

    }


    protected DFA10 dfa10 = new DFA10(this);
    static final String DFA10_eotS =
        "\6\uffff\1\40\1\42\2\uffff\1\45\1\47\1\51\15\33\4\uffff\1\73\13"+
        "\uffff\4\33\1\100\3\33\1\105\7\33\2\uffff\1\120\3\33\1\uffff\1\124"+
        "\1\125\1\33\1\127\1\uffff\1\130\2\33\1\133\2\33\1\136\1\137\3\uffff"+
        "\1\140\1\141\1\33\2\uffff\1\143\2\uffff\1\144\1\33\1\uffff\1\146"+
        "\1\147\4\uffff\1\150\2\uffff\1\33\3\uffff\1\33\1\153\1\uffff";
    static final String DFA10_eofS =
        "\154\uffff";
    static final String DFA10_minS =
        "\1\11\5\uffff\1\55\1\52\2\uffff\1\75\1\76\1\75\1\156\1\157\1\154"+
        "\1\141\1\146\1\145\1\157\2\162\2\145\1\141\1\157\4\uffff\1\45\13"+
        "\uffff\1\144\1\157\1\163\1\154\1\60\2\164\1\144\1\60\1\145\1\141"+
        "\1\154\1\145\1\165\2\162\1\115\1\uffff\1\60\1\154\1\145\1\163\1"+
        "\uffff\2\60\1\145\1\60\1\uffff\1\60\1\154\1\165\1\60\1\156\1\145"+
        "\2\60\3\uffff\2\60\1\145\2\uffff\1\60\2\uffff\1\60\1\162\1\uffff"+
        "\2\60\4\uffff\1\60\2\uffff\1\156\3\uffff\1\163\1\60\1\uffff";
    static final String DFA10_maxS =
        "\1\172\5\uffff\1\76\1\52\2\uffff\2\76\1\75\1\156\1\157\1\154\1\141"+
        "\1\156\1\145\1\157\2\162\1\145\1\162\1\141\1\157\4\uffff\1\45\13"+
        "\uffff\1\144\1\157\1\163\1\154\1\172\3\164\1\172\1\145\1\164\1\154"+
        "\1\145\1\165\2\162\1\120\1\uffff\1\172\1\154\1\145\1\163\1\uffff"+
        "\2\172\1\145\1\172\1\uffff\1\172\1\154\1\165\1\172\1\156\1\145\2"+
        "\172\3\uffff\2\172\1\145\2\uffff\1\172\2\uffff\1\172\1\162\1\uffff"+
        "\2\172\4\uffff\1\172\2\uffff\1\156\3\uffff\1\163\1\172\1\uffff";
    static final String DFA10_acceptS =
        "\1\uffff\1\1\1\2\1\3\1\4\1\5\2\uffff\1\12\1\13\20\uffff\1\45\1\46"+
        "\1\47\1\53\1\uffff\1\10\1\6\1\51\1\11\1\15\1\16\1\14\1\20\1\17\1"+
        "\22\1\21\21\uffff\1\50\4\uffff\1\27\4\uffff\1\34\10\uffff\1\7\1"+
        "\52\1\23\3\uffff\1\30\1\31\1\uffff\1\33\1\35\2\uffff\1\40\2\uffff"+
        "\1\43\1\44\1\24\1\25\1\uffff\1\32\1\36\1\uffff\1\41\1\42\1\26\2"+
        "\uffff\1\37";
    static final String DFA10_specialS =
        "\154\uffff}>";
    static final String[] DFA10_transitionS = {
            "\2\34\1\uffff\2\34\22\uffff\1\34\7\uffff\1\1\1\2\1\3\1\4\1\5"+
            "\1\6\1\35\1\7\12\32\1\10\1\11\1\12\1\13\1\14\2\uffff\32\33\4"+
            "\uffff\1\33\1\uffff\1\15\1\16\2\33\1\17\1\20\2\33\1\21\2\33"+
            "\1\22\1\33\1\23\1\24\1\25\1\33\1\26\1\33\1\27\1\33\1\30\1\33"+
            "\1\31\2\33",
            "",
            "",
            "",
            "",
            "",
            "\1\36\20\uffff\1\37",
            "\1\41",
            "",
            "",
            "\1\43\1\44",
            "\1\46",
            "\1\50",
            "\1\52",
            "\1\53",
            "\1\54",
            "\1\55",
            "\1\56\7\uffff\1\57",
            "\1\60",
            "\1\61",
            "\1\62",
            "\1\63",
            "\1\64",
            "\1\65\2\uffff\1\66\11\uffff\1\67",
            "\1\70",
            "\1\71",
            "",
            "",
            "",
            "",
            "\1\72",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\74",
            "\1\75",
            "\1\76",
            "\1\77",
            "\12\33\7\uffff\32\33\4\uffff\1\33\1\uffff\32\33",
            "\1\101",
            "\1\102",
            "\1\103\17\uffff\1\104",
            "\12\33\7\uffff\32\33\4\uffff\1\33\1\uffff\32\33",
            "\1\106",
            "\1\107\22\uffff\1\110",
            "\1\111",
            "\1\112",
            "\1\113",
            "\1\114",
            "\1\115",
            "\1\117\2\uffff\1\116",
            "",
            "\12\33\7\uffff\32\33\4\uffff\1\33\1\uffff\32\33",
            "\1\121",
            "\1\122",
            "\1\123",
            "",
            "\12\33\7\uffff\32\33\4\uffff\1\33\1\uffff\32\33",
            "\12\33\7\uffff\32\33\4\uffff\1\33\1\uffff\32\33",
            "\1\126",
            "\12\33\7\uffff\32\33\4\uffff\1\33\1\uffff\32\33",
            "",
            "\12\33\7\uffff\32\33\4\uffff\1\33\1\uffff\32\33",
            "\1\131",
            "\1\132",
            "\12\33\7\uffff\32\33\4\uffff\1\33\1\uffff\32\33",
            "\1\134",
            "\1\135",
            "\12\33\7\uffff\32\33\4\uffff\1\33\1\uffff\32\33",
            "\12\33\7\uffff\32\33\4\uffff\1\33\1\uffff\32\33",
            "",
            "",
            "",
            "\12\33\7\uffff\32\33\4\uffff\1\33\1\uffff\32\33",
            "\12\33\7\uffff\32\33\4\uffff\1\33\1\uffff\32\33",
            "\1\142",
            "",
            "",
            "\12\33\7\uffff\32\33\4\uffff\1\33\1\uffff\32\33",
            "",
            "",
            "\12\33\7\uffff\32\33\4\uffff\1\33\1\uffff\32\33",
            "\1\145",
            "",
            "\12\33\7\uffff\32\33\4\uffff\1\33\1\uffff\32\33",
            "\12\33\7\uffff\32\33\4\uffff\1\33\1\uffff\32\33",
            "",
            "",
            "",
            "",
            "\12\33\7\uffff\32\33\4\uffff\1\33\1\uffff\32\33",
            "",
            "",
            "\1\151",
            "",
            "",
            "",
            "\1\152",
            "\12\33\7\uffff\32\33\4\uffff\1\33\1\uffff\32\33",
            ""
    };

    static final short[] DFA10_eot = DFA.unpackEncodedString(DFA10_eotS);
    static final short[] DFA10_eof = DFA.unpackEncodedString(DFA10_eofS);
    static final char[] DFA10_min = DFA.unpackEncodedStringToUnsignedChars(DFA10_minS);
    static final char[] DFA10_max = DFA.unpackEncodedStringToUnsignedChars(DFA10_maxS);
    static final short[] DFA10_accept = DFA.unpackEncodedString(DFA10_acceptS);
    static final short[] DFA10_special = DFA.unpackEncodedString(DFA10_specialS);
    static final short[][] DFA10_transition;

    static {
        int numStates = DFA10_transitionS.length;
        DFA10_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA10_transition[i] = DFA.unpackEncodedString(DFA10_transitionS[i]);
        }
    }

    class DFA10 extends DFA {

        public DFA10(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 10;
            this.eot = DFA10_eot;
            this.eof = DFA10_eof;
            this.min = DFA10_min;
            this.max = DFA10_max;
            this.accept = DFA10_accept;
            this.special = DFA10_special;
            this.transition = DFA10_transition;
        }
        public String getDescription() {
            return "1:1: Tokens : ( T__11 | T__12 | T__13 | T__14 | T__15 | T__16 | T__17 | T__18 | T__19 | T__20 | T__21 | T__22 | T__23 | T__24 | T__25 | T__26 | T__27 | T__28 | T__29 | T__30 | T__31 | T__32 | T__33 | T__34 | T__35 | T__36 | T__37 | T__38 | T__39 | T__40 | T__41 | T__42 | T__43 | T__44 | T__45 | T__46 | INT | ID | WS | SL_COMMENT | ML_COMMENT | MAIN | ERROR );";
        }
    }
 

}