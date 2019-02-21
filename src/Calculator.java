
import calculator.antlr4.CalculatorLexer;
import calculator.antlr4.CalculatorParser;
import calculator.CalculatorEvalVisitor;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import org.antlr.v4.runtime.CharStreams.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.Exception;

public class Calculator {
    public static void main(String[] args) throws Exception {
        String inputFile = null;
        if (args.length > 0) inputFile = args[0];
        InputStream is = System.in;
        if (inputFile != null) is = new FileInputStream(inputFile);

        // create a CharStream that reads from file
        CharStream input = CharStreams.fromStream(is);
        // create a lexer that feeds off of input CharStream
        CalculatorLexer lexer = new CalculatorLexer(input);
        // create a buffer of tokens pulled from the lexer
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        // create a parser that feeds off the tokens buffer
        CalculatorParser parser = new CalculatorParser(tokens);
        ParseTree tree = parser.prog();
        // parse tree visitor
        CalculatorEvalVisitor eval = new CalculatorEvalVisitor();
        eval.visit(tree);
    }
}