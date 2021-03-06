import io.qameta.allure.Description;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.EventDetailsPage;
import pages.EventsPage;
import pages.MainPage;
import pages.VideoPage;
import utils.BaseHooks;
import utils.DateValidator;
import utils.EventType;
import utils.Helpers;

import java.io.IOException;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;


public class EpamTest extends BaseHooks {
    private final SoftAssert softAssert = new SoftAssert();

    @Test (testName = "Просмотр предстоящих мероприятий",
    description = "Просмотр предстоящих мероприятий")
    @Description("Проверка количества будущих мероприятий по счетчику")
    public void checkAmountOfFutureEventsTest () throws IOException {
        MainPage mainPage = new MainPage(getDriver());
        EventsPage eventsPage = mainPage.open().goToEventsTab();

        String counterNumber = eventsPage.getEventsCounterValue(EventType.UPCOMING);
        String cards = eventsPage.getAmountOfEventsCards();
        Helpers.takeScreenShot(getDriver(), "Будущие мероприятия");
        assertEquals(cards, counterNumber);

    }

    @Test (testName = "Просмотр прошлых мероприятий",
          description = "Просмотр прошлых мероприятий")
    @Description("Проверка наличия на карточках прошлых мероприятий атрибутов: язык, имя, дата, статус, спикеры")
    public void validatePastEventsCardsTest () throws IOException {
        MainPage mainPage = new MainPage(getDriver());
        EventsPage eventsPage = mainPage.open().goToEventsTab();
        eventsPage.goToPastEventsSubTab();
        Helpers.takeScreenShot(getDriver(), "Карточки прошедших мероприятий");

         softAssert.assertTrue(eventsPage.isEventCardContainsEventLanguage());
         softAssert.assertTrue(eventsPage.isEventCardContainsEventName());
         softAssert.assertTrue(eventsPage.isEventCardContainsEventDate());
         softAssert.assertTrue(eventsPage.isEventCardContainsRegistrationStatus());
         softAssert.assertTrue(eventsPage.isEventCardContainsSpeakers());

         softAssert.assertAll();

        logger.info("Event card has language, name, date, status, speakers");

    }


    @Test (testName = "Валидация дат предстоящих мероприятий",
    description = "Валидация дат предстоящих мероприятий")
    @Description("Проверка корректности дат будущих мероприятий")
    public void validateFutureEventsDatesTest () throws IOException {
        MainPage mainPage = new MainPage(getDriver());
        EventsPage eventsPage = mainPage.open().goToEventsTab();
        eventsPage.goToUpcomingEventsSubTab();

        Helpers.takeScreenShot(getDriver(), "Будущие мероприятия");

        List <String> eventDates = eventsPage.collectDatesOfEventsFromCards();
        for (String s: eventDates ) {
            logger.info(s);
        }
        List <Integer> results = DateValidator.validateDatesOfUpcomingEvents(eventDates);


      // Если сумма элементов списка равна 0, то все даты будущих мероприятий корректны
        long expected = 0;
        long listSum = results.stream()
                .mapToLong(Integer::longValue)
                .sum();

        assertEquals(expected, listSum);
    }

    @Test (testName = "Проверка дат мероприятий в конкретном городе",
    description = "Проверка дат мероприятий в конкретном городе")
    @Description("Проверка дат мероприятий, прошедших в Канаде")
    public void checkCanadaPastEventsTest () {

        MainPage mainPage = new MainPage(getDriver());
        EventsPage eventsPage = mainPage.open().goToEventsTab();
        eventsPage.goToPastEventsSubTab();
        String eventLocation = "Canada";
        eventsPage.selectLocationByCssSelector(eventLocation);


        String counterNumber = eventsPage.getEventsCounterValue(EventType.PAST);
        String cards = eventsPage.getAmountOfEventsCards();
        assertEquals(cards, counterNumber);

        List <String> eventDates = eventsPage.collectDatesOfEventsFromCards();
        for (String s: eventDates ) {
            logger.info(s);
        }
        List <Integer> results = DateValidator.validateDatesOfPastEvents(eventDates);

        // Если сумма элементов списка равна 0, то все даты прошедших мероприятий корректны
        long expected = 0;
        long listSum = results.stream()
                .mapToLong(Integer::longValue)
                .sum();
        Helpers.takeScreenShot(getDriver(), "Мероприятия в " + eventLocation);

        assertEquals(expected, listSum);

    }

    @Test (testName = "Фильтрация докладов",
            description = "Фильтрация докладов")
    @Description("Проверка фильтрации докладов")
    public void lectureFiltrationTest () throws IOException {
        MainPage mainPage = new MainPage(getDriver());
        VideoPage videoPage = mainPage.open().goToVideoTab();

        videoPage.clickMoreFilters();

        videoPage.makeFiltration("Category","Testing");
        videoPage.makeFiltration("Location", "Belarus");
        videoPage.makeFiltration("Language", "ENGLISH");

        //Прокрутка, чтобы можно было кликнуть на карточку после фильтрации
        JavascriptExecutor jse = (JavascriptExecutor)getDriver();
        jse.executeScript("window.scrollBy(0,250)");

        EventDetailsPage eventDetailsPage = videoPage.clickOnFirstArticle();
        Helpers.takeScreenShot(getDriver(), "Детали по выбранному докладу");

        String eventLocation = eventDetailsPage.getLocationInfo();
        String eventLanguage = eventDetailsPage.getLanguageInfo();
        List <WebElement> eventTags = eventDetailsPage.getCategories();

        boolean isTagPresent = false;
        for (WebElement tag : eventTags) {
            logger.debug("Получено значение: " + tag.getText());
            if (Helpers.isSubstringPresentInString(tag.getText(), "Testing")) {
                isTagPresent = true;
                break;
            }
        }

        softAssert.assertTrue(isTagPresent);
        softAssert.assertTrue(Helpers.isSubstringPresentInString(eventLocation,"Belarus"));
        softAssert.assertEquals("ENGLISH", eventLanguage);

        softAssert.assertAll();

    }

    @Test(testName = "Поиск докладов по ключевому слову",
            description = "Поиск докладов по ключевому слову")
    @Description("Проверка поиска по ключевому слову QA")
    public void searchVideoByKeyWordTest () throws InterruptedException, IOException {
        MainPage mainPage = new MainPage(getDriver());
        VideoPage videoPage = mainPage.open().goToVideoTab();

        String searchingWord = "QA";

        videoPage.search(searchingWord);

        Thread.sleep(1000);

        Helpers.takeScreenShot(getDriver(), "Результаты поиска по слову " + searchingWord);
        List <String> articleNames = videoPage.collectArticleNamesFromCards();

        for (String s: articleNames) {
            logger.info(s);
            assertTrue(Helpers.isSubstringPresentInString(s, searchingWord));

        }

    }
}

