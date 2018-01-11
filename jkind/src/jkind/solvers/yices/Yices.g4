grammar Yices;

result: (satResult | unsatResult) EOF;

// maxsat may return unknown when it gives up, but it gives a usable model still
satResult: ('sat' | 'unknown') unsatAssertions? model? cost?;

unsatResult: 'unsat' unsatCore?;

model: (alias | variable | function | predefined)+;

unsatAssertions: 'unsatisfied' 'assertion' 'ids:' INT+; 

cost: 'cost:' INT;

unsatCore: 'unsat' 'core' 'ids:' INT+;

alias: '(' '=' ID ID ')';

variable: '(' '=' ID value ')';

function: '(' '=' '(' ID value+ ')' value ')';

predefined: '(' '=' '(' PREDEFINED_OP value* ')' value ')';

PREDEFINED_OP: 'mod' | 'div' | 'to_int' | 'to_real';

value: BOOL | numeric;

BOOL: 'true' | 'false';
integer: '-'? INT;
numeric: '-'? INT ('/' INT)?;

fragment DIGIT: [0-9];
fragment SYMBOL: [a-zA-Z_@$#%!.^~\[\]:];

INT: DIGIT+;
ID: SYMBOL (SYMBOL | DIGIT)*;

WS: [ \t\n\r\f]+ -> skip;

ERROR: .;
