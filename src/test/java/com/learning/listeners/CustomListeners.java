package com.learning.listeners;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.mail.MessagingException;

import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.SkipException;

import com.learning.base.TestBase;
import com.learning.utilities.MonitoringMail;
import com.learning.utilities.TestConfig;
import com.learning.utilities.TestUtil;
import com.relevantcodes.extentreports.LogStatus;

public class CustomListeners extends TestBase implements ITestListener, ISuiteListener{

	
	String messageBody;
	@Override
	public void onTestStart(ITestResult result) {
		test = extRep.startTest(result.getName().toUpperCase());
		
		if(!TestUtil.isTestRunnable(result.getName(), excel)) {
			
			throw new SkipException("Skipping the Test  "+result.getName().toUpperCase()+" as the RunMode is NO");
		}
		
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		
		test.log(LogStatus.PASS, result.getName().toUpperCase()+" PASS");
		extRep.endTest(test);
		extRep.flush();
	}

	@Override
	public void onTestFailure(ITestResult result) {
		
		System.setProperty("org.uncommons.reportng.escape-output", "false");
		try {
			TestUtil.captureScreenshot();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//ExtentReports
		test.log(LogStatus.FAIL, result.getName().toUpperCase()+" Failed with exception : "+result.getThrowable());
		test.log(LogStatus.FAIL, test.addScreenCapture(TestUtil.screenshotName));
		
		//ReportNG
		Reporter.log("Click to see Screenshots");
		Reporter.log("<a target =\"blank\" href="+TestUtil.screenshotName+">Screenshot</a>");
		Reporter.log("<br>");
		Reporter.log("<br>");
		Reporter.log("<a target =\"blank\"href ="+TestUtil.screenshotName+">"
				+ "<img src="+TestUtil.screenshotName+" height=200 width=200></img></a>");
	
		extRep.endTest(test);
		extRep.flush();
		
	}

	@Override
	public void onTestSkipped(ITestResult result) {
	
		test.log(LogStatus.SKIP, result.getName().toUpperCase()+" Skipped the Test as the Run Mode is NO");
		extRep.endTest(test);
		extRep.flush();
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStart(ISuite suite) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onFinish(ISuite suite) {

		MonitoringMail mail = new MonitoringMail();
		try {
			messageBody = "http://"+InetAddress.getLocalHost().getHostAddress()
					+":8080/job/LiveProject_DataDrivenTestFramework/Extent_5fReport/";
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			mail.sendMail(TestConfig.server, TestConfig.from, TestConfig.to, TestConfig.subject, messageBody);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	

}
