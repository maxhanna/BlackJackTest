package Testing;
import SeleniumFeatures.*;
import junit.framework.Test;
import junit.framework.TestSuite;

public class SeleniumTestSuite {


	public static Test suite() {
		TestSuite suite = new TestSuite();
		suite.addTestSuite(JoinGameTest.class);
		suite.addTestSuite(HitTest.class);
		suite.addTestSuite(StayTest.class);
		suite.addTestSuite(WaitForNextGameTest.class);
		// play another game.

		suite.addTestSuite(HitTest.class);
		suite.addTestSuite(StayTest.class);
		suite.addTestSuite(WaitForNextGameTest.class);
		
		return suite;
	}

	public static void main(String[] args) {
		junit.textui.TestRunner.run(suite());
	}

}
