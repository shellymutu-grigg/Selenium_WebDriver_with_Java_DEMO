package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.MoveTargetOutOfBoundsException;
import org.testng.Assert;

import data.TextData;
import webElement.Element;

public class OrdersPage {

	WebDriver webDriver;
	Actions actions;

	By accountMenuBy = By.id("nav-link-accountList");
	By orderFilter = By.cssSelector("label[for='orderFilter']");
	By orderPageLinkBy = By.id("nav_prefetch_yourorders");
	By searchOrdersFieldBy = By.id("searchOrdersInput");
	By searchOrdersButtonBy = By.id("a-autoid-0");

	public OrdersPage(WebDriver webDriver) {
		this.webDriver = webDriver;
		actions = new Actions(webDriver);
	}

	public void openAccountMenu() {
		try{
			actions.moveToElement(Element.getElement(accountMenuBy, webDriver)).perform();
		}catch(MoveTargetOutOfBoundsException e){
			e.getMessage();
		}
	}
	
	public void openOrdersPage(){
		try{
			actions.moveToElement(Element.getElement(orderPageLinkBy, webDriver)).perform();
			Element.getElement(orderPageLinkBy, webDriver).click();
		}catch(MoveTargetOutOfBoundsException e){
			e.getMessage();
		}
	}
	
	public LogoutPage searchForOrders(){ 
		Element.getElement(searchOrdersFieldBy, webDriver).sendKeys(TextData.ORDER_SEARCH_TEXT);
		Element.getElement(searchOrdersButtonBy, webDriver).click();
		String orderFilterText = Element.getElement(orderFilter, webDriver).getText();
		
		Assert.assertEquals(orderFilterText, TextData.ORDER_FILTER_TEXT);
		LogoutPage logoutPage = new LogoutPage(webDriver);
		return logoutPage;
	}	
}