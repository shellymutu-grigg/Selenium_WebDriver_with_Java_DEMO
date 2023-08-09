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
		String titleText = System.getProperty("titleText") != null ? System.getProperty("titleText")
				: properties.getProperty("titleText");
		
		// Load .env file variables
		Dotenv dotenv = Dotenv
				   .configure()
				   .ignoreIfMissing()
				   .load();
		String email = dotenv.get("EMAIL");
		String password = dotenv.get("PASSWORD");

		webDriver.manage().window().maximize();
		webDriver.get(url);
		
		WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".nav-progressive-attribute")));
		
		String title = webDriver.getTitle();
		System.out.println(MessageFormat.format("Page Title: {0}",title));
		Assert.assertEquals(title, titleText);
		
		webDriver.findElement(By.xpath("//span[normalize-space()='Account & Lists']")).click();
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".a-spacing-small")));
		
		webDriver.findElement(By.id("ap_email")).sendKeys(email);
		webDriver.findElement(By.id("continue")).click();	
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".a-form-label")));
		
		webDriver.findElement(By.id("ap_password")).sendKeys(password);
		webDriver.findElement(By.id("signInSubmit")).click();	
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".nav-logo-link")));
		
//		webDriver.findElement(By.id("password")).sendKeys(password);
//		webDriver.findElement(By.cssSelector("button[value='Submit'")).click();	
//		
//		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".ng-tns-c148-3")));
		
	}

}
