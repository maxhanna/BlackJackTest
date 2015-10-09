package Testing;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;

import org.junit.*;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.Test;
import junit.framework.TestSuite;

public class Testsuite {


	public static Test suite() {
		WebDriver driver = new FirefoxDriver();
		String baseUrl = "http://localhost:8000";
		boolean acceptNextAlert = true;
		StringBuffer verificationErrors = new StringBuffer();


		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		TestSuite suite = new TestSuite();
		suite.addTestSuite(JoinGameTest.class);
		suite.addTestSuite(HitTest.class);
		suite.addTestSuite(StayTest.class);
		//suite.addTestSuite(WaitForNextGameTest.class);

		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
		
		return suite;
	}

	public static void main(String[] args) {
		junit.textui.TestRunner.run(suite());
	}

}
