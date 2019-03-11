import org.junit.Test;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import org.antlr.v4.runtime.CharStreams.*;

import calculator.antlr.CalculatorLexer;
import calculator.antlr.CalculatorParser;
import calculator.ast.CalculatorEvalVisitor;
import calculator.ast.CalculatorEvalAST;
import calculator.ast.Node;

import java.io.PrintStream;
import java.io.ByteArrayOutputStream;

import java.lang.Exception;
import java.lang.AssertionError;
import java.lang.FunctionalInterface;

import java.util.Map;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class TestJunit {

   private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
   private final PrintStream originalOut = System.out;

   public static final String LTAB = "      ";
   public static final String STAB = "   ";
   public static final String ANSI_GREEN =  "\u001B[32m";
   public static final String ANSI_RED = "\u001B[31m";
   public static final String ANSI_LOW = "\u001B[2m";
   public static final String ANSI_RESET = "\u001B[0m";
   public static final Tests tests = new Tests();

   public void antlr(String exp) throws Exception {
        // create a CharStream that reads from input string
        CharStream input = CharStreams.fromString(exp);
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
   }

   public void setOutStream() {
      System.setOut(new PrintStream(outContent));
   }

   public void resetOutStream() {
      outContent.reset();
      System.setOut(originalOut);
   }

   public void antlrTest(String testExp, String testRes) {
      String testExpRes = "";
      try {
         setOutStream();
         antlr(testExp);
         testExpRes = outContent.toString().replace("\r", "").replace("\n", "");
         resetOutStream();
         Assert.assertEquals(testRes, testExpRes);
         System.out.println(LTAB + ANSI_GREEN + "\u2713 " + ANSI_RESET + ANSI_LOW + testExp.trim().replace("\n", "  ") + " = " + testRes + ANSI_RESET);
      }
      catch (AssertionError e) {
         System.out.println(LTAB + ANSI_RED + "\u2717 " + ANSI_RESET + ANSI_LOW + testExp.trim().replace("\n", "  ") + " = " + testRes + "  (Got \"" + testExpRes + "\")" + ANSI_RESET);
      }
      catch (Exception e) {
         System.out.println(LTAB + ANSI_RED + "\u2717 " + ANSI_RESET + ANSI_LOW + "Parser failed on the input " + testExp);
      }
   }

   public void testExpressions(Map<String, String> testResults) {
      for (Map.Entry<String, String> test : testResults.entrySet()) {
         antlrTest(test.getKey(), test.getValue());
      }
      System.out.println("");
   }

   public void testCommands(Map<ArrayList<String>, String> testResults) {
      for (Map.Entry<ArrayList<String>, String> test : testResults.entrySet()) {
         String command = "";
         for (String cmd : test.getKey()) {
            command += cmd + "\n";
         }
         antlrTest(command, test.getValue());
      }
      System.out.println("");
   }

   @Test
   public void testMathOps() {
      System.out.println(STAB + "Mathematical Tests:");

      tests.setAddSubMultDivPowTests();
      Map<String, String> testResults = tests.mathTests;
      testExpressions(testResults);
   }

   @Test
   public void testBoolOps() {
      System.out.println(STAB + "Boolean Tests:");

      tests.setBooleanTests();
      Map<String, String> testResults = tests.booleanTests;
      testExpressions(testResults);
   }

   @Test
   public void testFunctionExpressions() {
      System.out.println(STAB + "Function Expression Tests:");

      tests.setFunctionExpressionTests();
      Map<String, String> testResults = tests.functionExpressionTests;
      testExpressions(testResults);
   }

   @Test
   public void testVarDefs() {
      System.out.println(STAB + "Variable Tests:");

      tests.setVarTests();
      Map<ArrayList<String>, String> testResults = tests.varTests;
      testCommands(testResults);
   }

   @Test
   public void testComments() {
      System.out.println(STAB + "Comment Tests:");

      tests.setCommentTests();
      Map<ArrayList<String>, String> testResults = tests.commentTests;
      testCommands(testResults);
   }

   @Test
   public void testPrint() {
      System.out.println(STAB + "Print Tests:");

      tests.setPrintTests();
      Map<String, String> testResults = tests.printTests;
      testExpressions(testResults);
   }

   @Test
   public void testIfElse() {
      System.out.println(STAB + "If/Else Tests:");

      tests.setIfElseTests();
      Map<ArrayList<String>, String> testResults = tests.ifElseTests;
      testCommands(testResults);
   }

   @Test
   public void testFunctions() {
      System.out.println(STAB + "Function Tests:");

      tests.setFunctionTests();
      Map<ArrayList<String>, String> testResults = tests.functionTests;
      testCommands(testResults);
   }

   public static class Tests {
      public Map<String, String> mathTests;
      public Map<String, String> booleanTests;
      public Map<String, String> functionExpressionTests;
      public Map<ArrayList<String>, String> varTests;
      public Map<ArrayList<String>, String> commentTests;
      public Map<String, String> printTests;
      public Map<ArrayList<String>, String> ifElseTests;
      public Map<ArrayList<String>, String> functionTests;

      public void setAddSubMultDivPowTests() {
         mathTests = new LinkedHashMap<String, String>();
         mathTests.put("2+3*8-7/7", Double.toString(2+3*8-7/7));
         mathTests.put("-2-(-(-4+(-14/7)*(-6/-1.5))-4*(-2)-5)+2", Double.toString(-2-(12-4*(-2)-5)+2));
         mathTests.put("4*(-1.0/3+1.0/5-1.0/7+1.0/9-1.0/11+1.0/13)", Double.toString(4*(-1.0/3+1.0/5-1.0/7+1.0/9-1.0/11+1.0/13)));
         mathTests.put("1+1+1.0/2+1.0/(1*2*3)+1.0/(1*2*3*4)+1.0/(1*2*3*4*5)", Double.toString(1+1+1.0/2+1.0/(1*2*3)+1.0/(1*2*3*4)+1.0/(1*2*3*4*5)));
         mathTests.put("-5.32-472+8.3*10*(12/7.2)^4", Double.toString(-5.32-472+8.3*10*Math.pow(12/7.2, 4)));
         mathTests.put("1.0/1^2+1.0/2^2+1.0/3^2+1.0/4^2+1.0/5^2", Double.toString(1.0/Math.pow(1,2)+1.0/Math.pow(2,2)+1.0/Math.pow(3,2)+1.0/Math.pow(4,2)+1.0/Math.pow(5,2)));
         mathTests.put("(1+(1.0/(2^52)))^(2^52)", Double.toString(Math.pow(1+1.0/Math.pow(2,52),Math.pow(2,52))));
      }

      public void setBooleanTests() {
         BinaryOperation and = (a, b) -> a != 0.0 && b != 0.0 ? 1.0 : 0.0;
         BinaryOperation or = (a, b) -> a != 0.0 || b != 0.0 ? 1.0 : 0.0;
         UnaryOperation not = a -> a == 0 ? 1.0 : 0.0;
         BinaryOperation gt = (a, b) -> a > b ? 1.0 : 0.0;
         BinaryOperation gte = (a, b) -> a >= b ? 1.0 : 0.0;
         BinaryOperation lt = (a, b) -> a < b ? 1.0 : 0.0;
         BinaryOperation lte = (a, b) -> a <= b ? 1.0 : 0.0;
         BinaryOperation eq = (a, b) -> a == b ? 1.0 : 0.0;
         BinaryOperation neq = (a, b) -> a != b ? 1.0 : 0.0;

         booleanTests = new LinkedHashMap<String, String>();
         booleanTests.put("2&&4", Double.toString(and.compute(2,4)));
         booleanTests.put("2||4", Double.toString(or.compute(2,4)));
         booleanTests.put("!2||!4", Double.toString(or.compute(not.compute(2),not.compute(4))));
         booleanTests.put("!2==!4", Double.toString(eq.compute(not.compute(2), not.compute(4))));
         booleanTests.put("!8.3<4.5", Double.toString(lt.compute(not.compute(8.3), 4.5)));
         booleanTests.put("6>5", Double.toString(gt.compute(6, 5)));
         booleanTests.put("3>=3", Double.toString(gte.compute(3, 3)));
         booleanTests.put("5<=6", Double.toString(lte.compute(5, 6)));
         booleanTests.put("2!=!2", Double.toString(neq.compute(2, not.compute(2))));
         booleanTests.put("!3&&4.24||2&&!0", Double.toString(or.compute(and.compute(not.compute(3),4.24),and.compute(2,not.compute(0)))));
         booleanTests.put("!!2&&(4-2^2+2^0)", Double.toString(and.compute(not.compute(not.compute(2)),4-Math.pow(2,2)+Math.pow(2,0))));
         booleanTests.put("e(3.14159&&2.71828)", Double.toString(Math.exp(and.compute(3.14159, 2.71828))));
      }

      public void setFunctionExpressionTests() {
         functionExpressionTests = new LinkedHashMap<String, String>();
         functionExpressionTests.put("(s(3.141592653589))^2+(c(3.141592653589))^2", Double.toString(Math.pow(Math.sin(3.141592653589),2)+Math.pow(Math.cos(3.141592653589),2)));
         functionExpressionTests.put("s(3.141592653589/2)/c(3.141592653589/2)", Double.toString(Math.sin(3.141592653589/2)/Math.cos(3.141592653589/2)));
         functionExpressionTests.put("l(e(read()+1))/l(e(read()))\n1", Double.toString(Math.log10(Math.exp(1+1))/Math.log10(Math.exp(1))));
         functionExpressionTests.put("sqrt(2)^(sqrt(2)^(sqrt(2)))", Double.toString(Math.pow(Math.sqrt(2),Math.pow(Math.sqrt(2),Math.sqrt(2)))));
         functionExpressionTests.put("read()*8+7^read()\n2", Double.toString(2*8+Math.pow(7, 2)));
         functionExpressionTests.put("l(e(e(1)))/l(e(1))", Double.toString(Math.log10(Math.exp(Math.exp(1)))/Math.log10(Math.exp(1))));
      }

      public void setVarTests() {
         varTests = new LinkedHashMap<ArrayList<String>, String>();
         final String[][] commands = {
            {"num=5+11*2", "num"},
            {"v=4*l(2)", "v=10^v", "v"},
            {"a=5*e(-2)", "b=s(a)+c(a)+sqrt(a)", "a+2*b/7"},
            {"i=l(1)", "j=i/l(e(1))", "k=e(1)^j", "m=e(1)^k", "n=m+i", "n"},
         };
         final String[] results = {
            Double.toString(5+11*2),
            Double.toString(Math.pow(10,4*Math.log10(2))),
            Double.toString(5*Math.exp(-2)+2*(Math.sin(5*Math.exp(-2))+Math.cos(5*Math.exp(-2))+Math.sqrt(5*Math.exp(-2)))/7),
            Double.toString(Math.pow(Math.exp(1),Math.pow(Math.exp(1),Math.log10(1)/Math.log(Math.exp(1))))+Math.log10(1)),
         };
         assert commands.length == results.length : "Invalid correspondence of commands to results (Variable Tests)";
         for (int i = 0; i < commands.length; i++) {
            ArrayList<String> cmds = new ArrayList<String>();
            for (int j = 0; j < commands[i].length; j++) {
               cmds.add(commands[i][j]);
            }
            varTests.put(cmds, results[i]);
         }
      }

      public void setCommentTests() {
         commentTests = new LinkedHashMap<ArrayList<String>, String>();
         final String[][] commands = {
            {"/*", "testing", "1+2+3+4", "*/", "5*6"},
            {"v=2/s(2)", "/*", "v=v*2", "v+2", "*/", "v"},
            {"/*", "test", "o=2.0", "*/", "o"},
            {"num=e(1)^8-1", "/*", "num=2", "hi", "num=((e(1))^8-1)/(e(1)^2+1)", "*/", "num=num/(e(1)^4+1)", "sqrt(sqrt(num+1))"},
         };
         final String[] results = {
            Double.toString(5*6),
            Double.toString(2/Math.sin(2)),
            Double.toString(0.0),
            Double.toString(Math.sqrt(Math.sqrt((Math.exp(8)-1)/(Math.exp(4)+1)+1))),
         };
         assert commands.length == results.length : "Invalid correspondence of commands to results (Comment Tests)";
         for (int i = 0; i < commands.length; i++) {
            ArrayList<String> cmds = new ArrayList<String>();
            for (int j = 0; j < commands[i].length; j++) {
               cmds.add(commands[i][j]);
            }
            commentTests.put(cmds, results[i]);
         }
      }

      public void setPrintTests() {
         printTests = new LinkedHashMap<String, String>();
         printTests.put("print 2*8, 4+7^2, e(1)+1", Double.toString(2.0*8)+", "+Double.toString(4.0+Math.pow(7, 2))+", "+Double.toString(Math.exp(1)+1));
         printTests.put("print \"hello\", 4+l(100), e(e(2))", "hello, "+Double.toString(4+Math.log10(100))+", "+Double.toString(Math.exp(Math.exp(2))));
         printTests.put("print \"test\", \"4^4\", 4^4, e(1)", "test, 4^4, "+Double.toString(Math.pow(4, 4))+", "+Double.toString(Math.exp(1)));
      }

      public void setIfElseTests() {
         ifElseTests = new LinkedHashMap<ArrayList<String>, String>();
         final String[][] commands = {
            {"x = 84", "if (x < 0) print \"negative\"", "else if (x == 0) print \"zero\"", "else print \"positive\""},
            {"x = -6", "if (x < 0) print \"negative\"", "else if (x == 0) print \"zero\"", "else print \"positive\""},
            {"x = 0", "if (x < 0) print \"negative\"", "else if (x == 0) print \"zero\"", "else print \"positive\""},
            {"if (e(4) > e(5)) {", "e(4) - e(5)", "}", "else {", "e(5) - e(4)", "}"},
         };
         final String[] results = {
            "positive",
            "negative",
            "zero",
            Double.toString(Math.exp(5)-Math.exp(4)),
         };
         assert commands.length == results.length : "Invalid correspondence of commands to results (Function Tests)";
         for (int i = 0; i < commands.length; i++) {
            ArrayList<String> cmds = new ArrayList<String>();
            for (int j = 0; j < commands[i].length; j++) {
               cmds.add(commands[i][j]);
            }
            ifElseTests.put(cmds, results[i]);
         }
      }

      public void setFunctionTests() {
         functionTests = new LinkedHashMap<ArrayList<String>, String>();
         final String[][] commands = {
            {"define factorial(n) {", "if (n <= 1) return 1", "else return n * factorial(n-1)", "}", "factorial(5)"},
            {"define test(p, q) {", "if (p < 10) return p - q", "else return p + q", "}", "x = 5", "test(x, 2 * x)"},
            {"define fibonacci(n) {", "if (n <= 1) return n", "else return fibonacci(n-1) + fibonacci(n-2)", "}", "fibonacci(8)"},
         };
         final String[] results = {
            Double.toString(5*4*3*2*1),
            Double.toString(5-10),
            Double.toString(13),
         };
         assert commands.length == results.length : "Invalid correspondence of commands to results (Function Tests)";
         for (int i = 0; i < commands.length; i++) {
            ArrayList<String> cmds = new ArrayList<String>();
            for (int j = 0; j < commands[i].length; j++) {
               cmds.add(commands[i][j]);
            }
            functionTests.put(cmds, results[i]);
         }
      }

      @FunctionalInterface
      public interface BinaryOperation {
         double compute(double a, double b);
      }

      @FunctionalInterface
      public interface UnaryOperation {
         double compute(double a);
      }
   }
}