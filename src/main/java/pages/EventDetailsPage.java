package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class EventDetailsPage extends GeneralPage {

    public static final String ADDRESS_OF_EVENT_LOCATOR = "//div[contains(@class,'location')]/span";
    public static final String LANGUAGE_OF_EVENT_LOCATOR = "//div[contains(@class,'language')]/span";
    public static final String EVENT_CATEGORIES_LOCATOR = "//div[contains(@class,'evnt-topics')]//label";


    public EventDetailsPage(WebDriver driver) {
        super(driver);
    }

    public String getLocationInfo() {
        return findElement(By.xpath(ADDRESS_OF_EVENT_LOCATOR)).getText();
    }

    public String getLanguageInfo() {
        return findElement(By.xpath(LANGUAGE_OF_EVENT_LOCATOR)).getText();
    }

    public List<WebElement> getCategories() {
        return driver.findElements(By.xpath(EVENT_CATEGORIES_LOCATOR));
    }

}
