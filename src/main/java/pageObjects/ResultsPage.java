package pageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import abstractComponents.AbstractComponents;
import abstractComponents.HelperFunctions;

public class ResultsPage extends AbstractComponents {

	WebDriver webDriver;
	
	// PageFactory Pattern
	@FindBy(css=".s-card-container")
	List<WebElement> products;
	
	@FindBy(id = "add-to-cart-button")
	WebElement addToCartButton;
	
	String bookTitle;
	
	By productsBy = By.cssSelector(".s-card-container");
	By productTitleBy = By.id("productTitle");
	
	public ResultsPage(WebDriver webDriver) {
		super(webDriver);
		this.webDriver = webDriver;
		PageFactory.initElements(webDriver, this);
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
	
	public String alternativeProductName(List<WebElement> products, int index) throws InterruptedException {
		webDriver.navigate().back();
		int alternateIndex = 1;
		
		String randomProductTitle = products.get(alternateIndex).getText().split("\n")[0];

		HelperFunctions testFunctions = new HelperFunctions();
		int alternateTitleIndex = testFunctions.determineIndex(randomProductTitle);
		randomProductTitle = products.get(alternateIndex).getText().split("\n")[alternateTitleIndex];
		products.get(alternateIndex).findElement(By.cssSelector("img[alt='"+ randomProductTitle +"']")).click();
		
		waitForElementToAppear(productTitleBy);
		
		return randomProductTitle;
	}
	
	public boolean verifyIfProductAvailable(String notAvailable, int index, List<WebElement> products) throws InterruptedException {
		if(notAvailable != null) {
			alternativeProductName(products, index);
		}
		WebElement addToCart = webDriver.findElement(By.id( "add-to-cart-button"));
		return addToCart == null ? false : true;
	}
	
	public CartPage addProductToCart(String productName, int index) throws InterruptedException {
		WebElement product = getProductByName(productName, index);
		product.findElement(By.cssSelector("img[alt='"+ bookTitle +"']")).click();
		
		waitForElementToAppear(productTitleBy);
			
		String notAvailable = webDriver.findElement(By.xpath("//span[normalize-space()='Have you moved recently?']")).getText();

		if(verifyIfProductAvailable(notAvailable, index, products)) {
			addToCartButton.click();
		}
		
		CartPage cartPage = new CartPage(webDriver);
		return cartPage;
	}
}
