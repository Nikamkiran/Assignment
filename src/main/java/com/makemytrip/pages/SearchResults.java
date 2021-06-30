package com.makemytrip.pages;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.makemytrip.utilities.ExcelOperations;
import com.makemytrip.utilities.UiOperations;

public class SearchResults {

	public static WebDriver oDriver;

	private static By advertisementPopup = By.xpath("//button[text()='OKAY, GOT IT!']");
	private static By resultBar = By.xpath("//div[@class='listingCard']");


	private static String airline = "(//span[contains(@class,'airlineName')])";
	private static String departure = "(//div[contains(@class,'timingOption')]/label/div/div/div[@class='flightTimeSection'][1]/p[1])";
	private static String arrival = "(//div[contains(@class,'timingOption')]/label/div/div/div[@class='flightTimeSection'][2]/p[1])";
	private static String pricing = "(//div[@class='priceSection']/div/div/p)";
	private static String totalDuration = "(//div[@class='flightTimeSection']/following-sibling::div[@class='appendRight40']/p)";


	public static String handleAdvertisement() throws InterruptedException {
		
		int count = 1;

		while (count < 10) {
			try {
				oDriver.findElement(advertisementPopup).click();

			}
			catch(Exception e){
				Thread.sleep(1000);
				count ++;
			}
		}

		if(count==10) {
			return "False";
		}
		else {
			return "True";
		}
	}

	
	public static void waitTillResultsAreLoaded() throws InterruptedException {
		
		UiOperations.ClickWithRetry(oDriver, resultBar);

		oDriver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL, Keys.END); 

		Thread.sleep(1500);
	}
	
	
	public static String fetchDetails() throws IOException, InterruptedException, EncryptedDocumentException, InvalidFormatException {
		List<WebElement> myElements = oDriver.findElements(resultBar);

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd_MM_yyyy_HH_mm_ss");
		LocalDateTime now = LocalDateTime.now();

		String airlineName = "";
		String start_time = "";
		String end_time = "";
		String price = "";
		String duration = "";
		String path = "output_"+now.format(dtf)+".xlsx";
		String[] details = new String[5];

		ExcelOperations.create(path);

		for (int j=1;j<=myElements.size();j++) {

			airlineName = UiOperations.GetTextWithRetry(oDriver, By.xpath(airline + "[" + j + "]"));
			start_time = UiOperations.GetTextWithRetry(oDriver, By.xpath(departure + "["+ j +"]"));
			end_time = UiOperations.GetTextWithRetry(oDriver, By.xpath(arrival + "["+ j +"]"));

			start_time = start_time.replace("\n", "").replace("+ 1 DAY", " (Next Day)");
			end_time = end_time.replace("\n", "").replace("+ 1 DAY", " (Next Day)");

			price = UiOperations.GetTextWithRetry(oDriver, By.xpath(pricing + "["+ j +"]"));

			duration = UiOperations.GetTextWithRetry(oDriver, By.xpath(totalDuration + "["+j+"]"));

			details[0] = airlineName;
			details[1] = start_time;
			details[2] = end_time;
			details[3] = price;
			details[4] = duration;

			ExcelOperations.write(path, details);

		}
		
		return path;
		
	}


}
