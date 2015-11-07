package SeleniumFeatures;

import java.util.concurrent.TimeUnit;
import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import junit.framework.TestCase;

public class WaitForNextGameTest extends TestCase {
	private WebDriver driver;
	private String baseUrl;
	private StringBuffer verificationErrors = new StringBuffer();

	@Before
	public void setUp() throws Exception {
		driver = new FirefoxDriver();
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
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}
}
