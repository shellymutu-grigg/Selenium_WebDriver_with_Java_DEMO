package pageObjects;

import java.text.MessageFormat;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import functions.HelperFunctions;

public class ResultsPage extends HelperFunctions {

	WebDriver webDriver;
	
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
	
	public List<WebElement> getProductList(){
		waitForElementToAppear(productsBy, webDriver);
		return products;
	}
	
	public WebElement getProductByName(String productName, int index) {
		WebElement product = products.stream()
				.filter(product_ -> 
					product_.getText().split("\n")[index].trim().contains(productName) 
				).findFirst()
				.orElse(null);

		try {
			setTitle(product);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return product;
	}
	
	public void selectProduct(WebElement product){
		product.click();
		try {
			setTitle(product);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void checkProductLink(WebElement productCheck){
		if(productCheck != null) {
			productCheck.click();
		} else {
			System.out.println(MessageFormat.format("{0} product not found", productCheck));
		}
	}
	
	public void setTitle(WebElement product){
		int productIndex = products.indexOf(product);
		if(productIndex >=0) {
			WebElement productImage = productsImages.get(productIndex);		
			bookTitle = productImage.getAttribute("alt");
		} else {
			System.out.println(MessageFormat.format("Index {0} of product is incorrect", productIndex));
		}
	}
	
	public CartPage addProductToCart(String productName, int index){
		WebElement product = getProductByName(productName, index);	
		waitForElementToAppear(resultsTitle, webDriver);
		setTitle(product);
		checkProductLink(product.findElement(By.cssSelector("img[alt='"+ bookTitle +"']")));
		waitForElementToAppear(productTitleBy, webDriver);
		webDriver.getTitle().contains(bookTitle);
		if(!isElementPresent(By.id("add-to-cart-button"))) {
			System.out.println(MessageFormat.format("Book {0} is not available for purchase in your area", productName));
		}
		addToCartButton.click();
		waitForElementToAppear(checkoutButtonBy, webDriver);
		CartPage cartPage = new CartPage(webDriver);
		return cartPage;
	}
	
	public boolean isElementPresent(By by) {
	  boolean exists = false;
	  List<WebElement> list = webDriver.findElements(by);
	  if(!list.isEmpty()) {
	      exists = true;
	  }
	  return exists;
	}
}
