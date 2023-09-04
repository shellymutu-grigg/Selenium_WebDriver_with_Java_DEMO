package pageObjects;

import data.ConfigData;
import data.LocalStore;
import data.TextData;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.MoveTargetOutOfBoundsException;

import webElement.Element;

import java.text.MessageFormat;

public class LogoutPage {
	Actions actions;
	
	By accountLinkBy = By.xpath("//*[contains(text(), '" + TextData.ACCOUNT_MENU_LINK_TEXT + "')]");
	By logoutLinkBy = By.id("nav-item-signout");
	
	public void openAccountMenu() {
		setActions();
		try{
	        actions.moveToElement(Element.getElement(accountLinkBy)).perform();
	    }catch(MoveTargetOutOfBoundsException e){
			System.out.println(MessageFormat.format("MoveTargetOutOfBoundsException: {0}", e.getMessage()));
			throw new MoveTargetOutOfBoundsException(MessageFormat.format("MoveTargetOutOfBoundsException: {0}", e.getMessage()));
	    }
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			System.out.println(MessageFormat.format("InterruptedException: {0}", e.getMessage()));
			throw new RuntimeException(MessageFormat.format("InterruptedException: {0}", e.getMessage()));
		}
	}
	
	public void logout() {
		setActions();
		try{
			actions.moveToElement(Element.getElement(logoutLinkBy)).perform();
			Element.click(logoutLinkBy, false);
	    }catch(MoveTargetOutOfBoundsException e){
			System.out.println(MessageFormat.format("MoveTargetOutOfBoundsException: {0}", e.getMessage()));
			throw new MoveTargetOutOfBoundsException(MessageFormat.format("MoveTargetOutOfBoundsException: {0}", e.getMessage()));	    }
	}

	public void setActions(){
		actions = new Actions((WebDriver) LocalStore.getObject(ConfigData.SYSTEM_PROPERTY_WEBDRIVER));
	}
}
