// $ANTLR 3.4 Lustre.g 2012-11-01 19:55:44

  package jkind.lustre;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked"})
public class LustreLexer extends Lexer {
    public static final int EOF=-1;
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
    public static final int T__47=47;
    public static final int T__48=48;
    public static final int T__49=49;
    public static final int T__50=50;
    public static final int T__51=51;
    public static final int T__52=52;
    public static final int T__53=53;
    public static final int T__54=54;
    public static final int T__55=55;
    public static final int T__56=56;
    public static final int T__57=57;
    public static final int T__58=58;
    public static final int T__59=59;
    public static final int T__60=60;
    public static final int T__61=61;
    public static final int T__62=62;
    public static final int T__63=63;
    public static final int T__64=64;
    public static final int T__65=65;
    public static final int T__66=66;
    public static final int T__67=67;
    public static final int BOOL=4;
    public static final int CONSTANTS=5;
    public static final int EQUATION=6;
    public static final int EQUATIONS=7;
    public static final int ERROR=8;
    public static final int ID=9;
    public static final int IDENT=10;
    public static final int IF=11;
    public static final int INPUTS=12;
    public static final int INT=13;
    public static final int LHS=14;
    public static final int LOCALS=15;
    public static final int MAIN=16;
    public static final int ML_COMMENT=17;
    public static final int NEGATE=18;
    public static final int NODECALL=19;
    public static final int NODES=20;
    public static final int NOT=21;
    public static final int OUTPUTS=22;
    public static final int PRE=23;
    public static final int PROGRAM=24;
    public static final int PROPERTIES=25;
    public static final int REAL=26;
    public static final int SL_COMMENT=27;
    public static final int TYPES=28;
    public static final int WS=29;

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

