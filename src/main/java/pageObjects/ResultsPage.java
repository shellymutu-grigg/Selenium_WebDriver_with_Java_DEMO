package pageObjects;

import java.text.MessageFormat;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import org.testng.TestException;
import webElement.Element;

public class ResultsPage {
	String bookTitle;

	By addToCartButtonBy = By.id("add-to-cart-button");
	By productsBy = By.cssSelector(".s-card-container");
	By productsImagesBy = By.cssSelector(".s-image");
	
	public List<WebElement> getProductList(){
		return Element.getElements(productsBy);
	}
	
	public WebElement getProductByName(String productName, int index) {
		WebElement product = Element.getElements(productsBy).stream()
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
	
	public void clickProductLink(String productTitle, WebElement productCheck){
		if(productCheck != null) {
			productCheck.click();
		} else {
			System.out.println(MessageFormat.format("{0} product not found", productTitle));
			throw new TestException(MessageFormat.format("{0} product not found", productTitle));
		}
	}
	
	public CartPage addProductToCart(){
		Element.getElement(addToCartButtonBy).click();
		CartPage cartPage = new CartPage();
		return cartPage;
	}

	public String setProduct(String productName, int index){
		WebElement product = getProductByName(productName, index);
		setTitle(product);
		return bookTitle;
	}

	public void setTitle(WebElement product){
		int productIndex = Element.getElements(productsBy).indexOf(product);
		if(productIndex >=0) {
			WebElement productImage = Element.getElements(productsImagesBy).get(productIndex);
			bookTitle = productImage.getAttribute("alt");
		} else {
			System.out.println(MessageFormat.format("Index {0} of product is incorrect", productIndex));
		}
	}
}
