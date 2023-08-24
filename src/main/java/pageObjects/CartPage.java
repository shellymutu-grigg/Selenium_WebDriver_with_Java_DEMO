package pageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import functions.HelperFunctions;

public class CartPage extends HelperFunctions {
	
	WebDriver webDriver;
		
	@FindBy(css="input[value='Delete']")
	List<WebElement> deleteItems;
		
	By cartBy = By.cssSelector(".sc-gift-option");
	By cartHeaderBy = By.cssSelector(".sc-list-item-removed-msg");
	By subTotalBy = By.id("sc-subtotal-label-buybox");

	public CartPage(WebDriver webDriver) {
		super(webDriver);
		this.webDriver = webDriver;
		PageFactory.initElements(webDriver, this);
	}
	
	public void openCart() {
		webDriver.findElement(By.cssSelector(".a-button-text[href='/cart?ref_=sw_gtc']")).click();		
		waitForElementToAppear(subTotalBy, webDriver);
	}
	
	public LogoutPage deleteCart() {
		waitForElementToAppear(cartBy, webDriver);
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		waitForElementToAppear(cartBy, webDriver);
		deleteItems.get(0).click();	
		try {
			Thread.sleep(5);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		waitForElementToAppear(cartHeaderBy, webDriver);			
		LogoutPage logoutPage = new LogoutPage(webDriver);
		return logoutPage;
	}
}
