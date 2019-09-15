package com.learning.testcases;

import java.util.Hashtable;

import org.openqa.selenium.Alert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;

import com.learning.base.TestBase;
import com.learning.utilities.TestUtil;

public class AddCustomerTest extends TestBase{
	
	
	@Test(dataProviderClass=TestUtil.class, dataProvider="dp")
	public void addCustomerTest(Hashtable<String,String> data) throws InterruptedException {
		
		if(!data.get("RunMode").equals("Y")) {
			throw new SkipException("Skipping the Test case as the RunMode for the data set is NO");
		}
		
		
		click("addCustBtn_CSS");
		type("firstname_CSS",data.get("Firstname"));
		type("lastname_XPATH",data.get("LastName"));
		type("postcode_CSS",data.get("PostCode"));
		click("addBtn_CSS");
		
		Thread.sleep(2000);
		Alert alert = wait.until(ExpectedConditions.alertIsPresent());		
		Assert.assertTrue(alert.getText().contains(data.get("AlertText")));		
		alert.accept();
		
		Thread.sleep(2000);
	}

}
