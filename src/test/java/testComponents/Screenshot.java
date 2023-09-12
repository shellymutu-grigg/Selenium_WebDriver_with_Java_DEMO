package testComponents;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import resources.WebDriverManager;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Screenshot {

    public static String captureScreenshot(String testCaseName) throws IOException {
        Date calendarDate = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = dateFormat.format(calendarDate);
        TakesScreenshot takeScreenshot = (TakesScreenshot) WebDriverManager.getDriver();
        File screenshot = takeScreenshot.getScreenshotAs(OutputType.FILE);
        File screenshotOutputFile = new File(System.getProperty("user.dir") + "//reports//screenshots//" + testCaseName + date.replace(":", "_").replace(" ", "_") + ".png");
        FileUtils.copyFile(screenshot, screenshotOutputFile);
        return System.getProperty("user.dir") + "//reports//screenshots//" + testCaseName + date.replace(":", "_").replace(" ", "_") + ".png";
    }
}
