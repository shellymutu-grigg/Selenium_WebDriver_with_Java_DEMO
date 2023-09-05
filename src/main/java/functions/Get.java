package functions;

import data.ConfigData;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Properties;

@Slf4j
public class Get {

    static Logger logger = LoggerFactory.getLogger(Get.class);

    public static String url(){
        return System.getProperty("url") != null ? System.getProperty("url")
                : ConfigData.AMAZON_WEBSITE_URL;
    }

    public static String globalProperty(String parameter) {
        // Read in properties file
        Properties properties = new Properties();
        FileInputStream fileInputStream;
        try {
            fileInputStream = new FileInputStream(System.getProperty("user.dir")
                    + "//src//test//resources//globalData.properties");
            properties.load(fileInputStream);
        } catch (IOException e) {
            logger.error("IOException: {}", e.getMessage());
            throw new RuntimeException(MessageFormat.format("IOException: {0}", e.getMessage()));
        }

        // If the value is sent via Maven commands use that otherwise use the globalData.properties file
        return System.getProperty(parameter) != null ? System.getProperty(parameter)
                : properties.getProperty(parameter);
    }
}
