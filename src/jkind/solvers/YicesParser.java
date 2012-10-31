// $ANTLR 3.4 Yices.g 2012-10-31 13:05:23

  package jkind.solvers;

  import jkind.solvers.SolverResult.Result;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked"})
public class YicesParser extends Parser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "DIGIT", "ERROR", "ID", "INT", "SYMBOL", "WS", "'('", "')'", "'-'", "'/'", "'='", "'false'", "'sat'", "'true'", "'unsat'"
    };

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

    // delegates
    public Parser[] getDelegates() {
        return new Parser[] {};
    }

    // delegators


    public YicesParser(TokenStream input) {
        this(input, new RecognizerSharedState());
    }
    public YicesParser(TokenStream input, RecognizerSharedState state) {
        super(input, state);
    }

    public String[] getTokenNames() { return YicesParser.tokenNames; }
    public String getGrammarFileName() { return "Yices.g"; }


      protected void ignore(Stack<Void> stack, List<Void> list, ArrayList<Void> arraylist) {}



    // $ANTLR start "solverResult"
    // Yices.g:25:1: solverResult returns [SolverResult sr] : result ( model )? EOF ;
    public final SolverResult solverResult() throws RecognitionException {
        SolverResult sr = null;


        Result result1 =null;

        Model model2 =null;


        try {
            // Yices.g:25:39: ( result ( model )? EOF )
            // Yices.g:26:5: result ( model )? EOF
            {
            pushFollow(FOLLOW_result_in_solverResult61);
            result1=result();

            state._fsp--;


            // Yices.g:26:12: ( model )?
            int alt1=2;
            int LA1_0 = input.LA(1);

            if ( (LA1_0==10) ) {
                alt1=1;
            }
            switch (alt1) {
                case 1 :
                    // Yices.g:26:12: model
                    {
                    pushFollow(FOLLOW_model_in_solverResult63);
                    model2=model();

                    state._fsp--;


                    }
                    break;

            }


            match(input,EOF,FOLLOW_EOF_in_solverResult66); 

             sr = new SolverResult(result1, model2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return sr;
    }
    // $ANTLR end "solverResult"



    // $ANTLR start "result"
    // Yices.g:30:1: result returns [Result r] : ( 'sat' | 'unsat' );
    public final Result result() throws RecognitionException {
        Result r = null;


        try {
            // Yices.g:30:26: ( 'sat' | 'unsat' )
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( (LA2_0==16) ) {
                alt2=1;
            }
            else if ( (LA2_0==18) ) {
                alt2=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 2, 0, input);

                throw nvae;

            }
            switch (alt2) {
                case 1 :
                    // Yices.g:31:5: 'sat'
                    {
                    match(input,16,FOLLOW_16_in_result90); 

                     r = Result.SAT; 

                    }
                    break;
                case 2 :
                    // Yices.g:32:5: 'unsat'
                    {
                    match(input,18,FOLLOW_18_in_result102); 

                     r = Result.UNSAT; 

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return r;
    }
    // $ANTLR end "result"



    // $ANTLR start "model"
    // Yices.g:35:1: model returns [Model m] : ( valueAssignment[m] | functionAssignment[m] )+ ;
    public final Model model() throws RecognitionException {
        Model m = null;



          m = new Model();

        try {
            // Yices.g:39:3: ( ( valueAssignment[m] | functionAssignment[m] )+ )
            // Yices.g:39:5: ( valueAssignment[m] | functionAssignment[m] )+
            {
            // Yices.g:39:5: ( valueAssignment[m] | functionAssignment[m] )+
            int cnt3=0;
            loop3:
            do {
                int alt3=3;
                int LA3_0 = input.LA(1);

                if ( (LA3_0==10) ) {
                    int LA3_2 = input.LA(2);

                    if ( (LA3_2==14) ) {
                        int LA3_3 = input.LA(3);

                        if ( (LA3_3==ID) ) {
                            alt3=1;
                        }
                        else if ( (LA3_3==10) ) {
                            alt3=2;
                        }


                    }


                }


                switch (alt3) {
            	case 1 :
            	    // Yices.g:39:6: valueAssignment[m]
            	    {
            	    pushFollow(FOLLOW_valueAssignment_in_model128);
            	    valueAssignment(m);

            	    state._fsp--;


            	    }
            	    break;
            	case 2 :
            	    // Yices.g:39:27: functionAssignment[m]
            	    {
            	    pushFollow(FOLLOW_functionAssignment_in_model133);
            	    functionAssignment(m);

            	    state._fsp--;


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


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return m;
    }
    // $ANTLR end "model"



    // $ANTLR start "valueAssignment"
    // Yices.g:41:1: valueAssignment[Model m] : '(' '=' ID value ')' ;
    public final void valueAssignment(Model m) throws RecognitionException {
        Token ID3=null;
        Value value4 =null;


        try {
            // Yices.g:41:25: ( '(' '=' ID value ')' )
            // Yices.g:42:5: '(' '=' ID value ')'
            {
            match(input,10,FOLLOW_10_in_valueAssignment149); 

            match(input,14,FOLLOW_14_in_valueAssignment151); 

            ID3=(Token)match(input,ID,FOLLOW_ID_in_valueAssignment153); 

            pushFollow(FOLLOW_value_in_valueAssignment155);
            value4=value();

            state._fsp--;


            match(input,11,FOLLOW_11_in_valueAssignment157); 

             m.addValue((ID3!=null?ID3.getText():null), value4); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return ;
    }
    // $ANTLR end "valueAssignment"



    // $ANTLR start "functionAssignment"
    // Yices.g:45:1: functionAssignment[Model m] : '(' '=' '(' ID integer ')' value ')' ;
    public final void functionAssignment(Model m) throws RecognitionException {
        Token ID5=null;
        YicesParser.integer_return integer6 =null;

        Value value7 =null;


        try {
            // Yices.g:45:28: ( '(' '=' '(' ID integer ')' value ')' )
            // Yices.g:46:5: '(' '=' '(' ID integer ')' value ')'
            {
            match(input,10,FOLLOW_10_in_functionAssignment178); 

            match(input,14,FOLLOW_14_in_functionAssignment180); 

            match(input,10,FOLLOW_10_in_functionAssignment182); 

            ID5=(Token)match(input,ID,FOLLOW_ID_in_functionAssignment184); 

            pushFollow(FOLLOW_integer_in_functionAssignment186);
            integer6=integer();

            state._fsp--;


            match(input,11,FOLLOW_11_in_functionAssignment188); 

            pushFollow(FOLLOW_value_in_functionAssignment190);
            value7=value();

            state._fsp--;


            match(input,11,FOLLOW_11_in_functionAssignment192); 

             m.addFunctionValue((ID5!=null?ID5.getText():null), Integer.parseInt((integer6!=null?input.toString(integer6.start,integer6.stop):null)), value7); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return ;
    }
    // $ANTLR end "functionAssignment"



    // $ANTLR start "value"
    // Yices.g:50:1: value returns [Value v] : ( 'true' | 'false' | numeric );
    public final Value value() throws RecognitionException {
        Value v = null;


        YicesParser.numeric_return numeric8 =null;


        try {
            // Yices.g:50:24: ( 'true' | 'false' | numeric )
            int alt4=3;
            switch ( input.LA(1) ) {
            case 17:
                {
                alt4=1;
                }
                break;
            case 15:
                {
                alt4=2;
                }
                break;
            case INT:
            case 12:
                {
                alt4=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 4, 0, input);

                throw nvae;

            }

            switch (alt4) {
                case 1 :
                    // Yices.g:51:5: 'true'
                    {
                    match(input,17,FOLLOW_17_in_value216); 

                     v = BoolValue.TRUE; 

                    }
                    break;
                case 2 :
                    // Yices.g:52:5: 'false'
                    {
                    match(input,15,FOLLOW_15_in_value229); 

                     v = BoolValue.FALSE; 

                    }
                    break;
                case 3 :
                    // Yices.g:53:5: numeric
                    {
                    pushFollow(FOLLOW_numeric_in_value241);
                    numeric8=numeric();

                    state._fsp--;


                     v = new NumericValue((numeric8!=null?input.toString(numeric8.start,numeric8.stop):null)); 

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return v;
    }
    // $ANTLR end "value"


    public static class numeric_return extends ParserRuleReturnScope {
    };


    // $ANTLR start "numeric"
    // Yices.g:56:1: numeric : ( '-' )? INT ( '/' INT )? ;
    public final YicesParser.numeric_return numeric() throws RecognitionException {
        YicesParser.numeric_return retval = new YicesParser.numeric_return();
        retval.start = input.LT(1);


        try {
            // Yices.g:56:8: ( ( '-' )? INT ( '/' INT )? )
            // Yices.g:56:10: ( '-' )? INT ( '/' INT )?
            {
            // Yices.g:56:10: ( '-' )?
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0==12) ) {
                alt5=1;
            }
            switch (alt5) {
                case 1 :
                    // Yices.g:56:10: '-'
                    {
                    match(input,12,FOLLOW_12_in_numeric257); 

                    }
                    break;

            }


            match(input,INT,FOLLOW_INT_in_numeric260); 

            // Yices.g:56:19: ( '/' INT )?
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0==13) ) {
                alt6=1;
            }
            switch (alt6) {
                case 1 :
                    // Yices.g:56:20: '/' INT
                    {
                    match(input,13,FOLLOW_13_in_numeric263); 

                    match(input,INT,FOLLOW_INT_in_numeric265); 

                    }
                    break;

            }


            }

            retval.stop = input.LT(-1);


        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "numeric"


    public static class integer_return extends ParserRuleReturnScope {
    };


    // $ANTLR start "integer"
    // Yices.g:57:1: integer : ( '-' )? INT ;
    public final YicesParser.integer_return integer() throws RecognitionException {
        YicesParser.integer_return retval = new YicesParser.integer_return();
        retval.start = input.LT(1);


        try {
            // Yices.g:57:8: ( ( '-' )? INT )
            // Yices.g:57:10: ( '-' )? INT
            {
            // Yices.g:57:10: ( '-' )?
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( (LA7_0==12) ) {
                alt7=1;
            }
            switch (alt7) {
                case 1 :
                    // Yices.g:57:10: '-'
                    {
                    match(input,12,FOLLOW_12_in_integer273); 

                    }
                    break;

            }


            match(input,INT,FOLLOW_INT_in_integer276); 

            }

            retval.stop = input.LT(-1);


        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "integer"

    // Delegated rules


 

    public static final BitSet FOLLOW_result_in_solverResult61 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_model_in_solverResult63 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_solverResult66 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_16_in_result90 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_18_in_result102 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_valueAssignment_in_model128 = new BitSet(new long[]{0x0000000000000402L});
    public static final BitSet FOLLOW_functionAssignment_in_model133 = new BitSet(new long[]{0x0000000000000402L});
    public static final BitSet FOLLOW_10_in_valueAssignment149 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_14_in_valueAssignment151 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_ID_in_valueAssignment153 = new BitSet(new long[]{0x0000000000029080L});
    public static final BitSet FOLLOW_value_in_valueAssignment155 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_11_in_valueAssignment157 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_10_in_functionAssignment178 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_14_in_functionAssignment180 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_10_in_functionAssignment182 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_ID_in_functionAssignment184 = new BitSet(new long[]{0x0000000000001080L});
    public static final BitSet FOLLOW_integer_in_functionAssignment186 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_11_in_functionAssignment188 = new BitSet(new long[]{0x0000000000029080L});
    public static final BitSet FOLLOW_value_in_functionAssignment190 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_11_in_functionAssignment192 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_17_in_value216 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_15_in_value229 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_numeric_in_value241 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_12_in_numeric257 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_INT_in_numeric260 = new BitSet(new long[]{0x0000000000002002L});
    public static final BitSet FOLLOW_13_in_numeric263 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_INT_in_numeric265 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_12_in_integer273 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_INT_in_integer276 = new BitSet(new long[]{0x0000000000000002L});

}