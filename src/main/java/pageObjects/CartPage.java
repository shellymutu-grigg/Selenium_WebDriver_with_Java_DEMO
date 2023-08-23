package pageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import abstractComponents.AbstractComponents;

public class CartPage extends AbstractComponents {
	
	WebDriver webDriver;
		
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
	
	public void openCart() {
		webDriver.findElement(By.cssSelector(".a-button-text[href='/cart?ref_=sw_gtc']")).click();		
		try {
			waitForElementToAppear(subTotalBy);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public LogoutPage deleteCart() {
		try {
			waitForElementToAppear(cartBy);
			deleteItems.get(0).click();		
			waitForElementToAppear(cartHeaderBy);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
		LogoutPage logoutPage = new LogoutPage(webDriver);
		return logoutPage;
	}
}
