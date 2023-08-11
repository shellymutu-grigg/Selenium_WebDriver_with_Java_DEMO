package abstractComponents;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.testng.Assert;

import io.github.cdimascio.dotenv.Dotenv;

public class HelperFunctions {
	
	public String getGlobalProperty(String parameter) throws IOException {;
		// Read in properties file
		Properties properties = new Properties();
		FileInputStream fileInputStream = new FileInputStream(System.getProperty("user.dir")
				+ "//src//test//resources//globalData.properties");
		properties.load(fileInputStream);
		
		// If the value is sent via Maven commands use that otherwise use the globalData.properties file
		String browser = System.getProperty(parameter) != null ? System.getProperty(parameter)
				: properties.getProperty(parameter);
		return browser;
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
	
	public int determineIndex(String productName) {
		int index =0;
		if(productName.contains("Previously viewed")) {
			index = 1;
		}
		return index;
	}
	
	public void validatePageTitle(String globalProperty, String pageTitle) throws IOException {
		String resultsTitle = getGlobalProperty(globalProperty);
		Assert.assertEquals(resultsTitle, pageTitle);
	}
	
}
