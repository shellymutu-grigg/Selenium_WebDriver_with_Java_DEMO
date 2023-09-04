package pageObjects;

import org.openqa.selenium.By;

import webElement.Element;

public class SearchPage {

	By searchFieldBy = By.id("twotabsearchtextbox");
	By searchButtonBy = By.id("nav-search-submit-button");
	
	public ResultsPage searchForProducts(String product) {
		Element.sendKeys(searchFieldBy, product);
		Element.click(searchButtonBy, false);
		return new ResultsPage();
	}
}