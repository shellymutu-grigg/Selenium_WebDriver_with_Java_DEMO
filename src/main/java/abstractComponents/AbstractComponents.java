package abstractComponents;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AbstractComponents {

	static WebDriver webDriver;

	public AbstractComponents(WebDriver webDriver) {
		AbstractComponents.webDriver = webDriver;
		PageFactory.initElements(webDriver, this);
	}

	public static void waitForElementToAppear(By findBy) throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));
		//Thread.sleep(14000);
		Thread.sleep(5);
		wait.until(ExpectedConditions.visibilityOfElementLocated(findBy));
	}
}