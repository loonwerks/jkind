// $ANTLR 3.5 Lustre.g 2013-02-12 10:12:16

  package jkind.lustre;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import org.antlr.runtime.tree.*;


@SuppressWarnings("all")
public class LustreParser extends Parser {
	public static final String[] tokenNames = new String[] {
		"<invalid>", "<EOR>", "<DOWN>", "<UP>", "ASSERTIONS", "BOOL", "CONSTANTS", 
		"EQUATION", "EQUATIONS", "ERROR", "ID", "IF", "INPUTS", "INT", "LHS", 
		"LOCALS", "MAIN", "ML_COMMENT", "NEGATE", "NODECALL", "NODES", "NOT", 
		"OUTPUTS", "PRE", "PROGRAM", "PROPERTIES", "REAL", "SL_COMMENT", "TYPES", 
		"WS", "'('", "')'", "'*'", "'+'", "','", "'-'", "'--%PROPERTY'", "'->'", 
		"'/'", "':'", "';'", "'<'", "'<='", "'<>'", "'='", "'=>'", "'>'", "'>='", 
		"'['", "']'", "'and'", "'assert'", "'bool'", "'const'", "'div'", "'else'", 
		"'int'", "'let'", "'node'", "'of'", "'or'", "'real'", "'returns'", "'subrange'", 
		"'tel'", "'then'", "'type'", "'var'", "'xor'"
	};
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
	public static final int T__68=68;
	public static final int ASSERTIONS=4;
	public static final int BOOL=5;
	public static final int CONSTANTS=6;
	public static final int EQUATION=7;
	public static final int EQUATIONS=8;
	public static final int ERROR=9;
	public static final int ID=10;
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
	@Override public String[] getTokenNames() { return LustreParser.tokenNames; }
	@Override public String getGrammarFileName() { return "Lustre.g"; }


	  protected void ignore(Stack<Void> stack, List<Void> list, ArrayList<Void> arraylist) {}

	  @Override
	  public void emitErrorMessage(String msg) {
	    System.out.println(msg);
	  }


	public static class program_return extends ParserRuleReturnScope {
		Object tree;
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "program"
	// Lustre.g:47:1: program : ( typedef | constant | node )* EOF -> ^( PROGRAM ^( TYPES ( typedef )* ) ^( CONSTANTS ( constant )* ) ^( NODES ( node )* ) ) ;
	public final LustreParser.program_return program() throws RecognitionException {
		LustreParser.program_return retval = new LustreParser.program_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		Token EOF4=null;
		ParserRuleReturnScope typedef1 =null;
		ParserRuleReturnScope constant2 =null;
		ParserRuleReturnScope node3 =null;

		Object EOF4_tree=null;
		RewriteRuleTokenStream stream_EOF=new RewriteRuleTokenStream(adaptor,"token EOF");
		RewriteRuleSubtreeStream stream_node=new RewriteRuleSubtreeStream(adaptor,"rule node");
		RewriteRuleSubtreeStream stream_constant=new RewriteRuleSubtreeStream(adaptor,"rule constant");
		RewriteRuleSubtreeStream stream_typedef=new RewriteRuleSubtreeStream(adaptor,"rule typedef");

		try {
			// Lustre.g:47:8: ( ( typedef | constant | node )* EOF -> ^( PROGRAM ^( TYPES ( typedef )* ) ^( CONSTANTS ( constant )* ) ^( NODES ( node )* ) ) )
			// Lustre.g:48:3: ( typedef | constant | node )* EOF
			{
			// Lustre.g:48:3: ( typedef | constant | node )*
			loop1:
			while (true) {
				int alt1=4;
				switch ( input.LA(1) ) {
				case 66:
					{
					alt1=1;
					}
					break;
				case 53:
					{
					alt1=2;
					}
					break;
				case 58:
					{
					alt1=3;
					}
					break;
				}
				switch (alt1) {
				case 1 :
					// Lustre.g:48:4: typedef
					{
					pushFollow(FOLLOW_typedef_in_program145);
					typedef1=typedef();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) stream_typedef.add(typedef1.getTree());
					}
					break;
				case 2 :
					// Lustre.g:48:14: constant
					{
					pushFollow(FOLLOW_constant_in_program149);
					constant2=constant();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) stream_constant.add(constant2.getTree());
					}
					break;
				case 3 :
					// Lustre.g:48:25: node
					{
					pushFollow(FOLLOW_node_in_program153);
					node3=node();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) stream_node.add(node3.getTree());
					}
					break;

				default :
					break loop1;
				}
			}

			EOF4=(Token)match(input,EOF,FOLLOW_EOF_in_program157); if (state.failed) return retval; 
			if ( state.backtracking==0 ) stream_EOF.add(EOF4);

			// AST REWRITE
			// elements: node, typedef, constant
			// token labels: 
			// rule labels: retval
			// token list labels: 
			// rule list labels: 
			// wildcard labels: 
			if ( state.backtracking==0 ) {
			retval.tree = root_0;
			RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.getTree():null);

			root_0 = (Object)adaptor.nil();
			// 48:36: -> ^( PROGRAM ^( TYPES ( typedef )* ) ^( CONSTANTS ( constant )* ) ^( NODES ( node )* ) )
			{
				// Lustre.g:49:5: ^( PROGRAM ^( TYPES ( typedef )* ) ^( CONSTANTS ( constant )* ) ^( NODES ( node )* ) )
				{
				Object root_1 = (Object)adaptor.nil();
				root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(PROGRAM, "PROGRAM"), root_1);
				// Lustre.g:50:7: ^( TYPES ( typedef )* )
				{
				Object root_2 = (Object)adaptor.nil();
				root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(TYPES, "TYPES"), root_2);
				// Lustre.g:50:15: ( typedef )*
				while ( stream_typedef.hasNext() ) {
					adaptor.addChild(root_2, stream_typedef.nextTree());
				}
				stream_typedef.reset();

				adaptor.addChild(root_1, root_2);
				}

				// Lustre.g:51:7: ^( CONSTANTS ( constant )* )
				{
				Object root_2 = (Object)adaptor.nil();
				root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(CONSTANTS, "CONSTANTS"), root_2);
				// Lustre.g:51:19: ( constant )*
				while ( stream_constant.hasNext() ) {
					adaptor.addChild(root_2, stream_constant.nextTree());
				}
				stream_constant.reset();

				adaptor.addChild(root_1, root_2);
				}

