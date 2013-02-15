grammar Yices;

solverResult: RESULT model? EOF;

RESULT: 'sat' | 'unsat';

model: (variable | function | predefined)+;

variable: '(' '=' ID value ')';

function: '(' '=' '(' ID integer ')' value ')';
  
predefined: '(' '=' '(' PREDEFINED_OP integer integer ')' integer ')';
  
PREDEFINED_OP: 'mod' | 'div';

value: BOOL | numeric;

BOOL: 'true' | 'false';
integer: '-'? INT;
numeric: '-'? INT ('/' INT)?;

fragment DIGIT: [0-9];
fragment SYMBOL: [a-zA-Z_@$#];

INT: DIGIT+;
ID: SYMBOL (SYMBOL | DIGIT)*;

WS: [ \t\n\r\f]+ -> skip;

ERROR: .;