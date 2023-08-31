package pageObjects;

import data.ConfigData;
import data.LocalStore;
import data.TextData;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.MoveTargetOutOfBoundsException;

import webElement.Element;

public class LogoutPage {
	Actions actions;
	
	By accountLinkBy = By.xpath("//*[contains(text(), '" + TextData.ACCOUNT_MENU_LINK_TEXT + "')]");
	By logoutLinkBy = By.id("nav-item-signout");
	
	public void openAccountMenu() {
		setActions();
		try{
	        actions.moveToElement(Element.getElement(accountLinkBy)).perform();
	    }catch(MoveTargetOutOfBoundsException e){
	        e.getMessage();
	    }
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void logout() {
		setActions();
		try{
			actions.moveToElement(Element.getElement(logoutLinkBy)).perform();
			Element.getElement(logoutLinkBy).click();
	    }catch(MoveTargetOutOfBoundsException e){
	        e.getMessage();
	    }
	}

	public void setActions(){
		actions = new Actions((WebDriver) LocalStore.getObject(ConfigData.SYSTEM_PROPERTY_WEBDRIVER));
	}
}
