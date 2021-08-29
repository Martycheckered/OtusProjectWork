package utils;

import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class Helpers {

    public static boolean isSubstringPresentInString (String source, String substring) {
        return source.toLowerCase().contains(substring.toLowerCase());
    }

    @Attachment(value = "{name}", type = "image/png")
    public static byte[] takeScreenShot(WebDriver driver, String name) {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }
}
