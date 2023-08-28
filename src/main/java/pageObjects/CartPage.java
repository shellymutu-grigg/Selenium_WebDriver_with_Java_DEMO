package pageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import functions.HelperFunctions;
import webElement.FindElement;

public class CartPage extends HelperFunctions {
	
	WebDriver webDriver;
		
	@FindBy(css="input[value='Delete']")
	List<WebElement> deleteItems;
		
	By cartBy = By.cssSelector(".sc-gift-option");
	By cartHeaderBy = By.cssSelector(".sc-list-item-removed-msg");
	By cartLinkBy = By.cssSelector(".a-button-text[href='/cart?ref_=sw_gtc']");
	By subTotalBy = By.id("sc-subtotal-label-buybox");

	public CartPage(WebDriver webDriver) {
		super(webDriver);
		this.webDriver = webDriver;
		PageFactory.initElements(webDriver, this);
	}
	
	public void openCart() {
		FindElement.getWebElement(cartLinkBy, webDriver).click();
		FindElement.getWebElement(subTotalBy, webDriver);
	}
	
	public LogoutPage deleteCart() {
		FindElement.getWebElement(cartBy, webDriver);
		FindElement.getWebElement(cartBy, webDriver);
		deleteItems.get(0).click();
		FindElement.getWebElement(cartHeaderBy, webDriver);
		LogoutPage logoutPage = new LogoutPage(webDriver);
		return logoutPage;
	}
}
