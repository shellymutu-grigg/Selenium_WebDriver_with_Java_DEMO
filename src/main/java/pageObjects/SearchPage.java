package pageObjects;

import java.text.MessageFormat;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import abstractComponents.AbstractComponents;
import abstractComponents.TestFunctions;

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
	
	public WebElement getProductByName(String productName, int index) {
		WebElement finalProduct = products.stream()
				.filter(product -> 
					product.getText().split("\n")[index].trim().contains(productName) 
				).findFirst()
				.orElse(null);
		bookTitle = finalProduct.getText().split("\n")[index];
		return finalProduct;
	}
	
	public WebElement selectRandomProduct(int resultIndex, int index) {
		WebElement finalProduct = products.get(resultIndex);
		bookTitle = finalProduct.getText().split("\n")[index];
		if(bookTitle.contains("by")) {
			bookTitle = finalProduct.getText().split("\n")[0];
		}
		return finalProduct;
	}
	

	public void addProductToCart(String productName, int index) throws InterruptedException {
		WebElement product = getProductByName(productName, index);
		product.findElement(By.cssSelector("img[alt='"+ bookTitle +"']")).click();
		
		waitForElementToAppear(productTitleBy);
			
		String notAvailable = webDriver.findElement(By.xpath("//span[normalize-space()='Have you moved recently?']")).getText();

		if(verifyIfTitleAvailable(notAvailable, index, products)) {
			addToCartButton.click();
		}			
	}
	public boolean verifyIfTitleAvailable(String notAvailable, int index, List<WebElement> products) throws InterruptedException {
		if(notAvailable != null) {
			selectAlternativeProduct(products, index);
		}
		WebElement addToCart = webDriver.findElement(By.id( "add-to-cart-button"));
		return addToCart == null ? false : true;
	}
	
	public String selectAlternativeProduct(List<WebElement> products, int index) throws InterruptedException {
		webDriver.navigate().back();
		int alternateIndex = 1;
		
		String randomProductTitle = products.get(alternateIndex).getText().split("\n")[0];

		TestFunctions testFunctions = new TestFunctions();
		int alternateTitleIndex = testFunctions.determineIndex(randomProductTitle);
		randomProductTitle = products.get(alternateIndex).getText().split("\n")[alternateTitleIndex];
		products.get(alternateIndex).findElement(By.cssSelector("img[alt='"+ randomProductTitle +"']")).click();
		
		waitForElementToAppear(productTitleBy);
		
		return randomProductTitle;
	}

}