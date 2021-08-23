package utils;

import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public class BaseHooks {
    protected static WebDriver driver;
    protected  Logger logger = LogManager.getLogger(this);

    @BeforeMethod
    public  void setup() {
        driver = WebDriverFactory.createDriver(Browsers.CHROME);

        if (driver != null) {
            driver.manage().deleteAllCookies();
            driver.manage().window().maximize();
        }
        logger.info("Driver is up");
    }

    @AfterMethod
    public  void teardown() {
        if (driver != null) {
            driver.quit();
        }
        logger.info("Driver down");
    }

    public static boolean isSubstringPresentInString (String source, String substring) {
        return source.toLowerCase().contains(substring.toLowerCase());
    }

    @Attachment(value = "{name}", type = "image/png")
    protected byte[] takeScreenShot(String name) throws IOException {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }


}

