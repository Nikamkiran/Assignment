package com.makemytrip.assignment;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.testng.annotations.Test;

import com.makemytrip.pages.SearchPage;
import com.makemytrip.pages.SearchResults;
import com.makemytrip.utilities.UiOperations;
import com.relevantcodes.extentreports.LogStatus;

public class Test_Case_1 extends Base_NG{
	
	@Test	
	public static void test() throws InterruptedException, EncryptedDocumentException, InvalidFormatException, IOException {
		
		SearchPage.oDriver = oDriver;
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd_MM_yyyy_HH_mm_ss");
		
		String login_popup_visible = SearchPage.handleLoginPopup();
		
		if(login_popup_visible == "True"){
			test.log(LogStatus.PASS, "Login popup was displayed and handled.");	
		}else {
			test.log(LogStatus.PASS, "Login popup was not displayed.");
		}
		
		SearchPage.selectFromCity();
		test.log(LogStatus.PASS, "From Section populated.");
		
		SearchPage.selectToCity();
		test.log(LogStatus.PASS, "To Section populated.");
		
		SearchPage.setDepartureDetails();
		test.log(LogStatus.PASS, "Departure date populated.");
		
		test.log(LogStatus.INFO, "Search Screen - " + test.addScreenCapture(UiOperations.capture(oDriver, LocalDateTime.now().format(dtf))));
		
		SearchPage.submitSearch();
		
		SearchResults.oDriver = oDriver;
		
		String advetisement_visible = SearchResults.handleAdvertisement();
		
		if(advetisement_visible == "True"){
			test.log(LogStatus.PASS, "Advertisement popup was displayed and handled.");	
		}else {
			test.log(LogStatus.PASS, "Advertisement popup was not displayed.");
		}
		
		test.log(LogStatus.INFO, "Search Results - " + test.addScreenCapture(UiOperations.capture(oDriver, LocalDateTime.now().format(dtf))));
		
		SearchResults.waitTillResultsAreLoaded();
		
		String number_of_flights = SearchResults.fetchDetails();
		
		test.log(LogStatus.INFO, "<a href='" + number_of_flights +"'> Click Here to open the output excel</a>");
	}
	
}
