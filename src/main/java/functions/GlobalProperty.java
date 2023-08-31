package functions;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class GlobalProperty {
    public static String getGlobalProperty(String parameter) throws IOException {;
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
}