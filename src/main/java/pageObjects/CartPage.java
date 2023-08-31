package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import webElement.Element;

public class CartPage {
	
	WebDriver webDriver;

	By cartBy = By.cssSelector(".sc-gift-option");
	By cartLinkBy = By.cssSelector(".a-button-text[href='/cart?ref_=sw_gtc']");
	By deleteItemBy = By.cssSelector("input[value='Delete']");

	public CartPage(WebDriver webDriver) {
		this.webDriver = webDriver;
	}
	
	public void openCart() {
		Element.getElement(cartLinkBy, webDriver).click();
	}
	
	public LogoutPage deleteCart() {
		Element.getElement(cartBy, webDriver);
		Element.getElements(deleteItemBy, webDriver).get(0).click();
		LogoutPage logoutPage = new LogoutPage(webDriver);
		return logoutPage;
	}
}
