package Testing;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
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

			TimeUnit.SECONDS.sleep(3);
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
	public void logTimedOut(String func)
	{

		//LOG PROGRESS
		File file = new File("ResultBJ.txt");


		try {
			FileWriter fileWritter;
			BufferedWriter bufferWritter;
			fileWritter = new FileWriter(file.getName(),true);
			bufferWritter = new BufferedWriter(fileWritter);
			bufferWritter.write(func+"() timed out. \r\n");
			bufferWritter.close();
			fileWritter.close();
		} catch (Exception e) {
			System.out.println("Could not open log file");
		}

	}
	@Given("^I am on my Blackjack website$")
	public void navigateToSite() throws Throwable {
		try {
			driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
			driver.navigate().to("localhost:8000");
		}
		catch (TimeoutException e)
		{
			logTimedOut("navigateToSite");
			
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
			driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
			if (driver.findElement(By.id("username")).isDisplayed())
				driver.findElement(By.id("username")).click();
		}
		catch (TimeoutException e)
		{
			logTimedOut("clickOnUserNameForm");
			
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
			driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
			if (driver.findElement(By.id("username")).isDisplayed())
				driver.findElement(By.id("username")).sendKeys("Max Hanna");
		}
		catch (TimeoutException e)
		{
			logTimedOut("populateUserNameForm");
			
		}
		catch (Exception E)
		{
			logFailed("populateUserNameForm");
		}
		//LOG PROGRESS
		log("populateUserNameForm");
	}

	@Then("^I should be on join game room page$")
	public void goToGameTablePage() throws Throwable {
		try {
			driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
			if (driver.findElement(By.id("submit")).isDisplayed())
				driver.findElement(By.id("submit")).click();
		}
		catch (TimeoutException e)
		{
			logTimedOut("goToGameTablePage");
			
		}
		catch(Exception E) {
			logFailed("goToGameTablePage");
		}
		if (!driver.findElement(By.id("submit")).isDisplayed())
			System.exit(1);
		//LOG PROGRESS
		log("goToGameTablePage");
	}


	@Given("^I am already logged in$")
	public void loggedIn() throws Throwable {
		try {
			driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
			driver.navigate().to("localhost:8000/&username=Max Hanna");
		}
		catch (TimeoutException e)
		{
			logTimedOut("loggedIn");
			
		}
		catch (Exception e)
		{
			logFailed("loggedIn");
		}
		//LOG PROGRESS
		log("loggedIn");
	}

	@When("^I click on the join table button$")
	public void clickOnJoinTable() throws Throwable {
		try {
			driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
			if (driver.findElement(By.id("submit")).isDisplayed())
				driver.findElement(By.id("submit")).click();	
		}
		catch (TimeoutException e)
		{
			logTimedOut("clickOnJoinTable");
			
		}
		catch (Exception e)
		{
			logFailed("clickOnJoinTable");
		}
		//LOG PROGRESS
		log("clickOnJoinTable");
	}

	@Then("^I should be on game room page$")
	public void inGameRoom() throws Throwable {
		try {
			driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
			driver.navigate().to("http://localhost:8000/?join=Max Hanna&table=Carleton%20Room");
		}
		catch (TimeoutException e)
		{
			logTimedOut("inGameRoom");
			
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
			driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
			driver.navigate().to("http://localhost:8000/?join=Max Hanna&table=Carleton%20Room");
		}
		catch (TimeoutException e)
		{
			logTimedOut("iAmInGame");
			
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
			driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
			if (driver.findElement(By.id("hitButton")).isDisplayed())
				driver.findElement(By.id("hitButton")).click();	
		}
		catch (TimeoutException e)
		{
			logTimedOut("iHit");
			
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
			driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
			if (driver.findElement(By.id("stayButton")).isDisplayed())
				driver.findElement(By.id("stayButton")).click();	
		}
		catch (TimeoutException e)
		{
			logTimedOut("iStay");
			
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
			driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
			driver.navigate().to("http://localhost:8000/?join=Max Hanna&table=Carleton%20Room");
		}
		catch (TimeoutException e)
		{
			logTimedOut("endGamePage");
			
		}
		catch (Exception e)
		{
			logFailed("endGamePage");
		}
		//LOG PROGRESS
		log("endGamePage");
	}
	@Given("^I populate the create table form$")
	public void populateCreateTableForm() throws Throwable {
		try{
			driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
			if (driver.findElement(By.id("tableName")).isDisplayed() && driver.findElement(By.id("numAI")).isDisplayed())
			{	
				driver.findElement(By.id("tableName")).sendKeys("Hungry");
				driver.findElement(By.id("numAI")).sendKeys("3");
			}
		}
		catch (TimeoutException e)
		{
			logTimedOut("populateCreateTableForm");
			
		}
		catch (Exception E)
		{
			logFailed("populateCreateTableForm");
		}
		//LOG PROGRESS
		log("populateCreateTableForm");
	}

	@Given("^I click on the create table forms submit button$")
	public void submitCreateTable() throws Throwable {
		try{
			driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
			if (driver.findElement(By.id("create")).isDisplayed())
				driver.findElement(By.id("create")).click();	
		}
		catch (TimeoutException e)
		{
			logTimedOut("submitCreateTable");
			
		}
		catch (Exception E)
		{
			logFailed("submitCreateTable");
		}
		//LOG PROGRESS
		log("submitCreateTable");
	}
	@When("^I click on the new join table button$")
	public void joinNewTable() throws Throwable {
		try {
			driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
			if (driver.findElement(By.id("submit")).isDisplayed())
				driver.findElement(By.id("submit")).click();
		}
		catch (TimeoutException e)
		{
			logTimedOut("joinNewTable");
			
		}
		catch (Exception e)
		{
			logFailed("joinNewTable");
		}
		//LOG PROGRESS
		log("joinNewTable");
	}

	@Then("^I should be on new game room page$")
	public void onNewGameRoomPage() throws Throwable {
		try {
			driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
			driver.navigate().to("http://localhost:8000/?username=Max Hanna&table=Hungry");
		}
		catch (TimeoutException e)
		{
			logTimedOut("onNewGameRoomPage");
			driver.navigate().to("http://localhost:8000/?username=Max Hanna&table=Hungry");
			
		}
		catch (Exception e)
		{
			logFailed("onNewGameRoomPage");
		}
		//LOG PROGRESS
		log("onNewGameRoomPage");
	}

}
