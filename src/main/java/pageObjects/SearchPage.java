package pageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import abstractComponents.AbstractComponents;

public class SearchPage extends AbstractComponents {

	WebDriver webDriver;

	// PageFactory Pattern
	@FindBy(id = "twotabsearchtextbox")
	WebElement searchField;

	@FindBy(id = "nav-search-submit-button")
	WebElement searchButton;

	@FindBy(id = "continue")
	WebElement continueButton;

	@FindBy(css=".sg-row")
	List<WebElement> products;
	
	By productsBy = By.cssSelector(".sg-row");

	public SearchPage(WebDriver webDriver) {
		super(webDriver);
		this.webDriver = webDriver;
		PageFactory.initElements(webDriver, this);
	}

	public void searchForProducts(String product) throws InterruptedException {
		searchField.sendKeys(product);
		searchButton.click();
		
		waitForElementToAppear(By.id("p_n_is_free_shipping-title"));
	}

	public List<WebElement> getProductList() throws InterruptedException{
		waitForElementToAppear(productsBy);
		return products;
	}

}