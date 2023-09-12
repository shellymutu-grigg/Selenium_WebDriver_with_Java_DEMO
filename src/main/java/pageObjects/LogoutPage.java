package pageObjects;

import data.LocalStore;
import data.TextData;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.MoveTargetOutOfBoundsException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webElement.Element;

import java.text.MessageFormat;

@Slf4j
public class LogoutPage {
	Actions actions;
	
	By accountLinkBy = By.xpath("//*[contains(text(), '" + TextData.ACCOUNT_MENU_LINK_TEXT + "')]");
	By logoutLinkBy = By.id("nav-item-signout");

	static Logger logger = LoggerFactory.getLogger(LogoutPage.class);
	
	public void openAccountMenu() {
		setActions();
		try{
	        actions.moveToElement(Element.getElement(accountLinkBy)).perform();
	    }catch(MoveTargetOutOfBoundsException e){
			logger.error("MoveTargetOutOfBoundsException: {}", e.getMessage());
			throw new MoveTargetOutOfBoundsException(MessageFormat.format("MoveTargetOutOfBoundsException: {0}", e.getMessage()));
	    }
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			logger.error("InterruptedException: {}", e.getMessage());
			throw new RuntimeException(MessageFormat.format("InterruptedException: {0}", e.getMessage()));
		}
	}
	
	public void logout() {
		setActions();
		try{
			actions.moveToElement(Element.getElement(logoutLinkBy)).perform();
			Element.click(logoutLinkBy, false);
	    }catch(MoveTargetOutOfBoundsException e){
			logger.error("MoveTargetOutOfBoundsException: {}", e.getMessage());
			throw new MoveTargetOutOfBoundsException(MessageFormat.format("MoveTargetOutOfBoundsException: {0}", e.getMessage()));	    }
	}

	public void setActions(){
		actions = new Actions((WebDriver) LocalStore.getObject(String.valueOf(Thread.currentThread().getId())));
	}
}
