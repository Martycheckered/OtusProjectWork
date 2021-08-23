package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;

public class VideoPage extends GeneralPage {


     public static final String MORE_FILTERS_LOCATOR = "//span[contains(text(),'More Filters')]";
    /* public static final String FILTER_CATEGORY_LOCATOR = "//span[contains(text(),'Category')]";
     public static final String FILTER_LOCATION_LOCATOR = "//span[contains(text(),'Location')]";
     public static final String FILTER_LANGUAGE_LOCATOR = "//span[contains(text(),'Language')]";
    public static final String CATEGORY_TESTING_LOCATOR = "//label[normalize-space(@data-value)='Testing']";
    public static final String LOCATION_BELARUS_LOCATOR = "//label[normalize-space(@data-value)='Belarus']";
    public static final String LANGUAGE_ENGLISH_LOCATOR = "//label[normalize-space(@data-value)='ENGLISH']";*/

    public static final String IMAGE_ON_CARD_LOCATOR = "//div[contains(@class,'evnt-talk-card')]//img";

    public static final String ARTICLE_LOCATOR = "//div[contains(@class,'evnt-talk-name')]";
    public static final String ARTICLE_NAME_LOCATOR = ".//span";
    public static final String TEMPLATE_FILTER = "//span[contains(text(),'%s')]";
    public static final String TEMPLATE_VALUE = "//label[normalize-space(@data-value)='%s']";

    @FindBy(xpath = "//span[contains(text(),'More Filters')]")
    private WebElement moreFiltersButton;

    @FindBy(xpath = "//input[contains(@placeholder,'Search by Talk Name')]")
    private WebElement searchField;

    @FindBy(xpath = ARTICLE_LOCATOR)
    private List<WebElement> talksNames;



    public VideoPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(this.driver, this);
    }
    @Step("Искать по строке : {request}")
    public void search(String request) {
       searchField.sendKeys(request);
       searchField.sendKeys(Keys.ENTER);

        waitUntilLoaderDisappears();

        logger.info("Search request sended: " + request );
    }
    @Step("Нажать кнопку \"More Filters\"")
    public void clickMoreFilters() {
        clickOnByXpath(MORE_FILTERS_LOCATOR);
        logger.info("MORE_FILTERS clicked");
    }
    @Step("Собрать все названия карточек")
    public List<String> collectArticleNamesFromCards (){
        waitUntilLoaderDisappears();
        List<WebElement> cards = talksNames;
        List <String> names = new ArrayList<>();
        for (WebElement card : cards) {
            names.add(card.findElement(By.xpath(ARTICLE_NAME_LOCATOR)).getText());
        }
        return names;
    }

    @Step("Кликнуть на первую карточку")
    public EventDetailsPage clickOnFirstArticle () {
        clickOnByXpath(IMAGE_ON_CARD_LOCATOR);
        logger.info("Go into first article after search");
        return new EventDetailsPage(driver);
    }

    @Step("Фильтрация по {filter} со значением {value}")
    public void makeFiltration (String filter, String value) {
        String currentFilter = String.format(TEMPLATE_FILTER, filter);
        String currentValue = String.format(TEMPLATE_VALUE, value);

        clickOnByXpath(currentFilter);
        logger.info("Selected filter : " + filter);
        clickOnByXpath(currentValue);
        logger.info("Selected value : " + value);
    }


}
