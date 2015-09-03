grammar SmtLib2;

model: '(' 'model' (define | comment)* ')' EOF;

comment:
  ';' .*?;

define: 
  '(' 'define-fun' id '(' arg* ')' (type) body ')'                       #definefun                   
| '(' 'declare-datatypes' '(' arg? ')' '(' '('id typeConstructor+ ')' ')' ')' #declareDataTypes
| '(' 'declare-sort' id INT ')' #declareSort
;   
  
typeConstructor:
  '(' ID typeMember* ')';
  
typeMember:
  '(' ID (type | ID) ')';

arg: '(' id type ')';

type: 'Bool' | 'Int' | 'Real' | id;

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
