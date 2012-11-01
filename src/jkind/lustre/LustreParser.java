// $ANTLR 3.4 Lustre.g 2012-10-31 19:34:50

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
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "BOOL", "CONSTANTS", "EQUATIONS", "ERROR", "ID", "IDENT", "IF", "INPUTS", "INT", "LOCALS", "MAIN", "ML_COMMENT", "NEGATE", "NODECALL", "NODES", "NOT", "OUTPUTS", "PRE", "PROGRAM", "PROPERTIES", "REAL", "SL_COMMENT", "TYPES", "WS", "'('", "')'", "'*'", "'+'", "','", "'-'", "'--%PROPERTY'", "'->'", "'/'", "':'", "';'", "'<'", "'<='", "'<>'", "'='", "'=>'", "'>'", "'>='", "'['", "']'", "'and'", "'bool'", "'const'", "'div'", "'else'", "'int'", "'let'", "'node'", "'of'", "'or'", "'real'", "'returns'", "'subrange'", "'tel'", "'then'", "'type'", "'var'", "'xor'"
    };

    public static final int EOF=-1;
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
    public static final int T__62=62;
    public static final int T__63=63;
    public static final int T__64=64;
    public static final int T__65=65;
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
    public static final int NODECALL=17;
    public static final int NODES=18;
    public static final int NOT=19;
    public static final int OUTPUTS=20;
    public static final int PRE=21;
    public static final int PROGRAM=22;
    public static final int PROPERTIES=23;
    public static final int REAL=24;
    public static final int SL_COMMENT=25;
    public static final int TYPES=26;
    public static final int WS=27;

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


    public static class program_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "program"
    // Lustre.g:50:1: program : ( typedef | constant | node )* EOF -> ^( PROGRAM ^( TYPES ( typedef )* ) ^( CONSTANTS ( constant )* ) ^( NODES ( node )* ) ) ;
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
            // Lustre.g:50:8: ( ( typedef | constant | node )* EOF -> ^( PROGRAM ^( TYPES ( typedef )* ) ^( CONSTANTS ( constant )* ) ^( NODES ( node )* ) ) )
            // Lustre.g:51:3: ( typedef | constant | node )* EOF
            {
            // Lustre.g:51:3: ( typedef | constant | node )*
            loop1:
            do {
                int alt1=4;
                switch ( input.LA(1) ) {
                case 63:
                    {
                    alt1=1;
                    }
                    break;
                case 50:
                    {
                    alt1=2;
                    }
                    break;
                case 55:
                    {
                    alt1=3;
                    }
                    break;

                }

                switch (alt1) {
            	case 1 :
            	    // Lustre.g:51:4: typedef
            	    {
            	    pushFollow(FOLLOW_typedef_in_program135);
            	    typedef1=typedef();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) stream_typedef.add(typedef1.getTree());

            	    }
            	    break;
            	case 2 :
            	    // Lustre.g:51:14: constant
            	    {
            	    pushFollow(FOLLOW_constant_in_program139);
            	    constant2=constant();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) stream_constant.add(constant2.getTree());

            	    }
            	    break;
            	case 3 :
            	    // Lustre.g:51:25: node
            	    {
            	    pushFollow(FOLLOW_node_in_program143);
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


            EOF4=(Token)match(input,EOF,FOLLOW_EOF_in_program147); if (state.failed) return retval; 
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
            // 51:36: -> ^( PROGRAM ^( TYPES ( typedef )* ) ^( CONSTANTS ( constant )* ) ^( NODES ( node )* ) )
            {
                // Lustre.g:52:5: ^( PROGRAM ^( TYPES ( typedef )* ) ^( CONSTANTS ( constant )* ) ^( NODES ( node )* ) )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot(
                (Object)adaptor.create(PROGRAM, "PROGRAM")
                , root_1);

                // Lustre.g:53:7: ^( TYPES ( typedef )* )
                {
                Object root_2 = (Object)adaptor.nil();
                root_2 = (Object)adaptor.becomeRoot(
                (Object)adaptor.create(TYPES, "TYPES")
                , root_2);

                // Lustre.g:53:15: ( typedef )*
                while ( stream_typedef.hasNext() ) {
                    adaptor.addChild(root_2, stream_typedef.nextTree());

                }
                stream_typedef.reset();

                adaptor.addChild(root_1, root_2);
                }

                // Lustre.g:54:7: ^( CONSTANTS ( constant )* )
                {
                Object root_2 = (Object)adaptor.nil();
                root_2 = (Object)adaptor.becomeRoot(
                (Object)adaptor.create(CONSTANTS, "CONSTANTS")
                , root_2);

                // Lustre.g:54:19: ( constant )*
                while ( stream_constant.hasNext() ) {
                    adaptor.addChild(root_2, stream_constant.nextTree());

                }
                stream_constant.reset();

                adaptor.addChild(root_1, root_2);
                }

                // Lustre.g:55:7: ^( NODES ( node )* )
                {
                Object root_2 = (Object)adaptor.nil();
                root_2 = (Object)adaptor.becomeRoot(
                (Object)adaptor.create(NODES, "NODES")
                , root_2);

                // Lustre.g:55:15: ( node )*
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
    // Lustre.g:58:1: typedef : 'type' ID '=' type ';' -> ^( ID type ) ;
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
        RewriteRuleTokenStream stream_42=new RewriteRuleTokenStream(adaptor,"token 42");
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleTokenStream stream_63=new RewriteRuleTokenStream(adaptor,"token 63");
        RewriteRuleTokenStream stream_38=new RewriteRuleTokenStream(adaptor,"token 38");
        RewriteRuleSubtreeStream stream_type=new RewriteRuleSubtreeStream(adaptor,"rule type");
        try {
            // Lustre.g:58:8: ( 'type' ID '=' type ';' -> ^( ID type ) )
            // Lustre.g:59:3: 'type' ID '=' type ';'
            {
            string_literal5=(Token)match(input,63,FOLLOW_63_in_typedef206); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_63.add(string_literal5);


            ID6=(Token)match(input,ID,FOLLOW_ID_in_typedef208); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_ID.add(ID6);


            char_literal7=(Token)match(input,42,FOLLOW_42_in_typedef210); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_42.add(char_literal7);


            pushFollow(FOLLOW_type_in_typedef212);
            type8=type();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_type.add(type8.getTree());

            char_literal9=(Token)match(input,38,FOLLOW_38_in_typedef214); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_38.add(char_literal9);


            // AST REWRITE
            // elements: ID, type
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {

            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 59:26: -> ^( ID type )
            {
                // Lustre.g:59:29: ^( ID type )
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
    // Lustre.g:62:1: constant : 'const' ID '=' expr ';' -> ^( ID expr ) ;
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
        RewriteRuleTokenStream stream_42=new RewriteRuleTokenStream(adaptor,"token 42");
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleTokenStream stream_50=new RewriteRuleTokenStream(adaptor,"token 50");
        RewriteRuleTokenStream stream_38=new RewriteRuleTokenStream(adaptor,"token 38");
        RewriteRuleSubtreeStream stream_expr=new RewriteRuleSubtreeStream(adaptor,"rule expr");
        try {
            // Lustre.g:62:9: ( 'const' ID '=' expr ';' -> ^( ID expr ) )
            // Lustre.g:63:3: 'const' ID '=' expr ';'
            {
            string_literal10=(Token)match(input,50,FOLLOW_50_in_constant232); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_50.add(string_literal10);


            ID11=(Token)match(input,ID,FOLLOW_ID_in_constant234); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_ID.add(ID11);


            char_literal12=(Token)match(input,42,FOLLOW_42_in_constant236); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_42.add(char_literal12);


            pushFollow(FOLLOW_expr_in_constant238);
            expr13=expr();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_expr.add(expr13.getTree());

            char_literal14=(Token)match(input,38,FOLLOW_38_in_constant240); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_38.add(char_literal14);


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
            // 63:27: -> ^( ID expr )
            {
                // Lustre.g:63:30: ^( ID expr )
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
    // Lustre.g:66:1: node : 'node' ID '(' (inputs= varDeclList )? ')' 'returns' '(' (outputs= varDeclList )? ')' ';' ( 'var' locals= varDeclList ';' )? 'let' ( equation | property )* 'tel' ';' -> ^( ID ^( INPUTS ( $inputs)? ) ^( OUTPUTS ( $outputs)? ) ^( LOCALS ( $locals)? ) ^( EQUATIONS ( equation )* ) ^( PROPERTIES ( property )* ) ) ;
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
        RewriteRuleTokenStream stream_59=new RewriteRuleTokenStream(adaptor,"token 59");
        RewriteRuleTokenStream stream_55=new RewriteRuleTokenStream(adaptor,"token 55");
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleTokenStream stream_64=new RewriteRuleTokenStream(adaptor,"token 64");
        RewriteRuleTokenStream stream_54=new RewriteRuleTokenStream(adaptor,"token 54");
        RewriteRuleTokenStream stream_61=new RewriteRuleTokenStream(adaptor,"token 61");
        RewriteRuleTokenStream stream_28=new RewriteRuleTokenStream(adaptor,"token 28");
        RewriteRuleTokenStream stream_29=new RewriteRuleTokenStream(adaptor,"token 29");
        RewriteRuleTokenStream stream_38=new RewriteRuleTokenStream(adaptor,"token 38");
        RewriteRuleSubtreeStream stream_equation=new RewriteRuleSubtreeStream(adaptor,"rule equation");
        RewriteRuleSubtreeStream stream_varDeclList=new RewriteRuleSubtreeStream(adaptor,"rule varDeclList");
        RewriteRuleSubtreeStream stream_property=new RewriteRuleSubtreeStream(adaptor,"rule property");
        try {
            // Lustre.g:66:5: ( 'node' ID '(' (inputs= varDeclList )? ')' 'returns' '(' (outputs= varDeclList )? ')' ';' ( 'var' locals= varDeclList ';' )? 'let' ( equation | property )* 'tel' ';' -> ^( ID ^( INPUTS ( $inputs)? ) ^( OUTPUTS ( $outputs)? ) ^( LOCALS ( $locals)? ) ^( EQUATIONS ( equation )* ) ^( PROPERTIES ( property )* ) ) )
            // Lustre.g:67:3: 'node' ID '(' (inputs= varDeclList )? ')' 'returns' '(' (outputs= varDeclList )? ')' ';' ( 'var' locals= varDeclList ';' )? 'let' ( equation | property )* 'tel' ';'
            {
            string_literal15=(Token)match(input,55,FOLLOW_55_in_node258); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_55.add(string_literal15);


            ID16=(Token)match(input,ID,FOLLOW_ID_in_node260); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_ID.add(ID16);


            char_literal17=(Token)match(input,28,FOLLOW_28_in_node262); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_28.add(char_literal17);


            // Lustre.g:67:23: (inputs= varDeclList )?
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( (LA2_0==ID) ) {
                alt2=1;
            }
            switch (alt2) {
                case 1 :
                    // Lustre.g:67:23: inputs= varDeclList
                    {
                    pushFollow(FOLLOW_varDeclList_in_node266);
                    inputs=varDeclList();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_varDeclList.add(inputs.getTree());

                    }
                    break;

            }


            char_literal18=(Token)match(input,29,FOLLOW_29_in_node269); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_29.add(char_literal18);


            string_literal19=(Token)match(input,59,FOLLOW_59_in_node273); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_59.add(string_literal19);


            char_literal20=(Token)match(input,28,FOLLOW_28_in_node275); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_28.add(char_literal20);


            // Lustre.g:68:24: (outputs= varDeclList )?
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( (LA3_0==ID) ) {
                alt3=1;
            }
            switch (alt3) {
                case 1 :
                    // Lustre.g:68:24: outputs= varDeclList
                    {
                    pushFollow(FOLLOW_varDeclList_in_node279);
                    outputs=varDeclList();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_varDeclList.add(outputs.getTree());

                    }
                    break;

            }


            char_literal21=(Token)match(input,29,FOLLOW_29_in_node282); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_29.add(char_literal21);


            char_literal22=(Token)match(input,38,FOLLOW_38_in_node284); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_38.add(char_literal22);


            // Lustre.g:69:3: ( 'var' locals= varDeclList ';' )?
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0==64) ) {
                alt4=1;
            }
            switch (alt4) {
                case 1 :
                    // Lustre.g:69:4: 'var' locals= varDeclList ';'
                    {
                    string_literal23=(Token)match(input,64,FOLLOW_64_in_node289); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_64.add(string_literal23);


                    pushFollow(FOLLOW_varDeclList_in_node293);
                    locals=varDeclList();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_varDeclList.add(locals.getTree());

                    char_literal24=(Token)match(input,38,FOLLOW_38_in_node295); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_38.add(char_literal24);


                    }
                    break;

            }


            string_literal25=(Token)match(input,54,FOLLOW_54_in_node301); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_54.add(string_literal25);


            // Lustre.g:71:5: ( equation | property )*
            loop5:
            do {
                int alt5=3;
                int LA5_0 = input.LA(1);

                if ( (LA5_0==ID) ) {
                    alt5=1;
                }
                else if ( (LA5_0==34) ) {
                    alt5=2;
                }


                switch (alt5) {
            	case 1 :
            	    // Lustre.g:71:6: equation
            	    {
            	    pushFollow(FOLLOW_equation_in_node308);
            	    equation26=equation();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) stream_equation.add(equation26.getTree());

            	    }
            	    break;
            	case 2 :
            	    // Lustre.g:71:17: property
            	    {
            	    pushFollow(FOLLOW_property_in_node312);
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


            string_literal28=(Token)match(input,61,FOLLOW_61_in_node318); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_61.add(string_literal28);


            char_literal29=(Token)match(input,38,FOLLOW_38_in_node320); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_38.add(char_literal29);


            // AST REWRITE
            // elements: outputs, equation, ID, locals, property, inputs
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
            // 72:13: -> ^( ID ^( INPUTS ( $inputs)? ) ^( OUTPUTS ( $outputs)? ) ^( LOCALS ( $locals)? ) ^( EQUATIONS ( equation )* ) ^( PROPERTIES ( property )* ) )
            {
                // Lustre.g:73:5: ^( ID ^( INPUTS ( $inputs)? ) ^( OUTPUTS ( $outputs)? ) ^( LOCALS ( $locals)? ) ^( EQUATIONS ( equation )* ) ^( PROPERTIES ( property )* ) )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot(
                stream_ID.nextNode()
                , root_1);

                // Lustre.g:74:7: ^( INPUTS ( $inputs)? )
                {
                Object root_2 = (Object)adaptor.nil();
                root_2 = (Object)adaptor.becomeRoot(
                (Object)adaptor.create(INPUTS, "INPUTS")
                , root_2);

                // Lustre.g:74:17: ( $inputs)?
                if ( stream_inputs.hasNext() ) {
                    adaptor.addChild(root_2, stream_inputs.nextTree());

                }
                stream_inputs.reset();

                adaptor.addChild(root_1, root_2);
                }

                // Lustre.g:75:7: ^( OUTPUTS ( $outputs)? )
                {
                Object root_2 = (Object)adaptor.nil();
                root_2 = (Object)adaptor.becomeRoot(
                (Object)adaptor.create(OUTPUTS, "OUTPUTS")
                , root_2);

                // Lustre.g:75:18: ( $outputs)?
                if ( stream_outputs.hasNext() ) {
                    adaptor.addChild(root_2, stream_outputs.nextTree());

                }
                stream_outputs.reset();

                adaptor.addChild(root_1, root_2);
                }

                // Lustre.g:76:7: ^( LOCALS ( $locals)? )
                {
                Object root_2 = (Object)adaptor.nil();
                root_2 = (Object)adaptor.becomeRoot(
                (Object)adaptor.create(LOCALS, "LOCALS")
                , root_2);

                // Lustre.g:76:17: ( $locals)?
                if ( stream_locals.hasNext() ) {
                    adaptor.addChild(root_2, stream_locals.nextTree());

                }
                stream_locals.reset();

                adaptor.addChild(root_1, root_2);
                }

                // Lustre.g:77:7: ^( EQUATIONS ( equation )* )
                {
                Object root_2 = (Object)adaptor.nil();
                root_2 = (Object)adaptor.becomeRoot(
                (Object)adaptor.create(EQUATIONS, "EQUATIONS")
                , root_2);

                // Lustre.g:77:19: ( equation )*
                while ( stream_equation.hasNext() ) {
                    adaptor.addChild(root_2, stream_equation.nextTree());

                }
                stream_equation.reset();

                adaptor.addChild(root_1, root_2);
                }

                // Lustre.g:78:7: ^( PROPERTIES ( property )* )
                {
                Object root_2 = (Object)adaptor.nil();
                root_2 = (Object)adaptor.becomeRoot(
                (Object)adaptor.create(PROPERTIES, "PROPERTIES")
                , root_2);

                // Lustre.g:78:20: ( property )*
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
    // Lustre.g:81:1: varDeclList : varDeclGroup ( ';' varDeclGroup )* -> ( varDeclGroup )+ ;
    public final LustreParser.varDeclList_return varDeclList() throws RecognitionException {
        LustreParser.varDeclList_return retval = new LustreParser.varDeclList_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token char_literal31=null;
        LustreParser.varDeclGroup_return varDeclGroup30 =null;

        LustreParser.varDeclGroup_return varDeclGroup32 =null;


        Object char_literal31_tree=null;
        RewriteRuleTokenStream stream_38=new RewriteRuleTokenStream(adaptor,"token 38");
        RewriteRuleSubtreeStream stream_varDeclGroup=new RewriteRuleSubtreeStream(adaptor,"rule varDeclGroup");
        try {
            // Lustre.g:81:12: ( varDeclGroup ( ';' varDeclGroup )* -> ( varDeclGroup )+ )
            // Lustre.g:82:3: varDeclGroup ( ';' varDeclGroup )*
            {
            pushFollow(FOLLOW_varDeclGroup_in_varDeclList408);
            varDeclGroup30=varDeclGroup();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_varDeclGroup.add(varDeclGroup30.getTree());

            // Lustre.g:82:16: ( ';' varDeclGroup )*
            loop6:
            do {
                int alt6=2;
                int LA6_0 = input.LA(1);

                if ( (LA6_0==38) ) {
                    int LA6_2 = input.LA(2);

                    if ( (LA6_2==ID) ) {
                        alt6=1;
                    }


                }


                switch (alt6) {
            	case 1 :
            	    // Lustre.g:82:17: ';' varDeclGroup
            	    {
            	    char_literal31=(Token)match(input,38,FOLLOW_38_in_varDeclList411); if (state.failed) return retval; 
            	    if ( state.backtracking==0 ) stream_38.add(char_literal31);


            	    pushFollow(FOLLOW_varDeclGroup_in_varDeclList413);
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
            // 82:36: -> ( varDeclGroup )+
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
    // Lustre.g:85:1: varDeclGroup : ID ( ',' ID )* ':' type -> ( ^( ID type ) )* ;
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
        RewriteRuleTokenStream stream_32=new RewriteRuleTokenStream(adaptor,"token 32");
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleTokenStream stream_37=new RewriteRuleTokenStream(adaptor,"token 37");
        RewriteRuleSubtreeStream stream_type=new RewriteRuleSubtreeStream(adaptor,"rule type");
        try {
            // Lustre.g:85:13: ( ID ( ',' ID )* ':' type -> ( ^( ID type ) )* )
            // Lustre.g:86:3: ID ( ',' ID )* ':' type
            {
            ID33=(Token)match(input,ID,FOLLOW_ID_in_varDeclGroup430); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_ID.add(ID33);


            // Lustre.g:86:6: ( ',' ID )*
            loop7:
            do {
                int alt7=2;
                int LA7_0 = input.LA(1);

                if ( (LA7_0==32) ) {
                    alt7=1;
                }


                switch (alt7) {
            	case 1 :
            	    // Lustre.g:86:7: ',' ID
            	    {
            	    char_literal34=(Token)match(input,32,FOLLOW_32_in_varDeclGroup433); if (state.failed) return retval; 
            	    if ( state.backtracking==0 ) stream_32.add(char_literal34);


            	    ID35=(Token)match(input,ID,FOLLOW_ID_in_varDeclGroup435); if (state.failed) return retval; 
            	    if ( state.backtracking==0 ) stream_ID.add(ID35);


            	    }
            	    break;

            	default :
            	    break loop7;
                }
            } while (true);


            char_literal36=(Token)match(input,37,FOLLOW_37_in_varDeclGroup439); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_37.add(char_literal36);


            pushFollow(FOLLOW_type_in_varDeclGroup441);
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
            // 86:25: -> ( ^( ID type ) )*
            {
                // Lustre.g:86:28: ( ^( ID type ) )*
                while ( stream_type.hasNext()||stream_ID.hasNext() ) {
                    // Lustre.g:86:28: ^( ID type )
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
    // Lustre.g:89:1: type : ( 'int' ^| 'subrange' '[' bound ',' bound ']' 'of' 'int' -> 'int' | 'bool' ^| 'real' ^| ID ^);
    public final LustreParser.type_return type() throws RecognitionException {
        LustreParser.type_return retval = new LustreParser.type_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token string_literal38=null;
        Token string_literal39=null;
        Token char_literal40=null;
        Token char_literal42=null;
        Token char_literal44=null;
        Token string_literal45=null;
        Token string_literal46=null;
        Token string_literal47=null;
        Token string_literal48=null;
        Token ID49=null;
        LustreParser.bound_return bound41 =null;

        LustreParser.bound_return bound43 =null;


        Object string_literal38_tree=null;
        Object string_literal39_tree=null;
        Object char_literal40_tree=null;
        Object char_literal42_tree=null;
        Object char_literal44_tree=null;
        Object string_literal45_tree=null;
        Object string_literal46_tree=null;
        Object string_literal47_tree=null;
        Object string_literal48_tree=null;
        Object ID49_tree=null;
        RewriteRuleTokenStream stream_47=new RewriteRuleTokenStream(adaptor,"token 47");
        RewriteRuleTokenStream stream_32=new RewriteRuleTokenStream(adaptor,"token 32");
        RewriteRuleTokenStream stream_56=new RewriteRuleTokenStream(adaptor,"token 56");
        RewriteRuleTokenStream stream_46=new RewriteRuleTokenStream(adaptor,"token 46");
        RewriteRuleTokenStream stream_53=new RewriteRuleTokenStream(adaptor,"token 53");
        RewriteRuleTokenStream stream_60=new RewriteRuleTokenStream(adaptor,"token 60");
        RewriteRuleSubtreeStream stream_bound=new RewriteRuleSubtreeStream(adaptor,"rule bound");
        try {
            // Lustre.g:89:5: ( 'int' ^| 'subrange' '[' bound ',' bound ']' 'of' 'int' -> 'int' | 'bool' ^| 'real' ^| ID ^)
            int alt8=5;
            switch ( input.LA(1) ) {
            case 53:
                {
                alt8=1;
                }
                break;
            case 60:
                {
                alt8=2;
                }
                break;
            case 49:
                {
                alt8=3;
                }
                break;
            case 58:
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
                    // Lustre.g:90:3: 'int' ^
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal38=(Token)match(input,53,FOLLOW_53_in_type460); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal38_tree = 
                    (Object)adaptor.create(string_literal38)
                    ;
                    root_0 = (Object)adaptor.becomeRoot(string_literal38_tree, root_0);
                    }

                    }
                    break;
                case 2 :
                    // Lustre.g:91:3: 'subrange' '[' bound ',' bound ']' 'of' 'int'
                    {
                    string_literal39=(Token)match(input,60,FOLLOW_60_in_type465); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_60.add(string_literal39);


                    char_literal40=(Token)match(input,46,FOLLOW_46_in_type467); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_46.add(char_literal40);


                    pushFollow(FOLLOW_bound_in_type469);
                    bound41=bound();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_bound.add(bound41.getTree());

                    char_literal42=(Token)match(input,32,FOLLOW_32_in_type471); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_32.add(char_literal42);


                    pushFollow(FOLLOW_bound_in_type473);
                    bound43=bound();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_bound.add(bound43.getTree());

                    char_literal44=(Token)match(input,47,FOLLOW_47_in_type475); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_47.add(char_literal44);


                    string_literal45=(Token)match(input,56,FOLLOW_56_in_type477); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_56.add(string_literal45);


                    string_literal46=(Token)match(input,53,FOLLOW_53_in_type479); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_53.add(string_literal46);


                    // AST REWRITE
                    // elements: 53
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {

                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 91:49: -> 'int'
                    {
                        adaptor.addChild(root_0, 
                        stream_53.nextNode()
                        );

                    }


                    retval.tree = root_0;
                    }

                    }
                    break;
                case 3 :
                    // Lustre.g:92:3: 'bool' ^
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal47=(Token)match(input,49,FOLLOW_49_in_type487); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal47_tree = 
                    (Object)adaptor.create(string_literal47)
                    ;
                    root_0 = (Object)adaptor.becomeRoot(string_literal47_tree, root_0);
                    }

                    }
                    break;
                case 4 :
                    // Lustre.g:93:3: 'real' ^
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal48=(Token)match(input,58,FOLLOW_58_in_type492); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal48_tree = 
                    (Object)adaptor.create(string_literal48)
                    ;
                    root_0 = (Object)adaptor.becomeRoot(string_literal48_tree, root_0);
                    }

                    }
                    break;
                case 5 :
                    // Lustre.g:94:3: ID ^
                    {
                    root_0 = (Object)adaptor.nil();


                    ID49=(Token)match(input,ID,FOLLOW_ID_in_type497); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    ID49_tree = 
                    (Object)adaptor.create(ID49)
                    ;
                    root_0 = (Object)adaptor.becomeRoot(ID49_tree, root_0);
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
    // Lustre.g:97:1: bound : ( '-' )? INT ;
    public final LustreParser.bound_return bound() throws RecognitionException {
        LustreParser.bound_return retval = new LustreParser.bound_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token char_literal50=null;
        Token INT51=null;

        Object char_literal50_tree=null;
        Object INT51_tree=null;

        try {
            // Lustre.g:97:6: ( ( '-' )? INT )
            // Lustre.g:98:3: ( '-' )? INT
            {
            root_0 = (Object)adaptor.nil();


            // Lustre.g:98:3: ( '-' )?
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( (LA9_0==33) ) {
                alt9=1;
            }
            switch (alt9) {
                case 1 :
                    // Lustre.g:98:3: '-'
                    {
                    char_literal50=(Token)match(input,33,FOLLOW_33_in_bound508); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal50_tree = 
                    (Object)adaptor.create(char_literal50)
                    ;
                    adaptor.addChild(root_0, char_literal50_tree);
                    }

                    }
                    break;

            }


            INT51=(Token)match(input,INT,FOLLOW_INT_in_bound511); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            INT51_tree = 
            (Object)adaptor.create(INT51)
            ;
            adaptor.addChild(root_0, INT51_tree);
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
    // Lustre.g:101:1: property : '--%PROPERTY' ID ';' -> ID ;
    public final LustreParser.property_return property() throws RecognitionException {
        LustreParser.property_return retval = new LustreParser.property_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token string_literal52=null;
        Token ID53=null;
        Token char_literal54=null;

        Object string_literal52_tree=null;
        Object ID53_tree=null;
        Object char_literal54_tree=null;
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleTokenStream stream_34=new RewriteRuleTokenStream(adaptor,"token 34");
        RewriteRuleTokenStream stream_38=new RewriteRuleTokenStream(adaptor,"token 38");

        try {
            // Lustre.g:101:9: ( '--%PROPERTY' ID ';' -> ID )
            // Lustre.g:102:3: '--%PROPERTY' ID ';'
            {
            string_literal52=(Token)match(input,34,FOLLOW_34_in_property521); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_34.add(string_literal52);


            ID53=(Token)match(input,ID,FOLLOW_ID_in_property523); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_ID.add(ID53);


            char_literal54=(Token)match(input,38,FOLLOW_38_in_property525); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_38.add(char_literal54);


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
            // 102:24: -> ID
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
    // Lustre.g:105:1: equation : ID '=' expr ';' -> ^( ID expr ) ;
    public final LustreParser.equation_return equation() throws RecognitionException {
        LustreParser.equation_return retval = new LustreParser.equation_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token ID55=null;
        Token char_literal56=null;
        Token char_literal58=null;
        LustreParser.expr_return expr57 =null;


        Object ID55_tree=null;
        Object char_literal56_tree=null;
        Object char_literal58_tree=null;
        RewriteRuleTokenStream stream_42=new RewriteRuleTokenStream(adaptor,"token 42");
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleTokenStream stream_38=new RewriteRuleTokenStream(adaptor,"token 38");
        RewriteRuleSubtreeStream stream_expr=new RewriteRuleSubtreeStream(adaptor,"rule expr");
        try {
            // Lustre.g:105:9: ( ID '=' expr ';' -> ^( ID expr ) )
            // Lustre.g:106:3: ID '=' expr ';'
            {
            ID55=(Token)match(input,ID,FOLLOW_ID_in_equation539); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_ID.add(ID55);


            char_literal56=(Token)match(input,42,FOLLOW_42_in_equation541); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_42.add(char_literal56);


            pushFollow(FOLLOW_expr_in_equation543);
            expr57=expr();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_expr.add(expr57.getTree());

            char_literal58=(Token)match(input,38,FOLLOW_38_in_equation545); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_38.add(char_literal58);


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
            // 106:19: -> ^( ID expr )
            {
                // Lustre.g:106:22: ^( ID expr )
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
    // Lustre.g:109:1: expr : arrowExpr ;
    public final LustreParser.expr_return expr() throws RecognitionException {
        LustreParser.expr_return retval = new LustreParser.expr_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        LustreParser.arrowExpr_return arrowExpr59 =null;



        try {
            // Lustre.g:109:5: ( arrowExpr )
            // Lustre.g:110:3: arrowExpr
            {
            root_0 = (Object)adaptor.nil();


            pushFollow(FOLLOW_arrowExpr_in_expr563);
            arrowExpr59=arrowExpr();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, arrowExpr59.getTree());

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
    // Lustre.g:113:1: arrowOp : '->' ;
    public final LustreParser.arrowOp_return arrowOp() throws RecognitionException {
        LustreParser.arrowOp_return retval = new LustreParser.arrowOp_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token string_literal60=null;

        Object string_literal60_tree=null;

        try {
            // Lustre.g:113:8: ( '->' )
            // Lustre.g:114:3: '->'
            {
            root_0 = (Object)adaptor.nil();


            string_literal60=(Token)match(input,35,FOLLOW_35_in_arrowOp573); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            string_literal60_tree = 
            (Object)adaptor.create(string_literal60)
            ;
            adaptor.addChild(root_0, string_literal60_tree);
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
    // Lustre.g:117:1: arrowExpr : impliesExpr ( ( arrowOp )=> arrowOp ^ arrowExpr )? ;
    public final LustreParser.arrowExpr_return arrowExpr() throws RecognitionException {
        LustreParser.arrowExpr_return retval = new LustreParser.arrowExpr_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        LustreParser.impliesExpr_return impliesExpr61 =null;

        LustreParser.arrowOp_return arrowOp62 =null;

        LustreParser.arrowExpr_return arrowExpr63 =null;



        try {
            // Lustre.g:117:10: ( impliesExpr ( ( arrowOp )=> arrowOp ^ arrowExpr )? )
            // Lustre.g:118:3: impliesExpr ( ( arrowOp )=> arrowOp ^ arrowExpr )?
            {
            root_0 = (Object)adaptor.nil();


            pushFollow(FOLLOW_impliesExpr_in_arrowExpr583);
            impliesExpr61=impliesExpr();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, impliesExpr61.getTree());

            // Lustre.g:118:15: ( ( arrowOp )=> arrowOp ^ arrowExpr )?
            int alt10=2;
            int LA10_0 = input.LA(1);

            if ( (LA10_0==35) ) {
                int LA10_1 = input.LA(2);

                if ( (synpred1_Lustre()) ) {
                    alt10=1;
                }
            }
            switch (alt10) {
                case 1 :
                    // Lustre.g:118:16: ( arrowOp )=> arrowOp ^ arrowExpr
                    {
                    pushFollow(FOLLOW_arrowOp_in_arrowExpr590);
                    arrowOp62=arrowOp();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) root_0 = (Object)adaptor.becomeRoot(arrowOp62.getTree(), root_0);

                    pushFollow(FOLLOW_arrowExpr_in_arrowExpr593);
                    arrowExpr63=arrowExpr();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, arrowExpr63.getTree());

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
    // Lustre.g:121:1: impliesOp : '=>' ;
    public final LustreParser.impliesOp_return impliesOp() throws RecognitionException {
        LustreParser.impliesOp_return retval = new LustreParser.impliesOp_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token string_literal64=null;

        Object string_literal64_tree=null;

        try {
            // Lustre.g:121:10: ( '=>' )
            // Lustre.g:122:3: '=>'
            {
            root_0 = (Object)adaptor.nil();


            string_literal64=(Token)match(input,43,FOLLOW_43_in_impliesOp605); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            string_literal64_tree = 
            (Object)adaptor.create(string_literal64)
            ;
            adaptor.addChild(root_0, string_literal64_tree);
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
    // Lustre.g:125:1: impliesExpr : orExpr ( ( impliesOp )=> impliesOp ^ impliesExpr )? ;
    public final LustreParser.impliesExpr_return impliesExpr() throws RecognitionException {
        LustreParser.impliesExpr_return retval = new LustreParser.impliesExpr_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        LustreParser.orExpr_return orExpr65 =null;

        LustreParser.impliesOp_return impliesOp66 =null;

        LustreParser.impliesExpr_return impliesExpr67 =null;



        try {
            // Lustre.g:125:12: ( orExpr ( ( impliesOp )=> impliesOp ^ impliesExpr )? )
            // Lustre.g:126:3: orExpr ( ( impliesOp )=> impliesOp ^ impliesExpr )?
            {
            root_0 = (Object)adaptor.nil();


            pushFollow(FOLLOW_orExpr_in_impliesExpr615);
            orExpr65=orExpr();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, orExpr65.getTree());

            // Lustre.g:126:10: ( ( impliesOp )=> impliesOp ^ impliesExpr )?
            int alt11=2;
            int LA11_0 = input.LA(1);

            if ( (LA11_0==43) ) {
                int LA11_1 = input.LA(2);

                if ( (synpred2_Lustre()) ) {
                    alt11=1;
                }
            }
            switch (alt11) {
                case 1 :
                    // Lustre.g:126:11: ( impliesOp )=> impliesOp ^ impliesExpr
                    {
                    pushFollow(FOLLOW_impliesOp_in_impliesExpr622);
                    impliesOp66=impliesOp();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) root_0 = (Object)adaptor.becomeRoot(impliesOp66.getTree(), root_0);

                    pushFollow(FOLLOW_impliesExpr_in_impliesExpr625);
                    impliesExpr67=impliesExpr();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, impliesExpr67.getTree());

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
    // Lustre.g:129:1: orOp : ( 'or' | 'xor' );
    public final LustreParser.orOp_return orOp() throws RecognitionException {
        LustreParser.orOp_return retval = new LustreParser.orOp_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token set68=null;

        Object set68_tree=null;

        try {
            // Lustre.g:129:5: ( 'or' | 'xor' )
            // Lustre.g:
            {
            root_0 = (Object)adaptor.nil();


            set68=(Token)input.LT(1);

            if ( input.LA(1)==57||input.LA(1)==65 ) {
                input.consume();
                if ( state.backtracking==0 ) adaptor.addChild(root_0, 
                (Object)adaptor.create(set68)
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
    // Lustre.g:133:1: orExpr : andExpr ( ( orOp )=> orOp ^ andExpr )* ;
    public final LustreParser.orExpr_return orExpr() throws RecognitionException {
        LustreParser.orExpr_return retval = new LustreParser.orExpr_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        LustreParser.andExpr_return andExpr69 =null;

        LustreParser.orOp_return orOp70 =null;

        LustreParser.andExpr_return andExpr71 =null;



        try {
            // Lustre.g:133:7: ( andExpr ( ( orOp )=> orOp ^ andExpr )* )
            // Lustre.g:134:3: andExpr ( ( orOp )=> orOp ^ andExpr )*
            {
            root_0 = (Object)adaptor.nil();


            pushFollow(FOLLOW_andExpr_in_orExpr651);
            andExpr69=andExpr();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, andExpr69.getTree());

            // Lustre.g:134:11: ( ( orOp )=> orOp ^ andExpr )*
            loop12:
            do {
                int alt12=2;
                int LA12_0 = input.LA(1);

                if ( (LA12_0==57||LA12_0==65) ) {
                    int LA12_2 = input.LA(2);

                    if ( (synpred3_Lustre()) ) {
                        alt12=1;
                    }


                }


                switch (alt12) {
            	case 1 :
            	    // Lustre.g:134:12: ( orOp )=> orOp ^ andExpr
            	    {
            	    pushFollow(FOLLOW_orOp_in_orExpr658);
            	    orOp70=orOp();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) root_0 = (Object)adaptor.becomeRoot(orOp70.getTree(), root_0);

            	    pushFollow(FOLLOW_andExpr_in_orExpr661);
            	    andExpr71=andExpr();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, andExpr71.getTree());

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
    // Lustre.g:137:1: andOp : 'and' ;
    public final LustreParser.andOp_return andOp() throws RecognitionException {
        LustreParser.andOp_return retval = new LustreParser.andOp_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token string_literal72=null;

        Object string_literal72_tree=null;

        try {
            // Lustre.g:137:6: ( 'and' )
            // Lustre.g:138:3: 'and'
            {
            root_0 = (Object)adaptor.nil();


            string_literal72=(Token)match(input,48,FOLLOW_48_in_andOp673); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            string_literal72_tree = 
            (Object)adaptor.create(string_literal72)
            ;
            adaptor.addChild(root_0, string_literal72_tree);
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
    // Lustre.g:141:1: andExpr : relationalExpr ( ( andOp )=> andOp ^ relationalExpr )* ;
    public final LustreParser.andExpr_return andExpr() throws RecognitionException {
        LustreParser.andExpr_return retval = new LustreParser.andExpr_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        LustreParser.relationalExpr_return relationalExpr73 =null;

        LustreParser.andOp_return andOp74 =null;

        LustreParser.relationalExpr_return relationalExpr75 =null;



        try {
            // Lustre.g:141:8: ( relationalExpr ( ( andOp )=> andOp ^ relationalExpr )* )
            // Lustre.g:142:3: relationalExpr ( ( andOp )=> andOp ^ relationalExpr )*
            {
            root_0 = (Object)adaptor.nil();


            pushFollow(FOLLOW_relationalExpr_in_andExpr683);
            relationalExpr73=relationalExpr();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, relationalExpr73.getTree());

            // Lustre.g:142:18: ( ( andOp )=> andOp ^ relationalExpr )*
            loop13:
            do {
                int alt13=2;
                int LA13_0 = input.LA(1);

                if ( (LA13_0==48) ) {
                    int LA13_2 = input.LA(2);

                    if ( (synpred4_Lustre()) ) {
                        alt13=1;
                    }


                }


                switch (alt13) {
            	case 1 :
            	    // Lustre.g:142:19: ( andOp )=> andOp ^ relationalExpr
            	    {
            	    pushFollow(FOLLOW_andOp_in_andExpr690);
            	    andOp74=andOp();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) root_0 = (Object)adaptor.becomeRoot(andOp74.getTree(), root_0);

            	    pushFollow(FOLLOW_relationalExpr_in_andExpr693);
            	    relationalExpr75=relationalExpr();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, relationalExpr75.getTree());

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
    // Lustre.g:145:1: relationalOp : ( '<' | '<=' | '>' | '>=' | '=' | '<>' );
    public final LustreParser.relationalOp_return relationalOp() throws RecognitionException {
        LustreParser.relationalOp_return retval = new LustreParser.relationalOp_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token set76=null;

        Object set76_tree=null;

        try {
            // Lustre.g:145:13: ( '<' | '<=' | '>' | '>=' | '=' | '<>' )
            // Lustre.g:
            {
            root_0 = (Object)adaptor.nil();


            set76=(Token)input.LT(1);

            if ( (input.LA(1) >= 39 && input.LA(1) <= 42)||(input.LA(1) >= 44 && input.LA(1) <= 45) ) {
                input.consume();
                if ( state.backtracking==0 ) adaptor.addChild(root_0, 
                (Object)adaptor.create(set76)
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
    // Lustre.g:149:1: relationalExpr : plusExpr ( ( relationalOp )=> relationalOp ^ plusExpr )? ;
    public final LustreParser.relationalExpr_return relationalExpr() throws RecognitionException {
        LustreParser.relationalExpr_return retval = new LustreParser.relationalExpr_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        LustreParser.plusExpr_return plusExpr77 =null;

        LustreParser.relationalOp_return relationalOp78 =null;

        LustreParser.plusExpr_return plusExpr79 =null;



        try {
            // Lustre.g:149:15: ( plusExpr ( ( relationalOp )=> relationalOp ^ plusExpr )? )
            // Lustre.g:150:3: plusExpr ( ( relationalOp )=> relationalOp ^ plusExpr )?
            {
            root_0 = (Object)adaptor.nil();


            pushFollow(FOLLOW_plusExpr_in_relationalExpr735);
            plusExpr77=plusExpr();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, plusExpr77.getTree());

            // Lustre.g:150:12: ( ( relationalOp )=> relationalOp ^ plusExpr )?
            int alt14=2;
            int LA14_0 = input.LA(1);

            if ( ((LA14_0 >= 39 && LA14_0 <= 42)||(LA14_0 >= 44 && LA14_0 <= 45)) ) {
                int LA14_1 = input.LA(2);

                if ( (synpred5_Lustre()) ) {
                    alt14=1;
                }
            }
            switch (alt14) {
                case 1 :
                    // Lustre.g:150:13: ( relationalOp )=> relationalOp ^ plusExpr
                    {
                    pushFollow(FOLLOW_relationalOp_in_relationalExpr742);
                    relationalOp78=relationalOp();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) root_0 = (Object)adaptor.becomeRoot(relationalOp78.getTree(), root_0);

                    pushFollow(FOLLOW_plusExpr_in_relationalExpr745);
                    plusExpr79=plusExpr();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, plusExpr79.getTree());

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
    // Lustre.g:153:1: plusOp : ( '+' | '-' );
    public final LustreParser.plusOp_return plusOp() throws RecognitionException {
        LustreParser.plusOp_return retval = new LustreParser.plusOp_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token set80=null;

        Object set80_tree=null;

        try {
            // Lustre.g:153:7: ( '+' | '-' )
            // Lustre.g:
            {
            root_0 = (Object)adaptor.nil();


            set80=(Token)input.LT(1);

            if ( input.LA(1)==31||input.LA(1)==33 ) {
                input.consume();
                if ( state.backtracking==0 ) adaptor.addChild(root_0, 
                (Object)adaptor.create(set80)
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
    // Lustre.g:157:1: plusExpr : timesExpr ( ( plusOp )=> plusOp ^ timesExpr )* ;
    public final LustreParser.plusExpr_return plusExpr() throws RecognitionException {
        LustreParser.plusExpr_return retval = new LustreParser.plusExpr_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        LustreParser.timesExpr_return timesExpr81 =null;

        LustreParser.plusOp_return plusOp82 =null;

        LustreParser.timesExpr_return timesExpr83 =null;



        try {
            // Lustre.g:157:9: ( timesExpr ( ( plusOp )=> plusOp ^ timesExpr )* )
            // Lustre.g:158:3: timesExpr ( ( plusOp )=> plusOp ^ timesExpr )*
            {
            root_0 = (Object)adaptor.nil();


            pushFollow(FOLLOW_timesExpr_in_plusExpr771);
            timesExpr81=timesExpr();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, timesExpr81.getTree());

            // Lustre.g:158:13: ( ( plusOp )=> plusOp ^ timesExpr )*
            loop15:
            do {
                int alt15=2;
                int LA15_0 = input.LA(1);

                if ( (LA15_0==31||LA15_0==33) ) {
                    int LA15_2 = input.LA(2);

                    if ( (synpred6_Lustre()) ) {
                        alt15=1;
                    }


                }


                switch (alt15) {
            	case 1 :
            	    // Lustre.g:158:14: ( plusOp )=> plusOp ^ timesExpr
            	    {
            	    pushFollow(FOLLOW_plusOp_in_plusExpr778);
            	    plusOp82=plusOp();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) root_0 = (Object)adaptor.becomeRoot(plusOp82.getTree(), root_0);

            	    pushFollow(FOLLOW_timesExpr_in_plusExpr781);
            	    timesExpr83=timesExpr();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, timesExpr83.getTree());

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
    // Lustre.g:161:1: timesOp : ( '*' | '/' | 'div' );
    public final LustreParser.timesOp_return timesOp() throws RecognitionException {
        LustreParser.timesOp_return retval = new LustreParser.timesOp_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token set84=null;

        Object set84_tree=null;

        try {
            // Lustre.g:161:8: ( '*' | '/' | 'div' )
            // Lustre.g:
            {
            root_0 = (Object)adaptor.nil();


            set84=(Token)input.LT(1);

            if ( input.LA(1)==30||input.LA(1)==36||input.LA(1)==51 ) {
                input.consume();
                if ( state.backtracking==0 ) adaptor.addChild(root_0, 
                (Object)adaptor.create(set84)
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
    // Lustre.g:165:1: timesExpr : prefixExpr ( ( timesOp )=> timesOp ^ prefixExpr )* ;
    public final LustreParser.timesExpr_return timesExpr() throws RecognitionException {
        LustreParser.timesExpr_return retval = new LustreParser.timesExpr_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        LustreParser.prefixExpr_return prefixExpr85 =null;

        LustreParser.timesOp_return timesOp86 =null;

        LustreParser.prefixExpr_return prefixExpr87 =null;



        try {
            // Lustre.g:165:10: ( prefixExpr ( ( timesOp )=> timesOp ^ prefixExpr )* )
            // Lustre.g:166:3: prefixExpr ( ( timesOp )=> timesOp ^ prefixExpr )*
            {
            root_0 = (Object)adaptor.nil();


            pushFollow(FOLLOW_prefixExpr_in_timesExpr811);
            prefixExpr85=prefixExpr();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, prefixExpr85.getTree());

            // Lustre.g:166:14: ( ( timesOp )=> timesOp ^ prefixExpr )*
            loop16:
            do {
                int alt16=2;
                int LA16_0 = input.LA(1);

                if ( (LA16_0==30||LA16_0==36||LA16_0==51) ) {
                    int LA16_2 = input.LA(2);

                    if ( (synpred7_Lustre()) ) {
                        alt16=1;
                    }


                }


                switch (alt16) {
            	case 1 :
            	    // Lustre.g:166:15: ( timesOp )=> timesOp ^ prefixExpr
            	    {
            	    pushFollow(FOLLOW_timesOp_in_timesExpr818);
            	    timesOp86=timesOp();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) root_0 = (Object)adaptor.becomeRoot(timesOp86.getTree(), root_0);

            	    pushFollow(FOLLOW_prefixExpr_in_timesExpr821);
            	    prefixExpr87=prefixExpr();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, prefixExpr87.getTree());

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
    // Lustre.g:169:1: prefixExpr : ( '-' prefixExpr -> ^( NEGATE prefixExpr ) | NOT ^ prefixExpr | PRE ^ prefixExpr | atomicExpr );
    public final LustreParser.prefixExpr_return prefixExpr() throws RecognitionException {
        LustreParser.prefixExpr_return retval = new LustreParser.prefixExpr_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token char_literal88=null;
        Token NOT90=null;
        Token PRE92=null;
        LustreParser.prefixExpr_return prefixExpr89 =null;

        LustreParser.prefixExpr_return prefixExpr91 =null;

        LustreParser.prefixExpr_return prefixExpr93 =null;

        LustreParser.atomicExpr_return atomicExpr94 =null;


        Object char_literal88_tree=null;
        Object NOT90_tree=null;
        Object PRE92_tree=null;
        RewriteRuleTokenStream stream_33=new RewriteRuleTokenStream(adaptor,"token 33");
        RewriteRuleSubtreeStream stream_prefixExpr=new RewriteRuleSubtreeStream(adaptor,"rule prefixExpr");
        try {
            // Lustre.g:169:11: ( '-' prefixExpr -> ^( NEGATE prefixExpr ) | NOT ^ prefixExpr | PRE ^ prefixExpr | atomicExpr )
            int alt17=4;
            switch ( input.LA(1) ) {
            case 33:
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
            case 28:
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
                    // Lustre.g:170:3: '-' prefixExpr
                    {
                    char_literal88=(Token)match(input,33,FOLLOW_33_in_prefixExpr833); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_33.add(char_literal88);


                    pushFollow(FOLLOW_prefixExpr_in_prefixExpr835);
                    prefixExpr89=prefixExpr();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_prefixExpr.add(prefixExpr89.getTree());

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
                    // 170:18: -> ^( NEGATE prefixExpr )
                    {
                        // Lustre.g:170:21: ^( NEGATE prefixExpr )
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
                    // Lustre.g:171:3: NOT ^ prefixExpr
                    {
                    root_0 = (Object)adaptor.nil();


                    NOT90=(Token)match(input,NOT,FOLLOW_NOT_in_prefixExpr847); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    NOT90_tree = 
                    (Object)adaptor.create(NOT90)
                    ;
                    root_0 = (Object)adaptor.becomeRoot(NOT90_tree, root_0);
                    }

                    pushFollow(FOLLOW_prefixExpr_in_prefixExpr850);
                    prefixExpr91=prefixExpr();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, prefixExpr91.getTree());

                    }
                    break;
                case 3 :
                    // Lustre.g:172:3: PRE ^ prefixExpr
                    {
                    root_0 = (Object)adaptor.nil();


                    PRE92=(Token)match(input,PRE,FOLLOW_PRE_in_prefixExpr854); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    PRE92_tree = 
                    (Object)adaptor.create(PRE92)
                    ;
                    root_0 = (Object)adaptor.becomeRoot(PRE92_tree, root_0);
                    }

                    pushFollow(FOLLOW_prefixExpr_in_prefixExpr857);
                    prefixExpr93=prefixExpr();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, prefixExpr93.getTree());

                    }
                    break;
                case 4 :
                    // Lustre.g:173:3: atomicExpr
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_atomicExpr_in_prefixExpr861);
                    atomicExpr94=atomicExpr();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, atomicExpr94.getTree());

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
    // Lustre.g:176:1: atomicExpr : ( ID -> ^( IDENT ID ) | INT | real | BOOL | IF ^ expr 'then' ! expr 'else' ! expr | ID '(' ( expr ( ',' expr )* )? ')' -> ^( NODECALL ID ( expr )* ) | '(' expr ')' -> expr );
    public final LustreParser.atomicExpr_return atomicExpr() throws RecognitionException {
        LustreParser.atomicExpr_return retval = new LustreParser.atomicExpr_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token ID95=null;
        Token INT96=null;
        Token BOOL98=null;
        Token IF99=null;
        Token string_literal101=null;
        Token string_literal103=null;
        Token ID105=null;
        Token char_literal106=null;
        Token char_literal108=null;
        Token char_literal110=null;
        Token char_literal111=null;
        Token char_literal113=null;
        LustreParser.real_return real97 =null;

        LustreParser.expr_return expr100 =null;

        LustreParser.expr_return expr102 =null;

        LustreParser.expr_return expr104 =null;

        LustreParser.expr_return expr107 =null;

        LustreParser.expr_return expr109 =null;

        LustreParser.expr_return expr112 =null;


        Object ID95_tree=null;
        Object INT96_tree=null;
        Object BOOL98_tree=null;
        Object IF99_tree=null;
        Object string_literal101_tree=null;
        Object string_literal103_tree=null;
        Object ID105_tree=null;
        Object char_literal106_tree=null;
        Object char_literal108_tree=null;
        Object char_literal110_tree=null;
        Object char_literal111_tree=null;
        Object char_literal113_tree=null;
        RewriteRuleTokenStream stream_32=new RewriteRuleTokenStream(adaptor,"token 32");
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleTokenStream stream_28=new RewriteRuleTokenStream(adaptor,"token 28");
        RewriteRuleTokenStream stream_29=new RewriteRuleTokenStream(adaptor,"token 29");
        RewriteRuleSubtreeStream stream_expr=new RewriteRuleSubtreeStream(adaptor,"rule expr");
        try {
            // Lustre.g:176:11: ( ID -> ^( IDENT ID ) | INT | real | BOOL | IF ^ expr 'then' ! expr 'else' ! expr | ID '(' ( expr ( ',' expr )* )? ')' -> ^( NODECALL ID ( expr )* ) | '(' expr ')' -> expr )
            int alt20=7;
            switch ( input.LA(1) ) {
            case ID:
                {
                int LA20_1 = input.LA(2);

                if ( (LA20_1==28) ) {
                    alt20=6;
                }
                else if ( ((LA20_1 >= 29 && LA20_1 <= 33)||(LA20_1 >= 35 && LA20_1 <= 36)||(LA20_1 >= 38 && LA20_1 <= 45)||LA20_1==48||(LA20_1 >= 51 && LA20_1 <= 52)||LA20_1==57||LA20_1==62||LA20_1==65) ) {
                    alt20=1;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 20, 1, input);

                    throw nvae;

                }
                }
                break;
            case INT:
                {
                int LA20_2 = input.LA(2);

                if ( (LA20_2==ERROR) ) {
                    alt20=3;
                }
                else if ( ((LA20_2 >= 29 && LA20_2 <= 33)||(LA20_2 >= 35 && LA20_2 <= 36)||(LA20_2 >= 38 && LA20_2 <= 45)||LA20_2==48||(LA20_2 >= 51 && LA20_2 <= 52)||LA20_2==57||LA20_2==62||LA20_2==65) ) {
                    alt20=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 20, 2, input);

                    throw nvae;

                }
                }
                break;
            case BOOL:
                {
                alt20=4;
                }
                break;
            case IF:
                {
                alt20=5;
                }
                break;
            case 28:
                {
                alt20=7;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 20, 0, input);

                throw nvae;

            }

            switch (alt20) {
                case 1 :
                    // Lustre.g:177:3: ID
                    {
                    ID95=(Token)match(input,ID,FOLLOW_ID_in_atomicExpr871); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_ID.add(ID95);


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
                    // 177:6: -> ^( IDENT ID )
                    {
                        // Lustre.g:177:9: ^( IDENT ID )
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
                    // Lustre.g:178:3: INT
                    {
                    root_0 = (Object)adaptor.nil();


                    INT96=(Token)match(input,INT,FOLLOW_INT_in_atomicExpr883); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    INT96_tree = 
                    (Object)adaptor.create(INT96)
                    ;
                    adaptor.addChild(root_0, INT96_tree);
                    }

                    }
                    break;
                case 3 :
                    // Lustre.g:179:3: real
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_real_in_atomicExpr887);
                    real97=real();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, real97.getTree());

                    }
                    break;
                case 4 :
                    // Lustre.g:180:3: BOOL
                    {
                    root_0 = (Object)adaptor.nil();


                    BOOL98=(Token)match(input,BOOL,FOLLOW_BOOL_in_atomicExpr891); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    BOOL98_tree = 
                    (Object)adaptor.create(BOOL98)
                    ;
                    adaptor.addChild(root_0, BOOL98_tree);
                    }

                    }
                    break;
                case 5 :
                    // Lustre.g:181:3: IF ^ expr 'then' ! expr 'else' ! expr
                    {
                    root_0 = (Object)adaptor.nil();


                    IF99=(Token)match(input,IF,FOLLOW_IF_in_atomicExpr895); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    IF99_tree = 
                    (Object)adaptor.create(IF99)
                    ;
                    root_0 = (Object)adaptor.becomeRoot(IF99_tree, root_0);
                    }

                    pushFollow(FOLLOW_expr_in_atomicExpr898);
                    expr100=expr();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, expr100.getTree());

                    string_literal101=(Token)match(input,62,FOLLOW_62_in_atomicExpr900); if (state.failed) return retval;

                    pushFollow(FOLLOW_expr_in_atomicExpr903);
                    expr102=expr();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, expr102.getTree());

                    string_literal103=(Token)match(input,52,FOLLOW_52_in_atomicExpr905); if (state.failed) return retval;

                    pushFollow(FOLLOW_expr_in_atomicExpr908);
                    expr104=expr();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, expr104.getTree());

                    }
                    break;
                case 6 :
                    // Lustre.g:182:3: ID '(' ( expr ( ',' expr )* )? ')'
                    {
                    ID105=(Token)match(input,ID,FOLLOW_ID_in_atomicExpr912); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_ID.add(ID105);


                    char_literal106=(Token)match(input,28,FOLLOW_28_in_atomicExpr914); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_28.add(char_literal106);


                    // Lustre.g:182:10: ( expr ( ',' expr )* )?
                    int alt19=2;
                    int LA19_0 = input.LA(1);

                    if ( (LA19_0==BOOL||LA19_0==ID||LA19_0==IF||LA19_0==INT||LA19_0==NOT||LA19_0==PRE||LA19_0==28||LA19_0==33) ) {
                        alt19=1;
                    }
                    switch (alt19) {
                        case 1 :
                            // Lustre.g:182:11: expr ( ',' expr )*
                            {
                            pushFollow(FOLLOW_expr_in_atomicExpr917);
                            expr107=expr();

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) stream_expr.add(expr107.getTree());

                            // Lustre.g:182:16: ( ',' expr )*
                            loop18:
                            do {
                                int alt18=2;
                                int LA18_0 = input.LA(1);

                                if ( (LA18_0==32) ) {
                                    alt18=1;
                                }


                                switch (alt18) {
                            	case 1 :
                            	    // Lustre.g:182:17: ',' expr
                            	    {
                            	    char_literal108=(Token)match(input,32,FOLLOW_32_in_atomicExpr920); if (state.failed) return retval; 
                            	    if ( state.backtracking==0 ) stream_32.add(char_literal108);


                            	    pushFollow(FOLLOW_expr_in_atomicExpr922);
                            	    expr109=expr();

                            	    state._fsp--;
                            	    if (state.failed) return retval;
                            	    if ( state.backtracking==0 ) stream_expr.add(expr109.getTree());

                            	    }
                            	    break;

                            	default :
                            	    break loop18;
                                }
                            } while (true);


                            }
                            break;

                    }


                    char_literal110=(Token)match(input,29,FOLLOW_29_in_atomicExpr928); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_29.add(char_literal110);


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
                    // 182:34: -> ^( NODECALL ID ( expr )* )
                    {
                        // Lustre.g:182:37: ^( NODECALL ID ( expr )* )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot(
                        (Object)adaptor.create(NODECALL, "NODECALL")
                        , root_1);

                        adaptor.addChild(root_1, 
                        stream_ID.nextNode()
                        );

                        // Lustre.g:182:51: ( expr )*
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
                    // Lustre.g:183:3: '(' expr ')'
                    {
                    char_literal111=(Token)match(input,28,FOLLOW_28_in_atomicExpr943); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_28.add(char_literal111);


                    pushFollow(FOLLOW_expr_in_atomicExpr945);
                    expr112=expr();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_expr.add(expr112.getTree());

                    char_literal113=(Token)match(input,29,FOLLOW_29_in_atomicExpr947); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_29.add(char_literal113);


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
                    // 183:16: -> expr
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
    // Lustre.g:186:1: real : a= INT '.' b= INT ->;
    public final LustreParser.real_return real() throws RecognitionException {
        LustreParser.real_return retval = new LustreParser.real_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token a=null;
        Token b=null;
        Token char_literal114=null;

        Object a_tree=null;
        Object b_tree=null;
        Object char_literal114_tree=null;
        RewriteRuleTokenStream stream_INT=new RewriteRuleTokenStream(adaptor,"token INT");
        RewriteRuleTokenStream stream_ERROR=new RewriteRuleTokenStream(adaptor,"token ERROR");

        try {
            // Lustre.g:186:5: (a= INT '.' b= INT ->)
            // Lustre.g:186:7: a= INT '.' b= INT
            {
            a=(Token)match(input,INT,FOLLOW_INT_in_real961); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_INT.add(a);


            char_literal114=(Token)match(input,ERROR,FOLLOW_ERROR_in_real963); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_ERROR.add(char_literal114);


            b=(Token)match(input,INT,FOLLOW_INT_in_real967); if (state.failed) return retval; 
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
            // 186:23: ->
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
        // Lustre.g:118:16: ( arrowOp )
        // Lustre.g:118:17: arrowOp
        {
        pushFollow(FOLLOW_arrowOp_in_synpred1_Lustre587);
        arrowOp();

        state._fsp--;
        if (state.failed) return ;

        }

    }
    // $ANTLR end synpred1_Lustre

    // $ANTLR start synpred2_Lustre
    public final void synpred2_Lustre_fragment() throws RecognitionException {
        // Lustre.g:126:11: ( impliesOp )
        // Lustre.g:126:12: impliesOp
        {
        pushFollow(FOLLOW_impliesOp_in_synpred2_Lustre619);
        impliesOp();

        state._fsp--;
        if (state.failed) return ;

        }

    }
    // $ANTLR end synpred2_Lustre

    // $ANTLR start synpred3_Lustre
    public final void synpred3_Lustre_fragment() throws RecognitionException {
        // Lustre.g:134:12: ( orOp )
        // Lustre.g:134:13: orOp
        {
        pushFollow(FOLLOW_orOp_in_synpred3_Lustre655);
        orOp();

        state._fsp--;
        if (state.failed) return ;

        }

    }
    // $ANTLR end synpred3_Lustre

    // $ANTLR start synpred4_Lustre
    public final void synpred4_Lustre_fragment() throws RecognitionException {
        // Lustre.g:142:19: ( andOp )
        // Lustre.g:142:20: andOp
        {
        pushFollow(FOLLOW_andOp_in_synpred4_Lustre687);
        andOp();

        state._fsp--;
        if (state.failed) return ;

        }

    }
    // $ANTLR end synpred4_Lustre

    // $ANTLR start synpred5_Lustre
    public final void synpred5_Lustre_fragment() throws RecognitionException {
        // Lustre.g:150:13: ( relationalOp )
        // Lustre.g:150:14: relationalOp
        {
        pushFollow(FOLLOW_relationalOp_in_synpred5_Lustre739);
        relationalOp();

        state._fsp--;
        if (state.failed) return ;

        }

    }
    // $ANTLR end synpred5_Lustre

    // $ANTLR start synpred6_Lustre
    public final void synpred6_Lustre_fragment() throws RecognitionException {
        // Lustre.g:158:14: ( plusOp )
        // Lustre.g:158:15: plusOp
        {
        pushFollow(FOLLOW_plusOp_in_synpred6_Lustre775);
        plusOp();

        state._fsp--;
        if (state.failed) return ;

        }

    }
    // $ANTLR end synpred6_Lustre

    // $ANTLR start synpred7_Lustre
    public final void synpred7_Lustre_fragment() throws RecognitionException {
        // Lustre.g:166:15: ( timesOp )
        // Lustre.g:166:16: timesOp
        {
        pushFollow(FOLLOW_timesOp_in_synpred7_Lustre815);
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


 

    public static final BitSet FOLLOW_typedef_in_program135 = new BitSet(new long[]{0x8084000000000000L});
    public static final BitSet FOLLOW_constant_in_program139 = new BitSet(new long[]{0x8084000000000000L});
    public static final BitSet FOLLOW_node_in_program143 = new BitSet(new long[]{0x8084000000000000L});
    public static final BitSet FOLLOW_EOF_in_program147 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_63_in_typedef206 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_ID_in_typedef208 = new BitSet(new long[]{0x0000040000000000L});
    public static final BitSet FOLLOW_42_in_typedef210 = new BitSet(new long[]{0x1422000000000100L});
    public static final BitSet FOLLOW_type_in_typedef212 = new BitSet(new long[]{0x0000004000000000L});
    public static final BitSet FOLLOW_38_in_typedef214 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_50_in_constant232 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_ID_in_constant234 = new BitSet(new long[]{0x0000040000000000L});
    public static final BitSet FOLLOW_42_in_constant236 = new BitSet(new long[]{0x0000000210281510L});
    public static final BitSet FOLLOW_expr_in_constant238 = new BitSet(new long[]{0x0000004000000000L});
    public static final BitSet FOLLOW_38_in_constant240 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_55_in_node258 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_ID_in_node260 = new BitSet(new long[]{0x0000000010000000L});
    public static final BitSet FOLLOW_28_in_node262 = new BitSet(new long[]{0x0000000020000100L});
    public static final BitSet FOLLOW_varDeclList_in_node266 = new BitSet(new long[]{0x0000000020000000L});
    public static final BitSet FOLLOW_29_in_node269 = new BitSet(new long[]{0x0800000000000000L});
    public static final BitSet FOLLOW_59_in_node273 = new BitSet(new long[]{0x0000000010000000L});
    public static final BitSet FOLLOW_28_in_node275 = new BitSet(new long[]{0x0000000020000100L});
    public static final BitSet FOLLOW_varDeclList_in_node279 = new BitSet(new long[]{0x0000000020000000L});
    public static final BitSet FOLLOW_29_in_node282 = new BitSet(new long[]{0x0000004000000000L});
    public static final BitSet FOLLOW_38_in_node284 = new BitSet(new long[]{0x0040000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_64_in_node289 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_varDeclList_in_node293 = new BitSet(new long[]{0x0000004000000000L});
    public static final BitSet FOLLOW_38_in_node295 = new BitSet(new long[]{0x0040000000000000L});
    public static final BitSet FOLLOW_54_in_node301 = new BitSet(new long[]{0x2000000400000100L});
    public static final BitSet FOLLOW_equation_in_node308 = new BitSet(new long[]{0x2000000400000100L});
    public static final BitSet FOLLOW_property_in_node312 = new BitSet(new long[]{0x2000000400000100L});
    public static final BitSet FOLLOW_61_in_node318 = new BitSet(new long[]{0x0000004000000000L});
    public static final BitSet FOLLOW_38_in_node320 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_varDeclGroup_in_varDeclList408 = new BitSet(new long[]{0x0000004000000002L});
    public static final BitSet FOLLOW_38_in_varDeclList411 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_varDeclGroup_in_varDeclList413 = new BitSet(new long[]{0x0000004000000002L});
    public static final BitSet FOLLOW_ID_in_varDeclGroup430 = new BitSet(new long[]{0x0000002100000000L});
    public static final BitSet FOLLOW_32_in_varDeclGroup433 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_ID_in_varDeclGroup435 = new BitSet(new long[]{0x0000002100000000L});
    public static final BitSet FOLLOW_37_in_varDeclGroup439 = new BitSet(new long[]{0x1422000000000100L});
    public static final BitSet FOLLOW_type_in_varDeclGroup441 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_53_in_type460 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_60_in_type465 = new BitSet(new long[]{0x0000400000000000L});
    public static final BitSet FOLLOW_46_in_type467 = new BitSet(new long[]{0x0000000200001000L});
    public static final BitSet FOLLOW_bound_in_type469 = new BitSet(new long[]{0x0000000100000000L});
    public static final BitSet FOLLOW_32_in_type471 = new BitSet(new long[]{0x0000000200001000L});
    public static final BitSet FOLLOW_bound_in_type473 = new BitSet(new long[]{0x0000800000000000L});
    public static final BitSet FOLLOW_47_in_type475 = new BitSet(new long[]{0x0100000000000000L});
    public static final BitSet FOLLOW_56_in_type477 = new BitSet(new long[]{0x0020000000000000L});
    public static final BitSet FOLLOW_53_in_type479 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_49_in_type487 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_58_in_type492 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_type497 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_33_in_bound508 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_INT_in_bound511 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_34_in_property521 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_ID_in_property523 = new BitSet(new long[]{0x0000004000000000L});
    public static final BitSet FOLLOW_38_in_property525 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_equation539 = new BitSet(new long[]{0x0000040000000000L});
    public static final BitSet FOLLOW_42_in_equation541 = new BitSet(new long[]{0x0000000210281510L});
    public static final BitSet FOLLOW_expr_in_equation543 = new BitSet(new long[]{0x0000004000000000L});
    public static final BitSet FOLLOW_38_in_equation545 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_arrowExpr_in_expr563 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_35_in_arrowOp573 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_impliesExpr_in_arrowExpr583 = new BitSet(new long[]{0x0000000800000002L});
    public static final BitSet FOLLOW_arrowOp_in_arrowExpr590 = new BitSet(new long[]{0x0000000210281510L});
    public static final BitSet FOLLOW_arrowExpr_in_arrowExpr593 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_43_in_impliesOp605 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_orExpr_in_impliesExpr615 = new BitSet(new long[]{0x0000080000000002L});
    public static final BitSet FOLLOW_impliesOp_in_impliesExpr622 = new BitSet(new long[]{0x0000000210281510L});
    public static final BitSet FOLLOW_impliesExpr_in_impliesExpr625 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_andExpr_in_orExpr651 = new BitSet(new long[]{0x0200000000000002L,0x0000000000000002L});
    public static final BitSet FOLLOW_orOp_in_orExpr658 = new BitSet(new long[]{0x0000000210281510L});
    public static final BitSet FOLLOW_andExpr_in_orExpr661 = new BitSet(new long[]{0x0200000000000002L,0x0000000000000002L});
    public static final BitSet FOLLOW_48_in_andOp673 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_relationalExpr_in_andExpr683 = new BitSet(new long[]{0x0001000000000002L});
    public static final BitSet FOLLOW_andOp_in_andExpr690 = new BitSet(new long[]{0x0000000210281510L});
    public static final BitSet FOLLOW_relationalExpr_in_andExpr693 = new BitSet(new long[]{0x0001000000000002L});
    public static final BitSet FOLLOW_plusExpr_in_relationalExpr735 = new BitSet(new long[]{0x0000378000000002L});
    public static final BitSet FOLLOW_relationalOp_in_relationalExpr742 = new BitSet(new long[]{0x0000000210281510L});
    public static final BitSet FOLLOW_plusExpr_in_relationalExpr745 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_timesExpr_in_plusExpr771 = new BitSet(new long[]{0x0000000280000002L});
    public static final BitSet FOLLOW_plusOp_in_plusExpr778 = new BitSet(new long[]{0x0000000210281510L});
    public static final BitSet FOLLOW_timesExpr_in_plusExpr781 = new BitSet(new long[]{0x0000000280000002L});
    public static final BitSet FOLLOW_prefixExpr_in_timesExpr811 = new BitSet(new long[]{0x0008001040000002L});
    public static final BitSet FOLLOW_timesOp_in_timesExpr818 = new BitSet(new long[]{0x0000000210281510L});
    public static final BitSet FOLLOW_prefixExpr_in_timesExpr821 = new BitSet(new long[]{0x0008001040000002L});
    public static final BitSet FOLLOW_33_in_prefixExpr833 = new BitSet(new long[]{0x0000000210281510L});
    public static final BitSet FOLLOW_prefixExpr_in_prefixExpr835 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NOT_in_prefixExpr847 = new BitSet(new long[]{0x0000000210281510L});
    public static final BitSet FOLLOW_prefixExpr_in_prefixExpr850 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_PRE_in_prefixExpr854 = new BitSet(new long[]{0x0000000210281510L});
    public static final BitSet FOLLOW_prefixExpr_in_prefixExpr857 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_atomicExpr_in_prefixExpr861 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_atomicExpr871 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INT_in_atomicExpr883 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_real_in_atomicExpr887 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_BOOL_in_atomicExpr891 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IF_in_atomicExpr895 = new BitSet(new long[]{0x0000000210281510L});
    public static final BitSet FOLLOW_expr_in_atomicExpr898 = new BitSet(new long[]{0x4000000000000000L});
    public static final BitSet FOLLOW_62_in_atomicExpr900 = new BitSet(new long[]{0x0000000210281510L});
    public static final BitSet FOLLOW_expr_in_atomicExpr903 = new BitSet(new long[]{0x0010000000000000L});
    public static final BitSet FOLLOW_52_in_atomicExpr905 = new BitSet(new long[]{0x0000000210281510L});
    public static final BitSet FOLLOW_expr_in_atomicExpr908 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_atomicExpr912 = new BitSet(new long[]{0x0000000010000000L});
    public static final BitSet FOLLOW_28_in_atomicExpr914 = new BitSet(new long[]{0x0000000230281510L});
    public static final BitSet FOLLOW_expr_in_atomicExpr917 = new BitSet(new long[]{0x0000000120000000L});
    public static final BitSet FOLLOW_32_in_atomicExpr920 = new BitSet(new long[]{0x0000000210281510L});
    public static final BitSet FOLLOW_expr_in_atomicExpr922 = new BitSet(new long[]{0x0000000120000000L});
    public static final BitSet FOLLOW_29_in_atomicExpr928 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_28_in_atomicExpr943 = new BitSet(new long[]{0x0000000210281510L});
    public static final BitSet FOLLOW_expr_in_atomicExpr945 = new BitSet(new long[]{0x0000000020000000L});
    public static final BitSet FOLLOW_29_in_atomicExpr947 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INT_in_real961 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_ERROR_in_real963 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_INT_in_real967 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_arrowOp_in_synpred1_Lustre587 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_impliesOp_in_synpred2_Lustre619 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_orOp_in_synpred3_Lustre655 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_andOp_in_synpred4_Lustre687 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_relationalOp_in_synpred5_Lustre739 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_plusOp_in_synpred6_Lustre775 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_timesOp_in_synpred7_Lustre815 = new BitSet(new long[]{0x0000000000000002L});

}