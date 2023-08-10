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
		String password = GetParameter("EMAIL");
		
		// Login to site
		SearchPage searchPage = loginPage.loginApplication(email, password);
		System.out.println(MessageFormat.format("submitOrderTest loginApplication success: {0}",email));
		
		searchPage.searchForProducts("istqb");
		
		List<WebElement> productList = searchPage.getProductList();
		System.out.println(MessageFormat.format("submitOrderTest searchForProducts size success: {0}", productList.size()));
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