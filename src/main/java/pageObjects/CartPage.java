package pageObjects;

import java.text.MessageFormat;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import abstractComponents.AbstractComponents;

public class CartPage extends AbstractComponents {
	
	WebDriver webDriver;
	
	// PageFactory Pattern
	@FindBy(css="input[value='Delete']")
	List<WebElement> deleteItems;
		
	By cartBy = By.id("sc-active-cart");

	public CartPage(WebDriver webDriver) {
		super(webDriver);
		this.webDriver = webDriver;
		PageFactory.initElements(webDriver, this);
	}
	
	public void openCart() {
		webDriver.findElement(By.cssSelector(".a-button-text[href='/cart?ref_=sw_gtc']")).click();
	}
	
	public LogoutPage deleteCart() throws InterruptedException {
		waitForElementToAppear(cartBy);
		System.out.println(MessageFormat.format("deleteItems.size(): {0}", deleteItems.size()));
		deleteItems.get(0).click();
		
		LogoutPage logoutPage = new LogoutPage(webDriver);
		return logoutPage;
	}
}
