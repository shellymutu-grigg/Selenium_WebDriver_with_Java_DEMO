package pageObjects;

import java.io.IOException;
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
	HelperFunctions helperFunctions = new HelperFunctions();
	
	// PageFactory Pattern
	@FindBy(css=".s-card-container")
	List<WebElement> products;
	
	@FindBy(css=".s-image")
	List<WebElement> productsImages;
	
	@FindBy(id = "add-to-cart-button")
	WebElement addToCartButton;
	
	String bookTitle;
	
	By productsBy = By.cssSelector(".s-card-container");
	By productTitleBy = By.id("productTitle");
	By resultsBy = By.id("a-autoid-0-announce");
	By resultsTitle = By.cssSelector(".s-result-item");
	By checkoutButtonBy = By.name("proceedToRetailCheckout");
	
	public ResultsPage(WebDriver webDriver) {
		super(webDriver);
		this.webDriver = webDriver;
		PageFactory.initElements(webDriver, this);
	}
	
	public List<WebElement> getProductList() throws InterruptedException, IOException{
		waitForElementToAppear(productsBy);
		helperFunctions.validatePageTitle("resultsTitle", webDriver.getTitle());

		return products;
	}
	
	public WebElement getProductByName(String productName, int index) throws Exception {
		WebElement product = products.stream()
				.filter(product_ -> 
					product_.getText().split("\n")[index].trim().contains(productName) 
				).findFirst()
				.orElse(null);

		setTitle(product);
		
		return product;
	}
	
	public void selectProduct(WebElement product) throws Exception{
		product.click();
		setTitle(product);
	}
	
	public void checkProductLink(WebElement productCheck) throws Exception {
		if(productCheck != null) {
			productCheck.click();
		} else {
			throw new Exception("Product not found");
		}
	}
	
	public void setTitle(WebElement product) throws Exception {
		int productIndex = products.indexOf(product);
		if(productIndex >=0) {
			WebElement productImage = productsImages.get(productIndex);		
			bookTitle = productImage.getAttribute("alt");
		} else {
			throw new Exception("Index of product is incorrect");
		}
	}
	
	public CartPage addProductToCart(String productName, int index) throws Exception {
		WebElement product = getProductByName(productName, index);
			
		waitForElementToAppear(resultsTitle);
		
		setTitle(product);
		
		checkProductLink(product.findElement(By.cssSelector("img[alt='"+ bookTitle +"']")));
		
		waitForElementToAppear(productTitleBy);
		webDriver.getTitle().contains(bookTitle);
		
		// Need to add a check around this
		addToCartButton.click();
		
		waitForElementToAppear(checkoutButtonBy);
		helperFunctions.validatePageTitle("cartTitle", webDriver.getTitle());
		
		CartPage cartPage = new CartPage(webDriver);
		return cartPage;
	}
}
