package configuration;

import data.ConfigData;
import functions.Get;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

public class EdgeDriverConfig {

	public static WebDriver setUpEdgeDriver() {
		EdgeOptions edgeOptions = new EdgeOptions();
		if (Get.globalProperty(ConfigData.HEADLESS)=="true") {
			edgeOptions.addArguments("--headless=new");
		}
		return new EdgeDriver(edgeOptions);
	}
}
