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
	
	@Given("^I am on my Blackjack website$")
	public void navigateToSite() throws Throwable {
		driver.navigate().to("localhost:8000");
		//LOG PROGRESS
		File file = new File("ResultBJ.txt");

		FileWriter fileWritter = new FileWriter(file.getName(),true);
		BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
		bufferWritter.write("Finished navigateToSite() with no errors \r\n");
		bufferWritter.close();
		fileWritter.close();
		
	}

	@When("^I click on the username form$")
	public void clickOnUserNameForm() throws Throwable {
		driver.findElement(By.id("username")).click();
		//LOG PROGRESS
		File file = new File("ResultBJ.txt");

		FileWriter fileWritter = new FileWriter(file.getName(),true);
		BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
		bufferWritter.write("Finished clickOnUserNameForm() with no errors \r\n");
		bufferWritter.close();
		fileWritter.close();
	}

	@When("^I populate the username form$")
	public void populateUserNameForm() throws Throwable {

		driver.findElement(By.id("username")).sendKeys("Max Hanna");
		//LOG PROGRESS
		File file = new File("ResultBJ.txt");

		FileWriter fileWritter = new FileWriter(file.getName(),true);
		BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
		bufferWritter.write("Finished populateUserNameForm() with no errors \r\n");
		bufferWritter.close();
		fileWritter.close();
	}

	@Then("^I should be on join game room page$")
	public void checkOnUserNameConfirmationPage() throws Throwable {
		driver.findElement(By.id("submit")).click();	
		if (!driver.findElement(By.id("submit")).isDisplayed())
			System.exit(1);
		//LOG PROGRESS
		File file = new File("ResultBJ.txt");

		FileWriter fileWritter = new FileWriter(file.getName(),true);
		BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
		bufferWritter.write("Finished checkOnUserNameConfirmationPage() with no errors \r\n");
		bufferWritter.close();
		fileWritter.close();
	}


	@Given("^I am already logged in$")
	public void I_have_logged_in() throws Throwable {
		driver.navigate().to("localhost:8000/&username=Max Hanna");
		//LOG PROGRESS
		File file = new File("ResultBJ.txt");

		FileWriter fileWritter = new FileWriter(file.getName(),true);
		BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
		bufferWritter.write("Finished I_have_logged_in() with no errors \r\n");
		bufferWritter.close();
		fileWritter.close();
	}

	@When("^I click on the join table button$")
	public void I_click_on_the_join_table_button() throws Throwable {

		driver.findElement(By.id("submit")).click();	
		//LOG PROGRESS
		File file = new File("ResultBJ.txt");

		FileWriter fileWritter = new FileWriter(file.getName(),true);
		BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
		bufferWritter.write("Finished I_click_on_the_join_table_button() with no errors \r\n");
		bufferWritter.close();
		fileWritter.close();
	}

	@Then("^I should be on game room page$")
	public void inGameRoom() throws Throwable {

		driver.navigate().to("http://localhost:8000/?join=Max Hanna&table=Carleton%20Room");
		//LOG PROGRESS
		File file = new File("ResultBJ.txt");

		FileWriter fileWritter = new FileWriter(file.getName(),true);
		BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
		bufferWritter.write("Finished inGameRoom() with no errors \r\n");
		bufferWritter.close();
		fileWritter.close();
	}


	@Given("^I am already in a game$")
	public void iAmInGame() throws Throwable {
		driver.navigate().to("http://localhost:8000/?join=Max Hanna&table=Carleton%20Room");
		//LOG PROGRESS
		File file = new File("ResultBJ.txt");

		FileWriter fileWritter = new FileWriter(file.getName(),true);
		BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
		bufferWritter.write("Finished iAmInGame() with no errors \r\n");
		bufferWritter.close();
		fileWritter.close();
	}

	@When("^I click on the hit button$")
	public void iHit() throws Throwable {
		driver.findElement(By.id("hitButton")).click();	
		//LOG PROGRESS
		File file = new File("ResultBJ.txt");

		FileWriter fileWritter = new FileWriter(file.getName(),true);
		BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
		bufferWritter.write("Finished iHit() with no errors \r\n");
		bufferWritter.close();
		fileWritter.close();
	}
	
	@When("^I click on the stay button$")
	public void iStay() throws Throwable {
		driver.findElement(By.id("stayButton")).click();	
		//LOG PROGRESS
		File file = new File("ResultBJ.txt");

		FileWriter fileWritter = new FileWriter(file.getName(),true);
		BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
		bufferWritter.write("Finished iStay() with no errors \r\n");
		bufferWritter.close();
		fileWritter.close();
	}

	@Then("^I should be on the end game page$")
	public void endGamePage() throws Throwable {
		driver.navigate().to("http://localhost:8000/?join=Max Hanna&table=Carleton%20Room");
		//LOG PROGRESS
		File file = new File("ResultBJ.txt");

		FileWriter fileWritter = new FileWriter(file.getName(),true);
		BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
		bufferWritter.write("Finished endGamePage() with no errors \r\n");
		bufferWritter.close();
		fileWritter.close();
	}
}
