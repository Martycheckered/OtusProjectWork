package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;

public class VideoPage extends GeneralPage {


     public static final String MORE_FILTERS_LOCATOR = "//span[contains(text(),'More Filters')]";
     public static final String SEARCH_FIELD_LOCATOR = "//input[contains(@placeholder,'Search by Talk Name')]";

    public static final String IMAGE_ON_CARD_LOCATOR = "//div[contains(@class,'evnt-talk-card')]//img";
    public static final String ARTICLE_LOCATOR = "//div[contains(@class,'evnt-talk-name')]";
    public static final String ARTICLE_NAME_LOCATOR = ".//span";
    public static final String TEMPLATE_FILTER = "//span[contains(text(),'%s')]";
    public static final String TEMPLATE_VALUE = "//label[normalize-space(@data-value)='%s']";



    public VideoPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(this.driver, this);
    }
    @Step("Искать по строке : {request}")
    public void search(String request) {
        findElement(By.xpath(SEARCH_FIELD_LOCATOR)).sendKeys(request);
        findElement(By.xpath(SEARCH_FIELD_LOCATOR)).sendKeys(Keys.ENTER);

        waitUntilLoaderDisappears();

        logger.info("Search request sended: " + request );
    }
    @Step("Нажать кнопку \"More Filters\"")
    public void clickMoreFilters() {
        clickOnBy(By.xpath(MORE_FILTERS_LOCATOR));
        logger.info("MORE_FILTERS clicked");
    }
    @Step("Собрать все названия карточек")
    public List<String> collectArticleNamesFromCards (){
        waitUntilLoaderDisappears();
        List<WebElement> cards = driver.findElements(By.xpath(ARTICLE_LOCATOR));
        List <String> names = new ArrayList<>();
        for (WebElement card : cards) {
            names.add(card.findElement(By.xpath(ARTICLE_NAME_LOCATOR)).getText());
        }
        return names;
    }

    @Step("Кликнуть на первую карточку")
    public EventDetailsPage clickOnFirstArticle () {
        clickOnBy(By.xpath(IMAGE_ON_CARD_LOCATOR));
        logger.info("Go into first article after search");
        return new EventDetailsPage(driver);
    }

    @Step("Фильтрация по {filter} со значением {value}")
    public void makeFiltration (String filter, String value) {
        String currentFilter = String.format(TEMPLATE_FILTER, filter);
        String currentValue = String.format(TEMPLATE_VALUE, value);

        clickOnBy(By.xpath(currentFilter));
        logger.info("Selected filter : " + filter);
        clickOnBy(By.xpath(currentValue));
        logger.info("Selected value : " + value);
    }


}
