package pnt.va.bootcamp.BootCamp_Selenium_Expedia;

import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;

/*
 * Go to https://expedia.com and create a script by using Maven project with
TestNG framework for following steps:
	i. Click on flights tab
	ii. Fill the form to search flight for roundtrip and click on search button
	iii. Select round trip flights
	iv. Switch to other tab
	v. Click on checkout
	vi. Fill the passenger’s information */

public class Expedia {
	String url = "https://expedia.com";
	ChromeDriver driver = new ChromeDriver();
	Actions act = new Actions(driver);
	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

	@Test
	public void homepageTest() throws InterruptedException {
		System.out.println("Test started!");

		WebElement flightTab = driver.findElement(By.xpath("//span[text()='Flights']"));
		flightTab.click();

		WebElement leaveFrom = driver.findElement(By.xpath("//button[@aria-label='Leaving from']"));
		leaveFrom.click();

		WebElement enterLeaveFrom = driver.findElement(By.xpath("//input[@placeholder='Where are you leaving from?']"));
		enterLeaveFrom.sendKeys("jfk" + Keys.ENTER);

		WebElement goingTo = driver.findElement(By.xpath("//button[@aria-label='Going to']"));
		goingTo.click();

		WebElement enterGoingTo = driver.findElement(By.id("location-field-leg1-destination"));
		enterGoingTo.sendKeys("hcmc" + Keys.ENTER);

		WebElement departDate = driver.findElement(By.id("d1-btn"));
		departDate.click();
		driver.findElement(By.xpath("//*[@aria-label='Nov 20, 2022']")).click();
		driver.findElement(By.xpath("//*[@aria-label='Nov 30, 2022']")).click();

		WebElement doneBtn = driver.findElement(By.xpath("//button[@data-stid='apply-date-picker']"));
		wait.until(ExpectedConditions.visibilityOf(doneBtn));
		doneBtn.click();

		WebElement submitBtn = driver.findElement(By.xpath("//button[@data-testid='submit-button']"));
		act.keyDown(submitBtn, Keys.COMMAND).click().keyUp(Keys.COMMAND).build().perform();
		Thread.sleep(5000);

		Set<String> windows = driver.getWindowHandles();
		Iterator<String> it = windows.iterator();
		String window1 = it.next();
		System.out.println(window1);
		String window2 = it.next();
		System.out.println(window2);

		driver.switchTo().window(window2);

		/* --------------------CAPCHA---IMPROVISED----------------- */

		driver.findElement(
				By.xpath("(//span[contains(text()," + "'All Nippon Airways flight, departing at 12:45pm')]")); // 1st depart flight 
																					
		driver.findElement(By.xpath("//button[text()='Select']")); // Select Fare button click

		driver.findElement(
				By.xpath("(//span[contains(text()," + "'All Nippon Airways flight, departing at 11:05pm')]")); // 1st return fligjt

		driver.findElement(By.xpath("//button[text()='Select']")); // Select Fare button click

		driver.findElement(By.xpath("//span[text()='Check out']")); // Checkout button click

		// Enter Traveller info *****
		driver.findElement(By.id("firstname[0]")).sendKeys("Kate");
		driver.findElement(By.id("lastname[0]")).sendKeys("Kumishi");

		WebElement countrySelect = driver.findElement(By.xpath("(//select[@aria-label='Country/Territory Code'])[1]"));
		Select selCountry = new Select(countrySelect);
		selCountry.selectByValue("1"); // select United States +1

		driver.findElement(By.id("phone-number[0]")).sendKeys("1112223333");
		driver.findElement(By.xpath("//span[text()='Male']")); // Select Male radio button

		WebElement monthDOB = driver.findElement(By.xpath("(//select[@data-custom-rules='validateDateOfBirth'])[1]"));
		Select selMonth = new Select(monthDOB);
		selMonth.selectByValue("5"); // select May

		WebElement dayDOB = driver.findElement(By.xpath("(//select[@data-custom-rules='validateDateOfBirth'])[2]"));
		Select selDay = new Select(dayDOB);
		selDay.selectByValue("26"); // select 26

		WebElement yearDOB = driver.findElement(By.xpath("(//select[@data-custom-rules='validateDateOfBirth'])[3]"));
		Select selYear = new Select(yearDOB);
		selYear.selectByVisibleText("1998"); // select year

		// Flight protection filled info *****
		driver.findElement(By.id("yes_insurance")).clear(); // yes, protect trip radio button
		driver.findElement(By.xpath("(//input[@name='creditCards[0].cardholder_name'])[1]"))
				.sendKeys("KateK.ushi@gmail.com"); // enter cc name
		driver.findElement(By.id("creditCardInput")).sendKeys("1231243523523"); // enter cc fake number

		WebElement monthCardExpiri = driver.findElement(By.xpath("//select[@name='creditCards[0].expiration_month']"));
		Select selMonthExpired = new Select(monthCardExpiri);
		selMonthExpired.selectByValue("4"); // select month April

		WebElement yearCardExpiri = driver.findElement(By.xpath("//select[@name='creditCards[0].expiration_month']"));
		Select selYearExpired = new Select(yearCardExpiri);
		selYearExpired.selectByValue("2026"); // select year 2026

		driver.findElement(By.id("new_cc_security_code")).sendKeys("452");
		driver.findElement(By.name("creditCards[0].street")).sendKeys("123 Ark Lane");
		driver.findElement(By.name("creditCards[0].city")).sendKeys("Darken City");

		WebElement cardState = driver.findElement(By.xpath("//select[@name=\"creditCards[0].state\"]"));
		Select selCardState = new Select(cardState);
		selCardState.selectByValue("NJ");

		driver.findElement(By.name("creditCards[0].zipcode")).sendKeys("12345");
		
		driver.findElement(By.id("CKO.CONFEMAILADDRESS")).click(); // Signup for email
		driver.findElement(By.id("complete-booking")).click(); // Checkout button

	}

	@BeforeMethod
	public void beforeMethod() {
		driver.get(url);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
	}

	@AfterMethod
	public void afterMethod() {
		driver.quit();

	}

	@BeforeClass
	public void beforeClass() {
		WebDriverManager.chromedriver().setup();
	}

	@AfterClass
	public void Screenshots() throws IOException {
		String path = System.getProperty("user.dir");
		Date dateSS = new Date();
		String screenshotDate = dateSS.toString().replace(" ", "_").replace(":", "_");
		File snap = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		FileHandler.copy(snap, new File(path + "/snapshot//" + screenshotDate + "expedia.png"));
	}

}
