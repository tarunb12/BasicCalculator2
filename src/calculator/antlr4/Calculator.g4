grammar Calculator;

@header { package calculator.antlr4; } 

prog: NL*? stat ( NL*? stat )* NL*? ;

stat
    : printStatement
    | expression
    | varDef
    | COMMENT
    ;

expression
    : expr
    ;

varDef
    : VAR EQ expr
	;

COMMENT
    : LCOM (.)*? RCOM -> channel(HIDDEN)
    ;

printStatement
    : print statement[true]
    ;

statement[boolean first] returns [String output]
    : QUOTE txt QUOTE (',' stm=statement[false])* 
    | expr (',' stm=statement[false])*
    ;

num 
    : INT
    | DOUBLE
    ;

expr returns [double result]
    : SUBT expr                 # UnaryMinus
    | exp LPAR expr RPAR        # Exponential
    | log LPAR expr RPAR        # Logarithm
    | sin LPAR expr RPAR        # Sine
    | cos LPAR expr RPAR        # Cosine
    | left=expr POW right=expr  # Power
    | sqrt LPAR expr RPAR       # SquareRoot
    | NOT expr                  # Not
    | left=expr AND right=expr  # And
    | left=expr OR right=expr   # Or
    | left=expr MULT right=expr # Multiply
    | left=expr DIV right=expr  # Divide
    | left=expr SUBT right=expr # Subtract
    | left=expr ADD right=expr  # Add
	| num                       # Number
    | VAR                       # Variable
    | LPAR expr RPAR            # Parenthesis
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
read: 'read' ;
exp : 'e' ;
log : 'l' ;
sqrt: 'sqrt' ;
sin : 's' ;
cos : 'c' ;

print: 'print' ;
QUOTE: '"' ;
txt  : (.)*? ;

NOT : '!' ;
AND : '&&' ;
OR  : '||' ;

LCOM: '/*' ;
RCOM: '*/' ;
NL  : '\r'? '\n' ;
BS  : [\\] ;
BSNL: [\\]'n' ;
BSTB: [\\]'t' ;
WS  : [ \t]+ -> skip ;