// $ANTLR 3.4 Lustre.g 2012-10-31 11:30:37

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
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "BOOL", "CONSTANTS", "EQUATIONS", "ERROR", "ID", "IDENT", "IF", "INPUTS", "INT", "LOCALS", "MAIN", "ML_COMMENT", "NEGATE", "NODECALL", "NODES", "NOT", "OUTPUTS", "PRE", "PROGRAM", "PROPERTIES", "REAL", "SL_COMMENT", "WS", "'('", "')'", "'*'", "'+'", "','", "'-'", "'--%PROPERTY'", "'->'", "'/'", "':'", "';'", "'<'", "'<='", "'<>'", "'='", "'=>'", "'>'", "'>='", "'['", "']'", "'and'", "'bool'", "'const'", "'div'", "'else'", "'int'", "'let'", "'node'", "'of'", "'or'", "'real'", "'returns'", "'subrange'", "'tel'", "'then'", "'var'", "'xor'"
    };

    public static final int EOF=-1;
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
    public static final int T__62=62;
    public static final int T__63=63;
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
    public static final int WS=26;

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
    // Lustre.g:49:1: program : ( constant | node )* -> ^( PROGRAM ^( CONSTANTS ( constant )* ) ^( NODES ( node )* ) ) ;
    public final LustreParser.program_return program() throws RecognitionException {
        LustreParser.program_return retval = new LustreParser.program_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        LustreParser.constant_return constant1 =null;

        LustreParser.node_return node2 =null;


        RewriteRuleSubtreeStream stream_node=new RewriteRuleSubtreeStream(adaptor,"rule node");
        RewriteRuleSubtreeStream stream_constant=new RewriteRuleSubtreeStream(adaptor,"rule constant");
        try {
            // Lustre.g:49:8: ( ( constant | node )* -> ^( PROGRAM ^( CONSTANTS ( constant )* ) ^( NODES ( node )* ) ) )
            // Lustre.g:50:3: ( constant | node )*
            {
            // Lustre.g:50:3: ( constant | node )*
            loop1:
            do {
                int alt1=3;
                int LA1_0 = input.LA(1);

                if ( (LA1_0==49) ) {
                    alt1=1;
                }
                else if ( (LA1_0==54) ) {
                    alt1=2;
                }


                switch (alt1) {
            	case 1 :
            	    // Lustre.g:50:4: constant
            	    {
            	    pushFollow(FOLLOW_constant_in_program130);
            	    constant1=constant();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) stream_constant.add(constant1.getTree());

            	    }
            	    break;
            	case 2 :
            	    // Lustre.g:50:15: node
            	    {
            	    pushFollow(FOLLOW_node_in_program134);
            	    node2=node();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) stream_node.add(node2.getTree());

            	    }
            	    break;

            	default :
            	    break loop1;
                }
            } while (true);


            // AST REWRITE
            // elements: constant, node
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {

            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 50:22: -> ^( PROGRAM ^( CONSTANTS ( constant )* ) ^( NODES ( node )* ) )
            {
                // Lustre.g:50:25: ^( PROGRAM ^( CONSTANTS ( constant )* ) ^( NODES ( node )* ) )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot(
                (Object)adaptor.create(PROGRAM, "PROGRAM")
                , root_1);

                // Lustre.g:50:35: ^( CONSTANTS ( constant )* )
                {
                Object root_2 = (Object)adaptor.nil();
                root_2 = (Object)adaptor.becomeRoot(
                (Object)adaptor.create(CONSTANTS, "CONSTANTS")
                , root_2);

                // Lustre.g:50:47: ( constant )*
                while ( stream_constant.hasNext() ) {
                    adaptor.addChild(root_2, stream_constant.nextTree());

                }
                stream_constant.reset();

                adaptor.addChild(root_1, root_2);
                }

                // Lustre.g:50:58: ^( NODES ( node )* )
                {
                Object root_2 = (Object)adaptor.nil();
                root_2 = (Object)adaptor.becomeRoot(
                (Object)adaptor.create(NODES, "NODES")
                , root_2);

                // Lustre.g:50:66: ( node )*
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


    public static class node_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "node"
    // Lustre.g:53:1: node : 'node' ID '(' (inputs= varDeclList )? ')' 'returns' '(' (outputs= varDeclList )? ')' ';' ( 'var' locals= varDeclList ';' )? 'let' ( equation | property )* 'tel' ';' -> ^( ID ^( INPUTS ( $inputs)? ) ^( OUTPUTS ( $outputs)? ) ^( LOCALS ( $locals)? ) ^( EQUATIONS ( equation )* ) ^( PROPERTIES ( property )* ) ) ;
    public final LustreParser.node_return node() throws RecognitionException {
        LustreParser.node_return retval = new LustreParser.node_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token string_literal3=null;
        Token ID4=null;
        Token char_literal5=null;
        Token char_literal6=null;
        Token string_literal7=null;
        Token char_literal8=null;
        Token char_literal9=null;
        Token char_literal10=null;
        Token string_literal11=null;
        Token char_literal12=null;
        Token string_literal13=null;
        Token string_literal16=null;
        Token char_literal17=null;
        LustreParser.varDeclList_return inputs =null;

        LustreParser.varDeclList_return outputs =null;

        LustreParser.varDeclList_return locals =null;

        LustreParser.equation_return equation14 =null;

        LustreParser.property_return property15 =null;


        Object string_literal3_tree=null;
        Object ID4_tree=null;
        Object char_literal5_tree=null;
        Object char_literal6_tree=null;
        Object string_literal7_tree=null;
        Object char_literal8_tree=null;
        Object char_literal9_tree=null;
        Object char_literal10_tree=null;
        Object string_literal11_tree=null;
        Object char_literal12_tree=null;
        Object string_literal13_tree=null;
        Object string_literal16_tree=null;
        Object char_literal17_tree=null;
        RewriteRuleTokenStream stream_58=new RewriteRuleTokenStream(adaptor,"token 58");
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleTokenStream stream_53=new RewriteRuleTokenStream(adaptor,"token 53");
        RewriteRuleTokenStream stream_62=new RewriteRuleTokenStream(adaptor,"token 62");
        RewriteRuleTokenStream stream_54=new RewriteRuleTokenStream(adaptor,"token 54");
        RewriteRuleTokenStream stream_60=new RewriteRuleTokenStream(adaptor,"token 60");
        RewriteRuleTokenStream stream_27=new RewriteRuleTokenStream(adaptor,"token 27");
        RewriteRuleTokenStream stream_28=new RewriteRuleTokenStream(adaptor,"token 28");
        RewriteRuleTokenStream stream_37=new RewriteRuleTokenStream(adaptor,"token 37");
        RewriteRuleSubtreeStream stream_equation=new RewriteRuleSubtreeStream(adaptor,"rule equation");
        RewriteRuleSubtreeStream stream_varDeclList=new RewriteRuleSubtreeStream(adaptor,"rule varDeclList");
        RewriteRuleSubtreeStream stream_property=new RewriteRuleSubtreeStream(adaptor,"rule property");
        try {
            // Lustre.g:53:5: ( 'node' ID '(' (inputs= varDeclList )? ')' 'returns' '(' (outputs= varDeclList )? ')' ';' ( 'var' locals= varDeclList ';' )? 'let' ( equation | property )* 'tel' ';' -> ^( ID ^( INPUTS ( $inputs)? ) ^( OUTPUTS ( $outputs)? ) ^( LOCALS ( $locals)? ) ^( EQUATIONS ( equation )* ) ^( PROPERTIES ( property )* ) ) )
            // Lustre.g:54:3: 'node' ID '(' (inputs= varDeclList )? ')' 'returns' '(' (outputs= varDeclList )? ')' ';' ( 'var' locals= varDeclList ';' )? 'let' ( equation | property )* 'tel' ';'
            {
            string_literal3=(Token)match(input,54,FOLLOW_54_in_node166); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_54.add(string_literal3);


            ID4=(Token)match(input,ID,FOLLOW_ID_in_node168); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_ID.add(ID4);


            char_literal5=(Token)match(input,27,FOLLOW_27_in_node170); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_27.add(char_literal5);


            // Lustre.g:54:23: (inputs= varDeclList )?
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( (LA2_0==ID) ) {
                alt2=1;
            }
            switch (alt2) {
                case 1 :
                    // Lustre.g:54:23: inputs= varDeclList
                    {
                    pushFollow(FOLLOW_varDeclList_in_node174);
                    inputs=varDeclList();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_varDeclList.add(inputs.getTree());

                    }
                    break;

            }


            char_literal6=(Token)match(input,28,FOLLOW_28_in_node177); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_28.add(char_literal6);


            string_literal7=(Token)match(input,58,FOLLOW_58_in_node181); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_58.add(string_literal7);


            char_literal8=(Token)match(input,27,FOLLOW_27_in_node183); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_27.add(char_literal8);


            // Lustre.g:55:24: (outputs= varDeclList )?
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( (LA3_0==ID) ) {
                alt3=1;
            }
            switch (alt3) {
                case 1 :
                    // Lustre.g:55:24: outputs= varDeclList
                    {
                    pushFollow(FOLLOW_varDeclList_in_node187);
                    outputs=varDeclList();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_varDeclList.add(outputs.getTree());

                    }
                    break;

            }


            char_literal9=(Token)match(input,28,FOLLOW_28_in_node190); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_28.add(char_literal9);


            char_literal10=(Token)match(input,37,FOLLOW_37_in_node192); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_37.add(char_literal10);


            // Lustre.g:56:3: ( 'var' locals= varDeclList ';' )?
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0==62) ) {
                alt4=1;
            }
            switch (alt4) {
                case 1 :
                    // Lustre.g:56:4: 'var' locals= varDeclList ';'
                    {
                    string_literal11=(Token)match(input,62,FOLLOW_62_in_node197); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_62.add(string_literal11);


                    pushFollow(FOLLOW_varDeclList_in_node201);
                    locals=varDeclList();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_varDeclList.add(locals.getTree());

                    char_literal12=(Token)match(input,37,FOLLOW_37_in_node203); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_37.add(char_literal12);


                    }
                    break;

            }


            string_literal13=(Token)match(input,53,FOLLOW_53_in_node209); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_53.add(string_literal13);


            // Lustre.g:58:5: ( equation | property )*
            loop5:
            do {
                int alt5=3;
                int LA5_0 = input.LA(1);

                if ( (LA5_0==ID) ) {
                    alt5=1;
                }
                else if ( (LA5_0==33) ) {
                    alt5=2;
                }


                switch (alt5) {
            	case 1 :
            	    // Lustre.g:58:6: equation
            	    {
            	    pushFollow(FOLLOW_equation_in_node216);
            	    equation14=equation();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) stream_equation.add(equation14.getTree());

            	    }
            	    break;
            	case 2 :
            	    // Lustre.g:58:17: property
            	    {
            	    pushFollow(FOLLOW_property_in_node220);
            	    property15=property();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) stream_property.add(property15.getTree());

            	    }
            	    break;

            	default :
            	    break loop5;
                }
            } while (true);


            string_literal16=(Token)match(input,60,FOLLOW_60_in_node226); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_60.add(string_literal16);


            char_literal17=(Token)match(input,37,FOLLOW_37_in_node228); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_37.add(char_literal17);


            // AST REWRITE
            // elements: property, outputs, inputs, equation, ID, locals
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
            // 59:13: -> ^( ID ^( INPUTS ( $inputs)? ) ^( OUTPUTS ( $outputs)? ) ^( LOCALS ( $locals)? ) ^( EQUATIONS ( equation )* ) ^( PROPERTIES ( property )* ) )
            {
                // Lustre.g:60:5: ^( ID ^( INPUTS ( $inputs)? ) ^( OUTPUTS ( $outputs)? ) ^( LOCALS ( $locals)? ) ^( EQUATIONS ( equation )* ) ^( PROPERTIES ( property )* ) )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot(
                stream_ID.nextNode()
                , root_1);

                // Lustre.g:61:7: ^( INPUTS ( $inputs)? )
                {
                Object root_2 = (Object)adaptor.nil();
                root_2 = (Object)adaptor.becomeRoot(
                (Object)adaptor.create(INPUTS, "INPUTS")
                , root_2);

                // Lustre.g:61:17: ( $inputs)?
                if ( stream_inputs.hasNext() ) {
                    adaptor.addChild(root_2, stream_inputs.nextTree());

                }
                stream_inputs.reset();

                adaptor.addChild(root_1, root_2);
                }

                // Lustre.g:62:7: ^( OUTPUTS ( $outputs)? )
                {
                Object root_2 = (Object)adaptor.nil();
                root_2 = (Object)adaptor.becomeRoot(
                (Object)adaptor.create(OUTPUTS, "OUTPUTS")
                , root_2);

                // Lustre.g:62:18: ( $outputs)?
                if ( stream_outputs.hasNext() ) {
                    adaptor.addChild(root_2, stream_outputs.nextTree());

                }
                stream_outputs.reset();

                adaptor.addChild(root_1, root_2);
                }

                // Lustre.g:63:7: ^( LOCALS ( $locals)? )
                {
                Object root_2 = (Object)adaptor.nil();
                root_2 = (Object)adaptor.becomeRoot(
                (Object)adaptor.create(LOCALS, "LOCALS")
                , root_2);

                // Lustre.g:63:17: ( $locals)?
                if ( stream_locals.hasNext() ) {
                    adaptor.addChild(root_2, stream_locals.nextTree());

                }
                stream_locals.reset();

                adaptor.addChild(root_1, root_2);
                }

                // Lustre.g:64:7: ^( EQUATIONS ( equation )* )
                {
                Object root_2 = (Object)adaptor.nil();
                root_2 = (Object)adaptor.becomeRoot(
                (Object)adaptor.create(EQUATIONS, "EQUATIONS")
                , root_2);

                // Lustre.g:64:19: ( equation )*
                while ( stream_equation.hasNext() ) {
                    adaptor.addChild(root_2, stream_equation.nextTree());

                }
                stream_equation.reset();

                adaptor.addChild(root_1, root_2);
                }

                // Lustre.g:65:7: ^( PROPERTIES ( property )* )
                {
                Object root_2 = (Object)adaptor.nil();
                root_2 = (Object)adaptor.becomeRoot(
                (Object)adaptor.create(PROPERTIES, "PROPERTIES")
                , root_2);

                // Lustre.g:65:20: ( property )*
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
    // Lustre.g:68:1: constant : 'const' ID '=' expr ';' -> ^( ID expr ) ;
    public final LustreParser.constant_return constant() throws RecognitionException {
        LustreParser.constant_return retval = new LustreParser.constant_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token string_literal18=null;
        Token ID19=null;
        Token char_literal20=null;
        Token char_literal22=null;
        LustreParser.expr_return expr21 =null;


        Object string_literal18_tree=null;
        Object ID19_tree=null;
        Object char_literal20_tree=null;
        Object char_literal22_tree=null;
        RewriteRuleTokenStream stream_49=new RewriteRuleTokenStream(adaptor,"token 49");
        RewriteRuleTokenStream stream_41=new RewriteRuleTokenStream(adaptor,"token 41");
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleTokenStream stream_37=new RewriteRuleTokenStream(adaptor,"token 37");
        RewriteRuleSubtreeStream stream_expr=new RewriteRuleSubtreeStream(adaptor,"rule expr");
        try {
            // Lustre.g:68:9: ( 'const' ID '=' expr ';' -> ^( ID expr ) )
            // Lustre.g:69:3: 'const' ID '=' expr ';'
            {
            string_literal18=(Token)match(input,49,FOLLOW_49_in_constant316); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_49.add(string_literal18);


            ID19=(Token)match(input,ID,FOLLOW_ID_in_constant318); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_ID.add(ID19);


            char_literal20=(Token)match(input,41,FOLLOW_41_in_constant320); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_41.add(char_literal20);


            pushFollow(FOLLOW_expr_in_constant322);
            expr21=expr();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_expr.add(expr21.getTree());

            char_literal22=(Token)match(input,37,FOLLOW_37_in_constant324); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_37.add(char_literal22);


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
            // 69:27: -> ^( ID expr )
            {
                // Lustre.g:69:30: ^( ID expr )
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
    // Lustre.g:72:1: varDeclList : varDeclGroup ( ';' varDeclGroup )* -> ( varDeclGroup )+ ;
    public final LustreParser.varDeclList_return varDeclList() throws RecognitionException {
        LustreParser.varDeclList_return retval = new LustreParser.varDeclList_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token char_literal24=null;
        LustreParser.varDeclGroup_return varDeclGroup23 =null;

        LustreParser.varDeclGroup_return varDeclGroup25 =null;


        Object char_literal24_tree=null;
        RewriteRuleTokenStream stream_37=new RewriteRuleTokenStream(adaptor,"token 37");
        RewriteRuleSubtreeStream stream_varDeclGroup=new RewriteRuleSubtreeStream(adaptor,"rule varDeclGroup");
        try {
            // Lustre.g:72:12: ( varDeclGroup ( ';' varDeclGroup )* -> ( varDeclGroup )+ )
            // Lustre.g:73:3: varDeclGroup ( ';' varDeclGroup )*
            {
            pushFollow(FOLLOW_varDeclGroup_in_varDeclList342);
            varDeclGroup23=varDeclGroup();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_varDeclGroup.add(varDeclGroup23.getTree());

            // Lustre.g:73:16: ( ';' varDeclGroup )*
            loop6:
            do {
                int alt6=2;
                int LA6_0 = input.LA(1);

                if ( (LA6_0==37) ) {
                    int LA6_2 = input.LA(2);

                    if ( (LA6_2==ID) ) {
                        alt6=1;
                    }


                }


                switch (alt6) {
            	case 1 :
            	    // Lustre.g:73:17: ';' varDeclGroup
            	    {
            	    char_literal24=(Token)match(input,37,FOLLOW_37_in_varDeclList345); if (state.failed) return retval; 
            	    if ( state.backtracking==0 ) stream_37.add(char_literal24);


            	    pushFollow(FOLLOW_varDeclGroup_in_varDeclList347);
            	    varDeclGroup25=varDeclGroup();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) stream_varDeclGroup.add(varDeclGroup25.getTree());

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
            // 73:36: -> ( varDeclGroup )+
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
    // Lustre.g:76:1: varDeclGroup : ID ( ',' ID )* ':' type -> ( ^( ID type ) )* ;
    public final LustreParser.varDeclGroup_return varDeclGroup() throws RecognitionException {
        LustreParser.varDeclGroup_return retval = new LustreParser.varDeclGroup_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token ID26=null;
        Token char_literal27=null;
        Token ID28=null;
        Token char_literal29=null;
        LustreParser.type_return type30 =null;


        Object ID26_tree=null;
        Object char_literal27_tree=null;
        Object ID28_tree=null;
        Object char_literal29_tree=null;
        RewriteRuleTokenStream stream_31=new RewriteRuleTokenStream(adaptor,"token 31");
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleTokenStream stream_36=new RewriteRuleTokenStream(adaptor,"token 36");
        RewriteRuleSubtreeStream stream_type=new RewriteRuleSubtreeStream(adaptor,"rule type");
        try {
            // Lustre.g:76:13: ( ID ( ',' ID )* ':' type -> ( ^( ID type ) )* )
            // Lustre.g:77:3: ID ( ',' ID )* ':' type
            {
            ID26=(Token)match(input,ID,FOLLOW_ID_in_varDeclGroup364); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_ID.add(ID26);


            // Lustre.g:77:6: ( ',' ID )*
            loop7:
            do {
                int alt7=2;
                int LA7_0 = input.LA(1);

                if ( (LA7_0==31) ) {
                    alt7=1;
                }


                switch (alt7) {
            	case 1 :
            	    // Lustre.g:77:7: ',' ID
            	    {
            	    char_literal27=(Token)match(input,31,FOLLOW_31_in_varDeclGroup367); if (state.failed) return retval; 
            	    if ( state.backtracking==0 ) stream_31.add(char_literal27);


            	    ID28=(Token)match(input,ID,FOLLOW_ID_in_varDeclGroup369); if (state.failed) return retval; 
            	    if ( state.backtracking==0 ) stream_ID.add(ID28);


            	    }
            	    break;

            	default :
            	    break loop7;
                }
            } while (true);


            char_literal29=(Token)match(input,36,FOLLOW_36_in_varDeclGroup373); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_36.add(char_literal29);


            pushFollow(FOLLOW_type_in_varDeclGroup375);
            type30=type();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_type.add(type30.getTree());

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
            // 77:25: -> ( ^( ID type ) )*
            {
                // Lustre.g:77:28: ( ^( ID type ) )*
                while ( stream_ID.hasNext()||stream_type.hasNext() ) {
                    // Lustre.g:77:28: ^( ID type )
                    {
                    Object root_1 = (Object)adaptor.nil();
                    root_1 = (Object)adaptor.becomeRoot(
                    stream_ID.nextNode()
                    , root_1);

                    adaptor.addChild(root_1, stream_type.nextTree());

                    adaptor.addChild(root_0, root_1);
                    }

                }
                stream_ID.reset();
                stream_type.reset();

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
    // Lustre.g:80:1: type : ( 'int' ^| 'subrange' '[' bound ',' bound ']' 'of' 'int' -> 'int' | 'bool' ^| 'real' ^);
    public final LustreParser.type_return type() throws RecognitionException {
        LustreParser.type_return retval = new LustreParser.type_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token string_literal31=null;
        Token string_literal32=null;
        Token char_literal33=null;
        Token char_literal35=null;
        Token char_literal37=null;
        Token string_literal38=null;
        Token string_literal39=null;
        Token string_literal40=null;
        Token string_literal41=null;
        LustreParser.bound_return bound34 =null;

        LustreParser.bound_return bound36 =null;


        Object string_literal31_tree=null;
        Object string_literal32_tree=null;
        Object char_literal33_tree=null;
        Object char_literal35_tree=null;
        Object char_literal37_tree=null;
        Object string_literal38_tree=null;
        Object string_literal39_tree=null;
        Object string_literal40_tree=null;
        Object string_literal41_tree=null;
        RewriteRuleTokenStream stream_59=new RewriteRuleTokenStream(adaptor,"token 59");
        RewriteRuleTokenStream stream_45=new RewriteRuleTokenStream(adaptor,"token 45");
        RewriteRuleTokenStream stream_46=new RewriteRuleTokenStream(adaptor,"token 46");
        RewriteRuleTokenStream stream_31=new RewriteRuleTokenStream(adaptor,"token 31");
        RewriteRuleTokenStream stream_55=new RewriteRuleTokenStream(adaptor,"token 55");
        RewriteRuleTokenStream stream_52=new RewriteRuleTokenStream(adaptor,"token 52");
        RewriteRuleSubtreeStream stream_bound=new RewriteRuleSubtreeStream(adaptor,"rule bound");
        try {
            // Lustre.g:80:5: ( 'int' ^| 'subrange' '[' bound ',' bound ']' 'of' 'int' -> 'int' | 'bool' ^| 'real' ^)
            int alt8=4;
            switch ( input.LA(1) ) {
            case 52:
                {
                alt8=1;
                }
                break;
            case 59:
                {
                alt8=2;
                }
                break;
            case 48:
                {
                alt8=3;
                }
                break;
            case 57:
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
                    // Lustre.g:81:3: 'int' ^
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal31=(Token)match(input,52,FOLLOW_52_in_type394); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal31_tree = 
                    (Object)adaptor.create(string_literal31)
                    ;
                    root_0 = (Object)adaptor.becomeRoot(string_literal31_tree, root_0);
                    }

                    }
                    break;
                case 2 :
                    // Lustre.g:82:3: 'subrange' '[' bound ',' bound ']' 'of' 'int'
                    {
                    string_literal32=(Token)match(input,59,FOLLOW_59_in_type399); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_59.add(string_literal32);


                    char_literal33=(Token)match(input,45,FOLLOW_45_in_type401); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_45.add(char_literal33);


                    pushFollow(FOLLOW_bound_in_type403);
                    bound34=bound();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_bound.add(bound34.getTree());

                    char_literal35=(Token)match(input,31,FOLLOW_31_in_type405); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_31.add(char_literal35);


                    pushFollow(FOLLOW_bound_in_type407);
                    bound36=bound();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_bound.add(bound36.getTree());

                    char_literal37=(Token)match(input,46,FOLLOW_46_in_type409); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_46.add(char_literal37);


                    string_literal38=(Token)match(input,55,FOLLOW_55_in_type411); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_55.add(string_literal38);


                    string_literal39=(Token)match(input,52,FOLLOW_52_in_type413); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_52.add(string_literal39);


                    // AST REWRITE
                    // elements: 52
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {

                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 82:49: -> 'int'
                    {
                        adaptor.addChild(root_0, 
                        stream_52.nextNode()
                        );

                    }


                    retval.tree = root_0;
                    }

                    }
                    break;
                case 3 :
                    // Lustre.g:83:3: 'bool' ^
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal40=(Token)match(input,48,FOLLOW_48_in_type421); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal40_tree = 
                    (Object)adaptor.create(string_literal40)
                    ;
                    root_0 = (Object)adaptor.becomeRoot(string_literal40_tree, root_0);
                    }

                    }
                    break;
                case 4 :
                    // Lustre.g:84:3: 'real' ^
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal41=(Token)match(input,57,FOLLOW_57_in_type426); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal41_tree = 
                    (Object)adaptor.create(string_literal41)
                    ;
                    root_0 = (Object)adaptor.becomeRoot(string_literal41_tree, root_0);
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
    // Lustre.g:87:1: bound : ( '-' )? INT ;
    public final LustreParser.bound_return bound() throws RecognitionException {
        LustreParser.bound_return retval = new LustreParser.bound_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token char_literal42=null;
        Token INT43=null;

        Object char_literal42_tree=null;
        Object INT43_tree=null;

        try {
            // Lustre.g:87:6: ( ( '-' )? INT )
            // Lustre.g:88:3: ( '-' )? INT
            {
            root_0 = (Object)adaptor.nil();


            // Lustre.g:88:3: ( '-' )?
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( (LA9_0==32) ) {
                alt9=1;
            }
            switch (alt9) {
                case 1 :
                    // Lustre.g:88:3: '-'
                    {
                    char_literal42=(Token)match(input,32,FOLLOW_32_in_bound437); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal42_tree = 
                    (Object)adaptor.create(char_literal42)
                    ;
                    adaptor.addChild(root_0, char_literal42_tree);
                    }

                    }
                    break;

            }


            INT43=(Token)match(input,INT,FOLLOW_INT_in_bound440); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            INT43_tree = 
            (Object)adaptor.create(INT43)
            ;
            adaptor.addChild(root_0, INT43_tree);
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
    // Lustre.g:91:1: property : '--%PROPERTY' ID ';' -> ID ;
    public final LustreParser.property_return property() throws RecognitionException {
        LustreParser.property_return retval = new LustreParser.property_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token string_literal44=null;
        Token ID45=null;
        Token char_literal46=null;

        Object string_literal44_tree=null;
        Object ID45_tree=null;
        Object char_literal46_tree=null;
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleTokenStream stream_33=new RewriteRuleTokenStream(adaptor,"token 33");
        RewriteRuleTokenStream stream_37=new RewriteRuleTokenStream(adaptor,"token 37");

        try {
            // Lustre.g:91:9: ( '--%PROPERTY' ID ';' -> ID )
            // Lustre.g:92:3: '--%PROPERTY' ID ';'
            {
            string_literal44=(Token)match(input,33,FOLLOW_33_in_property450); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_33.add(string_literal44);


            ID45=(Token)match(input,ID,FOLLOW_ID_in_property452); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_ID.add(ID45);


            char_literal46=(Token)match(input,37,FOLLOW_37_in_property454); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_37.add(char_literal46);


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
            // 92:24: -> ID
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
    // Lustre.g:95:1: equation : ID '=' expr ';' -> ^( ID expr ) ;
    public final LustreParser.equation_return equation() throws RecognitionException {
        LustreParser.equation_return retval = new LustreParser.equation_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token ID47=null;
        Token char_literal48=null;
        Token char_literal50=null;
        LustreParser.expr_return expr49 =null;


        Object ID47_tree=null;
        Object char_literal48_tree=null;
        Object char_literal50_tree=null;
        RewriteRuleTokenStream stream_41=new RewriteRuleTokenStream(adaptor,"token 41");
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleTokenStream stream_37=new RewriteRuleTokenStream(adaptor,"token 37");
        RewriteRuleSubtreeStream stream_expr=new RewriteRuleSubtreeStream(adaptor,"rule expr");
        try {
            // Lustre.g:95:9: ( ID '=' expr ';' -> ^( ID expr ) )
            // Lustre.g:96:3: ID '=' expr ';'
            {
            ID47=(Token)match(input,ID,FOLLOW_ID_in_equation468); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_ID.add(ID47);


            char_literal48=(Token)match(input,41,FOLLOW_41_in_equation470); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_41.add(char_literal48);


            pushFollow(FOLLOW_expr_in_equation472);
            expr49=expr();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_expr.add(expr49.getTree());

            char_literal50=(Token)match(input,37,FOLLOW_37_in_equation474); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_37.add(char_literal50);


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
            // 96:19: -> ^( ID expr )
            {
                // Lustre.g:96:22: ^( ID expr )
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
    // Lustre.g:99:1: expr : arrowExpr ;
    public final LustreParser.expr_return expr() throws RecognitionException {
        LustreParser.expr_return retval = new LustreParser.expr_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        LustreParser.arrowExpr_return arrowExpr51 =null;



        try {
            // Lustre.g:99:5: ( arrowExpr )
            // Lustre.g:100:3: arrowExpr
            {
            root_0 = (Object)adaptor.nil();


            pushFollow(FOLLOW_arrowExpr_in_expr492);
            arrowExpr51=arrowExpr();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, arrowExpr51.getTree());

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
    // Lustre.g:103:1: arrowOp : '->' ;
    public final LustreParser.arrowOp_return arrowOp() throws RecognitionException {
        LustreParser.arrowOp_return retval = new LustreParser.arrowOp_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token string_literal52=null;

        Object string_literal52_tree=null;

        try {
            // Lustre.g:103:8: ( '->' )
            // Lustre.g:104:3: '->'
            {
            root_0 = (Object)adaptor.nil();


            string_literal52=(Token)match(input,34,FOLLOW_34_in_arrowOp502); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            string_literal52_tree = 
            (Object)adaptor.create(string_literal52)
            ;
            adaptor.addChild(root_0, string_literal52_tree);
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
    // Lustre.g:107:1: arrowExpr : impliesExpr ( ( arrowOp )=> arrowOp ^ arrowExpr )? ;
    public final LustreParser.arrowExpr_return arrowExpr() throws RecognitionException {
        LustreParser.arrowExpr_return retval = new LustreParser.arrowExpr_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        LustreParser.impliesExpr_return impliesExpr53 =null;

        LustreParser.arrowOp_return arrowOp54 =null;

        LustreParser.arrowExpr_return arrowExpr55 =null;



        try {
            // Lustre.g:107:10: ( impliesExpr ( ( arrowOp )=> arrowOp ^ arrowExpr )? )
            // Lustre.g:108:3: impliesExpr ( ( arrowOp )=> arrowOp ^ arrowExpr )?
            {
            root_0 = (Object)adaptor.nil();


            pushFollow(FOLLOW_impliesExpr_in_arrowExpr512);
            impliesExpr53=impliesExpr();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, impliesExpr53.getTree());

            // Lustre.g:108:15: ( ( arrowOp )=> arrowOp ^ arrowExpr )?
            int alt10=2;
            int LA10_0 = input.LA(1);

            if ( (LA10_0==34) ) {
                int LA10_1 = input.LA(2);

                if ( (synpred1_Lustre()) ) {
                    alt10=1;
                }
            }
            switch (alt10) {
                case 1 :
                    // Lustre.g:108:16: ( arrowOp )=> arrowOp ^ arrowExpr
                    {
                    pushFollow(FOLLOW_arrowOp_in_arrowExpr519);
                    arrowOp54=arrowOp();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) root_0 = (Object)adaptor.becomeRoot(arrowOp54.getTree(), root_0);

                    pushFollow(FOLLOW_arrowExpr_in_arrowExpr522);
                    arrowExpr55=arrowExpr();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, arrowExpr55.getTree());

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
    // Lustre.g:111:1: impliesOp : '=>' ;
    public final LustreParser.impliesOp_return impliesOp() throws RecognitionException {
        LustreParser.impliesOp_return retval = new LustreParser.impliesOp_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token string_literal56=null;

        Object string_literal56_tree=null;

        try {
            // Lustre.g:111:10: ( '=>' )
            // Lustre.g:112:3: '=>'
            {
            root_0 = (Object)adaptor.nil();


            string_literal56=(Token)match(input,42,FOLLOW_42_in_impliesOp534); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            string_literal56_tree = 
            (Object)adaptor.create(string_literal56)
            ;
            adaptor.addChild(root_0, string_literal56_tree);
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
    // Lustre.g:115:1: impliesExpr : orExpr ( ( impliesOp )=> impliesOp ^ impliesExpr )? ;
    public final LustreParser.impliesExpr_return impliesExpr() throws RecognitionException {
        LustreParser.impliesExpr_return retval = new LustreParser.impliesExpr_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        LustreParser.orExpr_return orExpr57 =null;

        LustreParser.impliesOp_return impliesOp58 =null;

        LustreParser.impliesExpr_return impliesExpr59 =null;



        try {
            // Lustre.g:115:12: ( orExpr ( ( impliesOp )=> impliesOp ^ impliesExpr )? )
            // Lustre.g:116:3: orExpr ( ( impliesOp )=> impliesOp ^ impliesExpr )?
            {
            root_0 = (Object)adaptor.nil();


            pushFollow(FOLLOW_orExpr_in_impliesExpr544);
            orExpr57=orExpr();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, orExpr57.getTree());

            // Lustre.g:116:10: ( ( impliesOp )=> impliesOp ^ impliesExpr )?
            int alt11=2;
            int LA11_0 = input.LA(1);

            if ( (LA11_0==42) ) {
                int LA11_1 = input.LA(2);

                if ( (synpred2_Lustre()) ) {
                    alt11=1;
                }
            }
            switch (alt11) {
                case 1 :
                    // Lustre.g:116:11: ( impliesOp )=> impliesOp ^ impliesExpr
                    {
                    pushFollow(FOLLOW_impliesOp_in_impliesExpr551);
                    impliesOp58=impliesOp();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) root_0 = (Object)adaptor.becomeRoot(impliesOp58.getTree(), root_0);

                    pushFollow(FOLLOW_impliesExpr_in_impliesExpr554);
                    impliesExpr59=impliesExpr();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, impliesExpr59.getTree());

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
    // Lustre.g:119:1: orOp : ( 'or' | 'xor' );
    public final LustreParser.orOp_return orOp() throws RecognitionException {
        LustreParser.orOp_return retval = new LustreParser.orOp_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token set60=null;

        Object set60_tree=null;

        try {
            // Lustre.g:119:5: ( 'or' | 'xor' )
            // Lustre.g:
            {
            root_0 = (Object)adaptor.nil();


            set60=(Token)input.LT(1);

            if ( input.LA(1)==56||input.LA(1)==63 ) {
                input.consume();
                if ( state.backtracking==0 ) adaptor.addChild(root_0, 
                (Object)adaptor.create(set60)
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
    // Lustre.g:123:1: orExpr : andExpr ( ( orOp )=> orOp ^ andExpr )* ;
    public final LustreParser.orExpr_return orExpr() throws RecognitionException {
        LustreParser.orExpr_return retval = new LustreParser.orExpr_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        LustreParser.andExpr_return andExpr61 =null;

        LustreParser.orOp_return orOp62 =null;

        LustreParser.andExpr_return andExpr63 =null;



        try {
            // Lustre.g:123:7: ( andExpr ( ( orOp )=> orOp ^ andExpr )* )
            // Lustre.g:124:3: andExpr ( ( orOp )=> orOp ^ andExpr )*
            {
            root_0 = (Object)adaptor.nil();


            pushFollow(FOLLOW_andExpr_in_orExpr580);
            andExpr61=andExpr();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, andExpr61.getTree());

            // Lustre.g:124:11: ( ( orOp )=> orOp ^ andExpr )*
            loop12:
            do {
                int alt12=2;
                int LA12_0 = input.LA(1);

                if ( (LA12_0==56||LA12_0==63) ) {
                    int LA12_2 = input.LA(2);

                    if ( (synpred3_Lustre()) ) {
                        alt12=1;
                    }


                }


                switch (alt12) {
            	case 1 :
            	    // Lustre.g:124:12: ( orOp )=> orOp ^ andExpr
            	    {
            	    pushFollow(FOLLOW_orOp_in_orExpr587);
            	    orOp62=orOp();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) root_0 = (Object)adaptor.becomeRoot(orOp62.getTree(), root_0);

            	    pushFollow(FOLLOW_andExpr_in_orExpr590);
            	    andExpr63=andExpr();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, andExpr63.getTree());

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
    // Lustre.g:127:1: andOp : 'and' ;
    public final LustreParser.andOp_return andOp() throws RecognitionException {
        LustreParser.andOp_return retval = new LustreParser.andOp_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token string_literal64=null;

        Object string_literal64_tree=null;

        try {
            // Lustre.g:127:6: ( 'and' )
            // Lustre.g:128:3: 'and'
            {
            root_0 = (Object)adaptor.nil();


            string_literal64=(Token)match(input,47,FOLLOW_47_in_andOp602); if (state.failed) return retval;
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
    // $ANTLR end "andOp"


    public static class andExpr_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "andExpr"
    // Lustre.g:131:1: andExpr : relationalExpr ( ( andOp )=> andOp ^ relationalExpr )* ;
    public final LustreParser.andExpr_return andExpr() throws RecognitionException {
        LustreParser.andExpr_return retval = new LustreParser.andExpr_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        LustreParser.relationalExpr_return relationalExpr65 =null;

        LustreParser.andOp_return andOp66 =null;

        LustreParser.relationalExpr_return relationalExpr67 =null;



        try {
            // Lustre.g:131:8: ( relationalExpr ( ( andOp )=> andOp ^ relationalExpr )* )
            // Lustre.g:132:3: relationalExpr ( ( andOp )=> andOp ^ relationalExpr )*
            {
            root_0 = (Object)adaptor.nil();


            pushFollow(FOLLOW_relationalExpr_in_andExpr612);
            relationalExpr65=relationalExpr();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, relationalExpr65.getTree());

            // Lustre.g:132:18: ( ( andOp )=> andOp ^ relationalExpr )*
            loop13:
            do {
                int alt13=2;
                int LA13_0 = input.LA(1);

                if ( (LA13_0==47) ) {
                    int LA13_2 = input.LA(2);

                    if ( (synpred4_Lustre()) ) {
                        alt13=1;
                    }


                }


                switch (alt13) {
            	case 1 :
            	    // Lustre.g:132:19: ( andOp )=> andOp ^ relationalExpr
            	    {
            	    pushFollow(FOLLOW_andOp_in_andExpr619);
            	    andOp66=andOp();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) root_0 = (Object)adaptor.becomeRoot(andOp66.getTree(), root_0);

            	    pushFollow(FOLLOW_relationalExpr_in_andExpr622);
            	    relationalExpr67=relationalExpr();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, relationalExpr67.getTree());

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
    // Lustre.g:135:1: relationalOp : ( '<' | '<=' | '>' | '>=' | '=' | '<>' );
    public final LustreParser.relationalOp_return relationalOp() throws RecognitionException {
        LustreParser.relationalOp_return retval = new LustreParser.relationalOp_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token set68=null;

        Object set68_tree=null;

        try {
            // Lustre.g:135:13: ( '<' | '<=' | '>' | '>=' | '=' | '<>' )
            // Lustre.g:
            {
            root_0 = (Object)adaptor.nil();


            set68=(Token)input.LT(1);

            if ( (input.LA(1) >= 38 && input.LA(1) <= 41)||(input.LA(1) >= 43 && input.LA(1) <= 44) ) {
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
    // $ANTLR end "relationalOp"


    public static class relationalExpr_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "relationalExpr"
    // Lustre.g:139:1: relationalExpr : plusExpr ( ( relationalOp )=> relationalOp ^ plusExpr )? ;
    public final LustreParser.relationalExpr_return relationalExpr() throws RecognitionException {
        LustreParser.relationalExpr_return retval = new LustreParser.relationalExpr_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        LustreParser.plusExpr_return plusExpr69 =null;

        LustreParser.relationalOp_return relationalOp70 =null;

        LustreParser.plusExpr_return plusExpr71 =null;



        try {
            // Lustre.g:139:15: ( plusExpr ( ( relationalOp )=> relationalOp ^ plusExpr )? )
            // Lustre.g:140:3: plusExpr ( ( relationalOp )=> relationalOp ^ plusExpr )?
            {
            root_0 = (Object)adaptor.nil();


            pushFollow(FOLLOW_plusExpr_in_relationalExpr664);
            plusExpr69=plusExpr();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, plusExpr69.getTree());

            // Lustre.g:140:12: ( ( relationalOp )=> relationalOp ^ plusExpr )?
            int alt14=2;
            int LA14_0 = input.LA(1);

            if ( ((LA14_0 >= 38 && LA14_0 <= 41)||(LA14_0 >= 43 && LA14_0 <= 44)) ) {
                int LA14_1 = input.LA(2);

                if ( (synpred5_Lustre()) ) {
                    alt14=1;
                }
            }
            switch (alt14) {
                case 1 :
                    // Lustre.g:140:13: ( relationalOp )=> relationalOp ^ plusExpr
                    {
                    pushFollow(FOLLOW_relationalOp_in_relationalExpr671);
                    relationalOp70=relationalOp();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) root_0 = (Object)adaptor.becomeRoot(relationalOp70.getTree(), root_0);

                    pushFollow(FOLLOW_plusExpr_in_relationalExpr674);
                    plusExpr71=plusExpr();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, plusExpr71.getTree());

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
    // Lustre.g:143:1: plusOp : ( '+' | '-' );
    public final LustreParser.plusOp_return plusOp() throws RecognitionException {
        LustreParser.plusOp_return retval = new LustreParser.plusOp_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token set72=null;

        Object set72_tree=null;

        try {
            // Lustre.g:143:7: ( '+' | '-' )
            // Lustre.g:
            {
            root_0 = (Object)adaptor.nil();


            set72=(Token)input.LT(1);

            if ( input.LA(1)==30||input.LA(1)==32 ) {
                input.consume();
                if ( state.backtracking==0 ) adaptor.addChild(root_0, 
                (Object)adaptor.create(set72)
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
    // Lustre.g:147:1: plusExpr : timesExpr ( ( plusOp )=> plusOp ^ timesExpr )* ;
    public final LustreParser.plusExpr_return plusExpr() throws RecognitionException {
        LustreParser.plusExpr_return retval = new LustreParser.plusExpr_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        LustreParser.timesExpr_return timesExpr73 =null;

        LustreParser.plusOp_return plusOp74 =null;

        LustreParser.timesExpr_return timesExpr75 =null;



        try {
            // Lustre.g:147:9: ( timesExpr ( ( plusOp )=> plusOp ^ timesExpr )* )
            // Lustre.g:148:3: timesExpr ( ( plusOp )=> plusOp ^ timesExpr )*
            {
            root_0 = (Object)adaptor.nil();


            pushFollow(FOLLOW_timesExpr_in_plusExpr700);
            timesExpr73=timesExpr();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, timesExpr73.getTree());

            // Lustre.g:148:13: ( ( plusOp )=> plusOp ^ timesExpr )*
            loop15:
            do {
                int alt15=2;
                int LA15_0 = input.LA(1);

                if ( (LA15_0==30||LA15_0==32) ) {
                    int LA15_2 = input.LA(2);

                    if ( (synpred6_Lustre()) ) {
                        alt15=1;
                    }


                }


                switch (alt15) {
            	case 1 :
            	    // Lustre.g:148:14: ( plusOp )=> plusOp ^ timesExpr
            	    {
            	    pushFollow(FOLLOW_plusOp_in_plusExpr707);
            	    plusOp74=plusOp();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) root_0 = (Object)adaptor.becomeRoot(plusOp74.getTree(), root_0);

            	    pushFollow(FOLLOW_timesExpr_in_plusExpr710);
            	    timesExpr75=timesExpr();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, timesExpr75.getTree());

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
    // Lustre.g:151:1: timesOp : ( '*' | '/' | 'div' );
    public final LustreParser.timesOp_return timesOp() throws RecognitionException {
        LustreParser.timesOp_return retval = new LustreParser.timesOp_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token set76=null;

        Object set76_tree=null;

        try {
            // Lustre.g:151:8: ( '*' | '/' | 'div' )
            // Lustre.g:
            {
            root_0 = (Object)adaptor.nil();


            set76=(Token)input.LT(1);

            if ( input.LA(1)==29||input.LA(1)==35||input.LA(1)==50 ) {
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
    // $ANTLR end "timesOp"


    public static class timesExpr_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "timesExpr"
    // Lustre.g:155:1: timesExpr : prefixExpr ( ( timesOp )=> timesOp ^ prefixExpr )* ;
    public final LustreParser.timesExpr_return timesExpr() throws RecognitionException {
        LustreParser.timesExpr_return retval = new LustreParser.timesExpr_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        LustreParser.prefixExpr_return prefixExpr77 =null;

        LustreParser.timesOp_return timesOp78 =null;

        LustreParser.prefixExpr_return prefixExpr79 =null;



        try {
            // Lustre.g:155:10: ( prefixExpr ( ( timesOp )=> timesOp ^ prefixExpr )* )
            // Lustre.g:156:3: prefixExpr ( ( timesOp )=> timesOp ^ prefixExpr )*
            {
            root_0 = (Object)adaptor.nil();


            pushFollow(FOLLOW_prefixExpr_in_timesExpr740);
            prefixExpr77=prefixExpr();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, prefixExpr77.getTree());

            // Lustre.g:156:14: ( ( timesOp )=> timesOp ^ prefixExpr )*
            loop16:
            do {
                int alt16=2;
                int LA16_0 = input.LA(1);

                if ( (LA16_0==29||LA16_0==35||LA16_0==50) ) {
                    int LA16_2 = input.LA(2);

                    if ( (synpred7_Lustre()) ) {
                        alt16=1;
                    }


                }


                switch (alt16) {
            	case 1 :
            	    // Lustre.g:156:15: ( timesOp )=> timesOp ^ prefixExpr
            	    {
            	    pushFollow(FOLLOW_timesOp_in_timesExpr747);
            	    timesOp78=timesOp();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) root_0 = (Object)adaptor.becomeRoot(timesOp78.getTree(), root_0);

            	    pushFollow(FOLLOW_prefixExpr_in_timesExpr750);
            	    prefixExpr79=prefixExpr();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, prefixExpr79.getTree());

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
    // Lustre.g:159:1: prefixExpr : ( '-' prefixExpr -> ^( NEGATE prefixExpr ) | NOT ^ prefixExpr | PRE ^ prefixExpr | atomicExpr );
    public final LustreParser.prefixExpr_return prefixExpr() throws RecognitionException {
        LustreParser.prefixExpr_return retval = new LustreParser.prefixExpr_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token char_literal80=null;
        Token NOT82=null;
        Token PRE84=null;
        LustreParser.prefixExpr_return prefixExpr81 =null;

        LustreParser.prefixExpr_return prefixExpr83 =null;

        LustreParser.prefixExpr_return prefixExpr85 =null;

        LustreParser.atomicExpr_return atomicExpr86 =null;


        Object char_literal80_tree=null;
        Object NOT82_tree=null;
        Object PRE84_tree=null;
        RewriteRuleTokenStream stream_32=new RewriteRuleTokenStream(adaptor,"token 32");
        RewriteRuleSubtreeStream stream_prefixExpr=new RewriteRuleSubtreeStream(adaptor,"rule prefixExpr");
        try {
            // Lustre.g:159:11: ( '-' prefixExpr -> ^( NEGATE prefixExpr ) | NOT ^ prefixExpr | PRE ^ prefixExpr | atomicExpr )
            int alt17=4;
            switch ( input.LA(1) ) {
            case 32:
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
            case 27:
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
                    // Lustre.g:160:3: '-' prefixExpr
                    {
                    char_literal80=(Token)match(input,32,FOLLOW_32_in_prefixExpr762); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_32.add(char_literal80);


                    pushFollow(FOLLOW_prefixExpr_in_prefixExpr764);
                    prefixExpr81=prefixExpr();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_prefixExpr.add(prefixExpr81.getTree());

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
                    // 160:18: -> ^( NEGATE prefixExpr )
                    {
                        // Lustre.g:160:21: ^( NEGATE prefixExpr )
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
                    // Lustre.g:161:3: NOT ^ prefixExpr
                    {
                    root_0 = (Object)adaptor.nil();


                    NOT82=(Token)match(input,NOT,FOLLOW_NOT_in_prefixExpr776); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    NOT82_tree = 
                    (Object)adaptor.create(NOT82)
                    ;
                    root_0 = (Object)adaptor.becomeRoot(NOT82_tree, root_0);
                    }

                    pushFollow(FOLLOW_prefixExpr_in_prefixExpr779);
                    prefixExpr83=prefixExpr();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, prefixExpr83.getTree());

                    }
                    break;
                case 3 :
                    // Lustre.g:162:3: PRE ^ prefixExpr
                    {
                    root_0 = (Object)adaptor.nil();


                    PRE84=(Token)match(input,PRE,FOLLOW_PRE_in_prefixExpr783); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    PRE84_tree = 
                    (Object)adaptor.create(PRE84)
                    ;
                    root_0 = (Object)adaptor.becomeRoot(PRE84_tree, root_0);
                    }

                    pushFollow(FOLLOW_prefixExpr_in_prefixExpr786);
                    prefixExpr85=prefixExpr();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, prefixExpr85.getTree());

                    }
                    break;
                case 4 :
                    // Lustre.g:163:3: atomicExpr
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_atomicExpr_in_prefixExpr790);
                    atomicExpr86=atomicExpr();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, atomicExpr86.getTree());

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
    // Lustre.g:166:1: atomicExpr : ( ID -> ^( IDENT ID ) | INT | real | BOOL | IF ^ expr 'then' ! expr 'else' ! expr | ID '(' ( expr ( ',' expr )* )? ')' -> ^( NODECALL ID ( expr )* ) | '(' expr ')' -> expr );
    public final LustreParser.atomicExpr_return atomicExpr() throws RecognitionException {
        LustreParser.atomicExpr_return retval = new LustreParser.atomicExpr_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token ID87=null;
        Token INT88=null;
        Token BOOL90=null;
        Token IF91=null;
        Token string_literal93=null;
        Token string_literal95=null;
        Token ID97=null;
        Token char_literal98=null;
        Token char_literal100=null;
        Token char_literal102=null;
        Token char_literal103=null;
        Token char_literal105=null;
        LustreParser.real_return real89 =null;

        LustreParser.expr_return expr92 =null;

        LustreParser.expr_return expr94 =null;

        LustreParser.expr_return expr96 =null;

        LustreParser.expr_return expr99 =null;

        LustreParser.expr_return expr101 =null;

        LustreParser.expr_return expr104 =null;


        Object ID87_tree=null;
        Object INT88_tree=null;
        Object BOOL90_tree=null;
        Object IF91_tree=null;
        Object string_literal93_tree=null;
        Object string_literal95_tree=null;
        Object ID97_tree=null;
        Object char_literal98_tree=null;
        Object char_literal100_tree=null;
        Object char_literal102_tree=null;
        Object char_literal103_tree=null;
        Object char_literal105_tree=null;
        RewriteRuleTokenStream stream_31=new RewriteRuleTokenStream(adaptor,"token 31");
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleTokenStream stream_27=new RewriteRuleTokenStream(adaptor,"token 27");
        RewriteRuleTokenStream stream_28=new RewriteRuleTokenStream(adaptor,"token 28");
        RewriteRuleSubtreeStream stream_expr=new RewriteRuleSubtreeStream(adaptor,"rule expr");
        try {
            // Lustre.g:166:11: ( ID -> ^( IDENT ID ) | INT | real | BOOL | IF ^ expr 'then' ! expr 'else' ! expr | ID '(' ( expr ( ',' expr )* )? ')' -> ^( NODECALL ID ( expr )* ) | '(' expr ')' -> expr )
            int alt20=7;
            switch ( input.LA(1) ) {
            case ID:
                {
                int LA20_1 = input.LA(2);

                if ( (LA20_1==27) ) {
                    alt20=6;
                }
                else if ( ((LA20_1 >= 28 && LA20_1 <= 32)||(LA20_1 >= 34 && LA20_1 <= 35)||(LA20_1 >= 37 && LA20_1 <= 44)||LA20_1==47||(LA20_1 >= 50 && LA20_1 <= 51)||LA20_1==56||LA20_1==61||LA20_1==63) ) {
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
                else if ( ((LA20_2 >= 28 && LA20_2 <= 32)||(LA20_2 >= 34 && LA20_2 <= 35)||(LA20_2 >= 37 && LA20_2 <= 44)||LA20_2==47||(LA20_2 >= 50 && LA20_2 <= 51)||LA20_2==56||LA20_2==61||LA20_2==63) ) {
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
            case 27:
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
                    // Lustre.g:167:3: ID
                    {
                    ID87=(Token)match(input,ID,FOLLOW_ID_in_atomicExpr800); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_ID.add(ID87);


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
                    // 167:6: -> ^( IDENT ID )
                    {
                        // Lustre.g:167:9: ^( IDENT ID )
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
                    // Lustre.g:168:3: INT
                    {
                    root_0 = (Object)adaptor.nil();


                    INT88=(Token)match(input,INT,FOLLOW_INT_in_atomicExpr812); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    INT88_tree = 
                    (Object)adaptor.create(INT88)
                    ;
                    adaptor.addChild(root_0, INT88_tree);
                    }

                    }
                    break;
                case 3 :
                    // Lustre.g:169:3: real
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_real_in_atomicExpr816);
                    real89=real();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, real89.getTree());

                    }
                    break;
                case 4 :
                    // Lustre.g:170:3: BOOL
                    {
                    root_0 = (Object)adaptor.nil();


                    BOOL90=(Token)match(input,BOOL,FOLLOW_BOOL_in_atomicExpr820); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    BOOL90_tree = 
                    (Object)adaptor.create(BOOL90)
                    ;
                    adaptor.addChild(root_0, BOOL90_tree);
                    }

                    }
                    break;
                case 5 :
                    // Lustre.g:171:3: IF ^ expr 'then' ! expr 'else' ! expr
                    {
                    root_0 = (Object)adaptor.nil();


                    IF91=(Token)match(input,IF,FOLLOW_IF_in_atomicExpr824); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    IF91_tree = 
                    (Object)adaptor.create(IF91)
                    ;
                    root_0 = (Object)adaptor.becomeRoot(IF91_tree, root_0);
                    }

                    pushFollow(FOLLOW_expr_in_atomicExpr827);
                    expr92=expr();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, expr92.getTree());

                    string_literal93=(Token)match(input,61,FOLLOW_61_in_atomicExpr829); if (state.failed) return retval;

                    pushFollow(FOLLOW_expr_in_atomicExpr832);
                    expr94=expr();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, expr94.getTree());

                    string_literal95=(Token)match(input,51,FOLLOW_51_in_atomicExpr834); if (state.failed) return retval;

                    pushFollow(FOLLOW_expr_in_atomicExpr837);
                    expr96=expr();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, expr96.getTree());

                    }
                    break;
                case 6 :
                    // Lustre.g:172:3: ID '(' ( expr ( ',' expr )* )? ')'
                    {
                    ID97=(Token)match(input,ID,FOLLOW_ID_in_atomicExpr841); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_ID.add(ID97);


                    char_literal98=(Token)match(input,27,FOLLOW_27_in_atomicExpr843); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_27.add(char_literal98);


                    // Lustre.g:172:10: ( expr ( ',' expr )* )?
                    int alt19=2;
                    int LA19_0 = input.LA(1);

                    if ( (LA19_0==BOOL||LA19_0==ID||LA19_0==IF||LA19_0==INT||LA19_0==NOT||LA19_0==PRE||LA19_0==27||LA19_0==32) ) {
                        alt19=1;
                    }
                    switch (alt19) {
                        case 1 :
                            // Lustre.g:172:11: expr ( ',' expr )*
                            {
                            pushFollow(FOLLOW_expr_in_atomicExpr846);
                            expr99=expr();

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) stream_expr.add(expr99.getTree());

                            // Lustre.g:172:16: ( ',' expr )*
                            loop18:
                            do {
                                int alt18=2;
                                int LA18_0 = input.LA(1);

                                if ( (LA18_0==31) ) {
                                    alt18=1;
                                }


                                switch (alt18) {
                            	case 1 :
                            	    // Lustre.g:172:17: ',' expr
                            	    {
                            	    char_literal100=(Token)match(input,31,FOLLOW_31_in_atomicExpr849); if (state.failed) return retval; 
                            	    if ( state.backtracking==0 ) stream_31.add(char_literal100);


                            	    pushFollow(FOLLOW_expr_in_atomicExpr851);
                            	    expr101=expr();

                            	    state._fsp--;
                            	    if (state.failed) return retval;
                            	    if ( state.backtracking==0 ) stream_expr.add(expr101.getTree());

                            	    }
                            	    break;

                            	default :
                            	    break loop18;
                                }
                            } while (true);


                            }
                            break;

                    }


                    char_literal102=(Token)match(input,28,FOLLOW_28_in_atomicExpr857); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_28.add(char_literal102);


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
                    // 172:34: -> ^( NODECALL ID ( expr )* )
                    {
                        // Lustre.g:172:37: ^( NODECALL ID ( expr )* )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot(
                        (Object)adaptor.create(NODECALL, "NODECALL")
                        , root_1);

                        adaptor.addChild(root_1, 
                        stream_ID.nextNode()
                        );

                        // Lustre.g:172:51: ( expr )*
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
                    // Lustre.g:173:3: '(' expr ')'
                    {
                    char_literal103=(Token)match(input,27,FOLLOW_27_in_atomicExpr872); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_27.add(char_literal103);


                    pushFollow(FOLLOW_expr_in_atomicExpr874);
                    expr104=expr();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_expr.add(expr104.getTree());

                    char_literal105=(Token)match(input,28,FOLLOW_28_in_atomicExpr876); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_28.add(char_literal105);


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
                    // 173:16: -> expr
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
    // Lustre.g:176:1: real : a= INT '.' b= INT ->;
    public final LustreParser.real_return real() throws RecognitionException {
        LustreParser.real_return retval = new LustreParser.real_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token a=null;
        Token b=null;
        Token char_literal106=null;

        Object a_tree=null;
        Object b_tree=null;
        Object char_literal106_tree=null;
        RewriteRuleTokenStream stream_INT=new RewriteRuleTokenStream(adaptor,"token INT");
        RewriteRuleTokenStream stream_ERROR=new RewriteRuleTokenStream(adaptor,"token ERROR");

        try {
            // Lustre.g:176:5: (a= INT '.' b= INT ->)
            // Lustre.g:176:7: a= INT '.' b= INT
            {
            a=(Token)match(input,INT,FOLLOW_INT_in_real890); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_INT.add(a);


            char_literal106=(Token)match(input,ERROR,FOLLOW_ERROR_in_real892); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_ERROR.add(char_literal106);


            b=(Token)match(input,INT,FOLLOW_INT_in_real896); if (state.failed) return retval; 
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
            // 176:23: ->
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
        // Lustre.g:108:16: ( arrowOp )
        // Lustre.g:108:17: arrowOp
        {
        pushFollow(FOLLOW_arrowOp_in_synpred1_Lustre516);
        arrowOp();

        state._fsp--;
        if (state.failed) return ;

        }

    }
    // $ANTLR end synpred1_Lustre

    // $ANTLR start synpred2_Lustre
    public final void synpred2_Lustre_fragment() throws RecognitionException {
        // Lustre.g:116:11: ( impliesOp )
        // Lustre.g:116:12: impliesOp
        {
        pushFollow(FOLLOW_impliesOp_in_synpred2_Lustre548);
        impliesOp();

        state._fsp--;
        if (state.failed) return ;

        }

    }
    // $ANTLR end synpred2_Lustre

    // $ANTLR start synpred3_Lustre
    public final void synpred3_Lustre_fragment() throws RecognitionException {
        // Lustre.g:124:12: ( orOp )
        // Lustre.g:124:13: orOp
        {
        pushFollow(FOLLOW_orOp_in_synpred3_Lustre584);
        orOp();

        state._fsp--;
        if (state.failed) return ;

        }

    }
    // $ANTLR end synpred3_Lustre

    // $ANTLR start synpred4_Lustre
    public final void synpred4_Lustre_fragment() throws RecognitionException {
        // Lustre.g:132:19: ( andOp )
        // Lustre.g:132:20: andOp
        {
        pushFollow(FOLLOW_andOp_in_synpred4_Lustre616);
        andOp();

        state._fsp--;
        if (state.failed) return ;

        }

    }
    // $ANTLR end synpred4_Lustre

    // $ANTLR start synpred5_Lustre
    public final void synpred5_Lustre_fragment() throws RecognitionException {
        // Lustre.g:140:13: ( relationalOp )
        // Lustre.g:140:14: relationalOp
        {
        pushFollow(FOLLOW_relationalOp_in_synpred5_Lustre668);
        relationalOp();

        state._fsp--;
        if (state.failed) return ;

        }

    }
    // $ANTLR end synpred5_Lustre

    // $ANTLR start synpred6_Lustre
    public final void synpred6_Lustre_fragment() throws RecognitionException {
        // Lustre.g:148:14: ( plusOp )
        // Lustre.g:148:15: plusOp
        {
        pushFollow(FOLLOW_plusOp_in_synpred6_Lustre704);
        plusOp();

        state._fsp--;
        if (state.failed) return ;

        }

    }
    // $ANTLR end synpred6_Lustre

    // $ANTLR start synpred7_Lustre
    public final void synpred7_Lustre_fragment() throws RecognitionException {
        // Lustre.g:156:15: ( timesOp )
        // Lustre.g:156:16: timesOp
        {
        pushFollow(FOLLOW_timesOp_in_synpred7_Lustre744);
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


 

    public static final BitSet FOLLOW_constant_in_program130 = new BitSet(new long[]{0x0042000000000002L});
    public static final BitSet FOLLOW_node_in_program134 = new BitSet(new long[]{0x0042000000000002L});
    public static final BitSet FOLLOW_54_in_node166 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_ID_in_node168 = new BitSet(new long[]{0x0000000008000000L});
    public static final BitSet FOLLOW_27_in_node170 = new BitSet(new long[]{0x0000000010000100L});
    public static final BitSet FOLLOW_varDeclList_in_node174 = new BitSet(new long[]{0x0000000010000000L});
    public static final BitSet FOLLOW_28_in_node177 = new BitSet(new long[]{0x0400000000000000L});
    public static final BitSet FOLLOW_58_in_node181 = new BitSet(new long[]{0x0000000008000000L});
    public static final BitSet FOLLOW_27_in_node183 = new BitSet(new long[]{0x0000000010000100L});
    public static final BitSet FOLLOW_varDeclList_in_node187 = new BitSet(new long[]{0x0000000010000000L});
    public static final BitSet FOLLOW_28_in_node190 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_37_in_node192 = new BitSet(new long[]{0x4020000000000000L});
    public static final BitSet FOLLOW_62_in_node197 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_varDeclList_in_node201 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_37_in_node203 = new BitSet(new long[]{0x0020000000000000L});
    public static final BitSet FOLLOW_53_in_node209 = new BitSet(new long[]{0x1000000200000100L});
    public static final BitSet FOLLOW_equation_in_node216 = new BitSet(new long[]{0x1000000200000100L});
    public static final BitSet FOLLOW_property_in_node220 = new BitSet(new long[]{0x1000000200000100L});
    public static final BitSet FOLLOW_60_in_node226 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_37_in_node228 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_49_in_constant316 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_ID_in_constant318 = new BitSet(new long[]{0x0000020000000000L});
    public static final BitSet FOLLOW_41_in_constant320 = new BitSet(new long[]{0x0000000108281510L});
    public static final BitSet FOLLOW_expr_in_constant322 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_37_in_constant324 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_varDeclGroup_in_varDeclList342 = new BitSet(new long[]{0x0000002000000002L});
    public static final BitSet FOLLOW_37_in_varDeclList345 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_varDeclGroup_in_varDeclList347 = new BitSet(new long[]{0x0000002000000002L});
    public static final BitSet FOLLOW_ID_in_varDeclGroup364 = new BitSet(new long[]{0x0000001080000000L});
    public static final BitSet FOLLOW_31_in_varDeclGroup367 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_ID_in_varDeclGroup369 = new BitSet(new long[]{0x0000001080000000L});
    public static final BitSet FOLLOW_36_in_varDeclGroup373 = new BitSet(new long[]{0x0A11000000000000L});
    public static final BitSet FOLLOW_type_in_varDeclGroup375 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_52_in_type394 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_59_in_type399 = new BitSet(new long[]{0x0000200000000000L});
    public static final BitSet FOLLOW_45_in_type401 = new BitSet(new long[]{0x0000000100001000L});
    public static final BitSet FOLLOW_bound_in_type403 = new BitSet(new long[]{0x0000000080000000L});
    public static final BitSet FOLLOW_31_in_type405 = new BitSet(new long[]{0x0000000100001000L});
    public static final BitSet FOLLOW_bound_in_type407 = new BitSet(new long[]{0x0000400000000000L});
    public static final BitSet FOLLOW_46_in_type409 = new BitSet(new long[]{0x0080000000000000L});
    public static final BitSet FOLLOW_55_in_type411 = new BitSet(new long[]{0x0010000000000000L});
    public static final BitSet FOLLOW_52_in_type413 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_48_in_type421 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_57_in_type426 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_32_in_bound437 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_INT_in_bound440 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_33_in_property450 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_ID_in_property452 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_37_in_property454 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_equation468 = new BitSet(new long[]{0x0000020000000000L});
    public static final BitSet FOLLOW_41_in_equation470 = new BitSet(new long[]{0x0000000108281510L});
    public static final BitSet FOLLOW_expr_in_equation472 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_37_in_equation474 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_arrowExpr_in_expr492 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_34_in_arrowOp502 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_impliesExpr_in_arrowExpr512 = new BitSet(new long[]{0x0000000400000002L});
    public static final BitSet FOLLOW_arrowOp_in_arrowExpr519 = new BitSet(new long[]{0x0000000108281510L});
    public static final BitSet FOLLOW_arrowExpr_in_arrowExpr522 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_42_in_impliesOp534 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_orExpr_in_impliesExpr544 = new BitSet(new long[]{0x0000040000000002L});
    public static final BitSet FOLLOW_impliesOp_in_impliesExpr551 = new BitSet(new long[]{0x0000000108281510L});
    public static final BitSet FOLLOW_impliesExpr_in_impliesExpr554 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_andExpr_in_orExpr580 = new BitSet(new long[]{0x8100000000000002L});
    public static final BitSet FOLLOW_orOp_in_orExpr587 = new BitSet(new long[]{0x0000000108281510L});
    public static final BitSet FOLLOW_andExpr_in_orExpr590 = new BitSet(new long[]{0x8100000000000002L});
    public static final BitSet FOLLOW_47_in_andOp602 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_relationalExpr_in_andExpr612 = new BitSet(new long[]{0x0000800000000002L});
    public static final BitSet FOLLOW_andOp_in_andExpr619 = new BitSet(new long[]{0x0000000108281510L});
    public static final BitSet FOLLOW_relationalExpr_in_andExpr622 = new BitSet(new long[]{0x0000800000000002L});
    public static final BitSet FOLLOW_plusExpr_in_relationalExpr664 = new BitSet(new long[]{0x00001BC000000002L});
    public static final BitSet FOLLOW_relationalOp_in_relationalExpr671 = new BitSet(new long[]{0x0000000108281510L});
    public static final BitSet FOLLOW_plusExpr_in_relationalExpr674 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_timesExpr_in_plusExpr700 = new BitSet(new long[]{0x0000000140000002L});
    public static final BitSet FOLLOW_plusOp_in_plusExpr707 = new BitSet(new long[]{0x0000000108281510L});
    public static final BitSet FOLLOW_timesExpr_in_plusExpr710 = new BitSet(new long[]{0x0000000140000002L});
    public static final BitSet FOLLOW_prefixExpr_in_timesExpr740 = new BitSet(new long[]{0x0004000820000002L});
    public static final BitSet FOLLOW_timesOp_in_timesExpr747 = new BitSet(new long[]{0x0000000108281510L});
    public static final BitSet FOLLOW_prefixExpr_in_timesExpr750 = new BitSet(new long[]{0x0004000820000002L});
    public static final BitSet FOLLOW_32_in_prefixExpr762 = new BitSet(new long[]{0x0000000108281510L});
    public static final BitSet FOLLOW_prefixExpr_in_prefixExpr764 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NOT_in_prefixExpr776 = new BitSet(new long[]{0x0000000108281510L});
    public static final BitSet FOLLOW_prefixExpr_in_prefixExpr779 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_PRE_in_prefixExpr783 = new BitSet(new long[]{0x0000000108281510L});
    public static final BitSet FOLLOW_prefixExpr_in_prefixExpr786 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_atomicExpr_in_prefixExpr790 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_atomicExpr800 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INT_in_atomicExpr812 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_real_in_atomicExpr816 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_BOOL_in_atomicExpr820 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IF_in_atomicExpr824 = new BitSet(new long[]{0x0000000108281510L});
    public static final BitSet FOLLOW_expr_in_atomicExpr827 = new BitSet(new long[]{0x2000000000000000L});
    public static final BitSet FOLLOW_61_in_atomicExpr829 = new BitSet(new long[]{0x0000000108281510L});
    public static final BitSet FOLLOW_expr_in_atomicExpr832 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_51_in_atomicExpr834 = new BitSet(new long[]{0x0000000108281510L});
    public static final BitSet FOLLOW_expr_in_atomicExpr837 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_atomicExpr841 = new BitSet(new long[]{0x0000000008000000L});
    public static final BitSet FOLLOW_27_in_atomicExpr843 = new BitSet(new long[]{0x0000000118281510L});
    public static final BitSet FOLLOW_expr_in_atomicExpr846 = new BitSet(new long[]{0x0000000090000000L});
    public static final BitSet FOLLOW_31_in_atomicExpr849 = new BitSet(new long[]{0x0000000108281510L});
    public static final BitSet FOLLOW_expr_in_atomicExpr851 = new BitSet(new long[]{0x0000000090000000L});
    public static final BitSet FOLLOW_28_in_atomicExpr857 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_27_in_atomicExpr872 = new BitSet(new long[]{0x0000000108281510L});
    public static final BitSet FOLLOW_expr_in_atomicExpr874 = new BitSet(new long[]{0x0000000010000000L});
    public static final BitSet FOLLOW_28_in_atomicExpr876 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INT_in_real890 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_ERROR_in_real892 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_INT_in_real896 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_arrowOp_in_synpred1_Lustre516 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_impliesOp_in_synpred2_Lustre548 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_orOp_in_synpred3_Lustre584 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_andOp_in_synpred4_Lustre616 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_relationalOp_in_synpred5_Lustre668 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_plusOp_in_synpred6_Lustre704 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_timesOp_in_synpred7_Lustre744 = new BitSet(new long[]{0x0000000000000002L});

}