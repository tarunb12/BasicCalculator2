grammar Calculator;

@header { package calculator.antlr; }

prog: start* EOF ;

start
    : expr                      # PrintExpr
    | varDef                    # Assign
    | COMMENT                   # Comm
    | NL                        # NewLine
    ;

varDef  : VAR EQ expr           # VarDeclaration ;

COMMENT : LCOM (.)*? RCOM -> channel(HIDDEN) ;

function: exp LPAR expr RPAR    # Exponential
        | log LPAR expr RPAR    # Logarithm
        | sqrt LPAR expr RPAR   # SquareRoot
        | sin LPAR expr RPAR    # Sine
        | cos LPAR expr RPAR    # Cosine
        ;

expr
    : SUBT expr                 # UnaryMinus
    | expr POW expr             # Power
    | function                  # Func
    | NOT expr                  # Not
    | expr AND expr             # And
    | expr OR expr              # Or
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

exp : 'e' ;
log : 'l' ;
sqrt: 'sqrt' ;
sin : 's' ;
cos : 'c' ;

LCOM: '/*' ;
RCOM: '*/' ;
NL  : '\r'? '\n';
WS  : [ \t]+ -> skip;
