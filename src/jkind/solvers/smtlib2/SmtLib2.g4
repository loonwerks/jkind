grammar SmtLib2;

model: '(' 'model' define* ')' EOF;

define: '(' 'define-fun' ID '(' arg? ')' type body ')';

arg: '(' ID type ')';

type: 'Bool' | 'Int' | 'Real';

body: symbol                               # symbolBody
    | '(' fn body* ')'                     # consBody
    ;

fn: '=' | '-' | '/' | 'and' | 'ite' | 'not';

symbol: ID | BOOL | INT | REAL;

BOOL: 'true' | 'false';

fragment DIGIT: [0-9];
fragment SYMBOL: [a-zA-Z_@$#%!];

INT: DIGIT+;
REAL: DIGIT+ '.' DIGIT+;
ID: SYMBOL (SYMBOL | DIGIT)*;

WS: [ \t\n\r\f]+ -> skip;

ERROR: .;
