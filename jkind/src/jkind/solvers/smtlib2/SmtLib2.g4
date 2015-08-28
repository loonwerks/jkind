grammar SmtLib2;

model: '(' 'model' define* ')' EOF;

define: 
  '(' 'define-fun' id '(' arg? ')' (type | id) body ')'                       #definefun                   
| '(' 'declare-datatypes' '(' arg? ')' '(' '('id typeConstructor+ ')' ')' ')' #declareDataTypes
;   
  
typeConstructor:
  '(' ID typeMember* ')';
  
typeMember:
  '(' ID (type | ID) ')';

arg: '(' id type ')';

type: 'Bool' | 'Int' | 'Real';

body: symbol                               # symbolBody
    | '(' fn body* ')'                     # consBody
    ;

fn: '=' | '-' | '/' | 'and' | 'ite' | 'not' | '>=' | '<=' | '<' | '>' | id;

symbol: id | BOOL | INT | REAL;

id: qid | ID;

qid: '|' ID '|';

BOOL: 'true' | 'false';

fragment DIGIT: [0-9];
fragment SYMBOL: [a-zA-Z_@$#%!.^~\[\]];

INT: DIGIT+;
REAL: DIGIT+ '.' DIGIT+;
ID: SYMBOL (SYMBOL | DIGIT)*;

WS: [ \t\n\r\f]+ -> skip;

ERROR: .;
