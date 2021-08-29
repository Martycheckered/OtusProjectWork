package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class MainPage extends GeneralPage {

    private static final String URL = "https://events.epam.com/";
    private static final String EVENTS_TAB_LOCATOR = ".nav-link[href=\"/events\"]";
    private static final String VIDEO_TAB_LOCATOR = ".nav-link[href^=\"/video\"]";


    public MainPage(WebDriver driver) {
        super(driver);
    }

    @Step("Открытие главной страницы")
    public MainPage open() {
        driver.get(URL);
        waitUntilLoaderDisappears();
        acceptCookie();
        logger.info("Events.epam main page is open");

        return this;
    }
    @Step("Переход на вкладку \"Events\"")
    public EventsPage goToEventsTab() {
        clickOnBy(By.cssSelector(EVENTS_TAB_LOCATOR));

        logger.info("Transfer to Events tab");
        return new EventsPage(driver);
    }
    @Step("Переход на вкладку \"Video\"")
    public VideoPage goToVideoTab() {
        clickOnBy(By.cssSelector(VIDEO_TAB_LOCATOR));

        logger.info("Transfer to Video tab");
        return new VideoPage(driver);
    }


}
