grammar Yices2;

model: (alias | variable | function)+ EOF;

alias: '(' '=' ID ID ')';

variable: '(' '=' ID value ')';

function: '(' 'function' ID functionType functionValue* defaultValue? ')';

functionType: '(' 'type' '(' '->' type* ')' ')';

type: 'int' | 'real' | 'bool';

functionValue: '(' '=' '(' ID value+ ')' value ')';

defaultValue: '(' 'default' value ')';

value: BOOL | numeric;

BOOL: 'true' | 'false';

integer: INT                 # positiveInteger
       | '(' '-' INT ')'     # negativeInteger
       ;
       
quotient: '(' '/' numeric numeric ')';
         
numeric: integer             # integerNumeric
       | quotient            # quotientNumeric
       ;

fragment DIGIT: [0-9];
fragment SYMBOL: [a-zA-Z_@$#%!.^~\[\]];

INT: DIGIT+;
ID: SYMBOL (SYMBOL | DIGIT)*;

WS: [ \t\n\r\f]+ -> skip;

ERROR: .;