    // $ANTLR start "T__30"
    public final void mT__30() throws RecognitionException {
        try {
            int _type = T__30;
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
    // $ANTLR end "T__30"

    // $ANTLR start "T__31"
    public final void mT__31() throws RecognitionException {
        try {
            int _type = T__31;
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
    // $ANTLR end "T__31"

    // $ANTLR start "T__32"
    public final void mT__32() throws RecognitionException {
        try {
            int _type = T__32;
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
    // $ANTLR end "T__32"

    // $ANTLR start "T__33"
    public final void mT__33() throws RecognitionException {
        try {
            int _type = T__33;
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
    // $ANTLR end "T__33"

    // $ANTLR start "T__34"
    public final void mT__34() throws RecognitionException {
        try {
            int _type = T__34;
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
    // $ANTLR end "T__34"

    // $ANTLR start "T__35"
    public final void mT__35() throws RecognitionException {
        try {
            int _type = T__35;
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
    // $ANTLR end "T__35"

    // $ANTLR start "T__36"
    public final void mT__36() throws RecognitionException {
        try {
            int _type = T__36;
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
    // $ANTLR end "T__36"

    // $ANTLR start "T__37"
    public final void mT__37() throws RecognitionException {
        try {
            int _type = T__37;
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
    // $ANTLR end "T__37"

    // $ANTLR start "T__38"
    public final void mT__38() throws RecognitionException {
        try {
            int _type = T__38;
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
    // $ANTLR end "T__38"

    // $ANTLR start "T__39"
    public final void mT__39() throws RecognitionException {
        try {
            int _type = T__39;
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
    // $ANTLR end "T__39"

    // $ANTLR start "T__40"
    public final void mT__40() throws RecognitionException {
        try {
            int _type = T__40;
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
    // $ANTLR end "T__40"

    // $ANTLR start "T__41"
    public final void mT__41() throws RecognitionException {
        try {
            int _type = T__41;
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
    // $ANTLR end "T__41"

    // $ANTLR start "T__42"
    public final void mT__42() throws RecognitionException {
        try {
            int _type = T__42;
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
    // $ANTLR end "T__42"

    // $ANTLR start "T__43"
    public final void mT__43() throws RecognitionException {
        try {
            int _type = T__43;
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
    // $ANTLR end "T__43"

    // $ANTLR start "T__44"
    public final void mT__44() throws RecognitionException {
        try {
            int _type = T__44;
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
    // $ANTLR end "T__44"

    // $ANTLR start "T__45"
    public final void mT__45() throws RecognitionException {
        try {
            int _type = T__45;
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
    // $ANTLR end "T__45"

    // $ANTLR start "T__46"
    public final void mT__46() throws RecognitionException {
        try {
            int _type = T__46;
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
    // $ANTLR end "T__46"

    // $ANTLR start "T__47"
    public final void mT__47() throws RecognitionException {
        try {
            int _type = T__47;
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
    // $ANTLR end "T__47"

    // $ANTLR start "T__48"
    public final void mT__48() throws RecognitionException {
        try {
            int _type = T__48;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Lustre.g:32:7: ( '[' )
            // Lustre.g:32:9: '['
            {
            match('['); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__48"

    // $ANTLR start "T__49"
    public final void mT__49() throws RecognitionException {
        try {
            int _type = T__49;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Lustre.g:33:7: ( ']' )
            // Lustre.g:33:9: ']'
            {
            match(']'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__49"

    // $ANTLR start "T__50"
    public final void mT__50() throws RecognitionException {
        try {
            int _type = T__50;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Lustre.g:34:7: ( 'and' )
            // Lustre.g:34:9: 'and'
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
    // $ANTLR end "T__50"

    // $ANTLR start "T__51"
    public final void mT__51() throws RecognitionException {
        try {
            int _type = T__51;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Lustre.g:35:7: ( 'bool' )
            // Lustre.g:35:9: 'bool'
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
    // $ANTLR end "T__51"

    // $ANTLR start "T__52"
    public final void mT__52() throws RecognitionException {
        try {
            int _type = T__52;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Lustre.g:36:7: ( 'const' )
            // Lustre.g:36:9: 'const'
            {
            match("const"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__52"

    // $ANTLR start "T__53"
    public final void mT__53() throws RecognitionException {
        try {
            int _type = T__53;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Lustre.g:37:7: ( 'div' )
            // Lustre.g:37:9: 'div'
            {
            match("div"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__53"

    // $ANTLR start "T__54"
    public final void mT__54() throws RecognitionException {
        try {
            int _type = T__54;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Lustre.g:38:7: ( 'else' )
            // Lustre.g:38:9: 'else'
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
    // $ANTLR end "T__54"

    // $ANTLR start "T__55"
    public final void mT__55() throws RecognitionException {
        try {
            int _type = T__55;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Lustre.g:39:7: ( 'int' )
            // Lustre.g:39:9: 'int'
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
    // $ANTLR end "T__55"

    // $ANTLR start "T__56"
    public final void mT__56() throws RecognitionException {
        try {
            int _type = T__56;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Lustre.g:40:7: ( 'let' )
            // Lustre.g:40:9: 'let'
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
    // $ANTLR end "T__56"

    // $ANTLR start "T__57"
    public final void mT__57() throws RecognitionException {
        try {
            int _type = T__57;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Lustre.g:41:7: ( 'node' )
            // Lustre.g:41:9: 'node'
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
    // $ANTLR end "T__57"

    // $ANTLR start "T__58"
    public final void mT__58() throws RecognitionException {
        try {
            int _type = T__58;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Lustre.g:42:7: ( 'of' )
            // Lustre.g:42:9: 'of'
            {
            match("of"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__58"

    // $ANTLR start "T__59"
    public final void mT__59() throws RecognitionException {
        try {
            int _type = T__59;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Lustre.g:43:7: ( 'or' )
            // Lustre.g:43:9: 'or'
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
    // $ANTLR end "T__59"

    // $ANTLR start "T__60"
    public final void mT__60() throws RecognitionException {
        try {
            int _type = T__60;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Lustre.g:44:7: ( 'real' )
            // Lustre.g:44:9: 'real'
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
    // $ANTLR end "T__60"

    // $ANTLR start "T__61"
    public final void mT__61() throws RecognitionException {
        try {
            int _type = T__61;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Lustre.g:45:7: ( 'returns' )
            // Lustre.g:45:9: 'returns'
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
    // $ANTLR end "T__61"

    // $ANTLR start "T__62"
    public final void mT__62() throws RecognitionException {
        try {
            int _type = T__62;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Lustre.g:46:7: ( 'subrange' )
            // Lustre.g:46:9: 'subrange'
            {
            match("subrange"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__62"

    // $ANTLR start "T__63"
    public final void mT__63() throws RecognitionException {
        try {
            int _type = T__63;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Lustre.g:47:7: ( 'tel' )
            // Lustre.g:47:9: 'tel'
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
    // $ANTLR end "T__63"

    // $ANTLR start "T__64"
    public final void mT__64() throws RecognitionException {
        try {
            int _type = T__64;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Lustre.g:48:7: ( 'then' )
            // Lustre.g:48:9: 'then'
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
    // $ANTLR end "T__64"

    // $ANTLR start "T__65"
    public final void mT__65() throws RecognitionException {
        try {
            int _type = T__65;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Lustre.g:49:7: ( 'type' )
            // Lustre.g:49:9: 'type'
            {
            match("type"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__65"

    // $ANTLR start "T__66"
    public final void mT__66() throws RecognitionException {
        try {
            int _type = T__66;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Lustre.g:50:7: ( 'var' )
            // Lustre.g:50:9: 'var'
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
    // $ANTLR end "T__66"

    // $ANTLR start "T__67"
    public final void mT__67() throws RecognitionException {
        try {
            int _type = T__67;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Lustre.g:51:7: ( 'xor' )
            // Lustre.g:51:9: 'xor'
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
    // $ANTLR end "T__67"

    // $ANTLR start "IF"
    public final void mIF() throws RecognitionException {
        try {
            int _type = IF;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Lustre.g:199:3: ( 'if' )
            // Lustre.g:199:5: 'if'
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
    // $ANTLR end "IF"

    // $ANTLR start "NOT"
    public final void mNOT() throws RecognitionException {
        try {
            int _type = NOT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Lustre.g:200:4: ( 'not' )
            // Lustre.g:200:6: 'not'
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
    // $ANTLR end "NOT"

    // $ANTLR start "PRE"
    public final void mPRE() throws RecognitionException {
        try {
            int _type = PRE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Lustre.g:201:4: ( 'pre' )
            // Lustre.g:201:6: 'pre'
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
    // $ANTLR end "PRE"

    // $ANTLR start "BOOL"
    public final void mBOOL() throws RecognitionException {
        try {
            int _type = BOOL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Lustre.g:203:5: ( 'true' | 'false' )
            int alt1=2;
            int LA1_0 = input.LA(1);

            if ( (LA1_0=='t') ) {
                alt1=1;
            }
            else if ( (LA1_0=='f') ) {
                alt1=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 1, 0, input);

                throw nvae;

            }
            switch (alt1) {
                case 1 :
                    // Lustre.g:203:7: 'true'
                    {
                    match("true"); 



                    }
                    break;
                case 2 :
                    // Lustre.g:203:16: 'false'
                    {
                    match("false"); 



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
    // $ANTLR end "BOOL"

    // $ANTLR start "INT"
    public final void mINT() throws RecognitionException {
        try {
            int _type = INT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Lustre.g:204:4: ( ( '0' .. '9' )+ )
            // Lustre.g:204:6: ( '0' .. '9' )+
            {
            // Lustre.g:204:6: ( '0' .. '9' )+
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

    // $ANTLR start "ID"
    public final void mID() throws RecognitionException {
        try {
            int _type = ID;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Lustre.g:205:3: ( ( 'a' .. 'z' | 'A' .. 'Z' | '_' ) ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '0' .. '9' )* )
            // Lustre.g:206:3: ( 'a' .. 'z' | 'A' .. 'Z' | '_' ) ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '0' .. '9' )*
            {
            if ( (input.LA(1) >= 'A' && input.LA(1) <= 'Z')||input.LA(1)=='_'||(input.LA(1) >= 'a' && input.LA(1) <= 'z') ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            // Lustre.g:206:27: ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '0' .. '9' )*
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( ((LA3_0 >= '0' && LA3_0 <= '9')||(LA3_0 >= 'A' && LA3_0 <= 'Z')||LA3_0=='_'||(LA3_0 >= 'a' && LA3_0 <= 'z')) ) {
                    alt3=1;
                }


                switch (alt3) {
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
            	    break loop3;
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
            // Lustre.g:209:3: ( ( ' ' | '\\t' | '\\n' | '\\r' | '\\f' )+ )
            // Lustre.g:209:5: ( ' ' | '\\t' | '\\n' | '\\r' | '\\f' )+
            {
            // Lustre.g:209:5: ( ' ' | '\\t' | '\\n' | '\\r' | '\\f' )+
            int cnt4=0;
            loop4:
            do {
                int alt4=2;
                int LA4_0 = input.LA(1);

                if ( ((LA4_0 >= '\t' && LA4_0 <= '\n')||(LA4_0 >= '\f' && LA4_0 <= '\r')||LA4_0==' ') ) {
                    alt4=1;
                }


                switch (alt4) {
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
            	    if ( cnt4 >= 1 ) break loop4;
                        EarlyExitException eee =
                            new EarlyExitException(4, input);
                        throw eee;
                }
                cnt4++;
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
            // Lustre.g:211:11: ( '--' (~ ( '%' | '\\n' | '\\r' ) (~ ( '\\n' | '\\r' ) )* |) ( ( '\\r' )? '\\n' )? )
            // Lustre.g:211:13: '--' (~ ( '%' | '\\n' | '\\r' ) (~ ( '\\n' | '\\r' ) )* |) ( ( '\\r' )? '\\n' )?
            {
            match("--"); 



            // Lustre.g:211:18: (~ ( '%' | '\\n' | '\\r' ) (~ ( '\\n' | '\\r' ) )* |)
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( ((LA6_0 >= '\u0000' && LA6_0 <= '\t')||(LA6_0 >= '\u000B' && LA6_0 <= '\f')||(LA6_0 >= '\u000E' && LA6_0 <= '$')||(LA6_0 >= '&' && LA6_0 <= '\uFFFF')) ) {
                alt6=1;
            }
            else {
                alt6=2;
            }
            switch (alt6) {
                case 1 :
                    // Lustre.g:211:19: ~ ( '%' | '\\n' | '\\r' ) (~ ( '\\n' | '\\r' ) )*
                    {
                    if ( (input.LA(1) >= '\u0000' && input.LA(1) <= '\t')||(input.LA(1) >= '\u000B' && input.LA(1) <= '\f')||(input.LA(1) >= '\u000E' && input.LA(1) <= '$')||(input.LA(1) >= '&' && input.LA(1) <= '\uFFFF') ) {
                        input.consume();
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        recover(mse);
                        throw mse;
                    }


                    // Lustre.g:211:36: (~ ( '\\n' | '\\r' ) )*
                    loop5:
                    do {
                        int alt5=2;
                        int LA5_0 = input.LA(1);

                        if ( ((LA5_0 >= '\u0000' && LA5_0 <= '\t')||(LA5_0 >= '\u000B' && LA5_0 <= '\f')||(LA5_0 >= '\u000E' && LA5_0 <= '\uFFFF')) ) {
                            alt5=1;
                        }


                        switch (alt5) {
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
                    	    break loop5;
                        }
                    } while (true);


                    }
                    break;
                case 2 :
                    // Lustre.g:211:63: 
                    {
                    }
                    break;

            }


            // Lustre.g:211:65: ( ( '\\r' )? '\\n' )?
            int alt8=2;
            int LA8_0 = input.LA(1);

            if ( (LA8_0=='\n'||LA8_0=='\r') ) {
                alt8=1;
            }
            switch (alt8) {
                case 1 :
                    // Lustre.g:211:66: ( '\\r' )? '\\n'
                    {
                    // Lustre.g:211:66: ( '\\r' )?
                    int alt7=2;
                    int LA7_0 = input.LA(1);

                    if ( (LA7_0=='\r') ) {
                        alt7=1;
                    }
                    switch (alt7) {
                        case 1 :
                            // Lustre.g:211:66: '\\r'
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
            // Lustre.g:212:11: ( '/*' ( options {greedy=false; } : . )* '*/' )
            // Lustre.g:212:13: '/*' ( options {greedy=false; } : . )* '*/'
            {
            match("/*"); 



            // Lustre.g:212:18: ( options {greedy=false; } : . )*
            loop9:
            do {
                int alt9=2;
                int LA9_0 = input.LA(1);

                if ( (LA9_0=='*') ) {
                    int LA9_1 = input.LA(2);

                    if ( (LA9_1=='/') ) {
                        alt9=2;
                    }
                    else if ( ((LA9_1 >= '\u0000' && LA9_1 <= '.')||(LA9_1 >= '0' && LA9_1 <= '\uFFFF')) ) {
                        alt9=1;
                    }


                }
                else if ( ((LA9_0 >= '\u0000' && LA9_0 <= ')')||(LA9_0 >= '+' && LA9_0 <= '\uFFFF')) ) {
                    alt9=1;
                }


                switch (alt9) {
            	case 1 :
            	    // Lustre.g:212:45: .
            	    {
            	    matchAny(); 

            	    }
            	    break;

            	default :
            	    break loop9;
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
            // Lustre.g:213:5: ( '--%MAIN' ( ';' )? )
            // Lustre.g:213:7: '--%MAIN' ( ';' )?
            {
            match("--%MAIN"); 



            // Lustre.g:213:17: ( ';' )?
            int alt10=2;
            int LA10_0 = input.LA(1);

            if ( (LA10_0==';') ) {
                alt10=1;
            }
            switch (alt10) {
                case 1 :
                    // Lustre.g:213:17: ';'
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
            // Lustre.g:215:6: ( '.' )
            // Lustre.g:215:8: '.'
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
        // Lustre.g:1:8: ( T__30 | T__31 | T__32 | T__33 | T__34 | T__35 | T__36 | T__37 | T__38 | T__39 | T__40 | T__41 | T__42 | T__43 | T__44 | T__45 | T__46 | T__47 | T__48 | T__49 | T__50 | T__51 | T__52 | T__53 | T__54 | T__55 | T__56 | T__57 | T__58 | T__59 | T__60 | T__61 | T__62 | T__63 | T__64 | T__65 | T__66 | T__67 | IF | NOT | PRE | BOOL | INT | ID | WS | SL_COMMENT | ML_COMMENT | MAIN | ERROR )
        int alt11=49;
        alt11 = dfa11.predict(input);
        switch (alt11) {
            case 1 :
                // Lustre.g:1:10: T__30
                {
                mT__30(); 


                }
                break;
            case 2 :
                // Lustre.g:1:16: T__31
                {
                mT__31(); 


                }
                break;
            case 3 :
                // Lustre.g:1:22: T__32
                {
                mT__32(); 


                }
                break;
            case 4 :
                // Lustre.g:1:28: T__33
                {
                mT__33(); 


                }
                break;
            case 5 :
                // Lustre.g:1:34: T__34
                {
                mT__34(); 


                }
                break;
            case 6 :
                // Lustre.g:1:40: T__35
                {
                mT__35(); 


                }
                break;
            case 7 :
                // Lustre.g:1:46: T__36
                {
                mT__36(); 


                }
                break;
            case 8 :
                // Lustre.g:1:52: T__37
                {
                mT__37(); 


                }
                break;
            case 9 :
                // Lustre.g:1:58: T__38
                {
                mT__38(); 


                }
                break;
            case 10 :
                // Lustre.g:1:64: T__39
                {
                mT__39(); 


                }
                break;
            case 11 :
                // Lustre.g:1:70: T__40
                {
                mT__40(); 


                }
                break;
            case 12 :
                // Lustre.g:1:76: T__41
                {
                mT__41(); 


                }
                break;
            case 13 :
                // Lustre.g:1:82: T__42
                {
                mT__42(); 


                }
                break;
            case 14 :
                // Lustre.g:1:88: T__43
                {
                mT__43(); 


                }
                break;
            case 15 :
                // Lustre.g:1:94: T__44
                {
                mT__44(); 


                }
                break;
            case 16 :
                // Lustre.g:1:100: T__45
                {
                mT__45(); 


                }
                break;
            case 17 :
                // Lustre.g:1:106: T__46
                {
                mT__46(); 


                }
                break;
            case 18 :
                // Lustre.g:1:112: T__47
                {
                mT__47(); 


                }
                break;
            case 19 :
                // Lustre.g:1:118: T__48
                {
                mT__48(); 


                }
                break;
            case 20 :
                // Lustre.g:1:124: T__49
                {
                mT__49(); 


                }
                break;
            case 21 :
                // Lustre.g:1:130: T__50
                {
                mT__50(); 


                }
                break;
            case 22 :
                // Lustre.g:1:136: T__51
                {
                mT__51(); 


                }
                break;
            case 23 :
                // Lustre.g:1:142: T__52
                {
                mT__52(); 


                }
                break;
            case 24 :
                // Lustre.g:1:148: T__53
                {
                mT__53(); 


                }
                break;
            case 25 :
                // Lustre.g:1:154: T__54
                {
                mT__54(); 


                }
                break;
            case 26 :
                // Lustre.g:1:160: T__55
                {
                mT__55(); 


                }
                break;
            case 27 :
                // Lustre.g:1:166: T__56
                {
                mT__56(); 


                }
                break;
            case 28 :
                // Lustre.g:1:172: T__57
                {
                mT__57(); 


                }
                break;
            case 29 :
                // Lustre.g:1:178: T__58
                {
                mT__58(); 


                }
                break;
            case 30 :
                // Lustre.g:1:184: T__59
                {
                mT__59(); 


                }
                break;
            case 31 :
                // Lustre.g:1:190: T__60
                {
                mT__60(); 


                }
                break;
            case 32 :
                // Lustre.g:1:196: T__61
                {
                mT__61(); 


                }
                break;
            case 33 :
                // Lustre.g:1:202: T__62
                {
                mT__62(); 


                }
                break;
            case 34 :
                // Lustre.g:1:208: T__63
                {
                mT__63(); 


                }
                break;
            case 35 :
                // Lustre.g:1:214: T__64
                {
                mT__64(); 


                }
                break;
            case 36 :
                // Lustre.g:1:220: T__65
                {
                mT__65(); 


                }
                break;
            case 37 :
                // Lustre.g:1:226: T__66
                {
                mT__66(); 


                }
                break;
            case 38 :
                // Lustre.g:1:232: T__67
                {
                mT__67(); 


                }
                break;
            case 39 :
                // Lustre.g:1:238: IF
                {
                mIF(); 


                }
                break;
            case 40 :
                // Lustre.g:1:241: NOT
                {
                mNOT(); 


                }
                break;
            case 41 :
                // Lustre.g:1:245: PRE
                {
                mPRE(); 


                }
                break;
            case 42 :
                // Lustre.g:1:249: BOOL
                {
                mBOOL(); 


                }
                break;
            case 43 :
                // Lustre.g:1:254: INT
                {
                mINT(); 


                }
                break;
            case 44 :
                // Lustre.g:1:258: ID
                {
                mID(); 


                }
                break;
            case 45 :
                // Lustre.g:1:261: WS
                {
                mWS(); 


                }
                break;
            case 46 :
                // Lustre.g:1:264: SL_COMMENT
                {
                mSL_COMMENT(); 


                }
                break;
            case 47 :
                // Lustre.g:1:275: ML_COMMENT
                {
                mML_COMMENT(); 


                }
                break;
            case 48 :
                // Lustre.g:1:286: MAIN
                {
                mMAIN(); 


                }
                break;
            case 49 :
                // Lustre.g:1:291: ERROR
                {
                mERROR(); 


                }
                break;

        }

    }


    protected DFA11 dfa11 = new DFA11(this);
    static final String DFA11_eotS =
        "\6\uffff\1\45\1\47\2\uffff\1\52\1\54\1\56\2\uffff\20\40\4\uffff"+
        "\1\105\13\uffff\6\40\1\114\2\40\1\120\1\121\12\40\2\uffff\1\137"+
        "\2\40\1\142\1\40\1\144\1\uffff\1\145\1\40\1\147\2\uffff\3\40\1\153"+
        "\3\40\1\157\1\160\1\161\1\40\3\uffff\1\163\1\40\1\uffff\1\165\2"+
        "\uffff\1\166\1\uffff\1\167\2\40\1\uffff\1\172\1\173\1\174\3\uffff"+
        "\1\40\1\uffff\1\176\3\uffff\2\40\3\uffff\1\174\1\uffff\2\40\1\u0083"+
        "\1\40\1\uffff\1\u0085\1\uffff";
    static final String DFA11_eofS =
        "\u0086\uffff";
    static final String DFA11_minS =
        "\1\11\5\uffff\1\55\1\52\2\uffff\1\75\1\76\1\75\2\uffff\1\156\2\157"+
        "\1\151\1\154\1\146\1\145\1\157\1\146\1\145\1\165\1\145\1\141\1\157"+
        "\1\162\1\141\4\uffff\1\45\13\uffff\1\144\1\157\1\156\1\166\1\163"+
        "\1\164\1\60\1\164\1\144\2\60\1\141\1\142\1\154\1\145\1\160\1\165"+
        "\2\162\1\145\1\154\1\115\1\uffff\1\60\1\154\1\163\1\60\1\145\1\60"+
        "\1\uffff\1\60\1\145\1\60\2\uffff\1\154\1\165\1\162\1\60\1\156\2"+
        "\145\3\60\1\163\3\uffff\1\60\1\164\1\uffff\1\60\2\uffff\1\60\1\uffff"+
        "\1\60\1\162\1\141\1\uffff\3\60\3\uffff\1\145\1\uffff\1\60\3\uffff"+
        "\2\156\3\uffff\1\60\1\uffff\1\163\1\147\1\60\1\145\1\uffff\1\60"+
        "\1\uffff";
    static final String DFA11_maxS =
        "\1\172\5\uffff\1\76\1\52\2\uffff\2\76\1\75\2\uffff\1\156\2\157\1"+
        "\151\1\154\1\156\1\145\1\157\1\162\1\145\1\165\1\171\1\141\1\157"+
        "\1\162\1\141\4\uffff\1\45\13\uffff\1\144\1\157\1\156\1\166\1\163"+
        "\1\164\1\172\2\164\2\172\1\164\1\142\1\154\1\145\1\160\1\165\2\162"+
        "\1\145\1\154\1\120\1\uffff\1\172\1\154\1\163\1\172\1\145\1\172\1"+
        "\uffff\1\172\1\145\1\172\2\uffff\1\154\1\165\1\162\1\172\1\156\2"+
        "\145\3\172\1\163\3\uffff\1\172\1\164\1\uffff\1\172\2\uffff\1\172"+
        "\1\uffff\1\172\1\162\1\141\1\uffff\3\172\3\uffff\1\145\1\uffff\1"+
        "\172\3\uffff\2\156\3\uffff\1\172\1\uffff\1\163\1\147\1\172\1\145"+
        "\1\uffff\1\172\1\uffff";
    static final String DFA11_acceptS =
        "\1\uffff\1\1\1\2\1\3\1\4\1\5\2\uffff\1\12\1\13\3\uffff\1\23\1\24"+
        "\20\uffff\1\53\1\54\1\55\1\61\1\uffff\1\10\1\6\1\57\1\11\1\15\1"+
        "\16\1\14\1\20\1\17\1\22\1\21\26\uffff\1\56\6\uffff\1\47\3\uffff"+
        "\1\35\1\36\13\uffff\1\7\1\60\1\25\2\uffff\1\30\1\uffff\1\32\1\33"+
        "\1\uffff\1\50\3\uffff\1\42\3\uffff\1\45\1\46\1\51\1\uffff\1\26\1"+
        "\uffff\1\31\1\34\1\37\2\uffff\1\43\1\44\1\52\1\uffff\1\27\4\uffff"+
        "\1\40\1\uffff\1\41";
    static final String DFA11_specialS =
        "\u0086\uffff}>";
    static final String[] DFA11_transitionS = {
            "\2\41\1\uffff\2\41\22\uffff\1\41\7\uffff\1\1\1\2\1\3\1\4\1\5"+
            "\1\6\1\42\1\7\12\37\1\10\1\11\1\12\1\13\1\14\2\uffff\32\40\1"+
            "\15\1\uffff\1\16\1\uffff\1\40\1\uffff\1\17\1\20\1\21\1\22\1"+
            "\23\1\36\2\40\1\24\2\40\1\25\1\40\1\26\1\27\1\35\1\40\1\30\1"+
            "\31\1\32\1\40\1\33\1\40\1\34\2\40",
            "",
            "",
            "",
            "",
            "",
            "\1\43\20\uffff\1\44",
            "\1\46",
            "",
            "",
            "\1\50\1\51",
            "\1\53",
            "\1\55",
            "",
            "",
            "\1\57",
            "\1\60",
            "\1\61",
            "\1\62",
            "\1\63",
            "\1\65\7\uffff\1\64",
            "\1\66",
            "\1\67",
            "\1\70\13\uffff\1\71",
            "\1\72",
            "\1\73",
            "\1\74\2\uffff\1\75\11\uffff\1\77\6\uffff\1\76",
            "\1\100",
            "\1\101",
            "\1\102",
            "\1\103",
            "",
            "",
            "",
            "",
            "\1\104",
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
            "\1\106",
            "\1\107",
            "\1\110",
            "\1\111",
            "\1\112",
            "\1\113",
            "\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\32\40",
            "\1\115",
            "\1\116\17\uffff\1\117",
            "\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\32\40",
            "\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\32\40",
            "\1\122\22\uffff\1\123",
            "\1\124",
            "\1\125",
            "\1\126",
            "\1\127",
            "\1\130",
            "\1\131",
            "\1\132",
            "\1\133",
            "\1\134",
            "\1\136\2\uffff\1\135",
            "",
            "\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\32\40",
            "\1\140",
            "\1\141",
            "\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\32\40",
            "\1\143",
            "\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\32\40",
            "",
            "\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\32\40",
            "\1\146",
            "\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\32\40",
            "",
            "",
            "\1\150",
            "\1\151",
            "\1\152",
            "\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\32\40",
            "\1\154",
            "\1\155",
            "\1\156",
            "\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\32\40",
            "\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\32\40",
            "\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\32\40",
            "\1\162",
            "",
            "",
            "",
            "\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\32\40",
            "\1\164",
            "",
            "\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\32\40",
            "",
            "",
            "\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\32\40",
            "",
            "\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\32\40",
            "\1\170",
            "\1\171",
            "",
            "\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\32\40",
            "\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\32\40",
            "\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\32\40",
            "",
            "",
            "",
            "\1\175",
            "",
            "\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\32\40",
            "",
            "",
            "",
            "\1\177",
            "\1\u0080",
            "",
            "",
            "",
            "\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\32\40",
            "",
            "\1\u0081",
            "\1\u0082",
            "\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\32\40",
            "\1\u0084",
            "",
            "\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\32\40",
            ""
    };

    static final short[] DFA11_eot = DFA.unpackEncodedString(DFA11_eotS);
    static final short[] DFA11_eof = DFA.unpackEncodedString(DFA11_eofS);
    static final char[] DFA11_min = DFA.unpackEncodedStringToUnsignedChars(DFA11_minS);
    static final char[] DFA11_max = DFA.unpackEncodedStringToUnsignedChars(DFA11_maxS);
    static final short[] DFA11_accept = DFA.unpackEncodedString(DFA11_acceptS);
    static final short[] DFA11_special = DFA.unpackEncodedString(DFA11_specialS);
    static final short[][] DFA11_transition;

    static {
        int numStates = DFA11_transitionS.length;
        DFA11_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA11_transition[i] = DFA.unpackEncodedString(DFA11_transitionS[i]);
        }
    }

    class DFA11 extends DFA {

        public DFA11(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 11;
            this.eot = DFA11_eot;
            this.eof = DFA11_eof;
            this.min = DFA11_min;
            this.max = DFA11_max;
            this.accept = DFA11_accept;
            this.special = DFA11_special;
            this.transition = DFA11_transition;
        }
        public String getDescription() {
            return "1:1: Tokens : ( T__30 | T__31 | T__32 | T__33 | T__34 | T__35 | T__36 | T__37 | T__38 | T__39 | T__40 | T__41 | T__42 | T__43 | T__44 | T__45 | T__46 | T__47 | T__48 | T__49 | T__50 | T__51 | T__52 | T__53 | T__54 | T__55 | T__56 | T__57 | T__58 | T__59 | T__60 | T__61 | T__62 | T__63 | T__64 | T__65 | T__66 | T__67 | IF | NOT | PRE | BOOL | INT | ID | WS | SL_COMMENT | ML_COMMENT | MAIN | ERROR );";
        }
    }
 

}