// $ANTLR 3.4 Lustre.g 2012-10-30 15:32:27

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


    public static class node_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "node"
    // Lustre.g:42:1: node : ( constant )* 'node' ID '(' (inputs= varDeclList )? ')' 'returns' '(' (outputs= varDeclList )? ')' ';' ( 'var' locals= varDeclList ';' )? 'let' ( equation | property )* 'tel' ';' -> ^( NODE ID ^( CONSTANTS ( constant )* ) ^( INPUTS ( $inputs)? ) ^( OUTPUTS ( $outputs)? ) ^( LOCALS ( $locals)? ) ^( EQUATIONS ( equation )* ) ^( PROPERTIES ( property )* ) ) ;
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
            // Lustre.g:42:5: ( ( constant )* 'node' ID '(' (inputs= varDeclList )? ')' 'returns' '(' (outputs= varDeclList )? ')' ';' ( 'var' locals= varDeclList ';' )? 'let' ( equation | property )* 'tel' ';' -> ^( NODE ID ^( CONSTANTS ( constant )* ) ^( INPUTS ( $inputs)? ) ^( OUTPUTS ( $outputs)? ) ^( LOCALS ( $locals)? ) ^( EQUATIONS ( equation )* ) ^( PROPERTIES ( property )* ) ) )
            // Lustre.g:43:3: ( constant )* 'node' ID '(' (inputs= varDeclList )? ')' 'returns' '(' (outputs= varDeclList )? ')' ';' ( 'var' locals= varDeclList ';' )? 'let' ( equation | property )* 'tel' ';'
            {
            // Lustre.g:43:3: ( constant )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0==47) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // Lustre.g:43:3: constant
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


            // Lustre.g:44:23: (inputs= varDeclList )?
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( (LA2_0==ID) ) {
                alt2=1;
            }
            switch (alt2) {
                case 1 :
                    // Lustre.g:44:23: inputs= varDeclList
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


            // Lustre.g:45:24: (outputs= varDeclList )?
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( (LA3_0==ID) ) {
                alt3=1;
            }
            switch (alt3) {
                case 1 :
                    // Lustre.g:45:24: outputs= varDeclList
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


            // Lustre.g:46:3: ( 'var' locals= varDeclList ';' )?
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0==60) ) {
                alt4=1;
            }
            switch (alt4) {
                case 1 :
                    // Lustre.g:46:4: 'var' locals= varDeclList ';'
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


            // Lustre.g:48:5: ( equation | property )*
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
            	    // Lustre.g:48:6: equation
            	    {
            	    pushFollow(FOLLOW_equation_in_node174);
            	    equation13=equation();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) stream_equation.add(equation13.getTree());

            	    }
            	    break;
            	case 2 :
            	    // Lustre.g:48:17: property
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
            // elements: locals, outputs, constant, property, inputs, ID, equation
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
            // 49:13: -> ^( NODE ID ^( CONSTANTS ( constant )* ) ^( INPUTS ( $inputs)? ) ^( OUTPUTS ( $outputs)? ) ^( LOCALS ( $locals)? ) ^( EQUATIONS ( equation )* ) ^( PROPERTIES ( property )* ) )
            {
                // Lustre.g:50:5: ^( NODE ID ^( CONSTANTS ( constant )* ) ^( INPUTS ( $inputs)? ) ^( OUTPUTS ( $outputs)? ) ^( LOCALS ( $locals)? ) ^( EQUATIONS ( equation )* ) ^( PROPERTIES ( property )* ) )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot(
                (Object)adaptor.create(NODE, "NODE")
                , root_1);

                adaptor.addChild(root_1, 
                stream_ID.nextNode()
                );

                // Lustre.g:51:7: ^( CONSTANTS ( constant )* )
                {
                Object root_2 = (Object)adaptor.nil();
                root_2 = (Object)adaptor.becomeRoot(
                (Object)adaptor.create(CONSTANTS, "CONSTANTS")
                , root_2);

                // Lustre.g:51:19: ( constant )*
                while ( stream_constant.hasNext() ) {
                    adaptor.addChild(root_2, stream_constant.nextTree());

                }
                stream_constant.reset();

                adaptor.addChild(root_1, root_2);
                }

                // Lustre.g:52:7: ^( INPUTS ( $inputs)? )
                {
                Object root_2 = (Object)adaptor.nil();
                root_2 = (Object)adaptor.becomeRoot(
                (Object)adaptor.create(INPUTS, "INPUTS")
                , root_2);

                // Lustre.g:52:17: ( $inputs)?
                if ( stream_inputs.hasNext() ) {
                    adaptor.addChild(root_2, stream_inputs.nextTree());

                }
                stream_inputs.reset();

                adaptor.addChild(root_1, root_2);
                }

                // Lustre.g:53:7: ^( OUTPUTS ( $outputs)? )
                {
                Object root_2 = (Object)adaptor.nil();
                root_2 = (Object)adaptor.becomeRoot(
                (Object)adaptor.create(OUTPUTS, "OUTPUTS")
                , root_2);

                // Lustre.g:53:18: ( $outputs)?
                if ( stream_outputs.hasNext() ) {
                    adaptor.addChild(root_2, stream_outputs.nextTree());

                }
                stream_outputs.reset();

                adaptor.addChild(root_1, root_2);
                }

                // Lustre.g:54:7: ^( LOCALS ( $locals)? )
                {
                Object root_2 = (Object)adaptor.nil();
                root_2 = (Object)adaptor.becomeRoot(
                (Object)adaptor.create(LOCALS, "LOCALS")
                , root_2);

                // Lustre.g:54:17: ( $locals)?
                if ( stream_locals.hasNext() ) {
                    adaptor.addChild(root_2, stream_locals.nextTree());

                }
                stream_locals.reset();

                adaptor.addChild(root_1, root_2);
                }

                // Lustre.g:55:7: ^( EQUATIONS ( equation )* )
                {
                Object root_2 = (Object)adaptor.nil();
                root_2 = (Object)adaptor.becomeRoot(
                (Object)adaptor.create(EQUATIONS, "EQUATIONS")
                , root_2);

                // Lustre.g:55:19: ( equation )*
                while ( stream_equation.hasNext() ) {
                    adaptor.addChild(root_2, stream_equation.nextTree());

                }
                stream_equation.reset();

                adaptor.addChild(root_1, root_2);
                }

                // Lustre.g:56:7: ^( PROPERTIES ( property )* )
                {
                Object root_2 = (Object)adaptor.nil();
                root_2 = (Object)adaptor.becomeRoot(
                (Object)adaptor.create(PROPERTIES, "PROPERTIES")
                , root_2);

                // Lustre.g:56:20: ( property )*
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
    // Lustre.g:59:1: constant : 'const' ID '=' expr ';' -> ^( ID expr ) ;
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
            // Lustre.g:59:9: ( 'const' ID '=' expr ';' -> ^( ID expr ) )
            // Lustre.g:60:3: 'const' ID '=' expr ';'
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
            // 60:27: -> ^( ID expr )
            {
                // Lustre.g:60:30: ^( ID expr )
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
    // Lustre.g:63:1: varDeclList : varDeclGroup ( ';' varDeclGroup )* -> ( varDeclGroup )+ ;
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
            // Lustre.g:63:12: ( varDeclGroup ( ';' varDeclGroup )* -> ( varDeclGroup )+ )
            // Lustre.g:64:3: varDeclGroup ( ';' varDeclGroup )*
            {
            pushFollow(FOLLOW_varDeclGroup_in_varDeclList315);
            varDeclGroup22=varDeclGroup();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_varDeclGroup.add(varDeclGroup22.getTree());

            // Lustre.g:64:16: ( ';' varDeclGroup )*
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
            	    // Lustre.g:64:17: ';' varDeclGroup
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
            // 64:36: -> ( varDeclGroup )+
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
    // Lustre.g:67:1: varDeclGroup : ID ( ',' ID )* ':' type -> ( ^( ID type ) )* ;
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
            // Lustre.g:67:13: ( ID ( ',' ID )* ':' type -> ( ^( ID type ) )* )
            // Lustre.g:68:3: ID ( ',' ID )* ':' type
            {
            ID25=(Token)match(input,ID,FOLLOW_ID_in_varDeclGroup337); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_ID.add(ID25);


            // Lustre.g:68:6: ( ',' ID )*
            loop7:
            do {
                int alt7=2;
                int LA7_0 = input.LA(1);

                if ( (LA7_0==29) ) {
                    alt7=1;
                }


                switch (alt7) {
            	case 1 :
            	    // Lustre.g:68:7: ',' ID
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
            // 68:25: -> ( ^( ID type ) )*
            {
                // Lustre.g:68:28: ( ^( ID type ) )*
                while ( stream_type.hasNext()||stream_ID.hasNext() ) {
                    // Lustre.g:68:28: ^( ID type )
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
    // Lustre.g:71:1: type : ( 'int' ^| 'subrange' '[' INT ',' INT ']' 'of' 'int' -> 'int' | 'bool' ^| 'real' ^);
    public final LustreParser.type_return type() throws RecognitionException {
        LustreParser.type_return retval = new LustreParser.type_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token string_literal30=null;
        Token string_literal31=null;
        Token char_literal32=null;
        Token INT33=null;
        Token char_literal34=null;
        Token INT35=null;
        Token char_literal36=null;
        Token string_literal37=null;
        Token string_literal38=null;
        Token string_literal39=null;
        Token string_literal40=null;

        Object string_literal30_tree=null;
        Object string_literal31_tree=null;
        Object char_literal32_tree=null;
        Object INT33_tree=null;
        Object char_literal34_tree=null;
        Object INT35_tree=null;
        Object char_literal36_tree=null;
        Object string_literal37_tree=null;
        Object string_literal38_tree=null;
        Object string_literal39_tree=null;
        Object string_literal40_tree=null;
        RewriteRuleTokenStream stream_43=new RewriteRuleTokenStream(adaptor,"token 43");
        RewriteRuleTokenStream stream_INT=new RewriteRuleTokenStream(adaptor,"token INT");
        RewriteRuleTokenStream stream_44=new RewriteRuleTokenStream(adaptor,"token 44");
        RewriteRuleTokenStream stream_57=new RewriteRuleTokenStream(adaptor,"token 57");
        RewriteRuleTokenStream stream_53=new RewriteRuleTokenStream(adaptor,"token 53");
        RewriteRuleTokenStream stream_29=new RewriteRuleTokenStream(adaptor,"token 29");
        RewriteRuleTokenStream stream_50=new RewriteRuleTokenStream(adaptor,"token 50");

        try {
            // Lustre.g:71:5: ( 'int' ^| 'subrange' '[' INT ',' INT ']' 'of' 'int' -> 'int' | 'bool' ^| 'real' ^)
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
                    // Lustre.g:72:3: 'int' ^
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
                    // Lustre.g:73:3: 'subrange' '[' INT ',' INT ']' 'of' 'int'
                    {
                    string_literal31=(Token)match(input,57,FOLLOW_57_in_type372); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_57.add(string_literal31);


                    char_literal32=(Token)match(input,43,FOLLOW_43_in_type374); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_43.add(char_literal32);


                    INT33=(Token)match(input,INT,FOLLOW_INT_in_type376); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_INT.add(INT33);


                    char_literal34=(Token)match(input,29,FOLLOW_29_in_type378); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_29.add(char_literal34);


                    INT35=(Token)match(input,INT,FOLLOW_INT_in_type380); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_INT.add(INT35);


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
                    // 73:45: -> 'int'
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
                    // Lustre.g:74:3: 'bool' ^
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
                    // Lustre.g:75:3: 'real' ^
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


    public static class property_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "property"
    // Lustre.g:78:1: property : '--%PROPERTY' ID ';' -> ID ;
    public final LustreParser.property_return property() throws RecognitionException {
        LustreParser.property_return retval = new LustreParser.property_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token string_literal41=null;
        Token ID42=null;
        Token char_literal43=null;

        Object string_literal41_tree=null;
        Object ID42_tree=null;
        Object char_literal43_tree=null;
        RewriteRuleTokenStream stream_31=new RewriteRuleTokenStream(adaptor,"token 31");
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleTokenStream stream_35=new RewriteRuleTokenStream(adaptor,"token 35");

        try {
            // Lustre.g:78:9: ( '--%PROPERTY' ID ';' -> ID )
            // Lustre.g:79:3: '--%PROPERTY' ID ';'
            {
            string_literal41=(Token)match(input,31,FOLLOW_31_in_property410); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_31.add(string_literal41);


            ID42=(Token)match(input,ID,FOLLOW_ID_in_property412); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_ID.add(ID42);


            char_literal43=(Token)match(input,35,FOLLOW_35_in_property414); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_35.add(char_literal43);


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
            // 79:24: -> ID
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
    // Lustre.g:82:1: equation : ID '=' expr ';' -> ^( ID expr ) ;
    public final LustreParser.equation_return equation() throws RecognitionException {
        LustreParser.equation_return retval = new LustreParser.equation_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token ID44=null;
        Token char_literal45=null;
        Token char_literal47=null;
        LustreParser.expr_return expr46 =null;


        Object ID44_tree=null;
        Object char_literal45_tree=null;
        Object char_literal47_tree=null;
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleTokenStream stream_35=new RewriteRuleTokenStream(adaptor,"token 35");
        RewriteRuleTokenStream stream_39=new RewriteRuleTokenStream(adaptor,"token 39");
        RewriteRuleSubtreeStream stream_expr=new RewriteRuleSubtreeStream(adaptor,"rule expr");
        try {
            // Lustre.g:82:9: ( ID '=' expr ';' -> ^( ID expr ) )
            // Lustre.g:83:3: ID '=' expr ';'
            {
            ID44=(Token)match(input,ID,FOLLOW_ID_in_equation428); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_ID.add(ID44);


            char_literal45=(Token)match(input,39,FOLLOW_39_in_equation430); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_39.add(char_literal45);


            pushFollow(FOLLOW_expr_in_equation432);
            expr46=expr();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_expr.add(expr46.getTree());

            char_literal47=(Token)match(input,35,FOLLOW_35_in_equation434); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_35.add(char_literal47);


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
            // 83:19: -> ^( ID expr )
            {
                // Lustre.g:83:22: ^( ID expr )
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
    // Lustre.g:86:1: expr : arrowExpr ;
    public final LustreParser.expr_return expr() throws RecognitionException {
        LustreParser.expr_return retval = new LustreParser.expr_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        LustreParser.arrowExpr_return arrowExpr48 =null;



        try {
            // Lustre.g:86:5: ( arrowExpr )
            // Lustre.g:87:3: arrowExpr
            {
            root_0 = (Object)adaptor.nil();


            pushFollow(FOLLOW_arrowExpr_in_expr452);
            arrowExpr48=arrowExpr();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, arrowExpr48.getTree());

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
    // Lustre.g:90:1: arrowOp : '->' ;
    public final LustreParser.arrowOp_return arrowOp() throws RecognitionException {
        LustreParser.arrowOp_return retval = new LustreParser.arrowOp_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token string_literal49=null;

        Object string_literal49_tree=null;

        try {
            // Lustre.g:90:8: ( '->' )
            // Lustre.g:91:3: '->'
            {
            root_0 = (Object)adaptor.nil();


            string_literal49=(Token)match(input,32,FOLLOW_32_in_arrowOp462); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            string_literal49_tree = 
            (Object)adaptor.create(string_literal49)
            ;
            adaptor.addChild(root_0, string_literal49_tree);
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
    // Lustre.g:94:1: arrowExpr : impliesExpr ( ( arrowOp )=> arrowOp ^ arrowExpr )? ;
    public final LustreParser.arrowExpr_return arrowExpr() throws RecognitionException {
        LustreParser.arrowExpr_return retval = new LustreParser.arrowExpr_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        LustreParser.impliesExpr_return impliesExpr50 =null;

        LustreParser.arrowOp_return arrowOp51 =null;

        LustreParser.arrowExpr_return arrowExpr52 =null;



        try {
            // Lustre.g:94:10: ( impliesExpr ( ( arrowOp )=> arrowOp ^ arrowExpr )? )
            // Lustre.g:95:3: impliesExpr ( ( arrowOp )=> arrowOp ^ arrowExpr )?
            {
            root_0 = (Object)adaptor.nil();


            pushFollow(FOLLOW_impliesExpr_in_arrowExpr472);
            impliesExpr50=impliesExpr();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, impliesExpr50.getTree());

            // Lustre.g:95:15: ( ( arrowOp )=> arrowOp ^ arrowExpr )?
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( (LA9_0==32) ) {
                int LA9_1 = input.LA(2);

                if ( (synpred1_Lustre()) ) {
                    alt9=1;
                }
            }
            switch (alt9) {
                case 1 :
                    // Lustre.g:95:16: ( arrowOp )=> arrowOp ^ arrowExpr
                    {
                    pushFollow(FOLLOW_arrowOp_in_arrowExpr479);
                    arrowOp51=arrowOp();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) root_0 = (Object)adaptor.becomeRoot(arrowOp51.getTree(), root_0);

                    pushFollow(FOLLOW_arrowExpr_in_arrowExpr482);
                    arrowExpr52=arrowExpr();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, arrowExpr52.getTree());

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
    // Lustre.g:98:1: impliesOp : '=>' ;
    public final LustreParser.impliesOp_return impliesOp() throws RecognitionException {
        LustreParser.impliesOp_return retval = new LustreParser.impliesOp_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token string_literal53=null;

        Object string_literal53_tree=null;

        try {
            // Lustre.g:98:10: ( '=>' )
            // Lustre.g:99:3: '=>'
            {
            root_0 = (Object)adaptor.nil();


            string_literal53=(Token)match(input,40,FOLLOW_40_in_impliesOp494); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            string_literal53_tree = 
            (Object)adaptor.create(string_literal53)
            ;
            adaptor.addChild(root_0, string_literal53_tree);
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
    // Lustre.g:102:1: impliesExpr : orExpr ( ( impliesOp )=> impliesOp ^ impliesExpr )? ;
    public final LustreParser.impliesExpr_return impliesExpr() throws RecognitionException {
        LustreParser.impliesExpr_return retval = new LustreParser.impliesExpr_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        LustreParser.orExpr_return orExpr54 =null;

        LustreParser.impliesOp_return impliesOp55 =null;

        LustreParser.impliesExpr_return impliesExpr56 =null;



        try {
            // Lustre.g:102:12: ( orExpr ( ( impliesOp )=> impliesOp ^ impliesExpr )? )
            // Lustre.g:103:3: orExpr ( ( impliesOp )=> impliesOp ^ impliesExpr )?
            {
            root_0 = (Object)adaptor.nil();


            pushFollow(FOLLOW_orExpr_in_impliesExpr504);
            orExpr54=orExpr();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, orExpr54.getTree());

            // Lustre.g:103:10: ( ( impliesOp )=> impliesOp ^ impliesExpr )?
            int alt10=2;
            int LA10_0 = input.LA(1);

            if ( (LA10_0==40) ) {
                int LA10_1 = input.LA(2);

                if ( (synpred2_Lustre()) ) {
                    alt10=1;
                }
            }
            switch (alt10) {
                case 1 :
                    // Lustre.g:103:11: ( impliesOp )=> impliesOp ^ impliesExpr
                    {
                    pushFollow(FOLLOW_impliesOp_in_impliesExpr511);
                    impliesOp55=impliesOp();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) root_0 = (Object)adaptor.becomeRoot(impliesOp55.getTree(), root_0);

                    pushFollow(FOLLOW_impliesExpr_in_impliesExpr514);
                    impliesExpr56=impliesExpr();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, impliesExpr56.getTree());

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
    // Lustre.g:106:1: orOp : ( 'or' | 'xor' );
    public final LustreParser.orOp_return orOp() throws RecognitionException {
        LustreParser.orOp_return retval = new LustreParser.orOp_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token set57=null;

        Object set57_tree=null;

        try {
            // Lustre.g:106:5: ( 'or' | 'xor' )
            // Lustre.g:
            {
            root_0 = (Object)adaptor.nil();


            set57=(Token)input.LT(1);

            if ( input.LA(1)==54||input.LA(1)==61 ) {
                input.consume();
                if ( state.backtracking==0 ) adaptor.addChild(root_0, 
                (Object)adaptor.create(set57)
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
    // Lustre.g:110:1: orExpr : andExpr ( ( orOp )=> orOp ^ andExpr )* ;
    public final LustreParser.orExpr_return orExpr() throws RecognitionException {
        LustreParser.orExpr_return retval = new LustreParser.orExpr_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        LustreParser.andExpr_return andExpr58 =null;

        LustreParser.orOp_return orOp59 =null;

        LustreParser.andExpr_return andExpr60 =null;



        try {
            // Lustre.g:110:7: ( andExpr ( ( orOp )=> orOp ^ andExpr )* )
            // Lustre.g:111:3: andExpr ( ( orOp )=> orOp ^ andExpr )*
            {
            root_0 = (Object)adaptor.nil();


            pushFollow(FOLLOW_andExpr_in_orExpr540);
            andExpr58=andExpr();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, andExpr58.getTree());

            // Lustre.g:111:11: ( ( orOp )=> orOp ^ andExpr )*
            loop11:
            do {
                int alt11=2;
                int LA11_0 = input.LA(1);

                if ( (LA11_0==54||LA11_0==61) ) {
                    int LA11_2 = input.LA(2);

                    if ( (synpred3_Lustre()) ) {
                        alt11=1;
                    }


                }


                switch (alt11) {
            	case 1 :
            	    // Lustre.g:111:12: ( orOp )=> orOp ^ andExpr
            	    {
            	    pushFollow(FOLLOW_orOp_in_orExpr547);
            	    orOp59=orOp();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) root_0 = (Object)adaptor.becomeRoot(orOp59.getTree(), root_0);

            	    pushFollow(FOLLOW_andExpr_in_orExpr550);
            	    andExpr60=andExpr();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, andExpr60.getTree());

            	    }
            	    break;

            	default :
            	    break loop11;
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
    // Lustre.g:114:1: andOp : 'and' ;
    public final LustreParser.andOp_return andOp() throws RecognitionException {
        LustreParser.andOp_return retval = new LustreParser.andOp_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token string_literal61=null;

        Object string_literal61_tree=null;

        try {
            // Lustre.g:114:6: ( 'and' )
            // Lustre.g:115:3: 'and'
            {
            root_0 = (Object)adaptor.nil();


            string_literal61=(Token)match(input,45,FOLLOW_45_in_andOp562); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            string_literal61_tree = 
            (Object)adaptor.create(string_literal61)
            ;
            adaptor.addChild(root_0, string_literal61_tree);
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
    // Lustre.g:118:1: andExpr : relationalExpr ( ( andOp )=> andOp ^ relationalExpr )* ;
    public final LustreParser.andExpr_return andExpr() throws RecognitionException {
        LustreParser.andExpr_return retval = new LustreParser.andExpr_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        LustreParser.relationalExpr_return relationalExpr62 =null;

        LustreParser.andOp_return andOp63 =null;

        LustreParser.relationalExpr_return relationalExpr64 =null;



        try {
            // Lustre.g:118:8: ( relationalExpr ( ( andOp )=> andOp ^ relationalExpr )* )
            // Lustre.g:119:3: relationalExpr ( ( andOp )=> andOp ^ relationalExpr )*
            {
            root_0 = (Object)adaptor.nil();


            pushFollow(FOLLOW_relationalExpr_in_andExpr572);
            relationalExpr62=relationalExpr();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, relationalExpr62.getTree());

            // Lustre.g:119:18: ( ( andOp )=> andOp ^ relationalExpr )*
            loop12:
            do {
                int alt12=2;
                int LA12_0 = input.LA(1);

                if ( (LA12_0==45) ) {
                    int LA12_2 = input.LA(2);

                    if ( (synpred4_Lustre()) ) {
                        alt12=1;
                    }


                }


                switch (alt12) {
            	case 1 :
            	    // Lustre.g:119:19: ( andOp )=> andOp ^ relationalExpr
            	    {
            	    pushFollow(FOLLOW_andOp_in_andExpr579);
            	    andOp63=andOp();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) root_0 = (Object)adaptor.becomeRoot(andOp63.getTree(), root_0);

            	    pushFollow(FOLLOW_relationalExpr_in_andExpr582);
            	    relationalExpr64=relationalExpr();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, relationalExpr64.getTree());

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
    // $ANTLR end "andExpr"


    public static class relationalOp_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "relationalOp"
    // Lustre.g:122:1: relationalOp : ( '<' | '<=' | '>' | '>=' | '=' | '<>' );
    public final LustreParser.relationalOp_return relationalOp() throws RecognitionException {
        LustreParser.relationalOp_return retval = new LustreParser.relationalOp_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token set65=null;

        Object set65_tree=null;

        try {
            // Lustre.g:122:13: ( '<' | '<=' | '>' | '>=' | '=' | '<>' )
            // Lustre.g:
            {
            root_0 = (Object)adaptor.nil();


            set65=(Token)input.LT(1);

            if ( (input.LA(1) >= 36 && input.LA(1) <= 39)||(input.LA(1) >= 41 && input.LA(1) <= 42) ) {
                input.consume();
                if ( state.backtracking==0 ) adaptor.addChild(root_0, 
                (Object)adaptor.create(set65)
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
    // Lustre.g:126:1: relationalExpr : plusExpr ( ( relationalOp )=> relationalOp ^ plusExpr )? ;
    public final LustreParser.relationalExpr_return relationalExpr() throws RecognitionException {
        LustreParser.relationalExpr_return retval = new LustreParser.relationalExpr_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        LustreParser.plusExpr_return plusExpr66 =null;

        LustreParser.relationalOp_return relationalOp67 =null;

        LustreParser.plusExpr_return plusExpr68 =null;



        try {
            // Lustre.g:126:15: ( plusExpr ( ( relationalOp )=> relationalOp ^ plusExpr )? )
            // Lustre.g:127:3: plusExpr ( ( relationalOp )=> relationalOp ^ plusExpr )?
            {
            root_0 = (Object)adaptor.nil();


            pushFollow(FOLLOW_plusExpr_in_relationalExpr624);
            plusExpr66=plusExpr();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, plusExpr66.getTree());

            // Lustre.g:127:12: ( ( relationalOp )=> relationalOp ^ plusExpr )?
            int alt13=2;
            int LA13_0 = input.LA(1);

            if ( ((LA13_0 >= 36 && LA13_0 <= 39)||(LA13_0 >= 41 && LA13_0 <= 42)) ) {
                int LA13_1 = input.LA(2);

                if ( (synpred5_Lustre()) ) {
                    alt13=1;
                }
            }
            switch (alt13) {
                case 1 :
                    // Lustre.g:127:13: ( relationalOp )=> relationalOp ^ plusExpr
                    {
                    pushFollow(FOLLOW_relationalOp_in_relationalExpr631);
                    relationalOp67=relationalOp();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) root_0 = (Object)adaptor.becomeRoot(relationalOp67.getTree(), root_0);

                    pushFollow(FOLLOW_plusExpr_in_relationalExpr634);
                    plusExpr68=plusExpr();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, plusExpr68.getTree());

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
    // Lustre.g:130:1: plusOp : ( '+' | '-' );
    public final LustreParser.plusOp_return plusOp() throws RecognitionException {
        LustreParser.plusOp_return retval = new LustreParser.plusOp_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token set69=null;

        Object set69_tree=null;

        try {
            // Lustre.g:130:7: ( '+' | '-' )
            // Lustre.g:
            {
            root_0 = (Object)adaptor.nil();


            set69=(Token)input.LT(1);

            if ( input.LA(1)==28||input.LA(1)==30 ) {
                input.consume();
                if ( state.backtracking==0 ) adaptor.addChild(root_0, 
                (Object)adaptor.create(set69)
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
    // Lustre.g:134:1: plusExpr : timesExpr ( ( plusOp )=> plusOp ^ timesExpr )* ;
    public final LustreParser.plusExpr_return plusExpr() throws RecognitionException {
        LustreParser.plusExpr_return retval = new LustreParser.plusExpr_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        LustreParser.timesExpr_return timesExpr70 =null;

        LustreParser.plusOp_return plusOp71 =null;

        LustreParser.timesExpr_return timesExpr72 =null;



        try {
            // Lustre.g:134:9: ( timesExpr ( ( plusOp )=> plusOp ^ timesExpr )* )
            // Lustre.g:135:3: timesExpr ( ( plusOp )=> plusOp ^ timesExpr )*
            {
            root_0 = (Object)adaptor.nil();


            pushFollow(FOLLOW_timesExpr_in_plusExpr660);
            timesExpr70=timesExpr();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, timesExpr70.getTree());

            // Lustre.g:135:13: ( ( plusOp )=> plusOp ^ timesExpr )*
            loop14:
            do {
                int alt14=2;
                int LA14_0 = input.LA(1);

                if ( (LA14_0==28||LA14_0==30) ) {
                    int LA14_2 = input.LA(2);

                    if ( (synpred6_Lustre()) ) {
                        alt14=1;
                    }


                }


                switch (alt14) {
            	case 1 :
            	    // Lustre.g:135:14: ( plusOp )=> plusOp ^ timesExpr
            	    {
            	    pushFollow(FOLLOW_plusOp_in_plusExpr667);
            	    plusOp71=plusOp();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) root_0 = (Object)adaptor.becomeRoot(plusOp71.getTree(), root_0);

            	    pushFollow(FOLLOW_timesExpr_in_plusExpr670);
            	    timesExpr72=timesExpr();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, timesExpr72.getTree());

            	    }
            	    break;

            	default :
            	    break loop14;
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
    // Lustre.g:138:1: timesOp : ( '*' | '/' | 'div' );
    public final LustreParser.timesOp_return timesOp() throws RecognitionException {
        LustreParser.timesOp_return retval = new LustreParser.timesOp_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token set73=null;

        Object set73_tree=null;

        try {
            // Lustre.g:138:8: ( '*' | '/' | 'div' )
            // Lustre.g:
            {
            root_0 = (Object)adaptor.nil();


            set73=(Token)input.LT(1);

            if ( input.LA(1)==27||input.LA(1)==33||input.LA(1)==48 ) {
                input.consume();
                if ( state.backtracking==0 ) adaptor.addChild(root_0, 
                (Object)adaptor.create(set73)
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
    // Lustre.g:142:1: timesExpr : prefixExpr ( ( timesOp )=> timesOp ^ prefixExpr )* ;
    public final LustreParser.timesExpr_return timesExpr() throws RecognitionException {
        LustreParser.timesExpr_return retval = new LustreParser.timesExpr_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        LustreParser.prefixExpr_return prefixExpr74 =null;

        LustreParser.timesOp_return timesOp75 =null;

        LustreParser.prefixExpr_return prefixExpr76 =null;



        try {
            // Lustre.g:142:10: ( prefixExpr ( ( timesOp )=> timesOp ^ prefixExpr )* )
            // Lustre.g:143:3: prefixExpr ( ( timesOp )=> timesOp ^ prefixExpr )*
            {
            root_0 = (Object)adaptor.nil();


            pushFollow(FOLLOW_prefixExpr_in_timesExpr700);
            prefixExpr74=prefixExpr();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, prefixExpr74.getTree());

            // Lustre.g:143:14: ( ( timesOp )=> timesOp ^ prefixExpr )*
            loop15:
            do {
                int alt15=2;
                int LA15_0 = input.LA(1);

                if ( (LA15_0==27||LA15_0==33||LA15_0==48) ) {
                    int LA15_2 = input.LA(2);

                    if ( (synpred7_Lustre()) ) {
                        alt15=1;
                    }


                }


                switch (alt15) {
            	case 1 :
            	    // Lustre.g:143:15: ( timesOp )=> timesOp ^ prefixExpr
            	    {
            	    pushFollow(FOLLOW_timesOp_in_timesExpr707);
            	    timesOp75=timesOp();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) root_0 = (Object)adaptor.becomeRoot(timesOp75.getTree(), root_0);

            	    pushFollow(FOLLOW_prefixExpr_in_timesExpr710);
            	    prefixExpr76=prefixExpr();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, prefixExpr76.getTree());

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
    // $ANTLR end "timesExpr"


    public static class prefixExpr_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "prefixExpr"
    // Lustre.g:146:1: prefixExpr : ( '-' prefixExpr -> ^( NEGATE prefixExpr ) | NOT ^ prefixExpr | PRE ^ prefixExpr | atomicExpr );
    public final LustreParser.prefixExpr_return prefixExpr() throws RecognitionException {
        LustreParser.prefixExpr_return retval = new LustreParser.prefixExpr_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token char_literal77=null;
        Token NOT79=null;
        Token PRE81=null;
        LustreParser.prefixExpr_return prefixExpr78 =null;

        LustreParser.prefixExpr_return prefixExpr80 =null;

        LustreParser.prefixExpr_return prefixExpr82 =null;

        LustreParser.atomicExpr_return atomicExpr83 =null;


        Object char_literal77_tree=null;
        Object NOT79_tree=null;
        Object PRE81_tree=null;
        RewriteRuleTokenStream stream_30=new RewriteRuleTokenStream(adaptor,"token 30");
        RewriteRuleSubtreeStream stream_prefixExpr=new RewriteRuleSubtreeStream(adaptor,"rule prefixExpr");
        try {
            // Lustre.g:146:11: ( '-' prefixExpr -> ^( NEGATE prefixExpr ) | NOT ^ prefixExpr | PRE ^ prefixExpr | atomicExpr )
            int alt16=4;
            switch ( input.LA(1) ) {
            case 30:
                {
                alt16=1;
                }
                break;
            case NOT:
                {
                alt16=2;
                }
                break;
            case PRE:
                {
                alt16=3;
                }
                break;
            case BOOL:
            case ID:
            case IF:
            case INT:
            case 25:
                {
                alt16=4;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 16, 0, input);

                throw nvae;

            }

            switch (alt16) {
                case 1 :
                    // Lustre.g:147:3: '-' prefixExpr
                    {
                    char_literal77=(Token)match(input,30,FOLLOW_30_in_prefixExpr722); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_30.add(char_literal77);


                    pushFollow(FOLLOW_prefixExpr_in_prefixExpr724);
                    prefixExpr78=prefixExpr();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_prefixExpr.add(prefixExpr78.getTree());

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
                    // 147:18: -> ^( NEGATE prefixExpr )
                    {
                        // Lustre.g:147:21: ^( NEGATE prefixExpr )
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
                    // Lustre.g:148:3: NOT ^ prefixExpr
                    {
                    root_0 = (Object)adaptor.nil();


                    NOT79=(Token)match(input,NOT,FOLLOW_NOT_in_prefixExpr736); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    NOT79_tree = 
                    (Object)adaptor.create(NOT79)
                    ;
                    root_0 = (Object)adaptor.becomeRoot(NOT79_tree, root_0);
                    }

                    pushFollow(FOLLOW_prefixExpr_in_prefixExpr739);
                    prefixExpr80=prefixExpr();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, prefixExpr80.getTree());

                    }
                    break;
                case 3 :
                    // Lustre.g:149:3: PRE ^ prefixExpr
                    {
                    root_0 = (Object)adaptor.nil();


                    PRE81=(Token)match(input,PRE,FOLLOW_PRE_in_prefixExpr743); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    PRE81_tree = 
                    (Object)adaptor.create(PRE81)
                    ;
                    root_0 = (Object)adaptor.becomeRoot(PRE81_tree, root_0);
                    }

                    pushFollow(FOLLOW_prefixExpr_in_prefixExpr746);
                    prefixExpr82=prefixExpr();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, prefixExpr82.getTree());

                    }
                    break;
                case 4 :
                    // Lustre.g:150:3: atomicExpr
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_atomicExpr_in_prefixExpr750);
                    atomicExpr83=atomicExpr();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, atomicExpr83.getTree());

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
    // Lustre.g:153:1: atomicExpr : ( ID -> ^( IDENT ID ) | INT | real | BOOL | IF ^ expr 'then' ! expr 'else' ! expr | '(' expr ')' -> expr );
    public final LustreParser.atomicExpr_return atomicExpr() throws RecognitionException {
        LustreParser.atomicExpr_return retval = new LustreParser.atomicExpr_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token ID84=null;
        Token INT85=null;
        Token BOOL87=null;
        Token IF88=null;
        Token string_literal90=null;
        Token string_literal92=null;
        Token char_literal94=null;
        Token char_literal96=null;
        LustreParser.real_return real86 =null;

        LustreParser.expr_return expr89 =null;

        LustreParser.expr_return expr91 =null;

        LustreParser.expr_return expr93 =null;

        LustreParser.expr_return expr95 =null;


        Object ID84_tree=null;
        Object INT85_tree=null;
        Object BOOL87_tree=null;
        Object IF88_tree=null;
        Object string_literal90_tree=null;
        Object string_literal92_tree=null;
        Object char_literal94_tree=null;
        Object char_literal96_tree=null;
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleTokenStream stream_25=new RewriteRuleTokenStream(adaptor,"token 25");
        RewriteRuleTokenStream stream_26=new RewriteRuleTokenStream(adaptor,"token 26");
        RewriteRuleSubtreeStream stream_expr=new RewriteRuleSubtreeStream(adaptor,"rule expr");
        try {
            // Lustre.g:153:11: ( ID -> ^( IDENT ID ) | INT | real | BOOL | IF ^ expr 'then' ! expr 'else' ! expr | '(' expr ')' -> expr )
            int alt17=6;
            switch ( input.LA(1) ) {
            case ID:
                {
                alt17=1;
                }
                break;
            case INT:
                {
                int LA17_2 = input.LA(2);

                if ( (LA17_2==ERROR) ) {
                    alt17=3;
                }
                else if ( ((LA17_2 >= 26 && LA17_2 <= 28)||LA17_2==30||(LA17_2 >= 32 && LA17_2 <= 33)||(LA17_2 >= 35 && LA17_2 <= 42)||LA17_2==45||(LA17_2 >= 48 && LA17_2 <= 49)||LA17_2==54||LA17_2==59||LA17_2==61) ) {
                    alt17=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 17, 2, input);

                    throw nvae;

                }
                }
                break;
            case BOOL:
                {
                alt17=4;
                }
                break;
            case IF:
                {
                alt17=5;
                }
                break;
            case 25:
                {
                alt17=6;
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
                    // Lustre.g:154:3: ID
                    {
                    ID84=(Token)match(input,ID,FOLLOW_ID_in_atomicExpr760); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_ID.add(ID84);


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
                    // 154:6: -> ^( IDENT ID )
                    {
                        // Lustre.g:154:9: ^( IDENT ID )
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
                    // Lustre.g:155:3: INT
                    {
                    root_0 = (Object)adaptor.nil();


                    INT85=(Token)match(input,INT,FOLLOW_INT_in_atomicExpr772); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    INT85_tree = 
                    (Object)adaptor.create(INT85)
                    ;
                    adaptor.addChild(root_0, INT85_tree);
                    }

                    }
                    break;
                case 3 :
                    // Lustre.g:156:3: real
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_real_in_atomicExpr776);
                    real86=real();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, real86.getTree());

                    }
                    break;
                case 4 :
                    // Lustre.g:157:3: BOOL
                    {
                    root_0 = (Object)adaptor.nil();


                    BOOL87=(Token)match(input,BOOL,FOLLOW_BOOL_in_atomicExpr780); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    BOOL87_tree = 
                    (Object)adaptor.create(BOOL87)
                    ;
                    adaptor.addChild(root_0, BOOL87_tree);
                    }

                    }
                    break;
                case 5 :
                    // Lustre.g:158:3: IF ^ expr 'then' ! expr 'else' ! expr
                    {
                    root_0 = (Object)adaptor.nil();


                    IF88=(Token)match(input,IF,FOLLOW_IF_in_atomicExpr784); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    IF88_tree = 
                    (Object)adaptor.create(IF88)
                    ;
                    root_0 = (Object)adaptor.becomeRoot(IF88_tree, root_0);
                    }

                    pushFollow(FOLLOW_expr_in_atomicExpr787);
                    expr89=expr();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, expr89.getTree());

                    string_literal90=(Token)match(input,59,FOLLOW_59_in_atomicExpr789); if (state.failed) return retval;

                    pushFollow(FOLLOW_expr_in_atomicExpr792);
                    expr91=expr();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, expr91.getTree());

                    string_literal92=(Token)match(input,49,FOLLOW_49_in_atomicExpr794); if (state.failed) return retval;

                    pushFollow(FOLLOW_expr_in_atomicExpr797);
                    expr93=expr();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, expr93.getTree());

                    }
                    break;
                case 6 :
                    // Lustre.g:159:3: '(' expr ')'
                    {
                    char_literal94=(Token)match(input,25,FOLLOW_25_in_atomicExpr801); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_25.add(char_literal94);


                    pushFollow(FOLLOW_expr_in_atomicExpr803);
                    expr95=expr();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_expr.add(expr95.getTree());

                    char_literal96=(Token)match(input,26,FOLLOW_26_in_atomicExpr805); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_26.add(char_literal96);


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
                    // 159:16: -> expr
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
    // Lustre.g:162:1: real : a= INT '.' b= INT ->;
    public final LustreParser.real_return real() throws RecognitionException {
        LustreParser.real_return retval = new LustreParser.real_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token a=null;
        Token b=null;
        Token char_literal97=null;

        Object a_tree=null;
        Object b_tree=null;
        Object char_literal97_tree=null;
        RewriteRuleTokenStream stream_INT=new RewriteRuleTokenStream(adaptor,"token INT");
        RewriteRuleTokenStream stream_ERROR=new RewriteRuleTokenStream(adaptor,"token ERROR");

        try {
            // Lustre.g:162:5: (a= INT '.' b= INT ->)
            // Lustre.g:162:7: a= INT '.' b= INT
            {
            a=(Token)match(input,INT,FOLLOW_INT_in_real819); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_INT.add(a);


            char_literal97=(Token)match(input,ERROR,FOLLOW_ERROR_in_real821); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_ERROR.add(char_literal97);


            b=(Token)match(input,INT,FOLLOW_INT_in_real825); if (state.failed) return retval; 
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
            // 162:23: ->
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
        // Lustre.g:95:16: ( arrowOp )
        // Lustre.g:95:17: arrowOp
        {
        pushFollow(FOLLOW_arrowOp_in_synpred1_Lustre476);
        arrowOp();

        state._fsp--;
        if (state.failed) return ;

        }

    }
    // $ANTLR end synpred1_Lustre

    // $ANTLR start synpred2_Lustre
    public final void synpred2_Lustre_fragment() throws RecognitionException {
        // Lustre.g:103:11: ( impliesOp )
        // Lustre.g:103:12: impliesOp
        {
        pushFollow(FOLLOW_impliesOp_in_synpred2_Lustre508);
        impliesOp();

        state._fsp--;
        if (state.failed) return ;

        }

    }
    // $ANTLR end synpred2_Lustre

    // $ANTLR start synpred3_Lustre
    public final void synpred3_Lustre_fragment() throws RecognitionException {
        // Lustre.g:111:12: ( orOp )
        // Lustre.g:111:13: orOp
        {
        pushFollow(FOLLOW_orOp_in_synpred3_Lustre544);
        orOp();

        state._fsp--;
        if (state.failed) return ;

        }

    }
    // $ANTLR end synpred3_Lustre

    // $ANTLR start synpred4_Lustre
    public final void synpred4_Lustre_fragment() throws RecognitionException {
        // Lustre.g:119:19: ( andOp )
        // Lustre.g:119:20: andOp
        {
        pushFollow(FOLLOW_andOp_in_synpred4_Lustre576);
        andOp();

        state._fsp--;
        if (state.failed) return ;

        }

    }
    // $ANTLR end synpred4_Lustre

    // $ANTLR start synpred5_Lustre
    public final void synpred5_Lustre_fragment() throws RecognitionException {
        // Lustre.g:127:13: ( relationalOp )
        // Lustre.g:127:14: relationalOp
        {
        pushFollow(FOLLOW_relationalOp_in_synpred5_Lustre628);
        relationalOp();

        state._fsp--;
        if (state.failed) return ;

        }

    }
    // $ANTLR end synpred5_Lustre

    // $ANTLR start synpred6_Lustre
    public final void synpred6_Lustre_fragment() throws RecognitionException {
        // Lustre.g:135:14: ( plusOp )
        // Lustre.g:135:15: plusOp
        {
        pushFollow(FOLLOW_plusOp_in_synpred6_Lustre664);
        plusOp();

        state._fsp--;
        if (state.failed) return ;

        }

    }
    // $ANTLR end synpred6_Lustre

    // $ANTLR start synpred7_Lustre
    public final void synpred7_Lustre_fragment() throws RecognitionException {
        // Lustre.g:143:15: ( timesOp )
        // Lustre.g:143:16: timesOp
        {
        pushFollow(FOLLOW_timesOp_in_synpred7_Lustre704);
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
    public static final BitSet FOLLOW_43_in_type374 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_INT_in_type376 = new BitSet(new long[]{0x0000000020000000L});
    public static final BitSet FOLLOW_29_in_type378 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_INT_in_type380 = new BitSet(new long[]{0x0000100000000000L});
    public static final BitSet FOLLOW_44_in_type382 = new BitSet(new long[]{0x0020000000000000L});
    public static final BitSet FOLLOW_53_in_type384 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_50_in_type386 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_46_in_type394 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_55_in_type399 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_31_in_property410 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_ID_in_property412 = new BitSet(new long[]{0x0000000800000000L});
    public static final BitSet FOLLOW_35_in_property414 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_equation428 = new BitSet(new long[]{0x0000008000000000L});
    public static final BitSet FOLLOW_39_in_equation430 = new BitSet(new long[]{0x0000000042141510L});
    public static final BitSet FOLLOW_expr_in_equation432 = new BitSet(new long[]{0x0000000800000000L});
    public static final BitSet FOLLOW_35_in_equation434 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_arrowExpr_in_expr452 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_32_in_arrowOp462 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_impliesExpr_in_arrowExpr472 = new BitSet(new long[]{0x0000000100000002L});
    public static final BitSet FOLLOW_arrowOp_in_arrowExpr479 = new BitSet(new long[]{0x0000000042141510L});
    public static final BitSet FOLLOW_arrowExpr_in_arrowExpr482 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_40_in_impliesOp494 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_orExpr_in_impliesExpr504 = new BitSet(new long[]{0x0000010000000002L});
    public static final BitSet FOLLOW_impliesOp_in_impliesExpr511 = new BitSet(new long[]{0x0000000042141510L});
    public static final BitSet FOLLOW_impliesExpr_in_impliesExpr514 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_andExpr_in_orExpr540 = new BitSet(new long[]{0x2040000000000002L});
    public static final BitSet FOLLOW_orOp_in_orExpr547 = new BitSet(new long[]{0x0000000042141510L});
    public static final BitSet FOLLOW_andExpr_in_orExpr550 = new BitSet(new long[]{0x2040000000000002L});
    public static final BitSet FOLLOW_45_in_andOp562 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_relationalExpr_in_andExpr572 = new BitSet(new long[]{0x0000200000000002L});
    public static final BitSet FOLLOW_andOp_in_andExpr579 = new BitSet(new long[]{0x0000000042141510L});
    public static final BitSet FOLLOW_relationalExpr_in_andExpr582 = new BitSet(new long[]{0x0000200000000002L});
    public static final BitSet FOLLOW_plusExpr_in_relationalExpr624 = new BitSet(new long[]{0x000006F000000002L});
    public static final BitSet FOLLOW_relationalOp_in_relationalExpr631 = new BitSet(new long[]{0x0000000042141510L});
    public static final BitSet FOLLOW_plusExpr_in_relationalExpr634 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_timesExpr_in_plusExpr660 = new BitSet(new long[]{0x0000000050000002L});
    public static final BitSet FOLLOW_plusOp_in_plusExpr667 = new BitSet(new long[]{0x0000000042141510L});
    public static final BitSet FOLLOW_timesExpr_in_plusExpr670 = new BitSet(new long[]{0x0000000050000002L});
    public static final BitSet FOLLOW_prefixExpr_in_timesExpr700 = new BitSet(new long[]{0x0001000208000002L});
    public static final BitSet FOLLOW_timesOp_in_timesExpr707 = new BitSet(new long[]{0x0000000042141510L});
    public static final BitSet FOLLOW_prefixExpr_in_timesExpr710 = new BitSet(new long[]{0x0001000208000002L});
    public static final BitSet FOLLOW_30_in_prefixExpr722 = new BitSet(new long[]{0x0000000042141510L});
    public static final BitSet FOLLOW_prefixExpr_in_prefixExpr724 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NOT_in_prefixExpr736 = new BitSet(new long[]{0x0000000042141510L});
    public static final BitSet FOLLOW_prefixExpr_in_prefixExpr739 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_PRE_in_prefixExpr743 = new BitSet(new long[]{0x0000000042141510L});
    public static final BitSet FOLLOW_prefixExpr_in_prefixExpr746 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_atomicExpr_in_prefixExpr750 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_atomicExpr760 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INT_in_atomicExpr772 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_real_in_atomicExpr776 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_BOOL_in_atomicExpr780 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IF_in_atomicExpr784 = new BitSet(new long[]{0x0000000042141510L});
    public static final BitSet FOLLOW_expr_in_atomicExpr787 = new BitSet(new long[]{0x0800000000000000L});
    public static final BitSet FOLLOW_59_in_atomicExpr789 = new BitSet(new long[]{0x0000000042141510L});
    public static final BitSet FOLLOW_expr_in_atomicExpr792 = new BitSet(new long[]{0x0002000000000000L});
    public static final BitSet FOLLOW_49_in_atomicExpr794 = new BitSet(new long[]{0x0000000042141510L});
    public static final BitSet FOLLOW_expr_in_atomicExpr797 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_25_in_atomicExpr801 = new BitSet(new long[]{0x0000000042141510L});
    public static final BitSet FOLLOW_expr_in_atomicExpr803 = new BitSet(new long[]{0x0000000004000000L});
    public static final BitSet FOLLOW_26_in_atomicExpr805 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INT_in_real819 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_ERROR_in_real821 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_INT_in_real825 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_arrowOp_in_synpred1_Lustre476 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_impliesOp_in_synpred2_Lustre508 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_orOp_in_synpred3_Lustre544 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_andOp_in_synpred4_Lustre576 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_relationalOp_in_synpred5_Lustre628 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_plusOp_in_synpred6_Lustre664 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_timesOp_in_synpred7_Lustre704 = new BitSet(new long[]{0x0000000000000002L});

}