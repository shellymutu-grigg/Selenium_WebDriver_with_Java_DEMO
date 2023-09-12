package configuration;

import data.ConfigData;
import functions.Get;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

public class EdgeDriverConfig {

	public static WebDriver setUpEdgeDriver() {
		EdgeOptions edgeOptions = new EdgeOptions();
		boolean headless = Boolean.parseBoolean(Get.globalProperty(ConfigData.HEADLESS));
		if (headless) {
			edgeOptions.addArguments("--headless");
		}
		return new EdgeDriver(edgeOptions);
	}
}
