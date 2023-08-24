package resources;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;

public class EdgeDriverConfig {

	public static WebDriver setUpEdgeDriver(String browserNameString, WebDriver webDriver) {
		return webDriver = new EdgeDriver();
	}
}
