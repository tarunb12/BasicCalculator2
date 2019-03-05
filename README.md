# COP4020 Basic Calculator Antlr4 Implementaion, Part 2 #
## Compiling ##
~~~~
$ cd src
$ antlr4 -no-listener -visitor calculator/antlr/Calculator.g4
$ javac *.java calculator/**/*.java
~~~~

## Running ##
~~~~
$ java Calculator <filename>.bc
~~~~

## Testing (JUNIT) ##
~~~~
$ java TestRunner
~~~~

Please ensure you are in the src directory when running the program and testing.