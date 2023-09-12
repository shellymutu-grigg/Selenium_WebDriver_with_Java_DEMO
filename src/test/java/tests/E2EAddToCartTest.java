package tests;

import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.List;

import data.LocalStore;
import functions.Get;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import org.testng.annotations.Listeners;

import data.ConfigData;
import data.PageTitleData;
import data.TextData;
import functions.TestAssert;
import functions.TestCaseName;
import interfaces.IHelper;
import pageObjects.*;
import resources.ExtentListeners;
import resources.ExtentTestManager;
import resources.WebDriverListener;
import resources.WebDriverManager;
import testComponents.LoginProcess;
import testComponents.LogoutProcess;
import testComponents.TestSetup;
import webElement.Element;

@Listeners({ExtentListeners.class, WebDriverListener.class})
@Slf4j
public class E2EAddToCartTest extends TestSetup implements IHelper{
	LoginProcess loginProcess = new LoginProcess();
	LogoutProcess logoutProcess = new LogoutProcess();
	String password = System.getenv(ConfigData.AMAZON_PASSWORD_SUCCESS);

	String[] productStrings;

	Logger logger = LoggerFactory.getLogger(E2EAddToCartTest.class);
	
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

		WebDriverManager.getDriver().get(Get.url());

		LoginPage loginPage = new LoginPage();
		loginPage.navigateToURL();
		logger.info("Thread {} ({}) with webDriver with hashCode {} has navigated to landing page", Thread.currentThread().getId(), TestCaseName.convert(method.getName()), WebDriverManager.getDriver().hashCode());

		ExtentListeners extentListener = new ExtentListeners();
		extentListener.onTestStartScreenshot(method.getName());
		
		loginProcess.completeLogin(password, loginPage);
		logger.info("Thread {} ({}) with webDriver with hashCode {} has successfully completed login process", Thread.currentThread().getId(), TestCaseName.convert(method.getName()), WebDriverManager.getDriver().hashCode());

		TestAssert.elementNotNull(Element.getElement(By.xpath("//*[contains(text(), '"+ TextData.RETURNS_AND_ORDERS_TEXT +"')]")), "enterUserPassword(password)");
		TestAssert.pageTitle("enterUserPassword(password)", PageTitleData.LANDING_PAGE_TITLE, WebDriverManager.getDriver().getTitle());
		
		SearchPage searchPage = loginPage.generateSearchPage();
		TestAssert.elementNotNull(Element.getElement(By.xpath("//*[contains(text(), '"+ TextData.DELIVER_TO_TEXT +"')]")), "pauseForSearchPage()");
		TestAssert.pageTitle("pauseForSearchPage()", PageTitleData.LANDING_PAGE_TITLE, WebDriverManager.getDriver().getTitle());
		logger.info("Thread {} ({}) with webDriver with hashCode {} has successfully rendered search page", Thread.currentThread().getId(), TestCaseName.convert(method.getName()), WebDriverManager.getDriver().hashCode());

		ResultsPage resultsPage = searchPage.searchForProducts(TextData.SEARCH_TEXT);
		TestAssert.elementNotNull(Element.getElement(By.xpath("//*[contains(text(), '"+ TextData.RESULTS_TEXT +"')]")), "searchForProducts(TextData.SEARCH_TEXT)");
		logger.info("Thread {} ({}) with webDriver with hashCode {} has successfully generated results page", Thread.currentThread().getId(), TestCaseName.convert(method.getName()), WebDriverManager.getDriver().hashCode());

		List<WebElement> productList = resultsPage.getProductList();
		TestAssert.pageTitle("getProductList()", PageTitleData.RESULTS_PAGE_TITLE, WebDriverManager.getDriver().getTitle());

		int resultIndex = ConfigData.RESULTS_INDEX;

		productStrings = productList.get(resultIndex).getText().split("\n");

		int index = determineIndex();
		String productName = productList.get(resultIndex).getText().split("\n")[index];

		TestAssert.elementNotNull(Element.getElement(By.xpath("//*[contains(text(), '"+ TextData.RESULTS_TEXT +"')]")), "getProductList()");

