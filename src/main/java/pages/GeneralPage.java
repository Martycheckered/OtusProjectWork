package pages;

import io.qameta.allure.Step;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class GeneralPage {
    protected final Logger logger = LogManager.getLogger(this);
    protected WebDriver driver;
    private final By loader = By.cssSelector("div.evnt-global-loader");



    public GeneralPage(WebDriver driver) {
        this.driver = driver;
    }

    public void clickOnBy (By selector) {
        waitUntilLoaderDisappears();

        waitUntilElementIsClickable(selector);
        findElement(selector).click();

        waitUntilLoaderDisappears();
    }

    @Step("Нажатие на кнопку принять Cookie")
    public void acceptCookie() {
        clickOnBy(By.cssSelector("button#onetrust-accept-btn-handler"));

    }

    public void waitUntilLoaderDisappears () {
        new WebDriverWait(driver, 5)
                .until(ExpectedConditions.invisibilityOfElementLocated(loader));
    }

    public void waitUntilElementIsClickable (By element) {
        new WebDriverWait(driver, 5)
                .until(ExpectedConditions.elementToBeClickable(element));
    }
    public WebElement findElement (By element) {
        new WebDriverWait(driver, 5)
                .until(ExpectedConditions.visibilityOfElementLocated(element));
        return driver.findElement(element);
    }





}
