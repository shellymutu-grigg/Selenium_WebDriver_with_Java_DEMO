package pageObjects;

import org.openqa.selenium.By;

import webElement.Element;

import java.util.Objects;

public class CartPage {
	By cartLinkBy = By.cssSelector(".a-button-text[href='/cart?ref_=sw_gtc']");
	By deleteItemBy = By.cssSelector("input[value='Delete']");
	
	public void openCart() {
		Element.click(cartLinkBy);
	}
	
	public LogoutPage deleteCart() {
		// Always delete first item listed in cart
		Element.click(Objects.requireNonNull(Element.getElements(deleteItemBy)).get(0));
		return new LogoutPage();
	}
}
