grammar Lustre;

program: (typedef | constant | node)* EOF;

typedef: 'type' ID '=' topLevelType ';';

constant: 'const' ID (':' type)? '=' expr ';';

node:
  'node' ID '(' input=varDeclList? ')'
  'returns' '(' output=varDeclList? ')' ';'
  ('var' local=varDeclList ';')?
  'let'
    (equation | property | assertion | main)*
  'tel' ';'?
;

varDeclList: varDeclGroup (';' varDeclGroup)*;

varDeclGroup: ID (',' ID)* ':' type;

topLevelType: type                                       # plainType
    | 'struct' '{' (ID ':' type) (';' ID ':' type)* '}'  # recordType
    | 'enum' '{' ID (',' ID)* '}'                        # enumType
    ;

type: 'int'                                              # intType
    | 'subrange' '[' bound ',' bound ']' 'of' 'int'      # subrangeType
    | 'bool'                                             # boolType
    | 'real'                                             # realType
    | type '[' INT ']'                                   # arrayType
    | ID                                                 # userType
    ;

bound: '-'? INT;

property: '--%PROPERTY' ID ';';

main: '--%MAIN' ';'?;

assertion: 'assert' expr ';';

equation: (lhs | '(' lhs? ')') '=' expr ';';

lhs: ID (',' ID)*;

expr: ID                                                       # idExpr
    | INT                                                      # intExpr
    | REAL                                                     # realExpr
    | BOOL                                                     # boolExpr
    | op=('real' | 'floor') '(' expr ')'                       # castExpr
    | ID '(' (expr (',' expr)*)? ')'                           # nodeCallExpr
    | 'condact' '(' expr (',' expr)+ ')'                       # condactExpr
    | expr '.' ID                                              # recordAccessExpr
    | expr '{' ID ':=' expr '}'                                # recordUpdateExpr
    | expr '[' expr ']'                                        # arrayAccessExpr
    | expr '[' expr ':=' expr ']'                              # arrayUpdateExpr
    | 'pre' expr                                               # preExpr
    | 'not' expr                                               # notExpr
    | '-' expr                                                 # negateExpr
    | expr op=('*' | '/' | 'div' | 'mod') expr                 # binaryExpr
    | expr op=('+' | '-') expr                                 # binaryExpr
    | expr op=('<' | '<=' | '>' | '>=' | '=' | '<>') expr      # binaryExpr
    | expr op='and' expr                                       # binaryExpr
    | expr op=('or' | 'xor') expr                              # binaryExpr
    | expr op='=>'<assoc=right> expr                           # binaryExpr
    | expr op='->'<assoc=right> expr                           # binaryExpr
    | 'if' expr 'then' expr 'else' expr                        # ifThenElseExpr
    | ID '{' ID '=' expr (';' ID '=' expr)* '}'                # recordExpr
    | '[' expr (',' expr)* ']'                                 # arrayExpr
    | '(' expr (',' expr)* ')'                                 # tupleExpr
    ;

REAL: INT '.' INT;

BOOL: 'true' | 'false';
INT: [0-9]+;
ID: [a-zA-Z_][a-zA-Z_0-9]*;

WS: [ \t\n\r\f]+ -> skip;

SL_COMMENT: '--' (~[%\n\r] ~[\n\r]* | /* empty */) ('\r'? '\n')? -> skip;
ML_COMMENT: '/*' .*? '*/' -> skip;

ERROR: .;
