package com.learning.testcases;

import java.util.Hashtable;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.learning.base.TestBase;
import com.learning.utilities.TestUtil;

public class OpenAccountTest extends TestBase{
	
	
	@Test(dataProviderClass=TestUtil.class, dataProvider="dp")
	public void openAccountTest(Hashtable<String,String> data) throws InterruptedException {
		
		if(!TestUtil.isTestRunnable("openAccountTest", excel)) {
			
			throw new SkipException("Skipping the Test  "+"openAccountTest".toUpperCase()+" as the RunMode is NO");
		}
		
		click("openaccount_CSS");
		select("customer_CSS", data.get("Customer"));
		select("currency_CSS", data.get("Currency"));
		click("process_CSS");
		
		Thread.sleep(3000);
		Alert alert = wait.until(ExpectedConditions.alertIsPresent());
		alert.accept();
		
	}
	
	
}
