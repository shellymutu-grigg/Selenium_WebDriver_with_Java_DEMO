package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import webElement.FindElement;

public class CartPage {
	
	WebDriver webDriver;

	By cartBy = By.cssSelector(".sc-gift-option");
	By cartHeaderBy = By.cssSelector(".sc-list-item-removed-msg");
	By cartLinkBy = By.cssSelector(".a-button-text[href='/cart?ref_=sw_gtc']");
	By deleteItemBy = By.cssSelector("input[value='Delete']");

	public CartPage(WebDriver webDriver) {
		this.webDriver = webDriver;
	}
	
	public void openCart() {
		FindElement.getWebElement(cartLinkBy, webDriver).click();
	}
	
	public LogoutPage deleteCart() {
		FindElement.getWebElement(cartBy, webDriver);
		FindElement.getWebElements(deleteItemBy, webDriver).get(0).click();
		LogoutPage logoutPage = new LogoutPage(webDriver);
		return logoutPage;
	}
}
