package Testing;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

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
