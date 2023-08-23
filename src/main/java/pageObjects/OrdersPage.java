package pageObjects;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import abstractComponents.AbstractComponents;
import data.TextData;

public class OrdersPage extends AbstractComponents {

	WebDriver webDriver;
	Actions actions;

	By accountLinkBy = By.xpath("//span[normalize-space()='Account & Lists']");
	By accountMenuBy = By.id("nav-al-your-account");
	By orderPageLinkBy = By.id("nav_prefetch_yourorders");
	By orderHeaderBy = By.xpath("//h1[normalize-space()='Your Orders']");
	By searchOrdersFieldBy = By.id("searchOrdersInput");
	By searchOrdersButtonBy = By.id("a-autoid-0");

	public OrdersPage(WebDriver webDriver) {
		super(webDriver);
		this.webDriver = webDriver;
		PageFactory.initElements(webDriver, this);
		actions = new Actions(webDriver);
	}

	public void openAccountMenu() {
		WebElement acountMenu = webDriver.findElement(accountLinkBy);
		actions.moveToElement(acountMenu).perform();
		try {
			waitForElementToAppear(accountMenuBy);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void openOrderPage(){
		WebElement ordersLink = webDriver.findElement(orderPageLinkBy);
		actions.moveToElement(ordersLink).perform();
		ordersLink.click();		
		try {
			waitForElementToAppear(orderHeaderBy);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public LogoutPage searchForOrders(){ 
		WebElement searchField = webDriver.findElement(searchOrdersFieldBy);
		WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));
		searchField.sendKeys(TextData.ORDER_SEARCH_TEXT);
		WebElement searchOrdersButton = wait.until(ExpectedConditions.elementToBeClickable(searchOrdersButtonBy));
		searchOrdersButton.click();
		
		WebElement order = webDriver.findElement(By.cssSelector("label[for='orderFilter']"));
		String orderFilterText = order.getText();
		
		Assert.assertEquals(orderFilterText, TextData.ORDER_FILTER_TEXT);
		LogoutPage logoutPage = new LogoutPage(webDriver);
		return logoutPage;
	}	
}