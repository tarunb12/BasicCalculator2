grammar Calculator;

@header { package calculator.antlr; }

prog: start* EOF ;

start
    : function                          # StartFunc
    | functionCall                      # StartFuncCall
    | expr                              # PrintExpr
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

functionExpr
    : exp LPAR expr RPAR                # Exponential
    | log LPAR expr RPAR                # Logarithm
    | sqrt LPAR expr RPAR               # SquareRoot
    | sin LPAR expr RPAR                # Sine
    | cos LPAR expr RPAR                # Cosine
    ;

function:
    functionDef functionName LPAR (parameter (',' parameter)*)* RPAR NL* LBRAC
        start*
        (functionRet expr)? NL*
    RBRAC                               # Func
    ;

functionRetExpr     : expr ;
functionName        : VAR ;
parameter           : VAR ;

functionCall 
    : VAR LPAR (expr (',' expr)*)* RPAR # FuncCall
    ;

expr
    : SUBT expr                         # UnaryMinus
    | functionExpr                      # FuncExpr
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

functionDef : 'define' ;
functionRet : 'return' ;

LCOM: '/*' ;
RCOM: '*/' ;
LBRAC: '{' ;
RBRAC: '}' ;
BS  : [\\]      { setText("\\"); } ;
BSB : [\\]'b'   { setText("\b"); } ;
BSF : [\\]'f'   { setText("\f"); } ;
BSNL: [\\]'n'   { setText("\n"); } ;
BSCR: [\\]'r'   { setText("\r"); } ;
BSTB: [\\]'t'   { setText("\t"); } ;
BSSQ: [\\]'\''  { setText("\'"); } ;
BSDQ: [\\]'"'   { setText("\""); } ;
NL  : '\r'? '\n' -> channel(HIDDEN);
WS  : [ \t]+ -> skip ;