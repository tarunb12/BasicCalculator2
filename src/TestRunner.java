import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class TestRunner {

   public static final String ANSI_GREEN =  "\u001B[32m";
   public static final String ANSI_RED = "\u001B[31m";
   public static final String ANSI_LOW = "\u001B[2m";
   public static final String ANSI_RESET = "\u001B[0m";

   public static void main(String[] args) {
      System.out.println("");
      Result result = JUnitCore.runClasses(TestJunit.class);
      for (Failure failure : result.getFailures()) {
         System.out.println(failure.toString());
      }
      int failures = result.getFailureCount();
      int successes = result.getRunCount() - failures;
      long time = result.getRunTime();
      if (successes > 0) System.out.print(" " + ANSI_GREEN + Integer.toString(successes) + " tests passed" + ANSI_RESET);
      if (failures > 0) System.out.print(" " + ANSI_RED + Integer.toString(failures) + " tests failed" + ANSI_RESET);
      System.out.print(ANSI_LOW + " (" + Long.toString(time) + " ms)" + ANSI_RESET);
      System.out.println("\n");
   }
}