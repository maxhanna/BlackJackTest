package Testing;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import Server.WebServer;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class StepDefinitions {
	WebDriver driver = new FirefoxDriver();
	int counter = 0;
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

		driver.findElement(By.id("username")).sendKeys("Max Hanna"+counter);
	}

	@Then("^I should be on join game room page$")
	public void checkOnUserNameConfirmationPage() throws Throwable {
		driver.findElement(By.id("submit")).click();	
		if (!driver.findElement(By.id("submit")).isDisplayed())
			System.exit(1);
	}


	@Given("^I have already logged in$")
	public void I_have_logged_in() throws Throwable {
		navigateToSite();
		clickOnUserNameForm();
		populateUserNameForm();
		checkOnUserNameConfirmationPage();
	}

	@When("^I click on the join table button$")
	public void I_click_on_the_join_table_button() throws Throwable {

		driver.findElement(By.id("submit")).click();	
	}

	@Then("^I should be on game room page$")
	public void I_should_be_on_game_room_page() throws Throwable {

		assert(driver.findElement(By.xpath("//center[3]/table/tbody/tr[2]/td[2]")).getText().contains("X of X"));
	}


}
