package tests;

import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.List;

import data.LocalStore;
import functions.Get;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.TestException;
import org.testng.annotations.Test;
import org.testng.annotations.Listeners;

import data.ConfigData;
import data.PageTitleData;
import data.TextData;
import functions.TestAssert;
import functions.TestCaseName;
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
import webElement.Element;

@Listeners(ExtentListeners.class)
public class E2EAddToCartTest extends TestSetup implements IHelper{
	LoginProcess loginProcess = new LoginProcess();
	LogoutProcess logoutProcess = new LogoutProcess();
	String password = System.getenv(ConfigData.AMAZON_PASSWORD_SUCCESS);
	
	@Test(groups = { "E2E", "Smoke" },
			priority = 1,
			description = "Add to Cart End to End scenario",
			retryAnalyzer = resources.RetryAnalyzer.class)
	/*
		GIVEN a user enters a correct email and password combination
		WHEN they access the Enter Email and Enter Password screens
		THEN the application will log the user in
			AND the user will be able to search for products
			AND add a product to their cart
			AND remove the product from their cart
			AND logout from their account
	 */
	public void addToCartTest(Method method) {
		ExtentTestManager.startTest(TestCaseName.convert(method.getName()), "Verify a user is able to login, add an item to their cart, remove the item from their cart and log out successfully.");
		ExtentTestManager.getTest().assignAuthor("Shelly Mutu-Grigg");
		ExtentTestManager.getTest().assignDevice("Desktop");
		ExtentTestManager.getTest().assignCategory("E2E");
		ExtentTestManager.getTest().log(ExtentTestManager.getTest().getStatus(), MessageFormat.format("{0} has started executing in {1}.",
				TestCaseName.convert(method.getName()), StringUtils.capitalize(LocalStore.getObject(ConfigData.SYSTEM_PROPERTY_BROWSER).toString())));
		
		loginPage.navigateToURL();
		ExtentListeners extentListener = new ExtentListeners();
		extentListener.onTestStartScreenshot(method.getName());
		
		loginProcess.completeLogin(password, loginPage);
		TestAssert.elementNotNull(Element.getElement(By.xpath("//*[contains(text(), '"+ TextData.RETURNS_AND_ORDERS_TEXT +"')]")), "enterUserPassword(password)");
		TestAssert.pageTitle("enterUserPassword(password)", PageTitleData.LANDING_PAGE_TITLE, webDriver.getTitle());
		
		SearchPage searchPage = loginPage.generateSearchPage();
		TestAssert.elementNotNull(Element.getElement(By.xpath("//*[contains(text(), '"+ TextData.DELIVER_TO_TEXT +"')]")), "pauseForSearchPage()");
		TestAssert.pageTitle("pauseForSearchPage()", PageTitleData.LANDING_PAGE_TITLE, webDriver.getTitle());

		ResultsPage resultsPage = searchPage.searchForProducts(TextData.SEARCH_TEXT);
		TestAssert.elementNotNull(Element.getElement(By.xpath("//*[contains(text(), '"+ TextData.RESULTS_TEXT +"')]")), "searchForProducts(TextData.SEARCH_TEXT)");

		List<WebElement> productList = resultsPage.getProductList();
		TestAssert.pageTitle("getProductList()", PageTitleData.RESULTS_PAGE_TITLE, webDriver.getTitle());

		int resultIndex = ConfigData.RESULTS_INDEX;

		String[] productStrings = productList.get(resultIndex).getText().split("\n");
		
		String productName = productStrings[0];

		int index = determineIndex(productName);
		productName = productList.get(resultIndex).getText().split("\n")[index];

		TestAssert.elementNotNull(Element.getElement(By.xpath("//*[contains(text(), '"+ TextData.RESULTS_TEXT +"')]")), "getProductList()");

		String bookTitle = resultsPage.setProduct(productName, index);
		TestAssert.pageTitle("setProduct(productName, index)", PageTitleData.RESULTS_PAGE_TITLE, webDriver.getTitle());

		resultsPage.clickProductLink(bookTitle, By.cssSelector("img[alt='"+ bookTitle +"']"));

		if(Element.isPresent(By.xpath("//*[contains(text(), '"+ TextData.ADD_TO_CART_TEXT +"')]"))){
			CartPage cartPage = resultsPage.addProductToCart();
			TestAssert.elementNotNull(Element.getElement(By.xpath("//*[contains(text(), '"+ TextData.ADDED_TO_CART_TEXT +"')]")), "addProductToCart(productName, index)");
			TestAssert.pageTitle("addProductToCart(productName, index)", PageTitleData.CART_PAGE_TITLE, webDriver.getTitle());

			cartPage.openCart();
			TestAssert.elementNotNull(Element.getElement(By.xpath("//h1[normalize-space()='"+ TextData.SHOPPING_CART_TEXT +"']")), "openCart()");
			TestAssert.pageTitle("openCart()", PageTitleData.CART_PAGE_TITLE, webDriver.getTitle());

			LogoutPage logoutPage =  cartPage.deleteCart();
			TestAssert.elementNotNull(Element.getElement(By.xpath("//*[contains(text(), '"+ TextData.REMOVED_FROM_CART_TEXT +"')]")), "deleteCart()");
			TestAssert.pageTitle("deleteCart()", PageTitleData.CART_PAGE_TITLE, webDriver.getTitle());
			logoutProcess.cartLogout(logoutPage);
		} else{
			LogoutPage logoutPage = loginPage.initialiseLogoutPage();
			System.out.println(MessageFormat.format("{0} is not available for purchase", productName));
			logoutProcess.logout(logoutPage, "TestException: "+ productName);
			ExtentTestManager.getTest().fail(MessageFormat.format("{0} is not available for purchase", productName));
		}
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