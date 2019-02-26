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
        ParseTree tree = parser.stat(); // begin parsing at prog rule

        CalculatorEvalVisitor eval = new CalculatorEvalVisitor();
        Node node = eval.visit(tree);
        if (node == null) System.out.println("nully");
        CalculatorEvalAST ast = new CalculatorEvalAST();
        ast.Visit(node);

        // System.out.println("Parse Tree:\n" + tree.toStringTree(parser));
        // System.out.println("File Text:\n" + tree.getText());
    }
}