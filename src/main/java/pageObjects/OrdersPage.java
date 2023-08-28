package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.MoveTargetOutOfBoundsException;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import data.TextData;
import functions.HelperFunctions;
import webElement.FindElement;

public class OrdersPage extends HelperFunctions {

	WebDriver webDriver;
	Actions actions;

	By accountLinkBy = By.xpath("//span[normalize-space()='Account & Lists']");
	By accountMenuBy = By.id("nav-link-accountList");
	By orderFilter = By.cssSelector("label[for='orderFilter']");
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
		try{
			FindElement.getWebElement(accountMenuBy, webDriver);
			actions.moveToElement(FindElement.getWebElement(accountMenuBy, webDriver)).perform();
		}catch(MoveTargetOutOfBoundsException e){
			e.getMessage();
		}
	}
	
	public void openOrdersPage(){
		try{
			actions.moveToElement(FindElement.getWebElement(orderPageLinkBy, webDriver)).perform();
			FindElement.getWebElement(orderPageLinkBy, webDriver).click();
		}catch(MoveTargetOutOfBoundsException e){
			e.getMessage();
		}
	}
	
	public LogoutPage searchForOrders(){ 
		FindElement.getWebElement(searchOrdersFieldBy, webDriver).sendKeys(TextData.ORDER_SEARCH_TEXT);
		FindElement.getWebElement(searchOrdersButtonBy, webDriver).click();
		String orderFilterText = FindElement.getWebElement(orderFilter, webDriver).getText();
		
		Assert.assertEquals(orderFilterText, TextData.ORDER_FILTER_TEXT);
		LogoutPage logoutPage = new LogoutPage(webDriver);
		return logoutPage;
	}	
}