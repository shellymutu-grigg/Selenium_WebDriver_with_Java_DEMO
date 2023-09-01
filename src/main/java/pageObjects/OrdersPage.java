package pageObjects;

import data.ConfigData;
import data.LocalStore;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.MoveTargetOutOfBoundsException;
import org.testng.Assert;

import data.TextData;
import webElement.Element;

import java.text.MessageFormat;
import java.util.Objects;

public class OrdersPage {
	Actions actions;

	By accountMenuBy = By.id("nav-link-accountList");
	By orderFilter = By.cssSelector("label[for='orderFilter']");
	By orderPageLinkBy = By.id("nav_prefetch_yourorders");
	By searchOrdersFieldBy = By.id("searchOrdersInput");
	By searchOrdersButtonBy = By.id("a-autoid-0");

	public OrdersPage() {
		WebDriver webDriver = (WebDriver) LocalStore.getObject(ConfigData.SYSTEM_PROPERTY_WEBDRIVER);
		actions = new Actions(webDriver);
	}

	public void openAccountMenu() {
		try{
			actions.moveToElement(Element.getElement(accountMenuBy)).perform();
		}catch(MoveTargetOutOfBoundsException e){
			System.out.println(MessageFormat.format("MoveTargetOutOfBoundsException: {0}", e.getMessage()));
			throw new MoveTargetOutOfBoundsException(MessageFormat.format("MoveTargetOutOfBoundsException: {0}", e.getMessage()));
		}
	}
	
	public void openOrdersPage(){
		try{
			actions.moveToElement(Element.getElement(orderPageLinkBy)).perform();
			Element.click(orderPageLinkBy);
		}catch(MoveTargetOutOfBoundsException e){
			System.out.println(MessageFormat.format("MoveTargetOutOfBoundsException: {0}", e.getMessage()));
			throw new MoveTargetOutOfBoundsException(MessageFormat.format("MoveTargetOutOfBoundsException: {0}", e.getMessage()));
		}
	}
	
	public LogoutPage searchForOrders(){ 
		Element.sendKeys(searchOrdersFieldBy, TextData.ORDER_SEARCH_TEXT);
		Element.click(searchOrdersButtonBy);
		String orderFilterText = Objects.requireNonNull(Element.getElement(orderFilter)).getText();
		
		Assert.assertEquals(orderFilterText, TextData.ORDER_FILTER_TEXT);
		return new LogoutPage();
	}	
}