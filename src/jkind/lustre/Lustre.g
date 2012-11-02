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
  REAL;
  NEGATE;
  IDENT;
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
    (equation | property)*
  'tel' ';' ->
    ^(ID
      ^(INPUTS $inputs?)
      ^(OUTPUTS $outputs?)
      ^(LOCALS $locals?)
      ^(EQUATIONS equation*)
      ^(PROPERTIES property*))
;

varDeclList:
  varDeclGroup (';' varDeclGroup)* -> varDeclGroup+
;

varDeclGroup:
  ID (',' ID)* ':' type -> ^(ID type)*
;

type:
  'int'^
| 'subrange' '[' bound ',' bound ']' 'of' 'int' -> 'int'
| 'bool'^
| 'real'^
| ID^
;

bound:
  '-'? INT
;

property:
  '--%PROPERTY' ID ';' -> ID
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
  ID -> ^(IDENT ID)
| INT
| real
| BOOL
| IF^ expr 'then'! expr 'else'! expr
| ID '(' (expr (',' expr)*)? ')' -> ^(NODECALL ID expr*)
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