				// Lustre.g:52:7: ^( NODES ( node )* )
				{
				Object root_2 = (Object)adaptor.nil();
				root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(NODES, "NODES"), root_2);
				// Lustre.g:52:15: ( node )*
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
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "typedef"
	// Lustre.g:55:1: typedef : 'type' ID '=' type ';' -> ^( ID type ) ;
	public final LustreParser.typedef_return typedef() throws RecognitionException {
		LustreParser.typedef_return retval = new LustreParser.typedef_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		Token string_literal5=null;
		Token ID6=null;
		Token char_literal7=null;
		Token char_literal9=null;
		ParserRuleReturnScope type8 =null;

		Object string_literal5_tree=null;
		Object ID6_tree=null;
		Object char_literal7_tree=null;
		Object char_literal9_tree=null;
		RewriteRuleTokenStream stream_66=new RewriteRuleTokenStream(adaptor,"token 66");
		RewriteRuleTokenStream stream_44=new RewriteRuleTokenStream(adaptor,"token 44");
		RewriteRuleTokenStream stream_40=new RewriteRuleTokenStream(adaptor,"token 40");
		RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
		RewriteRuleSubtreeStream stream_type=new RewriteRuleSubtreeStream(adaptor,"rule type");

		try {
			// Lustre.g:55:8: ( 'type' ID '=' type ';' -> ^( ID type ) )
			// Lustre.g:56:3: 'type' ID '=' type ';'
			{
			string_literal5=(Token)match(input,66,FOLLOW_66_in_typedef216); if (state.failed) return retval; 
			if ( state.backtracking==0 ) stream_66.add(string_literal5);

			ID6=(Token)match(input,ID,FOLLOW_ID_in_typedef218); if (state.failed) return retval; 
			if ( state.backtracking==0 ) stream_ID.add(ID6);

			char_literal7=(Token)match(input,44,FOLLOW_44_in_typedef220); if (state.failed) return retval; 
			if ( state.backtracking==0 ) stream_44.add(char_literal7);

			pushFollow(FOLLOW_type_in_typedef222);
			type8=type();
			state._fsp--;
			if (state.failed) return retval;
			if ( state.backtracking==0 ) stream_type.add(type8.getTree());
			char_literal9=(Token)match(input,40,FOLLOW_40_in_typedef224); if (state.failed) return retval; 
			if ( state.backtracking==0 ) stream_40.add(char_literal9);

			// AST REWRITE
			// elements: type, ID
			// token labels: 
			// rule labels: retval
			// token list labels: 
			// rule list labels: 
			// wildcard labels: 
			if ( state.backtracking==0 ) {
			retval.tree = root_0;
			RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.getTree():null);

			root_0 = (Object)adaptor.nil();
			// 56:26: -> ^( ID type )
			{
				// Lustre.g:56:29: ^( ID type )
				{
				Object root_1 = (Object)adaptor.nil();
				root_1 = (Object)adaptor.becomeRoot(stream_ID.nextNode(), root_1);
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
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "constant"
	// Lustre.g:59:1: constant : 'const' ID '=' expr ';' -> ^( ID expr ) ;
	public final LustreParser.constant_return constant() throws RecognitionException {
		LustreParser.constant_return retval = new LustreParser.constant_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		Token string_literal10=null;
		Token ID11=null;
		Token char_literal12=null;
		Token char_literal14=null;
		ParserRuleReturnScope expr13 =null;

		Object string_literal10_tree=null;
		Object ID11_tree=null;
		Object char_literal12_tree=null;
		Object char_literal14_tree=null;
		RewriteRuleTokenStream stream_44=new RewriteRuleTokenStream(adaptor,"token 44");
		RewriteRuleTokenStream stream_40=new RewriteRuleTokenStream(adaptor,"token 40");
		RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
		RewriteRuleTokenStream stream_53=new RewriteRuleTokenStream(adaptor,"token 53");
		RewriteRuleSubtreeStream stream_expr=new RewriteRuleSubtreeStream(adaptor,"rule expr");

		try {
			// Lustre.g:59:9: ( 'const' ID '=' expr ';' -> ^( ID expr ) )
			// Lustre.g:60:3: 'const' ID '=' expr ';'
			{
			string_literal10=(Token)match(input,53,FOLLOW_53_in_constant242); if (state.failed) return retval; 
			if ( state.backtracking==0 ) stream_53.add(string_literal10);

			ID11=(Token)match(input,ID,FOLLOW_ID_in_constant244); if (state.failed) return retval; 
			if ( state.backtracking==0 ) stream_ID.add(ID11);

			char_literal12=(Token)match(input,44,FOLLOW_44_in_constant246); if (state.failed) return retval; 
			if ( state.backtracking==0 ) stream_44.add(char_literal12);

			pushFollow(FOLLOW_expr_in_constant248);
			expr13=expr();
			state._fsp--;
			if (state.failed) return retval;
			if ( state.backtracking==0 ) stream_expr.add(expr13.getTree());
			char_literal14=(Token)match(input,40,FOLLOW_40_in_constant250); if (state.failed) return retval; 
			if ( state.backtracking==0 ) stream_40.add(char_literal14);

			// AST REWRITE
			// elements: expr, ID
			// token labels: 
			// rule labels: retval
			// token list labels: 
			// rule list labels: 
			// wildcard labels: 
			if ( state.backtracking==0 ) {
			retval.tree = root_0;
			RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.getTree():null);

			root_0 = (Object)adaptor.nil();
			// 60:27: -> ^( ID expr )
			{
				// Lustre.g:60:30: ^( ID expr )
				{
				Object root_1 = (Object)adaptor.nil();
				root_1 = (Object)adaptor.becomeRoot(stream_ID.nextNode(), root_1);
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
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "node"
	// Lustre.g:63:1: node : 'node' ID '(' (inputs= varDeclList )? ')' 'returns' '(' (outputs= varDeclList )? ')' ';' ( 'var' locals= varDeclList ';' )? 'let' ( equation | property | assertion )* 'tel' ';' -> ^( ID ^( INPUTS ( $inputs)? ) ^( OUTPUTS ( $outputs)? ) ^( LOCALS ( $locals)? ) ^( EQUATIONS ( equation )* ) ^( PROPERTIES ( property )* ) ^( ASSERTIONS ( assertion )* ) ) ;
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
		Token string_literal29=null;
		Token char_literal30=null;
		ParserRuleReturnScope inputs =null;
		ParserRuleReturnScope outputs =null;
		ParserRuleReturnScope locals =null;
		ParserRuleReturnScope equation26 =null;
		ParserRuleReturnScope property27 =null;
		ParserRuleReturnScope assertion28 =null;

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
		Object string_literal29_tree=null;
		Object char_literal30_tree=null;
		RewriteRuleTokenStream stream_67=new RewriteRuleTokenStream(adaptor,"token 67");
		RewriteRuleTokenStream stream_58=new RewriteRuleTokenStream(adaptor,"token 58");
		RewriteRuleTokenStream stream_30=new RewriteRuleTokenStream(adaptor,"token 30");
		RewriteRuleTokenStream stream_57=new RewriteRuleTokenStream(adaptor,"token 57");
		RewriteRuleTokenStream stream_31=new RewriteRuleTokenStream(adaptor,"token 31");
		RewriteRuleTokenStream stream_40=new RewriteRuleTokenStream(adaptor,"token 40");
		RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
		RewriteRuleTokenStream stream_64=new RewriteRuleTokenStream(adaptor,"token 64");
		RewriteRuleTokenStream stream_62=new RewriteRuleTokenStream(adaptor,"token 62");
		RewriteRuleSubtreeStream stream_equation=new RewriteRuleSubtreeStream(adaptor,"rule equation");
		RewriteRuleSubtreeStream stream_varDeclList=new RewriteRuleSubtreeStream(adaptor,"rule varDeclList");
		RewriteRuleSubtreeStream stream_property=new RewriteRuleSubtreeStream(adaptor,"rule property");
		RewriteRuleSubtreeStream stream_assertion=new RewriteRuleSubtreeStream(adaptor,"rule assertion");

		try {
			// Lustre.g:63:5: ( 'node' ID '(' (inputs= varDeclList )? ')' 'returns' '(' (outputs= varDeclList )? ')' ';' ( 'var' locals= varDeclList ';' )? 'let' ( equation | property | assertion )* 'tel' ';' -> ^( ID ^( INPUTS ( $inputs)? ) ^( OUTPUTS ( $outputs)? ) ^( LOCALS ( $locals)? ) ^( EQUATIONS ( equation )* ) ^( PROPERTIES ( property )* ) ^( ASSERTIONS ( assertion )* ) ) )
			// Lustre.g:64:3: 'node' ID '(' (inputs= varDeclList )? ')' 'returns' '(' (outputs= varDeclList )? ')' ';' ( 'var' locals= varDeclList ';' )? 'let' ( equation | property | assertion )* 'tel' ';'
			{
			string_literal15=(Token)match(input,58,FOLLOW_58_in_node268); if (state.failed) return retval; 
			if ( state.backtracking==0 ) stream_58.add(string_literal15);

			ID16=(Token)match(input,ID,FOLLOW_ID_in_node270); if (state.failed) return retval; 
			if ( state.backtracking==0 ) stream_ID.add(ID16);

			char_literal17=(Token)match(input,30,FOLLOW_30_in_node272); if (state.failed) return retval; 
			if ( state.backtracking==0 ) stream_30.add(char_literal17);

			// Lustre.g:64:23: (inputs= varDeclList )?
			int alt2=2;
			int LA2_0 = input.LA(1);
			if ( (LA2_0==ID) ) {
				alt2=1;
			}
			switch (alt2) {
				case 1 :
					// Lustre.g:64:23: inputs= varDeclList
					{
					pushFollow(FOLLOW_varDeclList_in_node276);
					inputs=varDeclList();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) stream_varDeclList.add(inputs.getTree());
					}
					break;

			}

			char_literal18=(Token)match(input,31,FOLLOW_31_in_node279); if (state.failed) return retval; 
			if ( state.backtracking==0 ) stream_31.add(char_literal18);

			string_literal19=(Token)match(input,62,FOLLOW_62_in_node283); if (state.failed) return retval; 
			if ( state.backtracking==0 ) stream_62.add(string_literal19);

			char_literal20=(Token)match(input,30,FOLLOW_30_in_node285); if (state.failed) return retval; 
			if ( state.backtracking==0 ) stream_30.add(char_literal20);

			// Lustre.g:65:24: (outputs= varDeclList )?
			int alt3=2;
			int LA3_0 = input.LA(1);
			if ( (LA3_0==ID) ) {
				alt3=1;
			}
			switch (alt3) {
				case 1 :
					// Lustre.g:65:24: outputs= varDeclList
					{
					pushFollow(FOLLOW_varDeclList_in_node289);
					outputs=varDeclList();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) stream_varDeclList.add(outputs.getTree());
					}
					break;

			}

			char_literal21=(Token)match(input,31,FOLLOW_31_in_node292); if (state.failed) return retval; 
			if ( state.backtracking==0 ) stream_31.add(char_literal21);

			char_literal22=(Token)match(input,40,FOLLOW_40_in_node294); if (state.failed) return retval; 
			if ( state.backtracking==0 ) stream_40.add(char_literal22);

			// Lustre.g:66:3: ( 'var' locals= varDeclList ';' )?
			int alt4=2;
			int LA4_0 = input.LA(1);
			if ( (LA4_0==67) ) {
				alt4=1;
			}
			switch (alt4) {
				case 1 :
					// Lustre.g:66:4: 'var' locals= varDeclList ';'
					{
					string_literal23=(Token)match(input,67,FOLLOW_67_in_node299); if (state.failed) return retval; 
					if ( state.backtracking==0 ) stream_67.add(string_literal23);

					pushFollow(FOLLOW_varDeclList_in_node303);
					locals=varDeclList();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) stream_varDeclList.add(locals.getTree());
					char_literal24=(Token)match(input,40,FOLLOW_40_in_node305); if (state.failed) return retval; 
					if ( state.backtracking==0 ) stream_40.add(char_literal24);

					}
					break;

			}

			string_literal25=(Token)match(input,57,FOLLOW_57_in_node311); if (state.failed) return retval; 
			if ( state.backtracking==0 ) stream_57.add(string_literal25);

			// Lustre.g:68:5: ( equation | property | assertion )*
			loop5:
			while (true) {
				int alt5=4;
				switch ( input.LA(1) ) {
				case ID:
				case 30:
					{
					alt5=1;
					}
					break;
				case 36:
					{
					alt5=2;
					}
					break;
				case 51:
					{
					alt5=3;
					}
					break;
				}
				switch (alt5) {
				case 1 :
					// Lustre.g:68:6: equation
					{
					pushFollow(FOLLOW_equation_in_node318);
					equation26=equation();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) stream_equation.add(equation26.getTree());
					}
					break;
				case 2 :
					// Lustre.g:68:17: property
					{
					pushFollow(FOLLOW_property_in_node322);
					property27=property();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) stream_property.add(property27.getTree());
					}
					break;
				case 3 :
					// Lustre.g:68:28: assertion
					{
					pushFollow(FOLLOW_assertion_in_node326);
					assertion28=assertion();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) stream_assertion.add(assertion28.getTree());
					}
					break;

				default :
					break loop5;
				}
			}

			string_literal29=(Token)match(input,64,FOLLOW_64_in_node332); if (state.failed) return retval; 
			if ( state.backtracking==0 ) stream_64.add(string_literal29);

			char_literal30=(Token)match(input,40,FOLLOW_40_in_node334); if (state.failed) return retval; 
			if ( state.backtracking==0 ) stream_40.add(char_literal30);

			// AST REWRITE
			// elements: inputs, equation, locals, outputs, ID, property, assertion
			// token labels: 
			// rule labels: retval, inputs, locals, outputs
			// token list labels: 
			// rule list labels: 
			// wildcard labels: 
			if ( state.backtracking==0 ) {
			retval.tree = root_0;
			RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.getTree():null);
			RewriteRuleSubtreeStream stream_inputs=new RewriteRuleSubtreeStream(adaptor,"rule inputs",inputs!=null?inputs.getTree():null);
			RewriteRuleSubtreeStream stream_locals=new RewriteRuleSubtreeStream(adaptor,"rule locals",locals!=null?locals.getTree():null);
			RewriteRuleSubtreeStream stream_outputs=new RewriteRuleSubtreeStream(adaptor,"rule outputs",outputs!=null?outputs.getTree():null);

			root_0 = (Object)adaptor.nil();
			// 69:13: -> ^( ID ^( INPUTS ( $inputs)? ) ^( OUTPUTS ( $outputs)? ) ^( LOCALS ( $locals)? ) ^( EQUATIONS ( equation )* ) ^( PROPERTIES ( property )* ) ^( ASSERTIONS ( assertion )* ) )
			{
				// Lustre.g:70:5: ^( ID ^( INPUTS ( $inputs)? ) ^( OUTPUTS ( $outputs)? ) ^( LOCALS ( $locals)? ) ^( EQUATIONS ( equation )* ) ^( PROPERTIES ( property )* ) ^( ASSERTIONS ( assertion )* ) )
				{
				Object root_1 = (Object)adaptor.nil();
				root_1 = (Object)adaptor.becomeRoot(stream_ID.nextNode(), root_1);
				// Lustre.g:71:7: ^( INPUTS ( $inputs)? )
				{
				Object root_2 = (Object)adaptor.nil();
				root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(INPUTS, "INPUTS"), root_2);
				// Lustre.g:71:17: ( $inputs)?
				if ( stream_inputs.hasNext() ) {
					adaptor.addChild(root_2, stream_inputs.nextTree());
				}
				stream_inputs.reset();

				adaptor.addChild(root_1, root_2);
				}

				// Lustre.g:72:7: ^( OUTPUTS ( $outputs)? )
				{
				Object root_2 = (Object)adaptor.nil();
				root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(OUTPUTS, "OUTPUTS"), root_2);
				// Lustre.g:72:18: ( $outputs)?
				if ( stream_outputs.hasNext() ) {
					adaptor.addChild(root_2, stream_outputs.nextTree());
				}
				stream_outputs.reset();

				adaptor.addChild(root_1, root_2);
				}

				// Lustre.g:73:7: ^( LOCALS ( $locals)? )
				{
				Object root_2 = (Object)adaptor.nil();
				root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(LOCALS, "LOCALS"), root_2);
				// Lustre.g:73:17: ( $locals)?
				if ( stream_locals.hasNext() ) {
					adaptor.addChild(root_2, stream_locals.nextTree());
				}
				stream_locals.reset();

				adaptor.addChild(root_1, root_2);
				}

				// Lustre.g:74:7: ^( EQUATIONS ( equation )* )
				{
				Object root_2 = (Object)adaptor.nil();
				root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(EQUATIONS, "EQUATIONS"), root_2);
				// Lustre.g:74:19: ( equation )*
				while ( stream_equation.hasNext() ) {
					adaptor.addChild(root_2, stream_equation.nextTree());
				}
				stream_equation.reset();

				adaptor.addChild(root_1, root_2);
				}

				// Lustre.g:75:7: ^( PROPERTIES ( property )* )
				{
				Object root_2 = (Object)adaptor.nil();
				root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(PROPERTIES, "PROPERTIES"), root_2);
				// Lustre.g:75:20: ( property )*
				while ( stream_property.hasNext() ) {
					adaptor.addChild(root_2, stream_property.nextTree());
				}
				stream_property.reset();

				adaptor.addChild(root_1, root_2);
				}

				// Lustre.g:76:7: ^( ASSERTIONS ( assertion )* )
				{
				Object root_2 = (Object)adaptor.nil();
				root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(ASSERTIONS, "ASSERTIONS"), root_2);
				// Lustre.g:76:20: ( assertion )*
				while ( stream_assertion.hasNext() ) {
					adaptor.addChild(root_2, stream_assertion.nextTree());
				}
				stream_assertion.reset();

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
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "varDeclList"
	// Lustre.g:79:1: varDeclList : varDeclGroup ( ';' varDeclGroup )* -> ( varDeclGroup )+ ;
	public final LustreParser.varDeclList_return varDeclList() throws RecognitionException {
		LustreParser.varDeclList_return retval = new LustreParser.varDeclList_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		Token char_literal32=null;
		ParserRuleReturnScope varDeclGroup31 =null;
		ParserRuleReturnScope varDeclGroup33 =null;

		Object char_literal32_tree=null;
		RewriteRuleTokenStream stream_40=new RewriteRuleTokenStream(adaptor,"token 40");
		RewriteRuleSubtreeStream stream_varDeclGroup=new RewriteRuleSubtreeStream(adaptor,"rule varDeclGroup");

		try {
			// Lustre.g:79:12: ( varDeclGroup ( ';' varDeclGroup )* -> ( varDeclGroup )+ )
			// Lustre.g:80:3: varDeclGroup ( ';' varDeclGroup )*
			{
			pushFollow(FOLLOW_varDeclGroup_in_varDeclList435);
			varDeclGroup31=varDeclGroup();
			state._fsp--;
			if (state.failed) return retval;
			if ( state.backtracking==0 ) stream_varDeclGroup.add(varDeclGroup31.getTree());
			// Lustre.g:80:16: ( ';' varDeclGroup )*
			loop6:
			while (true) {
				int alt6=2;
				int LA6_0 = input.LA(1);
				if ( (LA6_0==40) ) {
					int LA6_2 = input.LA(2);
					if ( (LA6_2==ID) ) {
						alt6=1;
					}

				}

				switch (alt6) {
				case 1 :
					// Lustre.g:80:17: ';' varDeclGroup
					{
					char_literal32=(Token)match(input,40,FOLLOW_40_in_varDeclList438); if (state.failed) return retval; 
					if ( state.backtracking==0 ) stream_40.add(char_literal32);

					pushFollow(FOLLOW_varDeclGroup_in_varDeclList440);
					varDeclGroup33=varDeclGroup();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) stream_varDeclGroup.add(varDeclGroup33.getTree());
					}
					break;

				default :
					break loop6;
				}
			}

			// AST REWRITE
			// elements: varDeclGroup
			// token labels: 
			// rule labels: retval
			// token list labels: 
			// rule list labels: 
			// wildcard labels: 
			if ( state.backtracking==0 ) {
			retval.tree = root_0;
			RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.getTree():null);

			root_0 = (Object)adaptor.nil();
			// 80:36: -> ( varDeclGroup )+
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
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "varDeclGroup"
	// Lustre.g:83:1: varDeclGroup : ID ( ',' ID )* ':' type -> ( ^( ID type ) )* ;
	public final LustreParser.varDeclGroup_return varDeclGroup() throws RecognitionException {
		LustreParser.varDeclGroup_return retval = new LustreParser.varDeclGroup_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		Token ID34=null;
		Token char_literal35=null;
		Token ID36=null;
		Token char_literal37=null;
		ParserRuleReturnScope type38 =null;

		Object ID34_tree=null;
		Object char_literal35_tree=null;
		Object ID36_tree=null;
		Object char_literal37_tree=null;
		RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
		RewriteRuleTokenStream stream_34=new RewriteRuleTokenStream(adaptor,"token 34");
		RewriteRuleTokenStream stream_39=new RewriteRuleTokenStream(adaptor,"token 39");
		RewriteRuleSubtreeStream stream_type=new RewriteRuleSubtreeStream(adaptor,"rule type");

		try {
			// Lustre.g:83:13: ( ID ( ',' ID )* ':' type -> ( ^( ID type ) )* )
			// Lustre.g:84:3: ID ( ',' ID )* ':' type
			{
			ID34=(Token)match(input,ID,FOLLOW_ID_in_varDeclGroup457); if (state.failed) return retval; 
			if ( state.backtracking==0 ) stream_ID.add(ID34);

			// Lustre.g:84:6: ( ',' ID )*
			loop7:
			while (true) {
				int alt7=2;
				int LA7_0 = input.LA(1);
				if ( (LA7_0==34) ) {
					alt7=1;
				}

				switch (alt7) {
				case 1 :
					// Lustre.g:84:7: ',' ID
					{
					char_literal35=(Token)match(input,34,FOLLOW_34_in_varDeclGroup460); if (state.failed) return retval; 
					if ( state.backtracking==0 ) stream_34.add(char_literal35);

					ID36=(Token)match(input,ID,FOLLOW_ID_in_varDeclGroup462); if (state.failed) return retval; 
					if ( state.backtracking==0 ) stream_ID.add(ID36);

					}
					break;

				default :
					break loop7;
				}
			}

			char_literal37=(Token)match(input,39,FOLLOW_39_in_varDeclGroup466); if (state.failed) return retval; 
			if ( state.backtracking==0 ) stream_39.add(char_literal37);

			pushFollow(FOLLOW_type_in_varDeclGroup468);
			type38=type();
			state._fsp--;
			if (state.failed) return retval;
			if ( state.backtracking==0 ) stream_type.add(type38.getTree());
			// AST REWRITE
			// elements: ID, type
			// token labels: 
			// rule labels: retval
			// token list labels: 
			// rule list labels: 
			// wildcard labels: 
			if ( state.backtracking==0 ) {
			retval.tree = root_0;
			RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.getTree():null);

			root_0 = (Object)adaptor.nil();
			// 84:25: -> ( ^( ID type ) )*
			{
				// Lustre.g:84:28: ( ^( ID type ) )*
				while ( stream_ID.hasNext()||stream_type.hasNext() ) {
					// Lustre.g:84:28: ^( ID type )
					{
					Object root_1 = (Object)adaptor.nil();
					root_1 = (Object)adaptor.becomeRoot(stream_ID.nextNode(), root_1);
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
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "type"
	// Lustre.g:87:1: type : ( 'int' ^| 'subrange' '[' low= bound ',' high= bound ']' 'of' 'int' -> ^( 'int' $low $high) | 'bool' ^| 'real' ^| ID ^);
	public final LustreParser.type_return type() throws RecognitionException {
		LustreParser.type_return retval = new LustreParser.type_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		Token string_literal39=null;
		Token string_literal40=null;
		Token char_literal41=null;
		Token char_literal42=null;
		Token char_literal43=null;
		Token string_literal44=null;
		Token string_literal45=null;
		Token string_literal46=null;
		Token string_literal47=null;
		Token ID48=null;
		ParserRuleReturnScope low =null;
		ParserRuleReturnScope high =null;

		Object string_literal39_tree=null;
		Object string_literal40_tree=null;
		Object char_literal41_tree=null;
		Object char_literal42_tree=null;
		Object char_literal43_tree=null;
		Object string_literal44_tree=null;
		Object string_literal45_tree=null;
		Object string_literal46_tree=null;
		Object string_literal47_tree=null;
		Object ID48_tree=null;
		RewriteRuleTokenStream stream_49=new RewriteRuleTokenStream(adaptor,"token 49");
		RewriteRuleTokenStream stream_48=new RewriteRuleTokenStream(adaptor,"token 48");
		RewriteRuleTokenStream stream_59=new RewriteRuleTokenStream(adaptor,"token 59");
		RewriteRuleTokenStream stream_56=new RewriteRuleTokenStream(adaptor,"token 56");
		RewriteRuleTokenStream stream_63=new RewriteRuleTokenStream(adaptor,"token 63");
		RewriteRuleTokenStream stream_34=new RewriteRuleTokenStream(adaptor,"token 34");
		RewriteRuleSubtreeStream stream_bound=new RewriteRuleSubtreeStream(adaptor,"rule bound");

		try {
			// Lustre.g:87:5: ( 'int' ^| 'subrange' '[' low= bound ',' high= bound ']' 'of' 'int' -> ^( 'int' $low $high) | 'bool' ^| 'real' ^| ID ^)
			int alt8=5;
			switch ( input.LA(1) ) {
			case 56:
				{
				alt8=1;
				}
				break;
			case 63:
				{
				alt8=2;
				}
				break;
			case 52:
				{
				alt8=3;
				}
				break;
			case 61:
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
					// Lustre.g:88:3: 'int' ^
					{
					root_0 = (Object)adaptor.nil();


					string_literal39=(Token)match(input,56,FOLLOW_56_in_type487); if (state.failed) return retval;
					if ( state.backtracking==0 ) {
					string_literal39_tree = (Object)adaptor.create(string_literal39);
					root_0 = (Object)adaptor.becomeRoot(string_literal39_tree, root_0);
					}

					}
					break;
				case 2 :
					// Lustre.g:89:3: 'subrange' '[' low= bound ',' high= bound ']' 'of' 'int'
					{
					string_literal40=(Token)match(input,63,FOLLOW_63_in_type492); if (state.failed) return retval; 
					if ( state.backtracking==0 ) stream_63.add(string_literal40);

					char_literal41=(Token)match(input,48,FOLLOW_48_in_type494); if (state.failed) return retval; 
					if ( state.backtracking==0 ) stream_48.add(char_literal41);

					pushFollow(FOLLOW_bound_in_type498);
					low=bound();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) stream_bound.add(low.getTree());
					char_literal42=(Token)match(input,34,FOLLOW_34_in_type500); if (state.failed) return retval; 
					if ( state.backtracking==0 ) stream_34.add(char_literal42);

					pushFollow(FOLLOW_bound_in_type504);
					high=bound();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) stream_bound.add(high.getTree());
					char_literal43=(Token)match(input,49,FOLLOW_49_in_type506); if (state.failed) return retval; 
					if ( state.backtracking==0 ) stream_49.add(char_literal43);

					string_literal44=(Token)match(input,59,FOLLOW_59_in_type508); if (state.failed) return retval; 
					if ( state.backtracking==0 ) stream_59.add(string_literal44);

					string_literal45=(Token)match(input,56,FOLLOW_56_in_type510); if (state.failed) return retval; 
					if ( state.backtracking==0 ) stream_56.add(string_literal45);

					// AST REWRITE
					// elements: 56, low, high
					// token labels: 
					// rule labels: retval, high, low
					// token list labels: 
					// rule list labels: 
					// wildcard labels: 
					if ( state.backtracking==0 ) {
					retval.tree = root_0;
					RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.getTree():null);
					RewriteRuleSubtreeStream stream_high=new RewriteRuleSubtreeStream(adaptor,"rule high",high!=null?high.getTree():null);
					RewriteRuleSubtreeStream stream_low=new RewriteRuleSubtreeStream(adaptor,"rule low",low!=null?low.getTree():null);

					root_0 = (Object)adaptor.nil();
					// 89:58: -> ^( 'int' $low $high)
					{
						// Lustre.g:89:61: ^( 'int' $low $high)
						{
						Object root_1 = (Object)adaptor.nil();
						root_1 = (Object)adaptor.becomeRoot(stream_56.nextNode(), root_1);
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
					// Lustre.g:90:3: 'bool' ^
					{
					root_0 = (Object)adaptor.nil();


					string_literal46=(Token)match(input,52,FOLLOW_52_in_type526); if (state.failed) return retval;
					if ( state.backtracking==0 ) {
					string_literal46_tree = (Object)adaptor.create(string_literal46);
					root_0 = (Object)adaptor.becomeRoot(string_literal46_tree, root_0);
					}

					}
					break;
				case 4 :
					// Lustre.g:91:3: 'real' ^
					{
					root_0 = (Object)adaptor.nil();


					string_literal47=(Token)match(input,61,FOLLOW_61_in_type531); if (state.failed) return retval;
					if ( state.backtracking==0 ) {
					string_literal47_tree = (Object)adaptor.create(string_literal47);
					root_0 = (Object)adaptor.becomeRoot(string_literal47_tree, root_0);
					}

					}
					break;
				case 5 :
					// Lustre.g:92:3: ID ^
					{
					root_0 = (Object)adaptor.nil();


					ID48=(Token)match(input,ID,FOLLOW_ID_in_type536); if (state.failed) return retval;
					if ( state.backtracking==0 ) {
					ID48_tree = (Object)adaptor.create(ID48);
					root_0 = (Object)adaptor.becomeRoot(ID48_tree, root_0);
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
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "bound"
	// Lustre.g:95:1: bound : ( '-' )? INT -> INT[$bound.text] ;
	public final LustreParser.bound_return bound() throws RecognitionException {
		LustreParser.bound_return retval = new LustreParser.bound_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		Token char_literal49=null;
		Token INT50=null;

		Object char_literal49_tree=null;
		Object INT50_tree=null;
		RewriteRuleTokenStream stream_INT=new RewriteRuleTokenStream(adaptor,"token INT");
		RewriteRuleTokenStream stream_35=new RewriteRuleTokenStream(adaptor,"token 35");

		try {
			// Lustre.g:95:6: ( ( '-' )? INT -> INT[$bound.text] )
			// Lustre.g:96:3: ( '-' )? INT
			{
			// Lustre.g:96:3: ( '-' )?
			int alt9=2;
			int LA9_0 = input.LA(1);
			if ( (LA9_0==35) ) {
				alt9=1;
			}
			switch (alt9) {
				case 1 :
					// Lustre.g:96:3: '-'
					{
					char_literal49=(Token)match(input,35,FOLLOW_35_in_bound547); if (state.failed) return retval; 
					if ( state.backtracking==0 ) stream_35.add(char_literal49);

					}
					break;

			}

			INT50=(Token)match(input,INT,FOLLOW_INT_in_bound550); if (state.failed) return retval; 
			if ( state.backtracking==0 ) stream_INT.add(INT50);

			// AST REWRITE
			// elements: INT
			// token labels: 
			// rule labels: retval
			// token list labels: 
			// rule list labels: 
			// wildcard labels: 
			if ( state.backtracking==0 ) {
			retval.tree = root_0;
			RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.getTree():null);

			root_0 = (Object)adaptor.nil();
			// 96:12: -> INT[$bound.text]
			{
				adaptor.addChild(root_0, (Object)adaptor.create(INT, input.toString(retval.start,input.LT(-1))));
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
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "property"
	// Lustre.g:99:1: property : '--%PROPERTY' ID ';' -> ID ;
	public final LustreParser.property_return property() throws RecognitionException {
		LustreParser.property_return retval = new LustreParser.property_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		Token string_literal51=null;
		Token ID52=null;
		Token char_literal53=null;

		Object string_literal51_tree=null;
		Object ID52_tree=null;
		Object char_literal53_tree=null;
		RewriteRuleTokenStream stream_40=new RewriteRuleTokenStream(adaptor,"token 40");
		RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
		RewriteRuleTokenStream stream_36=new RewriteRuleTokenStream(adaptor,"token 36");

		try {
			// Lustre.g:99:9: ( '--%PROPERTY' ID ';' -> ID )
			// Lustre.g:100:3: '--%PROPERTY' ID ';'
			{
			string_literal51=(Token)match(input,36,FOLLOW_36_in_property565); if (state.failed) return retval; 
			if ( state.backtracking==0 ) stream_36.add(string_literal51);

			ID52=(Token)match(input,ID,FOLLOW_ID_in_property567); if (state.failed) return retval; 
			if ( state.backtracking==0 ) stream_ID.add(ID52);

			char_literal53=(Token)match(input,40,FOLLOW_40_in_property569); if (state.failed) return retval; 
			if ( state.backtracking==0 ) stream_40.add(char_literal53);

			// AST REWRITE
			// elements: ID
			// token labels: 
			// rule labels: retval
			// token list labels: 
			// rule list labels: 
			// wildcard labels: 
			if ( state.backtracking==0 ) {
			retval.tree = root_0;
			RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.getTree():null);

			root_0 = (Object)adaptor.nil();
			// 100:24: -> ID
			{
				adaptor.addChild(root_0, stream_ID.nextNode());
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


	public static class assertion_return extends ParserRuleReturnScope {
		Object tree;
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "assertion"
	// Lustre.g:103:1: assertion : 'assert' expr ';' -> expr ;
	public final LustreParser.assertion_return assertion() throws RecognitionException {
		LustreParser.assertion_return retval = new LustreParser.assertion_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		Token string_literal54=null;
		Token char_literal56=null;
		ParserRuleReturnScope expr55 =null;

		Object string_literal54_tree=null;
		Object char_literal56_tree=null;
		RewriteRuleTokenStream stream_40=new RewriteRuleTokenStream(adaptor,"token 40");
		RewriteRuleTokenStream stream_51=new RewriteRuleTokenStream(adaptor,"token 51");
		RewriteRuleSubtreeStream stream_expr=new RewriteRuleSubtreeStream(adaptor,"rule expr");

		try {
			// Lustre.g:103:10: ( 'assert' expr ';' -> expr )
			// Lustre.g:104:3: 'assert' expr ';'
			{
			string_literal54=(Token)match(input,51,FOLLOW_51_in_assertion583); if (state.failed) return retval; 
			if ( state.backtracking==0 ) stream_51.add(string_literal54);

			pushFollow(FOLLOW_expr_in_assertion585);
			expr55=expr();
			state._fsp--;
			if (state.failed) return retval;
			if ( state.backtracking==0 ) stream_expr.add(expr55.getTree());
			char_literal56=(Token)match(input,40,FOLLOW_40_in_assertion587); if (state.failed) return retval; 
			if ( state.backtracking==0 ) stream_40.add(char_literal56);

			// AST REWRITE
			// elements: expr
			// token labels: 
			// rule labels: retval
			// token list labels: 
			// rule list labels: 
			// wildcard labels: 
			if ( state.backtracking==0 ) {
			retval.tree = root_0;
			RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.getTree():null);

			root_0 = (Object)adaptor.nil();
			// 104:21: -> expr
			{
				adaptor.addChild(root_0, stream_expr.nextTree());
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
	// $ANTLR end "assertion"


	public static class equation_return extends ParserRuleReturnScope {
		Object tree;
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "equation"
	// Lustre.g:107:1: equation : lhs_opt_parens '=' expr ';' -> ^( EQUATION lhs_opt_parens expr ) ;
	public final LustreParser.equation_return equation() throws RecognitionException {
		LustreParser.equation_return retval = new LustreParser.equation_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		Token char_literal58=null;
		Token char_literal60=null;
		ParserRuleReturnScope lhs_opt_parens57 =null;
		ParserRuleReturnScope expr59 =null;

		Object char_literal58_tree=null;
		Object char_literal60_tree=null;
		RewriteRuleTokenStream stream_44=new RewriteRuleTokenStream(adaptor,"token 44");
		RewriteRuleTokenStream stream_40=new RewriteRuleTokenStream(adaptor,"token 40");
		RewriteRuleSubtreeStream stream_lhs_opt_parens=new RewriteRuleSubtreeStream(adaptor,"rule lhs_opt_parens");
		RewriteRuleSubtreeStream stream_expr=new RewriteRuleSubtreeStream(adaptor,"rule expr");

		try {
			// Lustre.g:107:9: ( lhs_opt_parens '=' expr ';' -> ^( EQUATION lhs_opt_parens expr ) )
			// Lustre.g:108:3: lhs_opt_parens '=' expr ';'
			{
			pushFollow(FOLLOW_lhs_opt_parens_in_equation601);
			lhs_opt_parens57=lhs_opt_parens();
			state._fsp--;
			if (state.failed) return retval;
			if ( state.backtracking==0 ) stream_lhs_opt_parens.add(lhs_opt_parens57.getTree());
			char_literal58=(Token)match(input,44,FOLLOW_44_in_equation603); if (state.failed) return retval; 
			if ( state.backtracking==0 ) stream_44.add(char_literal58);

			pushFollow(FOLLOW_expr_in_equation605);
			expr59=expr();
			state._fsp--;
			if (state.failed) return retval;
			if ( state.backtracking==0 ) stream_expr.add(expr59.getTree());
			char_literal60=(Token)match(input,40,FOLLOW_40_in_equation607); if (state.failed) return retval; 
			if ( state.backtracking==0 ) stream_40.add(char_literal60);

			// AST REWRITE
			// elements: expr, lhs_opt_parens
			// token labels: 
			// rule labels: retval
			// token list labels: 
			// rule list labels: 
			// wildcard labels: 
			if ( state.backtracking==0 ) {
			retval.tree = root_0;
			RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.getTree():null);

			root_0 = (Object)adaptor.nil();
			// 108:31: -> ^( EQUATION lhs_opt_parens expr )
			{
				// Lustre.g:108:34: ^( EQUATION lhs_opt_parens expr )
				{
				Object root_1 = (Object)adaptor.nil();
				root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(EQUATION, "EQUATION"), root_1);
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
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "lhs_opt_parens"
	// Lustre.g:111:1: lhs_opt_parens : ( lhs | '(' lhs ')' -> lhs );
	public final LustreParser.lhs_opt_parens_return lhs_opt_parens() throws RecognitionException {
		LustreParser.lhs_opt_parens_return retval = new LustreParser.lhs_opt_parens_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		Token char_literal62=null;
		Token char_literal64=null;
		ParserRuleReturnScope lhs61 =null;
		ParserRuleReturnScope lhs63 =null;

		Object char_literal62_tree=null;
		Object char_literal64_tree=null;
		RewriteRuleTokenStream stream_30=new RewriteRuleTokenStream(adaptor,"token 30");
		RewriteRuleTokenStream stream_31=new RewriteRuleTokenStream(adaptor,"token 31");
		RewriteRuleSubtreeStream stream_lhs=new RewriteRuleSubtreeStream(adaptor,"rule lhs");

		try {
			// Lustre.g:111:15: ( lhs | '(' lhs ')' -> lhs )
			int alt10=2;
			int LA10_0 = input.LA(1);
			if ( (LA10_0==ID) ) {
				alt10=1;
			}
			else if ( (LA10_0==30) ) {
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
					// Lustre.g:112:3: lhs
					{
					root_0 = (Object)adaptor.nil();


					pushFollow(FOLLOW_lhs_in_lhs_opt_parens627);
					lhs61=lhs();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) adaptor.addChild(root_0, lhs61.getTree());

					}
					break;
				case 2 :
					// Lustre.g:113:3: '(' lhs ')'
					{
					char_literal62=(Token)match(input,30,FOLLOW_30_in_lhs_opt_parens631); if (state.failed) return retval; 
					if ( state.backtracking==0 ) stream_30.add(char_literal62);

					pushFollow(FOLLOW_lhs_in_lhs_opt_parens633);
					lhs63=lhs();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) stream_lhs.add(lhs63.getTree());
					char_literal64=(Token)match(input,31,FOLLOW_31_in_lhs_opt_parens635); if (state.failed) return retval; 
					if ( state.backtracking==0 ) stream_31.add(char_literal64);

					// AST REWRITE
					// elements: lhs
					// token labels: 
					// rule labels: retval
					// token list labels: 
					// rule list labels: 
					// wildcard labels: 
					if ( state.backtracking==0 ) {
					retval.tree = root_0;
					RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.getTree():null);

					root_0 = (Object)adaptor.nil();
					// 113:15: -> lhs
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
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "lhs"
	// Lustre.g:116:1: lhs : ID ( ',' ID )* -> ^( LHS ( ID )* ) ;
	public final LustreParser.lhs_return lhs() throws RecognitionException {
		LustreParser.lhs_return retval = new LustreParser.lhs_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		Token ID65=null;
		Token char_literal66=null;
		Token ID67=null;

		Object ID65_tree=null;
		Object char_literal66_tree=null;
		Object ID67_tree=null;
		RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
		RewriteRuleTokenStream stream_34=new RewriteRuleTokenStream(adaptor,"token 34");

		try {
			// Lustre.g:116:4: ( ID ( ',' ID )* -> ^( LHS ( ID )* ) )
			// Lustre.g:117:3: ID ( ',' ID )*
			{
			ID65=(Token)match(input,ID,FOLLOW_ID_in_lhs649); if (state.failed) return retval; 
			if ( state.backtracking==0 ) stream_ID.add(ID65);

			// Lustre.g:117:6: ( ',' ID )*
			loop11:
			while (true) {
				int alt11=2;
				int LA11_0 = input.LA(1);
				if ( (LA11_0==34) ) {
					alt11=1;
				}

				switch (alt11) {
				case 1 :
					// Lustre.g:117:7: ',' ID
					{
					char_literal66=(Token)match(input,34,FOLLOW_34_in_lhs652); if (state.failed) return retval; 
					if ( state.backtracking==0 ) stream_34.add(char_literal66);

					ID67=(Token)match(input,ID,FOLLOW_ID_in_lhs654); if (state.failed) return retval; 
					if ( state.backtracking==0 ) stream_ID.add(ID67);

					}
					break;

				default :
					break loop11;
				}
			}

			// AST REWRITE
			// elements: ID
			// token labels: 
			// rule labels: retval
			// token list labels: 
			// rule list labels: 
			// wildcard labels: 
			if ( state.backtracking==0 ) {
			retval.tree = root_0;
			RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.getTree():null);

			root_0 = (Object)adaptor.nil();
			// 117:16: -> ^( LHS ( ID )* )
			{
				// Lustre.g:117:19: ^( LHS ( ID )* )
				{
				Object root_1 = (Object)adaptor.nil();
				root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(LHS, "LHS"), root_1);
				// Lustre.g:117:25: ( ID )*
				while ( stream_ID.hasNext() ) {
					adaptor.addChild(root_1, stream_ID.nextNode());
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
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "expr"
	// Lustre.g:120:1: expr : arrowExpr ;
	public final LustreParser.expr_return expr() throws RecognitionException {
		LustreParser.expr_return retval = new LustreParser.expr_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		ParserRuleReturnScope arrowExpr68 =null;


		try {
			// Lustre.g:120:5: ( arrowExpr )
			// Lustre.g:121:3: arrowExpr
			{
			root_0 = (Object)adaptor.nil();


			pushFollow(FOLLOW_arrowExpr_in_expr675);
			arrowExpr68=arrowExpr();
			state._fsp--;
			if (state.failed) return retval;
			if ( state.backtracking==0 ) adaptor.addChild(root_0, arrowExpr68.getTree());

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
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "arrowOp"
	// Lustre.g:124:1: arrowOp : '->' ;
	public final LustreParser.arrowOp_return arrowOp() throws RecognitionException {
		LustreParser.arrowOp_return retval = new LustreParser.arrowOp_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		Token string_literal69=null;

		Object string_literal69_tree=null;

		try {
			// Lustre.g:124:8: ( '->' )
			// Lustre.g:125:3: '->'
			{
			root_0 = (Object)adaptor.nil();


			string_literal69=(Token)match(input,37,FOLLOW_37_in_arrowOp685); if (state.failed) return retval;
			if ( state.backtracking==0 ) {
			string_literal69_tree = (Object)adaptor.create(string_literal69);
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
	// $ANTLR end "arrowOp"


	public static class arrowExpr_return extends ParserRuleReturnScope {
		Object tree;
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "arrowExpr"
	// Lustre.g:128:1: arrowExpr : impliesExpr ( ( arrowOp )=> arrowOp ^ arrowExpr )? ;
	public final LustreParser.arrowExpr_return arrowExpr() throws RecognitionException {
		LustreParser.arrowExpr_return retval = new LustreParser.arrowExpr_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		ParserRuleReturnScope impliesExpr70 =null;
		ParserRuleReturnScope arrowOp71 =null;
		ParserRuleReturnScope arrowExpr72 =null;


		try {
			// Lustre.g:128:10: ( impliesExpr ( ( arrowOp )=> arrowOp ^ arrowExpr )? )
			// Lustre.g:129:3: impliesExpr ( ( arrowOp )=> arrowOp ^ arrowExpr )?
			{
			root_0 = (Object)adaptor.nil();


			pushFollow(FOLLOW_impliesExpr_in_arrowExpr695);
			impliesExpr70=impliesExpr();
			state._fsp--;
			if (state.failed) return retval;
			if ( state.backtracking==0 ) adaptor.addChild(root_0, impliesExpr70.getTree());

			// Lustre.g:129:15: ( ( arrowOp )=> arrowOp ^ arrowExpr )?
			int alt12=2;
			int LA12_0 = input.LA(1);
			if ( (LA12_0==37) ) {
				int LA12_1 = input.LA(2);
				if ( (synpred1_Lustre()) ) {
					alt12=1;
				}
			}
			switch (alt12) {
				case 1 :
					// Lustre.g:129:16: ( arrowOp )=> arrowOp ^ arrowExpr
					{
					pushFollow(FOLLOW_arrowOp_in_arrowExpr702);
					arrowOp71=arrowOp();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) root_0 = (Object)adaptor.becomeRoot(arrowOp71.getTree(), root_0);
					pushFollow(FOLLOW_arrowExpr_in_arrowExpr705);
					arrowExpr72=arrowExpr();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) adaptor.addChild(root_0, arrowExpr72.getTree());

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
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "impliesOp"
	// Lustre.g:132:1: impliesOp : '=>' ;
	public final LustreParser.impliesOp_return impliesOp() throws RecognitionException {
		LustreParser.impliesOp_return retval = new LustreParser.impliesOp_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		Token string_literal73=null;

		Object string_literal73_tree=null;

		try {
			// Lustre.g:132:10: ( '=>' )
			// Lustre.g:133:3: '=>'
			{
			root_0 = (Object)adaptor.nil();


			string_literal73=(Token)match(input,45,FOLLOW_45_in_impliesOp717); if (state.failed) return retval;
			if ( state.backtracking==0 ) {
			string_literal73_tree = (Object)adaptor.create(string_literal73);
			adaptor.addChild(root_0, string_literal73_tree);
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
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "impliesExpr"
	// Lustre.g:136:1: impliesExpr : orExpr ( ( impliesOp )=> impliesOp ^ impliesExpr )? ;
	public final LustreParser.impliesExpr_return impliesExpr() throws RecognitionException {
		LustreParser.impliesExpr_return retval = new LustreParser.impliesExpr_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		ParserRuleReturnScope orExpr74 =null;
		ParserRuleReturnScope impliesOp75 =null;
		ParserRuleReturnScope impliesExpr76 =null;


		try {
			// Lustre.g:136:12: ( orExpr ( ( impliesOp )=> impliesOp ^ impliesExpr )? )
			// Lustre.g:137:3: orExpr ( ( impliesOp )=> impliesOp ^ impliesExpr )?
			{
			root_0 = (Object)adaptor.nil();


			pushFollow(FOLLOW_orExpr_in_impliesExpr727);
			orExpr74=orExpr();
			state._fsp--;
			if (state.failed) return retval;
			if ( state.backtracking==0 ) adaptor.addChild(root_0, orExpr74.getTree());

			// Lustre.g:137:10: ( ( impliesOp )=> impliesOp ^ impliesExpr )?
			int alt13=2;
			int LA13_0 = input.LA(1);
			if ( (LA13_0==45) ) {
				int LA13_1 = input.LA(2);
				if ( (synpred2_Lustre()) ) {
					alt13=1;
				}
			}
			switch (alt13) {
				case 1 :
					// Lustre.g:137:11: ( impliesOp )=> impliesOp ^ impliesExpr
					{
					pushFollow(FOLLOW_impliesOp_in_impliesExpr734);
					impliesOp75=impliesOp();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) root_0 = (Object)adaptor.becomeRoot(impliesOp75.getTree(), root_0);
					pushFollow(FOLLOW_impliesExpr_in_impliesExpr737);
					impliesExpr76=impliesExpr();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) adaptor.addChild(root_0, impliesExpr76.getTree());

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
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "orOp"
	// Lustre.g:140:1: orOp : ( 'or' | 'xor' );
	public final LustreParser.orOp_return orOp() throws RecognitionException {
		LustreParser.orOp_return retval = new LustreParser.orOp_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		Token set77=null;

		Object set77_tree=null;

		try {
			// Lustre.g:140:5: ( 'or' | 'xor' )
			// Lustre.g:
			{
			root_0 = (Object)adaptor.nil();


			set77=input.LT(1);
			if ( input.LA(1)==60||input.LA(1)==68 ) {
				input.consume();
				if ( state.backtracking==0 ) adaptor.addChild(root_0, (Object)adaptor.create(set77));
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
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "orExpr"
	// Lustre.g:144:1: orExpr : andExpr ( ( orOp )=> orOp ^ andExpr )* ;
	public final LustreParser.orExpr_return orExpr() throws RecognitionException {
		LustreParser.orExpr_return retval = new LustreParser.orExpr_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		ParserRuleReturnScope andExpr78 =null;
		ParserRuleReturnScope orOp79 =null;
		ParserRuleReturnScope andExpr80 =null;


		try {
			// Lustre.g:144:7: ( andExpr ( ( orOp )=> orOp ^ andExpr )* )
			// Lustre.g:145:3: andExpr ( ( orOp )=> orOp ^ andExpr )*
			{
			root_0 = (Object)adaptor.nil();


			pushFollow(FOLLOW_andExpr_in_orExpr763);
			andExpr78=andExpr();
			state._fsp--;
			if (state.failed) return retval;
			if ( state.backtracking==0 ) adaptor.addChild(root_0, andExpr78.getTree());

			// Lustre.g:145:11: ( ( orOp )=> orOp ^ andExpr )*
			loop14:
			while (true) {
				int alt14=2;
				int LA14_0 = input.LA(1);
				if ( (LA14_0==60||LA14_0==68) ) {
					int LA14_2 = input.LA(2);
					if ( (synpred3_Lustre()) ) {
						alt14=1;
					}

				}

				switch (alt14) {
				case 1 :
					// Lustre.g:145:12: ( orOp )=> orOp ^ andExpr
					{
					pushFollow(FOLLOW_orOp_in_orExpr770);
					orOp79=orOp();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) root_0 = (Object)adaptor.becomeRoot(orOp79.getTree(), root_0);
					pushFollow(FOLLOW_andExpr_in_orExpr773);
					andExpr80=andExpr();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) adaptor.addChild(root_0, andExpr80.getTree());

					}
					break;

				default :
					break loop14;
				}
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
	// $ANTLR end "orExpr"


	public static class andOp_return extends ParserRuleReturnScope {
		Object tree;
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "andOp"
	// Lustre.g:148:1: andOp : 'and' ;
	public final LustreParser.andOp_return andOp() throws RecognitionException {
		LustreParser.andOp_return retval = new LustreParser.andOp_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		Token string_literal81=null;

		Object string_literal81_tree=null;

		try {
			// Lustre.g:148:6: ( 'and' )
			// Lustre.g:149:3: 'and'
			{
			root_0 = (Object)adaptor.nil();


			string_literal81=(Token)match(input,50,FOLLOW_50_in_andOp785); if (state.failed) return retval;
			if ( state.backtracking==0 ) {
			string_literal81_tree = (Object)adaptor.create(string_literal81);
			adaptor.addChild(root_0, string_literal81_tree);
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
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "andExpr"
	// Lustre.g:152:1: andExpr : relationalExpr ( ( andOp )=> andOp ^ relationalExpr )* ;
	public final LustreParser.andExpr_return andExpr() throws RecognitionException {
		LustreParser.andExpr_return retval = new LustreParser.andExpr_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		ParserRuleReturnScope relationalExpr82 =null;
		ParserRuleReturnScope andOp83 =null;
		ParserRuleReturnScope relationalExpr84 =null;


		try {
			// Lustre.g:152:8: ( relationalExpr ( ( andOp )=> andOp ^ relationalExpr )* )
			// Lustre.g:153:3: relationalExpr ( ( andOp )=> andOp ^ relationalExpr )*
			{
			root_0 = (Object)adaptor.nil();


			pushFollow(FOLLOW_relationalExpr_in_andExpr795);
			relationalExpr82=relationalExpr();
			state._fsp--;
			if (state.failed) return retval;
			if ( state.backtracking==0 ) adaptor.addChild(root_0, relationalExpr82.getTree());

			// Lustre.g:153:18: ( ( andOp )=> andOp ^ relationalExpr )*
			loop15:
			while (true) {
				int alt15=2;
				int LA15_0 = input.LA(1);
				if ( (LA15_0==50) ) {
					int LA15_2 = input.LA(2);
					if ( (synpred4_Lustre()) ) {
						alt15=1;
					}

				}

				switch (alt15) {
				case 1 :
					// Lustre.g:153:19: ( andOp )=> andOp ^ relationalExpr
					{
					pushFollow(FOLLOW_andOp_in_andExpr802);
					andOp83=andOp();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) root_0 = (Object)adaptor.becomeRoot(andOp83.getTree(), root_0);
					pushFollow(FOLLOW_relationalExpr_in_andExpr805);
					relationalExpr84=relationalExpr();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) adaptor.addChild(root_0, relationalExpr84.getTree());

					}
					break;

				default :
					break loop15;
				}
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
	// $ANTLR end "andExpr"


	public static class relationalOp_return extends ParserRuleReturnScope {
		Object tree;
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "relationalOp"
	// Lustre.g:156:1: relationalOp : ( '<' | '<=' | '>' | '>=' | '=' | '<>' );
	public final LustreParser.relationalOp_return relationalOp() throws RecognitionException {
		LustreParser.relationalOp_return retval = new LustreParser.relationalOp_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		Token set85=null;

		Object set85_tree=null;

		try {
			// Lustre.g:156:13: ( '<' | '<=' | '>' | '>=' | '=' | '<>' )
			// Lustre.g:
			{
			root_0 = (Object)adaptor.nil();


			set85=input.LT(1);
			if ( (input.LA(1) >= 41 && input.LA(1) <= 44)||(input.LA(1) >= 46 && input.LA(1) <= 47) ) {
				input.consume();
				if ( state.backtracking==0 ) adaptor.addChild(root_0, (Object)adaptor.create(set85));
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
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "relationalExpr"
	// Lustre.g:160:1: relationalExpr : plusExpr ( ( relationalOp )=> relationalOp ^ plusExpr )? ;
	public final LustreParser.relationalExpr_return relationalExpr() throws RecognitionException {
		LustreParser.relationalExpr_return retval = new LustreParser.relationalExpr_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		ParserRuleReturnScope plusExpr86 =null;
		ParserRuleReturnScope relationalOp87 =null;
		ParserRuleReturnScope plusExpr88 =null;


		try {
			// Lustre.g:160:15: ( plusExpr ( ( relationalOp )=> relationalOp ^ plusExpr )? )
			// Lustre.g:161:3: plusExpr ( ( relationalOp )=> relationalOp ^ plusExpr )?
			{
			root_0 = (Object)adaptor.nil();


			pushFollow(FOLLOW_plusExpr_in_relationalExpr847);
			plusExpr86=plusExpr();
			state._fsp--;
			if (state.failed) return retval;
			if ( state.backtracking==0 ) adaptor.addChild(root_0, plusExpr86.getTree());

			// Lustre.g:161:12: ( ( relationalOp )=> relationalOp ^ plusExpr )?
			int alt16=2;
			int LA16_0 = input.LA(1);
			if ( ((LA16_0 >= 41 && LA16_0 <= 44)||(LA16_0 >= 46 && LA16_0 <= 47)) ) {
				int LA16_1 = input.LA(2);
				if ( (synpred5_Lustre()) ) {
					alt16=1;
				}
			}
			switch (alt16) {
				case 1 :
					// Lustre.g:161:13: ( relationalOp )=> relationalOp ^ plusExpr
					{
					pushFollow(FOLLOW_relationalOp_in_relationalExpr854);
					relationalOp87=relationalOp();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) root_0 = (Object)adaptor.becomeRoot(relationalOp87.getTree(), root_0);
					pushFollow(FOLLOW_plusExpr_in_relationalExpr857);
					plusExpr88=plusExpr();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) adaptor.addChild(root_0, plusExpr88.getTree());

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
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "plusOp"
	// Lustre.g:164:1: plusOp : ( '+' | '-' );
	public final LustreParser.plusOp_return plusOp() throws RecognitionException {
		LustreParser.plusOp_return retval = new LustreParser.plusOp_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		Token set89=null;

		Object set89_tree=null;

		try {
			// Lustre.g:164:7: ( '+' | '-' )
			// Lustre.g:
			{
			root_0 = (Object)adaptor.nil();


			set89=input.LT(1);
			if ( input.LA(1)==33||input.LA(1)==35 ) {
				input.consume();
				if ( state.backtracking==0 ) adaptor.addChild(root_0, (Object)adaptor.create(set89));
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
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "plusExpr"
	// Lustre.g:168:1: plusExpr : timesExpr ( ( plusOp )=> plusOp ^ timesExpr )* ;
	public final LustreParser.plusExpr_return plusExpr() throws RecognitionException {
		LustreParser.plusExpr_return retval = new LustreParser.plusExpr_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		ParserRuleReturnScope timesExpr90 =null;
		ParserRuleReturnScope plusOp91 =null;
		ParserRuleReturnScope timesExpr92 =null;


		try {
			// Lustre.g:168:9: ( timesExpr ( ( plusOp )=> plusOp ^ timesExpr )* )
			// Lustre.g:169:3: timesExpr ( ( plusOp )=> plusOp ^ timesExpr )*
			{
			root_0 = (Object)adaptor.nil();


			pushFollow(FOLLOW_timesExpr_in_plusExpr883);
			timesExpr90=timesExpr();
			state._fsp--;
			if (state.failed) return retval;
			if ( state.backtracking==0 ) adaptor.addChild(root_0, timesExpr90.getTree());

			// Lustre.g:169:13: ( ( plusOp )=> plusOp ^ timesExpr )*
			loop17:
			while (true) {
				int alt17=2;
				int LA17_0 = input.LA(1);
				if ( (LA17_0==33||LA17_0==35) ) {
					int LA17_2 = input.LA(2);
					if ( (synpred6_Lustre()) ) {
						alt17=1;
					}

				}

				switch (alt17) {
				case 1 :
					// Lustre.g:169:14: ( plusOp )=> plusOp ^ timesExpr
					{
					pushFollow(FOLLOW_plusOp_in_plusExpr890);
					plusOp91=plusOp();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) root_0 = (Object)adaptor.becomeRoot(plusOp91.getTree(), root_0);
					pushFollow(FOLLOW_timesExpr_in_plusExpr893);
					timesExpr92=timesExpr();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) adaptor.addChild(root_0, timesExpr92.getTree());

					}
					break;

				default :
					break loop17;
				}
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
	// $ANTLR end "plusExpr"


	public static class timesOp_return extends ParserRuleReturnScope {
		Object tree;
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "timesOp"
	// Lustre.g:172:1: timesOp : ( '*' | '/' | 'div' );
	public final LustreParser.timesOp_return timesOp() throws RecognitionException {
		LustreParser.timesOp_return retval = new LustreParser.timesOp_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		Token set93=null;

		Object set93_tree=null;

		try {
			// Lustre.g:172:8: ( '*' | '/' | 'div' )
			// Lustre.g:
			{
			root_0 = (Object)adaptor.nil();


			set93=input.LT(1);
			if ( input.LA(1)==32||input.LA(1)==38||input.LA(1)==54 ) {
				input.consume();
				if ( state.backtracking==0 ) adaptor.addChild(root_0, (Object)adaptor.create(set93));
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
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "timesExpr"
	// Lustre.g:176:1: timesExpr : prefixExpr ( ( timesOp )=> timesOp ^ prefixExpr )* ;
	public final LustreParser.timesExpr_return timesExpr() throws RecognitionException {
		LustreParser.timesExpr_return retval = new LustreParser.timesExpr_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		ParserRuleReturnScope prefixExpr94 =null;
		ParserRuleReturnScope timesOp95 =null;
		ParserRuleReturnScope prefixExpr96 =null;


		try {
			// Lustre.g:176:10: ( prefixExpr ( ( timesOp )=> timesOp ^ prefixExpr )* )
			// Lustre.g:177:3: prefixExpr ( ( timesOp )=> timesOp ^ prefixExpr )*
			{
			root_0 = (Object)adaptor.nil();


			pushFollow(FOLLOW_prefixExpr_in_timesExpr923);
			prefixExpr94=prefixExpr();
			state._fsp--;
			if (state.failed) return retval;
			if ( state.backtracking==0 ) adaptor.addChild(root_0, prefixExpr94.getTree());

			// Lustre.g:177:14: ( ( timesOp )=> timesOp ^ prefixExpr )*
			loop18:
			while (true) {
				int alt18=2;
				int LA18_0 = input.LA(1);
				if ( (LA18_0==32||LA18_0==38||LA18_0==54) ) {
					int LA18_2 = input.LA(2);
					if ( (synpred7_Lustre()) ) {
						alt18=1;
					}

				}

				switch (alt18) {
				case 1 :
					// Lustre.g:177:15: ( timesOp )=> timesOp ^ prefixExpr
					{
					pushFollow(FOLLOW_timesOp_in_timesExpr930);
					timesOp95=timesOp();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) root_0 = (Object)adaptor.becomeRoot(timesOp95.getTree(), root_0);
					pushFollow(FOLLOW_prefixExpr_in_timesExpr933);
					prefixExpr96=prefixExpr();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) adaptor.addChild(root_0, prefixExpr96.getTree());

					}
					break;

				default :
					break loop18;
				}
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
	// $ANTLR end "timesExpr"


	public static class prefixExpr_return extends ParserRuleReturnScope {
		Object tree;
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "prefixExpr"
	// Lustre.g:180:1: prefixExpr : ( '-' prefixExpr -> ^( NEGATE prefixExpr ) | NOT ^ prefixExpr | PRE ^ prefixExpr | atomicExpr );
	public final LustreParser.prefixExpr_return prefixExpr() throws RecognitionException {
		LustreParser.prefixExpr_return retval = new LustreParser.prefixExpr_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		Token char_literal97=null;
		Token NOT99=null;
		Token PRE101=null;
		ParserRuleReturnScope prefixExpr98 =null;
		ParserRuleReturnScope prefixExpr100 =null;
		ParserRuleReturnScope prefixExpr102 =null;
		ParserRuleReturnScope atomicExpr103 =null;

		Object char_literal97_tree=null;
		Object NOT99_tree=null;
		Object PRE101_tree=null;
		RewriteRuleTokenStream stream_35=new RewriteRuleTokenStream(adaptor,"token 35");
		RewriteRuleSubtreeStream stream_prefixExpr=new RewriteRuleSubtreeStream(adaptor,"rule prefixExpr");

		try {
			// Lustre.g:180:11: ( '-' prefixExpr -> ^( NEGATE prefixExpr ) | NOT ^ prefixExpr | PRE ^ prefixExpr | atomicExpr )
			int alt19=4;
			switch ( input.LA(1) ) {
			case 35:
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
			case 30:
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
					// Lustre.g:181:3: '-' prefixExpr
					{
					char_literal97=(Token)match(input,35,FOLLOW_35_in_prefixExpr945); if (state.failed) return retval; 
					if ( state.backtracking==0 ) stream_35.add(char_literal97);

					pushFollow(FOLLOW_prefixExpr_in_prefixExpr947);
					prefixExpr98=prefixExpr();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) stream_prefixExpr.add(prefixExpr98.getTree());
					// AST REWRITE
					// elements: prefixExpr
					// token labels: 
					// rule labels: retval
					// token list labels: 
					// rule list labels: 
					// wildcard labels: 
					if ( state.backtracking==0 ) {
					retval.tree = root_0;
					RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.getTree():null);

					root_0 = (Object)adaptor.nil();
					// 181:18: -> ^( NEGATE prefixExpr )
					{
						// Lustre.g:181:21: ^( NEGATE prefixExpr )
						{
						Object root_1 = (Object)adaptor.nil();
						root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(NEGATE, "NEGATE"), root_1);
						adaptor.addChild(root_1, stream_prefixExpr.nextTree());
						adaptor.addChild(root_0, root_1);
						}

					}


					retval.tree = root_0;
					}

					}
					break;
				case 2 :
					// Lustre.g:182:3: NOT ^ prefixExpr
					{
					root_0 = (Object)adaptor.nil();


					NOT99=(Token)match(input,NOT,FOLLOW_NOT_in_prefixExpr959); if (state.failed) return retval;
					if ( state.backtracking==0 ) {
					NOT99_tree = (Object)adaptor.create(NOT99);
					root_0 = (Object)adaptor.becomeRoot(NOT99_tree, root_0);
					}

					pushFollow(FOLLOW_prefixExpr_in_prefixExpr962);
					prefixExpr100=prefixExpr();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) adaptor.addChild(root_0, prefixExpr100.getTree());

					}
					break;
				case 3 :
					// Lustre.g:183:3: PRE ^ prefixExpr
					{
					root_0 = (Object)adaptor.nil();


					PRE101=(Token)match(input,PRE,FOLLOW_PRE_in_prefixExpr966); if (state.failed) return retval;
					if ( state.backtracking==0 ) {
					PRE101_tree = (Object)adaptor.create(PRE101);
					root_0 = (Object)adaptor.becomeRoot(PRE101_tree, root_0);
					}

					pushFollow(FOLLOW_prefixExpr_in_prefixExpr969);
					prefixExpr102=prefixExpr();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) adaptor.addChild(root_0, prefixExpr102.getTree());

					}
					break;
				case 4 :
					// Lustre.g:184:3: atomicExpr
					{
					root_0 = (Object)adaptor.nil();


					pushFollow(FOLLOW_atomicExpr_in_prefixExpr973);
					atomicExpr103=atomicExpr();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) adaptor.addChild(root_0, atomicExpr103.getTree());

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
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "atomicExpr"
	// Lustre.g:187:1: atomicExpr : ( ID | INT | real | BOOL | IF ^ expr 'then' ! expr 'else' ! expr | ID '(' ( expr ( ',' expr )* )? ')' -> ^( NODECALL ID ( expr )* ) | '(' expr ')' -> expr );
	public final LustreParser.atomicExpr_return atomicExpr() throws RecognitionException {
		LustreParser.atomicExpr_return retval = new LustreParser.atomicExpr_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		Token ID104=null;
		Token INT105=null;
		Token BOOL107=null;
		Token IF108=null;
		Token string_literal110=null;
		Token string_literal112=null;
		Token ID114=null;
		Token char_literal115=null;
		Token char_literal117=null;
		Token char_literal119=null;
		Token char_literal120=null;
		Token char_literal122=null;
		ParserRuleReturnScope real106 =null;
		ParserRuleReturnScope expr109 =null;
		ParserRuleReturnScope expr111 =null;
		ParserRuleReturnScope expr113 =null;
		ParserRuleReturnScope expr116 =null;
		ParserRuleReturnScope expr118 =null;
		ParserRuleReturnScope expr121 =null;

		Object ID104_tree=null;
		Object INT105_tree=null;
		Object BOOL107_tree=null;
		Object IF108_tree=null;
		Object string_literal110_tree=null;
		Object string_literal112_tree=null;
		Object ID114_tree=null;
		Object char_literal115_tree=null;
		Object char_literal117_tree=null;
		Object char_literal119_tree=null;
		Object char_literal120_tree=null;
		Object char_literal122_tree=null;
		RewriteRuleTokenStream stream_30=new RewriteRuleTokenStream(adaptor,"token 30");
		RewriteRuleTokenStream stream_31=new RewriteRuleTokenStream(adaptor,"token 31");
		RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
		RewriteRuleTokenStream stream_34=new RewriteRuleTokenStream(adaptor,"token 34");
		RewriteRuleSubtreeStream stream_expr=new RewriteRuleSubtreeStream(adaptor,"rule expr");

		try {
			// Lustre.g:187:11: ( ID | INT | real | BOOL | IF ^ expr 'then' ! expr 'else' ! expr | ID '(' ( expr ( ',' expr )* )? ')' -> ^( NODECALL ID ( expr )* ) | '(' expr ')' -> expr )
			int alt22=7;
			switch ( input.LA(1) ) {
			case ID:
				{
				int LA22_1 = input.LA(2);
				if ( (LA22_1==30) ) {
					alt22=6;
				}
				else if ( ((LA22_1 >= 31 && LA22_1 <= 35)||(LA22_1 >= 37 && LA22_1 <= 38)||(LA22_1 >= 40 && LA22_1 <= 47)||LA22_1==50||(LA22_1 >= 54 && LA22_1 <= 55)||LA22_1==60||LA22_1==65||LA22_1==68) ) {
					alt22=1;
				}

				else {
					if (state.backtracking>0) {state.failed=true; return retval;}
					int nvaeMark = input.mark();
					try {
						input.consume();
						NoViableAltException nvae =
							new NoViableAltException("", 22, 1, input);
						throw nvae;
					} finally {
						input.rewind(nvaeMark);
					}
				}

				}
				break;
			case INT:
				{
				int LA22_2 = input.LA(2);
				if ( (LA22_2==ERROR) ) {
					alt22=3;
				}
				else if ( ((LA22_2 >= 31 && LA22_2 <= 35)||(LA22_2 >= 37 && LA22_2 <= 38)||(LA22_2 >= 40 && LA22_2 <= 47)||LA22_2==50||(LA22_2 >= 54 && LA22_2 <= 55)||LA22_2==60||LA22_2==65||LA22_2==68) ) {
					alt22=2;
				}

				else {
					if (state.backtracking>0) {state.failed=true; return retval;}
					int nvaeMark = input.mark();
					try {
						input.consume();
						NoViableAltException nvae =
							new NoViableAltException("", 22, 2, input);
						throw nvae;
					} finally {
						input.rewind(nvaeMark);
					}
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
			case 30:
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
					// Lustre.g:188:3: ID
					{
					root_0 = (Object)adaptor.nil();


					ID104=(Token)match(input,ID,FOLLOW_ID_in_atomicExpr983); if (state.failed) return retval;
					if ( state.backtracking==0 ) {
					ID104_tree = (Object)adaptor.create(ID104);
					adaptor.addChild(root_0, ID104_tree);
					}

					}
					break;
				case 2 :
					// Lustre.g:189:3: INT
					{
					root_0 = (Object)adaptor.nil();


					INT105=(Token)match(input,INT,FOLLOW_INT_in_atomicExpr987); if (state.failed) return retval;
					if ( state.backtracking==0 ) {
					INT105_tree = (Object)adaptor.create(INT105);
					adaptor.addChild(root_0, INT105_tree);
					}

					}
					break;
				case 3 :
					// Lustre.g:190:3: real
					{
					root_0 = (Object)adaptor.nil();


					pushFollow(FOLLOW_real_in_atomicExpr991);
					real106=real();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) adaptor.addChild(root_0, real106.getTree());

					}
					break;
				case 4 :
					// Lustre.g:191:3: BOOL
					{
					root_0 = (Object)adaptor.nil();


					BOOL107=(Token)match(input,BOOL,FOLLOW_BOOL_in_atomicExpr995); if (state.failed) return retval;
					if ( state.backtracking==0 ) {
					BOOL107_tree = (Object)adaptor.create(BOOL107);
					adaptor.addChild(root_0, BOOL107_tree);
					}

					}
					break;
				case 5 :
					// Lustre.g:192:3: IF ^ expr 'then' ! expr 'else' ! expr
					{
					root_0 = (Object)adaptor.nil();


					IF108=(Token)match(input,IF,FOLLOW_IF_in_atomicExpr999); if (state.failed) return retval;
					if ( state.backtracking==0 ) {
					IF108_tree = (Object)adaptor.create(IF108);
					root_0 = (Object)adaptor.becomeRoot(IF108_tree, root_0);
					}

					pushFollow(FOLLOW_expr_in_atomicExpr1002);
					expr109=expr();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) adaptor.addChild(root_0, expr109.getTree());

					string_literal110=(Token)match(input,65,FOLLOW_65_in_atomicExpr1004); if (state.failed) return retval;
					pushFollow(FOLLOW_expr_in_atomicExpr1007);
					expr111=expr();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) adaptor.addChild(root_0, expr111.getTree());

					string_literal112=(Token)match(input,55,FOLLOW_55_in_atomicExpr1009); if (state.failed) return retval;
					pushFollow(FOLLOW_expr_in_atomicExpr1012);
					expr113=expr();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) adaptor.addChild(root_0, expr113.getTree());

					}
					break;
				case 6 :
					// Lustre.g:193:3: ID '(' ( expr ( ',' expr )* )? ')'
					{
					ID114=(Token)match(input,ID,FOLLOW_ID_in_atomicExpr1016); if (state.failed) return retval; 
					if ( state.backtracking==0 ) stream_ID.add(ID114);

					char_literal115=(Token)match(input,30,FOLLOW_30_in_atomicExpr1018); if (state.failed) return retval; 
					if ( state.backtracking==0 ) stream_30.add(char_literal115);

					// Lustre.g:193:10: ( expr ( ',' expr )* )?
					int alt21=2;
					int LA21_0 = input.LA(1);
					if ( (LA21_0==BOOL||(LA21_0 >= ID && LA21_0 <= IF)||LA21_0==INT||LA21_0==NOT||LA21_0==PRE||LA21_0==30||LA21_0==35) ) {
						alt21=1;
					}
					switch (alt21) {
						case 1 :
							// Lustre.g:193:11: expr ( ',' expr )*
							{
							pushFollow(FOLLOW_expr_in_atomicExpr1021);
							expr116=expr();
							state._fsp--;
							if (state.failed) return retval;
							if ( state.backtracking==0 ) stream_expr.add(expr116.getTree());
							// Lustre.g:193:16: ( ',' expr )*
							loop20:
							while (true) {
								int alt20=2;
								int LA20_0 = input.LA(1);
								if ( (LA20_0==34) ) {
									alt20=1;
								}

								switch (alt20) {
								case 1 :
									// Lustre.g:193:17: ',' expr
									{
									char_literal117=(Token)match(input,34,FOLLOW_34_in_atomicExpr1024); if (state.failed) return retval; 
									if ( state.backtracking==0 ) stream_34.add(char_literal117);

									pushFollow(FOLLOW_expr_in_atomicExpr1026);
									expr118=expr();
									state._fsp--;
									if (state.failed) return retval;
									if ( state.backtracking==0 ) stream_expr.add(expr118.getTree());
									}
									break;

								default :
									break loop20;
								}
							}

							}
							break;

					}

					char_literal119=(Token)match(input,31,FOLLOW_31_in_atomicExpr1032); if (state.failed) return retval; 
					if ( state.backtracking==0 ) stream_31.add(char_literal119);

					// AST REWRITE
					// elements: expr, ID
					// token labels: 
					// rule labels: retval
					// token list labels: 
					// rule list labels: 
					// wildcard labels: 
					if ( state.backtracking==0 ) {
					retval.tree = root_0;
					RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.getTree():null);

					root_0 = (Object)adaptor.nil();
					// 193:34: -> ^( NODECALL ID ( expr )* )
					{
						// Lustre.g:193:37: ^( NODECALL ID ( expr )* )
						{
						Object root_1 = (Object)adaptor.nil();
						root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(NODECALL, "NODECALL"), root_1);
						adaptor.addChild(root_1, stream_ID.nextNode());
						// Lustre.g:193:51: ( expr )*
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
					// Lustre.g:194:3: '(' expr ')'
					{
					char_literal120=(Token)match(input,30,FOLLOW_30_in_atomicExpr1047); if (state.failed) return retval; 
					if ( state.backtracking==0 ) stream_30.add(char_literal120);

					pushFollow(FOLLOW_expr_in_atomicExpr1049);
					expr121=expr();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) stream_expr.add(expr121.getTree());
					char_literal122=(Token)match(input,31,FOLLOW_31_in_atomicExpr1051); if (state.failed) return retval; 
					if ( state.backtracking==0 ) stream_31.add(char_literal122);

					// AST REWRITE
					// elements: expr
					// token labels: 
					// rule labels: retval
					// token list labels: 
					// rule list labels: 
					// wildcard labels: 
					if ( state.backtracking==0 ) {
					retval.tree = root_0;
					RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.getTree():null);

					root_0 = (Object)adaptor.nil();
					// 194:16: -> expr
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
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "real"
	// Lustre.g:197:1: real : INT '.' INT -> REAL[$real.text] ;
	public final LustreParser.real_return real() throws RecognitionException {
		LustreParser.real_return retval = new LustreParser.real_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		Token INT123=null;
		Token char_literal124=null;
		Token INT125=null;

		Object INT123_tree=null;
		Object char_literal124_tree=null;
		Object INT125_tree=null;
		RewriteRuleTokenStream stream_INT=new RewriteRuleTokenStream(adaptor,"token INT");
		RewriteRuleTokenStream stream_ERROR=new RewriteRuleTokenStream(adaptor,"token ERROR");

		try {
			// Lustre.g:197:5: ( INT '.' INT -> REAL[$real.text] )
			// Lustre.g:197:7: INT '.' INT
			{
			INT123=(Token)match(input,INT,FOLLOW_INT_in_real1063); if (state.failed) return retval; 
			if ( state.backtracking==0 ) stream_INT.add(INT123);

			char_literal124=(Token)match(input,ERROR,FOLLOW_ERROR_in_real1065); if (state.failed) return retval; 
			if ( state.backtracking==0 ) stream_ERROR.add(char_literal124);

			INT125=(Token)match(input,INT,FOLLOW_INT_in_real1067); if (state.failed) return retval; 
			if ( state.backtracking==0 ) stream_INT.add(INT125);

			// AST REWRITE
			// elements: 
			// token labels: 
			// rule labels: retval
			// token list labels: 
			// rule list labels: 
			// wildcard labels: 
			if ( state.backtracking==0 ) {
			retval.tree = root_0;
			RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.getTree():null);

			root_0 = (Object)adaptor.nil();
			// 197:19: -> REAL[$real.text]
			{
				adaptor.addChild(root_0, (Object)adaptor.create(REAL, input.toString(retval.start,input.LT(-1))));
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
		// Lustre.g:129:16: ( arrowOp )
		// Lustre.g:129:17: arrowOp
		{
		pushFollow(FOLLOW_arrowOp_in_synpred1_Lustre699);
		arrowOp();
		state._fsp--;
		if (state.failed) return;

		}

	}
	// $ANTLR end synpred1_Lustre

	// $ANTLR start synpred2_Lustre
	public final void synpred2_Lustre_fragment() throws RecognitionException {
		// Lustre.g:137:11: ( impliesOp )
		// Lustre.g:137:12: impliesOp
		{
		pushFollow(FOLLOW_impliesOp_in_synpred2_Lustre731);
		impliesOp();
		state._fsp--;
		if (state.failed) return;

		}

	}
	// $ANTLR end synpred2_Lustre

	// $ANTLR start synpred3_Lustre
	public final void synpred3_Lustre_fragment() throws RecognitionException {
		// Lustre.g:145:12: ( orOp )
		// Lustre.g:145:13: orOp
		{
		pushFollow(FOLLOW_orOp_in_synpred3_Lustre767);
		orOp();
		state._fsp--;
		if (state.failed) return;

		}

	}
	// $ANTLR end synpred3_Lustre

	// $ANTLR start synpred4_Lustre
	public final void synpred4_Lustre_fragment() throws RecognitionException {
		// Lustre.g:153:19: ( andOp )
		// Lustre.g:153:20: andOp
		{
		pushFollow(FOLLOW_andOp_in_synpred4_Lustre799);
		andOp();
		state._fsp--;
		if (state.failed) return;

		}

	}
	// $ANTLR end synpred4_Lustre

	// $ANTLR start synpred5_Lustre
	public final void synpred5_Lustre_fragment() throws RecognitionException {
		// Lustre.g:161:13: ( relationalOp )
		// Lustre.g:161:14: relationalOp
		{
		pushFollow(FOLLOW_relationalOp_in_synpred5_Lustre851);
		relationalOp();
		state._fsp--;
		if (state.failed) return;

		}

	}
	// $ANTLR end synpred5_Lustre

	// $ANTLR start synpred6_Lustre
	public final void synpred6_Lustre_fragment() throws RecognitionException {
		// Lustre.g:169:14: ( plusOp )
		// Lustre.g:169:15: plusOp
		{
		pushFollow(FOLLOW_plusOp_in_synpred6_Lustre887);
		plusOp();
		state._fsp--;
		if (state.failed) return;

		}

	}
	// $ANTLR end synpred6_Lustre

	// $ANTLR start synpred7_Lustre
	public final void synpred7_Lustre_fragment() throws RecognitionException {
		// Lustre.g:177:15: ( timesOp )
		// Lustre.g:177:16: timesOp
		{
		pushFollow(FOLLOW_timesOp_in_synpred7_Lustre927);
		timesOp();
		state._fsp--;
		if (state.failed) return;

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



	public static final BitSet FOLLOW_typedef_in_program145 = new BitSet(new long[]{0x0420000000000000L,0x0000000000000004L});
	public static final BitSet FOLLOW_constant_in_program149 = new BitSet(new long[]{0x0420000000000000L,0x0000000000000004L});
	public static final BitSet FOLLOW_node_in_program153 = new BitSet(new long[]{0x0420000000000000L,0x0000000000000004L});
	public static final BitSet FOLLOW_EOF_in_program157 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_66_in_typedef216 = new BitSet(new long[]{0x0000000000000400L});
	public static final BitSet FOLLOW_ID_in_typedef218 = new BitSet(new long[]{0x0000100000000000L});
	public static final BitSet FOLLOW_44_in_typedef220 = new BitSet(new long[]{0xA110000000000400L});
	public static final BitSet FOLLOW_type_in_typedef222 = new BitSet(new long[]{0x0000010000000000L});
	public static final BitSet FOLLOW_40_in_typedef224 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_53_in_constant242 = new BitSet(new long[]{0x0000000000000400L});
	public static final BitSet FOLLOW_ID_in_constant244 = new BitSet(new long[]{0x0000100000000000L});
	public static final BitSet FOLLOW_44_in_constant246 = new BitSet(new long[]{0x0000000840A02C20L});
	public static final BitSet FOLLOW_expr_in_constant248 = new BitSet(new long[]{0x0000010000000000L});
	public static final BitSet FOLLOW_40_in_constant250 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_58_in_node268 = new BitSet(new long[]{0x0000000000000400L});
	public static final BitSet FOLLOW_ID_in_node270 = new BitSet(new long[]{0x0000000040000000L});
	public static final BitSet FOLLOW_30_in_node272 = new BitSet(new long[]{0x0000000080000400L});
	public static final BitSet FOLLOW_varDeclList_in_node276 = new BitSet(new long[]{0x0000000080000000L});
	public static final BitSet FOLLOW_31_in_node279 = new BitSet(new long[]{0x4000000000000000L});
	public static final BitSet FOLLOW_62_in_node283 = new BitSet(new long[]{0x0000000040000000L});
	public static final BitSet FOLLOW_30_in_node285 = new BitSet(new long[]{0x0000000080000400L});
	public static final BitSet FOLLOW_varDeclList_in_node289 = new BitSet(new long[]{0x0000000080000000L});
	public static final BitSet FOLLOW_31_in_node292 = new BitSet(new long[]{0x0000010000000000L});
	public static final BitSet FOLLOW_40_in_node294 = new BitSet(new long[]{0x0200000000000000L,0x0000000000000008L});
	public static final BitSet FOLLOW_67_in_node299 = new BitSet(new long[]{0x0000000000000400L});
	public static final BitSet FOLLOW_varDeclList_in_node303 = new BitSet(new long[]{0x0000010000000000L});
	public static final BitSet FOLLOW_40_in_node305 = new BitSet(new long[]{0x0200000000000000L});
	public static final BitSet FOLLOW_57_in_node311 = new BitSet(new long[]{0x0008001040000400L,0x0000000000000001L});
	public static final BitSet FOLLOW_equation_in_node318 = new BitSet(new long[]{0x0008001040000400L,0x0000000000000001L});
	public static final BitSet FOLLOW_property_in_node322 = new BitSet(new long[]{0x0008001040000400L,0x0000000000000001L});
	public static final BitSet FOLLOW_assertion_in_node326 = new BitSet(new long[]{0x0008001040000400L,0x0000000000000001L});
	public static final BitSet FOLLOW_64_in_node332 = new BitSet(new long[]{0x0000010000000000L});
	public static final BitSet FOLLOW_40_in_node334 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_varDeclGroup_in_varDeclList435 = new BitSet(new long[]{0x0000010000000002L});
	public static final BitSet FOLLOW_40_in_varDeclList438 = new BitSet(new long[]{0x0000000000000400L});
	public static final BitSet FOLLOW_varDeclGroup_in_varDeclList440 = new BitSet(new long[]{0x0000010000000002L});
	public static final BitSet FOLLOW_ID_in_varDeclGroup457 = new BitSet(new long[]{0x0000008400000000L});
	public static final BitSet FOLLOW_34_in_varDeclGroup460 = new BitSet(new long[]{0x0000000000000400L});
	public static final BitSet FOLLOW_ID_in_varDeclGroup462 = new BitSet(new long[]{0x0000008400000000L});
	public static final BitSet FOLLOW_39_in_varDeclGroup466 = new BitSet(new long[]{0xA110000000000400L});
	public static final BitSet FOLLOW_type_in_varDeclGroup468 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_56_in_type487 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_63_in_type492 = new BitSet(new long[]{0x0001000000000000L});
	public static final BitSet FOLLOW_48_in_type494 = new BitSet(new long[]{0x0000000800002000L});
	public static final BitSet FOLLOW_bound_in_type498 = new BitSet(new long[]{0x0000000400000000L});
	public static final BitSet FOLLOW_34_in_type500 = new BitSet(new long[]{0x0000000800002000L});
	public static final BitSet FOLLOW_bound_in_type504 = new BitSet(new long[]{0x0002000000000000L});
	public static final BitSet FOLLOW_49_in_type506 = new BitSet(new long[]{0x0800000000000000L});
	public static final BitSet FOLLOW_59_in_type508 = new BitSet(new long[]{0x0100000000000000L});
	public static final BitSet FOLLOW_56_in_type510 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_52_in_type526 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_61_in_type531 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_ID_in_type536 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_35_in_bound547 = new BitSet(new long[]{0x0000000000002000L});
	public static final BitSet FOLLOW_INT_in_bound550 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_36_in_property565 = new BitSet(new long[]{0x0000000000000400L});
	public static final BitSet FOLLOW_ID_in_property567 = new BitSet(new long[]{0x0000010000000000L});
	public static final BitSet FOLLOW_40_in_property569 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_51_in_assertion583 = new BitSet(new long[]{0x0000000840A02C20L});
	public static final BitSet FOLLOW_expr_in_assertion585 = new BitSet(new long[]{0x0000010000000000L});
	public static final BitSet FOLLOW_40_in_assertion587 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_lhs_opt_parens_in_equation601 = new BitSet(new long[]{0x0000100000000000L});
	public static final BitSet FOLLOW_44_in_equation603 = new BitSet(new long[]{0x0000000840A02C20L});
	public static final BitSet FOLLOW_expr_in_equation605 = new BitSet(new long[]{0x0000010000000000L});
	public static final BitSet FOLLOW_40_in_equation607 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_lhs_in_lhs_opt_parens627 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_30_in_lhs_opt_parens631 = new BitSet(new long[]{0x0000000000000400L});
	public static final BitSet FOLLOW_lhs_in_lhs_opt_parens633 = new BitSet(new long[]{0x0000000080000000L});
	public static final BitSet FOLLOW_31_in_lhs_opt_parens635 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_ID_in_lhs649 = new BitSet(new long[]{0x0000000400000002L});
	public static final BitSet FOLLOW_34_in_lhs652 = new BitSet(new long[]{0x0000000000000400L});
	public static final BitSet FOLLOW_ID_in_lhs654 = new BitSet(new long[]{0x0000000400000002L});
	public static final BitSet FOLLOW_arrowExpr_in_expr675 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_37_in_arrowOp685 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_impliesExpr_in_arrowExpr695 = new BitSet(new long[]{0x0000002000000002L});
	public static final BitSet FOLLOW_arrowOp_in_arrowExpr702 = new BitSet(new long[]{0x0000000840A02C20L});
	public static final BitSet FOLLOW_arrowExpr_in_arrowExpr705 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_45_in_impliesOp717 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_orExpr_in_impliesExpr727 = new BitSet(new long[]{0x0000200000000002L});
	public static final BitSet FOLLOW_impliesOp_in_impliesExpr734 = new BitSet(new long[]{0x0000000840A02C20L});
	public static final BitSet FOLLOW_impliesExpr_in_impliesExpr737 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_andExpr_in_orExpr763 = new BitSet(new long[]{0x1000000000000002L,0x0000000000000010L});
	public static final BitSet FOLLOW_orOp_in_orExpr770 = new BitSet(new long[]{0x0000000840A02C20L});
	public static final BitSet FOLLOW_andExpr_in_orExpr773 = new BitSet(new long[]{0x1000000000000002L,0x0000000000000010L});
	public static final BitSet FOLLOW_50_in_andOp785 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_relationalExpr_in_andExpr795 = new BitSet(new long[]{0x0004000000000002L});
	public static final BitSet FOLLOW_andOp_in_andExpr802 = new BitSet(new long[]{0x0000000840A02C20L});
	public static final BitSet FOLLOW_relationalExpr_in_andExpr805 = new BitSet(new long[]{0x0004000000000002L});
	public static final BitSet FOLLOW_plusExpr_in_relationalExpr847 = new BitSet(new long[]{0x0000DE0000000002L});
	public static final BitSet FOLLOW_relationalOp_in_relationalExpr854 = new BitSet(new long[]{0x0000000840A02C20L});
	public static final BitSet FOLLOW_plusExpr_in_relationalExpr857 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_timesExpr_in_plusExpr883 = new BitSet(new long[]{0x0000000A00000002L});
	public static final BitSet FOLLOW_plusOp_in_plusExpr890 = new BitSet(new long[]{0x0000000840A02C20L});
	public static final BitSet FOLLOW_timesExpr_in_plusExpr893 = new BitSet(new long[]{0x0000000A00000002L});
	public static final BitSet FOLLOW_prefixExpr_in_timesExpr923 = new BitSet(new long[]{0x0040004100000002L});
	public static final BitSet FOLLOW_timesOp_in_timesExpr930 = new BitSet(new long[]{0x0000000840A02C20L});
	public static final BitSet FOLLOW_prefixExpr_in_timesExpr933 = new BitSet(new long[]{0x0040004100000002L});
	public static final BitSet FOLLOW_35_in_prefixExpr945 = new BitSet(new long[]{0x0000000840A02C20L});
	public static final BitSet FOLLOW_prefixExpr_in_prefixExpr947 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_NOT_in_prefixExpr959 = new BitSet(new long[]{0x0000000840A02C20L});
	public static final BitSet FOLLOW_prefixExpr_in_prefixExpr962 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_PRE_in_prefixExpr966 = new BitSet(new long[]{0x0000000840A02C20L});
	public static final BitSet FOLLOW_prefixExpr_in_prefixExpr969 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_atomicExpr_in_prefixExpr973 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_ID_in_atomicExpr983 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_INT_in_atomicExpr987 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_real_in_atomicExpr991 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_BOOL_in_atomicExpr995 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_IF_in_atomicExpr999 = new BitSet(new long[]{0x0000000840A02C20L});
	public static final BitSet FOLLOW_expr_in_atomicExpr1002 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
	public static final BitSet FOLLOW_65_in_atomicExpr1004 = new BitSet(new long[]{0x0000000840A02C20L});
	public static final BitSet FOLLOW_expr_in_atomicExpr1007 = new BitSet(new long[]{0x0080000000000000L});
	public static final BitSet FOLLOW_55_in_atomicExpr1009 = new BitSet(new long[]{0x0000000840A02C20L});
	public static final BitSet FOLLOW_expr_in_atomicExpr1012 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_ID_in_atomicExpr1016 = new BitSet(new long[]{0x0000000040000000L});
	public static final BitSet FOLLOW_30_in_atomicExpr1018 = new BitSet(new long[]{0x00000008C0A02C20L});
	public static final BitSet FOLLOW_expr_in_atomicExpr1021 = new BitSet(new long[]{0x0000000480000000L});
	public static final BitSet FOLLOW_34_in_atomicExpr1024 = new BitSet(new long[]{0x0000000840A02C20L});
	public static final BitSet FOLLOW_expr_in_atomicExpr1026 = new BitSet(new long[]{0x0000000480000000L});
	public static final BitSet FOLLOW_31_in_atomicExpr1032 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_30_in_atomicExpr1047 = new BitSet(new long[]{0x0000000840A02C20L});
	public static final BitSet FOLLOW_expr_in_atomicExpr1049 = new BitSet(new long[]{0x0000000080000000L});
	public static final BitSet FOLLOW_31_in_atomicExpr1051 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_INT_in_real1063 = new BitSet(new long[]{0x0000000000000200L});
	public static final BitSet FOLLOW_ERROR_in_real1065 = new BitSet(new long[]{0x0000000000002000L});
	public static final BitSet FOLLOW_INT_in_real1067 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_arrowOp_in_synpred1_Lustre699 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_impliesOp_in_synpred2_Lustre731 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_orOp_in_synpred3_Lustre767 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_andOp_in_synpred4_Lustre799 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_relationalOp_in_synpred5_Lustre851 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_plusOp_in_synpred6_Lustre887 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_timesOp_in_synpred7_Lustre927 = new BitSet(new long[]{0x0000000000000002L});
}
