package Testing;

import org.junit.runner.RunWith;

import cucumber.api.junit.Cucumber;

@SuppressWarnings("deprecation")
@RunWith(Cucumber.class)
@Cucumber.Options(format = {"pretty","json:target/"},features = {"src/CucumberFeatures/"})

public class CucumberTest {

}
