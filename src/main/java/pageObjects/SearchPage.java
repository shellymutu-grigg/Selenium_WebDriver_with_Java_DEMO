package pageObjects;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import abstractComponents.AbstractComponents;
import abstractComponents.HelperFunctions;
import data.PageTitleData;

public class SearchPage extends AbstractComponents {

	WebDriver webDriver;
	
	HelperFunctions helperFunctions = new HelperFunctions();

	// PageFactory Pattern
	@FindBy(id = "twotabsearchtextbox")
	WebElement searchField;

	@FindBy(id = "nav-search-submit-button")
	WebElement searchButton;

	@FindBy(id = "continue")
	WebElement continueButton;
	
	By departmentsBy = By.id("departments");

	public SearchPage(WebDriver webDriver) {
		super(webDriver);
		this.webDriver = webDriver;
		PageFactory.initElements(webDriver, this);
	}

	public ResultsPage searchForProducts(String product) throws InterruptedException, IOException {
		
		helperFunctions.validatePageTitle(PageTitleData.LOGGED_IN_LANDING_PAGE_TITLE, webDriver.getTitle());
		
		searchField.sendKeys(product);
		searchButton.click();
		
		waitForElementToAppear(departmentsBy);
		helperFunctions.validatePageTitle(PageTitleData.RESULTS_PAGE_TITLE, webDriver.getTitle());
		
		ResultsPage resultsPage = new ResultsPage(webDriver);
		return resultsPage;
	}

}