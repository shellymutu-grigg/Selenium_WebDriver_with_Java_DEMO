package functions;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Url {

    public static String getUrl(){
        Properties properties = new Properties();
        FileInputStream fileInputStream;
        try {
            fileInputStream = new FileInputStream(System.getProperty("user.dir")
                    + "//src//test//resources//globalData.properties");
            properties.load(fileInputStream);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String url = System.getProperty("url") != null ? System.getProperty("url")
                : properties.getProperty("url");
        return url;
    }
}
