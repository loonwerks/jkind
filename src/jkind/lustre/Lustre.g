grammar Lustre;

options {
  language = Java;
}

@header {
  package jkind.lustre;
  
  import java.math.BigDecimal;
  import jkind.lustre.ast.*;
}

@lexer::header {
  package jkind.lustre;
}

@members {
  protected void ignore(Stack<Void> stack, List<Void> list, ArrayList<Void> arraylist) {}
}

@lexer::members {
  protected void ignore(Stack<Void> stack, List<Void> list, ArrayList<Void> arraylist) {}
}

node returns [Node n]
@init{
  List<VarDecl> locals = new ArrayList<VarDecl>();
  List<Equation> equations = new ArrayList<Equation>();
  List<String> properties = new ArrayList<String>();
}:

  'node' ID '(' inputs=varDeclList ')'
  'returns' '(' outputs=varDeclList ')' ';'
  ('var' vars=varDeclList ';'                    { locals.addAll(vars.decls); }
   )?
  'let'
    ( equation                                   { equations.add($equation.eq); }
    | property                                   { properties.add($property.p); }
    )*  
  'tel' ';'

  { $n = AST.node($inputs.decls, $outputs.decls, locals, equations, properties); }
;

varDeclList returns [List<VarDecl> decls]
@init{ $decls = new ArrayList<VarDecl>(); }:

  (g1=varDeclGroup                   { decls.addAll($g1.decls); }
    (';' g2=varDeclGroup             { decls.addAll($g2.decls); }
     )*
   )?
;

varDeclGroup returns [List<VarDecl> decls]
@init{ List<String> names = new ArrayList<String>(); }:
  
  v1=ID              { names.add($v1.text); }
    (',' v2=ID       { names.add($v2.text); }
     )*
  ':' type
  
  { $decls = new ArrayList<VarDecl>();
    for (String name : names) {
      ids.add(new VarDecl(name, $type.t));
    }
  }
;

type returns [Type t]:
  'int'     { $t = Type.INT; }
| 'bool'    { $t = Type.BOOL; }
| 'real'    { $t = Type.REAL; }
;

property returns [String p]:
  '--%PROPERTY' ID ';'           { $p = $ID.text; }
;

equation returns [Equation eq]:
  ID '=' expr ';'                { $eq = new Equation($ID.text, $expr.e); }
;

expr returns [Expr e]:
  arrowExpr                      { $e = $arrowExpr.e; }
;

arrowOp returns [BinaryOp op]:
  '->' { $op = BinaryOp.ARROW; }
;

arrowExpr returns [Expr e]:
  e1=impliesExpr                  { $e = $e1.e; }
    ((arrowOp)=>arrowOp
     e2=arrowExpr                 { $e = new BinaryExpr($e, $arrowOp.op, $e2.e); }
    )?
;

impliesOp returns [BinaryOp op]:
  '=>' { $op = BinaryOp.IMPLIES; }
;

impliesExpr returns [Expr e]:
  e1=orExpr                       { $e = $e1.e; }
    ((impliesOp)=>impliesOp
     e2=impliesExpr               { $e = new BinaryExpr($e, $impliesOp.op, $e2.e); }
    )?
;

orOp returns [BinaryOp op]:
  'or' { $op = BinaryOp.OR; }
| 'xor' { $op = BinaryOp.XOR; }
;

orExpr returns [Expr e]:
  e1=andExpr                    { $e = $e1.e; }
    ((orOp)=> (orOp)
     e2=andExpr                 { $e = new BinaryExpr($e, $orOp.op, $e2.e); }
    )*
;

andOp returns [BinaryOp op]:
  'and' { $op = BinaryOp.AND; }
;

andExpr returns [Expr e]:
  e1=relationalExpr                 { $e = $e1.e; }
    ((andOp)=> andOp
      e2=relationalExpr             { $e = new BinaryExpr($e, $andOp.op, $e2.e); }
    )*
;

relationalOp returns [BinaryOp op]:
  '<'  { $op = BinaryOp.LESS; }
| '<=' { $op = BinaryOp.LESSEQUAL; }
| '>'  { $op = BinaryOp.GREATER; }
| '>=' { $op = BinaryOp.GREATEREQUAL; }
| '='  { $op = BinaryOp.EQUAL; }
| '<>' { $op = BinaryOp.NOTEQUAL; }
;

relationalExpr returns [Expr e]:
  e1=plusExpr                       { $e = $e1.e; }
    ((relationalOp)=> relationalOp
     e2=plusExpr                    { $e = new BinaryExpr($e, $relationalOp.op, $e2.e); }
    )?
;

plusOp returns [BinaryOp op]:
  '+' { $op = BinaryOp.PLUS; }
| '-' { $op = BinaryOp.MINUS; }
;

plusExpr returns [Expr e]:
  e1=timesExpr                       { $e = $e1.e; }
    ((plusOp)=> plusOp
     e2=timesExpr                    { $e = new BinaryExpr($e, $plusOp.op, $e2.e); }
    )*
;

timesOp returns [BinaryOp op]:
  '*' { $op = BinaryOp.MULTIPLY; }
| '/' { $op = BinaryOp.DIVIDE; }
;

timesExpr returns [Expr e]:
  e1=prefixExpr                 { $e = $e1.e; }
    ((timesOp)=> timesOp 
     e2=prefixExpr              { $e = new BinaryExpr($e, $timesOp.op, $e2.e); }
    )* 
;

prefixExpr returns [Expr e]:
  '-' e1=prefixExpr            { $e = new UnaryExpr(UnaryOp.NEGATIVE, $e1.e); }
| 'not' e2=prefixExpr          { $e = new UnaryExpr(UnaryOp.NOT, $e2.e); }
| 'pre' e3=prefixExpr          { $e = new UnaryExpr(UnaryOp.PRE, $e3.e); }
| atomicExpr                   { $e = $atomicExpr.e; }
;

atomicExpr returns [Expr e]:
  ID                          { $e = new IdExpr($ID.text); }
| INT                         { $e = new IntExpr(Integer.parse($INT.text)); }
| real                        { $e = new RealExpr(new BigDecimal($real.text)); }
| bool                        { $e = new BoolExpr($bool.b); }
| 'if' e1=expr       
  'then' e2=expr       
  'else' e3=expr              { $e = new IfThenElseExpr($e1.e, $e2.e, $e3.e); }
| '(' p=expr ')       '       { $e = $p.e; }
;

real: INT '.' INT;

bool returns [Boolean b]:
  'true'    { $b = true; }
| 'false'   { $b = false; }
;

INT: ('0'..'9')+;
ID:
  ('a'..'z'|'A'..'Z'|'_') ('a'..'z'|'A'..'Z'|'_'|'0'..'9')*
;

WS: (' ' | '\t' | '\n' | '\r' | '\f')+ {$channel = HIDDEN;};

SL_COMMENT: '--' ~('\n'|'\r')* ('\r'? '\n')? {$channel=HIDDEN;};
ML_COMMENT: '/*' (options {greedy=false;} : .)* '*/' {$channel=HIDDEN;};
ERROR: '.';