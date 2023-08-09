package tests;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.MessageFormat;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.cdimascio.dotenv.Dotenv;

public class Login {

	public static void main(String[] args) throws InterruptedException, IOException {
		WebDriverManager.chromedriver().setup();
		WebDriver webDriver = new ChromeDriver();
		webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

		// Read in properties file
		Properties properties = new Properties();
		FileInputStream fileInputStream = new FileInputStream(System.getProperty("user.dir")
				+ "//src//test//resources//globalData.properties");
		properties.load(fileInputStream);
		String url = System.getProperty("url") != null ? System.getProperty("url")
		: properties.getProperty("url");
		String legalText = System.getProperty("legalText") != null ? System.getProperty("legalText")
				: properties.getProperty("legalText");
		
		// Load .env file variables
		Dotenv dotenv = Dotenv
				   .configure()
				   .ignoreIfMissing()
				   .load();
		String email = dotenv.get("EMAIL");
		String password = dotenv.get("PASSWORD");
		
		System.out.println(MessageFormat.format("URL: {0}", url));
		System.out.println(MessageFormat.format("Email: {0}", email));
		System.out.println(MessageFormat.format("websiteTitle: {0}", legalText));

		// Navigate to web page
		webDriver.manage().window().maximize();
		webDriver.get(url);
		
		WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".ng-star-inserted")));
		
		String legal = webDriver.findElement(By.cssSelector(".legalBar-highlight")).getText();
		System.out.println(MessageFormat.format("legalText: {0}", legal));
		Assert.assertEquals(legal, legalText);
		
		webDriver.findElement(By.cssSelector("a[title='Sign In'] span[class='ng-star-inserted']")).click();
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".ng-tns-c88-0")));
		
		webDriver.findElement(By.id("emailInput")).sendKeys(email);
		webDriver.findElement(By.cssSelector("button[value='Submit'")).click();	
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".text-wrapper")));
		
		webDriver.findElement(By.id("password")).sendKeys(password);
		webDriver.findElement(By.cssSelector("button[value='Submit'")).click();	
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".ng-tns-c148-3")));
		
	}

}
