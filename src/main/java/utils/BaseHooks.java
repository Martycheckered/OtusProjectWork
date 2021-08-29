package utils;

import io.qameta.allure.Attachment;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseHooks {
    protected  WebDriver driver;
    protected  Logger logger = LogManager.getLogger(this);

    @BeforeMethod
    public  void setup() {
       driver = WebDriverFactory.create(System.getProperty("browser"), System.getProperty("options"));

        if (driver != null) {
            driver.manage().deleteAllCookies();
            //driver.manage().window().maximize();
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




}

