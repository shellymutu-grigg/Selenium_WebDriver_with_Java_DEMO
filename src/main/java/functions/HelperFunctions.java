package functions;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HelperFunctions {
	static WebDriver webDriver;

	public HelperFunctions(WebDriver webDriver) {
		HelperFunctions.webDriver = webDriver;
		PageFactory.initElements(webDriver, this);
	}
}
