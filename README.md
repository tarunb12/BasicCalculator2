# bc Calculator, pt.2 #

```console
$ cd src
$ antlr4 -visitor -no-listener calculator/antlr4/Calculator.g4
$ javac Calculator.java calculator/Calculator*.java calculator/antlr4/Calculator*.java
$ java Calculator <filename>.bc
```