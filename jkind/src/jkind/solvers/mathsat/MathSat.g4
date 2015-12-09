grammar MathSat;

model: '(' assignment* ')' EOF;

assignment: '(' id body ')';

unsatAssumptions: '(' symbol* ')';

body: symbol                               # symbolBody
    | '(' fn body* ')'                     # consBody
    ;

fn: '-' | '/';

symbol: id | BOOL | INT;

id: qid | ID;

qid: '|' ID '|';

BOOL: 'true' | 'false';

fragment DIGIT: [0-9];
fragment SYMBOL: [a-zA-Z_@$#%!.^~\[\]];

INT: DIGIT+;
ID: SYMBOL (SYMBOL | DIGIT)*;

WS: [ \t\n\r\f]+ -> skip;

ERROR: .;
