package pageObjects;

import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.TestException;
import webElement.Element;

@Slf4j
public class ResultsPage {
	String bookTitle;

	By addToCartButtonBy = By.id("add-to-cart-button");
	By productsBy = By.cssSelector(".s-card-container");
	By productsImagesBy = By.cssSelector(".s-image");

	static Logger logger = LoggerFactory.getLogger(ResultsPage.class);
	
	public List<WebElement> getProductList(){
		return Element.getElements(productsBy);
	}
	
	public WebElement getProductByName(String productName, int index) {
		WebElement product = Objects.requireNonNull(Element.getElements(productsBy)).stream()
				.filter(product_ -> 
					product_.getText().split("\n")[index].trim().contains(productName) 
				).findFirst()
				.orElse(null);

		setTitle(product);
		return product;
	}
	
	public void clickProductLink(String productTitle, By product){
		if(product != null) {
			Element.click(product, false);
		} else {
			logger.error("{} product not found", productTitle);
			throw new TestException(MessageFormat.format("{0} product not found", productTitle));
		}
	}
	
	public CartPage addProductToCart(){
		Element.click(addToCartButtonBy, false);
		return new CartPage();
	}

	public String setProduct(String productName, int index){
		WebElement product = getProductByName(productName, index);
		setTitle(product);
		return bookTitle;
	}

	public void setTitle(WebElement product){
		int productIndex = Objects.requireNonNull(Element.getElements(productsBy)).indexOf(product);
		if(productIndex >=0) {
			WebElement productImage = Objects.requireNonNull(Element.getElements(productsImagesBy)).get(productIndex);
			bookTitle = productImage.getAttribute("alt");
		} else {
			logger.error("Index {} of product is incorrect", productIndex);
		}
	}
}
