package Testing;

import SeleniumFeatures.HitTest;
import SeleniumFeatures.JoinGameTest;
import SeleniumFeatures.StayTest;
import SeleniumFeatures.WaitForNextGameTest;
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

		try {
			junit.textui.TestRunner.run(suite());
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
	}

}
