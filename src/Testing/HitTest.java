package Testing;

import java.util.concurrent.TimeUnit;
import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import junit.framework.TestCase;

public class HitTest extends TestCase{
  private WebDriver driver;
  private String baseUrl;
  private StringBuffer verificationErrors = new StringBuffer();

  @Before
  public void setUp() throws Exception {
    driver = new FirefoxDriver();
    baseUrl = "http://localhost:8000/?join=maxhanna&table=Carleton+Room";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  public void testHit() throws Exception {
    driver.get(baseUrl);

	assertTrue(driver.findElement(By.xpath("//center[3]/table/tbody/tr[2]/td[2]")).getText().contains("X of X"));
    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
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
