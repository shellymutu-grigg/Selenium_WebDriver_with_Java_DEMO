package pageObjects;

import java.lang.reflect.Array;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

	@FindBy(css=".s-card-container")
	List<WebElement> products;
	
	@FindBy(id = "add-to-cart-button")
	WebElement addToCartButton;
	
	String bookTitle;
	
	By productsBy = By.cssSelector(".s-card-container");
	By productTitleBy = By.id("productTitle");

	public SearchPage(WebDriver webDriver) {
		super(webDriver);
		this.webDriver = webDriver;
		PageFactory.initElements(webDriver, this);
	}

	public void searchForProducts(String product) throws InterruptedException {
		searchField.sendKeys(product);
		searchButton.click();
		
		waitForElementToAppear(By.id("departments"));
	}

	public List<WebElement> getProductList() throws InterruptedException{
		waitForElementToAppear(productsBy);
		return products;
	}
	
	public WebElement getProductByName(String productName) {
		WebElement finalProduct = products.stream()
				.filter(product -> product.getText().split("\n")[0].trim().equals(productName)).findFirst()
				.orElse(null);
		bookTitle = finalProduct.getText().split("\n")[0];
		return finalProduct;
	}
	
	public WebElement selectRandomProduct(Integer index) {
		WebElement finalProduct = products.get(index);
		bookTitle = finalProduct.getText().split("\n")[0];
		return finalProduct;
	}

	public void addProductToCart(String productName) throws InterruptedException {
		WebElement product = getProductByName(productName);
		product.findElement(By.cssSelector("img[alt='"+ bookTitle +"']")).click();
		
		waitForElementToAppear(productTitleBy);
		
		String notAvailable = webDriver.findElement(By.xpath("//span[normalize-space()='Have you moved recently?']")).getText();
		if(notAvailable == null) {
			addToCartButton.click();
		}
		else {
			webDriver.navigate().back();
			Random r = new Random();
			int low = 1;
			int high = 8;
			int result = r.nextInt(high-low) + low;
			selectRandomProduct(result);
		}
		
	}

}