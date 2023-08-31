package pageObjects;

import org.openqa.selenium.By;

import webElement.Element;

public class SearchPage {

	By searchFieldBy = By.id("twotabsearchtextbox");
	By searchButtonBy = By.id("nav-search-submit-button");
	
	public ResultsPage searchForProducts(String product) {
		Element.getElement(searchFieldBy).sendKeys(product);
		Element.getElement(searchButtonBy).click();
		ResultsPage resultsPage = new ResultsPage();
		return resultsPage;
	}
}