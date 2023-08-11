package pageObjects;

import java.io.IOException;
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
import abstractComponents.HelperFunctions;

public class OrdersPage extends AbstractComponents {

	WebDriver webDriver;
	Actions actions;
	HelperFunctions helperFunctions = new HelperFunctions();

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

	public void openAccountMenu() throws InterruptedException, IOException {
		WebElement acountMenu = webDriver.findElement(accountLinkBy);
		actions.moveToElement(acountMenu).perform();
		
		waitForElementToAppear(accountMenuBy);
	}
	
	public void openOrderPage() throws InterruptedException, IOException {
		WebElement ordersLink = webDriver.findElement(orderPageLinkBy);
		actions.moveToElement(ordersLink).perform();
		ordersLink.click();
		
		waitForElementToAppear(orderHeaderBy);
		helperFunctions.validatePageTitle("ordersTitle", webDriver.getTitle());
	}
	
	public LogoutPage searchForOrders() throws IOException { 
		WebElement searchField = webDriver.findElement(searchOrdersFieldBy);
		String searchText = helperFunctions.getGlobalProperty("orderSearchText");
		WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));
		searchField.sendKeys(searchText);
		WebElement searchOrdersButton = wait.until(ExpectedConditions.elementToBeClickable(searchOrdersButtonBy));
		searchOrdersButton.click();
		
		WebElement order = webDriver.findElement(By.cssSelector("label[for='orderFilter']"));
		String orderFilterText = order.getText();
		
		String expectedText = helperFunctions.getGlobalProperty("orderFilterText");
		Assert.assertEquals(orderFilterText, expectedText);
		LogoutPage logoutPage = new LogoutPage(webDriver);
		return logoutPage;
	}	
}