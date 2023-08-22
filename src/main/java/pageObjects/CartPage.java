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
import data.PageTitleData;

public class CartPage extends AbstractComponents {
	
	WebDriver webDriver;
	
	HelperFunctions helperFunctions = new HelperFunctions();
	
	// PageFactory Pattern
	@FindBy(css="input[value='Delete']")
	List<WebElement> deleteItems;
		
	By cartBy = By.id("sc-active-cart");
	By cartHeaderBy = By.cssSelector(".sc-cart-header");
	By subTotalBy = By.id("sc-subtotal-label-buybox");

	public CartPage(WebDriver webDriver) {
		super(webDriver);
		this.webDriver = webDriver;
		PageFactory.initElements(webDriver, this);
	}
	
	public void openCart() throws InterruptedException, IOException {
		webDriver.findElement(By.cssSelector(".a-button-text[href='/cart?ref_=sw_gtc']")).click();
		
		waitForElementToAppear(subTotalBy);
		helperFunctions.validatePageTitle(PageTitleData.CART_PAGE_TITLE, webDriver.getTitle());
	}
	
	public LogoutPage deleteCart() throws InterruptedException, IOException {
		waitForElementToAppear(cartBy);
		
		deleteItems.get(0).click();
		
		waitForElementToAppear(cartHeaderBy);
		helperFunctions.validatePageTitle(PageTitleData.CART_PAGE_TITLE, webDriver.getTitle());
		
		LogoutPage logoutPage = new LogoutPage(webDriver);
		return logoutPage;
	}
}
