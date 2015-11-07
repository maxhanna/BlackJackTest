package SeleniumFeatures;

import java.util.concurrent.TimeUnit;
import org.junit.*;
import junit.framework.TestCase;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;

public class JoinGameTest extends TestCase{
  private WebDriver driver;
  private String baseUrl;
  private StringBuffer verificationErrors = new StringBuffer();

  @Before
  public void setUp() throws Exception {
    driver = new FirefoxDriver();
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
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }

}
