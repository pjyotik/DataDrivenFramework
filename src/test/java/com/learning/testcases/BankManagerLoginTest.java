package com.learning.testcases;

import java.io.IOException;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.learning.base.TestBase;


public class BankManagerLoginTest extends TestBase{
	
	@Test
	public void bankManagerLoginTest() throws InterruptedException, IOException {
		
		
		verifyEquals("abc","xyz");
		Thread.sleep(3000);
		log.debug("Inside Login test");
		Reporter.log("Inside Login test");
		click("bankMgrLoginBtn_CSS");
		Thread.sleep(3000);
		
		Assert.assertTrue(isElementPresent(By.cssSelector(OR.getProperty("addCustBtn_CSS"))), "Login Not Successful");
		log.debug("Login Successfully executed");
		Reporter.log("Login Successfully executed");
		
		//Assert.fail("Test Case Failed");
		
	}

	

}
