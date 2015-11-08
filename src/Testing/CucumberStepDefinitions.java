package Testing;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import Server.WebServer;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class CucumberStepDefinitions {
	WebDriver driver = CucumberWebDriver.getDriver();
	@Given("^I am on my Blackjack website$")
	public void navigateToSite() throws Throwable {
		driver.navigate().to("localhost:8000");
	}

	@When("^I click on the username form$")
	public void clickOnUserNameForm() throws Throwable {
		driver.findElement(By.id("username")).click();
	}

	@When("^I populate the username form$")
	public void populateUserNameForm() throws Throwable {

		driver.findElement(By.id("username")).sendKeys("Max Hanna");

	}

	@Then("^I should be on join game room page$")
	public void checkOnUserNameConfirmationPage() throws Throwable {
		driver.findElement(By.id("submit")).click();	
		if (!driver.findElement(By.id("submit")).isDisplayed())
			System.exit(1);
	}


	@Given("^I am already logged in$")
	public void I_have_logged_in() throws Throwable {
		driver.navigate().to("localhost:8000/&username=Max Hanna");
	}

	@When("^I click on the join table button$")
	public void I_click_on_the_join_table_button() throws Throwable {

		driver.findElement(By.id("submit")).click();	
	}

	@Then("^I should be on game room page$")
	public void inGameRoom() throws Throwable {

		driver.navigate().to("http://localhost:8000/?join=Max Hanna&table=Carleton%20Room");
	}


	@Given("^I am already in a game$")
	public void iAmInGame() throws Throwable {
		driver.navigate().to("http://localhost:8000/?join=Max Hanna&table=Carleton%20Room");
	}

	@When("^I click on the hit button$")
	public void iHit() throws Throwable {
		driver.findElement(By.id("hitButton")).click();	
	}
	
	@When("^I click on the stay button$")
	public void iStay() throws Throwable {
		driver.findElement(By.id("stayButton")).click();	
	}

	@Then("^I should be on the end game page$")
	public void endGamePage() throws Throwable {
		driver.navigate().to("http://localhost:8000/?join=Max Hanna&table=Carleton%20Room");
	}
}
