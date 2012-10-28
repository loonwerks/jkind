// $ANTLR 3.4 Lustre.g 2012-10-28 08:04:56

  package jkind.lustre;

  import java.math.BigDecimal;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

@SuppressWarnings({"all", "warnings", "unchecked"})
public class LustreParser extends Parser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "ERROR", "ID", "INT", "MAIN", "ML_COMMENT", "SL_COMMENT", "WS", "'('", "')'", "'*'", "'+'", "','", "'-'", "'--%PROPERTY'", "'->'", "'/'", "':'", "';'", "'<'", "'<='", "'<>'", "'='", "'=>'", "'>'", "'>='", "'and'", "'bool'", "'const'", "'div'", "'else'", "'false'", "'if'", "'int'", "'let'", "'node'", "'not'", "'or'", "'pre'", "'real'", "'returns'", "'tel'", "'then'", "'true'", "'var'", "'xor'"
    };

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
    public static final int T__47=47;
    public static final int T__48=48;
    public static final int ERROR=4;
    public static final int ID=5;
    public static final int INT=6;
    public static final int MAIN=7;
    public static final int ML_COMMENT=8;
    public static final int SL_COMMENT=9;
    public static final int WS=10;

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

    public String[] getTokenNames() { return LustreParser.tokenNames; }
    public String getGrammarFileName() { return "Lustre.g"; }


      protected void ignore(Stack<Void> stack, List<Void> list, ArrayList<Void> arraylist) {}
      private Map<String, Expr> consts = new HashMap<String, Expr>();



    // $ANTLR start "node"
    // Lustre.g:26:1: node returns [Node n] : ( constant )* 'node' ID '(' inputs= varDeclList ')' 'returns' '(' outputs= varDeclList ')' ';' ( 'var' vars= varDeclList ';' )? 'let' ( equation | property )* 'tel' ';' ;
    public final Node node() throws RecognitionException {
        Node n = null;


        List<VarDecl> inputs =null;

        List<VarDecl> outputs =null;

        List<VarDecl> vars =null;

        Equation equation1 =null;

        String property2 =null;



          List<VarDecl> locals = new ArrayList<VarDecl>();
          List<Equation> equations = new ArrayList<Equation>();
          List<String> properties = new ArrayList<String>();

        try {
            // Lustre.g:31:2: ( ( constant )* 'node' ID '(' inputs= varDeclList ')' 'returns' '(' outputs= varDeclList ')' ';' ( 'var' vars= varDeclList ';' )? 'let' ( equation | property )* 'tel' ';' )
            // Lustre.g:32:3: ( constant )* 'node' ID '(' inputs= varDeclList ')' 'returns' '(' outputs= varDeclList ')' ';' ( 'var' vars= varDeclList ';' )? 'let' ( equation | property )* 'tel' ';'
            {
            // Lustre.g:32:3: ( constant )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0==31) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // Lustre.g:32:3: constant
            	    {
            	    pushFollow(FOLLOW_constant_in_node63);
            	    constant();

            	    state._fsp--;
            	    if (state.failed) return n;

            	    }
            	    break;

            	default :
            	    break loop1;
                }
            } while (true);


            match(input,38,FOLLOW_38_in_node68); if (state.failed) return n;

            match(input,ID,FOLLOW_ID_in_node70); if (state.failed) return n;

            match(input,11,FOLLOW_11_in_node72); if (state.failed) return n;

            pushFollow(FOLLOW_varDeclList_in_node76);
            inputs=varDeclList();

            state._fsp--;
            if (state.failed) return n;

            match(input,12,FOLLOW_12_in_node78); if (state.failed) return n;

            match(input,43,FOLLOW_43_in_node82); if (state.failed) return n;

            match(input,11,FOLLOW_11_in_node84); if (state.failed) return n;

            pushFollow(FOLLOW_varDeclList_in_node88);
            outputs=varDeclList();

            state._fsp--;
            if (state.failed) return n;

            match(input,12,FOLLOW_12_in_node90); if (state.failed) return n;

            match(input,21,FOLLOW_21_in_node92); if (state.failed) return n;

            // Lustre.g:35:3: ( 'var' vars= varDeclList ';' )?
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( (LA2_0==47) ) {
                alt2=1;
            }
            switch (alt2) {
                case 1 :
                    // Lustre.g:35:4: 'var' vars= varDeclList ';'
                    {
                    match(input,47,FOLLOW_47_in_node97); if (state.failed) return n;

                    pushFollow(FOLLOW_varDeclList_in_node101);
                    vars=varDeclList();

                    state._fsp--;
                    if (state.failed) return n;

                    match(input,21,FOLLOW_21_in_node103); if (state.failed) return n;

                    if ( state.backtracking==0 ) { locals.addAll(vars); }

                    }
                    break;

            }


            match(input,37,FOLLOW_37_in_node134); if (state.failed) return n;

            // Lustre.g:38:5: ( equation | property )*
            loop3:
            do {
                int alt3=3;
                int LA3_0 = input.LA(1);

                if ( (LA3_0==ID) ) {
                    alt3=1;
                }
                else if ( (LA3_0==17) ) {
                    alt3=2;
                }


                switch (alt3) {
            	case 1 :
            	    // Lustre.g:38:7: equation
            	    {
            	    pushFollow(FOLLOW_equation_in_node142);
            	    equation1=equation();

            	    state._fsp--;
            	    if (state.failed) return n;

            	    if ( state.backtracking==0 ) { equations.add(equation1); }

            	    }
            	    break;
            	case 2 :
            	    // Lustre.g:39:7: property
            	    {
            	    pushFollow(FOLLOW_property_in_node186);
            	    property2=property();

            	    state._fsp--;
            	    if (state.failed) return n;

            	    if ( state.backtracking==0 ) { properties.add(property2); }

            	    }
            	    break;

            	default :
            	    break loop3;
                }
            } while (true);


            match(input,44,FOLLOW_44_in_node233); if (state.failed) return n;

            match(input,21,FOLLOW_21_in_node235); if (state.failed) return n;

            if ( state.backtracking==0 ) { n = new Node(inputs, outputs, locals, equations, properties); }

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return n;
    }
    // $ANTLR end "node"



    // $ANTLR start "constant"
    // Lustre.g:46:1: constant : 'const' ID '=' expr ';' ;
    public final void constant() throws RecognitionException {
        Token ID3=null;
        Expr expr4 =null;


        try {
            // Lustre.g:46:9: ( 'const' ID '=' expr ';' )
            // Lustre.g:47:3: 'const' ID '=' expr ';'
            {
            match(input,31,FOLLOW_31_in_constant250); if (state.failed) return ;

            ID3=(Token)match(input,ID,FOLLOW_ID_in_constant252); if (state.failed) return ;

            match(input,25,FOLLOW_25_in_constant254); if (state.failed) return ;

            pushFollow(FOLLOW_expr_in_constant256);
            expr4=expr();

            state._fsp--;
            if (state.failed) return ;

            match(input,21,FOLLOW_21_in_constant258); if (state.failed) return ;

            if ( state.backtracking==0 ) { consts.put((ID3!=null?ID3.getText():null), expr4); }

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
    // $ANTLR end "constant"



    // $ANTLR start "varDeclList"
    // Lustre.g:50:1: varDeclList returns [List<VarDecl> decls] : (g1= varDeclGroup ( ';' g2= varDeclGroup )* )? ;
    public final List<VarDecl> varDeclList() throws RecognitionException {
        List<VarDecl> decls = null;


        List<VarDecl> g1 =null;

        List<VarDecl> g2 =null;


         decls = new ArrayList<VarDecl>(); 
        try {
            // Lustre.g:51:44: ( (g1= varDeclGroup ( ';' g2= varDeclGroup )* )? )
            // Lustre.g:53:3: (g1= varDeclGroup ( ';' g2= varDeclGroup )* )?
            {
            // Lustre.g:53:3: (g1= varDeclGroup ( ';' g2= varDeclGroup )* )?
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0==ID) ) {
                alt5=1;
            }
            switch (alt5) {
                case 1 :
                    // Lustre.g:53:4: g1= varDeclGroup ( ';' g2= varDeclGroup )*
                    {
                    pushFollow(FOLLOW_varDeclGroup_in_varDeclList291);
                    g1=varDeclGroup();

                    state._fsp--;
                    if (state.failed) return decls;

                    if ( state.backtracking==0 ) { decls.addAll(g1); }

                    // Lustre.g:54:5: ( ';' g2= varDeclGroup )*
                    loop4:
                    do {
                        int alt4=2;
                        int LA4_0 = input.LA(1);

                        if ( (LA4_0==21) ) {
                            int LA4_2 = input.LA(2);

                            if ( (LA4_2==ID) ) {
                                alt4=1;
                            }


                        }


                        switch (alt4) {
                    	case 1 :
                    	    // Lustre.g:54:6: ';' g2= varDeclGroup
                    	    {
                    	    match(input,21,FOLLOW_21_in_varDeclList318); if (state.failed) return decls;

                    	    pushFollow(FOLLOW_varDeclGroup_in_varDeclList322);
                    	    g2=varDeclGroup();

                    	    state._fsp--;
                    	    if (state.failed) return decls;

                    	    if ( state.backtracking==0 ) { decls.addAll(g2); }

                    	    }
                    	    break;

                    	default :
                    	    break loop4;
                        }
                    } while (true);


                    }
                    break;

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return decls;
    }
    // $ANTLR end "varDeclList"



    // $ANTLR start "varDeclGroup"
    // Lustre.g:59:1: varDeclGroup returns [List<VarDecl> decls] : v1= ID ( ',' v2= ID )* ':' type ;
    public final List<VarDecl> varDeclGroup() throws RecognitionException {
        List<VarDecl> decls = null;


        Token v1=null;
        Token v2=null;
        Type type5 =null;


         List<String> names = new ArrayList<String>(); 
        try {
            // Lustre.g:60:55: (v1= ID ( ',' v2= ID )* ':' type )
            // Lustre.g:62:3: v1= ID ( ',' v2= ID )* ':' type
            {
            v1=(Token)match(input,ID,FOLLOW_ID_in_varDeclGroup371); if (state.failed) return decls;

            if ( state.backtracking==0 ) { names.add((v1!=null?v1.getText():null)); }

            // Lustre.g:63:5: ( ',' v2= ID )*
            loop6:
            do {
                int alt6=2;
                int LA6_0 = input.LA(1);

                if ( (LA6_0==15) ) {
                    alt6=1;
                }


                switch (alt6) {
            	case 1 :
            	    // Lustre.g:63:6: ',' v2= ID
            	    {
            	    match(input,15,FOLLOW_15_in_varDeclGroup393); if (state.failed) return decls;

            	    v2=(Token)match(input,ID,FOLLOW_ID_in_varDeclGroup397); if (state.failed) return decls;

            	    if ( state.backtracking==0 ) { names.add((v2!=null?v2.getText():null)); }

            	    }
            	    break;

            	default :
            	    break loop6;
                }
            } while (true);


            match(input,20,FOLLOW_20_in_varDeclGroup417); if (state.failed) return decls;

            pushFollow(FOLLOW_type_in_varDeclGroup419);
            type5=type();

            state._fsp--;
            if (state.failed) return decls;

            if ( state.backtracking==0 ) { decls = new ArrayList<VarDecl>();
                for (String name : names) {
                  decls.add(new VarDecl(name, type5));
                }
              }

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return decls;
    }
    // $ANTLR end "varDeclGroup"



    // $ANTLR start "type"
    // Lustre.g:74:1: type returns [Type t] : ( 'int' | 'bool' | 'real' );
    public final Type type() throws RecognitionException {
        Type t = null;


        try {
            // Lustre.g:74:22: ( 'int' | 'bool' | 'real' )
            int alt7=3;
            switch ( input.LA(1) ) {
            case 36:
                {
                alt7=1;
                }
                break;
            case 30:
                {
                alt7=2;
                }
                break;
            case 42:
                {
                alt7=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return t;}
                NoViableAltException nvae =
                    new NoViableAltException("", 7, 0, input);

                throw nvae;

            }

            switch (alt7) {
                case 1 :
                    // Lustre.g:75:3: 'int'
                    {
                    match(input,36,FOLLOW_36_in_type438); if (state.failed) return t;

                    if ( state.backtracking==0 ) { t = Type.INT; }

                    }
                    break;
                case 2 :
                    // Lustre.g:76:3: 'bool'
                    {
                    match(input,30,FOLLOW_30_in_type448); if (state.failed) return t;

                    if ( state.backtracking==0 ) { t = Type.BOOL; }

                    }
                    break;
                case 3 :
                    // Lustre.g:77:3: 'real'
                    {
                    match(input,42,FOLLOW_42_in_type457); if (state.failed) return t;

                    if ( state.backtracking==0 ) { t = Type.REAL; }

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
        return t;
    }
    // $ANTLR end "type"



    // $ANTLR start "property"
    // Lustre.g:80:1: property returns [String p] : '--%PROPERTY' ID ';' ;
    public final String property() throws RecognitionException {
        String p = null;


        Token ID6=null;

        try {
            // Lustre.g:80:28: ( '--%PROPERTY' ID ';' )
            // Lustre.g:81:3: '--%PROPERTY' ID ';'
            {
            match(input,17,FOLLOW_17_in_property476); if (state.failed) return p;

            ID6=(Token)match(input,ID,FOLLOW_ID_in_property478); if (state.failed) return p;

            match(input,21,FOLLOW_21_in_property480); if (state.failed) return p;

            if ( state.backtracking==0 ) { p = (ID6!=null?ID6.getText():null); }

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return p;
    }
    // $ANTLR end "property"



    // $ANTLR start "equation"
    // Lustre.g:84:1: equation returns [Equation eq] : ID '=' expr ';' ;
    public final Equation equation() throws RecognitionException {
        Equation eq = null;


        Token ID7=null;
        Expr expr8 =null;


        try {
            // Lustre.g:84:31: ( ID '=' expr ';' )
            // Lustre.g:85:3: ID '=' expr ';'
            {
            ID7=(Token)match(input,ID,FOLLOW_ID_in_equation506); if (state.failed) return eq;

            match(input,25,FOLLOW_25_in_equation508); if (state.failed) return eq;

            pushFollow(FOLLOW_expr_in_equation510);
            expr8=expr();

            state._fsp--;
            if (state.failed) return eq;

            match(input,21,FOLLOW_21_in_equation512); if (state.failed) return eq;

            if ( state.backtracking==0 ) { eq = new Equation((ID7!=null?ID7.getText():null), expr8); }

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return eq;
    }
    // $ANTLR end "equation"



    // $ANTLR start "expr"
    // Lustre.g:88:1: expr returns [Expr e] : arrowExpr ;
    public final Expr expr() throws RecognitionException {
        Expr e = null;


        Expr arrowExpr9 =null;


        try {
            // Lustre.g:88:22: ( arrowExpr )
            // Lustre.g:89:3: arrowExpr
            {
            pushFollow(FOLLOW_arrowExpr_in_expr543);
            arrowExpr9=arrowExpr();

            state._fsp--;
            if (state.failed) return e;

            if ( state.backtracking==0 ) { e = arrowExpr9; }

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return e;
    }
    // $ANTLR end "expr"



    // $ANTLR start "arrowOp"
    // Lustre.g:92:1: arrowOp returns [BinaryOp op] : '->' ;
    public final BinaryOp arrowOp() throws RecognitionException {
        BinaryOp op = null;


        try {
            // Lustre.g:92:30: ( '->' )
            // Lustre.g:93:3: '->'
            {
            match(input,18,FOLLOW_18_in_arrowOp580); if (state.failed) return op;

            if ( state.backtracking==0 ) { op = BinaryOp.ARROW; }

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return op;
    }
    // $ANTLR end "arrowOp"



    // $ANTLR start "arrowExpr"
    // Lustre.g:96:1: arrowExpr returns [Expr e] : e1= impliesExpr ( ( arrowOp )=> arrowOp e2= arrowExpr )? ;
    public final Expr arrowExpr() throws RecognitionException {
        Expr e = null;


        Expr e1 =null;

        Expr e2 =null;

        BinaryOp arrowOp10 =null;


        try {
            // Lustre.g:96:27: (e1= impliesExpr ( ( arrowOp )=> arrowOp e2= arrowExpr )? )
            // Lustre.g:97:3: e1= impliesExpr ( ( arrowOp )=> arrowOp e2= arrowExpr )?
            {
            pushFollow(FOLLOW_impliesExpr_in_arrowExpr598);
            e1=impliesExpr();

            state._fsp--;
            if (state.failed) return e;

            if ( state.backtracking==0 ) { e = e1; }

            // Lustre.g:98:5: ( ( arrowOp )=> arrowOp e2= arrowExpr )?
            int alt8=2;
            int LA8_0 = input.LA(1);

            if ( (LA8_0==18) ) {
                int LA8_1 = input.LA(2);

                if ( (synpred1_Lustre()) ) {
                    alt8=1;
                }
            }
            switch (alt8) {
                case 1 :
                    // Lustre.g:98:6: ( arrowOp )=> arrowOp e2= arrowExpr
                    {
                    pushFollow(FOLLOW_arrowOp_in_arrowExpr628);
                    arrowOp10=arrowOp();

                    state._fsp--;
                    if (state.failed) return e;

                    pushFollow(FOLLOW_arrowExpr_in_arrowExpr637);
                    e2=arrowExpr();

                    state._fsp--;
                    if (state.failed) return e;

                    if ( state.backtracking==0 ) { e = new BinaryExpr(e, arrowOp10, e2); }

                    }
                    break;

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return e;
    }
    // $ANTLR end "arrowExpr"



    // $ANTLR start "impliesOp"
    // Lustre.g:103:1: impliesOp returns [BinaryOp op] : '=>' ;
    public final BinaryOp impliesOp() throws RecognitionException {
        BinaryOp op = null;


        try {
            // Lustre.g:103:32: ( '=>' )
            // Lustre.g:104:3: '=>'
            {
            match(input,26,FOLLOW_26_in_impliesOp676); if (state.failed) return op;

            if ( state.backtracking==0 ) { op = BinaryOp.IMPLIES; }

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return op;
    }
    // $ANTLR end "impliesOp"



    // $ANTLR start "impliesExpr"
    // Lustre.g:107:1: impliesExpr returns [Expr e] : e1= orExpr ( ( impliesOp )=> impliesOp e2= impliesExpr )? ;
    public final Expr impliesExpr() throws RecognitionException {
        Expr e = null;


        Expr e1 =null;

        Expr e2 =null;

        BinaryOp impliesOp11 =null;


        try {
            // Lustre.g:107:29: (e1= orExpr ( ( impliesOp )=> impliesOp e2= impliesExpr )? )
            // Lustre.g:108:3: e1= orExpr ( ( impliesOp )=> impliesOp e2= impliesExpr )?
            {
            pushFollow(FOLLOW_orExpr_in_impliesExpr694);
            e1=orExpr();

            state._fsp--;
            if (state.failed) return e;

            if ( state.backtracking==0 ) { e = e1; }

            // Lustre.g:109:5: ( ( impliesOp )=> impliesOp e2= impliesExpr )?
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( (LA9_0==26) ) {
                int LA9_1 = input.LA(2);

                if ( (synpred2_Lustre()) ) {
                    alt9=1;
                }
            }
            switch (alt9) {
                case 1 :
                    // Lustre.g:109:6: ( impliesOp )=> impliesOp e2= impliesExpr
                    {
                    pushFollow(FOLLOW_impliesOp_in_impliesExpr729);
                    impliesOp11=impliesOp();

                    state._fsp--;
                    if (state.failed) return e;

                    pushFollow(FOLLOW_impliesExpr_in_impliesExpr738);
                    e2=impliesExpr();

                    state._fsp--;
                    if (state.failed) return e;

                    if ( state.backtracking==0 ) { e = new BinaryExpr(e, impliesOp11, e2); }

                    }
                    break;

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return e;
    }
    // $ANTLR end "impliesExpr"



    // $ANTLR start "orOp"
    // Lustre.g:114:1: orOp returns [BinaryOp op] : ( 'or' | 'xor' );
    public final BinaryOp orOp() throws RecognitionException {
        BinaryOp op = null;


        try {
            // Lustre.g:114:27: ( 'or' | 'xor' )
            int alt10=2;
            int LA10_0 = input.LA(1);

            if ( (LA10_0==40) ) {
                alt10=1;
            }
            else if ( (LA10_0==48) ) {
                alt10=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return op;}
                NoViableAltException nvae =
                    new NoViableAltException("", 10, 0, input);

                throw nvae;

            }
            switch (alt10) {
                case 1 :
                    // Lustre.g:115:3: 'or'
                    {
                    match(input,40,FOLLOW_40_in_orOp775); if (state.failed) return op;

                    if ( state.backtracking==0 ) { op = BinaryOp.OR; }

                    }
                    break;
                case 2 :
                    // Lustre.g:116:3: 'xor'
                    {
                    match(input,48,FOLLOW_48_in_orOp781); if (state.failed) return op;

                    if ( state.backtracking==0 ) { op = BinaryOp.XOR; }

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
        return op;
    }
    // $ANTLR end "orOp"



    // $ANTLR start "orExpr"
    // Lustre.g:119:1: orExpr returns [Expr e] : e1= andExpr ( ( orOp )=> ( orOp ) e2= andExpr )* ;
    public final Expr orExpr() throws RecognitionException {
        Expr e = null;


        Expr e1 =null;

        Expr e2 =null;

        BinaryOp orOp12 =null;


        try {
            // Lustre.g:119:24: (e1= andExpr ( ( orOp )=> ( orOp ) e2= andExpr )* )
            // Lustre.g:120:3: e1= andExpr ( ( orOp )=> ( orOp ) e2= andExpr )*
            {
            pushFollow(FOLLOW_andExpr_in_orExpr799);
            e1=andExpr();

            state._fsp--;
            if (state.failed) return e;

            if ( state.backtracking==0 ) { e = e1; }

            // Lustre.g:121:5: ( ( orOp )=> ( orOp ) e2= andExpr )*
            loop11:
            do {
                int alt11=2;
                int LA11_0 = input.LA(1);

                if ( (LA11_0==40) ) {
                    int LA11_2 = input.LA(2);

                    if ( (synpred3_Lustre()) ) {
                        alt11=1;
                    }


                }
                else if ( (LA11_0==48) ) {
                    int LA11_3 = input.LA(2);

                    if ( (synpred3_Lustre()) ) {
                        alt11=1;
                    }


                }


                switch (alt11) {
            	case 1 :
            	    // Lustre.g:121:6: ( orOp )=> ( orOp ) e2= andExpr
            	    {
            	    // Lustre.g:121:15: ( orOp )
            	    // Lustre.g:121:16: orOp
            	    {
            	    pushFollow(FOLLOW_orOp_in_orExpr833);
            	    orOp12=orOp();

            	    state._fsp--;
            	    if (state.failed) return e;

            	    }


            	    pushFollow(FOLLOW_andExpr_in_orExpr843);
            	    e2=andExpr();

            	    state._fsp--;
            	    if (state.failed) return e;

            	    if ( state.backtracking==0 ) { e = new BinaryExpr(e, orOp12, e2); }

            	    }
            	    break;

            	default :
            	    break loop11;
                }
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
        return e;
    }
    // $ANTLR end "orExpr"



    // $ANTLR start "andOp"
    // Lustre.g:126:1: andOp returns [BinaryOp op] : 'and' ;
    public final BinaryOp andOp() throws RecognitionException {
        BinaryOp op = null;


        try {
            // Lustre.g:126:28: ( 'and' )
            // Lustre.g:127:3: 'and'
            {
            match(input,29,FOLLOW_29_in_andOp882); if (state.failed) return op;

            if ( state.backtracking==0 ) { op = BinaryOp.AND; }

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return op;
    }
    // $ANTLR end "andOp"



    // $ANTLR start "andExpr"
    // Lustre.g:130:1: andExpr returns [Expr e] : e1= relationalExpr ( ( andOp )=> andOp e2= relationalExpr )* ;
    public final Expr andExpr() throws RecognitionException {
        Expr e = null;


        Expr e1 =null;

        Expr e2 =null;

        BinaryOp andOp13 =null;


        try {
            // Lustre.g:130:25: (e1= relationalExpr ( ( andOp )=> andOp e2= relationalExpr )* )
            // Lustre.g:131:3: e1= relationalExpr ( ( andOp )=> andOp e2= relationalExpr )*
            {
            pushFollow(FOLLOW_relationalExpr_in_andExpr900);
            e1=relationalExpr();

            state._fsp--;
            if (state.failed) return e;

            if ( state.backtracking==0 ) { e = e1; }

            // Lustre.g:132:5: ( ( andOp )=> andOp e2= relationalExpr )*
            loop12:
            do {
                int alt12=2;
                int LA12_0 = input.LA(1);

                if ( (LA12_0==29) ) {
                    int LA12_2 = input.LA(2);

                    if ( (synpred4_Lustre()) ) {
                        alt12=1;
                    }


                }


                switch (alt12) {
            	case 1 :
            	    // Lustre.g:132:6: ( andOp )=> andOp e2= relationalExpr
            	    {
            	    pushFollow(FOLLOW_andOp_in_andExpr930);
            	    andOp13=andOp();

            	    state._fsp--;
            	    if (state.failed) return e;

            	    pushFollow(FOLLOW_relationalExpr_in_andExpr940);
            	    e2=relationalExpr();

            	    state._fsp--;
            	    if (state.failed) return e;

            	    if ( state.backtracking==0 ) { e = new BinaryExpr(e, andOp13, e2); }

            	    }
            	    break;

            	default :
            	    break loop12;
                }
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
        return e;
    }
    // $ANTLR end "andExpr"



    // $ANTLR start "relationalOp"
    // Lustre.g:137:1: relationalOp returns [BinaryOp op] : ( '<' | '<=' | '>' | '>=' | '=' | '<>' );
    public final BinaryOp relationalOp() throws RecognitionException {
        BinaryOp op = null;


        try {
            // Lustre.g:137:35: ( '<' | '<=' | '>' | '>=' | '=' | '<>' )
            int alt13=6;
            switch ( input.LA(1) ) {
            case 22:
                {
                alt13=1;
                }
                break;
            case 23:
                {
                alt13=2;
                }
                break;
            case 27:
                {
                alt13=3;
                }
                break;
            case 28:
                {
                alt13=4;
                }
                break;
            case 25:
                {
                alt13=5;
                }
                break;
            case 24:
                {
                alt13=6;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return op;}
                NoViableAltException nvae =
                    new NoViableAltException("", 13, 0, input);

                throw nvae;

            }

            switch (alt13) {
                case 1 :
                    // Lustre.g:138:3: '<'
                    {
                    match(input,22,FOLLOW_22_in_relationalOp975); if (state.failed) return op;

                    if ( state.backtracking==0 ) { op = BinaryOp.LESS; }

                    }
                    break;
                case 2 :
                    // Lustre.g:139:3: '<='
                    {
                    match(input,23,FOLLOW_23_in_relationalOp982); if (state.failed) return op;

                    if ( state.backtracking==0 ) { op = BinaryOp.LESSEQUAL; }

                    }
                    break;
                case 3 :
                    // Lustre.g:140:3: '>'
                    {
                    match(input,27,FOLLOW_27_in_relationalOp988); if (state.failed) return op;

                    if ( state.backtracking==0 ) { op = BinaryOp.GREATER; }

                    }
                    break;
                case 4 :
                    // Lustre.g:141:3: '>='
                    {
                    match(input,28,FOLLOW_28_in_relationalOp995); if (state.failed) return op;

                    if ( state.backtracking==0 ) { op = BinaryOp.GREATEREQUAL; }

                    }
                    break;
                case 5 :
                    // Lustre.g:142:3: '='
                    {
                    match(input,25,FOLLOW_25_in_relationalOp1001); if (state.failed) return op;

                    if ( state.backtracking==0 ) { op = BinaryOp.EQUAL; }

                    }
                    break;
                case 6 :
                    // Lustre.g:143:3: '<>'
                    {
                    match(input,24,FOLLOW_24_in_relationalOp1008); if (state.failed) return op;

                    if ( state.backtracking==0 ) { op = BinaryOp.NOTEQUAL; }

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
        return op;
    }
    // $ANTLR end "relationalOp"



    // $ANTLR start "relationalExpr"
    // Lustre.g:146:1: relationalExpr returns [Expr e] : e1= plusExpr ( ( relationalOp )=> relationalOp e2= plusExpr )? ;
    public final Expr relationalExpr() throws RecognitionException {
        Expr e = null;


        Expr e1 =null;

        Expr e2 =null;

        BinaryOp relationalOp14 =null;


        try {
            // Lustre.g:146:32: (e1= plusExpr ( ( relationalOp )=> relationalOp e2= plusExpr )? )
            // Lustre.g:147:3: e1= plusExpr ( ( relationalOp )=> relationalOp e2= plusExpr )?
            {
            pushFollow(FOLLOW_plusExpr_in_relationalExpr1026);
            e1=plusExpr();

            state._fsp--;
            if (state.failed) return e;

            if ( state.backtracking==0 ) { e = e1; }

            // Lustre.g:148:5: ( ( relationalOp )=> relationalOp e2= plusExpr )?
            int alt14=2;
            switch ( input.LA(1) ) {
                case 22:
                    {
                    int LA14_1 = input.LA(2);

                    if ( (synpred5_Lustre()) ) {
                        alt14=1;
                    }
                    }
                    break;
                case 23:
                    {
                    int LA14_2 = input.LA(2);

                    if ( (synpred5_Lustre()) ) {
                        alt14=1;
                    }
                    }
                    break;
                case 27:
                    {
                    int LA14_3 = input.LA(2);

                    if ( (synpred5_Lustre()) ) {
                        alt14=1;
                    }
                    }
                    break;
                case 28:
                    {
                    int LA14_4 = input.LA(2);

                    if ( (synpred5_Lustre()) ) {
                        alt14=1;
                    }
                    }
                    break;
                case 25:
                    {
                    int LA14_5 = input.LA(2);

                    if ( (synpred5_Lustre()) ) {
                        alt14=1;
                    }
                    }
                    break;
                case 24:
                    {
                    int LA14_6 = input.LA(2);

                    if ( (synpred5_Lustre()) ) {
                        alt14=1;
                    }
                    }
                    break;
            }

            switch (alt14) {
                case 1 :
                    // Lustre.g:148:6: ( relationalOp )=> relationalOp e2= plusExpr
                    {
                    pushFollow(FOLLOW_relationalOp_in_relationalExpr1062);
                    relationalOp14=relationalOp();

                    state._fsp--;
                    if (state.failed) return e;

                    pushFollow(FOLLOW_plusExpr_in_relationalExpr1071);
                    e2=plusExpr();

                    state._fsp--;
                    if (state.failed) return e;

                    if ( state.backtracking==0 ) { e = new BinaryExpr(e, relationalOp14, e2); }

                    }
                    break;

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return e;
    }
    // $ANTLR end "relationalExpr"



    // $ANTLR start "plusOp"
    // Lustre.g:153:1: plusOp returns [BinaryOp op] : ( '+' | '-' );
    public final BinaryOp plusOp() throws RecognitionException {
        BinaryOp op = null;


        try {
            // Lustre.g:153:29: ( '+' | '-' )
            int alt15=2;
            int LA15_0 = input.LA(1);

            if ( (LA15_0==14) ) {
                alt15=1;
            }
            else if ( (LA15_0==16) ) {
                alt15=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return op;}
                NoViableAltException nvae =
                    new NoViableAltException("", 15, 0, input);

                throw nvae;

            }
            switch (alt15) {
                case 1 :
                    // Lustre.g:154:3: '+'
                    {
                    match(input,14,FOLLOW_14_in_plusOp1113); if (state.failed) return op;

                    if ( state.backtracking==0 ) { op = BinaryOp.PLUS; }

                    }
                    break;
                case 2 :
                    // Lustre.g:155:3: '-'
                    {
                    match(input,16,FOLLOW_16_in_plusOp1119); if (state.failed) return op;

                    if ( state.backtracking==0 ) { op = BinaryOp.MINUS; }

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
        return op;
    }
    // $ANTLR end "plusOp"



    // $ANTLR start "plusExpr"
    // Lustre.g:158:1: plusExpr returns [Expr e] : e1= timesExpr ( ( plusOp )=> plusOp e2= timesExpr )* ;
    public final Expr plusExpr() throws RecognitionException {
        Expr e = null;


        Expr e1 =null;

        Expr e2 =null;

        BinaryOp plusOp15 =null;


        try {
            // Lustre.g:158:26: (e1= timesExpr ( ( plusOp )=> plusOp e2= timesExpr )* )
            // Lustre.g:159:3: e1= timesExpr ( ( plusOp )=> plusOp e2= timesExpr )*
            {
            pushFollow(FOLLOW_timesExpr_in_plusExpr1137);
            e1=timesExpr();

            state._fsp--;
            if (state.failed) return e;

            if ( state.backtracking==0 ) { e = e1; }

            // Lustre.g:160:5: ( ( plusOp )=> plusOp e2= timesExpr )*
            loop16:
            do {
                int alt16=2;
                int LA16_0 = input.LA(1);

                if ( (LA16_0==14) ) {
                    int LA16_2 = input.LA(2);

                    if ( (synpred6_Lustre()) ) {
                        alt16=1;
                    }


                }
                else if ( (LA16_0==16) ) {
                    int LA16_3 = input.LA(2);

                    if ( (synpred6_Lustre()) ) {
                        alt16=1;
                    }


                }


                switch (alt16) {
            	case 1 :
            	    // Lustre.g:160:6: ( plusOp )=> plusOp e2= timesExpr
            	    {
            	    pushFollow(FOLLOW_plusOp_in_plusExpr1173);
            	    plusOp15=plusOp();

            	    state._fsp--;
            	    if (state.failed) return e;

            	    pushFollow(FOLLOW_timesExpr_in_plusExpr1182);
            	    e2=timesExpr();

            	    state._fsp--;
            	    if (state.failed) return e;

            	    if ( state.backtracking==0 ) { e = new BinaryExpr(e, plusOp15, e2); }

            	    }
            	    break;

            	default :
            	    break loop16;
                }
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
        return e;
    }
    // $ANTLR end "plusExpr"



    // $ANTLR start "timesOp"
    // Lustre.g:165:1: timesOp returns [BinaryOp op] : ( '*' | '/' | 'div' );
    public final BinaryOp timesOp() throws RecognitionException {
        BinaryOp op = null;


        try {
            // Lustre.g:165:30: ( '*' | '/' | 'div' )
            int alt17=3;
            switch ( input.LA(1) ) {
            case 13:
                {
                alt17=1;
                }
                break;
            case 19:
                {
                alt17=2;
                }
                break;
            case 32:
                {
                alt17=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return op;}
                NoViableAltException nvae =
                    new NoViableAltException("", 17, 0, input);

                throw nvae;

            }

            switch (alt17) {
                case 1 :
                    // Lustre.g:166:3: '*'
                    {
                    match(input,13,FOLLOW_13_in_timesOp1224); if (state.failed) return op;

                    if ( state.backtracking==0 ) { op = BinaryOp.MULTIPLY; }

                    }
                    break;
                case 2 :
                    // Lustre.g:167:3: '/'
                    {
                    match(input,19,FOLLOW_19_in_timesOp1232); if (state.failed) return op;

                    if ( state.backtracking==0 ) { op = BinaryOp.DIVIDE; }

                    }
                    break;
                case 3 :
                    // Lustre.g:168:3: 'div'
                    {
                    match(input,32,FOLLOW_32_in_timesOp1240); if (state.failed) return op;

                    if ( state.backtracking==0 ) { op = BinaryOp.INT_DIVIDE; }

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
        return op;
    }
    // $ANTLR end "timesOp"



    // $ANTLR start "timesExpr"
    // Lustre.g:171:1: timesExpr returns [Expr e] : e1= prefixExpr ( ( timesOp )=> timesOp e2= prefixExpr )* ;
    public final Expr timesExpr() throws RecognitionException {
        Expr e = null;


        Expr e1 =null;

        Expr e2 =null;

        BinaryOp timesOp16 =null;


        try {
            // Lustre.g:171:27: (e1= prefixExpr ( ( timesOp )=> timesOp e2= prefixExpr )* )
            // Lustre.g:172:3: e1= prefixExpr ( ( timesOp )=> timesOp e2= prefixExpr )*
            {
            pushFollow(FOLLOW_prefixExpr_in_timesExpr1258);
            e1=prefixExpr();

            state._fsp--;
            if (state.failed) return e;

            if ( state.backtracking==0 ) { e = e1; }

            // Lustre.g:173:5: ( ( timesOp )=> timesOp e2= prefixExpr )*
            loop18:
            do {
                int alt18=2;
                switch ( input.LA(1) ) {
                case 13:
                    {
                    int LA18_2 = input.LA(2);

                    if ( (synpred7_Lustre()) ) {
                        alt18=1;
                    }


                    }
                    break;
                case 19:
                    {
                    int LA18_3 = input.LA(2);

                    if ( (synpred7_Lustre()) ) {
                        alt18=1;
                    }


                    }
                    break;
                case 32:
                    {
                    int LA18_4 = input.LA(2);

                    if ( (synpred7_Lustre()) ) {
                        alt18=1;
                    }


                    }
                    break;

                }

                switch (alt18) {
            	case 1 :
            	    // Lustre.g:173:6: ( timesOp )=> timesOp e2= prefixExpr
            	    {
            	    pushFollow(FOLLOW_timesOp_in_timesExpr1288);
            	    timesOp16=timesOp();

            	    state._fsp--;
            	    if (state.failed) return e;

            	    pushFollow(FOLLOW_prefixExpr_in_timesExpr1297);
            	    e2=prefixExpr();

            	    state._fsp--;
            	    if (state.failed) return e;

            	    if ( state.backtracking==0 ) { e = new BinaryExpr(e, timesOp16, e2); }

            	    }
            	    break;

            	default :
            	    break loop18;
                }
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
        return e;
    }
    // $ANTLR end "timesExpr"



    // $ANTLR start "prefixExpr"
    // Lustre.g:178:1: prefixExpr returns [Expr e] : ( '-' e1= prefixExpr | 'not' e2= prefixExpr | 'pre' e3= prefixExpr | atomicExpr );
    public final Expr prefixExpr() throws RecognitionException {
        Expr e = null;


        Expr e1 =null;

        Expr e2 =null;

        Expr e3 =null;

        Expr atomicExpr17 =null;


        try {
            // Lustre.g:178:28: ( '-' e1= prefixExpr | 'not' e2= prefixExpr | 'pre' e3= prefixExpr | atomicExpr )
            int alt19=4;
            switch ( input.LA(1) ) {
            case 16:
                {
                alt19=1;
                }
                break;
            case 39:
                {
                alt19=2;
                }
                break;
            case 41:
                {
                alt19=3;
                }
                break;
            case ID:
            case INT:
            case 11:
            case 34:
            case 35:
            case 46:
                {
                alt19=4;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return e;}
                NoViableAltException nvae =
                    new NoViableAltException("", 19, 0, input);

                throw nvae;

            }

            switch (alt19) {
                case 1 :
                    // Lustre.g:179:3: '-' e1= prefixExpr
                    {
                    match(input,16,FOLLOW_16_in_prefixExpr1333); if (state.failed) return e;

                    pushFollow(FOLLOW_prefixExpr_in_prefixExpr1337);
                    e1=prefixExpr();

                    state._fsp--;
                    if (state.failed) return e;

                    if ( state.backtracking==0 ) { e = new UnaryExpr(UnaryOp.NEGATIVE, e1); }

                    }
                    break;
                case 2 :
                    // Lustre.g:180:3: 'not' e2= prefixExpr
                    {
                    match(input,39,FOLLOW_39_in_prefixExpr1354); if (state.failed) return e;

                    pushFollow(FOLLOW_prefixExpr_in_prefixExpr1358);
                    e2=prefixExpr();

                    state._fsp--;
                    if (state.failed) return e;

                    if ( state.backtracking==0 ) { e = new UnaryExpr(UnaryOp.NOT, e2); }

                    }
                    break;
                case 3 :
                    // Lustre.g:181:3: 'pre' e3= prefixExpr
                    {
                    match(input,41,FOLLOW_41_in_prefixExpr1373); if (state.failed) return e;

                    pushFollow(FOLLOW_prefixExpr_in_prefixExpr1377);
                    e3=prefixExpr();

                    state._fsp--;
                    if (state.failed) return e;

                    if ( state.backtracking==0 ) { e = new UnaryExpr(UnaryOp.PRE, e3); }

                    }
                    break;
                case 4 :
                    // Lustre.g:182:3: atomicExpr
                    {
                    pushFollow(FOLLOW_atomicExpr_in_prefixExpr1392);
                    atomicExpr17=atomicExpr();

                    state._fsp--;
                    if (state.failed) return e;

                    if ( state.backtracking==0 ) { e = atomicExpr17; }

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
        return e;
    }
    // $ANTLR end "prefixExpr"



    // $ANTLR start "atomicExpr"
    // Lustre.g:185:1: atomicExpr returns [Expr e] : ( ID | INT | real | bool | 'if' e1= expr 'then' e2= expr 'else' e3= expr | '(' p= expr ')' );
    public final Expr atomicExpr() throws RecognitionException {
        Expr e = null;


        Token ID18=null;
        Token INT19=null;
        Expr e1 =null;

        Expr e2 =null;

        Expr e3 =null;

        Expr p =null;

        LustreParser.real_return real20 =null;

        Boolean bool21 =null;


        try {
            // Lustre.g:185:28: ( ID | INT | real | bool | 'if' e1= expr 'then' e2= expr 'else' e3= expr | '(' p= expr ')' )
            int alt20=6;
            switch ( input.LA(1) ) {
            case ID:
                {
                alt20=1;
                }
                break;
            case INT:
                {
                int LA20_2 = input.LA(2);

                if ( (LA20_2==ERROR) ) {
                    alt20=3;
                }
                else if ( ((LA20_2 >= 12 && LA20_2 <= 14)||LA20_2==16||(LA20_2 >= 18 && LA20_2 <= 19)||(LA20_2 >= 21 && LA20_2 <= 29)||(LA20_2 >= 32 && LA20_2 <= 33)||LA20_2==40||LA20_2==45||LA20_2==48) ) {
                    alt20=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return e;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 20, 2, input);

                    throw nvae;

                }
                }
                break;
            case 34:
            case 46:
                {
                alt20=4;
                }
                break;
            case 35:
                {
                alt20=5;
                }
                break;
            case 11:
                {
                alt20=6;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return e;}
                NoViableAltException nvae =
                    new NoViableAltException("", 20, 0, input);

                throw nvae;

            }

            switch (alt20) {
                case 1 :
                    // Lustre.g:186:3: ID
                    {
                    ID18=(Token)match(input,ID,FOLLOW_ID_in_atomicExpr1426); if (state.failed) return e;

                    if ( state.backtracking==0 ) { if (consts.containsKey((ID18!=null?ID18.getText():null))) {
                                                      e = consts.get((ID18!=null?ID18.getText():null));
                                                    } else {
                                                      e = new IdExpr((ID18!=null?ID18.getText():null));
                                                    }
                                                  }

                    }
                    break;
                case 2 :
                    // Lustre.g:192:3: INT
                    {
                    INT19=(Token)match(input,INT,FOLLOW_INT_in_atomicExpr1457); if (state.failed) return e;

                    if ( state.backtracking==0 ) { e = new IntExpr(Integer.parseInt((INT19!=null?INT19.getText():null))); }

                    }
                    break;
                case 3 :
                    // Lustre.g:193:3: real
                    {
                    pushFollow(FOLLOW_real_in_atomicExpr1487);
                    real20=real();

                    state._fsp--;
                    if (state.failed) return e;

                    if ( state.backtracking==0 ) { e = new RealExpr(new BigDecimal((real20!=null?input.toString(real20.start,real20.stop):null))); }

                    }
                    break;
                case 4 :
                    // Lustre.g:194:3: bool
                    {
                    pushFollow(FOLLOW_bool_in_atomicExpr1516);
                    bool21=bool();

                    state._fsp--;
                    if (state.failed) return e;

                    if ( state.backtracking==0 ) { e = new BoolExpr(bool21); }

                    }
                    break;
                case 5 :
                    // Lustre.g:195:3: 'if' e1= expr 'then' e2= expr 'else' e3= expr
                    {
                    match(input,35,FOLLOW_35_in_atomicExpr1545); if (state.failed) return e;

                    pushFollow(FOLLOW_expr_in_atomicExpr1549);
                    e1=expr();

                    state._fsp--;
                    if (state.failed) return e;

                    match(input,45,FOLLOW_45_in_atomicExpr1553); if (state.failed) return e;

                    pushFollow(FOLLOW_expr_in_atomicExpr1557);
                    e2=expr();

                    state._fsp--;
                    if (state.failed) return e;

                    match(input,33,FOLLOW_33_in_atomicExpr1561); if (state.failed) return e;

                    pushFollow(FOLLOW_expr_in_atomicExpr1565);
                    e3=expr();

                    state._fsp--;
                    if (state.failed) return e;

                    if ( state.backtracking==0 ) { e = new IfThenElseExpr(e1, e2, e3); }

                    }
                    break;
                case 6 :
                    // Lustre.g:198:3: '(' p= expr ')'
                    {
                    match(input,11,FOLLOW_11_in_atomicExpr1584); if (state.failed) return e;

                    pushFollow(FOLLOW_expr_in_atomicExpr1588);
                    p=expr();

                    state._fsp--;
                    if (state.failed) return e;

                    match(input,12,FOLLOW_12_in_atomicExpr1590); if (state.failed) return e;

                    if ( state.backtracking==0 ) { e = p; }

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
        return e;
    }
    // $ANTLR end "atomicExpr"


    public static class real_return extends ParserRuleReturnScope {
    };


    // $ANTLR start "real"
    // Lustre.g:201:1: real : INT '.' INT ;
    public final LustreParser.real_return real() throws RecognitionException {
        LustreParser.real_return retval = new LustreParser.real_return();
        retval.start = input.LT(1);


        try {
            // Lustre.g:201:5: ( INT '.' INT )
            // Lustre.g:201:7: INT '.' INT
            {
            match(input,INT,FOLLOW_INT_in_real1613); if (state.failed) return retval;

            match(input,ERROR,FOLLOW_ERROR_in_real1615); if (state.failed) return retval;

            match(input,INT,FOLLOW_INT_in_real1617); if (state.failed) return retval;

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
    // $ANTLR end "real"



    // $ANTLR start "bool"
    // Lustre.g:203:1: bool returns [Boolean b] : ( 'true' | 'false' );
    public final Boolean bool() throws RecognitionException {
        Boolean b = null;


        try {
            // Lustre.g:203:25: ( 'true' | 'false' )
            int alt21=2;
            int LA21_0 = input.LA(1);

            if ( (LA21_0==46) ) {
                alt21=1;
            }
            else if ( (LA21_0==34) ) {
                alt21=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return b;}
                NoViableAltException nvae =
                    new NoViableAltException("", 21, 0, input);

                throw nvae;

            }
            switch (alt21) {
                case 1 :
                    // Lustre.g:204:3: 'true'
                    {
                    match(input,46,FOLLOW_46_in_bool1630); if (state.failed) return b;

                    if ( state.backtracking==0 ) { b = true; }

                    }
                    break;
                case 2 :
                    // Lustre.g:205:3: 'false'
                    {
                    match(input,34,FOLLOW_34_in_bool1639); if (state.failed) return b;

                    if ( state.backtracking==0 ) { b = false; }

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
        return b;
    }
    // $ANTLR end "bool"

    // $ANTLR start synpred1_Lustre
    public final void synpred1_Lustre_fragment() throws RecognitionException {
        // Lustre.g:98:6: ( arrowOp )
        // Lustre.g:98:7: arrowOp
        {
        pushFollow(FOLLOW_arrowOp_in_synpred1_Lustre625);
        arrowOp();

        state._fsp--;
        if (state.failed) return ;

        }

    }
    // $ANTLR end synpred1_Lustre

    // $ANTLR start synpred2_Lustre
    public final void synpred2_Lustre_fragment() throws RecognitionException {
        // Lustre.g:109:6: ( impliesOp )
        // Lustre.g:109:7: impliesOp
        {
        pushFollow(FOLLOW_impliesOp_in_synpred2_Lustre726);
        impliesOp();

        state._fsp--;
        if (state.failed) return ;

        }

    }
    // $ANTLR end synpred2_Lustre

    // $ANTLR start synpred3_Lustre
    public final void synpred3_Lustre_fragment() throws RecognitionException {
        // Lustre.g:121:6: ( orOp )
        // Lustre.g:121:7: orOp
        {
        pushFollow(FOLLOW_orOp_in_synpred3_Lustre828);
        orOp();

        state._fsp--;
        if (state.failed) return ;

        }

    }
    // $ANTLR end synpred3_Lustre

    // $ANTLR start synpred4_Lustre
    public final void synpred4_Lustre_fragment() throws RecognitionException {
        // Lustre.g:132:6: ( andOp )
        // Lustre.g:132:7: andOp
        {
        pushFollow(FOLLOW_andOp_in_synpred4_Lustre926);
        andOp();

        state._fsp--;
        if (state.failed) return ;

        }

    }
    // $ANTLR end synpred4_Lustre

    // $ANTLR start synpred5_Lustre
    public final void synpred5_Lustre_fragment() throws RecognitionException {
        // Lustre.g:148:6: ( relationalOp )
        // Lustre.g:148:7: relationalOp
        {
        pushFollow(FOLLOW_relationalOp_in_synpred5_Lustre1058);
        relationalOp();

        state._fsp--;
        if (state.failed) return ;

        }

    }
    // $ANTLR end synpred5_Lustre

    // $ANTLR start synpred6_Lustre
    public final void synpred6_Lustre_fragment() throws RecognitionException {
        // Lustre.g:160:6: ( plusOp )
        // Lustre.g:160:7: plusOp
        {
        pushFollow(FOLLOW_plusOp_in_synpred6_Lustre1169);
        plusOp();

        state._fsp--;
        if (state.failed) return ;

        }

    }
    // $ANTLR end synpred6_Lustre

    // $ANTLR start synpred7_Lustre
    public final void synpred7_Lustre_fragment() throws RecognitionException {
        // Lustre.g:173:6: ( timesOp )
        // Lustre.g:173:7: timesOp
        {
        pushFollow(FOLLOW_timesOp_in_synpred7_Lustre1284);
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


 

    public static final BitSet FOLLOW_constant_in_node63 = new BitSet(new long[]{0x0000004080000000L});
    public static final BitSet FOLLOW_38_in_node68 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_ID_in_node70 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_11_in_node72 = new BitSet(new long[]{0x0000000000001020L});
    public static final BitSet FOLLOW_varDeclList_in_node76 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_12_in_node78 = new BitSet(new long[]{0x0000080000000000L});
    public static final BitSet FOLLOW_43_in_node82 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_11_in_node84 = new BitSet(new long[]{0x0000000000001020L});
    public static final BitSet FOLLOW_varDeclList_in_node88 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_12_in_node90 = new BitSet(new long[]{0x0000000000200000L});
    public static final BitSet FOLLOW_21_in_node92 = new BitSet(new long[]{0x0000802000000000L});
    public static final BitSet FOLLOW_47_in_node97 = new BitSet(new long[]{0x0000000000200020L});
    public static final BitSet FOLLOW_varDeclList_in_node101 = new BitSet(new long[]{0x0000000000200000L});
    public static final BitSet FOLLOW_21_in_node103 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_37_in_node134 = new BitSet(new long[]{0x0000100000020020L});
    public static final BitSet FOLLOW_equation_in_node142 = new BitSet(new long[]{0x0000100000020020L});
    public static final BitSet FOLLOW_property_in_node186 = new BitSet(new long[]{0x0000100000020020L});
    public static final BitSet FOLLOW_44_in_node233 = new BitSet(new long[]{0x0000000000200000L});
    public static final BitSet FOLLOW_21_in_node235 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_31_in_constant250 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_ID_in_constant252 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_25_in_constant254 = new BitSet(new long[]{0x0000428C00010860L});
    public static final BitSet FOLLOW_expr_in_constant256 = new BitSet(new long[]{0x0000000000200000L});
    public static final BitSet FOLLOW_21_in_constant258 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_varDeclGroup_in_varDeclList291 = new BitSet(new long[]{0x0000000000200002L});
    public static final BitSet FOLLOW_21_in_varDeclList318 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_varDeclGroup_in_varDeclList322 = new BitSet(new long[]{0x0000000000200002L});
    public static final BitSet FOLLOW_ID_in_varDeclGroup371 = new BitSet(new long[]{0x0000000000108000L});
    public static final BitSet FOLLOW_15_in_varDeclGroup393 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_ID_in_varDeclGroup397 = new BitSet(new long[]{0x0000000000108000L});
    public static final BitSet FOLLOW_20_in_varDeclGroup417 = new BitSet(new long[]{0x0000041040000000L});
    public static final BitSet FOLLOW_type_in_varDeclGroup419 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_36_in_type438 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_30_in_type448 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_42_in_type457 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_17_in_property476 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_ID_in_property478 = new BitSet(new long[]{0x0000000000200000L});
    public static final BitSet FOLLOW_21_in_property480 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_equation506 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_25_in_equation508 = new BitSet(new long[]{0x0000428C00010860L});
    public static final BitSet FOLLOW_expr_in_equation510 = new BitSet(new long[]{0x0000000000200000L});
    public static final BitSet FOLLOW_21_in_equation512 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_arrowExpr_in_expr543 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_18_in_arrowOp580 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_impliesExpr_in_arrowExpr598 = new BitSet(new long[]{0x0000000000040002L});
    public static final BitSet FOLLOW_arrowOp_in_arrowExpr628 = new BitSet(new long[]{0x0000428C00010860L});
    public static final BitSet FOLLOW_arrowExpr_in_arrowExpr637 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_26_in_impliesOp676 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_orExpr_in_impliesExpr694 = new BitSet(new long[]{0x0000000004000002L});
    public static final BitSet FOLLOW_impliesOp_in_impliesExpr729 = new BitSet(new long[]{0x0000428C00010860L});
    public static final BitSet FOLLOW_impliesExpr_in_impliesExpr738 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_40_in_orOp775 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_48_in_orOp781 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_andExpr_in_orExpr799 = new BitSet(new long[]{0x0001010000000002L});
    public static final BitSet FOLLOW_orOp_in_orExpr833 = new BitSet(new long[]{0x0000428C00010860L});
    public static final BitSet FOLLOW_andExpr_in_orExpr843 = new BitSet(new long[]{0x0001010000000002L});
    public static final BitSet FOLLOW_29_in_andOp882 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_relationalExpr_in_andExpr900 = new BitSet(new long[]{0x0000000020000002L});
    public static final BitSet FOLLOW_andOp_in_andExpr930 = new BitSet(new long[]{0x0000428C00010860L});
    public static final BitSet FOLLOW_relationalExpr_in_andExpr940 = new BitSet(new long[]{0x0000000020000002L});
    public static final BitSet FOLLOW_22_in_relationalOp975 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_23_in_relationalOp982 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_27_in_relationalOp988 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_28_in_relationalOp995 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_25_in_relationalOp1001 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_24_in_relationalOp1008 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_plusExpr_in_relationalExpr1026 = new BitSet(new long[]{0x000000001BC00002L});
    public static final BitSet FOLLOW_relationalOp_in_relationalExpr1062 = new BitSet(new long[]{0x0000428C00010860L});
    public static final BitSet FOLLOW_plusExpr_in_relationalExpr1071 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_14_in_plusOp1113 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_16_in_plusOp1119 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_timesExpr_in_plusExpr1137 = new BitSet(new long[]{0x0000000000014002L});
    public static final BitSet FOLLOW_plusOp_in_plusExpr1173 = new BitSet(new long[]{0x0000428C00010860L});
    public static final BitSet FOLLOW_timesExpr_in_plusExpr1182 = new BitSet(new long[]{0x0000000000014002L});
    public static final BitSet FOLLOW_13_in_timesOp1224 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_19_in_timesOp1232 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_32_in_timesOp1240 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_prefixExpr_in_timesExpr1258 = new BitSet(new long[]{0x0000000100082002L});
    public static final BitSet FOLLOW_timesOp_in_timesExpr1288 = new BitSet(new long[]{0x0000428C00010860L});
    public static final BitSet FOLLOW_prefixExpr_in_timesExpr1297 = new BitSet(new long[]{0x0000000100082002L});
    public static final BitSet FOLLOW_16_in_prefixExpr1333 = new BitSet(new long[]{0x0000428C00010860L});
    public static final BitSet FOLLOW_prefixExpr_in_prefixExpr1337 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_39_in_prefixExpr1354 = new BitSet(new long[]{0x0000428C00010860L});
    public static final BitSet FOLLOW_prefixExpr_in_prefixExpr1358 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_41_in_prefixExpr1373 = new BitSet(new long[]{0x0000428C00010860L});
    public static final BitSet FOLLOW_prefixExpr_in_prefixExpr1377 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_atomicExpr_in_prefixExpr1392 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_atomicExpr1426 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INT_in_atomicExpr1457 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_real_in_atomicExpr1487 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_bool_in_atomicExpr1516 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_35_in_atomicExpr1545 = new BitSet(new long[]{0x0000428C00010860L});
    public static final BitSet FOLLOW_expr_in_atomicExpr1549 = new BitSet(new long[]{0x0000200000000000L});
    public static final BitSet FOLLOW_45_in_atomicExpr1553 = new BitSet(new long[]{0x0000428C00010860L});
    public static final BitSet FOLLOW_expr_in_atomicExpr1557 = new BitSet(new long[]{0x0000000200000000L});
    public static final BitSet FOLLOW_33_in_atomicExpr1561 = new BitSet(new long[]{0x0000428C00010860L});
    public static final BitSet FOLLOW_expr_in_atomicExpr1565 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_11_in_atomicExpr1584 = new BitSet(new long[]{0x0000428C00010860L});
    public static final BitSet FOLLOW_expr_in_atomicExpr1588 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_12_in_atomicExpr1590 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INT_in_real1613 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ERROR_in_real1615 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_INT_in_real1617 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_46_in_bool1630 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_34_in_bool1639 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_arrowOp_in_synpred1_Lustre625 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_impliesOp_in_synpred2_Lustre726 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_orOp_in_synpred3_Lustre828 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_andOp_in_synpred4_Lustre926 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_relationalOp_in_synpred5_Lustre1058 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_plusOp_in_synpred6_Lustre1169 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_timesOp_in_synpred7_Lustre1284 = new BitSet(new long[]{0x0000000000000002L});

}