grammar Lustre;

options {
  language = Java;
  output = AST;
}

tokens {
  NODE;
  CONSTANTS;
  INPUTS;
  OUTPUTS;
  LOCALS;
  EQUATIONS;
  PROPERTIES;
  REAL;
  NEGATE;
  IDENT;
}

@header {
  package jkind.lustre;
}

@lexer::header {
  package jkind.lustre;
}

@members {
  protected void ignore(Stack<Void> stack, List<Void> list, ArrayList<Void> arraylist) {}
  private Map<String, Expr> consts = new HashMap<String, Expr>();
  
  private CommonTree makeReal(String text) {
    return new CommonTree(new CommonToken(REAL, text));
  }
  
  @Override
  public void emitErrorMessage(String msg) {
    System.out.println(msg);
  }
}

@lexer::members {
  protected void ignore(Stack<Void> stack, List<Void> list, ArrayList<Void> arraylist) {}
}

node:
  constant*
  'node' ID '(' inputs=varDeclList? ')'
  'returns' '(' outputs=varDeclList? ')' ';'
  ('var' locals=varDeclList ';')?
  'let'
    (equation | property)*
  'tel' ';' ->
    ^(NODE ID
      ^(CONSTANTS constant*)
      ^(INPUTS $inputs?)
      ^(OUTPUTS $outputs?)
      ^(LOCALS $locals?)
      ^(EQUATIONS equation*)
      ^(PROPERTIES property*))
;

constant:
  'const' ID '=' expr ';' -> ^(ID expr)
;

varDeclList:
  varDeclGroup (';' varDeclGroup)* -> varDeclGroup+
;

varDeclGroup:
  ID (',' ID)* ':' type -> ^(ID type)*
;

type:
  'int'^
| 'subrange' '[' INT ',' INT ']' 'of' 'int' -> 'int'
| 'bool'^
| 'real'^
;

property:
  '--%PROPERTY' ID ';' -> ID
;

equation:
  ID '=' expr ';' -> ^(ID expr)
;

expr:
  arrowExpr
;

arrowOp:
  '->'
;

arrowExpr:
  impliesExpr ((arrowOp)=>arrowOp^ arrowExpr)?
;

impliesOp:
  '=>'
;

impliesExpr:
  orExpr ((impliesOp)=>impliesOp^ impliesExpr)?
;

orOp:
  'or' | 'xor'
;

orExpr:
  andExpr ((orOp)=>orOp^ andExpr)*
;

andOp:
  'and'
;

andExpr:
  relationalExpr ((andOp)=>andOp^ relationalExpr)*
;

relationalOp:
  '<' | '<=' | '>' | '>=' | '=' | '<>'
;

relationalExpr:
  plusExpr ((relationalOp)=>relationalOp^ plusExpr)?
;

plusOp:
  '+' | '-'
;

plusExpr:
  timesExpr ((plusOp)=>plusOp^ timesExpr)*
;

timesOp:
  '*' | '/' | 'div'
;

timesExpr:
  prefixExpr ((timesOp)=>timesOp^ prefixExpr)*
;

prefixExpr:
  '-' prefixExpr -> ^(NEGATE prefixExpr)
| NOT^ prefixExpr
| PRE^ prefixExpr
| atomicExpr
;

atomicExpr:
  ID -> ^(IDENT ID)
| INT
| real
| BOOL
| IF^ expr 'then'! expr 'else'! expr
| '(' expr ')' -> expr
;

real: a=INT '.' b=INT -> {makeReal($a.text + "." + $b.text)};

IF: 'if';
NOT: 'not';
PRE: 'pre';

BOOL: 'true' | 'false';
INT: ('0'..'9')+;
ID:
  ('a'..'z'|'A'..'Z'|'_') ('a'..'z'|'A'..'Z'|'_'|'0'..'9')*
;

WS: (' ' | '\t' | '\n' | '\r' | '\f')+ {$channel = HIDDEN;};

SL_COMMENT: '--' (~('%'|'\n'|'\r') ~('\n'|'\r')* | /* empty */) ('\r'? '\n')? {$channel=HIDDEN;};
ML_COMMENT: '/*' (options {greedy=false;} : .)* '*/' {$channel=HIDDEN;};
MAIN: '--%MAIN' ';'? {$channel=HIDDEN;};

ERROR: '.';
