package com.makemytrip.utilities;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class UiOperations {
	
	static WebDriver oDriver = null;
	static int number_of_retires = 3;
	
	public static WebDriver ChromeLaunchUrl(String url) {

		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"\\DriverExe\\chromedriver.exe");
		
		ChromeOptions options = new ChromeOptions();
		
		options.addArguments("--no-sandbox"); 
		options.addArguments("--disable-infobars"); 
		options.addArguments("--disable-dev-shm-usage"); 
		options.addArguments("--disable-browser-side-navigation"); 
		options.addArguments("--disable-gpu");

		oDriver = new ChromeDriver(options);
		oDriver.manage().window().maximize();
		oDriver.get(url);
		
		return oDriver;

	}

	public static void WaitAndClick(WebDriver oDriver,By locator,int WaitTime) throws InterruptedException {

		WebDriverWait oWait = new WebDriverWait(oDriver, WaitTime);
		oWait.until(ExpectedConditions.elementToBeClickable(locator)).click();

	}

	public static String WaitAndGetText(WebDriver oDriver,By locator,int WaitTime) throws InterruptedException {

		WebDriverWait oWait = new WebDriverWait(oDriver, WaitTime);
		return oWait.until(ExpectedConditions.elementToBeClickable(locator)).getText();

	}

	public static void WaitAndSendKeys(WebDriver oDriver,By locator,String value, int WaitTime) throws InterruptedException {

		WebDriverWait oWait = new WebDriverWait(oDriver, WaitTime);
		oWait.until(ExpectedConditions.elementToBeClickable(locator)).sendKeys(value);

	}

	public static void ClickWithRetry(WebDriver oDriver,By locator) throws InterruptedException {

		int count = 0;
		while(count!=number_of_retires) {
			try {
				WaitAndClick(oDriver,locator,90);
				break;
			}catch(TimeoutException r) {
				System.err.println("Element located by " + locator + " was not found in 90 seconds");
				System.err.println("Stacktrace - ");
				System.err.println(r.toString());
			}
			catch(Exception e) {
				Thread.sleep(1000);
				count++;
			}
		}
	}

	public static void SendKeysWithRetry(WebDriver oDriver,By locator,String value) throws InterruptedException {

		int count = 0;
		while(count!=number_of_retires) {
			try {
				WaitAndSendKeys(oDriver,locator,value,90);
				break;
			}catch(TimeoutException r) {
				System.err.println("Element located by " + locator + " was not found in 90 seconds");
				System.err.println("Stacktrace - ");
				System.err.println(r.toString());
				break;
			}
			catch(Exception e) {
				Thread.sleep(1000);
				count++;
			}
		}

	}

	public static String GetTextWithRetry(WebDriver oDriver,By locator) throws InterruptedException {

		int count = 0;
		String result = "";
		while(count!=number_of_retires) {
			try {
				result = WaitAndGetText(oDriver,locator,90);
				return result;
			}catch(TimeoutException r) {
				System.err.println("Element located by " + locator + " was not found in 90 seconds");
				System.err.println("Stacktrace - ");
				System.err.println(r.toString());
				return null;
			}
			catch(Exception e) {
				Thread.sleep(1000);
				count++;
			}
		}
		return null;

	}
	
	public static void CloseAndQuitBrowser(WebDriver oDriver) {
		oDriver.close();
		oDriver.quit();
	}

	public static String capture(WebDriver oDriver,String screenShotName) throws IOException {
        TakesScreenshot ts = (TakesScreenshot)oDriver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        String dest = System.getProperty("user.dir") +"/screenshots/"+screenShotName+".png";
        File destination = new File(dest);
        FileUtils.copyFile(source, destination);        
                     
        return dest;
    }

}
