package com.makemytrip.assignment;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

import com.makemytrip.utilities.UiOperations;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

public class Base_NG {
	
	public static WebDriver oDriver;
	static ExtentTest test;
	static ExtentReports report;
	
	@BeforeSuite
	public void initializeSuite() {
		report = new ExtentReports(System.getProperty("user.dir")+"\\ExtentReportResults.html");
		test = report.startTest("Hyderabad To Mumbai");
	}
	
	@BeforeClass
	public void launchBrowser() {
		oDriver = UiOperations.ChromeLaunchUrl("https://www.makemytrip.com/flights/");
	}
	
	
	@AfterClass
	public void closeBrowser() {
		UiOperations.CloseAndQuitBrowser(oDriver);
	}
	
	@AfterSuite
	public void tearDownSuite() {
		report.endTest(test);
		report.flush();
	}
	
}
