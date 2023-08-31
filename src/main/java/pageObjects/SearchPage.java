package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import webElement.Element;

public class SearchPage {

	WebDriver webDriver;

	By searchFieldBy = By.id("twotabsearchtextbox");
	By searchButtonBy = By.id("nav-search-submit-button");

	public SearchPage(WebDriver webDriver) {
		this.webDriver = webDriver;
	}
	
	public ResultsPage searchForProducts(String product) {
		Element.getElement(searchFieldBy, webDriver).sendKeys(product);
		Element.getElement(searchButtonBy, webDriver).click();
		ResultsPage resultsPage = new ResultsPage(webDriver);
		return resultsPage;
	}
}