package pageObjects;

import data.ConfigData;
import data.LocalStore;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import webElement.Element;

public class CartPage {
	By cartBy = By.cssSelector(".sc-gift-option");
	By cartLinkBy = By.cssSelector(".a-button-text[href='/cart?ref_=sw_gtc']");
	By deleteItemBy = By.cssSelector("input[value='Delete']");
	
	public void openCart() {
		Element.getElement(cartLinkBy).click();
	}
	
	public LogoutPage deleteCart() {
		Element.getElement(cartBy);
		Element.getElements(deleteItemBy).get(0).click();
		LogoutPage logoutPage = new LogoutPage();
		return logoutPage;
	}
}
