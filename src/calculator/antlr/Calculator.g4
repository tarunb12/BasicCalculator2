grammar Calculator;

@header { package calculator.antlr; }

prog: start* EOF ;

start
    : function                          # StartFunc
    | statement                         # StartStatement
    | varDef                            # Assign
    | varDefExpr                        # AssignExpr
    | expr                              # PrintExpr
    | printText                         # PrintExpressionText
    | COMMENT                           # Comm
    | NL                                # NewLine
    ;

varDef  
    : VAR EQ expr                       # VariableDefinition
    ;

varDefExpr
    : VAR POW EQ expr                   # PowEqual
    | VAR MULT EQ expr                  # MultEqual
    | VAR DIV EQ expr                   # DivEqual
    | VAR SUBT EQ expr                  # SubtEqual
    | VAR ADD EQ expr                   # AddEqual
    | ADD ADD VAR                       # PreIncrement
    | SUBT SUBT VAR                     # PreDecrement
    | VAR ADD ADD                       # PostIncrement
    | VAR SUBT SUBT                     # PostDecrement
    ;

COMMENT : LCOM (.)*? RCOM -> channel(HIDDEN) ;

printText
    : print text (',' text)*            # PrintExprText
    ;

text
    : QUOTE txt QUOTE                   # TextStatement
    | expr                              # ExprStatement
    ;

function:
    functionDef functionName LPAR (parameter (',' parameter)*)* RPAR NL*
    ( LBRAC start* returnExpr? NL* RBRAC
    | start
    | returnExpr
    )                                   # Func
    ;

functionName        : VAR ;
parameter           : VAR ;

functionCall 
    : functionName LPAR (expr (',' expr)*)* RPAR # FuncCall
    ;
// return : check if scope length > 1
statement
    : ifStatement
    | forLoop
    | whileLoop
    ;

ifStatement
    : ifBranch elseBranch?              # IfDefStatement
    ;

ifBranch
    : ifDef LPAR expr RPAR NL* (
          LBRAC start* returnExpr? NL* RBRAC
        | start
        | returnExpr
    );

elseBranch
    : elseDef NL* (
          LBRAC start* returnExpr? NL* RBRAC
        | start
        | returnExpr
    );

whileLoop
    : whileDef LPAR expr RPAR
        ( LBRAC start* RBRAC
        | start
        )                               # WhileLoopStatement
    ;

forLoop
    : forDef LPAR varDef SEMI expr SEMI varDefExpr RPAR (
          LBRAC start* RBRAC
        | start
    )                                   # ForLoopStatement
    ;

returnExpr
    : functionRet expr
    ;

functionExpr
    : exp LPAR expr RPAR                # Exponential
    | log LPAR expr RPAR                # Logarithm
    | sqrt LPAR expr RPAR               # SquareRoot
    | sin LPAR expr RPAR                # Sine
    | cos LPAR expr RPAR                # Cosine
    ;

expr
    : SUBT expr                         # UnaryMinus
    | functionCall                      # ExprFuncCall
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
    | expr GT expr                      # GreaterThan
    | expr GTE expr                     # GreaterThanOrEqualTo
    | expr LT expr                      # LessThan
    | expr LTE expr                     # LessThanOrEqualTo
    | expr LEQ expr                     # LogicalEqual
    | expr LNEQ expr                    # LogicalNotEqual
    | LPAR expr RPAR                    # Parenthesis
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

ifDef       : 'if' ;
then        : 'then' ;
elseDef     : 'else';
whileDef    : 'while' ;
forDef      : 'for' ;
functionDef : 'define' ;
functionRet : 'return' ;

SEMI: ';' ;
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
NL  : '\r'? '\n' -> channel(HIDDEN) ;
WS  : [ \t]+ -> skip ;