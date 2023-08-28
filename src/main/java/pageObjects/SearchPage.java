package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import functions.HelperFunctions;
import webElement.FindElement;

public class SearchPage extends HelperFunctions {

	WebDriver webDriver;
	
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
	
	public ResultsPage searchForProducts(String product) {
		searchField.sendKeys(product);
		searchButton.click();
		FindElement.getWebElement(departmentsBy, webDriver);
		ResultsPage resultsPage = new ResultsPage(webDriver);
		return resultsPage;
	}
}