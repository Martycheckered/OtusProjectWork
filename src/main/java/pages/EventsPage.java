package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.EventType;

import java.util.ArrayList;
import java.util.List;

public class EventsPage extends GeneralPage {
    private static final String UPCOMING_COUNTER_LOCATOR = "//span[contains(text(),'Upcoming')]/following-sibling::span[contains(@class,'evnt-tab-counter')]";
    private static final String PAST_COUNTER_LOCATOR = "//span[contains(text(),'Past')]/following-sibling::span[contains(@class,'evnt-tab-counter')]";
    private static final String EVENT_CARD_LOCATOR = "div[class^=\"evnt-event-card\"]";
    private static final String EVENT_CARD_LOCATOR2 = "//div[contains(@class,'evnt-event-card')]";
    private static final String PAST_EVENTS_TAB_LOCATOR = "//span[contains(text(),'Past Events')]";
    private static final String UPCOMING_EVENTS_TAB_LOCATOR = "//span[contains(text(),'Upcoming events')]";
    private static final String EVENT_NAME_LOCATOR = "//h1[@data-original-title]/span";
    private static final String EVENT_LANGUAGE_LOCATOR = "//p[contains(@class,'language')]/span";
    private static final String EVENT_DATE_LOCATOR = ".//p/span[contains(@class,'date')]";
    private static final String EVENT_REGISTRATION_LOCATOR = "//p/span[contains(@class,'status')]";
    private static final String EVENT_SPEAKER_LOCATOR = "//div[contains(@class,'evnt-speaker')]";
    private static final String LOCATION_FILTER_LOCATOR = "//span[contains(@class, 'evnt-filter-text') and contains(text(), \"Location\")]";

    private static final String filterValue = "label[data-value=\"%s\"]";

    public EventsPage(WebDriver driver) {
        super(driver);
    }

    @Step("Получение количества мероприятий со счетчика")
    public String getEventsCounterValue(EventType type) {
        WebElement counter;
        if(type.equals(EventType.UPCOMING)) {
            counter = findElement(By.xpath(UPCOMING_COUNTER_LOCATOR));
        }
        else {
            counter = findElement(By.xpath(PAST_COUNTER_LOCATOR));
        }

        logger.info("Counter value is : " + counter.getText());
        return  counter.getText();
    }
    @Step("Получение количества карточек мероприятий")
    public String getAmountOfEventsCards() {

        WebElement [] events = driver.findElements(By.cssSelector(EVENT_CARD_LOCATOR)).toArray(new WebElement[0]);
        logger.info("Amount of cards is : " + String.valueOf(events.length));
        return String.valueOf(events.length);
    }

    @Step("Переключение на дочернюю вкладку \"Past Events\"")
    public void goToPastEventsSubTab() {
        clickOnBy(By.xpath(PAST_EVENTS_TAB_LOCATOR));
        logger.info("Past events subtab was clicked");
    }
    @Step("Переключение на дочернюю вкладку \"Upcoming Events\"")
    public void goToUpcomingEventsSubTab() {
        clickOnBy(By.xpath(UPCOMING_EVENTS_TAB_LOCATOR));
        logger.info("Upcoming events subtab was clicked");
    }
    @Step("Проверка, содержит ли карточка события язык")
    public boolean isEventCardContainsEventLanguage () {
        WebElement event = findElement(By.cssSelector(EVENT_CARD_LOCATOR));
        if (event.findElements(By.xpath(EVENT_LANGUAGE_LOCATOR)).size() == 0) {
            return false;
        }
        String eventLanguage = event.findElement(By.xpath(EVENT_LANGUAGE_LOCATOR)).getText();

        return !eventLanguage.equals("");
    }

    @Step("Проверка, содержит ли карточка события название")
    public boolean isEventCardContainsEventName () {
        WebElement event = findElement(By.cssSelector(EVENT_CARD_LOCATOR));
        if (event.findElements(By.xpath(EVENT_NAME_LOCATOR)).size() == 0) {
            return false;
        }
        String eventName = event.findElement(By.xpath(EVENT_NAME_LOCATOR)).getText();

        return !eventName.equals("");
    }

    @Step("Проверка, содержит ли карточка события дату")
    public boolean isEventCardContainsEventDate () {
        WebElement event = findElement(By.xpath(EVENT_CARD_LOCATOR2));
        if (event.findElements(By.xpath(EVENT_DATE_LOCATOR)).size() == 0) {
            return false;
        }
        String eventName = event.findElement(By.xpath(EVENT_DATE_LOCATOR)).getText();

        return !eventName.equals("");
    }

    @Step("Проверка, содержит ли карточка события регистрационный статус")
    public boolean isEventCardContainsRegistrationStatus() {
        WebElement event = findElement(By.cssSelector(EVENT_CARD_LOCATOR));
        if (event.findElements(By.xpath(EVENT_REGISTRATION_LOCATOR)).size() == 0) {
            return false;
        }
        String eventName = event.findElement(By.xpath(EVENT_REGISTRATION_LOCATOR)).getText();

        return !eventName.equals("");
    }

    @Step("Проверка, содержит ли карточка события спикеров")
    public boolean isEventCardContainsSpeakers() {
        WebElement event = findElement(By.cssSelector(EVENT_CARD_LOCATOR));
        if (event.findElements(By.xpath(EVENT_SPEAKER_LOCATOR)).size() == 0) {
            return false;
        }
       else
           return true;
    }

    @Step("Сбор всех дат с карточек мероприятий")
    public List <String> collectDatesOfEventsFromCards (){
        List<WebElement> cards = driver.findElements(By.xpath(EVENT_CARD_LOCATOR2));
        List <String> datesInCards = new ArrayList<>();
        for (WebElement card : cards) {
            datesInCards.add(card.findElement(By.xpath(EVENT_DATE_LOCATOR)).getText());
        }
        return datesInCards;
    }

    @Step("Выбрать местоположение: {locationName}")
    public void selectLocationByCssSelector (String locationName) {
        String finalLocator = String.format(filterValue, locationName);
        clickOnBy(By.xpath(LOCATION_FILTER_LOCATOR));
        clickOnBy(By.cssSelector(finalLocator));
        logger.info("Selected ---" + locationName);

    }
}
