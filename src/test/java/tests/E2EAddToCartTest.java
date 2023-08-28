package tests;

import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import org.testng.annotations.Listeners;

import data.ConfigData;
import data.PageTitleData;
import data.TextData;
import functions.AssertElementNotNull;
import functions.AssertPageTitle;
import functions.ConvertTestCaseName;
import interfaces.IHelper;
import pageObjects.CartPage;
import pageObjects.LogoutPage;
import pageObjects.ResultsPage;
import pageObjects.SearchPage;
import resources.ExtentListeners;
import resources.ExtentTestManager;
import testComponents.LoginProcess;
import testComponents.LogoutProcess;
import testComponents.TestSetup;
import webElement.FindElement;

@Listeners(ExtentListeners.class)
public class E2EAddToCartTest extends TestSetup implements IHelper{
	LoginProcess loginProcess = new LoginProcess();
	LogoutProcess logoutProcess = new LogoutProcess();
	String password = System.getenv("AMAZON_PASSWORD_SUCCESS");
	
	@Test(groups = { "E2E" }, priority = 1, description = "Add to Cart End to End scenario")
	/*
		GIVEN a user enters a correct email and password combination
		WHEN they access the Enter Email and Enter Password screens
		THEN the application will log the user in
			AND the user will be able to search for products
			AND add a product to their cart
			AND remove the product from their cart
			AND logout from their account
	 */
	public void addToCartTest(Method method) throws Exception {
		ExtentTestManager.startTest(ConvertTestCaseName.convertTestCaseName(method.getName()), "Verify a user is able to login, add an item to their cart, remove the item from their cart and log out successfully.");
		ExtentTestManager.getTest().log(ExtentTestManager.getTest().getStatus(), MessageFormat.format("{0} has started executing in {1}.",
				ConvertTestCaseName.convertTestCaseName(method.getName()), StringUtils.capitalize(System.getProperty("Browser"))));
		
		loginPage.navigateToURL();
		ExtentListeners extentListener = new ExtentListeners();
		extentListener.onTestStartScreenshot(method.getName());
		
		loginProcess.completeLogin(password, loginPage, webDriver);
		AssertElementNotNull.assertElementNotNull(FindElement.getWebElement(By.xpath("//*[contains(text(), '"+ TextData.RETURNS_AND_ORDERS_TEXT +"')]"), webDriver), "enterUserPassword(password)");
		AssertPageTitle.assertPageTitle("enterUserPassword(password)", PageTitleData.LANDING_PAGE_TITLE, webDriver.getTitle());
		
		SearchPage searchPage = loginPage.generateSearchPage();
		AssertElementNotNull.assertElementNotNull(FindElement.getWebElement(By.xpath("//*[contains(text(), '"+ TextData.DELIVER_TO_TEXT +"')]"), webDriver), "pauseForSearchPage()");
		AssertPageTitle.assertPageTitle("pauseForSearchPage()", PageTitleData.LANDING_PAGE_TITLE, webDriver.getTitle());

		ResultsPage resultsPage = searchPage.searchForProducts(TextData.SEARCH_TEXT);
		AssertElementNotNull.assertElementNotNull(FindElement.getWebElement(By.xpath("//*[contains(text(), '"+ TextData.RESULTS_TEXT +"')]"), webDriver), "searchForProducts(TextData.SEARCH_TEXT)");
		AssertPageTitle.assertPageTitle("searchForProducts(TextData.SEARCH_TEXT)", PageTitleData.RESULTS_PAGE_TITLE, webDriver.getTitle());

		List<WebElement> productList = resultsPage.getProductList();
		AssertPageTitle.assertPageTitle("getProductList()", PageTitleData.RESULTS_PAGE_TITLE, webDriver.getTitle());

		int resultIndex = ConfigData.RESULTS_INDEX;

		String[] productStrings = productList.get(resultIndex).getText().split("\n");
		
		String productName = productStrings[0];

		int index = determineIndex(productName);
		productName = productList.get(resultIndex).getText().split("\n")[index];

		AssertElementNotNull.assertElementNotNull(FindElement.getWebElement(By.xpath("//*[contains(text(), '"+ TextData.RESULTS_TEXT +"')]"), webDriver), "getProductList()");
		
		CartPage cartPage = resultsPage.addProductToCart(productName, index);
		AssertElementNotNull.assertElementNotNull(FindElement.getWebElement(By.xpath("//*[contains(text(), '"+ TextData.ADDED_TO_CART_TEXT +"')]"), webDriver), "addProductToCart(productName, index)");
		AssertPageTitle.assertPageTitle("addProductToCart(productName, index)", PageTitleData.CART_PAGE_TITLE, webDriver.getTitle());
		
		cartPage.openCart();
		AssertElementNotNull.assertElementNotNull(FindElement.getWebElement(By.xpath("//h1[normalize-space()='"+ TextData.SHOPPING_CART_TEXT +"']"), webDriver), "openCart()");
		AssertPageTitle.assertPageTitle("openCart()", PageTitleData.CART_PAGE_TITLE, webDriver.getTitle());
				
		LogoutPage logoutPage =  cartPage.deleteCart();
		AssertElementNotNull.assertElementNotNull(FindElement.getWebElement(By.xpath("//*[contains(text(), '"+ TextData.REMOVED_FROM_CART_TEXT +"')]"), webDriver), "deleteCart()");
		AssertPageTitle.assertPageTitle("deleteCart()", PageTitleData.CART_PAGE_TITLE, webDriver.getTitle());

		logoutProcess.cartLogout(logoutPage, webDriver);
	}

	@Override
	public int determineIndex(String productName) {
		int index =0;
		if(productName.contains(TextData.PREVIOUSLY_VIEWED_TEXT)) {
			index = 1;
		}
		return index;
	}

}