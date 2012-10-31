// $ANTLR 3.4 Lustre.g 2012-10-30 21:07:52

  package jkind.lustre;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import org.antlr.runtime.tree.*;


@SuppressWarnings({"all", "warnings", "unchecked"})
public class LustreParser extends Parser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "BOOL", "CONSTANTS", "EQUATIONS", "ERROR", "ID", "IDENT", "IF", "INPUTS", "INT", "LOCALS", "MAIN", "ML_COMMENT", "NEGATE", "NODE", "NOT", "OUTPUTS", "PRE", "PROPERTIES", "REAL", "SL_COMMENT", "WS", "'('", "')'", "'*'", "'+'", "','", "'-'", "'--%PROPERTY'", "'->'", "'/'", "':'", "';'", "'<'", "'<='", "'<>'", "'='", "'=>'", "'>'", "'>='", "'['", "']'", "'and'", "'bool'", "'const'", "'div'", "'else'", "'int'", "'let'", "'node'", "'of'", "'or'", "'real'", "'returns'", "'subrange'", "'tel'", "'then'", "'var'", "'xor'"
    };

    public static final int EOF=-1;
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
    public static final int BOOL=4;
    public static final int CONSTANTS=5;
    public static final int EQUATIONS=6;
    public static final int ERROR=7;
    public static final int ID=8;
    public static final int IDENT=9;
    public static final int IF=10;
    public static final int INPUTS=11;
    public static final int INT=12;
    public static final int LOCALS=13;
    public static final int MAIN=14;
    public static final int ML_COMMENT=15;
    public static final int NEGATE=16;
    public static final int NODE=17;
    public static final int NOT=18;
    public static final int OUTPUTS=19;
    public static final int PRE=20;
    public static final int PROPERTIES=21;
    public static final int REAL=22;
    public static final int SL_COMMENT=23;
    public static final int WS=24;

    // delegates
    public Parser[] getDelegates() {
        return new Parser[] {};
    }

    // delegators


    public LustreParser(TokenStream input) {
        this(input, new RecognizerSharedState());
    }
    public LustreParser(TokenStream input, RecognizerSharedState state) {
        super(input, state);
    }

protected TreeAdaptor adaptor = new CommonTreeAdaptor();

