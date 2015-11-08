package Testing;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class CucumberStepDefinitions {

	WebDriver driver = CucumberWebDriver.getDriver();
	public void log(String func)
	{

		//LOG PROGRESS
		File file = new File("ResultBJ.txt");


		try {
			FileWriter fileWritter;
			BufferedWriter bufferWritter;
			fileWritter = new FileWriter(file.getName(),true);
			bufferWritter = new BufferedWriter(fileWritter);
			bufferWritter.write("Finished "+func+"()\r\n");
			bufferWritter.close();
			fileWritter.close();
		} catch (Exception e) {
			System.out.println("Could not open log file");
		}

	}
	public void logFailed(String func)
	{

		//LOG PROGRESS
		File file = new File("ResultBJ.txt");


		try {
			FileWriter fileWritter;
			BufferedWriter bufferWritter;
			fileWritter = new FileWriter(file.getName(),true);
			bufferWritter = new BufferedWriter(fileWritter);
			bufferWritter.write("Errors detected in "+func+"() \r\n");
			bufferWritter.close();
			fileWritter.close();
		} catch (Exception e) {
			System.out.println("Could not open log file");
		}

	}
	@Given("^I am on my Blackjack website$")
	public void navigateToSite() throws Throwable {
		try {
			driver.navigate().to("localhost:8000");
		}
		catch (Exception E)
		{
			logFailed("navigateToSite");
		}
		//LOG PROGRESS
		log("navigateToSite");

	}

	@When("^I click on the username form$")
	public void clickOnUserNameForm() throws Throwable {
		try{
			driver.findElement(By.id("username")).click();
		}
		catch (Exception E) {
			logFailed("clickOnUserNameForm");
		}
		//LOG PROGRESS

		log("clickOnUserNameForm");
	}

	@When("^I populate the username form$")
	public void populateUserNameForm() throws Throwable {
		try{
			driver.findElement(By.id("username")).sendKeys("Max Hanna");
		}
		catch (Exception E)
		{
			logFailed("populateUserNameForm");
		}
		//LOG PROGRESS
		log("populateUserNameForm");
	}

	@Then("^I should be on join game room page$")
	public void checkOnUserNameConfirmationPage() throws Throwable {
		try {
		driver.findElement(By.id("submit")).click();
		}
		catch(Exception E) {
			logFailed("checkOnUserNameConfirmationPage");
		}
		if (!driver.findElement(By.id("submit")).isDisplayed())
			System.exit(1);
		//LOG PROGRESS
		log("checkOnUserNameConfirmationPage");
	}


	@Given("^I am already logged in$")
	public void I_have_logged_in() throws Throwable {
		try {
		driver.navigate().to("localhost:8000/&username=Max Hanna");
		}
		catch (Exception e)
		{
			logFailed("I_have_logged_in");
		}
		//LOG PROGRESS
		log("I_have_logged_in");
	}

	@When("^I click on the join table button$")
	public void I_click_on_the_join_table_button() throws Throwable {
try {
		driver.findElement(By.id("submit")).click();	
}
catch (Exception e)
{
	logFailed("I_click_on_the_join_table_button");
}
		//LOG PROGRESS
		log("I_click_on_the_join_table_button");
	}

	@Then("^I should be on game room page$")
	public void inGameRoom() throws Throwable {
try {
		driver.navigate().to("http://localhost:8000/?join=Max Hanna&table=Carleton%20Room");
}
catch(Exception e)
{
	logFailed("inGameRoom");
}
		//LOG PROGRESS
		log("inGameRoom");
	}


	@Given("^I am already in a game$")
	public void iAmInGame() throws Throwable {
		try {
		driver.navigate().to("http://localhost:8000/?join=Max Hanna&table=Carleton%20Room");
		}
		catch(Exception e)
		{
			logFailed("iAmInGame");
		}
		//LOG PROGRESS
		log("iAmInGame");
	}

	@When("^I click on the hit button$")
	public void iHit() throws Throwable {
		try{
			driver.findElement(By.id("hitButton")).click();	
		}
		catch(Exception E)
		{
			logFailed("iHit");
		}
		//LOG PROGRESS
		log("iHit");
	}

	@When("^I click on the stay button$")
	public void iStay() throws Throwable {
		try {
		driver.findElement(By.id("stayButton")).click();	
		}
		catch (Exception e)
		{
			logFailed("iStay");
		}
		//LOG PROGRESS
		log("iStay");
	}

	@Then("^I should be on the end game page$")
	public void endGamePage() throws Throwable {
		try {
		driver.navigate().to("http://localhost:8000/?join=Max Hanna&table=Carleton%20Room");
		}
		catch (Exception e)
		{
			logFailed("endGamePage");
		}
		//LOG PROGRESS
		log("endGamePage");
	}
}
