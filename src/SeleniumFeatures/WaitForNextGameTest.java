package SeleniumFeatures;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.logging.Logs;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import junit.framework.TestCase;

public class WaitForNextGameTest extends TestCase {
	private WebDriver driver;
	private String baseUrl;
	private StringBuffer verificationErrors = new StringBuffer();

	@Before
	public void setUp() throws Exception {
		LoggingPreferences logs = new LoggingPreferences();
		logs.enable(LogType.BROWSER, Level.ALL);
		logs.enable(LogType.CLIENT, Level.ALL);
		logs.enable(LogType.DRIVER, Level.ALL);
		logs.enable(LogType.PERFORMANCE, Level.ALL);
		logs.enable(LogType.PROFILER, Level.ALL);
		logs.enable(LogType.SERVER, Level.ALL);

		DesiredCapabilities desiredCapabilities = DesiredCapabilities.firefox();
		desiredCapabilities.setCapability(CapabilityType.LOGGING_PREFS, logs);
		driver = new FirefoxDriver(desiredCapabilities);
		baseUrl = "http://localhost:8000/?join=maxhanna&table=Carleton+Room";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@Test
	public void testWaitForNextGame() throws Exception {
		driver.get(baseUrl);
		assertTrue(driver.findElement(By.cssSelector("center")).getText().contains("There are")
				&& driver.findElement(By.cssSelector("center")).getText().contains(" seconds until table restarts for next round!"));
		Thread.sleep(57000); 
		
		assertTrue(driver.findElement(By.cssSelector("input[type=\"submit\"]")).isDisplayed());
	}

	@After
	public void tearDown() throws Exception {
		Logs logs = driver.manage().logs();
		LogEntries logEntries = logs.get(LogType.DRIVER);
		File file = new File("ResultBJ.txt");

		FileWriter fileWritter = new FileWriter(file.getName(),true);
		BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
		for (LogEntry logEntry : logEntries) {
			bufferWritter.write(logEntry.getMessage() + "\r\n");

		}

		bufferWritter.write("Finished WaitForNextGameTest Selenium Test\r\n");
		bufferWritter.close();
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}
}
