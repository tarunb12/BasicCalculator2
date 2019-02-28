grammar Calculator;

@header { package calculator.antlr; }

prog: start* EOF ;

start
    : expr                              # PrintExpr
    | varDef                            # Assign
    | printStatement                    # PrintExprText
    | COMMENT                           # Comm
    | NL                                # NewLine
    ;

varDef  : VAR EQ expr                   # VariableDefinition ;

COMMENT : LCOM (.)*? RCOM -> channel(HIDDEN) ;

printStatement
    : print statement (',' statement)*  # PrintExpressionText
    ;

statement
    : QUOTE txt QUOTE                   # TextStatement
    | expr                              # ExprStatement
    ;

function
    : exp LPAR expr RPAR                # Exponential
    | log LPAR expr RPAR                # Logarithm
    | sqrt LPAR expr RPAR               # SquareRoot
    | sin LPAR expr RPAR                # Sine
    | cos LPAR expr RPAR                # Cosine
    ;

expr
    : SUBT expr                         # UnaryMinus
    | function                          # Func
    | expr POW expr                     # Power
    | NOT expr                          # Not
    | expr AND expr                     # And
    | expr OR expr                      # Or
    | expr MULT expr                    # Multiply
    | expr DIV expr                     # Divide
    | expr SUBT expr                    # Subtract
    | expr ADD expr                     # Add
    | NUM                               # Number
    | VAR                               # Variable
    | rd LPAR RPAR                      # Read
    | LPAR expr RPAR                    # Parenthesis
    | expr GT expr                      # GreaterThan
    | expr GTE expr                     # GreaterThanOrEqualTo
    | expr LT expr                      # LessThan
    | expr LTE expr                     # LessThanOrEqualTo
    | expr LEQ expr                     # LogicalEqual
    | expr LNEQ expr                    # LogicalNotEqual
    ;

NUM
    : INT
    | DOUBLE
    ;

INT     : [0-9]+ ;
DOUBLE  : [0-9]*'.'[0-9]+;

VAR     : [_a-zA-Z]+[_a-zA-Z0-9]* ;

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
LT  : '<' ;
LTE : '<=' ;
GT  : '>' ;
GTE : '>=' ;
LEQ : '==' ;
LNEQ: '!=' ;

exp : 'e' ;
log : 'l' ;
sqrt: 'sqrt' ;
sin : 's' ;
cos : 'c' ;
rd  : 'read' ;

print: 'print' ;
QUOTE: '"' ;
txt  : ~(NL)*? ;

LCOM: '/*' ;
RCOM: '*/' ;
BS  : [\\]      { setText("\\"); } ;
BSNL: [\\]'n'   { setText("\n"); } ;
BSTB: [\\]'t'   { setText("\t"); } ;
BSQT: [\\]'"'   { setText("\""); } ;
NL  : '\r'? '\n';
WS  : [ \t]+ -> skip;