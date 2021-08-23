package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class EventDetailsPage extends GeneralPage {

    public static final String ADDRESS_OF_EVENT_LOCATOR = "//div[contains(@class,'location')]/span";
    public static final String LANGUAGE_OF_EVENT_LOCATOR = "//div[contains(@class,'language')]/span";
    public static final String TAG_TESTING_OF_EVENT_LOCATOR = "//div[contains(@class,'evnt-topic')]/label[contains(text(),'Testing')]";

    @FindBy(xpath = "//div[contains(@class,'location')]/span")
    private WebElement locationInfo;

    @FindBy(xpath = "//div[contains(@class,'language')]/span")
    private WebElement languageInfo;

    @FindBy(xpath = "//div[contains(@class,'evnt-topics')]//label")
    private List<WebElement> categories;

    public EventDetailsPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(this.driver, this);
    }

    public String getLocationInfo() {
        return locationInfo.getText();
    }

    public String getLanguageInfo() {
        return languageInfo.getText();
    }

    public List<WebElement> getCategories() {
        return categories;
    }

}
