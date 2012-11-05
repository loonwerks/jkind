grammar Lustre;

options {
  language = Java;
  output = AST;
}

tokens {
  PROGRAM;
  TYPES;
  NODES;
  CONSTANTS;
  INPUTS;
  OUTPUTS;
  LOCALS;
  EQUATIONS;
  EQUATION;
  LHS;
  PROPERTIES;
  ASSERTIONS;
  REAL;
  NEGATE;
  NODECALL;
}

@header {
  package jkind.lustre;
}

@lexer::header {
  package jkind.lustre;
}

@members {
  protected void ignore(Stack<Void> stack, List<Void> list, ArrayList<Void> arraylist) {}

  @Override
  public void emitErrorMessage(String msg) {
    System.out.println(msg);
  }
}

@lexer::members {
  protected void ignore(Stack<Void> stack, List<Void> list, ArrayList<Void> arraylist) {}
}

program:
  (typedef | constant | node)* EOF ->
    ^(PROGRAM
      ^(TYPES typedef*)
      ^(CONSTANTS constant*)
      ^(NODES node*))
;

typedef:
  'type' ID '=' type ';' -> ^(ID type)
;

constant:
  'const' ID '=' expr ';' -> ^(ID expr)
;

node:
  'node' ID '(' inputs=varDeclList? ')'
  'returns' '(' outputs=varDeclList? ')' ';'
  ('var' locals=varDeclList ';')?
  'let'
    (equation | property | assertion)*
  'tel' ';' ->
    ^(ID
      ^(INPUTS $inputs?)
      ^(OUTPUTS $outputs?)
      ^(LOCALS $locals?)
      ^(EQUATIONS equation*)
      ^(PROPERTIES property*)
      ^(ASSERTIONS assertion*))
;

varDeclList:
  varDeclGroup (';' varDeclGroup)* -> varDeclGroup+
;

varDeclGroup:
  ID (',' ID)* ':' type -> ^(ID type)*
;

type:
  'int'^
| 'subrange' '[' low=bound ',' high=bound ']' 'of' 'int' -> ^('int' $low $high)
| 'bool'^
| 'real'^
| ID^
;

bound:
  '-'? INT -> INT[$bound.text]
;

property:
  '--%PROPERTY' ID ';' -> ID
;

assertion:
  'assert' expr ';' -> expr
;

equation:
  lhs_opt_parens '=' expr ';' -> ^(EQUATION lhs_opt_parens expr)
;

lhs_opt_parens:
  lhs
| '(' lhs ')' -> lhs
;

lhs:
  ID (',' ID)* -> ^(LHS ID*)
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
  ID
| INT
| real
| BOOL
| IF^ expr 'then'! expr 'else'! expr
| ID '(' (expr (',' expr)*)? ')' -> ^(NODECALL ID expr*)
| '(' expr ')' -> expr
;

real: INT '.' INT -> REAL[$real.text];

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
