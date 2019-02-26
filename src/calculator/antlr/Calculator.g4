grammar Calculator;

@header { package calculator.antlr; }

prog: start* EOF ;

start
    : expr      # PrintExpr
    | varDef    # Assign
    | comment   # Comm
    | NL        # NewLine
    ;

varDef  : VAR EQ expr ;

comment : '/*' (.)*? '*/' ;

sinFunc : 's' LPAR expr RPAR ;
cosFunc : 'c' LPAR expr RPAR ;
logFunc : 'l' LPAR expr RPAR ;
expFunc : 'e' LPAR expr RPAR ;
sqrtFunc: 'sqrt' LPAR expr RPAR ;

function: expFunc
        | logFunc
        | sqrtFunc
        | sinFunc
        | cosFunc
        ;

expr
    : SUBT expr                 # UnaryMinus
    | expr POW expr             # Power
    | function                  # Func
    | expr AND expr             # And
    | expr OR expr              # Or
    | NOT expr                  # Not
    | expr MULT expr            # Multiply
    | expr DIV expr             # Divide
    | expr ADD expr             # Add
    | expr SUBT expr            # Subtract
    | NUM                       # Number
    | VAR                       # Variable
    | LPAR expr RPAR            # Parenthesis
    ;

NUM : '-'?([0-9]+)|([0-9]*'.'[0-9]+) ;  // -?((INT) | (FLOAT))
VAR : [_a-zA-Z]+[_a-zA-Z0-9]* ;     // (_alphabet)+(_alphanumeric)*

POW : '^' ;
MULT: '*' ;
DIV : '/' ;
ADD : '+' ;
SUBT: '-' ;
LPAR: '(' ;
RPAR: ')' ;
EQ  : '=' ;

NOT : '!' ;
AND : '&&' ;
OR  : '||' ;

LCOM: '/*' ;
RCOM: '*/' ;
NL  : '\r'? '\n';
WS  : [ \t]+ -> skip;
