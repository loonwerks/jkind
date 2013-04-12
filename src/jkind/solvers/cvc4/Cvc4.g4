grammar Cvc4;

model: '(' 'model' (defval | defun)* ')' EOF;

defval: '(' 'define-fun' ID '(' ')' type body ')';

defun: '(' 'define-fun' ID '(' arg ')' type body ')';

arg: '(' ID type ')';

type: 'Bool' | 'Int' | 'Real';

body: symbol                               # symbolBody
    | '(' fn body* ')'                     # consBody
    ;

fn: '=' | '-' | '/' | 'and' | 'ite' | 'not';

symbol: ID | BOOL | INT;

BOOL: 'true' | 'false';

fragment DIGIT: [0-9];
fragment SYMBOL: [a-zA-Z_@$#%!];

INT: DIGIT+;
ID: SYMBOL (SYMBOL | DIGIT)*;

WS: [ \t\n\r\f]+ -> skip;

ERROR: .;
