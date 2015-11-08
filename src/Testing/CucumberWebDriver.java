package Testing;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import cucumber.api.java.Before;

public class CucumberWebDriver {
    private static boolean initialized = false;
    private static WebDriver driver;

    @Before
    public void initialize(){
        if (!initialized){
            initialized = true;
            driver = new FirefoxDriver();
        }
    }
    public static WebDriver getDriver(){
        return driver;
    }   
}