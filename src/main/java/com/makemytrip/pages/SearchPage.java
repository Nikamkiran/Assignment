package com.makemytrip.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.makemytrip.utilities.UiOperations;

public class SearchPage {

	public static WebDriver oDriver;
	private static By login_pop_up = By.xpath("//div[@data-cy='outsideModal']");

	private static By fromCity = By.id("fromCity");
	private static By fromCityInput = By.xpath("//input[@placeholder='From']");
	private static By fromCityDropDown = By.xpath("//p[contains(text(),'Hyderabad')]/../../..");

	private static By toCity = By.id("toCity");
	private static By toCityIntput = By.xpath("//input[@placeholder='To']");
	private static By toCityDropDown = By.xpath("(//p[contains(text(),'Mumbai')]/../../..)[1]");

	private static By departureCalendarToggle = By.xpath("//span[text()='DEPARTURE']");
	private static By departureDate = By.xpath("//div[@aria-label='Thu Jul 15 2021']");

	private static By Submit = By.xpath("//p[@data-cy='submit']/a");


	public static String handleLoginPopup() {
		try {
			Thread.sleep(2000);
			oDriver.findElement(login_pop_up).click();
			return "True";
		}
		catch(Exception e){
			return "False";
		}
	}
	
	public static void selectFromCity() throws InterruptedException {
		
		UiOperations.ClickWithRetry(oDriver, fromCity);
		UiOperations.SendKeysWithRetry(oDriver, fromCityInput, "Hyderabad");
		Thread.sleep(1500);
		UiOperations.ClickWithRetry(oDriver, fromCityDropDown);
	}

	public static void selectToCity() throws InterruptedException {
		
		UiOperations.ClickWithRetry(oDriver, toCity);
		UiOperations.SendKeysWithRetry(oDriver, toCityIntput, "Mumbai");
		Thread.sleep(1500);
		UiOperations.ClickWithRetry(oDriver,toCityDropDown);
	}
	
	public static void setDepartureDetails() throws InterruptedException {
		
		UiOperations.ClickWithRetry(oDriver, departureCalendarToggle);
		UiOperations.ClickWithRetry(oDriver, departureDate);
	}
	
	public static void submitSearch() throws InterruptedException {
		
		UiOperations.ClickWithRetry(oDriver, Submit);
	}

}
