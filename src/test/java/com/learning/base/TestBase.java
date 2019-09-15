package com.learning.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.learning.utilities.ExcelReader;
import com.learning.utilities.ExtentManager;
import com.learning.utilities.TestUtil;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;


public class TestBase {
	
	
	/*
	 * Wedriver
	 * Properties
	 * Logs
	 * ExtentReports
	 * DB
	 * Excel
	 * Mails 
	 * ReportNg, ExtentReports
	 * Jenkins
	 */

	public static WebDriver driver;
	public static Properties config = new Properties();
	public static Properties OR = new Properties();
	public static FileInputStream fis;
	public static Logger log = Logger.getLogger("devpinoyLogger");
	public static ExcelReader excel = new ExcelReader(System.getProperty("user.dir") +"\\src\\test\\resources\\excel\\testdata.xlsx");
	//public static ExcelReader excel = new ExcelReader(System.getProperty("user.dir") +"\\src\\test\\resources\\excel\\testdata.xlsx");
	public static WebDriverWait wait;
	public ExtentReports extRep = ExtentManager.getInstance();
	public static ExtentTest test;
	public static String browser;
	
	@BeforeSuite
	public void setUp() {
		
		if(driver == null) {
			
			FileInputStream fis = null;
			try {
				fis = new FileInputStream(System.getProperty("user.dir") +"\\src\\test\\resources\\properties\\Config.properties");
			} 
			catch (FileNotFoundException e) {				
				e.printStackTrace();
			} 
			try {
				config.load(fis);
				log.debug("config file loaded !!!");
			}
			catch (IOException e) {
				
				e.printStackTrace();
			}
			try {
			fis = new FileInputStream(System.getProperty("user.dir") +"\\src\\test\\resources\\properties\\OR.properties");
			}
			catch (FileNotFoundException e) {				
				e.printStackTrace();
			}
			
			try {
				OR.load(fis);
				log.debug("OR file loaded !!!");
			}
			catch (IOException e) {
				
				e.printStackTrace();
			}
			
			
			// to take the browser from jenkins
			if(System.getenv("browser")!=null && !System.getenv("browser").isEmpty()) {
				
				browser = System.getenv("browser");
			}else {
				
				browser = config.getProperty("browser");
			}
			
			config.setProperty("browser", browser);			
				
		
			if(config.getProperty("browser").equalsIgnoreCase("firefox")) {
				
				//System.setProperty("webdriver.gecko.driver",System.getProperty("user.dir") +"\\src\\test\\resources\\executables\\geckodriver.exe");
				driver = new FirefoxDriver();
				
			}else if(config.getProperty("browser").equalsIgnoreCase("chrome")) {
				
				System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir") +"\\src\\test\\resources\\executables\\chromedriver.exe");
				driver = new ChromeDriver();
				log.debug("Chrome browser launched !!!");
				
			}else if(config.getProperty("browser").equalsIgnoreCase("InternetExplorer")) {
				
				System.setProperty("webdriver.ie.driver",System.getProperty("user.dir") +"\\src\\test\\resources\\executables\\IEDriverServer.exe");
				driver = new InternetExplorerDriver();
				
			}
			
			driver.get(config.getProperty("testsiteURL"));
			log.debug("navigated to the URL  ...." + config.getProperty("testsiteURL"));			
			driver.manage().timeouts().implicitlyWait(Integer.parseInt(config.getProperty("Implicit.wait")), TimeUnit.SECONDS);
			driver.manage().window().maximize();
			wait = new WebDriverWait(driver, 5);
			
		}
		
	}

	
	public boolean isElementPresent(By by) {
		
		try {
			driver.findElement(by);
			return true;
			
		}catch(NoSuchElementException e) {
			return false;
		}
		
		
	}
	
	
	public void click(String locator) {
		
		if(locator.endsWith("_CSS")) {
			driver.findElement(By.cssSelector(OR.getProperty(locator))).click();
		}else if(locator.endsWith("_XPATH")) {
			driver.findElement(By.xpath(OR.getProperty(locator))).click();
		}else if(locator.endsWith("_ID")) {
			driver.findElement(By.id(OR.getProperty(locator))).click();
		}else if(locator.endsWith("_NAME")) {
			driver.findElement(By.name(OR.getProperty(locator))).click();
		}else if(locator.endsWith("_LINKTEXT")) {
			driver.findElement(By.linkText(OR.getProperty(locator))).click();
		}else if(locator.endsWith("_CLASSNAME")) {
			driver.findElement(By.className(OR.getProperty(locator))).click();
		}
		
		test.log(LogStatus.INFO, "Clicking on : " +locator);
	}
	
	public void type(String locator, String value) {
		
		if(locator.endsWith("_CSS")) {
			driver.findElement(By.cssSelector(OR.getProperty(locator))).sendKeys(value);
		}else if(locator.endsWith("_XPATH")) {
			driver.findElement(By.xpath(OR.getProperty(locator))).sendKeys(value);
		}else if(locator.endsWith("_ID")) {
			driver.findElement(By.id(OR.getProperty(locator))).sendKeys(value);
		}else if(locator.endsWith("_NAME")) {
			driver.findElement(By.name(OR.getProperty(locator))).sendKeys(value);
		}else if(locator.endsWith("_LINKTEXT")) {
			driver.findElement(By.linkText(OR.getProperty(locator))).sendKeys(value);
		}else if(locator.endsWith("_CLASSNAME")) {
			driver.findElement(By.className(OR.getProperty(locator))).sendKeys(value);
		}
		
		test.log(LogStatus.INFO, "Typing in : " +locator +" entered value as :" +value);
	}
	
	
	public void verifyEquals(String expected,String actual) throws IOException {
		
		try {
			Assert.assertEquals(actual, expected);
		}catch(Throwable t) {
			
			TestUtil.captureScreenshot();
			
			//ReportNG
			Reporter.log("<br>"+"Verification Failure : "+t.getMessage() +"<br>");
			Reporter.log("<a target =\"blank\"href ="+TestUtil.screenshotName+">"
					+ "<img src="+TestUtil.screenshotName+" height=200 width=200></img></a>");
			Reporter.log("<br>");
			Reporter.log("<br>");
			
			//ExtentReports
			test.log(LogStatus.FAIL, " Verification Failed with exception : " +t.getMessage());
			test.log(LogStatus.FAIL, test.addScreenCapture(TestUtil.screenshotName));
			
		}
		
		
	}
	
	static WebElement dropdown;
	
	public void select(String locator, String value) {
		
		if(locator.endsWith("_CSS")) {
			dropdown = driver.findElement(By.cssSelector(OR.getProperty(locator)));
		}else if(locator.endsWith("_XPATH")) {
			dropdown = driver.findElement(By.xpath(OR.getProperty(locator)));
		}else if(locator.endsWith("_ID")) {
			dropdown = driver.findElement(By.id(OR.getProperty(locator)));
		}else if(locator.endsWith("_NAME")) {
			dropdown = driver.findElement(By.name(OR.getProperty(locator)));
		}else if(locator.endsWith("_LINKTEXT")) {
			dropdown = driver.findElement(By.linkText(OR.getProperty(locator)));
		}else if(locator.endsWith("_CLASSNAME")) {
			dropdown = driver.findElement(By.className(OR.getProperty(locator)));
		}
		
		Select select = new Select(dropdown);
		
		test.log(LogStatus.INFO, "Typing in : " +locator +" entered value as :" +value);
		
	}
	
	@AfterSuite
	public void tearDown() {
		if(driver!=null) {
		driver.quit();
		}
		log.debug("Test excecution completed");
	}
	
	
	
	
}
