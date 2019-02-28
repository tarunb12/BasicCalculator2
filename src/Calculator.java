import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import org.antlr.v4.runtime.CharStreams.*;

import calculator.antlr.CalculatorLexer;
import calculator.antlr.CalculatorParser;
import calculator.ast.CalculatorEvalVisitor;
import calculator.ast.CalculatorEvalAST;
import calculator.ast.Node;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class Calculator {
    public static void main(String[] args) throws Exception {
        try {
            String inputFile = null; // file name to read from
            if ( args.length > 0 ) inputFile = args[0]; // reads filename arg from command line
            InputStream is = System.in;
            if ( inputFile != null ) is = new FileInputStream(inputFile); // new input stream if valid filename
            
            // create a CharStream that reads from standard input
            CharStream input = CharStreams.fromStream(is);
            // create a lexer that feeds off of input CharStream
            CalculatorLexer lexer = new CalculatorLexer(input);
            // create a buffer of tokens pulled from the lexer
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            // create a parser that feeds off the tokens buffer
            CalculatorParser parser = new CalculatorParser(tokens);
            // Create native parse tree
            ParseTree tree = parser.prog();

            // create visitor class to transform parse tree to AST
            CalculatorEvalVisitor toAST = new CalculatorEvalVisitor();
            // transform parse tree into custom AST
            Node ast = toAST.visit(tree);
            // create AST evaluator class
            CalculatorEvalAST eval = new CalculatorEvalAST();
            // visit and evaluate AST
            eval.visit(ast);
        } catch (FileNotFoundException e) {
            // Interactive Mode ?
        }
    }
}