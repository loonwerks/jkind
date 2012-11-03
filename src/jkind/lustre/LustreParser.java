// $ANTLR 3.4 Lustre.g 2012-11-03 10:19:04

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
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "BOOL", "CONSTANTS", "EQUATION", "EQUATIONS", "ERROR", "ID", "IF", "INPUTS", "INT", "LHS", "LOCALS", "MAIN", "ML_COMMENT", "NEGATE", "NODECALL", "NODES", "NOT", "OUTPUTS", "PRE", "PROGRAM", "PROPERTIES", "REAL", "SL_COMMENT", "TYPES", "WS", "'('", "')'", "'*'", "'+'", "','", "'-'", "'--%PROPERTY'", "'->'", "'/'", "':'", "';'", "'<'", "'<='", "'<>'", "'='", "'=>'", "'>'", "'>='", "'['", "']'", "'and'", "'bool'", "'const'", "'div'", "'else'", "'int'", "'let'", "'node'", "'of'", "'or'", "'real'", "'returns'", "'subrange'", "'tel'", "'then'", "'type'", "'var'", "'xor'"
    };

    public static final int EOF=-1;
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
    public static final int T__62=62;
    public static final int T__63=63;
    public static final int T__64=64;
    public static final int T__65=65;
    public static final int T__66=66;
    public static final int BOOL=4;
    public static final int CONSTANTS=5;
    public static final int EQUATION=6;
    public static final int EQUATIONS=7;
    public static final int ERROR=8;
    public static final int ID=9;
    public static final int IF=10;
    public static final int INPUTS=11;
    public static final int INT=12;
    public static final int LHS=13;
    public static final int LOCALS=14;
    public static final int MAIN=15;
    public static final int ML_COMMENT=16;
    public static final int NEGATE=17;
    public static final int NODECALL=18;
    public static final int NODES=19;
    public static final int NOT=20;
    public static final int OUTPUTS=21;
    public static final int PRE=22;
    public static final int PROGRAM=23;
    public static final int PROPERTIES=24;
    public static final int REAL=25;
    public static final int SL_COMMENT=26;
    public static final int TYPES=27;
    public static final int WS=28;

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

      @Override
      public void emitErrorMessage(String msg) {
        System.out.println(msg);
      }


    public static class program_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "program"
    // Lustre.g:46:1: program : ( typedef | constant | node )* EOF -> ^( PROGRAM ^( TYPES ( typedef )* ) ^( CONSTANTS ( constant )* ) ^( NODES ( node )* ) ) ;
    public final LustreParser.program_return program() throws RecognitionException {
        LustreParser.program_return retval = new LustreParser.program_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token EOF4=null;
        LustreParser.typedef_return typedef1 =null;

        LustreParser.constant_return constant2 =null;

        LustreParser.node_return node3 =null;


        Object EOF4_tree=null;
        RewriteRuleTokenStream stream_EOF=new RewriteRuleTokenStream(adaptor,"token EOF");
        RewriteRuleSubtreeStream stream_node=new RewriteRuleSubtreeStream(adaptor,"rule node");
        RewriteRuleSubtreeStream stream_constant=new RewriteRuleSubtreeStream(adaptor,"rule constant");
        RewriteRuleSubtreeStream stream_typedef=new RewriteRuleSubtreeStream(adaptor,"rule typedef");
        try {
            // Lustre.g:46:8: ( ( typedef | constant | node )* EOF -> ^( PROGRAM ^( TYPES ( typedef )* ) ^( CONSTANTS ( constant )* ) ^( NODES ( node )* ) ) )
            // Lustre.g:47:3: ( typedef | constant | node )* EOF
            {
            // Lustre.g:47:3: ( typedef | constant | node )*
            loop1:
            do {
                int alt1=4;
                switch ( input.LA(1) ) {
                case 64:
                    {
                    alt1=1;
                    }
                    break;
                case 51:
                    {
                    alt1=2;
                    }
                    break;
                case 56:
                    {
                    alt1=3;
                    }
                    break;

                }

                switch (alt1) {
            	case 1 :
            	    // Lustre.g:47:4: typedef
            	    {
            	    pushFollow(FOLLOW_typedef_in_program140);
            	    typedef1=typedef();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) stream_typedef.add(typedef1.getTree());

            	    }
            	    break;
            	case 2 :
            	    // Lustre.g:47:14: constant
            	    {
            	    pushFollow(FOLLOW_constant_in_program144);
            	    constant2=constant();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) stream_constant.add(constant2.getTree());

            	    }
            	    break;
            	case 3 :
            	    // Lustre.g:47:25: node
            	    {
            	    pushFollow(FOLLOW_node_in_program148);
            	    node3=node();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) stream_node.add(node3.getTree());

            	    }
            	    break;

            	default :
            	    break loop1;
                }
            } while (true);


            EOF4=(Token)match(input,EOF,FOLLOW_EOF_in_program152); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_EOF.add(EOF4);


            // AST REWRITE
            // elements: typedef, constant, node
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {

            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 47:36: -> ^( PROGRAM ^( TYPES ( typedef )* ) ^( CONSTANTS ( constant )* ) ^( NODES ( node )* ) )
            {
                // Lustre.g:48:5: ^( PROGRAM ^( TYPES ( typedef )* ) ^( CONSTANTS ( constant )* ) ^( NODES ( node )* ) )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot(
                (Object)adaptor.create(PROGRAM, "PROGRAM")
                , root_1);

                // Lustre.g:49:7: ^( TYPES ( typedef )* )
                {
                Object root_2 = (Object)adaptor.nil();
                root_2 = (Object)adaptor.becomeRoot(
                (Object)adaptor.create(TYPES, "TYPES")
                , root_2);

                // Lustre.g:49:15: ( typedef )*
                while ( stream_typedef.hasNext() ) {
                    adaptor.addChild(root_2, stream_typedef.nextTree());

                }
                stream_typedef.reset();

                adaptor.addChild(root_1, root_2);
                }

                // Lustre.g:50:7: ^( CONSTANTS ( constant )* )
                {
                Object root_2 = (Object)adaptor.nil();
                root_2 = (Object)adaptor.becomeRoot(
                (Object)adaptor.create(CONSTANTS, "CONSTANTS")
                , root_2);

                // Lustre.g:50:19: ( constant )*
                while ( stream_constant.hasNext() ) {
                    adaptor.addChild(root_2, stream_constant.nextTree());

                }
                stream_constant.reset();

                adaptor.addChild(root_1, root_2);
                }

                // Lustre.g:51:7: ^( NODES ( node )* )
                {
                Object root_2 = (Object)adaptor.nil();
                root_2 = (Object)adaptor.becomeRoot(
                (Object)adaptor.create(NODES, "NODES")
                , root_2);

                // Lustre.g:51:15: ( node )*
                while ( stream_node.hasNext() ) {
                    adaptor.addChild(root_2, stream_node.nextTree());

                }
                stream_node.reset();

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
    // $ANTLR end "program"


    public static class typedef_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "typedef"
    // Lustre.g:54:1: typedef : 'type' ID '=' type ';' -> ^( ID type ) ;
    public final LustreParser.typedef_return typedef() throws RecognitionException {
        LustreParser.typedef_return retval = new LustreParser.typedef_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token string_literal5=null;
        Token ID6=null;
        Token char_literal7=null;
        Token char_literal9=null;
        LustreParser.type_return type8 =null;


        Object string_literal5_tree=null;
        Object ID6_tree=null;
        Object char_literal7_tree=null;
        Object char_literal9_tree=null;
        RewriteRuleTokenStream stream_43=new RewriteRuleTokenStream(adaptor,"token 43");
        RewriteRuleTokenStream stream_64=new RewriteRuleTokenStream(adaptor,"token 64");
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleTokenStream stream_39=new RewriteRuleTokenStream(adaptor,"token 39");
        RewriteRuleSubtreeStream stream_type=new RewriteRuleSubtreeStream(adaptor,"rule type");
        try {
            // Lustre.g:54:8: ( 'type' ID '=' type ';' -> ^( ID type ) )
            // Lustre.g:55:3: 'type' ID '=' type ';'
            {
            string_literal5=(Token)match(input,64,FOLLOW_64_in_typedef211); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_64.add(string_literal5);


            ID6=(Token)match(input,ID,FOLLOW_ID_in_typedef213); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_ID.add(ID6);


            char_literal7=(Token)match(input,43,FOLLOW_43_in_typedef215); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_43.add(char_literal7);


            pushFollow(FOLLOW_type_in_typedef217);
            type8=type();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_type.add(type8.getTree());

            char_literal9=(Token)match(input,39,FOLLOW_39_in_typedef219); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_39.add(char_literal9);


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
            // 55:26: -> ^( ID type )
            {
                // Lustre.g:55:29: ^( ID type )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot(
                stream_ID.nextNode()
                , root_1);

                adaptor.addChild(root_1, stream_type.nextTree());

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
    // $ANTLR end "typedef"


    public static class constant_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "constant"
    // Lustre.g:58:1: constant : 'const' ID '=' expr ';' -> ^( ID expr ) ;
    public final LustreParser.constant_return constant() throws RecognitionException {
        LustreParser.constant_return retval = new LustreParser.constant_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token string_literal10=null;
        Token ID11=null;
        Token char_literal12=null;
        Token char_literal14=null;
        LustreParser.expr_return expr13 =null;


        Object string_literal10_tree=null;
        Object ID11_tree=null;
        Object char_literal12_tree=null;
        Object char_literal14_tree=null;
        RewriteRuleTokenStream stream_43=new RewriteRuleTokenStream(adaptor,"token 43");
        RewriteRuleTokenStream stream_51=new RewriteRuleTokenStream(adaptor,"token 51");
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleTokenStream stream_39=new RewriteRuleTokenStream(adaptor,"token 39");
        RewriteRuleSubtreeStream stream_expr=new RewriteRuleSubtreeStream(adaptor,"rule expr");
        try {
            // Lustre.g:58:9: ( 'const' ID '=' expr ';' -> ^( ID expr ) )
            // Lustre.g:59:3: 'const' ID '=' expr ';'
            {
            string_literal10=(Token)match(input,51,FOLLOW_51_in_constant237); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_51.add(string_literal10);


            ID11=(Token)match(input,ID,FOLLOW_ID_in_constant239); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_ID.add(ID11);


            char_literal12=(Token)match(input,43,FOLLOW_43_in_constant241); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_43.add(char_literal12);


            pushFollow(FOLLOW_expr_in_constant243);
            expr13=expr();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_expr.add(expr13.getTree());

            char_literal14=(Token)match(input,39,FOLLOW_39_in_constant245); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_39.add(char_literal14);


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
            // 59:27: -> ^( ID expr )
            {
                // Lustre.g:59:30: ^( ID expr )
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


    public static class node_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "node"
    // Lustre.g:62:1: node : 'node' ID '(' (inputs= varDeclList )? ')' 'returns' '(' (outputs= varDeclList )? ')' ';' ( 'var' locals= varDeclList ';' )? 'let' ( equation | property )* 'tel' ';' -> ^( ID ^( INPUTS ( $inputs)? ) ^( OUTPUTS ( $outputs)? ) ^( LOCALS ( $locals)? ) ^( EQUATIONS ( equation )* ) ^( PROPERTIES ( property )* ) ) ;
    public final LustreParser.node_return node() throws RecognitionException {
        LustreParser.node_return retval = new LustreParser.node_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token string_literal15=null;
        Token ID16=null;
        Token char_literal17=null;
        Token char_literal18=null;
        Token string_literal19=null;
        Token char_literal20=null;
        Token char_literal21=null;
        Token char_literal22=null;
        Token string_literal23=null;
        Token char_literal24=null;
        Token string_literal25=null;
        Token string_literal28=null;
        Token char_literal29=null;
        LustreParser.varDeclList_return inputs =null;

        LustreParser.varDeclList_return outputs =null;

        LustreParser.varDeclList_return locals =null;

        LustreParser.equation_return equation26 =null;

        LustreParser.property_return property27 =null;


        Object string_literal15_tree=null;
        Object ID16_tree=null;
        Object char_literal17_tree=null;
        Object char_literal18_tree=null;
        Object string_literal19_tree=null;
        Object char_literal20_tree=null;
        Object char_literal21_tree=null;
        Object char_literal22_tree=null;
        Object string_literal23_tree=null;
        Object char_literal24_tree=null;
        Object string_literal25_tree=null;
        Object string_literal28_tree=null;
        Object char_literal29_tree=null;
        RewriteRuleTokenStream stream_30=new RewriteRuleTokenStream(adaptor,"token 30");
        RewriteRuleTokenStream stream_56=new RewriteRuleTokenStream(adaptor,"token 56");
        RewriteRuleTokenStream stream_55=new RewriteRuleTokenStream(adaptor,"token 55");
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleTokenStream stream_65=new RewriteRuleTokenStream(adaptor,"token 65");
        RewriteRuleTokenStream stream_62=new RewriteRuleTokenStream(adaptor,"token 62");
        RewriteRuleTokenStream stream_60=new RewriteRuleTokenStream(adaptor,"token 60");
        RewriteRuleTokenStream stream_39=new RewriteRuleTokenStream(adaptor,"token 39");
        RewriteRuleTokenStream stream_29=new RewriteRuleTokenStream(adaptor,"token 29");
        RewriteRuleSubtreeStream stream_equation=new RewriteRuleSubtreeStream(adaptor,"rule equation");
        RewriteRuleSubtreeStream stream_varDeclList=new RewriteRuleSubtreeStream(adaptor,"rule varDeclList");
        RewriteRuleSubtreeStream stream_property=new RewriteRuleSubtreeStream(adaptor,"rule property");
        try {
            // Lustre.g:62:5: ( 'node' ID '(' (inputs= varDeclList )? ')' 'returns' '(' (outputs= varDeclList )? ')' ';' ( 'var' locals= varDeclList ';' )? 'let' ( equation | property )* 'tel' ';' -> ^( ID ^( INPUTS ( $inputs)? ) ^( OUTPUTS ( $outputs)? ) ^( LOCALS ( $locals)? ) ^( EQUATIONS ( equation )* ) ^( PROPERTIES ( property )* ) ) )
            // Lustre.g:63:3: 'node' ID '(' (inputs= varDeclList )? ')' 'returns' '(' (outputs= varDeclList )? ')' ';' ( 'var' locals= varDeclList ';' )? 'let' ( equation | property )* 'tel' ';'
            {
            string_literal15=(Token)match(input,56,FOLLOW_56_in_node263); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_56.add(string_literal15);


            ID16=(Token)match(input,ID,FOLLOW_ID_in_node265); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_ID.add(ID16);


            char_literal17=(Token)match(input,29,FOLLOW_29_in_node267); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_29.add(char_literal17);


            // Lustre.g:63:23: (inputs= varDeclList )?
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( (LA2_0==ID) ) {
                alt2=1;
            }
            switch (alt2) {
                case 1 :
                    // Lustre.g:63:23: inputs= varDeclList
                    {
                    pushFollow(FOLLOW_varDeclList_in_node271);
                    inputs=varDeclList();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_varDeclList.add(inputs.getTree());

                    }
                    break;

            }


            char_literal18=(Token)match(input,30,FOLLOW_30_in_node274); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_30.add(char_literal18);


            string_literal19=(Token)match(input,60,FOLLOW_60_in_node278); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_60.add(string_literal19);


            char_literal20=(Token)match(input,29,FOLLOW_29_in_node280); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_29.add(char_literal20);


            // Lustre.g:64:24: (outputs= varDeclList )?
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( (LA3_0==ID) ) {
                alt3=1;
            }
            switch (alt3) {
                case 1 :
                    // Lustre.g:64:24: outputs= varDeclList
                    {
                    pushFollow(FOLLOW_varDeclList_in_node284);
                    outputs=varDeclList();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_varDeclList.add(outputs.getTree());

                    }
                    break;

            }


            char_literal21=(Token)match(input,30,FOLLOW_30_in_node287); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_30.add(char_literal21);


            char_literal22=(Token)match(input,39,FOLLOW_39_in_node289); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_39.add(char_literal22);


            // Lustre.g:65:3: ( 'var' locals= varDeclList ';' )?
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0==65) ) {
                alt4=1;
            }
            switch (alt4) {
                case 1 :
                    // Lustre.g:65:4: 'var' locals= varDeclList ';'
                    {
                    string_literal23=(Token)match(input,65,FOLLOW_65_in_node294); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_65.add(string_literal23);


                    pushFollow(FOLLOW_varDeclList_in_node298);
                    locals=varDeclList();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_varDeclList.add(locals.getTree());

                    char_literal24=(Token)match(input,39,FOLLOW_39_in_node300); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_39.add(char_literal24);


                    }
                    break;

            }


            string_literal25=(Token)match(input,55,FOLLOW_55_in_node306); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_55.add(string_literal25);


            // Lustre.g:67:5: ( equation | property )*
            loop5:
            do {
                int alt5=3;
                int LA5_0 = input.LA(1);

                if ( (LA5_0==ID||LA5_0==29) ) {
                    alt5=1;
                }
                else if ( (LA5_0==35) ) {
                    alt5=2;
                }


                switch (alt5) {
            	case 1 :
            	    // Lustre.g:67:6: equation
            	    {
            	    pushFollow(FOLLOW_equation_in_node313);
            	    equation26=equation();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) stream_equation.add(equation26.getTree());

            	    }
            	    break;
            	case 2 :
            	    // Lustre.g:67:17: property
            	    {
            	    pushFollow(FOLLOW_property_in_node317);
            	    property27=property();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) stream_property.add(property27.getTree());

            	    }
            	    break;

            	default :
            	    break loop5;
                }
            } while (true);


            string_literal28=(Token)match(input,62,FOLLOW_62_in_node323); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_62.add(string_literal28);


            char_literal29=(Token)match(input,39,FOLLOW_39_in_node325); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_39.add(char_literal29);


            // AST REWRITE
            // elements: inputs, locals, property, outputs, equation, ID
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
            // 68:13: -> ^( ID ^( INPUTS ( $inputs)? ) ^( OUTPUTS ( $outputs)? ) ^( LOCALS ( $locals)? ) ^( EQUATIONS ( equation )* ) ^( PROPERTIES ( property )* ) )
            {
                // Lustre.g:69:5: ^( ID ^( INPUTS ( $inputs)? ) ^( OUTPUTS ( $outputs)? ) ^( LOCALS ( $locals)? ) ^( EQUATIONS ( equation )* ) ^( PROPERTIES ( property )* ) )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot(
                stream_ID.nextNode()
                , root_1);

                // Lustre.g:70:7: ^( INPUTS ( $inputs)? )
                {
                Object root_2 = (Object)adaptor.nil();
                root_2 = (Object)adaptor.becomeRoot(
                (Object)adaptor.create(INPUTS, "INPUTS")
                , root_2);

                // Lustre.g:70:17: ( $inputs)?
                if ( stream_inputs.hasNext() ) {
                    adaptor.addChild(root_2, stream_inputs.nextTree());

                }
                stream_inputs.reset();

                adaptor.addChild(root_1, root_2);
                }

                // Lustre.g:71:7: ^( OUTPUTS ( $outputs)? )
                {
                Object root_2 = (Object)adaptor.nil();
                root_2 = (Object)adaptor.becomeRoot(
                (Object)adaptor.create(OUTPUTS, "OUTPUTS")
                , root_2);

                // Lustre.g:71:18: ( $outputs)?
                if ( stream_outputs.hasNext() ) {
                    adaptor.addChild(root_2, stream_outputs.nextTree());

                }
                stream_outputs.reset();

                adaptor.addChild(root_1, root_2);
                }

                // Lustre.g:72:7: ^( LOCALS ( $locals)? )
                {
                Object root_2 = (Object)adaptor.nil();
                root_2 = (Object)adaptor.becomeRoot(
                (Object)adaptor.create(LOCALS, "LOCALS")
                , root_2);

                // Lustre.g:72:17: ( $locals)?
                if ( stream_locals.hasNext() ) {
                    adaptor.addChild(root_2, stream_locals.nextTree());

                }
                stream_locals.reset();

                adaptor.addChild(root_1, root_2);
                }

                // Lustre.g:73:7: ^( EQUATIONS ( equation )* )
                {
                Object root_2 = (Object)adaptor.nil();
                root_2 = (Object)adaptor.becomeRoot(
                (Object)adaptor.create(EQUATIONS, "EQUATIONS")
                , root_2);

                // Lustre.g:73:19: ( equation )*
                while ( stream_equation.hasNext() ) {
                    adaptor.addChild(root_2, stream_equation.nextTree());

                }
                stream_equation.reset();

                adaptor.addChild(root_1, root_2);
                }

                // Lustre.g:74:7: ^( PROPERTIES ( property )* )
                {
                Object root_2 = (Object)adaptor.nil();
                root_2 = (Object)adaptor.becomeRoot(
                (Object)adaptor.create(PROPERTIES, "PROPERTIES")
                , root_2);

                // Lustre.g:74:20: ( property )*
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


    public static class varDeclList_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "varDeclList"
    // Lustre.g:77:1: varDeclList : varDeclGroup ( ';' varDeclGroup )* -> ( varDeclGroup )+ ;
    public final LustreParser.varDeclList_return varDeclList() throws RecognitionException {
        LustreParser.varDeclList_return retval = new LustreParser.varDeclList_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token char_literal31=null;
        LustreParser.varDeclGroup_return varDeclGroup30 =null;

        LustreParser.varDeclGroup_return varDeclGroup32 =null;


        Object char_literal31_tree=null;
        RewriteRuleTokenStream stream_39=new RewriteRuleTokenStream(adaptor,"token 39");
        RewriteRuleSubtreeStream stream_varDeclGroup=new RewriteRuleSubtreeStream(adaptor,"rule varDeclGroup");
        try {
            // Lustre.g:77:12: ( varDeclGroup ( ';' varDeclGroup )* -> ( varDeclGroup )+ )
            // Lustre.g:78:3: varDeclGroup ( ';' varDeclGroup )*
            {
            pushFollow(FOLLOW_varDeclGroup_in_varDeclList413);
            varDeclGroup30=varDeclGroup();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_varDeclGroup.add(varDeclGroup30.getTree());

            // Lustre.g:78:16: ( ';' varDeclGroup )*
            loop6:
            do {
                int alt6=2;
                int LA6_0 = input.LA(1);

                if ( (LA6_0==39) ) {
                    int LA6_2 = input.LA(2);

                    if ( (LA6_2==ID) ) {
                        alt6=1;
                    }


                }


                switch (alt6) {
            	case 1 :
            	    // Lustre.g:78:17: ';' varDeclGroup
            	    {
            	    char_literal31=(Token)match(input,39,FOLLOW_39_in_varDeclList416); if (state.failed) return retval; 
            	    if ( state.backtracking==0 ) stream_39.add(char_literal31);


            	    pushFollow(FOLLOW_varDeclGroup_in_varDeclList418);
            	    varDeclGroup32=varDeclGroup();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) stream_varDeclGroup.add(varDeclGroup32.getTree());

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
            // 78:36: -> ( varDeclGroup )+
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
    // Lustre.g:81:1: varDeclGroup : ID ( ',' ID )* ':' type -> ( ^( ID type ) )* ;
    public final LustreParser.varDeclGroup_return varDeclGroup() throws RecognitionException {
        LustreParser.varDeclGroup_return retval = new LustreParser.varDeclGroup_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token ID33=null;
        Token char_literal34=null;
        Token ID35=null;
        Token char_literal36=null;
        LustreParser.type_return type37 =null;


        Object ID33_tree=null;
        Object char_literal34_tree=null;
        Object ID35_tree=null;
        Object char_literal36_tree=null;
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleTokenStream stream_33=new RewriteRuleTokenStream(adaptor,"token 33");
        RewriteRuleTokenStream stream_38=new RewriteRuleTokenStream(adaptor,"token 38");
        RewriteRuleSubtreeStream stream_type=new RewriteRuleSubtreeStream(adaptor,"rule type");
        try {
            // Lustre.g:81:13: ( ID ( ',' ID )* ':' type -> ( ^( ID type ) )* )
            // Lustre.g:82:3: ID ( ',' ID )* ':' type
            {
            ID33=(Token)match(input,ID,FOLLOW_ID_in_varDeclGroup435); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_ID.add(ID33);


            // Lustre.g:82:6: ( ',' ID )*
            loop7:
            do {
                int alt7=2;
                int LA7_0 = input.LA(1);

                if ( (LA7_0==33) ) {
                    alt7=1;
                }


                switch (alt7) {
            	case 1 :
            	    // Lustre.g:82:7: ',' ID
            	    {
            	    char_literal34=(Token)match(input,33,FOLLOW_33_in_varDeclGroup438); if (state.failed) return retval; 
            	    if ( state.backtracking==0 ) stream_33.add(char_literal34);


            	    ID35=(Token)match(input,ID,FOLLOW_ID_in_varDeclGroup440); if (state.failed) return retval; 
            	    if ( state.backtracking==0 ) stream_ID.add(ID35);


            	    }
            	    break;

            	default :
            	    break loop7;
                }
            } while (true);


            char_literal36=(Token)match(input,38,FOLLOW_38_in_varDeclGroup444); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_38.add(char_literal36);


            pushFollow(FOLLOW_type_in_varDeclGroup446);
            type37=type();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_type.add(type37.getTree());

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
            // 82:25: -> ( ^( ID type ) )*
            {
                // Lustre.g:82:28: ( ^( ID type ) )*
                while ( stream_type.hasNext()||stream_ID.hasNext() ) {
                    // Lustre.g:82:28: ^( ID type )
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
    // Lustre.g:85:1: type : ( 'int' ^| 'subrange' '[' low= bound ',' high= bound ']' 'of' 'int' -> ^( 'int' $low $high) | 'bool' ^| 'real' ^| ID ^);
    public final LustreParser.type_return type() throws RecognitionException {
        LustreParser.type_return retval = new LustreParser.type_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token string_literal38=null;
        Token string_literal39=null;
        Token char_literal40=null;
        Token char_literal41=null;
        Token char_literal42=null;
        Token string_literal43=null;
        Token string_literal44=null;
        Token string_literal45=null;
        Token string_literal46=null;
        Token ID47=null;
        LustreParser.bound_return low =null;

        LustreParser.bound_return high =null;


        Object string_literal38_tree=null;
        Object string_literal39_tree=null;
        Object char_literal40_tree=null;
        Object char_literal41_tree=null;
        Object char_literal42_tree=null;
        Object string_literal43_tree=null;
        Object string_literal44_tree=null;
        Object string_literal45_tree=null;
        Object string_literal46_tree=null;
        Object ID47_tree=null;
        RewriteRuleTokenStream stream_48=new RewriteRuleTokenStream(adaptor,"token 48");
        RewriteRuleTokenStream stream_57=new RewriteRuleTokenStream(adaptor,"token 57");
        RewriteRuleTokenStream stream_47=new RewriteRuleTokenStream(adaptor,"token 47");
        RewriteRuleTokenStream stream_33=new RewriteRuleTokenStream(adaptor,"token 33");
        RewriteRuleTokenStream stream_54=new RewriteRuleTokenStream(adaptor,"token 54");
        RewriteRuleTokenStream stream_61=new RewriteRuleTokenStream(adaptor,"token 61");
        RewriteRuleSubtreeStream stream_bound=new RewriteRuleSubtreeStream(adaptor,"rule bound");
        try {
            // Lustre.g:85:5: ( 'int' ^| 'subrange' '[' low= bound ',' high= bound ']' 'of' 'int' -> ^( 'int' $low $high) | 'bool' ^| 'real' ^| ID ^)
            int alt8=5;
            switch ( input.LA(1) ) {
            case 54:
                {
                alt8=1;
                }
                break;
            case 61:
                {
                alt8=2;
                }
                break;
            case 50:
                {
                alt8=3;
                }
                break;
            case 59:
                {
                alt8=4;
                }
                break;
            case ID:
                {
                alt8=5;
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
                    // Lustre.g:86:3: 'int' ^
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal38=(Token)match(input,54,FOLLOW_54_in_type465); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal38_tree = 
                    (Object)adaptor.create(string_literal38)
                    ;
                    root_0 = (Object)adaptor.becomeRoot(string_literal38_tree, root_0);
                    }

                    }
                    break;
                case 2 :
                    // Lustre.g:87:3: 'subrange' '[' low= bound ',' high= bound ']' 'of' 'int'
                    {
                    string_literal39=(Token)match(input,61,FOLLOW_61_in_type470); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_61.add(string_literal39);


                    char_literal40=(Token)match(input,47,FOLLOW_47_in_type472); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_47.add(char_literal40);


                    pushFollow(FOLLOW_bound_in_type476);
                    low=bound();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_bound.add(low.getTree());

                    char_literal41=(Token)match(input,33,FOLLOW_33_in_type478); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_33.add(char_literal41);


                    pushFollow(FOLLOW_bound_in_type482);
                    high=bound();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_bound.add(high.getTree());

                    char_literal42=(Token)match(input,48,FOLLOW_48_in_type484); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_48.add(char_literal42);


                    string_literal43=(Token)match(input,57,FOLLOW_57_in_type486); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_57.add(string_literal43);


                    string_literal44=(Token)match(input,54,FOLLOW_54_in_type488); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_54.add(string_literal44);


                    // AST REWRITE
                    // elements: low, high, 54
                    // token labels: 
                    // rule labels: retval, high, low
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {

                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
                    RewriteRuleSubtreeStream stream_high=new RewriteRuleSubtreeStream(adaptor,"rule high",high!=null?high.tree:null);
                    RewriteRuleSubtreeStream stream_low=new RewriteRuleSubtreeStream(adaptor,"rule low",low!=null?low.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 87:58: -> ^( 'int' $low $high)
                    {
                        // Lustre.g:87:61: ^( 'int' $low $high)
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot(
                        stream_54.nextNode()
                        , root_1);

                        adaptor.addChild(root_1, stream_low.nextTree());

                        adaptor.addChild(root_1, stream_high.nextTree());

                        adaptor.addChild(root_0, root_1);
                        }

                    }


                    retval.tree = root_0;
                    }

                    }
                    break;
                case 3 :
                    // Lustre.g:88:3: 'bool' ^
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal45=(Token)match(input,50,FOLLOW_50_in_type504); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal45_tree = 
                    (Object)adaptor.create(string_literal45)
                    ;
                    root_0 = (Object)adaptor.becomeRoot(string_literal45_tree, root_0);
                    }

                    }
                    break;
                case 4 :
                    // Lustre.g:89:3: 'real' ^
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal46=(Token)match(input,59,FOLLOW_59_in_type509); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal46_tree = 
                    (Object)adaptor.create(string_literal46)
                    ;
                    root_0 = (Object)adaptor.becomeRoot(string_literal46_tree, root_0);
                    }

                    }
                    break;
                case 5 :
                    // Lustre.g:90:3: ID ^
                    {
                    root_0 = (Object)adaptor.nil();


                    ID47=(Token)match(input,ID,FOLLOW_ID_in_type514); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    ID47_tree = 
                    (Object)adaptor.create(ID47)
                    ;
                    root_0 = (Object)adaptor.becomeRoot(ID47_tree, root_0);
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
    // Lustre.g:93:1: bound : ( '-' )? INT -> INT[$bound.text] ;
    public final LustreParser.bound_return bound() throws RecognitionException {
        LustreParser.bound_return retval = new LustreParser.bound_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token char_literal48=null;
        Token INT49=null;

        Object char_literal48_tree=null;
        Object INT49_tree=null;
        RewriteRuleTokenStream stream_INT=new RewriteRuleTokenStream(adaptor,"token INT");
        RewriteRuleTokenStream stream_34=new RewriteRuleTokenStream(adaptor,"token 34");

        try {
            // Lustre.g:93:6: ( ( '-' )? INT -> INT[$bound.text] )
            // Lustre.g:94:3: ( '-' )? INT
            {
            // Lustre.g:94:3: ( '-' )?
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( (LA9_0==34) ) {
                alt9=1;
            }
            switch (alt9) {
                case 1 :
                    // Lustre.g:94:3: '-'
                    {
                    char_literal48=(Token)match(input,34,FOLLOW_34_in_bound525); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_34.add(char_literal48);


                    }
                    break;

            }


            INT49=(Token)match(input,INT,FOLLOW_INT_in_bound528); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_INT.add(INT49);


            // AST REWRITE
            // elements: INT
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {

            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 94:12: -> INT[$bound.text]
            {
                adaptor.addChild(root_0, 
                (Object)adaptor.create(INT, input.toString(retval.start,input.LT(-1)))
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
    // $ANTLR end "bound"


    public static class property_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "property"
    // Lustre.g:97:1: property : '--%PROPERTY' ID ';' -> ID ;
    public final LustreParser.property_return property() throws RecognitionException {
        LustreParser.property_return retval = new LustreParser.property_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token string_literal50=null;
        Token ID51=null;
        Token char_literal52=null;

        Object string_literal50_tree=null;
        Object ID51_tree=null;
        Object char_literal52_tree=null;
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleTokenStream stream_35=new RewriteRuleTokenStream(adaptor,"token 35");
        RewriteRuleTokenStream stream_39=new RewriteRuleTokenStream(adaptor,"token 39");

        try {
            // Lustre.g:97:9: ( '--%PROPERTY' ID ';' -> ID )
            // Lustre.g:98:3: '--%PROPERTY' ID ';'
            {
            string_literal50=(Token)match(input,35,FOLLOW_35_in_property543); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_35.add(string_literal50);


            ID51=(Token)match(input,ID,FOLLOW_ID_in_property545); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_ID.add(ID51);


            char_literal52=(Token)match(input,39,FOLLOW_39_in_property547); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_39.add(char_literal52);


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
            // 98:24: -> ID
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
    // Lustre.g:101:1: equation : lhs_opt_parens '=' expr ';' -> ^( EQUATION lhs_opt_parens expr ) ;
    public final LustreParser.equation_return equation() throws RecognitionException {
        LustreParser.equation_return retval = new LustreParser.equation_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token char_literal54=null;
        Token char_literal56=null;
        LustreParser.lhs_opt_parens_return lhs_opt_parens53 =null;

        LustreParser.expr_return expr55 =null;


        Object char_literal54_tree=null;
        Object char_literal56_tree=null;
        RewriteRuleTokenStream stream_43=new RewriteRuleTokenStream(adaptor,"token 43");
        RewriteRuleTokenStream stream_39=new RewriteRuleTokenStream(adaptor,"token 39");
        RewriteRuleSubtreeStream stream_lhs_opt_parens=new RewriteRuleSubtreeStream(adaptor,"rule lhs_opt_parens");
        RewriteRuleSubtreeStream stream_expr=new RewriteRuleSubtreeStream(adaptor,"rule expr");
        try {
            // Lustre.g:101:9: ( lhs_opt_parens '=' expr ';' -> ^( EQUATION lhs_opt_parens expr ) )
            // Lustre.g:102:3: lhs_opt_parens '=' expr ';'
            {
            pushFollow(FOLLOW_lhs_opt_parens_in_equation561);
            lhs_opt_parens53=lhs_opt_parens();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_lhs_opt_parens.add(lhs_opt_parens53.getTree());

            char_literal54=(Token)match(input,43,FOLLOW_43_in_equation563); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_43.add(char_literal54);


            pushFollow(FOLLOW_expr_in_equation565);
            expr55=expr();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_expr.add(expr55.getTree());

            char_literal56=(Token)match(input,39,FOLLOW_39_in_equation567); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_39.add(char_literal56);


            // AST REWRITE
            // elements: expr, lhs_opt_parens
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {

            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 102:31: -> ^( EQUATION lhs_opt_parens expr )
            {
                // Lustre.g:102:34: ^( EQUATION lhs_opt_parens expr )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot(
                (Object)adaptor.create(EQUATION, "EQUATION")
                , root_1);

                adaptor.addChild(root_1, stream_lhs_opt_parens.nextTree());

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


    public static class lhs_opt_parens_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "lhs_opt_parens"
    // Lustre.g:105:1: lhs_opt_parens : ( lhs | '(' lhs ')' -> lhs );
    public final LustreParser.lhs_opt_parens_return lhs_opt_parens() throws RecognitionException {
        LustreParser.lhs_opt_parens_return retval = new LustreParser.lhs_opt_parens_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token char_literal58=null;
        Token char_literal60=null;
        LustreParser.lhs_return lhs57 =null;

        LustreParser.lhs_return lhs59 =null;


        Object char_literal58_tree=null;
        Object char_literal60_tree=null;
        RewriteRuleTokenStream stream_30=new RewriteRuleTokenStream(adaptor,"token 30");
        RewriteRuleTokenStream stream_29=new RewriteRuleTokenStream(adaptor,"token 29");
        RewriteRuleSubtreeStream stream_lhs=new RewriteRuleSubtreeStream(adaptor,"rule lhs");
        try {
            // Lustre.g:105:15: ( lhs | '(' lhs ')' -> lhs )
            int alt10=2;
            int LA10_0 = input.LA(1);

            if ( (LA10_0==ID) ) {
                alt10=1;
            }
            else if ( (LA10_0==29) ) {
                alt10=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 10, 0, input);

                throw nvae;

            }
            switch (alt10) {
                case 1 :
                    // Lustre.g:106:3: lhs
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_lhs_in_lhs_opt_parens587);
                    lhs57=lhs();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, lhs57.getTree());

                    }
                    break;
                case 2 :
                    // Lustre.g:107:3: '(' lhs ')'
                    {
                    char_literal58=(Token)match(input,29,FOLLOW_29_in_lhs_opt_parens591); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_29.add(char_literal58);


                    pushFollow(FOLLOW_lhs_in_lhs_opt_parens593);
                    lhs59=lhs();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_lhs.add(lhs59.getTree());

                    char_literal60=(Token)match(input,30,FOLLOW_30_in_lhs_opt_parens595); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_30.add(char_literal60);


                    // AST REWRITE
                    // elements: lhs
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {

                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 107:15: -> lhs
                    {
                        adaptor.addChild(root_0, stream_lhs.nextTree());

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
    // $ANTLR end "lhs_opt_parens"


    public static class lhs_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "lhs"
    // Lustre.g:110:1: lhs : ID ( ',' ID )* -> ^( LHS ( ID )* ) ;
    public final LustreParser.lhs_return lhs() throws RecognitionException {
        LustreParser.lhs_return retval = new LustreParser.lhs_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token ID61=null;
        Token char_literal62=null;
        Token ID63=null;

        Object ID61_tree=null;
        Object char_literal62_tree=null;
        Object ID63_tree=null;
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleTokenStream stream_33=new RewriteRuleTokenStream(adaptor,"token 33");

        try {
            // Lustre.g:110:4: ( ID ( ',' ID )* -> ^( LHS ( ID )* ) )
            // Lustre.g:111:3: ID ( ',' ID )*
            {
            ID61=(Token)match(input,ID,FOLLOW_ID_in_lhs609); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_ID.add(ID61);


            // Lustre.g:111:6: ( ',' ID )*
            loop11:
            do {
                int alt11=2;
                int LA11_0 = input.LA(1);

                if ( (LA11_0==33) ) {
                    alt11=1;
                }


                switch (alt11) {
            	case 1 :
            	    // Lustre.g:111:7: ',' ID
            	    {
            	    char_literal62=(Token)match(input,33,FOLLOW_33_in_lhs612); if (state.failed) return retval; 
            	    if ( state.backtracking==0 ) stream_33.add(char_literal62);


            	    ID63=(Token)match(input,ID,FOLLOW_ID_in_lhs614); if (state.failed) return retval; 
            	    if ( state.backtracking==0 ) stream_ID.add(ID63);


            	    }
            	    break;

            	default :
            	    break loop11;
                }
            } while (true);


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
            // 111:16: -> ^( LHS ( ID )* )
            {
                // Lustre.g:111:19: ^( LHS ( ID )* )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot(
                (Object)adaptor.create(LHS, "LHS")
                , root_1);

                // Lustre.g:111:25: ( ID )*
                while ( stream_ID.hasNext() ) {
                    adaptor.addChild(root_1, 
                    stream_ID.nextNode()
                    );

                }
                stream_ID.reset();

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
    // $ANTLR end "lhs"


    public static class expr_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "expr"
    // Lustre.g:114:1: expr : arrowExpr ;
    public final LustreParser.expr_return expr() throws RecognitionException {
        LustreParser.expr_return retval = new LustreParser.expr_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        LustreParser.arrowExpr_return arrowExpr64 =null;



        try {
            // Lustre.g:114:5: ( arrowExpr )
            // Lustre.g:115:3: arrowExpr
            {
            root_0 = (Object)adaptor.nil();


            pushFollow(FOLLOW_arrowExpr_in_expr635);
            arrowExpr64=arrowExpr();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, arrowExpr64.getTree());

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
    // Lustre.g:118:1: arrowOp : '->' ;
    public final LustreParser.arrowOp_return arrowOp() throws RecognitionException {
        LustreParser.arrowOp_return retval = new LustreParser.arrowOp_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token string_literal65=null;

        Object string_literal65_tree=null;

        try {
            // Lustre.g:118:8: ( '->' )
            // Lustre.g:119:3: '->'
            {
            root_0 = (Object)adaptor.nil();


            string_literal65=(Token)match(input,36,FOLLOW_36_in_arrowOp645); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            string_literal65_tree = 
            (Object)adaptor.create(string_literal65)
            ;
            adaptor.addChild(root_0, string_literal65_tree);
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
    // Lustre.g:122:1: arrowExpr : impliesExpr ( ( arrowOp )=> arrowOp ^ arrowExpr )? ;
    public final LustreParser.arrowExpr_return arrowExpr() throws RecognitionException {
        LustreParser.arrowExpr_return retval = new LustreParser.arrowExpr_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        LustreParser.impliesExpr_return impliesExpr66 =null;

        LustreParser.arrowOp_return arrowOp67 =null;

        LustreParser.arrowExpr_return arrowExpr68 =null;



        try {
            // Lustre.g:122:10: ( impliesExpr ( ( arrowOp )=> arrowOp ^ arrowExpr )? )
            // Lustre.g:123:3: impliesExpr ( ( arrowOp )=> arrowOp ^ arrowExpr )?
            {
            root_0 = (Object)adaptor.nil();


            pushFollow(FOLLOW_impliesExpr_in_arrowExpr655);
            impliesExpr66=impliesExpr();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, impliesExpr66.getTree());

            // Lustre.g:123:15: ( ( arrowOp )=> arrowOp ^ arrowExpr )?
            int alt12=2;
            int LA12_0 = input.LA(1);

            if ( (LA12_0==36) ) {
                int LA12_1 = input.LA(2);

                if ( (synpred1_Lustre()) ) {
                    alt12=1;
                }
            }
            switch (alt12) {
                case 1 :
                    // Lustre.g:123:16: ( arrowOp )=> arrowOp ^ arrowExpr
                    {
                    pushFollow(FOLLOW_arrowOp_in_arrowExpr662);
                    arrowOp67=arrowOp();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) root_0 = (Object)adaptor.becomeRoot(arrowOp67.getTree(), root_0);

                    pushFollow(FOLLOW_arrowExpr_in_arrowExpr665);
                    arrowExpr68=arrowExpr();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, arrowExpr68.getTree());

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
    // Lustre.g:126:1: impliesOp : '=>' ;
    public final LustreParser.impliesOp_return impliesOp() throws RecognitionException {
        LustreParser.impliesOp_return retval = new LustreParser.impliesOp_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token string_literal69=null;

        Object string_literal69_tree=null;

        try {
            // Lustre.g:126:10: ( '=>' )
            // Lustre.g:127:3: '=>'
            {
            root_0 = (Object)adaptor.nil();


            string_literal69=(Token)match(input,44,FOLLOW_44_in_impliesOp677); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            string_literal69_tree = 
            (Object)adaptor.create(string_literal69)
            ;
            adaptor.addChild(root_0, string_literal69_tree);
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
    // Lustre.g:130:1: impliesExpr : orExpr ( ( impliesOp )=> impliesOp ^ impliesExpr )? ;
    public final LustreParser.impliesExpr_return impliesExpr() throws RecognitionException {
        LustreParser.impliesExpr_return retval = new LustreParser.impliesExpr_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        LustreParser.orExpr_return orExpr70 =null;

        LustreParser.impliesOp_return impliesOp71 =null;

        LustreParser.impliesExpr_return impliesExpr72 =null;



        try {
            // Lustre.g:130:12: ( orExpr ( ( impliesOp )=> impliesOp ^ impliesExpr )? )
            // Lustre.g:131:3: orExpr ( ( impliesOp )=> impliesOp ^ impliesExpr )?
            {
            root_0 = (Object)adaptor.nil();


            pushFollow(FOLLOW_orExpr_in_impliesExpr687);
            orExpr70=orExpr();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, orExpr70.getTree());

            // Lustre.g:131:10: ( ( impliesOp )=> impliesOp ^ impliesExpr )?
            int alt13=2;
            int LA13_0 = input.LA(1);

            if ( (LA13_0==44) ) {
                int LA13_1 = input.LA(2);

                if ( (synpred2_Lustre()) ) {
                    alt13=1;
                }
            }
            switch (alt13) {
                case 1 :
                    // Lustre.g:131:11: ( impliesOp )=> impliesOp ^ impliesExpr
                    {
                    pushFollow(FOLLOW_impliesOp_in_impliesExpr694);
                    impliesOp71=impliesOp();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) root_0 = (Object)adaptor.becomeRoot(impliesOp71.getTree(), root_0);

                    pushFollow(FOLLOW_impliesExpr_in_impliesExpr697);
                    impliesExpr72=impliesExpr();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, impliesExpr72.getTree());

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
    // Lustre.g:134:1: orOp : ( 'or' | 'xor' );
    public final LustreParser.orOp_return orOp() throws RecognitionException {
        LustreParser.orOp_return retval = new LustreParser.orOp_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token set73=null;

        Object set73_tree=null;

        try {
            // Lustre.g:134:5: ( 'or' | 'xor' )
            // Lustre.g:
            {
            root_0 = (Object)adaptor.nil();


            set73=(Token)input.LT(1);

            if ( input.LA(1)==58||input.LA(1)==66 ) {
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
    // $ANTLR end "orOp"


    public static class orExpr_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "orExpr"
    // Lustre.g:138:1: orExpr : andExpr ( ( orOp )=> orOp ^ andExpr )* ;
    public final LustreParser.orExpr_return orExpr() throws RecognitionException {
        LustreParser.orExpr_return retval = new LustreParser.orExpr_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        LustreParser.andExpr_return andExpr74 =null;

        LustreParser.orOp_return orOp75 =null;

        LustreParser.andExpr_return andExpr76 =null;



        try {
            // Lustre.g:138:7: ( andExpr ( ( orOp )=> orOp ^ andExpr )* )
            // Lustre.g:139:3: andExpr ( ( orOp )=> orOp ^ andExpr )*
            {
            root_0 = (Object)adaptor.nil();


            pushFollow(FOLLOW_andExpr_in_orExpr723);
            andExpr74=andExpr();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, andExpr74.getTree());

            // Lustre.g:139:11: ( ( orOp )=> orOp ^ andExpr )*
            loop14:
            do {
                int alt14=2;
                int LA14_0 = input.LA(1);

                if ( (LA14_0==58||LA14_0==66) ) {
                    int LA14_2 = input.LA(2);

                    if ( (synpred3_Lustre()) ) {
                        alt14=1;
                    }


                }


                switch (alt14) {
            	case 1 :
            	    // Lustre.g:139:12: ( orOp )=> orOp ^ andExpr
            	    {
            	    pushFollow(FOLLOW_orOp_in_orExpr730);
            	    orOp75=orOp();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) root_0 = (Object)adaptor.becomeRoot(orOp75.getTree(), root_0);

            	    pushFollow(FOLLOW_andExpr_in_orExpr733);
            	    andExpr76=andExpr();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, andExpr76.getTree());

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
    // $ANTLR end "orExpr"


    public static class andOp_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "andOp"
    // Lustre.g:142:1: andOp : 'and' ;
    public final LustreParser.andOp_return andOp() throws RecognitionException {
        LustreParser.andOp_return retval = new LustreParser.andOp_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token string_literal77=null;

        Object string_literal77_tree=null;

        try {
            // Lustre.g:142:6: ( 'and' )
            // Lustre.g:143:3: 'and'
            {
            root_0 = (Object)adaptor.nil();


            string_literal77=(Token)match(input,49,FOLLOW_49_in_andOp745); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            string_literal77_tree = 
            (Object)adaptor.create(string_literal77)
            ;
            adaptor.addChild(root_0, string_literal77_tree);
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
    // Lustre.g:146:1: andExpr : relationalExpr ( ( andOp )=> andOp ^ relationalExpr )* ;
    public final LustreParser.andExpr_return andExpr() throws RecognitionException {
        LustreParser.andExpr_return retval = new LustreParser.andExpr_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        LustreParser.relationalExpr_return relationalExpr78 =null;

        LustreParser.andOp_return andOp79 =null;

        LustreParser.relationalExpr_return relationalExpr80 =null;



        try {
            // Lustre.g:146:8: ( relationalExpr ( ( andOp )=> andOp ^ relationalExpr )* )
            // Lustre.g:147:3: relationalExpr ( ( andOp )=> andOp ^ relationalExpr )*
            {
            root_0 = (Object)adaptor.nil();


            pushFollow(FOLLOW_relationalExpr_in_andExpr755);
            relationalExpr78=relationalExpr();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, relationalExpr78.getTree());

            // Lustre.g:147:18: ( ( andOp )=> andOp ^ relationalExpr )*
            loop15:
            do {
                int alt15=2;
                int LA15_0 = input.LA(1);

                if ( (LA15_0==49) ) {
                    int LA15_2 = input.LA(2);

                    if ( (synpred4_Lustre()) ) {
                        alt15=1;
                    }


                }


                switch (alt15) {
            	case 1 :
            	    // Lustre.g:147:19: ( andOp )=> andOp ^ relationalExpr
            	    {
            	    pushFollow(FOLLOW_andOp_in_andExpr762);
            	    andOp79=andOp();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) root_0 = (Object)adaptor.becomeRoot(andOp79.getTree(), root_0);

            	    pushFollow(FOLLOW_relationalExpr_in_andExpr765);
            	    relationalExpr80=relationalExpr();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, relationalExpr80.getTree());

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
    // $ANTLR end "andExpr"


    public static class relationalOp_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "relationalOp"
    // Lustre.g:150:1: relationalOp : ( '<' | '<=' | '>' | '>=' | '=' | '<>' );
    public final LustreParser.relationalOp_return relationalOp() throws RecognitionException {
        LustreParser.relationalOp_return retval = new LustreParser.relationalOp_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token set81=null;

        Object set81_tree=null;

        try {
            // Lustre.g:150:13: ( '<' | '<=' | '>' | '>=' | '=' | '<>' )
            // Lustre.g:
            {
            root_0 = (Object)adaptor.nil();


            set81=(Token)input.LT(1);

            if ( (input.LA(1) >= 40 && input.LA(1) <= 43)||(input.LA(1) >= 45 && input.LA(1) <= 46) ) {
                input.consume();
                if ( state.backtracking==0 ) adaptor.addChild(root_0, 
                (Object)adaptor.create(set81)
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
    // Lustre.g:154:1: relationalExpr : plusExpr ( ( relationalOp )=> relationalOp ^ plusExpr )? ;
    public final LustreParser.relationalExpr_return relationalExpr() throws RecognitionException {
        LustreParser.relationalExpr_return retval = new LustreParser.relationalExpr_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        LustreParser.plusExpr_return plusExpr82 =null;

        LustreParser.relationalOp_return relationalOp83 =null;

        LustreParser.plusExpr_return plusExpr84 =null;



        try {
            // Lustre.g:154:15: ( plusExpr ( ( relationalOp )=> relationalOp ^ plusExpr )? )
            // Lustre.g:155:3: plusExpr ( ( relationalOp )=> relationalOp ^ plusExpr )?
            {
            root_0 = (Object)adaptor.nil();


            pushFollow(FOLLOW_plusExpr_in_relationalExpr807);
            plusExpr82=plusExpr();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, plusExpr82.getTree());

            // Lustre.g:155:12: ( ( relationalOp )=> relationalOp ^ plusExpr )?
            int alt16=2;
            int LA16_0 = input.LA(1);

            if ( ((LA16_0 >= 40 && LA16_0 <= 43)||(LA16_0 >= 45 && LA16_0 <= 46)) ) {
                int LA16_1 = input.LA(2);

                if ( (synpred5_Lustre()) ) {
                    alt16=1;
                }
            }
            switch (alt16) {
                case 1 :
                    // Lustre.g:155:13: ( relationalOp )=> relationalOp ^ plusExpr
                    {
                    pushFollow(FOLLOW_relationalOp_in_relationalExpr814);
                    relationalOp83=relationalOp();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) root_0 = (Object)adaptor.becomeRoot(relationalOp83.getTree(), root_0);

                    pushFollow(FOLLOW_plusExpr_in_relationalExpr817);
                    plusExpr84=plusExpr();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, plusExpr84.getTree());

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
    // Lustre.g:158:1: plusOp : ( '+' | '-' );
    public final LustreParser.plusOp_return plusOp() throws RecognitionException {
        LustreParser.plusOp_return retval = new LustreParser.plusOp_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token set85=null;

        Object set85_tree=null;

        try {
            // Lustre.g:158:7: ( '+' | '-' )
            // Lustre.g:
            {
            root_0 = (Object)adaptor.nil();


            set85=(Token)input.LT(1);

            if ( input.LA(1)==32||input.LA(1)==34 ) {
                input.consume();
                if ( state.backtracking==0 ) adaptor.addChild(root_0, 
                (Object)adaptor.create(set85)
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
    // Lustre.g:162:1: plusExpr : timesExpr ( ( plusOp )=> plusOp ^ timesExpr )* ;
    public final LustreParser.plusExpr_return plusExpr() throws RecognitionException {
        LustreParser.plusExpr_return retval = new LustreParser.plusExpr_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        LustreParser.timesExpr_return timesExpr86 =null;

        LustreParser.plusOp_return plusOp87 =null;

        LustreParser.timesExpr_return timesExpr88 =null;



        try {
            // Lustre.g:162:9: ( timesExpr ( ( plusOp )=> plusOp ^ timesExpr )* )
            // Lustre.g:163:3: timesExpr ( ( plusOp )=> plusOp ^ timesExpr )*
            {
            root_0 = (Object)adaptor.nil();


            pushFollow(FOLLOW_timesExpr_in_plusExpr843);
            timesExpr86=timesExpr();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, timesExpr86.getTree());

            // Lustre.g:163:13: ( ( plusOp )=> plusOp ^ timesExpr )*
            loop17:
            do {
                int alt17=2;
                int LA17_0 = input.LA(1);

                if ( (LA17_0==32||LA17_0==34) ) {
                    int LA17_2 = input.LA(2);

                    if ( (synpred6_Lustre()) ) {
                        alt17=1;
                    }


                }


                switch (alt17) {
            	case 1 :
            	    // Lustre.g:163:14: ( plusOp )=> plusOp ^ timesExpr
            	    {
            	    pushFollow(FOLLOW_plusOp_in_plusExpr850);
            	    plusOp87=plusOp();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) root_0 = (Object)adaptor.becomeRoot(plusOp87.getTree(), root_0);

            	    pushFollow(FOLLOW_timesExpr_in_plusExpr853);
            	    timesExpr88=timesExpr();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, timesExpr88.getTree());

            	    }
            	    break;

            	default :
            	    break loop17;
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
    // Lustre.g:166:1: timesOp : ( '*' | '/' | 'div' );
    public final LustreParser.timesOp_return timesOp() throws RecognitionException {
        LustreParser.timesOp_return retval = new LustreParser.timesOp_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token set89=null;

        Object set89_tree=null;

        try {
            // Lustre.g:166:8: ( '*' | '/' | 'div' )
            // Lustre.g:
            {
            root_0 = (Object)adaptor.nil();


            set89=(Token)input.LT(1);

            if ( input.LA(1)==31||input.LA(1)==37||input.LA(1)==52 ) {
                input.consume();
                if ( state.backtracking==0 ) adaptor.addChild(root_0, 
                (Object)adaptor.create(set89)
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
    // Lustre.g:170:1: timesExpr : prefixExpr ( ( timesOp )=> timesOp ^ prefixExpr )* ;
    public final LustreParser.timesExpr_return timesExpr() throws RecognitionException {
        LustreParser.timesExpr_return retval = new LustreParser.timesExpr_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        LustreParser.prefixExpr_return prefixExpr90 =null;

        LustreParser.timesOp_return timesOp91 =null;

        LustreParser.prefixExpr_return prefixExpr92 =null;



        try {
            // Lustre.g:170:10: ( prefixExpr ( ( timesOp )=> timesOp ^ prefixExpr )* )
            // Lustre.g:171:3: prefixExpr ( ( timesOp )=> timesOp ^ prefixExpr )*
            {
            root_0 = (Object)adaptor.nil();


            pushFollow(FOLLOW_prefixExpr_in_timesExpr883);
            prefixExpr90=prefixExpr();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, prefixExpr90.getTree());

            // Lustre.g:171:14: ( ( timesOp )=> timesOp ^ prefixExpr )*
            loop18:
            do {
                int alt18=2;
                int LA18_0 = input.LA(1);

                if ( (LA18_0==31||LA18_0==37||LA18_0==52) ) {
                    int LA18_2 = input.LA(2);

                    if ( (synpred7_Lustre()) ) {
                        alt18=1;
                    }


                }


                switch (alt18) {
            	case 1 :
            	    // Lustre.g:171:15: ( timesOp )=> timesOp ^ prefixExpr
            	    {
            	    pushFollow(FOLLOW_timesOp_in_timesExpr890);
            	    timesOp91=timesOp();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) root_0 = (Object)adaptor.becomeRoot(timesOp91.getTree(), root_0);

            	    pushFollow(FOLLOW_prefixExpr_in_timesExpr893);
            	    prefixExpr92=prefixExpr();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, prefixExpr92.getTree());

            	    }
            	    break;

            	default :
            	    break loop18;
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
    // Lustre.g:174:1: prefixExpr : ( '-' prefixExpr -> ^( NEGATE prefixExpr ) | NOT ^ prefixExpr | PRE ^ prefixExpr | atomicExpr );
    public final LustreParser.prefixExpr_return prefixExpr() throws RecognitionException {
        LustreParser.prefixExpr_return retval = new LustreParser.prefixExpr_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token char_literal93=null;
        Token NOT95=null;
        Token PRE97=null;
        LustreParser.prefixExpr_return prefixExpr94 =null;

        LustreParser.prefixExpr_return prefixExpr96 =null;

        LustreParser.prefixExpr_return prefixExpr98 =null;

        LustreParser.atomicExpr_return atomicExpr99 =null;


        Object char_literal93_tree=null;
        Object NOT95_tree=null;
        Object PRE97_tree=null;
        RewriteRuleTokenStream stream_34=new RewriteRuleTokenStream(adaptor,"token 34");
        RewriteRuleSubtreeStream stream_prefixExpr=new RewriteRuleSubtreeStream(adaptor,"rule prefixExpr");
        try {
            // Lustre.g:174:11: ( '-' prefixExpr -> ^( NEGATE prefixExpr ) | NOT ^ prefixExpr | PRE ^ prefixExpr | atomicExpr )
            int alt19=4;
            switch ( input.LA(1) ) {
            case 34:
                {
                alt19=1;
                }
                break;
            case NOT:
                {
                alt19=2;
                }
                break;
            case PRE:
                {
                alt19=3;
                }
                break;
            case BOOL:
            case ID:
            case IF:
            case INT:
            case 29:
                {
                alt19=4;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 19, 0, input);

                throw nvae;

            }

            switch (alt19) {
                case 1 :
                    // Lustre.g:175:3: '-' prefixExpr
                    {
                    char_literal93=(Token)match(input,34,FOLLOW_34_in_prefixExpr905); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_34.add(char_literal93);


                    pushFollow(FOLLOW_prefixExpr_in_prefixExpr907);
                    prefixExpr94=prefixExpr();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_prefixExpr.add(prefixExpr94.getTree());

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
                    // 175:18: -> ^( NEGATE prefixExpr )
                    {
                        // Lustre.g:175:21: ^( NEGATE prefixExpr )
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
                    // Lustre.g:176:3: NOT ^ prefixExpr
                    {
                    root_0 = (Object)adaptor.nil();


                    NOT95=(Token)match(input,NOT,FOLLOW_NOT_in_prefixExpr919); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    NOT95_tree = 
                    (Object)adaptor.create(NOT95)
                    ;
                    root_0 = (Object)adaptor.becomeRoot(NOT95_tree, root_0);
                    }

                    pushFollow(FOLLOW_prefixExpr_in_prefixExpr922);
                    prefixExpr96=prefixExpr();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, prefixExpr96.getTree());

                    }
                    break;
                case 3 :
                    // Lustre.g:177:3: PRE ^ prefixExpr
                    {
                    root_0 = (Object)adaptor.nil();


                    PRE97=(Token)match(input,PRE,FOLLOW_PRE_in_prefixExpr926); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    PRE97_tree = 
                    (Object)adaptor.create(PRE97)
                    ;
                    root_0 = (Object)adaptor.becomeRoot(PRE97_tree, root_0);
                    }

                    pushFollow(FOLLOW_prefixExpr_in_prefixExpr929);
                    prefixExpr98=prefixExpr();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, prefixExpr98.getTree());

                    }
                    break;
                case 4 :
                    // Lustre.g:178:3: atomicExpr
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_atomicExpr_in_prefixExpr933);
                    atomicExpr99=atomicExpr();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, atomicExpr99.getTree());

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
    // Lustre.g:181:1: atomicExpr : ( ID | INT | real | BOOL | IF ^ expr 'then' ! expr 'else' ! expr | ID '(' ( expr ( ',' expr )* )? ')' -> ^( NODECALL ID ( expr )* ) | '(' expr ')' -> expr );
    public final LustreParser.atomicExpr_return atomicExpr() throws RecognitionException {
        LustreParser.atomicExpr_return retval = new LustreParser.atomicExpr_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token ID100=null;
        Token INT101=null;
        Token BOOL103=null;
        Token IF104=null;
        Token string_literal106=null;
        Token string_literal108=null;
        Token ID110=null;
        Token char_literal111=null;
        Token char_literal113=null;
        Token char_literal115=null;
        Token char_literal116=null;
        Token char_literal118=null;
        LustreParser.real_return real102 =null;

        LustreParser.expr_return expr105 =null;

        LustreParser.expr_return expr107 =null;

        LustreParser.expr_return expr109 =null;

        LustreParser.expr_return expr112 =null;

        LustreParser.expr_return expr114 =null;

        LustreParser.expr_return expr117 =null;


        Object ID100_tree=null;
        Object INT101_tree=null;
        Object BOOL103_tree=null;
        Object IF104_tree=null;
        Object string_literal106_tree=null;
        Object string_literal108_tree=null;
        Object ID110_tree=null;
        Object char_literal111_tree=null;
        Object char_literal113_tree=null;
        Object char_literal115_tree=null;
        Object char_literal116_tree=null;
        Object char_literal118_tree=null;
        RewriteRuleTokenStream stream_30=new RewriteRuleTokenStream(adaptor,"token 30");
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleTokenStream stream_33=new RewriteRuleTokenStream(adaptor,"token 33");
        RewriteRuleTokenStream stream_29=new RewriteRuleTokenStream(adaptor,"token 29");
        RewriteRuleSubtreeStream stream_expr=new RewriteRuleSubtreeStream(adaptor,"rule expr");
        try {
            // Lustre.g:181:11: ( ID | INT | real | BOOL | IF ^ expr 'then' ! expr 'else' ! expr | ID '(' ( expr ( ',' expr )* )? ')' -> ^( NODECALL ID ( expr )* ) | '(' expr ')' -> expr )
            int alt22=7;
            switch ( input.LA(1) ) {
            case ID:
                {
                int LA22_1 = input.LA(2);

                if ( (LA22_1==29) ) {
                    alt22=6;
                }
                else if ( ((LA22_1 >= 30 && LA22_1 <= 34)||(LA22_1 >= 36 && LA22_1 <= 37)||(LA22_1 >= 39 && LA22_1 <= 46)||LA22_1==49||(LA22_1 >= 52 && LA22_1 <= 53)||LA22_1==58||LA22_1==63||LA22_1==66) ) {
                    alt22=1;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 22, 1, input);

                    throw nvae;

                }
                }
                break;
            case INT:
                {
                int LA22_2 = input.LA(2);

                if ( (LA22_2==ERROR) ) {
                    alt22=3;
                }
                else if ( ((LA22_2 >= 30 && LA22_2 <= 34)||(LA22_2 >= 36 && LA22_2 <= 37)||(LA22_2 >= 39 && LA22_2 <= 46)||LA22_2==49||(LA22_2 >= 52 && LA22_2 <= 53)||LA22_2==58||LA22_2==63||LA22_2==66) ) {
                    alt22=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 22, 2, input);

                    throw nvae;

                }
                }
                break;
            case BOOL:
                {
                alt22=4;
                }
                break;
            case IF:
                {
                alt22=5;
                }
                break;
            case 29:
                {
                alt22=7;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 22, 0, input);

                throw nvae;

            }

            switch (alt22) {
                case 1 :
                    // Lustre.g:182:3: ID
                    {
                    root_0 = (Object)adaptor.nil();


                    ID100=(Token)match(input,ID,FOLLOW_ID_in_atomicExpr943); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    ID100_tree = 
                    (Object)adaptor.create(ID100)
                    ;
                    adaptor.addChild(root_0, ID100_tree);
                    }

                    }
                    break;
                case 2 :
                    // Lustre.g:183:3: INT
                    {
                    root_0 = (Object)adaptor.nil();


                    INT101=(Token)match(input,INT,FOLLOW_INT_in_atomicExpr947); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    INT101_tree = 
                    (Object)adaptor.create(INT101)
                    ;
                    adaptor.addChild(root_0, INT101_tree);
                    }

                    }
                    break;
                case 3 :
                    // Lustre.g:184:3: real
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_real_in_atomicExpr951);
                    real102=real();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, real102.getTree());

                    }
                    break;
                case 4 :
                    // Lustre.g:185:3: BOOL
                    {
                    root_0 = (Object)adaptor.nil();


                    BOOL103=(Token)match(input,BOOL,FOLLOW_BOOL_in_atomicExpr955); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    BOOL103_tree = 
                    (Object)adaptor.create(BOOL103)
                    ;
                    adaptor.addChild(root_0, BOOL103_tree);
                    }

                    }
                    break;
                case 5 :
                    // Lustre.g:186:3: IF ^ expr 'then' ! expr 'else' ! expr
                    {
                    root_0 = (Object)adaptor.nil();


                    IF104=(Token)match(input,IF,FOLLOW_IF_in_atomicExpr959); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    IF104_tree = 
                    (Object)adaptor.create(IF104)
                    ;
                    root_0 = (Object)adaptor.becomeRoot(IF104_tree, root_0);
                    }

                    pushFollow(FOLLOW_expr_in_atomicExpr962);
                    expr105=expr();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, expr105.getTree());

                    string_literal106=(Token)match(input,63,FOLLOW_63_in_atomicExpr964); if (state.failed) return retval;

                    pushFollow(FOLLOW_expr_in_atomicExpr967);
                    expr107=expr();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, expr107.getTree());

                    string_literal108=(Token)match(input,53,FOLLOW_53_in_atomicExpr969); if (state.failed) return retval;

                    pushFollow(FOLLOW_expr_in_atomicExpr972);
                    expr109=expr();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, expr109.getTree());

                    }
                    break;
                case 6 :
                    // Lustre.g:187:3: ID '(' ( expr ( ',' expr )* )? ')'
                    {
                    ID110=(Token)match(input,ID,FOLLOW_ID_in_atomicExpr976); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_ID.add(ID110);


                    char_literal111=(Token)match(input,29,FOLLOW_29_in_atomicExpr978); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_29.add(char_literal111);


                    // Lustre.g:187:10: ( expr ( ',' expr )* )?
                    int alt21=2;
                    int LA21_0 = input.LA(1);

                    if ( (LA21_0==BOOL||(LA21_0 >= ID && LA21_0 <= IF)||LA21_0==INT||LA21_0==NOT||LA21_0==PRE||LA21_0==29||LA21_0==34) ) {
                        alt21=1;
                    }
                    switch (alt21) {
                        case 1 :
                            // Lustre.g:187:11: expr ( ',' expr )*
                            {
                            pushFollow(FOLLOW_expr_in_atomicExpr981);
                            expr112=expr();

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) stream_expr.add(expr112.getTree());

                            // Lustre.g:187:16: ( ',' expr )*
                            loop20:
                            do {
                                int alt20=2;
                                int LA20_0 = input.LA(1);

                                if ( (LA20_0==33) ) {
                                    alt20=1;
                                }


                                switch (alt20) {
                            	case 1 :
                            	    // Lustre.g:187:17: ',' expr
                            	    {
                            	    char_literal113=(Token)match(input,33,FOLLOW_33_in_atomicExpr984); if (state.failed) return retval; 
                            	    if ( state.backtracking==0 ) stream_33.add(char_literal113);


                            	    pushFollow(FOLLOW_expr_in_atomicExpr986);
                            	    expr114=expr();

                            	    state._fsp--;
                            	    if (state.failed) return retval;
                            	    if ( state.backtracking==0 ) stream_expr.add(expr114.getTree());

                            	    }
                            	    break;

                            	default :
                            	    break loop20;
                                }
                            } while (true);


                            }
                            break;

                    }


                    char_literal115=(Token)match(input,30,FOLLOW_30_in_atomicExpr992); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_30.add(char_literal115);


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
                    // 187:34: -> ^( NODECALL ID ( expr )* )
                    {
                        // Lustre.g:187:37: ^( NODECALL ID ( expr )* )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot(
                        (Object)adaptor.create(NODECALL, "NODECALL")
                        , root_1);

                        adaptor.addChild(root_1, 
                        stream_ID.nextNode()
                        );

                        // Lustre.g:187:51: ( expr )*
                        while ( stream_expr.hasNext() ) {
                            adaptor.addChild(root_1, stream_expr.nextTree());

                        }
                        stream_expr.reset();

                        adaptor.addChild(root_0, root_1);
                        }

                    }


                    retval.tree = root_0;
                    }

                    }
                    break;
                case 7 :
                    // Lustre.g:188:3: '(' expr ')'
                    {
                    char_literal116=(Token)match(input,29,FOLLOW_29_in_atomicExpr1007); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_29.add(char_literal116);


                    pushFollow(FOLLOW_expr_in_atomicExpr1009);
                    expr117=expr();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_expr.add(expr117.getTree());

                    char_literal118=(Token)match(input,30,FOLLOW_30_in_atomicExpr1011); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_30.add(char_literal118);


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
                    // 188:16: -> expr
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
    // Lustre.g:191:1: real : INT '.' INT -> REAL[$real.text] ;
    public final LustreParser.real_return real() throws RecognitionException {
        LustreParser.real_return retval = new LustreParser.real_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token INT119=null;
        Token char_literal120=null;
        Token INT121=null;

        Object INT119_tree=null;
        Object char_literal120_tree=null;
        Object INT121_tree=null;
        RewriteRuleTokenStream stream_INT=new RewriteRuleTokenStream(adaptor,"token INT");
        RewriteRuleTokenStream stream_ERROR=new RewriteRuleTokenStream(adaptor,"token ERROR");

        try {
            // Lustre.g:191:5: ( INT '.' INT -> REAL[$real.text] )
            // Lustre.g:191:7: INT '.' INT
            {
            INT119=(Token)match(input,INT,FOLLOW_INT_in_real1023); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_INT.add(INT119);


            char_literal120=(Token)match(input,ERROR,FOLLOW_ERROR_in_real1025); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_ERROR.add(char_literal120);


            INT121=(Token)match(input,INT,FOLLOW_INT_in_real1027); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_INT.add(INT121);


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
            // 191:19: -> REAL[$real.text]
            {
                adaptor.addChild(root_0, 
                (Object)adaptor.create(REAL, input.toString(retval.start,input.LT(-1)))
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
    // $ANTLR end "real"

    // $ANTLR start synpred1_Lustre
    public final void synpred1_Lustre_fragment() throws RecognitionException {
        // Lustre.g:123:16: ( arrowOp )
        // Lustre.g:123:17: arrowOp
        {
        pushFollow(FOLLOW_arrowOp_in_synpred1_Lustre659);
        arrowOp();

        state._fsp--;
        if (state.failed) return ;

        }

    }
    // $ANTLR end synpred1_Lustre

    // $ANTLR start synpred2_Lustre
    public final void synpred2_Lustre_fragment() throws RecognitionException {
        // Lustre.g:131:11: ( impliesOp )
        // Lustre.g:131:12: impliesOp
        {
        pushFollow(FOLLOW_impliesOp_in_synpred2_Lustre691);
        impliesOp();

        state._fsp--;
        if (state.failed) return ;

        }

    }
    // $ANTLR end synpred2_Lustre

    // $ANTLR start synpred3_Lustre
    public final void synpred3_Lustre_fragment() throws RecognitionException {
        // Lustre.g:139:12: ( orOp )
        // Lustre.g:139:13: orOp
        {
        pushFollow(FOLLOW_orOp_in_synpred3_Lustre727);
        orOp();

        state._fsp--;
        if (state.failed) return ;

        }

    }
    // $ANTLR end synpred3_Lustre

    // $ANTLR start synpred4_Lustre
    public final void synpred4_Lustre_fragment() throws RecognitionException {
        // Lustre.g:147:19: ( andOp )
        // Lustre.g:147:20: andOp
        {
        pushFollow(FOLLOW_andOp_in_synpred4_Lustre759);
        andOp();

        state._fsp--;
        if (state.failed) return ;

        }

    }
    // $ANTLR end synpred4_Lustre

    // $ANTLR start synpred5_Lustre
    public final void synpred5_Lustre_fragment() throws RecognitionException {
        // Lustre.g:155:13: ( relationalOp )
        // Lustre.g:155:14: relationalOp
        {
        pushFollow(FOLLOW_relationalOp_in_synpred5_Lustre811);
        relationalOp();

        state._fsp--;
        if (state.failed) return ;

        }

    }
    // $ANTLR end synpred5_Lustre

    // $ANTLR start synpred6_Lustre
    public final void synpred6_Lustre_fragment() throws RecognitionException {
        // Lustre.g:163:14: ( plusOp )
        // Lustre.g:163:15: plusOp
        {
        pushFollow(FOLLOW_plusOp_in_synpred6_Lustre847);
        plusOp();

        state._fsp--;
        if (state.failed) return ;

        }

    }
    // $ANTLR end synpred6_Lustre

    // $ANTLR start synpred7_Lustre
    public final void synpred7_Lustre_fragment() throws RecognitionException {
        // Lustre.g:171:15: ( timesOp )
        // Lustre.g:171:16: timesOp
        {
        pushFollow(FOLLOW_timesOp_in_synpred7_Lustre887);
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


 

    public static final BitSet FOLLOW_typedef_in_program140 = new BitSet(new long[]{0x0108000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_constant_in_program144 = new BitSet(new long[]{0x0108000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_node_in_program148 = new BitSet(new long[]{0x0108000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_EOF_in_program152 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_64_in_typedef211 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_ID_in_typedef213 = new BitSet(new long[]{0x0000080000000000L});
    public static final BitSet FOLLOW_43_in_typedef215 = new BitSet(new long[]{0x2844000000000200L});
    public static final BitSet FOLLOW_type_in_typedef217 = new BitSet(new long[]{0x0000008000000000L});
    public static final BitSet FOLLOW_39_in_typedef219 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_51_in_constant237 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_ID_in_constant239 = new BitSet(new long[]{0x0000080000000000L});
    public static final BitSet FOLLOW_43_in_constant241 = new BitSet(new long[]{0x0000000420501610L});
    public static final BitSet FOLLOW_expr_in_constant243 = new BitSet(new long[]{0x0000008000000000L});
    public static final BitSet FOLLOW_39_in_constant245 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_56_in_node263 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_ID_in_node265 = new BitSet(new long[]{0x0000000020000000L});
    public static final BitSet FOLLOW_29_in_node267 = new BitSet(new long[]{0x0000000040000200L});
    public static final BitSet FOLLOW_varDeclList_in_node271 = new BitSet(new long[]{0x0000000040000000L});
    public static final BitSet FOLLOW_30_in_node274 = new BitSet(new long[]{0x1000000000000000L});
    public static final BitSet FOLLOW_60_in_node278 = new BitSet(new long[]{0x0000000020000000L});
    public static final BitSet FOLLOW_29_in_node280 = new BitSet(new long[]{0x0000000040000200L});
    public static final BitSet FOLLOW_varDeclList_in_node284 = new BitSet(new long[]{0x0000000040000000L});
    public static final BitSet FOLLOW_30_in_node287 = new BitSet(new long[]{0x0000008000000000L});
    public static final BitSet FOLLOW_39_in_node289 = new BitSet(new long[]{0x0080000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_node294 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_varDeclList_in_node298 = new BitSet(new long[]{0x0000008000000000L});
    public static final BitSet FOLLOW_39_in_node300 = new BitSet(new long[]{0x0080000000000000L});
    public static final BitSet FOLLOW_55_in_node306 = new BitSet(new long[]{0x4000000820000200L});
    public static final BitSet FOLLOW_equation_in_node313 = new BitSet(new long[]{0x4000000820000200L});
    public static final BitSet FOLLOW_property_in_node317 = new BitSet(new long[]{0x4000000820000200L});
    public static final BitSet FOLLOW_62_in_node323 = new BitSet(new long[]{0x0000008000000000L});
    public static final BitSet FOLLOW_39_in_node325 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_varDeclGroup_in_varDeclList413 = new BitSet(new long[]{0x0000008000000002L});
    public static final BitSet FOLLOW_39_in_varDeclList416 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_varDeclGroup_in_varDeclList418 = new BitSet(new long[]{0x0000008000000002L});
    public static final BitSet FOLLOW_ID_in_varDeclGroup435 = new BitSet(new long[]{0x0000004200000000L});
    public static final BitSet FOLLOW_33_in_varDeclGroup438 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_ID_in_varDeclGroup440 = new BitSet(new long[]{0x0000004200000000L});
    public static final BitSet FOLLOW_38_in_varDeclGroup444 = new BitSet(new long[]{0x2844000000000200L});
    public static final BitSet FOLLOW_type_in_varDeclGroup446 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_54_in_type465 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_61_in_type470 = new BitSet(new long[]{0x0000800000000000L});
    public static final BitSet FOLLOW_47_in_type472 = new BitSet(new long[]{0x0000000400001000L});
    public static final BitSet FOLLOW_bound_in_type476 = new BitSet(new long[]{0x0000000200000000L});
    public static final BitSet FOLLOW_33_in_type478 = new BitSet(new long[]{0x0000000400001000L});
    public static final BitSet FOLLOW_bound_in_type482 = new BitSet(new long[]{0x0001000000000000L});
    public static final BitSet FOLLOW_48_in_type484 = new BitSet(new long[]{0x0200000000000000L});
    public static final BitSet FOLLOW_57_in_type486 = new BitSet(new long[]{0x0040000000000000L});
    public static final BitSet FOLLOW_54_in_type488 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_50_in_type504 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_59_in_type509 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_type514 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_34_in_bound525 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_INT_in_bound528 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_35_in_property543 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_ID_in_property545 = new BitSet(new long[]{0x0000008000000000L});
    public static final BitSet FOLLOW_39_in_property547 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_lhs_opt_parens_in_equation561 = new BitSet(new long[]{0x0000080000000000L});
    public static final BitSet FOLLOW_43_in_equation563 = new BitSet(new long[]{0x0000000420501610L});
    public static final BitSet FOLLOW_expr_in_equation565 = new BitSet(new long[]{0x0000008000000000L});
    public static final BitSet FOLLOW_39_in_equation567 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_lhs_in_lhs_opt_parens587 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_29_in_lhs_opt_parens591 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_lhs_in_lhs_opt_parens593 = new BitSet(new long[]{0x0000000040000000L});
    public static final BitSet FOLLOW_30_in_lhs_opt_parens595 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_lhs609 = new BitSet(new long[]{0x0000000200000002L});
    public static final BitSet FOLLOW_33_in_lhs612 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_ID_in_lhs614 = new BitSet(new long[]{0x0000000200000002L});
    public static final BitSet FOLLOW_arrowExpr_in_expr635 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_36_in_arrowOp645 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_impliesExpr_in_arrowExpr655 = new BitSet(new long[]{0x0000001000000002L});
    public static final BitSet FOLLOW_arrowOp_in_arrowExpr662 = new BitSet(new long[]{0x0000000420501610L});
    public static final BitSet FOLLOW_arrowExpr_in_arrowExpr665 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_44_in_impliesOp677 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_orExpr_in_impliesExpr687 = new BitSet(new long[]{0x0000100000000002L});
    public static final BitSet FOLLOW_impliesOp_in_impliesExpr694 = new BitSet(new long[]{0x0000000420501610L});
    public static final BitSet FOLLOW_impliesExpr_in_impliesExpr697 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_andExpr_in_orExpr723 = new BitSet(new long[]{0x0400000000000002L,0x0000000000000004L});
    public static final BitSet FOLLOW_orOp_in_orExpr730 = new BitSet(new long[]{0x0000000420501610L});
    public static final BitSet FOLLOW_andExpr_in_orExpr733 = new BitSet(new long[]{0x0400000000000002L,0x0000000000000004L});
    public static final BitSet FOLLOW_49_in_andOp745 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_relationalExpr_in_andExpr755 = new BitSet(new long[]{0x0002000000000002L});
    public static final BitSet FOLLOW_andOp_in_andExpr762 = new BitSet(new long[]{0x0000000420501610L});
    public static final BitSet FOLLOW_relationalExpr_in_andExpr765 = new BitSet(new long[]{0x0002000000000002L});
    public static final BitSet FOLLOW_plusExpr_in_relationalExpr807 = new BitSet(new long[]{0x00006F0000000002L});
    public static final BitSet FOLLOW_relationalOp_in_relationalExpr814 = new BitSet(new long[]{0x0000000420501610L});
    public static final BitSet FOLLOW_plusExpr_in_relationalExpr817 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_timesExpr_in_plusExpr843 = new BitSet(new long[]{0x0000000500000002L});
    public static final BitSet FOLLOW_plusOp_in_plusExpr850 = new BitSet(new long[]{0x0000000420501610L});
    public static final BitSet FOLLOW_timesExpr_in_plusExpr853 = new BitSet(new long[]{0x0000000500000002L});
    public static final BitSet FOLLOW_prefixExpr_in_timesExpr883 = new BitSet(new long[]{0x0010002080000002L});
    public static final BitSet FOLLOW_timesOp_in_timesExpr890 = new BitSet(new long[]{0x0000000420501610L});
    public static final BitSet FOLLOW_prefixExpr_in_timesExpr893 = new BitSet(new long[]{0x0010002080000002L});
    public static final BitSet FOLLOW_34_in_prefixExpr905 = new BitSet(new long[]{0x0000000420501610L});
    public static final BitSet FOLLOW_prefixExpr_in_prefixExpr907 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NOT_in_prefixExpr919 = new BitSet(new long[]{0x0000000420501610L});
    public static final BitSet FOLLOW_prefixExpr_in_prefixExpr922 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_PRE_in_prefixExpr926 = new BitSet(new long[]{0x0000000420501610L});
    public static final BitSet FOLLOW_prefixExpr_in_prefixExpr929 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_atomicExpr_in_prefixExpr933 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_atomicExpr943 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INT_in_atomicExpr947 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_real_in_atomicExpr951 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_BOOL_in_atomicExpr955 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IF_in_atomicExpr959 = new BitSet(new long[]{0x0000000420501610L});
    public static final BitSet FOLLOW_expr_in_atomicExpr962 = new BitSet(new long[]{0x8000000000000000L});
    public static final BitSet FOLLOW_63_in_atomicExpr964 = new BitSet(new long[]{0x0000000420501610L});
    public static final BitSet FOLLOW_expr_in_atomicExpr967 = new BitSet(new long[]{0x0020000000000000L});
    public static final BitSet FOLLOW_53_in_atomicExpr969 = new BitSet(new long[]{0x0000000420501610L});
    public static final BitSet FOLLOW_expr_in_atomicExpr972 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_atomicExpr976 = new BitSet(new long[]{0x0000000020000000L});
    public static final BitSet FOLLOW_29_in_atomicExpr978 = new BitSet(new long[]{0x0000000460501610L});
    public static final BitSet FOLLOW_expr_in_atomicExpr981 = new BitSet(new long[]{0x0000000240000000L});
    public static final BitSet FOLLOW_33_in_atomicExpr984 = new BitSet(new long[]{0x0000000420501610L});
    public static final BitSet FOLLOW_expr_in_atomicExpr986 = new BitSet(new long[]{0x0000000240000000L});
    public static final BitSet FOLLOW_30_in_atomicExpr992 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_29_in_atomicExpr1007 = new BitSet(new long[]{0x0000000420501610L});
    public static final BitSet FOLLOW_expr_in_atomicExpr1009 = new BitSet(new long[]{0x0000000040000000L});
    public static final BitSet FOLLOW_30_in_atomicExpr1011 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INT_in_real1023 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_ERROR_in_real1025 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_INT_in_real1027 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_arrowOp_in_synpred1_Lustre659 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_impliesOp_in_synpred2_Lustre691 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_orOp_in_synpred3_Lustre727 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_andOp_in_synpred4_Lustre759 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_relationalOp_in_synpred5_Lustre811 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_plusOp_in_synpred6_Lustre847 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_timesOp_in_synpred7_Lustre887 = new BitSet(new long[]{0x0000000000000002L});

}