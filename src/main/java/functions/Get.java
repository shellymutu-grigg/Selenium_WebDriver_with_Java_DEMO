package functions;

import data.ConfigData;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class Get {
    public static String url(){
        String url = System.getProperty("url") != null ? System.getProperty("url")
                : ConfigData.AMAZON_WEBSITE_URL;
        return url;
    }

    public static String globalProperty(String parameter) {
        // Read in properties file
        Properties properties = new Properties();
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(System.getProperty("user.dir")
                    + "//src//test//resources//globalData.properties");
            properties.load(fileInputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // If the value is sent via Maven commands use that otherwise use the globalData.properties file
        String browser = System.getProperty(parameter) != null ? System.getProperty(parameter)
                : properties.getProperty(parameter);
        return browser;
    }
}
