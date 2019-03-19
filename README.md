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

Please ensure you are in the `./src` directory when running the program and testing.

Below are sample inputs for the syntax of the language:

```
/* Function */
define function(x) {
    ...
}


# If/Else */
if (condition) {
    ...
}
else if (condition) {
    ...
}
...
else {
    ...
}

/* For Loop */
for (variableDefinition; condition; variableRedefinition) {
    ...
}

/* While Loop */
while (condition) {
    ...
}
```

Any input files are relative to the `./src` directory.