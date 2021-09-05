package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseHooks {
    private static final ThreadLocal<WebDriver> drivers = new ThreadLocal<>();
    protected  Logger logger = LogManager.getLogger(this);

    protected WebDriver getDriver() {
        return  drivers.get();
    }

    @BeforeMethod
    public  void setup() {
        WebDriver driver =
        WebDriverFactory.create(System.getProperty("browser"), System.getProperty("options"));
        drivers.set(driver);

        logger.info("Driver is up");
    }

    @AfterMethod
    public  void teardown() {
        getDriver().quit();
        drivers.remove();
        logger.info("Driver down");
    }




}

