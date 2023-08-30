package configuration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

public class EdgeDriverConfig {

	public static WebDriver setUpEdgeDriver(String browserNameString) {
		EdgeOptions edgeOptions = new EdgeOptions();
		if (browserNameString.contains("headless")) {
			edgeOptions.addArguments("--headless=new");
		}
		return new EdgeDriver(edgeOptions);
	}
}
