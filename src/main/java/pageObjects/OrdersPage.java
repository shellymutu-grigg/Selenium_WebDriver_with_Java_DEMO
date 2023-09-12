package pageObjects;

import data.ConfigData;
import data.LocalStore;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.MoveTargetOutOfBoundsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import data.TextData;
import webElement.Element;

import java.text.MessageFormat;
import java.util.Objects;

@Slf4j
public class OrdersPage {
	Actions actions;

	By accountMenuBy = By.id("nav-link-accountList");
	By orderFilter = By.cssSelector("label[for='orderFilter']");
	By orderPageLinkBy = By.id("nav_prefetch_yourorders");
	By searchOrdersFieldBy = By.id("searchOrdersInput");
	By searchOrdersButtonBy = By.id("a-autoid-0");

	static Logger logger = LoggerFactory.getLogger(OrdersPage.class);

	public OrdersPage() {
		WebDriver webDriver = (WebDriver) LocalStore.getObject(String.valueOf(Thread.currentThread().getId()));
		actions = new Actions(webDriver);
	}

	public void openAccountMenu() {
		try{
			actions.moveToElement(Element.getElement(accountMenuBy)).perform();
		}catch(MoveTargetOutOfBoundsException e){
			logger.error("MoveTargetOutOfBoundsException: {}", e.getMessage());
			throw new MoveTargetOutOfBoundsException(MessageFormat.format("MoveTargetOutOfBoundsException: {0}", e.getMessage()));
		}
	}
	
	public void openOrdersPage(){
		try{
			actions.moveToElement(Element.getElement(orderPageLinkBy)).perform();
			Element.click(orderPageLinkBy, false);
		}catch(MoveTargetOutOfBoundsException e){
			logger.error("MoveTargetOutOfBoundsException: {}", e.getMessage());
			throw new MoveTargetOutOfBoundsException(MessageFormat.format("MoveTargetOutOfBoundsException: {0}", e.getMessage()));
		}
	}
	
	public LogoutPage searchForOrders(){ 
		Element.sendKeys(searchOrdersFieldBy, TextData.ORDER_SEARCH_TEXT);
		Element.click(searchOrdersButtonBy, false);
		String orderFilterText = Objects.requireNonNull(Element.getElement(orderFilter)).getText();
		
		Assert.assertEquals(orderFilterText, TextData.ORDER_FILTER_TEXT);
		return new LogoutPage();
	}	
}