package SeleniumFeatures;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import org.junit.*;
import junit.framework.TestCase;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.logging.Logs;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

public class JoinGameTest extends TestCase{
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
		baseUrl = "http://localhost:8000";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	public void testJoinAGame() throws Exception {
		driver.get(baseUrl);
		driver.findElement(By.id("username")).clear();
		driver.findElement(By.id("username")).sendKeys("maxhanna");
		driver.findElement(By.cssSelector("input[id=\"submit\"]")).click();
		driver.findElement(By.cssSelector("input[id=\"submit\"]")).click();
		assertTrue(driver.findElement(By.xpath("//center[3]/table/tbody/tr[2]/td[2]")).getText().contains("X of X"));

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

		bufferWritter.close();
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}

}
