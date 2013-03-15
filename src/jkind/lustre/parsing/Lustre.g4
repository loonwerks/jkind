grammar Lustre;

program: (typedef | constant | node)* EOF;

typedef: 'type' ID '=' type ';';

constant: 'const' ID (':' type)? '=' expr ';';

node:
  'node' ID '(' input=varDeclList? ')'
  'returns' '(' output=varDeclList? ')' ';'
  ('var' local=varDeclList ';')?
  'let'
    (equation | property | assertion)*
  'tel' ';'?
;

varDeclList: varDeclGroup (';' varDeclGroup)*;

varDeclGroup: ID (',' ID)* ':' type;

type: 'int'                                              # intType
    | 'subrange' '[' bound ',' bound ']' 'of' 'int'      # subrangeType
    | 'bool'                                             # boolType
    | 'real'                                             # realType
    | ID                                                 # userType
    ;

bound: '-'? INT;

property: '--%PROPERTY' ID ';';

assertion: 'assert' expr ';';

equation: (lhs | '(' lhs ')') '=' expr ';';

lhs: ID (',' ID)*;

expr: ID                                                       # idExpr
    | INT                                                      # intExpr
    | REAL                                                     # realExpr
    | BOOL                                                     # boolExpr
    | ID '(' (expr (',' expr)*)? ')'                           # nodeCallExpr
    | 'pre' expr                                               # preExpr
    | 'not' expr                                               # notExpr
    | '-' expr                                                 # negateExpr
    | expr op=('*' | '/' | 'div') expr                         # binaryExpr
    | expr op=('+' | '-') expr                                 # binaryExpr
    | expr op=('<' | '<=' | '>' | '>=' | '=' | '<>') expr      # binaryExpr
    | expr op='and' expr                                       # binaryExpr
    | expr op=('or' | 'xor') expr                              # binaryExpr
    | expr op='=>'<assoc=right> expr                           # binaryExpr
    | expr op='->'<assoc=right> expr                           # binaryExpr
    | 'if' expr 'then' expr 'else' expr                        # ifThenElseExpr
    | '(' expr ')'                                             # parenExpr
    ;

REAL: INT '.' INT;

BOOL: 'true' | 'false';
INT: [0-9]+;
ID: [a-zA-Z_][a-zA-Z_0-9]*;

WS: [ \t\n\r\f]+ -> skip;

SL_COMMENT: '--' (~[%\n\r] ~[\n\r]* | /* empty */) ('\r'? '\n')? -> skip;
ML_COMMENT: '/*' .*? '*/' -> skip;
MAIN: '--%MAIN' ';'? -> skip;

ERROR: .;