		String bookTitle = resultsPage.setProduct(productName, index);
		TestAssert.pageTitle("setProduct(productName, index)", PageTitleData.RESULTS_PAGE_TITLE, WebDriverManager.getDriver().getTitle());

		resultsPage.clickProductLink(bookTitle, By.cssSelector("img[alt='"+ bookTitle +"']"));
		logger.info("Thread {} ({}) with webDriver with hashCode {} has selected product number {} with product name {} from the results list", Thread.currentThread().getId(), TestCaseName.convert(method.getName()), WebDriverManager.getDriver().hashCode(), ConfigData.RESULTS_INDEX, productName);

		if(Element.isPresent(By.xpath("//*[contains(text(), '"+ TextData.ADD_TO_CART_TEXT +"')]"))){
			CartPage cartPage = resultsPage.addProductToCart();
			TestAssert.elementNotNull(Element.getElement(By.xpath("//*[contains(text(), '"+ TextData.ADDED_TO_CART_TEXT +"')]")), "addProductToCart(productName, index)");
			TestAssert.pageTitle("addProductToCart(productName, index)", PageTitleData.CART_PAGE_TITLE, WebDriverManager.getDriver().getTitle());
			logger.info("Thread {} ({}) with webDriver with hashCode {} successfully added product {} to cart", Thread.currentThread().getId(), TestCaseName.convert(method.getName()), WebDriverManager.getDriver().hashCode(), productName);

			cartPage.openCart();
			TestAssert.elementNotNull(Element.getElement(By.xpath("//h1[normalize-space()='"+ TextData.SHOPPING_CART_TEXT +"']")), "openCart()");
			TestAssert.pageTitle("openCart()", PageTitleData.CART_PAGE_TITLE, WebDriverManager.getDriver().getTitle());
			logger.info("Thread {} ({}) with webDriver with hashCode {} successfully navigated to cart page", Thread.currentThread().getId(), TestCaseName.convert(method.getName()), WebDriverManager.getDriver().hashCode());

			LogoutPage logoutPage =  cartPage.deleteCart();
			TestAssert.elementNotNull(Element.getElement(By.xpath("//*[contains(text(), '"+ TextData.REMOVED_FROM_CART_TEXT +"')]")), "deleteCart()");
			TestAssert.pageTitle("deleteCart()", PageTitleData.CART_PAGE_TITLE, WebDriverManager.getDriver().getTitle());
			logger.info("Thread {} ({}) with webDriver with hashCode {} successfully deleted product {} from cart", Thread.currentThread().getId(), TestCaseName.convert(method.getName()), WebDriverManager.getDriver().hashCode(), productName);

			logoutProcess.cartLogout(logoutPage);
			logger.info("Thread {} ({}) with webDriver with hashCode {} successfully completed logout process", Thread.currentThread().getId(), TestCaseName.convert(method.getName()), WebDriverManager.getDriver().hashCode());


		} else{
			LogoutPage logoutPage = loginPage.initialiseLogoutPage();
			logger.info("Thread {} ({}) with webDriver with hashCode {} has determined that product {} is not available for purchase", Thread.currentThread().getId(), TestCaseName.convert(method.getName()), WebDriverManager.getDriver().hashCode(), productName);

			logoutProcess.logout(logoutPage, "TestException: "+ productName);
			logger.error("{} failed to complete", TestCaseName.convert(method.getName()));
			logger.info("Thread {} ({}) with webDriver with hashCode {} has failed to complete for product {}", Thread.currentThread().getId(), TestCaseName.convert(method.getName()), WebDriverManager.getDriver().hashCode(), productName);
			ExtentTestManager.getTest().fail(MessageFormat.format("{0} is not available for purchase", productName));
		}
	}

	@Override
	public int determineIndex() {
		int index =0;
		if(productStrings[index].contains(TextData.PREVIOUSLY_VIEWED_TEXT)) {
			index++;
		}
		if(productStrings[index].contains(TextData.SPONSORED_TEXT)) {
			index++;
		}
		logger.info("Thread {} with webDriver with hashCode {} has product name {}", Thread.currentThread().getId(), WebDriverManager.getDriver().hashCode(), productStrings[index]);
		return index;
	}

}