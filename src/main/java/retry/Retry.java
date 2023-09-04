package retry;

import data.TextData;
import org.openqa.selenium.By;
import webElement.Element;

public abstract class Retry {

    public static void retry(By by, boolean list, String action, String text, int numberOfTimes){
        for(int i = 0; i < numberOfTimes; i++) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            switch (action) {
                case TextData.CLICK:
                    Element.click(by, list);
                    break;
                case TextData.SEND_KEYS:
                    Element.sendKeys(by, text);
                    break;
                default:
                    break;
            }
        }
    }
}
