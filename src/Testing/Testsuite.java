package Testing;
import junit.framework.Test;
import junit.framework.TestSuite;

public class Testsuite {

  public static Test suite() {
    TestSuite suite = new TestSuite();
    suite.addTestSuite(JoinGameTest.class);
    suite.addTestSuite(StayTest.class);
    suite.addTestSuite(HitTest.class);
    return suite;
  }

  public static void main(String[] args) {
    junit.textui.TestRunner.run(suite());
  }
}
