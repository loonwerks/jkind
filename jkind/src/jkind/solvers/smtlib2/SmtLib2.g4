grammar SmtLib2;

model: '(' 'model' define* ')' EOF;

define: '(' 'define-fun' id '(' arg* ')' type body ')';

arg: '(' id type ')';

type: 'Bool' | 'Int' | 'Real';

body: symbol                               # symbolBody
    | '(' fn body* ')'                     # consBody
    ;

fn: '=' | '-' | '/' | 'and' | 'ite' | 'not' | '>=' | '<=' | '<' | '>';

symbol: id | BOOL | INT | REAL;

id: qid | ID;

qid: '|' ID '|';

BOOL: 'true' | 'false';

fragment DIGIT: [0-9];
fragment SYMBOL: [a-zA-Z_@$#%!.^~\[\]'-];

INT: DIGIT+;
REAL: DIGIT+ '.' DIGIT+;
ID: SYMBOL (SYMBOL | DIGIT)*;

WS: [ \t\n\r\f]+ -> skip;

ERROR: .;
