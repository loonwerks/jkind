grammar DRealModel;

model: (var_assign | warning)* EOF;

var_assign : symbol ':' var_value ; 

warning: 'Warning:' TOEOL;

var_value: 
     '[ ENTIRE ]' '=' '[' number_value ',' number_value ']'   # numberRangeVal
   | 'Bool' '=' three_val_bool                                # boolVal
   ; 
   
number_value: 
   INFRULE                 # infinityVal 
  | REAL                   # realVal
  | INTEGER                # integerVal
  ;
  
three_val_bool: 
    'true'                 # trueVal 
  | 'false'                # falseVal
  | 'undef'                # undefVal
  ; 
  
symbol: SIMPLE_SYMBOL | QUOTED_SYMBOL ; 

INTEGER: ('+' | '-')? NUMERAL ; 
REAL: ('+' | '-')? NUMERAL '.' NUMERAL (EXPONENT)? ;
INFRULE: ('+' | '-')? 'INFTY' ;
EXPONENT: ('e' | 'E') ('+' | '-')? NUMERAL ; 


NUMERAL: [0-9]+;
// ID: [a-zA-Z_~][a-zA-Z_0-9~]*;
// ~ is used internally. Users should not use it.
// come back to this to add all allowed symbols...need to figure out how to get ANTLR 
// to recognize symbols like ! and *
SIMPLE_SYMBOL: SYMBOL_CHAR SYMBOL_OR_NUM_CHAR*;
SYMBOL_CHAR: ('a'..'z' | 'A'..'Z' | '_' | '~' | '-' | '\\' | '!' | '@' | '$' | '%' | '^' | '&' | '*' | '+' | '=' | '<' | '>' | '.' | '?' | '/' );
SYMBOL_OR_NUM_CHAR: (SYMBOL_CHAR | '0'..'9');

QUOTED_SYMBOL: '|' (~'|'.*?) '|';
STRING: '"' ('""' | .)*? '"';
WS: [ \t\n\r\f]+ -> skip;
COMMENT: ';' (~[%\n\r] ~[\n\r]* | /* empty */) ('\r'? '\n')? -> skip; 
TOEOL: (~[%\n\r] ~[\n\r]*) ('\r'? '\n')? ; 

ERROR: .;