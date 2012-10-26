grammar Yices;

options {
  language = Java;
}

@header {
  package jkind.solvers;

  import jkind.solvers.SolverResult.Result;
}

@lexer::header {
  package jkind.solvers;
}

@members {
  protected void ignore(Stack<Void> stack, List<Void> list, ArrayList<Void> arraylist) {}
}

@lexer::members {
  protected void ignore(Stack<Void> stack, List<Void> list, ArrayList<Void> arraylist) {}
}

solverResult returns [SolverResult sr]:
    result model? EOF
    { $sr = new SolverResult($result.r, $model.m); }
  ;

result returns [Result r]:
    'sat'     { $r = Result.SAT; }
  | 'unsat'   { $r = Result.UNSAT; }
  ;

model returns [Model m]
@init{
  $m = new Model();
}
  : (valueAssignment[m] | functionAssignment[m])+ ;

valueAssignment[Model m]:
    '(' '=' ID value ')'     { $m.addValue($ID.text, $value.v); }
  ;

functionAssignment[Model m]:
    '(' '=' '(' ID integer ')' value ')'
    { $m.addFunctionValue($ID.text, Integer.parseInt($integer.text), $value.v); }
  ;

value returns [Value v]:
    'true'      { $v = BoolValue.TRUE; }
  | 'false'     { $v = BoolValue.FALSE; }
  | numeric     { $v = new NumericValue($numeric.text); }
  ;

numeric: '-'? INT ('/' INT)?;
integer: '-'? INT;

fragment DIGIT: '0'..'9';
fragment SYMBOL: 'a'..'z' | 'A'..'Z' | '_' | '@' | '$';

ID: SYMBOL (SYMBOL | DIGIT)*;
INT: DIGIT+;

WS: (' ' | '\t' | '\n' | '\r' | '\f')+ {$channel = HIDDEN;};

ERROR: '.';
