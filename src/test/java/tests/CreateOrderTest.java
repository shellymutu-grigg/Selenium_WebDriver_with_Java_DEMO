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
		System.out.println(MessageFormat.format("productList.size(): ", productList.size()));

		int index = Integer.parseInt(getGlobalProperty("resultIndex"));
		searchPage.addProductToCart(productList.get(index).getText().split("\n")[0]);
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