public void setTreeAdaptor(TreeAdaptor adaptor) {
    this.adaptor = adaptor;
}
public TreeAdaptor getTreeAdaptor() {
    return adaptor;
}
    public String[] getTokenNames() { return LustreParser.tokenNames; }
    public String getGrammarFileName() { return "Lustre.g"; }


      protected void ignore(Stack<Void> stack, List<Void> list, ArrayList<Void> arraylist) {}
      private Map<String, Expr> consts = new HashMap<String, Expr>();
      
      private CommonTree makeReal(String text) {
        return new CommonTree(new CommonToken(REAL, text));
      }
      
      @Override
      public void emitErrorMessage(String msg) {
        System.out.println(msg);
      }


    public static class node_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "node"
    // Lustre.g:47:1: node : ( constant )* 'node' ID '(' (inputs= varDeclList )? ')' 'returns' '(' (outputs= varDeclList )? ')' ';' ( 'var' locals= varDeclList ';' )? 'let' ( equation | property )* 'tel' ';' -> ^( NODE ID ^( CONSTANTS ( constant )* ) ^( INPUTS ( $inputs)? ) ^( OUTPUTS ( $outputs)? ) ^( LOCALS ( $locals)? ) ^( EQUATIONS ( equation )* ) ^( PROPERTIES ( property )* ) ) ;
    public final LustreParser.node_return node() throws RecognitionException {
        LustreParser.node_return retval = new LustreParser.node_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token string_literal2=null;
        Token ID3=null;
        Token char_literal4=null;
        Token char_literal5=null;
        Token string_literal6=null;
        Token char_literal7=null;
        Token char_literal8=null;
        Token char_literal9=null;
        Token string_literal10=null;
        Token char_literal11=null;
        Token string_literal12=null;
        Token string_literal15=null;
        Token char_literal16=null;
        LustreParser.varDeclList_return inputs =null;

        LustreParser.varDeclList_return outputs =null;

        LustreParser.varDeclList_return locals =null;

        LustreParser.constant_return constant1 =null;

        LustreParser.equation_return equation13 =null;

        LustreParser.property_return property14 =null;


        Object string_literal2_tree=null;
        Object ID3_tree=null;
        Object char_literal4_tree=null;
        Object char_literal5_tree=null;
        Object string_literal6_tree=null;
        Object char_literal7_tree=null;
        Object char_literal8_tree=null;
        Object char_literal9_tree=null;
        Object string_literal10_tree=null;
        Object char_literal11_tree=null;
        Object string_literal12_tree=null;
        Object string_literal15_tree=null;
        Object char_literal16_tree=null;
        RewriteRuleTokenStream stream_58=new RewriteRuleTokenStream(adaptor,"token 58");
        RewriteRuleTokenStream stream_56=new RewriteRuleTokenStream(adaptor,"token 56");
        RewriteRuleTokenStream stream_51=new RewriteRuleTokenStream(adaptor,"token 51");
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleTokenStream stream_35=new RewriteRuleTokenStream(adaptor,"token 35");
        RewriteRuleTokenStream stream_52=new RewriteRuleTokenStream(adaptor,"token 52");
        RewriteRuleTokenStream stream_25=new RewriteRuleTokenStream(adaptor,"token 25");
        RewriteRuleTokenStream stream_26=new RewriteRuleTokenStream(adaptor,"token 26");
        RewriteRuleTokenStream stream_60=new RewriteRuleTokenStream(adaptor,"token 60");
        RewriteRuleSubtreeStream stream_constant=new RewriteRuleSubtreeStream(adaptor,"rule constant");
        RewriteRuleSubtreeStream stream_equation=new RewriteRuleSubtreeStream(adaptor,"rule equation");
        RewriteRuleSubtreeStream stream_varDeclList=new RewriteRuleSubtreeStream(adaptor,"rule varDeclList");
        RewriteRuleSubtreeStream stream_property=new RewriteRuleSubtreeStream(adaptor,"rule property");
        try {
            // Lustre.g:47:5: ( ( constant )* 'node' ID '(' (inputs= varDeclList )? ')' 'returns' '(' (outputs= varDeclList )? ')' ';' ( 'var' locals= varDeclList ';' )? 'let' ( equation | property )* 'tel' ';' -> ^( NODE ID ^( CONSTANTS ( constant )* ) ^( INPUTS ( $inputs)? ) ^( OUTPUTS ( $outputs)? ) ^( LOCALS ( $locals)? ) ^( EQUATIONS ( equation )* ) ^( PROPERTIES ( property )* ) ) )
            // Lustre.g:48:3: ( constant )* 'node' ID '(' (inputs= varDeclList )? ')' 'returns' '(' (outputs= varDeclList )? ')' ';' ( 'var' locals= varDeclList ';' )? 'let' ( equation | property )* 'tel' ';'
            {
            // Lustre.g:48:3: ( constant )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0==47) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // Lustre.g:48:3: constant
            	    {
            	    pushFollow(FOLLOW_constant_in_node119);
            	    constant1=constant();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) stream_constant.add(constant1.getTree());

            	    }
            	    break;

            	default :
            	    break loop1;
                }
            } while (true);


            string_literal2=(Token)match(input,52,FOLLOW_52_in_node124); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_52.add(string_literal2);


            ID3=(Token)match(input,ID,FOLLOW_ID_in_node126); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_ID.add(ID3);


            char_literal4=(Token)match(input,25,FOLLOW_25_in_node128); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_25.add(char_literal4);


            // Lustre.g:49:23: (inputs= varDeclList )?
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( (LA2_0==ID) ) {
                alt2=1;
            }
            switch (alt2) {
                case 1 :
                    // Lustre.g:49:23: inputs= varDeclList
                    {
                    pushFollow(FOLLOW_varDeclList_in_node132);
                    inputs=varDeclList();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_varDeclList.add(inputs.getTree());

                    }
                    break;

            }


            char_literal5=(Token)match(input,26,FOLLOW_26_in_node135); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_26.add(char_literal5);


            string_literal6=(Token)match(input,56,FOLLOW_56_in_node139); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_56.add(string_literal6);


            char_literal7=(Token)match(input,25,FOLLOW_25_in_node141); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_25.add(char_literal7);


            // Lustre.g:50:24: (outputs= varDeclList )?
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( (LA3_0==ID) ) {
                alt3=1;
            }
            switch (alt3) {
                case 1 :
                    // Lustre.g:50:24: outputs= varDeclList
                    {
                    pushFollow(FOLLOW_varDeclList_in_node145);
                    outputs=varDeclList();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_varDeclList.add(outputs.getTree());

                    }
                    break;

            }


            char_literal8=(Token)match(input,26,FOLLOW_26_in_node148); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_26.add(char_literal8);


            char_literal9=(Token)match(input,35,FOLLOW_35_in_node150); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_35.add(char_literal9);


            // Lustre.g:51:3: ( 'var' locals= varDeclList ';' )?
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0==60) ) {
                alt4=1;
            }
            switch (alt4) {
                case 1 :
                    // Lustre.g:51:4: 'var' locals= varDeclList ';'
                    {
                    string_literal10=(Token)match(input,60,FOLLOW_60_in_node155); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_60.add(string_literal10);


                    pushFollow(FOLLOW_varDeclList_in_node159);
                    locals=varDeclList();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_varDeclList.add(locals.getTree());

                    char_literal11=(Token)match(input,35,FOLLOW_35_in_node161); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_35.add(char_literal11);


                    }
                    break;

            }


            string_literal12=(Token)match(input,51,FOLLOW_51_in_node167); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_51.add(string_literal12);


            // Lustre.g:53:5: ( equation | property )*
            loop5:
            do {
                int alt5=3;
                int LA5_0 = input.LA(1);

                if ( (LA5_0==ID) ) {
                    alt5=1;
                }
                else if ( (LA5_0==31) ) {
                    alt5=2;
                }


                switch (alt5) {
            	case 1 :
            	    // Lustre.g:53:6: equation
            	    {
            	    pushFollow(FOLLOW_equation_in_node174);
            	    equation13=equation();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) stream_equation.add(equation13.getTree());

            	    }
            	    break;
            	case 2 :
            	    // Lustre.g:53:17: property
            	    {
            	    pushFollow(FOLLOW_property_in_node178);
            	    property14=property();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) stream_property.add(property14.getTree());

            	    }
            	    break;

            	default :
            	    break loop5;
                }
            } while (true);


            string_literal15=(Token)match(input,58,FOLLOW_58_in_node184); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_58.add(string_literal15);


            char_literal16=(Token)match(input,35,FOLLOW_35_in_node186); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_35.add(char_literal16);


            // AST REWRITE
            // elements: locals, outputs, ID, constant, property, inputs, equation
            // token labels: 
            // rule labels: retval, inputs, locals, outputs
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {

            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
            RewriteRuleSubtreeStream stream_inputs=new RewriteRuleSubtreeStream(adaptor,"rule inputs",inputs!=null?inputs.tree:null);
            RewriteRuleSubtreeStream stream_locals=new RewriteRuleSubtreeStream(adaptor,"rule locals",locals!=null?locals.tree:null);
            RewriteRuleSubtreeStream stream_outputs=new RewriteRuleSubtreeStream(adaptor,"rule outputs",outputs!=null?outputs.tree:null);

            root_0 = (Object)adaptor.nil();
            // 54:13: -> ^( NODE ID ^( CONSTANTS ( constant )* ) ^( INPUTS ( $inputs)? ) ^( OUTPUTS ( $outputs)? ) ^( LOCALS ( $locals)? ) ^( EQUATIONS ( equation )* ) ^( PROPERTIES ( property )* ) )
            {
                // Lustre.g:55:5: ^( NODE ID ^( CONSTANTS ( constant )* ) ^( INPUTS ( $inputs)? ) ^( OUTPUTS ( $outputs)? ) ^( LOCALS ( $locals)? ) ^( EQUATIONS ( equation )* ) ^( PROPERTIES ( property )* ) )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot(
                (Object)adaptor.create(NODE, "NODE")
                , root_1);

                adaptor.addChild(root_1, 
                stream_ID.nextNode()
                );

                // Lustre.g:56:7: ^( CONSTANTS ( constant )* )
                {
                Object root_2 = (Object)adaptor.nil();
                root_2 = (Object)adaptor.becomeRoot(
                (Object)adaptor.create(CONSTANTS, "CONSTANTS")
                , root_2);

                // Lustre.g:56:19: ( constant )*
                while ( stream_constant.hasNext() ) {
                    adaptor.addChild(root_2, stream_constant.nextTree());

                }
                stream_constant.reset();

                adaptor.addChild(root_1, root_2);
                }

                // Lustre.g:57:7: ^( INPUTS ( $inputs)? )
                {
                Object root_2 = (Object)adaptor.nil();
                root_2 = (Object)adaptor.becomeRoot(
                (Object)adaptor.create(INPUTS, "INPUTS")
                , root_2);

                // Lustre.g:57:17: ( $inputs)?
                if ( stream_inputs.hasNext() ) {
                    adaptor.addChild(root_2, stream_inputs.nextTree());

                }
                stream_inputs.reset();

                adaptor.addChild(root_1, root_2);
                }

                // Lustre.g:58:7: ^( OUTPUTS ( $outputs)? )
                {
                Object root_2 = (Object)adaptor.nil();
                root_2 = (Object)adaptor.becomeRoot(
                (Object)adaptor.create(OUTPUTS, "OUTPUTS")
                , root_2);

                // Lustre.g:58:18: ( $outputs)?
                if ( stream_outputs.hasNext() ) {
                    adaptor.addChild(root_2, stream_outputs.nextTree());

                }
                stream_outputs.reset();

                adaptor.addChild(root_1, root_2);
                }

                // Lustre.g:59:7: ^( LOCALS ( $locals)? )
                {
                Object root_2 = (Object)adaptor.nil();
                root_2 = (Object)adaptor.becomeRoot(
                (Object)adaptor.create(LOCALS, "LOCALS")
                , root_2);

                // Lustre.g:59:17: ( $locals)?
                if ( stream_locals.hasNext() ) {
                    adaptor.addChild(root_2, stream_locals.nextTree());

                }
                stream_locals.reset();

                adaptor.addChild(root_1, root_2);
                }

                // Lustre.g:60:7: ^( EQUATIONS ( equation )* )
                {
                Object root_2 = (Object)adaptor.nil();
                root_2 = (Object)adaptor.becomeRoot(
                (Object)adaptor.create(EQUATIONS, "EQUATIONS")
                , root_2);

                // Lustre.g:60:19: ( equation )*
                while ( stream_equation.hasNext() ) {
                    adaptor.addChild(root_2, stream_equation.nextTree());

                }
                stream_equation.reset();

                adaptor.addChild(root_1, root_2);
                }

                // Lustre.g:61:7: ^( PROPERTIES ( property )* )
                {
                Object root_2 = (Object)adaptor.nil();
                root_2 = (Object)adaptor.becomeRoot(
                (Object)adaptor.create(PROPERTIES, "PROPERTIES")
                , root_2);

                // Lustre.g:61:20: ( property )*
                while ( stream_property.hasNext() ) {
                    adaptor.addChild(root_2, stream_property.nextTree());

                }
                stream_property.reset();

                adaptor.addChild(root_1, root_2);
                }

                adaptor.addChild(root_0, root_1);
                }

            }


            retval.tree = root_0;
            }

            }

            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "node"


    public static class constant_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "constant"
    // Lustre.g:64:1: constant : 'const' ID '=' expr ';' -> ^( ID expr ) ;
    public final LustreParser.constant_return constant() throws RecognitionException {
        LustreParser.constant_return retval = new LustreParser.constant_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token string_literal17=null;
        Token ID18=null;
        Token char_literal19=null;
        Token char_literal21=null;
        LustreParser.expr_return expr20 =null;


        Object string_literal17_tree=null;
        Object ID18_tree=null;
        Object char_literal19_tree=null;
        Object char_literal21_tree=null;
        RewriteRuleTokenStream stream_47=new RewriteRuleTokenStream(adaptor,"token 47");
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleTokenStream stream_35=new RewriteRuleTokenStream(adaptor,"token 35");
        RewriteRuleTokenStream stream_39=new RewriteRuleTokenStream(adaptor,"token 39");
        RewriteRuleSubtreeStream stream_expr=new RewriteRuleSubtreeStream(adaptor,"rule expr");
        try {
            // Lustre.g:64:9: ( 'const' ID '=' expr ';' -> ^( ID expr ) )
            // Lustre.g:65:3: 'const' ID '=' expr ';'
            {
            string_literal17=(Token)match(input,47,FOLLOW_47_in_constant289); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_47.add(string_literal17);


            ID18=(Token)match(input,ID,FOLLOW_ID_in_constant291); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_ID.add(ID18);


            char_literal19=(Token)match(input,39,FOLLOW_39_in_constant293); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_39.add(char_literal19);


            pushFollow(FOLLOW_expr_in_constant295);
            expr20=expr();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_expr.add(expr20.getTree());

            char_literal21=(Token)match(input,35,FOLLOW_35_in_constant297); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_35.add(char_literal21);


            // AST REWRITE
            // elements: ID, expr
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {

            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 65:27: -> ^( ID expr )
            {
                // Lustre.g:65:30: ^( ID expr )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot(
                stream_ID.nextNode()
                , root_1);

                adaptor.addChild(root_1, stream_expr.nextTree());

                adaptor.addChild(root_0, root_1);
                }

            }


            retval.tree = root_0;
            }

            }

            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "constant"


    public static class varDeclList_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "varDeclList"
    // Lustre.g:68:1: varDeclList : varDeclGroup ( ';' varDeclGroup )* -> ( varDeclGroup )+ ;
    public final LustreParser.varDeclList_return varDeclList() throws RecognitionException {
        LustreParser.varDeclList_return retval = new LustreParser.varDeclList_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token char_literal23=null;
        LustreParser.varDeclGroup_return varDeclGroup22 =null;

        LustreParser.varDeclGroup_return varDeclGroup24 =null;


        Object char_literal23_tree=null;
        RewriteRuleTokenStream stream_35=new RewriteRuleTokenStream(adaptor,"token 35");
        RewriteRuleSubtreeStream stream_varDeclGroup=new RewriteRuleSubtreeStream(adaptor,"rule varDeclGroup");
        try {
            // Lustre.g:68:12: ( varDeclGroup ( ';' varDeclGroup )* -> ( varDeclGroup )+ )
            // Lustre.g:69:3: varDeclGroup ( ';' varDeclGroup )*
            {
            pushFollow(FOLLOW_varDeclGroup_in_varDeclList315);
            varDeclGroup22=varDeclGroup();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_varDeclGroup.add(varDeclGroup22.getTree());

            // Lustre.g:69:16: ( ';' varDeclGroup )*
            loop6:
            do {
                int alt6=2;
                int LA6_0 = input.LA(1);

                if ( (LA6_0==35) ) {
                    int LA6_2 = input.LA(2);

                    if ( (LA6_2==ID) ) {
                        alt6=1;
                    }


                }


                switch (alt6) {
            	case 1 :
            	    // Lustre.g:69:17: ';' varDeclGroup
            	    {
            	    char_literal23=(Token)match(input,35,FOLLOW_35_in_varDeclList318); if (state.failed) return retval; 
            	    if ( state.backtracking==0 ) stream_35.add(char_literal23);


            	    pushFollow(FOLLOW_varDeclGroup_in_varDeclList320);
            	    varDeclGroup24=varDeclGroup();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) stream_varDeclGroup.add(varDeclGroup24.getTree());

            	    }
            	    break;

            	default :
            	    break loop6;
                }
            } while (true);


            // AST REWRITE
            // elements: varDeclGroup
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {

            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 69:36: -> ( varDeclGroup )+
            {
                if ( !(stream_varDeclGroup.hasNext()) ) {
                    throw new RewriteEarlyExitException();
                }
                while ( stream_varDeclGroup.hasNext() ) {
                    adaptor.addChild(root_0, stream_varDeclGroup.nextTree());

                }
                stream_varDeclGroup.reset();

            }


            retval.tree = root_0;
            }

            }

            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "varDeclList"


    public static class varDeclGroup_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "varDeclGroup"
    // Lustre.g:72:1: varDeclGroup : ID ( ',' ID )* ':' type -> ( ^( ID type ) )* ;
    public final LustreParser.varDeclGroup_return varDeclGroup() throws RecognitionException {
        LustreParser.varDeclGroup_return retval = new LustreParser.varDeclGroup_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token ID25=null;
        Token char_literal26=null;
        Token ID27=null;
        Token char_literal28=null;
        LustreParser.type_return type29 =null;


        Object ID25_tree=null;
        Object char_literal26_tree=null;
        Object ID27_tree=null;
        Object char_literal28_tree=null;
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleTokenStream stream_34=new RewriteRuleTokenStream(adaptor,"token 34");
        RewriteRuleTokenStream stream_29=new RewriteRuleTokenStream(adaptor,"token 29");
        RewriteRuleSubtreeStream stream_type=new RewriteRuleSubtreeStream(adaptor,"rule type");
        try {
            // Lustre.g:72:13: ( ID ( ',' ID )* ':' type -> ( ^( ID type ) )* )
            // Lustre.g:73:3: ID ( ',' ID )* ':' type
            {
            ID25=(Token)match(input,ID,FOLLOW_ID_in_varDeclGroup337); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_ID.add(ID25);


            // Lustre.g:73:6: ( ',' ID )*
            loop7:
            do {
                int alt7=2;
                int LA7_0 = input.LA(1);

                if ( (LA7_0==29) ) {
                    alt7=1;
                }


                switch (alt7) {
            	case 1 :
            	    // Lustre.g:73:7: ',' ID
            	    {
            	    char_literal26=(Token)match(input,29,FOLLOW_29_in_varDeclGroup340); if (state.failed) return retval; 
            	    if ( state.backtracking==0 ) stream_29.add(char_literal26);


            	    ID27=(Token)match(input,ID,FOLLOW_ID_in_varDeclGroup342); if (state.failed) return retval; 
            	    if ( state.backtracking==0 ) stream_ID.add(ID27);


            	    }
            	    break;

            	default :
            	    break loop7;
                }
            } while (true);


            char_literal28=(Token)match(input,34,FOLLOW_34_in_varDeclGroup346); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_34.add(char_literal28);


            pushFollow(FOLLOW_type_in_varDeclGroup348);
            type29=type();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_type.add(type29.getTree());

            // AST REWRITE
            // elements: type, ID
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {

            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 73:25: -> ( ^( ID type ) )*
            {
                // Lustre.g:73:28: ( ^( ID type ) )*
                while ( stream_type.hasNext()||stream_ID.hasNext() ) {
                    // Lustre.g:73:28: ^( ID type )
                    {
                    Object root_1 = (Object)adaptor.nil();
                    root_1 = (Object)adaptor.becomeRoot(
                    stream_ID.nextNode()
                    , root_1);

                    adaptor.addChild(root_1, stream_type.nextTree());

                    adaptor.addChild(root_0, root_1);
                    }

                }
                stream_type.reset();
                stream_ID.reset();

            }


            retval.tree = root_0;
            }

            }

            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "varDeclGroup"


    public static class type_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "type"
    // Lustre.g:76:1: type : ( 'int' ^| 'subrange' '[' bound ',' bound ']' 'of' 'int' -> 'int' | 'bool' ^| 'real' ^);
    public final LustreParser.type_return type() throws RecognitionException {
        LustreParser.type_return retval = new LustreParser.type_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token string_literal30=null;
        Token string_literal31=null;
        Token char_literal32=null;
        Token char_literal34=null;
        Token char_literal36=null;
        Token string_literal37=null;
        Token string_literal38=null;
        Token string_literal39=null;
        Token string_literal40=null;
        LustreParser.bound_return bound33 =null;

        LustreParser.bound_return bound35 =null;


        Object string_literal30_tree=null;
        Object string_literal31_tree=null;
        Object char_literal32_tree=null;
        Object char_literal34_tree=null;
        Object char_literal36_tree=null;
        Object string_literal37_tree=null;
        Object string_literal38_tree=null;
        Object string_literal39_tree=null;
        Object string_literal40_tree=null;
        RewriteRuleTokenStream stream_43=new RewriteRuleTokenStream(adaptor,"token 43");
        RewriteRuleTokenStream stream_44=new RewriteRuleTokenStream(adaptor,"token 44");
        RewriteRuleTokenStream stream_57=new RewriteRuleTokenStream(adaptor,"token 57");
        RewriteRuleTokenStream stream_53=new RewriteRuleTokenStream(adaptor,"token 53");
        RewriteRuleTokenStream stream_29=new RewriteRuleTokenStream(adaptor,"token 29");
        RewriteRuleTokenStream stream_50=new RewriteRuleTokenStream(adaptor,"token 50");
        RewriteRuleSubtreeStream stream_bound=new RewriteRuleSubtreeStream(adaptor,"rule bound");
        try {
            // Lustre.g:76:5: ( 'int' ^| 'subrange' '[' bound ',' bound ']' 'of' 'int' -> 'int' | 'bool' ^| 'real' ^)
            int alt8=4;
            switch ( input.LA(1) ) {
            case 50:
                {
                alt8=1;
                }
                break;
            case 57:
                {
                alt8=2;
                }
                break;
            case 46:
                {
                alt8=3;
                }
                break;
            case 55:
                {
                alt8=4;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 8, 0, input);

                throw nvae;

            }

            switch (alt8) {
                case 1 :
                    // Lustre.g:77:3: 'int' ^
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal30=(Token)match(input,50,FOLLOW_50_in_type367); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal30_tree = 
                    (Object)adaptor.create(string_literal30)
                    ;
                    root_0 = (Object)adaptor.becomeRoot(string_literal30_tree, root_0);
                    }

                    }
                    break;
                case 2 :
                    // Lustre.g:78:3: 'subrange' '[' bound ',' bound ']' 'of' 'int'
                    {
                    string_literal31=(Token)match(input,57,FOLLOW_57_in_type372); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_57.add(string_literal31);


                    char_literal32=(Token)match(input,43,FOLLOW_43_in_type374); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_43.add(char_literal32);


                    pushFollow(FOLLOW_bound_in_type376);
                    bound33=bound();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_bound.add(bound33.getTree());

                    char_literal34=(Token)match(input,29,FOLLOW_29_in_type378); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_29.add(char_literal34);


                    pushFollow(FOLLOW_bound_in_type380);
                    bound35=bound();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_bound.add(bound35.getTree());

                    char_literal36=(Token)match(input,44,FOLLOW_44_in_type382); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_44.add(char_literal36);


                    string_literal37=(Token)match(input,53,FOLLOW_53_in_type384); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_53.add(string_literal37);


                    string_literal38=(Token)match(input,50,FOLLOW_50_in_type386); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_50.add(string_literal38);


                    // AST REWRITE
                    // elements: 50
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {

                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 78:49: -> 'int'
                    {
                        adaptor.addChild(root_0, 
                        stream_50.nextNode()
                        );

                    }


                    retval.tree = root_0;
                    }

                    }
                    break;
                case 3 :
                    // Lustre.g:79:3: 'bool' ^
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal39=(Token)match(input,46,FOLLOW_46_in_type394); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal39_tree = 
                    (Object)adaptor.create(string_literal39)
                    ;
                    root_0 = (Object)adaptor.becomeRoot(string_literal39_tree, root_0);
                    }

                    }
                    break;
                case 4 :
                    // Lustre.g:80:3: 'real' ^
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal40=(Token)match(input,55,FOLLOW_55_in_type399); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal40_tree = 
                    (Object)adaptor.create(string_literal40)
                    ;
                    root_0 = (Object)adaptor.becomeRoot(string_literal40_tree, root_0);
                    }

                    }
                    break;

            }
            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "type"


    public static class bound_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "bound"
    // Lustre.g:83:1: bound : ( '-' )? INT ;
    public final LustreParser.bound_return bound() throws RecognitionException {
        LustreParser.bound_return retval = new LustreParser.bound_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token char_literal41=null;
        Token INT42=null;

        Object char_literal41_tree=null;
        Object INT42_tree=null;

        try {
            // Lustre.g:83:6: ( ( '-' )? INT )
            // Lustre.g:84:3: ( '-' )? INT
            {
            root_0 = (Object)adaptor.nil();


            // Lustre.g:84:3: ( '-' )?
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( (LA9_0==30) ) {
                alt9=1;
            }
            switch (alt9) {
                case 1 :
                    // Lustre.g:84:3: '-'
                    {
                    char_literal41=(Token)match(input,30,FOLLOW_30_in_bound410); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal41_tree = 
                    (Object)adaptor.create(char_literal41)
                    ;
                    adaptor.addChild(root_0, char_literal41_tree);
                    }

                    }
                    break;

            }


            INT42=(Token)match(input,INT,FOLLOW_INT_in_bound413); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            INT42_tree = 
            (Object)adaptor.create(INT42)
            ;
            adaptor.addChild(root_0, INT42_tree);
            }

            }

            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "bound"


    public static class property_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "property"
    // Lustre.g:87:1: property : '--%PROPERTY' ID ';' -> ID ;
    public final LustreParser.property_return property() throws RecognitionException {
        LustreParser.property_return retval = new LustreParser.property_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token string_literal43=null;
        Token ID44=null;
        Token char_literal45=null;

        Object string_literal43_tree=null;
        Object ID44_tree=null;
        Object char_literal45_tree=null;
        RewriteRuleTokenStream stream_31=new RewriteRuleTokenStream(adaptor,"token 31");
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleTokenStream stream_35=new RewriteRuleTokenStream(adaptor,"token 35");

        try {
            // Lustre.g:87:9: ( '--%PROPERTY' ID ';' -> ID )
            // Lustre.g:88:3: '--%PROPERTY' ID ';'
            {
            string_literal43=(Token)match(input,31,FOLLOW_31_in_property423); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_31.add(string_literal43);


            ID44=(Token)match(input,ID,FOLLOW_ID_in_property425); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_ID.add(ID44);


            char_literal45=(Token)match(input,35,FOLLOW_35_in_property427); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_35.add(char_literal45);


            // AST REWRITE
            // elements: ID
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {

            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 88:24: -> ID
            {
                adaptor.addChild(root_0, 
                stream_ID.nextNode()
                );

            }


            retval.tree = root_0;
            }

            }

            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "property"


    public static class equation_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "equation"
    // Lustre.g:91:1: equation : ID '=' expr ';' -> ^( ID expr ) ;
    public final LustreParser.equation_return equation() throws RecognitionException {
        LustreParser.equation_return retval = new LustreParser.equation_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token ID46=null;
        Token char_literal47=null;
        Token char_literal49=null;
        LustreParser.expr_return expr48 =null;


        Object ID46_tree=null;
        Object char_literal47_tree=null;
        Object char_literal49_tree=null;
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleTokenStream stream_35=new RewriteRuleTokenStream(adaptor,"token 35");
        RewriteRuleTokenStream stream_39=new RewriteRuleTokenStream(adaptor,"token 39");
        RewriteRuleSubtreeStream stream_expr=new RewriteRuleSubtreeStream(adaptor,"rule expr");
        try {
            // Lustre.g:91:9: ( ID '=' expr ';' -> ^( ID expr ) )
            // Lustre.g:92:3: ID '=' expr ';'
            {
            ID46=(Token)match(input,ID,FOLLOW_ID_in_equation441); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_ID.add(ID46);


            char_literal47=(Token)match(input,39,FOLLOW_39_in_equation443); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_39.add(char_literal47);


            pushFollow(FOLLOW_expr_in_equation445);
            expr48=expr();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_expr.add(expr48.getTree());

            char_literal49=(Token)match(input,35,FOLLOW_35_in_equation447); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_35.add(char_literal49);


            // AST REWRITE
            // elements: expr, ID
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {

            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 92:19: -> ^( ID expr )
            {
                // Lustre.g:92:22: ^( ID expr )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot(
                stream_ID.nextNode()
                , root_1);

                adaptor.addChild(root_1, stream_expr.nextTree());

                adaptor.addChild(root_0, root_1);
                }

            }


            retval.tree = root_0;
            }

            }

            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "equation"


    public static class expr_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "expr"
    // Lustre.g:95:1: expr : arrowExpr ;
    public final LustreParser.expr_return expr() throws RecognitionException {
        LustreParser.expr_return retval = new LustreParser.expr_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        LustreParser.arrowExpr_return arrowExpr50 =null;



        try {
            // Lustre.g:95:5: ( arrowExpr )
            // Lustre.g:96:3: arrowExpr
            {
            root_0 = (Object)adaptor.nil();


            pushFollow(FOLLOW_arrowExpr_in_expr465);
            arrowExpr50=arrowExpr();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, arrowExpr50.getTree());

            }

            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "expr"


    public static class arrowOp_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "arrowOp"
    // Lustre.g:99:1: arrowOp : '->' ;
    public final LustreParser.arrowOp_return arrowOp() throws RecognitionException {
        LustreParser.arrowOp_return retval = new LustreParser.arrowOp_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token string_literal51=null;

        Object string_literal51_tree=null;

        try {
            // Lustre.g:99:8: ( '->' )
            // Lustre.g:100:3: '->'
            {
            root_0 = (Object)adaptor.nil();


            string_literal51=(Token)match(input,32,FOLLOW_32_in_arrowOp475); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            string_literal51_tree = 
            (Object)adaptor.create(string_literal51)
            ;
            adaptor.addChild(root_0, string_literal51_tree);
            }

            }

            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "arrowOp"


    public static class arrowExpr_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "arrowExpr"
    // Lustre.g:103:1: arrowExpr : impliesExpr ( ( arrowOp )=> arrowOp ^ arrowExpr )? ;
    public final LustreParser.arrowExpr_return arrowExpr() throws RecognitionException {
        LustreParser.arrowExpr_return retval = new LustreParser.arrowExpr_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        LustreParser.impliesExpr_return impliesExpr52 =null;

        LustreParser.arrowOp_return arrowOp53 =null;

        LustreParser.arrowExpr_return arrowExpr54 =null;



        try {
            // Lustre.g:103:10: ( impliesExpr ( ( arrowOp )=> arrowOp ^ arrowExpr )? )
            // Lustre.g:104:3: impliesExpr ( ( arrowOp )=> arrowOp ^ arrowExpr )?
            {
            root_0 = (Object)adaptor.nil();


            pushFollow(FOLLOW_impliesExpr_in_arrowExpr485);
            impliesExpr52=impliesExpr();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, impliesExpr52.getTree());

            // Lustre.g:104:15: ( ( arrowOp )=> arrowOp ^ arrowExpr )?
            int alt10=2;
            int LA10_0 = input.LA(1);

            if ( (LA10_0==32) ) {
                int LA10_1 = input.LA(2);

                if ( (synpred1_Lustre()) ) {
                    alt10=1;
                }
            }
            switch (alt10) {
                case 1 :
                    // Lustre.g:104:16: ( arrowOp )=> arrowOp ^ arrowExpr
                    {
                    pushFollow(FOLLOW_arrowOp_in_arrowExpr492);
                    arrowOp53=arrowOp();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) root_0 = (Object)adaptor.becomeRoot(arrowOp53.getTree(), root_0);

                    pushFollow(FOLLOW_arrowExpr_in_arrowExpr495);
                    arrowExpr54=arrowExpr();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, arrowExpr54.getTree());

                    }
                    break;

            }


            }

            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "arrowExpr"


    public static class impliesOp_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "impliesOp"
    // Lustre.g:107:1: impliesOp : '=>' ;
    public final LustreParser.impliesOp_return impliesOp() throws RecognitionException {
        LustreParser.impliesOp_return retval = new LustreParser.impliesOp_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token string_literal55=null;

        Object string_literal55_tree=null;

        try {
            // Lustre.g:107:10: ( '=>' )
            // Lustre.g:108:3: '=>'
            {
            root_0 = (Object)adaptor.nil();


            string_literal55=(Token)match(input,40,FOLLOW_40_in_impliesOp507); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            string_literal55_tree = 
            (Object)adaptor.create(string_literal55)
            ;
            adaptor.addChild(root_0, string_literal55_tree);
            }

            }

            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "impliesOp"


    public static class impliesExpr_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "impliesExpr"
    // Lustre.g:111:1: impliesExpr : orExpr ( ( impliesOp )=> impliesOp ^ impliesExpr )? ;
    public final LustreParser.impliesExpr_return impliesExpr() throws RecognitionException {
        LustreParser.impliesExpr_return retval = new LustreParser.impliesExpr_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        LustreParser.orExpr_return orExpr56 =null;

        LustreParser.impliesOp_return impliesOp57 =null;

        LustreParser.impliesExpr_return impliesExpr58 =null;



        try {
            // Lustre.g:111:12: ( orExpr ( ( impliesOp )=> impliesOp ^ impliesExpr )? )
            // Lustre.g:112:3: orExpr ( ( impliesOp )=> impliesOp ^ impliesExpr )?
            {
            root_0 = (Object)adaptor.nil();


            pushFollow(FOLLOW_orExpr_in_impliesExpr517);
            orExpr56=orExpr();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, orExpr56.getTree());

            // Lustre.g:112:10: ( ( impliesOp )=> impliesOp ^ impliesExpr )?
            int alt11=2;
            int LA11_0 = input.LA(1);

            if ( (LA11_0==40) ) {
                int LA11_1 = input.LA(2);

                if ( (synpred2_Lustre()) ) {
                    alt11=1;
                }
            }
            switch (alt11) {
                case 1 :
                    // Lustre.g:112:11: ( impliesOp )=> impliesOp ^ impliesExpr
                    {
                    pushFollow(FOLLOW_impliesOp_in_impliesExpr524);
                    impliesOp57=impliesOp();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) root_0 = (Object)adaptor.becomeRoot(impliesOp57.getTree(), root_0);

                    pushFollow(FOLLOW_impliesExpr_in_impliesExpr527);
                    impliesExpr58=impliesExpr();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, impliesExpr58.getTree());

                    }
                    break;

            }


            }

            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "impliesExpr"


    public static class orOp_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "orOp"
    // Lustre.g:115:1: orOp : ( 'or' | 'xor' );
    public final LustreParser.orOp_return orOp() throws RecognitionException {
        LustreParser.orOp_return retval = new LustreParser.orOp_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token set59=null;

        Object set59_tree=null;

        try {
            // Lustre.g:115:5: ( 'or' | 'xor' )
            // Lustre.g:
            {
            root_0 = (Object)adaptor.nil();


            set59=(Token)input.LT(1);

            if ( input.LA(1)==54||input.LA(1)==61 ) {
                input.consume();
                if ( state.backtracking==0 ) adaptor.addChild(root_0, 
                (Object)adaptor.create(set59)
                );
                state.errorRecovery=false;
                state.failed=false;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }


            }

            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "orOp"


    public static class orExpr_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "orExpr"
    // Lustre.g:119:1: orExpr : andExpr ( ( orOp )=> orOp ^ andExpr )* ;
    public final LustreParser.orExpr_return orExpr() throws RecognitionException {
        LustreParser.orExpr_return retval = new LustreParser.orExpr_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        LustreParser.andExpr_return andExpr60 =null;

        LustreParser.orOp_return orOp61 =null;

        LustreParser.andExpr_return andExpr62 =null;



        try {
            // Lustre.g:119:7: ( andExpr ( ( orOp )=> orOp ^ andExpr )* )
            // Lustre.g:120:3: andExpr ( ( orOp )=> orOp ^ andExpr )*
            {
            root_0 = (Object)adaptor.nil();


            pushFollow(FOLLOW_andExpr_in_orExpr553);
            andExpr60=andExpr();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, andExpr60.getTree());

            // Lustre.g:120:11: ( ( orOp )=> orOp ^ andExpr )*
            loop12:
            do {
                int alt12=2;
                int LA12_0 = input.LA(1);

                if ( (LA12_0==54||LA12_0==61) ) {
                    int LA12_2 = input.LA(2);

                    if ( (synpred3_Lustre()) ) {
                        alt12=1;
                    }


                }


                switch (alt12) {
            	case 1 :
            	    // Lustre.g:120:12: ( orOp )=> orOp ^ andExpr
            	    {
            	    pushFollow(FOLLOW_orOp_in_orExpr560);
            	    orOp61=orOp();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) root_0 = (Object)adaptor.becomeRoot(orOp61.getTree(), root_0);

            	    pushFollow(FOLLOW_andExpr_in_orExpr563);
            	    andExpr62=andExpr();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, andExpr62.getTree());

            	    }
            	    break;

            	default :
            	    break loop12;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "orExpr"


    public static class andOp_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "andOp"
    // Lustre.g:123:1: andOp : 'and' ;
    public final LustreParser.andOp_return andOp() throws RecognitionException {
        LustreParser.andOp_return retval = new LustreParser.andOp_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token string_literal63=null;

        Object string_literal63_tree=null;

        try {
            // Lustre.g:123:6: ( 'and' )
            // Lustre.g:124:3: 'and'
            {
            root_0 = (Object)adaptor.nil();


            string_literal63=(Token)match(input,45,FOLLOW_45_in_andOp575); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            string_literal63_tree = 
            (Object)adaptor.create(string_literal63)
            ;
            adaptor.addChild(root_0, string_literal63_tree);
            }

            }

            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "andOp"


    public static class andExpr_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "andExpr"
    // Lustre.g:127:1: andExpr : relationalExpr ( ( andOp )=> andOp ^ relationalExpr )* ;
    public final LustreParser.andExpr_return andExpr() throws RecognitionException {
        LustreParser.andExpr_return retval = new LustreParser.andExpr_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        LustreParser.relationalExpr_return relationalExpr64 =null;

        LustreParser.andOp_return andOp65 =null;

        LustreParser.relationalExpr_return relationalExpr66 =null;



        try {
            // Lustre.g:127:8: ( relationalExpr ( ( andOp )=> andOp ^ relationalExpr )* )
            // Lustre.g:128:3: relationalExpr ( ( andOp )=> andOp ^ relationalExpr )*
            {
            root_0 = (Object)adaptor.nil();


            pushFollow(FOLLOW_relationalExpr_in_andExpr585);
            relationalExpr64=relationalExpr();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, relationalExpr64.getTree());

            // Lustre.g:128:18: ( ( andOp )=> andOp ^ relationalExpr )*
            loop13:
            do {
                int alt13=2;
                int LA13_0 = input.LA(1);

                if ( (LA13_0==45) ) {
                    int LA13_2 = input.LA(2);

                    if ( (synpred4_Lustre()) ) {
                        alt13=1;
                    }


                }


                switch (alt13) {
            	case 1 :
            	    // Lustre.g:128:19: ( andOp )=> andOp ^ relationalExpr
            	    {
            	    pushFollow(FOLLOW_andOp_in_andExpr592);
            	    andOp65=andOp();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) root_0 = (Object)adaptor.becomeRoot(andOp65.getTree(), root_0);

            	    pushFollow(FOLLOW_relationalExpr_in_andExpr595);
            	    relationalExpr66=relationalExpr();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, relationalExpr66.getTree());

            	    }
            	    break;

            	default :
            	    break loop13;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "andExpr"


    public static class relationalOp_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "relationalOp"
    // Lustre.g:131:1: relationalOp : ( '<' | '<=' | '>' | '>=' | '=' | '<>' );
    public final LustreParser.relationalOp_return relationalOp() throws RecognitionException {
        LustreParser.relationalOp_return retval = new LustreParser.relationalOp_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token set67=null;

        Object set67_tree=null;

        try {
            // Lustre.g:131:13: ( '<' | '<=' | '>' | '>=' | '=' | '<>' )
            // Lustre.g:
            {
            root_0 = (Object)adaptor.nil();


            set67=(Token)input.LT(1);

            if ( (input.LA(1) >= 36 && input.LA(1) <= 39)||(input.LA(1) >= 41 && input.LA(1) <= 42) ) {
                input.consume();
                if ( state.backtracking==0 ) adaptor.addChild(root_0, 
                (Object)adaptor.create(set67)
                );
                state.errorRecovery=false;
                state.failed=false;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }


            }

            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "relationalOp"


    public static class relationalExpr_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "relationalExpr"
    // Lustre.g:135:1: relationalExpr : plusExpr ( ( relationalOp )=> relationalOp ^ plusExpr )? ;
    public final LustreParser.relationalExpr_return relationalExpr() throws RecognitionException {
        LustreParser.relationalExpr_return retval = new LustreParser.relationalExpr_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        LustreParser.plusExpr_return plusExpr68 =null;

        LustreParser.relationalOp_return relationalOp69 =null;

        LustreParser.plusExpr_return plusExpr70 =null;



        try {
            // Lustre.g:135:15: ( plusExpr ( ( relationalOp )=> relationalOp ^ plusExpr )? )
            // Lustre.g:136:3: plusExpr ( ( relationalOp )=> relationalOp ^ plusExpr )?
            {
            root_0 = (Object)adaptor.nil();


            pushFollow(FOLLOW_plusExpr_in_relationalExpr637);
            plusExpr68=plusExpr();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, plusExpr68.getTree());

            // Lustre.g:136:12: ( ( relationalOp )=> relationalOp ^ plusExpr )?
            int alt14=2;
            int LA14_0 = input.LA(1);

            if ( ((LA14_0 >= 36 && LA14_0 <= 39)||(LA14_0 >= 41 && LA14_0 <= 42)) ) {
                int LA14_1 = input.LA(2);

                if ( (synpred5_Lustre()) ) {
                    alt14=1;
                }
            }
            switch (alt14) {
                case 1 :
                    // Lustre.g:136:13: ( relationalOp )=> relationalOp ^ plusExpr
                    {
                    pushFollow(FOLLOW_relationalOp_in_relationalExpr644);
                    relationalOp69=relationalOp();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) root_0 = (Object)adaptor.becomeRoot(relationalOp69.getTree(), root_0);

                    pushFollow(FOLLOW_plusExpr_in_relationalExpr647);
                    plusExpr70=plusExpr();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, plusExpr70.getTree());

                    }
                    break;

            }


            }

            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "relationalExpr"


    public static class plusOp_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "plusOp"
    // Lustre.g:139:1: plusOp : ( '+' | '-' );
    public final LustreParser.plusOp_return plusOp() throws RecognitionException {
        LustreParser.plusOp_return retval = new LustreParser.plusOp_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token set71=null;

        Object set71_tree=null;

        try {
            // Lustre.g:139:7: ( '+' | '-' )
            // Lustre.g:
            {
            root_0 = (Object)adaptor.nil();


            set71=(Token)input.LT(1);

            if ( input.LA(1)==28||input.LA(1)==30 ) {
                input.consume();
                if ( state.backtracking==0 ) adaptor.addChild(root_0, 
                (Object)adaptor.create(set71)
                );
                state.errorRecovery=false;
                state.failed=false;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }


            }

            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "plusOp"


    public static class plusExpr_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "plusExpr"
    // Lustre.g:143:1: plusExpr : timesExpr ( ( plusOp )=> plusOp ^ timesExpr )* ;
    public final LustreParser.plusExpr_return plusExpr() throws RecognitionException {
        LustreParser.plusExpr_return retval = new LustreParser.plusExpr_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        LustreParser.timesExpr_return timesExpr72 =null;

        LustreParser.plusOp_return plusOp73 =null;

        LustreParser.timesExpr_return timesExpr74 =null;



        try {
            // Lustre.g:143:9: ( timesExpr ( ( plusOp )=> plusOp ^ timesExpr )* )
            // Lustre.g:144:3: timesExpr ( ( plusOp )=> plusOp ^ timesExpr )*
            {
            root_0 = (Object)adaptor.nil();


            pushFollow(FOLLOW_timesExpr_in_plusExpr673);
            timesExpr72=timesExpr();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, timesExpr72.getTree());

            // Lustre.g:144:13: ( ( plusOp )=> plusOp ^ timesExpr )*
            loop15:
            do {
                int alt15=2;
                int LA15_0 = input.LA(1);

                if ( (LA15_0==28||LA15_0==30) ) {
                    int LA15_2 = input.LA(2);

                    if ( (synpred6_Lustre()) ) {
                        alt15=1;
                    }


                }


                switch (alt15) {
            	case 1 :
            	    // Lustre.g:144:14: ( plusOp )=> plusOp ^ timesExpr
            	    {
            	    pushFollow(FOLLOW_plusOp_in_plusExpr680);
            	    plusOp73=plusOp();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) root_0 = (Object)adaptor.becomeRoot(plusOp73.getTree(), root_0);

            	    pushFollow(FOLLOW_timesExpr_in_plusExpr683);
            	    timesExpr74=timesExpr();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, timesExpr74.getTree());

            	    }
            	    break;

            	default :
            	    break loop15;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "plusExpr"


    public static class timesOp_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "timesOp"
    // Lustre.g:147:1: timesOp : ( '*' | '/' | 'div' );
    public final LustreParser.timesOp_return timesOp() throws RecognitionException {
        LustreParser.timesOp_return retval = new LustreParser.timesOp_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token set75=null;

        Object set75_tree=null;

        try {
            // Lustre.g:147:8: ( '*' | '/' | 'div' )
            // Lustre.g:
            {
            root_0 = (Object)adaptor.nil();


            set75=(Token)input.LT(1);

            if ( input.LA(1)==27||input.LA(1)==33||input.LA(1)==48 ) {
                input.consume();
                if ( state.backtracking==0 ) adaptor.addChild(root_0, 
                (Object)adaptor.create(set75)
                );
                state.errorRecovery=false;
                state.failed=false;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }


            }

            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "timesOp"


    public static class timesExpr_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "timesExpr"
    // Lustre.g:151:1: timesExpr : prefixExpr ( ( timesOp )=> timesOp ^ prefixExpr )* ;
    public final LustreParser.timesExpr_return timesExpr() throws RecognitionException {
        LustreParser.timesExpr_return retval = new LustreParser.timesExpr_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        LustreParser.prefixExpr_return prefixExpr76 =null;

        LustreParser.timesOp_return timesOp77 =null;

        LustreParser.prefixExpr_return prefixExpr78 =null;



        try {
            // Lustre.g:151:10: ( prefixExpr ( ( timesOp )=> timesOp ^ prefixExpr )* )
            // Lustre.g:152:3: prefixExpr ( ( timesOp )=> timesOp ^ prefixExpr )*
            {
            root_0 = (Object)adaptor.nil();


            pushFollow(FOLLOW_prefixExpr_in_timesExpr713);
            prefixExpr76=prefixExpr();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, prefixExpr76.getTree());

            // Lustre.g:152:14: ( ( timesOp )=> timesOp ^ prefixExpr )*
            loop16:
            do {
                int alt16=2;
                int LA16_0 = input.LA(1);

                if ( (LA16_0==27||LA16_0==33||LA16_0==48) ) {
                    int LA16_2 = input.LA(2);

                    if ( (synpred7_Lustre()) ) {
                        alt16=1;
                    }


                }


                switch (alt16) {
            	case 1 :
            	    // Lustre.g:152:15: ( timesOp )=> timesOp ^ prefixExpr
            	    {
            	    pushFollow(FOLLOW_timesOp_in_timesExpr720);
            	    timesOp77=timesOp();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) root_0 = (Object)adaptor.becomeRoot(timesOp77.getTree(), root_0);

            	    pushFollow(FOLLOW_prefixExpr_in_timesExpr723);
            	    prefixExpr78=prefixExpr();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, prefixExpr78.getTree());

            	    }
            	    break;

            	default :
            	    break loop16;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "timesExpr"


    public static class prefixExpr_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "prefixExpr"
    // Lustre.g:155:1: prefixExpr : ( '-' prefixExpr -> ^( NEGATE prefixExpr ) | NOT ^ prefixExpr | PRE ^ prefixExpr | atomicExpr );
    public final LustreParser.prefixExpr_return prefixExpr() throws RecognitionException {
        LustreParser.prefixExpr_return retval = new LustreParser.prefixExpr_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token char_literal79=null;
        Token NOT81=null;
        Token PRE83=null;
        LustreParser.prefixExpr_return prefixExpr80 =null;

        LustreParser.prefixExpr_return prefixExpr82 =null;

        LustreParser.prefixExpr_return prefixExpr84 =null;

        LustreParser.atomicExpr_return atomicExpr85 =null;


        Object char_literal79_tree=null;
        Object NOT81_tree=null;
        Object PRE83_tree=null;
        RewriteRuleTokenStream stream_30=new RewriteRuleTokenStream(adaptor,"token 30");
        RewriteRuleSubtreeStream stream_prefixExpr=new RewriteRuleSubtreeStream(adaptor,"rule prefixExpr");
        try {
            // Lustre.g:155:11: ( '-' prefixExpr -> ^( NEGATE prefixExpr ) | NOT ^ prefixExpr | PRE ^ prefixExpr | atomicExpr )
            int alt17=4;
            switch ( input.LA(1) ) {
            case 30:
                {
                alt17=1;
                }
                break;
            case NOT:
                {
                alt17=2;
                }
                break;
            case PRE:
                {
                alt17=3;
                }
                break;
            case BOOL:
            case ID:
            case IF:
            case INT:
            case 25:
                {
                alt17=4;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 17, 0, input);

                throw nvae;

            }

            switch (alt17) {
                case 1 :
                    // Lustre.g:156:3: '-' prefixExpr
                    {
                    char_literal79=(Token)match(input,30,FOLLOW_30_in_prefixExpr735); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_30.add(char_literal79);


                    pushFollow(FOLLOW_prefixExpr_in_prefixExpr737);
                    prefixExpr80=prefixExpr();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_prefixExpr.add(prefixExpr80.getTree());

                    // AST REWRITE
                    // elements: prefixExpr
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {

                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 156:18: -> ^( NEGATE prefixExpr )
                    {
                        // Lustre.g:156:21: ^( NEGATE prefixExpr )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot(
                        (Object)adaptor.create(NEGATE, "NEGATE")
                        , root_1);

                        adaptor.addChild(root_1, stream_prefixExpr.nextTree());

                        adaptor.addChild(root_0, root_1);
                        }

                    }


                    retval.tree = root_0;
                    }

                    }
                    break;
                case 2 :
                    // Lustre.g:157:3: NOT ^ prefixExpr
                    {
                    root_0 = (Object)adaptor.nil();


                    NOT81=(Token)match(input,NOT,FOLLOW_NOT_in_prefixExpr749); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    NOT81_tree = 
                    (Object)adaptor.create(NOT81)
                    ;
                    root_0 = (Object)adaptor.becomeRoot(NOT81_tree, root_0);
                    }

                    pushFollow(FOLLOW_prefixExpr_in_prefixExpr752);
                    prefixExpr82=prefixExpr();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, prefixExpr82.getTree());

                    }
                    break;
                case 3 :
                    // Lustre.g:158:3: PRE ^ prefixExpr
                    {
                    root_0 = (Object)adaptor.nil();


                    PRE83=(Token)match(input,PRE,FOLLOW_PRE_in_prefixExpr756); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    PRE83_tree = 
                    (Object)adaptor.create(PRE83)
                    ;
                    root_0 = (Object)adaptor.becomeRoot(PRE83_tree, root_0);
                    }

                    pushFollow(FOLLOW_prefixExpr_in_prefixExpr759);
                    prefixExpr84=prefixExpr();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, prefixExpr84.getTree());

                    }
                    break;
                case 4 :
                    // Lustre.g:159:3: atomicExpr
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_atomicExpr_in_prefixExpr763);
                    atomicExpr85=atomicExpr();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, atomicExpr85.getTree());

                    }
                    break;

            }
            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "prefixExpr"


    public static class atomicExpr_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "atomicExpr"
    // Lustre.g:162:1: atomicExpr : ( ID -> ^( IDENT ID ) | INT | real | BOOL | IF ^ expr 'then' ! expr 'else' ! expr | '(' expr ')' -> expr );
    public final LustreParser.atomicExpr_return atomicExpr() throws RecognitionException {
        LustreParser.atomicExpr_return retval = new LustreParser.atomicExpr_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token ID86=null;
        Token INT87=null;
        Token BOOL89=null;
        Token IF90=null;
        Token string_literal92=null;
        Token string_literal94=null;
        Token char_literal96=null;
        Token char_literal98=null;
        LustreParser.real_return real88 =null;

        LustreParser.expr_return expr91 =null;

        LustreParser.expr_return expr93 =null;

        LustreParser.expr_return expr95 =null;

        LustreParser.expr_return expr97 =null;


        Object ID86_tree=null;
        Object INT87_tree=null;
        Object BOOL89_tree=null;
        Object IF90_tree=null;
        Object string_literal92_tree=null;
        Object string_literal94_tree=null;
        Object char_literal96_tree=null;
        Object char_literal98_tree=null;
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleTokenStream stream_25=new RewriteRuleTokenStream(adaptor,"token 25");
        RewriteRuleTokenStream stream_26=new RewriteRuleTokenStream(adaptor,"token 26");
        RewriteRuleSubtreeStream stream_expr=new RewriteRuleSubtreeStream(adaptor,"rule expr");
        try {
            // Lustre.g:162:11: ( ID -> ^( IDENT ID ) | INT | real | BOOL | IF ^ expr 'then' ! expr 'else' ! expr | '(' expr ')' -> expr )
            int alt18=6;
            switch ( input.LA(1) ) {
            case ID:
                {
                alt18=1;
                }
                break;
            case INT:
                {
                int LA18_2 = input.LA(2);

                if ( (LA18_2==ERROR) ) {
                    alt18=3;
                }
                else if ( ((LA18_2 >= 26 && LA18_2 <= 28)||LA18_2==30||(LA18_2 >= 32 && LA18_2 <= 33)||(LA18_2 >= 35 && LA18_2 <= 42)||LA18_2==45||(LA18_2 >= 48 && LA18_2 <= 49)||LA18_2==54||LA18_2==59||LA18_2==61) ) {
                    alt18=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 18, 2, input);

                    throw nvae;

                }
                }
                break;
            case BOOL:
                {
                alt18=4;
                }
                break;
            case IF:
                {
                alt18=5;
                }
                break;
            case 25:
                {
                alt18=6;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 18, 0, input);

                throw nvae;

            }

            switch (alt18) {
                case 1 :
                    // Lustre.g:163:3: ID
                    {
                    ID86=(Token)match(input,ID,FOLLOW_ID_in_atomicExpr773); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_ID.add(ID86);


                    // AST REWRITE
                    // elements: ID
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {

                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 163:6: -> ^( IDENT ID )
                    {
                        // Lustre.g:163:9: ^( IDENT ID )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot(
                        (Object)adaptor.create(IDENT, "IDENT")
                        , root_1);

                        adaptor.addChild(root_1, 
                        stream_ID.nextNode()
                        );

                        adaptor.addChild(root_0, root_1);
                        }

                    }


                    retval.tree = root_0;
                    }

                    }
                    break;
                case 2 :
                    // Lustre.g:164:3: INT
                    {
                    root_0 = (Object)adaptor.nil();


                    INT87=(Token)match(input,INT,FOLLOW_INT_in_atomicExpr785); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    INT87_tree = 
                    (Object)adaptor.create(INT87)
                    ;
                    adaptor.addChild(root_0, INT87_tree);
                    }

                    }
                    break;
                case 3 :
                    // Lustre.g:165:3: real
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_real_in_atomicExpr789);
                    real88=real();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, real88.getTree());

                    }
                    break;
                case 4 :
                    // Lustre.g:166:3: BOOL
                    {
                    root_0 = (Object)adaptor.nil();


                    BOOL89=(Token)match(input,BOOL,FOLLOW_BOOL_in_atomicExpr793); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    BOOL89_tree = 
                    (Object)adaptor.create(BOOL89)
                    ;
                    adaptor.addChild(root_0, BOOL89_tree);
                    }

                    }
                    break;
                case 5 :
                    // Lustre.g:167:3: IF ^ expr 'then' ! expr 'else' ! expr
                    {
                    root_0 = (Object)adaptor.nil();


                    IF90=(Token)match(input,IF,FOLLOW_IF_in_atomicExpr797); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    IF90_tree = 
                    (Object)adaptor.create(IF90)
                    ;
                    root_0 = (Object)adaptor.becomeRoot(IF90_tree, root_0);
                    }

                    pushFollow(FOLLOW_expr_in_atomicExpr800);
                    expr91=expr();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, expr91.getTree());

                    string_literal92=(Token)match(input,59,FOLLOW_59_in_atomicExpr802); if (state.failed) return retval;

                    pushFollow(FOLLOW_expr_in_atomicExpr805);
                    expr93=expr();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, expr93.getTree());

                    string_literal94=(Token)match(input,49,FOLLOW_49_in_atomicExpr807); if (state.failed) return retval;

                    pushFollow(FOLLOW_expr_in_atomicExpr810);
                    expr95=expr();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, expr95.getTree());

                    }
                    break;
                case 6 :
                    // Lustre.g:168:3: '(' expr ')'
                    {
                    char_literal96=(Token)match(input,25,FOLLOW_25_in_atomicExpr814); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_25.add(char_literal96);


                    pushFollow(FOLLOW_expr_in_atomicExpr816);
                    expr97=expr();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_expr.add(expr97.getTree());

                    char_literal98=(Token)match(input,26,FOLLOW_26_in_atomicExpr818); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_26.add(char_literal98);


                    // AST REWRITE
                    // elements: expr
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {

                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 168:16: -> expr
                    {
                        adaptor.addChild(root_0, stream_expr.nextTree());

                    }


                    retval.tree = root_0;
                    }

                    }
                    break;

            }
            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "atomicExpr"


    public static class real_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "real"
    // Lustre.g:171:1: real : a= INT '.' b= INT ->;
    public final LustreParser.real_return real() throws RecognitionException {
        LustreParser.real_return retval = new LustreParser.real_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token a=null;
        Token b=null;
        Token char_literal99=null;

        Object a_tree=null;
        Object b_tree=null;
        Object char_literal99_tree=null;
        RewriteRuleTokenStream stream_INT=new RewriteRuleTokenStream(adaptor,"token INT");
        RewriteRuleTokenStream stream_ERROR=new RewriteRuleTokenStream(adaptor,"token ERROR");

        try {
            // Lustre.g:171:5: (a= INT '.' b= INT ->)
            // Lustre.g:171:7: a= INT '.' b= INT
            {
            a=(Token)match(input,INT,FOLLOW_INT_in_real832); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_INT.add(a);


            char_literal99=(Token)match(input,ERROR,FOLLOW_ERROR_in_real834); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_ERROR.add(char_literal99);


            b=(Token)match(input,INT,FOLLOW_INT_in_real838); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_INT.add(b);


            // AST REWRITE
            // elements: 
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {

            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 171:23: ->
            {
                adaptor.addChild(root_0, makeReal((a!=null?a.getText():null) + "." + (b!=null?b.getText():null)));

            }


            retval.tree = root_0;
            }

            }

            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "real"

    // $ANTLR start synpred1_Lustre
    public final void synpred1_Lustre_fragment() throws RecognitionException {
        // Lustre.g:104:16: ( arrowOp )
        // Lustre.g:104:17: arrowOp
        {
        pushFollow(FOLLOW_arrowOp_in_synpred1_Lustre489);
        arrowOp();

        state._fsp--;
        if (state.failed) return ;

        }

    }
    // $ANTLR end synpred1_Lustre

    // $ANTLR start synpred2_Lustre
    public final void synpred2_Lustre_fragment() throws RecognitionException {
        // Lustre.g:112:11: ( impliesOp )
        // Lustre.g:112:12: impliesOp
        {
        pushFollow(FOLLOW_impliesOp_in_synpred2_Lustre521);
        impliesOp();

        state._fsp--;
        if (state.failed) return ;

        }

    }
    // $ANTLR end synpred2_Lustre

    // $ANTLR start synpred3_Lustre
    public final void synpred3_Lustre_fragment() throws RecognitionException {
        // Lustre.g:120:12: ( orOp )
        // Lustre.g:120:13: orOp
        {
        pushFollow(FOLLOW_orOp_in_synpred3_Lustre557);
        orOp();

        state._fsp--;
        if (state.failed) return ;

        }

    }
    // $ANTLR end synpred3_Lustre

    // $ANTLR start synpred4_Lustre
    public final void synpred4_Lustre_fragment() throws RecognitionException {
        // Lustre.g:128:19: ( andOp )
        // Lustre.g:128:20: andOp
        {
        pushFollow(FOLLOW_andOp_in_synpred4_Lustre589);
        andOp();

        state._fsp--;
        if (state.failed) return ;

        }

    }
    // $ANTLR end synpred4_Lustre

    // $ANTLR start synpred5_Lustre
    public final void synpred5_Lustre_fragment() throws RecognitionException {
        // Lustre.g:136:13: ( relationalOp )
        // Lustre.g:136:14: relationalOp
        {
        pushFollow(FOLLOW_relationalOp_in_synpred5_Lustre641);
        relationalOp();

        state._fsp--;
        if (state.failed) return ;

        }

    }
    // $ANTLR end synpred5_Lustre

    // $ANTLR start synpred6_Lustre
    public final void synpred6_Lustre_fragment() throws RecognitionException {
        // Lustre.g:144:14: ( plusOp )
        // Lustre.g:144:15: plusOp
        {
        pushFollow(FOLLOW_plusOp_in_synpred6_Lustre677);
        plusOp();

        state._fsp--;
        if (state.failed) return ;

        }

    }
    // $ANTLR end synpred6_Lustre

    // $ANTLR start synpred7_Lustre
    public final void synpred7_Lustre_fragment() throws RecognitionException {
        // Lustre.g:152:15: ( timesOp )
        // Lustre.g:152:16: timesOp
        {
        pushFollow(FOLLOW_timesOp_in_synpred7_Lustre717);
        timesOp();

        state._fsp--;
        if (state.failed) return ;

        }

    }
    // $ANTLR end synpred7_Lustre

    // Delegated rules

    public final boolean synpred6_Lustre() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred6_Lustre_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred5_Lustre() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred5_Lustre_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred1_Lustre() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred1_Lustre_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred7_Lustre() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred7_Lustre_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred4_Lustre() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred4_Lustre_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred3_Lustre() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred3_Lustre_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred2_Lustre() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred2_Lustre_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }


 

    public static final BitSet FOLLOW_constant_in_node119 = new BitSet(new long[]{0x0010800000000000L});
    public static final BitSet FOLLOW_52_in_node124 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_ID_in_node126 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_25_in_node128 = new BitSet(new long[]{0x0000000004000100L});
    public static final BitSet FOLLOW_varDeclList_in_node132 = new BitSet(new long[]{0x0000000004000000L});
    public static final BitSet FOLLOW_26_in_node135 = new BitSet(new long[]{0x0100000000000000L});
    public static final BitSet FOLLOW_56_in_node139 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_25_in_node141 = new BitSet(new long[]{0x0000000004000100L});
    public static final BitSet FOLLOW_varDeclList_in_node145 = new BitSet(new long[]{0x0000000004000000L});
    public static final BitSet FOLLOW_26_in_node148 = new BitSet(new long[]{0x0000000800000000L});
    public static final BitSet FOLLOW_35_in_node150 = new BitSet(new long[]{0x1008000000000000L});
    public static final BitSet FOLLOW_60_in_node155 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_varDeclList_in_node159 = new BitSet(new long[]{0x0000000800000000L});
    public static final BitSet FOLLOW_35_in_node161 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_51_in_node167 = new BitSet(new long[]{0x0400000080000100L});
    public static final BitSet FOLLOW_equation_in_node174 = new BitSet(new long[]{0x0400000080000100L});
    public static final BitSet FOLLOW_property_in_node178 = new BitSet(new long[]{0x0400000080000100L});
    public static final BitSet FOLLOW_58_in_node184 = new BitSet(new long[]{0x0000000800000000L});
    public static final BitSet FOLLOW_35_in_node186 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_47_in_constant289 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_ID_in_constant291 = new BitSet(new long[]{0x0000008000000000L});
    public static final BitSet FOLLOW_39_in_constant293 = new BitSet(new long[]{0x0000000042141510L});
    public static final BitSet FOLLOW_expr_in_constant295 = new BitSet(new long[]{0x0000000800000000L});
    public static final BitSet FOLLOW_35_in_constant297 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_varDeclGroup_in_varDeclList315 = new BitSet(new long[]{0x0000000800000002L});
    public static final BitSet FOLLOW_35_in_varDeclList318 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_varDeclGroup_in_varDeclList320 = new BitSet(new long[]{0x0000000800000002L});
    public static final BitSet FOLLOW_ID_in_varDeclGroup337 = new BitSet(new long[]{0x0000000420000000L});
    public static final BitSet FOLLOW_29_in_varDeclGroup340 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_ID_in_varDeclGroup342 = new BitSet(new long[]{0x0000000420000000L});
    public static final BitSet FOLLOW_34_in_varDeclGroup346 = new BitSet(new long[]{0x0284400000000000L});
    public static final BitSet FOLLOW_type_in_varDeclGroup348 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_50_in_type367 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_57_in_type372 = new BitSet(new long[]{0x0000080000000000L});
    public static final BitSet FOLLOW_43_in_type374 = new BitSet(new long[]{0x0000000040001000L});
    public static final BitSet FOLLOW_bound_in_type376 = new BitSet(new long[]{0x0000000020000000L});
    public static final BitSet FOLLOW_29_in_type378 = new BitSet(new long[]{0x0000000040001000L});
    public static final BitSet FOLLOW_bound_in_type380 = new BitSet(new long[]{0x0000100000000000L});
    public static final BitSet FOLLOW_44_in_type382 = new BitSet(new long[]{0x0020000000000000L});
    public static final BitSet FOLLOW_53_in_type384 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_50_in_type386 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_46_in_type394 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_55_in_type399 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_30_in_bound410 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_INT_in_bound413 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_31_in_property423 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_ID_in_property425 = new BitSet(new long[]{0x0000000800000000L});
    public static final BitSet FOLLOW_35_in_property427 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_equation441 = new BitSet(new long[]{0x0000008000000000L});
    public static final BitSet FOLLOW_39_in_equation443 = new BitSet(new long[]{0x0000000042141510L});
    public static final BitSet FOLLOW_expr_in_equation445 = new BitSet(new long[]{0x0000000800000000L});
    public static final BitSet FOLLOW_35_in_equation447 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_arrowExpr_in_expr465 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_32_in_arrowOp475 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_impliesExpr_in_arrowExpr485 = new BitSet(new long[]{0x0000000100000002L});
    public static final BitSet FOLLOW_arrowOp_in_arrowExpr492 = new BitSet(new long[]{0x0000000042141510L});
    public static final BitSet FOLLOW_arrowExpr_in_arrowExpr495 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_40_in_impliesOp507 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_orExpr_in_impliesExpr517 = new BitSet(new long[]{0x0000010000000002L});
    public static final BitSet FOLLOW_impliesOp_in_impliesExpr524 = new BitSet(new long[]{0x0000000042141510L});
    public static final BitSet FOLLOW_impliesExpr_in_impliesExpr527 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_andExpr_in_orExpr553 = new BitSet(new long[]{0x2040000000000002L});
    public static final BitSet FOLLOW_orOp_in_orExpr560 = new BitSet(new long[]{0x0000000042141510L});
    public static final BitSet FOLLOW_andExpr_in_orExpr563 = new BitSet(new long[]{0x2040000000000002L});
    public static final BitSet FOLLOW_45_in_andOp575 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_relationalExpr_in_andExpr585 = new BitSet(new long[]{0x0000200000000002L});
    public static final BitSet FOLLOW_andOp_in_andExpr592 = new BitSet(new long[]{0x0000000042141510L});
    public static final BitSet FOLLOW_relationalExpr_in_andExpr595 = new BitSet(new long[]{0x0000200000000002L});
    public static final BitSet FOLLOW_plusExpr_in_relationalExpr637 = new BitSet(new long[]{0x000006F000000002L});
    public static final BitSet FOLLOW_relationalOp_in_relationalExpr644 = new BitSet(new long[]{0x0000000042141510L});
    public static final BitSet FOLLOW_plusExpr_in_relationalExpr647 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_timesExpr_in_plusExpr673 = new BitSet(new long[]{0x0000000050000002L});
    public static final BitSet FOLLOW_plusOp_in_plusExpr680 = new BitSet(new long[]{0x0000000042141510L});
    public static final BitSet FOLLOW_timesExpr_in_plusExpr683 = new BitSet(new long[]{0x0000000050000002L});
    public static final BitSet FOLLOW_prefixExpr_in_timesExpr713 = new BitSet(new long[]{0x0001000208000002L});
    public static final BitSet FOLLOW_timesOp_in_timesExpr720 = new BitSet(new long[]{0x0000000042141510L});
    public static final BitSet FOLLOW_prefixExpr_in_timesExpr723 = new BitSet(new long[]{0x0001000208000002L});
    public static final BitSet FOLLOW_30_in_prefixExpr735 = new BitSet(new long[]{0x0000000042141510L});
    public static final BitSet FOLLOW_prefixExpr_in_prefixExpr737 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NOT_in_prefixExpr749 = new BitSet(new long[]{0x0000000042141510L});
    public static final BitSet FOLLOW_prefixExpr_in_prefixExpr752 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_PRE_in_prefixExpr756 = new BitSet(new long[]{0x0000000042141510L});
    public static final BitSet FOLLOW_prefixExpr_in_prefixExpr759 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_atomicExpr_in_prefixExpr763 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_atomicExpr773 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INT_in_atomicExpr785 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_real_in_atomicExpr789 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_BOOL_in_atomicExpr793 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IF_in_atomicExpr797 = new BitSet(new long[]{0x0000000042141510L});
    public static final BitSet FOLLOW_expr_in_atomicExpr800 = new BitSet(new long[]{0x0800000000000000L});
    public static final BitSet FOLLOW_59_in_atomicExpr802 = new BitSet(new long[]{0x0000000042141510L});
    public static final BitSet FOLLOW_expr_in_atomicExpr805 = new BitSet(new long[]{0x0002000000000000L});
    public static final BitSet FOLLOW_49_in_atomicExpr807 = new BitSet(new long[]{0x0000000042141510L});
    public static final BitSet FOLLOW_expr_in_atomicExpr810 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_25_in_atomicExpr814 = new BitSet(new long[]{0x0000000042141510L});
    public static final BitSet FOLLOW_expr_in_atomicExpr816 = new BitSet(new long[]{0x0000000004000000L});
    public static final BitSet FOLLOW_26_in_atomicExpr818 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INT_in_real832 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_ERROR_in_real834 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_INT_in_real838 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_arrowOp_in_synpred1_Lustre489 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_impliesOp_in_synpred2_Lustre521 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_orOp_in_synpred3_Lustre557 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_andOp_in_synpred4_Lustre589 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_relationalOp_in_synpred5_Lustre641 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_plusOp_in_synpred6_Lustre677 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_timesOp_in_synpred7_Lustre717 = new BitSet(new long[]{0x0000000000000002L});

}