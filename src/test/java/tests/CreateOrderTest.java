package tests;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import io.github.cdimascio.dotenv.Dotenv;

import pageObjects.SearchPage;
import testComponents.TestSetup;

public class CreateOrderTest extends TestSetup {	
	@Test(groups = { "Login" })
	public void submitOrderTest() throws IOException, InterruptedException {		
		String email = GetParameter("EMAIL");
		String password = GetParameter("PASSWORD_SUCCESS");
		
		// Login to site
		SearchPage searchPage = loginPage.loginApplication(email, password);		
		searchPage.searchForProducts(getGlobalProperty("searchText"));
		
		List<WebElement> productList = searchPage.getProductList();
		int resultIndex = Integer.parseInt(getGlobalProperty("resultIndex"));

		String[] productStrings = productList.get(resultIndex).getText().split("\n");
		
		String productName = productStrings[0];

		int index = determineIndex(productName);
		productName = productList.get(resultIndex).getText().split("\n")[index];

		searchPage.addProductToCart(productName, index);
		System.out.println(MessageFormat.format("addProductToCart: {0}", productName));

	}
	
	public int determineIndex(String productName) {
		int index =0;
		if(productName.contains("Previously viewed")) {
			index = 1;
		}
		return index;
	}
	
	public String GetParameter(String parameter){
		// Load .env file variables
		Dotenv dotenv = Dotenv
				   .configure()
				   .ignoreIfMissing()
				   .load();
		String fileParameter = dotenv.get(parameter);
		return fileParameter;
		
	}

}